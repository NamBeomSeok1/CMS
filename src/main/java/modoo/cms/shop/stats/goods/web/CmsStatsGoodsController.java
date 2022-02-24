package modoo.cms.shop.stats.goods.web;

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
import modoo.module.shop.goods.ctgry.service.GoodsCtgryService;
import modoo.module.shop.goods.ctgry.service.GoodsCtgryVO;
import modoo.module.shop.stats.goods.service.StatsGoodsSearchVO;
import modoo.module.shop.stats.goods.service.StatsGoodsService;

/**
 * 상품분석
 * @author dymoon
 *
 */
@Controller
public class CmsStatsGoodsController extends CommonDefaultController {

	private static final Logger LOGGER = LoggerFactory.getLogger(CmsStatsGoodsController.class);
	
	private static final String ROOT_CTGRY_ID = "GCTGRY_0000000000000"; //최상위 카타고리ID
	
	@Resource(name = "statsGoodsService")
	private StatsGoodsService statsGoodsService;
	
	@Resource(name = "goodsCtgryService")
	private GoodsCtgryService goodsCtgryService;
	
	/**
	 * 상품별 매출
	 * @param searchVO
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/decms/shop/stats/goodsSelngManage.do")
	public String goodsSelngManage(@ModelAttribute("searchVO") StatsGoodsSearchVO searchVO, Model model) throws Exception {
		if(StringUtils.isEmpty(searchVO.getSearchBgnde())) {
			searchVO.setSearchBgnde(CommonUtils.getCurrentDateFormat("yyyy-MM-dd", -30));
		}
		
		if(StringUtils.isEmpty(searchVO.getSearchEndde())) {
			searchVO.setSearchEndde(CommonUtils.getCurrentDateFormat("yyyy-MM-dd"));
		}
		
		GoodsCtgryVO goodsCtgry = new GoodsCtgryVO();
		goodsCtgry.setSearchUpperGoodsCtgryId(ROOT_CTGRY_ID);
		List<?> cate1List = goodsCtgryService.selectGoodsCtgryList(goodsCtgry);
		model.addAttribute("cate1List", cate1List);
		
		model.addAttribute("searchToday", CommonUtils.getCurrentDateFormat("yyyy-MM-dd"));
		model.addAttribute("searchMonth11", CommonUtils.getCurrentDateFormat("yyyy-MM-dd","MONTH","F",-1));
		model.addAttribute("searchMonth12", CommonUtils.getCurrentDateFormat("yyyy-MM-dd","MONTH","L",-1));
		model.addAttribute("searchThisMonth01", CommonUtils.getCurrentDateFormat("yyyy-MM-dd","DAY","F",0));
		model.addAttribute("searchWeek11", CommonUtils.getWeekDay("yyyy-MM-dd", "MON", 0));
		model.addAttribute("searchWeek12", CommonUtils.getWeekDay("yyyy-MM-dd", "SUN", 0));
		model.addAttribute("searchThreeDay", CommonUtils.getCurrentDateFormat("yyyy-MM-dd","DAY",null,-3));
		return "modoo/cms/shop/stats/goods/goodsSelngManage";
	}
	
	/**
	 * 상품별 매출 목록
	 * @param searchVO
	 * @return
	 */
	@RequestMapping(value = "/decms/shop/stats/goodsSelngList.json")
	@ResponseBody
	public JsonResult goodsSelngList(StatsGoodsSearchVO searchVO) {
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
				
				if(StringUtils.isNotEmpty(searchVO.getSearchCateCode3())) {
					searchVO.setSearchGoodsCtgryId(searchVO.getSearchCateCode3());
				}else if(StringUtils.isNotEmpty(searchVO.getSearchCateCode2())) {
					searchVO.setSearchGoodsCtgryId(searchVO.getSearchCateCode2());
				}else if(StringUtils.isNotEmpty(searchVO.getSearchCateCode1())) {
					searchVO.setSearchGoodsCtgryId(searchVO.getSearchCateCode1());
				}
				
				searchVO.setSearchBgnde(searchVO.getSearchBgnde().replace("-", ""));
				searchVO.setSearchEndde(searchVO.getSearchEndde().replace("-", ""));
				
				List<?> resultList = statsGoodsService.selectStatsGoodsSelngList(searchVO);
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
	 * 상품별 매출 목록 엑셀
	 * @param searchVO
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/decms/shop/stats/goodsSelngListExcel.do")
	public ModelAndView selngSexdstnListExcel(StatsGoodsSearchVO searchVO, Model model) throws Exception {
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

		if(StringUtils.isNotEmpty(searchVO.getSearchCateCode3())) {
			searchVO.setSearchGoodsCtgryId(searchVO.getSearchCateCode3());
		}else if(StringUtils.isNotEmpty(searchVO.getSearchCateCode2())) {
			searchVO.setSearchGoodsCtgryId(searchVO.getSearchCateCode2());
		}else if(StringUtils.isNotEmpty(searchVO.getSearchCateCode1())) {
			searchVO.setSearchGoodsCtgryId(searchVO.getSearchCateCode1());
		}
		
		searchVO.setSearchBgnde(searchVO.getSearchBgnde().replace("-", ""));
		searchVO.setSearchEndde(searchVO.getSearchEndde().replace("-", ""));
		
		List<?> resultList = statsGoodsService.selectStatsGoodsSelngList(searchVO);
		
		map.put("dataList", resultList);
		map.put("template", "stats_goods_selng_list.xlsx");
		map.put("fileName", "상품별별매출통계");
		
		return new ModelAndView("commonExcelView", map);
	}
	
	
	/**
	 * 입점사매출순위
	 * @param searchVO
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/decms/shop/stats/cmpnySelngManage.do")
	public String cmpnySelngManage(@ModelAttribute("searchVO") StatsGoodsSearchVO searchVO, Model model) throws Exception {
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
		return "modoo/cms/shop/stats/goods/cmpnySelngManage";
	}
	
	/**
	 * 업체매출순위 목록
	 * @param searchVO
	 * @return
	 */
	@RequestMapping(value = "/decms/shop/stats/cmpnySelngList.json")
	@ResponseBody
	public JsonResult cmpnySelngList(StatsGoodsSearchVO searchVO) {
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
				
				List<?> resultList = statsGoodsService.selectStatsCmpnySelngList(searchVO);
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
	 * 만족도순위
	 * @param searchVO
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/decms/shop/stats/goodsStsfdgSelngManage.do")
	public String goodsStsfdgSelngManage(@ModelAttribute("searchVO") StatsGoodsSearchVO searchVO, Model model) throws Exception {
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
		return "modoo/cms/shop/stats/goods/goodsStsfdgSelngManage";
	}
	
	/**
	 * 만족도순위 목록
	 * @param searchVO
	 * @return
	 */
	@RequestMapping(value = "/decms/shop/stats/goodsStsfdgList.json")
	@ResponseBody
	public JsonResult goodsStsfdgList(StatsGoodsSearchVO searchVO) {
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
				
				List<?> resultList = statsGoodsService.selectStatsGoodsStsfdgSelngList(searchVO);
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
	 * 만족도순위 엑셀 다운로드
	 * @param searchVO
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/decms/shop/stats/goodsStsfdgListExcel.do")
	public ModelAndView goodsStsfdgListExcel(StatsGoodsSearchVO searchVO, Model model) throws Exception {
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
		
		List<?> resultList = statsGoodsService.selectStatsGoodsStsfdgSelngList(searchVO);
		
		map.put("dataList", resultList);
		map.put("template", "stats_score_selng_list.xlsx");
		map.put("fileName", "상품별만족도");
		
		return new ModelAndView("commonExcelView", map);
	}
}
