package modoo.module.system.sts.bst.service;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;

@Service("bbsStatsScheduling")
public class BbsStatsScheduling extends EgovAbstractServiceImpl {

	@Resource(name = "bbsSummaryService")
	private BbsSummaryService bbsSummaryService;
	
	/**
	 * 게시물 통계를 위한 집계 배치 프로그램
	 * @throws Exception
	 */
	public void summaryBbsStats() throws Exception {
		BbsSummaryVO bst = new BbsSummaryVO();
		bst.setStatsSe("BBS");
		bbsSummaryService.insertBbsSummaryStats(bst);
	}
}
