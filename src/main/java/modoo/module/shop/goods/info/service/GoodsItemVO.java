package modoo.module.shop.goods.info.service;

public class GoodsItemVO {

	/** 항목고유ID */
	private String gitemId;
	/** 상품고유ID */
	private String goodsId;
	/** 항목구분코드 : D 기본, A 추가옵션, F : 첫구독옵션 */
	private String gitemSeCode;
	/** 항목순번 */
	private Integer gitemSn;
	/** 항목명 */
	private String gitemNm;
	/** 항목개수 */
	private Integer gitemCo;
	/** 항목가격 */
	private java.math.BigDecimal gitemPc;
	/** 항목상태코드 : T 재고있음, F 재고없음, X 비활성 */
	private String gitemSttusCode;
	/** 항목구분 코드명 */
	private String gitemSeCodeNm;
	
	public String getGitemId() {
		return gitemId;
	}
	public void setGitemId(String gitemId) {
		this.gitemId = gitemId;
	}
	public String getGoodsId() {
		return goodsId;
	}
	public void setGoodsId(String goodsId) {
		this.goodsId = goodsId;
	}
	public Integer getGitemSn() {
		return gitemSn;
	}
	public void setGitemSn(Integer gitemSn) {
		this.gitemSn = gitemSn;
	}
	public String getGitemNm() {
		return gitemNm;
	}
	public void setGitemNm(String gitemNm) {
		this.gitemNm = gitemNm;
	}
	public Integer getGitemCo() {
		return gitemCo;
	}
	public void setGitemCo(Integer gitemCo) {
		this.gitemCo = gitemCo;
	}
	public String getGitemSttusCode() {
		return gitemSttusCode;
	}
	@Override
	public String toString() {
		return "GoodsItemVO [gitemId=" + gitemId + ", goodsId=" + goodsId + ", gitemSeCode=" + gitemSeCode
				+ ", gitemSn=" + gitemSn + ", gitemNm=" + gitemNm + ", gitemCo=" + gitemCo + ", gitemPc=" + gitemPc
				+ ", gitemSttusCode=" + gitemSttusCode + ", gitemSeCodeNm=" + gitemSeCodeNm + "]";
	}
	public void setGitemSttusCode(String gitemSttusCode) {
		this.gitemSttusCode = gitemSttusCode;
	}
	public String getGitemSeCode() {
		return gitemSeCode;
	}
	public void setGitemSeCode(String gitemSeCode) {
		this.gitemSeCode = gitemSeCode;
	}
	public java.math.BigDecimal getGitemPc() {
		return gitemPc;
	}
	public void setGitemPc(java.math.BigDecimal gitemPc) {
		this.gitemPc = gitemPc;
	}
	public String getGitemSeCodeNm() {
		return gitemSeCodeNm;
	}
	public void setGitemSeCodeNm(String gitemSeCodeNm) {
		this.gitemSeCodeNm = gitemSeCodeNm;
	}
}
