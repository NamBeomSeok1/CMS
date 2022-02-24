package modoo.module.common.web;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import modoo.module.common.service.CommonDefaultSearchVO;
import modoo.module.common.service.ErrorInfo;
import modoo.module.common.service.JsonResult;
import egovframework.com.cmm.EgovMessageSource;
import egovframework.com.cmm.service.EgovProperties;
import egovframework.rte.fdl.property.EgovPropertyService;
import egovframework.rte.ptl.mvc.tags.ui.pagination.PaginationInfo;

public class CommonDefaultController {
	
	public final static String defaultSiteId = EgovProperties.getProperty("CMS.portal.SITE_ID");

	/** EgovPropertyService */
	@Resource(name = "propertiesService")
	protected EgovPropertyService propertiesService;

	/** EgovMessageSource */
	@Resource(name = "egovMessageSource")
	protected EgovMessageSource egovMessageSource;
	
	protected void loggerError(Logger LOGGER, Exception e) {
		if("DEV".equals(EgovProperties.getProperty("CMS.mode")))  {
			e.printStackTrace();
		}else {
			LOGGER.error("Exception : ", e);
		}
	}

	/**
	 * Pagination
	 * @param paginationInfo
	 * @param searchVO
	 * @throws Exception
	 */
	protected void setPagination(PaginationInfo paginationInfo, CommonDefaultSearchVO searchVO) throws Exception {
		searchVO.setPageUnit(searchVO.getPageUnit());
		searchVO.setPageSize(propertiesService.getInt("pageSize"));

		paginationInfo.setCurrentPageNo(searchVO.getPageIndex());
		paginationInfo.setRecordCountPerPage(searchVO.getPageUnit());
		paginationInfo.setPageSize(searchVO.getPageSize());

		searchVO.setFirstIndex(paginationInfo.getFirstRecordIndex());
		searchVO.setLastIndex(paginationInfo.getLastRecordIndex());
		searchVO.setRecordCountPerPage(paginationInfo.getRecordCountPerPage());
	}

	protected void setPagination(PaginationInfo paginationInfo, CommonDefaultSearchVO searchVO, int pageSize) throws Exception {
		searchVO.setPageUnit(searchVO.getPageUnit());
		if(pageSize > 0) {
			searchVO.setPageSize(pageSize);
		}else {
			searchVO.setPageSize(propertiesService.getInt("pageSize"));
		}

		paginationInfo.setCurrentPageNo(searchVO.getPageIndex());
		paginationInfo.setRecordCountPerPage(searchVO.getPageUnit());
		paginationInfo.setPageSize(searchVO.getPageSize());

		searchVO.setFirstIndex(paginationInfo.getFirstRecordIndex());
		searchVO.setLastIndex(paginationInfo.getLastRecordIndex());
		searchVO.setRecordCountPerPage(paginationInfo.getRecordCountPerPage());
	}
		
	/**
	 * Json validation
	 * @param bindingResult
	 * @param jsonResult
	 * @param errDi
	 * @return
	 * @throws Exception
	 */
	protected boolean isHasErrorsJson(BindingResult bindingResult, JsonResult jsonResult, String errDi) throws Exception {
		boolean isHasErrors = false;
		if(bindingResult.hasErrors()) {
			List<FieldError> errors = bindingResult.getFieldErrors();
			String errorMessage = "";
			List<ErrorInfo> errList = new ArrayList<ErrorInfo>();

			for(FieldError error : errors) {
				ErrorInfo ei = new ErrorInfo();
				ei.setParam(error.getField());
				String message = egovMessageSource.getMessage(error);
				ei.setMessage(message);
				errList.add(ei);
				
				errorMessage += message + errDi;
			}

			jsonResult.setSuccess(false);
			jsonResult.setErrorList(errList);
			jsonResult.setMessage(errorMessage);

			isHasErrors = true;
		}

		return isHasErrors;
		
	}

	/**
	 * Json validation
	 * @param bindingResult
	 * @param jsonResult
	 * @return
	 * @throws Exception
	 */
	protected boolean isHasErrorsJson(BindingResult bindingResult, JsonResult jsonResult) throws Exception {
		return this.isHasErrorsJson(bindingResult, jsonResult, "\n");
		/*
		boolean isHasErrors = false;
		if(bindingResult.hasErrors()) {
			List<FieldError> errors = bindingResult.getFieldErrors();
			String errorMessage = "";
			List<ErrorInfo> errList = new ArrayList<ErrorInfo>();

			for(FieldError error : errors) {
				ErrorInfo ei = new ErrorInfo();
				ei.setParam(error.getField());
				String message = egovMessageSource.getMessage(error);
				ei.setMessage(message);
				errList.add(ei);
				
				errorMessage += message + "\n";
			}

			jsonResult.setSuccess(false);
			jsonResult.setErrorList(errList);
			jsonResult.setMessage(errorMessage);

			isHasErrors = true;
		}

		return isHasErrors;
		*/
	}

	/**
	 * Vaildate Message
	 * @param message
	 * @param jsonResult
	 * @return
	 */
	protected JsonResult vaildateMessage(String message, JsonResult jsonResult) {
		jsonResult.setSuccess(false);
		jsonResult.setMessage(message);
		return jsonResult;
	}
	
	/**
	 * XSS 방지 처리
	 * @param data
	 * @return
	 */
	protected String unscript(String data) {
		if (data == null || data.trim().equals("")) {
			return "";
		}
		
		String ret = data;
		ret = ret.replaceAll("<(S|s)(C|c)(R|r)(I|i)(P|p)(T|t)", "&lt;script");
		ret = ret.replaceAll("</(S|s)(C|c)(R|r)(I|i)(P|p)(T|t)", "&lt;/script");
		ret = ret.replaceAll("<(O|o)(B|b)(J|j)(E|e)(C|c)(T|t)", "&lt;object");
		ret = ret.replaceAll("</(O|o)(B|b)(J|j)(E|e)(C|c)(T|t)", "&lt;/object");
		ret = ret.replaceAll("<(A|a)(P|p)(P|p)(L|l)(E|e)(T|t)", "&lt;applet");
		ret = ret.replaceAll("</(A|a)(P|p)(P|p)(L|l)(E|e)(T|t)", "&lt;/applet");
		ret = ret.replaceAll("<(E|e)(M|m)(B|b)(E|e)(D|d)", "&lt;embed");
		ret = ret.replaceAll("</(E|e)(M|m)(B|b)(E|e)(D|d)", "&lt;embed");
		ret = ret.replaceAll("<(F|f)(O|o)(R|r)(M|m)", "&lt;form");
		ret = ret.replaceAll("</(F|f)(O|o)(R|r)(M|m)", "&lt;form");
		return ret;
	}
	
}
