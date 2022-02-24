package modoo.module.biztalk.service;

import java.util.List;

import modoo.module.common.service.CommonDefaultSearchVO;

@SuppressWarnings("serial")
public class BiztalkVO extends CommonDefaultSearchVO {

	/** biz용 메시지 일련번호ID */
	private String msgIdx;
	
	/** biz용 국가코드 */
	private String countryCode;
	
	/** biz용 수신자 번호 */
	private String recipient;
	private List recipientList;
	
	/** biz용 배너구분코드 */
	private String senderKey;
	
	//biz용  발신 메시지 내용
	private String message; 
	private List messageList;
	
	/** biz용 전송 방식 : PUSH */
	private String resMethod;
	
	/* biz용 요청 결과 코드 */
	private String responseCode;
	
	/* biz용 요청 결과 메세지 */
	private String msg;
	
	//서버IP (토큰사용할 서버IP : 공유기 IP)
	private String serverIp;
	
	//biz용 토큰
	private String token;
	
	//오늘 일자
	private String today;
	
	//알림톡ID
	private String alimtalkId;
	
	//템플릿코드
	private String tmplatCode;
	
	//템플릿명
	private String tmplatNm;
	
	//템플릿내용
	private String tmplatCn;
	
	//최초등록시점
	private java.util.Date frstRegistPnttm;
	
	//최초등록자ID
	private String frstRegisterId;
	
	//최종수정시점
	private java.util.Date lastUpdtPnttm;
	
	//최종수정자ID
	private String lastUpdusrId;
	
	//사용여부
	private String useAt;
	
	//알림톡로그
	private String alimtalkLogId;
	
	//전송구분코드
	private String sendCode;
	
	//전송내용
	private String sendCn;
	
	//전송시점
	private java.util.Date registPnttm;
	
	//비즈톡전송결과코드
	private String bizResultCode;
	
	//카카오전송결과코드
	private String kakaoResultCode;
	
	//카카오전송결과확인시점
	private java.util.Date kakaoResultPnttm;
	
	//파라미터 일괄용
	private List paramList;
	
	public String getMsgIdx() {
		return msgIdx;
	}

	public void setMsgIdx(String msgIdx) {
		this.msgIdx = msgIdx;
	}

	public String getCountryCode() {
		return countryCode;
	}

	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}

	public String getRecipient() {
		return recipient;
	}

	public void setRecipient(String recipient) {
		this.recipient = recipient;
	}

	public String getSenderKey() {
		return senderKey;
	}

	public void setSenderKey(String senderKey) {
		this.senderKey = senderKey;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getResMethod() {
		return resMethod;
	}

	public void setResMethod(String resMethod) {
		this.resMethod = resMethod;
	}

	public String getResponseCode() {
		return responseCode;
	}

	public void setResponseCode(String responseCode) {
		this.responseCode = responseCode;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getToday() {
		return today;
	}

	public void setToday(String today) {
		this.today = today;
	}

	public String getAlimtalkId() {
		return alimtalkId;
	}

	public void setAlimtalkId(String alimtalkId) {
		this.alimtalkId = alimtalkId;
	}

	public String getTmplatCode() {
		return tmplatCode;
	}

	public void setTmplatCode(String tmplatCode) {
		this.tmplatCode = tmplatCode;
	}

	public String getTmplatNm() {
		return tmplatNm;
	}

	public void setTmplatNm(String tmplatNm) {
		this.tmplatNm = tmplatNm;
	}

	public String getTmplatCn() {
		return tmplatCn;
	}

	public void setTmplatCn(String tmplatCn) {
		this.tmplatCn = tmplatCn;
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

	public String getAlimtalkLogId() {
		return alimtalkLogId;
	}

	public void setAlimtalkLogId(String alimtalkLogId) {
		this.alimtalkLogId = alimtalkLogId;
	}

	public String getSendCode() {
		return sendCode;
	}

	public void setSendCode(String sendCode) {
		this.sendCode = sendCode;
	}

	public String getSendCn() {
		return sendCn;
	}

	public void setSendCn(String sendCn) {
		this.sendCn = sendCn;
	}

	public java.util.Date getRegistPnttm() {
		return registPnttm;
	}

	public void setRegistPnttm(java.util.Date registPnttm) {
		this.registPnttm = registPnttm;
	}

	public String getBizResultCode() {
		return bizResultCode;
	}

	public void setBizResultCode(String bizResultCode) {
		this.bizResultCode = bizResultCode;
	}

	public String getKakaoResultCode() {
		return kakaoResultCode;
	}

	public void setKakaoResultCode(String kakaoResultCode) {
		this.kakaoResultCode = kakaoResultCode;
	}

	public java.util.Date getKakaoResultPnttm() {
		return kakaoResultPnttm;
	}

	public void setKakaoResultPnttm(java.util.Date kakaoResultPnttm) {
		this.kakaoResultPnttm = kakaoResultPnttm;
	}

	public List getRecipientList() {
		return recipientList;
	}

	public void setRecipientList(List recipientList) {
		this.recipientList = recipientList;
	}

	public List getMessageList() {
		return messageList;
	}

	public void setMessageList(List messageList) {
		this.messageList = messageList;
	}

	public List getParamList() {
		return paramList;
	}

	public void setParamList(List paramList) {
		this.paramList = paramList;
	}

	public String getServerIp() {
		return serverIp;
	}

	public void setServerIp(String serverIp) {
		this.serverIp = serverIp;
	}

}
