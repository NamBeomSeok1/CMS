package modoo.module.thema.service.impl;

import java.util.Date;
import java.util.List;

import modoo.module.common.service.CommonDefaultSearchVO;
import modoo.module.shop.goods.info.service.GoodsVO;

@SuppressWarnings("serial")
public class GoodsThemaVO extends CommonDefaultSearchVO{
	
	/**테마 번호*/
	private java.math.BigDecimal themaNo;
	/**테마 명*/
	private String themaSj;
	/**테마시작일*/
	private Date themaBeginDt;
	/**테마마감일*/
	private Date themaEndDt;
	/**테마내용*/
	private String themaCn;
	/**제휴사ID*/
	private String prtnrId;
	/**테마 클릭URL*/
	private String themaUrl;
	/**테마 썸네일*/
	private String themaThumbnail;
	/**테마메인이미지(PC)*/
	private String themaMainImgPc;
	/**테마메인이미지(모바일)*/
	private String themaMainImgMob;
	/**테마상세이미지*/
	private String themaDetailImg;
	/**마감유무*/
	private String endAt;
	/**첫등록일*/
	private Date frstRegistPnttm;
	/**첫등록자ID*/
	private String frstRegisterId;
	/**최종수정일*/
	private Date lastUpdtPnttm;
	/**최종수정자고유ID*/
	private String lastUpdusrId;
	/**테마 타입코드*/
	private String themaTyCode;
	/**테마 순서*/
	private Integer themaSn;
	
	/*테마상품들*/
	private List<GoodsVO> goodsList;
	
	/*이미지 타입*/
	private String searchImageType; 
	/* 검색 : 테마 제휴사 구분 */
	private String searchPrtnrId;
	
	/* 검색 : 테마 마감여부 */
	private String searchEndAt;
	
	/* 검색 : 검색조건 */
	private String searchCondition;
	
	/* 검색 : 검색키워드 */
	private String searchKeyword;
	
	/* 검색 : 정렬순서 */
	private String searchOrder;
	
	
	

	public String getSearchImageType() {
		return searchImageType;
	}
	public void setSearchImageType(String searchImageType) {
		this.searchImageType = searchImageType;
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
	public String getSearchOrder() {
		return searchOrder;
	}
	public void setSearchOrder(String searchOrder) {
		this.searchOrder = searchOrder;
	}
	public List<GoodsVO> getGoodsList() {
		return goodsList;
	}
	public void setGoodsList(List<GoodsVO> goodsList) {
		this.goodsList = goodsList;
	}
	public java.math.BigDecimal getThemaNo() {
		return themaNo;
	}
	public void setThemaNo(java.math.BigDecimal themaNo) {
		this.themaNo = themaNo;
	}
	public String getThemaSj() {
		return themaSj;
	}
	public void setThemaSj(String themaSj) {
		this.themaSj = themaSj;
	}
	public Date getThemaBeginDt() {
		return themaBeginDt;
	}
	public void setThemaBeginDt(Date themaBeginDt) {
		this.themaBeginDt = themaBeginDt;
	}
	public Date getThemaEndDt() {
		return themaEndDt;
	}
	public void setThemaEndDt(Date themaEndDt) {
		this.themaEndDt = themaEndDt;
	}
	public String getThemaCn() {
		return themaCn;
	}
	public void setThemaCn(String themaCn) {
		this.themaCn = themaCn;
	}
	public String getPrtnrId() {
		return prtnrId;
	}
	public void setPrtnrId(String prtnrId) {
		this.prtnrId = prtnrId;
	}
	public String getThemaUrl() {
		return themaUrl;
	}
	public void setThemaUrl(String themaUrl) {
		this.themaUrl = themaUrl;
	}
	public String getThemaThumbnail() {
		return themaThumbnail;
	}
	public void setThemaThumbnail(String themaThumbnail) {
		this.themaThumbnail = themaThumbnail;
	}
	public String getThemaMainImgPc() {
		return themaMainImgPc;
	}
	public void setThemaMainImgPc(String themaMainImgPc) {
		this.themaMainImgPc = themaMainImgPc;
	}
	public String getThemaMainImgMob() {
		return themaMainImgMob;
	}
	public void setThemaMainImgMob(String themaMainImgMob) {
		this.themaMainImgMob = themaMainImgMob;
	}
	public String getThemaDetailImg() {
		return themaDetailImg;
	}
	public void setThemaDetailImg(String themaDetailImg) {
		this.themaDetailImg = themaDetailImg;
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
	public String getThemaTyCode() {
		return themaTyCode;
	}
	public void setThemaTyCode(String themaTyCode) {
		this.themaTyCode = themaTyCode;
	}
	public Integer getThemaSn() {
		return themaSn;
	}
	public void setThemaSn(Integer themaSn) {
		this.themaSn = themaSn;
	}
	
	

}
