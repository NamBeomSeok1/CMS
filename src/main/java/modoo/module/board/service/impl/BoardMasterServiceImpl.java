package modoo.module.board.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import modoo.module.board.service.BoardMasterService;
import modoo.module.board.service.BoardMasterVO;
import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;
import egovframework.rte.fdl.idgnr.EgovIdGnrService;

@Service("boardMasterService")
public class BoardMasterServiceImpl extends EgovAbstractServiceImpl implements BoardMasterService {

	@Resource(name = "boardMasterMapper")
	private BoardMasterMapper boardMasterMapper;
	
	@Resource(name = "bbsMasterIdGnrService")
	private EgovIdGnrService bbsMasterIdGnrService;

	/**
	 * 게시판마스터 목록
	 */
	@Override
	public List<?> selectBoardMasterList(BoardMasterVO searchVO) throws Exception {
		return boardMasterMapper.selectBoardMasterList(searchVO);
	}

	/**
	 * 게시판마스터 목록 카운트
	 */
	@Override
	public int selectBoardMasterListCnt(BoardMasterVO searchVO) throws Exception {
		return boardMasterMapper.selectBoardMasterListCnt(searchVO);
	}

	/**
	 * 게시판마스터 저장
	 */
	@Override
	public void insertBoardMaster(BoardMasterVO boardMaster) throws Exception {
		String bbsId = bbsMasterIdGnrService.getNextStringId();
		boardMaster.setBbsId(bbsId);
		boardMasterMapper.insertBoardMaster(boardMaster);
	}

	/**
	 * 게시판마스터 상세
	 */
	@Override
	public BoardMasterVO selectBoardMaster(BoardMasterVO boardMaster) throws Exception {
		return boardMasterMapper.selectBoardMaster(boardMaster);
	}

	/**
	 * 게시판마스터 수정
	 */
	@Override
	public void updateBoardMaster(BoardMasterVO boardMaster) throws Exception {
		boardMasterMapper.updateBoardMaster(boardMaster);
	}

	/**
	 * 게시판마스터 삭제
	 */
	@Override
	public void deleteBoardMaster(BoardMasterVO boardMaster) throws Exception {
		boardMasterMapper.deleteBoardMaster(boardMaster);

	}

}
