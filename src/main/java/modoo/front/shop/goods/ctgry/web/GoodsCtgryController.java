package modoo.front.shop.goods.ctgry.web;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import egovframework.com.cmm.LoginVO;
import egovframework.com.cmm.util.EgovUserDetailsHelper;
import modoo.module.common.web.CommonDefaultController;
import modoo.module.shop.goods.ctgry.service.GoodsCtgryService;
import modoo.module.shop.goods.ctgry.service.GoodsCtgryVO;

@Controller
public class GoodsCtgryController extends CommonDefaultController {
	
	private static final String EZWEL_GROUP_ID = "GROUP_00000000000001";
	private static final String DEFAULT_PRTNR_ID = "PRTNR_0000";
	private static final String EZWEL_PRTNR_ID = "PRTNR_0001";
	
	@Resource(name = "goodsCtgryService")
	private GoodsCtgryService goodsCtgryService;
	
	/**
	 * 카테고리 메뉴 목록
	 * @param searchVO
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/embed/shop/goods/goodsCtgryLocation.do")
	public String goodsCtgryLocation(@ModelAttribute("searchVO") GoodsCtgryVO searchVO, Model model) throws Exception {
		
		List<GoodsCtgryVO> menuCtgryList = goodsCtgryService.selectGoodsCtgryMenuList(searchVO);
		model.addAttribute("menuCtgryList", menuCtgryList);
		
		return "modoo/front/shop/goods/ctgry/goodsCtgryLocation";
	}
	
	/**
	 * 서브 카테고리 메뉴 목록
	 * @param searchVO
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/embed/shop/goods/goodsSubCtgryLocation.do")
	public String goodsSubCtgryLocation(@ModelAttribute("searchVO") GoodsCtgryVO searchVO, Model model) throws Exception {
		LoginVO user = (LoginVO)EgovUserDetailsHelper.getAuthenticatedUser();
		if(EgovUserDetailsHelper.isAuthenticated()) {
			if(!EgovUserDetailsHelper.getAuthorities().contains("ROLE_SHOP")) {
				if(EZWEL_GROUP_ID.equals(user.getGroupId())) {
					searchVO.setSearchPrtnrId(EZWEL_PRTNR_ID);
				}else {
					searchVO.setSearchPrtnrId(DEFAULT_PRTNR_ID);
				}
			}
		}else {
			searchVO.setSearchPrtnrId(DEFAULT_PRTNR_ID);
		}

		List<GoodsCtgryVO> subCtgryList = goodsCtgryService.selectSubCtgryList(searchVO);
		model.addAttribute("subCtgryList", subCtgryList);
		
		return "modoo/front/shop/goods/ctgry/goodsSubCtgryLocation";
	}
	
	/**
	 * 서브 카테고리 메뉴 목록 (모바일)
	 * @param searchVO
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/embed/shop/goods/goodsMobileCtgryLocation.do")
	public String goodsMobileCtgryLocation(@ModelAttribute("searchVO") GoodsCtgryVO searchVO, Model model) throws Exception {
			LoginVO user = (LoginVO)EgovUserDetailsHelper.getAuthenticatedUser();
			if(EgovUserDetailsHelper.isAuthenticated()) {
				if(!EgovUserDetailsHelper.getAuthorities().contains("ROLE_SHOP")) {
					if(EZWEL_GROUP_ID.equals(user.getGroupId())) {
						searchVO.setSearchPrtnrId(EZWEL_PRTNR_ID);
					}else {
						searchVO.setSearchPrtnrId(DEFAULT_PRTNR_ID);
					}
				}
			}else {
				searchVO.setSearchPrtnrId(DEFAULT_PRTNR_ID);
			}

		List<GoodsCtgryVO> subCtgryList = goodsCtgryService.selectSubCtgryList(searchVO);
		model.addAttribute("subCtgryList", subCtgryList);
		
		return "modoo/front/shop/goods/ctgry/goodsMobileCtgryLocation";
	}

}
