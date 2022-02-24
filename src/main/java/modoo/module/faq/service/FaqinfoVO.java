package modoo.module.faq.service;

import org.hibernate.validator.constraints.NotEmpty;

import modoo.module.common.service.CommonDefaultSearchVO;

@SuppressWarnings("serial")
public class FaqinfoVO extends CommonDefaultSearchVO {

	/** FAQ고유ID */
	private String faqId;
	/** 사이트고유ID */
	private String siteId;
	/** FAQ분류코드 : SITE 사이트공통, CP : 업체 */
	private String faqClCode;
	/** 질문제목 */
	@NotEmpty
	private String qestnSj;
	/** 질문내용 */
	private String qestnCn;
	/** 답변내용 */
	private String answerCn;
	/** 조회수 */
	private int rdcnt;
	/** 첨부파일ID */
	private String atchFileId;
	/** 최초등록시점 */
	private java.util.Date frstRegistPnttm;
	/** 최초등록자ID */
	private String frstRegisterId;
	/** 최종수정시점 */
	private java.util.Date lastUpdtPnttm;
	/** 최종수정자ID */
	private String lastUpdusrId;
	/** FAQ구분코드 */
	@NotEmpty
	private String faqSeCode;
	/** FAQ구분코드명 */
	private String faqSeCodeNm;

	/*
	 * 검색 : FAQ분류코드
	 */
	private String searchFaqClCode;
	
	/*
	 * 검색: FAQ구분코드
	 */
	private String[] searchFaqSeCode;
	
	/*
	 * 검색 : 그룹코드
	 */
	private String searchGroupCode;
	
	public String getFaqId() {
		return faqId;
	}
	public void setFaqId(String faqId) {
		this.faqId = faqId;
	}
	public String getSiteId() {
		return siteId;
	}
	public void setSiteId(String siteId) {
		this.siteId = siteId;
	}
	public String getQestnSj() {
		return qestnSj;
	}
	public void setQestnSj(String qestnSj) {
		this.qestnSj = qestnSj;
	}
	public String getQestnCn() {
		return qestnCn;
	}
	public void setQestnCn(String qestnCn) {
		this.qestnCn = qestnCn;
	}
	public String getAnswerCn() {
		return answerCn;
	}
	public void setAnswerCn(String answerCn) {
		this.answerCn = answerCn;
	}
	public int getRdcnt() {
		return rdcnt;
	}
	public void setRdcnt(int rdcnt) {
		this.rdcnt = rdcnt;
	}
	public String getAtchFileId() {
		return atchFileId;
	}
	public void setAtchFileId(String atchFileId) {
		this.atchFileId = atchFileId;
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
	public String getFaqSeCode() {
		return faqSeCode;
	}
	public void setFaqSeCode(String faqSeCode) {
		this.faqSeCode = faqSeCode;
	}
	public String[] getSearchFaqSeCode() {
		return searchFaqSeCode;
	}
	public void setSearchFaqSeCode(String[] searchFaqSeCode) {
		this.searchFaqSeCode = searchFaqSeCode;
	}
	public String getFaqClCode() {
		return faqClCode;
	}
	public void setFaqClCode(String faqClCode) {
		this.faqClCode = faqClCode;
	}
	public String getSearchFaqClCode() {
		return searchFaqClCode;
	}
	public void setSearchFaqClCode(String searchFaqClCode) {
		this.searchFaqClCode = searchFaqClCode;
	}
	public String getSearchGroupCode() {
		return searchGroupCode;
	}
	public void setSearchGroupCode(String searchGroupCode) {
		this.searchGroupCode = searchGroupCode;
	}
	public String getFaqSeCodeNm() {
		return faqSeCodeNm;
	}
	public void setFaqSeCodeNm(String faqSeCodeNm) {
		this.faqSeCodeNm = faqSeCodeNm;
	}
	
}
