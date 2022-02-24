package modoo.module.best.service.impl;

import java.util.List;

import egovframework.rte.psl.dataaccess.mapper.Mapper;

@Mapper("bestMapper")
public interface BestMapper {

	void deleteBestList(BestVO searchVO);

	void insertBest(BestVO bestVO);

	List<?> selectBestList(BestVO searchVO);

	int selectBestListCnt(BestVO searchVO);

	BestVO selectBest(BestVO searchVO);

	Integer selectNextBestNo();

	void updateBest(BestVO bestVO);

	void deleteBestImg(BestVO bestVO);

}
