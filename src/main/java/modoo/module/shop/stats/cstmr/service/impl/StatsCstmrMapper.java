package modoo.module.shop.stats.cstmr.service.impl;

import java.util.List;

import egovframework.rte.psl.dataaccess.mapper.Mapper;
import modoo.module.shop.stats.cstmr.service.StatsCstmrSearchVO;

@Mapper("statsCstmrMapper")
public interface StatsCstmrMapper {

	/**
	 * 고객 요일별 분석목록
	 * @param searchVO
	 * @return
	 * @throws Exception
	 */
	List<?> selectStatsCstmrWeekList(StatsCstmrSearchVO searchVO) throws Exception;
	
	/**
	 * 고객 시간별 분석목록
	 * @param searchVO
	 * @return
	 * @throws Exception
	 */
	List<?> selectStatsCstmrHourList(StatsCstmrSearchVO searchVO) throws Exception;
	
	/**
	 * 고객 배송지역별 분석 목록
	 * @param searchVO
	 * @return
	 * @throws Exception
	 */
	List<?> selectStatsCstmrAreaList(StatsCstmrSearchVO searchVO) throws Exception;
	
	/**
	 * Ez 포인트 사용분석 목록
	 * @param searchVO
	 * @return
	 * @throws Exception
	 */
	List<?> selectStatsCstmrEzpointList(StatsCstmrSearchVO searchVO) throws Exception;
}
