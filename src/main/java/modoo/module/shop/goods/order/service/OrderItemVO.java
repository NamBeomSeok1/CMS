package modoo.module.shop.goods.order.service;

public class OrderItemVO {
	
	/** 주문항목고유번호 */
	private java.math.BigDecimal oitemNo;
	/** 주문고유번호 */
	//private java.math.BigDecimal orderNo;
	private String orderNo;
	/** 상품항목고유ID */
	private String gitemId;
	/** 항목명 */
	private String gitemNm;
	/** 항목구분 */
	private String gistemSeCode;
	/** 항목가격 */
	private java.math.BigDecimal gitemPc;
	/** 항목답변 */
	private String gitemAnswer;
	/**항목구분코드명 */
	private String gistemSeCodeNm;
	
	/** 장바구니고유번호 */
	private java.math.BigDecimal cartNo;
	
	public java.math.BigDecimal getOitemNo() {
		return oitemNo;
	}
	public void setOitemNo(java.math.BigDecimal oitemNo) {
		this.oitemNo = oitemNo;
	}
	public String getOrderNo() {
		return orderNo;
	}
	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}
	public String getGitemId() {
		return gitemId;
	}
	public void setGitemId(String gitemId) {
		this.gitemId = gitemId;
	}
	public String getGitemNm() {
		return gitemNm;
	}
	public void setGitemNm(String gitemNm) {
		this.gitemNm = gitemNm;
	}
	public String getGistemSeCode() {
		return gistemSeCode;
	}
	public void setGistemSeCode(String gistemSeCode) {
		this.gistemSeCode = gistemSeCode;
	}
	public java.math.BigDecimal getGitemPc() {
		return gitemPc;
	}
	public void setGitemPc(java.math.BigDecimal gitemPc) {
		this.gitemPc = gitemPc;
	}
	public String getGitemAnswer() {
		return gitemAnswer;
	}
	public void setGitemAnswer(String gitemAnswer) {
		this.gitemAnswer = gitemAnswer;
	}
	public java.math.BigDecimal getCartNo() {
		return cartNo;
	}
	public void setCartNo(java.math.BigDecimal cartNo) {
		this.cartNo = cartNo;
	}
	public String getGistemSeCodeNm() {
		return gistemSeCodeNm;
	}
	public void setGistemSeCodeNm(String gistemSeCodeNm) {
		this.gistemSeCodeNm = gistemSeCodeNm;
	}

}
