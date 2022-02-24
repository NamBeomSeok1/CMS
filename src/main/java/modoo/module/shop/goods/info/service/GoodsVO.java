package modoo.module.shop.goods.info.service;

import java.util.List;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import modoo.module.common.service.CommonDefaultSearchVO;
import modoo.module.shop.cmpny.service.PrtnrCmpnyVO;
import modoo.module.shop.goods.image.service.GoodsImageVO;
import modoo.module.shop.goods.keyword.service.GoodsKeywordVO;
import modoo.module.shop.goods.recomend.service.GoodsRecomendVO;

@SuppressWarnings("serial")
public class GoodsVO extends CommonDefaultSearchVO {

	/** 상품고유ID */
	private String goodsId;
	/** 제휴사매핑고유ID */
	private String pcmapngId;
	/** 제휴사고유ID */
	private String prtnrId;
	/** 업체고유ID */
	private String cmpnyId;
	private String cmpnyNm;
	/** 상품카테고리고유ID */
	private String goodsCtgryId;
	private String goodsCtgryNm; //카테고리명
	/** 상품종류코드 : GNR 일반상품, SBS 구독상품 */
	private String goodsKndCode = "SBS";
	/** 상품명 */
	@NotEmpty
	private String goodsNm;
	/** 모델명 */
	@NotEmpty
	private String modelNm;
	/** 제조사(수입사) */
	private String makr;
	/** 원산지 */
	private String orgplce;
	/** 브랜드고유ID */
	private String brandId;
	private String brandNm; //브랜드명
	/** 인증사항 */
	private String crtfcMatter;
	/** 상담연락처 */
	@NotEmpty
	private String cnsltTelno;
	/** 상품소개 */
	private String goodsIntrcn;
	/** 이벤트문구 */
	private String evntWords;
	/** 관리자메모 */
	private String mngrMemo;
	/** 품절여부 */
	private String soldOutAt;
	/** 영상소스내용 */
	private String mvpSourcCn;
	/** 상품내용 */
	@Length(min = 0, max = 5592405)
	private String goodsCn;
	/** 상품가격 */
	private java.math.BigDecimal goodsPc;
	/** 상품수수료율 */
	private java.math.BigDecimal goodsFeeRate;
	/** 상품공급가 */
	private java.math.BigDecimal goodsSplpc;
	/** 상품적립금 */
	private java.math.BigDecimal goodsRsrvmney;
	/** 상품적립금비율여부 */
	private String goodsRsrvmneyRateAt;
	/** 상품적립금비율 */
	private java.math.BigDecimal goodsRsrvmneyRate;
	/** 상품 수수료*/
	//private java.math.BigDecimal goodsFee;
	/** 시중가 */
	private java.math.BigDecimal mrktPc;
	/** 과세구분코드 : TA01 과세,   TA02 면세 */
	private String taxtSeCode;
	/** 배송비구문코드 : DS01 선불,  DS02 착불, DS03 무료 */
	private String dlvySeCode;
	/** 무료배송가격 */
	private java.math.BigDecimal freeDlvyPc;
	/** 배송정책구분코드 : DP01 판매자정책, DP02 기본정책, DP03 상품별정책 */
	private String dlvyPolicySeCode;
	/** 배송정책내용 */
	private String dlvyPolicyCn;
	/** 배송비용 */
	private java.math.BigDecimal dlvyPc;
	/** 도서산간지역 배송비용 */
	@NotNull
	private java.math.BigDecimal islandDlvyPc;
	/** 제주도 배송비용 */
	@NotNull
	private java.math.BigDecimal jejuDlvyPc;
	/** 구독주기구분코드 : WEEK, MONTH */
	private String sbscrptCycleSeCode;
	/** 구독 주 주기 : 콤마로 분리 */
	private String sbscrptWeekCycle;
	/** 구독 주 배송요일 : 콤마로 분리 */
	private String sbscrptDlvyWd;
	/** 구독최소이용 주 주기 */
	private Integer sbscrptMinUseWeek;
	/** 구독 월 주기 */
	private String sbscrptMtCycle;
	/** 구독최소이용 월 주기 */
	private Integer sbscrptMinUseMt;
	/** 구독 월 배송일 */
	private Integer sbscrptDlvyDay;
	/** 배송기준 결제일 */
	private Integer sbscrptSetleDay;
	/** 묶음배송여부 */
	private String bundleDlvyAt;
	/** 묶음배송개수 */
	private Integer bundleDlvyCo;
	/** 조회수 */
	private Integer rdcnt;
	/** 판매수 */
	private Integer sleCo;
	/** 옵션 사용여부 */
	private String optnUseAt;
	/** 기본옵션 사용여부 */
	private String dOptnUseAt;
	/** 추가옵션 사용여부 */
	private String aOptnUseAt;
	/** 첫구독옵션 사용여부 */
	private String fOptnUseAt;
	/** 질답옵션 사용여부 */
	private String qOptnUseAt;
	/** 성인인증필요여부 */
	private String adultCrtAt;
	/** 등록상태코드 : R 대기, C 완료, E 종료 */
	private String registSttusCode;
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


	/** 수강권상품코드 */
	private String vchCode;
	/** 수강권상품코드 */
	private String vchCodeNm;
	/** 수강권상품유무 */
	private String vchUseAt;
	/** 수강권기간타입(우선은 month만 추후 추가)  */
	private String vchPdTy;
	/** 수강권기간타입명 */
	private String vchPdTynm;
	/** 수강권유효기간(개월,주말 추후 추가) */
	private String vchValidPd;

	/** 첫구독옵션 필수여부 */
	private String frstOptnEssntlAt;
	
	/** 구독 주 주기 목록 */
	private List<String> sbscrptWeekCycleList;
	/** 구독 주 배송요일 목록 */
	private List<String> sbscrptDlvyWdList;
	/** 구독 월 주기 목록 */
	private List<String> sbscrptMtCycleList;
	
	/** 상품대표이미지경로 */
	private String goodsTitleImagePath; //원본
	private String goodsTitleImageThumbPath; //썸네일
	
	/** 상품항목 목록 */
	private List<GoodsItemVO> goodsItemList;
	/** 기본옵션 항목 */
	private List<GoodsItemVO> dGitemList;
	/** 추가옵션 항목 */
	private List<GoodsItemVO> aGitemList;
	/** 첫구독옵션 항목 */
	private List<GoodsItemVO> fGitemList;
	/** 질답옵션 항목*/
	private List<GoodsItemVO> qGitemList;
	/** 검색키워드 목록*/
	private List<GoodsKeywordVO> goodsKeywordList;
	
	/** 상품설명이미지 목록 */
	private List<GoodsImageVO> gdcImageList;
	/** 상품이벤트이미지 목록 */
	private List<GoodsImageVO> evtImageList;
	/** 상품이미지 목록 */
	private List<GoodsImageVO> goodsImageList;
	
	/** 추천상품 목록 */
	private List<GoodsRecomendVO> goodsRecomendList;
	
	/** 브랜드추천상품 목록 */
	private List<?> brandGoodsRecomendList;

	/** 1회 체험 여부 */
	private String exprnUseAt;
	
	/** 1회 체험 가격 */
	private java.math.BigDecimal exprnPc;

	/** 시중가 노출 여부 */
	private String mrktUseAt;
	
	/** 1회 체험 복수할인가격*/
	private java.math.BigDecimal compnoDscntPc;
	
	/** 1회 */
	private String compnoDscntUseAt;
	
	/** 상품 뎁스별 카테고리 코드 : goodsCtgryId */
	@NotEmpty
	private String cateCode1;
	private String cateCode2;
	private String cateCode3;
	
	private String cmpnyTelno;
	
	private String hdryId;
	private String hdryNm;
	
	/** 이벤트 진행 여부	 */
	private String eventAt;

	/*
	 * 검색 : 업체고유ID
	 */
	private String searchCmpnyId;

	/*
	 * 검색 : 업체명
	 */
	private String searchCmpnyNm;
	
	/*
	 * 검색 : 카테고리ID
	 */
	private String searchGoodsCtgryId;
	private String searchSubCtgryId;
	
	/*
	 * 검색 : Depth 별 카테고리 코드 
	 */
	private String searchCateCode1;
	private String searchCateCode2;
	private String searchCateCode3;
	
	/*
	 * 검색 : 등록상테코드
	 */
	private String searchRegistSttusCode;

	/*
	 * 검색 : 상품고유ID가 아닌
	 */
	private String searchNotGoodsId;
	
	/*
	 * 검색 : 오름차순, 내림차순
	 */
	private String searchOrderField2;
	
	/**
	 * 검색 : 배스트여부
	 */
	private String searchBestAt;
	
	/** 제휴사매핑목록 */
	private List<PrtnrCmpnyVO> prtnrCmpnyList;

	/** 상품 썸네일 */
	private String goodsImageThumbPath;
	
	/** 베스트구독여부 */
	private java.lang.Integer bestOrdr = 0;
	
	/*
	 * 검색 : 브랜드ID
	 */
	private String searchGoodsBrandId;
	
	/*
	 * 검색 : 파트너사ID (B2C, 이지웰)
	 */
	private String searchPrtnrId;
	
	/*
	 * 검색 : 이벤트파트너사ID (B2C, 이지웰)
	 */
	private String searchEventPrtnrId;
	
	/*
	 * 검색 : 검색용 
	 */
	private List<String> searchGoodsKeyword;
	
	/*
	 * 검색 : 구독, 일반
	 */
	private String searchKndCode;
	
	/*
	 * 검색 : 구독 상품이면서 BEST 구독 
	 */
	private String searchMainBestGoodsAt;
	
	/*
	 * 검색 : 상품번호 
	 */
	private String searchGoodsId;
	/*
	 * 검색 : 관리자 여부
	 */
	private String searchManageAt;

	public String getGoodsId() {
		return goodsId;
	}
	public void setGoodsId(String goodsId) {
		this.goodsId = goodsId;
	}
	public String getPcmapngId() {
		return pcmapngId;
	}
	public void setPcmapngId(String pcmapngId) {
		this.pcmapngId = pcmapngId;
	}
	public String getCmpnyId() {
		return cmpnyId;
	}
	public void setCmpnyId(String cmpnyId) {
		this.cmpnyId = cmpnyId;
	}
	public String getGoodsCtgryId() {
		return goodsCtgryId;
	}
	public void setGoodsCtgryId(String goodsCtgryId) {
		this.goodsCtgryId = goodsCtgryId;
	}
	public String getGoodsKndCode() {
		return goodsKndCode;
	}
	public void setGoodsKndCode(String goodsKndCode) {
		this.goodsKndCode = goodsKndCode;
	}
	public String getGoodsNm() {
		return goodsNm;
	}
	public void setGoodsNm(String goodsNm) {
		this.goodsNm = goodsNm;
	}
	public String getModelNm() {
		return modelNm;
	}
	public void setModelNm(String modelNm) {
		this.modelNm = modelNm;
	}
	public String getMakr() {
		return makr;
	}
	public void setMakr(String makr) {
		this.makr = makr;
	}
	public String getOrgplce() {
		return orgplce;
	}
	public void setOrgplce(String orgplce) {
		this.orgplce = orgplce;
	}
	public String getBrandId() {
		return brandId;
	}
	public void setBrandId(String brandId) {
		this.brandId = brandId;
	}
	public String getCrtfcMatter() {
		return crtfcMatter;
	}
	public void setCrtfcMatter(String crtfcMatter) {
		this.crtfcMatter = crtfcMatter;
	}
	public String getCnsltTelno() {
		return cnsltTelno;
	}
	public void setCnsltTelno(String cnsltTelno) {
		this.cnsltTelno = cnsltTelno;
	}
	public String getGoodsIntrcn() {
		return goodsIntrcn;
	}
	public void setGoodsIntrcn(String goodsIntrcn) {
		this.goodsIntrcn = goodsIntrcn;
	}
	public String getEvntWords() {
		return evntWords;
	}
	public void setEvntWords(String evntWords) {
		this.evntWords = evntWords;
	}
	public String getMngrMemo() {
		return mngrMemo;
	}
	public void setMngrMemo(String mngrMemo) {
		this.mngrMemo = mngrMemo;
	}
	public List<?> getBrandGoodsRecomendList() {
		return brandGoodsRecomendList;
	}
	public void setBrandGoodsRecomendList(List<?> brandGoodsRecomendList) {
		this.brandGoodsRecomendList = brandGoodsRecomendList;
	}
	public String getMvpSourcCn() {
		return mvpSourcCn;
	}
	public void setMvpSourcCn(String mvpSourcCn) {
		this.mvpSourcCn = mvpSourcCn;
	}
	public String getGoodsCn() {
		return goodsCn;
	}
	public void setGoodsCn(String goodsCn) {
		this.goodsCn = goodsCn;
	}
	public java.math.BigDecimal getMrktPc() {
		return mrktPc;
	}
	public java.math.BigDecimal getGoodsPc() {
		return goodsPc;
	}
	public void setGoodsPc(java.math.BigDecimal goodsPc) {
		this.goodsPc = goodsPc;
	}
	public java.math.BigDecimal getGoodsRsrvmney() {
		return goodsRsrvmney;
	}
	public void setGoodsRsrvmney(java.math.BigDecimal goodsRsrvmney) {
		this.goodsRsrvmney = goodsRsrvmney;
	}
	public String getGoodsRsrvmneyRateAt() {
		return goodsRsrvmneyRateAt;
	}
	public void setGoodsRsrvmneyRateAt(String goodsRsrvmneyRateAt) {
		this.goodsRsrvmneyRateAt = goodsRsrvmneyRateAt;
	}
	public java.math.BigDecimal getGoodsRsrvmneyRate() {
		return goodsRsrvmneyRate;
	}
	public void setGoodsRsrvmneyRate(java.math.BigDecimal goodsRsrvmneyRate) {
		this.goodsRsrvmneyRate = goodsRsrvmneyRate;
	}

	public String getVchCode() {
		return vchCode;
	}

	public void setVchCode(String vchCode) {
		this.vchCode = vchCode;
	}

	public String getVchCodeNm() {
		return vchCodeNm;
	}

	public void setVchCodeNm(String vchCodeNm) {
		this.vchCodeNm = vchCodeNm;
	}

	public String getVchUseAt() {
		return vchUseAt;
	}

	public void setVchUseAt(String vchUseAt) {
		this.vchUseAt = vchUseAt;
	}

	public String getVchPdTy() {
		return vchPdTy;
	}

	public void setVchPdTy(String vchPdTy) {
		this.vchPdTy = vchPdTy;
	}

	public String getVchValidPd() {
		return vchValidPd;
	}

	public void setVchValidPd(String vchValidPd) {
		this.vchValidPd = vchValidPd;
	}

	public String getVchPdTynm() {
		return vchPdTynm;
	}

	public void setVchPdTynm(String vchPdTynm) {
		this.vchPdTynm = vchPdTynm;
	}

	/*public java.math.BigDecimal getGoodsFee() {
                    return goodsFee;
                }
                public void setGoodsFee(java.math.BigDecimal goodsFee) {
                    this.goodsFee = goodsFee;
                }*/
	public void setMrktPc(java.math.BigDecimal mrktPc) {
		this.mrktPc = mrktPc;
	}
	public String getTaxtSeCode() {
		return taxtSeCode;
	}
	public void setTaxtSeCode(String taxtSeCode) {
		this.taxtSeCode = taxtSeCode;
	}
	public String getDlvySeCode() {
		return dlvySeCode;
	}
	public void setDlvySeCode(String dlvySeCode) {
		this.dlvySeCode = dlvySeCode;
	}
	public java.math.BigDecimal getDlvyPc() {
		return dlvyPc;
	}
	public void setDlvyPc(java.math.BigDecimal dlvyPc) {
		this.dlvyPc = dlvyPc;
	}
	public String getBundleDlvyAt() {
		return bundleDlvyAt;
	}
	public void setBundleDlvyAt(String bundleDlvyAt) {
		this.bundleDlvyAt = bundleDlvyAt;
	}
	public Integer getBundleDlvyCo() {
		return bundleDlvyCo;
	}
	public void setBundleDlvyCo(Integer bundleDlvyCo) {
		this.bundleDlvyCo = bundleDlvyCo;
	}
	public Integer getRdcnt() {
		return rdcnt;
	}
	public void setRdcnt(Integer rdcnt) {
		this.rdcnt = rdcnt;
	}
	public Integer getSleCo() {
		return sleCo;
	}
	public void setSleCo(Integer sleCo) {
		this.sleCo = sleCo;
	}
	public String getRegistSttusCode() {
		return registSttusCode;
	}
	public void setRegistSttusCode(String registSttusCode) {
		this.registSttusCode = registSttusCode;
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
	public String getExprnUseAt() {
		return exprnUseAt;
	}
	public void setExprnUseAt(String exprnUseAt) {
		this.exprnUseAt = exprnUseAt;
	}
	public java.math.BigDecimal getExprnPc() {
		return exprnPc;
	}
	public void setExprnPc(java.math.BigDecimal exprnPc) {
		this.exprnPc = exprnPc;
	}
	public String getCompnoDscntUseAt() {
		return compnoDscntUseAt;
	}
	public void setCompnoDscntUseAt(String compnoDscntUseAt) {
		this.compnoDscntUseAt = compnoDscntUseAt;
	}
	public java.math.BigDecimal getCompnoDscntPc() {
		return compnoDscntPc;
	}
	public void setCompnoDscntPc(java.math.BigDecimal compnoDscntPc) {
		this.compnoDscntPc = compnoDscntPc;
	}
	public String getSearchCmpnyId() {
		return searchCmpnyId;
	}
	public void setSearchCmpnyId(String searchCmpnyId) {
		this.searchCmpnyId = searchCmpnyId;
	}
	public String getSearchRegistSttusCode() {
		return searchRegistSttusCode;
	}
	public void setSearchRegistSttusCode(String searchRegistSttusCode) {
		this.searchRegistSttusCode = searchRegistSttusCode;
	}
	public String getSearchGoodsCtgryId() {
		return searchGoodsCtgryId;
	}
	public void setSearchGoodsCtgryId(String searchGoodsCtgryId) {
		this.searchGoodsCtgryId = searchGoodsCtgryId;
	}
	public String getSearchCmpnyNm() {
		return searchCmpnyNm;
	}
	public void setSearchCmpnyNm(String searchCmpnyNm) {
		this.searchCmpnyNm = searchCmpnyNm;
	}
	public String getSearchCateCode1() {
		return searchCateCode1;
	}
	public void setSearchCateCode1(String searchCateCode1) {
		this.searchCateCode1 = searchCateCode1;
	}
	public String getSearchCateCode2() {
		return searchCateCode2;
	}
	public void setSearchCateCode2(String searchCateCode2) {
		this.searchCateCode2 = searchCateCode2;
	}
	public String getSearchCateCode3() {
		return searchCateCode3;
	}
	public void setSearchCateCode3(String searchCateCode3) {
		this.searchCateCode3 = searchCateCode3;
	}
	public String getCateCode1() {
		return cateCode1;
	}
	public void setCateCode1(String cateCode1) {
		this.cateCode1 = cateCode1;
	}
	public String getCateCode2() {
		return cateCode2;
	}
	public void setCateCode2(String cateCode2) {
		this.cateCode2 = cateCode2;
	}
	public String getCateCode3() {
		return cateCode3;
	}
	public void setCateCode3(String cateCode3) {
		this.cateCode3 = cateCode3;
	}
	public List<GoodsItemVO> getGoodsItemList() {
		return goodsItemList;
	}
	public void setGoodsItemList(List<GoodsItemVO> goodsItemList) {
		this.goodsItemList = goodsItemList;
	}
	public List<GoodsImageVO> getGdcImageList() {
		return gdcImageList;
	}
	public void setGdcImageList(List<GoodsImageVO> gdcImageList) {
		this.gdcImageList = gdcImageList;
	}
	public String getCmpnyNm() {
		return cmpnyNm;
	}
	public void setCmpnyNm(String cmpnyNm) {
		this.cmpnyNm = cmpnyNm;
	}
	public List<GoodsKeywordVO> getGoodsKeywordList() {
		return goodsKeywordList;
	}
	public void setGoodsKeywordList(List<GoodsKeywordVO> goodsKeywordList) {
		this.goodsKeywordList = goodsKeywordList;
	}
	public String getGoodsTitleImagePath() {
		return goodsTitleImagePath;
	}
	public void setGoodsTitleImagePath(String goodsTitleImagePath) {
		this.goodsTitleImagePath = goodsTitleImagePath;
	}
	public String getGoodsCtgryNm() {
		return goodsCtgryNm;
	}
	public void setGoodsCtgryNm(String goodsCtgryNm) {
		this.goodsCtgryNm = goodsCtgryNm;
	}
	public String getSearchNotGoodsId() {
		return searchNotGoodsId;
	}
	public void setSearchNotGoodsId(String searchNotGoodsId) {
		this.searchNotGoodsId = searchNotGoodsId;
	}
	public String getGoodsTitleImageThumbPath() {
		return goodsTitleImageThumbPath;
	}
	public void setGoodsTitleImageThumbPath(String goodsTitleImageThumbPath) {
		this.goodsTitleImageThumbPath = goodsTitleImageThumbPath;
	}
	public List<GoodsRecomendVO> getGoodsRecomendList() {
		return goodsRecomendList;
	}
	public void setGoodsRecomendList(List<GoodsRecomendVO> goodsRecomendList) {
		this.goodsRecomendList = goodsRecomendList;
	}
	public String getOptnUseAt() {
		return optnUseAt;
	}
	public void setOptnUseAt(String optnUseAt) {
		this.optnUseAt = optnUseAt;
	}
	public String getdOptnUseAt() {
		return dOptnUseAt;
	}
	public void setdOptnUseAt(String dOptnUseAt) {
		this.dOptnUseAt = dOptnUseAt;
	}
	public String getaOptnUseAt() {
		return aOptnUseAt;
	}
	public void setaOptnUseAt(String aOptnUseAt) {
		this.aOptnUseAt = aOptnUseAt;
	}
	public String getfOptnUseAt() {
		return fOptnUseAt;
	}
	public void setfOptnUseAt(String fOptnUseAt) {
		this.fOptnUseAt = fOptnUseAt;
	}
	public List<GoodsItemVO> getdGitemList() {
		return dGitemList;
	}
	public void setdGitemList(List<GoodsItemVO> dGitemList) {
		this.dGitemList = dGitemList;
	}
	public List<GoodsItemVO> getaGitemList() {
		return aGitemList;
	}
	public void setaGitemList(List<GoodsItemVO> aGitemList) {
		this.aGitemList = aGitemList;
	}
	public List<GoodsItemVO> getfGitemList() {
		return fGitemList;
	}
	public void setfGitemList(List<GoodsItemVO> fGitemList) {
		this.fGitemList = fGitemList;
	}
	public String getSoldOutAt() {
		return soldOutAt;
	}
	public void setSoldOutAt(String soldOutAt) {
		this.soldOutAt = soldOutAt;
	}
	public String getqOptnUseAt() {
		return qOptnUseAt;
	}
	public void setqOptnUseAt(String qOptnUseAt) {
		this.qOptnUseAt = qOptnUseAt;
	}
	public String getAdultCrtAt() {
		return adultCrtAt;
	}
	public void setAdultCrtAt(String adultCrtAt) {
		this.adultCrtAt = adultCrtAt;
	}
	public List<GoodsItemVO> getqGitemList() {
		return qGitemList;
	}
	public void setqGitemList(List<GoodsItemVO> qGitemList) {
		this.qGitemList = qGitemList;
	}
	public String getSbscrptCycleSeCode() {
		return sbscrptCycleSeCode;
	}
	public void setSbscrptCycleSeCode(String sbscrptCycleSeCode) {
		this.sbscrptCycleSeCode = sbscrptCycleSeCode;
	}
	public String getSbscrptWeekCycle() {
		return sbscrptWeekCycle;
	}
	public void setSbscrptWeekCycle(String sbscrptWeekCycle) {
		this.sbscrptWeekCycle = sbscrptWeekCycle;
	}
	public String getSbscrptDlvyWd() {
		return sbscrptDlvyWd;
	}
	public void setSbscrptDlvyWd(String sbscrptDlvyWd) {
		this.sbscrptDlvyWd = sbscrptDlvyWd;
	}
	public Integer getSbscrptMinUseWeek() {
		return sbscrptMinUseWeek;
	}
	public void setSbscrptMinUseWeek(Integer sbscrptMinUseWeek) {
		this.sbscrptMinUseWeek = sbscrptMinUseWeek;
	}
	public String getSbscrptMtCycle() {
		return sbscrptMtCycle;
	}
	public void setSbscrptMtCycle(String sbscrptMtCycle) {
		this.sbscrptMtCycle = sbscrptMtCycle;
	}
	public Integer getSbscrptMinUseMt() {
		return sbscrptMinUseMt;
	}
	public void setSbscrptMinUseMt(Integer sbscrptMinUseMt) {
		this.sbscrptMinUseMt = sbscrptMinUseMt;
	}
	public Integer getSbscrptDlvyDay() {
		return sbscrptDlvyDay;
	}
	public void setSbscrptDlvyDay(Integer sbscrptDlvyDay) {
		this.sbscrptDlvyDay = sbscrptDlvyDay;
	}
	public List<GoodsImageVO> getGoodsImageList() {
		return goodsImageList;
	}
	public void setGoodsImageList(List<GoodsImageVO> goodsImageList) {
		this.goodsImageList = goodsImageList;
	}
	public String getSearchBestAt() {
		return searchBestAt;
	}
	public void setSearchBestAt(String searchBestAt) {
		this.searchBestAt = searchBestAt;
	}
	public List<PrtnrCmpnyVO> getPrtnrCmpnyList() {
		return prtnrCmpnyList;
	}
	public void setPrtnrCmpnyList(List<PrtnrCmpnyVO> prtnrCmpnyList) {
		this.prtnrCmpnyList = prtnrCmpnyList;
	}
	public Integer getSbscrptSetleDay() {
		return sbscrptSetleDay;
	}
	public void setSbscrptSetleDay(Integer sbscrptSetleDay) {
		this.sbscrptSetleDay = sbscrptSetleDay;
	}
	public String getBrandNm() {
		return brandNm;
	}
	public void setBrandNm(String brandNm) {
		this.brandNm = brandNm;
	}
	public List<String> getSbscrptWeekCycleList() {
		return sbscrptWeekCycleList;
	}
	public void setSbscrptWeekCycleList(List<String> sbscrptWeekCycleList) {
		this.sbscrptWeekCycleList = sbscrptWeekCycleList;
	}
	public List<String> getSbscrptDlvyWdList() {
		return sbscrptDlvyWdList;
	}
	public void setSbscrptDlvyWdList(List<String> sbscrptDlvyWdList) {
		this.sbscrptDlvyWdList = sbscrptDlvyWdList;
	}
	public List<String> getSbscrptMtCycleList() {
		return sbscrptMtCycleList;
	}
	public void setSbscrptMtCycleList(List<String> sbscrptMtCycleList) {
		this.sbscrptMtCycleList = sbscrptMtCycleList;
	}
	public String getGoodsImageThumbPath() {
		return goodsImageThumbPath;
	}
	public void setGoodsImageThumbPath(String goodsImageThumbPath) {
		this.goodsImageThumbPath = goodsImageThumbPath;
	}
	public java.math.BigDecimal getGoodsFeeRate() {
		return goodsFeeRate;
	}
	public void setGoodsFeeRate(java.math.BigDecimal goodsFeeRate) {
		this.goodsFeeRate = goodsFeeRate;
	}
	public java.math.BigDecimal getGoodsSplpc() {
		return goodsSplpc;
	}
	public void setGoodsSplpc(java.math.BigDecimal goodsSplpc) {
		this.goodsSplpc = goodsSplpc;
	}
	public String getDlvyPolicySeCode() {
		return dlvyPolicySeCode;
	}
	public void setDlvyPolicySeCode(String dlvyPolicySeCode) {
		this.dlvyPolicySeCode = dlvyPolicySeCode;
	}
	public String getDlvyPolicyCn() {
		return dlvyPolicyCn;
	}
	public void setDlvyPolicyCn(String dlvyPolicyCn) {
		this.dlvyPolicyCn = dlvyPolicyCn;
	}
	public java.math.BigDecimal getFreeDlvyPc() {
		return freeDlvyPc;
	}
	public void setFreeDlvyPc(java.math.BigDecimal freeDlvyPc) {
		this.freeDlvyPc = freeDlvyPc;
	}
	public java.lang.Integer getBestOrdr() {
		return bestOrdr;
	}
	public void setBestOrdr(java.lang.Integer bestOrdr) {
		this.bestOrdr = bestOrdr;
	}
	public String getSearchGoodsBrandId() {
		return searchGoodsBrandId;
	}
	public void setSearchGoodsBrandId(String searchGoodsBrandId) {
		this.searchGoodsBrandId = searchGoodsBrandId;
	}
	public String getSearchPrtnrId() {
		return searchPrtnrId;
	}
	public void setSearchPrtnrId(String searchPrtnrId) {
		this.searchPrtnrId = searchPrtnrId;
	}
	public List<String> getSearchGoodsKeyword() {
		return searchGoodsKeyword;
	}
	public void setSearchGoodsKeyword(List<String> searchGoodsKeyword) {
		this.searchGoodsKeyword = searchGoodsKeyword;
	}
	public java.math.BigDecimal getIslandDlvyPc() {
		return islandDlvyPc;
	}
	public void setIslandDlvyPc(java.math.BigDecimal islandDlvyPc) {
		this.islandDlvyPc = islandDlvyPc;
	}
	public java.math.BigDecimal getJejuDlvyPc() {
		return jejuDlvyPc;
	}
	public void setJejuDlvyPc(java.math.BigDecimal jejuDlvyPc) {
		this.jejuDlvyPc = jejuDlvyPc;
	}
	public String getPrtnrId() {
		return prtnrId;
	}
	public void setPrtnrId(String prtnrId) {
		this.prtnrId = prtnrId;
	}
	public String getSearchKndCode() {
		return searchKndCode;
	}
	public void setSearchKndCode(String searchKndCode) {
		this.searchKndCode = searchKndCode;
	}
	public String getSearchMainBestGoodsAt() {
		return searchMainBestGoodsAt;
	}
	public void setSearchMainBestGoodsAt(String searchMainBestGoodsAt) {
		this.searchMainBestGoodsAt = searchMainBestGoodsAt;
	}
	public String getSearchSubCtgryId() {
		return searchSubCtgryId;
	}
	public void setSearchSubCtgryId(String searchSubCtgryId) {
		this.searchSubCtgryId = searchSubCtgryId;
	}
	public String getCmpnyTelno() {
		return cmpnyTelno;
	}
	public void setCmpnyTelno(String cmpnyTelno) {
		this.cmpnyTelno = cmpnyTelno;
	}
	public String getFrstOptnEssntlAt() {
		return frstOptnEssntlAt;
	}
	public void setFrstOptnEssntlAt(String frstOptnEssntlAt) {
		this.frstOptnEssntlAt = frstOptnEssntlAt;
	}
	public String getHdryId() {
		return hdryId;
	}
	public void setHdryId(String hdryId) {
		this.hdryId = hdryId;
	}
	public String getHdryNm() {
		return hdryNm;
	}
	public void setHdryNm(String hdryNm) {
		this.hdryNm = hdryNm;
	}
	public String getSearchOrderField2() {
		return searchOrderField2;
	}
	public void setSearchOrderField2(String searchOrderField2) {
		this.searchOrderField2 = searchOrderField2;
	}
	public String getMrktUseAt() {
		return mrktUseAt;
	}
	public void setMrktUseAt(String mrktUseAt) {
		this.mrktUseAt = mrktUseAt;
	}
	public String getEventAt() {
		return eventAt;
	}
	public void setEventAt(String eventAt) {
		this.eventAt = eventAt;
	}
	public List<GoodsImageVO> getEvtImageList() {
		return evtImageList;
	}
	public void setEvtImageList(List<GoodsImageVO> evtImageList) {
		this.evtImageList = evtImageList;
	}
	public String getSearchGoodsId() {
		return searchGoodsId;
	}
	public void setSearchGoodsId(String searchGoodsId) {
		this.searchGoodsId = searchGoodsId;
	}
	public String getSearchEventPrtnrId() {
		return searchEventPrtnrId;
	}
	public void setSearchEventPrtnrId(String searchEventPrtnrId) {
		this.searchEventPrtnrId = searchEventPrtnrId;
	}
	public String getSearchManageAt() {
		return searchManageAt;
	}
	public void setSearchManageAt(String searchManageAt) {
		this.searchManageAt = searchManageAt;
	}

}
