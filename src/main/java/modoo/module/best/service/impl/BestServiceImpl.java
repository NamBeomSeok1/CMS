package modoo.module.best.service.impl;

import java.util.List;

import javax.annotation.Resource;
import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import modoo.module.best.service.BestService;

@Service("bestService")
public class BestServiceImpl implements BestService {
	
	@Resource(name = "bestMapper")
	private BestMapper bestMapper;
	
	@Transactional
	@Override
	public void saveBestGoods(BestVO searchVO, List<BestVO> bestList) {
		bestMapper.deleteBestList(searchVO);
		for (BestVO bestVO : bestList) {
			bestMapper.insertBest(bestVO);
		}
	}

	@Override
	public List<?> selectBestList(BestVO searchVO) {
		return bestMapper.selectBestList(searchVO);
	}

	@Override
	public int selectBestListCnt(BestVO searchVO) {
		return bestMapper.selectBestListCnt(searchVO);
	}

	@Override
	public BestVO selectBest(BestVO searchVO) {
		return bestMapper.selectBest(searchVO);
	}

	@Override
	public Integer selectNextBestNo() {
		return bestMapper.selectNextBestNo();
	}

	@Override
	public void insertBest(BestVO bestVO) {
		bestMapper.insertBest(bestVO);
		
	}

	@Override
	public void updateBest(BestVO bestVO) {
		bestMapper.updateBest(bestVO);
		
	}

	@Override
	public void deleteBestImg(BestVO bestVO) {
		bestMapper.deleteBestImg(bestVO);
		
	}

}
