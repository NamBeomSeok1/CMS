package modoo.module.shop.goods.setle.service;

import modoo.module.common.service.CommonDefaultSearchVO;

@SuppressWarnings("serial")
public class OrderSetleVO extends CommonDefaultSearchVO {

	/** 주문결제고유번호 */
	private java.math.BigDecimal orderSetleNo;
	/** 결제포인트 */
	private java.math.BigDecimal setlePoint;
	/** 결제카드금액 */
	private java.math.BigDecimal setleCardAmount;
	/** 결제총금액 */
	private java.math.BigDecimal setleTotAmount;
	/** 결제종류코드 */
	private String setleTyCode;
	/** 결제상태코드 */
	private String setleSttusCode;
	/** 결제결과코드 */
	private String setleResultCode;
	/** 결제결과종류코드 */
	private String setleResultTyCode;
	/** 결제결과메세지 */
	private String setleResultMssage;
	/** 이지웰결제승인번호 */
	private String ezwSetleConfmNo;
	/** 이니시스결제승인번호 */
	private String iniSetleConfmNo;
	/** 결제예정일*/
	private String setlePrarnde;
	
	/** 결제시점 */
	private java.util.Date setlePnttm;
	/** 임시 키*/
	private String tempKey;
	/** 사용여부 */
	private String useAt;
	/** 구독상품 여부 */
	private Boolean isSbs=false;
	/** 할인가격 */
	private java.math.BigDecimal dscntPc;
	/** 첫번째 주문정보*/
	private String firOrderInfo;
	/** 두번째 주문정보*/
	private String SeOrderInfo;
	/**단건결제방식*/
	private String goPayMethod;
	/* 검색 : 주문번호 */
	//private java.math.BigDecimal searchOrderNo;
	private String searchOrderNo;
	/* 검색 : 상품명 */
	private String searchGoodsNm;
	/* 검색: 카테고리 */
	private String searchGoodsCtgryId;
	/* 검색 : 카테고리 대 */
	private String searchGoodsCtgryId1;
	/* 검색 : 카테고리 중 */
	private String searchGoodsCtgryId2;
	/* 검색 : 카테고리 소 */
	private String searchGoodsCtgryId3;
	/* 검색 : 결제상태 */
	private String searchSetleSttusCode;
	/* 검색 : 기간-시작 */
	private String searchBgnde;
	/* 검색 : 기간-종료 */
	private String searchEndde;
	/* 검색 : 배송상태 */
	private String searchDlvySttusCode;
	/* 검색 : 주문/배송 목록, 주문 리스트 구분 */
	private String searchListType;
	/* 검색 : 업체ID */
	private String searchCmpnyId;
	/* 검색 : 주문배송번호 */
	private java.math.BigDecimal searchOrderDlvyNo;
	/* 검색 : 주문그룹번호 */
	private java.math.BigDecimal searchOrderGroupNo;

	public String getGoPayMethod() {
		return goPayMethod;
	}

	public void setGoPayMethod(String goPayMethod) {
		this.goPayMethod = goPayMethod;
	}

	public java.math.BigDecimal getSearchOrderDlvyNo() {
		return searchOrderDlvyNo;
	}
	public void setSearchOrderDlvyNo(java.math.BigDecimal searchOrderDlvyNo) {
		this.searchOrderDlvyNo = searchOrderDlvyNo;
	}
	public java.math.BigDecimal getOrderSetleNo() {
		return orderSetleNo;
	}
	public void setOrderSetleNo(java.math.BigDecimal orderSetleNo) {
		this.orderSetleNo = orderSetleNo;
	}
	public java.math.BigDecimal getSetlePoint() {
		return setlePoint;
	}
	public void setSetlePoint(java.math.BigDecimal setlePoint) {
		this.setlePoint = setlePoint;
	}
	public java.math.BigDecimal getSetleCardAmount() {
		return setleCardAmount;
	}
	public void setSetleCardAmount(java.math.BigDecimal setleCardAmount) {
		this.setleCardAmount = setleCardAmount;
	}
	public java.math.BigDecimal getSetleTotAmount() {
		return setleTotAmount;
	}
	public void setSetleTotAmount(java.math.BigDecimal setleTotAmount) {
		this.setleTotAmount = setleTotAmount;
	}
	public String getSetleTyCode() {
		return setleTyCode;
	}
	public void setSetleTyCode(String setleTyCode) {
		this.setleTyCode = setleTyCode;
	}
	public String getSetleSttusCode() {
		return setleSttusCode;
	}
	public void setSetleSttusCode(String setleSttusCode) {
		this.setleSttusCode = setleSttusCode;
	}
	public String getSetleResultCode() {
		return setleResultCode;
	}
	public void setSetleResultCode(String setleResultCode) {
		this.setleResultCode = setleResultCode;
	}
	public String getSetleResultTyCode() {
		return setleResultTyCode;
	}
	public void setSetleResultTyCode(String setleResultTyCode) {
		this.setleResultTyCode = setleResultTyCode;
	}
	public String getSetleResultMssage() {
		return setleResultMssage;
	}
	public void setSetleResultMssage(String setleResultMssage) {
		this.setleResultMssage = setleResultMssage;
	}
	public String getEzwSetleConfmNo() {
		return ezwSetleConfmNo;
	}
	public void setEzwSetleConfmNo(String ezwSetleConfmNo) {
		this.ezwSetleConfmNo = ezwSetleConfmNo;
	}
	public String getIniSetleConfmNo() {
		return iniSetleConfmNo;
	}
	public void setIniSetleConfmNo(String iniSetleConfmNo) {
		this.iniSetleConfmNo = iniSetleConfmNo;
	}
	public String getSetlePrarnde() {
		return setlePrarnde;
	}
	public void setSetlePrarnde(String setlePrarnde) {
		this.setlePrarnde = setlePrarnde;
	}
	public java.util.Date getSetlePnttm() {
		return setlePnttm;
	}
	public void setSetlePnttm(java.util.Date setlePnttm) {
		this.setlePnttm = setlePnttm;
	}
	public String getTempKey() {
		return tempKey;
	}
	public void setTempKey(String tempKey) {
		this.tempKey = tempKey;
	}
	public String getUseAt() {
		return useAt;
	}
	public void setUseAt(String useAt) {
		this.useAt = useAt;
	}
	public String getFirOrderInfo() {
		return firOrderInfo;
	}
	public void setFirOrderInfo(String firOrderInfo) {
		this.firOrderInfo = firOrderInfo;
	}
	public String getSeOrderInfo() {
		return SeOrderInfo;
	}
	public void setSeOrderInfo(String seOrderInfo) {
		SeOrderInfo = seOrderInfo;
	}
	public String getSearchOrderNo() {
		return searchOrderNo;
	}
	public void setSearchOrderNo(String searchOrderNo) {
		this.searchOrderNo = searchOrderNo;
	}
	public String getSearchGoodsNm() {
		return searchGoodsNm;
	}
	public void setSearchGoodsNm(String searchGoodsNm) {
		this.searchGoodsNm = searchGoodsNm;
	}
	public String getSearchGoodsCtgryId1() {
		return searchGoodsCtgryId1;
	}
	public void setSearchGoodsCtgryId1(String searchGoodsCtgryId1) {
		this.searchGoodsCtgryId1 = searchGoodsCtgryId1;
	}
	public String getSearchGoodsCtgryId2() {
		return searchGoodsCtgryId2;
	}
	public void setSearchGoodsCtgryId2(String searchGoodsCtgryId2) {
		this.searchGoodsCtgryId2 = searchGoodsCtgryId2;
	}
	public String getSearchGoodsCtgryId3() {
		return searchGoodsCtgryId3;
	}
	public void setSearchGoodsCtgryId3(String searchGoodsCtgryId3) {
		this.searchGoodsCtgryId3 = searchGoodsCtgryId3;
	}
	public String getSearchSetleSttusCode() {
		return searchSetleSttusCode;
	}
	public void setSearchSetleSttusCode(String searchSetleSttusCode) {
		this.searchSetleSttusCode = searchSetleSttusCode;
	}
	public String getSearchDlvySttusCode() {
		return searchDlvySttusCode;
	}
	public void setSearchDlvySttusCode(String searchDlvySttusCode) {
		this.searchDlvySttusCode = searchDlvySttusCode;
	}
	public Boolean getIsSbs() {
		return isSbs;
	}
	public void setIsSbs(Boolean isSbs) {
		this.isSbs = isSbs;
	}
	public java.math.BigDecimal getDscntPc() {
		return dscntPc;
	}
	public void setDscntPc(java.math.BigDecimal dscntPc) {
		this.dscntPc = dscntPc;
	}
	public String getSearchGoodsCtgryId() {
		return searchGoodsCtgryId;
	}
	public void setSearchGoodsCtgryId(String searchGoodsCtgryId) {
		this.searchGoodsCtgryId = searchGoodsCtgryId;
	}
	public String getSearchBgnde() {
		return searchBgnde;
	}
	public void setSearchBgnde(String searchBgnde) {
		this.searchBgnde = searchBgnde;
	}
	public String getSearchEndde() {
		return searchEndde;
	}
	public void setSearchEndde(String searchEndde) {
		this.searchEndde = searchEndde;
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
	public java.math.BigDecimal getSearchOrderGroupNo() {
		return searchOrderGroupNo;
	}
	public void setSearchOrderGroupNo(java.math.BigDecimal searchOrderGroupNo) {
		this.searchOrderGroupNo = searchOrderGroupNo;
	}

}
