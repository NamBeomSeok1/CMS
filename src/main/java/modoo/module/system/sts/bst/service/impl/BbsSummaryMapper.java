package modoo.module.system.sts.bst.service.impl;

import java.util.List;

import modoo.module.system.sts.bst.service.BbsSummaryVO;
import egovframework.rte.psl.dataaccess.mapper.Mapper;

@Mapper("bbsSummaryMapper")
public interface BbsSummaryMapper {
	
	/**
	 * 게시물 통계 집계 
	 */
	void insertBbsSummaryStats(BbsSummaryVO bst) throws Exception;
	
	/**
	 * 게시물통계 날짜별 카운트
	 * @param bst
	 * @return
	 */
	int selectBbsSummaryCheckDateCnt(BbsSummaryVO bst) throws Exception;
	
	/**
	 * 게시물 통계 목록
	 * @param searchVO
	 * @return
	 */
	List<BbsSummaryVO> selectBbsSummaryStats(BbsSummaryVO searchVO) throws Exception;

}
