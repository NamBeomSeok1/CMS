package modoo.module.shop.goods.order.service;

import java.math.BigDecimal;
import java.util.List;

import modoo.module.common.service.CommonDefaultSearchVO;
import modoo.module.shop.goods.info.service.GoodsCouponVO;
import modoo.module.shop.goods.info.service.GoodsItemVO;
import modoo.module.shop.goods.info.service.GoodsVO;

@SuppressWarnings("serial")
public class OrderVO extends CommonDefaultSearchVO {
	
	/** 주문고유번호 */
	//private java.math.BigDecimal orderNo;
	private String orderNo;
	/** 주문그룹고유번호 */
	private java.math.BigDecimal orderGroupNo;
	/** 장바구니고유번호 */
	private java.math.BigDecimal cartNo;
	/** 상품고유ID */
	private String goodsId;
	/** 주문종류코드 */
	private String orderKndCode;
	/** 주문결제구분코드 */
	private String orderSetleSeCode;
	/** 주문주기구분코드 */
	private String sbscrptCycleSeCode;
	/** 구독_주_주기 */
	private Integer sbscrptWeekCycle;
	/** 구독배송요일 */
	private Integer sbscrptDlvyWd;
	/** 구독이용_주 */
	private Integer sbscrptUseWeek;
	/** 구독이용_월_주기 */
	private Integer sbscrptMtCycle;
	/** 구독이용월 */
	private Integer sbscrptUseMt;
	/** 구독배송일 */
	private Integer sbscrptDlvyDay;
	/** 주문수량 */
	private Integer orderCo;
	/** 상품금액 */
	private java.math.BigDecimal goodsAmount;
	/** 할인금액 */
	private java.math.BigDecimal dscntAmount;
	/** 배송비금액 */
	private java.math.BigDecimal dlvyAmount;
	/** 도서산관추가배송금액*/
	private java.math.BigDecimal islandDlvyAmount;
	/** 제주추가배송금액*/
	private java.math.BigDecimal jejuDlvyAmount;
	/** 적립금 */
	private java.math.BigDecimal rsrvmney;
	/** B2C 적립금 */
	/** 총금액 */
	private java.math.BigDecimal totAmount;
	/** 1회체험복수할인금액 */
	private java.math.BigDecimal compnoDscntAmount;
	/** 배송지명 */
	private String dlvyAdresNm;
	/** 수령인명 */
	private String dlvyUserNm;
	/** 연락처 */
	private String telno;
	/** 수령인연락처 */
	private String recptrTelno;
	private String recptrTelno1; //앞자리 3 
	private String recptrTelno2; //중간자리 4
	private String recptrTelno3; //끝자리 4
	/** 주문취소사유 */
	private String orderCanclResn;
	/** 배송우편번호 */
	private String dlvyZip;
	/** 배송주소 */
	private String dlvyAdres;
	/** 배송주소 상세 */
	private String dlvyAdresDetail;
	/** 배송메세지 */
	private String dlvyMssage;
	/** 주문상태코드 : ST01 구독요청, ST02 구독확인, ST03 구독시작 */
	private String orderSttusCode;
	/** 주문시점 */
	private java.util.Date orderPnttm;
	/** 주문취소시점 */
	private java.util.Date orderCanclPnttm;
	/** 주문자ID */
	private String ordrrId;
	/** 주문자명 */
	private String ordrrNm;
	/** 주문자명 */
	private String ordrrEmail;
	/** 주문자성별(M,F,E)*/
	private String ordrrSexdstn;
	/** 주문자연령대(0,10,20...90) */
	private String ordrrAgrde;
	/** 카도고유ID */
	private String cardId;
	/** 빌키 */
	private String billKey;
	/** 현재차수 */
	private Integer nowOdr;
	/** 현재차수 */
	private String nextSetlede;
	/** 개인정보동의 여부 */
	private String indvdlinfoAgreAt;
	/** 구매조건동의 여부 */
	private String purchsCndAgreAt;
	/** 최초등록시점 */
	private java.util.Date frstRegistPnttm;
	/** 최초등록자ID */
	private String frstRegisterId;
	/** 최종수정시점 */
	private java.util.Date lastUpdtPnttm;
	/** 최종수정자ID */
	private String lastUpdusrId;
	/** 사용여부 */
	private String useAt;
	
	/** 주문항목 목록 */
	private List<OrderItemVO> orderItemList;

	/** 업체요청사항 목록 */
	private List<OrderItemVO> orderQItemList;
	
	/** 주문항목 id배열*/
	private List<String> orderItemIdList;
	
	/** 주문쿠폰리스트*/
	private List<GoodsCouponVO> orderCouponList; 
	
	/** 주문상품정보*/
	private GoodsVO goods;

	/** 주문상품정보*/
	private String goodsTitleImagePath;

	/** 주문상품정보*/
	private java.math.BigDecimal nextTotPc;
	
	/** 주문요청상태코드 */
	private String orderReqSttusCode;
	
	/** 주문상태코드명 */
	private String orderSttusCodeNm;
	
	/* 이니시스 결제번호 */
	private String iniSetleConfmNo;
	/* 요청유형코드 */
	private String reqTyCode;
	/*도서산간체크*/
	private String islandDlvyChk;
	/*무료배송가격*/
	private java.math.BigDecimal freeDlvyPc;
	/*1회 체험 여부*/
	private String exprnUseAt;
	/*1회 복수구매 할인 여부*/
	private String compnoDscntUseAt;
	/*1회 체험 가격*/
	private java.math.BigDecimal exprnAmount;

	/** 배송상태 **/
	private String dlvySttusCodeNm;

	private java.math.BigDecimal orderDlvyNo;

	public String getDlvySttusCodeNm() {
		return dlvySttusCodeNm;
	}

	public void setDlvySttusCodeNm(String dlvySttusCodeNm) {
		this.dlvySttusCodeNm = dlvySttusCodeNm;
	}

	public BigDecimal getOrderDlvyNo() {
		return orderDlvyNo;
	}

	public void setOrderDlvyNo(BigDecimal orderDlvyNo) {
		this.orderDlvyNo = orderDlvyNo;
	}

	public java.math.BigDecimal getCompnoDscntAmount() {
		return compnoDscntAmount;
	}
	public void setCompnoDscntAmount(java.math.BigDecimal compnoDscntAmount) {
		this.compnoDscntAmount = compnoDscntAmount;
	}
	public String getCompnoDscntUseAt() {
		return compnoDscntUseAt;
	}
	public void setCompnoDscntUseAt(String compnoDscntUseAt) {
		this.compnoDscntUseAt = compnoDscntUseAt;
	}
	public String getIslandDlvyChk() {
		return islandDlvyChk;
	}
	public void setIslandDlvyChk(String islandDlvyChk) {
		this.islandDlvyChk = islandDlvyChk;
	}
	public java.math.BigDecimal getNextTotPc() {
		return nextTotPc;
	}
	public void setNextTotPc(java.math.BigDecimal nextTotPc) {
		this.nextTotPc = nextTotPc;
	}
	public String getGoodsTitleImagePath() {
		return goodsTitleImagePath;
	}
	public void setGoodsTitleImagePath(String goodsTitleImagePath) {
		this.goodsTitleImagePath = goodsTitleImagePath;
	}
	public String getOrderNo() {
		return orderNo;
	}
	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}
	public java.math.BigDecimal getOrderGroupNo() {
		return orderGroupNo;
	}
	public void setOrderGroupNo(java.math.BigDecimal orderGroupNo) {
		this.orderGroupNo = orderGroupNo;
	}
	public java.math.BigDecimal getCartNo() {
		return cartNo;
	}
	public void setCartNo(java.math.BigDecimal cartNo) {
		this.cartNo = cartNo;
	}
	public String getGoodsId() {
		return goodsId;
	}
	public void setGoodsId(String goodsId) {
		this.goodsId = goodsId;
	}
	public String getOrderKndCode() {
		return orderKndCode;
	}
	public void setOrderKndCode(String orderKndCode) {
		this.orderKndCode = orderKndCode;
	}
	public String getSbscrptCycleSeCode() {
		return sbscrptCycleSeCode;
	}
	public void setSbscrptCycleSeCode(String sbscrptCycleSeCode) {
		this.sbscrptCycleSeCode = sbscrptCycleSeCode;
	}
	public Integer getSbscrptWeekCycle() {
		return sbscrptWeekCycle;
	}
	public void setSbscrptWeekCycle(Integer sbscrptWeekCycle) {
		this.sbscrptWeekCycle = sbscrptWeekCycle;
	}
	public Integer getSbscrptDlvyWd() {
		return sbscrptDlvyWd;
	}
	public void setSbscrptDlvyWd(Integer sbscrptDlvyWd) {
		this.sbscrptDlvyWd = sbscrptDlvyWd;
	}
	public Integer getSbscrptUseWeek() {
		return sbscrptUseWeek;
	}
	public void setSbscrptUseWeek(Integer sbscrptUseWeek) {
		this.sbscrptUseWeek = sbscrptUseWeek;
	}
	public Integer getSbscrptMtCycle() {
		return sbscrptMtCycle;
	}
	public void setSbscrptMtCycle(Integer sbscrptMtCycle) {
		this.sbscrptMtCycle = sbscrptMtCycle;
	}
	public Integer getSbscrptUseMt() {
		return sbscrptUseMt;
	}
	public void setSbscrptUseMt(Integer sbscrptUseMt) {
		this.sbscrptUseMt = sbscrptUseMt;
	}
	public Integer getSbscrptDlvyDay() {
		return sbscrptDlvyDay;
	}
	public void setSbscrptDlvyDay(Integer sbscrptDlvyDay) {
		this.sbscrptDlvyDay = sbscrptDlvyDay;
	}
	public Integer getOrderCo() {
		return orderCo;
	}
	public void setOrderCo(Integer orderCo) {
		this.orderCo = orderCo;
	}
	public java.math.BigDecimal getGoodsAmount() {
		return goodsAmount;
	}
	public void setGoodsAmount(java.math.BigDecimal goodsAmount) {
		this.goodsAmount = goodsAmount;
	}
	public java.math.BigDecimal getDscntAmount() {
		return dscntAmount;
	}
	public void setDscntAmount(java.math.BigDecimal dscntAmount) {
		this.dscntAmount = dscntAmount;
	}
	public java.math.BigDecimal getDlvyAmount() {
		return dlvyAmount;
	}
	public void setDlvyAmount(java.math.BigDecimal dlvyAmount) {
		this.dlvyAmount = dlvyAmount;
	}
	public java.math.BigDecimal getRsrvmney() {
		return rsrvmney;
	}
	public void setRsrvmney(java.math.BigDecimal rsrvmney) {
		this.rsrvmney = rsrvmney;
	}
	public java.math.BigDecimal getIslandDlvyAmount() {
		return islandDlvyAmount;
	}
	public void setIslandDlvyAmount(java.math.BigDecimal islandDlvyAmount) {
		this.islandDlvyAmount = islandDlvyAmount;
	}
	public java.math.BigDecimal getJejuDlvyAmount() {
		return jejuDlvyAmount;
	}
	public void setJejuDlvyAmount(java.math.BigDecimal jejuDlvyAmount) {
		this.jejuDlvyAmount = jejuDlvyAmount;
	}
	public java.math.BigDecimal getTotAmount() {
		return totAmount;
	}
	public void setTotAmount(java.math.BigDecimal totAmount) {
		this.totAmount = totAmount;
	}
	public String getDlvyAdresNm() {
		return dlvyAdresNm;
	}
	public void setDlvyAdresNm(String dlvyAdresNm) {
		this.dlvyAdresNm = dlvyAdresNm;
	}
	public String getDlvyUserNm() {
		return dlvyUserNm;
	}
	public void setDlvyUserNm(String dlvyUserNm) {
		this.dlvyUserNm = dlvyUserNm;
	}
	public List<GoodsCouponVO> getOrderCouponList() {
		return orderCouponList;
	}
	public void setOrderCouponList(List<GoodsCouponVO> orderCouponList) {
		this.orderCouponList = orderCouponList;
	}
	public String getOrdrrSexdstn() {
		return ordrrSexdstn;
	}
	public void setOrdrrSexdstn(String ordrrSexdstn) {
		this.ordrrSexdstn = ordrrSexdstn;
	}
	public String getOrdrrAgrde() {
		return ordrrAgrde;
	}
	public void setOrdrrAgrde(String ordrrAgrde) {
		this.ordrrAgrde = ordrrAgrde;
	}
	public String getTelno() {
		return telno;
	}
	public void setTelno(String telno) {
		this.telno = telno;
	}
	public String getDlvyZip() {
		return dlvyZip;
	}
	public void setDlvyZip(String dlvyZip) {
		this.dlvyZip = dlvyZip;
	}
	public String getDlvyAdres() {
		return dlvyAdres;
	}
	public void setDlvyAdres(String dlvyAdres) {
		this.dlvyAdres = dlvyAdres;
	}
	public String getDlvyAdresDetail() {
		return dlvyAdresDetail;
	}
	public void setDlvyAdresDetail(String dlvyAdresDetail) {
		this.dlvyAdresDetail = dlvyAdresDetail;
	}
	public String getDlvyMssage() {
		return dlvyMssage;
	}
	public void setDlvyMssage(String dlvyMssage) {
		this.dlvyMssage = dlvyMssage;
	}
	public String getOrderSttusCode() {
		return orderSttusCode;
	}
	public void setOrderSttusCode(String orderSttusCode) {
		this.orderSttusCode = orderSttusCode;
	}
	public java.util.Date getOrderPnttm() {
		return orderPnttm;
	}
	public void setOrderPnttm(java.util.Date orderPnttm) {
		this.orderPnttm = orderPnttm;
	}
	public java.util.Date getOrderCanclPnttm() {
		return orderCanclPnttm;
	}
	public void setOrderCanclPnttm(java.util.Date orderCanclPnttm) {
		this.orderCanclPnttm = orderCanclPnttm;
	}
	public String getOrdrrId() {
		return ordrrId;
	}
	public void setOrdrrId(String ordrrId) {
		this.ordrrId = ordrrId;
	}
	public String getOrdrrNm() {
		return ordrrNm;
	}
	public void setOrdrrNm(String ordrrNm) {
		this.ordrrNm = ordrrNm;
	}
	public String getOrdrrEmail() {
		return ordrrEmail;
	}
	public void setOrdrrEmail(String ordrrEmail) {
		this.ordrrEmail = ordrrEmail;
	}
	public java.util.Date getFrstRegistPnttm() {
		return frstRegistPnttm;
	}
	public void setFrstRegistPnttm(java.util.Date frstRegistPnttm) {
		this.frstRegistPnttm = frstRegistPnttm;
	}
	public String getFrstRegisterId() {
		return frstRegisterId;
	}
	public void setFrstRegisterId(String frstRegisterId) {
		this.frstRegisterId = frstRegisterId;
	}
	public java.util.Date getLastUpdtPnttm() {
		return lastUpdtPnttm;
	}
	public void setLastUpdtPnttm(java.util.Date lastUpdtPnttm) {
		this.lastUpdtPnttm = lastUpdtPnttm;
	}
	public String getLastUpdusrId() {
		return lastUpdusrId;
	}
	public void setLastUpdusrId(String lastUpdusrId) {
		this.lastUpdusrId = lastUpdusrId;
	}
	public String getUseAt() {
		return useAt;
	}
	public void setUseAt(String useAt) {
		this.useAt = useAt;
	}
	public String getRecptrTelno() {
		return recptrTelno;
	}
	public void setRecptrTelno(String recptrTelno) {
		this.recptrTelno = recptrTelno;
	}
	public String getOrderCanclResn() {
		return orderCanclResn;
	}
	public void setOrderCanclResn(String orderCanclResn) {
		this.orderCanclResn = orderCanclResn;
	}
	public List<OrderItemVO> getOrderItemList() {
		return orderItemList;
	}
	public void setOrderItemList(List<OrderItemVO> orderItemList) {
		this.orderItemList = orderItemList;
	}
	public List<OrderItemVO> getorderQItemList() {
		return orderQItemList;
	}
	public void setorderQItemList(List<OrderItemVO> orderQItemList) {
		this.orderQItemList = orderQItemList;
	}
	public GoodsVO getGoods() {
		return goods;
	}
	public void setGoods(GoodsVO goods) {
		this.goods = goods;
	}
	public String getOrderSetleSeCode() {
		return orderSetleSeCode;
	}
	public void setOrderSetleSeCode(String orderSetleSeCode) {
		this.orderSetleSeCode = orderSetleSeCode;
	}
	public String getRecptrTelno1() {
		return recptrTelno1;
	}
	public void setRecptrTelno1(String recptrTelno1) {
		this.recptrTelno1 = recptrTelno1;
	}
	public String getRecptrTelno2() {
		return recptrTelno2;
	}
	public void setRecptrTelno2(String recptrTelno2) {
		this.recptrTelno2 = recptrTelno2;
	}
	public String getRecptrTelno3() {
		return recptrTelno3;
	}
	public void setRecptrTelno3(String recptrTelno3) {
		this.recptrTelno3 = recptrTelno3;
	}
	public String getIndvdlinfoAgreAt() {
		return indvdlinfoAgreAt;
	}
	public void setIndvdlinfoAgreAt(String indvdlinfoAgreAt) {
		this.indvdlinfoAgreAt = indvdlinfoAgreAt;
	}
	public String getPurchsCndAgreAt() {
		return purchsCndAgreAt;
	}
	public void setPurchsCndAgreAt(String purchsCndAgreAt) {
		this.purchsCndAgreAt = purchsCndAgreAt;
	}
	public String getCardId() {
		return cardId;
	}
	public void setCardId(String cardId) {
		this.cardId = cardId;
	}
	public String getBillKey() {
		return billKey;
	}
	public void setBillKey(String billKey) {
		this.billKey = billKey;
	}
	public Integer getNowOdr() {
		return nowOdr;
	}
	public void setNowOdr(Integer nowOdr) {
		this.nowOdr = nowOdr;
	}
	public String getNextSetlede() {
		return nextSetlede;
	}
	public void setNextSetlede(String nextSetlede) {
		this.nextSetlede = nextSetlede;
	}
	public String getOrderReqSttusCode() {
		return orderReqSttusCode;
	}
	public void setOrderReqSttusCode(String orderReqSttusCode) {
		this.orderReqSttusCode = orderReqSttusCode;
	}
	public String getIniSetleConfmNo() {
		return iniSetleConfmNo;
	}
	public void setIniSetleConfmNo(String iniSetleConfmNo) {
		this.iniSetleConfmNo = iniSetleConfmNo;
	}
	public List<String> getOrderItemIdList() {
		return orderItemIdList;
	}
	public void setOrderItemIdList(List<String> orderItemIdList) {
		this.orderItemIdList = orderItemIdList;
	}
	public String getReqTyCode() {
		return reqTyCode;
	}
	public void setReqTyCode(String reqTyCode) {
		this.reqTyCode = reqTyCode;
	}
	public java.math.BigDecimal getFreeDlvyPc() {
		return freeDlvyPc;
	}
	public void setFreeDlvyPc(java.math.BigDecimal freeDlvyPc) {
		this.freeDlvyPc = freeDlvyPc;
	}
	public String getExprnUseAt() {
		return exprnUseAt;
	}
	public void setExprnUseAt(String exprnUseAt) {
		this.exprnUseAt = exprnUseAt;
	}
	public java.math.BigDecimal getExprnAmount() {
		return exprnAmount;
	}
	public void setExprnAmount(java.math.BigDecimal exprnAmount) {
		this.exprnAmount = exprnAmount;
	}
	public String getOrderSttusCodeNm() {
		return orderSttusCodeNm;
	}
	public void setOrderSttusCodeNm(String orderSttusCodeNm) {
		this.orderSttusCodeNm = orderSttusCodeNm;
	}
}
