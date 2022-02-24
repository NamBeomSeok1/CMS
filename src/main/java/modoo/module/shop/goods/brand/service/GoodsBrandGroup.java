package modoo.module.shop.goods.brand.service;

import java.util.List;

public class GoodsBrandGroup {

	/** 단어 */
	private String wrd;

	/** 브랜드목록 */
	private List<GoodsBrandVO> goodsBrandList;

	public String getWrd() {
		return wrd;
	}

	public void setWrd(String wrd) {
		this.wrd = wrd;
	}

	public List<GoodsBrandVO> getGoodsBrandList() {
		return goodsBrandList;
	}

	public void setGoodsBrandList(List<GoodsBrandVO> goodsBrandList) {
		this.goodsBrandList = goodsBrandList;
	}

}
