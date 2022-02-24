package modoo.cms.shop.cmpny.web;

import java.util.List;

import javax.annotation.Resource;
import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import egovframework.com.cmm.ComDefaultCodeVO;
import egovframework.com.cmm.LoginVO;
import egovframework.com.cmm.service.EgovCmmUseService;
import egovframework.com.cmm.util.EgovUserDetailsHelper;
import egovframework.com.uat.uia.service.EgovLoginService;
import egovframework.com.utl.fcc.service.EgovDateUtil;
import egovframework.rte.psl.dataaccess.util.EgovMap;
import egovframework.rte.ptl.mvc.tags.ui.pagination.PaginationInfo;
import modoo.module.common.service.JsonResult;
import modoo.module.common.util.SiteDomainHelper;
import modoo.module.common.web.CommonDefaultController;
import modoo.module.mber.info.service.MberService;
import modoo.module.mber.info.service.MberVO;
import modoo.module.shop.bank.service.BankService;
import modoo.module.shop.bank.service.BankVO;
import modoo.module.shop.cmpny.service.CmpnyService;
import modoo.module.shop.cmpny.service.CmpnyVO;
import modoo.module.shop.cmpny.service.PrtnrCmpnyService;
import modoo.module.shop.cmpny.service.PrtnrCmpnyVO;
import modoo.module.shop.cmpny.service.PrtnrService;
import modoo.module.shop.goods.brand.service.GoodsBrandService;
import modoo.module.shop.goods.info.service.GoodsService;
import modoo.module.shop.goods.info.service.GoodsVO;
import modoo.module.shop.hdry.service.HdryCmpnyService;
import modoo.module.shop.hdry.service.HdryCmpnyVO;

@Controller
public class CmsCmpnyController extends CommonDefaultController {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(CmsCmpnyController.class);
	
	private static final String EZW_PRTNR_ID = "PRTNR_0001";
	
	@Resource(name = "cmpnyService")
	private CmpnyService cmpnyService;
	
	/** EgovCmmUseService */
	@Resource(name = "EgovCmmUseService")
	private EgovCmmUseService cmmUseService;
	
	@Resource(name = "bankService")
	private BankService bankService;
	
	@Resource(name = "hdryCmpnyService")
	private HdryCmpnyService hdryCmpnyService;
	
	@Resource(name = "mberService")
	private MberService mberService;
	
	@Resource(name = "goodsService")
	private GoodsService goodsService;
	
	@Resource(name = "prtnrService")
	private PrtnrService prtnrService;
	
	@Resource(name = "prtnrCmpnyService")
	private PrtnrCmpnyService prtnrCmpnyService;
	
	@Resource(name = "loginService")
	private EgovLoginService loginService;
	
	@Resource(name = "goodsBrandService")
	private GoodsBrandService goodsBrandService;
	
	
	/**
	 * 업체 관리
	 * @param searchVO
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/decms/shop/cmpny/cmpnyManage.do")
	public String cmpnyManage(@ModelAttribute("searchVO") CmpnyVO searchVO, Model model) throws Exception {
		
		//내부 직원 권한이 아닐때 
		if(!EgovUserDetailsHelper.getAuthorities().contains("ROLE_EMPLOYEE")) {
			return "redirect:/decms/index.do";
		}
		
		if(StringUtils.isEmpty(searchVO.getSearchBgnde())) {
			searchVO.setSearchBgnde(EgovDateUtil.addMonth(EgovDateUtil.getToday(),-3));
		}
		
		if(StringUtils.isEmpty(searchVO.getSearchEndde())) {
			searchVO.setSearchEndde(EgovDateUtil.getToday());
		}

		return "modoo/cms/shop/cmpny/cmpnyManage";
	}
	
	/**
	 * 업체목록
	 * @param searchVO
	 * @return
	 */
	@RequestMapping(value = "/decms/shop/cmpny/cmpnyList.json")
	@ResponseBody
	public JsonResult cmpnyList(CmpnyVO searchVO) {
		JsonResult jsonResult = new JsonResult();
		
		try {
			//내부 직원 권한이 아닐때 
			if(!EgovUserDetailsHelper.getAuthorities().contains("ROLE_EMPLOYEE")) {
				jsonResult.setMessage(egovMessageSource.getMessage("cms.fail.accessDenied")); //접근 권한이 없습니다.
				jsonResult.setSuccess(false);
			}else {
				if(StringUtils.isNotEmpty(searchVO.getSearchBgnde())) {
					searchVO.setSearchBgnde(EgovDateUtil.validChkDate(searchVO.getSearchBgnde()));
				}
				if(StringUtils.isNotEmpty(searchVO.getSearchEndde())) {
					searchVO.setSearchEndde(EgovDateUtil.validChkDate(searchVO.getSearchEndde()));
				}
				
				PaginationInfo paginationInfo = new PaginationInfo();
				searchVO.setPageUnit(propertiesService.getInt("gridPageUnit"));
				this.setPagination(paginationInfo, searchVO);
				
				List<?> resultList = cmpnyService.selectCmpnyList(searchVO);
				jsonResult.put("list", resultList);
				
				int totalRecordCount = cmpnyService.selectCmpnyListCnt(searchVO);
				paginationInfo.setTotalRecordCount(totalRecordCount);
				jsonResult.put("paginationInfo", paginationInfo);
				
				jsonResult.setSuccess(true);
			}
			
		} catch(Exception e) {
			loggerError(LOGGER, e);
			jsonResult.setSuccess(false);
			jsonResult.setMessage(egovMessageSource.getMessage("fail.common.select")); //조회에 실패하였습니다.
		}
		
		return jsonResult;
	}
	
	/**
	 * 업체 전체 목록
	 * @param searchVO
	 * @return
	 */
	@RequestMapping(value = "/decms/shop/cmpny/cmpnyAllList.json")
	@ResponseBody
	public JsonResult cmpnyAllList(CmpnyVO searchVO) {
		JsonResult jsonResult = new JsonResult();
		
		try {
			//관리자 권한이 아닐때 
			if(!EgovUserDetailsHelper.getAuthorities().contains("ROLE_EMPLOYEE")) {
				jsonResult.setMessage(egovMessageSource.getMessage("cms.fail.accessDenied")); //접근 권한이 없습니다.
				jsonResult.setSuccess(false);
			}else {
				List<?> resultList = cmpnyService.selectCmpnyAllList(searchVO);
				jsonResult.put("list", resultList);
				
				jsonResult.setSuccess(true);
			}
		} catch(Exception e) {
			loggerError(LOGGER, e);
			jsonResult.setSuccess(false);
			jsonResult.setMessage(egovMessageSource.getMessage("fail.common.select")); //조회에 실패하였습니다.
		}
		
		return jsonResult;
	}
	
	/**
	 * 업체등록 폼
	 * @param searchVO
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/decms/shop/cmpny/writeCmpny.do")
	public String writeCmpny(@ModelAttribute("searchVO") CmpnyVO searchVO,
			@RequestParam(value = "menuId") String menuId, Model model) throws Exception {
		
		//내부 직원 권한이 아닐때 
		if(!EgovUserDetailsHelper.getAuthorities().contains("ROLE_EMPLOYEE")) {
			return "redirect:/decms/index.do";
		}
		
		// 제휴사 목록
//		PrtnrVO prtnr = new PrtnrVO();
//		List<PrtnrVO> prtnrList = prtnrService.selectPrtnrList(prtnr);
//		model.addAttribute("prtnrList", prtnrList);

		// 정산일구분코드 목록
		ComDefaultCodeVO codeVO = new ComDefaultCodeVO();
		codeVO.setCodeId("CMS005");
		List<?> stdeSeCodeList = cmmUseService.selectCmmCodeDetail(codeVO);
		model.addAttribute("stdeSeCodeList", stdeSeCodeList);
		
		//공급가 정책구분코드 목록
		codeVO.setCodeId("CMS006");
		List<?> splpcSeCodeList = cmmUseService.selectCmmCodeDetail(codeVO);
		model.addAttribute("splpcSeCodeList", splpcSeCodeList);
		
		//등록상태코드 목록
		codeVO.setCodeId("CMS007");
		List<?> opnngSttusCodeList = cmmUseService.selectCmmCodeDetail(codeVO);
		model.addAttribute("opnngSttusCodeList", opnngSttusCodeList); 
		
		//은행목록
		BankVO bank = new BankVO();
		List<BankVO> bankList = bankService.selectBankList(bank);
		model.addAttribute("bankList", bankList);
		
		//택배사목록
		HdryCmpnyVO hdryCmpny = new HdryCmpnyVO();
		List<?> hdryCmpnyList = hdryCmpnyService.selectHdryCmpnyList(hdryCmpny);
		model.addAttribute("hdryCmpnyList", hdryCmpnyList);
		
		CmpnyVO cmpny = new CmpnyVO();
		cmpny.setOpnngDe(EgovDateUtil.getToday()); //입점일
		cmpny.setSplpcSeCode("SP01"); // 카테고리 수수료 정책 (기본)
		cmpny.setOpnngSttusCode("R"); // 등록대기
		cmpny.setPrtnrId(EZW_PRTNR_ID); // 이지웰 파트너사
		cmpny.setStdeSeCode("STDE04"); //말일 고정 2020.10.07 오영석 차장 요청
		cmpny.setPrtnrCmpnyList(prtnrCmpnyService.selectPrtnrCmpnyList(new PrtnrCmpnyVO())); // 제휴사 목록
		cmpny.setRtngudDlvyPc(java.math.BigDecimal.ZERO); //반품 배송비
		cmpny.setExchngDlvyPc(java.math.BigDecimal.ZERO); //교환 배송비
		model.addAttribute("cmpny", cmpny);
		

		return "modoo/cms/shop/cmpny/cmpnyForm";
	}
	
	/**
	 * 업체 정보 입력값 공백 제거처리
	 * @param cmpny
	 */
	private void trimCmpnyInfoValue(CmpnyVO cmpny) {
		cmpny.setCmpnyNm(cmpny.getCmpnyNm().trim());
		cmpny.setBizrno(cmpny.getBizrno().trim());
		cmpny.setChargerEmail(cmpny.getChargerEmail().trim());
	}
	
	/**
	 * 업체저장
	 * @param cmpny
	 * @param bindingResult
	 * @return
	 */
	@RequestMapping(value = "/decms/shop/cmpny/writeCmpny.json")
	@ResponseBody
	public JsonResult writeCmpny(@Valid CmpnyVO cmpny, BindingResult bindingResult,
			@RequestParam(value = "menuId") String menuId) {
		JsonResult jsonResult = new JsonResult();
		LoginVO user = (LoginVO)EgovUserDetailsHelper.getAuthenticatedUser();
		
		try {
			this.trimCmpnyInfoValue(cmpny);
			
			//관리자 권한이 아닐때 
			if(!EgovUserDetailsHelper.getAuthorities().contains("ROLE_EMPLOYEE")) {
				jsonResult.setMessage(egovMessageSource.getMessage("cms.fail.accessDenied")); //접근 권한이 없습니다.
				jsonResult.setSuccess(false);
			}else if(!this.isHasErrorsJson(bindingResult, jsonResult, "<br/>")) {
				
				
				if(StringUtils.isEmpty(cmpny.getCmpnyUserEsntlId())) {
					this.vaildateMessage(egovMessageSource.getMessage("NotEmpty.cmpnyVO.cmpnyUserEsntlId"), jsonResult); // 사용자를 선택하세요.
				}else if(cmpny.getHdryCmpnyList() == null || cmpny.getHdryCmpnyList().size() == 0) {
					jsonResult.setMessage("배송회사를 선택하세요!"); 
					jsonResult.setSuccess(false);
				}else {
					int pcCnt = 0;
					for(PrtnrCmpnyVO pc : cmpny.getPrtnrCmpnyList()) {
						if(StringUtils.isNotEmpty(pc.getUseAt()) && "Y".equals(pc.getUseAt())) {
							pcCnt++;
						}
					}
					
					if(pcCnt == 0) {
						jsonResult.setMessage("제휴사를를 선택하세요!"); 
						jsonResult.setSuccess(false);
						return jsonResult;
					}
					
					MberVO mber = new MberVO();
					mber.setMberId(cmpny.getCmpnyMberId());
					/*
					//신규ID 생성이면
					if(StringUtils.isEmpty(cmpny.getCmpnyUserEsntlId())) {
						
						//비밀번호 길이 검사
						if(cmpny.getCmpnyMberPassword().length() < 8 || cmpny.getCmpnyMberPassword().length() > 20) {
							this.vaildateMessage(egovMessageSource.getMessage("Length.cmpnyVO.cmpnyMberPassword"), jsonResult); // 비밀번호는 8자이상 20자 이하로 입력하세요.
							return jsonResult;
						}
						
						//중복ID 검사
						//mber.setMberId(cmpny.getCmpnyMberId());
						if(mberService.selectCheckDuplMberIdCnt(mber) > 0) {
							this.vaildateMessage(egovMessageSource.getMessage("mberVO.fail.useMberId"), jsonResult); //이미 등록된 ID 입니다.
							return jsonResult;
						}
					}else { //기존ID 연결
						if(mberService.selectCheckDuplMberIdCnt(mber) == 0) {
							this.vaildateMessage(egovMessageSource.getMessage("mberVO.fail.notExistMberId"), jsonResult); //존재하지 않는 사용자 ID 입니다.
							return jsonResult;
						}
					}
					*/
					
					if(mberService.selectCheckDuplMberIdCnt(mber) == 0) {
						this.vaildateMessage(egovMessageSource.getMessage("mberVO.fail.notExistMberId"), jsonResult); //존재하지 않는 사용자 ID 입니다.
						return jsonResult;
					}
					
					String bizrno = cmpny.getBizrno().replaceAll("[^0-9]", "");
					
					CmpnyVO svo = new CmpnyVO();
					svo.setSearchCondition("EQ_NM");
					svo.setSearchKeyword(cmpny.getCmpnyNm());
					if(cmpnyService.selectCmpnyListCnt(svo) > 0) {
						this.vaildateMessage(egovMessageSource.getMessage("cmpny.fail.existCmpnyNm"), jsonResult); //이미 존재하는 업체명 입니다.
					}else if(cmpnyService.selectCmpnyBizrnoCheckCnt(cmpny) > 0) {
						this.vaildateMessage(egovMessageSource.getMessage("cmpny.fail.existBizrno"), jsonResult); //이미 존재하는 사업자등록번호 입니다.
					}else {
						cmpny.setBizrno(bizrno);
						cmpny.setOpnngDe(EgovDateUtil.validChkDate(cmpny.getOpnngDe()));
						cmpny.setFrstRegisterId(user.getUniqId());
						
						cmpnyService.insertCmpny(cmpny);
						
						//브랜드 메뉴 다시 읽기
						goodsBrandService.reloadGoodsBrandGroupList();
						
						String redirectUrl = "/decms/shop/cmpny/cmpnyManage.do?menuId=" + menuId;
						jsonResult.setRedirectUrl(redirectUrl);
						jsonResult.setSuccess(true);
						jsonResult.setMessage(egovMessageSource.getMessage("success.common.insert")); //정상적으로 등록되었습니다.
					
					}
					
				}
			}
			
		} catch(Exception e) {
			loggerError(LOGGER, e);
			jsonResult.setSuccess(false);
			jsonResult.setMessage(egovMessageSource.getMessage("fail.common.insert")); //생성이 실패하였습니다.
		}
		
		return jsonResult;
	}
	
	/**
	 * 업체수정 폼
	 * @param searchVO
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/decms/shop/cmpny/modifyCmpny.do")
	public String modifyCmpny(@ModelAttribute("searchVO") CmpnyVO searchVO,
			@RequestParam(value = "menuId") String menuId, Model model) throws Exception {
		
		LoginVO user = (LoginVO)EgovUserDetailsHelper.getAuthenticatedUser();
		if(!EgovUserDetailsHelper.getAuthorities().contains("ROLE_EMPLOYEE") && StringUtils.isNotEmpty(user.getCmpnyId())) {
			searchVO.setCmpnyId(user.getCmpnyId());
		}else if(!EgovUserDetailsHelper.getAuthorities().contains("ROLE_EMPLOYEE") && StringUtils.isEmpty(user.getCmpnyId())) {
			model.addAttribute("errorMessage", "업체정보입력이 완료되지 않았습니다.");
			model.addAttribute("errorRedirectUrl", "/decms/index.do");
			return "modoo/common/error/error";
		}
		
		// 제휴사 목록
//		PrtnrVO prtnr = new PrtnrVO();
//		List<PrtnrVO> prtnrList = prtnrService.selectPrtnrList(prtnr);
//		model.addAttribute("prtnrList", prtnrList);
				
		// 정산일구분코드 목록
		ComDefaultCodeVO codeVO = new ComDefaultCodeVO();
		codeVO.setCodeId("CMS005");
		List<?> stdeSeCodeList = cmmUseService.selectCmmCodeDetail(codeVO);
		model.addAttribute("stdeSeCodeList", stdeSeCodeList);
		
		//공급가 정책구분코드 목록
		codeVO.setCodeId("CMS006");
		List<?> splpcSeCodeList = cmmUseService.selectCmmCodeDetail(codeVO);
		model.addAttribute("splpcSeCodeList", splpcSeCodeList);
		
		//등록상태코드 목록
		codeVO.setCodeId("CMS007");
		List<?> opnngSttusCodeList = cmmUseService.selectCmmCodeDetail(codeVO);
		model.addAttribute("opnngSttusCodeList", opnngSttusCodeList); 
		
		//은행목록
		BankVO bank = new BankVO();
		List<BankVO> bankList = bankService.selectBankList(bank);
		model.addAttribute("bankList", bankList);
		
		//택배사목록
		HdryCmpnyVO hdryCmpny = new HdryCmpnyVO();
		List<?> hdryCmpnyList = hdryCmpnyService.selectHdryCmpnyList(hdryCmpny);
		model.addAttribute("hdryCmpnyList", hdryCmpnyList);
		
		CmpnyVO cmpny = cmpnyService.selectCmpny(searchVO);
		String bizrno = cmpny.getBizrno();
		if(bizrno.length() == 10) {
			bizrno = bizrno.substring(0,3) + "-" + bizrno.substring(3,5) + "-" + bizrno.substring(5,10);
			cmpny.setBizrno(bizrno);
		}
		model.addAttribute("cmpny", cmpny);
		
		//상태 카운트
		GoodsVO goods = new GoodsVO();
		goods.setSearchCmpnyId(cmpny.getCmpnyId());
		EgovMap goodsSttusCnt = goodsService.selectGoodsSttusCnt(goods);
		model.addAttribute("goodsSttusCnt", goodsSttusCnt);

		return "modoo/cms/shop/cmpny/cmpnyForm";
	}

	/**
	 * 업체수정
	 * @param cmpny
	 * @param bindingResult
	 * @return
	 */
	@RequestMapping(value = "/decms/shop/cmpny/modifyCmpny.json")
	@ResponseBody
	public JsonResult modifyCmpny(
			CmpnyVO searchVO,
			@Valid CmpnyVO cmpny, BindingResult bindingResult,
			@RequestParam(value = "menuId") String menuId) {
		JsonResult jsonResult = new JsonResult();
		LoginVO user = (LoginVO)EgovUserDetailsHelper.getAuthenticatedUser();
		
		try {
			this.trimCmpnyInfoValue(cmpny);
			
			if(!EgovUserDetailsHelper.getAuthorities().contains("ROLE_EMPLOYEE")) {
				CmpnyVO checkVO = cmpnyService.selectCmpny(cmpny);
				if(!checkVO.getCmpnyId().equals(user.getCmpnyId())) { // ROLE_SHOP 자기 자신의 업체 정보가 아닐때
					jsonResult.setMessage(egovMessageSource.getMessage("cms.fail.accessDenied")); //접근 권한이 없습니다.
					jsonResult.setSuccess(false);
					return jsonResult;
				}
				
				//업체가 강제적으로 바꾸지 못하게 처리
				cmpny.setCmpnyId(checkVO.getCmpnyId()); // 업체고유ID
				cmpny.setCmpnyUserEsntlId(checkVO.getCmpnyUserEsntlId()); //사용자고유ID
				cmpny.setOpnngSttusCode(checkVO.getOpnngSttusCode()); // 입점승인인상태코드
				cmpny.setPrtnrCmpnyList(checkVO.getPrtnrCmpnyList()); // 제휴사
				cmpny.setStdeSeCode(checkVO.getStdeSeCode()); //정산일
				
			}
			
			if(cmpny.getHdryCmpnyList() == null || cmpny.getHdryCmpnyList().size() == 0) {
				jsonResult.setMessage("배송회사를 선택하세요!"); 
				jsonResult.setSuccess(false);
			}else if(!this.isHasErrorsJson(bindingResult, jsonResult, "<br/>")) {
				
				int pcCnt = 0;
				for(PrtnrCmpnyVO pc : cmpny.getPrtnrCmpnyList()) {
					if(StringUtils.isNotEmpty(pc.getUseAt()) && "Y".equals(pc.getUseAt())) {
						pcCnt++;
					}
				}
				
				if(pcCnt == 0) {
					jsonResult.setMessage("제휴사를를 선택하세요!"); 
					jsonResult.setSuccess(false);
					return jsonResult;
				}
				
				if(StringUtils.isEmpty(cmpny.getCmpnyId())) {
					this.vaildateMessage(egovMessageSource.getMessage("cms.fail.access"), jsonResult); // 잘못된 접근입니다.
					LOGGER.error("cmpnyId 가 없음.");
				}else {
					String bizrno = cmpny.getBizrno().replaceAll("[^0-9]", "");
					cmpny.setBizrno(bizrno);
					
					if(cmpnyService.selectCmpnyBizrnoCheckCnt(cmpny) > 0) {
						this.vaildateMessage(egovMessageSource.getMessage("cmpny.fail.existBizrno"), jsonResult); // 이미 존재하는 사업자등록번호 입니다.
					}else {
					
						cmpny.setOpnngDe(EgovDateUtil.validChkDate(cmpny.getOpnngDe()));
						cmpny.setLastUpduserId(user.getUniqId());

						cmpnyService.updateCmpny(cmpny);
						
						//제휴사 reload
						goodsBrandService.reloadGoodsBrandGroupList();
						
						String redirectUrl = "/decms/shop/cmpny/cmpnyManage.do?menuId=" + menuId
								+ "&searchCondition=" + searchVO.getSearchCondition()
								+ "&searchKeyword=" + searchVO.getSearchKeyword()
								+ "&searchBgnde=" + searchVO.getSearchBgnde()
								+ "&searchEndde=" + searchVO.getSearchEndde()
								+ "&pageIndex=" + searchVO.getPageIndex();
						jsonResult.setRedirectUrl(redirectUrl);
						jsonResult.setSuccess(true);
						jsonResult.setMessage(egovMessageSource.getMessage("success.common.update")); //정상적으로 수정되었습니다.
					}
				}
			}
			
		} catch(Exception e) {
			loggerError(LOGGER, e);
			jsonResult.setSuccess(false);
			jsonResult.setMessage(egovMessageSource.getMessage("fail.common.update")); //수정이 실패하였습니다.
		}
		
		return jsonResult;
	}
	
	/**
	 * 업체사용자 목록
	 * @param searchVO
	 * @return
	 */
	@RequestMapping(value = "/decms/shop/cmpny/cmpnyMberList.json")
	@ResponseBody
	public JsonResult cmpnyMberList(MberVO searchVO) {
		JsonResult jsonResult = new JsonResult();
		
		try {
			if(StringUtils.isEmpty(searchVO.getSiteId())) {
				searchVO.setSearchSiteId(SiteDomainHelper.getSiteId());
			}
			
			PaginationInfo paginationInfo = new PaginationInfo();
			searchVO.setPageUnit(propertiesService.getInt("gridPageUnit"));
			this.setPagination(paginationInfo, searchVO);
			
			List<?> resultList = cmpnyService.selectCmpnyMberList(searchVO);
			jsonResult.put("list", resultList);
			
			int totalRecordCount = cmpnyService.selectCmpnyMberListCnt(searchVO);
			paginationInfo.setTotalRecordCount(totalRecordCount);
			jsonResult.put("paginationInfo", paginationInfo);
			
			jsonResult.setSuccess(true);
			
		} catch(Exception e) {
			loggerError(LOGGER, e);
			jsonResult.setSuccess(false);
			jsonResult.setMessage(egovMessageSource.getMessage("fail.common.select")); //조회에 실패하였습니다.
		}
		
		return jsonResult;
	}

	/**
	 * 업체 사용자 비밀번호 초기화
	 * @param cmpny
	 * @return
	 */
	/*@RequestMapping(value = "/decms/shop/cmpny/initPasswordUser.json")
	@ResponseBody
	public JsonResult initPasswordUser(CmpnyVO cmpny) {
		JsonResult jsonResult = new JsonResult();
		
		try {
			if(StringUtils.isEmpty(cmpny.getCmpnyUserEsntlId()) || StringUtils.isEmpty(cmpny.getCmpnyMberId())) {
				this.vaildateMessage(egovMessageSource.getMessage("cms.fail.access"), jsonResult); // 잘못된 접근입니다.
				LOGGER.error("EsntlId or mberId 가 없음.");
			}else if(StringUtils.isEmpty(cmpny.getCmpnyMberPassword())) {
				this.vaildateMessage(egovMessageSource.getMessage("NotEmpty.mberVO.password"), jsonResult); //비밀번호를 입력하세요!
			}else {
				
				cmpnyService.initCmpnyMberPassword(cmpny);

				jsonResult.setSuccess(true);
				jsonResult.setMessage(egovMessageSource.getMessage("success.common.update")); //정상적으로 수정되었습니다.
			}
		} catch(Exception e) {
			loggerError(LOGGER, e);
			jsonResult.setSuccess(false);
			jsonResult.setMessage(egovMessageSource.getMessage("fail.request.msg")); //요청처리를 실패하였습니다.
		}
		
		return jsonResult;
		
	}*/
	
	/**
	 * 업체 상태코드 변경
	 * @param cmpny
	 * @return
	 */
	@RequestMapping(value = "/decms/shop/cmpny/changeSttusCode.json")
	@ResponseBody
	public JsonResult changeSttusCode(CmpnyVO cmpny) {
		JsonResult jsonResult = new JsonResult();
		LoginVO user = (LoginVO)EgovUserDetailsHelper.getAuthenticatedUser();
		
		try {
			if(!EgovUserDetailsHelper.getAuthorities().contains("ROLE_EMPLOYEE")) {
				jsonResult.setMessage(egovMessageSource.getMessage("cms.fail.accessDenied")); //접근 권한이 없습니다.
				jsonResult.setSuccess(false);
				return jsonResult;
			}
			
			if(StringUtils.isEmpty(cmpny.getCmpnyId())) {
				this.vaildateMessage(egovMessageSource.getMessage("cms.fail.access"), jsonResult); // 잘못된 접근입니다.
				LOGGER.error("cmpnyId 가 없음.");
			}else {
				cmpny.setLastUpduserId(user.getUniqId());
				cmpnyService.updateHdryCmpnySttusCode(cmpny);

				jsonResult.setSuccess(true);
				jsonResult.setMessage(egovMessageSource.getMessage("success.common.update")); //정상적으로 수정되었습니다.
			}
		
		} catch(Exception e) {
			loggerError(LOGGER, e);
			jsonResult.setSuccess(false);
			jsonResult.setMessage(egovMessageSource.getMessage("fail.request.msg")); //요청처리를 실패하였습니다.
			
		}
		return jsonResult;
	}
	
	/**
	 * 업체 삭제
	 * @param cmpny
	 * @return
	 */
	@RequestMapping(value = "/decms/shop/cmpny/deleteCmpny.json", method = RequestMethod.POST)
	@ResponseBody
	public JsonResult deleteCmpny(CmpnyVO cmpny, @RequestParam(value = "menuId") String menuId) {
		JsonResult jsonResult = new JsonResult();
		LoginVO user = (LoginVO)EgovUserDetailsHelper.getAuthenticatedUser();
		
		try {
			if(!EgovUserDetailsHelper.getAuthorities().contains("ROLE_EMPLOYEE")) {
				jsonResult.setMessage(egovMessageSource.getMessage("cms.fail.accessDenied")); //접근 권한이 없습니다.
				jsonResult.setSuccess(false);
				return jsonResult;
			}
			
			if(StringUtils.isEmpty(cmpny.getCmpnyId())) {
				
			}else {
				cmpny.setLastUpduserId(user.getUniqId());
				cmpnyService.deleteCmpny(cmpny);
				
				jsonResult.setSuccess(true);
				jsonResult.setMessage(egovMessageSource.getMessage("success.common.delete")); //정상적으로 삭제되었습니다.
				
				String redirectUrl = "/decms/shop/cmpny/cmpnyManage.do?menuId=" + menuId;
				jsonResult.setRedirectUrl(redirectUrl);
				
			}
			
		} catch(Exception e) {
			loggerError(LOGGER, e);
			jsonResult.setSuccess(false);
			jsonResult.setMessage(egovMessageSource.getMessage("fail.common.delete")); //삭제가 실패하였습니다.
		}
		
		return jsonResult;
	}
	
	/**
	 * 업체 비밀번호 변경
	 * @param mber
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/decms/shop/cmpny/cmpnyUserPwdChange.do")
	@ResponseBody
	public JsonResult cmpnyUserPwdChange(MberVO mber, String oldPassword, Model model) {
		JsonResult jsonResult = new JsonResult();
		LoginVO user = (LoginVO)EgovUserDetailsHelper.getAuthenticatedUser();
		
		try {

			if(StringUtils.isEmpty(oldPassword)) {
			}else if(StringUtils.isEmpty(mber.getPassword())) {
				jsonResult.setMessage("기존 비밀번호를 입력하세요"); 
				jsonResult.setSuccess(false);
			}else if(StringUtils.isEmpty(mber.getRepassword())) {
				jsonResult.setMessage("비밀번호확인을 입력하세요"); 
				jsonResult.setSuccess(false);
			}else if(!mber.getPassword().equals(mber.getRepassword())) {
				jsonResult.setMessage("비밀번호와 비밀번호확인이 다릅니다."); 
				jsonResult.setSuccess(false);
			}else {
				
				LoginVO checkVO = new LoginVO();
				checkVO.setId(user.getId());
				checkVO.setPassword(oldPassword);
				checkVO = loginService.actionLogin(checkVO);
				
				if (checkVO != null && checkVO.getId() != null && !checkVO.getId().equals("")) {
					mber.setMberId(user.getId());
					mberService.updatePassword(mber);

					jsonResult.setSuccess(true);
					jsonResult.setMessage(egovMessageSource.getMessage("success.common.update")); //정상적으로 수정되었습니다.
				}else {
					jsonResult.setMessage("기존 비밀번호를 확인하세요!"); 
					jsonResult.setSuccess(false);
				}
			}

		} catch(Exception e) {
			loggerError(LOGGER, e);
			jsonResult.setSuccess(false);
			jsonResult.setMessage(egovMessageSource.getMessage("fail.request.msg")); //요청처리를 실패하였습니다.
			
		}
		return jsonResult;
	}


}
