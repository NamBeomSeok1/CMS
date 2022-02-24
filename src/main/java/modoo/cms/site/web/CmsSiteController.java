package modoo.cms.site.web;

import javax.annotation.Resource;
import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import egovframework.com.cmm.EgovMessageSource;
import egovframework.com.cmm.util.EgovUserDetailsHelper;
import modoo.module.common.service.JsonResult;
import modoo.module.common.util.SiteDomainHelper;
import modoo.module.common.web.CommonDefaultController;
import modoo.module.site.service.SiteService;
import modoo.module.site.service.SiteVO;

@Controller
public class CmsSiteController extends CommonDefaultController {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(CmsSiteController.class);
	
	@Resource(name = "siteService")
	private SiteService siteService;
	
	/** EgovMessageSource */
	@Resource(name = "egovMessageSource")
	protected EgovMessageSource egovMessageSource;

	/**
	 * 사이트 관리
	 * @param searchVO
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/decms/site/info/siteManage.do", method = RequestMethod.GET)
	public String siteManage(SiteVO searchVO, Model model) throws Exception {
		//관리 권한이 아닐때 
		if(!EgovUserDetailsHelper.getAuthorities().contains("ROLE_ADMIN")) {
			return "redirect:/decms/index.do";
		}

		return "forward:/decms/site/info/modifySite.do";
	}
	
	/**
	 * 사이트 수정 폼
	 * @param site
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/decms/site/info/modifySite.do", method = RequestMethod.GET)
	public String modifySite(SiteVO site, Model model) throws Exception {
		//관리 권한이 아닐때 
		if(!EgovUserDetailsHelper.getAuthorities().contains("ROLE_ADMIN")) {
			return "redirect:/decms/index.do";
		}
		
		if(StringUtils.isEmpty(site.getSiteId())) {
			site.setSiteId(SiteDomainHelper.getSiteId());
		}
		
		SiteVO vo = siteService.selectSite(site);

		model.addAttribute("site", vo);

		return "modoo/cms/site/info/siteForm";
	}
	
	/**
	 * 사이트 수정
	 * @param site
	 * @param bindingResult
	 * @return
	 */
	@RequestMapping(value = "/decms/ajax/site/info/modifySite.json")
	@ResponseBody
	public JsonResult modifySite(@Valid SiteVO site, BindingResult bindingResult) {
		JsonResult jsonResult = new JsonResult();
		
		try {
			//내부 직원 권한이 아닐때 
			if(!EgovUserDetailsHelper.getAuthorities().contains("ROLE_ADMIN")) {
				jsonResult.setMessage(egovMessageSource.getMessage("cms.fail.accessDenied")); //접근 권한이 없습니다.
				jsonResult.setSuccess(false);
			}else {
				if(!this.isHasErrorsJson(bindingResult, jsonResult)) {
					
					// 도메인 입력 확인
					
					
					// 도메인 중복 확인 (입력 중복, 저장 중복)
					
					siteService.updateSite(site);
					
					jsonResult.setSuccess(true);
					jsonResult.setMessage(egovMessageSource.getMessage("success.common.update")); //정상적으로 수정되었습니다.
				}
			}
		}catch(Exception e) {
			LOGGER.error("Exception : ", e);
			jsonResult.setSuccess(false);
			jsonResult.setMessage(egovMessageSource.getMessage("fail.common.update")); // 수정이 실패하였습니다.
			
		}

		return jsonResult;
	}
	
	
}
