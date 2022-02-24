package modoo.module.shop.goods.keyword.service;

import java.util.List;

import modoo.module.shop.goods.info.service.GoodsVO;

public interface GoodsKeywordService {

	/**
	 * 상품키워드 목록
	 * @param searchVO
	 * @return
	 * @throws Exception
	 */
	List<GoodsKeywordVO> selectGoodsKeywordList(GoodsVO goods) throws Exception;
	
	/**
	 * 상품키워드 저장
	 * @param goodsKeyword
	 * @throws Exception
	 */
	void insertGoodsKeyword(GoodsKeywordVO goodsKeyword) throws Exception;
	
	/**
	 * 상품키워드 삭제
	 * @param goodsKeyword
	 * @throws Exception
	 */
	void deleteGoodsKeyword(GoodsKeywordVO goodsKeyword) throws Exception;
	
	/**
	 * 상품연관 키워드 목록
	 * @param goods
	 * @return
	 * @throws Exception
	 */
	List<?> selectRelationGoodsKeywordList(GoodsVO goods) throws Exception;
}
