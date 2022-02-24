package modoo.cms.system.sts.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import modoo.module.common.service.JsonResult;
import modoo.module.common.web.CommonDefaultController;
import modoo.module.system.sts.bst.service.BbsSummaryService;
import modoo.module.system.sts.bst.service.BbsSummaryVO;
import egovframework.com.cmm.util.EgovUserDetailsHelper;
import egovframework.com.utl.fcc.service.EgovDateUtil;

@Controller
public class CmsBbsSummaryController extends CommonDefaultController {

	private static final Logger LOGGER = LoggerFactory.getLogger(CmsBbsSummaryController.class);
	
	@Resource(name = "bbsSummaryService")
	private BbsSummaryService bbsSummaryService;
	
	/**
	 * 게시물통계
	 * @param searchVO
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/decms/system/sts/bst/bbsSummary.do")
	public String bbsSummary(@ModelAttribute("searchVO") BbsSummaryVO searchVO, Model model) throws Exception {
		//내부 직원 권한이 아닐때 
		if(!EgovUserDetailsHelper.getAuthorities().contains("ROLE_EMPLOYEE")) {
			return "redirect:/decms/index.do";
		}
		
		String searchBgnde = EgovDateUtil.formatDate(EgovDateUtil.addMonth(EgovDateUtil.getToday(), -1), "-");
        String searchEndde = EgovDateUtil.formatDate(EgovDateUtil.getToday(), "-");

        searchVO.setSearchBgnde(searchBgnde);
        searchVO.setSearchEndde(searchEndde);
        
        searchVO.setStatsSe("BBS"); //게시글 전체
        
		return "modoo/cms/system/sts/bst/bbsSummary";
	}
	
	/**
	 * 게시물통계 목록
	 * @param searchVO
	 * @return
	 */
	@RequestMapping(value = "/decms/system/sts/bst/bbsSummaryList.json")
	@ResponseBody
	public JsonResult bbsSummaryList(BbsSummaryVO searchVO) {
		JsonResult jsonResult = new JsonResult();
		
		try {
			//내부 직원 권한이 아닐때 
			if(!EgovUserDetailsHelper.getAuthorities().contains("ROLE_EMPLOYEE")) {
				jsonResult.setMessage(egovMessageSource.getMessage("cms.fail.accessDenied")); //접근 권한이 없습니다.
				jsonResult.setSuccess(false);
			}else {
				searchVO.setSearchBgnde(EgovDateUtil.validChkDate(searchVO.getSearchBgnde()));
				searchVO.setSearchEndde(EgovDateUtil.validChkDate(searchVO.getSearchEndde()));
				
				List<BbsSummaryVO> resultList = bbsSummaryService.selectBbsSummaryStats(searchVO);
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
	 * 게시물통계 엑셀 다운로드
	 * @param searchVO
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/decms/system/sts/bst/bbsSummaryListExcel.do")
	public ModelAndView bbsSummaryListExcel(@ModelAttribute("searchVO") BbsSummaryVO searchVO, Model model) throws Exception {
		//내부 직원 권한이 아닐때 
		if(!EgovUserDetailsHelper.getAuthorities().contains("ROLE_EMPLOYEE")) {
			return new ModelAndView("redirect:/decms/index.do");
		}
		
		Map<String, Object> map = new HashMap<String, Object>();
		
		searchVO.setSearchBgnde(EgovDateUtil.validChkDate(searchVO.getSearchBgnde()));
		searchVO.setSearchEndde(EgovDateUtil.validChkDate(searchVO.getSearchEndde()));
		
		List<BbsSummaryVO> resultList = bbsSummaryService.selectBbsSummaryStats(searchVO);
		map.put("dataList", resultList);
		map.put("template", "system_sts_bbssummary_list.xlsx");
		map.put("fileName", "게시물통계");
		
		return new ModelAndView("commonExcelView", map);
	}
}
