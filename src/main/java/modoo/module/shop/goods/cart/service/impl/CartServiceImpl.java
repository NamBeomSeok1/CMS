package modoo.module.shop.goods.cart.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;
import egovframework.rte.fdl.idgnr.EgovIdGnrService;
import egovframework.rte.psl.dataaccess.util.EgovMap;
import modoo.module.shop.goods.cart.service.CartItem;
import modoo.module.shop.goods.cart.service.CartService;
import modoo.module.shop.goods.cart.service.CartVO;
import modoo.module.shop.goods.info.service.GoodsItemVO;
import modoo.module.shop.goods.info.service.GoodsVO;
import modoo.module.shop.goods.info.service.impl.GoodsItemMapper;
import modoo.module.shop.goods.info.service.impl.GoodsMapper;

@Service("cartService")
public class CartServiceImpl extends EgovAbstractServiceImpl implements CartService {
	
	@Resource(name = "cartMapper")
	private CartMapper cartMapper;

	@Resource(name = "goodsMapper")
	private GoodsMapper goodsMapper;
	
	@Resource(name = "cartIdGnrService")
	private EgovIdGnrService cartIdGnrService;
	
	@Resource(name = "cartItemIdGnrService")
	private EgovIdGnrService cartItemIdGnrService;
	
	@Resource(name = "goodsItemMapper")
	private GoodsItemMapper goodsItemMapper;
	
	/**
	 * 장바구니 목록
	 */
	@Override
	public List<EgovMap> selectCartList(CartVO searchVO) throws Exception {
		List<EgovMap> cartList = cartMapper.selectCartList(searchVO);
		for(EgovMap cart: cartList) {
			CartItem cartItem = new CartItem();
			cartItem.setCartNo((BigDecimal) cart.get("cartNo"));
			List<CartItem>  cartItemList = cartMapper.selectCartItemList(cartItem);
			cart.put("cartItemList", cartItemList);
			
			//업쳉요청사항 목록
			GoodsItemVO goodsItem = new GoodsItemVO();
			goodsItem.setGoodsId((String) cart.get("goodsId"));
			goodsItem.setGitemSeCode("Q");	
			List<GoodsItemVO> goodsItemList = goodsItemMapper.selectGoodsItemList(goodsItem);
			cart.put("goodsItemList", goodsItemList);
			
			//상품정보
			GoodsVO goods = new GoodsVO();
			goods.setGoodsId((String)cart.get("goodsId"));
			goods=goodsMapper.selectGoods(goods);
			cart.put("goods", goods);
		}
		
		return cartList;
	}

	/**
	 * 장바구니 목록 카운트
	 */
	@Override
	public int selectCartListCnt(CartVO searchVO) throws Exception {
		return cartMapper.selectCartListCnt(searchVO);
	}
	
	/**
	 * 장바구니 상세
	 */
	@Override
	public EgovMap selectCart(CartVO searchVO) throws Exception {
		
		EgovMap cart = cartMapper.selectCart(searchVO);
		//카트 아이템 리스트 
		CartItem cartItem = new CartItem();
		cartItem.setCartNo((BigDecimal)cart.get("cartNo"));
		List<CartItem> cartItemList = cartMapper.selectCartItemList(cartItem);
		for(CartItem c : cartItemList){
			if(c.getGitemSeCode().equals("D")){
			cart.put("ditem", c);
			}else if(c.getGitemSeCode().equals("A")){
			cart.put("aitem", c);
			}else if(c.getGitemSeCode().equals("F")){
			cart.put("fitem", c);
			}
		}
		
		//카트 상품정보
		GoodsVO goods = new GoodsVO();
		goods.setGoodsId((String)cart.get("goodsId"));
		goods=goodsMapper.selectGoods(goods);
		cart.put("goods", goods);
		
		//상품 아이템 리스트
		GoodsItemVO goodsItem = new GoodsItemVO();
		goodsItem.setGoodsId((String)cart.get("goodsId"));
		goodsItem.setGitemSeCode("D");
		List<GoodsItemVO> doptList = goodsItemMapper.selectGoodsItemList(goodsItem);
		cart.put("dopt", doptList);
		goodsItem.setGitemSeCode("A");
		List<GoodsItemVO> aoptList = goodsItemMapper.selectGoodsItemList(goodsItem);
		cart.put("aopt", aoptList);
		goodsItem.setGitemSeCode("F");
		List<GoodsItemVO> foptList = goodsItemMapper.selectGoodsItemList(goodsItem);
		cart.put("fopt", foptList);
		return cart;
	}

	/**
	 * 장바구니 저장
	 */
	@Override
	public java.math.BigDecimal insertCart(CartVO cart) throws Exception {
		java.math.BigDecimal cartNo = cartIdGnrService.getNextBigDecimalId();
		cart.setCartNo(cartNo);
		
		cartMapper.insertCart(cart);
		
		GoodsItemVO goodsItem = new GoodsItemVO();
		CartItem cartItem = new CartItem(); 
		
		//장바구니 추가 항목 저장
		if(cart.getCartItemIdList()!=null && cart.getCartItemIdList().size()>0){
			for(String id:cart.getCartItemIdList()){
				if(StringUtils.isNotEmpty(id)){
					java.math.BigDecimal cartItemNo = cartItemIdGnrService.getNextBigDecimalId();
					goodsItem.setGitemId(id);
					goodsItem=goodsItemMapper.selectGoodsItem(goodsItem);
					cartItem.setCartItemNo(cartItemNo);
					cartItem.setCartNo(cart.getCartNo());
					cartItem.setGitemId(id);
					cartItem.setGitemNm(goodsItem.getGitemNm());
					cartItem.setGitemPc(goodsItem.getGitemPc());
					cartItem.setGitemSeCode(goodsItem.getGitemSeCode());
					cartMapper.insertCartItem(cartItem);
				}
			}
		}
		//추가항목 저장
		if(cart.getCartItemList() != null && cart.getCartItemList().size() > 0) {
			for(CartItem item : cart.getCartItemList()) {
				java.math.BigDecimal cartItemNo = cartItemIdGnrService.getNextBigDecimalId();
				item.setCartItemNo(cartItemNo);
				item.setCartNo(cart.getCartNo());
				cartMapper.insertCartItem(item);
			}
		}else{
			return cartNo;
		}
		
		return cartNo;
	}

	@Override
	public void updateCartClose(CartVO cart) throws Exception {
		cartMapper.updateCartClose(cart);
	}
	
	//장바구니 수정
	@Override
	public void updateCart(CartVO cart) throws Exception {
		
		GoodsItemVO goodsItem = new GoodsItemVO();
		CartItem cartItem = new CartItem(); 
		
		cartItem.setCartNo(cart.getCartNo());
		cartMapper.deleteCartItem(cartItem);
		
		if(cart.getCartItemIdList()!=null && cart.getCartItemIdList().size()>0){
			for(String id:cart.getCartItemIdList()){
				if(StringUtils.isNotEmpty(id)){
					java.math.BigDecimal cartItemNo = cartItemIdGnrService.getNextBigDecimalId();
					goodsItem.setGitemId(id);
					goodsItem=goodsItemMapper.selectGoodsItem(goodsItem);
					cartItem.setCartItemNo(cartItemNo);
					cartItem.setCartNo(cart.getCartNo());
					cartItem.setGitemId(id);
					cartItem.setGitemNm(goodsItem.getGitemNm());
					cartItem.setGitemPc(goodsItem.getGitemPc());
					cartItem.setGitemSeCode(goodsItem.getGitemSeCode());
					cartMapper.insertCartItem(cartItem);
				}
			}
		}
		
		cartMapper.updateCart(cart);
	}
	
	//장바구니 체험구독 여부
	 @Override
	public int selectCartExistCnt(CartVO cart) throws Exception {
		return cartMapper.selectCartExistCnt(cart);
	}
}
