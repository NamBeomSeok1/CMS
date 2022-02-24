package modoo.module.shop.goods.cart.service.impl;

import java.util.List;

import egovframework.rte.psl.dataaccess.mapper.Mapper;
import egovframework.rte.psl.dataaccess.util.EgovMap;
import modoo.module.shop.goods.cart.service.CartItem;
import modoo.module.shop.goods.cart.service.CartVO;

@Mapper("cartMapper")
public interface CartMapper {
	
	/**
	 * 장바구니 목록
	 * @param searchVO
	 * @return
	 * @throws Exception
	 */
	List<EgovMap> selectCartList(CartVO searchVO) throws Exception;
	
	/**
	 * 장바구니 상세
	 * @param searchVO
	 * @return
	 * @throws Exception
	 */
	EgovMap selectCart(CartVO searchVO) throws Exception;
	/**
	 * 장바구니 목록 카운트
	 * @param searchVO
	 * @return
	 * @throws Exception
	 */
	int selectCartListCnt(CartVO searchVO) throws Exception;

	/**
	 * 장바구니 저장
	 * @param cart
	 * @throws Exception
	 */
	void insertCart(CartVO cart) throws Exception;
	
	/**
	 * 장바구니 수정
	 * @param cart
	 * @throws Exception
	 */
	void updateCart(CartVO cart) throws Exception;
	
	/**
	 * 장바구니항목 저장
	 * @param cartItem
	 * @throws Exception
	 */
	void insertCartItem(CartItem cartItem) throws Exception;

	/**
	 * 장바구니항목 삭제
	 * @param cartItem
	 * @throws Exception
	 */
	void deleteCartItem(CartItem cartItem) throws Exception;
	
	/**
	 * 장바구니항목 목록
	 * @param cartItem
	 * @return
	 * @throws Exception
	 */
	List<CartItem> selectCartItemList(CartItem cartItem) throws Exception;
	
	/**
	 * 장바구니 닫기
	 * @param cart
	 * @return
	 * @throws Exception
	 */
	void updateCartClose(CartVO cart) throws Exception;
	
	/**
	 * 동일상품 장바구니 여부
	 * @param car
	 * @return
	 * @throws Exception
	 */
	int selectCartExistCnt(CartVO cart) throws Exception;
}

