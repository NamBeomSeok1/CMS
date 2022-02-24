package modoo.module.event.service.impl;

import java.util.Date;
import java.util.List;

import modoo.module.common.service.CommonDefaultSearchVO;

@SuppressWarnings("serial")
public class GoodsEventVO extends CommonDefaultSearchVO {
	
	/** 이벤트 번호 */
	private java.math.BigDecimal eventNo;
	/** 업체ID */
	private String cmpnyId;
	/** 이벤트 제목 */
	private String eventSj;
	/** 이벤트 시작일시 */
	private Date eventBeginDt;
	/** 이벤트 종료일시 */
	private Date eventEndDt;
	/** 이벤트 내용(설명) */
	private String eventCn;
	/** 이벤트 제휴사 */
	private String prtnrId;
	/** 이벤트 썸네일 */
	private String eventThumbnail;
	/** 이벤트 배너 이미지 */
	private String eventBannerImg;
	/** 이벤트 메인 이미지 (PC) */
	private String eventMainImgPc;
	/** 이벤트 메인 이미지 (MOBILE) */
	private String eventMainImgMob;
	/** 이벤트 상세 이미지 */
	private String eventDetailImg;
	/** 이벤트 url */
	private String eventUrl;
	/** 이벤트 수량 */
	private Integer eventCnt;
	/** 이벤트 마감여부 */
	private String endAt;
	/** 최초등록일시 */
	private Date frstRegistPnttm;
	/** 최초등록자 ID */
	private String frstRegisterId;
	/** 최종등록일시 */
	private Date lastUpdtPnttm;
	/** 최종수정자 ID */
	private String lastUpdusrId;
	/** 사용여부 */
	private String useAt;
	/** 이벤트 브랜드 배너 이미지 (pc) */
	private String eventBrandImgPc;
	/** 이벤트 브랜드 배너 이미지 (모바일) */
	private String eventBrandImgMobile;
	/** 제휴사명 */
	private String prtnrNm;
	/** 업체명 */
	private String cmpnyNm;
	/** 이벤트 이미지 유형(삭제처리용) */
	private String imageType;
	/** 이벤트유형구분 */
	private String eventTyCode;

	/** 이벤트 상품 목록 */
	private List<GoodsEventMapngVO> mapngList;
	

	/* 검색 : 이벤트 제휴사 구분 */
	private String searchPrtnrId;
	
	/* 검색 : 이벤트 마감여부 */
	private String searchEndAt;
	
	/* 검색 : 검색조건 */
	private String searchCondition;
	
	/* 검색 : 검색키워드 */
	private String searchKeyword;
	
	/* 검색 : 정렬순서 */
	private String searchOrder;

	public java.math.BigDecimal getEventNo() {
		return eventNo;
	}
	public void setEventNo(java.math.BigDecimal eventNo) {
		this.eventNo = eventNo;
	}
	public String getCmpnyId() {
		return cmpnyId;
	}
	public void setCmpnyId(String cmpnyId) {
		this.cmpnyId = cmpnyId;
	}
	public String getEventSj() {
		return eventSj;
	}
	public void setEventSj(String eventSj) {
		this.eventSj = eventSj;
	}
	public Date getEventBeginDt() {
		return eventBeginDt;
	}
	public void setEventBeginDt(Date eventBeginDt) {
		this.eventBeginDt = eventBeginDt;
	}
	public Date getEventEndDt() {
		return eventEndDt;
	}
	public void setEventEndDt(Date eventEndDt) {
		this.eventEndDt = eventEndDt;
	}
	public String getEventCn() {
		return eventCn;
	}
	public void setEventCn(String eventCn) {
		this.eventCn = eventCn;
	}
	public String getPrtnrId() {
		return prtnrId;
	}
	public void setPrtnrId(String prtnrId) {
		this.prtnrId = prtnrId;
	}
	public String getEventThumbnail() {
		return eventThumbnail;
	}
	public void setEventThumbnail(String eventThumbnail) {
		this.eventThumbnail = eventThumbnail;
	}
	public String getEventBannerImg() {
		return eventBannerImg;
	}
	public void setEventBannerImg(String eventBannerImg) {
		this.eventBannerImg = eventBannerImg;
	}
	public String getEventMainImgPc() {
		return eventMainImgPc;
	}
	public void setEventMainImgPc(String eventMainImgPc) {
		this.eventMainImgPc = eventMainImgPc;
	}
	public String getEventMainImgMob() {
		return eventMainImgMob;
	}
	public void setEventMainImgMob(String eventMainImgMob) {
		this.eventMainImgMob = eventMainImgMob;
	}
	public String getEventDetailImg() {
		return eventDetailImg;
	}
	public void setEventDetailImg(String eventDetailImg) {
		this.eventDetailImg = eventDetailImg;
	}
	public String getEventUrl() {
		return eventUrl;
	}
	public void setEventUrl(String eventUrl) {
		this.eventUrl = eventUrl;
	}
	public Integer getEventCnt() {
		return eventCnt;
	}
	public void setEventCnt(Integer eventCnt) {
		this.eventCnt = eventCnt;
	}
	public String getEndAt() {
		return endAt;
	}
	public void setEndAt(String endAt) {
		this.endAt = endAt;
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
	public String getUseAt() {
		return useAt;
	}
	public void setUseAt(String useAt) {
		this.useAt = useAt;
	}
	public List<GoodsEventMapngVO> getMapngList() {
		return mapngList;
	}
	public void setMapngList(List<GoodsEventMapngVO> mapngList) {
		this.mapngList = mapngList;
	}
	public String getEventBrandImgPc() {
		return eventBrandImgPc;
	}
	public void setEventBrandImgPc(String eventBrandImgPc) {
		this.eventBrandImgPc = eventBrandImgPc;
	}
	public String getEventBrandImgMobile() {
		return eventBrandImgMobile;
	}
	public void setEventBrandImgMobile(String eventBrandImgMobile) {
		this.eventBrandImgMobile = eventBrandImgMobile;
	}
	public String getPrtnrNm() {
		return prtnrNm;
	}
	public void setPrtnrNm(String prtnrNm) {
		this.prtnrNm = prtnrNm;
	}
	public String getCmpnyNm() {
		return cmpnyNm;
	}
	public void setCmpnyNm(String cmpnyNm) {
		this.cmpnyNm = cmpnyNm;
	}
	public String getImageType() {
		return imageType;
	}
	public void setImageType(String imageType) {
		this.imageType = imageType;
	}
	public String getSearchPrtnrId() {
		return searchPrtnrId;
	}
	public void setSearchPrtnrId(String searchPrtnrId) {
		this.searchPrtnrId = searchPrtnrId;
	}
	public String getSearchEndAt() {
		return searchEndAt;
	}
	public void setSearchEndAt(String searchEndAt) {
		this.searchEndAt = searchEndAt;
	}
	public String getSearchCondition() {
		return searchCondition;
	}
	public void setSearchCondition(String searchCondition) {
		this.searchCondition = searchCondition;
	}
	public String getSearchKeyword() {
		return searchKeyword;
	}
	public void setSearchKeyword(String searchKeyword) {
		this.searchKeyword = searchKeyword;
	}
	public String getEventTyCode() {
		return eventTyCode;
	}
	public void setEventTyCode(String eventTyCode) {
		this.eventTyCode = eventTyCode;
	}
	public String getSearchOrder() {
		return searchOrder;
	}
	public void setSearchOrder(String searchOrder) {
		this.searchOrder = searchOrder;
	}
	
}
