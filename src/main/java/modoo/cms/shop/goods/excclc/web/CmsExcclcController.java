package modoo.cms.shop.goods.excclc.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import egovframework.com.cmm.ComDefaultCodeVO;
import egovframework.com.cmm.LoginVO;
import egovframework.com.cmm.service.EgovCmmUseService;
import egovframework.com.cmm.util.EgovUserDetailsHelper;
import egovframework.rte.psl.dataaccess.util.EgovMap;
import egovframework.rte.ptl.mvc.tags.ui.pagination.PaginationInfo;
import modoo.module.common.service.JsonResult;
import modoo.module.common.util.CommonUtils;
import modoo.module.common.web.CommonDefaultController;
import modoo.module.shop.cmpny.service.CmpnyService;
import modoo.module.shop.cmpny.service.CmpnyVO;
import modoo.module.shop.goods.ctgry.service.GoodsCtgryService;
import modoo.module.shop.goods.ctgry.service.GoodsCtgryVO;
import modoo.module.shop.goods.excclc.service.ExcclcService;
import modoo.module.shop.goods.excclc.service.ExcclcVO;

@Controller
public class CmsExcclcController extends CommonDefaultController {

	private static final Logger LOGGER = LoggerFactory.getLogger(CmsExcclcController.class);
	
	private static final String ROOT_CTGRY_ID = "GCTGRY_0000000000000"; //최상위 카타고리ID
	private static final String SEARC_MIN_YEAR = "2020"; //검색 최소값
	
	/** EgovCmmUseService */
	@Resource(name = "EgovCmmUseService")
	private EgovCmmUseService cmmUseService;
	
	@Resource(name = "goodsCtgryService")
	private GoodsCtgryService goodsCtgryService;
	
	@Resource(name = "excclcService")
	private ExcclcService excclcService;
	
	@Resource(name = "cmpnyService")
	private CmpnyService cmpnyService;
	
	/**
	 * 이지웰 정산관리
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/decms/shop/goods/excclcHedofcManage.do")
	public String excclcHedofcManage(@ModelAttribute("searchVO") ExcclcVO searchVO, Model model) throws Exception {
		
		GoodsCtgryVO goodsCtgry = new GoodsCtgryVO();
		goodsCtgry.setSearchUpperGoodsCtgryId(ROOT_CTGRY_ID);
		List<?> cate1List = goodsCtgryService.selectGoodsCtgryList(goodsCtgry);
		model.addAttribute("cate1List", cate1List);
		
		//정산상태코드
		ComDefaultCodeVO codeVO = new ComDefaultCodeVO();
		codeVO.setCodeId("CMS031");
		List<?> excclcSttusCodeList = cmmUseService.selectCmmCodeDetail(codeVO);
		model.addAttribute("excclcSttusCodeList", excclcSttusCodeList);
		
		String currYear = CommonUtils.getCurrentDateFormat("yyyy");
		model.addAttribute("maxYear", currYear);
		model.addAttribute("minYear", SEARC_MIN_YEAR); //2020
		if(StringUtils.isEmpty(searchVO.getSearchExcclcYear())) {
			searchVO.setSearchExcclcYear(currYear);
		}
		if(StringUtils.isEmpty(searchVO.getSearchExcclcMonth())) {
			searchVO.setSearchExcclcMonth(CommonUtils.getCurrentDateFormat("M"));
		}
		
		return "modoo/cms/shop/goods/excclc/excclcHedofcManage";
	}
	
	
	/**
	 * 이지웰 정산 목록
	 * @param searchVO
	 * @return
	 */
	@RequestMapping(value = "/decms/shop/goods/excclcHedofcList.json")
	@ResponseBody
	public JsonResult excclcHedofcList(ExcclcVO searchVO) {
		JsonResult jsonResult = new JsonResult();
		
		try {
			PaginationInfo paginationInfo = new PaginationInfo();
			if(searchVO.getPageUnit() <= 10) {
				searchVO.setPageUnit(propertiesService.getInt("gridPageUnit"));
			}
			this.setPagination(paginationInfo, searchVO);
			
			List<?> resultList = excclcService.selectEzwelExcclcList(searchVO);
			jsonResult.put("list", resultList);
			
			EgovMap map = excclcService.selectEzwelExcclcListTotalSum(searchVO);
			jsonResult.put("total", map);
			int totalRecordCount = ((java.math.BigDecimal) map.get("cnt")).intValue();
			//int totalRecordCount = excclcService.selectEzwelExcclcListCnt(searchVO);
			paginationInfo.setTotalRecordCount(totalRecordCount);
			jsonResult.put("paginationInfo", paginationInfo);
			
			jsonResult.setSuccess(true);
			
		}catch(Exception e) {
			loggerError(LOGGER, e);
			jsonResult.setSuccess(false);
			jsonResult.setMessage(egovMessageSource.getMessage("fail.common.select")); //조회에 실패하였습니다.
		}
		
		return jsonResult;
	}
	
	/**
	 * 이지웰 정산 목록 엑셀 다운로드
	 * @param menuId
	 * @param searchVO
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/decms/shop/goods/ezwelExcclcExcelList.do")
	public ModelAndView ezwelExcclcExcelList(@RequestParam("menuId") String menuId, ExcclcVO searchVO) throws Exception {
		LoginVO user = (LoginVO)EgovUserDetailsHelper.getAuthenticatedUser();
		Map<String, Object> map = new HashMap<String, Object>();
		String fileName = "이지웰정산목록";
		
		
		searchVO.setFirstIndex(0);
		searchVO.setRecordCountPerPage(Integer.MAX_VALUE);
		List<?> resultList = excclcService.selectEzwelExcclcList(searchVO);
		
		map.put("dataList", resultList);
		map.put("template", "ezwel_excclc_list.xlsx");
		map.put("fileName", fileName);
		
		return new ModelAndView("commonExcelView", map);
	}
	
	
	/**
	 * CP 정산관리
	 * @param searchVO
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = {"/decms/shop/goods/excclcManage.do","/decms/shop/goods/excclcCpManage.do","/decms/shop/goods/cpExcclcManage.do"})
	public String excclcCpManage(@ModelAttribute("searchVO") ExcclcVO searchVO,
			HttpServletRequest request, Model model) throws Exception {
		LoginVO user = (LoginVO)EgovUserDetailsHelper.getAuthenticatedUser();
		
		if(request.getServletPath().equals("/decms/shop/goods/cpExcclcManage.do")) {
			CmpnyVO cmpny = new CmpnyVO();
			cmpny.setCmpnyId(user.getCmpnyId());
			cmpny = cmpnyService.selectCmpny(cmpny);
			model.addAttribute("cmpnyNm", cmpny.getCmpnyNm());
			
			model.addAttribute("EXCCLC_MODE", "CP");
		}else {
			if(!EgovUserDetailsHelper.getAuthorities().contains("ROLE_EMPLOYEE")) {
				return "redirect:/decms/index.do";
			}
			model.addAttribute("EXCCLC_MODE", "MNG");
		}
		
		//if(StringUtils.isEmpty(searchVO.getSearchPrtnrId())) {
		//	searchVO.setSearchPrtnrId("PRTNR_0001"); //기본 B2B 검색
		//}
		
		//정산상태코드
		ComDefaultCodeVO codeVO = new ComDefaultCodeVO();
		codeVO.setCodeId("CMS031"); //CP 정산상태코드
		List<?> excclcSttusCodeList = cmmUseService.selectCmmCodeDetail(codeVO);
		model.addAttribute("excclcSttusCodeList", excclcSttusCodeList);
		
		String currYear = CommonUtils.getCurrentDateFormat("yyyy");
		model.addAttribute("maxYear", currYear);
		model.addAttribute("minYear", SEARC_MIN_YEAR); //2020
		if(StringUtils.isEmpty(searchVO.getSearchExcclcYear())) {
			searchVO.setSearchExcclcYear(currYear);
		}
		if(StringUtils.isEmpty(searchVO.getSearchExcclcMonth())) {
			searchVO.setSearchExcclcMonth(CommonUtils.getCurrentDateFormat("M"));
		}
		
		
		
		return "modoo/cms/shop/goods/excclc/excclcCpManage";
	}
	
	/**
	 * CP 정산 목록
	 * @param searchVO
	 * @return
	 */
	@RequestMapping(value = "/decms/shop/goods/excclcCpList.json")
	@ResponseBody
	public JsonResult excclcCpList(ExcclcVO searchVO) {
		JsonResult jsonResult = new JsonResult();
		LoginVO user = (LoginVO)EgovUserDetailsHelper.getAuthenticatedUser();

		try {
			//모드에 따라 CP에게 보여질 데이터를 검색한다. 
			if(!EgovUserDetailsHelper.getAuthorities().contains("ROLE_EMPLOYEE")) {
				searchVO.setSearchMode("CP");
				searchVO.setSearchCmpnyId(user.getCmpnyId());
			}else {
				searchVO.setSearchMode("MNG");
			}
			
			PaginationInfo paginationInfo = new PaginationInfo();
			if(searchVO.getPageUnit() <= 10) {
				searchVO.setPageUnit(propertiesService.getInt("gridPageUnit"));
			}
			this.setPagination(paginationInfo, searchVO);
			
			List<?> resultList = excclcService.selectExcclcList(searchVO);
			jsonResult.put("list", resultList);
			
			int totalRecordCount = excclcService.selectExcclcListCnt(searchVO);
			paginationInfo.setTotalRecordCount(totalRecordCount);
			jsonResult.put("paginationInfo", paginationInfo);
			
			//정산목록 계
			EgovMap totSum = excclcService.selectExcclcListTotalSum(searchVO);
			jsonResult.put("totSum", totSum);

			jsonResult.setSuccess(true);
			
		}catch(Exception e) {
			loggerError(LOGGER, e);
			jsonResult.setSuccess(false);
			jsonResult.setMessage(egovMessageSource.getMessage("fail.common.select")); //조회에 실패하였습니다.
		}
		
		return jsonResult;
	}
	
	/**
	 * CP 정산 상태 변경
	 * @param orderSetleNoList
	 * @return
	 */
	@RequestMapping(value = "/decms/shop/goods/saveExcclcCp.json")
	@ResponseBody
	public JsonResult saveExcclcCp(@RequestBody ExcclcVO excclc) {
		JsonResult jsonResult = new JsonResult();
		LoginVO user = (LoginVO)EgovUserDetailsHelper.getAuthenticatedUser();
		
		try {
			if(!EgovUserDetailsHelper.getAuthorities().contains("ROLE_EMPLOYEE")) {
				jsonResult.setSuccess(false);
				jsonResult.setMessage("권한이 없습니다.");
			}else if(excclc.getOrderSetleNoList() == null || excclc.getOrderSetleNoList().size() == 0) {
				jsonResult.setSuccess(false);
				jsonResult.setMessage("목록을 선택하세요!");
			}else {
			
				String noStr = "";
				for(Integer no : excclc.getOrderSetleNoList()) {
					noStr += no.toString() + ",";
				}
				
				excclcService.updateExcclcSttus(excclc);
				
				String message = "정산 처리되었습니다.";
				if("CPE02".equals(excclc.getExcclcSttusCode())) {
					message = "정산보류 처리되었습니다.";
				}

				LOGGER.info("정산처리자 : " + user.getId() + " [" + noStr + "] " + message);

				jsonResult.setMessage(message);
				jsonResult.setSuccess(true);
			}
		}catch(Exception e) {
			loggerError(LOGGER, e);
			jsonResult.setSuccess(false);
			jsonResult.setMessage("정산 상태 변경에 실패했습니다."); 
		}
		
		return jsonResult;
	}

	/**
	 * 정산예상일정 수정
	 * @param excclc
	 * @return
	 */
	@RequestMapping(value = "/decms/shop/goods/saveExcclcPrarnde.json")
	@ResponseBody
	public JsonResult saveExcclcPrarnde(@RequestBody ExcclcVO excclc) {
		JsonResult jsonResult = new JsonResult();
		LoginVO user = (LoginVO)EgovUserDetailsHelper.getAuthenticatedUser();
		
		try {
			if(!EgovUserDetailsHelper.getAuthorities().contains("ROLE_EMPLOYEE")) {
				jsonResult.setSuccess(false);
				jsonResult.setMessage("권한이 없습니다.");
			}else if(excclc.getOrderSetleNo() == null) {
				jsonResult.setSuccess(false);
				jsonResult.setMessage("지급대상을 선택하세요.");
			}else {
				excclc.setExcclcPrarnde(excclc.getExcclcPrarnde().replace("-", ""));
				excclcService.updateExcclcPrarnde(excclc);
				jsonResult.setMessage("지급일정이 수정되었습니다.");
				jsonResult.setSuccess(true);
			}
			
		}catch(Exception e) {
			loggerError(LOGGER, e);
			jsonResult.setSuccess(false);
			jsonResult.setMessage("정산 일정 변경에 실패했습니다."); 
		}
		
		return jsonResult;
	}
	
	/**
	 * 정산 엑셀 다운로드
	 * @param menuId
	 * @param searchVO
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/decms/shop/goods/excclcExcelList.do")
	public ModelAndView excclcExcelList(@RequestParam("menuId") String menuId, ExcclcVO searchVO) throws Exception {
		LoginVO user = (LoginVO)EgovUserDetailsHelper.getAuthenticatedUser();
		Map<String, Object> map = new HashMap<String, Object>();
		String fileName = "정산목록";
		
		boolean isManager = false;
		if(!EgovUserDetailsHelper.getAuthorities().contains("ROLE_EMPLOYEE")) {
			searchVO.setSearchCmpnyId(user.getCmpnyId());
			CmpnyVO cmpny = new CmpnyVO();
			cmpny.setCmpnyId(user.getCmpnyId());
			cmpny = cmpnyService.selectCmpny(cmpny);
			fileName = cmpny.getCmpnyNm() + "_정산목록";
		}else {
			isManager = true;
		}
		
		searchVO.setFirstIndex(0);
		searchVO.setRecordCountPerPage(Integer.MAX_VALUE);
		List<?> resultList = excclcService.selectExcclcList(searchVO);
		
		map.put("dataList", resultList);
		if("excclcCpManage".equals(menuId) && isManager) {
			map.put("template", "excclc_list.xlsx");
		}else if("excclcCpDetailManage".equals(menuId) && isManager) {
			map.put("template", "excclc_detail_list.xlsx");
		}else if("cpExcclcManage".equals(menuId)) { // cp Excel
			map.put("template", "cp_excclc_list.xlsx");
		}
		map.put("fileName", fileName);
		
		return new ModelAndView("commonExcelView", map);
	}
	
	
}
