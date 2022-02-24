package modoo.module.common.service;

import java.util.List;

import egovframework.rte.psl.dataaccess.util.EgovMap;

public class JsonResult {

	private boolean isSuccess = false;

	private String message;

	private EgovMap data;

	private List<ErrorInfo> errorList = null;
	
	private String eventCode;
	
	private String redirectUrl;
	
	public JsonResult() {
		this.data = new EgovMap();
	}

	public boolean isSuccess() {
		return isSuccess;
	}

	public void setSuccess(boolean success) {
		isSuccess = success;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public List<ErrorInfo> getErrorList() {
		return errorList;
	}

	public void setErrorList(List<ErrorInfo> errorList) {
		this.errorList = errorList;
	}
	
	public void put(String key, Object value) {
		data.put(key, value);
	}
	
	public EgovMap getData() {
		return data;
	}
	
	public String getEventCode() {
		return eventCode;
	}

	public void setEventCode(String eventCode) {
		this.eventCode = eventCode;
	}
	
	public String getRedirectUrl() {
		return redirectUrl;
	}

	public void setRedirectUrl(String redirectUrl) {
		this.redirectUrl = redirectUrl;
	}

	@Override
	public String toString() {
		return "JsonResult [isSuccess=" + isSuccess + ", message=" + message + ", data=" + data + "]";
	}
	
}