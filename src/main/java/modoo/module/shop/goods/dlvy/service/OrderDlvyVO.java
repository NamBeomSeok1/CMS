package modoo.module.shop.goods.dlvy.service;

import modoo.module.common.service.CommonDefaultSearchVO;

@SuppressWarnings("serial")
public class OrderDlvyVO extends CommonDefaultSearchVO {
	
	/** 주문배송고유번호 */
	private java.math.BigDecimal orderDlvyNo;
	/** 주문고유번호 */
	//private java.math.BigDecimal orderNo;
	private String orderNo;
	/** 주문결제고유번호 */
	private java.math.BigDecimal orderSetleNo;
	/** 주문종류코드 */
	private String orderKndCode;
	/** 주문주기 구분코드 */
	private String sbscrptCycleSeCode;
	/** 구독 주 주기 */
	private Integer sbscrptWeekCycle;
	/** 구독배송요일 */
	private Integer sbscrptDlvyWd;
	/** 구독이용 월 주기 */
	private Integer sbscrptMtCycle;
	/** 구독배송일 */
	private Integer sbscrptDlvyDay;
	/** 차수 */
	private Integer orderOdr;
	/** 주문수량 */
	private Integer orderCo;
	/** 판매금액 */
	private java.math.BigDecimal sleAmount;
	/** 할인금액 */
	private java.math.BigDecimal dscntAmount;
	/** 결제금액(판매금액-할인금액) */
	private java.math.BigDecimal setleAmount;
	/** 배송비금액 */
	private java.math.BigDecimal dlvyAmount;
	/** 수령인명 */
	private String dlvyUserNm;
	/** 수령인연락처 */
	private String telno;
	/** 배송우편번호 */
	private String dlvyZip;
	/** 배송지주소 */
	private String dlvyAdres;
	/** 배송지상세주소 */
	private String dlvyAdresDetail;
	/** 배송메시지 */
	private String dlvyMssage;
	/** 배송상태코드 */
	private String dlvySttusCode;
	/** 택배사고유ID */
	private String hdryId;
	/** 택배배송일 */
	private String hdryDlvyDe;
	/** 송장번호 */
	private String invcNo;
	/** 주문 옵션 정보 */
	private String orderInfo;
	/** 주문요청상태코드 */
	private String orderReqSttusCode;
	/** 요청유형코드 */
	private String reqTyCode;
	/** 등록시점 */
	private java.util.Date registPnttm;
	/** 최종수정시점 */
	private java.util.Date lastUpdtPnttm;
	/** 최종수정자ID */
	private String lastUpdusrId;
	/** 취소시점 */
	private java.util.Date cancelPnttm;
	/** 취소금액 */
	private java.math.BigDecimal cancelAmount;
	/** 취소자 */
	private String cancelUsrId;
	
	/*
	 * 검색 : 업체고유ID
	 */
	private String searchCmpnyId;
	
	/*
	 * 검색 : 카테고리ID
	 */
	private String searchGoodsCtgryId;
	
	/*
	 * 검색 : 주문요청상태코드
	 */
	private String searchOrderReqSttusCode;
	
	/*
	 * 검색 : 주문요청유형코드
	 */
	private String searchReqTyCode;
	
	/*
	 * 검색 : 주문번호
	 */
	private java.math.BigDecimal searchOrderNo;

	/*
	 * 검색 : 결제상태코드
	 */
	private String searchSetleSttusCode;
	
	/*
	 * 검색 : 배송상태코드
	 */
	private String searchDlvySttusCode;
	
	/*
	 * 검색 : 상품명
	 */
	private String searchGoodsNm;
	
	/*
	 * 검색 : 상품구분
	 */
	private String searchOrderKndCode;
	
	/*
	 * 검색 : 검색목록유형 : 취소, 교환, 반품
	 */
	private String searchListType;
	/* 검색 : 주문자id */
	private String searchOrdrrId;
	/* 검색 : 상품번호 */
	private String searchGoodsId;
	/* 검색 : 주문그룹번호 */
	private java.math.BigDecimal searchOrderGroupNo;
	/* 검색 : 구독상태코드 */
	private String searchOrderSttusCode;
	/* 검색 : 주문자명 */
	private String searchOrdrrNm;
	/* 검색 : 업체명 */
	private String searchCmpnyNm;
	
	public java.math.BigDecimal getOrderDlvyNo() {
		return orderDlvyNo;
	}

	public void setOrderDlvyNo(java.math.BigDecimal orderDlvyNo) {
		this.orderDlvyNo = orderDlvyNo;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public java.math.BigDecimal getOrderSetleNo() {
		return orderSetleNo;
	}

	public void setOrderSetleNo(java.math.BigDecimal orderSetleNo) {
		this.orderSetleNo = orderSetleNo;
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

	public Integer getSbscrptMtCycle() {
		return sbscrptMtCycle;
	}

	public void setSbscrptMtCycle(Integer sbscrptMtCycle) {
		this.sbscrptMtCycle = sbscrptMtCycle;
	}

	public Integer getSbscrptDlvyDay() {
		return sbscrptDlvyDay;
	}

	public void setSbscrptDlvyDay(Integer sbscrptDlvyDay) {
		this.sbscrptDlvyDay = sbscrptDlvyDay;
	}

	public Integer getOrderOdr() {
		return orderOdr;
	}

	public void setOrderOdr(Integer orderOdr) {
		this.orderOdr = orderOdr;
	}

	public Integer getOrderCo() {
		return orderCo;
	}

	public void setOrderCo(Integer orderCo) {
		this.orderCo = orderCo;
	}

	public java.math.BigDecimal getSleAmount() {
		return sleAmount;
	}

	public void setSleAmount(java.math.BigDecimal sleAmount) {
		this.sleAmount = sleAmount;
	}
	public java.math.BigDecimal getDscntAmount() {
		return dscntAmount;
	}

	public void setDscntAmount(java.math.BigDecimal dscntAmount) {
		this.dscntAmount = dscntAmount;
	}

	public java.math.BigDecimal getSetleAmount() {
		return setleAmount;
	}

	public void setSetleAmount(java.math.BigDecimal setleAmount) {
		this.setleAmount = setleAmount;
	}

	public java.math.BigDecimal getDlvyAmount() {
		return dlvyAmount;
	}

	public void setDlvyAmount(java.math.BigDecimal dlvyAmount) {
		this.dlvyAmount = dlvyAmount;
	}

	public String getDlvyUserNm() {
		return dlvyUserNm;
	}

	public void setDlvyUserNm(String dlvyUserNm) {
		this.dlvyUserNm = dlvyUserNm;
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

	public String getDlvySttusCode() {
		return dlvySttusCode;
	}

	public void setDlvySttusCode(String dlvySttusCode) {
		this.dlvySttusCode = dlvySttusCode;
	}

	public String getHdryId() {
		return hdryId;
	}

	public void setHdryId(String hdryId) {
		this.hdryId = hdryId;
	}

	public String getHdryDlvyDe() {
		return hdryDlvyDe;
	}

	public void setHdryDlvyDe(String hdryDlvyDe) {
		this.hdryDlvyDe = hdryDlvyDe;
	}

	public String getInvcNo() {
		return invcNo;
	}

	public void setInvcNo(String invcNo) {
		this.invcNo = invcNo;
	}
	public String getOrderInfo() {
		return orderInfo;
	}

	public void setOrderInfo(String orderInfo) {
		this.orderInfo = orderInfo;
	}
	public String getOrderReqSttusCode() {
		return orderReqSttusCode;
	}

	public void setOrderReqSttusCode(String orderReqSttusCode) {
		this.orderReqSttusCode = orderReqSttusCode;
	}

	public String getReqTyCode() {
		return reqTyCode;
	}

	public void setReqTyCode(String reqTyCode) {
		this.reqTyCode = reqTyCode;
	}

	public java.util.Date getRegistPnttm() {
		return registPnttm;
	}

	public void setRegistPnttm(java.util.Date registPnttm) {
		this.registPnttm = registPnttm;
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

	public String getSearchCmpnyId() {
		return searchCmpnyId;
	}

	public void setSearchCmpnyId(String searchCmpnyId) {
		this.searchCmpnyId = searchCmpnyId;
	}

	public String getSearchGoodsCtgryId() {
		return searchGoodsCtgryId;
	}

	public void setSearchGoodsCtgryId(String searchGoodsCtgryId) {
		this.searchGoodsCtgryId = searchGoodsCtgryId;
	}

	public String getSearchOrderReqSttusCode() {
		return searchOrderReqSttusCode;
	}

	public void setSearchOrderReqSttusCode(String searchOrderReqSttusCode) {
		this.searchOrderReqSttusCode = searchOrderReqSttusCode;
	}

	public String getSearchSetleSttusCode() {
		return searchSetleSttusCode;
	}

	public void setSearchSetleSttusCode(String searchSetleSttusCode) {
		this.searchSetleSttusCode = searchSetleSttusCode;
	}

	public java.math.BigDecimal getSearchOrderNo() {
		return searchOrderNo;
	}

	public void setSearchOrderNo(java.math.BigDecimal searchOrderNo) {
		this.searchOrderNo = searchOrderNo;
	}

	public String getSearchGoodsNm() {
		return searchGoodsNm;
	}

	public void setSearchGoodsNm(String searchGoodsNm) {
		this.searchGoodsNm = searchGoodsNm;
	}

	public String getSearchDlvySttusCode() {
		return searchDlvySttusCode;
	}

	public void setSearchDlvySttusCode(String searchDlvySttusCode) {
		this.searchDlvySttusCode = searchDlvySttusCode;
	}

	public String getSearchReqTyCode() {
		return searchReqTyCode;
	}

	public void setSearchReqTyCode(String searchReqTyCode) {
		this.searchReqTyCode = searchReqTyCode;
	}

	public String getSearchListType() {
		return searchListType;
	}

	public void setSearchListType(String searchListType) {
		this.searchListType = searchListType;
	}

	public String getSearchOrdrrId() {
		return searchOrdrrId;
	}

	public void setSearchOrdrrId(String searchOrdrrId) {
		this.searchOrdrrId = searchOrdrrId;
	}

	public String getSearchGoodsId() {
		return searchGoodsId;
	}

	public void setSearchGoodsId(String searchGoodsId) {
		this.searchGoodsId = searchGoodsId;
	}

	public java.math.BigDecimal getSearchOrderGroupNo() {
		return searchOrderGroupNo;
	}

	public void setSearchOrderGroupNo(java.math.BigDecimal searchOrderGroupNo) {
		this.searchOrderGroupNo = searchOrderGroupNo;
	}

	public String getSearchOrderSttusCode() {
		return searchOrderSttusCode;
	}

	public void setSearchOrderSttusCode(String searchOrderSttusCode) {
		this.searchOrderSttusCode = searchOrderSttusCode;
	}

	public String getSearchOrdrrNm() {
		return searchOrdrrNm;
	}

	public void setSearchOrdrrNm(String searchOrdrrNm) {
		this.searchOrdrrNm = searchOrdrrNm;
	}

	public String getSearchCmpnyNm() {
		return searchCmpnyNm;
	}

	public void setSearchCmpnyNm(String searchCmpnyNm) {
		this.searchCmpnyNm = searchCmpnyNm;
	}

	public String getSearchOrderKndCode() {
		return searchOrderKndCode;
	}

	public void setSearchOrderKndCode(String searchOrderKndCode) {
		this.searchOrderKndCode = searchOrderKndCode;
	}

	public java.util.Date getCancelPnttm() {
		return cancelPnttm;
	}

	public void setCancelPnttm(java.util.Date cancelPnttm) {
		this.cancelPnttm = cancelPnttm;
	}

	public java.math.BigDecimal getCancelAmount() {
		return cancelAmount;
	}

	public void setCancelAmount(java.math.BigDecimal cancelAmount) {
		this.cancelAmount = cancelAmount;
	}

	public String getCancelUsrId() {
		return cancelUsrId;
	}

	public void setCancelUsrId(String cancelUsrId) {
		this.cancelUsrId = cancelUsrId;
	}

}
