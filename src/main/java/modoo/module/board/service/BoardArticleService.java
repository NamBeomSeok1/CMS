package modoo.module.board.service;

import java.util.List;

public interface BoardArticleService {
	
	/**
	 * 게시물 목록
	 * @param searchVO
	 * @return
	 * @throws Exception
	 */
	List<?> selectBoardArticleList(BoardArticleVO searchVO) throws Exception;

	/**
	 * 게시물 목록 카운트
	 * @param searchVO
	 * @return
	 * @throws Exception
	 */
	int selectBoardArticleListCnt(BoardArticleVO searchVO) throws Exception;
	
	/**
	 * 공지사항 목록
	 * @param searchVO
	 * @return
	 * @throws Exception
	 */
	List<?> selectBoardArticleNoticeList(BoardArticleVO searchVO) throws Exception;
	
	/**
	 * 게시물 저장
	 * @param boardArticle
	 * @throws Exception
	 */
	void insertBoardArticle(BoardArticleVO article) throws Exception;
	
	/**
	 * 게시물 상세
	 * @param boardArticle
	 * @return
	 * @throws Exception
	 */
	BoardArticleVO selectBoardArticle(BoardArticleVO article) throws Exception;
	
	/**
	 * 게시물 수정
	 * @param boardArticle
	 * @throws Exception
	 */
	void updateBoardArticle(BoardArticleVO article) throws Exception;
	
	/**
	 * 게시물 삭제
	 * @param boardArticle
	 * @throws Exception
	 */
	void deleteBoardArticle(BoardArticleVO article) throws Exception;

}
