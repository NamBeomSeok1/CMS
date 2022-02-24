package modoo.module.system.sts.bst.service;

import java.util.List;

public interface BbsSummaryService {

	/**
	 * 게시물 통계 집계 
	 */
	void insertBbsSummaryStats(BbsSummaryVO bst) throws Exception;
	
	/**
	 * 게시물 통계 목록
	 * @param searchVO
	 * @return
	 */
	List<BbsSummaryVO> selectBbsSummaryStats(BbsSummaryVO searchVO) throws Exception;
}
