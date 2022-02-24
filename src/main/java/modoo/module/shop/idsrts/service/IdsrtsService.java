package modoo.module.shop.idsrts.service;

import java.util.List;

public interface IdsrtsService {

	//도서산간 목록
	List<IdsrtsVO> selectIdsrtsList(IdsrtsVO searchVO) throws Exception;
	
	//도서산간 목록 카운트
	int selectIdsrtsListCnt(IdsrtsVO searchVO) throws Exception;
	
	//도서산간저장
	void insertIdsrts(IdsrtsVO searchVO) throws Exception;
	
	 //도서산간상세
	IdsrtsVO selectIdsrts(IdsrtsVO searchVO) throws Exception;
	
	 //도서산간수정
	void updateIdsrts(IdsrtsVO searchVO) throws Exception;
	
	//도서산간삭제
	void deleteIdsrts(IdsrtsVO searchVO) throws Exception;
	
	//도서산간 체크 카운트
	int selectIdsrtsCheckCnt(IdsrtsVO searchVO) throws Exception;
}
