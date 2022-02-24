package modoo.module.shop.goods.sch.service;

import modoo.module.common.service.CommonDefaultSearchVO;

@SuppressWarnings("serial")
public class GoodsSearchVO extends CommonDefaultSearchVO {
	
	/** 상품검색고유번호 */
	private java.math.BigDecimal goodsSearchNo;
	/** 사용자고유ID */
	private String esntlId;
	/** 세션ID */
	private String sessionId;
	/** 검색어 */
	private String srchwrd;
	/** 등록시점 */
	private java.util.Date registPnttm;
	
	/** 조회수 */
	private int hitCo;
	
	/*
	 * 검색 : 사용자고유ID
	 */
	private String searchEsntlId;

	public java.math.BigDecimal getGoodsSearchNo() {
		return goodsSearchNo;
	}
	public void setGoodsSearchNo(java.math.BigDecimal goodsSearchNo) {
		this.goodsSearchNo = goodsSearchNo;
	}
	public String getEsntlId() {
		return esntlId;
	}
	public void setEsntlId(String esntlId) {
		this.esntlId = esntlId;
	}
	public String getSessionId() {
		return sessionId;
	}
	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}
	public String getSrchwrd() {
		return srchwrd;
	}
	public void setSrchwrd(String srchwrd) {
		this.srchwrd = srchwrd;
	}
	public java.util.Date getRegistPnttm() {
		return registPnttm;
	}
	public void setRegistPnttm(java.util.Date registPnttm) {
		this.registPnttm = registPnttm;
	}
	public String getSearchEsntlId() {
		return searchEsntlId;
	}
	public void setSearchEsntlId(String searchEsntlId) {
		this.searchEsntlId = searchEsntlId;
	}
	public int getHitCo() {
		return hitCo;
	}
	public void setHitCo(int hitCo) {
		this.hitCo = hitCo;
	}

	
}
