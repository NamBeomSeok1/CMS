package modoo.module.shop.goods.hist.service;

import modoo.module.common.service.CommonDefaultSearchVO;

@SuppressWarnings("serial")
public class OrderReqHistVO extends CommonDefaultSearchVO {

	/** 주문요청이력고유번호 */
	private java.math.BigDecimal orderReqHistNo;
	/** 주문배송고유번호 */
	private java.math.BigDecimal orderDlvyNo;
	/** 요청유형코드 */
	private String reqTyCode;
	/** 요청처리시점 */
	private java.util.Date reqPnttm;
	/** 사유내용 */
	private String reasonCn;
	/* 검색 : 주문취소, 교환, 환불 구분 */
	private String searchListType;
	/* 검색 : 업체ID */
	private String searchCmpnyId;
	
	public java.math.BigDecimal getOrderReqHistNo() {
		return orderReqHistNo;
	}
	public void setOrderReqHistNo(java.math.BigDecimal orderReqHistNo) {
		this.orderReqHistNo = orderReqHistNo;
	}
	public java.math.BigDecimal getOrderDlvyNo() {
		return orderDlvyNo;
	}
	public void setOrderDlvyNo(java.math.BigDecimal orderDlvyNo) {
		this.orderDlvyNo = orderDlvyNo;
	}
	public String getReqTyCode() {
		return reqTyCode;
	}
	public void setReqTyCode(String reqTyCode) {
		this.reqTyCode = reqTyCode;
	}
	public java.util.Date getReqPnttm() {
		return reqPnttm;
	}
	public void setReqPnttm(java.util.Date reqPnttm) {
		this.reqPnttm = reqPnttm;
	}
	public String getReasonCn() {
		return reasonCn;
	}
	public void setReasonCn(String reasonCn) {
		this.reasonCn = reasonCn;
	}
	public String getSearchListType() {
		return searchListType;
	}
	public void setSearchListType(String searchListType) {
		this.searchListType = searchListType;
	}
	public String getSearchCmpnyId() {
		return searchCmpnyId;
	}
	public void setSearchCmpnyId(String searchCmpnyId) {
		this.searchCmpnyId = searchCmpnyId;
	}

}
