package modoo.module.shop.stats.selng.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;
import modoo.module.common.service.CommonDefaultSearchVO;
import modoo.module.shop.stats.selng.service.StatsSelngService;

@Service("statsSelngService")
public class StatsSelngServiceImpl extends EgovAbstractServiceImpl implements StatsSelngService {
	
	@Resource(name = "statsSelngMapper")
	private StatsSelngMapper statsSelngMapper;

	/**
	 * 일별주문 목록
	 */
	@Override
	public List<?> selectStatsSelngDayList(CommonDefaultSearchVO searchVO) throws Exception {
		return statsSelngMapper.selectStatsSelngDayList(searchVO);
	}

	/**
	 * 일별매출 목록
	 */
	@Override
	public List<?> selectStateSelngList(CommonDefaultSearchVO searchVO) throws Exception {
		return statsSelngMapper.selectStateSelngList(searchVO);
	}

	/**
	 * 요일별 매출 목록
	 */
	@Override
	public List<?> selectStateSelngWeekList(CommonDefaultSearchVO searchVO) throws Exception {
		return statsSelngMapper.selectStateSelngWeekList(searchVO);
	}

	/**
	 * 시간대별 매출 목록
	 */
	@Override
	public List<?> selectStateSelngHourList(CommonDefaultSearchVO searchVO) throws Exception {
		return statsSelngMapper.selectStateSelngHourList(searchVO);
	}

	/**
	 * 주별 주문건수 매출 목록
	 */
	@Override
	public List<?> selectStateSelngMonthWeekList(CommonDefaultSearchVO searchVO) throws Exception {
		return statsSelngMapper.selectStateSelngMonthWeekList(searchVO);
	}

	/**
	 * 월별 주문건수 매출 목록
	 */
	@Override
	public List<?> selectStateSelngMonthList(CommonDefaultSearchVO searchVO) throws Exception {
		return statsSelngMapper.selectStateSelngMonthList(searchVO);
	}

	/**
	 * 연령대별 주문거수 매출 목록
	 */
	@Override
	public List<?> selectStateSelngAgeList(CommonDefaultSearchVO searchVO) throws Exception {
		return statsSelngMapper.selectStateSelngAgeList(searchVO);
	}

	/**
	 * 성별매출 목록
	 */
	@Override
	public List<?> selectStateSelngSexdstnList(CommonDefaultSearchVO searchVO) throws Exception {
		return statsSelngMapper.selectStateSelngSexdstnList(searchVO);
	}

}
