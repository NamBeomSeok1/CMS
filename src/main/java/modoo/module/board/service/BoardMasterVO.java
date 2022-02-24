package modoo.module.board.service;

import org.hibernate.validator.constraints.NotEmpty;

import modoo.module.common.service.CommonDefaultSearchVO;

@SuppressWarnings("serial")
public class BoardMasterVO extends CommonDefaultSearchVO {

	/** 게시판고유ID */
	private String bbsId;
	/** 사이트고유ID */
	private String siteId;
	/** 게시판명 */
	@NotEmpty
	private String bbsNm;
	/** 카테고리마스터ID */
	private String ctgryMasterId;
	/** 게시판 템플릿코드 */
	private String bbsTmplatCode;
	/** 공지여부 */
	private String noticeAt;
	/** 비밀여부 */
	private String secretAt;
	/** 익명여부 */
	private String annymtyAt;
	/** 사용기간여부 */
	private String usgpdAt;
	/** 담장여부 */
	private String replyAt;
	/** 댓글여부 */
	private String commentAt;
	/** 파일첨부여부 */
	private String fileAtachAt;
	/** 첨부파일개수 */
	private Integer atchFileCo;
	/** 첨부파일크기 */
	private Integer atchFileSize;
	/** 목록권한코드 */
	private String listAuthorCode;
	/** 읽기권한코드 */
	private String redingAuthorCode;
	/** 쓰기권한코드 */
	private String writngAuthorCode;
	/** 수정권한코드 */
	private String updtAuthorCode;
	/** 삭제권한코드 */
	private String deleteAuthorCode;
	/** 답장권한코드 */
	private String replyAuthorCode;
	/** 다운로드권한코드 */
	private String downloadAuthorCode;
	/** 댓글권한코드 */
	private String commentAuthorCode;
	/** 댓글정렬순서타입 */
	private String commentSortOrderType;
	/** 목록갯수 */
	private Integer listCo = 10;
	/** 최초등록시점 */
	private java.util.Date frstRegistPnttm;
	/** 최초등록자ID*/
	private String frstRegisterId;
	/** 최종수정시점 */
	private java.util.Date lastUpdtPnttm;
	/** 최종수정자ID */
	private String lastUpdusrId;
	/** 사용여부 */
	private String useAt;
	
	public String getBbsId() {
		return bbsId;
	}
	public void setBbsId(String bbsId) {
		this.bbsId = bbsId;
	}
	public String getSiteId() {
		return siteId;
	}
	public void setSiteId(String siteId) {
		this.siteId = siteId;
	}
	public String getBbsNm() {
		return bbsNm;
	}
	public void setBbsNm(String bbsNm) {
		this.bbsNm = bbsNm;
	}
	public String getCtgryMasterId() {
		return ctgryMasterId;
	}
	public void setCtgryMasterId(String ctgryMasterId) {
		this.ctgryMasterId = ctgryMasterId;
	}
	public String getBbsTmplatCode() {
		return bbsTmplatCode;
	}
	public void setBbsTmplatCode(String bbsTmplatCode) {
		this.bbsTmplatCode = bbsTmplatCode;
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
	public String getAnnymtyAt() {
		return annymtyAt;
	}
	public void setAnnymtyAt(String annymtyAt) {
		this.annymtyAt = annymtyAt;
	}
	public String getUsgpdAt() {
		return usgpdAt;
	}
	public void setUsgpdAt(String usgpdAt) {
		this.usgpdAt = usgpdAt;
	}
	public String getReplyAt() {
		return replyAt;
	}
	public void setReplyAt(String replyAt) {
		this.replyAt = replyAt;
	}
	public String getCommentAt() {
		return commentAt;
	}
	public void setCommentAt(String commentAt) {
		this.commentAt = commentAt;
	}
	public String getFileAtachAt() {
		return fileAtachAt;
	}
	public void setFileAtachAt(String fileAtachAt) {
		this.fileAtachAt = fileAtachAt;
	}
	public Integer getAtchFileCo() {
		return atchFileCo;
	}
	public void setAtchFileCo(Integer atchFileCo) {
		this.atchFileCo = atchFileCo;
	}
	public Integer getAtchFileSize() {
		return atchFileSize;
	}
	public void setAtchFileSize(Integer atchFileSize) {
		this.atchFileSize = atchFileSize;
	}
	public String getListAuthorCode() {
		return listAuthorCode;
	}
	public void setListAuthorCode(String listAuthorCode) {
		this.listAuthorCode = listAuthorCode;
	}
	public String getRedingAuthorCode() {
		return redingAuthorCode;
	}
	public void setRedingAuthorCode(String redingAuthorCode) {
		this.redingAuthorCode = redingAuthorCode;
	}
	public String getWritngAuthorCode() {
		return writngAuthorCode;
	}
	public void setWritngAuthorCode(String writngAuthorCode) {
		this.writngAuthorCode = writngAuthorCode;
	}
	public String getUpdtAuthorCode() {
		return updtAuthorCode;
	}
	public void setUpdtAuthorCode(String updtAuthorCode) {
		this.updtAuthorCode = updtAuthorCode;
	}
	public String getDeleteAuthorCode() {
		return deleteAuthorCode;
	}
	public void setDeleteAuthorCode(String deleteAuthorCode) {
		this.deleteAuthorCode = deleteAuthorCode;
	}
	public String getReplyAuthorCode() {
		return replyAuthorCode;
	}
	public void setReplyAuthorCode(String replyAuthorCode) {
		this.replyAuthorCode = replyAuthorCode;
	}
	public String getDownloadAuthorCode() {
		return downloadAuthorCode;
	}
	public void setDownloadAuthorCode(String downloadAuthorCode) {
		this.downloadAuthorCode = downloadAuthorCode;
	}
	public String getCommentAuthorCode() {
		return commentAuthorCode;
	}
	public void setCommentAuthorCode(String commentAuthorCode) {
		this.commentAuthorCode = commentAuthorCode;
	}
	public String getCommentSortOrderType() {
		return commentSortOrderType;
	}
	public void setCommentSortOrderType(String commentSortOrderType) {
		this.commentSortOrderType = commentSortOrderType;
	}
	public Integer getListCo() {
		return listCo;
	}
	public void setListCo(Integer listCo) {
		this.listCo = listCo;
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


}
