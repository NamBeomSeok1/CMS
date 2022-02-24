package modoo.module.shop.goods.dlvy.service.impl;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import modoo.module.common.service.JsonResult;
import modoo.module.mber.info.service.MberVO;
import modoo.module.mber.info.service.impl.MberMapper;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import egovframework.com.cmm.LoginVO;
import egovframework.com.cmm.util.EgovUserDetailsHelper;
import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;
import egovframework.rte.psl.dataaccess.util.EgovMap;
import modoo.module.biling.service.Biling;
import modoo.module.biling.service.EzwelFunc;
import modoo.module.shop.goods.dlvy.service.OrderDlvyService;
import modoo.module.shop.goods.dlvy.service.OrderDlvyVO;
import modoo.module.shop.goods.hist.service.OrderReqHistVO;
import modoo.module.shop.goods.hist.service.impl.OrderReqHistMapper;
import modoo.module.shop.goods.order.service.OrderVO;
import modoo.module.shop.goods.order.service.impl.OrderMapper;
import modoo.module.shop.goods.setle.service.OrderSetleVO;
import modoo.module.shop.goods.setle.service.impl.OrderSetleMapper;

@Service("orderDlvyService")
public class OrderDlvyServiceImpl extends EgovAbstractServiceImpl implements OrderDlvyService {

	@Resource(name = "orderDlvyMapper")
	private OrderDlvyMapper orderDlvyMapper;
	
	@Resource(name = "orderReqHistMapper")
	private OrderReqHistMapper orderReqHistMapper;
	
	@Resource(name = "orderSetleMapper")
	private OrderSetleMapper orderSetleMapper;
	
	@Resource(name = "orderMapper")
	private OrderMapper orderMapper;

	@Resource(name = "mberMapper")
	private MberMapper mberMapper;
	
	/**
	 * 주문배송 목록(SBS)
	 */
	@Override
	public List<?> selectOrderDlvyList(OrderDlvyVO searchVO) throws Exception {
		return orderDlvyMapper.selectOrderDlvyList(searchVO);
	}
	

	@Override
	public EgovMap selectGnrOrderDlvy(OrderDlvyVO searchVO) throws Exception {
	return orderDlvyMapper.selectGnrOrderDlvy(searchVO);
}

	/**
	 * 주문배송 목록 카운트
	 */
	@Override
	public int selectOrderDlvyListCnt(OrderDlvyVO searchVO) throws Exception {
		return orderDlvyMapper.selectOrderDlvyListCnt(searchVO);
	}
	
	/**
	 * 주문배송 판매금액
	 */
	@Override
	public int selectOrderDlvyListTotAmount(OrderDlvyVO searchVO) throws Exception {
		return orderDlvyMapper.selectOrderDlvyListTotAmount(searchVO);
	}

	/**
	 * 주문배송 1개 입력
	 */
	@Override
	public OrderDlvyVO selectOrderDlvy(OrderSetleVO orderSetle) {
		return orderDlvyMapper.selectOrderDlvy(orderSetle);
	}
	@Override
	public void updateOrderDlvy(OrderDlvyVO orderDlvy) {
		orderDlvyMapper.updateOrderDlvy(orderDlvy);
	}
	/**
	 * 송장번호 등록
	 */
	@Override
	public void updateInvcNo(OrderDlvyVO searchVO) {
		orderDlvyMapper.updateInvcNo(searchVO);
	}
	/**
	 * 상품준비중에서 배송준비중으로 변경
	 */
	@Override
	public void updateDlvySttusCode(OrderDlvyVO searchVO) {
		orderDlvyMapper.updateDlvySttusCode(searchVO);
	}

	/**
	 * 요청 처리상태 변경 - CP측
	 * @throws Exception 
	 */
	@Override
	@Transactional
	public void modifyProcessStatus(HttpServletRequest request, OrderDlvyVO searchVO, String mobileYn, JsonResult JsonResult) throws Exception {
		/*System.out.println("================= 취소 요청! ===============");
		
		LoginVO user = (LoginVO)EgovUserDetailsHelper.getAuthenticatedUser();
		searchVO.setLastUpdusrId(user.getUniqId());
		
		System.out.println("setLastUpdusrId : " + user.getUniqId());
		
		OrderVO orderSearchVO = new OrderVO();
		orderSearchVO.setOrderNo(searchVO.getOrderNo());
		orderSearchVO.setLastUpdusrId(user.getUniqId());
		
		*//** 주문 취소 **//*
		// 주문 취소 승인
		if (("C04").equals(searchVO.getReqTyCode())) {
			orderSearchVO.setOrderSttusCode("ST99");
			orderMapper.updateOrderStatus(orderSearchVO);
			cancelOrder(searchVO, mobileYn);
			orderDlvyMapper.updateOrderDlvyStatus(searchVO);
		
		// 주문 취소 반려
		} else if (("C03").equals(searchVO.getReqTyCode())) {
			searchVO.setOrderReqSttusCode("O");
			orderSearchVO.setOrderSttusCode("ST02");
			orderMapper.updateOrderStatus(orderSearchVO);
			orderDlvyMapper.updateOrderDlvyStatus(searchVO);
		}
		
		*//** 교환 **//*
		// 교환 신청 승인
		if (("E02").equals(searchVO.getReqTyCode())) {
			searchVO.setDlvySttusCode("DLVY01");
			orderDlvyMapper.updateOrderDlvyStatus(searchVO);
		
		// 교환 반려
		} else if (("E03").equals(searchVO.getReqTyCode())) {
			searchVO.setOrderReqSttusCode("O");
			orderMapper.updateOrderStatus(orderSearchVO);
			orderDlvyMapper.updateOrderDlvyStatus(searchVO);
		}
		
		*//** 반품 **//*
		// 반품(환불) 신청 승인
		if (("R04").equals(searchVO.getReqTyCode())) {
			orderDlvyMapper.updateOrderDlvyStatus(searchVO);

		// 반품(환불) 반려
		} else if (("R03").equals(searchVO.getReqTyCode())) {
			searchVO.setOrderReqSttusCode("O");
			orderMapper.updateOrderStatus(orderSearchVO);
			orderDlvyMapper.updateOrderDlvyStatus(searchVO);
			
		// 반품(환불) 완료
		} else if (("R05").equals(searchVO.getReqTyCode())) {

			OrderVO orderVO = orderMapper.selectOrderByDlvy(searchVO);
			searchVO.setOrderNo(orderVO.getOrderNo());
			searchVO.setSearchOrderGroupNo(orderVO.getOrderGroupNo());
			searchVO.setDlvySttusCode("DLVY03");
			cancelOrder(searchVO, mobileYn);
			orderDlvyMapper.updateOrderDlvyStatus(searchVO);
		}

		// 이력 생성
		OrderReqHistVO vo = new OrderReqHistVO();
		vo.setOrderDlvyNo(searchVO.getOrderDlvyNo());
		vo.setReqTyCode(searchVO.getReqTyCode());
		orderReqHistMapper.insertOrderReqHist(vo);*/
		LoginVO user = (LoginVO)EgovUserDetailsHelper.getAuthenticatedUser();
		searchVO.setLastUpdusrId(user.getUniqId());

		System.out.println("setLastUpdusrId : " + user.getUniqId());

		OrderVO orderSearchVO = new OrderVO();
		orderSearchVO.setOrderNo(searchVO.getOrderNo());
		orderSearchVO.setLastUpdusrId(user.getUniqId());

		/** 주문 취소 **/
		// 주문 취소 승인
		if (("C04").equals(searchVO.getReqTyCode())) {
			orderSearchVO.setOrderSttusCode("ST99");
			orderMapper.updateOrderStatus(orderSearchVO);
			cancelOrder(request, searchVO, mobileYn, JsonResult);
			orderDlvyMapper.updateOrderDlvyStatus(searchVO);

			// 주문 취소 반려
		} else if (("C03").equals(searchVO.getReqTyCode())) {
			searchVO.setOrderReqSttusCode("O");
			orderSearchVO.setOrderSttusCode("ST02");
			orderMapper.updateOrderStatus(orderSearchVO);
			orderDlvyMapper.updateOrderDlvyStatus(searchVO);
		}

		/** 교환 **/
		// 교환 신청 승인
		if (("E02").equals(searchVO.getReqTyCode())) {
			searchVO.setDlvySttusCode("DLVY01");
			orderDlvyMapper.updateOrderDlvyStatus(searchVO);

			// 교환 반려
		} else if (("E03").equals(searchVO.getReqTyCode())) {
			searchVO.setOrderReqSttusCode("O");
			orderMapper.updateOrderStatus(orderSearchVO);
			orderDlvyMapper.updateOrderDlvyStatus(searchVO);
		}

		/** 반품 **/
		// 반품(환불) 신청 승인
		if (("R04").equals(searchVO.getReqTyCode())) {
			orderDlvyMapper.updateOrderDlvyStatus(searchVO);

			// 반품(환불) 반려
		} else if (("R03").equals(searchVO.getReqTyCode())) {
			searchVO.setOrderReqSttusCode("O");
			orderMapper.updateOrderStatus(orderSearchVO);
			orderDlvyMapper.updateOrderDlvyStatus(searchVO);

			// 반품(환불) 완료
		} else if (("R05").equals(searchVO.getReqTyCode())) {

			OrderVO orderVO = orderMapper.selectOrderByDlvy(searchVO);
			searchVO.setOrderNo(orderVO.getOrderNo());
			searchVO.setSearchOrderGroupNo(orderVO.getOrderGroupNo());
			searchVO.setDlvySttusCode("DLVY03");
			cancelOrder(request, searchVO, mobileYn, JsonResult);
			orderDlvyMapper.updateOrderDlvyStatus(searchVO);
		}

		// 이력 생성
		OrderReqHistVO vo = new OrderReqHistVO();
		vo.setOrderDlvyNo(searchVO.getOrderDlvyNo());
		vo.setReqTyCode(searchVO.getReqTyCode());
		orderReqHistMapper.insertOrderReqHist(vo);

	}
	
	/**
	 * 주문취소
	 * @param searchVO
	 * @throws Exception
	 */
	@Transactional
	public void cancelOrder(HttpServletRequest request, OrderDlvyVO searchVO, String mobileYn, JsonResult jsonResult) throws Exception {
		System.out.println("===================== cancelOrder start !! =====================");

		try {
			LoginVO user = (LoginVO)EgovUserDetailsHelper.getAuthenticatedUser();

			searchVO.setCancelUsrId(user.getUniqId());
			//System.out.println("setLastUpdusrId : " + user.getUniqId());
			//System.out.println("searchVO:" + searchVO.toString());

			OrderVO searchOrderVO = new OrderVO();
			searchOrderVO.setOrderNo(searchVO.getOrderNo());
			searchOrderVO.setOrderGroupNo(searchVO.getSearchOrderGroupNo());

			OrderVO orderInfo = orderMapper.selectOrder(searchOrderVO);

			System.out.println("orderInfo:" + orderInfo.toString());

			MberVO mberSearchVO = new MberVO();
			mberSearchVO.setMberId(orderInfo.getOrdrrId());

			System.out.println("searchVO.getOrdrrId:" + orderInfo.getOrdrrId());

			Biling inicis = new Biling();

			EgovMap orderSetle = (EgovMap) orderSetleMapper.selectOrderSetleToCancel(searchOrderVO);
			OrderSetleVO selteVO = new OrderSetleVO(); //결제이력 저장용 param

			if (orderSetle != null) {

				orderSetle.put("mobileYn", mobileYn);

				selteVO.setSetleTyCode((String) orderSetle.get("setleTyCode"));
				selteVO.setSetleResultTyCode((String) orderSetle.get("setleResultTyCode"));

				// B2C
				if ("CARD".equals(orderSetle.get("setleResultTyCode")) || "HPP".equals(orderSetle.get("setleResultTyCode"))) { //카드결제의 경우
					//System.out.println("#########################" + (String)orderSetle.get("iniSetleConfmNo"));

					searchOrderVO.setIniSetleConfmNo((String)orderSetle.get("iniSetleConfmNo"));
					selteVO.setIniSetleConfmNo((String) orderSetle.get("iniSetleConfmNo"));
					java.math.BigDecimal confirmPrice = orderSetleMapper.selectConfirmPrice(searchOrderVO);
					orderSetle.put("confirmPrice", confirmPrice);
					inicis.partialCancelCardSetle(orderSetle, selteVO);

					java.math.BigDecimal cancelAmount = new BigDecimal(Long.toString((Long)orderSetle.get("setleCardAmount")));
					searchVO.setCancelAmount(cancelAmount);
				}



				List<OrderSetleVO> setleList = orderSetleMapper.selectNextOrderSetle(searchVO);

				System.out.println("===========================================" + selteVO.toString());

				if (!"F".equals((String) orderSetle.get("setleTyCode"))) {
					for (OrderSetleVO setleInfo : setleList) {
						//System.out.println("@@@@@@@@@@@@@@@@@@nextSetle" + setleInfo.toString());
						selteVO.setOrderSetleNo(setleInfo.getOrderSetleNo());
						//System.out.println("@@@@@@@@@@@@@@@@@@updateOrderSetle param:" + selteVO.toString());
						orderSetleMapper.updateOrderSetle(selteVO);
					}

					searchVO.setOrderNo(null);
					//System.out.println("updateOrderDlvyStatus param: " + searchVO.toString());
					orderDlvyMapper.updateOrderDlvyStatus(searchVO);
				}

				jsonResult.put("setleSttusCode", selteVO.getSetleSttusCode());
				jsonResult.setSuccess(true);
				System.out.println("============================jsonResult:" + jsonResult.toString());

			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/*2022-02-17 수정 이전 버전
	public void cancelOrder(OrderDlvyVO searchVO, String mobileYn) throws Exception {
		System.out.println("===================== cancelOrder start !! =====================");
		
		try {
			LoginVO user = (LoginVO)EgovUserDetailsHelper.getAuthenticatedUser();
			
			searchVO.setCancelUsrId(user.getUniqId());
			//System.out.println("setLastUpdusrId : " + user.getUniqId());
			
			OrderVO searchOrderVO = new OrderVO();
			searchOrderVO.setOrderNo(searchVO.getOrderNo());
			searchOrderVO.setOrderGroupNo(searchVO.getSearchOrderGroupNo());

			Biling inicis = new Biling();
			EzwelFunc ezwel = new EzwelFunc();
			
			EgovMap orderSetle = (EgovMap) orderSetleMapper.selectOrderSetleToCancel(searchOrderVO);
			OrderSetleVO selteVO = new OrderSetleVO(); //결제이력 저장용 param
			
			if (orderSetle != null) {

				orderSetle.put("mobileYn", mobileYn);
				
				selteVO.setSetleTyCode((String) orderSetle.get("setleTyCode"));
				selteVO.setSetleResultTyCode((String) orderSetle.get("setleResultTyCode"));

				if ("GROUP_00000000000001".equals(user.getGroupId())) { // 이지웰 계정일 경우
					ezwel.cancelEzwelPointSetle(orderSetle, selteVO); // 이지웰 무조건 취소 (주문내역 조회 목적)
					
					if ("EZCD".equals(orderSetle.get("setleResultTyCode"))) { //포인트, 카드 복합결제의 경우
						//System.out.println("#########################" + (String)orderSetle.get("iniSetleConfmNo"));
						
						searchOrderVO.setIniSetleConfmNo((String) orderSetle.get("iniSetleConfmNo"));
						selteVO.setIniSetleConfmNo((String) orderSetle.get("iniSetleConfmNo"));
						java.math.BigDecimal confirmPrice = orderSetleMapper.selectConfirmPrice(searchOrderVO);
						orderSetle.put("confirmPrice", confirmPrice);
						inicis.partialCancelCardSetle(orderSetle, selteVO);
						
					} else if ("CARD".equals(orderSetle.get("setleResultTyCode"))) { //카드결제의 경우
						//System.out.println("#########################" + (String)orderSetle.get("iniSetleConfmNo"));
						
						searchOrderVO.setIniSetleConfmNo((String) orderSetle.get("iniSetleConfmNo"));
						selteVO.setIniSetleConfmNo((String) orderSetle.get("iniSetleConfmNo"));
						java.math.BigDecimal confirmPrice = orderSetleMapper.selectConfirmPrice(searchOrderVO);
						orderSetle.put("confirmPrice", confirmPrice);
						inicis.partialCancelCardSetle(orderSetle, selteVO);

					}
					
					java.math.BigDecimal cancelAmount = new BigDecimal(Long.toString((Long)orderSetle.get("setleCardAmount")));
					searchVO.setCancelAmount(cancelAmount);

				} else{ // 일반 계정일 경우
					if ("CARD".equals(orderSetle.get("setleResultTyCode"))) { //카드결제의 경우
						//System.out.println("#########################" + (String)orderSetle.get("iniSetleConfmNo"));
						
						searchOrderVO.setIniSetleConfmNo((String)orderSetle.get("iniSetleConfmNo"));
						selteVO.setIniSetleConfmNo((String) orderSetle.get("iniSetleConfmNo"));
						java.math.BigDecimal confirmPrice = orderSetleMapper.selectConfirmPrice(searchOrderVO);
						orderSetle.put("confirmPrice", confirmPrice);
						inicis.partialCancelCardSetle(orderSetle, selteVO);
						
						java.math.BigDecimal cancelAmount = new BigDecimal(Long.toString((Long)orderSetle.get("setleCardAmount")));
						searchVO.setCancelAmount(cancelAmount);
						
					}
				}

				List<OrderSetleVO> setleList = orderSetleMapper.selectNextOrderSetle(searchVO);
				
				if (!"F".equals((String) orderSetle.get("setleTyCode"))) {
					for (OrderSetleVO setleInfo : setleList) {
						//System.out.println("@@@@@@@@@@@@@@@@@@nextSetle" + setleInfo.toString());
						selteVO.setOrderSetleNo(setleInfo.getOrderSetleNo());
						//System.out.println("@@@@@@@@@@@@@@@@@@updateOrderSetle param:" + selteVO.toString());
						orderSetleMapper.updateOrderSetle(selteVO);
					}

					searchVO.setOrderNo(null);
					//System.out.println("updateOrderDlvyStatus param: " + searchVO.toString());
					orderDlvyMapper.updateOrderDlvyStatus(searchVO);
				}
			}
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}

	}*/

	/**
	 * 교환 재발송중으로 변경
	 */
	@Override
	public void updateExchangeStatus(OrderDlvyVO searchVO) {
		searchVO.setOrderReqSttusCode("E");
		searchVO.setReqTyCode("E04");
		orderDlvyMapper.updateOrderDlvyStatus(searchVO);
	}
	/**
	 *  회차별 상세보기 목록 갯수
	 */
	@Override
	public int selectSubscribeDetailCnt(OrderDlvyVO searchVO) {
		return orderDlvyMapper.selectSubscribeDetailCnt(searchVO);
	}
	/**
	 *  회차별 상세보기 목록
	 */
	@Override
	public List<?> selectSubscribeDetail(OrderDlvyVO searchVO) {
		return orderDlvyMapper.selectSubscribeDetail(searchVO);
	}
	/**
	 * 취소, 교환, 반품 목록
	 */
	@Override
	public List<?> selectOrderReqList(OrderDlvyVO searchVO) {
		return orderDlvyMapper.selectOrderReqList(searchVO);
	}
	/**
	 * 취소, 교환, 반품 목록 갯수
	 */
	@Override
	public int selectOrderReqListCnt(OrderDlvyVO searchVO) {
		return orderDlvyMapper.selectOrderReqListCnt(searchVO);
	}
	/**
	 * CP > 주문 현황 > 회차별 구독현황 목록
	 */
	@Override
	public List<?> selectOrderDlvyHist(OrderDlvyVO searchVO) {
		return orderDlvyMapper.selectOrderDlvyHist(searchVO);
	}
	/**
	 * CP > 주문 현황 > 회차별 구독현황 목록 갯수
	 */
	@Override
	public int selectOrderDlvyHistCnt(OrderDlvyVO searchVO) {
		return orderDlvyMapper.selectOrderDlvyHistCnt(searchVO);
	}

	/**
	 * 주문 현황 > 주문 건수, 판매 금액
	 */
	@Override
	public EgovMap selectOrderStat(OrderDlvyVO searchVO) {
		return orderDlvyMapper.selectOrderStat(searchVO);
	}


	@Override
	public OrderDlvyVO selectOrderDlvyByDlvyNo(OrderDlvyVO orderDlvy) {
		return orderDlvyMapper.selectOrderDlvyByDlvyNo(orderDlvy);
	}

}
