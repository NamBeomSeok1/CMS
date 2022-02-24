package modoo.cms.comment.web;

import java.util.List;

import javax.annotation.Resource;
import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import modoo.module.comment.service.CommentService;
import modoo.module.comment.service.CommentVO;
import modoo.module.common.service.JsonResult;
import modoo.module.common.util.CommonUtils;
import modoo.module.common.web.CommonDefaultController;
import egovframework.com.cmm.LoginVO;
import egovframework.com.cmm.util.EgovUserDetailsHelper;
import egovframework.rte.ptl.mvc.tags.ui.pagination.PaginationInfo;

@Controller
public class CmsCommentController extends CommonDefaultController {

	private static final Logger LOGGER = LoggerFactory.getLogger(CmsCommentController.class);
	
	@Resource(name = "commentService")
	private CommentService commentService;
	
	@RequestMapping(value = "/decms/comment/commentManage.do")
	public String commentManage(CommentVO searchVO, Model model) throws Exception {
		
		return "modoo/cms/comment/commentManage";
	}

	/**
	 * 댓글 목록
	 * @param searchVO
	 * @return
	 */
	@RequestMapping(value = "/decms/comment/commentList.json")
	@ResponseBody
	public JsonResult commentList(CommentVO searchVO) {
		JsonResult jsonResult = new JsonResult();
		LoginVO user = (LoginVO)EgovUserDetailsHelper.getAuthenticatedUser();
		searchVO.setWrterId(user.getId());
		
		try {
			PaginationInfo paginationInfo = new PaginationInfo();
			searchVO.setPageUnit(propertiesService.getInt("gridPageUnit"));
			this.setPagination(paginationInfo, searchVO);
			
			List<?> resultList = commentService.selectCommentList(searchVO);
			jsonResult.put("resultList", resultList);
			
			int totalRecordCount = commentService.selectCommentListCnt(searchVO);
			paginationInfo.setTotalRecordCount(totalRecordCount);
			jsonResult.put("paginationInfo", paginationInfo);
			
			jsonResult.setSuccess(true);
			
		} catch(Exception e) {
			loggerError(LOGGER, e);
			jsonResult.setSuccess(false);
			jsonResult.setMessage(egovMessageSource.getMessage("fail.common.select")); //조회에 실패하였습니다.
		}

		return jsonResult;
	}
	
	/**
	 * 댓글 저장
	 * @param comment
	 * @param bindingResult
	 * @return
	 */
	@RequestMapping(value = "/decms/comment/writeComment.json", method = RequestMethod.POST)
	@ResponseBody
	public JsonResult writeComment(@Valid CommentVO comment, BindingResult bindingResult) {
		JsonResult jsonResult = new JsonResult();
		LoginVO user = (LoginVO)EgovUserDetailsHelper.getAuthenticatedUser();
		
		try {
			if(!this.isHasErrorsJson(bindingResult, jsonResult, "<br/>")) {

				if(StringUtils.isEmpty(comment.getCntntsId()) || StringUtils.isEmpty(comment.getCntntsSeCode())) {
					this.vaildateMessage(egovMessageSource.getMessage("cms.fail.access"), jsonResult); // 잘못된 접근입니다.
					LOGGER.error("cntntsId 또는 cntntsSeCode 가 없음.");
				}else {
					if(comment.getCommentParntId() == null) {
						comment.setCommentParntId(0L);
						comment.setCommentNo(1L);
						comment.setCommentReplyLc(0);
					}
					comment.setFrstRegisterId(user.getUniqId());
					comment.setWrterId(user.getId());
					comment.setCommentCn(CommonUtils.unscript(comment.getCommentCn()));
					if(StringUtils.isEmpty(comment.getSecretAt()) || !"Y".equals(comment.getSecretAt())) {
						comment.setSecretAt("N"); // 비밀글 여부 N
					}
					commentService.insertComment(comment);
					
					jsonResult.setSuccess(true);
				}
			}
		} catch(Exception e) {
			loggerError(LOGGER, e);
			jsonResult.setSuccess(false);
			jsonResult.setMessage(egovMessageSource.getMessage("fail.common.insert")); //생성이 실패하였습니다.
		}
		
		return jsonResult;
	}
	
	/**
	 * 댓글 수정
	 * @param comment
	 * @param bindingResult
	 * @return
	 */
	@RequestMapping(value = "/decms/comment/modifyComment.json", method = RequestMethod.POST)
	@ResponseBody
	public JsonResult modifyComment(@Valid CommentVO comment, BindingResult bindingResult) {
		JsonResult jsonResult = new JsonResult();
		LoginVO user = (LoginVO)EgovUserDetailsHelper.getAuthenticatedUser();
		
		try {
			if(!this.isHasErrorsJson(bindingResult, jsonResult, "<br/>")) {
				if(comment.getCommentId() == null) {
					this.vaildateMessage(egovMessageSource.getMessage("cms.fail.access"), jsonResult); // 잘못된 접근입니다.
					LOGGER.error("commentId 가 없음.");
				}else {
					comment.setLastUpduserId(user.getUniqId());
					comment.setCommentCn(CommonUtils.unscript(comment.getCommentCn()));
					if(StringUtils.isEmpty(comment.getSecretAt()) || !"Y".equals(comment.getSecretAt())) {
						comment.setSecretAt("N"); // 비밀글 여부 N
					}
					commentService.updateComment(comment);
					
					jsonResult.setSuccess(true);
					jsonResult.put("result", comment);
				}
			}
			
		} catch(Exception e) {
			loggerError(LOGGER, e);
			jsonResult.setSuccess(false);
			jsonResult.setMessage(egovMessageSource.getMessage("fail.common.update")); //수정이 실패하였습니다.
		}
		
		return jsonResult;
	}
	
	/**
	 * 댓글 삭제
	 * @param comment
	 * @return
	 */
	@RequestMapping(value = "/decms/comment/deleteComment.json", method = RequestMethod.POST)
	@ResponseBody
	public JsonResult deleteComment(CommentVO comment) {
		JsonResult jsonResult = new JsonResult();
		LoginVO user = (LoginVO)EgovUserDetailsHelper.getAuthenticatedUser();
		
		try {
			if(comment.getCommentId() == null) {
				this.vaildateMessage(egovMessageSource.getMessage("cms.fail.access"), jsonResult); // 잘못된 접근입니다.
				LOGGER.error("commentId 가 없음.");
			}else {
				comment.setLastUpduserId(user.getUniqId());
				commentService.deleteComment(comment);
				
				jsonResult.setSuccess(true);
				jsonResult.setMessage(egovMessageSource.getMessage("success.common.delete")); //정상적으로 삭제되었습니다
			}
			
		} catch(Exception e) {
			loggerError(LOGGER, e);
			jsonResult.setSuccess(false);
			jsonResult.setMessage(egovMessageSource.getMessage("fail.common.delete")); //삭제가 실패하였습니다.
		}
		
		return jsonResult;
	}
}
