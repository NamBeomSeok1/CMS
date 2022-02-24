package modoo.cms.shop.stats.cstmr.web;

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

import egovframework.com.cmm.LoginVO;
import egovframework.com.cmm.util.EgovUserDetailsHelper;
import modoo.module.common.service.JsonResult;
import modoo.module.common.util.CommonUtils;
import modoo.module.common.web.CommonDefaultController;
import modoo.module.shop.stats.cstmr.service.StatsCstmrSearchVO;
import modoo.module.shop.stats.cstmr.service.StatsCstmrService;

@Controller
public class CmsStatsCstmrController extends CommonDefaultController {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(CmsStatsCstmrController.class);
	
	@Resource(name = "statsCstmrService")
	private StatsCstmrService statsCstmrService;
	
	/**
	 * 고객 요일별 분석
	 * @param searchVO
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/decms/shop/stats/cstmrWeekManage.do")
	public String cstmrWeekManage(@ModelAttribute("searchVO") StatsCstmrSearchVO searchVO, Model model) throws Exception {
		if(StringUtils.isEmpty(searchVO.getSearchBgnde())) {
			searchVO.setSearchBgnde(CommonUtils.getCurrentDateFormat("yyyy-MM-dd", -30));
		}
		
		if(StringUtils.isEmpty(searchVO.getSearchEndde())) {
			searchVO.setSearchEndde(CommonUtils.getCurrentDateFormat("yyyy-MM-dd"));
		}

		model.addAttribute("searchToday", CommonUtils.getCurrentDateFormat("yyyy-MM-dd"));
		model.addAttribute("searchDay1", CommonUtils.getCurrentDateFormat("yyyy-MM-dd","DAY",null,-1));
		model.addAttribute("searchDay3", CommonUtils.getCurrentDateFormat("yyyy-MM-dd","DAY",null,-3));
		model.addAttribute("searchDay7", CommonUtils.getCurrentDateFormat("yyyy-MM-dd","DAY",null,-7));
		model.addAttribute("searchDay10", CommonUtils.getCurrentDateFormat("yyyy-MM-dd","DAY",null,-10));
		model.addAttribute("searchDay20", CommonUtils.getCurrentDateFormat("yyyy-MM-dd","DAY",null,-20));
		model.addAttribute("searchDay30", CommonUtils.getCurrentDateFormat("yyyy-MM-dd","DAY",null,-30));
		
		return "modoo/cms/shop/stats/cstmr/cstmrWeekManage";
	}
	
	/**
	 * 고객 요일별 분석 목록
	 * @param searchVO
	 * @return
	 */
	@RequestMapping(value = "/decms/shop/stats/cstmrWeekList.json")
	@ResponseBody
	public JsonResult cstmrWeekList(StatsCstmrSearchVO searchVO) {
		LoginVO user = (LoginVO)EgovUserDetailsHelper.getAuthenticatedUser();
		JsonResult jsonResult = new JsonResult();
		
		try {
			if(StringUtils.isEmpty(searchVO.getSearchBgnde())) {
				jsonResult.setSuccess(false);
				jsonResult.setMessage("시작일을 선택하세요");
			}else if(StringUtils.isEmpty(searchVO.getSearchEndde())) {
				jsonResult.setSuccess(false);
				jsonResult.setMessage("종료일을 선택하세요");
			}else {
				if(!EgovUserDetailsHelper.getAuthorities().contains("ROLE_EMPLOYEE")) {
					searchVO.setSearchCondition("CID");
					if(StringUtils.isEmpty(user.getCmpnyId())) {
						searchVO.setSearchKeyword("업체고유ID가 없음"); //검색이 안되게 하기위해
					}else {
						searchVO.setSearchKeyword(user.getCmpnyId());
					}
				}
				
				searchVO.setSearchBgnde(searchVO.getSearchBgnde().replace("-", ""));
				searchVO.setSearchEndde(searchVO.getSearchEndde().replace("-", ""));
				
				List<?> resultList = statsCstmrService.selectStatsCstmrWeekList(searchVO);
				jsonResult.put("list", resultList);
				
				jsonResult.setSuccess(true);
			}
			
		}catch(Exception e) {
			loggerError(LOGGER, e);
			jsonResult.setSuccess(false);
			jsonResult.setMessage(egovMessageSource.getMessage("fail.common.select")); //조회에 실패하였습니다.
		}
		
		return jsonResult;
	}
	
	/**
	 * 고객 요일별 분석 엑셀
	 * @param searchVO
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/decms/shop/stats/cstmrWeekListExcel.do")
	public ModelAndView cstmrWeekListExcel(@ModelAttribute("searchVO") StatsCstmrSearchVO searchVO, Model model) throws Exception {
		LoginVO user = (LoginVO)EgovUserDetailsHelper.getAuthenticatedUser();
		Map<String, Object> map = new HashMap<String, Object>();
		
		searchVO.setSearchBgnde(searchVO.getSearchBgnde().replace("-", ""));
		searchVO.setSearchEndde(searchVO.getSearchEndde().replace("-", ""));
		
		List<?> resultList = statsCstmrService.selectStatsCstmrWeekList(searchVO);
		map.put("dataList", resultList);
		map.put("template", "stats_cstmr_week_list.xlsx");
		map.put("fileName", "교객요일별분석");
		
		return new ModelAndView("commonExcelView", map);
	}

	/**
	 * 고객 시간대별 분석
	 * @param searchVO
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/decms/shop/stats/cstmrHourManage.do")
	public String cstmrHourManage(@ModelAttribute("searchVO") StatsCstmrSearchVO searchVO, Model model) throws Exception {
		if(StringUtils.isEmpty(searchVO.getSearchBgnde())) {
			searchVO.setSearchBgnde(CommonUtils.getCurrentDateFormat("yyyy-MM-dd", -30));
		}
		
		if(StringUtils.isEmpty(searchVO.getSearchEndde())) {
			searchVO.setSearchEndde(CommonUtils.getCurrentDateFormat("yyyy-MM-dd"));
		}

		model.addAttribute("searchToday", CommonUtils.getCurrentDateFormat("yyyy-MM-dd"));
		model.addAttribute("searchDay1", CommonUtils.getCurrentDateFormat("yyyy-MM-dd","DAY",null,-1));
		model.addAttribute("searchDay3", CommonUtils.getCurrentDateFormat("yyyy-MM-dd","DAY",null,-3));
		model.addAttribute("searchDay7", CommonUtils.getCurrentDateFormat("yyyy-MM-dd","DAY",null,-7));
		model.addAttribute("searchDay10", CommonUtils.getCurrentDateFormat("yyyy-MM-dd","DAY",null,-10));
		model.addAttribute("searchDay20", CommonUtils.getCurrentDateFormat("yyyy-MM-dd","DAY",null,-20));
		model.addAttribute("searchDay30", CommonUtils.getCurrentDateFormat("yyyy-MM-dd","DAY",null,-30));
		
		return "modoo/cms/shop/stats/cstmr/cstmrHourManage";
	}
	
	/**
	 * 고객 요일별 분석 목록
	 * @param searchVO
	 * @return
	 */
	@RequestMapping(value = "/decms/shop/stats/cstmrHourList.json")
	@ResponseBody
	public JsonResult cstmrHourList(StatsCstmrSearchVO searchVO) {
		LoginVO user = (LoginVO)EgovUserDetailsHelper.getAuthenticatedUser();
		JsonResult jsonResult = new JsonResult();
		
		try {
			if(StringUtils.isEmpty(searchVO.getSearchBgnde())) {
				jsonResult.setSuccess(false);
				jsonResult.setMessage("시작일을 선택하세요");
			}else if(StringUtils.isEmpty(searchVO.getSearchEndde())) {
				jsonResult.setSuccess(false);
				jsonResult.setMessage("종료일을 선택하세요");
			}else {
				if(!EgovUserDetailsHelper.getAuthorities().contains("ROLE_EMPLOYEE")) {
					searchVO.setSearchCondition("CID");
					if(StringUtils.isEmpty(user.getCmpnyId())) {
						searchVO.setSearchKeyword("업체고유ID가 없음"); //검색이 안되게 하기위해
					}else {
						searchVO.setSearchKeyword(user.getCmpnyId());
					}
				}
				
				searchVO.setSearchBgnde(searchVO.getSearchBgnde().replace("-", ""));
				searchVO.setSearchEndde(searchVO.getSearchEndde().replace("-", ""));
				
				List<?> resultList = statsCstmrService.selectStatsCstmrHourList(searchVO);
				jsonResult.put("list", resultList);
				
				jsonResult.setSuccess(true);
			}
			
		}catch(Exception e) {
			loggerError(LOGGER, e);
			jsonResult.setSuccess(false);
			jsonResult.setMessage(egovMessageSource.getMessage("fail.common.select")); //조회에 실패하였습니다.
		}
		
		return jsonResult;
	}
	
	/**
	 * 고객 요일별 분석 엑셀
	 * @param searchVO
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/decms/shop/stats/cstmrHourListExcel.do")
	public ModelAndView cstmrHourListExcel(@ModelAttribute("searchVO") StatsCstmrSearchVO searchVO, Model model) throws Exception {
		LoginVO user = (LoginVO)EgovUserDetailsHelper.getAuthenticatedUser();
		Map<String, Object> map = new HashMap<String, Object>();
		
		searchVO.setSearchBgnde(searchVO.getSearchBgnde().replace("-", ""));
		searchVO.setSearchEndde(searchVO.getSearchEndde().replace("-", ""));
		
		List<?> resultList = statsCstmrService.selectStatsCstmrHourList(searchVO);
		map.put("dataList", resultList);
		map.put("template", "stats_cstmr_hour_list.xlsx");
		map.put("fileName", "교객시간대별분석");
		
		return new ModelAndView("commonExcelView", map);
	}
	
	/**
	 * 배송지역별 분석
	 * @param searchVO
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/decms/shop/stats/cstmrAreaManage.do")
	public String cstmrAreaManage(@ModelAttribute("searchVO") StatsCstmrSearchVO searchVO, Model model) throws Exception {
		if(StringUtils.isEmpty(searchVO.getSearchBgnde())) {
			searchVO.setSearchBgnde(CommonUtils.getCurrentDateFormat("yyyy-MM-dd", -30));
		}
		
		if(StringUtils.isEmpty(searchVO.getSearchEndde())) {
			searchVO.setSearchEndde(CommonUtils.getCurrentDateFormat("yyyy-MM-dd"));
		}

		model.addAttribute("searchToday", CommonUtils.getCurrentDateFormat("yyyy-MM-dd"));
		model.addAttribute("searchDay1", CommonUtils.getCurrentDateFormat("yyyy-MM-dd","DAY",null,-1));
		model.addAttribute("searchDay3", CommonUtils.getCurrentDateFormat("yyyy-MM-dd","DAY",null,-3));
		model.addAttribute("searchDay7", CommonUtils.getCurrentDateFormat("yyyy-MM-dd","DAY",null,-7));
		model.addAttribute("searchDay10", CommonUtils.getCurrentDateFormat("yyyy-MM-dd","DAY",null,-10));
		model.addAttribute("searchDay20", CommonUtils.getCurrentDateFormat("yyyy-MM-dd","DAY",null,-20));
		model.addAttribute("searchDay30", CommonUtils.getCurrentDateFormat("yyyy-MM-dd","DAY",null,-30));
		
		return "modoo/cms/shop/stats/cstmr/cstmrAreaManage";
	}
	
	/**
	 * 배송지역별 목록
	 * @param searchVO
	 * @return
	 */
	@RequestMapping(value = "/decms/shop/stats/cstmrAreaList.json")
	@ResponseBody
	public JsonResult cstmrAreaList(StatsCstmrSearchVO searchVO) {
		LoginVO user = (LoginVO)EgovUserDetailsHelper.getAuthenticatedUser();
		JsonResult jsonResult = new JsonResult();
		
		try {
			if(StringUtils.isEmpty(searchVO.getSearchBgnde())) {
				jsonResult.setSuccess(false);
				jsonResult.setMessage("시작일을 선택하세요");
			}else if(StringUtils.isEmpty(searchVO.getSearchEndde())) {
				jsonResult.setSuccess(false);
				jsonResult.setMessage("종료일을 선택하세요");
			}else {
				if(!EgovUserDetailsHelper.getAuthorities().contains("ROLE_EMPLOYEE")) {
					searchVO.setSearchCondition("CID");
					if(StringUtils.isEmpty(user.getCmpnyId())) {
						searchVO.setSearchKeyword("업체고유ID가 없음"); //검색이 안되게 하기위해
					}else {
						searchVO.setSearchKeyword(user.getCmpnyId());
					}
				}
				
				searchVO.setSearchBgnde(searchVO.getSearchBgnde().replace("-", ""));
				searchVO.setSearchEndde(searchVO.getSearchEndde().replace("-", ""));
				
				List<?> resultList = statsCstmrService.selectStatsCstmrAreaList(searchVO);
				jsonResult.put("list", resultList);
				
				jsonResult.setSuccess(true);
			}
			
		}catch(Exception e) {
			loggerError(LOGGER, e);
			jsonResult.setSuccess(false);
			jsonResult.setMessage(egovMessageSource.getMessage("fail.common.select")); //조회에 실패하였습니다.
		}
		
		return jsonResult;
	}
	
	/**
	 * 배송지역별 엑셀 
	 * @param searchVO
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/decms/shop/stats/cstmrAreaListExcel.do")
	public ModelAndView cstmrAreaListExcel(@ModelAttribute("searchVO") StatsCstmrSearchVO searchVO, Model model) throws Exception {
		LoginVO user = (LoginVO)EgovUserDetailsHelper.getAuthenticatedUser();
		Map<String, Object> map = new HashMap<String, Object>();
		
		searchVO.setSearchBgnde(searchVO.getSearchBgnde().replace("-", ""));
		searchVO.setSearchEndde(searchVO.getSearchEndde().replace("-", ""));
		
		List<?> resultList = statsCstmrService.selectStatsCstmrAreaList(searchVO);
		map.put("dataList", resultList);
		map.put("template", "stats_cstmr_area_list.xlsx");
		map.put("fileName", "지역별분석");
		
		return new ModelAndView("commonExcelView", map);
	}
	
	/**
	 * Ez 포인트 사용분석
	 * @param searchVO
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/decms/shop/stats/cstmrEzpointManage.do")
	public String cstmrEzpointManage(@ModelAttribute("searchVO") StatsCstmrSearchVO searchVO, Model model) throws Exception {
		if(StringUtils.isEmpty(searchVO.getSearchBgnde())) {
			searchVO.setSearchBgnde(CommonUtils.getCurrentDateFormat("yyyy-MM-dd", -30));
		}
		
		if(StringUtils.isEmpty(searchVO.getSearchEndde())) {
			searchVO.setSearchEndde(CommonUtils.getCurrentDateFormat("yyyy-MM-dd"));
		}

		model.addAttribute("searchThisMonth01", CommonUtils.getCurrentDateFormat("yyyy-MM-dd","DAY","F",0));
		model.addAttribute("searchThisMonth02", CommonUtils.getCurrentDateFormat("yyyy-MM-dd","DAY","L",0));
		model.addAttribute("searchToday", CommonUtils.getCurrentDateFormat("yyyy-MM-dd"));
		model.addAttribute("searchDay1", CommonUtils.getCurrentDateFormat("yyyy-MM-dd","DAY",null,-1));
		model.addAttribute("searchDay3", CommonUtils.getCurrentDateFormat("yyyy-MM-dd","DAY",null,-3));
		model.addAttribute("searchDay7", CommonUtils.getCurrentDateFormat("yyyy-MM-dd","DAY",null,-7));
		model.addAttribute("searchDay10", CommonUtils.getCurrentDateFormat("yyyy-MM-dd","DAY",null,-10));
		model.addAttribute("searchDay20", CommonUtils.getCurrentDateFormat("yyyy-MM-dd","DAY",null,-20));
		model.addAttribute("searchDay30", CommonUtils.getCurrentDateFormat("yyyy-MM-dd","DAY",null,-30));
		
		return "modoo/cms/shop/stats/cstmr/cstmrEzpointManage";
	}
	
	/**
	 * Ez 포인트 사용분석 목록
	 * @param searchVO
	 * @return
	 */
	@RequestMapping(value = "/decms/shop/stats/cstmrEzpointList.json")
	@ResponseBody
	public JsonResult cstmrEzpointList(StatsCstmrSearchVO searchVO) {
		LoginVO user = (LoginVO)EgovUserDetailsHelper.getAuthenticatedUser();
		JsonResult jsonResult = new JsonResult();
		
		try {
			if(StringUtils.isEmpty(searchVO.getSearchBgnde())) {
				jsonResult.setSuccess(false);
				jsonResult.setMessage("시작일을 선택하세요");
			}else if(StringUtils.isEmpty(searchVO.getSearchEndde())) {
				jsonResult.setSuccess(false);
				jsonResult.setMessage("종료일을 선택하세요");
			}else {
				if(!EgovUserDetailsHelper.getAuthorities().contains("ROLE_EMPLOYEE")) {
					searchVO.setSearchCondition("CID");
					if(StringUtils.isEmpty(user.getCmpnyId())) {
						searchVO.setSearchKeyword("업체고유ID가 없음"); //검색이 안되게 하기위해
					}else {
						searchVO.setSearchKeyword(user.getCmpnyId());
					}
				}
				
				searchVO.setSearchBgnde(searchVO.getSearchBgnde().replace("-", ""));
				searchVO.setSearchEndde(searchVO.getSearchEndde().replace("-", ""));
				
				List<?> resultList = statsCstmrService.selectStatsCstmrEzpointList(searchVO);
				jsonResult.put("list", resultList);
				
				jsonResult.setSuccess(true);
			}
			
		}catch(Exception e) {
			loggerError(LOGGER, e);
			jsonResult.setSuccess(false);
			jsonResult.setMessage(egovMessageSource.getMessage("fail.common.select")); //조회에 실패하였습니다.
		}
		
		return jsonResult;
	}
	
	/**
	 * Ez 포인트 사용분석 목록 엑셀
	 * @param searchVO
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/decms/shop/stats/cstmrEzpointListExcel.do")
	public ModelAndView cstmrEzpointListExcel(@ModelAttribute("searchVO") StatsCstmrSearchVO searchVO, Model model) throws Exception {
		LoginVO user = (LoginVO)EgovUserDetailsHelper.getAuthenticatedUser();
		Map<String, Object> map = new HashMap<String, Object>();
		
		searchVO.setSearchBgnde(searchVO.getSearchBgnde().replace("-", ""));
		searchVO.setSearchEndde(searchVO.getSearchEndde().replace("-", ""));
		
		List<?> resultList = statsCstmrService.selectStatsCstmrEzpointList(searchVO);
		map.put("dataList", resultList);
		map.put("template", "stats_cstmr_ezpoint_list.xlsx");
		map.put("fileName", "Ez포인트분석");
		
		return new ModelAndView("commonExcelView", map);
	}
}
