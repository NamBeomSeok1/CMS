package modoo.cms.shop.idsrts.web;

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

import egovframework.com.cmm.EgovMessageSource;
import egovframework.com.cmm.util.EgovUserDetailsHelper;
import egovframework.rte.ptl.mvc.tags.ui.pagination.PaginationInfo;
import modoo.module.common.service.JsonResult;
import modoo.module.common.web.CommonDefaultController;
import modoo.module.shop.idsrts.service.IdsrtsService;
import modoo.module.shop.idsrts.service.IdsrtsVO;

@Controller
public class CmsIdsrtsController extends CommonDefaultController {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(CmsIdsrtsController.class);

	/** EgovMessageSource */
	@Resource(name = "egovMessageSource")
	protected EgovMessageSource egovMessageSource;
	
	@Resource(name = "idsrtsService")
	private IdsrtsService idsrtsService;
	
	//도서산간 관리
	@RequestMapping(value = "/decms/shop/idsrts/idsrtsManage.do")
	public String idsrtsManage(@ModelAttribute("searchVO") IdsrtsVO searchVO, Model model) throws Exception {
		//내부 직원 권한이 아닐때 
		if(!EgovUserDetailsHelper.getAuthorities().contains("ROLE_EMPLOYEE")) {
			return "redirect:/decms/index.do";
		}
		
		return "modoo/cms/shop/idsrts/idsrtsManage";
	}
	
	//도서산간 목록
	@RequestMapping(value = "/decms/shop/idsrts/idsrtsList.json")
	@ResponseBody
	public JsonResult idsrtsList(IdsrtsVO searchVO) {
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
				
				List<?> resultList = idsrtsService.selectIdsrtsList(searchVO);
				jsonResult.put("list", resultList);
				
				int totalRecordCount = idsrtsService.selectIdsrtsListCnt(searchVO);
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
	
	//도서산간 등록폼
	@RequestMapping(value = "/decms/embed/shop/idsrts/writeIdsrts.do")
	public String writeIdsrts(@ModelAttribute("searchVO") IdsrtsVO searchVO, Model model) throws Exception {
		//내부 직원 권한이 아닐때 
		if(!EgovUserDetailsHelper.getAuthorities().contains("ROLE_EMPLOYEE")) {
			return "redirect:/decms/index.do";
		}	
		
		model.addAttribute("idsrts", new IdsrtsVO());
		return "modoo/cms/shop/idsrts/idsrtsForm";
	}
	
	//도서산간 저장
	@RequestMapping(value = "/decms/shop/idsrts/writeIdsrts.json", method = RequestMethod.POST)
	@ResponseBody
	public JsonResult writeIdsrts(@Valid IdsrtsVO idsrts, BindingResult bindingResult) {
		JsonResult jsonResult = new JsonResult();
		
		try {
			//내부 직원 권한이 아닐때 
			if(!EgovUserDetailsHelper.getAuthorities().contains("ROLE_EMPLOYEE")) {
				jsonResult.setMessage(egovMessageSource.getMessage("cms.fail.accessDenied")); //접근 권한이 없습니다.
				jsonResult.setSuccess(false);
			}else {
				int checkCnt = idsrtsService.selectIdsrtsCheckCnt(idsrts);
				if(checkCnt > 0){
					jsonResult.setSuccess(false);
					jsonResult.setMessage(egovMessageSource.getMessage("idsrts.fail.existZip")); //생성이 실패하였습니다.
				}else{
					if(!this.isHasErrorsJson(bindingResult, jsonResult, "<br/>")) {
						idsrtsService.insertIdsrts(idsrts);
						jsonResult.setSuccess(true);
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

	//도서산간 수정 폼
	@RequestMapping(value = "/decms/embed/shop/idsrts/modifyIdsrts.do")
	public String modifyHdryCmpny(@ModelAttribute("searchVO") IdsrtsVO searchVO, Model model) throws Exception {
		//내부 직원 권한이 아닐때 
		if(!EgovUserDetailsHelper.getAuthorities().contains("ROLE_EMPLOYEE")) {
			return "redirect:/decms/index.do";
		}

		IdsrtsVO idsrts = idsrtsService.selectIdsrts(searchVO);
		model.addAttribute("idsrts", idsrts);
		return "modoo/cms/shop/idsrts/idsrtsForm";
	}

	//도서산간 수정
	@RequestMapping(value = "/decms/shop/idsrts/modifyIdsrts.json", method = RequestMethod.POST)
	@ResponseBody
	public JsonResult modifyHdryCmpny(@Valid IdsrtsVO idsrts, BindingResult bindingResult) {
		JsonResult jsonResult = new JsonResult();
		
		try {
			//내부 직원 권한이 아닐때 
			if(!EgovUserDetailsHelper.getAuthorities().contains("ROLE_EMPLOYEE")) {
				jsonResult.setMessage(egovMessageSource.getMessage("cms.fail.accessDenied")); //접근 권한이 없습니다.
				jsonResult.setSuccess(false);
			}else {
				if(!this.isHasErrorsJson(bindingResult, jsonResult, "<br/>")) {
					if(StringUtils.isEmpty(idsrts.getZip())) {
						this.vaildateMessage(egovMessageSource.getMessage("cms.fail.access"), jsonResult); // 잘못된 접근입니다.
						LOGGER.error("zip가 없음.");
					}else {
						idsrtsService.updateIdsrts(idsrts);
						jsonResult.setSuccess(true);
					}
				}
			}
		} catch(Exception e) {
			loggerError(LOGGER, e);
			jsonResult.setSuccess(false);
			jsonResult.setMessage(egovMessageSource.getMessage("fail.common.update")); //수정이 실패하였습니다
		}
		
		return jsonResult;
	}
	
	//도서산간 삭제
	@RequestMapping(value = "/decms/shop/idsrts/deleteIdsrts.json", method = RequestMethod.POST)
	@ResponseBody
	public JsonResult deleteHdryCmpny(IdsrtsVO idsrts) {
		JsonResult jsonResult = new JsonResult();
		try {
			//내부 직원 권한이 아닐때 
			if(!EgovUserDetailsHelper.getAuthorities().contains("ROLE_EMPLOYEE")) {
				jsonResult.setMessage(egovMessageSource.getMessage("cms.fail.accessDenied")); //접근 권한이 없습니다.
				jsonResult.setSuccess(false);
			}else {
				if(StringUtils.isEmpty(idsrts.getZip())) {
					this.vaildateMessage(egovMessageSource.getMessage("cms.fail.access"), jsonResult); // 잘못된 접근입니다.
					LOGGER.error("zip가 없음.");
				}else {
					idsrtsService.deleteIdsrts(idsrts);
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
