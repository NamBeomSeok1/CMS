package modoo.module.shop.stats.selng.service.impl;

import java.util.List;

import egovframework.rte.psl.dataaccess.mapper.Mapper;
import modoo.module.common.service.CommonDefaultSearchVO;

@Mapper("statsSelngMapper")
public interface StatsSelngMapper {

	/**
	 * 일별주문 목록
	 * @param searchVO
	 * @return
	 * @throws Exception
	 */
	List<?> selectStatsSelngDayList(CommonDefaultSearchVO searchVO) throws Exception;
	
	/**
	 * 일별매출 목록
	 * @param searchVO
	 * @return
	 * @throws Exception
	 */
	List<?> selectStateSelngList(CommonDefaultSearchVO searchVO) throws Exception;
	
	/**
	 * 요일별 매출 목록
	 * @param searchVO
	 * @return
	 * @throws Exception
	 */
	List<?> selectStateSelngWeekList(CommonDefaultSearchVO searchVO) throws Exception;
	
	/**
	 * 시간대별 매출 목록
	 * @param searchVO
	 * @return
	 * @throws Exception
	 */
	List<?> selectStateSelngHourList(CommonDefaultSearchVO searchVO) throws Exception;
	
	/**
	 * 주별 주문건수 매출 목록
	 * @param searchVO
	 * @return
	 * @throws Exception
	 */
	List<?> selectStateSelngMonthWeekList(CommonDefaultSearchVO searchVO) throws Exception;
	
	/**
	 * 월별 주문건수 매출 목록
	 * @param searchVO
	 * @return
	 * @throws Exception
	 */
	List<?> selectStateSelngMonthList(CommonDefaultSearchVO searchVO) throws Exception;
	
	/**
	 * 연령대별 주문거수 매출 목록
	 * @param searchVO
	 * @return
	 * @throws Exception
	 */
	List<?> selectStateSelngAgeList(CommonDefaultSearchVO searchVO) throws Exception;
	
	/**
	 * 성별매출 목록
	 * @param searchVO
	 * @return
	 * @throws Exception
	 */
	List<?> selectStateSelngSexdstnList(CommonDefaultSearchVO searchVO) throws Exception;
}
