package modoo.front.shop.goods.order.web;

import java.math.BigDecimal;
import java.util.List;

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
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import egovframework.com.cmm.EgovMessageSource;
import egovframework.com.cmm.LoginVO;
import egovframework.com.cmm.util.EgovUserDetailsHelper;
import egovframework.rte.psl.dataaccess.util.EgovMap;
import modoo.module.common.service.JsonResult;
import modoo.module.common.web.CommonDefaultController;
import modoo.module.shop.goods.cart.service.CartItem;
import modoo.module.shop.goods.cart.service.CartService;
import modoo.module.shop.goods.cart.service.CartVO;
import modoo.module.shop.goods.info.service.GoodsItemVO;
import modoo.module.shop.goods.info.service.GoodsService;
import modoo.module.shop.goods.info.service.GoodsVO;
import net.sf.json.JSONArray;

@Controller
public class CartController extends CommonDefaultController{

	private static final Logger LOGGER = LoggerFactory.getLogger(CartController.class);
			
	@Resource(name="cartService")
	private CartService cartService;

	@Resource(name="goodsService")
	private GoodsService goodsService;

	@Resource(name = "egovMessageSource")
	protected EgovMessageSource egovMessageSource;
	
	
	/**
	 * 장바구니 이동
	 * @param cart
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/shop/goods/cart.do")
	public String cart(CartVO cart,Model model) throws Exception {
		if(!EgovUserDetailsHelper.isAuthenticated()) {
			return "redirect:/index.do";
		}
		LoginVO user = (LoginVO)EgovUserDetailsHelper.getAuthenticatedUser();

		
		cart.setSearchOrdrrId(user.getId());
		cart.setCartAddAt("Y");  
		
		List<EgovMap> resultList=(List<EgovMap>) cartService.selectCartList(cart);
		
		for(EgovMap m : resultList){
			GoodsVO goods = new GoodsVO();
			goods=(GoodsVO)m.get("goods");
			m.put("goods",goods);
			
			List<CartItem> cartItemList = (List<CartItem>)m.get("cartItemList");
			m.put("cartItemList", cartItemList);
			
			List<GoodsItemVO> goodsItemList = (List<GoodsItemVO>)m.get("goodsItemList");
			m.put("goodsItemList", goodsItemList);
		}
		model.addAttribute("cartList",resultList);
		
		return "modoo/front/shop/order/cart";
	}		
	
	
	/**
	 *장바구니 상세 
	 * @param cartNo
	 * @return
	 */
	@RequestMapping("/shop/goods/selectCart.json")
	@ResponseBody
	public JsonResult selectCart(@RequestParam("cartNo") java.math.BigDecimal cartNo){
		
		JsonResult result = new JsonResult();
		
		try {
			CartVO cart = new CartVO();
			cart.setCartNo(cartNo);
			EgovMap cartMap = cartService.selectCart(cart);
			
			if(cartMap!=null){
				result.put("result", cartMap);
				
				result.setMessage("success.common.select");
				result.setSuccess(true);
			}
		} catch (Exception e) {
			loggerError(LOGGER, e);
			result.setMessage("fail.common.select");
			result.setSuccess(false);
		}
		return result;
	}
	
	/*
	 * 장바구니 등록
	 * @param cart
	 * @return 
	 */
	@ResponseBody
	@RequestMapping(value="/shop/goods/insertCart.json", method=RequestMethod.POST)
	public JsonResult insertCart(@RequestBody CartVO cart){
		
		JsonResult result = new JsonResult();
		try {
			LoginVO user = (LoginVO)EgovUserDetailsHelper.getAuthenticatedUser();
			
			GoodsVO goods = new GoodsVO();
			goods.setGoodsId(cart.getGoodsId());
			goods = goodsService.selectGoods(goods);
			
			result.setSuccess(true);
			if(StringUtils.isEmpty(cart.getGoodsId())){
				result.setSuccess(false);
				result.setMessage(egovMessageSource.getMessage("fail.common.insert"));
				return result;
			}else{
				CartVO searchVO = new CartVO();
				searchVO.setCartAddAt("Y"); 
				searchVO.setSearchOrdrrId(user.getId());
				int cartChkNo = cartService.selectCartListCnt(searchVO);
				if(cartChkNo>9){
					result.setSuccess(false);
					result.setMessage("장바구니는 최대 10개까지 담을 수 있습니다.");
					return result;
				}
			}
			if(!"Y".equals(cart.getExprnUseAt())){
				if("SBS".equals(cart.getGoodsKndCode())){
					if("WEEK".equals(cart.getSbscrptCycleSeCode())) {
						if(cart.getSbscrptWeekCycle() == null || cart.getSbscrptDlvyWd() == null) {
							result.setSuccess(false);
							result.setMessage(egovMessageSource.getMessage("fail.common.insert"));
							return result;
						}
					}else if("MONTH".equals(cart.getSbscrptCycleSeCode())) {
						if(cart.getSbscrptMtCycle() == null || cart.getSbscrptDlvyDay() == null) {
							result.setSuccess(false);
							result.setMessage(egovMessageSource.getMessage("fail.common.insert"));
							return result;
						}
					}
				}
			}
			
			if(cart.getOrderCo() == null || cart.getOrderCo() <= 0) {
				result.setSuccess(false);
				result.setMessage(egovMessageSource.getMessage("fail.common.insert"));
				return result;
			}
			
			
			
			//첫구독 필수 검사
			if(!"Y".equals(cart.getExprnUseAt())){
				if("Y".equals(goods.getFrstOptnEssntlAt())) {
					List<String> cartItemList = cart.getCartItemIdList();
					List<GoodsItemVO> fGitemList = goods.getfGitemList();
					int cnt = 0;
					if(cartItemList != null && fGitemList != null) {
						for(GoodsItemVO gitem : fGitemList) {
							for(String citem : cartItemList) {
								if(citem!=null){
									if(citem.equals(gitem.getGitemId())) {
										cnt++;
									}
								}
							}
						}
					}
					if(cnt == 0) {
						result.setSuccess(false);
						result.setMessage("첫 구독 옵션을 선택하세요!");
						return result;
					}
				}
			}
			cart.setOrdrrId(user.getId());
			cart.setCartAddAt("Y"); // 단일 구독(결제)
			cart.setSearchOrdrrId(user.getId());
			//동일상품 장바구니 여부
			int exprnChkCnt = cartService.selectCartExistCnt(cart);
			
			if(exprnChkCnt>0){
				result.setSuccess(false);
				result.setMessage("장바구니의 동일한 상품이 있습니다. 옵션을 변경하려면 장바구니에서 변경해주세요.");
			}
			
			if(result.isSuccess()){
				java.math.BigDecimal cartno =  cartService.insertCart(cart);
				cart.setSearchOrdrrId(user.getId());
				Integer cartCnt = cartService.selectCartListCnt(cart);
				result.put("cartCnt", cartCnt);
				result.setSuccess(true);
				//result.setMessage(egovMessageSource.getMessage("success.common.insert"));
				result.setMessage("장바구니에 상품이 담겼습니다.<br/>장바구니로 이동하시겠습니까?");
			}
			
		} catch (Exception e) {
			loggerError(LOGGER, e);
			result.setSuccess(false);
			result.setMessage(egovMessageSource.getMessage("fail.common.insert"));
		}
		return result;
	}
	
	/**
	 * 장바구니 목록 
	 * @param searchVO
	 * @return 
	 * 
	 */
	@RequestMapping(value="/shop/goods/selectCartList.json",method=RequestMethod.GET)
	@ResponseBody
	public JsonResult selectCartList(CartVO searchVO){
		
		JsonResult result = new JsonResult();
		
		try {
			LoginVO user = (LoginVO)EgovUserDetailsHelper.getAuthenticatedUser();
			
			searchVO.setSearchOrdrrId(user.getId());
			searchVO.setCartAddAt("Y");
			List<?> resultList=cartService.selectCartList(searchVO); 
			Integer totCartCnt = cartService.selectCartListCnt(searchVO);
			
			result.setSuccess(true);
			result.put("resultList", resultList);
			result.put("totCartCnt", totCartCnt);
			
		} catch (Exception e) {
			loggerError(LOGGER, e);
			result.setSuccess(false);
			result.setMessage(egovMessageSource.getMessage("fail.common.select"));
		}
		return result;
	}
	
	/*
	 * 장바구니 수정
	 * @param cart
	 * @return
	 *  
	 */
	@RequestMapping(value="/shop/goods/updateCart.json" , method=RequestMethod.POST)
	@ResponseBody
	public JsonResult deleteCart(@RequestBody CartVO cart){

		JsonResult result = new JsonResult();
		try {
			
			if("SBS".equals(cart.getGoodsKndCode())){
				if("WEEK".equals(cart.getSbscrptCycleSeCode())) {
					if(cart.getSbscrptWeekCycle() == null || cart.getSbscrptDlvyWd() == null) {
						result.setSuccess(false);
						result.setMessage(egovMessageSource.getMessage("fail.common.update"));
					}
				}else if("MONTH".equals(cart.getSbscrptCycleSeCode())) {
					if(cart.getSbscrptMtCycle() == null || cart.getSbscrptDlvyDay() == null) {
						result.setSuccess(false);
						result.setMessage(egovMessageSource.getMessage("fail.common.update"));
					}
				}
			}
			
			if(cart.getOrderCo() == null || cart.getOrderCo() <= 0) {
				result.setSuccess(false);
				result.setMessage(egovMessageSource.getMessage("fail.common.update"));
			}
			
				result.setSuccess(true);
				result.setMessage(egovMessageSource.getMessage("success.common.update"));
				cartService.updateCart(cart);
				
			
		} catch (Exception e) {
			loggerError(LOGGER, e);
			result.setSuccess(false);
			result.setMessage(egovMessageSource.getMessage("fail.common.update"));
		}
		return result;
	}
	/*
	 * 장바구니 삭제
	 * @param cart
	 * @return
	 *  
	 */
	@RequestMapping(value="/shop/goods/deleteCart.json" , method=RequestMethod.POST)
	@ResponseBody
	public JsonResult deleteCart(HttpServletRequest req){
		
		JsonResult result = new JsonResult();
		
		try {
			String[] delCartNoList=req.getParameterValues("delCartNoList");
			
			for(String i : delCartNoList){
				CartVO cart = new CartVO();
				cart.setCartNo(new BigDecimal(i));
				cartService.updateCartClose(cart);
				result.setSuccess(true);
				result.setMessage(egovMessageSource.getMessage("success.common.delete"));
			}
			
		} catch(Exception e) {
			loggerError(LOGGER, e);
			result.setSuccess(false);
			result.setMessage(egovMessageSource.getMessage("fail.common.delete"));
		}
		
		return result;
	}
	
}