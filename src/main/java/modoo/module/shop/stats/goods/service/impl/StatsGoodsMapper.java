package modoo.module.shop.stats.goods.service.impl;

import java.util.List;

import egovframework.rte.psl.dataaccess.mapper.Mapper;
import modoo.module.shop.stats.goods.service.StatsGoodsSearchVO;

@Mapper("statsGoodsMapper")
public interface StatsGoodsMapper {

	/**
	 * 상품매출 순위 목록
	 * @param searchVO
	 * @return
	 * @throws Exception
	 */
	List<?> selectStatsGoodsSelngList(StatsGoodsSearchVO searchVO) throws Exception;
	
	/**
	 * 업체매출 순위 목록
	 * @param searchVO
	 * @return
	 * @throws Exception
	 */
	List<?> selectStatsCmpnySelngList(StatsGoodsSearchVO searchVO) throws Exception;
	
	/**
	 * 상품별만족도 순위 목록
	 * @param searchVO
	 * @return
	 * @throws Exception
	 */
	List<?> selectStatsGoodsStsfdgSelngList(StatsGoodsSearchVO searchVO) throws Exception;
}
