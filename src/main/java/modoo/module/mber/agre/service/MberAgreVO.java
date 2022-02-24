package modoo.module.mber.agre.service;

public class MberAgreVO {

	/** 사용자고유ID */
	private String esntlId;
	/** 서비스이용약관동의여부 */
	private String termsCondAt;
	/** 개인정보처리방침동의여부 */
	private String privInfoAt;
	/** 이벤트마케팅수신동의여부 */
	private String eventMarktAt;
	/**i 최초등록시점 */
	private java.util.Date frstRegistPnttm;
	/** 최종수정시점 */
	private java.util.Date lastUpdtPnttm;

	public String getEsntlId() { 
		return esntlId;
	}
	public void setEsntlId(String esntlId) {
		this.esntlId = esntlId;
	}
	public String getTermsCondAt() {
		return termsCondAt;
	}
	public void setTermsCondAt(String termsCondAt) {
		this.termsCondAt = termsCondAt;
	}
	public String getPrivInfoAt() {
		return privInfoAt;
	}
	public void setPrivInfoAt(String privInfoAt) {
		this.privInfoAt = privInfoAt;
	}
	public String getEventMarktAt() {
		return eventMarktAt;
	}
	public void setEventMarktAt(String eventMarktAt) {
		this.eventMarktAt = eventMarktAt;
	}
	public java.util.Date getFrstRegistPnttm() {
		return frstRegistPnttm;
	}
	public void setFrstRegistPnttm(java.util.Date frstRegistPnttm) {
		this.frstRegistPnttm = frstRegistPnttm;
	}
	public java.util.Date getLastUpdtPnttm() {
		return lastUpdtPnttm;
	}
	public void setLastUpdtPnttm(java.util.Date lastUpdtPnttm) {
		this.lastUpdtPnttm = lastUpdtPnttm;
	}
	
	
}
