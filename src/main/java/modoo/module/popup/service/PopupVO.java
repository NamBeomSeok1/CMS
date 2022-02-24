package modoo.module.popup.service;

import org.hibernate.validator.constraints.NotEmpty;

import modoo.module.common.service.CommonDefaultSearchVO;

@SuppressWarnings("serial")
public class PopupVO extends CommonDefaultSearchVO {
	
	/** 팝업고유ID */
	private String popupId;
	/** 사이트교유ID */
	private String siteId;
	/** 팝업제목 */
	@NotEmpty
	private String popupSj;
	/** 팝업내용 */
	private String popupCn;
	/** 팝업시작일 */
	private String popupBgnde;
	@NotEmpty
	private String popupBgnDate; //팝업 시작 날짜
	private String popupBgnHour; //팝업 시작시
	private String popupBgnMin; //팝업 시작분
	/** 팝업종료일 */
	private String popupEndde;
	@NotEmpty
	private String popupEndDate; //팝업 종료 날짜
	private String popupEndHour; //팝업 종료시
	private String popupEndMin; //팝업 종료분
	/** 팝업구분코드 */
	private String popupSeCode;
	/** 팝업TOP위치 */
	private Integer popupTop = 0;
	/** 팝업Left위치*/
	private Integer popupLeft = 0;
	/** 팝업너비 */
	private Integer popupWidth;
	/** 팝업높이 */
	private Integer popupHeight;
	/** 팝업이미지경로 */
	private String popupImgPath;
	/** 팝업이미지구분코드 */
	private String popupImgSeCode;
	/** 팝업링크 */
	private String popupLink;
	/** 활성여부 */
	private String actvtyAt = "N";
	/** 최초등록시점 */
	private java.util.Date frstRegistPnttm;
	/** 최초등록자ID */
	private String frstRegisterId;
	/** 최종수정시점 */
	private java.util.Date lastUpdtPnttm;
	/** 최종수정자ID */
	private String lastUpdusrId;
	/** 사용여부 */
	private String useAt = "Y";
	
	/** 금일검색여부 */
	private String searchTodayAt;
	
	public String getPopupId() {
		return popupId;
	}
	public void setPopupId(String popupId) {
		this.popupId = popupId;
	}
	public String getSiteId() {
		return siteId;
	}
	public void setSiteId(String siteId) {
		this.siteId = siteId;
	}
	public String getPopupSj() {
		return popupSj;
	}
	public void setPopupSj(String popupSj) {
		this.popupSj = popupSj;
	}
	public String getPopupCn() {
		return popupCn;
	}
	public void setPopupCn(String popupCn) {
		this.popupCn = popupCn;
	}
	public String getPopupBgnde() {
		return popupBgnde;
	}
	public void setPopupBgnde(String popupBgnde) {
		this.popupBgnde = popupBgnde;
	}
	public String getPopupBgnDate() {
		return popupBgnDate;
	}
	public void setPopupBgnDate(String popupBgnDate) {
		this.popupBgnDate = popupBgnDate;
	}
	public String getPopupBgnHour() {
		return popupBgnHour;
	}
	public void setPopupBgnHour(String popupBgnHour) {
		this.popupBgnHour = popupBgnHour;
	}
	public String getPopupBgnMin() {
		return popupBgnMin;
	}
	public void setPopupBgnMin(String popupBgnMin) {
		this.popupBgnMin = popupBgnMin;
	}
	public String getPopupEndde() {
		return popupEndde;
	}
	public void setPopupEndde(String popupEndde) {
		this.popupEndde = popupEndde;
	}
	public String getPopupEndDate() {
		return popupEndDate;
	}
	public void setPopupEndDate(String popupEndDate) {
		this.popupEndDate = popupEndDate;
	}
	public String getPopupEndHour() {
		return popupEndHour;
	}
	public void setPopupEndHour(String popupEndHour) {
		this.popupEndHour = popupEndHour;
	}
	public String getPopupEndMin() {
		return popupEndMin;
	}
	public void setPopupEndMin(String popupEndMin) {
		this.popupEndMin = popupEndMin;
	}
	public String getPopupSeCode() {
		return popupSeCode;
	}
	public void setPopupSeCode(String popupSeCode) {
		this.popupSeCode = popupSeCode;
	}
	public Integer getPopupTop() {
		return popupTop;
	}
	public void setPopupTop(Integer popupTop) {
		this.popupTop = popupTop;
	}
	public Integer getPopupLeft() {
		return popupLeft;
	}
	public void setPopupLeft(Integer popupLeft) {
		this.popupLeft = popupLeft;
	}
	public Integer getPopupWidth() {
		return popupWidth;
	}
	public void setPopupWidth(Integer popupWidth) {
		this.popupWidth = popupWidth;
	}
	public Integer getPopupHeight() {
		return popupHeight;
	}
	public void setPopupHeight(Integer popupHeight) {
		this.popupHeight = popupHeight;
	}
	public String getPopupImgPath() {
		return popupImgPath;
	}
	public void setPopupImgPath(String popupImgPath) {
		this.popupImgPath = popupImgPath;
	}
	public String getPopupImgSeCode() {
		return popupImgSeCode;
	}
	public void setPopupImgSeCode(String popupImgSeCode) {
		this.popupImgSeCode = popupImgSeCode;
	}
	public String getPopupLink() {
		return popupLink;
	}
	public void setPopupLink(String popupLink) {
		this.popupLink = popupLink;
	}
	public String getActvtyAt() {
		return actvtyAt;
	}
	public void setActvtyAt(String actvtyAt) {
		this.actvtyAt = actvtyAt;
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
	public String getSearchTodayAt() {
		return searchTodayAt;
	}
	public void setSearchTodayAt(String searchTodayAt) {
		this.searchTodayAt = searchTodayAt;
	}
	
}
