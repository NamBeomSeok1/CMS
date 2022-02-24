package modoo.module.shop.goods.setle.service.impl;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;
import modoo.module.biling.service.Biling;
import modoo.module.common.util.NumberUtil;
import modoo.module.shop.goods.info.service.GoodsVO;
import modoo.module.shop.goods.info.service.impl.GoodsMapper;
import modoo.module.shop.goods.order.service.OrderVO;
import modoo.module.shop.goods.order.service.impl.OrderMapper;
import modoo.module.shop.goods.setle.service.OrderSetleService;
import modoo.module.shop.goods.setle.service.OrderSetleVO;

@Service("orderSetleService")
public class OrderSetleServiceImpl extends EgovAbstractServiceImpl implements OrderSetleService {
	
	@Resource(name = "orderSetleMapper")
	private OrderSetleMapper orderSetleMapper;

	@Resource(name = "orderMapper")
	private OrderMapper orderMapper;

	@Resource(name = "goodsMapper")
	private GoodsMapper goodsMapper;
	
	/**
	 * 결제 목록
	 */
	@Override
	public List<?> selectOrderSetleList(OrderSetleVO searchVO) {
		return orderSetleMapper.selectOrderSetleList(searchVO);
	}
	
	/**
	 * 결제 목록 갯수
	 */
	@Override
	public int selectOrderSetleListCnt(OrderSetleVO searchVO) {
		return orderSetleMapper.selectOrderSetleListCnt(searchVO);
	}
	/*
	 *결제 등록 
	 */
	@Override
	public void insertOrderSetle(OrderSetleVO orderSetle) {
		String tempKey  = NumberUtil.numberGen(4)+System.currentTimeMillis();
		orderSetle.setTempKey(tempKey);
		if(orderSetle.getIsSbs()==true && orderSetle.getIsSbs()!=null){
			orderSetle.setSetleSttusCode("R");
			orderSetle.setSetleResultMssage(null);
			orderSetle.setSetleResultCode(null);
		}
		orderSetleMapper.insertSelectOrder(orderSetle);
		
		//pk조회
		orderSetle.setTempKey(tempKey);
		OrderSetleVO newOrderSetle = orderSetleMapper.selectOrderSetle(orderSetle);
		HashMap<String, Object> map =new HashMap<>();
		map.put("orderSetleNo", newOrderSetle.getOrderSetleNo());
		map.put("orderNo", orderSetle.getSearchOrderNo());
		
		//주문배송 저장
		insertNextSTN_ORDER_DLVY(map);
	}
	
	/**
	 * 빌링 프로세스
	 * @param billingInfo
	 * @return
	 * @Exception
	 */
	@Override
	public HashMap<String, Object> billingProcess(HashMap<String, Object> billingMap) throws Exception{
			
			/*LoginVO user = (LoginVO)EgovUserDetailsHelper.getAuthorities();*/
			HashMap<String, Object> resultMap = new HashMap<>();
			Biling billing = new Biling();
			GoodsVO goods = new GoodsVO();
			OrderVO order = new OrderVO();
			order.setOrdrrId((String)billingMap.get("userId"));
			order.setOrderGroupNo((BigDecimal)billingMap.get("orderGroupNo"));
			List<OrderVO> resultList = orderMapper.selectMyOrderList(order);
			//주문개수가 여러개일 때
			if(resultList.size()>1){
				billingMap.put("goodsNm", "모두의구독"+resultList.size()+"개의 상품");
				java.math.BigDecimal totPc = java.math.BigDecimal.ZERO; //null;
				for(OrderVO od : resultList){
					totPc.add(od.getTotAmount());
				}
				billingMap.put("goodsPc", totPc);
				//주문개수가 한개일 때
			}else{
				for(OrderVO od : resultList){
					goods.setGoodsId(od.getGoodsId());
					goods=goodsMapper.selectGoods(goods);
					billingMap.put("goodsNm", goods.getGoodsNm());
					billingMap.put("goodsPc", od.getTotAmount());
				}
			}
			//빌링(주문개수에 상관없이 한 번만)
			HashMap<String, Object> bilingResult = billing.biling(billingMap);
			OrderSetleVO orderSetle = new OrderSetleVO();
			//결제성공
			if((Boolean)bilingResult.get("success")==true){
				resultMap.put("success",bilingResult.get("success"));
				resultMap.put("resultMsg",bilingResult.get("resultMsg"));
				orderSetle.setSetleSttusCode("S");	
				orderSetle.setSetleTyCode("CARD");	//이지웰때 변경필요
				orderSetle.setSetleResultCode(String.valueOf(bilingResult.get("resultCode")));
				orderSetle.setSetleResultMssage(String.valueOf(bilingResult.get("resultMsg")));
				for(OrderVO od : resultList){
					od.setBillKey((String)bilingResult.get("billKey"));
					od.setCardId((String)billingMap.get("cardId"));
					
					//주문정보수정(빌키,카드고유id)
					orderMapper.updateOrder(od);
					//주문결제 테이블 생성
					orderSetle.setIniSetleConfmNo((String)bilingResult.get("iniCode"));
					orderSetle.setSetleTotAmount(od.getTotAmount());
					orderSetle.setSearchOrderNo(od.getOrderNo());
					insertOrderSetle(orderSetle);
					//구독상품일 경우 다음 날짜 추가 구독일땐 한 번더
					if(od.getOrderKndCode().equals("SBS")){
						od.setNextSetlede(nextSbsDate(od));
						od.setNowOdr(od.getNowOdr()+1);
						//다음차수,날짜 추가
						orderMapper.updateOrder(od);
						orderSetle.setSetlePrarnde(nextSbsDate(od));
						orderSetle.setIsSbs(true);
						insertOrderSetle(orderSetle);
						}
					}
				//결제 실패
				}else if((Boolean)bilingResult.get("success")==false){
					resultMap.put("success",bilingResult.get("success"));
					resultMap.put("resultMsg",bilingResult.get("resultMsg"));
					orderSetle.setSetleTyCode("CARD");	//이지웰때 변경필요
					orderSetle.setSetleSttusCode("F");
					orderSetle.setSetleResultCode(String.valueOf(bilingResult.get("resultCode")));
					orderSetle.setSetleResultMssage(String.valueOf(bilingResult.get("resultMsg")));
					for(OrderVO od : resultList){
						od.setBillKey((String)bilingResult.get("billKey"));
						od.setCardId((String)billingMap.get("cardId"));
						//주문정보수정(빌키,카드고유id)
						orderMapper.updateOrder(od);
						//주문결제 테이블 생성
						orderSetle.setIniSetleConfmNo((String)bilingResult.get("iniCode"));
						orderSetle.setSetleTotAmount(od.getTotAmount());
						orderSetle.setSearchOrderNo(od.getOrderNo());
						insertOrderSetle(orderSetle);
						//구독상품일 경우 다음 날짜 추가 구독일땐 한 번더
						if(od.getOrderKndCode().equals("SBS")){
							od.setNextSetlede(nextSbsDate(od));
							od.setNowOdr(od.getNowOdr()+1);
							//다음차수,날짜 추가
							orderMapper.updateOrder(od);
							orderSetle.setSetlePrarnde(nextSbsDate(od));
							orderSetle.setIsSbs(true);
							insertOrderSetle(orderSetle);
						}
					}
				}
					
		return resultMap;
	}
			
	/*
	 * 다음 구독 날짜 계산
	 * @param orderVO
	 * @return
	 * throws Exception
	 */
	private String nextSbsDate(OrderVO order) throws Exception {

		order=orderMapper.selectOrder(order);
		Calendar cal = Calendar.getInstance();
		cal.setTime(order.getOrderPnttm());
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
		
		if(order.getSbscrptCycleSeCode().equalsIgnoreCase("WEEK")){
			int curWeek = cal.get(Calendar.DAY_OF_WEEK);	//구독 주기(주)
			int sbsWkCycle = order.getSbscrptWeekCycle();	//구독 주기(주)
			int sbsWkWd = order.getSbscrptDlvyWd()+1; 	//배송요일
			cal.add(Calendar.WEEK_OF_MONTH, sbsWkCycle);
			cal.add(Calendar.DAY_OF_WEEK, sbsWkWd-curWeek);
			
		}else if(order.getSbscrptCycleSeCode().equalsIgnoreCase("MONTH")){
			int curDay = cal.get(Calendar.DAY_OF_MONTH);	//구독 주기(주)
			int sbsMtCycle = order.getSbscrptWeekCycle();	//구독 주기 (월)
			int sbsMtdy = order.getSbscrptWeekCycle();	//배송 날짜
				cal.add(Calendar.WEEK_OF_MONTH, sbsMtCycle);
				cal.add(Calendar.DAY_OF_MONTH, sbsMtdy-curDay);
			}
		return dateFormat.format(cal.getTime());
		}

	@Override
	public void insertNextSTN_ORDER_DLVY(HashMap<String, Object> map) {
		orderSetleMapper.insertNextSTN_ORDER_DLVY(map);
	}
	
	@Override
	public void updateOrderSetle(OrderSetleVO orderSetle) {
		orderSetleMapper.updateOrderSetle(orderSetle);
	}
	
}
