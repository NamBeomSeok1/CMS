package modoo.cms.shop.goods.review.web;

import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import egovframework.com.cmm.LoginVO;
import egovframework.com.cmm.util.EgovUserDetailsHelper;
import egovframework.rte.ptl.mvc.tags.ui.pagination.PaginationInfo;
import modoo.module.comment.service.CommentService;
import modoo.module.comment.service.CommentVO;
import modoo.module.common.service.JsonResult;
import modoo.module.common.web.CommonDefaultController;

@Controller
public class CmsReviewController extends CommonDefaultController {

	private static final Logger LOGGER = LoggerFactory.getLogger(CmsReviewController.class);

	@Resource(name = "commentService")
	private CommentService commentService;
	
	/**
	 * 상품평관리
	 * @param searchVO
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/decms/shop/goods/review/reviewManage.do")
	public String reviewManage(@ModelAttribute("searchVO")CommentVO searchVO, Model model) throws Exception {
		return "modoo/cms/shop/goods/review/reviewManage";
	}
	
	/**
	 * 상품평목록
	 * @param searchVO
	 * @return
	 */
	@RequestMapping(value = "/decms/shop/goods/review/reviewList.json", method=RequestMethod.GET)
	@ResponseBody
	public JsonResult reviewList(CommentVO searchVO) {
		JsonResult jsonResult = new JsonResult();
		LoginVO user = (LoginVO)EgovUserDetailsHelper.getAuthenticatedUser();

		if(!EgovUserDetailsHelper.getAuthorities().contains("ROLE_EMPLOYEE")) {
			if(StringUtils.isEmpty(user.getCmpnyId())) { //업체 매핑이 안되어 있으면.
				jsonResult.setMessage(egovMessageSource.getMessage("cms.fail.not.cmpnyId")); //업체등록이 필요합니다.
				jsonResult.setSuccess(false);
				return jsonResult;
			}
			searchVO.setSearchCmpnyId(user.getCmpnyId());
		}
		
		try {
//			searchVO.setSearchCntntsSeCode("STN_ORDER"); //리뷰

			PaginationInfo paginationInfo = new PaginationInfo();
			searchVO.setPageUnit(propertiesService.getInt("gridPageUnit"));
			this.setPagination(paginationInfo, searchVO);
			
			List<?> resultList = commentService.selectReviewList(searchVO);
			jsonResult.put("list", resultList);
			
			int totalRecordCount = commentService.selectReviewListCnt(searchVO);
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
	 * 리뷰 삭제
	 */
	@RequestMapping(value = "/decms/shop/goods/review/deleteReview.json")
	@ResponseBody
	public JsonResult deleteComment(@RequestParam(value="commentId", required=false) Long commentId) throws Exception {
		JsonResult jsonResult = new JsonResult();
		LoginVO user = (LoginVO)EgovUserDetailsHelper.getAuthenticatedUser();
		CommentVO comment = new CommentVO();
		comment.setCommentId(commentId);
		comment.setLastUpduserId(user.getUniqId());
		
		try {
			commentService.deleteComment(comment);
			jsonResult.setSuccess(true);
			jsonResult.setMessage(egovMessageSource.getMessage("success.common.delete")); // 정상적으로 삭제되었습니다. src/main/resources/egovframework/message/com/message-common_ko.properties
		}catch(Exception e) {
			LOGGER.error("Exception : " + e);
			jsonResult.setSuccess(false);
			jsonResult.setMessage(egovMessageSource.getMessage("fail.common.delete")); //삭제가 실패하였습니다.
		}

		return jsonResult;
	}

}
