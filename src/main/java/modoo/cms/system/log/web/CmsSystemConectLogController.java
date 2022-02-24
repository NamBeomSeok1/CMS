package modoo.cms.system.log.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import modoo.module.common.service.JsonResult;
import modoo.module.common.util.SiteDomainHelper;
import modoo.module.common.web.CommonDefaultController;
import modoo.module.system.log.conect.service.ConectLogService;
import modoo.module.system.log.conect.service.ConectLogVO;
import egovframework.com.cmm.util.EgovUserDetailsHelper;
import egovframework.com.utl.fcc.service.EgovDateUtil;

@Controller
public class CmsSystemConectLogController extends CommonDefaultController {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(CmsSystemConectLogController.class);
	
	@Resource(name = "conectLogService")
	private ConectLogService conectLogService;
	
	/**
	 * 접속통계
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/decms/system/log/conectLogManage.do")
	public String conectLogManage(@ModelAttribute("searchVO") ConectLogVO searchVO, Model model) throws Exception {
		//내부 직원 권한이 아닐때 
		if(!EgovUserDetailsHelper.getAuthorities().contains("ROLE_EMPLOYEE")) {
			return "redirect:/decms/index.do";
		}
		
		String searchBgnde = EgovDateUtil.formatDate(EgovDateUtil.addMonth(EgovDateUtil.getToday(), -1), "-");
        String searchEndde = EgovDateUtil.formatDate(EgovDateUtil.getToday(), "-");

        searchVO.setSearchBgnde(searchBgnde);
        searchVO.setSearchEndde(searchEndde);
        
		return "modoo/cms/system/log/conect/conectLogManage";
	}
	
	/**
	 * B2C접속카운트 목록
	 * @param searchVO
	 * @return
	 */
	@RequestMapping(value = "/decms/system/log/conectLogCountList.json")
	@ResponseBody
	public JsonResult connctLogList(ConectLogVO searchVO) {
		JsonResult jsonResult = new JsonResult();
		
		try {
			//내부 직원 권한이 아닐때 
			if(!EgovUserDetailsHelper.getAuthorities().contains("ROLE_EMPLOYEE")) {
				jsonResult.setMessage(egovMessageSource.getMessage("cms.fail.accessDenied")); //접근 권한이 없습니다.
				jsonResult.setSuccess(false);
			}else {
				if(StringUtils.isEmpty(searchVO.getSiteId())) {
					searchVO.setSearchSiteId(SiteDomainHelper.getSiteId());
				}
				
				searchVO.setSearchBgnde(EgovDateUtil.validChkDate(searchVO.getSearchBgnde()));
				searchVO.setSearchEndde(EgovDateUtil.validChkDate(searchVO.getSearchEndde()));
				
				List<ConectLogVO> B2CResultList = (List<ConectLogVO>) conectLogService.selectB2CConectCountList(searchVO);
				jsonResult.put("B2CList", B2CResultList);

				List<ConectLogVO> B2BResultList = (List<ConectLogVO>) conectLogService.selectB2BConectCountList(searchVO);
				jsonResult.put("B2BList", B2BResultList);

				Integer B2CTotalLogCount = 0;
				Integer B2BTotalLogCount = 0;
				
				if(B2CResultList.size() > 0) {
					ConectLogVO vo = new ConectLogVO();
					vo = (ConectLogVO) B2CResultList.get(B2CResultList.size()-1);
					B2CTotalLogCount = vo.getSumCount();

				}
				if(B2BResultList.size() > 0) {
					ConectLogVO vo = new ConectLogVO();
					vo = (ConectLogVO) B2BResultList.get(B2BResultList.size()-1);
					B2BTotalLogCount = vo.getSumCount();
					
				}
				
				jsonResult.put("B2CTotalLogCount", B2CTotalLogCount);
				jsonResult.put("B2BTotalLogCount", B2BTotalLogCount);
				
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
	 * 접속카운트 엑셀 다운로드
	 * @param searchVO
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/decms/system/log/conectLogCountListExcel.do")
	public ModelAndView conectLogCountListExcel(@ModelAttribute("searchVO") ConectLogVO searchVO, Model model) throws Exception {
		//내부 직원 권한이 아닐때 
		if(!EgovUserDetailsHelper.getAuthorities().contains("ROLE_EMPLOYEE")) {
			return new ModelAndView("redirect:/decms/index.do");
		}
		
		Map<String, Object> map = new HashMap<String, Object>();

		if(StringUtils.isEmpty(searchVO.getSiteId())) {
			searchVO.setSearchSiteId(SiteDomainHelper.getSiteId());
		}
		searchVO.setSearchBgnde(EgovDateUtil.validChkDate(searchVO.getSearchBgnde()));
		searchVO.setSearchEndde(EgovDateUtil.validChkDate(searchVO.getSearchEndde()));
		
		List<ConectLogVO> resultList = conectLogService.selectConectCountList(searchVO);
		map.put("dataList", resultList);
		map.put("template", "system_log_list.xlsx");
		map.put("fileName", "일별통계");
		
		return new ModelAndView("commonExcelView", map);
	}

}
