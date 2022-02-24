package modoo.module.board.service.impl;

import java.util.List;

import modoo.module.board.service.BoardArticleVO;
import egovframework.rte.psl.dataaccess.mapper.Mapper;

@Mapper("boardArticleMapper")
public interface BoardArticleMapper {

	/**
	 * 게시글 목록
	 * @param searchVO
	 * @return
	 * @throws Exception
	 */
	List<?> selectBoardArticleList(BoardArticleVO searchVO) throws Exception;

	/**
	 * 게시글 목록 카운트
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
	 * 게시글 저장
	 * @param boardArticle
	 * @throws Exception
	 */
	void insertBoardArticle(BoardArticleVO article) throws Exception;
	
	/**
	 * 답글 저장
	 * @param article
	 * @throws Exception
	 */
	void insertReplyBoardArticle(BoardArticleVO article) throws Exception;
	
	/**
	 * 게시물 상세
	 * @param boardArticle
	 * @return
	 * @throws Exception
	 */
	BoardArticleVO selectBoardArticle(BoardArticleVO article) throws Exception;
	
	/**
	 * 게시글 수정
	 * @param boardArticle
	 * @throws Exception
	 */
	void updateBoardArticle(BoardArticleVO article) throws Exception;
	
	/**
	 * 게시글 삭제
	 * @param boardArticle
	 * @throws Exception
	 */
	void deleteBoardArticle(BoardArticleVO article) throws Exception;
	
	/**
	 * 부모글의 NTT_NO
	 * @param article
	 * @return
	 * @throws Exception
	 */
	Long getParentNttNo(BoardArticleVO article) throws Exception;
	
	/**
	 * 다른글 NTT_NO + 1
	 * @param article
	 * @throws Exception
	 */
	void updateOtherNttNo(BoardArticleVO article) throws Exception;
	
	/**
	 * 게시글 NTT_NO 수정
	 * @param boardArticle
	 * @throws Exception
	 */
	void updateNttNo(BoardArticleVO boardArticle) throws Exception;
}
