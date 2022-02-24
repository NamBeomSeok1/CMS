package modoo.front.shop.goods.brand.web;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import egovframework.com.cmm.LoginVO;
import egovframework.com.cmm.util.EgovUserDetailsHelper;
import egovframework.rte.ptl.mvc.tags.ui.pagination.PaginationInfo;
import modoo.module.common.service.JsonResult;
import modoo.module.common.web.CommonDefaultController;
import modoo.module.shop.goods.brand.service.GoodsBrandService;
import modoo.module.shop.goods.brand.service.GoodsBrandVO;
import modoo.module.shop.goods.info.service.GoodsService;
import modoo.module.shop.goods.info.service.GoodsVO;

@Controller
public class GoodsBrandController extends CommonDefaultController {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(GoodsBrandController.class);
	
	private static final Integer BRAND_PAGE_UNIT = 12;
	
	private static final String EZWEL_GROUP_ID = "GROUP_00000000000001";
	private static final String DEFAULT_PRTNR_ID = "PRTNR_0000";
	private static final String EZWEL_PRTNR_ID = "PRTNR_0001";
	
	@Resource(name = "goodsBrandService")
	private GoodsBrandService goodsBrandService;
	
	@Resource(name = "goodsService")
	private GoodsService goodsService;
	
	/**
	 * 브랜드관 목록
	 * @param searchVO
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/shop/goods/goodsBrandList.do")
	public String goodsBrandList(@ModelAttribute("searchVO") GoodsBrandVO searchVO, Model model) throws Exception {
		
		PaginationInfo paginationInfo = new PaginationInfo();
		searchVO.setPageUnit(10);
		this.setPagination(paginationInfo, searchVO);
		
		List<?> resultList = goodsBrandService.selectGoodsBrandList(searchVO);
		model.addAttribute("resultList", resultList);
		
		int totalRecordCount = goodsBrandService.selectGoodsBrandListCnt(searchVO);
		paginationInfo.setTotalRecordCount(totalRecordCount);
		model.addAttribute("paginationInfo", paginationInfo);
		
		//임시 
		//return "redirect:/shop/goods/brandGoodsList.do?searchGoodsBrandId=GBRAND_0000000000025";
		
		return "modoo/front/shop/goods/brand/goodsBrandList";
	}
	
	/**
	 * 브랜드관 
	 * @param searchVO
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/shop/goods/brandGoodsList.do")
	public String brandGoodsList(@ModelAttribute("searchVO") GoodsVO searchVO, Model model) throws Exception {
		LoginVO user = (LoginVO)EgovUserDetailsHelper.getAuthenticatedUser();
		model.addAttribute("user", user);
		/*if(EgovUserDetailsHelper.isAuthenticated()) {
			if(!EgovUserDetailsHelper.getAuthorities().contains("ROLE_SHOP")) {
				if(EZWEL_GROUP_ID.equals(user.getGroupId())) {
					searchVO.setSearchPrtnrId(EZWEL_PRTNR_ID);
				}else {
					searchVO.setSearchPrtnrId(DEFAULT_PRTNR_ID);
				}
			}
		}else {
			searchVO.setSearchPrtnrId(DEFAULT_PRTNR_ID);
		}*/
		
		/* 이벤트 관련 권한 정보 */
		if (user != null) {
			if(EZWEL_GROUP_ID.equals(user.getGroupId())) {
				searchVO.setSearchPrtnrId(EZWEL_PRTNR_ID);
			}else {
				searchVO.setSearchPrtnrId(DEFAULT_PRTNR_ID);
			}
		} else {
			searchVO.setSearchPrtnrId(DEFAULT_PRTNR_ID);
		}
		
		if(StringUtils.isEmpty(searchVO.getSearchGoodsBrandId())) {
			return "redirect:/index.do";
		}
		
		GoodsBrandVO brand = new GoodsBrandVO();
		brand.setBrandId(searchVO.getSearchGoodsBrandId());
		brand.setSearchPrtnrId(searchVO.getSearchPrtnrId());
		brand = goodsBrandService.selectGoodsBrand(brand);
		model.addAttribute("brand", brand);
		
		searchVO.setSearchRegistSttusCode("C"); //등록완료
		
		PaginationInfo paginationInfo = new PaginationInfo();
		searchVO.setPageUnit(BRAND_PAGE_UNIT);
		this.setPagination(paginationInfo, searchVO);
		
		//가격 검색
		if(StringUtils.isNotEmpty(searchVO.getSearchOrderField())) {
			if("LPC".equals(searchVO.getSearchOrderField())) {
				searchVO.setSearchOrderType("ASC");
			} else if( "HPC".equals(searchVO.getSearchOrderField())
					|| "RDCNT".equals(searchVO.getSearchOrderField()) 
					|| "LAT".equals(searchVO.getSearchOrderField()) 
					|| "SEL".equals(searchVO.getSearchOrderField()) ) {
				searchVO.setSearchOrderType("DESC");
			}
		} else {
			searchVO.setSearchOrderField("RDCNT"); //기본 인기순
			searchVO.setSearchOrderType("DESC");
		}
		searchVO.setSearchRegistSttusCode("C");
		List<?> resultList = goodsService.selectGoodsList(searchVO);
		model.addAttribute("resultList", resultList);

		int totalRecordCount = goodsService.selectGoodsListCnt(searchVO);
		paginationInfo.setTotalRecordCount(totalRecordCount);
		model.addAttribute("paginationInfo", paginationInfo);
		
		return "modoo/front/shop/goods/brand/brandGoodsList";
	}
	
	
	/**
	 * 브랜드 소개
	 * @param searchVO
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/shop/goods/goodsBrandInfo.do")
	public String goodsBrandInfo(@ModelAttribute("searchVO") GoodsVO searchVO, Model model) throws Exception {
		GoodsBrandVO brand = new GoodsBrandVO();
		brand.setBrandId(searchVO.getSearchGoodsBrandId());
		brand = goodsBrandService.selectGoodsBrand(brand);
		model.addAttribute("brand", brand);
		
		return "modoo/front/shop/goods/brand/goodsBrandInfo";
	}
	
	/**
	 * 브랜드관 페이지 이동
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/shop/goods/brandList.do")
	public String brandList() throws Exception {

		return "modoo/front/shop/goods/brand/brandList";
	}
	
	/**
	 * 브랜드관 가나다순 목록
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "/shop/goods/brandList.json", method=RequestMethod.GET)
	public JsonResult getBrandListData() throws Exception {
		 LoginVO user = (LoginVO)EgovUserDetailsHelper.getAuthenticatedUser();
	        String brandExpsrSeCode = "B2C";
	        if (user != null) {
	            if (EgovUserDetailsHelper.getAuthorities().contains("ROLE_ADMIN")) {
	                brandExpsrSeCode = null;
	            } else {
	                if(EZWEL_GROUP_ID.equals(user.getGroupId())) {
	                    brandExpsrSeCode = "B2B";
	                }else {
	                    brandExpsrSeCode = "B2C";
	                }
	            }
	        } else {
	            brandExpsrSeCode = "B2C";
	        }
		
		JsonResult jsonResult = new JsonResult();
		HashMap<String, Object> searchMap = new HashMap<String, Object>();
		
		GoodsBrandVO brandVO = new GoodsBrandVO();		
		brandVO.setSearchBrandNmStart("가"); brandVO.setSearchBrandNmEnd("나");searchMap.put("brand01", brandVO);
		brandVO = new GoodsBrandVO();
		brandVO.setSearchBrandNmStart("나"); brandVO.setSearchBrandNmEnd("다");searchMap.put("brand02", brandVO);
		brandVO = new GoodsBrandVO();
		brandVO.setSearchBrandNmStart("다"); brandVO.setSearchBrandNmEnd("라");searchMap.put("brand03", brandVO);
		brandVO = new GoodsBrandVO();
		brandVO.setSearchBrandNmStart("라"); brandVO.setSearchBrandNmEnd("마");searchMap.put("brand04", brandVO);
		brandVO = new GoodsBrandVO();
		brandVO.setSearchBrandNmStart("마"); brandVO.setSearchBrandNmEnd("바");searchMap.put("brand05", brandVO);
		brandVO = new GoodsBrandVO();
		brandVO.setSearchBrandNmStart("바"); brandVO.setSearchBrandNmEnd("사");searchMap.put("brand06", brandVO);
		brandVO = new GoodsBrandVO();
		brandVO.setSearchBrandNmStart("사"); brandVO.setSearchBrandNmEnd("아");searchMap.put("brand07", brandVO);
		brandVO = new GoodsBrandVO();
		brandVO.setSearchBrandNmStart("아"); brandVO.setSearchBrandNmEnd("자");searchMap.put("brand08", brandVO);
		brandVO = new GoodsBrandVO();
		brandVO.setSearchBrandNmStart("자"); brandVO.setSearchBrandNmEnd("차");searchMap.put("brand09", brandVO);
		brandVO = new GoodsBrandVO();
		brandVO.setSearchBrandNmStart("차"); brandVO.setSearchBrandNmEnd("카");searchMap.put("brand10", brandVO);
		brandVO = new GoodsBrandVO();
		brandVO.setSearchBrandNmStart("카"); brandVO.setSearchBrandNmEnd("타");searchMap.put("brand11", brandVO);
		brandVO = new GoodsBrandVO();
		brandVO.setSearchBrandNmStart("타"); brandVO.setSearchBrandNmEnd("파");searchMap.put("brand12", brandVO);
		brandVO = new GoodsBrandVO();
		brandVO.setSearchBrandNmStart("파"); brandVO.setSearchBrandNmEnd("하");searchMap.put("brand13", brandVO);
		brandVO = new GoodsBrandVO();
		brandVO.setSearchBrandNmStart("하"); brandVO.setSearchBrandNmEnd(null);searchMap.put("brand14", brandVO);
		brandVO = new GoodsBrandVO();
		brandVO.setSearchBrandNmStart("A"); brandVO.setSearchBrandNmEnd("B");searchMap.put("brandA", brandVO);
		brandVO = new GoodsBrandVO();
		brandVO.setSearchBrandNmStart("B"); brandVO.setSearchBrandNmEnd("C");searchMap.put("brandB", brandVO);
		brandVO = new GoodsBrandVO();
		brandVO.setSearchBrandNmStart("C"); brandVO.setSearchBrandNmEnd("D");searchMap.put("brandC", brandVO);
		brandVO = new GoodsBrandVO();
		brandVO.setSearchBrandNmStart("D"); brandVO.setSearchBrandNmEnd("E");searchMap.put("brandD", brandVO);
		brandVO = new GoodsBrandVO();
		brandVO.setSearchBrandNmStart("E"); brandVO.setSearchBrandNmEnd("F");searchMap.put("brandE", brandVO);
		brandVO = new GoodsBrandVO();
		brandVO.setSearchBrandNmStart("F"); brandVO.setSearchBrandNmEnd("G");searchMap.put("brandF", brandVO);
		brandVO = new GoodsBrandVO();
		brandVO.setSearchBrandNmStart("G"); brandVO.setSearchBrandNmEnd("H");searchMap.put("brandG", brandVO);
		brandVO = new GoodsBrandVO();
		brandVO.setSearchBrandNmStart("H"); brandVO.setSearchBrandNmEnd("I");searchMap.put("brandH", brandVO);
		brandVO = new GoodsBrandVO();
		brandVO.setSearchBrandNmStart("I"); brandVO.setSearchBrandNmEnd("J");searchMap.put("brandI", brandVO);
		brandVO = new GoodsBrandVO();
		brandVO.setSearchBrandNmStart("J"); brandVO.setSearchBrandNmEnd("K");searchMap.put("brandJ", brandVO);
		brandVO = new GoodsBrandVO();
		brandVO.setSearchBrandNmStart("K"); brandVO.setSearchBrandNmEnd("L");searchMap.put("brandK", brandVO);
		brandVO = new GoodsBrandVO();
		brandVO.setSearchBrandNmStart("L"); brandVO.setSearchBrandNmEnd("M");searchMap.put("brandL", brandVO);
		brandVO = new GoodsBrandVO();
		brandVO.setSearchBrandNmStart("M"); brandVO.setSearchBrandNmEnd("N");searchMap.put("brandM", brandVO);
		brandVO = new GoodsBrandVO();
		brandVO.setSearchBrandNmStart("N"); brandVO.setSearchBrandNmEnd("O");searchMap.put("brandN", brandVO);
		brandVO = new GoodsBrandVO();
		brandVO.setSearchBrandNmStart("O"); brandVO.setSearchBrandNmEnd("P");searchMap.put("brandO", brandVO);
		brandVO = new GoodsBrandVO();
		brandVO.setSearchBrandNmStart("P"); brandVO.setSearchBrandNmEnd("Q");searchMap.put("brandP", brandVO);
		brandVO = new GoodsBrandVO();
		brandVO.setSearchBrandNmStart("Q"); brandVO.setSearchBrandNmEnd("R");searchMap.put("brandQ", brandVO);
		brandVO = new GoodsBrandVO();
		brandVO.setSearchBrandNmStart("R"); brandVO.setSearchBrandNmEnd("S");searchMap.put("brandR", brandVO);
		brandVO = new GoodsBrandVO();
		brandVO.setSearchBrandNmStart("S"); brandVO.setSearchBrandNmEnd("T");searchMap.put("brandS", brandVO);
		brandVO = new GoodsBrandVO();
		brandVO.setSearchBrandNmStart("T"); brandVO.setSearchBrandNmEnd("U");searchMap.put("brandT", brandVO);
		brandVO = new GoodsBrandVO();
		brandVO.setSearchBrandNmStart("U"); brandVO.setSearchBrandNmEnd("V");searchMap.put("brandU", brandVO);
		brandVO = new GoodsBrandVO();
		brandVO.setSearchBrandNmStart("V"); brandVO.setSearchBrandNmEnd("W");searchMap.put("brandV", brandVO);
		brandVO = new GoodsBrandVO();
		brandVO.setSearchBrandNmStart("W"); brandVO.setSearchBrandNmEnd("X");searchMap.put("brandW", brandVO);
		brandVO = new GoodsBrandVO();
		brandVO.setSearchBrandNmStart("X"); brandVO.setSearchBrandNmEnd("Y");searchMap.put("brandX", brandVO);
		brandVO = new GoodsBrandVO();
		brandVO.setSearchBrandNmStart("Y"); brandVO.setSearchBrandNmEnd("Z");searchMap.put("brandY", brandVO);
		brandVO = new GoodsBrandVO();
		brandVO.setSearchBrandNmStart("Z"); brandVO.setSearchBrandNmEnd("가");searchMap.put("brandZ", brandVO);
		brandVO = new GoodsBrandVO();
		brandVO.setSearchBrandNmStart("가"); brandVO.setSearchBrandNmEnd("Z");searchMap.put("brandEtc", brandVO);

		Iterator<String> keys = searchMap.keySet().iterator();
        while (keys.hasNext()){
            String key = keys.next();

            GoodsBrandVO searchVO = (GoodsBrandVO) searchMap.get(key);
            searchVO.setBrandExpsrSeCode(brandExpsrSeCode);
            List<GoodsBrandVO> goodsBrandList = goodsBrandService.selectGoodsBrandByChar(searchVO);
			
            jsonResult.put(key, goodsBrandList);
        }

		return jsonResult;
	}

}
