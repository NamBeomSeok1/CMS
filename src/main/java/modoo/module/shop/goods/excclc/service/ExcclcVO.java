package modoo.module.shop.goods.excclc.service;

import java.util.ArrayList;

import modoo.module.common.service.CommonDefaultSearchVO;

@SuppressWarnings("serial")
public class ExcclcVO extends CommonDefaultSearchVO {
	
	/** 주문결제고유번호 */
	private java.math.BigDecimal orderSetleNo;
	/** 정산예정일 */
	private String excclcPrarnde;
	/** 정산상태코드 */
	private String excclcSttusCode;
	/** 정산시점 */
	private java.util.Date excclcPnttm;


	/** 검색 : 업체명 */
	private String searchCmpnyNm;
	/** 검색 : 업체고유ID */
	private String searchCmpnyId;
	
	/** 검색 : 본사정산상태 코드 (E01 대기, E02 정산보류, E03 정산요청, E04 정산처리중, E05 정산완료)
	 *  	CP정산상태코드 (CPE01 정산대기, CPE02 정산보류, CPE03 정산완료) 
	 */
	private String searchExcclcSttusCode;
	
	/** 검색 : 제휴사고유ID */
	private String searchPrtnrId;
	
	/** 검색 : 업체사용자ID */
	private String searchCmpnyUserId; 
	
	/** 검색 : 정산기간 : PD01 1~말일, PD02 1~15, PD03 16~말일 */
	private String searchExcclcSePd;

	/** 검색 : 정산년 */
	private String searchExcclcYear;
	
	/** 검색 : 정산월 */
	private String searchExcclcMonth;
	
	/** 주문결제번호 목록 */
	private ArrayList<Integer> orderSetleNoList;
	
	/** 검색 : 모드 (CP, MNG)*/
	private String searchMode;
	
	public java.math.BigDecimal getOrderSetleNo() {
		return orderSetleNo;
	}

	public void setOrderSetleNo(java.math.BigDecimal orderSetleNo) {
		this.orderSetleNo = orderSetleNo;
	}

	public String getExcclcPrarnde() {
		return excclcPrarnde;
	}

	public void setExcclcPrarnde(String excclcPrarnde) {
		this.excclcPrarnde = excclcPrarnde;
	}

	public String getSearchCmpnyNm() {
		return searchCmpnyNm;
	}

	public void setSearchCmpnyNm(String searchCmpnyNm) {
		this.searchCmpnyNm = searchCmpnyNm;
	}

	public String getSearchExcclcSttusCode() {
		return searchExcclcSttusCode;
	}

	public void setSearchExcclcSttusCode(String searchExcclcSttusCode) {
		this.searchExcclcSttusCode = searchExcclcSttusCode;
	}

	public String getSearchCmpnyUserId() {
		return searchCmpnyUserId;
	}

	public void setSearchCmpnyUserId(String searchCmpnyUserId) {
		this.searchCmpnyUserId = searchCmpnyUserId;
	}

	public String getExcclcSttusCode() {
		return excclcSttusCode;
	}

	public void setExcclcSttusCode(String excclcSttusCode) {
		this.excclcSttusCode = excclcSttusCode;
	}

	public java.util.Date getExcclcPnttm() {
		return excclcPnttm;
	}

	public void setExcclcPnttm(java.util.Date excclcPnttm) {
		this.excclcPnttm = excclcPnttm;
	}

	public String getSearchPrtnrId() {
		return searchPrtnrId;
	}

	public void setSearchPrtnrId(String searchPrtnrId) {
		this.searchPrtnrId = searchPrtnrId;
	}

	public String getSearchCmpnyId() {
		return searchCmpnyId;
	}

	public void setSearchCmpnyId(String searchCmpnyId) {
		this.searchCmpnyId = searchCmpnyId;
	}

	public String getSearchExcclcMonth() {
		return searchExcclcMonth;
	}

	public void setSearchExcclcMonth(String searchExcclcMonth) {
		this.searchExcclcMonth = searchExcclcMonth;
	}

	public String getSearchExcclcSePd() {
		return searchExcclcSePd;
	}

	public void setSearchExcclcSePd(String searchExcclcSePd) {
		this.searchExcclcSePd = searchExcclcSePd;
	}

	public String getSearchExcclcYear() {
		return searchExcclcYear;
	}

	public void setSearchExcclcYear(String searchExcclcYear) {
		this.searchExcclcYear = searchExcclcYear;
	}

	public ArrayList<Integer> getOrderSetleNoList() {
		return orderSetleNoList;
	}

	public void setOrderSetleNoList(ArrayList<Integer> orderSetleNoList) {
		this.orderSetleNoList = orderSetleNoList;
	}

	public String getSearchMode() {
		return searchMode;
	}

	public void setSearchMode(String searchMode) {
		this.searchMode = searchMode;
	}

}
