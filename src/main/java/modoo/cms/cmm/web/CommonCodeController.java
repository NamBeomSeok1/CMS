package modoo.cms.cmm.web;

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
import org.springframework.web.bind.annotation.ResponseBody;

import modoo.module.common.code.service.CmmnCodeService;
import modoo.module.common.code.service.CmmnCodeVO;
import modoo.module.common.code.service.CmmnDetailCodeVO;
import modoo.module.common.service.JsonResult;
import modoo.module.common.web.CommonDefaultController;
import egovframework.com.cmm.LoginVO;
import egovframework.com.cmm.util.EgovUserDetailsHelper;
import egovframework.rte.ptl.mvc.tags.ui.pagination.PaginationInfo;

@Controller
public class CommonCodeController extends CommonDefaultController {

	private static final Logger LOGGER = LoggerFactory.getLogger(CommonCodeController.class);
	
	@Resource(name = "cmmnCodeService")
	private CmmnCodeService cmmnCodeService;

	/**
	 * 공통코드 관리
	 * @param searchVO
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/decms/code/commonCodeManage.do")
	public String commonCodeManage(@ModelAttribute("searchVO") CmmnCodeVO searchVO,
			@ModelAttribute("searchDetailVO") CmmnDetailCodeVO searchDetailVO, Model model) throws Exception {
		//내부 직원 권한이 아닐때 
		if(!EgovUserDetailsHelper.getAuthorities().contains("ROLE_EMPLOYEE")) {
			return "redirect:/decms/index.do";
		}
		
		return "modoo/cms/cmm/code/commonCodeManage";
	}
	
	/**
	 * 공통코드 목록 
	 * @param searchVO
	 * @return
	 */
	@RequestMapping(value = "/decms/code/codeList.json")
	@ResponseBody
	public JsonResult codeList(CmmnCodeVO searchVO) {
		JsonResult jsonResult = new JsonResult();
		
		try {
			//내부 직원 권한이 아닐때 
			if(!EgovUserDetailsHelper.getAuthorities().contains("ROLE_EMPLOYEE")) {
				jsonResult.setMessage(egovMessageSource.getMessage("cms.fail.accessDenied")); //접근 권한이 없습니다.
				jsonResult.setSuccess(false);
			}else {
				PaginationInfo paginationInfo = new PaginationInfo();
				searchVO.setPageUnit(propertiesService.getInt("gridPageUnit"));
				this.setPagination(paginationInfo, searchVO);
				
				List<CmmnCodeVO> resultList = cmmnCodeService.selectCmmnCodeList(searchVO);
				jsonResult.put("list", resultList);
				
				int totalRecordCount = cmmnCodeService.selectCmmnCodeListCnt(searchVO);
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
	 * 공통코드 작성 폼
	 * @param searchVO
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/decms/embed/code/writeCode.do")
	public String writeCode(@ModelAttribute("searchVO") CmmnCodeVO searchVO, Model model) throws Exception {
		//내부 직원 권한이 아닐때 
		if(!EgovUserDetailsHelper.getAuthorities().contains("ROLE_EMPLOYEE")) {
			return "redirect:/decms/index.do";
		}
		
		CmmnCodeVO code = new CmmnCodeVO();
		code.setClCode("CMS");
		
		String codeId = cmmnCodeService.getNextCmmnCodeId(code);
		code.setCodeId(codeId);
		
		model.addAttribute("code", code);
		model.addAttribute("mode", "insert");
		
		return "modoo/cms/cmm/code/commonCodeForm";
	}
	
	/**
	 * 공통코드 저장
	 * @param code
	 * @param bindingResult
	 * @return
	 */
	@RequestMapping(value = "/decms/code/writeCode.json")
	@ResponseBody
	public JsonResult writeCode(@Valid CmmnCodeVO code, BindingResult bindingResult) {
		JsonResult jsonResult = new JsonResult();
		LoginVO user = (LoginVO)EgovUserDetailsHelper.getAuthenticatedUser();
		
		try {
			//내부 직원 권한이 아닐때 
			if(!EgovUserDetailsHelper.getAuthorities().contains("ROLE_EMPLOYEE")) {
				jsonResult.setMessage(egovMessageSource.getMessage("cms.fail.accessDenied")); //접근 권한이 없습니다.
				jsonResult.setSuccess(false);
			}else {
				if(!this.isHasErrorsJson(bindingResult, jsonResult, "<br/>")) {
					
					int cnt = cmmnCodeService.selectCheckCmmnCodeId(code);
					if(cnt > 0) {
						jsonResult.setSuccess(false);
						jsonResult.setMessage(egovMessageSource.getMessage("common.isExist.msg")); //이미 존재하거나 과거에 등록이 되었던 상태입니다.
						
					}else {

						code.setFrstRegisterId(user.getUniqId());
						cmmnCodeService.insertCmmnCode(code);
						jsonResult.setSuccess(true);
					}
					
				}
			}
		}catch(Exception e) {
			loggerError(LOGGER, e);
			jsonResult.setSuccess(false);
			jsonResult.setMessage(egovMessageSource.getMessage("fail.common.insert")); //생성이 실패하였습니다
		}
		return jsonResult;
	}
	
	/**
	 * 공통코드 수정 폼
	 * @param searchVO
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/decms/embed/code/modifyCode.do")
	public String modifyCode(@ModelAttribute("searchVO") CmmnCodeVO searchVO, Model model) throws Exception {
		//내부 직원 권한이 아닐때 
		if(!EgovUserDetailsHelper.getAuthorities().contains("ROLE_EMPLOYEE")) {
			return "redirect:/decms/index.do";
		}
		
		CmmnCodeVO code = cmmnCodeService.selectCmmnCode(searchVO);
		model.addAttribute("code", code);
		model.addAttribute("mode", "update");
		
		return "modoo/cms/cmm/code/commonCodeForm";
	}
	
	/**
	 * 공통코드 수정
	 * @param code
	 * @param bindingResult
	 * @return
	 */
	@RequestMapping(value = "/decms/code/modifyCode.json")
	@ResponseBody
	public JsonResult modifyCode(@Valid CmmnCodeVO code, BindingResult bindingResult) {
		JsonResult jsonResult = new JsonResult();
		LoginVO user = (LoginVO)EgovUserDetailsHelper.getAuthenticatedUser();
		
		try {
			//내부 직원 권한이 아닐때 
			if(!EgovUserDetailsHelper.getAuthorities().contains("ROLE_EMPLOYEE")) {
				jsonResult.setMessage(egovMessageSource.getMessage("cms.fail.accessDenied")); //접근 권한이 없습니다.
				jsonResult.setSuccess(false);
			}else {
				if(!this.isHasErrorsJson(bindingResult, jsonResult, "<br/>")) {
					
					if(StringUtils.isEmpty(code.getCodeId())) {
						this.vaildateMessage(egovMessageSource.getMessage("cms.fail.access"), jsonResult); // 잘못된 접근입니다.
						LOGGER.error("codeId 가 없음.");
					}else {

						code.setLastUpdusrId(user.getUniqId());
						cmmnCodeService.updateCmmnCode(code);
						jsonResult.setSuccess(true);
					}
					
				}
			}
		}catch(Exception e) {
			loggerError(LOGGER, e);
			jsonResult.setSuccess(false);
			jsonResult.setMessage(egovMessageSource.getMessage("fail.common.update")); //수정이 실패하였습니다.
		}
		return jsonResult;
	}
	
	/**
	 * 공통코드 삭제
	 * @param code
	 * @return
	 */
	@RequestMapping(value = "/decms/code/deleteCode.json")
	@ResponseBody
	public JsonResult deleteCode(CmmnCodeVO code) {
		JsonResult jsonResult = new JsonResult();
		LoginVO user = (LoginVO)EgovUserDetailsHelper.getAuthenticatedUser();
		
		try {
			//내부 직원 권한이 아닐때 
			if(!EgovUserDetailsHelper.getAuthorities().contains("ROLE_EMPLOYEE")) {
				jsonResult.setMessage(egovMessageSource.getMessage("cms.fail.accessDenied")); //접근 권한이 없습니다.
				jsonResult.setSuccess(false);
			}else {
				if(StringUtils.isEmpty(code.getCodeId())) {
					this.vaildateMessage(egovMessageSource.getMessage("cms.fail.access"), jsonResult); // 잘못된 접근입니다.
					LOGGER.error("codeId 가 없음.");
				}else if(cmmnCodeService.selectCmmnDetailCodeCheckCnt(code) > 0) {
					this.vaildateMessage(egovMessageSource.getMessage("cmmCodeVO.fail.checkSubCode"), jsonResult); // 하위코드가 존재합니다.
				}else {
					code.setLastUpdusrId(user.getUniqId());
					cmmnCodeService.deleteCmmnCode(code);
					jsonResult.setSuccess(true);
				}
			}
		}catch(Exception e) {
			loggerError(LOGGER, e);
			jsonResult.setSuccess(false);
			jsonResult.setMessage(egovMessageSource.getMessage("fail.common.delete")); //삭제가 실패하였습니다.
		}
		return jsonResult;
	}
	
	/**
	 * 공통코드상세 목록
	 * @param searchVO
	 * @return
	 */
	@RequestMapping(value = "/decms/code/codeDetailList.json")
	@ResponseBody 
	public JsonResult codeDetailList(CmmnDetailCodeVO searchVO) {
		JsonResult jsonResult = new JsonResult();
		
		try {
			//내부 직원 권한이 아닐때 
			if(!EgovUserDetailsHelper.getAuthorities().contains("ROLE_EMPLOYEE")) {
				jsonResult.setMessage(egovMessageSource.getMessage("cms.fail.accessDenied")); //접근 권한이 없습니다.
				jsonResult.setSuccess(false);
			}else {
				List<CmmnDetailCodeVO> resultList = cmmnCodeService.selectCmmnDetailCodeList(searchVO);
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
	 * 공통코드상세 등록 폼
	 * @param searchVO
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/decms/embed/code/writeCodeDetail.do")
	public String writeCodeDetail(CmmnDetailCodeVO searchVO, Model model) throws Exception {
		//내부 직원 권한이 아닐때 
		if(!EgovUserDetailsHelper.getAuthorities().contains("ROLE_EMPLOYEE")) {
			return "redirect:/decms/index.do";
		}
		
		CmmnDetailCodeVO codeDetail = new CmmnDetailCodeVO();
		codeDetail.setCodeId(searchVO.getCodeId());
		int maxSn = cmmnCodeService.selectCmmnDetailCodeSnMaxCnt(codeDetail);
		model.addAttribute("codeDetail", codeDetail);
		model.addAttribute("maxSn", maxSn);
		return "modoo/cms/cmm/code/commonCodeDetailForm";
	}
	
	/**
	 * 공통코드상세 저장
	 * @param codeDetail
	 * @param bindingResult
	 * @return
	 */
	@RequestMapping(value = "/decms/code/writeCodeDetail.json")
	@ResponseBody
	public JsonResult writeCodeDetail(@Valid CmmnDetailCodeVO codeDetail, BindingResult bindingResult) {
		JsonResult jsonResult = new JsonResult();
		LoginVO user = (LoginVO)EgovUserDetailsHelper.getAuthenticatedUser();
		
		try {
			//내부 직원 권한이 아닐때 
			if(!EgovUserDetailsHelper.getAuthorities().contains("ROLE_EMPLOYEE")) {
				jsonResult.setMessage(egovMessageSource.getMessage("cms.fail.accessDenied")); //접근 권한이 없습니다.
				jsonResult.setSuccess(false);
			}else {
				if(!this.isHasErrorsJson(bindingResult, jsonResult, "<br/>")) {
					
					int cnt = cmmnCodeService.selectCheckCmmnCodeDetailCnt(codeDetail);
					if(cnt > 0) {
						jsonResult.setSuccess(false);
						jsonResult.setMessage(egovMessageSource.getMessage("common.isExist.msg")); //이미 존재하거나 과거에 등록이 되었던 상태입니다.
						
					}else {
						codeDetail.setFrstRegisterId(user.getUniqId());
						cmmnCodeService.insertCmmnCodeDetail(codeDetail);
						jsonResult.setSuccess(true);
					}
					
				}
			}
		}catch(Exception e) {
			loggerError(LOGGER, e);
			jsonResult.setSuccess(false);
			jsonResult.setMessage(egovMessageSource.getMessage("fail.common.insert")); //생성이 실패하였습니다
		}
		return jsonResult;
	}

	/**
	 * 공통코드상세 수정 폼
	 * @param searchVO
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/decms/embed/code/modifyCodeDetail.do")
	public String modifyCodeDetail(CmmnDetailCodeVO searchVO, Model model) throws Exception {
		//내부 직원 권한이 아닐때 
		if(!EgovUserDetailsHelper.getAuthorities().contains("ROLE_EMPLOYEE")) {
			return "redirect:/decms/index.do";
		}
		
		CmmnDetailCodeVO codeDetail = cmmnCodeService.selectCmmnCodeDetail(searchVO);
		model.addAttribute("codeDetail", codeDetail);
		
		return "modoo/cms/cmm/code/commonCodeDetailForm";
	}
	
	/**
	 * 공통코드상세 수정
	 * @param codeDetail
	 * @param bindingResult
	 * @return
	 */
	@RequestMapping(value = "/decms/code/modifyCodeDetail.json")
	@ResponseBody
	public JsonResult modifyCodeDetail(@Valid CmmnDetailCodeVO codeDetail, BindingResult bindingResult) {
		JsonResult jsonResult = new JsonResult();
		LoginVO user = (LoginVO)EgovUserDetailsHelper.getAuthenticatedUser();
		
		try {
			//내부 직원 권한이 아닐때 
			if(!EgovUserDetailsHelper.getAuthorities().contains("ROLE_EMPLOYEE")) {
				jsonResult.setMessage(egovMessageSource.getMessage("cms.fail.accessDenied")); //접근 권한이 없습니다.
				jsonResult.setSuccess(false);
			}else {
				if(!this.isHasErrorsJson(bindingResult, jsonResult, "<br/>")) {
					
					if(StringUtils.isEmpty(codeDetail.getCodeId()) || StringUtils.isEmpty(codeDetail.getCode())) {
						this.vaildateMessage(egovMessageSource.getMessage("cms.fail.access"), jsonResult); // 잘못된 접근입니다.
						LOGGER.error("codeId or code 가 없음.");
						
					}else {
						codeDetail.setLastUpdusrId(user.getUniqId());
						cmmnCodeService.updateCmmnCodeDetail(codeDetail);
						jsonResult.setSuccess(true);
					}
					
				}
			}
		}catch(Exception e) {
			loggerError(LOGGER, e);
			jsonResult.setSuccess(false);
			jsonResult.setMessage(egovMessageSource.getMessage("fail.common.update")); //수정이 실패하였습니다.
		}
		return jsonResult;
	}
	
	/**
	 * 공통코드상세 삭제
	 * @param codeDetail
	 * @return
	 */
	@RequestMapping(value = "/decms/code/deleteCodeDetail.json")
	@ResponseBody
	public JsonResult deleteCodeDetail(CmmnDetailCodeVO codeDetail) {
		JsonResult jsonResult = new JsonResult();
		LoginVO user = (LoginVO)EgovUserDetailsHelper.getAuthenticatedUser();
		
		try {
			//내부 직원 권한이 아닐때 
			if(!EgovUserDetailsHelper.getAuthorities().contains("ROLE_EMPLOYEE")) {
				jsonResult.setMessage(egovMessageSource.getMessage("cms.fail.accessDenied")); //접근 권한이 없습니다.
				jsonResult.setSuccess(false);
			}else {
				if(StringUtils.isEmpty(codeDetail.getCodeId()) || StringUtils.isEmpty(codeDetail.getCode())) {
					this.vaildateMessage(egovMessageSource.getMessage("cms.fail.access"), jsonResult); // 잘못된 접근입니다.
					LOGGER.error("codeId or code 가 없음.");
					
				}else {
					codeDetail.setLastUpdusrId(user.getUniqId());
					cmmnCodeService.deleteCmmnCodeDetail(codeDetail);
					jsonResult.setSuccess(true);

				}
			}
		} catch(Exception e) {
			loggerError(LOGGER, e);
			jsonResult.setSuccess(false);
			jsonResult.setMessage(egovMessageSource.getMessage("fail.common.delete")); //삭제가 실패하였습니다.
		}
		
		return jsonResult;
	}
}

