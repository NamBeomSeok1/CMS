package modoo.module.shop.idsrts.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;
import modoo.module.shop.idsrts.service.IdsrtsService;
import modoo.module.shop.idsrts.service.IdsrtsVO;

@Service("idsrtsService")
public class IdsrtsServiceImpl extends EgovAbstractServiceImpl implements IdsrtsService {
	
	@Resource(name = "idsrtsMapper")
	private IdsrtsMapper idsrtsMapper;
	
	/**
	 * 도서산간목록
	 */
	@Override
	public List<IdsrtsVO> selectIdsrtsList(IdsrtsVO searchVO) throws Exception {
		return idsrtsMapper.selectIdsrtsList(searchVO);
	}

	/**
	 * 도서산간목록 카운트
	 */
	@Override
	public int selectIdsrtsListCnt(IdsrtsVO searchVO) throws Exception {
		return idsrtsMapper.selectIdsrtsListCnt(searchVO);
	}

	//도서산간저장
	@Override
	public void insertIdsrts(IdsrtsVO searchVO) throws Exception {
		idsrtsMapper.insertIdsrts(searchVO);
	}

	//도서산간상세
	@Override
	public IdsrtsVO selectIdsrts(IdsrtsVO searchVO) throws Exception {
		return idsrtsMapper.selectIdsrts(searchVO);
	}

	//도서산간수정
	@Override
	public void updateIdsrts(IdsrtsVO searchVO) throws Exception {
		idsrtsMapper.updateIdsrts(searchVO);
	}

	//도서산간삭제
	@Override
	public void deleteIdsrts(IdsrtsVO searchVO) throws Exception {
		idsrtsMapper.deleteIdsrts(searchVO);
	}

	//도서산간 체크 카운트
	@Override
	public int selectIdsrtsCheckCnt(IdsrtsVO searchVO) throws Exception {
		return idsrtsMapper.selectIdsrtsCheckCnt(searchVO);
	}

}
