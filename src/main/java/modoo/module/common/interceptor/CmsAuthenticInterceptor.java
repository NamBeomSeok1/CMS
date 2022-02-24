package modoo.module.common.interceptor;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.ModelAndViewDefiningException;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import egovframework.com.cmm.EgovMessageSource;
import egovframework.com.cmm.service.Globals;
import egovframework.com.cmm.util.EgovUserDetailsHelper;
import modoo.module.common.service.JsonResult;

public class CmsAuthenticInterceptor extends HandlerInterceptorAdapter {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(CmsAuthenticInterceptor.class);
	
	@Resource(name = "egovMessageSource")
	protected EgovMessageSource egovMessageSource;

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		boolean result = true;
		List<String> authList = (List<String>) EgovUserDetailsHelper.getAuthorities();
		JsonResult jsonResult = new JsonResult();
		
		/*
		for(String auth: authList) {
			LOGGER.debug("ROLE : " + auth);
		}
		*/

		if(!EgovUserDetailsHelper.isAuthenticated()) {
			jsonResult.setMessage(egovMessageSource.getMessage("cms.fail.accessError")); //세션 만료 또는 로그인 후 이용가능 합니다.
			jsonResult.setRedirectUrl(Globals.MAIN_PAGE);
			result = false;

		}else if(!authList.contains("ROLE_SHOP")) { //관리자 권한 (ROLE_SHOP)
			jsonResult.setMessage(egovMessageSource.getMessage("cms.fail.accessDenied")); //접근 권한이 없습니다.
			jsonResult.setRedirectUrl(Globals.MAIN_PAGE);
			result = false;
		}

		if(result == false) {
			jsonResult.setSuccess(false);

			ModelAndView mv = new ModelAndView("jsonView");
			//mv.addObject(jsonResult);
			mv.addObject("data", jsonResult.getData());
			mv.addObject("errorList", jsonResult.getErrorList());
			mv.addObject("eventCode", jsonResult.getEventCode());
			mv.addObject("message", jsonResult.getMessage());
			mv.addObject("redirectUrl", jsonResult.getRedirectUrl());
			mv.addObject("success", jsonResult.isSuccess());
			

			throw new ModelAndViewDefiningException(mv);
		}
		
		return result; 
	}

}
