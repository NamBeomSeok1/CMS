package modoo.module.shop.goods.dlvy.service.impl;

import java.util.List;

import egovframework.rte.psl.dataaccess.mapper.Mapper;
import egovframework.rte.psl.dataaccess.util.EgovMap;
import modoo.module.shop.goods.dlvy.service.OrderDlvyVO;
import modoo.module.shop.goods.order.service.OrderVO;
import modoo.module.shop.goods.setle.service.OrderSetleVO;

@Mapper("orderDlvyMapper")
public interface OrderDlvyMapper {
	
	/**
	 * 주문배송 목록(SBS)
	 * @param searchVO
	 * @return
	 * @throws Exception
	 */
	List<?> selectOrderDlvyList(OrderDlvyVO searchVO) throws Exception;
	
	/**
	 * 주문배송 목록(GNR)
	 * @param searchVO
	 * @return
	 * @throws Exception
	 */
	EgovMap selectGnrOrderDlvy(OrderDlvyVO searchVO) throws Exception;
	
	/**
	 * 주문배송 목록 카운트
	 * @param searchVO
	 * @return
	 * @throws Exception
	 */
	int selectOrderDlvyListCnt(OrderDlvyVO searchVO) throws Exception;
	
	/**
	 * 주문배송 판매금액
	 * @param searchVO
	 * @return
	 * @throws Exception
	 */
	int selectOrderDlvyListTotAmount(OrderDlvyVO searchVO) throws Exception;
	
	/**
	 * 1개 주문배송
	 * @param orderSetle
	 * @return
	 */
	OrderDlvyVO selectOrderDlvy(OrderSetleVO orderSetle);
	
	/**
	 * 배송 내역 수정
	 * @param orderSetle
	 * @return
	 */
	void updateOrderDlvy(OrderDlvyVO orderDlvy);

	/**
	 * 구독 주문 배송 가격 수정
	 * @param orderSetle
	 * @return
	 */
	void updateSbsOrderDlvy(OrderDlvyVO orderDlvy);

	/**
	 * 송장번호 등록
	 * @param searchVO
	 * @return
	 */
	void updateInvcNo(OrderDlvyVO searchVO);
	
	/**
	 * 상품준비중에서 배송준비중으로 변경
	 * @param searchVO
	 */
	void updateDlvySttusCode(OrderDlvyVO searchVO);
	
	/**
	 * 주문취소 시 이력에 FK로 추가할 ORDER_DLVY_NO 조회
	 * @param searchVO
	 * @return
	 */
	java.math.BigDecimal selectMaxOrderDlvyNoByOrderNo(OrderDlvyVO searchVO);
	/**
	 * 주문 요청 처리상태 변경
	 * @param searchDlvy
	 */
	void updateOrderDlvyStatus(OrderDlvyVO searchDlvy);
	/**
	 * 교환 재발송중으로 변경
	 * @param searchVO
	 */
	void updateExchangeStatus(OrderDlvyVO searchVO);
	/**
	 * 마이페이지 교환/환불 목록
	 * @param searchVO
	 * @return
	 */
	List<?> selectMyRefundList(OrderVO searchVO);
	/**
	 * 마이페이지 교환/환불 목록
	 * @param searchVO
	 * @return
	 */
	int selectMyRefundListCnt(OrderVO searchVO);
	/**
	 *  회차별 상세보기 목록 갯수
	 * @param searchVO
	 * @return
	 */
	int selectSubscribeDetailCnt(OrderDlvyVO searchVO);
	/**
	 *  회차별 상세보기 목록
	 * @param searchVO
	 * @return
	 */
	List<?> selectSubscribeDetail(OrderDlvyVO searchVO);
	/**
	 * 취소, 반품, 교환 목록
	 * @param searchVO
	 * @return
	 */
	List<?> selectOrderReqList(OrderDlvyVO searchVO);
	/**
	 * 취소, 반품, 교환 목록 갯수
	 * @param searchVO
	 * @return
	 */
	int selectOrderReqListCnt(OrderDlvyVO searchVO);
	/**
	 * CP > 주문 현황 > 회차별 구독현황 목록
	 * @param searchVO
	 * @return
	 */
	List<?> selectOrderDlvyHist(OrderDlvyVO searchVO);
	/**
	 * CP > 주문 현황 > 회차별 구독현황 목록 갯수
	 * @param searchVO
	 * @return
	 */
	int selectOrderDlvyHistCnt(OrderDlvyVO searchVO);
	/**
	 * 구독해지 > 주문배송 테이블 update
	 * @param dlvyVO
	 */
	void updateOrderDlvyStatusReady(OrderDlvyVO dlvyVO);
	/**
	 * 주문 현황 > 주문 건수, 판매 금액
	 * @param searchVO
	 * @return
	 */
	EgovMap selectOrderStat(OrderDlvyVO searchVO);
	/**
	 * 주문배송 1개 조회
	 * @param orderDlvy
	 * @return
	 */
	OrderDlvyVO selectOrderDlvyByDlvyNo(OrderDlvyVO orderDlvy);

}
