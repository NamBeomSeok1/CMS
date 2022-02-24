package modoo.module.shop.goods.image.service;

import modoo.module.common.service.CommonDefaultSearchVO;

@SuppressWarnings("serial")
public class GoodsImageVO extends CommonDefaultSearchVO {

	/** 상품이미지고유번호 */
	private java.math.BigDecimal goodsImageNo;
	/** 상품고유ID */
	private String goodsId;
	/** 상품이미지구분코드 : GDC (상품설명), DEK (데스크톱), MOB (모바일), BAN (배너), GNR (일반상품) */
	private String goodsImageSeCode;
	/** 상품이미지순번 */
	private Integer goodsImageSn;
	/** 원본상품이미지경로 */
	private String goodsImagePath;
	/** 상품이미지썸네일경로 */
	private String goodsImageThumbPath;
	/** 대_상품이미지경로 */
	private String goodsLrgeImagePath;
	/** 중_상품이미지경로 */
	private String goodsMiddlImagePath;
	/** 소_상품이미지경로 */
	private String goodsSmallImagePath;

	public java.math.BigDecimal getGoodsImageNo() {
		return goodsImageNo;
	}
	public void setGoodsImageNo(java.math.BigDecimal goodsImageNo) {
		this.goodsImageNo = goodsImageNo;
	}
	public String getGoodsId() {
		return goodsId;
	}
	public void setGoodsId(String goodsId) {
		this.goodsId = goodsId;
	}
	public String getGoodsImageSeCode() {
		return goodsImageSeCode;
	}
	public void setGoodsImageSeCode(String goodsImageSeCode) {
		this.goodsImageSeCode = goodsImageSeCode;
	}
	public Integer getGoodsImageSn() {
		return goodsImageSn;
	}
	public void setGoodsImageSn(Integer goodsImageSn) {
		this.goodsImageSn = goodsImageSn;
	}
	public String getGoodsImagePath() {
		return goodsImagePath;
	}
	public void setGoodsImagePath(String goodsImagePath) {
		this.goodsImagePath = goodsImagePath;
	}
	public String getGoodsImageThumbPath() {
		return goodsImageThumbPath;
	}
	public void setGoodsImageThumbPath(String goodsImageThumbPath) {
		this.goodsImageThumbPath = goodsImageThumbPath;
	}
	public String getGoodsLrgeImagePath() {
		return goodsLrgeImagePath;
	}
	public void setGoodsLrgeImagePath(String goodsLrgeImagePath) {
		this.goodsLrgeImagePath = goodsLrgeImagePath;
	}
	public String getGoodsMiddlImagePath() {
		return goodsMiddlImagePath;
	}
	public void setGoodsMiddlImagePath(String goodsMiddlImagePath) {
		this.goodsMiddlImagePath = goodsMiddlImagePath;
	}
	public String getGoodsSmallImagePath() {
		return goodsSmallImagePath;
	}
	public void setGoodsSmallImagePath(String goodsSmallImagePath) {
		this.goodsSmallImagePath = goodsSmallImagePath;
	}
	
}
