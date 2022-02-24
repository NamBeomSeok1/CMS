package modoo.module.shop.goods.ctgry.service;

import java.util.List;
import java.util.Map;

import org.hibernate.validator.constraints.NotEmpty;

import modoo.module.common.service.CommonDefaultSearchVO;
import modoo.module.shop.goods.info.service.GoodsVO;

@SuppressWarnings("serial")
public class GoodsCtgryVO extends CommonDefaultSearchVO {

	/** 상품카테고리고유ID */
	private String goodsCtgryId;
	/** 상위상품카테고리고유ID */
	@NotEmpty
	private String upperGoodsCtgryId;
	/** 상품카테고리명 */
	@NotEmpty
	private String goodsCtgryNm;
	/** 상품카테고리순번 */
	private Integer goodsCtgrySn;
	/** 활성화여부 */
	private String actvtyAt;
	/** 기본정렬코드 */
	private String bassSortCode;
	/** 기본진열코드 */
	private String bassExhbiCode;
	
	/** 사용여부 */
	private String useAt;

	/** 카테고리이미지경로*/
	private String ctgryImagePath;

	/** 카테고리노출구분코드*/
	private String ctgryExpsrSeCode;
	

	
	/** 깊이 */
	private Integer dp;
	
	/** tree : tui grid 옵션 */
	private Map<String, Object> _attributes;
	
	/** tree : 서브 카테고리 목록 */
	private List<GoodsCtgryVO> _children;
	
	/** 카테고리 상품 리스트 개수*/
	private int goodsListCnt;
	
	/*
	 * 검색 : 상위카테고리고유ID
	 */
	private String searchUpperGoodsCtgryId;

	/*
	 * 검색 : 서브카테고리고유ID
	 */
	private String searchSubCtgryId;

	/*
	 * 검색 : 제휴사ID 
	 */
	private String searchPrtnrId;
	
	
	public int getGoodsListCnt() {
		return goodsListCnt;
	}
	public void setGoodsListCnt(int goodsListCnt) {
		this.goodsListCnt = goodsListCnt;
	}
	public String getGoodsCtgryId() {
		return goodsCtgryId;
	}
	public void setGoodsCtgryId(String goodsCtgryId) {
		this.goodsCtgryId = goodsCtgryId;
	}
	public String getUpperGoodsCtgryId() {
		return upperGoodsCtgryId;
	}
	public void setUpperGoodsCtgryId(String upperGoodsCtgryId) {
		this.upperGoodsCtgryId = upperGoodsCtgryId;
	}
	public String getGoodsCtgryNm() {
		return goodsCtgryNm;
	}
	public void setGoodsCtgryNm(String goodsCtgryNm) {
		this.goodsCtgryNm = goodsCtgryNm;
	}
	public Integer getGoodsCtgrySn() {
		return goodsCtgrySn;
	}
	public void setGoodsCtgrySn(Integer goodsCtgrySn) {
		this.goodsCtgrySn = goodsCtgrySn;
	}
	public String getUseAt() {
		return useAt;
	}
	public void setUseAt(String useAt) {
		this.useAt = useAt;
	}
	public List<GoodsCtgryVO> get_children() {
		return _children;
	}
	public void set_children(List<GoodsCtgryVO> _children) {
		this._children = _children;
	}
	public Map<String, Object> get_attributes() {
		return _attributes;
	}
	public void set_attributes(Map<String, Object> _attributes) {
		this._attributes = _attributes;
	}
	public String getSearchUpperGoodsCtgryId() {
		return searchUpperGoodsCtgryId;
	}
	public void setSearchUpperGoodsCtgryId(String searchUpperGoodsCtgryId) {
		this.searchUpperGoodsCtgryId = searchUpperGoodsCtgryId;
	}
	public Integer getDp() {
		return dp;
	}
	public void setDp(Integer dp) {
		this.dp = dp;
	}
	public String getActvtyAt() {
		return actvtyAt;
	}
	public void setActvtyAt(String actvtyAt) {
		this.actvtyAt = actvtyAt;
	}
	public String getCtgryExpsrSeCode() {
		return ctgryExpsrSeCode;
	}
	public void setCtgryExpsrSeCode(String ctgryExpsrSeCode) {
		this.ctgryExpsrSeCode = ctgryExpsrSeCode;
	}
	public String getBassSortCode() {
		return bassSortCode;
	}
	public void setBassSortCode(String bassSortCode) {
		this.bassSortCode = bassSortCode;
	}
	public String getBassExhbiCode() {
		return bassExhbiCode;
	}
	public void setBassExhbiCode(String bassExhbiCode) {
		this.bassExhbiCode = bassExhbiCode;
	}
	public String getCtgryImagePath() {
		return ctgryImagePath;
	}
	public void setCtgryImagePath(String ctgryImagePath) {
		this.ctgryImagePath = ctgryImagePath;
	}
	public String getSearchSubCtgryId() {
		return searchSubCtgryId;
	}
	public void setSearchSubCtgryId(String searchSubCtgryId) {
		this.searchSubCtgryId = searchSubCtgryId;
	}
	public String getSearchPrtnrId() {
		return searchPrtnrId;
	}
	public void setSearchPrtnrId(String searchPrtnrId) {
		this.searchPrtnrId = searchPrtnrId;
	}
	
}
