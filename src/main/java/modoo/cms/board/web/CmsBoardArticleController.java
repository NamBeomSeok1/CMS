package modoo.cms.board.web;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import modoo.module.biztalk.service.BiztalkService;
import modoo.module.biztalk.service.BiztalkVO;
import modoo.module.board.service.BoardArticleService;
import modoo.module.board.service.BoardArticleVO;
import modoo.module.board.service.BoardMasterService;
import modoo.module.board.service.BoardMasterVO;
import modoo.module.common.service.FileMngUtil;
import modoo.module.common.service.JsonResult;
import modoo.module.common.util.CommonUtils;
import modoo.module.common.web.CommonDefaultController;
import modoo.module.shop.cmpny.service.CmpnyService;
import modoo.module.shop.cmpny.service.CmpnyVO;
import egovframework.com.cmm.EgovMessageSource;
import egovframework.com.cmm.LoginVO;
import egovframework.com.cmm.service.EgovFileMngService;
import egovframework.com.cmm.service.EgovFileMngUtil;
import egovframework.com.cmm.service.FileVO;
import egovframework.com.cmm.util.EgovUserDetailsHelper;
import egovframework.com.utl.fcc.service.EgovDateUtil;
import egovframework.rte.ptl.mvc.tags.ui.pagination.PaginationInfo;

@Controller
public class CmsBoardArticleController extends CommonDefaultController {

	private static final Logger LOGGER = LoggerFactory.getLogger(CmsBoardArticleController.class);
	
	/** EgovMessageSource */
	@Resource(name = "egovMessageSource")
	protected EgovMessageSource egovMessageSource;
	
	@Resource(name = "boardMasterService")
	private BoardMasterService boardMasterService;
	
	@Resource(name = "boardArticleService")
	private BoardArticleService boardArticleService;
	
	@Resource(name = "fileMngUtil")
	private FileMngUtil fileMngUtil;
	
	@Resource(name = "EgovFileMngService")
	private EgovFileMngService fileMngService;
	
	@Resource(name = "biztalkService")
	private BiztalkService biztalkService;
	
	@Resource(name = "cmpnyService")
	private CmpnyService cmpnyService;
	
	/**
	 * 권한 여부 
	 * @param boardMaster
	 * @return
	 */
	private Map<String, Object> getBoardAuthAt(BoardMasterVO boardMaster) {
		List<String> authorities = EgovUserDetailsHelper.getAuthorities();
		Map<String, Object> auth = new HashMap<String, Object>();
		
		if(authorities.contains(boardMaster.getListAuthorCode())) {
			auth.put("listAt", "Y");
		}else { auth.put("listAt", "N"); }

		if(authorities.contains(boardMaster.getRedingAuthorCode())) {
			auth.put("redingAt", "Y");
		}else { auth.put("redingAt", "N"); } 
		
		if(authorities.contains(boardMaster.getWritngAuthorCode())) {
			auth.put("writngAt", "Y");
		}else { auth.put("writngAt", "N"); }

		if(authorities.contains(boardMaster.getUpdtAuthorCode())) {
			auth.put("updtAt", "Y");
		}else { auth.put("updtAt", "N"); }

		if(authorities.contains(boardMaster.getDeleteAuthorCode())) {
			auth.put("deleteAt", "Y");
		}else { auth.put("deleteAt", "N"); }

		if(authorities.contains(boardMaster.getReplyAuthorCode())) {
			auth.put("replyAt", "Y");
		}else { auth.put("replyAt", "N"); }

		if(authorities.contains(boardMaster.getDownloadAuthorCode())) {
			auth.put("downloadAt", "Y");
		}else { auth.put("downloadAt", "N"); }

		if(authorities.contains(boardMaster.getCommentAuthorCode())) {
			auth.put("commentAt","Y");
		}else { auth.put("commentAt","N"); }
		
		return auth;
	}
	
	/**
	 * 게시글 관리 (팝업)
	 * @param searchVO
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/decms/embed/board/article/boardManage.do")
	public String boardManage(@ModelAttribute("searchVO") BoardArticleVO searchVO, Model model) throws Exception {
		if(StringUtils.isEmpty(searchVO.getBbsId())) {
			return "modoo/common/error/accessDenied";
		}

		BoardMasterVO boardMaster = new BoardMasterVO();
		boardMaster.setBbsId(searchVO.getBbsId());
		boardMaster = boardMasterService.selectBoardMaster(boardMaster);
		model.addAttribute("boardMaster", boardMaster);
		
		return "modoo/cms/board/" + boardMaster.getBbsTmplatCode() + "/boardManage";
	}

	/**
	 * 게시글 목록 페이지
	 * @param searchVO
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/decms/board/article/boardList.do")
	public String boardList(@ModelAttribute("searchVO") BoardArticleVO searchVO, Model model) throws Exception {
		if(StringUtils.isEmpty(searchVO.getBbsId())) {
			return "modoo/common/error/accessDenied";
		}

		BoardMasterVO boardMaster = new BoardMasterVO();
		boardMaster.setBbsId(searchVO.getBbsId());
		boardMaster = boardMasterService.selectBoardMaster(boardMaster);
		model.addAttribute("boardMaster", boardMaster);
		
		//권한 정보
		model.addAttribute("boardAuth", getBoardAuthAt(boardMaster));

		return "modoo/cms/board/" + boardMaster.getBbsTmplatCode() + "/boardList";
	}
	
	/**
	 * 게시글 목록 (json)
	 * @param searchVO
	 * @return
	 */
	@RequestMapping(value = "/decms/board/article/boardList.json")
	@ResponseBody
	public JsonResult boardList(BoardArticleVO searchVO) {
		JsonResult jsonResult = new JsonResult();

		try {
			/* 게시판 설정 정보 : 게시기간 테스트
			BoardMasterVO boardMaster = new BoardMasterVO();
			boardMaster.setBbsId(searchVO.getBbsId());
			boardMaster = boardMasterService.selectBoardMaster(boardMaster);
			searchVO.setSearchUsgpdAt(boardMaster.getUsgpdAt());
			*/
			
			PaginationInfo paginationInfo = new PaginationInfo();
			searchVO.setPageUnit(propertiesService.getInt("gridPageUnit"));
			this.setPagination(paginationInfo, searchVO);
			
			//공지사항 목록
			//List<?> noticeList = boardArticleService.selectBoardArticleNoticeList(searchVO);
			//jsonResult.put("noticeList", noticeList);
			
			List<?> resultList = boardArticleService.selectBoardArticleList(searchVO);
			jsonResult.put("list", resultList);
			
			int totalRecordCount = boardArticleService.selectBoardArticleListCnt(searchVO);
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
	 * 게시글 쓰기 폼
	 * @param searchVO
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/decms/embed/board/article/writeBoardArticle.do")
	public String writeBoardArticle(BoardArticleVO searchVO, Model model) throws Exception {
		//내부 직원 권한이 아닐때 
		if(!EgovUserDetailsHelper.getAuthorities().contains("ROLE_EMPLOYEE")) {
			return "redirect:/decms/index.do";
		}
		
		LoginVO user = (LoginVO)EgovUserDetailsHelper.getAuthenticatedUser();

		if(StringUtils.isEmpty(searchVO.getBbsId())) {
			return "modoo/common/error/accessDenied";
		}

		BoardMasterVO boardMaster = new BoardMasterVO();
		boardMaster.setBbsId(searchVO.getBbsId());
		boardMaster = boardMasterService.selectBoardMaster(boardMaster);
		model.addAttribute("boardMaster", boardMaster);
		
		BoardArticleVO article = new BoardArticleVO();
		article.setBbsId(boardMaster.getBbsId());
		article.setNtcrId(user.getId());
		article.setNtcrNm(user.getName());
		model.addAttribute("article", article);
		
		return "modoo/cms/board/" + boardMaster.getBbsTmplatCode() + "/boardForm";
	}
	
	/**
	 * 게시글 저장
	 * @param article
	 * @return
	 */
	@RequestMapping(value = "/decms/board/article/writeBoardArticle.json")
	@ResponseBody
	public JsonResult writeBoardArticle(final MultipartHttpServletRequest multiRequest,
			@Valid BoardArticleVO article, BindingResult bindingResult) {
		JsonResult jsonResult = new JsonResult();
		LoginVO user = (LoginVO)EgovUserDetailsHelper.getAuthenticatedUser();

		try {
			//내부 직원 권한이 아닐때 
			if(!EgovUserDetailsHelper.getAuthorities().contains("ROLE_EMPLOYEE")) {
				jsonResult.setMessage(egovMessageSource.getMessage("cms.fail.accessDenied")); //접근 권한이 없습니다.
				jsonResult.setSuccess(false);
			}else if(!this.isHasErrorsJson(bindingResult, jsonResult, "<br/>")) {
				
				final List<MultipartFile> fileList = multiRequest.getFiles("atchFile");
				String atchFileId = "";

				if(!fileList.isEmpty()) {
					String prefixPath = "BOARD" + File.separator + article.getBbsId();
					List<FileVO> files = fileMngUtil.parseFileInf(fileList, "BBS_", 0, "", "", prefixPath);
					atchFileId = fileMngService.insertFileInfs(files);
					article.setAtchFileId(atchFileId);
				}
				
				if(StringUtils.isEmpty(article.getNtcrNm())) { article.setNtcrNm(user.getName()); }
				if(StringUtils.isEmpty(article.getNtcrId())) { article.setNtcrId(user.getId()); }

				if(StringUtils.isNotEmpty(article.getNtceBgnde())) {
					article.setNtceBgnde(article.getNtceBgnde().replaceAll("-", ""));
				}else {
					article.setNtceBgnde("19000101");
				}
				if(StringUtils.isNotEmpty(article.getNtceEndde())) {
					article.setNtceEndde(article.getNtceEndde().replaceAll("-", ""));
				}else {
					article.setNtceEndde("99991231");
				}
				
				if(EgovDateUtil.getDaysDiff(article.getNtceBgnde(), article.getNtceEndde()) < 0) {
					jsonResult.setSuccess(false);
					jsonResult.setMessage(egovMessageSource.getMessage("cms.fail.endSmallDate")); //종료일이 시작일 보다 작을 수 없습니다.
				}else {
					
					if(StringUtils.isEmpty(article.getNoticeAt())) article.setNoticeAt("N");
					if(StringUtils.isEmpty(article.getSecretAt())) article.setSecretAt("N");
					article.setReplyAt("N");

					article.setFrstRegisterId(user.getUniqId());
					article.setNttCn(CommonUtils.unscript(article.getNttCn()));
					
					boardArticleService.insertBoardArticle(article);
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
	 * 본사 공지사항 알림톡 발송
	 * @param searchVO
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "/decms/embed/board/article/sendBoardArticleAlimTalk.json", method = RequestMethod.POST)
	public JsonResult sendBoardArticleAlimTalk(BoardArticleVO searchVO) throws Exception {
		JsonResult jsonResult = new JsonResult();
		BoardArticleVO article = boardArticleService.selectBoardArticle(searchVO);
		
		//CP공지일 경우 모든 CP담당자에게 알림톡 발송
		if ("BBSMSTR_0000000000CP".equals(article.getBbsId())) {
			/* 공지사항 알림톡 */
			BiztalkVO bizTalk = new BiztalkVO();
			List<CmpnyVO> contactList = cmpnyService.selectContactList();
			
			System.out.println("=========================" + contactList.toString());
			
			for (CmpnyVO contact : contactList) {
				
				System.out.println("=========================" + contact.toString());
				
				if (contact.getChargerTelno() != null) {
					bizTalk.setRecipient(contact.getChargerTelno());
					bizTalk.setTmplatCode("template_016");
					/*[모두의구독] 관리자가 공지사항을 등록했습니다. 입점업체 담당자 분들께서는 확인 바랍니다.
					 * 공지사항 제목 : #{SUBJECT}
					 */
					BiztalkVO template = biztalkService.selectBizTalkTemplate(bizTalk);
					String message = template.getTmplatCn();
					message = message.replaceAll("#\\{SUBJECT\\}", article.getNttSj());
					bizTalk.setMessage(message);
					
					BiztalkVO result = biztalkService.sendAlimTalk(bizTalk);
				}
				
			}
		
			jsonResult.setSuccess(true);
			jsonResult.setMessage("알림톡 발송이 완료되었습니다.");
		}
		
		return jsonResult;
	}
	
	/**
	 * 게시글 상세 페이지
	 * @param searchVO
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/decms/embed/board/article/viewBoardArticle.do")
	public String viewBoardArticle(BoardArticleVO searchVO, Model model) throws Exception {
		LoginVO user = (LoginVO)EgovUserDetailsHelper.getAuthenticatedUser();
		model.addAttribute("loginUser", user);

		if(searchVO.getNttId() == null) {
			return "modoo/common/error/accessDenied";
		}

		BoardArticleVO article = boardArticleService.selectBoardArticle(searchVO);
		model.addAttribute("article", article);
		

		BoardMasterVO boardMaster = new BoardMasterVO();
		boardMaster.setBbsId(article.getBbsId());
		boardMaster = boardMasterService.selectBoardMaster(boardMaster);
		model.addAttribute("boardMaster", boardMaster);
		
		//권한 정보
		model.addAttribute("boardAuth", getBoardAuthAt(boardMaster));
		
		return "modoo/cms/board/" + boardMaster.getBbsTmplatCode() + "/boardView";
	}
	
	/**
	 * 게시글 수정 폼
	 * @param searchVO
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/decms/embed/board/article/modifyBoardArticle.do")
	public String modifyBoardArticle(BoardArticleVO searchVO, Model model) throws Exception {
		//내부 직원 권한이 아닐때 
		if(!EgovUserDetailsHelper.getAuthorities().contains("ROLE_EMPLOYEE")) {
			return "redirect:/decms/index.do";
		}

		LoginVO user = (LoginVO)EgovUserDetailsHelper.getAuthenticatedUser();

		if(searchVO.getNttId() == null) {
			return "modoo/common/error/accessDenied";
		}

		BoardArticleVO article = boardArticleService.selectBoardArticle(searchVO);
		model.addAttribute("article", article);
		

		BoardMasterVO boardMaster = new BoardMasterVO();
		boardMaster.setBbsId(article.getBbsId());
		boardMaster = boardMasterService.selectBoardMaster(boardMaster);
		model.addAttribute("boardMaster", boardMaster);
		
		return "modoo/cms/board/" + boardMaster.getBbsTmplatCode() + "/boardForm";
	}
	
	/**
	 * 게시글 수정
	 * @param multiRequest
	 * @param article
	 * @param bindingResult
	 * @return
	 */
	@RequestMapping(value = "/decms/board/article/modifyBoardArticle.json", method = RequestMethod.POST)
	@ResponseBody
	public JsonResult modifyBoardArticle(final MultipartHttpServletRequest multiRequest,
			@Valid BoardArticleVO article, BindingResult bindingResult) {
		JsonResult jsonResult = new JsonResult();
		LoginVO user = (LoginVO)EgovUserDetailsHelper.getAuthenticatedUser();

		try {
			if(!this.isHasErrorsJson(bindingResult, jsonResult, "<br/>")) {
				
				final List<MultipartFile> fileList = multiRequest.getFiles("atchFile");
				if(!fileList.isEmpty()) {
					String atchFileId = article.getAtchFileId();
					String prefixPath = "BOARD" + File.separator + article.getBbsId();
					if(StringUtils.isEmpty(atchFileId)) {
						List<FileVO> files = fileMngUtil.parseFileInf(fileList, "BBS_", 0, "", "", prefixPath);
						atchFileId = fileMngService.insertFileInfs(files);
						article.setAtchFileId(atchFileId);
					}else {
						FileVO fvo = new FileVO();
						fvo.setAtchFileId(atchFileId);
						int cnt = fileMngService.getMaxFileSN(fvo);
						List<FileVO> files = fileMngUtil.parseFileInf(fileList, "BBS_", cnt, atchFileId, "", prefixPath);
						fileMngService.updateFileInfs(files);
					}
				}
				
				
				if(StringUtils.isEmpty(article.getNtcrNm())) { article.setNtcrNm(user.getName()); }
				if(StringUtils.isEmpty(article.getNtcrId())) { article.setNtcrId(user.getId()); }

				if(StringUtils.isNotEmpty(article.getNtceBgnde())) {
					article.setNtceBgnde(article.getNtceBgnde().replaceAll("-", ""));
				}else {
					article.setNtceBgnde("19000101");
				}
				if(StringUtils.isNotEmpty(article.getNtceEndde())) {
					article.setNtceEndde(article.getNtceEndde().replaceAll("-", ""));
				}else {
					article.setNtceEndde("99991231");
				}
				
				if(EgovDateUtil.getDaysDiff(article.getNtceBgnde(), article.getNtceEndde()) < 0) {
					jsonResult.setSuccess(false);
					jsonResult.setMessage(egovMessageSource.getMessage("boardArticleVO.fail.checkDate")); //종료일이 시작일 보다 작을 수 없습니다.
				}else {
					if(StringUtils.isEmpty(article.getNoticeAt())) article.setNoticeAt("N");
					if(StringUtils.isEmpty(article.getSecretAt())) article.setSecretAt("N");
				
					article.setLastUpdusrId(user.getUniqId());
					article.setNttCn(CommonUtils.unscript(article.getNttCn()));
					
					boardArticleService.updateBoardArticle(article);
					jsonResult.setSuccess(true);
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
	 * 게시글 답장 폼
	 * @param searchVO
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/decms/embed/board/article/replyBoardArticle.do")
	public String replyBoardArticle(@ModelAttribute("searchVO") BoardArticleVO searchVO, Model model) throws Exception {
		LoginVO user = (LoginVO)EgovUserDetailsHelper.getAuthenticatedUser();
		if(searchVO.getNttId() == null) {
			return "modoo/common/error/accessDenied";
		}
		
		//부모글
		BoardArticleVO pArticle = boardArticleService.selectBoardArticle(searchVO);
		model.addAttribute("pArticle", pArticle);
		
		BoardArticleVO article = new BoardArticleVO();
		article.setNttId(pArticle.getNttId());
		article.setSortOrdr(pArticle.getSortOrdr());
		article.setReplyAt("Y");
		article.setReplyLc(pArticle.getReplyLc());
		//article.setNttSj("RE: " + pArticle.getNttSj());
		article.setBbsId(pArticle.getBbsId());
		article.setNtcrNm(user.getName());
		article.setNtcrId(user.getId());
		model.addAttribute("article", article);
		
		BoardMasterVO boardMaster = new BoardMasterVO();
		boardMaster.setBbsId(pArticle.getBbsId());
		boardMaster = boardMasterService.selectBoardMaster(boardMaster);
		model.addAttribute("boardMaster", boardMaster);
		
		model.addAttribute("writeMode", "reply");
		
		return "modoo/cms/board/" + boardMaster.getBbsTmplatCode() + "/boardForm";
	}
	
	/**
	 * 게시글 답장 저장 
	 * @param article
	 * @return
	 */
	@RequestMapping(value = "/decms/board/article/replyBoardArticle.json", method = RequestMethod.POST)
	@ResponseBody
	public JsonResult replyBoardArticle(final MultipartHttpServletRequest multiRequest,
			@Valid BoardArticleVO article, BindingResult bindingResult) {
		JsonResult jsonResult = new JsonResult();
		LoginVO user = (LoginVO)EgovUserDetailsHelper.getAuthenticatedUser();

		try {
			if(!this.isHasErrorsJson(bindingResult, jsonResult, "<br/>")) {
				
				final List<MultipartFile> fileList = multiRequest.getFiles("atchFile");
				String atchFileId = "";

				if(!fileList.isEmpty()) {
					String prefixPath = "BOARD" + File.separator + article.getBbsId();
					List<FileVO> files = fileMngUtil.parseFileInf(fileList, "BBS_", 0, "", "", prefixPath);
					atchFileId = fileMngService.insertFileInfs(files);
					article.setAtchFileId(atchFileId);
				}
				
				
				if(StringUtils.isEmpty(article.getNtcrNm())) { article.setNtcrNm(user.getName()); }
				if(StringUtils.isEmpty(article.getNtcrId())) { article.setNtcrId(user.getId()); }

				if(StringUtils.isNotEmpty(article.getNtceBgnde())) {
					article.setNtceBgnde(article.getNtceBgnde().replaceAll("-", ""));
				}else {
					article.setNtceBgnde("19000101");
				}
				if(StringUtils.isNotEmpty(article.getNtceEndde())) {
					article.setNtceEndde(article.getNtceEndde().replaceAll("-", ""));
				}else {
					article.setNtceEndde("99991231");
				}
				
				if(EgovDateUtil.getDaysDiff(article.getNtceBgnde(), article.getNtceEndde()) < 0) {
					jsonResult.setSuccess(false);
					jsonResult.setMessage(egovMessageSource.getMessage("boardArticleVO.fail.checkDate")); //종료일이 시작일 보다 작을 수 없습니다.
				}else {
					
					if(StringUtils.isEmpty(article.getNoticeAt())) article.setNoticeAt("N");
					if(StringUtils.isEmpty(article.getSecretAt())) article.setSecretAt("N");

					article.setReplyAt("Y");
					article.setReplyLc(article.getReplyLc() + 1);
					article.setParntscttNo(article.getNttId());
					article.setFrstRegisterId(user.getUniqId());
					article.setNttCn(CommonUtils.unscript(article.getNttCn()));
					
					boardArticleService.insertBoardArticle(article);
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
	 * 게시글 삭제
	 * @param article
	 * @return
	 */
	@RequestMapping(value = "/decms/board/deleteArticle.json", method = RequestMethod.POST)
	@ResponseBody
	public JsonResult deleteArticle(BoardArticleVO article) {
		JsonResult jsonResult = new JsonResult();
		LoginVO user = (LoginVO)EgovUserDetailsHelper.getAuthenticatedUser();
		
		try {
			//내부 직원 권한이 아닐때 
			if(!EgovUserDetailsHelper.getAuthorities().contains("ROLE_EMPLOYEE")) {
				jsonResult.setMessage(egovMessageSource.getMessage("cms.fail.accessDenied")); //접근 권한이 없습니다.
				jsonResult.setSuccess(false);
			}else {
				article.setLastUpdusrId(user.getUniqId());
				boardArticleService.deleteBoardArticle(article);
				
				jsonResult.setSuccess(true);
				jsonResult.setMessage(egovMessageSource.getMessage("success.common.delete")); //정상적으로 삭제되었습니다
			}
			
		} catch(Exception e) {
			loggerError(LOGGER, e);
			jsonResult.setSuccess(false);
			jsonResult.setMessage(egovMessageSource.getMessage("fail.common.delete")); //삭제 실패하였습니다.
		}
		
		return jsonResult;
	}
	
}
