package modoo.cms.shop.goods.info.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import egovframework.com.cmm.ComDefaultCodeVO;
import egovframework.com.cmm.LoginVO;
import egovframework.com.cmm.service.EgovCmmUseService;
import egovframework.com.cmm.service.EgovProperties;
import egovframework.com.cmm.util.EgovUserDetailsHelper;
import egovframework.rte.psl.dataaccess.util.EgovMap;
import egovframework.rte.ptl.mvc.tags.ui.pagination.PaginationInfo;
import modoo.module.common.service.FileMngUtil;
import modoo.module.common.service.JsonResult;
import modoo.module.common.util.CommonUtils;
import modoo.module.common.web.CommonDefaultController;
import modoo.module.shop.cmpny.service.CmpnyService;
import modoo.module.shop.cmpny.service.CmpnyVO;
import modoo.module.shop.cmpny.service.PrtnrCmpnyService;
import modoo.module.shop.cmpny.service.PrtnrCmpnyVO;
import modoo.module.shop.goods.brand.service.GoodsBrandService;
import modoo.module.shop.goods.brand.service.GoodsBrandVO;
import modoo.module.shop.goods.ctgry.service.GoodsCtgryService;
import modoo.module.shop.goods.ctgry.service.GoodsCtgryVO;
import modoo.module.shop.goods.image.service.GoodsImageVO;
import modoo.module.shop.goods.info.service.GoodsItemVO;
import modoo.module.shop.goods.info.service.GoodsService;
import modoo.module.shop.goods.info.service.GoodsVO;
import modoo.module.shop.hdry.service.HdryCmpnyService;
import modoo.module.shop.hdry.service.HdryCmpnyVO;

@Controller
public class CmsGoodsController extends CommonDefaultController {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(CmsGoodsController.class);
	
	private static final String ROOT_CTGRY_ID = "GCTGRY_0000000000000"; //최상위 카타고리ID

	@Resource(name = "cmpnyService")
	private CmpnyService cmpnyService;
	
	@Resource(name = "goodsService")
	private GoodsService goodsService;
	
	/** EgovCmmUseService */
	@Resource(name = "EgovCmmUseService")
	private EgovCmmUseService cmmUseService;
	
	@Resource(name = "goodsCtgryService")
	private GoodsCtgryService goodsCtgryService;
	
	@Resource(name = "goodsBrandService")
	private GoodsBrandService goodsBrandService;
	
	@Resource(name = "prtnrCmpnyService")
	private PrtnrCmpnyService prtnrCmpnyService;
	
	@Resource(name = "hdryCmpnyService")
	private HdryCmpnyService hdryCmpnyService;
	
	@Resource(name = "fileMngUtil")
	private FileMngUtil fileMngUtil;
	
	/**
	 * 상품 관리
	 * @param searchVO
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/decms/shop/goods/goodsManage.do")
	public String goodsManage(@ModelAttribute("searchVO") GoodsVO searchVO, Model model) throws Exception {
		LoginVO user = (LoginVO)EgovUserDetailsHelper.getAuthenticatedUser();
		if(!EgovUserDetailsHelper.getAuthorities().contains("ROLE_EMPLOYEE")) {
			if(StringUtils.isEmpty(user.getCmpnyId())) { //업체 매핑이 안되어 있으면.
				return "redirect:/decms/index.do";
			}
			searchVO.setSearchCmpnyId(user.getCmpnyId());
		}
		
		GoodsCtgryVO goodsCtgry = new GoodsCtgryVO();
		goodsCtgry.setSearchUpperGoodsCtgryId(ROOT_CTGRY_ID);
		List<?> cate1List = goodsCtgryService.selectGoodsCtgryList(goodsCtgry);
		model.addAttribute("cate1List", cate1List);
		
		//상품 상태 카운트
		EgovMap sttusCntinfo = goodsService.selectGoodsSttusCnt(searchVO);
		model.addAttribute("sttusCntinfo", sttusCntinfo);
		
		//등록상태코드
		ComDefaultCodeVO codeVO = new ComDefaultCodeVO();
		codeVO.setCodeId("CMS013");
		List<?> registSttusCodeList = cmmUseService.selectCmmCodeDetail(codeVO);
		model.addAttribute("registSttusCodeList", registSttusCodeList);

		//수강권코드리스트
		codeVO.setCodeId("CMS033");
		List<?> vchKindCodeList = cmmUseService.selectCmmCodeDetail(codeVO);
		model.addAttribute("vchKindCodeList", vchKindCodeList);
		
		return "modoo/cms/shop/goods/info/goodsManage";
	}
	
	/**
	 * 상품 목록
	 * @param searchVO
	 * @return
	 */
	@RequestMapping(value = "/decms/shop/goods/goodsList.json")
	@ResponseBody
	public JsonResult goodsList(GoodsVO searchVO,
			@RequestParam(name="searchCateCode1", required = false) String searchCateCode1,
			@RequestParam(name="searchCateCode2", required = false) String searchCateCode2,
			@RequestParam(name="searchCateCode3", required = false) String searchCateCode3
			) {
		LoginVO user = (LoginVO)EgovUserDetailsHelper.getAuthenticatedUser();
		JsonResult jsonResult = new JsonResult();
		
		if(!EgovUserDetailsHelper.getAuthorities().contains("ROLE_EMPLOYEE")) {
			if(StringUtils.isEmpty(user.getCmpnyId())) { //업체 매핑이 안되어 있으면.
				jsonResult.setMessage(egovMessageSource.getMessage("cms.fail.not.cmpnyId")); //업체등록이 필요합니다.
				jsonResult.setSuccess(false);
				return jsonResult;
			}
				searchVO.setSearchCmpnyId(user.getCmpnyId());
		}
		
		try {
			if(StringUtils.isNotEmpty(searchCateCode3)) {
				searchVO.setSearchGoodsCtgryId(searchCateCode3);
			}else if(StringUtils.isNotEmpty(searchCateCode2)) {
				searchVO.setSearchGoodsCtgryId(searchCateCode2);
			}else if(StringUtils.isNotEmpty(searchCateCode1)) {
				searchVO.setSearchGoodsCtgryId(searchCateCode1);
			}
			
			/*if(StringUtils.isNotEmpty(searchVO.getSearchOrderField())) {
				if("HPC".equals(searchVO.getSearchOrderField())
						|| "DPC".equals(searchVO.getSearchOrderField())) {
					searchVO.setSearchOrderType("DESC");
				}
			}*/
			
			//오름차순, 내림차순 적용
			if("ASC".equals(searchVO.getSearchOrderField2())){
				searchVO.setSearchOrderType("ASC");
				
			} else {
				searchVO.setSearchOrderType("DESC");
			}
			
			PaginationInfo paginationInfo = new PaginationInfo();
			if(searchVO.getPageUnit() <= 10) {
				searchVO.setPageUnit(propertiesService.getInt("gridPageUnit"));
			}
			this.setPagination(paginationInfo, searchVO);
			
			List<?> resultList = goodsService.selectGoodsList(searchVO);
			jsonResult.put("list", resultList);
			
			int totalRecordCount = goodsService.selectGoodsListCnt(searchVO);
			paginationInfo.setTotalRecordCount(totalRecordCount);
			jsonResult.put("paginationInfo", paginationInfo);
			
			jsonResult.setSuccess(true);
		} catch(Exception e) {
			loggerError(LOGGER, e);
			jsonResult.setSuccess(false);
			jsonResult.setMessage(egovMessageSource.getMessage("fail.common.select")); //조회에 실패하였습니다.
		}
		
		
		return jsonResult;
	}
	
	/**
	 * 상품 목록 엑셀 다운로드
	 * @param searchVO
	 * @param searchCateCode1
	 * @param searchCateCode2
	 * @param searchCateCode3
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/decms/shop/goods/goodsListExcel.do")
	public ModelAndView goodsListExcel(@ModelAttribute("searchVO") GoodsVO searchVO,
			@RequestParam(name="searchCateCode1", required = false) String searchCateCode1,
			@RequestParam(name="searchCateCode2", required = false) String searchCateCode2,
			@RequestParam(name="searchCateCode3", required = false) String searchCateCode3, Model model) throws Exception {
		LoginVO user = (LoginVO)EgovUserDetailsHelper.getAuthenticatedUser();
		Map<String, Object> map = new HashMap<String, Object>();
		
		if(EgovUserDetailsHelper.getAuthorities().contains("ROLE_SHOP")) {
			if (!EgovUserDetailsHelper.getAuthorities().contains("ROLE_ADMIN")) {
				searchVO.setSearchCmpnyId(user.getCmpnyId());	
			}
			if(StringUtils.isNotEmpty(searchCateCode3)) {
				searchVO.setSearchGoodsCtgryId(searchCateCode3);
			}else if(StringUtils.isNotEmpty(searchCateCode2)) {
				searchVO.setSearchGoodsCtgryId(searchCateCode2);
			}else if(StringUtils.isNotEmpty(searchCateCode1)) {
				searchVO.setSearchGoodsCtgryId(searchCateCode1);
			}
		}
		
		searchVO.setRecordCountPerPage(Integer.MAX_VALUE);
		searchVO.setFirstIndex(0);
		List<?> resultList = goodsService.selectGoodsList(searchVO);
		map.put("dataList", resultList);
		map.put("template", "goods_list.xlsx");
		map.put("fileName", "상품목록");
		
		return new ModelAndView("commonExcelView", map);
	}
	
	/**
	 * 상품 작성 폼
	 * @param searchVO
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/decms/shop/goods/writeGoods.do", method = RequestMethod.GET)
	public String writeGoods(@ModelAttribute("searchVO") GoodsVO searchVO,
			@ModelAttribute("copyGoodsId") String copyGoodsId, Model model) throws Exception {
		LoginVO user = (LoginVO)EgovUserDetailsHelper.getAuthenticatedUser();
		if(!EgovUserDetailsHelper.getAuthorities().contains("ROLE_EMPLOYEE")) {
			if(StringUtils.isEmpty(user.getCmpnyId())) { //업체 매핑이 안되어 있으면.
				return "redirect:/decms/index.do";
			}else {
				searchVO.setCmpnyId(user.getCmpnyId());
			}
		}
		
		//상품종류
		ComDefaultCodeVO codeVO = new ComDefaultCodeVO();
		codeVO.setCodeId("CMS009");
		List<?> goodsKndCodeList = cmmUseService.selectCmmCodeDetail(codeVO);
		model.addAttribute("goodsKndCodeList", goodsKndCodeList);

		//과세구분코드
		codeVO.setCodeId("CMS010");
		List<?> taxtSeCodeList = cmmUseService.selectCmmCodeDetail(codeVO);
		model.addAttribute("taxtSeCodeList", taxtSeCodeList);

		//배송비구분코드
		codeVO.setCodeId("CMS011");
		List<?> dlvySeCodeList = cmmUseService.selectCmmCodeDetail(codeVO);
		model.addAttribute("dlvySeCodeList", dlvySeCodeList);
		
		//배송주기_달 코드
		codeVO.setCodeId("CMS012");
		List<?> dlvyCycleCtList = cmmUseService.selectCmmCodeDetail(codeVO);
		model.addAttribute("dlvyCycleCtList", dlvyCycleCtList);
		
		//등록상태코드
		codeVO.setCodeId("CMS013");
		List<?> registSttusCodeList = cmmUseService.selectCmmCodeDetail(codeVO);
		model.addAttribute("registSttusCodeList", registSttusCodeList);

		//수강권코드리스트
		codeVO.setCodeId("CMS033");
		List<?> vchKindCodeList = cmmUseService.selectCmmCodeDetail(codeVO);
		model.addAttribute("vchKindCodeList", vchKindCodeList);

		//수강권기간타입코드리스트
		codeVO.setCodeId("CMS034");
		List<?> vchPdTyCodeList = cmmUseService.selectCmmCodeDetail(codeVO);
		model.addAttribute("vchPdTyCodeList", vchPdTyCodeList);

		//카테고리 (ROOT)
		/*GoodsCtgryVO goodsCtgry = new GoodsCtgryVO();
		goodsCtgry.setSearchUpperGoodsCtgryId(ROOT_CTGRY_ID);
		List<?> cate1List = goodsCtgryService.selectGoodsCtgryList(goodsCtgry);
		model.addAttribute("cate1List", cate1List);*/
		
		/*//브랜드
		if(StringUtils.isNotEmpty(searchVO.getCmpnyId())) {
			GoodsBrandVO goodsBrand = new GoodsBrandVO();
			goodsBrand.setFirstIndex(0);
			goodsBrand.setRecordCountPerPage(Integer.MAX_VALUE);
			goodsBrand.setSearchCmpnyId(user.getCmpnyId());
			List<GoodsBrandVO> brandList = goodsBrandService.selectGoodsBrandList(goodsBrand);
			model.addAttribute("brandList", brandList);
		}
		
		//택배사 선택
		if(StringUtils.isNotEmpty(searchVO.getCmpnyId())) {
			HdryCmpnyVO hdryCmpny = new HdryCmpnyVO();
			hdryCmpny.setSearchCmpnyId(searchVO.getCmpnyId());
			List<HdryCmpnyVO> hdryList = hdryCmpnyService.selectGoodsHdryList(hdryCmpny);	
			model.addAttribute("hdryList", hdryList);
		}*/
		
		GoodsVO goods = new GoodsVO();
		if(StringUtils.isNotEmpty(copyGoodsId)) { // 복사할 대상이 있으면
			GoodsVO copyGoods = new GoodsVO();
			copyGoods.setGoodsId(copyGoodsId);
			goods = goodsService.selectGoods(copyGoods);
			
			if(!EgovUserDetailsHelper.getAuthorities().contains("ROLE_EMPLOYEE")) {
				if(!goods.getCmpnyId().equals(user.getCmpnyId())) { //내 상품이 아니면
					return "redirect:/decms/index.do";
				}
			}
			
			List<GoodsItemVO> dGitemList = goods.getdGitemList();
			for(GoodsItemVO ivo : dGitemList) {
				ivo.setGitemId(null);
			}
			List<GoodsItemVO> aGitemList = goods.getaGitemList();
			for(GoodsItemVO ivo : aGitemList) {
				ivo.setGitemId(null);
			}
			List<GoodsItemVO> fGitemList = goods.getfGitemList();
			for(GoodsItemVO ivo : fGitemList) {
				ivo.setGitemId(null);
			}
			List<GoodsItemVO> qGitemList = goods.getqGitemList();
			for(GoodsItemVO ivo : qGitemList) {
				ivo.setGitemId(null);
			}
			
			//카테고리 
			GoodsCtgryVO goodsCtgry = new GoodsCtgryVO();
			goodsCtgry.setGoodsCtgryId(goods.getGoodsCtgryId());
			EgovMap cateMap = goodsCtgryService.getGoodsCtgryDepthList(goodsCtgry, goods);
			model.addAttribute("cate1List", cateMap.get("cate1List"));
			model.addAttribute("cate2List", cateMap.get("cate2List"));
			model.addAttribute("cate3List", cateMap.get("cate3List"));

			goods.setGoodsId(null);
			for(GoodsImageVO evt : goods.getEvtImageList()) { // 설명이미지
				evt.setGoodsImageNo(null);
			}
			for(GoodsImageVO gdc : goods.getGdcImageList()) { // 설명이미지
				gdc.setGoodsImageNo(null);
			}
			for(GoodsImageVO gimg : goods.getGoodsImageList()) { // 상품이미지
				gimg.setGoodsImageNo(null);
			}
			
			searchVO.setCmpnyId(goods.getCmpnyId());
		}else {
			//카테고리 (ROOT)
			GoodsCtgryVO goodsCtgry = new GoodsCtgryVO();
			goodsCtgry.setSearchUpperGoodsCtgryId(ROOT_CTGRY_ID);
			List<?> cate1List = goodsCtgryService.selectGoodsCtgryList(goodsCtgry);
			model.addAttribute("cate1List", cate1List);
			
			if(!EgovUserDetailsHelper.getAuthorities().contains("ROLE_EMPLOYEE")) {
				goods.setCmpnyId(user.getCmpnyId());
				
				//제휴사매핑목록
				PrtnrCmpnyVO prtnrCmpny = new PrtnrCmpnyVO();
				prtnrCmpny.setCmpnyId(user.getCmpnyId());
				List<PrtnrCmpnyVO> prtnrCmpnyList = prtnrCmpnyService.selectPrtnrCmpnyList(prtnrCmpny);
				goods.setPrtnrCmpnyList(prtnrCmpnyList);
			}
			goods.setGoodsKndCode("SBS"); //구독상품
			goods.setBundleDlvyAt("N"); // 묶음배송 불가능
			/*
			goods.setWmallRsrvmney(java.math.BigDecimal.ZERO); // 복지몰적립금
			goods.setWmallRsrvmneyRate(java.math.BigDecimal.ZERO); //복지몰적립금비율
			goods.setB2cRsrvmney(java.math.BigDecimal.ZERO); // B2C적립금
			goods.setB2cRsrvmneyRate(java.math.BigDecimal.ZERO); // B2C적립금비율
			*/
			goods.setGoodsRsrvmney(java.math.BigDecimal.ZERO); // 상품적립금
			goods.setGoodsRsrvmneyRate(java.math.BigDecimal.ZERO); // 상품적립금비율
			goods.setTaxtSeCode("TA01"); // 과세
			goods.setDlvySeCode("DS01"); // 선불
			goods.setBundleDlvyAt("Y"); // 묶음배송
			goods.setRegistSttusCode("R"); //대기
			goods.setOptnUseAt("N"); //옵션사용여부
			goods.setdOptnUseAt("N"); //기본옵션사용여부
			goods.setaOptnUseAt("N"); //추가옵션사용여부
			goods.setfOptnUseAt("N"); //첫구매옵션사용여부
			goods.setqOptnUseAt("N"); //질답옵션사용여부
			goods.setDlvyPolicySeCode("DP02"); //기본정책
			goods.setDlvyPolicyCn(EgovProperties.getProperty("GOODS.plicy.cn"));
			goods.setGoodsFeeRate(java.math.BigDecimal.ZERO); //수수료율 
			goods.setIslandDlvyPc(java.math.BigDecimal.ZERO); //도서산간 추가배송비
			goods.setDlvySeCode("DS03"); //무료배송
			goods.setBundleDlvyAt("N");
			goods.setSbscrptCycleSeCode("WEEK"); //구독주기
			goods.setSbscrptSetleDay(3); //구독배송일기준결제일
			goods.setFrstOptnEssntlAt("N"); //첫구독옵션필수여부
		}
		model.addAttribute("goods", goods);
		
		//브랜드
		if(StringUtils.isNotEmpty(searchVO.getCmpnyId())) {
			GoodsBrandVO goodsBrand = new GoodsBrandVO();
			goodsBrand.setFirstIndex(0);
			goodsBrand.setRecordCountPerPage(Integer.MAX_VALUE);
			goodsBrand.setSearchCmpnyId(user.getCmpnyId());
			List<GoodsBrandVO> brandList = goodsBrandService.selectGoodsBrandList(goodsBrand);
			model.addAttribute("brandList", brandList);
		}
		
		//택배사 선택
		if(StringUtils.isNotEmpty(searchVO.getCmpnyId())) {
			HdryCmpnyVO hdryCmpny = new HdryCmpnyVO();
			hdryCmpny.setSearchCmpnyId(searchVO.getCmpnyId());
			List<HdryCmpnyVO> hdryList = hdryCmpnyService.selectGoodsHdryList(hdryCmpny);	
			model.addAttribute("hdryList", hdryList);
		}
		
		model.addAttribute("dlvyPolicyCn", EgovProperties.getProperty("GOODS.plicy.cn"));

		return "modoo/cms/shop/goods/info/goodsForm";
	}
	
	/**
	 * 상품 저장
	 * @param searchVO
	 * @param goods
	 * @param bindingResult
	 * @param menuId
	 * @return
	 */
	@RequestMapping(value = "/decms/shop/goods/writeGoods.json", method = RequestMethod.POST)
	@ResponseBody
	public JsonResult writeGoods(@ModelAttribute("searchVO") GoodsVO searchVO, 
			@Valid GoodsVO goods, BindingResult bindingResult,
			@RequestParam(value = "menuId") String menuId) {
		JsonResult jsonResult = new JsonResult();
		LoginVO user = (LoginVO)EgovUserDetailsHelper.getAuthenticatedUser();
		
		try {
			/*
			if(!DoubleSubmitHelper.checkAndSaveToken()) {
				//this.vaildateMessage(egovMessageSource.getMessage("cms.fail.access"), jsonResult); // 잘못된 접근입니다.
				String redirectUrl = "/decms/shop/goods/goodsManage.do"
						+ "?menuId=" + menuId
						+ "&searchCondition" + searchVO.getSearchCondition()
						+ "&searchKeyword=" + searchVO.getSearchKeyword()
						+ "&searchCateCode1=" + searchVO.getSearchCateCode1()
						+ "&searchCateCode2=" + searchVO.getSearchCateCode2()
						+ "&searchCateCode3=" + searchVO.getSearchCateCode3()
						+ "&searchCmpnyId=" + searchVO.getSearchCmpnyId()
						+ "&searchRegistSttusCode=" + searchVO.getSearchRegistSttusCode();
				jsonResult.setRedirectUrl(redirectUrl);
			}else if(!this.isHasErrorsJson(bindingResult, jsonResult, "<br/>")) {
			*/
			
			if(goods.getGdcImageList() == null || goods.getGdcImageList().size() == 0) {
				jsonResult.setMessage("설명 이미지는 필수 사항입니다.");
				jsonResult.setSuccess(false);
				return jsonResult;
			}else if(goods.getGoodsImageList() == null || goods.getGoodsImageList().size() == 0) {
				jsonResult.setMessage("상품 이미지는 필수 사항입니다.");
				jsonResult.setSuccess(false);
				return jsonResult;
			}
			else if(goods.getHdryId()== null  || goods.getHdryId().length() == 0) {
				jsonResult.setMessage("택배사는 필수 사항입니다.");
				jsonResult.setSuccess(false);
				return jsonResult;
			}

			if(!this.isHasErrorsJson(bindingResult, jsonResult, "<br/>")) {

				this.setGoodsValue(goods);

				if(!EgovUserDetailsHelper.getAuthorities().contains("ROLE_EMPLOYEE")) { // ROLE_SHOP 이면 강제 입력
					goods.setCmpnyId(user.getCmpnyId());
					goods.setRegistSttusCode("R"); //등록대기
				}

				goods.setFrstRegisterId(user.getUniqId());
				
				if(StringUtils.isEmpty(goods.getGoodsCtgryId())) {
					this.vaildateMessage(egovMessageSource.getMessage("NotEmpty.goodsVO.cateCode1"), jsonResult); // 카테고리를 선택하세요.
				}else {
					
					goodsService.insertGoods(goods);
					jsonResult.setSuccess(true);
					jsonResult.setMessage(egovMessageSource.getMessage("success.common.insert")); // 정상적으로 등록되었습니다.
					String redirectUrl = "/decms/shop/goods/goodsManage.do"
										+ "?menuId=" + menuId
										+ "&searchCondition" + searchVO.getSearchCondition()
										+ "&searchKeyword=" + searchVO.getSearchKeyword()
										+ "&searchCateCode1=" + searchVO.getSearchCateCode1()
										+ "&searchCateCode2=" + searchVO.getSearchCateCode2()
										+ "&searchCateCode3=" + searchVO.getSearchCateCode3()
										+ "&searchCmpnyId=" + searchVO.getSearchCmpnyId()
										+ "&searchCmpnyNm=" + searchVO.getSearchCmpnyNm()
										+ "&searchGoodsId=" + searchVO.getSearchGoodsId()
										+ "&pageIndex=" + searchVO.getPageIndex()
										+ "&searchRegistSttusCode=" + searchVO.getSearchRegistSttusCode();
					jsonResult.setRedirectUrl(redirectUrl);
				}
			}
		} catch(Exception e) {
			loggerError(LOGGER, e);
			jsonResult.setSuccess(false);
			jsonResult.setMessage(egovMessageSource.getMessage("fail.common.insert")); //생성이 실패하였습니다.
		}
		
		return jsonResult;
	}
	
	/**
	 * 상품 정보
	 * @param searchVO
	 * @return
	 */
	@RequestMapping(value = "/decms/shop/goods/goodsInfo.json")
	@ResponseBody
	public JsonResult goodsInfo(GoodsVO searchVO) {
		LoginVO user = (LoginVO)EgovUserDetailsHelper.getAuthenticatedUser();
		JsonResult jsonResult = new JsonResult();
		
		try {
			if(StringUtils.isEmpty(searchVO.getGoodsId())) {
				this.vaildateMessage(egovMessageSource.getMessage("cms.fail.access"), jsonResult); // 잘못된 접근입니다.
				LOGGER.error("goodsId 가 없음.");
			}else {
				GoodsVO goods = goodsService.selectGoods(searchVO);

				if(!EgovUserDetailsHelper.getAuthorities().contains("ROLE_EMPLOYEE")) { // ROLE_SHOP 이면 강제 입력
					if(!goods.getCmpnyId().equals(user.getCmpnyId())) { // 자신에게 속한게 아니면
						jsonResult.setMessage(egovMessageSource.getMessage("cms.fail.access")); //잘못된 접근입니다.
						jsonResult.setSuccess(false);
						return jsonResult;
					}
				}
				
				jsonResult.put("goods", goods);
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
	 * 상품값 처리
	 * @param goods
	 */
	private void setGoodsValue(GoodsVO goods) {
		
		/*if(StringUtils.isEmpty(goods.getGoodsSetleSeCode())) {
			goods.setGoodsSetleSeCode("SBS"); // 구독결제
		}*/
		
		//품절여부
		if(StringUtils.isEmpty(goods.getSoldOutAt())) {
			goods.setSoldOutAt("N");
		}

		// 카테고리 코드
		if(StringUtils.isNotEmpty(goods.getCateCode3())) goods.setGoodsCtgryId(goods.getCateCode3());
		else if(StringUtils.isNotEmpty(goods.getCateCode2())) goods.setGoodsCtgryId(goods.getCateCode2());
		else if(StringUtils.isNotEmpty(goods.getCateCode1())) goods.setGoodsCtgryId(goods.getCateCode1());
		
		/*
		//복지몰 적립금
		if(goods.getWmallPc() == null) goods.setWmallPc(java.math.BigDecimal.ZERO);
		if(goods.getWmallRsrvmney() == null) goods.setWmallRsrvmney(java.math.BigDecimal.ZERO);
		if(StringUtils.isEmpty(goods.getWmallRsrvmneyRateAt())) {
			goods.setWmallRsrvmneyRateAt("N");
			goods.setWmallRsrvmneyRate(java.math.BigDecimal.ZERO);
		}
		
		//B2C 적립금
		if(goods.getB2cPc() == null) goods.setB2cPc(java.math.BigDecimal.ZERO);
		if(goods.getB2cRsrvmney() == null) goods.setB2cRsrvmney(java.math.BigDecimal.ZERO);
		if(StringUtils.isEmpty(goods.getB2cRsrvmneyRateAt())) {
			goods.setB2cRsrvmneyRateAt("N");
			goods.setB2cRsrvmneyRate(java.math.BigDecimal.ZERO);
		}
		*/
		
		//상품 적립금
		if(goods.getGoodsPc() == null) goods.setGoodsPc(java.math.BigDecimal.ZERO);
		if(goods.getGoodsRsrvmney() == null) goods.setGoodsRsrvmney(java.math.BigDecimal.ZERO);
		if(StringUtils.isEmpty(goods.getGoodsRsrvmneyRateAt())) {
			goods.setGoodsRsrvmneyRateAt("N");
			goods.setGoodsRsrvmneyRate(java.math.BigDecimal.ZERO);
		}
		
		//브랜드ID
		if(StringUtils.isEmpty(goods.getBrandId())) goods.setBrandId(null);
		
		goods.setMakr(CommonUtils.unscript(goods.getMakr()));
		goods.setOrgplce(CommonUtils.unscript(goods.getOrgplce()));
		goods.setCrtfcMatter(CommonUtils.unscript(goods.getCrtfcMatter()));
		goods.setCnsltTelno(CommonUtils.unscript(goods.getCnsltTelno()));
		goods.setEvntWords(CommonUtils.unscript(goods.getEvntWords()));
		goods.setGoodsIntrcn(CommonUtils.unscript(goods.getGoodsIntrcn()));
		goods.setMngrMemo(CommonUtils.unscript(goods.getMngrMemo()));
		goods.setMvpSourcCn(CommonUtils.unscript(goods.getMvpSourcCn()));
		goods.setGoodsCn(CommonUtils.unscript(goods.getGoodsCn()));
		
		//옵션처리
		if("N".equals(goods.getOptnUseAt())) {
			goods.setdOptnUseAt("N");
			goods.setaOptnUseAt("N");
			goods.setfOptnUseAt("N");

		}

		// 무료 배송비
		if("DS03".equals(goods.getDlvySeCode())) {
			goods.setDlvyPc(java.math.BigDecimal.ZERO);
			goods.setFreeDlvyPc(java.math.BigDecimal.ZERO);
		}
		
	}

	/**
	 * 상품 수정 폼
	 * @param searchVO
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/decms/shop/goods/modifyGoods.do", method = RequestMethod.GET)
	public String modifyGoods(@ModelAttribute("searchVO") GoodsVO searchVO, Model model) throws Exception {
		LoginVO user = (LoginVO)EgovUserDetailsHelper.getAuthenticatedUser();
		GoodsVO goods = goodsService.selectGoods(searchVO);
		
		if(!EgovUserDetailsHelper.getAuthorities().contains("ROLE_EMPLOYEE")) { // ROLE_SHOP 이면 강제 입력
			if(!goods.getCmpnyId().equals(user.getCmpnyId())) { // 자신에게 속한게 아니면
				return "redirect:/decms/index.do";
			}
			
			// 업체일경우 등록완료 상태에서는 결제주기/옵션/가격을 수정을 못하게 막아야한다.
			if(!"R".equals(goods.getRegistSttusCode())) { //등록 대기가 아니면
				model.addAttribute("readOnlyAt", "Y");
			}
		}

		//상품종류
		ComDefaultCodeVO codeVO = new ComDefaultCodeVO();
		codeVO.setCodeId("CMS009");
		List<?> goodsKndCodeList = cmmUseService.selectCmmCodeDetail(codeVO);
		model.addAttribute("goodsKndCodeList", goodsKndCodeList);

		//과세구분코드
		codeVO.setCodeId("CMS010");
		List<?> taxtSeCodeList = cmmUseService.selectCmmCodeDetail(codeVO);
		model.addAttribute("taxtSeCodeList", taxtSeCodeList);

		//배송비구분코드
		codeVO.setCodeId("CMS011");
		List<?> dlvySeCodeList = cmmUseService.selectCmmCodeDetail(codeVO);
		model.addAttribute("dlvySeCodeList", dlvySeCodeList);
		
		//배송주기_달 코드
		codeVO.setCodeId("CMS012");
		List<?> dlvyCycleCtList = cmmUseService.selectCmmCodeDetail(codeVO);
		model.addAttribute("dlvyCycleCtList", dlvyCycleCtList);
		
		//등록상태코드
		codeVO.setCodeId("CMS013");
		List<?> registSttusCodeList = cmmUseService.selectCmmCodeDetail(codeVO);
		model.addAttribute("registSttusCodeList", registSttusCodeList);

		//수강권코드리스트
		codeVO.setCodeId("CMS033");
		List<?> vchKindCodeList = cmmUseService.selectCmmCodeDetail(codeVO);
		model.addAttribute("vchKindCodeList", vchKindCodeList);

		//수강권기간타입코드리스트
		codeVO.setCodeId("CMS034");
		List<?> vchPdTyCodeList = cmmUseService.selectCmmCodeDetail(codeVO);
		model.addAttribute("vchPdTyCodeList", vchPdTyCodeList);
		
		//카테고리 
		GoodsCtgryVO goodsCtgry = new GoodsCtgryVO();
		goodsCtgry.setGoodsCtgryId(goods.getGoodsCtgryId());
		EgovMap cateMap = goodsCtgryService.getGoodsCtgryDepthList(goodsCtgry, goods);
		model.addAttribute("cate1List", cateMap.get("cate1List"));
		model.addAttribute("cate2List", cateMap.get("cate2List"));
		model.addAttribute("cate3List", cateMap.get("cate3List"));
				
		//브랜드
		GoodsBrandVO goodsBrand = new GoodsBrandVO();
		goodsBrand.setFirstIndex(0);
		goodsBrand.setRecordCountPerPage(Integer.MAX_VALUE);
		goodsBrand.setSearchCmpnyId(goods.getCmpnyId());
		List<GoodsBrandVO> brandList = goodsBrandService.selectGoodsBrandList(goodsBrand);
		model.addAttribute("brandList", brandList);

		//택배사 선택
		HdryCmpnyVO hdryCmpny = new HdryCmpnyVO();
		hdryCmpny.setSearchCmpnyId(goods.getCmpnyId());
		List<HdryCmpnyVO> hdryList = hdryCmpnyService.selectGoodsHdryList(hdryCmpny);	
		model.addAttribute("hdryList", hdryList);

		//임시 처리 -----------------------------------------------------------------
		if(StringUtils.isEmpty(goods.getSbscrptCycleSeCode())) {
			goods.setSbscrptCycleSeCode("MONTH");
		}
		
		CmpnyVO cmpny = new CmpnyVO();
		cmpny.setCmpnyId(goods.getCmpnyId());
		cmpny = cmpnyService.selectCmpny(cmpny);

		
		model.addAttribute("cmpnyDlvyPolicyCn", cmpny.getCmpnyDlvyPolicyCn());
		model.addAttribute("dlvyPolicyCn", EgovProperties.getProperty("GOODS.plicy.cn"));
		model.addAttribute("goods", goods);
		
		return "modoo/cms/shop/goods/info/goodsForm";
	}
	
	/**
	 * 상품 수정
	 * @param searchVO
	 * @param goods
	 * @param bindingResult
	 * @param menuId
	 * @return
	 */
	@RequestMapping(value = "/decms/shop/goods/modifyGoods.json", method = RequestMethod.POST)
	@ResponseBody
	public JsonResult modifyGoods(@ModelAttribute("searchVO") GoodsVO searchVO, 
			@Valid GoodsVO goods, BindingResult bindingResult,
			@RequestParam(value = "menuId") String menuId) {
		JsonResult jsonResult = new JsonResult();
		LoginVO user = (LoginVO)EgovUserDetailsHelper.getAuthenticatedUser();
		
		try {
			GoodsVO checkVO = goodsService.selectGoods(goods);
			if(!EgovUserDetailsHelper.getAuthorities().contains("ROLE_EMPLOYEE")) { // ROLE_SHOP 이면 강제 입력
				if(!checkVO.getCmpnyId().equals(user.getCmpnyId())) {
					jsonResult.setMessage(egovMessageSource.getMessage("cms.fail.accessDenied")); //접근 권한이 없습니다.
					jsonResult.setSuccess(false);
					return jsonResult;
				}

				if(!"R".equals(checkVO.getRegistSttusCode())) { //등록 대기가 아니면
					//상품종류
					goods.setGoodsKndCode(checkVO.getGoodsKndCode());
					
					//결제주기
					goods.setSbscrptCycleSeCode(checkVO.getSbscrptCycleSeCode());
					goods.setSbscrptWeekCycle(checkVO.getSbscrptWeekCycle());
					goods.setSbscrptDlvyWd(checkVO.getSbscrptDlvyWd());
					goods.setSbscrptMinUseWeek(checkVO.getSbscrptMinUseWeek());
					
					goods.setSbscrptMtCycle(checkVO.getSbscrptMtCycle());
					goods.setSbscrptMinUseMt(checkVO.getSbscrptMinUseMt());
					goods.setSbscrptDlvyDay(checkVO.getSbscrptDlvyDay());
					
					//옵션
					goods.setOptnUseAt(checkVO.getOptnUseAt());
					goods.setdOptnUseAt(checkVO.getdOptnUseAt()); //기본옵션
					goods.setdGitemList(checkVO.getdGitemList());
					goods.setaOptnUseAt(checkVO.getaOptnUseAt()); //추가옵션
					goods.setaGitemList(checkVO.getaGitemList());
					goods.setfOptnUseAt(checkVO.getfOptnUseAt()); //첫구독옵션
					goods.setfGitemList(checkVO.getfGitemList());
					goods.setqOptnUseAt(checkVO.getqOptnUseAt()); //업체요청사항
					goods.setqGitemList(checkVO.getqGitemList());
					
					goods.setFrstOptnEssntlAt(checkVO.getFrstOptnEssntlAt()); //첫구독옵션 필수 사항 여부
					
					goods.setGoodsPc(checkVO.getGoodsPc()); //판매가
					goods.setGoodsFeeRate(checkVO.getGoodsFeeRate()); // 수수료율
					goods.setGoodsSplpc(checkVO.getGoodsSplpc()); // 공급가
					goods.setGoodsRsrvmney(checkVO.getGoodsRsrvmney()); //상품 적립금
					goods.setGoodsRsrvmneyRateAt(checkVO.getGoodsRsrvmneyRateAt()); // 상품 적립금 퍼센트 설정여부
					goods.setGoodsRsrvmneyRate(checkVO.getGoodsRsrvmneyRate()); // 상품 적립금 퍼센트
					goods.setTaxtSeCode(checkVO.getTaxtSeCode()); //과세/면세
					goods.setDlvyPolicySeCode(checkVO.getDlvyPolicySeCode()); // 배송정책
					
					//TODO : 오픈 전 막아야한다.
					//goods.setIslandDlvyPc(checkVO.getIslandDlvyPc()); //도서산간 추가 배송비
					//goods.setJejuDlvyPc(checkVO.getJejuDlvyPc()); //제주도 배송비

					goods.setDlvySeCode(checkVO.getDlvySeCode()); //배송구분코드
					goods.setDlvyPc(checkVO.getDlvyPc()); // 배송비
					goods.setFreeDlvyPc(checkVO.getFreeDlvyPc()); //무료주문기준가격
					
					goods.setBundleDlvyAt(checkVO.getBundleDlvyAt()); // 묶음배송여부
					goods.setBundleDlvyCo(checkVO.getBundleDlvyCo()); // 묶음배송갯수
					
					
				}
				goods.setMrktPc(checkVO.getMrktPc()); //시중가
				goods.setRegistSttusCode(checkVO.getRegistSttusCode()); //등록상태 
			}
			
			/*
			if(!DoubleSubmitHelper.checkAndSaveToken()) {
				//this.vaildateMessage(egovMessageSource.getMessage("cms.fail.access"), jsonResult); // 잘못된 접근입니다.
				String redirectUrl = "/decms/shop/goods/goodsManage.do"
						+ "?menuId=" + menuId
						+ "&searchCondition" + searchVO.getSearchCondition()
						+ "&searchKeyword=" + searchVO.getSearchKeyword()
						+ "&searchCateCode1=" + searchVO.getSearchCateCode1()
						+ "&searchCateCode2=" + searchVO.getSearchCateCode2()
						+ "&searchCateCode3=" + searchVO.getSearchCateCode3()
						+ "&searchCmpnyId=" + searchVO.getSearchCmpnyId()
						+ "&searchRegistSttusCode=" + searchVO.getSearchRegistSttusCode();
				jsonResult.setRedirectUrl(redirectUrl);
			}else if(!this.isHasErrorsJson(bindingResult, jsonResult, "<br/>")) {
			*/
			
			if(goods.getGdcImageList() == null || goods.getGdcImageList().size() == 0) {
				jsonResult.setMessage("설명 이미지는 필수 사항입니다.");
				jsonResult.setSuccess(false);
				return jsonResult;
			}else if(goods.getGoodsImageList() == null || goods.getGoodsImageList().size() == 0) {
				jsonResult.setMessage("상품 이미지는 필수 사항입니다.");
				jsonResult.setSuccess(false);
				return jsonResult;
			}else if(goods.getHdryId()== null || goods.getHdryId().length() == 0) {
				jsonResult.setMessage("택배사는 필수 사항입니다.");
				jsonResult.setSuccess(false);
				return jsonResult;
			}
			
			if(!this.isHasErrorsJson(bindingResult, jsonResult, "<br/>")) {
				
				this.setGoodsValue(goods);
				
				goods.setLastUpdusrId(user.getUniqId());
				goodsService.updateGoods(goods);
				
				jsonResult.setSuccess(true);
				jsonResult.setMessage(egovMessageSource.getMessage("success.common.update")); // 정상적으로 수정되었습니다.
				String redirectUrl = "/decms/shop/goods/goodsManage.do"
									+ "?menuId=" + menuId
									+ "&searchCondition" + searchVO.getSearchCondition()
									+ "&searchKeyword=" + searchVO.getSearchKeyword()
									+ "&searchCateCode1=" + searchVO.getSearchCateCode1()
									+ "&searchCateCode2=" + searchVO.getSearchCateCode2()
									+ "&searchCateCode3=" + searchVO.getSearchCateCode3()
									+ "&searchCmpnyId=" + searchVO.getSearchCmpnyId()
									+ "&searchCmpnyNm=" + searchVO.getSearchCmpnyNm()
									+ "&searchGoodsId=" + searchVO.getSearchGoodsId()
									+ "&pageIndex=" + searchVO.getPageIndex()
									+ "&searchRegistSttusCode=" + searchVO.getSearchRegistSttusCode();
				jsonResult.setRedirectUrl(redirectUrl);
			}
			
		} catch(Exception e) {
			loggerError(LOGGER, e);
			jsonResult.setSuccess(false);
			jsonResult.setMessage(egovMessageSource.getMessage("fail.common.update")); //수정이 실패하였습니다.
		}
		
		return jsonResult;
	}
	
	/**
	 * 상품 삭제
	 * @param goods
	 * @return
	 */
	@RequestMapping(value = "/decms/shop/goods/deleteGoods.json", method = RequestMethod.POST)
	@ResponseBody
	public JsonResult deleteGoods(GoodsVO goods) {
		JsonResult jsonResult = new JsonResult();
		LoginVO user = (LoginVO)EgovUserDetailsHelper.getAuthenticatedUser();
		
		try {
			if(StringUtils.isEmpty(goods.getGoodsId())) {
				this.vaildateMessage(egovMessageSource.getMessage("cms.fail.access"), jsonResult); // 잘못된 접근입니다.
				LOGGER.error("goodsId 가 없음.");
			}else {
				
				/* 정책변경 : 관리자만 삭제할 수 있는 권한으로 인해 주석처리됨
				GoodsVO checkVO = goodsService.selectGoods(goods);
				if(!EgovUserDetailsHelper.getAuthorities().contains("ROLE_EMPLOYEE")) { // ROLE_SHOP 이면 강제 입력
					if(!checkVO.getCmpnyId().equals(user.getCmpnyId())) {
						jsonResult.setMessage(egovMessageSource.getMessage("cms.fail.accessDenied")); //접근 권한이 없습니다.
						jsonResult.setSuccess(false);
						return jsonResult;
					}
				}
				*/
				//직원 이상만 삭제 하게 함.
				if(!EgovUserDetailsHelper.getAuthorities().contains("ROLE_EMPLOYEE")) { 
					jsonResult.setMessage(egovMessageSource.getMessage("cms.fail.accessDenied")); //접근 권한이 없습니다.
					jsonResult.setSuccess(false);
					return jsonResult;
				}
				
				
				goods.setLastUpdusrId(user.getUniqId());

				goodsService.deleteGoods(goods);
				jsonResult.setSuccess(true);
				jsonResult.setMessage(egovMessageSource.getMessage("success.common.delete")); //정상적으로 삭제되었습니다.
			}
			
		} catch(Exception e) {
			loggerError(LOGGER, e);
			jsonResult.setSuccess(false);
			jsonResult.setMessage(egovMessageSource.getMessage("fail.common.delete")); //삭제가 실패하였습니다.
		}
		
		return jsonResult;
	}
	
	/**
	 * 상품 이벤트 이미지 업로드
	 * @param multiRequest
	 * @return
	 */
	@RequestMapping(value = "/decms/shop/goods/uploadEvtImageFile.json", method = RequestMethod.POST)
	@ResponseBody
	public JsonResult uploadEvtImageFile(final MultipartHttpServletRequest multiRequest) {
		JsonResult jsonResult = new JsonResult();
		
		try {
			final List<MultipartFile> fileList = multiRequest.getFiles("atchFile");
			jsonResult.setSuccess(true);
			jsonResult.put("fileList", fileMngUtil.parseGoodsFileList(fileList, "EVT")); // 상품 설명이미지

			
		} catch(Exception e) {
			loggerError(LOGGER, e);
			jsonResult.setSuccess(false);
			jsonResult.setMessage(egovMessageSource.getMessage("errors.file.transfer")); //파일전송중 오류가 발생했습니다.
		}
		
		return jsonResult;
	}
	
	/**
	 * 상품 설명 이미지 업로드
	 * @param multiRequest
	 * @return
	 */
	@RequestMapping(value = "/decms/shop/goods/uploadGdcImageFile.json", method = RequestMethod.POST)
	@ResponseBody
	public JsonResult uploadGdcImageFile(final MultipartHttpServletRequest multiRequest) {
		JsonResult jsonResult = new JsonResult();
		
		try {
			final List<MultipartFile> fileList = multiRequest.getFiles("atchFile");
			jsonResult.setSuccess(true);
			jsonResult.put("fileList", fileMngUtil.parseGoodsFileList(fileList, "GDC")); // 상품 설명이미지

			
		} catch(Exception e) {
			loggerError(LOGGER, e);
			jsonResult.setSuccess(false);
			jsonResult.setMessage(egovMessageSource.getMessage("errors.file.transfer")); //파일전송중 오류가 발생했습니다.
		}
		
		return jsonResult;
	}
	
	/**
	 * 상품 이미지 업로드
	 * @param multiRequest
	 * @return
	 */
	@RequestMapping(value = "/decms/shop/goods/uploadGnrImageFile.json", method = RequestMethod.POST)
	@ResponseBody
	public JsonResult uploadGnrImageFile(final MultipartHttpServletRequest multiRequest) {
		JsonResult jsonResult = new JsonResult();
		
		try {
			final List<MultipartFile> fileList = multiRequest.getFiles("atchFile");
			jsonResult.setSuccess(true);
			jsonResult.put("fileList", fileMngUtil.parseGoodsFileList(fileList, "GNR")); // 상품 이미지 

			
		} catch(Exception e) {
			loggerError(LOGGER, e);
			jsonResult.setSuccess(false);
			jsonResult.setMessage(egovMessageSource.getMessage("errors.file.transfer")); //파일전송중 오류가 발생했습니다.
		}
		
		return jsonResult;
	}
	
	@RequestMapping(value = "/decms/shop/goods/modifyMultipleGoods.json", method = RequestMethod.POST)
	@ResponseBody
	public JsonResult modifyMultipleGoods(HttpServletRequest req) {
		JsonResult jsonResult = new JsonResult();
		
		String[] goodsIds = req.getParameterValues("goodsIds[]");
		String action = req.getParameter("action");
		
		try {
			for (String goodsId : goodsIds) {
				GoodsVO goods = new GoodsVO();
				goods.setGoodsId(goodsId);
				goods.setRegistSttusCode(action);
				goodsService.updateGoodsRegistSttus(goods);
			}

			jsonResult.setSuccess(true);
			jsonResult.setMessage(egovMessageSource.getMessage("success.common.update")); //정상적으로 수정되었습니다.
			
		} catch(Exception e) {
			loggerError(LOGGER, e);
			jsonResult.setSuccess(false);
			jsonResult.setMessage(egovMessageSource.getMessage("fail.common.update")); //수정이 실패하였습니다.
		}
		
		return jsonResult;
	}
}
