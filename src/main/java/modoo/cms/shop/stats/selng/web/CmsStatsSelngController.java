package modoo.cms.shop.stats.selng.web;

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
import modoo.module.common.service.CommonDefaultSearchVO;
import modoo.module.common.service.JsonResult;
import modoo.module.common.util.CommonUtils;
import modoo.module.common.web.CommonDefaultController;
import modoo.module.shop.stats.selng.service.StatsSelngService;

/**
 * 매출분석
 * @author dymoon
 *
 */
@Controller
public class CmsStatsSelngController extends CommonDefaultController {

	private static final Logger LOGGER = LoggerFactory.getLogger(CmsStatsSelngController.class);
	
	@Resource(name = "statsSelngService")
	private StatsSelngService statsSelngService;

	/**
	 * 일별 매출
	 * @param searchVO
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/decms/shop/stats/selngDayManage.do")
	public String selngManage(@ModelAttribute("searchVO") CommonDefaultSearchVO searchVO, Model model) throws Exception {
		if(StringUtils.isEmpty(searchVO.getSearchBgnde())) {
			searchVO.setSearchBgnde(CommonUtils.getCurrentDateFormat("yyyy-MM-dd", -30));
		}
		
		if(StringUtils.isEmpty(searchVO.getSearchEndde())) {
			searchVO.setSearchEndde(CommonUtils.getCurrentDateFormat("yyyy-MM-dd"));
		}
		
		model.addAttribute("searchThisMonth01", CommonUtils.getCurrentDateFormat("yyyy-MM-dd","DAY","F",0));
		model.addAttribute("searchThisMonth02", CommonUtils.getCurrentDateFormat("yyyy-MM-dd","DAY","L",0));
		model.addAttribute("searchToday", CommonUtils.getCurrentDateFormat("yyyy-MM-dd"));
		model.addAttribute("searchYesterday", CommonUtils.getCurrentDateFormat("yyyy-MM-dd",-1));
		model.addAttribute("searchDay3", CommonUtils.getCurrentDateFormat("yyyy-MM-dd",-3));
		model.addAttribute("searchDay7", CommonUtils.getCurrentDateFormat("yyyy-MM-dd",-7));
		model.addAttribute("searchDay10", CommonUtils.getCurrentDateFormat("yyyy-MM-dd",-10));
		model.addAttribute("searchDay20", CommonUtils.getCurrentDateFormat("yyyy-MM-dd",-20));
		model.addAttribute("searchDay30", CommonUtils.getCurrentDateFormat("yyyy-MM-dd",-30));
		return "modoo/cms/shop/stats/selng/selngDayManage";
	}
	
	/**
	 * 일별 매출 목록
	 * @param searchVO
	 * @return
	 */
	@RequestMapping(value = "/decms/shop/stats/selngDayList.json")
	@ResponseBody 
	public JsonResult selngDayList(CommonDefaultSearchVO searchVO) {
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
				
				List<?> resultList = statsSelngService.selectStatsSelngDayList(searchVO);
				jsonResult.put("list", resultList);
				
				List<?> resultSelngList = statsSelngService.selectStateSelngList(searchVO);
				jsonResult.put("resultSelngList", resultSelngList);
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
	 * 일별 매출 엑셀 다운로드
	 * @param searchVO
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/decms/shop/stats/selngDayListExcel.do")
	public ModelAndView selngDayListExcel(CommonDefaultSearchVO searchVO, Model model) throws Exception {
		LoginVO user = (LoginVO)EgovUserDetailsHelper.getAuthenticatedUser();
		Map<String, Object> map = new HashMap<String, Object>();
		
		searchVO.setSearchBgnde(searchVO.getSearchBgnde().replace("-", ""));
		searchVO.setSearchEndde(searchVO.getSearchEndde().replace("-", ""));
		
		if(!EgovUserDetailsHelper.getAuthorities().contains("ROLE_EMPLOYEE")) {
			searchVO.setSearchCondition("CID");
			if(StringUtils.isEmpty(user.getCmpnyId())) {
				searchVO.setSearchKeyword("업체고유ID가 없음"); //검색이 안되게 하기위해
			}else {
				searchVO.setSearchKeyword(user.getCmpnyId());
			}
		}
		
		List<?> resultList = statsSelngService.selectStateSelngList(searchVO);
		
		map.put("dataList", resultList);
		map.put("template", "stats_selng_day_list.xlsx");
		map.put("fileName", "일별매출통계");
		
		return new ModelAndView("commonExcelView", map);
	}
	
	/**
	 * 요일/시간대별 매출
	 * @param searchVO
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/decms/shop/stats/selngWeekManage.do")
	public String selngWeekManage(@ModelAttribute("searchVO") CommonDefaultSearchVO searchVO, Model model) throws Exception {
		if(StringUtils.isEmpty(searchVO.getSearchBgnde())) {
			searchVO.setSearchBgnde(CommonUtils.getCurrentDateFormat("yyyy-MM-dd", -30));
		}
		
		if(StringUtils.isEmpty(searchVO.getSearchEndde())) {
			searchVO.setSearchEndde(CommonUtils.getCurrentDateFormat("yyyy-MM-dd"));
		}
		
		model.addAttribute("searchToday", CommonUtils.getCurrentDateFormat("yyyy-MM-dd"));
		model.addAttribute("searchMonth6", CommonUtils.getCurrentDateFormat("yyyy-MM-dd","MONTH",null,-6));
		model.addAttribute("searchMonth3", CommonUtils.getCurrentDateFormat("yyyy-MM-dd","MONTH",null,-3));
		model.addAttribute("searchMonth2", CommonUtils.getCurrentDateFormat("yyyy-MM-dd","MONTH",null,-2));
		model.addAttribute("searchMonth1", CommonUtils.getCurrentDateFormat("yyyy-MM-dd","MONTH",null,-1));
		model.addAttribute("searchThisMonth01", CommonUtils.getCurrentDateFormat("yyyy-MM-dd","DAY","F",0));
		model.addAttribute("searchThisMonth02", CommonUtils.getCurrentDateFormat("yyyy-MM-dd","DAY","L",0));
		
		return "modoo/cms/shop/stats/selng/selngWeekManage";
	}
	
	/**
	 * 요일/시간대별 매출 목록
	 * @param searchVO
	 * @return
	 */
	@RequestMapping(value = "/decms/shop/stats/selngWeekList.json")
	@ResponseBody 
	public JsonResult selngWeekList(CommonDefaultSearchVO searchVO) {
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
				
				List<?> resultList = statsSelngService.selectStateSelngWeekList(searchVO);
				jsonResult.put("list", resultList);
				
				List<?> resultHourList = statsSelngService.selectStateSelngHourList(searchVO);
				jsonResult.put("resultHourList", resultHourList);
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
	 * 주별 매출
	 * @param searchVO
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/decms/shop/stats/selngMonthWeekManage.do")
	public String selngMonthWeekManage(@ModelAttribute("searchVO") CommonDefaultSearchVO searchVO, Model model) throws Exception {
		if(StringUtils.isEmpty(searchVO.getSearchBgnde())) {
			//searchVO.setSearchBgnde(CommonUtils.getCurrentDateFormat("yyyy-MM-dd", -30));
			searchVO.setSearchBgnde( CommonUtils.getWeekDay("yyyy-MM-dd", "MON", -4));
		}
		
		if(StringUtils.isEmpty(searchVO.getSearchEndde())) {
			searchVO.setSearchEndde(CommonUtils.getCurrentDateFormat("yyyy-MM-dd"));
		}
		
		model.addAttribute("searchWeek11", CommonUtils.getWeekDay("yyyy-MM-dd", "MON", -1));
		model.addAttribute("searchWeek12", CommonUtils.getWeekDay("yyyy-MM-dd", "SUN", 0));
		model.addAttribute("searchWeek21", CommonUtils.getWeekDay("yyyy-MM-dd", "MON", -2));
		model.addAttribute("searchWeek22", CommonUtils.getWeekDay("yyyy-MM-dd", "SUN", 0));
		model.addAttribute("searchWeek31", CommonUtils.getWeekDay("yyyy-MM-dd", "MON", -3));
		model.addAttribute("searchWeek32", CommonUtils.getWeekDay("yyyy-MM-dd", "SUN", 0));
		model.addAttribute("searchWeek41", CommonUtils.getWeekDay("yyyy-MM-dd", "MON", -4));
		model.addAttribute("searchWeek42", CommonUtils.getWeekDay("yyyy-MM-dd", "SUN", 0));

		return "modoo/cms/shop/stats/selng/selngMonthWeekManage";
	}
	
	/**
	 * 주별 주문건수 목록
	 * @param searchVO
	 * @return
	 */
	@RequestMapping(value = "/decms/shop/stats/selngMonthWeekList.json")
	@ResponseBody 
	public JsonResult selngMonthWeekList(CommonDefaultSearchVO searchVO) {
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
				
				List<?> resultList = statsSelngService.selectStateSelngMonthWeekList(searchVO);
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
	 * 주별 매출 엑셀 다운로드
	 * @param searchVO
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/decms/shop/stats/selngMonthWeekListExcel.do")
	public ModelAndView selngMonthWeekListExcel(CommonDefaultSearchVO searchVO, Model model) throws Exception {
		LoginVO user = (LoginVO)EgovUserDetailsHelper.getAuthenticatedUser();
		Map<String, Object> map = new HashMap<String, Object>();
		
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
		
		List<?> resultList = statsSelngService.selectStateSelngMonthWeekList(searchVO);
		
		map.put("dataList", resultList);
		map.put("template", "stats_selng_week_list.xlsx");
		map.put("fileName", "주별매출통계");
		
		return new ModelAndView("commonExcelView", map);
	}
	
	
	/**
	 * 월별 매출 
	 * @param searchVO
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/decms/shop/stats/selngMonthManage.do")
	public String selngMonthManage(@ModelAttribute("searchVO") CommonDefaultSearchVO searchVO, Model model) throws Exception {
		if(StringUtils.isEmpty(searchVO.getSearchBgnde())) {
			model.addAttribute("searchBgnYear", CommonUtils.getCurrentDateFormat("yyyy","MONTH",null,-6));
			model.addAttribute("searchBgnMonth", CommonUtils.getCurrentDateFormat("MM","MONTH",null,-6));
		}
		
		if(StringUtils.isEmpty(searchVO.getSearchEndde())) {
			model.addAttribute("searchEndYear", CommonUtils.getCurrentDateFormat("yyyy"));
			model.addAttribute("searchEndMonth", CommonUtils.getCurrentDateFormat("MM"));
		}
		
		String currYear = CommonUtils.getCurrentDateFormat("yyyy");
		model.addAttribute("currYear", currYear);

		return "modoo/cms/shop/stats/selng/selngMonthManage";
	}
	
	/**
	 * 월별 매출 건수 목록
	 * @param searchBgnYear
	 * @param searchBgnMonth
	 * @param searchEndYear
	 * @param searchEndMonth
	 * @param searchVO
	 * @return
	 */
	@RequestMapping(value = "/decms/shop/stats/selngMonthList.json")
	@ResponseBody 
	public JsonResult selngMonthList(String searchBgnYear, String searchBgnMonth,
			String searchEndYear, String searchEndMonth, CommonDefaultSearchVO searchVO) {
		LoginVO user = (LoginVO)EgovUserDetailsHelper.getAuthenticatedUser();
		JsonResult jsonResult = new JsonResult();
		
		try {
			if(StringUtils.isEmpty(searchBgnYear)) {
				jsonResult.setSuccess(false);
				jsonResult.setMessage("시작년도를 입력하세요!");
			}else if(StringUtils.isEmpty(searchBgnMonth)) {
				jsonResult.setSuccess(false);
				jsonResult.setMessage("시작월을 입력하세요!");
			}else if(StringUtils.isEmpty(searchEndYear)) {
				jsonResult.setSuccess(false);
				jsonResult.setMessage("종료년도를 선택하세요");
			}else if(StringUtils.isEmpty(searchEndMonth)) {
				jsonResult.setSuccess(false);
				jsonResult.setMessage("종료월을 선택하세요");
			}else {
				if(!EgovUserDetailsHelper.getAuthorities().contains("ROLE_EMPLOYEE")) {
					searchVO.setSearchCondition("CID");
					if(StringUtils.isEmpty(user.getCmpnyId())) {
						searchVO.setSearchKeyword("업체고유ID가 없음"); //검색이 안되게 하기위해
					}else {
						searchVO.setSearchKeyword(user.getCmpnyId());
					}
				}
				
				searchVO.setSearchBgnde(searchBgnYear + searchBgnMonth + "01");
				searchVO.setSearchEndde(searchEndYear + searchEndMonth + "01"); //쿼리에서 마지막일 계산
				
				List<?> resultList = statsSelngService.selectStateSelngMonthList(searchVO);
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
	 * 월별 매출 엑셀 다운로드
	 * @param searchVO
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/decms/shop/stats/selngMonthListExcel.do")
	public ModelAndView selngMonthListExcel(String searchBgnYear, String searchBgnMonth,
	String searchEndYear, String searchEndMonth, CommonDefaultSearchVO searchVO, Model model) throws Exception {
		LoginVO user = (LoginVO)EgovUserDetailsHelper.getAuthenticatedUser();
		Map<String, Object> map = new HashMap<String, Object>();
		
		if(StringUtils.isEmpty(searchBgnYear)) {
			return null;
		}else if(StringUtils.isEmpty(searchBgnMonth)) {
			return null;
		}else if(StringUtils.isEmpty(searchEndYear)) {
			return null;
		}else if(StringUtils.isEmpty(searchEndMonth)) {
			return null;
		}else {
			if(!EgovUserDetailsHelper.getAuthorities().contains("ROLE_EMPLOYEE")) {
				searchVO.setSearchCondition("CID");
				if(StringUtils.isEmpty(user.getCmpnyId())) {
					searchVO.setSearchKeyword("업체고유ID가 없음"); //검색이 안되게 하기위해
				}else {
					searchVO.setSearchKeyword(user.getCmpnyId());
				}
			}
			
			searchVO.setSearchBgnde(searchBgnYear + searchBgnMonth + "01");
			searchVO.setSearchEndde(searchEndYear + searchEndMonth + "01"); //쿼리에서 마지막일 계산
			
			List<?> resultList = statsSelngService.selectStateSelngMonthList(searchVO);

			map.put("dataList", resultList);
			map.put("template", "stats_selng_month_list.xlsx");
			map.put("fileName", "주별매출통계");
			
			return new ModelAndView("commonExcelView", map);
		}
	}
	
	/**
	 * 연령별 매출
	 * @param searchVO
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/decms/shop/stats/selngAgeManage.do")
	public String selngAgeManage(@ModelAttribute("searchVO") CommonDefaultSearchVO searchVO, Model model) throws Exception {
		if(StringUtils.isEmpty(searchVO.getSearchBgnde())) {
			searchVO.setSearchBgnde(CommonUtils.getCurrentDateFormat("yyyy-MM-dd", -30));
		}
		
		if(StringUtils.isEmpty(searchVO.getSearchEndde())) {
			searchVO.setSearchEndde(CommonUtils.getCurrentDateFormat("yyyy-MM-dd"));
		}
		
		model.addAttribute("searchToday", CommonUtils.getCurrentDateFormat("yyyy-MM-dd"));
		model.addAttribute("searchMonth11", CommonUtils.getCurrentDateFormat("yyyy-MM-dd","MONTH","F",-1));
		model.addAttribute("searchMonth12", CommonUtils.getCurrentDateFormat("yyyy-MM-dd","MONTH","L",-1));
		model.addAttribute("searchThisMonth01", CommonUtils.getCurrentDateFormat("yyyy-MM-dd","DAY","F",0));
		model.addAttribute("searchWeek11", CommonUtils.getWeekDay("yyyy-MM-dd", "MON", 0));
		model.addAttribute("searchWeek12", CommonUtils.getWeekDay("yyyy-MM-dd", "SUN", 0));
		model.addAttribute("searchThreeDay", CommonUtils.getCurrentDateFormat("yyyy-MM-dd","DAY",null,-3));

		return "modoo/cms/shop/stats/selng/selngAgeManage";
	}
	
	/**
	 * 연령별 매출 목록
	 * @param searchVO
	 * @return
	 */
	@RequestMapping(value = "/decms/shop/stats/selngAgeList.json")
	@ResponseBody 
	public JsonResult selngAgeList(CommonDefaultSearchVO searchVO) {
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
				
				List<?> resultList = statsSelngService.selectStateSelngAgeList(searchVO);
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
	 * 연령별 매출 엑셀 다운로드
	 * @param searchVO
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/decms/shop/stats/selngAgeListExcel.do")
	public ModelAndView selngAgeListExcel(CommonDefaultSearchVO searchVO, Model model) throws Exception {
		LoginVO user = (LoginVO)EgovUserDetailsHelper.getAuthenticatedUser();
		Map<String, Object> map = new HashMap<String, Object>();
		
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
		
		List<?> resultList = statsSelngService.selectStateSelngAgeList(searchVO);
		
		map.put("dataList", resultList);
		map.put("template", "stats_selng_age_list.xlsx");
		map.put("fileName", "연령별매출통계");
		
		return new ModelAndView("commonExcelView", map);
	}
	
	/**
	 * 성별 매출
	 * @param searchVO
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/decms/shop/stats/selngSexdstnManage.do")
	public String selngSexdstnManage(@ModelAttribute("searchVO") CommonDefaultSearchVO searchVO, Model model) throws Exception {
		if(StringUtils.isEmpty(searchVO.getSearchBgnde())) {
			searchVO.setSearchBgnde(CommonUtils.getCurrentDateFormat("yyyy-MM-dd", -30));
		}
		
		if(StringUtils.isEmpty(searchVO.getSearchEndde())) {
			searchVO.setSearchEndde(CommonUtils.getCurrentDateFormat("yyyy-MM-dd"));
		}
		
		model.addAttribute("searchToday", CommonUtils.getCurrentDateFormat("yyyy-MM-dd"));
		model.addAttribute("searchMonth11", CommonUtils.getCurrentDateFormat("yyyy-MM-dd","MONTH","F",-1));
		model.addAttribute("searchMonth12", CommonUtils.getCurrentDateFormat("yyyy-MM-dd","MONTH","L",-1));
		model.addAttribute("searchThisMonth01", CommonUtils.getCurrentDateFormat("yyyy-MM-dd","DAY","F",0));
		model.addAttribute("searchWeek11", CommonUtils.getWeekDay("yyyy-MM-dd", "MON", 0));
		model.addAttribute("searchWeek12", CommonUtils.getWeekDay("yyyy-MM-dd", "SUN", 0));
		model.addAttribute("searchThreeDay", CommonUtils.getCurrentDateFormat("yyyy-MM-dd","DAY",null,-3));

		return "modoo/cms/shop/stats/selng/selngSexdstnManage";
	}
	
	/**
	 * 성별 매출 목록
	 * @param searchVO
	 * @return
	 */
	@RequestMapping(value = "/decms/shop/stats/selngSexdstnList.json")
	@ResponseBody 
	public JsonResult selngSexdstnList(CommonDefaultSearchVO searchVO) {
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
				
				List<?> resultList = statsSelngService.selectStateSelngSexdstnList(searchVO);
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
	 * 성별매출 엑셀 다운로드
	 * @param searchVO
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/decms/shop/stats/selngSexdstnListExcel.do")
	public ModelAndView selngSexdstnListExcel(CommonDefaultSearchVO searchVO, Model model) throws Exception {
		LoginVO user = (LoginVO)EgovUserDetailsHelper.getAuthenticatedUser();
		Map<String, Object> map = new HashMap<String, Object>();
		
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
		
		List<?> resultList = statsSelngService.selectStateSelngAgeList(searchVO);
		
		map.put("dataList", resultList);
		map.put("template", "stats_selng_sexdstn_list.xlsx");
		map.put("fileName", "성별매출통계");
		
		return new ModelAndView("commonExcelView", map);
	}
}
