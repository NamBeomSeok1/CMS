package modoo.module.shop.goods.keyword.service.impl;

import java.util.List;

import egovframework.rte.psl.dataaccess.mapper.Mapper;
import modoo.module.shop.goods.info.service.GoodsVO;
import modoo.module.shop.goods.keyword.service.GoodsKeywordVO;

@Mapper("goodsKeywordMapper")
public interface GoodsKeywordMapper {

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
	 * 상품키우ㅗ드 수정
	 * @param goodsKeyword
	 * @throws Exception
	 */
	void updateGoodsKeyword(GoodsKeywordVO goodsKeyword) throws Exception;
	
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
