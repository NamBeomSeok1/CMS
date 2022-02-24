package modoo.module.system.log.login.service;

import modoo.module.common.service.CommonDefaultSearchVO;

@SuppressWarnings("serial")
public class LoginLogVO extends CommonDefaultSearchVO {

	/** 로그ID */
	private String logId;
	/** 사이트고유ID */
	private String siteId;
	/** 접속ID */
	private String conectId;
	/** 접속IP */
	private String conectIp;
	/** 접속방식 */
	private String conectMthd;
	/** 오류발생여부 */
	private String errorOccrrncAt;
	/** 오류코드 */
	private String errorCode;
	/** 생성일시 */
	private String creatDt;
	/** 생성년 */
	private String creatYear;
	/** 생성월 */
	private String creatMonth;
	/** 생성일 */
	private String creatDay;
	
	public String getLogId() {
		return logId;
	}
	public void setLogId(String logId) {
		this.logId = logId;
	}
	public String getSiteId() {
		return siteId;
	}
	public void setSiteId(String siteId) {
		this.siteId = siteId;
	}
	public String getConectId() {
		return conectId;
	}
	public void setConectId(String conectId) {
		this.conectId = conectId;
	}
	public String getConectIp() {
		return conectIp;
	}
	public void setConectIp(String conectIp) {
		this.conectIp = conectIp;
	}
	public String getConectMthd() {
		return conectMthd;
	}
	public void setConectMthd(String conectMthd) {
		this.conectMthd = conectMthd;
	}
	public String getErrorOccrrncAt() {
		return errorOccrrncAt;
	}
	public void setErrorOccrrncAt(String errorOccrrncAt) {
		this.errorOccrrncAt = errorOccrrncAt;
	}
	public String getErrorCode() {
		return errorCode;
	}
	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}
	public String getCreatDt() {
		return creatDt;
	}
	public void setCreatDt(String creatDt) {
		this.creatDt = creatDt;
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
	
}
