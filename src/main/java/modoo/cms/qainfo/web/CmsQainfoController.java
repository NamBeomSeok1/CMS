package modoo.cms.qainfo.web;

import java.math.BigDecimal;
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
import org.springframework.web.bind.annotation.ResponseBody;

import modoo.module.biztalk.service.BiztalkService;
import modoo.module.biztalk.service.BiztalkVO;
import modoo.module.common.service.JsonResult;
import modoo.module.common.util.CommonUtils;
import modoo.module.common.util.SiteDomainHelper;
import modoo.module.common.web.CommonDefaultController;
import modoo.module.mber.info.service.MberService;
import modoo.module.mber.info.service.MberVO;
import modoo.module.qainfo.service.QainfoService;
import modoo.module.qainfo.service.QainfoVO;
import modoo.module.shop.goods.info.service.GoodsService;
import modoo.module.shop.goods.order.service.OrderVO;
import egovframework.com.cmm.ComDefaultCodeVO;
import egovframework.com.cmm.LoginVO;
import egovframework.com.cmm.service.EgovCmmUseService;
import egovframework.com.cmm.service.EgovFileMngService;
import egovframework.com.cmm.service.FileVO;
import egovframework.com.cmm.service.impl.FileManageDAO;
import egovframework.com.cmm.util.EgovUserDetailsHelper;
import egovframework.com.utl.fcc.service.EgovDateUtil;
import egovframework.rte.psl.dataaccess.util.EgovMap;
import egovframework.rte.ptl.mvc.tags.ui.pagination.PaginationInfo;

@Controller
public class CmsQainfoController extends CommonDefaultController {

	private static final Logger LOGGER = LoggerFactory.getLogger(CmsQainfoController.class);
	
	@Resource(name = "qainfoService")
	private QainfoService qainfoService;
	
	/** EgovCmmUseService */
	@Resource(name = "EgovCmmUseService")
	private EgovCmmUseService cmmUseService;
	
	@Resource(name = "mberService")
	private MberService mberService;

	@Resource(name = "EgovFileMngService")
	private EgovFileMngService fileMngService;
	
	@Resource(name = "biztalkService")
	private BiztalkService biztalkService;
	
	/**
	 * 질답 관리
	 * @param searchVO
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/decms/qainfo/qainfoManage.do")
	public String qainfoManage(@ModelAttribute("searchVO") QainfoVO searchVO, Model model) throws Exception {
		LoginVO user = (LoginVO)EgovUserDetailsHelper.getAuthenticatedUser();
		if(!EgovUserDetailsHelper.getAuthorities().contains("ROLE_EMPLOYEE")) {
			if(StringUtils.isEmpty(user.getCmpnyId())) { //업체 매핑이 안되어 있으면.
				return "redirect:/decms/index.do";
			}
			searchVO.setSearchCmpnyId(user.getCmpnyId());
		}else if(StringUtils.isEmpty(searchVO.getQaSeCode())) {
			return "redirect:/decms/index.do";
		}
		
		//문의유형코드 목록
		ComDefaultCodeVO codeVO = new ComDefaultCodeVO();
		codeVO.setCodeId("CMS018");
		List<?> qestnTyCodeList1 = cmmUseService.selectCmmCodeDetail(codeVO);
		model.addAttribute("qestnTyCodeList1", qestnTyCodeList1);
		
		codeVO.setCodeId("CMS019");
		List<?> qestnTyCodeList2 = cmmUseService.selectCmmCodeDetail(codeVO);
		model.addAttribute("qestnTyCodeList2", qestnTyCodeList2);
		
		codeVO.setCodeId("CMS015");
		List<?> qaSttusCodeList = cmmUseService.selectCmmCodeDetail(codeVO);
		model.addAttribute("qaSttusCodeList", qaSttusCodeList);
		
		return "modoo/cms/qainfo/qainfoManage";
	}
	
	/**
	 * 질답 목록
	 * @param searchVO
	 * @return
	 */
	@RequestMapping(value = "/decms/qainfo/qainfoList.json")
	@ResponseBody
	public JsonResult qainfoList(QainfoVO searchVO) {
		LoginVO user = (LoginVO)EgovUserDetailsHelper.getAuthenticatedUser();
		JsonResult jsonResult = new JsonResult();
		
		if(!EgovUserDetailsHelper.getAuthorities().contains("ROLE_EMPLOYEE")) {
			if(StringUtils.isEmpty(user.getCmpnyId())) { //업체 매핑이 안되어 있으면.
				jsonResult.setMessage(egovMessageSource.getMessage("cms.fail.not.cmpnyId")); //업체등록이 필요합니다.
				jsonResult.setSuccess(false);
				return jsonResult;
			}
			searchVO.setSearchCmpnyId(user.getCmpnyId());
		}
		
		try {
			if(StringUtils.isEmpty(searchVO.getSiteId())) {
				searchVO.setSearchSiteId(SiteDomainHelper.getSiteId());
			}
			
			PaginationInfo paginationInfo = new PaginationInfo();
			searchVO.setPageUnit(propertiesService.getInt("gridPageUnit"));
			this.setPagination(paginationInfo, searchVO);
			
			List<?> resultList = qainfoService.selectQainfoList(searchVO);
			jsonResult.put("list", resultList);
			
			int totalRecordCount = qainfoService.selectQainfoListCnt(searchVO);
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
	 * 질답 등록 폼
	 * @param searchVO
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/decms/embed/qainfo/writeQainfo.do")
	public String writeQainfo(@ModelAttribute("searchVO") QainfoVO searchVO, Model model) throws Exception {
		if(!EgovUserDetailsHelper.getAuthorities().contains("ROLE_EMPLOYEE")) {
			return "redirect:/decms/index.do";
		}

		QainfoVO qainfo = new QainfoVO();
		
		if(StringUtils.isEmpty(searchVO.getSiteId())) {
			qainfo.setSiteId(SiteDomainHelper.getSiteId());
		}
		
		//질답진행상태코드 목록
		ComDefaultCodeVO codeVO = new ComDefaultCodeVO();
		codeVO.setCodeId("CMS015");
		List<?> qaSttusCodeList = cmmUseService.selectCmmCodeDetail(codeVO);
		model.addAttribute("qaSttusCodeList", qaSttusCodeList);
		
		qainfo.setQnaProcessSttusCode("R");
		qainfo.setWritngDe(EgovDateUtil.getToday() + "000000");
		//qainfo.setAnswerDe(EgovDateUtil.getToday());
		model.addAttribute("qainfo", qainfo);
		return "modoo/cms/qainfo/qainfoForm";
	}
	
	/**
	 * 질답 저장
	 * @param qainfo
	 * @param bindingResult
	 * @return
	 */
	@RequestMapping(value = "/decms/qainfo/writeQainfo.json", method = RequestMethod.POST)
	@ResponseBody
	public JsonResult writeQainfo(@Valid QainfoVO qainfo, BindingResult bindingResult) {
		JsonResult jsonResult = new JsonResult();
		LoginVO user = (LoginVO)EgovUserDetailsHelper.getAuthenticatedUser();
		
		try {
			if(!EgovUserDetailsHelper.getAuthorities().contains("ROLE_EMPLOYEE")) {
				jsonResult.setMessage(egovMessageSource.getMessage("cms.fail.accessDenied")); //접근권한이 없습니다.
				jsonResult.setSuccess(false);
				return jsonResult;
			}
			
			if(!this.isHasErrorsJson(bindingResult, jsonResult, "<br/>")) {
				MberVO mber = new MberVO();
				mber.setMberId(qainfo.getWrterId());
				int cnt = mberService.selectCheckDuplMberIdCnt(mber);
				if(cnt == 0) {
					jsonResult.setSuccess(false);
					jsonResult.setMessage("존재하지 않는 사용자압니다. ID : " + qainfo.getWrterId());
					return jsonResult;
				}
				
				qainfo.setQestnSj(CommonUtils.unscript(qainfo.getQestnSj()));
				qainfo.setQestnCn(CommonUtils.unscript(qainfo.getQestnCn()));
				qainfo.setAnswerCn(CommonUtils.unscript(qainfo.getAnswerCn()));
				
				qainfo.setWritngDe(CommonUtils.validChkDateTime(qainfo.getWritngDe()));
				
				if(!"C".equals(qainfo.getQnaProcessSttusCode())) { //완료가 아니면
					qainfo.setAnswerDe(null);
				}else {
					if(StringUtils.isNotEmpty(qainfo.getAnswerDe())) {
						qainfo.setAnswerDe(CommonUtils.validChkDateTime(qainfo.getAnswerDe()));
					}
				}
				
				qainfo.setFrstRegisterId(user.getUniqId());

				qainfoService.insertQainfo(qainfo);
				jsonResult.setSuccess(true);
			}
		} catch(Exception e) {
			loggerError(LOGGER, e);
			jsonResult.setSuccess(false);
			jsonResult.setMessage(egovMessageSource.getMessage("fail.common.insert")); //생성이 실패하였습니다.
		}
		
		return jsonResult;
	}
	
	/**
	 * 질답 수정 폼
	 * @param searchVO
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/decms/embed/qainfo/modifyQainfo.do")
	public String modifyQainfo(@ModelAttribute("searchVO") QainfoVO searchVO, Model model) throws Exception {
		QainfoVO qainfo = qainfoService.selectQainfo(searchVO);
		
		//질답진행상태코드 목록
		ComDefaultCodeVO codeVO = new ComDefaultCodeVO();
		codeVO.setCodeId("CMS015");
		List<?> qaSttusCodeList = cmmUseService.selectCmmCodeDetail(codeVO);
		model.addAttribute("qaSttusCodeList", qaSttusCodeList);
		
		FileVO fvo = new FileVO();
		fvo.setAtchFileId(qainfo.getAtchFileId());
		List<?> imgs = fileMngService.selectImageFileList(fvo);
		model.addAttribute("imgs", imgs);
		
		model.addAttribute("qainfo", qainfo);

		
		if(EgovUserDetailsHelper.getAuthorities().contains("ROLE_EMPLOYEE")) {
			return "modoo/cms/qainfo/qainfoForm";
		}else {
			return "modoo/cms/qainfo/qainfoReplyForm";
			
		}
	}
	
	/**
	 * 질답 수정
	 * @param qainfo
	 * @param bindingResult
	 * @return
	 */
	@RequestMapping(value = "/decms/qainfo/modifyQainfo.json", method = RequestMethod.POST)
	@ResponseBody
	public JsonResult modifyQainfo(@Valid QainfoVO qainfo, BindingResult bindingResult) {
		JsonResult jsonResult = new JsonResult();
		LoginVO user = (LoginVO)EgovUserDetailsHelper.getAuthenticatedUser();
		try {
			if(!EgovUserDetailsHelper.getAuthorities().contains("ROLE_EMPLOYEE")) {
				jsonResult.setMessage(egovMessageSource.getMessage("cms.fail.accessDenied")); //접근권한이 없습니다.
				jsonResult.setSuccess(false);
				return jsonResult;
			}
			
			if(!this.isHasErrorsJson(bindingResult, jsonResult, "<br/>")) {
				
				QainfoVO searchVO = qainfoService.selectQainfo(qainfo);
				MberVO mber = new MberVO();
				mber.setMberId(qainfo.getWrterId());
				int cnt = mberService.selectCheckDuplMberIdCnt(mber);
				if(cnt == 0) {
					jsonResult.setSuccess(false);
					jsonResult.setMessage("존재하지 않는 사용자압니다. ID : " + qainfo.getWrterId());
					return jsonResult;
				}
				
				qainfo.setQestnSj(CommonUtils.unscript(qainfo.getQestnSj()));
				qainfo.setQestnCn(CommonUtils.unscript(qainfo.getQestnCn()));
				qainfo.setAnswerCn(CommonUtils.unscript(qainfo.getAnswerCn()));
				qainfo.setAnswerId(user.getId());
				qainfo.setAnswerNm(user.getName());
				
				qainfo.setWritngDe(CommonUtils.validChkDateTime(qainfo.getWritngDe()));
				
				if(!"C".equals(qainfo.getQnaProcessSttusCode())) { //완료가 아니면
					qainfo.setAnswerDe(null);
				}else {
					if(StringUtils.isNotEmpty(qainfo.getWrterTelno())){
						//주문완료 비즈톡
						qaAlimTalk(qainfo,searchVO);
						/*//주문완료 비즈톡
						BiztalkVO bizTalk = new BiztalkVO();
						
						bizTalk.setRecipient(qainfo.getWrterTelno());
						
						//알림톡 템플릿 내용 조회
						if("GOODS".equals(searchVO.getQaSeCode())){
							bizTalk.setTmplatCode("template_011");
						}else if("SITE".equals(searchVO.getQaSeCode())){
							bizTalk.setTmplatCode("template_010");
						}
						BiztalkVO template = biztalkService.selectBizTalkTemplate(bizTalk);
						String message = template.getTmplatCn();
						if(message.contains("{PRODUCTNAME}")){
							message = message.replaceAll("#\\{PRODUCTNAME\\}",searchVO.getGoodsNm());
						}
						bizTalk.setMessage(message);
						
						BiztalkVO result = biztalkService.sendAlimTalk(bizTalk);*/
					}
					if(StringUtils.isNotEmpty(qainfo.getAnswerDe())) {
						qainfo.setAnswerDe(CommonUtils.validChkDateTime(qainfo.getAnswerDe()));
					}
				}
				qainfo.setLastUpdusrId(user.getUniqId());

				qainfoService.updateQainfo(qainfo);
				jsonResult.setSuccess(true);
			}
		} catch(Exception e) {
			loggerError(LOGGER, e);
			jsonResult.setSuccess(false);
			jsonResult.setMessage(egovMessageSource.getMessage("fail.common.update")); //수정이 실패하였습니다.
		}
		
		return jsonResult;
	}
	
	/**
	 * QNA 비즈톡
	 *  
	 */
	public void qaAlimTalk(QainfoVO qainfo,QainfoVO searchVO ){
		
		try {
			//주문완료 비즈톡
			BiztalkVO bizTalk = new BiztalkVO();
			
			bizTalk.setRecipient(qainfo.getWrterTelno());
			
			//알림톡 템플릿 내용 조회
			if("GOODS".equals(searchVO.getQaSeCode())){
				bizTalk.setTmplatCode("template_011");
			}else if("SITE".equals(searchVO.getQaSeCode())){
				bizTalk.setTmplatCode("template_010");
			}else{
				throw new Exception();
			}
			BiztalkVO template = biztalkService.selectBizTalkTemplate(bizTalk);
			String message = template.getTmplatCn();
			if(message.contains("{PRODUCTNAME}")){
				message = message.replaceAll("#\\{PRODUCTNAME\\}",searchVO.getGoodsNm());
			}
			bizTalk.setMessage(message);
			
			BiztalkVO result = biztalkService.sendAlimTalk(bizTalk);

		}catch (Exception e) {
			loggerError(LOGGER, e);
		}
	}
	
	/**
	 * 질답 답변 저장
	 * @param qainfo
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/decms/qainfo/saveReplyQainfo.json", method = RequestMethod.POST)
	@ResponseBody
	public JsonResult modifyReplyQainfo(QainfoVO qainfo) throws Exception {
		JsonResult jsonResult = new JsonResult();
		LoginVO user = (LoginVO)EgovUserDetailsHelper.getAuthenticatedUser();
		
		try {
			if(StringUtils.isEmpty(qainfo.getQaId())) {
				this.vaildateMessage(egovMessageSource.getMessage("cms.fail.access"), jsonResult); // 잘못된 접근입니다.
				LOGGER.error("qaId 가 없음.");
			}else {
				qainfo.setAnswerId(user.getId());
				qainfo.setAnswerNm(user.getName());
				//qainfo.setAnswerDe(CommonUtils.getCurrentDateTime()); Query에서 저장
				qainfo.setAnswerCn(CommonUtils.unscript(qainfo.getAnswerCn()));
				qainfo.setLastUpdusrId(user.getUniqId());
				
				qainfoService.updateQainfoAnswer(qainfo);
				jsonResult.setMessage("저장되었습니다.");
				jsonResult.setSuccess(true);
				                                                                                                                                        
				//답변 비즈톡
			}
			
		} catch(Exception e) {
			loggerError(LOGGER, e);
			jsonResult.setSuccess(false);
			jsonResult.setMessage(egovMessageSource.getMessage("fail.common.update")); //수정이 실패하였습니다.
		}
		
		return jsonResult;
	}

	/**
	 * 질답 삭제
	 * @param qainfo
	 * @return
	 */
	@RequestMapping(value = "/decms/qainfo/deleteQainfo.json")
	@ResponseBody
	public JsonResult deleteQainfo(QainfoVO qainfo) {
		JsonResult jsonResult = new JsonResult();
		LoginVO user = (LoginVO)EgovUserDetailsHelper.getAuthenticatedUser();

		try {
			if(!EgovUserDetailsHelper.getAuthorities().contains("ROLE_EMPLOYEE")) {
				jsonResult.setMessage(egovMessageSource.getMessage("cms.fail.accessDenied")); //접근권한이 없습니다.
				jsonResult.setSuccess(false);
				return jsonResult;
			}
			
			if(StringUtils.isEmpty(qainfo.getQaId())) {
				this.vaildateMessage(egovMessageSource.getMessage("cms.fail.access"), jsonResult); // 잘못된 접근입니다.
				LOGGER.error("qaId 가 없음.");
			}else {
				qainfo.setLastUpdusrId(user.getUniqId());
				qainfoService.deleteQainfo(qainfo);
				jsonResult.setSuccess(true);
			}

		} catch(Exception e) {
			loggerError(LOGGER, e);
			jsonResult.setSuccess(false);
			jsonResult.setMessage(egovMessageSource.getMessage("fail.common.delete")); //삭제가 실패하였습니다.
		}
		
		return jsonResult;
	}
	
	/**
	 * CP > 교환, 반품 > 교환사유, 반품사유
	 * @param qaInfo
	 * @return
	 */
	@RequestMapping(value = "/decms/qainfo/getReasonCn.json")
	@ResponseBody
	public JsonResult getReasonCn(QainfoVO searchVO) {
		JsonResult jsonResult = new JsonResult();
		
		try {
			QainfoVO qaInfo = qainfoService.selectReason(searchVO);
			
			FileVO fvo = new FileVO();
			fvo.setAtchFileId(qaInfo.getAtchFileId());
			List<?> imgs = fileMngService.selectImageFileList(fvo);
			
			jsonResult.put("qaInfo", qaInfo);
			jsonResult.put("imgs", imgs);
			jsonResult.setSuccess(true);

		} catch(Exception e) {
			loggerError(LOGGER, e);
			jsonResult.setSuccess(false);
			//jsonResult.setMessage(egovMessageSource.getMessage("fail.common.delete"));
		}
		
		return jsonResult;
		
	}
	
	//**************************************************************************************************************************
	
	@RequestMapping(value = "/decms/qainfo/qainfoCpList.json")
	@ResponseBody
	public JsonResult qainfoCpList(QainfoVO searchVO) {
		LoginVO user = (LoginVO)EgovUserDetailsHelper.getAuthenticatedUser();
		JsonResult jsonResult = new JsonResult();
		
		try {
			if(StringUtils.isEmpty(searchVO.getSiteId())) {
				searchVO.setSearchSiteId(SiteDomainHelper.getSiteId());
			}
			
			searchVO.setQaSeCode("CP");
			if(!EgovUserDetailsHelper.getAuthorities().contains("ROLE_EMPLOYEE")) {
				searchVO.setWrterId(user.getId());
			}
			
			PaginationInfo paginationInfo = new PaginationInfo();
			searchVO.setPageUnit(propertiesService.getInt("gridPageUnit"));
			this.setPagination(paginationInfo, searchVO);
			
			List<?> resultList = qainfoService.selectQainfoList(searchVO);
			jsonResult.put("list", resultList);
			
			int totalRecordCount = qainfoService.selectQainfoListCnt(searchVO);
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
	 * 본사QA 실문 등록 폼
	 * @param searchVO
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/decms/embed/qainfo/writeCpQainfo.do")
	public String writeCpQainfo(@ModelAttribute("searchVO") QainfoVO searchVO, Model model) throws Exception  {
		LoginVO user = (LoginVO)EgovUserDetailsHelper.getAuthenticatedUser();
		
		if( StringUtils.isEmpty(searchVO.getQaSeCode()) || !"CP".equals(searchVO.getQaSeCode()) )  {
			return "redirect:/decms/index.do";
		}
		
		QainfoVO qainfo = new QainfoVO();
		
		if(StringUtils.isEmpty(searchVO.getSiteId())) {
			qainfo.setSiteId(SiteDomainHelper.getSiteId());
		}
		
		//질답진행상태코드 목록
		ComDefaultCodeVO codeVO = new ComDefaultCodeVO();
		codeVO.setCodeId("CMS015");
		List<?> qaSttusCodeList = cmmUseService.selectCmmCodeDetail(codeVO);
		model.addAttribute("qaSttusCodeList", qaSttusCodeList);
		
		//문의 유형
		codeVO.setCodeId("CMS019");
		List<?> qestnTyCodeList2 = cmmUseService.selectCmmCodeDetail(codeVO);
		model.addAttribute("qestnTyCodeList2", qestnTyCodeList2);
		
		qainfo.setQnaProcessSttusCode("R");
		qainfo.setQaSeCode(searchVO.getQaSeCode());
		qainfo.setWritngDe(EgovDateUtil.getToday() + "000000");
		//qainfo.setAnswerDe(EgovDateUtil.getToday());
		
		qainfo.setWrterId(user.getId());
		qainfo.setWrterNm(user.getName());
		qainfo.setEmailAdres(user.getEmail());

		model.addAttribute("qainfo", qainfo);
		
		return "modoo/cms/qainfo/qainfoCpForm";
	}
	
	/**
	 * 본사QA 질문 저장
	 * @param qainfo
	 * @param bindingResult
	 * @return
	 */
	@RequestMapping(value = "/decms/qainfo/writeCpQainfo.json", method = RequestMethod.POST)
	@ResponseBody
	public JsonResult writeCpQainfo(@Valid QainfoVO qainfo, BindingResult bindingResult) {
		JsonResult jsonResult = new JsonResult();
		LoginVO user = (LoginVO)EgovUserDetailsHelper.getAuthenticatedUser();
		
		try {
			
			if(!this.isHasErrorsJson(bindingResult, jsonResult, "<br/>")) {
				qainfo.setQestnSj(CommonUtils.unscript(qainfo.getQestnSj()));
				qainfo.setQestnCn(CommonUtils.unscript(qainfo.getQestnCn()));
				qainfo.setAnswerCn(CommonUtils.unscript(qainfo.getAnswerCn()));
				qainfo.setWritngDe(CommonUtils.getCurrentDateTime());
				qainfo.setWrterId(user.getId());
				qainfo.setWrterNm(user.getName());
				qainfo.setQnaProcessSttusCode("R");
				
				qainfo.setFrstRegisterId(user.getUniqId());

				qainfoService.insertQainfo(qainfo);
				jsonResult.setSuccess(true);
			}
		} catch(Exception e) {
			loggerError(LOGGER, e);
			jsonResult.setSuccess(false);
			jsonResult.setMessage(egovMessageSource.getMessage("fail.common.insert")); //생성이 실패하였습니다.
		}
		
		return jsonResult;
	}
	
	/**
	 * 본사QA 상세
	 * @param searchVO
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/decms/embed/qainfo/viewCpQainfo.do")
	public String viewCpQainfo(@ModelAttribute("searchVO") QainfoVO searchVO, Model model) throws Exception {
		LoginVO user = (LoginVO)EgovUserDetailsHelper.getAuthenticatedUser();
		QainfoVO qainfo = qainfoService.selectQainfo(searchVO);
		
		if(!user.getId().equals(qainfo.getWrterId())) {
			return "modoo/common/error/accessDenied";
		}
		
		
		//질답진행상태코드 목록
		ComDefaultCodeVO codeVO = new ComDefaultCodeVO();
		codeVO.setCodeId("CMS015");
		List<?> qaSttusCodeList = cmmUseService.selectCmmCodeDetail(codeVO);
		model.addAttribute("qaSttusCodeList", qaSttusCodeList);
		
		FileVO fvo = new FileVO();
		fvo.setAtchFileId(qainfo.getAtchFileId());
		List<?> imgs = fileMngService.selectImageFileList(fvo);
		model.addAttribute("imgs", imgs);
		
		model.addAttribute("qainfo", qainfo);
		
		return "modoo/cms/qainfo/qainfoCpView";
	}
	
	/**
	 * 본사QA 질문 수정 폼
	 * @param searchVO
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/decms/embed/qainfo/modifyCpQainfo.do")
	public String modifyCpQainfo(@ModelAttribute("searchVO") QainfoVO searchVO, Model model) throws Exception {
		LoginVO user = (LoginVO)EgovUserDetailsHelper.getAuthenticatedUser();
		QainfoVO qainfo = qainfoService.selectQainfo(searchVO);
		
		if(!user.getId().equals(qainfo.getWrterId())) {
			return "modoo/common/error/accessDenied";
		}
		
		//질답진행상태코드 목록
		ComDefaultCodeVO codeVO = new ComDefaultCodeVO();
		codeVO.setCodeId("CMS015");
		List<?> qaSttusCodeList = cmmUseService.selectCmmCodeDetail(codeVO);
		model.addAttribute("qaSttusCodeList", qaSttusCodeList);
		
		FileVO fvo = new FileVO();
		fvo.setAtchFileId(qainfo.getAtchFileId());
		List<?> imgs = fileMngService.selectImageFileList(fvo);
		model.addAttribute("imgs", imgs);
		
		model.addAttribute("qainfo", qainfo);
		
		return "modoo/cms/qainfo/qainfoCpForm";
	}
	
	/**
	 *  본사QA 질문 수정
	 * @param qainfo
	 * @param bindingResult
	 * @return
	 */
	@RequestMapping(value = "/decms/qainfo/modifyCpQainfo.json", method = RequestMethod.POST)
	@ResponseBody
	public JsonResult modifyCpQainfo(@Valid QainfoVO qainfo, BindingResult bindingResult) {
		JsonResult jsonResult = new JsonResult();
		LoginVO user = (LoginVO)EgovUserDetailsHelper.getAuthenticatedUser();
		
		try {
			/*
			if(!EgovUserDetailsHelper.getAuthorities().contains("ROLE_EMPLOYEE")) {
				jsonResult.setMessage(egovMessageSource.getMessage("cms.fail.accessDenied")); //접근권한이 없습니다.
				jsonResult.setSuccess(false);
				return jsonResult;
			}
			*/
			QainfoVO checkVO = qainfoService.selectQainfo(qainfo);
			if(!user.getId().equals(checkVO.getWrterId())) {
				jsonResult.setMessage(egovMessageSource.getMessage("cms.fail.accessDenied")); //접근권한이 없습니다.
				jsonResult.setSuccess(false);
			}else {
				qainfo.setWrterId(checkVO.getWrterId());
				qainfo.setWritngDe(checkVO.getWritngDe());
				qainfo.setWrterNm(checkVO.getWrterNm());
				
				if(!this.isHasErrorsJson(bindingResult, jsonResult, "<br/>")) {
					qainfo.setQestnSj(CommonUtils.unscript(qainfo.getQestnSj()));
					qainfo.setQestnCn(CommonUtils.unscript(qainfo.getQestnCn()));
					
					qainfo.setLastUpdusrId(user.getUniqId());

					qainfoService.updateQainfoQestn(qainfo);
					jsonResult.setSuccess(true);
				}
			}
		} catch(Exception e) {
			loggerError(LOGGER, e);
			jsonResult.setSuccess(false);
			jsonResult.setMessage(egovMessageSource.getMessage("fail.common.update")); //수정이 실패하였습니다.
		}
		
		return jsonResult;
	}
}
