package modoo.module.comment.service.impl;

import java.util.List;

import modoo.module.comment.service.CommentVO;
import modoo.module.shop.goods.info.service.GoodsVO;
import modoo.module.shop.goods.order.service.OrderVO;
import egovframework.com.cmm.LoginVO;
import egovframework.rte.psl.dataaccess.mapper.Mapper;

@Mapper("commentMapper")
public interface CommentMapper {
	
	/**
	 * 댓글 목록
	 * @param searchVO
	 * @return
	 * @throws Exception
	 */
	List<?> selectCommentList(CommentVO searchVO) throws Exception;
	
	/**
	 * 댓글 목록 카운트
	 * @param searchVO
	 * @return
	 * @throws Exception
	 */
	int selectCommentListCnt(CommentVO searchVO) throws Exception;
	
	/**
	 * 댓글 저장
	 * @param comment
	 * @throws Exception
	 */
	void insertComment(CommentVO comment) throws Exception;
	
	/**
	 * 댓글 상세
	 * @param comment
	 * @return
	 * @throws Exception
	 */
	CommentVO selectComment(CommentVO comment) throws Exception;
	
	/**
	 * 댓글 수정
	 * @param comment
	 * @throws Exception
	 */
	void updateComment(CommentVO comment) throws Exception;
	
	/**
	 * 댓글 삭제
	 * @param comment
	 * @throws Exception
	 */
	void deleteComment(CommentVO comment) throws Exception;

	int selectAvgScore(CommentVO searchVO);

	List<?> selectImageList(CommentVO searchVO);

	List<?> selectOrderList(GoodsVO loginVO);

	GoodsVO selectGoodsInfo(GoodsVO goods);

	List<?> selectUserCommentList(CommentVO commentVO);

	OrderVO selectOrder(GoodsVO loginVO);

	void deleteCommentFileRef(CommentVO searchVO);

	List<?> selectMyCommentList(CommentVO searchVO);

	int selectMyCommentListCnt(CommentVO searchVO);

	List<?> selectMyCommentTodoList(CommentVO searchVO);

	int selectMyCommentTodoListCnt(CommentVO searchVO);
	
	
	
	
	
	
	
	
	
	/**
	 * 상품평 목록
	 * @param searchVO
	 * @return
	 */
	List<?> selectReviewList(CommentVO searchVO) throws Exception;
	
	/**
	 * 상품평 목록 카운트
	 * @param searchVO
	 * @return
	 * @throws Exception
	 */
	int selectReviewListCnt(CommentVO searchVO) throws Exception;
	
	/**
	 * 나의 상품평 등록 카운트
	 * @param searchVO
	 * @return
	 * @throws Exception
	 */
	int selectMyReviewCnt(CommentVO searchVO) throws Exception;

}
