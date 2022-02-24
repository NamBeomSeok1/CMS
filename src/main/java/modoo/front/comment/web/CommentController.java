package modoo.front.comment.web;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import egovframework.com.cmm.LoginVO;
import egovframework.com.cmm.service.EgovFileMngService;
import egovframework.com.cmm.service.FileVO;
import egovframework.com.cmm.util.EgovUserDetailsHelper;
import egovframework.rte.psl.dataaccess.util.EgovMap;
import egovframework.rte.ptl.mvc.tags.ui.pagination.PaginationInfo;
import modoo.module.comment.service.CommentService;
import modoo.module.comment.service.CommentVO;
import modoo.module.common.service.FileMngUtil;
import modoo.module.common.service.JsonResult;
import modoo.module.common.web.CommonDefaultController;
import modoo.module.shop.goods.info.service.GoodsService;
import modoo.module.shop.goods.info.service.GoodsVO;
import modoo.module.shop.goods.order.service.OrderService;
import modoo.module.shop.goods.order.service.OrderVO;

/**
 * 
 * @author 송지유
 *
 */
@Controller("CommentController")
public class CommentController extends CommonDefaultController {

	private static final Logger LOGGER = LoggerFactory.getLogger(CommentController.class);
	
	@Resource(name = "commentService")
	private CommentService commentService;
	
	@Resource(name = "fileMngUtil")
	private FileMngUtil fileMngUtil;
	
	@Resource(name = "EgovFileMngService")
	private EgovFileMngService fileMngService;
	
	@Resource(name = "goodsService")
	private GoodsService goodsService;
	
	@Resource(name = "orderService")
	private OrderService orderService;
	
	/**
	 * 리뷰 목록
	 * @param goods
	 * @param model
	 * @return
	 */
	@RequestMapping("/shop/goods/review/reviewList.do")
	public String reviewList(GoodsVO goods, Model model) {
		boolean isLogin = EgovUserDetailsHelper.isAuthenticated();
		boolean hasRegistered = false;
		CommentVO commentVO = new CommentVO();
		
		if (isLogin) {
			LoginVO loginVO = (LoginVO) EgovUserDetailsHelper.getAuthenticatedUser();
			commentVO.setWrterId(loginVO.getId());
		}

		commentVO.setCntntsId(goods.getGoodsId());
		List<?> userCommentList = commentService.selectUserCommentList(commentVO);
		if (userCommentList.size() > 0) {
			hasRegistered = true;
		}

		model.addAttribute("isLogin", isLogin);
		model.addAttribute("hasRegistered", hasRegistered); //이미 리뷰를 등록했는지
		
		return "modoo/front/shop/goods/info/review/reviewList";
	}

	/**
	 * 리뷰 쓰기 폼
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/shop/goods/review/reviewWrite.do")
	public String reviewWrite(Model model) throws Exception {
		
		return "modoo/front/shop/goods/info/review/reviewWrite";
	}
	
	/**
	 * 리뷰 상세
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/shop/goods/review/reviewView.do")
	public String reviewView(Model model) throws Exception {
		
		return "modoo/front/shop/goods/info/review/reviewView";
	}

	/**
	 * 리뷰 목록
	 * @param searchVO
	 * @return
	 * @throws Exception
	 */

	@ResponseBody
	@RequestMapping(value = "/shop/goods/comment/commentList.json", method=RequestMethod.GET)
	public HashMap<String, Object> getCommentList(CommentVO searchVO) throws Exception {
		HashMap<String, Object> json = new HashMap<String, Object>();
		List<HashMap<String, Object>> resultList = new ArrayList<HashMap<String, Object>>();
		
		System.out.println(" ==================================================================상품평목록(사용자)");
		
		// 페이징 처리
		PaginationInfo paginationInfo = new PaginationInfo();
		this.setPagination(paginationInfo, searchVO);

		searchVO.setCntntsSeCode("STN_ORDER");
		List<?> commentList = commentService.selectCommentList(searchVO); // 목록
		int totalRecordCount = commentService.selectCommentListCnt(searchVO); // 목록 카운트
		paginationInfo.setTotalRecordCount(totalRecordCount);
		
		int avgScore = commentService.selectAvgScore(searchVO);

		for (int i = 0, n=commentList.size(); i<n; i++ ) {
			HashMap<String, Object> item = new HashMap<String, Object>();
			
			CommentVO comment = (CommentVO) commentList.get(i);

			List<?> imgs = new ArrayList<>();
			if(StringUtils.hasText(comment.getAtchFileId())) {
				FileVO fvo = new FileVO();
				fvo.setAtchFileId(comment.getAtchFileId());
				imgs = fileMngService.selectImageFileList(fvo);
			}

			item.put("score", comment.getScore());
			item.put("commentCn", comment.getCommentCn());
			
			item.put("frstRegistPnttm", comment.getFrstRegistPnttm());
			item.put("isWriter", isWriter(comment.getFrstRegisterId()));
			
			if (isWriter(comment.getFrstRegisterId()) == true) {
				item.put("wrterId", comment.getWrterId());	
			} else {
				item.put("wrterId", filterID(comment.getWrterId()));	
			}
			
			item.put("secretAt", comment.getSecretAt());
			item.put("imgs", imgs);
			item.put("options", null);
			item.put("commentId", comment.getCommentId());
			
			resultList.add(item);
		}

		boolean isLogin = EgovUserDetailsHelper.isAuthenticated();
		LoginVO loginVO = (LoginVO) EgovUserDetailsHelper.getAuthenticatedUser();
		GoodsVO goods = new GoodsVO();
		
		if (isLogin) {
			goods.setFrstRegisterId(loginVO.getUniqId());
			searchVO.setFrstRegisterId(loginVO.getUniqId());
		}
		
		goods.setGoodsId(searchVO.getSearchGoodsId());
		OrderVO orderInfo = commentService.selectOrder(goods);

		boolean hasRegistered = false;
		boolean hasOrdered = false;
		
		if ( orderInfo != null ) {
			hasOrdered = true;
			searchVO.setCntntsId(orderInfo.getOrderNo());
			List<?> userCommentList = commentService.selectUserCommentList(searchVO);
			if (userCommentList.size() > 0) {
				hasRegistered = true;
			}
		}
		
		json.put("resultList", resultList);
		json.put("paginationInfo", paginationInfo);
		json.put("avgScore", avgScore);
		json.put("orderInfo", orderInfo);
		
		if (EgovUserDetailsHelper.getAuthorities().contains("ROLE_ADMIN")) {
			json.put("hasRegistered", false);
			json.put("isLogin", true);
			json.put("hasOrdered", true);
		} else {
			json.put("hasRegistered", hasRegistered);
			json.put("isLogin", isLogin);
			json.put("hasOrdered", hasOrdered);
		}
		
		return json;
	}

	/**
	 * 리뷰 등록 시 상품 정보
	 * @param goods
	 * @return
	 * @throws Exception
	 */
	
	@ResponseBody
	@RequestMapping(value = "/shop/goods/comment/goodsInfo.json", method=RequestMethod.GET)
	public HashMap<String, Object> getGoodsInfo(GoodsVO goods) throws Exception {
		HashMap<String, Object> json = new HashMap<String, Object>();
		
		GoodsVO goodsInfo = goodsService.selectGoods(goods);

		if (goodsInfo != null) {
			json.put("goodsNm", goodsInfo.getGoodsNm());
			json.put("goodsPc", goodsInfo.getGoodsPc());
			json.put("goodsTitleImageThumbPath", goodsInfo.getGoodsTitleImageThumbPath());
		}

		return json;
	}
	
	/**
	 * 리뷰 등록
	 * @param comment
	 * @param bindingResult
	 * @return
	 * @throws Exception
	 */
	
	@ResponseBody
	@RequestMapping(value = "/shop/goods/comment/registComment.do", method=RequestMethod.POST)
	public JsonResult registComment(CommentVO comment,
			final MultipartHttpServletRequest multiRequest,
			BindingResult bindingResult) throws Exception {
		JsonResult jsonResult = new JsonResult();
		LoginVO loginVO = (LoginVO) EgovUserDetailsHelper.getAuthenticatedUser();

		final List<MultipartFile> fileList = multiRequest.getFiles("atchFile");

		for (MultipartFile file : fileList) {
			System.out.println("파일 목록");
			System.out.println(file.getOriginalFilename());
		}
		
		String atchFileId = "";
		if(!fileList.isEmpty()) {
			String prefixPath = File.separator + "REVIEW";
			List<FileVO> files = fileMngUtil.parseFileInf(fileList, "REVIEW_", 0, "", "", prefixPath); // 저장경로 : src/main/resources/egovframework/egovProps/globals.properties -> Globals.fileStorePath 참고
			atchFileId = fileMngService.insertFileInfs(files);
			comment.setAtchFileId(atchFileId); // 첨부파일고유ID
		}
		
		comment.setFrstRegisterId(loginVO.getUniqId());
		comment.setCntntsSeCode("STN_ORDER");
		comment.setSecretAt("N");
		//comment.setWrterId(loginVO.getId());
		comment.setWrterNm(loginVO.getName());
		if(comment.getCommentParntId() == null) {
			comment.setCommentParntId(0L);
			comment.setCommentNo(1L);
			comment.setCommentReplyLc(0);
		}
		
		if(commentService.selectMyReviewCnt(comment) > 0 && !"admin".equals(loginVO.getId())) {
			jsonResult.setMessage("이미 리뷰를 등록 했습니다.");
			jsonResult.setSuccess(false);
			return jsonResult;
		}
		
		try {
			if(!this.isHasErrorsJson(bindingResult, jsonResult,"<br/>")) {
				comment.setSecretAt("N");
				commentService.insertComment(comment);
				jsonResult.setSuccess(true);
				jsonResult.setMessage(egovMessageSource.getMessage("success.common.insert"));
			}
		} catch(Exception e) {
			loggerError(LOGGER, e);
			jsonResult.setSuccess(false);
			jsonResult.setMessage(egovMessageSource.getMessage("fail.common.insert"));
		}

		return jsonResult;
	}
	
	/**
	 * 리뷰 수정
	 * @param comment
	 * @param bindingResult
	 * @return
	 * @throws Exception
	 */
	
	@ResponseBody
	@RequestMapping(value = "/shop/goods/comment/modifyComment.do", method=RequestMethod.POST)
	public JsonResult modifyComment(CommentVO searchVO,
			final MultipartHttpServletRequest multiRequest,
			BindingResult bindingResult) throws Exception {
		JsonResult jsonResult = new JsonResult();
		LoginVO loginVO = (LoginVO) EgovUserDetailsHelper.getAuthenticatedUser();

		final List<MultipartFile> fileList = multiRequest.getFiles("atchFile");
		String atchFileId = searchVO.getAtchFileId();
		String prefixPath = File.separator + "REVIEW";

		if (atchFileId != null) {
			System.out.println("=============================================파일 수정");
			
			FileVO fvo = new FileVO();
			fvo.setAtchFileId(atchFileId);
//			String[] originFileSns = multiRequest.getParameterValues("fileSn");
//
//			if (originFileSns != null) {
//				System.out.println("=============================================기존 파일 삭제, 신규 파일 등록");
//				List<String> originFileSnList = Arrays.asList(originFileSns);
//				List<FileVO> dbFiles = fileMngService.selectFileInfs(fvo);
//
//				for (FileVO dbFile : dbFiles) {
//					if (originFileSnList.indexOf(dbFile.getFileSn()) == -1 ) {
//						System.out.println("다음 파일을 삭제합니다. :" + dbFile.getFileSn());
//						fileMngService.deleteFileInf(dbFile);	
//					}
//				}	
//			} else {
//				System.out.println("=============================================기존 파일 삭제만");
//				List<FileVO> dbFiles = fileMngService.selectFileInfs(fvo);
//				fileMngService.deleteFileInfs(dbFiles);	
//			}
			
			if(!fileList.isEmpty()) {
				int cnt = fileMngService.getMaxFileSN(fvo);
				List<FileVO> files = fileMngUtil.parseFileInf(fileList, "REVIEW_", cnt, searchVO.getAtchFileId(), "", prefixPath);				
				fileMngService.updateFileInfs(files);
			}
	
		} else {
			System.out.println("=============================================파일 신규 등록만");
			if(!fileList.isEmpty()) {
				List<FileVO> files = fileMngUtil.parseFileInf(fileList, "REVIEW_", 0, "", "", prefixPath);
				atchFileId = fileMngService.insertFileInfs(files);	
			}
		}
		searchVO.setAtchFileId(atchFileId);
		searchVO.setLastUpduserId(loginVO.getUniqId());
		searchVO.setSecretAt("N");
		//searchVO.setCommentParntId((long) 0);
		
		try {
			if(!this.isHasErrorsJson(bindingResult, jsonResult,"<br/>")) {
				commentService.updateComment(searchVO);
				jsonResult.setSuccess(true);
				jsonResult.setMessage(egovMessageSource.getMessage("success.common.update"));
			}
		} catch(Exception e) {
			loggerError(LOGGER, e);
			jsonResult.setSuccess(false);
			jsonResult.setMessage(egovMessageSource.getMessage("fail.common.update"));
		}

		return jsonResult;
	}

	/**
	 * 리뷰 상세 정보
	 * @param comment
	 * @return
	 * @throws Exception
	 */
	
	@ResponseBody
	@RequestMapping(value = "/shop/goods/comment/commentDetail.json", method=RequestMethod.GET)
	public HashMap<String, Object> getCommentDetail(CommentVO comment) throws Exception {
		HashMap<String, Object> json = new HashMap<String, Object>();

		CommentVO commentInfo = commentService.selectComment(comment);

		FileVO fvo = new FileVO();
		fvo.setAtchFileId(commentInfo.getAtchFileId());
		List<?> imgs = fileMngService.selectImageFileList(fvo);

		json.put("score", commentInfo.getScore());
		json.put("commentCn", commentInfo.getCommentCn());
		json.put("wrterId", commentInfo.getMberId());
		json.put("frstRegistPnttm", commentInfo.getFrstRegistPnttm());
		json.put("isWriter", isWriter(commentInfo.getFrstRegisterId()));
		json.put("secretAt", commentInfo.getSecretAt());
		json.put("mberId", filterID(commentInfo.getMberId()));
		json.put("atchFileId", commentInfo.getAtchFileId());
		json.put("imgs", imgs);
		json.put("commentId", commentInfo.getCommentId());
		json.put("cntntsId", commentInfo.getCntntsId());
		
		
		return json;
	}
	
	/**
	 * 리뷰 삭제
	 */
	@RequestMapping(value = "/shop/goods/comment/deleteComment.do", method=RequestMethod.POST)
	@ResponseBody
	public JsonResult deleteComment(CommentVO comment) throws Exception {
		JsonResult jsonResult = new JsonResult();
		LoginVO user = (LoginVO)EgovUserDetailsHelper.getAuthenticatedUser();
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
	
	/**
	 * 내가 작성한 리뷰 목록
	 * @param searchVO
	 * @return
	 * @throws Exception
	 */
	
	@ResponseBody
	@RequestMapping(value="/shop/goods/review/myReviewList.json", method=RequestMethod.GET)
	public HashMap<String, Object> myReviewList(CommentVO searchVO) throws Exception {
		HashMap<String, Object> json = new HashMap<String, Object>();

		boolean isLogin = EgovUserDetailsHelper.isAuthenticated();
		GoodsVO goods = new GoodsVO();
		if (isLogin) {
			LoginVO loginVO = (LoginVO) EgovUserDetailsHelper.getAuthenticatedUser();
			//searchVO.setOrdrrId(loginVO.getId());
			searchVO.setFrstRegisterId(loginVO.getUniqId());
		}

		PaginationInfo paginationInfo = new PaginationInfo(); // 페이징 처리

		this.setPagination(paginationInfo, searchVO);
		//searchVO.setSearchCntntsSeCode("STN_ORDER");

		List<?> commentList = commentService.selectMyCommentList(searchVO);
		int totalRecordCount = commentService.selectMyCommentListCnt(searchVO);
		paginationInfo.setTotalRecordCount(totalRecordCount);
		
		List<HashMap<String, Object>> resultList = new ArrayList<HashMap<String, Object>>();
		
		try {
			
			for (int i = 0, n=commentList.size(); i<n; i++ ) {
				HashMap<String, Object> item = new HashMap<String, Object>();
				
				EgovMap comment = (EgovMap) commentList.get(i);

				FileVO fvo = new FileVO();
				
				fvo.setAtchFileId((String)comment.get("atchFileId"));
				List<?> imgs = fileMngService.selectImageFileList(fvo);

				goods.setGoodsId((String)comment.get("searchGoodsId"));

				OrderVO orderInfo = commentService.selectOrder(goods);
				GoodsVO goodsInfo = commentService.selectGoodsInfo(goods);
				
				OrderVO orderItem = new OrderVO();
				orderItem.setOrderNo((String)comment.get("orderNo"));
				List<?> options = orderService.selectOrderItemList(orderItem);
				
				item.put("score", comment.get("score"));
				item.put("commentCn", comment.get("commentCn"));
				item.put("wrterId", comment.get("wrterId"));
				item.put("frstRegistPnttm", comment.get("frstRegistPnttm"));
				item.put("isWriter", isWriter((String)comment.get("frstRegisterId")));
				item.put("secretAt", comment.get("secretAt"));
				item.put("imgs", imgs);
				item.put("options", options);
				item.put("commentId", comment.get("commentId"));
				item.put("orderInfo", orderInfo);
				item.put("goodsInfo", goodsInfo);
				
				resultList.add(item);
			}
		}catch (Exception e) {
			e.printStackTrace();
		}

		json.put("resultList", resultList);
		json.put("paginationInfo", paginationInfo);
		
		return json;
	}
	
	/**
	 * 작성 가능한 리뷰 목록
	 * @param searchVO
	 * @return
	 * @throws Exception
	 */
	
	@ResponseBody
	@RequestMapping(value="/shop/goods/review/myReviewTodoList.json", method=RequestMethod.GET)	
	public HashMap<String, Object> myReviewTodoList(CommentVO searchVO) throws Exception {
		HashMap<String, Object> json = new HashMap<String, Object>();
		
		System.out.println("==========================================작성가능한리뷰");
		
		PaginationInfo paginationInfo = new PaginationInfo();
		this.setPagination(paginationInfo, searchVO);
		
		boolean isLogin = EgovUserDetailsHelper.isAuthenticated();
				
		if (isLogin) {
			LoginVO loginVO = (LoginVO) EgovUserDetailsHelper.getAuthenticatedUser();
			searchVO.setFrstRegisterId(loginVO.getUniqId());
		}
	
		List<?> commentList = commentService.selectMyCommentTodoList(searchVO);
		int totalRecordCount = commentService.selectMyCommentTodoListCnt(searchVO); // 목록 카운트
		paginationInfo.setTotalRecordCount(totalRecordCount);

		json.put("resultList", commentList);
		json.put("paginationInfo", paginationInfo);
		
		return json;
	}
	
	/**
	 * 리뷰 작성자 ID와 현재 조회하는 사용자 ID가 같은지 조회
	 * @param writerId
	 * @return
	 */
	
	public boolean isWriter(String writerId) {
		LoginVO loginVO = (LoginVO) EgovUserDetailsHelper.getAuthenticatedUser();

		if (loginVO != null) {
			if (loginVO.getUniqId().equals(writerId)) {
				return true;
			} else {
				return false;	
			}
		} else {
			return false;
		}
	}
	
	/**
	 * 작성자 ID를 일부만 공개하도록 가공된 스트링을 리턴
	 * @param mberId
	 * @return
	 */
	
	public String filterID(String mberId) {
		String filteredId = mberId.substring(0, 1) + "*****" + mberId.substring(mberId.length()-1, mberId.length());
		return filteredId;
	}	
	
}
