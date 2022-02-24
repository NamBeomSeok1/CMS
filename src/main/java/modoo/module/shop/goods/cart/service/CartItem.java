package modoo.module.shop.goods.cart.service;

public class CartItem {

	/** 장바구니항목고유번호 */
	private java.math.BigDecimal cartItemNo;
	
	/** 장바구니고유번호 */
	private java.math.BigDecimal cartNo;
	
	/** 상품항목고유ID */
	private String gitemId;
	
	/** 상품항목구분코드 */
	private String gitemSeCode;
	
	/** 항목명 */
	private String gitemNm;
	
	/** 항목가격 */
	private java.math.BigDecimal gitemPc;

	public java.math.BigDecimal getCartItemNo() {
		return cartItemNo;
	}

	public void setCartItemNo(java.math.BigDecimal cartItemNo) {
		this.cartItemNo = cartItemNo;
	}

	public java.math.BigDecimal getCartNo() {
		return cartNo;
	}

	public void setCartNo(java.math.BigDecimal cartNo) {
		this.cartNo = cartNo;
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

	public java.math.BigDecimal getGitemPc() {
		return gitemPc;
	}

	public void setGitemPc(java.math.BigDecimal gitemPc) {
		this.gitemPc = gitemPc;
	}

	public String getGitemSeCode() {
		return gitemSeCode;
	}

	public void setGitemSeCode(String gitemSeCode) {
		this.gitemSeCode = gitemSeCode;
	}
	
}
