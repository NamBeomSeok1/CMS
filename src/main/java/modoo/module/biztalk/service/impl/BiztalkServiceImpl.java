package modoo.module.biztalk.service.impl;

import java.net.URL;
import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import modoo.module.biztalk.service.BiztalkService;
import modoo.module.biztalk.service.BiztalkVO;
import net.sf.json.JSONObject;
import net.sf.json.JSONSerializer;
import egovframework.com.cmm.EgovMessageSource;
import egovframework.com.cmm.service.Globals;
import egovframework.com.cmm.util.EgovUserDetailsHelper;
import egovframework.com.utl.fcc.service.APIUtil;
import egovframework.com.utl.fcc.service.EgovDateUtil;
import egovframework.com.utl.fcc.service.EgovStringUtil;
import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;
import egovframework.rte.fdl.idgnr.EgovIdGnrService;

@Service("biztalkService")
public class BiztalkServiceImpl extends EgovAbstractServiceImpl implements BiztalkService {
	private static final Logger LOGGER = LoggerFactory.getLogger(BiztalkServiceImpl.class);
	
	@Resource(name = "biztalkMapper")
	private BiztalkMapper biztalkMapper;
	
	@Resource(name = "alimLogIdGnrService")
	private EgovIdGnrService alimLogIdGnrService;
	
	/** EgovMessageSource */
	@Resource(name = "egovMessageSource")
	protected EgovMessageSource egovMessageSource;
	
	//비즈톡 전송
	public BiztalkVO sendAlimTalk(BiztalkVO vo) throws Exception{
		if(EgovStringUtil.isEmpty(vo.getRecipient())){
			vo.setResponseCode("error");
			vo.setMsg(egovMessageSource.getMessage("NotEmpty.BiztalkVO.recipient"));
		}else if(EgovStringUtil.isEmpty(vo.getTmplatCode())){
			vo.setResponseCode("error");
			vo.setMsg(egovMessageSource.getMessage("NotEmpty.BiztalkVO.tmpltCode"));
		}else{
			String alimtalkLogId = alimLogIdGnrService.getNextStringId();
			String resMethod = EgovStringUtil.isEmpty(vo.getResMethod()) ? "PUSH" : vo.getResMethod();
			String token = bizTalkGetToken();
			vo.setToken(token);
			
			URL url = new URL(Globals.BIZTALK_APIURL + "/v2/kko/sendAlimTalk");
			JSONObject param = new JSONObject();
			param.put("msgIdx", alimtalkLogId);
			param.put("countryCode", EgovStringUtil.isEmpty(vo.getCountryCode()) ? Globals.BIZTALK_COUNTRYCODE : vo.getCountryCode());
			param.put("recipient", vo.getRecipient());
			param.put("senderKey", Globals.BIZTALK_SENDERKEY);
			param.put("message", vo.getMessage());
			param.put("tmpltCode", vo.getTmplatCode());
			param.put("resMethod", resMethod);
			
			LOGGER.info("BizTalk param :" + param.toString());
			LOGGER.info("BizTalk token :" + token);
			String jsonString = APIUtil.postBizUrl(url, param.toString(), token);
			LOGGER.info("BizTalk return : " + jsonString);
			JSONObject jObj = (JSONObject)JSONSerializer.toJSON(jsonString);
			String responseCode = jObj.getString("responseCode");
			
			vo.setAlimtalkLogId(alimtalkLogId);
			vo.setBizResultCode(responseCode);
			vo.setSendCode(resMethod);
			vo.setSendCn(param.toString());
			
			biztalkMapper.insertBizTalkHistory(vo);
		}
		
		return vo;
	}
	
	//비즈톡 토큰생성
	public String bizTalkGetToken() throws Exception{
		String token = "";
		BiztalkVO vo = new BiztalkVO();
		vo.setServerIp(Globals.BIZTALK_SERVERIP);
		
		BiztalkVO result = biztalkMapper.selectBizTalkToken(vo);
		//하루에 한 번 토큰 발급
		if(result != null){
			token = result.getToken();
		}else{
			URL url = new URL(Globals.BIZTALK_APIURL + "/v2/auth/getToken");
			JSONObject param = new JSONObject();
			param.put("bsid", Globals.BIZTALK_BSID);
			param.put("passwd", Globals.BIZTALK_PASSWD);
			
			String jsonString = APIUtil.postBizUrl(url, param.toString(), null);
			JSONObject jObj = (JSONObject)JSONSerializer.toJSON(jsonString);
			String responseCode = jObj.getString("responseCode");
			if("1000".equals(responseCode)){
				token = jObj.getString("token");
				vo.setToken(token);
				biztalkMapper.insertBizTalkToken(vo);
			}
		}
		
		return token;
	}
	
	//알림톡내용 조회
	public BiztalkVO selectBizTalkTemplate(BiztalkVO vo) throws Exception{
		return biztalkMapper.selectBizTalkTemplate(vo);
	}
}
