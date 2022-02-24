package modoo.module.qainfo.service;

import org.hibernate.validator.constraints.NotEmpty;

import modoo.module.common.service.CommonDefaultSearchVO;

@SuppressWarnings("serial")
public class QainfoVO extends CommonDefaultSearchVO {

	/** 질답고유ID */
	private String qaId;
	/** 사이트고유ID */
	private String siteId;
	/** 상품고유ID */
	private String goodsId;
	/** 파일고유ID */
	private String atchFileId;
	/** 질문제목 */
	private String qestnSj;
	/** 질문내용 */
	@NotEmpty
	private String qestnCn;
	/** 작성일 */
	private String writngDe;
	/** 작성자ID */
	private String wrterId;
	/** 작성자명 */
	private String wrterNm;
	/** 이메일주소 */
	private String emailAdres;
	/** 작성자전화번호 */
	private String wrterTelno;
	/** 작성비밀번호 */
	private String writngPassword;
	/** 답변내용 */
	private String answerCn;
	/** 답변일 */
	private String answerDe;
	/** 답변자ID */
	private String answerId;
	/** 답변자명 */
	private String answerNm;
	/** 질답진행상태코드 */
	private String qnaProcessSttusCode;
	/** 질답구분코드 */
	private String qaSeCode;
	/** 질답유형코드 */
	private String qestnTyCode;
	/** 비밀글여부*/
	private String secretAt;
	/** 최초등록시점 */
	private String frstRegistPnttm;
	/** 최초등록자ID */
	private String frstRegisterId;
	/** 최종수정시점 */
	private String lastUpdtPnttm;
	/** 최종수정자ID */
	private String lastUpdusrId;
	
	/** 사용여부 */
	private String useAt;

	private String telno1;
	private String telno2;
	private String telno3;
	
	private String goodsNm;

	private String reqTyCodeNm;
	
	/*
	 * 검색 : 상품명
	 */
	private String searchGoodsNm;
	
	/*
	 * 검색 : 질답유형코드
	 */
	private String searchQestnTyCode;
	
	/*
	 * 검색 : 진행상태코드
	 */
	private String searchQnaProcessSttusCode;
	
	/*
	 * 검색 : 업체고유ID
	 */
	private String searchCmpnyId;
	
	/*
	 * 검색 : 업체명
	 */
	private String searchCmpnyNm;
	
	/*
	 * 검색 : 배송ID
	 */
	private java.math.BigDecimal searchOrderDlvyNo;
	
	public String getGoodsId() {
		return goodsId;
	}
	public void setGoodsId(String goodsId) {
		this.goodsId = goodsId;
	}
	public String getQaId() {
		return qaId;
	}
	public void setQaId(String qaId) {
		this.qaId = qaId;
	}
	public String getSiteId() {
		return siteId;
	}
	public void setSiteId(String siteId) {
		this.siteId = siteId;
	}
	public String getAtchFileId() {
		return atchFileId;
	}
	public void setAtchFileId(String atchFileId) {
		this.atchFileId = atchFileId;
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
	public String getWritngDe() {
		return writngDe;
	}
	public void setWritngDe(String writngDe) {
		this.writngDe = writngDe;
	}
	public String getWrterNm() {
		return wrterNm;
	}
	public void setWrterNm(String wrterNm) {
		this.wrterNm = wrterNm;
	}
	public String getEmailAdres() {
		return emailAdres;
	}
	public void setEmailAdres(String emailAdres) {
		this.emailAdres = emailAdres;
	}
	public String getWrterTelno() {
		return wrterTelno;
	}
	public void setWrterTelno(String wrterTelno) {
		this.wrterTelno = wrterTelno;
	}
	public String getWritngPassword() {
		return writngPassword;
	}
	public void setWritngPassword(String writngPassword) {
		this.writngPassword = writngPassword;
	}
	public String getAnswerCn() {
		return answerCn;
	}
	public void setAnswerCn(String answerCn) {
		this.answerCn = answerCn;
	}
	public String getAnswerDe() {
		return answerDe;
	}
	public void setAnswerDe(String answerDe) {
		this.answerDe = answerDe;
	}
	public String getAnswerId() {
		return answerId;
	}
	public void setAnswerId(String answerId) {
		this.answerId = answerId;
	}
	public String getAnswerNm() {
		return answerNm;
	}
	public void setAnswerNm(String answerNm) {
		this.answerNm = answerNm;
	}
	public String getQnaProcessSttusCode() {
		return qnaProcessSttusCode;
	}
	public void setQnaProcessSttusCode(String qnaProcessSttusCode) {
		this.qnaProcessSttusCode = qnaProcessSttusCode;
	}
	public String getQaSeCode() {
		return qaSeCode;
	}
	public void setQaSeCode(String qaSeCode) {
		this.qaSeCode = qaSeCode;
	}
	public String getQestnTyCode() {
		return qestnTyCode;
	}
	public void setQestnTyCode(String qestnTyCode) {
		this.qestnTyCode = qestnTyCode;
	}
	public String getSecretAt() {
		return secretAt;
	}
	public void setSecretAt(String secretAt) {
		this.secretAt = secretAt;
	}
	public String getFrstRegistPnttm() {
		return frstRegistPnttm;
	}
	public void setFrstRegistPnttm(String frstRegistPnttm) {
		this.frstRegistPnttm = frstRegistPnttm;
	}
	public String getFrstRegisterId() {
		return frstRegisterId;
	}
	public void setFrstRegisterId(String frstRegisterId) {
		this.frstRegisterId = frstRegisterId;
	}
	public String getLastUpdtPnttm() {
		return lastUpdtPnttm;
	}
	public void setLastUpdtPnttm(String lastUpdtPnttm) {
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
	public String getSearchGoodsNm() {
		return searchGoodsNm;
	}
	public void setSearchGoodsNm(String searchGoodsNm) {
		this.searchGoodsNm = searchGoodsNm;
	}
	public String getWrterId() {
		return wrterId;
	}
	public void setWrterId(String wrterId) {
		this.wrterId = wrterId;
	}
	public String getSearchQestnTyCode() {
		return searchQestnTyCode;
	}
	public void setSearchQestnTyCode(String searchQestnTyCode) {
		this.searchQestnTyCode = searchQestnTyCode;
	}
	public String getSearchQnaProcessSttusCode() {
		return searchQnaProcessSttusCode;
	}
	public void setSearchQnaProcessSttusCode(String searchQnaProcessSttusCode) {
		this.searchQnaProcessSttusCode = searchQnaProcessSttusCode;
	}
	public String getSearchCmpnyId() {
		return searchCmpnyId;
	}
	public void setSearchCmpnyId(String searchCmpnyId) {
		this.searchCmpnyId = searchCmpnyId;
	}
	public java.math.BigDecimal getSearchOrderDlvyNo() {
		return searchOrderDlvyNo;
	}
	public void setSearchOrderDlvyNo(java.math.BigDecimal searchOrderDlvyNo) {
		this.searchOrderDlvyNo = searchOrderDlvyNo;
	}
	public String getSearchCmpnyNm() {
		return searchCmpnyNm;
	}
	public void setSearchCmpnyNm(String searchCmpnyNm) {
		this.searchCmpnyNm = searchCmpnyNm;
	}
	public String getReqTyCodeNm() {
	      return reqTyCodeNm;
	 }
	   public void setReqTyCodeNm(String reqTyCodeNm) {
	       this.reqTyCodeNm = reqTyCodeNm;
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
	public String getGoodsNm() {
		return goodsNm;
	}
	public void setGoodsNm(String goodsNm) {
		this.goodsNm = goodsNm;
	}
}
