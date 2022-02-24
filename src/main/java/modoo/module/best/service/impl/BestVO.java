package modoo.module.best.service.impl;

import java.util.Date;

import modoo.module.common.service.CommonDefaultSearchVO;

@SuppressWarnings("serial")
public class BestVO extends CommonDefaultSearchVO{
	
	/** 베스트 번호 */
	private java.math.BigDecimal bestNo;
	/** 대표유형 */
	private String bestTyCode;
	/** 대표url */
	private String bestUrl;
	/** 노출 순서 */
	private java.math.BigDecimal expsrOrdr;
	/** 상품 코드 */
	private String goodsId;
	/** 제휴사코드 */
	private String prtnrId;
	/** 노출 상태 */
	private String actvtyAt;
	/** 대표 제목 */
	private String reprsntSj;
	/** 대표 문구 */
	private String reprsntText;
	/** 노출기간 */
	private java.util.Date expsrBeginDe;
	private java.util.Date expsrEndDe;
	/** 베스트 썸네일 */
	private String bestThumbnail;
	/** 베스트 PC이미지 */
	private String bestImgPc;
	/** 베스트 모바일이미지 */
	private String bestImgMob;
	/** 최초등록시점 **/
	private Date frstRegistPnttm;
	/** 최초등록ID **/
	private String frstRegisterId;
	/** 최종수정시점 **/
	private Date lastUpdtPnttm;
	/** 최종수정ID **/
	private String lastUpdusrId;

	/* 검색: 제휴사코드 */
	private String searchPrtnrId;
	/* 이미지 처리용 */
	private String imageType;

	public java.math.BigDecimal getExpsrOrdr() {
		return expsrOrdr;
	}
	public void setExpsrOrdr(java.math.BigDecimal expsrOrdr) {
		this.expsrOrdr = expsrOrdr;
	}
	public String getGoodsId() {
		return goodsId;
	}
	public void setGoodsId(String goodsId) {
		this.goodsId = goodsId;
	}
	public String getPrtnrId() {
		return prtnrId;
	}
	public void setPrtnrId(String prtnrId) {
		this.prtnrId = prtnrId;
	}
	public String getActvtyAt() {
		return actvtyAt;
	}
	public void setActvtyAt(String actvtyAt) {
		this.actvtyAt = actvtyAt;
	}
	public String getReprsntSj() {
		return reprsntSj;
	}
	public void setReprsntSj(String reprsntSj) {
		this.reprsntSj = reprsntSj;
	}
	public String getReprsntText() {
		return reprsntText;
	}
	public void setReprsntText(String reprsntText) {
		this.reprsntText = reprsntText;
	}
	public java.util.Date getExpsrBeginDe() {
		return expsrBeginDe;
	}
	public void setExpsrBeginDe(java.util.Date expsrBeginDe) {
		this.expsrBeginDe = expsrBeginDe;
	}
	public java.util.Date getExpsrEndDe() {
		return expsrEndDe;
	}
	public void setExpsrEndDe(java.util.Date expsrEndDe) {
		this.expsrEndDe = expsrEndDe;
	}
	public java.math.BigDecimal getBestNo() {
		return bestNo;
	}
	public void setBestNo(java.math.BigDecimal bestNo) {
		this.bestNo = bestNo;
	}
	public String getBestTyCode() {
		return bestTyCode;
	}
	public void setBestTyCode(String bestTyCode) {
		this.bestTyCode = bestTyCode;
	}
	public String getBestUrl() {
		return bestUrl;
	}
	public void setBestUrl(String bestUrl) {
		this.bestUrl = bestUrl;
	}
	public String getBestThumbnail() {
		return bestThumbnail;
	}
	public void setBestThumbnail(String bestThumbnail) {
		this.bestThumbnail = bestThumbnail;
	}
	public String getBestImgPc() {
		return bestImgPc;
	}
	public void setBestImgPc(String bestImgPc) {
		this.bestImgPc = bestImgPc;
	}
	public String getBestImgMob() {
		return bestImgMob;
	}
	public void setBestImgMob(String bestImgMob) {
		this.bestImgMob = bestImgMob;
	}
	public String getSearchPrtnrId() {
		return searchPrtnrId;
	}
	public void setSearchPrtnrId(String searchPrtnrId) {
		this.searchPrtnrId = searchPrtnrId;
	}
	public Date getFrstRegistPnttm() {
		return frstRegistPnttm;
	}
	public void setFrstRegistPnttm(Date frstRegistPnttm) {
		this.frstRegistPnttm = frstRegistPnttm;
	}
	public String getFrstRegisterId() {
		return frstRegisterId;
	}
	public void setFrstRegisterId(String frstRegisterId) {
		this.frstRegisterId = frstRegisterId;
	}
	public String getImageType() {
		return imageType;
	}
	public void setImageType(String imageType) {
		this.imageType = imageType;
	}
	public Date getLastUpdtPnttm() {
		return lastUpdtPnttm;
	}
	public void setLastUpdtPnttm(Date lastUpdtPnttm) {
		this.lastUpdtPnttm = lastUpdtPnttm;
	}
	public String getLastUpdusrId() {
		return lastUpdusrId;
	}
	public void setLastUpdusrId(String lastUpdusrId) {
		this.lastUpdusrId = lastUpdusrId;
	}

}
