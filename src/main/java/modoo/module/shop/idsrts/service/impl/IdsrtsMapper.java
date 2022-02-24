package modoo.module.shop.idsrts.service.impl;

import java.util.List;

import egovframework.rte.psl.dataaccess.mapper.Mapper;
import modoo.module.shop.idsrts.service.IdsrtsVO;

@Mapper("idsrtsMapper")
public interface IdsrtsMapper {

	//도서산간목록
	List<IdsrtsVO> selectIdsrtsList(IdsrtsVO searchVO) throws Exception;
	
	//도서산간목록 카운트
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
