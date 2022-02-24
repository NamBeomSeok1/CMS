package modoo.module.shop.goods.cart.service;

import java.util.List;

import modoo.module.common.service.CommonDefaultSearchVO;

@SuppressWarnings("serial")
public class CartVO extends CommonDefaultSearchVO {

	/** 장바구니고유번호 */
	private java.math.BigDecimal cartNo;
	/** 상품고유ID */
	private String goodsId;
	/** 주문자ID */
	private String ordrrId;
	/** 구독주기 구분코드 */
	private String sbscrptCycleSeCode;
	/** 구독 주 주기 */
	private Integer sbscrptWeekCycle;
	/** 구독배송요일 */
	private Integer sbscrptDlvyWd;
	/** 구독이용 주 */
	private Integer sbscrptUseWeek;
	/** 구독이용 월 주기 */
	private Integer sbscrptMtCycle;
	/** 구독이용월 */
	private Integer sbscrptUseMt;
	/** 구독배송일 */
	private Integer sbscrptDlvyDay;
	/** 주문수량 */
	private Integer orderCo;
	/** 장바구니담기여부 */
	private String cartAddAt = "N";
	/** 등록시점 */
	private java.util.Date registPnttm;
	/** 일반/구독상품 여부 */
	private String goodsKndCode;
	/** 1회체험 여부 */
	private String exprnUseAt;
	/** 1회체험 복수할인여부 */
	private String compnoDscntUseAt;
	
	/** 1회체험 가격 */
	private java.math.BigDecimal exprnPc;
	
	/** 장바구니항목 목록 */
	private List<CartItem> cartItemList;

	/** 장바구니항목 id배열*/
	private List<String> cartItemIdList;
	
	public List<String> getCartItemIdList() {
		return cartItemIdList;
	}
	public void setCartItemIdList(List<String> cartItemIdList) {
		this.cartItemIdList = cartItemIdList;
	}
	/**
	 * 검색 : 주문자ID
	 */
	private String searchOrdrrId;
	
	/**
	 * 검색 : 장바구니고유번호 목록
	 */
	private List<Integer> searchCartNoList;
	
	public java.math.BigDecimal getCartNo() {
		return cartNo;
	}
	public void setCartNo(java.math.BigDecimal cartNo) {
		this.cartNo = cartNo;
	}
	public String getGoodsId() {
		return goodsId;
	}
	public void setGoodsId(String goodsId) {
		this.goodsId = goodsId;
	}
	public String getOrdrrId() {
		return ordrrId;
	}
	public void setOrdrrId(String ordrrId) {
		this.ordrrId = ordrrId;
	}
	public String getSbscrptCycleSeCode() {
		return sbscrptCycleSeCode;
	}
	public void setSbscrptCycleSeCode(String sbscrptCycleSeCode) {
		this.sbscrptCycleSeCode = sbscrptCycleSeCode;
	}
	public Integer getSbscrptWeekCycle() {
		return sbscrptWeekCycle;
	}
	public void setSbscrptWeekCycle(Integer sbscrptWeekCycle) {
		this.sbscrptWeekCycle = sbscrptWeekCycle;
	}
	public Integer getSbscrptDlvyWd() {
		return sbscrptDlvyWd;
	}
	public void setSbscrptDlvyWd(Integer sbscrptDlvyWd) {
		this.sbscrptDlvyWd = sbscrptDlvyWd;
	}
	public Integer getSbscrptUseWeek() {
		return sbscrptUseWeek;
	}
	public void setSbscrptUseWeek(Integer sbscrptUseWeek) {
		this.sbscrptUseWeek = sbscrptUseWeek;
	}
	public Integer getSbscrptMtCycle() {
		return sbscrptMtCycle;
	}
	public void setSbscrptMtCycle(Integer sbscrptMtCycle) {
		this.sbscrptMtCycle = sbscrptMtCycle;
	}
	public Integer getSbscrptUseMt() {
		return sbscrptUseMt;
	}
	public void setSbscrptUseMt(Integer sbscrptUseMt) {
		this.sbscrptUseMt = sbscrptUseMt;
	}
	public Integer getSbscrptDlvyDay() {
		return sbscrptDlvyDay;
	}
	public void setSbscrptDlvyDay(Integer sbscrptDlvyDay) {
		this.sbscrptDlvyDay = sbscrptDlvyDay;
	}
	public Integer getOrderCo() {
		return orderCo;
	}
	public void setOrderCo(Integer orderCo) {
		this.orderCo = orderCo;
	}
	public String getCartAddAt() {
		return cartAddAt;
	}
	public void setCartAddAt(String cartAddAt) {
		this.cartAddAt = cartAddAt;
	}
	public java.util.Date getRegistPnttm() {
		return registPnttm;
	}
	public void setRegistPnttm(java.util.Date registPnttm) {
		this.registPnttm = registPnttm;
	}
	public String getSearchOrdrrId() {
		return searchOrdrrId;
	}
	public void setSearchOrdrrId(String searchOrdrrId) {
		this.searchOrdrrId = searchOrdrrId;
	}
	public List<Integer> getSearchCartNoList() {
		return searchCartNoList;
	}
	public void setSearchCartNoList(List<Integer> searchCartNoList) {
		this.searchCartNoList = searchCartNoList;
	}
	public List<CartItem> getCartItemList() {
		return cartItemList;
	}
	public void setCartItemList(List<CartItem> cartItemList) {
		this.cartItemList = cartItemList;
	}
	public String getGoodsKndCode() {
		return goodsKndCode;
	}
	public void setGoodsKndCode(String goodsKndCode) {
		this.goodsKndCode = goodsKndCode;
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

}
