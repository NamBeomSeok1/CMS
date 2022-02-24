package modoo.module.shop.goods.recomend.service.impl;

import java.util.List;

import egovframework.rte.psl.dataaccess.mapper.Mapper;
import modoo.module.shop.goods.recomend.service.GoodsRecomendVO;

@Mapper("goodsRecomendMapper")
public interface GoodsRecomendMapper {
	
	/**
	 * 추천상품 목록
	 * @param goodsRecomend
	 * @return
	 * @throws Exception
	 */
	List<GoodsRecomendVO> selectGoodsRecomendList(GoodsRecomendVO goodsRecomend) throws Exception;
	
	/**
	 * 추천상품 저장
	 * @param goodsRecomend
	 * @throws Exception
	 */
	void insertGoodsRecomend(GoodsRecomendVO goodsRecomend) throws Exception;
	
	/**
	 * 추천상품 수정
	 * @param goodsRecomend
	 * @throws Exception
	 */
	void updateGoodsRecomend(GoodsRecomendVO goodsRecomend) throws Exception;
	
	/**
	 * 추천상품 삭제
	 * @param goodsRecomend
	 * @throws Exception
	 */
	void deleteGoodsRecomend(GoodsRecomendVO goodsRecomend) throws Exception;

}
