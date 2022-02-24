package modoo.module.best.service;

import java.util.List;

import modoo.module.best.service.impl.BestVO;

public interface BestService {

	void saveBestGoods(BestVO searchVO, List<BestVO> bestList);

	List<?> selectBestList(BestVO searchVO);

	int selectBestListCnt(BestVO searchVO);

	BestVO selectBest(BestVO searchVO);

	Integer selectNextBestNo();

	void insertBest(BestVO bestVO);

	void updateBest(BestVO bestVO);

	void deleteBestImg(BestVO bestVO);

}
