package modoo.module.system.sts.bst.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import modoo.module.system.sts.bst.service.BbsSummaryService;
import modoo.module.system.sts.bst.service.BbsSummaryVO;
import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;

@Service("bbsSummaryService")
public class BbsSummaryServiceImpl extends EgovAbstractServiceImpl implements BbsSummaryService {
	
	@Resource(name = "bbsSummaryMapper")
	private BbsSummaryMapper bbsSummaryMapper;

	@Override
	public void insertBbsSummaryStats(BbsSummaryVO bst) throws Exception {
		if(bbsSummaryMapper.selectBbsSummaryCheckDateCnt(bst) == 0) {
			bbsSummaryMapper.insertBbsSummaryStats(bst);
		}
	}

	/**
	 * 게시물 통계 목록
	 */
	@Override
	public List<BbsSummaryVO> selectBbsSummaryStats(BbsSummaryVO searchVO) throws Exception {
		return bbsSummaryMapper.selectBbsSummaryStats(searchVO);
	}

}
