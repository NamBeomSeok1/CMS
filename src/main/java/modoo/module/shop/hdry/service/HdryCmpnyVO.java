package modoo.module.shop.hdry.service;

import modoo.module.common.service.CommonDefaultSearchVO;

@SuppressWarnings("serial")
public class HdryCmpnyVO extends CommonDefaultSearchVO {

	/** 택배사고유ID */
	private String hdryId;
	
	/** 택배사명 */
	private String hdryNm;
	
	/** 택배사전화번호 */
	private String hdryTelno;
	
	/** 사용여부 */
	private String useAt;
	
	/*
	 * 검색: 업체아이디
	 */
	private String searchCmpnyId;

	public String getHdryId() {
		return hdryId;
	}

	public void setHdryId(String hdryId) {
		this.hdryId = hdryId;
	}

	public String getHdryNm() {
		return hdryNm;
	}

	public void setHdryNm(String hdryNm) {
		this.hdryNm = hdryNm;
	}

	public String getHdryTelno() {
		return hdryTelno;
	}

	public void setHdryTelno(String hdryTelno) {
		this.hdryTelno = hdryTelno;
	}

	public String getUseAt() {
		return useAt;
	}

	public void setUseAt(String useAt) {
		this.useAt = useAt;
	}

	public String getSearchCmpnyId() {
		return searchCmpnyId;
	}

	public void setSearchCmpnyId(String searchCmpnyId) {
		this.searchCmpnyId = searchCmpnyId;
	}
	
	
}
