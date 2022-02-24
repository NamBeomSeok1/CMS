package modoo.front.banner.web;

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

import modoo.module.banner.service.BannerService;
import modoo.module.banner.service.BannerVO;
import modoo.module.common.service.JsonResult;
import modoo.module.common.util.SiteDomainHelper;
import modoo.module.common.web.CommonDefaultController;
import egovframework.com.cmm.ComDefaultCodeVO;
import egovframework.com.cmm.EgovMessageSource;
import egovframework.com.cmm.LoginVO;
import egovframework.com.cmm.service.EgovCmmUseService;
import egovframework.com.cmm.util.EgovUserDetailsHelper;
import egovframework.com.utl.fcc.service.EgovDateUtil;
import egovframework.rte.ptl.mvc.tags.ui.pagination.PaginationInfo;

@Controller
public class BannerController extends CommonDefaultController {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(BannerController.class);

	/** EgovMessageSource */
	@Resource(name = "egovMessageSource")
	protected EgovMessageSource egovMessageSource;
	
	@Resource(name = "bannerService")
	private BannerService bannerService;
	
	/** EgovCmmUseService */
	@Resource(name = "EgovCmmUseService")
	private EgovCmmUseService cmmUseService;
	
	
	//투데이스 픽 - 메인추출
	@RequestMapping(value = "/banner/pickList.do")
	public String todaysPickList(@ModelAttribute("searchVO") BannerVO searchVO, Model model) throws Exception {
		
		List<?> resultList = bannerService.selectTodaysPickList(searchVO);
		model.addAttribute("resultList", resultList);
	
		return "modoo/front/banner/pickList";
	}
	
	//투데이스 픽 - 메인추출
	/*
	@RequestMapping(value = "/banner/todaysPickList.do")
	public String todaysPickList(@ModelAttribute("searchVO") BannerVO searchVO, Model model) throws Exception {
		List<?> resultList = bannerService.selectTodaysPickList(searchVO);
		model.addAttribute("resultList", resultList);
	
		return "modoo/front/banner/todaysPickList";
	}
	*/
}
