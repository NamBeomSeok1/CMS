package modoo.module.shop.goods.keyword.service;

import modoo.module.common.service.CommonDefaultSearchVO;

@SuppressWarnings("serial")
public class GoodsKeywordVO extends CommonDefaultSearchVO {

	/** 상품키워드 고유번호 */
	private java.math.BigDecimal goodsKeywordNo;
	/** 상품고유ID */
	private String goodsId;
	/** 키원드명 */
	private String keywordNm;
	
	public java.math.BigDecimal getGoodsKeywordNo() {
		return goodsKeywordNo;
	}
	public void setGoodsKeywordNo(java.math.BigDecimal goodsKeywordNo) {
		this.goodsKeywordNo = goodsKeywordNo;
	}
	public String getGoodsId() {
		return goodsId;
	}
	public void setGoodsId(String goodsId) {
		this.goodsId = goodsId;
	}
	public String getKeywordNm() {
		return keywordNm;
	}
	public void setKeywordNm(String keywordNm) {
		this.keywordNm = keywordNm;
	}
	
}
