package modoo.module.system.log.conect.service;

import modoo.module.common.service.CommonDefaultSearchVO;

@SuppressWarnings("serial")
public class ConectLogVO extends CommonDefaultSearchVO {

	/** 접속록그ID */
	private String conectId;
	/** 사이트고유ID */
	private String siteId;
	/** 세션ID */
	private String sessionId;
	/** 로그구분코드 */
	private String logSeCode;
	/** 발생시점 */
	private java.util.Date creatPnttm;
	/** 발생년 */
	private String creatYear;
	/** 발생월 */
	private String creatMonth;
	/** 발생일 */
	private String creatDay;
	/** 발생시 */
	private String creatHour;
	/** User Agent */
	private String userAgent;
	/** referer */
	private String referer;
	/** 요청자IP */
	private String rqesterIp;
	
	/** 발생일시 */
	private String creatDt;
	/** 카운트 */
	private Integer sumCount;
	/**B2C발생일시 */
	private String b2cCreatDt;
	/**B2C카운트 */
	private Integer b2cSumCount;
	/**B2B발생일시 */
	private String b2bCreatDt;
	/**B2B카운트 */
	private Integer b2bSumCount;
	
	public String getConectId() {
		return conectId;
	}
	public void setConectId(String conectId) {
		this.conectId = conectId;
	}
	public String getSiteId() {
		return siteId;
	}
	public void setSiteId(String siteId) {
		this.siteId = siteId;
	}
	public String getSessionId() {
		return sessionId;
	}
	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}
	public String getLogSeCode() {
		return logSeCode;
	}
	public void setLogSeCode(String logSeCode) {
		this.logSeCode = logSeCode;
	}
	public java.util.Date getCreatPnttm() {
		return creatPnttm;
	}
	public void setCreatPnttm(java.util.Date creatPnttm) {
		this.creatPnttm = creatPnttm;
	}
	public String getCreatYear() {
		return creatYear;
	}
	public void setCreatYear(String creatYear) {
		this.creatYear = creatYear;
	}
	public String getCreatMonth() {
		return creatMonth;
	}
	public void setCreatMonth(String creatMonth) {
		this.creatMonth = creatMonth;
	}
	public String getCreatDay() {
		return creatDay;
	}
	public void setCreatDay(String creatDay) {
		this.creatDay = creatDay;
	}
	public String getCreatHour() {
		return creatHour;
	}
	public void setCreatHour(String creatHour) {
		this.creatHour = creatHour;
	}
	public String getUserAgent() {
		return userAgent;
	}
	public void setUserAgent(String userAgent) {
		this.userAgent = userAgent;
	}
	public String getReferer() {
		return referer;
	}
	public void setReferer(String referer) {
		this.referer = referer;
	}
	public String getB2cCreatDt() {
		return b2cCreatDt;
	}
	public void setB2cCreatDt(String b2cCreatDt) {
		this.b2cCreatDt = b2cCreatDt;
	}
	public Integer getB2cSumCount() {
		return b2cSumCount;
	}
	public void setB2cSumCount(Integer b2cSumCount) {
		this.b2cSumCount = b2cSumCount;
	}
	public String getB2bCreatDt() {
		return b2bCreatDt;
	}
	public void setB2bCreatDt(String b2bCreatDt) {
		this.b2bCreatDt = b2bCreatDt;
	}
	public Integer getB2bSumCount() {
		return b2bSumCount;
	}
	public void setB2bSumCount(Integer b2bSumCount) {
		this.b2bSumCount = b2bSumCount;
	}
	public String getRqesterIp() {
		return rqesterIp;
	}
	public void setRqesterIp(String rqesterIp) {
		this.rqesterIp = rqesterIp;
	}
	public String getCreatDt() {
		return creatDt;
	}
	public void setCreatDt(String creatDt) {
		this.creatDt = creatDt;
	}
	public Integer getSumCount() {
		return sumCount;
	}
	public void setSumCount(Integer sumCount) {
		this.sumCount = sumCount;
	}


	
	
}
