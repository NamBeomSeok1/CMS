package modoo.module.board.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import modoo.module.board.service.BoardArticleService;
import modoo.module.board.service.BoardArticleVO;
import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;
import egovframework.rte.fdl.idgnr.EgovIdGnrService;

@Service("boardArticleService")
public class BoardArticleServiceImpl extends EgovAbstractServiceImpl implements BoardArticleService {
	
	@Resource(name = "boardArticleMapper")
	private BoardArticleMapper boardArticleMapper;
	
	@Resource(name = "bbsNttIdGnrService")
	private EgovIdGnrService bbsNttIdGnrService;

	/**
	 * 게시물 목록
	 */
	@Override
	public List<?> selectBoardArticleList(BoardArticleVO searchVO) throws Exception {
		return boardArticleMapper.selectBoardArticleList(searchVO);
	}

	/**
	 * 게시물 목록 카운트
	 */
	@Override
	public int selectBoardArticleListCnt(BoardArticleVO searchVO) throws Exception {
		return boardArticleMapper.selectBoardArticleListCnt(searchVO);
	}
	
	/**
	 * 공지사항 목록
	 */
	@Override
	public List<?> selectBoardArticleNoticeList(BoardArticleVO searchVO) throws Exception {
		return boardArticleMapper.selectBoardArticleNoticeList(searchVO);
	}

	/**
	 * 게시물 저장
	 */
	@Override
	public void insertBoardArticle(BoardArticleVO article) throws Exception {
		Long nttId = bbsNttIdGnrService.getNextLongId();
		article.setNttId(nttId);

		//답글 여부
		if("Y".equals(article.getReplyAt())) {
			boardArticleMapper.insertReplyBoardArticle(article);

			// 계층형 
			long nttNo = boardArticleMapper.getParentNttNo(article);

			//같은 sortOrdr 글의 NTT_NO 1씩증가
			article.setNttNo(nttNo);
			boardArticleMapper.updateOtherNttNo(article);
			
			article.setNttNo(nttNo + 1);
			boardArticleMapper.updateNttNo(article);
		}else {
			article.setParntscttNo(0L);
			article.setReplyLc(0);
			article.setReplyAt("N");
			boardArticleMapper.insertBoardArticle(article);
		}
	}

	/**
	 * 게시물 상세
	 */
	@Override
	public BoardArticleVO selectBoardArticle(BoardArticleVO article) throws Exception {
		return boardArticleMapper.selectBoardArticle(article);
	}

	/**
	 * 게시물 수정
	 */
	@Override
	public void updateBoardArticle(BoardArticleVO article) throws Exception {
		boardArticleMapper.updateBoardArticle(article);
	}

	/**
	 * 게시물 삭제
	 */
	@Override
	public void deleteBoardArticle(BoardArticleVO article) throws Exception {
		boardArticleMapper.deleteBoardArticle(article);
	}

}
