package modoo.module.shop.stats.goods.service;

import java.util.List;

public interface StatsGoodsService {

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
