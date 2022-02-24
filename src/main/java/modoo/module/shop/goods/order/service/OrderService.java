package modoo.module.shop.goods.order.service;

import egovframework.rte.psl.dataaccess.util.EgovMap;
import modoo.module.common.service.JsonResult;
import modoo.module.qainfo.service.QainfoVO;
import modoo.module.shop.goods.dlvy.service.OrderDlvyVO;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;

public interface OrderService {

	/**
	 * 주문목록
	 * @param searchVO
	 * @return
	 * @throws Exception
	 */
	List<OrderVO> selectMyOrderList(OrderVO searchVO) throws Exception;

	/**
	 * 주문그룹저장 - 카드결제 승인 이후
	 * @param order
	 * @throws Exception
	 */
	HashMap<String, Object> insertOrderGroupPayFinish(OrderGroupVO orderGroup, HashMap<String, Object> billingMap) throws Exception;

	/**
	 * 주문그룹저장
	 * @param order
	 * @throws Exception
	 */
	HashMap<String, Object> insertOrderGroup(OrderGroupVO orderGroup, HashMap<String, Object> billingMap) throws Exception;

	/**
	 * 주문그룹넣기(이니시스 모듈)
	 * @param orderGroup
	 */
	HashMap<String, Object> insertPaymentOrderGroup(HttpServletRequest request, OrderGroupVO orderGroup) throws Exception;

	/**
	 * 오더그룹No생성
	 * @param orderGroup
	 * @return
	 * @throws Exception
	 */
	BigDecimal orderGroupNo(OrderGroupVO orderGroup)throws Exception;

	/**
	 * 결제정보
	 * @param JsonResult
	 * @param resultMap
	 * @return
	 * @throws Exception
	 */
	JsonResult paymentInfo(JsonResult JsonResult, HashMap<String, Object> resultMap, HttpServletRequest req)throws Exception;


	abstract void saveOrderSetleInfo(HashMap<String, Object> infoMap, HashMap<String, Object> payResult,
									 List<OrderVO> resultList) throws Exception;

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
	 * 주문 그룹 삭제
	 * @param searchVO
	 * @return
	 */
	void deleteOrderGroup(OrderVO order)throws Exception;

	/**
	 * 마이페이지 > 구독중인 상품 목록
	 * @param searchVO
	 * @return
	 */

	int selectSubscribeNowListCnt(OrderVO searchVO);

	/**
	 * 마이페이지 > 구독중인 상품 목록 갯수
	 * @param searchVO
	 * @return
	 */

	List<?> selectSubscribeNowList(OrderVO searchVO);

	/**
	 * 주문 1개 상세 정보
	 * @param searchVO
	 * @return
	 * @throws Exception
	 */

	OrderVO selectOrder(OrderVO searchVO) throws Exception;

	/**
	 * 주문 1개 옵션 삭제
	 * @param searchVO
	 * @return
	 */
	void deleteOrderItem(OrderItemVO searchVO);

	/**
	 * 구독옵션변경 주문 1개 상세 정보
	 * @param searchVO
	 * @return
	 * @throws Exception
	 */
	EgovMap selectModOrder(OrderVO searchVO) throws Exception;

	/**
	 * 주문 1개당 옵션 목록
	 * @param searchVO
	 * @return
	 */

	List<?> selectOrderItemList(OrderVO searchVO);

	/**
	 * 주문 1개 업체요청정보
	 * @param searchVO
	 * @return
	 */
	List<?> selectOrderQItemList(OrderVO searchVO);

	/**
	 * 주문 1개당 배송 목록
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
	void cancelOrder(OrderVO searchOrderVO);

	/**
	 * 마이페이지 > 주문 교환/환불 목록 카운트
	 * @param searchVO
	 * @return
	 */
	int selectMyRefundListCnt(OrderVO searchVO);
	/**
	 * 마이페이지 > 주문 교환/환불 목록
	 * @param searchVO
	 * @return
	 */
	List<?> selectMyRefundList(OrderVO searchVO);
	/**
	 * 교환/반품접수
	 * @param dlvyVO
	 * @throws Exception
	 */
	void refundOrder(OrderDlvyVO dlvyVO, QainfoVO qainfo) throws Exception;

	/**
	 * 구독해지접수
	 * @param searchVO
	 */
	void stopSubscribeRequest(OrderVO searchVO);
	/**
	 * 구독해지확인
	 * @param searchVO
	 */
	void stopSubscribeConfirm(OrderVO searchVO);
	/**
	 * 구독해지취소
	 * @param searchVO
	 */
	void stopSubscribeCancel(OrderVO searchVO);
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
