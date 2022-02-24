package modoo.module.shop.user.service;

import modoo.module.common.service.CommonDefaultSearchVO;

@SuppressWarnings("serial")
public class DlvyAdresVO extends CommonDefaultSearchVO {
	
	/** 배송자주소고유번호 */
	private java.math.BigDecimal dadresNo;
	/** 배송지명 */
	private String dlvyAdresNm;
	/** 배송지우편번호 */
	private String dlvyZip;
	/** 배송지주소 */
	private String dlvyAdres;
	/** 배송지상세주소 */
	private String dlvyAdresDetail;
	/** 연락처 */
	private String telno;
	private String telno1;
	private String telno2;
	private String telno3;
	
	/** 배송사용자ID */
	private String dlvyUserId;
	/** 받는이명(수령인) */
	private String dlvyUserNm;
	/** 사용시점 */
	private java.util.Date usePnttm;
	/** 기본배송지 여부 */
	private String bassDlvyAt;
	/** 배송지 메세지 */
	private String dlvyMssage;
	/** 최초등록시점 */
	private java.util.Date frstRegistPnttm;
	/** 최초등록자ID */
	private String frstRegisterId;
	/** 최종수정시점 */
	private java.util.Date lastUpdtPnttm;
	/** 최종수정자ID */
	private String lastUpdusrId;
	
	/** 최근배송커맨드 : DEL 최근배송에서 삭제, USE 최근배송 수정 */
	private String recentDlvyCmd;

	/**
	 * 검색 : 배송사용자ID
	 */
	private String searchDlvyUserId;
	
	public java.math.BigDecimal getDadresNo() {
		return dadresNo;
	}
	public void setDadresNo(java.math.BigDecimal dadresNo) {
		this.dadresNo = dadresNo;
	}
	public String getDlvyAdresNm() {
		return dlvyAdresNm;
	}
	public void setDlvyAdresNm(String dlvyAdresNm) {
		this.dlvyAdresNm = dlvyAdresNm;
	}
	public String getDlvyZip() {
		return dlvyZip;
	}
	public void setDlvyZip(String dlvyZip) {
		this.dlvyZip = dlvyZip;
	}
	public String getDlvyAdres() {
		return dlvyAdres;
	}
	public void setDlvyAdres(String dlvyAdres) {
		this.dlvyAdres = dlvyAdres;
	}
	public String getDlvyAdresDetail() {
		return dlvyAdresDetail;
	}
	public void setDlvyAdresDetail(String dlvyAdresDetail) {
		this.dlvyAdresDetail = dlvyAdresDetail;
	}
	public String getTelno() {
		return telno;
	}
	public void setTelno(String telno) {
		this.telno = telno;
	}
	public String getDlvyUserId() {
		return dlvyUserId;
	}
	public void setDlvyUserId(String dlvyUserId) {
		this.dlvyUserId = dlvyUserId;
	}
	public String getDlvyUserNm() {
		return dlvyUserNm;
	}
	public void setDlvyUserNm(String dlvyUserNm) {
		this.dlvyUserNm = dlvyUserNm;
	}
	public java.util.Date getUsePnttm() {
		return usePnttm;
	}
	public void setUsePnttm(java.util.Date usePnttm) {
		this.usePnttm = usePnttm;
	}
	public String getBassDlvyAt() {
		return bassDlvyAt;
	}
	public void setBassDlvyAt(String bassDlvyAt) {
		this.bassDlvyAt = bassDlvyAt;
	}
	public String getDlvyMssage() {
		return dlvyMssage;
	}
	public void setDlvyMssage(String dlvyMssage) {
		this.dlvyMssage = dlvyMssage;
	}
	public java.util.Date getFrstRegistPnttm() {
		return frstRegistPnttm;
	}
	public void setFrstRegistPnttm(java.util.Date frstRegistPnttm) {
		this.frstRegistPnttm = frstRegistPnttm;
	}
	public String getFrstRegisterId() {
		return frstRegisterId;
	}
	public void setFrstRegisterId(String frstRegisterId) {
		this.frstRegisterId = frstRegisterId;
	}
	public java.util.Date getLastUpdtPnttm() {
		return lastUpdtPnttm;
	}
	public void setLastUpdtPnttm(java.util.Date lastUpdtPnttm) {
		this.lastUpdtPnttm = lastUpdtPnttm;
	}
	public String getLastUpdusrId() {
		return lastUpdusrId;
	}
	public void setLastUpdusrId(String lastUpdusrId) {
		this.lastUpdusrId = lastUpdusrId;
	}
	public String getSearchDlvyUserId() {
		return searchDlvyUserId;
	}
	public void setSearchDlvyUserId(String searchDlvyUserId) {
		this.searchDlvyUserId = searchDlvyUserId;
	}
	public String getRecentDlvyCmd() {
		return recentDlvyCmd;
	}
	public void setRecentDlvyCmd(String recentDlvyCmd) {
		this.recentDlvyCmd = recentDlvyCmd;
	}
	public String getTelno1() {
		return telno1;
	}
	public void setTelno1(String telno1) {
		this.telno1 = telno1;
	}
	public String getTelno2() {
		return telno2;
	}
	public void setTelno2(String telno2) {
		this.telno2 = telno2;
	}
	public String getTelno3() {
		return telno3;
	}
	public void setTelno3(String telno3) {
		this.telno3 = telno3;
	}
	
	
}
