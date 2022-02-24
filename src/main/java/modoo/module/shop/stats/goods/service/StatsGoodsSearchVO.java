package modoo.module.shop.stats.goods.service;

import modoo.module.common.service.CommonDefaultSearchVO;

@SuppressWarnings("serial")
public class StatsGoodsSearchVO extends CommonDefaultSearchVO {

	/*
	 * 검색 : 카테고리ID
	 */
	private String searchGoodsCtgryId;
	
	private String searchCateCode1;
	private String searchCateCode2;
	private String searchCateCode3;
	
	/**
	 * 검색 : 업체ID
	 */
	private String searchCmpnyId;
	
	public String getSearchGoodsCtgryId() {
		return searchGoodsCtgryId;
	}
	public void setSearchGoodsCtgryId(String searchGoodsCtgryId) {
		this.searchGoodsCtgryId = searchGoodsCtgryId;
	}
	public String getSearchCateCode1() {
		return searchCateCode1;
	}
	public void setSearchCateCode1(String searchCateCode1) {
		this.searchCateCode1 = searchCateCode1;
	}
	public String getSearchCateCode2() {
		return searchCateCode2;
	}
	public void setSearchCateCode2(String searchCateCode2) {
		this.searchCateCode2 = searchCateCode2;
	}
	public String getSearchCateCode3() {
		return searchCateCode3;
	}
	public void setSearchCateCode3(String searchCateCode3) {
		this.searchCateCode3 = searchCateCode3;
	}
	public String getSearchCmpnyId() {
		return searchCmpnyId;
	}
	public void setSearchCmpnyId(String searchCmpnyId) {
		this.searchCmpnyId = searchCmpnyId;
	}
	
}
