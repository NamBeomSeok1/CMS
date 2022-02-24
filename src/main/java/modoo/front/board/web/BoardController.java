package modoo.front.board.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import egovframework.com.cmm.service.EgovFileMngService;
import egovframework.com.cmm.util.EgovUserDetailsHelper;
import egovframework.rte.ptl.mvc.tags.ui.pagination.PaginationInfo;
import modoo.module.board.service.BoardArticleService;
import modoo.module.board.service.BoardArticleVO;
import modoo.module.board.service.BoardMasterService;
import modoo.module.board.service.BoardMasterVO;
import modoo.module.common.service.FileMngUtil;
import modoo.module.common.web.CommonDefaultController;

@Controller
public class BoardController extends CommonDefaultController {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(BoardController.class);
	
	@Resource(name = "boardMasterService")
	private BoardMasterService boardMasterService;
	
	@Resource(name = "boardArticleService")
	private BoardArticleService boardArticleService;
	
	@Resource(name = "fileMngUtil")
	private FileMngUtil fileMngUtil;
	
	@Resource(name = "EgovFileMngService")
	private EgovFileMngService fileMngService;
	
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
	 * 게시판 정보
	 * @param bbsId
	 * @return
	 * @throws Exception
	 */
	private BoardMasterVO getBoardMasterInfo(String bbsId, Model model) throws Exception {
		BoardMasterVO boardMaster = new BoardMasterVO();
		boardMaster.setBbsId(bbsId);
		boardMaster = boardMasterService.selectBoardMaster(boardMaster);
		model.addAttribute("boardMaster", boardMaster);
		return boardMaster;
	}
	
	/**
	 * 게시글 목록
	 * @param searchVO
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/board/boardList.do")
	public String boardList(@ModelAttribute("searchVO") BoardArticleVO searchVO, Model model) throws Exception {
		if(StringUtils.isEmpty(searchVO.getBbsId())) {
			return "redirect:/index.do?error=bbsId";
		}
		
		List<String> authorities = EgovUserDetailsHelper.getAuthorities();
		
		//게시판 정보
		BoardMasterVO boardMaster = getBoardMasterInfo(searchVO.getBbsId(), model);
		
		if(!authorities.contains(boardMaster.getListAuthorCode())) { //읽기 권한
			return "redirect:/";
		}

		//권한 정보
		model.addAttribute("boardAuth", getBoardAuthAt(boardMaster));
		
		PaginationInfo paginationInfo = new PaginationInfo();
		searchVO.setPageUnit(propertiesService.getInt("pageUnit"));
		this.setPagination(paginationInfo, searchVO);
		
		List<?> resultList = boardArticleService.selectBoardArticleList(searchVO);
		model.addAttribute("resultList", resultList);
		
		int totalRecordCount = boardArticleService.selectBoardArticleListCnt(searchVO);
		paginationInfo.setTotalRecordCount(totalRecordCount);
		model.addAttribute("paginationInfo", paginationInfo);
		

		return "modoo/front/board/" + boardMaster.getBbsTmplatCode() + "/boardList";
	}
}
