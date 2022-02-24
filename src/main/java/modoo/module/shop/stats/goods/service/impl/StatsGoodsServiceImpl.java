package modoo.module.shop.stats.goods.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;
import modoo.module.shop.stats.goods.service.StatsGoodsSearchVO;
import modoo.module.shop.stats.goods.service.StatsGoodsService;

@Service("statsGoodsService")
public class StatsGoodsServiceImpl extends EgovAbstractServiceImpl implements StatsGoodsService {
	
	@Resource(name = "statsGoodsMapper")
	private StatsGoodsMapper statsGoodsMapper;

	/**
	 * 상품매출 순위 목록
	 */
	@Override
	public List<?> selectStatsGoodsSelngList(StatsGoodsSearchVO searchVO) throws Exception {
		return statsGoodsMapper.selectStatsGoodsSelngList(searchVO);
	}

	/**
	 * 업체매출 순위 목록
	 */
	@Override
	public List<?> selectStatsCmpnySelngList(StatsGoodsSearchVO searchVO) throws Exception {
		return statsGoodsMapper.selectStatsCmpnySelngList(searchVO);
	}

	/**
	 * 상품별만족도 순위 목록
	 */
	@Override
	public List<?> selectStatsGoodsStsfdgSelngList(StatsGoodsSearchVO searchVO) throws Exception {
		return statsGoodsMapper.selectStatsGoodsStsfdgSelngList(searchVO);
	}

}
