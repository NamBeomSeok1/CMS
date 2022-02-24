package modoo.module.mber.info.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.net.URI;

import javax.annotation.Resource;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.apache.http.impl.client.CloseableHttpClient;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.twelvemonkeys.lang.StringUtil;

import modoo.module.common.service.JsonResult;
import modoo.module.common.service.ModooUserDetailHelper;
import modoo.module.mber.agre.service.MberAgreService;
import modoo.module.mber.agre.service.MberAgreVO;
import modoo.module.mber.info.service.MberService;
import modoo.module.mber.info.service.MberVO;
import egovframework.com.cmm.service.Globals;
import egovframework.com.utl.fcc.service.EgovStringUtil;
import egovframework.com.utl.sim.service.EgovFileScrty;
import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;
import egovframework.rte.fdl.idgnr.EgovIdGnrService;

@Service("mberService")
public class MberServiceImpl extends EgovAbstractServiceImpl implements MberService {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(MberServiceImpl.class);
	
	@Resource(name = "mberMapper")
	private MberMapper mberMapper;
	
	@Resource(name = "MberIdGnrService")
	private EgovIdGnrService mberIdGnrService;
	
	@Resource(name = "mberAgreService")
	private MberAgreService mberAgreService;
	
	//TODO : 이주화 구성시 와스간에 mber권한 
	private List<MberVO> mberCachedAuthList = new ArrayList<MberVO>();
	
	/**
	 * 회원 목록
	 */
	@Override
	public List<?> selectMberList(MberVO searchVO) throws Exception {
		return mberMapper.selectMberList(searchVO);
	}

	/**
	 * 회원 목록 카운트
	 */
	@Override
	public int selectMberListCnt(MberVO searchVO) throws Exception {
		return mberMapper.selectMberListCnt(searchVO);
	}

	/**
	 * 회원 저장
	 */
	@Override
	public void insertMber(MberVO mber) throws Exception {
		String esntlId = mberIdGnrService.getNextStringId();
		mber.setEsntlId(esntlId);

		String encryptPassword = EgovFileScrty.encryptPassword(mber.getPassword(), mber.getMberId());
		mber.setPassword(encryptPassword);
		
		mberMapper.insertMber(mber);
		
		for(MberVO vo : this.mberCachedAuthList) {
			System.out.println("before : " + vo.getMberId() + "," + vo.getAuthorCode());
		}
		
		MberVO newMber = new MberVO();
		newMber.setMberId(mber.getMberTyCode() + mber.getMberId());
		newMber.setAuthorCode(mber.getAuthorCode());
		
		MberService mService =  ModooUserDetailHelper.getMberService();
		mService.addMberRole(newMber);

	}
	
	/**
	 * SSO 회원 저장
	 */
	@Override
	public String insertSsoMber(MberVO mber) throws Exception {
		String esntlId = mberIdGnrService.getNextStringId();
		mber.setEsntlId(esntlId);
		
		//패스워드 처리는 의미 없게 처리
		if(!"KAKAO".equals(mber.getClientCd()) && !"GOOGLE".equals(mber.getClientCd()) && !"APPLE".equals(mber.getClientCd()) && !"NAVER".equals(mber.getClientCd())){
			mber.setPassword("sso_user_pass"); //실제 패스워드가 아니고 NOT NULL 처리
		}
		
		//회원저장
		mberMapper.insertSsoMber(mber);
		
		MberVO newMber = new MberVO();
		newMber.setMberId(mber.getMberTyCode() + mber.getMberId());
		newMber.setAuthorCode(mber.getAuthorCode());
		
		MberService mService =  ModooUserDetailHelper.getMberService();
		mService.addMberRole(newMber);;
		
		return esntlId;
	}
	
	/**
	 * PORTAL 회원 저장 
	 */
	@Override
	public void insertPortalMber(MberVO mber) throws Exception {
		HttpPost httpPost = new HttpPost();
		httpPost.addHeader("Accept", "application/json");
		httpPost.addHeader("Content-Type", "application/json");
		
		String portalUrl = Globals.FOX_PORTALURL + "/api/mber/mberInfo.json";
		
		URI uri = new URIBuilder(portalUrl)
				.addParameter("esntlId", mber.getEsntlId())
				.build();
		httpPost.setURI(uri);
		
		HttpClient httpClient = HttpClientBuilder.create().build();
		HttpResponse httpResponse = httpClient.execute(httpPost);
		
		String resultJson = EntityUtils.toString(httpResponse.getEntity());
		
		ObjectMapper objectMapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		JsonResult jsonResult = objectMapper.readValue(resultJson, JsonResult.class);
		
		if(jsonResult.isSuccess()) {
			JSONParser parser = new JSONParser();
			JSONObject obj = (JSONObject) parser.parse(resultJson);
			JSONObject data = (JSONObject) obj.get("data");
			JSONObject mberData = (JSONObject) obj.get("mber");
			
			mber.setEsntlId(mberData.get("esntlId").toString());
			mber.setMberId(mberData.get("mberId").toString());
			mber.setMberNm(mberData.get("mberNm").toString());
			mber.setEmail(mberData.get("email").toString());
			mber.setMberTyCode(mberData.get("mberTyCode").toString());
			mber.setProfileImg(mberData.get("profileImg").toString());
			mber.setMberSttus("P");
			mber.setSiteId("SITE_00000");
			//성별, 연령대 추가하기
			
			
			//추후 멤버 타입에 따라 변경 될 수 있음
			mber.setGroupId("GROUP_00000000000001");
			mber.setAuthorCode("ROLE_USER");
			
			if(mber != null && !EgovStringUtil.isEmpty(mber.getEsntlId())) {
				mberMapper.insertPortalMber(mber);
			}
		}
	}
	
	/**
	 * 회원 상세
	 */
	@Override
	public MberVO selectMber(MberVO mber) throws Exception {
		return mberMapper.selectMber(mber);
	}

	/**
	 * 회원 수정
	 */
	@Override
	public void updateMber(MberVO mber) throws Exception {
		mberMapper.updateMber(mber);
	}
	
	/**
	 * 회원 상태 수정
	 */
	@Override
	public void updateMberSttus(MberVO mber) throws Exception {
		mberMapper.updateMberSttus(mber);
	}

	/**
	 * 회원 삭제
	 */
	@Override
	public void deleteMber(MberVO mber) throws Exception {
		mberMapper.deleteMber(mber);
	}

	/**
	 * 회원 ID 중복 카운트
	 */
	@Override
	public int selectCheckDuplMberIdCnt(MberVO mber) throws Exception {
		return mberMapper.selectCheckDuplMberIdCnt(mber);
	}

	/**
	 * 비밀번호 수정
	 */
	@Override
	public void updatePassword(MberVO mber) throws Exception {
		String encryptPassword = EgovFileScrty.encryptPassword(mber.getPassword(), mber.getMberId());
		mber.setPassword(encryptPassword);
		mberMapper.updatePassword(mber);
	}

	/**
	 * 잠김 해제
	 */
	@Override
	public void updateLockIncorrect(MberVO mber) throws Exception {
		mberMapper.updateLockIncorrect(mber);
	}

	/**
	 * SSO 회원가입체크
	 */
	@Override
	public int selectSsoMemberCheck(MberVO mber) throws Exception {
		return mberMapper.selectSsoMemberCheck(mber);
	}

	/**
	 * SSO 회원상세
	 */
	@Override
	public MberVO selectSsoMember(MberVO mber) throws Exception {
		return mberMapper.selectSsoMember(mber);
	}

	/**
	 * 회원권한 목록
	 */
	@Override
	public List<MberVO> selectMberAuthList() throws Exception {
		if(mberCachedAuthList.size() == 0)  {
			this.mberCachedAuthList = mberMapper.selectMberAuthList();
		}else {
			LOGGER.info("GET Cached : mberCachedAuthList ");
		}
		return this.mberCachedAuthList;
	}
	
	/**
	 * 이지웰 상태 수정
	 */
	@Override
	public void updateEzwelMember(MberVO mber) throws Exception {
		mberMapper.updateEzwelMember(mber);
	}

	@Override
	public void addMberRole(MberVO mber) throws Exception {
		this.mberCachedAuthList.add(mber);
	}

	


}
