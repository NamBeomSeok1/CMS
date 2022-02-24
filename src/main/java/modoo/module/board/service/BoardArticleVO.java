package modoo.module.board.service;

import org.hibernate.validator.constraints.NotEmpty;

import modoo.module.common.service.CommonDefaultSearchVO;

@SuppressWarnings("serial")
public class BoardArticleVO extends CommonDefaultSearchVO {

	/** 게시물ID */
	private Long nttId;
	/** 게시판ID */
	private String bbsId;
	/** 카테고리고유ID */
	private String ctgryId;
	/** 게시물번호 */
	private Long nttNo;
	/** 게시물제목 */
	@NotEmpty
	private String nttSj;
	/** 게시물내용 */
	private String nttCn;
	/** 공지여부 */
	private String noticeAt = "N";
	/** 비밀글여부 */
	private String secretAt = "N";
	/** 답장여부 */
	private String replyAt = "N";
	/** 부모글번호 */
	private Long parntscttNo = 0L;
	/** 답장위치 */
	private Integer replyLc = 0;
	/** 정렬순서 */
	private Long sortOrdr = 0L;
	/** 조회수 */
	private Integer inqireCo = 0;
	/** 게시시작일 */
	private String ntceBgnde;
	/** 게시종료일 */
	private String ntceEndde;
	/** 게시자ID */
	private String ntcrId = "";
	/** 게시자명 */
	private String ntcrNm = "";
	/** 비밀번호 */
	private String password = "";
	/** 첨부파일고유ID */
	private String atchFileId = "";
	/** 최초등록시점 */
	private java.util.Date frstRegistPnttm;
	/** 최초등록자ID */
	private String frstRegisterId;
	/** 최종수정시점 */
	private java.util.Date lastUpdtPnttm;
	/** 최종수정자ID */
	private String lastUpdusrId;
	/** 사용여부 */
	private String useAt;
	
	/** 게시글여부 */
	private String bbscttAt;
	
	/*
	 * 검색 : bbsId
	 */
	private String searchBbsId;
	
	/**
	 * 검색 : 게시기간여부
	 */
	private String searchUsgpdAt;
	
	public Long getNttId() {
		return nttId;
	}
	public void setNttId(Long nttId) {
		this.nttId = nttId;
	}
	public String getBbsId() {
		return bbsId;
	}
	public void setBbsId(String bbsId) {
		this.bbsId = bbsId;
	}
	public String getCtgryId() {
		return ctgryId;
	}
	public void setCtgryId(String ctgryId) {
		this.ctgryId = ctgryId;
	}
	public Long getNttNo() {
		return nttNo;
	}
	public void setNttNo(Long nttNo) {
		this.nttNo = nttNo;
	}
	public String getNttSj() {
		return nttSj;
	}
	public void setNttSj(String nttSj) {
		this.nttSj = nttSj;
	}
	public String getNttCn() {
		return nttCn;
	}
	public void setNttCn(String nttCn) {
		this.nttCn = nttCn;
	}
	public String getNoticeAt() {
		return noticeAt;
	}
	public void setNoticeAt(String noticeAt) {
		this.noticeAt = noticeAt;
	}
	public String getSecretAt() {
		return secretAt;
	}
	public void setSecretAt(String secretAt) {
		this.secretAt = secretAt;
	}
	public String getReplyAt() {
		return replyAt;
	}
	public void setReplyAt(String replyAt) {
		this.replyAt = replyAt;
	}
	public Long getParntscttNo() {
		return parntscttNo;
	}
	public void setParntscttNo(Long parntscttNo) {
		this.parntscttNo = parntscttNo;
	}
	public Integer getReplyLc() {
		return replyLc;
	}
	public void setReplyLc(Integer replyLc) {
		this.replyLc = replyLc;
	}
	public Long getSortOrdr() {
		return sortOrdr;
	}
	public void setSortOrdr(Long sortOrdr) {
		this.sortOrdr = sortOrdr;
	}
	public Integer getInqireCo() {
		return inqireCo;
	}
	public void setInqireCo(Integer inqireCo) {
		this.inqireCo = inqireCo;
	}
	public String getNtceBgnde() {
		return ntceBgnde;
	}
	public void setNtceBgnde(String ntceBgnde) {
		this.ntceBgnde = ntceBgnde;
	}
	public String getNtceEndde() {
		return ntceEndde;
	}
	public void setNtceEndde(String ntceEndde) {
		this.ntceEndde = ntceEndde;
	}
	public String getNtcrId() {
		return ntcrId;
	}
	public void setNtcrId(String ntcrId) {
		this.ntcrId = ntcrId;
	}
	public String getNtcrNm() {
		return ntcrNm;
	}
	public void setNtcrNm(String ntcrNm) {
		this.ntcrNm = ntcrNm;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
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
	public String getUseAt() {
		return useAt;
	}
	public void setUseAt(String useAt) {
		this.useAt = useAt;
	}
	public String getSearchBbsId() {
		return searchBbsId;
	}
	public void setSearchBbsId(String searchBbsId) {
		this.searchBbsId = searchBbsId;
	}
	public String getSearchUsgpdAt() {
		return searchUsgpdAt;
	}
	public void setSearchUsgpdAt(String searchUsgpdAt) {
		this.searchUsgpdAt = searchUsgpdAt;
	}
	public String getBbscttAt() {
		return bbscttAt;
	}
	public void setBbscttAt(String bbscttAt) {
		this.bbscttAt = bbscttAt;
	}
	
	
}
