package modoo.module.comment.service;

import org.hibernate.validator.constraints.NotEmpty;

import modoo.module.common.service.CommonDefaultSearchVO;

@SuppressWarnings("serial")
public class CommentVO extends CommonDefaultSearchVO {
	
	/** 댓글고유ID */
	private Long commentId;
	/** 댓글구분코드 */
	private String cntntsSeCode;
	/** 콘텐츠ID (NTT_ID ....) */
	private String cntntsId;
	/** 작성자ID */
	private String wrterId;
	/** 작성자명 */
	private String wrterNm;
	/** 댓글내용 */
	@NotEmpty
	private String commentCn;
	/** 비밀번호 */
	private String password;
	/** 댓글정렬순서 */
	private Long commentSortOrdr;
	/** 댓글번호 */
	private Long commentNo;
	/** 댓글부모ID */
	private Long commentParntId;
	/** 댓글답변위치 */
	private Integer commentReplyLc;
	/** 추천수 */
	private Integer recomendCo;
	/** 비추천수 */
	private Integer unrecomendCo;
	/** 사용여부 */
	private String useAt;
	/** 최초등록시점 */
	private java.util.Date frstRegistPnttm;
	/** 최초등록자ID */
	private String frstRegisterId;
	/** 최종수정시점 */
	private java.util.Date lastUpdtPnttm;
	/** 최종수정자ID */
	private String lastUpduserId;
	/** 비밀글 여부*/
	private String secretAt;
	/** 점수 */
	private Integer score;
	/** 아이디 */
	private String mberId;
	/** 첨부파일ID */
	private String atchFileId;

	/*
	 * 주문자ID
	 */
	private String ordrrId;
	
	/*
	 * 검색 : 콘텐츠구분코드
	 */
	private String searchCntntsSeCode;

	/*
	 * 검색 : 콘텐츠 ID
	 */
	private String searchCntntsId;
	
	/*
	 * 검색 : 상품ID
	 */
	private String searchGoodsId;
	
	private String searchCommentCn; //검색 : 댓글내용
	private String searchWrterId;	//검색 : 작성자ID
	private String searchWrterNm;	//검색 : 작성자명
	private String searchGoodsNm;	//검색 : 상품명
	private Integer searchScore;	//검색 : 점수
	
	/*
	 * 검색 : 업체고유ID
	 */
	private String searchCmpnyId;

	public Long getCommentId() {
		return commentId;
	}
	public void setCommentId(Long commentId) {
		this.commentId = commentId;
	}
	public String getCntntsSeCode() {
		return cntntsSeCode;
	}
	public void setCntntsSeCode(String cntntsSeCode) {
		this.cntntsSeCode = cntntsSeCode;
	}
	public String getCntntsId() {
		return cntntsId;
	}
	public void setCntntsId(String cntntsId) {
		this.cntntsId = cntntsId;
	}
	public String getWrterId() {
		return wrterId;
	}
	public void setWrterId(String wrterId) {
		this.wrterId = wrterId;
	}
	public String getWrterNm() {
		return wrterNm;
	}
	public void setWrterNm(String wrterNm) {
		this.wrterNm = wrterNm;
	}
	public String getCommentCn() {
		return commentCn;
	}
	public void setCommentCn(String commentCn) {
		this.commentCn = commentCn;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public Long getCommentSortOrdr() {
		return commentSortOrdr;
	}
	public void setCommentSortOrdr(Long commentSortOrdr) {
		this.commentSortOrdr = commentSortOrdr;
	}
	public Long getCommentNo() {
		return commentNo;
	}
	public void setCommentNo(Long commentNo) {
		this.commentNo = commentNo;
	}
	public Long getCommentParntId() {
		return commentParntId;
	}
	public void setCommentParntId(Long commentParntId) {
		this.commentParntId = commentParntId;
	}
	public Integer getCommentReplyLc() {
		return commentReplyLc;
	}
	public void setCommentReplyLc(Integer commentReplyLc) {
		this.commentReplyLc = commentReplyLc;
	}
	public Integer getRecomendCo() {
		return recomendCo;
	}
	public void setRecomendCo(Integer recomendCo) {
		this.recomendCo = recomendCo;
	}
	public Integer getUnrecomendCo() {
		return unrecomendCo;
	}
	public void setUnrecomendCo(Integer unrecomendCo) {
		this.unrecomendCo = unrecomendCo;
	}
	public String getUseAt() {
		return useAt;
	}
	public void setUseAt(String useAt) {
		this.useAt = useAt;
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
	public String getLastUpduserId() {
		return lastUpduserId;
	}
	public void setLastUpduserId(String lastUpduserId) {
		this.lastUpduserId = lastUpduserId;
	}
	public String getSearchCntntsId() {
		return searchCntntsId;
	}
	public void setSearchCntntsId(String searchCntntsId) {
		this.searchCntntsId = searchCntntsId;
	}
	public String getSearchCntntsSeCode() {
		return searchCntntsSeCode;
	}
	public void setSearchCntntsSeCode(String searchCntntsSeCode) {
		this.searchCntntsSeCode = searchCntntsSeCode;
	}
	public String getSecretAt() {
		return secretAt;
	}
	public void setSecretAt(String secretAt) {
		this.secretAt = secretAt;
	}
	public Integer getScore() {
		return score;
	}
	public void setScore(Integer score) {
		this.score = score;
	}
	public String getMberId() {
		return mberId;
	}
	public void setMberId(String mberId) {
		this.mberId = mberId;
	}
	public String getAtchFileId() {
		return atchFileId;
	}
	public void setAtchFileId(String atchFileId) {
		this.atchFileId = atchFileId;
	}
	public String getSearchGoodsId() {
		return searchGoodsId;
	}
	public void setSearchGoodsId(String searchGoodsId) {
		this.searchGoodsId = searchGoodsId;
	}
	public String getOrdrrId() {
		return ordrrId;
	}
	public void setOrdrrId(String ordrrId) {
		this.ordrrId = ordrrId;
	}
	public String getSearchCommentCn() {
		return searchCommentCn;
	}
	public void setSearchCommentCn(String searchCommentCn) {
		this.searchCommentCn = searchCommentCn;
	}
	public String getSearchWrterId() {
		return searchWrterId;
	}
	public void setSearchWrterId(String searchWrterId) {
		this.searchWrterId = searchWrterId;
	}
	public String getSearchWrterNm() {
		return searchWrterNm;
	}
	public void setSearchWrterNm(String searchWrterNm) {
		this.searchWrterNm = searchWrterNm;
	}
	public String getSearchGoodsNm() {
		return searchGoodsNm;
	}
	public void setSearchGoodsNm(String searchGoodsNm) {
		this.searchGoodsNm = searchGoodsNm;
	}
	public Integer getSearchScore() {
		return searchScore;
	}
	public void setSearchScore(Integer searchScore) {
		this.searchScore = searchScore;
	}
	public String getSearchCmpnyId() {
		return searchCmpnyId;
	}
	public void setSearchCmpnyId(String searchCmpnyId) {
		this.searchCmpnyId = searchCmpnyId;
	}
	
}
