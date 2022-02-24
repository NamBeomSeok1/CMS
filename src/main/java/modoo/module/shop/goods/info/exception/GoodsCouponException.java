package modoo.module.shop.goods.info.exception;

@SuppressWarnings("serial")
public class GoodsCouponException extends Exception {

	public GoodsCouponException(String msg) {
		super(msg);
	}
	
	public GoodsCouponException(Exception e) {
		super(e);
	}
}
