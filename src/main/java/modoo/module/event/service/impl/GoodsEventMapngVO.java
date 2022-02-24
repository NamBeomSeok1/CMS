package modoo.module.event.service.impl;

public class GoodsEventMapngVO {
	
	/** 이벤트 번호 */
	private java.math.BigDecimal eventNo;
	/** 상품 번호 */
	private String goodsId;
	/** 이벤트 적용 범위 구분 코드(CP당, 전체 구매건..) */
	private String eventRangeSeCode;
	/** 이벤트 유형 구분 코드 */
	private String eventTyCode;
	
	public String getGoodsId() {
		return goodsId;
	}
	public void setGoodsId(String goodsId) {
		this.goodsId = goodsId;
	}
	public java.math.BigDecimal getEventNo() {
		return eventNo;
	}
	public void setEventNo(java.math.BigDecimal eventNo) {
		this.eventNo = eventNo;
	}
	public String getEventRangeSeCode() {
		return eventRangeSeCode;
	}
	public void setEventRangeSeCode(String eventRangeSeCode) {
		this.eventRangeSeCode = eventRangeSeCode;
	}
	public String getEventTyCode() {
		return eventTyCode;
	}
	public void setEventTyCode(String eventTyCode) {
		this.eventTyCode = eventTyCode;
	}
	
}
