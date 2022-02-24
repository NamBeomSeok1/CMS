package modoo.module.shop.stats.cstmr.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;
import modoo.module.shop.stats.cstmr.service.StatsCstmrSearchVO;
import modoo.module.shop.stats.cstmr.service.StatsCstmrService;

@Service("statsCstmrService")
public class StatsCstmrServiceImpl extends EgovAbstractServiceImpl implements StatsCstmrService {

	@Resource(name = "statsCstmrMapper")
	private StatsCstmrMapper statsCstmrMapper;

	/**
	 * 고객 요일별 분석목록
	 */
	@Override
	public List<?> selectStatsCstmrWeekList(StatsCstmrSearchVO searchVO) throws Exception {
		return statsCstmrMapper.selectStatsCstmrWeekList(searchVO);
	}

	/**
	 * 고객 시간대별 분석목록
	 */
	@Override
	public List<?> selectStatsCstmrHourList(StatsCstmrSearchVO searchVO) throws Exception {
		return statsCstmrMapper.selectStatsCstmrHourList(searchVO);
	}

	/**
	 * 고객 배송지역별 분석 목록
	 */
	@Override
	public List<?> selectStatsCstmrAreaList(StatsCstmrSearchVO searchVO) throws Exception {
		return statsCstmrMapper.selectStatsCstmrAreaList(searchVO);
	}

	/**
	 * Ez 포인트 사용분석 목록
	 */
	@Override
	public List<?> selectStatsCstmrEzpointList(StatsCstmrSearchVO searchVO) throws Exception {
		return statsCstmrMapper.selectStatsCstmrEzpointList(searchVO);
	}

}
