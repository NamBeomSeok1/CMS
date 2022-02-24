package modoo.module.comment.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import modoo.module.comment.service.CommentService;
import modoo.module.comment.service.CommentVO;
import modoo.module.shop.goods.info.service.GoodsVO;
import modoo.module.shop.goods.order.service.OrderVO;
import egovframework.com.cmm.LoginVO;
import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;
import egovframework.rte.fdl.idgnr.EgovIdGnrService;

@Service("commentService")
public class CommentServiceImpl extends EgovAbstractServiceImpl implements CommentService {
	
	@Resource(name = "commentMapper")
	private CommentMapper commentMapper;
	
	@Resource(name = "commentIdGnrService")
	private EgovIdGnrService commentIdGnrService;

	/**
	 * 댓글 목록
	 */
	@Override
	public List<?> selectCommentList(CommentVO searchVO) throws Exception {
		return commentMapper.selectCommentList(searchVO);
	}

	/**
	 * 댓글 목록 카운트
	 */
	@Override
	public int selectCommentListCnt(CommentVO searchVO) throws Exception {
		return commentMapper.selectCommentListCnt(searchVO);
	}

	/**
	 * 댓글 저장
	 */
	@Override
	public void insertComment(CommentVO comment) throws Exception {
		Long commentId = commentIdGnrService.getNextLongId();
		comment.setCommentId(commentId);
		
		if(comment.getCommentParntId() == 0) {
			commentMapper.insertComment(comment);
		}else {
			
		}
	}

	/**
	 * 댓글 상세
	 */
	@Override
	public CommentVO selectComment(CommentVO comment) throws Exception {
		return commentMapper.selectComment(comment);
	}

	/**
	 * 댓글 수정
	 */
	@Override
	public void updateComment(CommentVO comment) throws Exception {
		commentMapper.updateComment(comment);
	}

	/**
	 * 댓글 삭제
	 */
	@Override
	public void deleteComment(CommentVO comment) throws Exception {
		commentMapper.deleteComment(comment);
	}

	@Override
	public int selectAvgScore(CommentVO searchVO) {
		return commentMapper.selectAvgScore(searchVO);
	}

	@Override
	public List<?> selectImageList(CommentVO searchVO) {
		return commentMapper.selectImageList(searchVO);
	}

	@Override
	public OrderVO selectOrder(GoodsVO loginVO) {
		return commentMapper.selectOrder(loginVO);
	}

	@Override
	public GoodsVO selectGoodsInfo(GoodsVO goods) {
		return commentMapper.selectGoodsInfo(goods);
	}

	@Override
	public List<?> selectUserCommentList(CommentVO commentVO) {
		return commentMapper.selectUserCommentList(commentVO);
	}

	@Override
	public void deleteCommentFileRef(CommentVO searchVO) {
		commentMapper.deleteCommentFileRef(searchVO);
	}

	@Override
	public List<?> selectMyCommentList(CommentVO searchVO) {
		return commentMapper.selectMyCommentList(searchVO);
	}

	@Override
	public int selectMyCommentListCnt(CommentVO searchVO) {
		return commentMapper.selectMyCommentListCnt(searchVO);
	}

	@Override
	public List<?> selectMyCommentTodoList(CommentVO searchVO) {
		return commentMapper.selectMyCommentTodoList(searchVO);
	}

	@Override
	public int selectMyCommentTodoListCnt(CommentVO searchVO) {
		return commentMapper.selectMyCommentTodoListCnt(searchVO);
	}


	
	
	/**
	 * 상품평 목록
	 */
	@Override
	public List<?> selectReviewList(CommentVO searchVO) throws Exception {
		return commentMapper.selectReviewList(searchVO);
	}

	/**
	 * 상품평 목록 카운트
	 */
	@Override
	public int selectReviewListCnt(CommentVO searchVO) throws Exception {
		return commentMapper.selectReviewListCnt(searchVO);
	}

	/**
	 * 나의 상품평 등록 카운트
	 */
	@Override
	public int selectMyReviewCnt(CommentVO searchVO) throws Exception {
		return commentMapper.selectMyReviewCnt(searchVO);
	}

}
