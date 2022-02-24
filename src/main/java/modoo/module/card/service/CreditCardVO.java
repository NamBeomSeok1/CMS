package modoo.module.card.service;

import javax.validation.Valid;

import org.hibernate.validator.constraints.NotEmpty;

import modoo.module.common.service.CommonDefaultSearchVO;

@SuppressWarnings("serial")
public class CreditCardVO extends CommonDefaultSearchVO{

	/*카드 고유 번호*/
	private String cardId;

	/*사용자 고유 아이디*/
	private String esntlId;
	
	/*카드 이름*/
	private String cardNm;
	
	//@NotEmpty
	/*카드 패스워드 앞 두자리(암호화)*/
	private String cardPassword;

	//@NotEmpty
	/*카드 사용 기간(암호화)*/
	private String cardUsgpd;

	//@NotEmpty
	/*카드 번호(암호화)*/
	private String cardNo;
	
	//@NotEmpty
	/*생년 월일(암호화)*/
	private String brthdy;

	/*기본카드사용여부(Y,N)*/
	private String bassUseAt ;

	/*카드사용여부(Y,N)*/
	private String useAt ;

	/*카드번호 마지막4자리*/
	private String lastCardNo;

	/*인증 비밀번호*/
	private String password;
	
	/*등록날짜*/
	private java.util.Date registPnttm;

	/*수정날짜*/
	private java.util.Date lastUpdtPnttm;

	public String getCardId() {
		return cardId;
	}

	public void setCardId(String cardId) {
		this.cardId = cardId;
	}

	public String getEsntlId() {
		return esntlId;
	}

	public void setEsntlId(String esntlId) {
		this.esntlId = esntlId;
	}

	public String getCardNm() {
		return cardNm;
	}

	public void setCardNm(String cardNm) {
		this.cardNm = cardNm;
	}

	public String getCardPassword() {
		return cardPassword;
	}

	public void setCardPassword(String cardPassword) {
		this.cardPassword = cardPassword;
	}

	public String getCardUsgpd() {
		return cardUsgpd;
	}

	public void setCardUsgpd(String cardUsgpd) {
		this.cardUsgpd = cardUsgpd;
	}

	public String getCardNo() {
		return cardNo;
	}

	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}

	public String getBrthdy() {
		return brthdy;
	}

	public void setBrthdy(String brthdy) {
		this.brthdy = brthdy;
	}

	public String getBassUseAt() {
		return bassUseAt;
	}

	public void setBassUseAt(String bassUseAt) {
		this.bassUseAt = bassUseAt;
	}

	public String getUseAt() {
		return useAt;
	}

	public void setUseAt(String useAt) {
		this.useAt = useAt;
	}

	public java.util.Date getRegistPnttm() {
		return registPnttm;
	}

	public void setRegistPnttm(java.util.Date registPnttm) {
		this.registPnttm = registPnttm;
	}

	public java.util.Date getLastUpdtPnttm() {
		return lastUpdtPnttm;
	}

	public void setLastUpdtPnttm(java.util.Date lastUpdtPnttm) {
		this.lastUpdtPnttm = lastUpdtPnttm;
	}
	public String getLastCardNo() {
		return lastCardNo;
	}

	public void setLastCardNo(String lastCardNo) {
		this.lastCardNo = lastCardNo;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}


}