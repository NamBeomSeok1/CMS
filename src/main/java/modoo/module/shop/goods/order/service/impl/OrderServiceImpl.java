package modoo.module.shop.goods.order.service.impl;

import java.math.BigDecimal;
import java.security.Signature;
import java.text.SimpleDateFormat;
import java.util.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import com.inicis.std.util.SignatureUtil;
import egovframework.com.cmm.LoginVO;
import egovframework.com.cmm.service.EgovProperties;
import egovframework.com.cmm.util.EgovUserDetailsHelper;
import modoo.module.common.service.JsonResult;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;


import egovframework.com.utl.fcc.service.EgovDateUtil;
import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;
import egovframework.rte.fdl.idgnr.EgovIdGnrService;
import egovframework.rte.psl.dataaccess.util.EgovMap;
import modoo.module.biling.service.Biling;
import modoo.module.biling.service.EzwelFunc;
import modoo.module.common.util.CommonUtils;
import modoo.module.common.util.NumberUtil;
import modoo.module.qainfo.service.QainfoVO;
import modoo.module.qainfo.service.impl.QainfoMapper;
import modoo.module.shop.goods.cart.service.CartItem;
import modoo.module.shop.goods.cart.service.CartVO;
import modoo.module.shop.goods.cart.service.impl.CartMapper;
import modoo.module.shop.goods.dlvy.service.OrderDlvyVO;
import modoo.module.shop.goods.dlvy.service.impl.OrderDlvyMapper;
import modoo.module.shop.goods.hist.service.OrderReqHistVO;
import modoo.module.shop.goods.hist.service.impl.OrderReqHistMapper;
import modoo.module.shop.goods.info.service.GoodsCouponService;
import modoo.module.shop.goods.info.service.GoodsCouponVO;
import modoo.module.shop.goods.info.service.GoodsItemVO;
import modoo.module.shop.goods.info.service.GoodsVO;
import modoo.module.shop.goods.info.service.impl.GoodsCouponMapper;
import modoo.module.shop.goods.info.service.impl.GoodsItemMapper;
import modoo.module.shop.goods.info.service.impl.GoodsMapper;
import modoo.module.shop.goods.order.service.OrderGroupVO;
import modoo.module.shop.goods.order.service.OrderItemVO;
import modoo.module.shop.goods.order.service.OrderService;
import modoo.module.shop.goods.order.service.OrderVO;
import modoo.module.shop.goods.setle.service.OrderSetleVO;
import modoo.module.shop.goods.setle.service.impl.OrderSetleMapper;
import modoo.module.shop.user.service.DlvyAdresVO;
import modoo.module.shop.user.service.impl.DlvyAdresMapper;

@Service("orderService")
public class OrderServiceImpl extends EgovAbstractServiceImpl implements OrderService {
	
	
	@Resource(name = "goodsMapper")
	private GoodsMapper goodsMapper;

	@Resource(name = "orderMapper")
	private OrderMapper orderMapper;
	
	@Resource(name = "dlvyAdresMapper")
	private DlvyAdresMapper dlvyAdresMapper;
	
	@Resource(name = "cartMapper")
	private CartMapper cartMapper;
	
	@Resource(name = "orderSetleMapper")
	private OrderSetleMapper orderSetleMapper;
	
	@Resource(name = "orderGroupIdGnrService")
	private EgovIdGnrService orderGroupIdGnrService;
	
	@Resource(name = "orderIdGnrService")
	private EgovIdGnrService orderIdGnrService;
	
	@Resource(name = "orderItemIdGnrService")
	private EgovIdGnrService orderItemIdGnrService;
	
	@Resource(name = "dlvyAdresIdGnrService")
	private EgovIdGnrService dlvyAdresIdGnrService;
	
	@Resource(name="goodsItemMapper")
	private GoodsItemMapper goodsItemMapper;
	
	@Resource(name = "orderDlvyMapper")
	private OrderDlvyMapper orderDlvyMapper;
	
	@Resource(name = "orderReqHistMapper")
	private OrderReqHistMapper orderReqHistMapper;
	
	@Resource(name = "qainfoMapper")
	private QainfoMapper qainfoMapper;
	
	@Resource(name = "qainfoIdGnrService")
	private EgovIdGnrService qainfoIdGnrService;

	@Resource(name = "goodsCouponMapper")
	private GoodsCouponMapper goodsCouponMapper;
	
	/**
	 * 주문목록
	 */
	@Override
	public List<OrderVO> selectMyOrderList(OrderVO searchVO) throws Exception {
		List<OrderVO> orderList = orderMapper.selectMyOrderList(searchVO);
		for(OrderVO order: orderList) {
			OrderItemVO orderItem = new OrderItemVO();
			//orderItem.setOrderNo((BigDecimal) order.getOrderNo());
			orderItem.setOrderNo(order.getOrderNo());
			List<OrderItemVO>  orderItemList = (List<OrderItemVO>) orderMapper.selectOrderItemList(order);
			order.setOrderItemList(orderItemList);
			
			//업쳉요청사항 목록
			List<OrderItemVO> orderQItemList = (List<OrderItemVO>) orderMapper.selectOrderQItemList(order);
			order.setorderQItemList(orderQItemList);
			
			GoodsVO goods = new GoodsVO();
			goods.setGoodsId((String)order.getGoodsId());
			goods=goodsMapper.selectGoods(goods);
			order.setGoods(goods);
		}
		
		return orderList;
	}
	
	/**
	 * 주문그룹저장
	 */
	@Override
	public HashMap<String, Object> insertOrderGroupPayFinish(OrderGroupVO orderGroup, HashMap<String, Object> infoMap){
		
		
		HashMap<String, Object> payResult = new HashMap<>();
		try {
			//주문 그룹
			java.math.BigDecimal orderGroupNo = orderGroupIdGnrService.getNextBigDecimalId();
			orderGroup.setOrderGroupNo(orderGroupNo);
			orderMapper.insertOrderGroup(orderGroup);
			
			infoMap.put("orderGroupNo", orderGroupNo);
			
			if(orderGroup.isNewDlvyInfo()) { //신규배송주소
				DlvyAdresVO dlvyAdres = orderGroup.getDlvyInfo();
				java.math.BigDecimal dadresNo = dlvyAdresIdGnrService.getNextBigDecimalId();
				dlvyAdres.setDadresNo(dadresNo);
				dlvyAdresMapper.insertDlvyAdres(orderGroup.getDlvyInfo());
				
				//기본 배송지 수정
				if("Y".equals(dlvyAdres.getBassDlvyAt())){
					dlvyAdresMapper.updateRelisBassDlvyAdres(dlvyAdres);
					dlvyAdresMapper.updateSetBassDlvyAdres(dlvyAdres);
				}
			}
			
			//주문
			
			//구독상품일때 첫번째주문 주문옵션정보 
			ArrayDeque<String> firOrderInfoList = new ArrayDeque<String>();
			//두번째주문 주문옵션정보 
			ArrayDeque<String> seOrderInfoList = new ArrayDeque<String>();
			for(OrderVO order : orderGroup.getOrderList()) {
				String orderNo = EgovDateUtil.getToday() + orderIdGnrService.getNextStringId();
				order.setOrderNo(orderNo);
				order.setOrderGroupNo(orderGroupNo);

				//할인 가격 넣기
				CartItem cartItem = new CartItem();
				cartItem.setCartNo(order.getCartNo());
				List<CartItem> cartItems = (List<CartItem>)cartMapper.selectCartItemList(cartItem);
				java.math.BigDecimal dscntPc = new BigDecimal(0);
				
				if(cartItems.size()!=0){
					for(CartItem ci : cartItems){
						if(ci.getGitemPc()!=null){
							if(ci.getGitemPc().compareTo(java.math.BigDecimal.ZERO) == -1){
								dscntPc = dscntPc.add(ci.getGitemPc());
							}
						}
					}
				}
				//복수 구매 할인 가격
				if("Y".equals(order.getCompnoDscntUseAt())){
					GoodsVO goods = new GoodsVO();
					goods.setGoodsId(order.getGoodsId());
					goods = goodsMapper.selectGoods(goods);
					dscntPc = dscntPc.add(goods.getCompnoDscntPc().multiply(new BigDecimal(order.getOrderCo())));
				}
				order.setDscntAmount(dscntPc);
				orderMapper.insertOrder(order);
				goodsMapper.updateGoodsListSleCo(orderGroupNo);
				
				if(order.getOrderItemList()!=null) {
					StringBuilder firOrderInfo = new StringBuilder();
					StringBuilder seOrderInfo = new StringBuilder();
					
					for(OrderItemVO item : order.getOrderItemList()) {
						java.math.BigDecimal oitemNo = orderItemIdGnrService.getNextBigDecimalId();
						item.setOitemNo(oitemNo);
						item.setOrderNo(order.getOrderNo());
						item.setCartNo(order.getCartNo());
						if(StringUtils.isEmpty(item.getGitemAnswer())) {
							item.setGitemAnswer(null);
						}
						orderMapper.insertOrderItem(item);
	                }
					//주문옵션정보넣기
					List<OrderItemVO> orderItemList = (List<OrderItemVO>) orderMapper.selectOrderItemList(order);
					for(OrderItemVO item : orderItemList){
						String sym = "+";
						if(!"Q".equals(item.getGistemSeCode())){
							if(item.getGitemPc().compareTo(java.math.BigDecimal.ZERO)==-1){
								sym = "";
							}
							if("D".equals(item.getGistemSeCode())){
								firOrderInfo.append("기본옵션:"+item.getGitemNm()+"("+sym+item.getGitemPc()+")");
								seOrderInfo.append("기본옵션:"+item.getGitemNm()+"("+sym+item.getGitemPc()+")");
							}else if("A".equals(item.getGistemSeCode())){
								firOrderInfo.append("추가옵션:"+item.getGitemNm()+"("+sym+item.getGitemPc()+")");
								seOrderInfo.append("추가옵션:"+item.getGitemNm()+"("+sym+item.getGitemPc()+")");
							}else if("F".equals(item.getGistemSeCode())){
								firOrderInfo.append("첫구독옵션:"+item.getGitemNm()+"("+sym+item.getGitemPc()+")");
							}
						}
					}
					firOrderInfoList.add(firOrderInfo.toString());
					seOrderInfoList.add(seOrderInfo.toString());
				}
				//카트 사용 못하게 막기 (USE_AT = 'N')
				CartVO cart = new CartVO();
				cart.setCartNo(order.getCartNo());
				cartMapper.updateCartClose(cart);
			}
			infoMap.put("firOrderInfoList",firOrderInfoList);
			infoMap.put("seOrderInfoList",seOrderInfoList);
			 
			OrderVO order = new OrderVO();
			java.math.BigDecimal totOrderPc = new BigDecimal(0);
			
			order.setOrdrrId((String)infoMap.get("userId"));
			order.setOrderGroupNo((BigDecimal)infoMap.get("orderGroupNo"));
			//주문정보
			List<OrderVO> resultList = orderMapper.selectMyOrderList(order);
			
			//주문 가격 총 계산
			for(OrderVO od: resultList){
				totOrderPc=totOrderPc.add(od.getTotAmount());
			}

			
			//카드 결제	
			if(infoMap.get("payMethod").equals("card")){
				payResult.put("success", true);
				
				/*
				if(totOrderPc.compareTo(new BigDecimal(0))==1){
					payResult = cardPay(infoMap, resultList);
					if((Boolean)payResult.get("success")){
						infoMap.put("billKey", (String)payResult.get("billKey"));
						infoMap.put("iniCode", (String)payResult.get("iniCode"));
					}else{
						throw new Exception();
					}
				}else{
					payResult.put("success", true);
				}
				*/
				//this.saveOrderSetleInfo(infoMap, payResult, resultList);
				
				//첫구독옵션가격
				java.math.BigDecimal fOptPc = new BigDecimal(0);
				OrderSetleVO orderSetle = new OrderSetleVO();
				
				ArrayDeque<java.math.BigDecimal> ezPointQue= new ArrayDeque<java.math.BigDecimal>();
				for(OrderVO od : resultList){
					List<OrderItemVO> odItems = (List<OrderItemVO>) orderMapper.selectOrderItemList(od);
					java.math.BigDecimal dscntPc = new BigDecimal(0);
					//할인 가격 넣기
					if(odItems.size()!=0){
						for(OrderItemVO oi : odItems){
							if(oi.getGitemPc()!=null){
								if(oi.getGitemPc().compareTo(java.math.BigDecimal.ZERO) == -1){
									dscntPc = dscntPc.add(oi.getGitemPc());
								}
							}
							//첫구독옵션가격 빼기(-일땐 +로)(+일땐 -로) 다음회차가격에 더하기
							if("F".equals(oi.getGistemSeCode())){
								fOptPc=fOptPc.add(oi.getGitemPc()).multiply(new BigDecimal(-1));
							}
						}
					}
					//1회체험 복수 할인 가격 체험
					if("Y".equals(od.getCompnoDscntUseAt())){
						GoodsVO goods = new GoodsVO();
						goods.setGoodsId(od.getGoodsId());
						goods = goodsMapper.selectGoods(goods);
						dscntPc = dscntPc.add(goods.getCompnoDscntPc().multiply(new BigDecimal(od.getOrderCo())));
					}
					orderSetle.setDscntPc(dscntPc);
					//결제 성공여부
					if((Boolean)payResult.get("success")) {
						orderSetle.setSetleSttusCode("S");	
					}else {
						orderSetle.setSetleSttusCode("F");
					}
					
					//결제정보추가
					orderSetle.setFirOrderInfo(firOrderInfoList.poll());
					orderSetle.setSeOrderInfo(seOrderInfoList.poll());
					orderSetle.setSetlePrarnde(null);
					orderSetle.setIsSbs(false);
					orderSetle.setSearchOrderNo(od.getOrderNo());
					orderSetle.setSetleResultCode(String.valueOf(payResult.get("resultCode")));
					orderSetle.setSetleResultMssage(String.valueOf(payResult.get("resultMsg")));
					orderSetle.setSetlePnttm(new Date());
					//쿠폰발급
					if("CPN".equals(od.getOrderKndCode())){
						GoodsCouponVO goodsCoupon = new GoodsCouponVO();
						goodsCoupon.setGoodsId(od.getGoodsId());
						goodsCoupon.setOrderCo(od.getOrderCo());
						List<GoodsCouponVO> goodsCouponList = goodsCouponMapper.selectSleGoodsCoupon(goodsCoupon);
						for(GoodsCouponVO gc : goodsCouponList){
							gc.setOrderNo(od.getOrderNo());
							goodsCouponMapper.updateGoodsCouponOrderNo(gc);
						}
					}
					//주문정보수정(빌키,카드고유id)
					//orderMapper.updateOrder(od);
					
					//주문결제 테이블 생성
					//카드 결제정보 저장	
					if(infoMap.get("payMethod").equals("card")){
						orderSetle.setIniSetleConfmNo((String)infoMap.get("iniCode"));
						orderSetle.setSetleTyCode("CARD");
						orderSetle.setSetleTotAmount(od.getTotAmount().add(dscntPc));
						orderSetle.setSetleCardAmount(od.getTotAmount().add(dscntPc));
						orderSetle.setSetleResultTyCode("CARD");
					}
					
					this.saveOrderSetle(orderSetle);
					//구독상품일 경우 다음 날짜 추가 구독일땐 한 번더 주문결제 삽입
					if("SBS".equals(od.getOrderKndCode())&& StringUtils.isNotEmpty(od.getSbscrptCycleSeCode())){
						od.setNextSetlede(nextSbsDate(od));
						od.setNowOdr(od.getNowOdr()+1);
						//다음차수,날짜 추가
						orderMapper.updateOrder(od);
						//첫구독 옵션 빼기
						orderSetle.setSetleTotAmount(od.getTotAmount().add(dscntPc).add(fOptPc));
						orderSetle.setSetlePrarnde(nextSbsDate(od));
						orderSetle.setIsSbs(true);
						this.saveOrderSetle(orderSetle);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		}
		return payResult;
	}


	//주문그룹번호 생성
	@Override
	public BigDecimal orderGroupNo(OrderGroupVO orderGroup) {
		try {
			if (orderGroup.getOrderGroupNo() == null) {
				return orderGroupIdGnrService.getNextBigDecimalId();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return orderGroup.getOrderGroupNo();
	}
	
	/**
	 * 주문그룹저장
	 */
	@Override
	@Transactional
	public HashMap<String, Object> insertOrderGroup(OrderGroupVO orderGroup, HashMap<String, Object> infoMap){
		
		//빌링 프로세스
		HashMap<String, Object> payResult = new HashMap<>();
		try {
			//주문 그룹
			java.math.BigDecimal orderGroupNo = orderGroupIdGnrService.getNextBigDecimalId();
			orderGroup.setOrderGroupNo(orderGroupNo);
			orderMapper.insertOrderGroup(orderGroup);
			
			infoMap.put("orderGroupNo", orderGroupNo);
			
			if(orderGroup.isNewDlvyInfo()) { //신규배송주소
				DlvyAdresVO dlvyAdres = orderGroup.getDlvyInfo();
				java.math.BigDecimal dadresNo = dlvyAdresIdGnrService.getNextBigDecimalId();
				dlvyAdres.setDadresNo(dadresNo);
				dlvyAdresMapper.insertDlvyAdres(orderGroup.getDlvyInfo());
				
				//기본 배송지 수정
				if("Y".equals(dlvyAdres.getBassDlvyAt())){
					dlvyAdresMapper.updateRelisBassDlvyAdres(dlvyAdres);
					dlvyAdresMapper.updateSetBassDlvyAdres(dlvyAdres);
				}
			}
			
			//주문
			
			//구독상품일때 첫번째주문 주문옵션정보 
			ArrayDeque<String> firOrderInfoList = new ArrayDeque<String>();
			//두번째주문 주문옵션정보 
			ArrayDeque<String> seOrderInfoList = new ArrayDeque<String>();
			for(OrderVO order : orderGroup.getOrderList()) {
				//java.math.BigDecimal orderNo = orderIdGnrService.getNextBigDecimalId(); 
				String orderNo = EgovDateUtil.getToday() + orderIdGnrService.getNextStringId();
				order.setOrderNo(orderNo);
				order.setOrderGroupNo(orderGroupNo);

				//할인 가격 넣기
				CartItem cartItem = new CartItem();
				cartItem.setCartNo(order.getCartNo());
				List<CartItem> cartItems = (List<CartItem>)cartMapper.selectCartItemList(cartItem);
				java.math.BigDecimal dscntPc = new BigDecimal(0);
				
				if(cartItems.size()!=0){
					for(CartItem ci : cartItems){
						if(ci.getGitemPc()!=null){
							if(ci.getGitemPc().compareTo(java.math.BigDecimal.ZERO) == -1){
								dscntPc = dscntPc.add(ci.getGitemPc());
							}
						}
					}
				}
				//복수 구매 할인 가격
				if("Y".equals(order.getCompnoDscntUseAt())){
					GoodsVO goods = new GoodsVO();
					goods.setGoodsId(order.getGoodsId());
					goods = goodsMapper.selectGoods(goods);
					dscntPc = dscntPc.add(goods.getCompnoDscntPc().multiply(new BigDecimal(order.getOrderCo())));
				}
				order.setDscntAmount(dscntPc);
				orderMapper.insertOrder(order);
				goodsMapper.updateGoodsListSleCo(orderGroupNo);
				
				if(order.getOrderItemList()!=null) {
					StringBuilder firOrderInfo = new StringBuilder();
					StringBuilder seOrderInfo = new StringBuilder();
					
					for(OrderItemVO item : order.getOrderItemList()) {
						java.math.BigDecimal oitemNo = orderItemIdGnrService.getNextBigDecimalId();
						item.setOitemNo(oitemNo);
						item.setOrderNo(order.getOrderNo());
						item.setCartNo(order.getCartNo());
						if(StringUtils.isEmpty(item.getGitemAnswer())) {
							item.setGitemAnswer(null);
						}
						orderMapper.insertOrderItem(item);
	                }
					//주문옵션정보넣기
					List<OrderItemVO> orderItemList = (List<OrderItemVO>) orderMapper.selectOrderItemList(order);
					for(OrderItemVO item : orderItemList){
						String sym = "+";
						if(!"Q".equals(item.getGistemSeCode())){
							if(item.getGitemPc().compareTo(java.math.BigDecimal.ZERO)==-1){
								sym = "";
							}
							if("D".equals(item.getGistemSeCode())){
								firOrderInfo.append("기본옵션:"+item.getGitemNm()+"("+sym+item.getGitemPc()+")");
								seOrderInfo.append("기본옵션:"+item.getGitemNm()+"("+sym+item.getGitemPc()+")");
							}else if("A".equals(item.getGistemSeCode())){
								firOrderInfo.append("추가옵션:"+item.getGitemNm()+"("+sym+item.getGitemPc()+")");
								seOrderInfo.append("추가옵션:"+item.getGitemNm()+"("+sym+item.getGitemPc()+")");
							}else if("F".equals(item.getGistemSeCode())){
								firOrderInfo.append("첫구독옵션:"+item.getGitemNm()+"("+sym+item.getGitemPc()+")");
							}
						}
					}
					firOrderInfoList.add(firOrderInfo.toString());
					seOrderInfoList.add(seOrderInfo.toString());
				}
				//카트 사용 못하게 막기 (USE_AT = 'N')
				CartVO cart = new CartVO();
				cart.setCartNo(order.getCartNo());
				cartMapper.updateCartClose(cart);
			}
			infoMap.put("firOrderInfoList",firOrderInfoList);
			infoMap.put("seOrderInfoList",seOrderInfoList);
			 
			OrderVO order = new OrderVO();
			java.math.BigDecimal totOrderPc = new BigDecimal(0);
			
			order.setOrdrrId((String)infoMap.get("userId"));
			order.setOrderGroupNo((BigDecimal)infoMap.get("orderGroupNo"));
			//주문정보
			List<OrderVO> resultList = orderMapper.selectMyOrderList(order);
			
			//주문 가격 총 계산
			for(OrderVO od: resultList){
				totOrderPc=totOrderPc.add(od.getTotAmount());
			}

			//복수주문일 때 이지웰 주문 코드 리스트
			ArrayDeque<String> ezCodeList = new ArrayDeque<String>();
			//이지웰 주문 금액 리스트 
			ArrayDeque<java.math.BigDecimal> ezPointQue= new ArrayDeque<java.math.BigDecimal>();
			//이지웰 주문 코드
			String ezCode = "";
			Boolean isEzCodes = false;
			//이지웰포인트 결제
			if(infoMap.get("payMethod").equals("point")){
				payResult = ezwelPointPay(infoMap, resultList);
				if((Boolean)payResult.get("success")){
					//이지웰 복수 주문일때(주문코드넣기)
					if((Boolean)payResult.get("ezCodes")){
						ezCodeList=(ArrayDeque<String>) payResult.get("ezCodeList");
						isEzCodes=true;
					}else{
						ezCode = (String)payResult.get("ezCode"); 
					}
					//이지웰 주문일 때도 빌링키를 발급받아야한다.
					Biling biling = new Biling();
					payResult=biling.bilingKey(infoMap);
					infoMap.put("billKey", payResult.get("billKey"));
					payResult.put("ezCode", ezCode);
					payResult.put("ezCodes", isEzCodes);
					payResult.put("ezCodeList",ezCodeList);
					//모든 결제 성공시 주문 결제 정보 삽입
					if((Boolean)payResult.get("success")){
						this.saveOrderSetleInfo(infoMap, payResult, resultList);
					}else{
						throw new Exception();
					}
				}
			//카드 결제	
			}else if(infoMap.get("payMethod").equals("card")){
				//이지웰 회원일 때
				if((Boolean)infoMap.get("isEzwel")){
					infoMap.put("isCard", true);
					//주문가격 0원일때 이니시스 X 
					if(totOrderPc.compareTo(new BigDecimal(0))==0){
						payResult.put("success", true);
					}else{
						payResult = cardPay(infoMap, resultList);
					}
					if((Boolean)payResult.get("success")){
						if(payResult.get("billKey")!=null && payResult.get("iniCode")!=null){
							infoMap.put("billKey", (String)payResult.get("billKey"));
							infoMap.put("iniCode", (String)payResult.get("iniCode"));
						}
						payResult = ezwelPointPay(infoMap, resultList);
						//주문 여러개일때 이지웰 주문정보
						if((Boolean)payResult.get("ezCodes")){
							ezCodeList=(ArrayDeque<String>) payResult.get("ezCodeList");
							isEzCodes=true;
						}else{
							ezCode = (String)payResult.get("ezCode"); 
						}
						payResult.put("ezCode", ezCode);
						payResult.put("ezCodes", isEzCodes);
						payResult.put("ezCodeList",ezCodeList);
						this.saveOrderSetleInfo(infoMap, payResult, resultList);
					}else{
						throw new Exception();
					}
				}else{
					if(totOrderPc.compareTo(new BigDecimal(0))==1){
						payResult = cardPay(infoMap, resultList);
						if((Boolean)payResult.get("success")){
							infoMap.put("billKey", (String)payResult.get("billKey"));
							infoMap.put("iniCode", (String)payResult.get("iniCode"));
						}else{
							throw new Exception();
						}
					}else{
						payResult.put("success", true);
					}
					this.saveOrderSetleInfo(infoMap, payResult, resultList);
				}
			//포인트,카드 결제
			}else if(infoMap.get("payMethod").equals("both")){
				ArrayDeque<java.math.BigDecimal> ezPointStored= new ArrayDeque<java.math.BigDecimal>();
				EzwelFunc ezFunc = new EzwelFunc();
				java.math.BigDecimal ezwelPoint = ezFunc.ezwelPointSearch((String)infoMap.get("userKey"), (String)infoMap.get("clientCd"));
				for(OrderVO od : resultList) {
					//이지웰 포인트계산
					if(ezwelPoint.compareTo(java.math.BigDecimal.ZERO)==1){
						//포인트가 총가격보다 클때
						if(ezwelPoint.compareTo(od.getTotAmount().add(od.getDscntAmount()))==1){
							ezPointQue.add(od.getTotAmount().add(od.getDscntAmount()));
							ezwelPoint=ezwelPoint.subtract(od.getTotAmount().add(od.getDscntAmount()));
						//총가격이 포인트보다 클때	
						}else{
							ezPointQue.add(ezwelPoint);
							ezwelPoint=java.math.BigDecimal.ZERO;
						}
					}else{
						ezPointQue.add(java.math.BigDecimal.ZERO);
					}
				}
				infoMap.put("ezPointQue", ezPointQue);
				payResult = cardPay(infoMap, resultList);
				
				if((Boolean)payResult.get("success")){
					infoMap.put("billKey", payResult.get("billKey"));
					infoMap.put("iniCode", payResult.get("iniCode"));
					payResult = ezwelPointPay(infoMap, resultList);
					if((Boolean)payResult.get("ezCodes")){
						ezCodeList=(ArrayDeque<String>) payResult.get("ezCodeList");
						isEzCodes=true;
						ezPointQue= (ArrayDeque<java.math.BigDecimal>) payResult.get("ezPointQue");
						ezPointStored = (ArrayDeque<java.math.BigDecimal>) payResult.get("ezPointStored");
					}else{
						ezCode = (String)payResult.get("ezCode"); 
					}
					payResult.put("ezCode", ezCode);
					payResult.put("ezCodeList",ezCodeList);
					payResult.put("ezPointQue",ezPointStored);
					payResult.put("ezCodes", isEzCodes);
					this.saveOrderSetleInfo(infoMap, payResult, resultList);
				}else{
					throw new Exception();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		}
		return payResult;
	}

	/**
	 * 주문그룹저장(이니시스 모듈결제 --> 주문그룹저장만)
	 *
	 * @return
	 */
	@Override
	@Transactional
	public HashMap<String, Object> insertPaymentOrderGroup(HttpServletRequest request, OrderGroupVO orderGroup) {

		HashMap<String, Object> resultMap = new HashMap<>();

		LoginVO user = (LoginVO) EgovUserDetailsHelper.getAuthenticatedUser();

		try {
			//주문 그룹
			java.math.BigDecimal orderGroupNo = orderGroupNo(orderGroup);
			orderGroup.setOrderGroupNo(orderGroupNo);
			orderMapper.insertOrderGroup(orderGroup);

			resultMap.put("orderGroupNo", orderGroupNo);

			if (orderGroup.isNewDlvyInfo()) { //신규배송주소
				DlvyAdresVO dlvyAdres = orderGroup.getDlvyInfo();
				java.math.BigDecimal dadresNo = dlvyAdresIdGnrService.getNextBigDecimalId();
				dlvyAdres.setDadresNo(dadresNo);
				dlvyAdresMapper.insertDlvyAdres(orderGroup.getDlvyInfo());

				//기본 배송지 수정
				if ("Y".equals(dlvyAdres.getBassDlvyAt())) {
					dlvyAdresMapper.updateRelisBassDlvyAdres(dlvyAdres);
					dlvyAdresMapper.updateSetBassDlvyAdres(dlvyAdres);
				}
			}

			//주문

			//구독상품일때 첫번째주문 주문옵션정보
			ArrayDeque<String> firOrderInfoList = new ArrayDeque<String>();
			//두번째주문 주문옵션정보
			ArrayDeque<String> seOrderInfoList = new ArrayDeque<String>();
			for (OrderVO order : orderGroup.getOrderList()) {
				//java.math.BigDecimal orderNo = orderIdGnrService.getNextBigDecimalId();
				String orderNo = EgovDateUtil.getToday() + orderIdGnrService.getNextStringId();
				order.setOrderNo(orderNo);
				order.setOrderGroupNo(orderGroupNo);

				//할인 가격 넣기x
				CartItem cartItem = new CartItem();
				cartItem.setCartNo(order.getCartNo());
				List<CartItem> cartItems = (List<CartItem>) cartMapper.selectCartItemList(cartItem);
				java.math.BigDecimal dscntPc = new BigDecimal(0);

				if (cartItems.size() != 0) {
					for (CartItem ci : cartItems) {
						if (ci.getGitemPc() != null) {
							if (ci.getGitemPc().compareTo(java.math.BigDecimal.ZERO) == -1) {
								dscntPc = dscntPc.add(ci.getGitemPc());
							}
						}
					}
				}
				//복수 구매 할인 가격
				if ("Y".equals(order.getCompnoDscntUseAt())) {
					GoodsVO goods = new GoodsVO();
					goods.setGoodsId(order.getGoodsId());
					goods = goodsMapper.selectGoods(goods);
					dscntPc = dscntPc.add(goods.getCompnoDscntPc().multiply(new BigDecimal(order.getOrderCo())));
				}
				order.setDscntAmount(dscntPc);
				order.setOrderSttusCode("ST100");

				System.out.println("==================== insertOrder =====================");
				System.out.println(order.toString());
				orderMapper.insertOrder(order);
				//goodsMapper.updateGoodsListSleCo(orderGroupNo);

				if (order.getOrderItemList() != null) {
					StringBuilder firOrderInfo = new StringBuilder();
					StringBuilder seOrderInfo = new StringBuilder();

					for (OrderItemVO item : order.getOrderItemList()) {
						java.math.BigDecimal oitemNo = orderItemIdGnrService.getNextBigDecimalId();
						item.setOitemNo(oitemNo);
						item.setOrderNo(order.getOrderNo());
						item.setCartNo(order.getCartNo());
						if (StringUtils.isEmpty(item.getGitemAnswer())) {
							item.setGitemAnswer(null);
						}
						orderMapper.insertOrderItem(item);
					}
				}
			}
			OrderVO order = new OrderVO();
			java.math.BigDecimal totOrderPc = new BigDecimal(0);

			order.setOrdrrId(user.getId());
			order.setOrderGroupNo(orderGroupNo);
			//주문정보
			List<OrderVO> resultList = orderMapper.selectMyOrderList(order);

			//주문 가격 총 계산
			for (OrderVO od : resultList) {
				totOrderPc = totOrderPc.add(od.getTotAmount());
			}

			/*if ("both".equals(orderGroup.getPayMethod())) {
				java.math.BigDecimal ezPoint = null;
				if (user.getGroupId().equals(PartnerGroupId.EZWEL.getCode()))
					ezPoint = new EzwelFunc().ezwelPointSearch(user.getUserKey(), user.getClientCd());
				else if (user.getGroupId().equals(PartnerGroupId.EXANADU.getCode())) {
					ezPoint = ExanaduSSO.pointSearch(request);
					//ezPoint = new BigDecimal(50); // TODO 복합결제 테스트용
				} else {
					KcpPayment kcpPayment = new KcpPayment();
					kcpInfoVO = kcpPayment.loadKcpPoint(kcpInfoVO);
					ezPoint = new BigDecimal(kcpInfoVO.getKcpResult().getRsv_pnt());
					//ezPoint=new BigDecimal("900");
				}
				System.out.println("복합 포인트 조회 --- " + ezPoint);
				if (ezPoint.compareTo(BigDecimal.ZERO) == 1) {
					totOrderPc = totOrderPc.subtract(ezPoint);
				}

			}*/
			System.out.println("복합 카드결제 상품가격 --- " + totOrderPc.toPlainString());

			//이니시스 모듈 정보담기
			if (resultList != null) {
				resultMap.put("price", totOrderPc);
				resultMap.put("oid", orderGroupNo);
				resultMap.put("buyertel", resultList.get(0).getTelno());
				resultMap.put("buyeremail", resultList.get(0).getOrdrrEmail());
				if (resultList.size() == 1) {
					GoodsVO goods = new GoodsVO();
					goods.setGoodsId(resultList.get(0).getGoodsId());
					goods = goodsMapper.selectGoods(goods);
					resultMap.put("goodname", goods.getGoodsNm());
				} else if (resultList.size() > 1) {
					resultMap.put("goodname", "모두의구독" + resultList.size() + "개의 상품");
				}
			}
		} catch (Exception e) {
			System.out.println("=======================에러 발생==========================");
			e.printStackTrace();
		}
		return resultMap;
	}

	/**
	 * 결제정보
	 */
	@Override
	public JsonResult paymentInfo(JsonResult jsonResult, HashMap<String, Object> resultMap, HttpServletRequest req) throws Exception {
		String mid = EgovProperties.getProperty("INICIS.mid");        // 가맹점 ID(가맹점 수정후 고정)
		//인증
		String signKey = EgovProperties.getProperty("INICIS.signKey");
		;    // 실제 key
		String timestamp = SignatureUtil.getTimestamp();            // util에 의해서 자동생성
		String oid = String.valueOf(resultMap.get("oid"));    // 가맹점 주문번호(가맹점에서 직접 설정)
		String price = String.valueOf(resultMap.get("price"));                                                    // 상품가격(특수기호 제외, 가맹점에서 직접 설정)
		String goodname = String.valueOf(resultMap.get("goodname"));                                                    // 상품가격(특수기호 제외, 가맹점에서 직접 설정)
		String gopaymethod = String.valueOf(resultMap.get("gopayMethod"));
		String payMethod = String.valueOf(resultMap.get("payMethod"));
		String isCart = String.valueOf(resultMap.get("isCart"));
//		String gopaymethod			= "Card:DirectBank:HPP:Culture:DGCL:Bcsh:HPMN";													// 상품가격(특수기호 제외, 가맹점에서 직접 설정)
		String acceptmethod = "";                                                    // 상품가격(특수기호 제외, 가맹점에서 직접 설정)
		if ("Card".equals(resultMap.get("gopayMethod"))) acceptmethod = "below1000";
		if ("HPP".equals(resultMap.get("gopayMethod"))) acceptmethod = "HPP(2)";

		String cardNoInterestQuota = "11-2:3:,34-5:12,14-6:12:24,12-12:36,06-9:12,01-3:4";        // 카드 무이자 여부 설정(가맹점에서 직접 설정)
		String cardQuotaBase = "2:3:4:5:6:11:12:24:36";        // 가맹점에서 사용할 할부 개월수 설정

		//###############################################
		// 2. 가맹점 확인을 위한 signKey를 해시값으로 변경 (SHA-256방식 사용)
		//###############################################
		String mKey = SignatureUtil.hash(signKey, "SHA-256");

		//###############################################
		// 2.signature 생성
		//###############################################
		Map<String, String> signParam = new HashMap<String, String>();

		signParam.put("oid", oid);                    // 필수
		signParam.put("price", price);                // 필수
		signParam.put("timestamp", timestamp);        // 필수

		// signature 데이터 생성 (모듈에서 자동으로 signParam을 알파벳 순으로 정렬후 NVP 방식으로 나열해 hash)
		String signature = SignatureUtil.makeSignature(signParam);
		/* 기타 */
		String siteDomain = req.getScheme() + "://" + req.getServerName(); //가맹점 도메인 입력; //가맹점 도메인 입력

		/*StringBuffer url = req.getRequestURL();
		String uri = req.getRequestURI();
		int idx = (((uri != null) && (uri.length() > 0)) ? url.indexOf(uri) : url.length());
		String host = ""; //base url
		String domain = url.substring(0, idx); //base url
		idx = host.indexOf("://");
		System.out.println(uri);
		if(idx > 0) {
		  domain = host.substring(0,idx); //remove scheme if present
		}*/
		if (req.getServerName().contains("modoo.ai")) {
			siteDomain = "https://" + req.getServerName(); //가맹점 도메인 입력
		}

		/*장바구니 번호리스트*/
		/*StringBuilder cartnoStr = (StringBuilder)resultMap.get("cartnoStr");*/

		StringBuilder cartnoStr = (StringBuilder) resultMap.get("cartnoStr");
		/*for(int i=0;i<cartnoArr.size();i++){
			if(i==0){
				cartnoStr.append("?cartno="+cartnoArr.get(i));
			}
			cartnoStr.append("&cartno="+cartnoArr.get(i));
		}*/

		String closeUrl = siteDomain + "/shop/goods/closeInisis.do?orderGroupNo=" + oid;
		String popupUrl = siteDomain + "/inisis/popup.jsp";
		String returnUrl = siteDomain + "/shop/goods/inisisResponse.do" + cartnoStr + "&orderGroupNo=" + oid + "&payMethod=" + payMethod + "&gopayMethod=" + gopaymethod + "&isCart=" + isCart;

		jsonResult.put("acceptmethod", acceptmethod);
		jsonResult.put("gopaymethod", gopaymethod);
		jsonResult.put("returnUrl", siteDomain);
		jsonResult.put("closeUrl", closeUrl);
		jsonResult.put("popupUrl", popupUrl);
		jsonResult.put("signature", signature);
		jsonResult.put("buyertel", String.valueOf(resultMap.get("buyertel")));
		jsonResult.put("mKey", mKey);
		jsonResult.put("cardQuotaBase", cardQuotaBase);
		jsonResult.put("cardNoInterestQuota", cardNoInterestQuota);
		jsonResult.put("goodname", goodname);
		jsonResult.put("price", price);
		jsonResult.put("oid", oid);
		jsonResult.put("timestamp", timestamp);
		jsonResult.put("signKey", signKey);
		jsonResult.put("mid", mid);
		jsonResult.put("returnUrl", returnUrl);
		// 페이지 URL에서 고정된 부분을 적는다.
		// Ex) returnURL이 http://localhost:8080INIpayStdSample/INIStdPayReturn.jsp 라면
		// http://localhost:8080/INIpayStdSample 까지만 기입한다.

		//결제 방식(card,point,both)
		return jsonResult;
	}

	/**
	 * 결제 정보 저장
	 *
	 * @param infoMap
	 * @param payResult
	 * @param resultList
	 * @throws Exception
	 */
	@Override
	public void saveOrderSetleInfo(HashMap<String, Object> infoMap, HashMap<String, Object> payResult,
								   List<OrderVO> resultList) throws Exception {

		System.out.println("=================== saveOrderSetleInfo ===================");

		//첫구독옵션가격
		java.math.BigDecimal fOptPc = new BigDecimal(0);
		OrderSetleVO orderSetle = new OrderSetleVO();
		//첫번째주문옵션정보
		ArrayDeque<String> firOrderInfoList = new ArrayDeque<String>();
		//두번째주문옵션정보
		ArrayDeque<String> seOrderInfoList = new ArrayDeque<String>();

		//이지웰 정보
		ArrayDeque<String> ezCodeList = new ArrayDeque<String>();
		ArrayDeque<java.math.BigDecimal> ezPointQue = new ArrayDeque<java.math.BigDecimal>();
		for (OrderVO od : resultList) {
			/*List<OrderItemVO> odItems = (List<OrderItemVO>) orderMapper.selectOrderItemList(od);*/
			java.math.BigDecimal dscntPc = new BigDecimal(0);

			StringBuilder firOrderInfo = new StringBuilder();
			StringBuilder seOrderInfo = new StringBuilder();

			//주문옵션정보넣기
			List<OrderItemVO> orderItemList = (List<OrderItemVO>) orderMapper.selectOrderItemList(od);
			for (OrderItemVO item : orderItemList) {
				String sym = "+";
				if (!"Q".equals(item.getGistemSeCode())) {
					if (item.getGitemPc().compareTo(java.math.BigDecimal.ZERO) == -1) {
						sym = "";
					}
					if ("D".equals(item.getGistemSeCode())) {
						firOrderInfo.append("기본옵션:" + item.getGitemNm() + "(" + sym + item.getGitemPc() + ")");
						seOrderInfo.append("기본옵션:" + item.getGitemNm() + "(" + sym + item.getGitemPc() + ")");
					} else if ("A".equals(item.getGistemSeCode())) {
						firOrderInfo.append("추가옵션:" + item.getGitemNm() + "(" + sym + item.getGitemPc() + ")");
						seOrderInfo.append("추가옵션:" + item.getGitemNm() + "(" + sym + item.getGitemPc() + ")");
					} else if ("F".equals(item.getGistemSeCode())) {
						firOrderInfo.append("첫구독옵션:" + item.getGitemNm() + "(" + sym + item.getGitemPc() + ")");
					}
				}
			}
			firOrderInfoList.add(firOrderInfo.toString());
			seOrderInfoList.add(seOrderInfo.toString());

			//할인 가격 넣기
			if (orderItemList.size() != 0) {
				for (OrderItemVO oi : orderItemList) {
					if (oi.getGitemPc() != null) {
						if (oi.getGitemPc().compareTo(java.math.BigDecimal.ZERO) == -1) {
							dscntPc = dscntPc.add(oi.getGitemPc());
						}
					}
					//첫구독옵션가격 빼기(-일땐 +로)(+일땐 -로) 다음회차가격에 더하기
					if ("F".equals(oi.getGistemSeCode())) {
						fOptPc = fOptPc.add(oi.getGitemPc()).multiply(new BigDecimal(-1));
					}
				}
			}
			//1회체험 복수 할인 가격 체험
		/*	if ("Y".equals(od.getCompnoDscntUseAt())) {
				GoodsVO goods = new GoodsVO();
				goods.setGoodsId(od.getGoodsId());
				goods = goodsMapper.selectGoods(goods);
				java.math.BigDecimal compnoDscntPc = new BigDecimal(0);
				if ("Y".equals(od.getExprnUseAt())) {
					compnoDscntPc = goods.getExprnCompnoDscntPc();
				} else {
					compnoDscntPc = goods.getCompnoDscntPc();
				}
				dscntPc = dscntPc.add(compnoDscntPc.multiply(new BigDecimal(od.getOrderCo())));
			}*/
			orderSetle.setDscntPc(dscntPc);
			//결제 성공여부
			if ((Boolean) payResult.get("success")) {
				orderSetle.setSetleSttusCode("S");
				od.setOrderSttusCode("ST02");
				if (infoMap.get("gopayMethod") != null) {   //단건결제방식저장
					orderSetle.setGoPayMethod(String.valueOf(infoMap.get("gopayMethod")));
				}
			} else {
				od.setOrderSttusCode("ST100");
				orderSetle.setSetleSttusCode("F");
			}
			od.setBillKey((String) infoMap.get("billKey"));
			od.setCardId((String) infoMap.get("cardId"));
			//결제정보추가
			orderSetle.setFirOrderInfo(firOrderInfoList.poll());
			orderSetle.setSeOrderInfo(seOrderInfoList.poll());
			orderSetle.setSetlePrarnde(null);
			orderSetle.setIsSbs(false);
			orderSetle.setSearchOrderNo(od.getOrderNo());
			orderSetle.setSetleResultCode(String.valueOf(payResult.get("resultCode")));
			orderSetle.setSetleResultMssage(String.valueOf(payResult.get("resultMsg")));
			orderSetle.setSetlePnttm(new Date());
			//쿠폰발급
			if ("CPN".equals(od.getOrderKndCode())) {
				GoodsCouponVO goodsCoupon = new GoodsCouponVO();
				goodsCoupon.setGoodsId(od.getGoodsId());
				goodsCoupon.setOrderCo(od.getOrderCo());
				List<GoodsCouponVO> goodsCouponList = goodsCouponMapper.selectSleGoodsCoupon(goodsCoupon);
				for (GoodsCouponVO gc : goodsCouponList) {
					gc.setOrderNo(od.getOrderNo());
					goodsCouponMapper.updateGoodsCouponOrderNo(gc);
				}
			}
			//주문정보수정(빌키,카드고유id)
			if (StringUtils.isEmpty(od.getCardId())) {
				od.setCardId(null);
			}
			orderMapper.updateOrder(od);


			/**
			 * EZP : 포인트로만 결제
			 * EZCD, BNCD, EXCD : 포인트+카드 결제
			 * CARD : 카드로만 결제
			 **/
			//주문결제 테이블 생성
			//양쪽 결제일 때 정보 저장
		/*	if ("both".equals(infoMap.get("payMethod"))) {
				orderSetle.setIniSetleConfmNo((String) infoMap.get("iniCode"));
				//복수주문

				if (payResult.get("ezCodes") != null && payResult.get("ezCode") != null) {
					if ((Boolean) payResult.get("ezCodes")) {
						ezCodeList = (ArrayDeque<String>) payResult.get("ezCodeList");
						orderSetle.setEzwSetleConfmNo(ezCodeList.poll());

						ezPointQue = (ArrayDeque<java.math.BigDecimal>) payResult.get("ezPointQue");

						System.out.println("ezPointQue:" + ezPointQue);

						orderSetle.setSetlePoint(ezPointQue.peek());

						//포인트가 0이 아닐때
						if (ezPointQue.peek().compareTo(java.math.BigDecimal.ZERO) != 0) {
							orderSetle.setSetleTyCode("EZCD");
							orderSetle.setSetleResultTyCode("EZCD");
							if (PartnerGroupId.BENEPIA.getCode().equals(infoMap.get("partnerId"))) {
								orderSetle.setSetleTyCode("BNCD");
								orderSetle.setSetleResultTyCode("BNCD");
							} else if (PartnerGroupId.EXANADU.getCode().equals(infoMap.get("partnerId"))) {
								orderSetle.setSetleTyCode("EXCD");
								orderSetle.setSetleResultTyCode("EXCD");
								orderSetle.setCustId((String) payResult.get("custId"));
							}
						} else {
							orderSetle.setSetleTyCode("CARD");
							orderSetle.setSetleResultTyCode("CARD");
							if("HPP".equals(infoMap.get("gopayMethod")) || "MOBILE".equals(infoMap.get("gopayMethod"))){
								orderSetle.setSetleTyCode("HPP");
								orderSetle.setSetleResultTyCode("HPP");
							}
						}
						orderSetle.setSetleCardAmount(od.getTotAmount().subtract(ezPointQue.poll()));

					} else {

						if (BigDecimal.ZERO.compareTo((BigDecimal) infoMap.get("useEzwelPoint")) != 0) {
							orderSetle.setSetleTyCode("EZCD");
							orderSetle.setSetleResultTyCode("EZCD");
							if (PartnerGroupId.BENEPIA.getCode().equals(infoMap.get("partnerId"))) {
								orderSetle.setSetleTyCode("BNCD");
								orderSetle.setSetleResultTyCode("BNCD");
							} else if (PartnerGroupId.EXANADU.getCode().equals(infoMap.get("partnerId"))) {
								orderSetle.setSetleTyCode("EXCD");
								orderSetle.setSetleResultTyCode("EXCD");
								orderSetle.setCustId((String) payResult.get("custId"));
							}
						} else {
							orderSetle.setSetleTyCode("CARD");
							orderSetle.setSetleResultTyCode("CARD");
							if("HPP".equals(infoMap.get("gopayMethod")) || "MOBILE".equals(infoMap.get("gopayMethod"))){
								orderSetle.setSetleTyCode("HPP");
								orderSetle.setSetleResultTyCode("HPP");
							}
						}
						orderSetle.setEzwSetleConfmNo((String) payResult.get("ezCode"));
						orderSetle.setSetlePoint((BigDecimal) infoMap.get("useEzwelPoint"));
						orderSetle.setSetleCardAmount(od.getTotAmount().add(dscntPc).subtract((BigDecimal) infoMap.get("useEzwelPoint")));
					}
				}
				orderSetle.setSetleTotAmount(od.getTotAmount().add(dscntPc));

				//포인트 결제정보 저장
			} else if ("point".equals(infoMap.get("payMethod"))) {
				if (payResult.get("ezCodes") != null && payResult.get("ezCode") != null) {
					if ((Boolean) payResult.get("ezCodes")) {
						ezCodeList = (ArrayDeque<String>) payResult.get("ezCodeList");
						orderSetle.setEzwSetleConfmNo(ezCodeList.poll());
					} else {
						orderSetle.setEzwSetleConfmNo((String) payResult.get("ezCode"));
					}
				}
				orderSetle.setSetleTyCode("EZP");
				orderSetle.setSetleTotAmount(od.getTotAmount().add(dscntPc));
				orderSetle.setSetlePoint(od.getTotAmount().add(dscntPc));
				orderSetle.setSetleCardAmount(java.math.BigDecimal.ZERO);
				orderSetle.setSetleResultTyCode("EZP");
				if (PartnerGroupId.BENEPIA.getCode().equals(infoMap.get("partnerId"))) {
					orderSetle.setSetleTyCode("BNP");
					orderSetle.setSetleResultTyCode("BNP");
				} else if (PartnerGroupId.EXANADU.getCode().equals(infoMap.get("partnerId"))) {
					orderSetle.setSetleTyCode("EXA");
					orderSetle.setSetleResultTyCode("EXA");
					orderSetle.setCustId((String) payResult.get("custId"));
				}

				//카드 결제정보 저장
			}*/if ("card".equals(infoMap.get("payMethod"))) {
				if (payResult.get("ezCodes") != null && payResult.get("ezCode") != null) {
					if ((Boolean) infoMap.get("isEzwel")) {
						if ((Boolean) payResult.get("ezCodes")) {
							ezCodeList = (ArrayDeque<String>) payResult.get("ezCodeList");
							orderSetle.setEzwSetleConfmNo(ezCodeList.poll());
						}
						/*else{
							orderSetle.setEzwSetleConfmNo((String)payResult.get("ezCode"));
						}*/
					} else if ((Boolean) infoMap.get("isExanadu")) {
						if ((Boolean) payResult.get("ezCodes")) {
							ezCodeList = (ArrayDeque<String>) payResult.get("ezCodeList");
							orderSetle.setEzwSetleConfmNo(ezCodeList.poll());
						}
					}
					orderSetle.setSetlePoint(java.math.BigDecimal.ZERO);
				}
				orderSetle.setIniSetleConfmNo((String) infoMap.get("iniCode"));
				orderSetle.setSetleTyCode("CARD");
				orderSetle.setSetleResultTyCode("CARD");
				if("HPP".equals(infoMap.get("gopayMethod")) || "MOBILE".equals(infoMap.get("gopayMethod"))){
					orderSetle.setSetleTyCode("HPP");
					orderSetle.setSetleResultTyCode("HPP");
				}
				orderSetle.setSetleTotAmount(od.getTotAmount());
				orderSetle.setSetleCardAmount(od.getTotAmount());

			}
			this.saveOrderSetle(orderSetle);
			//구독상품일 경우 다음 날짜 추가 구독일땐 한 번더 주문결제 삽입
			if ("SBS".equals(od.getOrderKndCode()) && StringUtils.isNotEmpty(od.getSbscrptCycleSeCode()) && (Boolean) payResult.get("success")) {
				od.setNextSetlede(nextSbsDate(od));
				od.setNowOdr(od.getNowOdr() + 1);
				//다음차수,날짜 추가
				orderMapper.updateOrder(od);
				//첫구독 옵션 빼기
				orderSetle.setSetleTotAmount(od.getTotAmount().add(fOptPc));
				orderSetle.setSetlePrarnde(nextSbsDate(od));
				orderSetle.setIsSbs(true);
			/* else if(PartnerGroupId.EXANADU.getCode().equals(infoMap.get("partnerId"))) {
					orderSetle.setSetleTyCode("CARD");
					orderSetle.setSetleResultTyCode("CARD");
				}*/
				this.saveOrderSetle(orderSetle);
			}
		}
	}
	
	
	
	/**
	 * 주문결제 저장
	 * @param orderSetle
	 * @throws Exception
	 */
	private void saveOrderSetle(OrderSetleVO orderSetle) throws Exception {
		String tempKey  = NumberUtil.numberGen(4)+System.currentTimeMillis();
		orderSetle.setTempKey(tempKey);
		
		Boolean isSbs = false;
		if(orderSetle.getIsSbs()==true && orderSetle.getIsSbs()!=null)isSbs = true;
		//구독 상품 다음결제정보 초기화
		if(isSbs){
			orderSetle.setSetleSttusCode("R");
			orderSetle.setSetleResultMssage(null);
			orderSetle.setSetleResultCode(null);
			orderSetle.setSetleCardAmount(null);
			orderSetle.setSetlePoint(null);
			orderSetle.setEzwSetleConfmNo(null);
			orderSetle.setIniSetleConfmNo(null);
			orderSetle.setSetlePnttm(null);
			orderSetle.setSetleResultTyCode(null);
		}
		orderSetleMapper.insertSelectOrder(orderSetle);
		OrderSetleVO newOrderSetle = orderSetleMapper.selectOrderSetle(orderSetle);
		
		HashMap<String, Object> map =new HashMap<>();
		
		map.put("orderSetleNo", newOrderSetle.getOrderSetleNo());
		map.put("orderNo", orderSetle.getSearchOrderNo());
		
		if(isSbs){
			map.put("orderReqSttusCode", 'W');
			map.put("dscntPc", 0);
			map.put("orderInfo",orderSetle.getSeOrderInfo());
		}else{
			map.put("orderReqSttusCode", 'O');
			map.put("dscntPc", orderSetle.getDscntPc());
			map.put("orderInfo",orderSetle.getFirOrderInfo());
		}
		
		orderSetleMapper.insertNextSTN_ORDER_DLVY(map);
		
		//두번째 배송내역가격수정
		if(isSbs){
			OrderDlvyVO orderDlvy = new OrderDlvyVO();
			orderDlvy.setOrderNo(orderSetle.getSearchOrderNo());
			orderDlvyMapper.updateSbsOrderDlvy(orderDlvy);
		}
		
		
		//정산 지급일 수정
		if(!"R".equals(orderSetle.getSetleSttusCode())){
			orderSetleMapper.updateExcclcPrarnde(newOrderSetle);
		}
	}
	
	/*
	 * 이지웰 포인트 결제
	 *@param infoMap
	 *@param orderList
	 *@throws Exception
	 */
	private HashMap<String, Object> ezwelPointPay(HashMap<String, Object> infoMap, List<OrderVO> orderList) throws Exception{
		
		EzwelFunc ezwelFunc = new EzwelFunc();
		HashMap<String, Object> resultMap = new HashMap<>();
		GoodsVO goods = new GoodsVO();
		SimpleDateFormat dayFormat = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat hourFormat = new SimpleDateFormat("hh:mm:ss");
		
		infoMap.put("orderNm", infoMap.get("userName"));
		infoMap.put("orderEmail", infoMap.get("userEmail"));
		
		//주문개수 여러개일때
		ArrayDeque<String> ezCodeList = new ArrayDeque<String>();
		ArrayDeque<java.math.BigDecimal> ezPointQue= new ArrayDeque<java.math.BigDecimal>();
		ArrayDeque<java.math.BigDecimal> ezPointStored= new ArrayDeque<java.math.BigDecimal>();
		if(orderList.size()>1){
			infoMap.put("goodsNm", "모두의구독"+orderList.size()+"개의 상품");
			for(OrderVO od : orderList){
				//이지웰 포인트 확인
				java.math.BigDecimal ezwelPoint = ezwelFunc.ezwelPointSearch((String)infoMap.get("userKey"), (String)infoMap.get("clientCd"));
				goods.setGoodsId(od.getGoodsId());
				goods=goodsMapper.selectGoods(goods);
				infoMap.put("useMileage",0);
				infoMap.put("useSpecialPoint", 0);
				infoMap.put("goodsNm", goods.getGoodsNm());
				infoMap.put("unitCost", goods.getGoodsPc());
				infoMap.put("buyPrice", goods.getGoodsSplpc());
				infoMap.put("orderCount",od.getOrderCo());
				infoMap.put("orderTotal",od.getTotAmount().add(od.getDscntAmount()));
				if(infoMap.get("payMethod").equals("both")){
					//포인트가 총가격보다 클때
					if(ezwelPoint.compareTo(java.math.BigDecimal.ZERO)==1){
						infoMap.remove("useEzwelPoint");
						if(ezwelPoint.compareTo(od.getTotAmount().add(od.getDscntAmount()))==1)
							infoMap.put("useEzwelPoint",od.getTotAmount().add(od.getDscntAmount()));
						else{
							infoMap.put("useEzwelPoint",ezwelPoint);
						}
						infoMap.put("usePoint", infoMap.get("useEzwelPoint"));
						infoMap.put("payMoney", infoMap.get("useEzwelPoint"));
					}else{
						infoMap.remove("useEzwelPoint");
						infoMap.put("useEzwelPoint",java.math.BigDecimal.ZERO);
						infoMap.put("usePoint", 0);
						infoMap.put("payMoney", 0);
					}
					ezPointQue.add((BigDecimal)infoMap.get("useEzwelPoint"));
					ezPointStored.add((BigDecimal)infoMap.get("useEzwelPoint"));

				}else if(infoMap.get("payMethod").equals("point")){
					infoMap.put("usePoint", od.getTotAmount().add(od.getDscntAmount()));
					infoMap.put("payMoney", od.getTotAmount().add(od.getDscntAmount()));
				}else if((Boolean)infoMap.get("isCard")==true){
					infoMap.put("usePoint", java.math.BigDecimal.ZERO);
					infoMap.put("payMoney", java.math.BigDecimal.ZERO);
				}
				infoMap.put("orderDd", dayFormat.format(od.getOrderPnttm()));
				infoMap.put("orderTm", hourFormat.format(od.getOrderPnttm()));
				infoMap.put("aspOrderNum",od.getOrderNo());
				/*infoMap.put("aspOrderNum",infoMap.get("orderGroupNo"));*/
				resultMap=ezwelFunc.ezwelPointUse(infoMap);
				ezCodeList.add((String)resultMap.get("ezCode"));
				resultMap.put("ezCodeList",ezCodeList);
				resultMap.put("ezCodes", true);
				resultMap.put("ezPointQue",ezPointQue);
				resultMap.put("ezPointStored",ezPointStored);
			}
			//주문개수가 한개일 때
		}else{
				OrderVO od = orderList.get(0);
				goods.setGoodsId(od.getGoodsId());
				goods=goodsMapper.selectGoods(goods);
				infoMap.put("useMileage",0);
				infoMap.put("useSpecialPoint", 0);
				infoMap.put("goodsNm", goods.getGoodsNm());
				infoMap.put("unitCost", goods.getGoodsPc());
				infoMap.put("buyPrice", goods.getGoodsSplpc());
				infoMap.put("orderCount",orderList.get(0).getOrderCo());
				infoMap.put("orderTotal",orderList.get(0).getTotAmount().add(od.getDscntAmount()));
				if(infoMap.get("payMethod").equals("both")){
					infoMap.put("usePoint", infoMap.get("useEzwelPoint"));
					infoMap.put("payMoney", infoMap.get("useEzwelPoint"));
				}else if(infoMap.get("payMethod").equals("point")){
					infoMap.put("usePoint", od.getTotAmount().add(od.getDscntAmount()));
					infoMap.put("payMoney", od.getTotAmount().add(od.getDscntAmount()));
				}else if((Boolean)infoMap.get("isCard")==true){
					infoMap.put("usePoint", java.math.BigDecimal.ZERO);
					infoMap.put("payMoney", java.math.BigDecimal.ZERO);
				}
				infoMap.put("orderDd", dayFormat.format(od.getOrderPnttm()));
				infoMap.put("orderTm", hourFormat.format(od.getOrderPnttm()));
				infoMap.put("aspOrderNum",od.getOrderNo());
				/*infoMap.put("aspOrderNum", infoMap.get("orderGroupNo"));*/
				resultMap=ezwelFunc.ezwelPointUse(infoMap);
				resultMap.put("ezCode", (String)resultMap.get("ezCode"));
				resultMap.put("ezCodes", false);
		}
		return resultMap;
	}
	/*
	 * 카드 결제
	 *@param infoMap
	 *@param orderList
	 *@throws Exception
	 */
	private HashMap<String, Object> cardPay(HashMap<String, Object> infoMap, List<OrderVO> orderList) throws Exception{
		
		HashMap<String, Object> resultMap = new HashMap<>();
		Biling billing = new Biling();
		GoodsVO goods = new GoodsVO();
		ArrayDeque<java.math.BigDecimal> ezPointQue= new ArrayDeque<java.math.BigDecimal>();
		//주문개수 여러개일때
		if(orderList.size()>1){
			infoMap.put("goodsNm", "모두의구독"+orderList.size()+"개의 상품");
			
			java.math.BigDecimal totPc = new BigDecimal(0);
			
			for(OrderVO od : orderList){
				if(infoMap.get("ezPointQue")!=null){
					ezPointQue= (ArrayDeque<java.math.BigDecimal>) infoMap.get("ezPointQue");
					totPc = totPc.add(od.getDscntAmount().add(od.getTotAmount()).subtract((BigDecimal)ezPointQue.poll()));
				}else{
					totPc = totPc.add(od.getDscntAmount().add(od.getTotAmount()));
				}
			}
			infoMap.put("goodsPc",totPc);
			//주문개수가 한개일 때
		}else{
				OrderVO od = orderList.get(0);
				goods.setGoodsId(od.getGoodsId());
				goods=goodsMapper.selectGoods(goods);
				infoMap.put("goodsNm", goods.getGoodsNm());
				if(infoMap.get("useEzwelPoint")!=null){
					infoMap.put("goodsPc",od.getDscntAmount().add(od.getTotAmount()).subtract((BigDecimal)infoMap.get("useEzwelPoint")));
				}else{
					infoMap.put("goodsPc", od.getDscntAmount().add(od.getTotAmount()));
				}
		}
		//빌링(주문개수에 상관없이 한 번만)
		resultMap = billing.biling(infoMap);
		return resultMap;
	}

	/**
	 * 주문그룹 삭제
	 *
	 * @throws Exception
	 */
	@Override
	public void deleteOrderGroup(OrderVO order) throws Exception {
		List<OrderVO> orderList = orderMapper.selectMyOrderList(order);
		//주문옵션삭제
		for (OrderVO od : orderList) {
			OrderItemVO odItem = new OrderItemVO();
			odItem.setOrderNo(od.getOrderNo());
			orderMapper.deleteOrderItem(odItem);
		}
		//주문삭제
		orderMapper.deleteOrder(order);
		orderMapper.deleteOrderGroup(order);
	}
	
	@Override
	public void updateOrder(OrderVO order) throws Exception {
		
		orderMapper.updateOrder(order);
		
	}
	/**
	 * 구독 변경
	 */
	@Override
	public void updateSbsOrder(OrderVO order) throws Exception {
		
		OrderVO searchVO = new OrderVO();
		searchVO.setOrderNo(order.getOrderNo());
		searchVO = orderMapper.selectOrder(searchVO);
		
		GoodsItemVO goodsItem = new GoodsItemVO();
		OrderItemVO orderItem = new OrderItemVO(); 
		StringBuilder orderInfo = new StringBuilder();
		java.math.BigDecimal dscntPc = new BigDecimal(0);
		String nextSetleDe=null;
		
			
		if(order.getSbscrptMtCycle()!=null || order.getSbscrptDlvyDay()!=null ||order.getSbscrptWeekCycle()!=null || order.getSbscrptDlvyDay()!=null){
			nextSetleDe = nextSbsDate(order);
		}
		order.setNextSetlede(nextSetleDe);
		orderItem.setOrderNo(order.getOrderNo());
		//주문 항목 삭제 후 삽입
		if(order.getOrderItemIdList()!=null && order.getOrderItemIdList().size()>0 && StringUtils.isEmpty(order.getDlvyAdres())){
			orderMapper.deleteOrderItem(orderItem);
			for(int i=0;i<order.getOrderItemIdList().size();i++){
				if(StringUtils.isNotEmpty(order.getOrderItemIdList().get(i))){
					java.math.BigDecimal orderItemNo = orderItemIdGnrService.getNextBigDecimalId();
					goodsItem.setGitemId(order.getOrderItemIdList().get(i));
					goodsItem=goodsItemMapper.selectGoodsItem(goodsItem);
					orderItem.setOitemNo(orderItemNo);
					orderItem.setOrderNo(order.getOrderNo());
					orderItem.setGitemId(order.getOrderItemIdList().get(i));
					orderItem.setGitemNm(goodsItem.getGitemNm());
					orderItem.setGitemPc(goodsItem.getGitemPc());
					orderItem.setGistemSeCode(goodsItem.getGitemSeCode());
					orderMapper.insertOrderItem(orderItem);
					String sym = "+";
					if(orderItem.getGitemPc().compareTo(java.math.BigDecimal.ZERO)==-1){
						sym = "";
						dscntPc = dscntPc.add(goodsItem.getGitemPc());
					}
					if("D".equals(orderItem.getGistemSeCode())){
						orderInfo.append("기본옵션:"+orderItem.getGitemNm()+"("+sym+orderItem.getGitemPc()+")");
					}else if("A".equals(orderItem.getGistemSeCode())){
						orderInfo.append("추가옵션:"+orderItem.getGitemNm()+"("+sym+orderItem.getGitemPc()+")");
					}else if("F".equals(orderItem.getGistemSeCode())){
						orderInfo.append("첫구독옵션:"+orderItem.getGitemNm()+"("+sym+orderItem.getGitemPc()+")");
					}
				}
			}
		}
		//복수 할인 가격 체험
		if("Y".equals(order.getCompnoDscntUseAt())){
			GoodsVO goods = new GoodsVO();
			goods.setGoodsId(searchVO.getGoodsId());
			goods = goodsMapper.selectGoods(goods);
			dscntPc = dscntPc.add(goods.getCompnoDscntPc().multiply(new BigDecimal(order.getOrderCo())));
		}
		
		if(dscntPc.compareTo(new BigDecimal(0))==0){
			order.setDscntAmount(new BigDecimal(0));
		}else{
			order.setDscntAmount(dscntPc);
		}
		
		//할인가격넣기
		orderMapper.updateSbsOrder(order);
		
		/*List<?> orderDlvyList =orderMapper.selectOrderDlvyList(order);*/
		OrderDlvyVO dlvy = orderMapper.selectRecetOrderDlvy(order);
		//주문 배송 내역 수정
		OrderDlvyVO orderDlvy = new OrderDlvyVO();
		if(dscntPc.compareTo(new BigDecimal(0))==0){
			orderDlvy.setDscntAmount(dscntPc.multiply(new BigDecimal(-1)));
		}else{
			orderDlvy.setDscntAmount(new BigDecimal(0));
		}
		orderDlvy.setOrderInfo(orderInfo.toString());
		orderDlvy.setOrderDlvyNo(dlvy.getOrderDlvyNo());
		orderDlvy.setOrderCo(order.getOrderCo());
		orderDlvy.setDlvyAdres(order.getDlvyAdres());
		orderDlvy.setDlvyAdresDetail(order.getDlvyAdresDetail());
		orderDlvy.setDlvyUserNm(order.getDlvyUserNm());
		orderDlvy.setDlvyMssage(order.getDlvyMssage());
		orderDlvy.setDlvyZip(order.getDlvyZip());
		orderDlvy.setTelno(order.getRecptrTelno());
		orderDlvy.setLastUpdusrId(order.getLastUpdusrId());
		if(order.getSbscrptMtCycle()!=null || order.getSbscrptDlvyDay()!=null){
			int sbscrptMtCycle =  order.getSbscrptMtCycle()!=null?order.getSbscrptMtCycle():null;
			int sbscrptDlvyDay =  order.getSbscrptDlvyDay()!=null?order.getSbscrptDlvyDay():null;
			orderDlvy.setSbscrptMtCycle(sbscrptMtCycle);
			orderDlvy.setSbscrptDlvyDay(sbscrptDlvyDay);
		}else if(order.getSbscrptWeekCycle()!=null || order.getSbscrptDlvyDay()!=null){
			int sbscrptWeekCycle = order.getSbscrptWeekCycle()!=null?order.getSbscrptWeekCycle():null;
			int sbscrptDlvyWd = order.getSbscrptDlvyWd()!=null?order.getSbscrptDlvyWd():null;
			orderDlvy.setSbscrptWeekCycle(sbscrptWeekCycle);
			orderDlvy.setSbscrptDlvyWd(sbscrptDlvyWd);
		}
		orderDlvyMapper.updateOrderDlvy(orderDlvy);
		
		//주문 결제테이블 수정
		OrderSetleVO orderSetle = new OrderSetleVO();
		orderSetle.setOrderSetleNo(dlvy.getOrderSetleNo());
		orderSetle.setSetleTotAmount(new BigDecimal(-1));
		if(nextSetleDe!=null){
			orderSetle.setSetlePrarnde(nextSetleDe);
		}
		orderSetleMapper.updateOrderSetle(orderSetle);
	}
	
	
	/**
	 * 마이페이지 > 구독중인 상품 목록 갯수
	 */

	@Override
	public int selectSubscribeNowListCnt(OrderVO searchVO) {
		return orderMapper.selectSubscribeNowListCnt(searchVO);
	}

	/**
	 * 마이페이지 > 구독중인 상품 목록
	 */

	@Override
	public List<?> selectSubscribeNowList(OrderVO searchVO) {
		return orderMapper.selectSubscribeNowList(searchVO);
	}

	@Override
	public OrderDlvyVO selectRecetOrderDlvy(OrderVO searchVO) {
		return orderMapper.selectRecetOrderDlvy(searchVO);
	}
	
	/**
	 * 주문 1개 상세 정보
	 * @throws Exception 
	 */
	@Override
	public OrderVO selectOrder(OrderVO searchVO) throws Exception {
		searchVO=orderMapper.selectOrder(searchVO);
		if(searchVO!=null){
			if("CPN".equals(searchVO.getOrderKndCode())){
				GoodsCouponVO goodsCoupon = new GoodsCouponVO();
				goodsCoupon.setOrderNo(searchVO.getOrderNo());
				List<GoodsCouponVO> orderCouponList = goodsCouponMapper.selectGoodsCouponList(goodsCoupon);
				searchVO.setOrderCouponList(orderCouponList);
			}
		}
		return searchVO;
	}
	
	@Override
	public void deleteOrderItem(OrderItemVO searchVO) {
		orderMapper.deleteOrderItem(searchVO);
	}

	@Override
	public EgovMap selectModOrder(OrderVO searchVO) throws Exception {
		EgovMap order = orderMapper.selectModOrder(searchVO);
		//order 아이템 리스트 
		/*OrderItemVO orderItem = new OrderItemVO();*/
		List<OrderItemVO> orderItemList = (List<OrderItemVO>) orderMapper.selectOrderItemList(searchVO);
		for(OrderItemVO o : orderItemList){
			if(o.getGistemSeCode().equals("D")){
			order.put("ditem", o);
			}else if(o.getGistemSeCode().equals("A")){
			order.put("aitem", o);
			}else if(o.getGistemSeCode().equals("F")){
			order.put("fitem", o);
			}
		}
		
		//카트 상품정보
		GoodsVO goods = new GoodsVO();
		goods.setGoodsId((String)order.get("goodsId"));
		goods=goodsMapper.selectGoods(goods);
		order.put("goods", goods);
		
		//상품 아이템 리스트
		GoodsItemVO goodsItem = new GoodsItemVO();
		goodsItem.setGoodsId((String)order.get("goodsId"));
		goodsItem.setGitemSeCode("D");
		List<GoodsItemVO> doptList = goodsItemMapper.selectGoodsItemList(goodsItem);
		order.put("dopt", doptList);
		goodsItem.setGitemSeCode("A");
		List<GoodsItemVO> aoptList = goodsItemMapper.selectGoodsItemList(goodsItem);
		order.put("aopt", aoptList);
		goodsItem.setGitemSeCode("F");
		List<GoodsItemVO> foptList = goodsItemMapper.selectGoodsItemList(goodsItem);
		order.put("fopt", foptList);
		return order;
	}
	/**
	 * 주문 1개당 옵션 목록
	 */
	@Override
	public List<?> selectOrderItemList(OrderVO searchVO) {
		return orderMapper.selectOrderItemList(searchVO);
	}
	
	/**
	 * 주문 1개당 업체요청정보
	 */
	@Override
	public List<?> selectOrderQItemList(OrderVO searchVO) {
		return orderMapper.selectOrderQItemList(searchVO);
	}
	
	/**
	 * 주문 1개당 배송 목록
	 */
	@Override
	public List<?> selectOrderDlvyList(OrderVO searchVO) {
		return orderMapper.selectOrderDlvyList(searchVO);
	}

	
	/*
	 * 다음 구독 날짜 계산
	 * @param orderVO
	 * @return
	 * throws Exception
	 */
	private String nextSbsDate(OrderVO order) throws Exception {
		/*order=orderMapper.selectOrder(order);*/
		Calendar cal = Calendar.getInstance();
		cal.setTime(order.getOrderPnttm());//주문날짜
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
		
		if(order.getSbscrptCycleSeCode().equalsIgnoreCase("WEEK")){
			int curWeek = cal.get(Calendar.DAY_OF_WEEK);	//구독 주기(주)
			int sbsWkCycle = order.getSbscrptWeekCycle();	//구독 주기(요일)
			int sbsWkWd = order.getSbscrptDlvyWd(); 	//배송요일
			cal.add(Calendar.WEEK_OF_MONTH, sbsWkCycle);
			cal.add(Calendar.DAY_OF_WEEK, sbsWkWd-curWeek);
			
			
			Calendar cal2 = Calendar.getInstance();
			Calendar cal1 = Calendar.getInstance();
			
			cal2.setTime(cal.getTime());
			cal1.setTime(order.getOrderPnttm());
			
			cal2.add(Calendar.DATE, -cal1.get(Calendar.DATE));
			int day = Integer.valueOf(dateFormat.format(cal2.getTime()).substring(6,8));
			
			if(day < 4){
				cal.add(Calendar.WEEK_OF_MONTH, 1);
			}
			
		}else if(order.getSbscrptCycleSeCode().equalsIgnoreCase("MONTH")){
			int curDay = cal.get(Calendar.DAY_OF_MONTH);	
			int sbsMtCycle = order.getSbscrptMtCycle();	//구독 주기 (월)
			int sbsMtdy = order.getSbscrptDlvyDay();	//배송 날짜
			cal.add(Calendar.MONTH, sbsMtCycle);
            /*cal.add(Calendar.DAY_OF_MONTH, sbsMtdy-curDay);*/
           
           
			Calendar cal2 = Calendar.getInstance();
			Calendar cal1 = Calendar.getInstance();
			
			cal2.setTime(cal.getTime());
			cal1.setTime(order.getOrderPnttm());
			
			cal2.add(Calendar.DAY_OF_MONTH, -cal1.get(Calendar.DAY_OF_MONTH));
			
			int day = Integer.valueOf(dateFormat.format(cal2.getTime()).substring(6,8));
			
			if(day < 15){
				cal.add(Calendar.MONTH, 1);
			}
			int actualMaximum = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
			if(sbsMtdy > actualMaximum) {
                sbsMtdy = actualMaximum;
			}           
			cal.set(Calendar.DAY_OF_MONTH, sbsMtdy);
		}
		return dateFormat.format(cal.getTime());
	}
	
	/**
	 * 주문 취소, 주문 취소 이력 생성
	 */
	@Override
	@Transactional
	public void cancelOrder(OrderVO searchVO) {
		searchVO.setOrderSttusCode("ST99");
		orderMapper.updateOrderStatus(searchVO);
		
		OrderDlvyVO searchDlvy = new OrderDlvyVO();
		searchDlvy.setOrderNo(searchVO.getOrderNo());
		searchDlvy.setOrderReqSttusCode("C");
		searchDlvy.setReqTyCode("C01");
		
		System.out.println("=============================" + searchDlvy.getOrderNo());

		orderDlvyMapper.updateOrderDlvyStatus(searchDlvy);

		OrderReqHistVO vo = new OrderReqHistVO();
		vo.setOrderDlvyNo(orderDlvyMapper.selectMaxOrderDlvyNoByOrderNo(searchDlvy));
		vo.setReqTyCode(searchDlvy.getReqTyCode());
		
		orderReqHistMapper.insertOrderReqHist(vo); // 이력 생성

	}

	/**
	 * 마이페이지 교환/환불 목록
	 */
	
	@Override
	public List<?> selectMyRefundList(OrderVO searchVO) {
		return orderDlvyMapper.selectMyRefundList(searchVO);
	}
	
	/**
	 * 마이페이지 교환/환불 목록 갯수
	 */
	@Override
	public int selectMyRefundListCnt(OrderVO searchVO) {
		return orderDlvyMapper.selectMyRefundListCnt(searchVO);
	}

	/**
	 * 교환/반품 접수
	 * @throws Exception 
	 */
	@Override
	@Transactional
	public void refundOrder(OrderDlvyVO dlvyVO, QainfoVO qainfo) throws Exception {
	
		java.math.BigDecimal orderDlvyNo = orderDlvyMapper.selectMaxOrderDlvyNoByOrderNo(dlvyVO);
		//배송상태, 요청상태 변경
		dlvyVO.setOrderDlvyNo(orderDlvyNo);
		orderDlvyMapper.updateDlvySttusCode(dlvyVO);
		orderDlvyMapper.updateOrderDlvyStatus(dlvyVO);
		// 교환/반품 문의 접수
		String qaId = qainfoIdGnrService.getNextStringId();
		qainfo.setQaId(qaId);
		qainfo.setWritngDe(CommonUtils.getCurrentDateTime());
		qainfoMapper.insertQainfo(qainfo);
		// 이력 생성
		OrderReqHistVO histVO = new OrderReqHistVO();
		histVO.setOrderDlvyNo(orderDlvyNo);
		histVO.setReqTyCode(dlvyVO.getReqTyCode());
		histVO.setReasonCn(qainfo.getQestnCn());
		orderReqHistMapper.insertOrderReqHist(histVO);
	}
	/**
	 * 구독해지접수
	 */
	@Override
	@Transactional
	public void stopSubscribeRequest(OrderVO searchVO) {
		searchVO.setOrderSttusCode("ST01");
		orderMapper.updateOrderStatus(searchVO);
		OrderDlvyVO dlvyVO = new OrderDlvyVO();
		dlvyVO.setOrderReqSttusCode("T");
		dlvyVO.setReqTyCode("T01");
		dlvyVO.setOrderNo(searchVO.getOrderNo());
		orderDlvyMapper.updateOrderDlvyStatusReady(dlvyVO);
		
		// TODO 이력 테이블에 insert
	}
	/**
	 * 구독해지 확인
	 */
	@Override
	@Transactional
	public void stopSubscribeConfirm(OrderVO searchVO) {
		searchVO.setOrderSttusCode("ST04");
		orderMapper.updateOrderStatus(searchVO);
		OrderDlvyVO dlvyVO = new OrderDlvyVO();
		dlvyVO.setOrderReqSttusCode("T");
		dlvyVO.setReqTyCode("T02");
		dlvyVO.setOrderNo(searchVO.getOrderNo());
		orderDlvyMapper.updateOrderDlvyStatusReady(dlvyVO);
		orderSetleMapper.stopSubscribeConfirm(searchVO);
		
		// TODO 이력 테이블에 insert
	}
	/**
	 * 구독해지 취소
	 */
	@Override
	public void stopSubscribeCancel(OrderVO searchVO) {
		searchVO.setOrderSttusCode("ST02");
		orderMapper.updateOrderStatus(searchVO);
		OrderDlvyVO dlvyVO = new OrderDlvyVO();
		dlvyVO.setOrderReqSttusCode("W");
		dlvyVO.setReqTyCode("T03");
		
		dlvyVO.setOrderNo(searchVO.getOrderNo());
		orderDlvyMapper.updateOrderDlvyStatusReady(dlvyVO);
		//orderSetleMapper.stopSubscribeCancel(searchVO);
		
		OrderReqHistVO vo = new OrderReqHistVO();	
		vo.setOrderDlvyNo(orderDlvyMapper.selectMaxOrderDlvyNoByOrderNo(dlvyVO));	
		vo.setReqTyCode("T03");	
			
		orderReqHistMapper.insertOrderReqHist(vo); // 이력 생성
		
	}
	/**
	 * 구독 이용 횟수
	 */
	@Override
	public int selectSbsCnt(OrderVO searchVO) {
		
		return orderMapper.selectSbsCnt(searchVO);
	}
	/**
	 *1회 체험 구독 이용횟수 
	 */
	@Override
	public int selectExprnCnt(OrderVO searchVO) {
		return orderMapper.selectExprnCnt(searchVO);
	}
	/**
	 * 이벤트 상품 
	 */
	@Override
	public int selectEvtGoodsOrderCnt(OrderVO searchVO) {
		return orderMapper.selectEvtGoodsOrderCnt(searchVO);
	}
	
	
}
