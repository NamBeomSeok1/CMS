package modoo.module.thema.service.impl;


import modoo.module.common.service.CommonDefaultSearchVO;

public class GoodsThemaMapngVO extends CommonDefaultSearchVO {

	/** 테마고유번호*/
	private java.math.BigDecimal themaNo;
	/**상품고유번호 */
	private String goodsId;
	/**테마범위구분코드*/
	private String themaRangeSeCode;
	/**테마타입구분코드 */
	private String themaTyCode;
	
	public java.math.BigDecimal getThemaNo() {
		return themaNo;
	}
	public void setThemaNo(java.math.BigDecimal themaNo) {
		this.themaNo = themaNo;
	}
	public String getGoodsId() {
		return goodsId;
	}
	public void setGoodsId(String goodsId) {
		this.goodsId = goodsId;
	}
	public String getThemaRangeSeCode() {
		return themaRangeSeCode;
	}
	public void setThemaRangeSeCode(String themaRangeSeCode) {
		this.themaRangeSeCode = themaRangeSeCode;
	}
	public String getThemaTyCode() {
		return themaTyCode;
	}
	public void setThemaTyCode(String themaTyCode) {
		this.themaTyCode = themaTyCode;
	}
	
	

	
}
