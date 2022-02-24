package modoo.module.banner.service;

import org.hibernate.validator.constraints.NotEmpty;

import modoo.module.common.service.CommonDefaultSearchVO;

@SuppressWarnings("serial")
public class BannerVO extends CommonDefaultSearchVO {

	/** 배너고유ID */
	private String bannerId;
	/** 사이트고유ID */
	private String siteId;
	/** 사이트고유메뉴ID */
	private String menuId;
	/** 배너구분코드 */
	private String bannerSeCode;
	private String bannerSeCodeNm; // 배너구분코드 명
	/** 정렬번호 */
	private Integer sortNo;
	/** 배너명 */
	@NotEmpty
	private String bannerNm;
	/** 배너경로 */
	/*@NotEmpty*/
	private String bannerPath;
	/** 배너링크 */
	/*@NotEmpty*/
	private String bannerLink;
	/** 배너새창여부 */
	private String bannerWindowAt;
	/** 배너시작일 */
	private String bannerBgnde;
	@NotEmpty
	private String bannerBgnDate; // 배너 시작 날짜
	private String bannerBgnHour; // 배너 시작 시간
	private String bannerBgnMin; // 배너 시작 분
	/** 배너종료일 */
	private String bannerEndde;
	@NotEmpty
	private String bannerEndDate; // 배너 종료 날짜
	private String bannerEndHour; // 배너 종료 시간
	private String bannerEndMin; // 배너 종료 분
	/** 활성여부 */
	private String actvtyAt;
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
	/** 배너모바일이미지경로 */
	private String bannerMPath;
	/** 요일 */
	private String dfk;
	/** 상품id */
	private String goodsId;
	/** 상품명 */
	private String goodsNm;
	/** 이벤트문구 */
	private String evtTxt;
	/** 배경색(투데이픽스에서 사용) */
	private String bcrnClor;
	/**
	 * 검색 : 구분코드
	 */
	private String searchSeCode;
	//제휴사고유ID
	private String prtnrId;
	//제휴사명
	private String prtnrNm;
	//관리자여부
	private String mngAt;
	
	/** 배너타입코드 : GOODS 상품, EVENT 이벤트 */
	private String bannerTyCode;
	/** 배너라벨 */
	private String bannerLbl;
	/** 배너라벨색 */
	private String bannerLblClor;
	/** 글자색 */
	private String fontClor;
	
	/** 검색 : 제휴사ID */
	private String searchPrtnrId;
	
	public String getBannerId() {
		return bannerId;
	}
	public void setBannerId(String bannerId) {
		this.bannerId = bannerId;
	}
	public String getSiteId() {
		return siteId;
	}
	public void setSiteId(String siteId) {
		this.siteId = siteId;
	}
	public String getBannerSeCode() {
		return bannerSeCode;
	}
	public void setBannerSeCode(String bannerSeCode) {
		this.bannerSeCode = bannerSeCode;
	}
	public Integer getSortNo() {
		return sortNo;
	}
	public void setSortNo(Integer sortNo) {
		this.sortNo = sortNo;
	}
	public String getBannerNm() {
		return bannerNm;
	}
	public void setBannerNm(String bannerNm) {
		this.bannerNm = bannerNm;
	}
	public String getBannerPath() {
		return bannerPath;
	}
	public void setBannerPath(String bannerPath) {
		this.bannerPath = bannerPath;
	}
	public String getBannerLink() {
		return bannerLink;
	}
	public void setBannerLink(String bannerLink) {
		this.bannerLink = bannerLink;
	}
	public String getBannerWindowAt() {
		return bannerWindowAt;
	}
	public void setBannerWindowAt(String bannerWindowAt) {
		this.bannerWindowAt = bannerWindowAt;
	}
	public String getBannerBgnde() {
		return bannerBgnde;
	}
	public void setBannerBgnde(String bannerBgnde) {
		this.bannerBgnde = bannerBgnde;
	}
	public String getBannerEndde() {
		return bannerEndde;
	}
	public void setBannerEndde(String bannerEndde) {
		this.bannerEndde = bannerEndde;
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
	public String getBannerBgnDate() {
		return bannerBgnDate;
	}
	public void setBannerBgnDate(String bannerBgnDate) {
		this.bannerBgnDate = bannerBgnDate;
	}
	public String getBannerBgnHour() {
		return bannerBgnHour;
	}
	public void setBannerBgnHour(String bannerBgnHour) {
		this.bannerBgnHour = bannerBgnHour;
	}
	public String getBannerBgnMin() {
		return bannerBgnMin;
	}
	public void setBannerBgnMin(String bannerBgnMin) {
		this.bannerBgnMin = bannerBgnMin;
	}
	public String getBannerEndDate() {
		return bannerEndDate;
	}
	public void setBannerEndDate(String bannerEndDate) {
		this.bannerEndDate = bannerEndDate;
	}
	public String getBannerEndHour() {
		return bannerEndHour;
	}
	public void setBannerEndHour(String bannerEndHour) {
		this.bannerEndHour = bannerEndHour;
	}
	public String getBannerEndMin() {
		return bannerEndMin;
	}
	public void setBannerEndMin(String bannerEndMin) {
		this.bannerEndMin = bannerEndMin;
	}
	public String getSearchSeCode() {
		return searchSeCode;
	}
	public void setSearchSeCode(String searchSeCode) {
		this.searchSeCode = searchSeCode;
	}
	public String getBannerSeCodeNm() {
		return bannerSeCodeNm;
	}
	public void setBannerSeCodeNm(String bannerSeCodeNm) {
		this.bannerSeCodeNm = bannerSeCodeNm;
	}
	public String getBannerMPath() {
		return bannerMPath;
	}
	public void setBannerMPath(String bannerMPath) {
		this.bannerMPath = bannerMPath;
	}
	public String getDfk() {
		return dfk;
	}
	public void setDfk(String dfk) {
		this.dfk = dfk;
	}
	public String getGoodsId() {
		return goodsId;
	}
	public void setGoodsId(String goodsId) {
		this.goodsId = goodsId;
	}
	public String getEvtTxt() {
		return evtTxt;
	}
	public void setEvtTxt(String evtTxt) {
		this.evtTxt = evtTxt;
	}
	public String getMenuId() {
		return menuId;
	}
	public void setMenuId(String menuId) {
		this.menuId = menuId;
	}
	public String getGoodsNm() {
		return goodsNm;
	}
	public void setGoodsNm(String goodsNm) {
		this.goodsNm = goodsNm;
	}
	public String getBcrnClor() {
		return bcrnClor;
	}
	public void setBcrnClor(String bcrnClor) {
		this.bcrnClor = bcrnClor;
	}
	public String getPrtnrId() {
		return prtnrId;
	}
	public void setPrtnrId(String prtnrId) {
		this.prtnrId = prtnrId;
	}
	public String getPrtnrNm() {
		return prtnrNm;
	}
	public void setPrtnrNm(String prtnrNm) {
		this.prtnrNm = prtnrNm;
	}
	public String getMngAt() {
		return mngAt;
	}
	public void setMngAt(String mngAt) {
		this.mngAt = mngAt;
	}
	public String getBannerTyCode() {
		return bannerTyCode;
	}
	public void setBannerTyCode(String bannerTyCode) {
		this.bannerTyCode = bannerTyCode;
	}
	public String getBannerLbl() {
		return bannerLbl;
	}
	public void setBannerLbl(String bannerLbl) {
		this.bannerLbl = bannerLbl;
	}
	public String getBannerLblClor() {
		return bannerLblClor;
	}
	public void setBannerLblClor(String bannerLblClor) {
		this.bannerLblClor = bannerLblClor;
	}
	public String getFontClor() {
		return fontClor;
	}
	public void setFontClor(String fontClor) {
		this.fontClor = fontClor;
	}
	public String getSearchPrtnrId() {
		return searchPrtnrId;
	}
	public void setSearchPrtnrId(String searchPrtnrId) {
		this.searchPrtnrId = searchPrtnrId;
	}
	
}
