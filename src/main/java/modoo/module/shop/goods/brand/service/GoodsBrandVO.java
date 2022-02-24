package modoo.module.shop.goods.brand.service;

import java.util.List;

import org.hibernate.validator.constraints.NotEmpty;

import modoo.module.common.service.CommonDefaultSearchVO;
import modoo.module.shop.goods.info.service.GoodsVO;

@SuppressWarnings("serial")
public class GoodsBrandVO extends CommonDefaultSearchVO {

	/** 브랜드고유ID */
	private String brandId;
	/** 업체고유ID */
	private String cmpnyId;
	/** 브랜드대표B2C상품ID */
	private String brandBtcGoodsId;
	/** 브랜드대표B2C상품ID */
	private String brandBtbGoodsId;
	/** 브랜드대표상품 */
	private GoodsVO goods;
	/** 업체고유이름 */
	private String cmpnyNm;
	/** 브랜드명 */
	@NotEmpty
	private String brandNm;
	/** 브랜드노출구분코드 : ALL 모두, B2B, B2C, NONE 미노출 */
	private String brandExpsrSeCode;
	/** 브랜드이미지경로 */
	private String brandImagePath;
	/** 브랜드썸네일경로 */
	private String brandImageThumbPath;
	/** 브랜드취급상품 */
	private String brandIntGoods;
	
	/** 브랜드 대표이미지 (PC) */
	private String brandRepImg;
	/** 브랜드 대표이미지 (MOBILE) */
	private String brandRepImgMob;
	/** 브랜드 소개 이미지 */
	private String brandIntImg;
	
	/** 초성 : ㄱ,ㄴ,ㄷ...ㅎ */
	private String wrd;
	
	/** 제휴사 */
	private String prtnrId;
	
	/** 데스크톱브랜드 이미지 목록 */
	//List<GoodsBrandImageVO> dekBrandImageList;
	
	/** 모바일브랜드 이미지 목록 */
	//List<GoodsBrandImageVO> mobBrandImageList;

	/** 브랜드 컴퓨터 대표 이미지 목록 */
	List<GoodsBrandImageVO> repBrandImageList;
	
	/** 브랜드 모바일 대표 이미지 목록 */
	List<GoodsBrandImageVO> repMobBrandImageList;
	
	
	/** 브랜드 소개 이미지 목록 */
	List<GoodsBrandImageVO> intBrandImageList;
	
	/** 이벤트 이미지 목록 */
	List<GoodsBrandImageVO> evtBrandImageList;
	
	/** 이벤트 모바일 이미지 목록 */
	List<GoodsBrandImageVO> evtMobBrandImageList;
	
	/** 이벤트 진행 여부 */
	private String eventAt;

	/*
	 * 검색 : 업체고유ID
	 */
	private String searchCmpnyId;
	
	/**브랜드 소개 제목*/
	private String brandIntSj;
	
	/**브랜드 소개 내용*/
	private String brandIntCn;

	/**브랜드 소개 연결링크*/
	private String brandIntLink;
	
	private String eventUrl;
	
	/** 브랜드대표B2C상품 */
	private GoodsVO brandBtcGoods;
	/** 브랜드대표B2B상품*/
	private GoodsVO brandBtbGoods;
	/** 교환반품주소 */
	private String svcAdres;
	/** 교환반품택배사 **/
	private String svcHdryNm;
	/** 반품배송비 */
	private java.math.BigDecimal rtngudDlvyPc;
	/** 교환배송비 */
	private java.math.BigDecimal exchngDlvyPc;
	
	/*
	 * 검색 : 제휴사ID 
	 */
	private String searchPrtnrId;
	/*
	 * 검색 : 브랜드관 이름 (시작)
	 */
	private String searchBrandNmStart;
	/*
	 * 검색 : 브랜드관 이름 (끝)
	 */
	private String searchBrandNmEnd;
	
	public String getBrandIntSj() {
		return brandIntSj;
	}
	public void setBrandIntSj(String brandIntSj) {
		this.brandIntSj = brandIntSj;
	}
	public GoodsVO getGoods() {
		return goods;
	}
	public void setGoods(GoodsVO goods) {
		this.goods = goods;
	}
	public String getBrandIntCn() {
		return brandIntCn;
	}
	public void setBrandIntCn(String brandIntCn) {
		this.brandIntCn = brandIntCn;
	}
	public String getBrandIntLink() {
		return brandIntLink;
	}
	public void setBrandIntLink(String brandIntLink) {
		this.brandIntLink = brandIntLink;
	}
	public String getBrandId() {
		return brandId;
	}
	public void setBrandId(String brandId) {
		this.brandId = brandId;
	}
	
	public String getCmpnyId() {
		return cmpnyId;
	}
	public void setCmpnyId(String cmpnyId) {
		this.cmpnyId = cmpnyId;
	}
	public String getBrandNm() {
		return brandNm;
	}
	public void setBrandNm(String brandNm) {
		this.brandNm = brandNm;
	}
	public String getBrandImagePath() {
		return brandImagePath;
	}
	public void setBrandImagePath(String brandImagePath) {
		this.brandImagePath = brandImagePath;
	}
	public String getBrandImageThumbPath() {
		return brandImageThumbPath;
	}
	public void setBrandImageThumbPath(String brandImageThumbPath) {
		this.brandImageThumbPath = brandImageThumbPath;
	}
	public String getSearchCmpnyId() {
		return searchCmpnyId;
	}
	public void setSearchCmpnyId(String searchCmpnyId) {
		this.searchCmpnyId = searchCmpnyId;
	}
/*	public List<GoodsBrandImageVO> getDekBrandImageList() {
		return dekBrandImageList;
	}
	public void setDekBrandImageList(List<GoodsBrandImageVO> dekBrandImageList) {
		this.dekBrandImageList = dekBrandImageList;
	}
	public List<GoodsBrandImageVO> getMobBrandImageList() {
		return mobBrandImageList;
	}
	public void setMobBrandImageList(List<GoodsBrandImageVO> mobBrandImageList) {
		this.mobBrandImageList = mobBrandImageList;
	}*/
	public List<GoodsBrandImageVO> getRepBrandImageList() {
		return repBrandImageList;
	}
	public void setRepBrandImageList(List<GoodsBrandImageVO> repBrandImageList) {
		this.repBrandImageList = repBrandImageList;
	}
	public List<GoodsBrandImageVO> getIntBrandImageList() {
		return intBrandImageList;
	}
	public void setIntBrandImageList(List<GoodsBrandImageVO> intBrandImageList) {
		this.intBrandImageList = intBrandImageList;
	}
	public List<GoodsBrandImageVO> getRepMobBrandImageList() {
		return repMobBrandImageList;
	}
	public void setRepMobBrandImageList(List<GoodsBrandImageVO> repMobBrandImageList) {
		this.repMobBrandImageList = repMobBrandImageList;
	}
	public String getCmpnyNm() {
		return cmpnyNm;
	}
	public void setCmpnyNm(String cmpnyNm) {
		this.cmpnyNm = cmpnyNm;
	}
	public String getWrd() {
		return wrd;
	}
	public void setWrd(String wrd) {
		this.wrd = wrd;
	}
	public String getSearchPrtnrId() {
		return searchPrtnrId;
	}
	public void setSearchPrtnrId(String searchPrtnrId) {
		this.searchPrtnrId = searchPrtnrId;
	}
	public String getPrtnrId() {
		return prtnrId;
	}
	public void setPrtnrId(String prtnrId) {
		this.prtnrId = prtnrId;
	}
	public String getBrandExpsrSeCode() {
		return brandExpsrSeCode;
	}
	public void setBrandExpsrSeCode(String brandExpsrSeCode) {
		this.brandExpsrSeCode = brandExpsrSeCode;
	}
	public String getSearchBrandNmStart() {
		return searchBrandNmStart;
	}
	public void setSearchBrandNmStart(String searchBrandNmStart) {
		this.searchBrandNmStart = searchBrandNmStart;
	}
	public String getSearchBrandNmEnd() {
		return searchBrandNmEnd;
	}
	public void setSearchBrandNmEnd(String searchBrandNmEnd) {
		this.searchBrandNmEnd = searchBrandNmEnd;
	}
	public String getBrandIntGoods() {
		return brandIntGoods;
	}
	public void setBrandIntGoods(String brandIntGoods) {
		this.brandIntGoods = brandIntGoods;
	}
	public String getBrandRepImg() {
		return brandRepImg;
	}
	public void setBrandRepImg(String brandRepImg) {
		this.brandRepImg = brandRepImg;
	}
	public String getBrandRepImgMob() {
		return brandRepImgMob;
	}
	public void setBrandRepImgMob(String brandRepImgMob) {
		this.brandRepImgMob = brandRepImgMob;
	}
	public String getBrandIntImg() {
		return brandIntImg;
	}
	public void setBrandIntImg(String brandIntImg) {
		this.brandIntImg = brandIntImg;
	}
	public String getEventAt() {
		return eventAt;
	}
	public void setEventAt(String eventAt) {
		this.eventAt = eventAt;
	}
	public List<GoodsBrandImageVO> getEvtBrandImageList() {
		return evtBrandImageList;
	}
	public void setEvtBrandImageList(List<GoodsBrandImageVO> evtBrandImageList) {
		this.evtBrandImageList = evtBrandImageList;
	}
	public List<GoodsBrandImageVO> getEvtMobBrandImageList() {
		return evtMobBrandImageList;
	}
	public void setEvtMobBrandImageList(List<GoodsBrandImageVO> evtMobBrandImageList) {
		this.evtMobBrandImageList = evtMobBrandImageList;
	}
	public String getEventUrl() {
		return eventUrl;
	}
	public void setEventUrl(String eventUrl) {
		this.eventUrl = eventUrl;
	}
	
	public String getBrandBtcGoodsId() {
		return brandBtcGoodsId;
	}
	public void setBrandBtcGoodsId(String brandBtcGoodsId) {
		this.brandBtcGoodsId = brandBtcGoodsId;
	}
	public String getBrandBtbGoodsId() {
		return brandBtbGoodsId;
	}
	public void setBrandBtbGoodsId(String brandBtbGoodsId) {
		this.brandBtbGoodsId = brandBtbGoodsId;
	}
	public GoodsVO getBrandBtcGoods() {
		return brandBtcGoods;
	}
	public void setBrandBtcGoods(GoodsVO brandBtcGoods) {
		this.brandBtcGoods = brandBtcGoods;
	}
	public GoodsVO getBrandBtbGoods() {
		return brandBtbGoods;
	}
	public void setBrandBtbGoods(GoodsVO brandBtbGoods) {
		this.brandBtbGoods = brandBtbGoods;
	}
	public String getSvcAdres() {
		return svcAdres;
	}
	public void setSvcAdres(String svcAdres) {
		this.svcAdres = svcAdres;
	}
	public String getSvcHdryNm() {
		return svcHdryNm;
	}
	public void setSvcHdryNm(String svcHdryNm) {
		this.svcHdryNm = svcHdryNm;
	}
	public java.math.BigDecimal getRtngudDlvyPc() {
		return rtngudDlvyPc;
	}
	public void setRtngudDlvyPc(java.math.BigDecimal rtngudDlvyPc) {
		this.rtngudDlvyPc = rtngudDlvyPc;
	}
	public java.math.BigDecimal getExchngDlvyPc() {
		return exchngDlvyPc;
	}
	public void setExchngDlvyPc(java.math.BigDecimal exchngDlvyPc) {
		this.exchngDlvyPc = exchngDlvyPc;
	}
	
}
