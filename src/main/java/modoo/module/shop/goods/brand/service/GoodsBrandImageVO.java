package modoo.module.shop.goods.brand.service;

import modoo.module.common.service.CommonDefaultSearchVO;

@SuppressWarnings("serial")
public class GoodsBrandImageVO extends CommonDefaultSearchVO {

	/** 브랜드이미지고유번호 */
	private java.math.BigDecimal brandImageNo;
	/** 브랜드고유ID */
	private String brandId;
	/** 브랜드이미지구분코드 : DEK 데스크톱, MOB 모바일  REP대표이미지, INT소개이미지*/
	private String brandImageSeCode;
	/** 브랜드이미지 순번 */
	private Integer brandImageSn;
	/** 브랜드이미지경로 */
	private String brandImagePath;
	/** 브랜드이미지썸네일 경로 */
	private String brandImageThumbPath;
	
	public java.math.BigDecimal getBrandImageNo() {
		return brandImageNo;
	}
	public void setBrandImageNo(java.math.BigDecimal brandImageNo) {
		this.brandImageNo = brandImageNo;
	}
	public String getBrandId() {
		return brandId;
	}
	public void setBrandId(String brandId) {
		this.brandId = brandId;
	}
	public String getBrandImageSeCode() {
		return brandImageSeCode;
	}
	public void setBrandImageSeCode(String brandImageSeCode) {
		this.brandImageSeCode = brandImageSeCode;
	}
	public Integer getBrandImageSn() {
		return brandImageSn;
	}
	public void setBrandImageSn(Integer brandImageSn) {
		this.brandImageSn = brandImageSn;
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

}
