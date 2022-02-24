package modoo.module.shop.goods.info.service;

import java.math.BigInteger;
import java.util.Date;
import java.util.List;

public class GoodsCouponVO {
	
	/** 상품쿠폰고유번호 */
	private java.math.BigDecimal goodsCouponNo;
	/** 상품고유ID */
	private String goodsId;
	/** 상품이름 */
	private String goodsNm;
	/** 상품이름 */
	private String couponNm;
	/** 쿠폰번호 */
	private String couponNo;
	/** 쿠폰구분코드번호 */
	private String couponKndCode;
	/** 쿠폰구분코드번호 */
	private String couponSttusCode;
	/** 등록시점 */
	private java.util.Date registPnttm;
	/** 쿠폰사용시작시점(YYYYmmdd) */
	private String couponBeginPnttm;
	/** 쿠폰사용종료시점(YYYYmmdd)*/
	private String couponEndPnttm;
	/** 업로드그룹ID : yyyyMMddHHmmssSSS + user_uniq_id */
	private String uploadGroupId;
	/** 등록자고유ID */
	private String registerId;
	/** 주문고유번호 */
	private String orderNo;
	/** 주문고유번호 */
	private Integer orderCo;
	/** 주문자고유ID */
	private String ordrrId;
	/** 쿠폰기간타입 */
	private String couponPdTy;
	/** 쿠폰추가기간 */
	private Integer couponAddPd;
	/** 최종수정날짜 */
	private java.util.Date lastUpdtPnttm;

	/** 판매여부 */
	private String sleAt;
	
	private GoodsCouponAttribute _attributes;
	
	/**
	 * 검색 : 상품쿠폰고유번호 목록
	 */
	private List<java.math.BigDecimal> searchGoodsCouponNoList;

	public GoodsCouponVO() {
		_attributes = new GoodsCouponAttribute();
	}


	public String getCouponNm() {
		return couponNm;
	}

	public void setCouponNm(String couponNm) {
		this.couponNm = couponNm;
	}

	public String getCouponKndCode() {
		return couponKndCode;
	}

	public void setCouponKndCode(String couponKndCode) {
		this.couponKndCode = couponKndCode;
	}

	public String getOrdrrId() {
		return ordrrId;
	}

	public void setOrdrrId(String ordrrId) {
		this.ordrrId = ordrrId;
	}

	public String getCouponPdTy() {
		return couponPdTy;
	}

	public void setCouponPdTy(String couponPdTy) {
		this.couponPdTy = couponPdTy;
	}

	public Date getLastUpdtPnttm() {
		return lastUpdtPnttm;
	}

	public void setLastUpdtPnttm(Date lastUpdtPnttm) {
		this.lastUpdtPnttm = lastUpdtPnttm;
	}

	public String getCouponSttusCode() {
		return couponSttusCode;
	}

	public void setCouponSttusCode(String couponSttusCode) {
		this.couponSttusCode = couponSttusCode;
	}

	public String getCouponBeginPnttm() {
		return couponBeginPnttm;
	}

	public void setCouponBeginPnttm(String couponBeginPnttm) {
		this.couponBeginPnttm = couponBeginPnttm;
	}

	public String getCouponEndPnttm() {
		return couponEndPnttm;
	}

	public void setCouponEndPnttm(String couponEndPnttm) {
		this.couponEndPnttm = couponEndPnttm;
	}

	public Integer getCouponAddPd() {
		return couponAddPd;
	}

	public void setCouponAddPd(Integer couponAddPd) {
		this.couponAddPd = couponAddPd;
	}

	public java.math.BigDecimal getGoodsCouponNo() {
		return goodsCouponNo;
	}
	public void setGoodsCouponNo(java.math.BigDecimal goodsCouponNo) {
		this.goodsCouponNo = goodsCouponNo;
	}
	public String getGoodsId() {
		return goodsId;
	}
	public void setGoodsId(String goodsId) {
		this.goodsId = goodsId;
	}
	public String getCouponNo() {
		return couponNo;
	}
	public void setCouponNo(String couponNo) {
		this.couponNo = couponNo;
	}
	public java.util.Date getRegistPnttm() {
		return registPnttm;
	}
	public void setRegistPnttm(java.util.Date registPnttm) {
		this.registPnttm = registPnttm;
	}
	public String getUploadGroupId() {
		return uploadGroupId;
	}
	public void setUploadGroupId(String uploadGroupId) {
		this.uploadGroupId = uploadGroupId;
	}
	public String getRegisterId() {
		return registerId;
	}
	public void setRegisterId(String registerId) {
		this.registerId = registerId;
	}
	public String getOrderNo() {
		return orderNo;
	}
	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}
	public List<java.math.BigDecimal> getSearchGoodsCouponNoList() {
		return searchGoodsCouponNoList;
	}
	public void setSearchGoodsCouponNoList(List<java.math.BigDecimal> searchGoodsCouponNoList) {
		this.searchGoodsCouponNoList = searchGoodsCouponNoList;
	}
	public GoodsCouponAttribute get_attributes() {
		return _attributes;
	}
	public void set_attributes(GoodsCouponAttribute _attributes) {
		this._attributes = _attributes;
	}
	public String getSleAt() {
		return sleAt;
	}
	public void setSleAt(String sleAt) {
		this.sleAt = sleAt;
	}
	public Integer getOrderCo() {
		return orderCo;
	}
	public void setOrderCo(Integer orderCo) {
		this.orderCo = orderCo;
	}
	public String getGoodsNm() {
		return goodsNm;
	}
	public void setGoodsNm(String goodsNm) {
		this.goodsNm = goodsNm;
	}
	public void setDisabled(boolean flag) {
		if(flag) {
			this._attributes.setDisabled(true);
		}
	}
	
}

class GoodsCouponAttribute {

	private boolean disabled;

	public boolean isDisabled() {
		return disabled;
	}

	public void setDisabled(boolean disabled) {
		this.disabled = disabled;
	}
	
	
}
