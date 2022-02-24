package modoo.module.shop.goods.recomend.service;

import modoo.module.shop.goods.info.service.GoodsVO;

@SuppressWarnings("serial")
public class GoodsRecomendVO extends GoodsVO {

	/** 상품관련추천상품고유번호 */
	private java.math.BigDecimal goodsRecomendNo;
	/** 상품고유ID */
	private String goodsId;
	/** 추천상품고유ID */
	private String recomendGoodsId;
	/** 추천상품순번 */
	private Integer recomendGoodsSn;
	
	
	public java.math.BigDecimal getGoodsRecomendNo() {
		return goodsRecomendNo;
	}
	public void setGoodsRecomendNo(java.math.BigDecimal goodsRecomendNo) {
		this.goodsRecomendNo = goodsRecomendNo;
	}
	public String getGoodsId() {
		return goodsId;
	}
	public void setGoodsId(String goodsId) {
		this.goodsId = goodsId;
	}
	public String getRecomendGoodsId() {
		return recomendGoodsId;
	}
	public void setRecomendGoodsId(String recomendGoodsId) {
		this.recomendGoodsId = recomendGoodsId;
	}
	public Integer getRecomendGoodsSn() {
		return recomendGoodsSn;
	}
	public void setRecomendGoodsSn(Integer recomendGoodsSn) {
		this.recomendGoodsSn = recomendGoodsSn;
	}

	
}
