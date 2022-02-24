package modoo.module.shop.goods.setle.service.impl;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;

import egovframework.rte.psl.dataaccess.mapper.Mapper;
import egovframework.rte.psl.dataaccess.util.EgovMap;
import modoo.module.shop.goods.dlvy.service.OrderDlvyVO;
import modoo.module.shop.goods.order.service.OrderVO;
import modoo.module.shop.goods.setle.service.OrderSetleVO;

@Mapper("orderSetleMapper")
public interface OrderSetleMapper {
	
	/**
	 * 결제 목록
	 * @param searchVO
	 * @return
	 */
	List<?> selectOrderSetleList(OrderSetleVO searchVO);
	
	/**
	 * 결제 목록 갯수
	 * @param searchVO
	 * @return
	 */
	int selectOrderSetleListCnt(OrderSetleVO searchVO);

	/**
	 * 결제 상세
	 * @param searchVO
	 * @return
	 */
	OrderSetleVO selectOrderSetle(OrderSetleVO searchVO);
	
	/**
	 * 결제 등록
	 * @param orderSetle
	 * @return
	 */
	void insertSelectOrder(OrderSetleVO orderSetle);
	
	/**
	 * 결제 배송지 등록 
	 * @param map
	 * @return
	 */
	void insertNextSTN_ORDER_DLVY(HashMap<String, Object> map);

	/**
	 * 정산 지급일 수정
	 * @param orderSetle
	 */
	void updateExcclcPrarnde(OrderSetleVO orderSetle);
	/**
	 * 취소할 결제 내역 조회
	 * @param orderVO
	 * @return
	 */
	EgovMap selectOrderSetleToCancel(OrderVO orderVO);

	/**
	 * 결제 내역 수정
	 * @param orderSetle
	 */
	void updateOrderSetle(OrderSetleVO orderSetle);

	// 부분취소할 카드 결제 금액 조회
	BigDecimal selectConfirmPrice(OrderVO orderVO);

	// 이지웰 결제 내역 조회
	List<EgovMap> selectEzwelSetleToCancel(OrderVO searchOrderVO);

	// 카드 결제 내역 조회
	List<EgovMap> selectCardSetleToCancel(OrderVO searchOrderVO);
	
	/**
	 * 구독 해지
	 * @param searchVO
	 */
	void stopSubscribeConfirm(OrderVO searchVO);
	/**
	 * 구독 해지 취소
	 * @param searchVO
	 */
	void stopSubscribeCancel(OrderVO searchVO);
	
	/** 결제 취소할 내역
	 * 
	 * @param searchOrderVO
	 * @return
	 */
	List<?> selectOrderSetleListToCancel(OrderVO searchOrderVO);

	List<OrderSetleVO> selectNextOrderSetle(OrderDlvyVO searchVO);

}
