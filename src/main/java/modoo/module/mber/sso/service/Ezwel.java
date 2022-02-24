package modoo.module.mber.sso.service;

import egovframework.com.cmm.service.EgovProperties;

public class Ezwel {

	
	/*제휴사(CP) 업체코드 'ksubes' 고정**/
	private String cspCd=EgovProperties.getProperty("SSO.ezwel.cspCd");
	/*고객사 코드**/
	private String clientCd;
	/*사용자 유저키**/
	private String userKey;
	/*사용자 이름**/
	private String userNm;
	/*제휴사 로그인 후 이동 URL**/
	private String goUrl;
	/*해당 고객사 포인트사용여부**/
	private String pointYn;
	/*해당 고객사 적립금사용여부**/
	private String ezmilYn;
	/*특별포인트 사용 여부**/
	private String specialUseYn;
	/*해당고객사 특별포인트 명칭**/
	private String specialPointNm;
	/*해당 고객사 결제수단별 현금영수증 발행여부**/
	private String receiptYn;
	/*해당 고객사 authKey**/
	private String authKey;
	
	public String getAuthKey() {
		return authKey;
	}
	public void setAuthKey(String authKey) {
		this.authKey = authKey;
	}
	public String getCspCd() {
		return cspCd;
	}
	public void setCspCd(String cspCd) {
		this.cspCd = cspCd;
	}
	public String getClientCd() {
		return clientCd;
	}
	public void setClientCd(String clientCd) {
		this.clientCd = clientCd;
	}
	public String getUserKey() {
		return userKey;
	}
	public void setUserKey(String userKey) {
		this.userKey = userKey;
	}
	public String getUserNm() {
		return userNm;
	}
	public void setUserNm(String userNm) {
		this.userNm = userNm;
	}
	public String getGoUrl() {
		return goUrl;
	}
	public void setGoUrl(String goUrl) {
		this.goUrl = goUrl;
	}
	public String getPointYn() {
		return pointYn;
	}
	public void setPointYn(String pointYn) {
		this.pointYn = pointYn;
	}
	public String getEzmilYn() {
		return ezmilYn;
	}
	public void setEzmilYn(String ezmilYn) {
		this.ezmilYn = ezmilYn;
	}
	public String getSpecialUseYn() {
		return specialUseYn;
	}
	public void setSpecialUseYn(String specialUseYn) {
		this.specialUseYn = specialUseYn;
	}
	public String getSpecialPointNm() {
		return specialPointNm;
	}
	public void setSpecialPointNm(String specialPointNm) {
		this.specialPointNm = specialPointNm;
	}
	public String getReceiptYn() {
		return receiptYn;
	}
	public void setReceiptYn(String receiptYn) {
		this.receiptYn = receiptYn;
	}
	@Override
	public String toString() {
		return "Ezwel [cspCd=" + cspCd + ", clientCd=" + clientCd + ", userKey=" + userKey + ", userNm=" + userNm
				+ ", goUrl=" + goUrl + ", pointYn=" + pointYn + ", ezmilYn=" + ezmilYn + ", specialUseYn="
				+ specialUseYn + ", specialPointNm=" + specialPointNm + ", receiptYn=" + receiptYn + "]";
	}
}
