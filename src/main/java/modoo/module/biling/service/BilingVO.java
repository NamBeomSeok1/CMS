package modoo.module.biling.service;

import egovframework.com.cmm.service.EgovProperties;

@SuppressWarnings("serial")
public class BilingVO{
	
	public BilingVO() {
	}
	/*결제 타입 (빌링키 발급 "Auth",빌링 승인 "Biling", 비인증 결제 "Pay" */
	private String type;

	/*payMethod "Card"고정*/
	private String payMethod="Card";
	
	/*결제 시간 YYYYMMDDhhmmss*/
	private String timeStamp;
	
	/*가맹점 요청 서버IP*/
	private String clientIp="1.234.209.212";
	
	/*상점에 발급된 가맹점 ID(지금은 테스트ID)*/
	private String mid=EgovProperties.getProperty("INICIS.subscription.mid");
	
	/*가맹점 URL*/
	private String url="https://dev.modoo.ai";
	
	/*가맹점 주문번호*/
	private String moid;
	
	/*상품명*/
	private String goodName;
	
	/*구매자 명*/
	private String buyerName;
	
	/*구매자 이메일*/
	private String buyerEmail;
	
	/*구매자 연락처*/
	private String buyerTel;
	
	/*결제 금액*/
	private String price;
	
	/*카드 번호(암호화)*/
	private String cardNumber;
	
	/*카드 유효기간(암호화)*/
	private String cardExpire;
	
	/*생년 월일(암호화)*/
	private String regNo;
	
	/*카드 비밀번호 2자리(암호화)*/
	private String cardPw;
	
	/*hash값(SHA-512)*/
	private String hashData;
	
	/*-----빌링 승인에 추가 필요한 parameter-----*/
	
	/*통화코드 (WON / USD)*/
	private String currency;
	
	/*승인요청할 빌링키값*/
	private String billkey;
	
	/*본인인증 여부 "00" 고정*/
	private String authentification;
	
	/*할부 기간(일시불 : 00 / 그 외 : 02, 03 ...)-정기결제에는 필요 없을 거 같음*/
	private String cardQuota;
	
	/*무이자 구분-정기결제에는 필요 없을 거 같음*/
	private String quotaInterest;
	
	/*-----신용카드(비인증)결제에 추가 필요한 parameter-----*/

	/*카드 포인트 사용 유무*/
	private String cardPoint;

	/*언어설정(eng)*/
	private String language;
	
	/* 취소요청 tid */
	private String tid;
	
	/* 부분취소 후 남은 금액 */
	private String confirmPrice;

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getPayMethod() {
		return payMethod;
	}

	public void setPayMethod(String payMethod) {
		this.payMethod = payMethod;
	}

	public String getTimeStamp() {
		return timeStamp;
	}

	public void setTimeStamp(String timeStamp) {
		this.timeStamp = timeStamp;
	}

	public String getClientIp() {
		return clientIp;
	}

	public void setClientIp(String clientIp) {
		this.clientIp = clientIp;
	}

	public String getMid() {
		return mid;
	}

	public void setMid(String mid) {
		this.mid = mid;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getMoid() {
		return moid;
	}

	public void setMoid(String moid) {
		this.moid = moid;
	}

	public String getGoodName() {
		return goodName;
	}

	public void setGoodName(String goodName) {
		this.goodName = goodName;
	}

	public String getBuyerName() {
		return buyerName;
	}

	public void setBuyerName(String buyerName) {
		this.buyerName = buyerName;
	}

	public String getBuyerEmail() {
		return buyerEmail;
	}

	public void setBuyerEmail(String buyerEmail) {
		this.buyerEmail = buyerEmail;
	}

	public String getBuyerTel() {
		return buyerTel;
	}

	public void setBuyerTel(String buyerTel) {
		this.buyerTel = buyerTel;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public String getCardNumber() {
		return cardNumber;
	}

	public void setCardNumber(String cardNumber) {
		this.cardNumber = cardNumber;
	}

	public String getCardExpire() {
		return cardExpire;
	}

	public void setCardExpire(String cardExpire) {
		this.cardExpire = cardExpire;
	}

	public String getRegNo() {
		return regNo;
	}

	public void setRegNo(String regNo) {
		this.regNo = regNo;
	}

	public String getCardPw() {
		return cardPw;
	}

	public void setCardPw(String cardPw) {
		this.cardPw = cardPw;
	}

	public String getHashData() {
		return hashData;
	}

	public void setHashData(String hashData) {
		this.hashData = hashData;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public String getBillkey() {
		return billkey;
	}

	public void setBillkey(String billkey) {
		this.billkey = billkey;
	}

	public String getAuthentification() {
		return authentification;
	}

	public void setAuthentification(String authentification) {
		this.authentification = authentification;
	}

	public String getCardQuota() {
		return cardQuota;
	}

	public void setCardQuota(String cardQuota) {
		this.cardQuota = cardQuota;
	}

	public String getQuotaInterest() {
		return quotaInterest;
	}

	public void setQuotaInterest(String quotaInterest) {
		this.quotaInterest = quotaInterest;
	}

	public String getCardPoint() {
		return cardPoint;
	}

	public void setCardPoint(String cardPoint) {
		this.cardPoint = cardPoint;
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public String getTid() {
		return tid;
	}

	public void setTid(String tid) {
		this.tid = tid;
	}

	public String getConfirmPrice() {
		return confirmPrice;
	}

	public void setConfirmPrice(String confirmPrice) {
		this.confirmPrice = confirmPrice;
	}

}