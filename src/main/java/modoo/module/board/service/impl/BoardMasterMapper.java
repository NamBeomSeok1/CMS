package modoo.module.board.service.impl;

import java.util.List;

import modoo.module.board.service.BoardMasterVO;
import egovframework.rte.psl.dataaccess.mapper.Mapper;

@Mapper("boardMasterMapper")
public interface BoardMasterMapper {

	/**
	 * 게시판마스터 목록
	 * @param searchVO
	 * @return
	 * @throws Exception
	 */
	List<?> selectBoardMasterList(BoardMasterVO searchVO) throws Exception;
	
	/**
	 * 게시판마스터 목록 카운트
	 * @param searchVO
	 * @return
	 * @throws Exception
	 */
	int selectBoardMasterListCnt(BoardMasterVO searchVO) throws Exception;
	
	/**
	 * 게시판마스터 저장
	 * @param boardMaster
	 * @throws Exception
	 */
	void insertBoardMaster(BoardMasterVO boardMaster) throws Exception;
	
	/**
	 * 게시판마스터 상세
	 * @param boardMaster
	 * @return
	 * @throws Exception
	 */
	BoardMasterVO selectBoardMaster(BoardMasterVO boardMaster) throws Exception;
	
	/**
	 * 게시판마스터 수정
	 * @param boardMaster
	 * @throws Exception
	 */
	void updateBoardMaster(BoardMasterVO boardMaster) throws Exception;

	/**
	 * 게시판마스터 삭제
	 * @param boardMaster
	 * @throws Exception
	 */
	void deleteBoardMaster(BoardMasterVO boardMaster) throws Exception;
}
