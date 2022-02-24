package modoo.module.shop.cmpny.service;

import modoo.module.common.service.CommonDefaultSearchVO;

@SuppressWarnings("serial")
public class CmpnyInqryVO extends CommonDefaultSearchVO{

	/* 문의 id*/
	private String inqryId;
	/*업체 담당자 고유 id*/
	private String esntlId;
	/*파일고유id*/
	private String atchFileId;
	/*업체 명*/
	private String cmpnyNm;
	/*담당자 이름*/
	private String cmpnyCharger;
	/*업체 소개*/
	private String cmpnyIntrcn;
	/*상품 소개*/
	private String goodsIntrcn;
	/*담당자 전화번호*/
	private String telno;
	private String telno1;
	private String telno2;
	private String telno3;
	/*이메일*/
	private String cmpnyEmail;
	private String frstRegistPnttm;
	
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
	public String getCmpnyEmail() {
		return cmpnyEmail;
	}
	public void setCmpnyEmail(String cmpnyEmail) {
		this.cmpnyEmail = cmpnyEmail;
	}
	public String getEmail1() {
		return email1;
	}
	public void setEmail1(String email1) {
		this.email1 = email1;
	}
	public String getEmail2() {
		return email2;
	}
	public void setEmail2(String email2) {
		this.email2 = email2;
	}
	private String email1;
	private String email2;
	public String getInqryId() {
		return inqryId;
	}
	public void setInqryId(String inqryId) {
		this.inqryId = inqryId;
	}
	public String getEsntlId() {
		return esntlId;
	}
	public void setEsntlId(String esntlId) {
		this.esntlId = esntlId;
	}
	public String getAtchFileId() {
		return atchFileId;
	}
	public void setAtchFileId(String atchFileId) {
		this.atchFileId = atchFileId;
	}
	public String getCmpnyNm() {
		return cmpnyNm;
	}
	public void setCmpnyNm(String cmpnyNm) {
		this.cmpnyNm = cmpnyNm;
	}
	public String getCmpnyCharger() {
		return cmpnyCharger;
	}
	public void setCmpnyCharger(String cmpnyCharger) {
		this.cmpnyCharger = cmpnyCharger;
	}
	public String getCmpnyIntrcn() {
		return cmpnyIntrcn;
	}
	public void setCmpnyIntrcn(String cmpnyIntrcn) {
		this.cmpnyIntrcn = cmpnyIntrcn;
	}
	public String getGoodsIntrcn() {
		return goodsIntrcn;
	}
	public void setGoodsIntrcn(String goodsIntrcn) {
		this.goodsIntrcn = goodsIntrcn;
	}
	public String getTelno() {
		return telno;
	}
	public void setTelno(String telno) {
		this.telno = telno;
	}
	public String getFrstRegistPnttm() {
		return frstRegistPnttm;
	}
	public void setFrstRegistPnttm(String frstRegistPnttm) {
		this.frstRegistPnttm = frstRegistPnttm;
	}
	
}
