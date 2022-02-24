package modoo.module.common.service;

import egovframework.com.cmm.ComDefaultVO;

@SuppressWarnings("serial")
public class CommonDefaultSearchVO extends ComDefaultVO {

	/** 검색 시작일 */
	private String searchBgnde = "";
	
	/** 검색 종료일 */
	private String searchEndde = "";

	/** 검색 : 사이크고유ID */
	private String searchSiteId = "";
	
	/** 검색 : 사이크고유메뉴ID */
	private String searchMenuId = "";
	
	/** 정렬 : 정렬필드 */
	private String searchOrderField = "";
	
	/** 정렬 : 정렬방식 */
	private String searchOrderType = "ASC";

	/** ip */
	private String clientIp="";

	public String getClientIp() {
		return clientIp;
	}

	public void setClientIp(String clientIp) {
		this.clientIp = clientIp;
	}

	public String getSearchBgnde() {
		return searchBgnde;
	}

	public void setSearchBgnde(String searchBgnde) {
		this.searchBgnde = searchBgnde;
	}

	public String getSearchEndde() {
		return searchEndde;
	}

	public void setSearchEndde(String searchEndde) {
		this.searchEndde = searchEndde;
	}

	public String getSearchSiteId() {
		return searchSiteId;
	}

	public void setSearchSiteId(String searchSiteId) {
		this.searchSiteId = searchSiteId;
	}

	public String getSearchOrderField() {
		return searchOrderField;
	}

	public void setSearchOrderField(String searchOrderField) {
		this.searchOrderField = searchOrderField;
	}

	public String getSearchOrderType() {
		return searchOrderType;
	}

	public void setSearchOrderType(String searchOrderType) {
		this.searchOrderType = searchOrderType;
	}

	public String getSearchMenuId() {
		return searchMenuId;
	}

	public void setSearchMenuId(String searchMenuId) {
		this.searchMenuId = searchMenuId;
	}
	
}
