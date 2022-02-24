package modoo.module.shop.goods.order.service.impl;

import java.util.List;

import egovframework.rte.psl.dataaccess.mapper.Mapper;
import egovframework.rte.psl.dataaccess.util.EgovMap;
import modoo.module.shop.goods.dlvy.service.OrderDlvyVO;
import modoo.module.shop.goods.order.service.OrderGroupVO;
import modoo.module.shop.goods.order.service.OrderItemVO;
import modoo.module.shop.goods.order.service.OrderVO;

@Mapper("orderMapper")
public interface OrderMapper {
	
	/**
	 * 주문목록
	 * @param searchVO
	 * @return
	 * @throws Exception
	 */
	List<OrderVO> selectMyOrderList(OrderVO searchVO) throws Exception;
	
	/**
	 * 주문그룹 저장
	 * @param orderGroup
	 * @throws Exception
	 */
	void insertOrderGroup(OrderGroupVO orderGroup) throws Exception;
	
	/**
	 * 주문저장
	 * @param order
	 * @throws Exception
	 */
	void insertOrder(OrderVO order) throws Exception;

	/**
	 * 주문수정
	 * @param order
	 * @throws Exception
	 */
	void updateOrder(OrderVO order) throws Exception;

	/**
	 * 주문 구독변경 수정
	 * @param order
	 * @throws Exception
	 */
	void updateSbsOrder(OrderVO order) throws Exception;
	
	/**
	 * 주문항목 저장
	 * @param orderItem
	 * @throws Exception
	 */
	void insertOrderItem(OrderItemVO orderItem) throws Exception;

	/**
	 * 마이페이지 > 구독중인 상품 목록 갯수
	 * @param searchVO
	 * @return
	 */
	
	int selectSubscribeNowListCnt(OrderVO searchVO);

	/**
	 * 마이페이지 > 구독중인 상품 목록
	 * @param searchVO
	 * @return
	 */
	
	List<?> selectSubscribeNowList(OrderVO searchVO);

	/**
	 * 주문 1개 상세 정보
	 * @param searchVO
	 * @return
	 */
	OrderVO selectOrder(OrderVO searchVO);

	/**
	 * 구독옵션변경 주문 1개 상세 정보
	 * @param searchVO
	 * @return
	 */
	EgovMap selectModOrder(OrderVO searchVO) throws Exception;
	
	
	/**
	 * 주문 1개 옵션 정보
	 * @param searchVO
	 * @return
	 */
	List<?> selectOrderItemList(OrderVO searchVO);

	/**
	 * 주문 1개 옵션 삭제
	 * @param searchVO
	 * @return
	 */
	void deleteOrderItem(OrderItemVO searchVO);

	/**
	 * 주문 그룹 삭제
	 * @param searchVO
	 * @return
	 */
	void deleteOrderGroup(OrderVO order);


	/**
	 * 주문 삭제
	 * @param searchVO
	 * @return
	 */
	void deleteOrder(OrderVO order);
	
	/**
	 * 주문 1개 업체요청정보
	 * @param searchVO
	 * @return
	 */
	List<?> selectOrderQItemList(OrderVO searchVO);
	
	/**
	 * 주문 1개 배송 정보
	 * @param searchVO
	 * @return
	 */
	List<?> selectOrderDlvyList(OrderVO searchVO);

	/**
	 * 주문 최근 배송 정보1개
	 * @param searchVO
	 * @return
	 */
	OrderDlvyVO selectRecetOrderDlvy(OrderVO searchVO);

	/**
	 * 주문 취소
	 * @param searchVO
	 */
	void updateOrderStatus(OrderVO searchVO);

	/**
	 * 같은 주문그룹번호로 주문한 주문건 목록
	 * @param searchVO
	 * @return
	 */
	List<EgovMap> selectOrderListByOrderGroupNo(OrderDlvyVO searchVO);
	
	/**
	 * 교환/반품 접수
	 * @param searchVO
	 */
	void refundOrder(OrderVO searchVO);
	/**
	 * orderDlvyNo로 Order 리턴
	 * @param searchVO
	 * @return
	 */
	OrderVO selectOrderByDlvy(OrderDlvyVO searchVO);
	/**
	 * 구독 이용 횟수
	 * @param searchVO
	 * @return
	 */
	int selectSbsCnt(OrderVO searchVO);

	/**
	 * 1회 체험 구독 이용 횟수
	 * @param searchVO
	 * @return
	 */
	int selectExprnCnt(OrderVO searchVO);

	/**
	 * 이벤트 상품 구매 횟수
	 * @param searchVO
	 * @return
	 */
	int selectEvtGoodsOrderCnt(OrderVO searchVO);
}
