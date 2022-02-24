package modoo.cms.board.web;

import java.util.List;

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
import org.springframework.web.bind.annotation.ResponseBody;

import modoo.module.board.service.BoardMasterService;
import modoo.module.board.service.BoardMasterVO;
import modoo.module.common.service.JsonResult;
import modoo.module.common.util.SiteDomainHelper;
import modoo.module.common.web.CommonDefaultController;
import modoo.module.mber.author.service.MberAuthorService;
import modoo.module.mber.author.service.MberAuthorVO;
import egovframework.com.cmm.ComDefaultCodeVO;
import egovframework.com.cmm.EgovMessageSource;
import egovframework.com.cmm.LoginVO;
import egovframework.com.cmm.service.EgovCmmUseService;
import egovframework.com.cmm.util.EgovUserDetailsHelper;
import egovframework.rte.ptl.mvc.tags.ui.pagination.PaginationInfo;

@Controller
public class CmsBoardMasterController extends CommonDefaultController {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(CmsBoardMasterController.class);
	
	/** EgovMessageSource */
	@Resource(name = "egovMessageSource")
	protected EgovMessageSource egovMessageSource;
	
	@Resource(name = "boardMasterService")
	private BoardMasterService boardMasterService;
	
	/** EgovCmmUseService */
	@Resource(name = "EgovCmmUseService")
	private EgovCmmUseService cmmUseService;
	
	@Resource(name = "mberAuthorService")
	private MberAuthorService mberAuthorService;
	
	/**
	 * 게시판 관리
	 * @param searchVO
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/decms/board/master/boardMasterManage.do")
	public String boardMasterManage(@ModelAttribute("searchVO") BoardMasterVO searchVO, Model model) throws Exception {
		//내부 직원 권한이 아닐때 
		if(!EgovUserDetailsHelper.getAuthorities().contains("ROLE_EMPLOYEE")) {
			return "redirect:/decms/index.do";
		}	

		return "modoo/cms/board/boardMasterManage";
	}
	
	/**
	 * 게시판 목록
	 * @param searchVO
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/decms/board/master/boardMasterList.json")
	@ResponseBody
	public JsonResult boardMasterList(BoardMasterVO searchVO) {
		JsonResult jsonResult = new JsonResult();
		
		try {
			//내부 직원 권한이 아닐때 
			if(!EgovUserDetailsHelper.getAuthorities().contains("ROLE_EMPLOYEE")) {
				jsonResult.setMessage(egovMessageSource.getMessage("cms.fail.accessDenied")); //접근 권한이 없습니다.
				jsonResult.setSuccess(false);
			}else {
				PaginationInfo paginationInfo = new PaginationInfo();
				searchVO.setPageUnit(propertiesService.getInt("gridPageUnit"));
				this.setPagination(paginationInfo, searchVO);
				
				List<?> resultList = boardMasterService.selectBoardMasterList(searchVO);
				jsonResult.put("list", resultList);
				
				int totalRecordCount = boardMasterService.selectBoardMasterListCnt(searchVO);
				paginationInfo.setTotalRecordCount(totalRecordCount);
				jsonResult.put("paginationInfo", paginationInfo);
				
				jsonResult.setSuccess(true);
			}
		} catch(Exception e) {
			LOGGER.error("Exception : " + e);
			jsonResult.setSuccess(false);
			jsonResult.setMessage(egovMessageSource.getMessage("fail.common.select")); //조회에 실패하였습니다.
		}
		
		return jsonResult;
	}
	
	/**
	 * 게시판 등록 폼
	 * @param searchVO
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/decms/embed/board/master/writeBoardMaster.do")
	public String writeBoardMaster(@ModelAttribute("searchVO") BoardMasterVO searchVO, Model model) throws Exception {
		//내부 직원 권한이 아닐때 
		if(!EgovUserDetailsHelper.getAuthorities().contains("ROLE_EMPLOYEE")) {
			return "redirect:/decms/index.do";
		}	

		BoardMasterVO boardMaster = new BoardMasterVO();
		if(StringUtils.isEmpty(searchVO.getSiteId())) {
			boardMaster.setSiteId(SiteDomainHelper.getSiteId());
		}
		
		//게시판 유형 목록
		ComDefaultCodeVO codeVO = new ComDefaultCodeVO();
		codeVO.setCodeId("CMS003");
		List<?> bbsTmplatCodeList = cmmUseService.selectCmmCodeDetail(codeVO);
		model.addAttribute("bbsTmplatCodeList", bbsTmplatCodeList);
		
		// 사용 권한 목록
		MberAuthorVO ma = new MberAuthorVO();
		List<MberAuthorVO> authorList = mberAuthorService.selectUsedAuthorList(ma);
		model.addAttribute("authorList", authorList);
		
		boardMaster.setNoticeAt("N");
		boardMaster.setSecretAt("N");
		boardMaster.setAnnymtyAt("N");
		boardMaster.setUsgpdAt("N");
		boardMaster.setReplyAt("N");
		boardMaster.setCommentAt("N");
		boardMaster.setFileAtachAt("Y");
		
		boardMaster.setListAuthorCode("ROLE_ANONYMOUS"); //목록권한
		boardMaster.setRedingAuthorCode("ROLE_ANONYMOUS"); //읽기권한
		boardMaster.setWritngAuthorCode("ROLE_USER"); 	// 쓰기권한
		boardMaster.setUpdtAuthorCode("ROLE_ADMIN"); 	// 수정권한
		boardMaster.setDeleteAuthorCode("ROLE_ADMIN"); 	// 삭제권한
		boardMaster.setReplyAuthorCode("ROLE_USER");		// 답장권한
		boardMaster.setCommentAuthorCode("ROLE_USER");	// 댓글권한
		
		boardMaster.setCommentSortOrderType("DESC"); //댓글 정렬 방식
		boardMaster.setListCo(10);
		boardMaster.setAtchFileSize(20); // MB
		model.addAttribute("boardMaster", boardMaster);
		
		return "modoo/cms/board/boardMasterForm";
	}
	
	/**
	 * 게시판 저장
	 * @param boardMaster
	 * @param bindingResult
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/decms/board/master/writeBoardMaster.json")
	@ResponseBody
	public JsonResult writeBoadMaster(@Valid BoardMasterVO boardMaster, BindingResult bindingResult) {
		JsonResult jsonResult = new JsonResult();
		LoginVO user = (LoginVO)EgovUserDetailsHelper.getAuthenticatedUser();
		
		try {
			//내부 직원 권한이 아닐때 
			if(!EgovUserDetailsHelper.getAuthorities().contains("ROLE_EMPLOYEE")) {
				jsonResult.setMessage(egovMessageSource.getMessage("cms.fail.accessDenied")); //접근 권한이 없습니다.
				jsonResult.setSuccess(false);
			}else {
				if(!this.isHasErrorsJson(bindingResult, jsonResult, "<br/>")) {
					if(StringUtils.isEmpty(boardMaster.getSiteId())) {
						boardMaster.setSiteId(SiteDomainHelper.getSiteId());
					}
					
					boardMaster.setFrstRegisterId(user.getUniqId());
					this.setBoardMasterDefaultValue(boardMaster);
					
					boardMasterService.insertBoardMaster(boardMaster);

					jsonResult.setSuccess(true);
					jsonResult.setMessage(egovMessageSource.getMessage("success.common.insert")); //정상적으로 등록되었습니다.
				}
			}
		} catch(Exception e) {
			LOGGER.error("Exception : " + e);
			jsonResult.setSuccess(false);
			jsonResult.setMessage(egovMessageSource.getMessage("fail.common.insert")); //생성이 실패하였습니다.
		}
		
		return jsonResult;
	}
	
	/**
	 * 게시판 수정 폼
	 * @param searchVO
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/decms/embed/board/master/modifyBoardMaster.do")
	public String modifyBoardMaster(@ModelAttribute("searchVO") BoardMasterVO searchVO, Model model) throws Exception {
		//내부 직원 권한이 아닐때 
		if(!EgovUserDetailsHelper.getAuthorities().contains("ROLE_EMPLOYEE")) {
			return "redirect:/decms/index.do";
		}
		
		//게시판 유형 목록
		ComDefaultCodeVO codeVO = new ComDefaultCodeVO();
		codeVO.setCodeId("CMS003");
		List<?> bbsTmplatCodeList = cmmUseService.selectCmmCodeDetail(codeVO);
		model.addAttribute("bbsTmplatCodeList", bbsTmplatCodeList);
		
		// 사용 권한 목록
		MberAuthorVO ma = new MberAuthorVO();
		List<MberAuthorVO> authorList = mberAuthorService.selectUsedAuthorList(ma);
		model.addAttribute("authorList", authorList);
		
		BoardMasterVO boardMaster = boardMasterService.selectBoardMaster(searchVO);
		model.addAttribute("boardMaster", boardMaster);
		
		return "modoo/cms/board/boardMasterForm";
	}
	
	/**
	 * 게시판 수정
	 * @param boardMaster
	 * @param bindingResult
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/decms/board/master/modifyBoardMaster.json")
	@ResponseBody
	public JsonResult modifyBoadMaster(@Valid BoardMasterVO boardMaster, BindingResult bindingResult) {
		JsonResult jsonResult = new JsonResult();
		LoginVO user = (LoginVO)EgovUserDetailsHelper.getAuthenticatedUser();
		
		try {
			//내부 직원 권한이 아닐때 
			if(!EgovUserDetailsHelper.getAuthorities().contains("ROLE_EMPLOYEE")) {
				jsonResult.setMessage(egovMessageSource.getMessage("cms.fail.accessDenied")); //접근 권한이 없습니다.
				jsonResult.setSuccess(false);
			}else {
				if(!this.isHasErrorsJson(bindingResult, jsonResult, "<br/>")) {
					
					if(StringUtils.isEmpty(boardMaster.getBbsId())) {
						this.vaildateMessage(egovMessageSource.getMessage("cms.fail.access"), jsonResult); // 잘못된 접근입니다.
						LOGGER.error("bbsId 가 없음.");
					}else {
						
						boardMaster.setLastUpdusrId(user.getUniqId());
						this.setBoardMasterDefaultValue(boardMaster);

						boardMasterService.updateBoardMaster(boardMaster);
						
						jsonResult.setSuccess(true);
						jsonResult.setMessage(egovMessageSource.getMessage("success.common.update")); //정상적으로 수정되었습니다.
					}
				}
			}

		} catch(Exception e) {
			LOGGER.error("Exception : " + e);
			jsonResult.setSuccess(false);
			jsonResult.setMessage(egovMessageSource.getMessage("fail.common.update")); //수정이 실패하였습니다.
		}
		
		return jsonResult;
	}
	
	/**
	 * 게시판 삭제
	 * @param boardMaster
	 * @return
	 */
	@RequestMapping(value = "/decms/board/master/deleteBoardMaster.json")
	@ResponseBody
	public JsonResult deleteBoardMaster(BoardMasterVO boardMaster) {
		JsonResult jsonResult = new JsonResult();
		LoginVO user = (LoginVO)EgovUserDetailsHelper.getAuthenticatedUser();
		
		try {
			//내부 직원 권한이 아닐때 
			if(!EgovUserDetailsHelper.getAuthorities().contains("ROLE_EMPLOYEE")) {
				jsonResult.setMessage(egovMessageSource.getMessage("cms.fail.accessDenied")); //접근 권한이 없습니다.
				jsonResult.setSuccess(false);
			}else {
				if(StringUtils.isEmpty(boardMaster.getBbsId())) {
					this.vaildateMessage(egovMessageSource.getMessage("cms.fail.access"), jsonResult); // 잘못된 접근입니다.
					LOGGER.error("bbsId 가 없음.");
				}else {
					
					boardMaster.setLastUpdusrId(user.getUniqId());
					boardMasterService.deleteBoardMaster(boardMaster);
					
					jsonResult.setSuccess(true);
					jsonResult.setMessage(egovMessageSource.getMessage("success.common.delete")); //정상적으로 삭제되었습니다.
				}
			}
			
		} catch(Exception e) {
			LOGGER.error("Exception : " + e);
			jsonResult.setSuccess(false);
			jsonResult.setMessage(egovMessageSource.getMessage("fail.common.delete")); //삭제가 실패하였습니다.
		}
		
		return jsonResult;
	}
	
	/**
	 * 저장시 기본값 지정
	 * @param boardMaster
	 */
	private void setBoardMasterDefaultValue(BoardMasterVO boardMaster) {
		
		//카테고리ID
		if(StringUtils.isEmpty(boardMaster.getCtgryMasterId())) {
			boardMaster.setCtgryMasterId(null);
		}
		
		//공지여부
		if(StringUtils.isEmpty(boardMaster.getNoticeAt())) {
			boardMaster.setNoticeAt("N");
		}
		//비밀글여부
		if(StringUtils.isEmpty(boardMaster.getSecretAt())) {
			boardMaster.setSecretAt("N");
		}
		//익명여부
		if(StringUtils.isEmpty(boardMaster.getAnnymtyAt())) {
			boardMaster.setAnnymtyAt("N");
		}
		//사용기간여부
		if(StringUtils.isEmpty(boardMaster.getUsgpdAt())) {
			boardMaster.setUsgpdAt("N");
		}
		//답장여부
		if(StringUtils.isEmpty(boardMaster.getReplyAt())) {
			boardMaster.setReplyAt("N");
		}
		//댓글여부
		if(StringUtils.isEmpty(boardMaster.getCommentAt())) {
			boardMaster.setCommentAt("N");
		}
		//첨부파일여부
		if(StringUtils.isEmpty(boardMaster.getFileAtachAt())) {
			boardMaster.setFileAtachAt("N");
		}
		if(StringUtils.isEmpty(boardMaster.getListAuthorCode())) {
			boardMaster.setListAuthorCode("ROLE_ANONYMOUS");
		}
		if(StringUtils.isEmpty(boardMaster.getRedingAuthorCode()) ) {
			boardMaster.setRedingAuthorCode("ROLE_ANONYMOUS");
		}
		if(StringUtils.isEmpty(boardMaster.getWritngAuthorCode())) {
			boardMaster.setWritngAuthorCode("ROLE_USER");
		}
		if(StringUtils.isEmpty(boardMaster.getUpdtAuthorCode())) {
			boardMaster.setUpdtAuthorCode("ROLE_ADMIN");
		}
		if(StringUtils.isEmpty(boardMaster.getDeleteAuthorCode())) {
			boardMaster.setDeleteAuthorCode("ROLE_ADMIN");
		}
		if(StringUtils.isEmpty(boardMaster.getReplyAuthorCode())) {
			boardMaster.setReplyAuthorCode("ROLE_USER");
		}
		if(StringUtils.isEmpty(boardMaster.getDownloadAuthorCode())) {
			boardMaster.setDownloadAuthorCode("ROLE_ANONYMOUS");
		}
		if(StringUtils.isEmpty(boardMaster.getCommentAuthorCode())) {
			boardMaster.setCommentAuthorCode("ROLE_USER");
		}
		//게시글 목록 갯수
		if(boardMaster.getListCo() == null) {
			boardMaster.setListCo(10);
		}
	}
}
