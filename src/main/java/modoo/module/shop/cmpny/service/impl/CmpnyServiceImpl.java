package modoo.module.shop.cmpny.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import egovframework.com.utl.sim.service.EgovFileScrty;
import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;
import egovframework.rte.fdl.idgnr.EgovIdGnrService;
import egovframework.rte.psl.dataaccess.util.EgovMap;
import modoo.module.common.util.SiteDomainHelper;
import modoo.module.mber.info.service.MberVO;
import modoo.module.mber.info.service.impl.MberMapper;
import modoo.module.shop.cmpny.service.CmpnyService;
import modoo.module.shop.cmpny.service.CmpnyVO;
import modoo.module.shop.cmpny.service.PrtnrCmpnyVO;
import modoo.module.shop.cmpny.service.PrtnrVO;
import modoo.module.shop.hdry.service.HdryCmpnyVO;

@Service("cmpnyService")
public class CmpnyServiceImpl extends EgovAbstractServiceImpl implements CmpnyService {

	@Resource(name = "cmpnyMapper")
	private CmpnyMapper cmpnyMapper;
	
	@Resource(name = "mberMapper")
	private MberMapper mberMapper;
	
	@Resource(name = "prtnrCmpnyMapper")
	private PrtnrCmpnyMapper prtnrCmpnyMapper;

	@Resource(name = "cmpnyIdGnrService")
	private EgovIdGnrService cmpnyIdGnrService;
	
	@Resource(name = "MberIdGnrService")
	private EgovIdGnrService mberIdGnrService;
	
	@Resource(name = "prtnCmpnyMapngIdGnrService")
	private EgovIdGnrService prtnCmpnyMapngIdGnrService;
	
	private static final String B2C_PRTNRID = "PRTNR_0000"; // B2C 기본
	
	/**
	 * 업체목록
	 */
	@Override
	public List<?> selectCmpnyList(CmpnyVO searchVO) throws Exception {
		return cmpnyMapper.selectCmpnyList(searchVO);
	}

	/**
	 * 업체목록 카운트
	 */
	@Override
	public int selectCmpnyListCnt(CmpnyVO searchVO) throws Exception {
		return cmpnyMapper.selectCmpnyListCnt(searchVO);
	}
	
	/**
	 * 업체 전체 목록
	 */
	@Override
	public List<?> selectCmpnyAllList(CmpnyVO searchVO) throws Exception {
		return cmpnyMapper.selectCmpnyAllList(searchVO);
	}

	/**
	 * 업체저장
	 */
	@Override
	public void insertCmpny(CmpnyVO cmpny) throws Exception {
		String esntlId = cmpny.getCmpnyUserEsntlId();
		//User 등록
		if(StringUtils.isEmpty(esntlId)) { // 신규회원
			MberVO mber = new MberVO();
			esntlId = mberIdGnrService.getNextStringId();
			String encryptPassword = EgovFileScrty.encryptPassword(cmpny.getCmpnyMberPassword(), cmpny.getCmpnyMberId());

			mber.setMberNm(cmpny.getCmpnyNm()); //업체명
			mber.setEsntlId(esntlId);
			mber.setEmail(cmpny.getChargerEmail()); //담당자이메일
			mber.setSiteId(SiteDomainHelper.getSiteId());
			mber.setMberId(cmpny.getCmpnyMberId());
			mber.setPassword(encryptPassword);
			mber.setMberSttus("P");
			mber.setAuthorCode("ROLE_SHOP");
			mber.setMberTyCode("USR02");
			
			mberMapper.insertMber(mber);
		}

		String cmpnyId = cmpnyIdGnrService.getNextStringId();
		cmpny.setCmpnyId(cmpnyId);
		cmpny.setCmpnyUserEsntlId(esntlId);
		cmpnyMapper.insertCmpny(cmpny);

		//택배사
		/*if(cmpny.getHdryCmpnyList() != null && cmpny.getHdryCmpnyList().size() > 0) {
			HdryCmpnyVO hdryCmpny = cmpny.getHdryCmpnyList().get(0);
			CmpnyVO vo = new CmpnyVO();
			vo.setCmpnyId(cmpny.getCmpnyId());
			vo.setHdryId(hdryCmpny.getHdryId());
			cmpnyMapper.insertHdryCmpnyMapng(vo);
		}*/
		if(cmpny.getHdryCmpnyList() != null) {
			for(HdryCmpnyVO hdryCmpny : cmpny.getHdryCmpnyList()) {
				if(hdryCmpny.getHdryId() != null) {
					CmpnyVO vo = new CmpnyVO();
					vo.setCmpnyId(cmpny.getCmpnyId());
					vo.setHdryId(hdryCmpny.getHdryId());
					cmpnyMapper.insertHdryCmpnyMapng(vo);
				}
			}
		}
		
		//제휴사 처리
		for(PrtnrCmpnyVO prtnrCmpny : cmpny.getPrtnrCmpnyList()) {
			prtnrCmpny.setCmpnyId(cmpny.getCmpnyId());

			if(StringUtils.isNotEmpty(prtnrCmpny.getUseAt()) && "Y".equals(prtnrCmpny.getUseAt())) {
				if(StringUtils.isEmpty(prtnrCmpny.getPcmapngId())) {
					String pcmapngId = prtnCmpnyMapngIdGnrService.getNextStringId();
					prtnrCmpny.setPcmapngId(pcmapngId);
					prtnrCmpnyMapper.insertPrtnrCmpny(prtnrCmpny);
				}else {
					prtnrCmpnyMapper.updatePrtnrCmpny(prtnrCmpny);
				}
			}else {
				if(StringUtils.isNotEmpty(prtnrCmpny.getPcmapngId())) {
					//매핑이 삭제 전 STN_GOODS PCMAPNG_ID NULL 처리

					prtnrCmpnyMapper.deletePrtnrCmpny(prtnrCmpny);
				}
				
			}
			
		}
		
	}

	/**
	 * 업체상세
	 */
	@Override
	public CmpnyVO selectCmpny(CmpnyVO cmpny) throws Exception {
		List<HdryCmpnyVO> hdryCmpnyList = cmpnyMapper.selectHdryCmpnyMapngList(cmpny);
		CmpnyVO resultVO = cmpnyMapper.selectCmpny(cmpny);
		if(resultVO != null) {
			resultVO.setHdryCmpnyList(hdryCmpnyList);
			
			PrtnrCmpnyVO prtnrCmpny = new PrtnrCmpnyVO();
			prtnrCmpny.setCmpnyId(cmpny.getCmpnyId());
			List<PrtnrCmpnyVO> prtnrCmpnyList = prtnrCmpnyMapper.selectPrtnrCmpnyList(prtnrCmpny);
			
			resultVO.setPrtnrCmpnyList(prtnrCmpnyList);
		}

		return resultVO;
	}

	/**
	 * 업체수정
	 */
	@Override
	public void updateCmpny(CmpnyVO cmpny) throws Exception {
		cmpnyMapper.updateCmpny(cmpny);
		
		cmpnyMapper.deleteHdryCmpnyMapng(cmpny);
		
		//택배사
		/*if(cmpny.getHdryCmpnyList() != null && cmpny.getHdryCmpnyList().size() > 0) {
			HdryCmpnyVO hdryCmpny = cmpny.getHdryCmpnyList().get(0);
			CmpnyVO vo = new CmpnyVO();
			vo.setCmpnyId(cmpny.getCmpnyId());
			vo.setHdryId(hdryCmpny.getHdryId());
			cmpnyMapper.insertHdryCmpnyMapng(vo);
		}*/
		if(cmpny.getHdryCmpnyList() != null) {
			for(HdryCmpnyVO hdryCmpny : cmpny.getHdryCmpnyList()) {
				if(hdryCmpny.getHdryId() != null) {
					CmpnyVO vo = new CmpnyVO();
					vo.setCmpnyId(cmpny.getCmpnyId());
					vo.setHdryId(hdryCmpny.getHdryId());
					cmpnyMapper.insertHdryCmpnyMapng(vo);
				}
			}
		}
		
		//제휴사처리
		for(PrtnrCmpnyVO prtnrCmpny : cmpny.getPrtnrCmpnyList()) {
			prtnrCmpny.setCmpnyId(cmpny.getCmpnyId());

			if(StringUtils.isNotEmpty(prtnrCmpny.getUseAt()) && "Y".equals(prtnrCmpny.getUseAt())) {
				if(StringUtils.isEmpty(prtnrCmpny.getPcmapngId())) {
					String pcmapngId = prtnCmpnyMapngIdGnrService.getNextStringId();
					prtnrCmpny.setPcmapngId(pcmapngId);
					prtnrCmpnyMapper.insertPrtnrCmpny(prtnrCmpny);
				}else {
					prtnrCmpnyMapper.updatePrtnrCmpny(prtnrCmpny);
				}
			}else {
				if(StringUtils.isNotEmpty(prtnrCmpny.getPcmapngId())) {
					//매핑이 삭제 전 STN_GOODS PCMAPNG_ID NULL 처리

					prtnrCmpnyMapper.deletePrtnrCmpny(prtnrCmpny);
				}
				
			}
			
		}

	}

	/**
	 * 업체삭제
	 */
	@Override
	public void deleteCmpny(CmpnyVO cmpny) throws Exception {
		cmpnyMapper.deleteCmpny(cmpny);
	}

	/**
	 * 사업자등록번호 조회 카운트
	 */
	@Override
	public int selectCmpnyBizrnoCheckCnt(CmpnyVO cmpny) throws Exception {
		return cmpnyMapper.selectCmpnyBizrnoCheckCnt(cmpny);
	}

	/**
	 * 업체 사용자 검색
	 */
	@Override
	public List<?> selectCmpnyMberList(MberVO searchVO) throws Exception {
		return cmpnyMapper.selectCmpnyMberList(searchVO);
	}

	/**
	 * 업체 사용자 목록 카운
	 */
	@Override
	public int selectCmpnyMberListCnt(MberVO searchVO) throws Exception {
		return cmpnyMapper.selectCmpnyMberListCnt(searchVO);
	}

	/**
	 * 업체 사용자 비밀번호 초기화
	 */
	@Override
	public void initCmpnyMberPassword(CmpnyVO cmpny) throws Exception {
		String password = EgovFileScrty.encryptPassword(cmpny.getCmpnyMberPassword(),cmpny.getCmpnyMberId());
		cmpny.setCmpnyMberPassword(password);
		
		cmpnyMapper.updateCmpnyMberPassword(cmpny);
	}

	/**
	 * 업체상태코드 수정
	 */
	@Override
	public void updateHdryCmpnySttusCode(CmpnyVO cmpny) throws Exception {
		cmpnyMapper.updateHdryCmpnySttusCode(cmpny);
	}
	
	/*
	 * 주문상품업체번호
	 */
	@Override
	public CmpnyVO selectOrderCmpnyTelno(EgovMap map) throws Exception {
		return cmpnyMapper.selectOrderCmpnyTelno(map);
	}
	/**
	 * 공지사항 알림톡 발송할 CP 담당자 전화번호 목록
	 */
	@Override
	public List<CmpnyVO> selectContactList() {
		return cmpnyMapper.selectContactList();
	}
}
