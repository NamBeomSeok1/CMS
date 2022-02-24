package modoo.module.shop.goods.dlvy.service;

import java.util.List;

import egovframework.rte.psl.dataaccess.util.EgovMap;
import modoo.module.common.service.JsonResult;
import modoo.module.shop.goods.dlvy.service.OrderDlvyVO;
import modoo.module.shop.goods.order.service.OrderVO;
import modoo.module.shop.goods.setle.service.OrderSetleVO;

import javax.servlet.http.HttpServletRequest;

public interface OrderDlvyService {
	
	/**
	 * 주문배송 목록
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
	 * 배송 내역 1개
	 * @param orderSetle
	 * @return
	 */
	OrderDlvyVO selectOrderDlvy(OrderSetleVO orderSetle);
	
	/**
	 * 배송 내역 1개
	 * @param orderSetle
	 * @return
	 */
	OrderDlvyVO selectOrderDlvyByDlvyNo(OrderDlvyVO orderDlvy);

	/**
	 * 배송 내역 수정
	 * @param orderSetle
	 * @return
	 */
	void updateOrderDlvy(OrderDlvyVO orderDlvy);

	/**
	 * 송장번호 등록
	 * @param searchVO
	 */
	void updateInvcNo(OrderDlvyVO searchVO);
	
	/**
	 * 상품준비중에서 배송준비중으로 변경
	 * @param searchVO
	 */
	void updateDlvySttusCode(OrderDlvyVO searchVO);
	/**
	 * 요청 처리상태 변경
	 * @param searchVO
	 * @param mobileYn 
	 * @throws Exception 
	 */
	void modifyProcessStatus(HttpServletRequest request, OrderDlvyVO searchVO, String mobileYn, JsonResult jsonResult) throws Exception;
	/**
	 * 교환 재발송중으로 변경
	 * @param searchVO
	 */
	void updateExchangeStatus(OrderDlvyVO searchVO);
	/**
	 * 회차별 상세보기 목록 갯수
	 * @param searchVO
	 * @return
	 */
	int selectSubscribeDetailCnt(OrderDlvyVO searchVO);
	/**
	 * 회차별 상세보기 목록
	 * @param searchVO
	 * @return
	 */
	List<?> selectSubscribeDetail(OrderDlvyVO searchVO);
	/**
	 * 취소, 교환 반품 목록
	 * @param searchVO
	 * @return
	 */
	List<?> selectOrderReqList(OrderDlvyVO searchVO);
	/**
	 * 취소, 교환, 반품 목록 갯수
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
	 * CP > 주문 현황 > 회차별 구독현황 갯수
	 * @param searchVO
	 * @return
	 */
	int selectOrderDlvyHistCnt(OrderDlvyVO searchVO);
	/**
	 * 주문 현황 > 주문 건수, 판매 금액
	 * @param searchVO
	 * @return
	 */
	EgovMap selectOrderStat(OrderDlvyVO searchVO);
	
}
