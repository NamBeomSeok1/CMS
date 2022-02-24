package modoo.front.shop.goods.event.web;

import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import egovframework.com.cmm.LoginVO;
import egovframework.com.cmm.util.EgovUserDetailsHelper;
import egovframework.rte.ptl.mvc.tags.ui.pagination.PaginationInfo;
import modoo.module.common.service.JsonResult;
import modoo.module.common.web.CommonDefaultController;
import modoo.module.event.service.GoodsEventService;
import modoo.module.event.service.impl.GoodsEventVO;
import modoo.module.shop.goods.info.service.GoodsService;

@Controller
public class GoodsEventController extends CommonDefaultController {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(GoodsEventController.class);
	
	private static final String EZWEL_GROUP_ID = "GROUP_00000000000001";
	private static final String DEFAULT_PRTNR_ID = "PRTNR_0000";
	private static final String EZWEL_PRTNR_ID = "PRTNR_0001";
	
	@Resource(name = "goodsEventService")
	private GoodsEventService goodsEventService;
	
	@Resource(name = "goodsService")
	private GoodsService goodsService;

	/**
	 * 이벤트관 페이지 이동
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/shop/event/goodsEventLocation.do")
	public String goodsEventLocation() throws Exception {

		return "modoo/front/shop/goods/event/events/eventLocation";
	}
	

	/**
	 * 이벤트관 location
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/shop/event/goodsEventList.do")
	public String goodsEventList() throws Exception {
		
		return "modoo/front/shop/goods/event/goodsEventList";
	}
	
	/**
	 * 이벤트관 목록
	 * @param searchVO
	 * @param model
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/shop/event/eventList.json", method=RequestMethod.GET)
	public JsonResult eventList(GoodsEventVO searchVO, Model model) {
		JsonResult result = new JsonResult();

		try {
			
			LoginVO user = (LoginVO)EgovUserDetailsHelper.getAuthenticatedUser();
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
			
			/** 페이징 처리 */
			PaginationInfo paginationInfo = new PaginationInfo();
			this.setPagination(paginationInfo, searchVO);
			int totalRecordCount = goodsEventService.selectFrontGoodsEventListCnt(searchVO);
			paginationInfo.setTotalRecordCount(totalRecordCount);
			
			List<?> dataList = goodsEventService.selectFrontGoodsEventList(searchVO);
			
			result.put("list", dataList);
			result.put("paginationInfo", paginationInfo);
			result.setMessage("");
			result.setSuccess(true);
			
		}catch (Exception e) {
			e.printStackTrace();
			result.setMessage("");
			result.setSuccess(false);
		}
	
		return result;
	}
	
	/**
	 * 2021 소확행 이벤트 페이지 1
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/shop/event/open1.do")
	public String open1() throws Exception {

		return "modoo/front/shop/goods/event/events/open1";
	}
	
	/** 
	 * 2021 소확행 이벤트 페이지 2 (이지웰)
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/shop/event/open2.do")
	public String open2() throws Exception {
		String url = "modoo/front/shop/goods/event/events/open2";
		
		/*LoginVO user = (LoginVO)EgovUserDetailsHelper.getAuthenticatedUser();
		if(EgovUserDetailsHelper.isAuthenticated()) {
			if(!EgovUserDetailsHelper.getAuthorities().contains("ROLE_SHOP")) {
				if(EZWEL_GROUP_ID.equals(user.getGroupId())) {
					url = "modoo/front/shop/goods/event/events/open2";
				}else {
					url = "redirect:/index.do";
				}
			}
		}else {
			url = "redirect:/index.do";
		}*/
		
		return url;
	}
	/**
	 * 2021 소확행 이벤트 페이지 2 (B2C)
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/shop/event/open2B2C.do")
	public String open2B2C() throws Exception {
		String url = "modoo/front/shop/goods/event/events/open2B2C";
		
		/*LoginVO user = (LoginVO)EgovUserDetailsHelper.getAuthenticatedUser();
		if(EgovUserDetailsHelper.isAuthenticated()) {
			if(!EgovUserDetailsHelper.getAuthorities().contains("ROLE_SHOP")) {
				if(EZWEL_GROUP_ID.equals(user.getGroupId())) {
					url = "redirect:/index.do";
				}else {
					url = "modoo/front/shop/goods/event/events/open2B2C";
				}
			}
		}else {
			url = "redirect:/index.do";
		}*/
		
		return url;
	}
	
	/**
	 * 건강 지킴이 3총사
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/shop/event/eventHealth.do")
	public String eventHealth() throws Exception {

		return "modoo/front/shop/goods/event/events/eventHealth";
	}

}
