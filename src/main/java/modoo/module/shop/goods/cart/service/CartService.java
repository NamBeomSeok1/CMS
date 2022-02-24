package modoo.module.shop.goods.cart.service;

import java.util.List;

import egovframework.rte.psl.dataaccess.util.EgovMap;

public interface CartService {
	
	/**
	 * 장바구니 목록
	 * @param searchVO
	 * @return
	 * @throws Exception
	 */
	List<?> selectCartList(CartVO searchVO) throws Exception;
	
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
	java.math.BigDecimal insertCart(CartVO cart) throws Exception;
	
	/**
	 * 장바구니 수정
	 * @param cart
	 * @throws Exception
	 */
	
	void updateCart(CartVO cart) throws Exception;
	
	/**
	 * 장바구니 닫기
	 * @param cart
	 * @return
	 * @throws Exception
	 */
	void updateCartClose(CartVO cart) throws Exception;
	
	
	/**
	 * 동일상품 장바구니 여부
	 * @param cart
	 * @return
	 * @throws Exception
	 */
	int selectCartExistCnt(CartVO cart) throws Exception;
}
