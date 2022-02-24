package modoo.cms.faq.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
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
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import modoo.module.common.service.FileMngUtil;
import modoo.module.common.service.JsonResult;
import modoo.module.common.util.CommonUtils;
import modoo.module.common.util.SiteDomainHelper;
import modoo.module.common.web.CommonDefaultController;
import modoo.module.faq.service.FaqinfoService;
import modoo.module.faq.service.FaqinfoVO;
import egovframework.com.cmm.ComDefaultCodeVO;
import egovframework.com.cmm.LoginVO;
import egovframework.com.cmm.service.EgovCmmUseService;
import egovframework.com.cmm.service.EgovFileMngService;
import egovframework.com.cmm.service.FileVO;
import egovframework.com.cmm.util.EgovUserDetailsHelper;
import egovframework.rte.ptl.mvc.tags.ui.pagination.PaginationInfo;

@Controller
public class CmsFaqinfoController extends CommonDefaultController {

	private static final Logger LOGGER = LoggerFactory.getLogger(CmsFaqinfoController.class);
	
	@Resource(name = "faqinfoService")
	private FaqinfoService faqinfoService;
	
	@Resource(name = "fileMngUtil")
	private FileMngUtil fileMngUtil;
	
	@Resource(name = "EgovFileMngService")
	private EgovFileMngService fileMngService;
	
	@Resource(name = "EgovCmmUseService")
	private EgovCmmUseService EgovCmmUseService;
	
	
	/**
	 * FAQ 관리
	 * @param searchVO
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/decms/faq/faqManage.do")
	public String faqManage(@ModelAttribute("searchVO") FaqinfoVO searchVO, Model model) throws Exception {
		if(!EgovUserDetailsHelper.getAuthorities().contains("ROLE_EMPLOYEE")) {
			return "redirect:/decms/index.do";
		}
		
		//FAQ분류코드 
		if(StringUtils.isEmpty(searchVO.getSearchFaqClCode())) {
			searchVO.setSearchFaqClCode("SITE");
		}

		return "modoo/cms/faq/faqManage";
	}

	/**
	 * FAQ CP 목록
	 * @param searchVO
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/decms/faq/faqCpList.do")
	public String faqCpManage(@ModelAttribute("searchVO") FaqinfoVO searchVO, Model model) throws Exception {
		searchVO.setSearchFaqClCode("CP");

		return "modoo/cms/faq/faqCpList";
	}
	
	
	/**
	 * FAQ 목록
	 * @param searchVO
	 * @return
	 */
	@RequestMapping(value = "/decms/faq/faqList.json")
	@ResponseBody
	public JsonResult faqList(FaqinfoVO searchVO) {
		JsonResult jsonResult = new JsonResult();
		
		try {
			if(StringUtils.isEmpty(searchVO.getSiteId())) {
				searchVO.setSearchSiteId(SiteDomainHelper.getSiteId());
			}
			
			PaginationInfo paginationInfo = new PaginationInfo();
			searchVO.setPageUnit(propertiesService.getInt("gridPageUnit"));
			this.setPagination(paginationInfo, searchVO);
			
			if (searchVO.getFaqSeCode() != null) {
				String[] code = searchVO.getFaqSeCode().split(",");
				searchVO.setSearchFaqSeCode(code);
			}
			
			List<?> resultList = faqinfoService.selectFaqinfoList(searchVO);
			jsonResult.put("list", resultList);
			
			int totalRecordCount = faqinfoService.selectFaqinfoListCnt(searchVO);
			paginationInfo.setTotalRecordCount(totalRecordCount);
			jsonResult.put("paginationInfo", paginationInfo);
			
			List<?> faqSeCodeList = selectCode("CMS2020");

			jsonResult.put("faqSeCodeList", faqSeCodeList);
			jsonResult.setSuccess(true);
			
		} catch(Exception e) {
			LOGGER.error("Exception : " + e);
			jsonResult.setSuccess(false);
			jsonResult.setMessage(egovMessageSource.getMessage("fail.common.select")); //조회에 실패하였습니다.
		}
		
		return jsonResult;
	}
	
	/**
	 * FAQ 등록 폼
	 * @param searchVO
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/decms/embed/faq/writeFaq.do")
	public String writeFaq(@ModelAttribute("searchVO") FaqinfoVO searchVO, Model model) throws Exception {
		if(!EgovUserDetailsHelper.getAuthorities().contains("ROLE_EMPLOYEE")) {
			return "redirect:/decms/index.do";
		}
		
		FaqinfoVO faq = new FaqinfoVO();
		if(StringUtils.isEmpty(searchVO.getSiteId())) {
			faq.setSiteId(SiteDomainHelper.getSiteId());
		}
		
		//FAQ분류코드
		if(StringUtils.isEmpty(searchVO.getFaqClCode())) {
			faq.setFaqClCode("SITE");
		}else {
			faq.setFaqClCode(searchVO.getFaqClCode());
		}

		model.addAttribute("faqSeCodeList", selectCode("CMS2020"));
		model.addAttribute("faq", faq);

		return "modoo/cms/faq/faqForm";
	}
	
	/**
	 * 코드 조회
	 * @param codeId
	 * @return
	 * @throws Exception
	 */
	
	public List<?> selectCode(String codeId) throws Exception{

		ComDefaultCodeVO codeVO = new ComDefaultCodeVO();
		codeVO.setCodeId("CMS020");
		List<?> faqTypeCodeList = EgovCmmUseService.selectCmmCodeDetail(codeVO);

		return faqTypeCodeList;
	}
	
	/**
	 * FAQ 저장
	 * @param multiRequest
	 * @param faq
	 * @param bindingResult
	 * @return
	 */
	@RequestMapping(value = "/decms/faq/writeFaq.json")
	@ResponseBody
	public JsonResult writeFaq(final MultipartHttpServletRequest multiRequest,
			@Valid FaqinfoVO faq, BindingResult bindingResult) {
		JsonResult jsonResult = new JsonResult();
		LoginVO user = (LoginVO)EgovUserDetailsHelper.getAuthenticatedUser();

		try {
			if(!EgovUserDetailsHelper.getAuthorities().contains("ROLE_EMPLOYEE")) {
				jsonResult.setMessage(egovMessageSource.getMessage("cms.fail.accessDenied")); //접근권한이 없습니다.
				jsonResult.setSuccess(false);
				return jsonResult;
			}
			
			if(!this.isHasErrorsJson(bindingResult, jsonResult, "<br/>")) {
				final List<MultipartFile> fileList = multiRequest.getFiles("atchFile");
				String atchFileId = "";

				if(!fileList.isEmpty()) {
					String prefixPath = "FAQ"; //+ File.separator + article.getBbsId();
					List<FileVO> files = fileMngUtil.parseFileInf(fileList, "FAQ_", 0, "", "", prefixPath);
					atchFileId = fileMngService.insertFileInfs(files);
					faq.setAtchFileId(atchFileId);
				}
				
				faq.setQestnCn(CommonUtils.unscript(faq.getQestnCn()));
				faq.setAnswerCn(CommonUtils.unscript(faq.getAnswerCn()));
				faq.setFrstRegisterId(user.getUniqId());

				faqinfoService.insertFaqinfo(faq);
				jsonResult.setSuccess(true);
			}
		} catch(Exception e) {
			LOGGER.error("Exception : " + e);
			jsonResult.setSuccess(false);
			jsonResult.setMessage(egovMessageSource.getMessage("fail.common.insert")); //생성이 실패하였습니다.
		}
		
		return jsonResult;
	}
	
	/**
	 * FAQ 상세
	 * @param searchVO
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/decms/embed/faq/viewFaq.do")
	public String viewFaq(@ModelAttribute("searchVO") FaqinfoVO searchVO, Model model) throws Exception {
		
		ComDefaultCodeVO codeVO = new ComDefaultCodeVO();
		codeVO.setCodeId("CMS020");
		List<?> faqSeCodeList = EgovCmmUseService.selectCmmCodeDetail(codeVO);
		model.addAttribute("faqSeCodeList", faqSeCodeList);
		
		FaqinfoVO faq = faqinfoService.selectFaqinfo(searchVO);
		model.addAttribute("faq", faq);
		
		return "modoo/cms/faq/faqView";
	}
	
	/**
	 * FAQ 수정 폼
	 * @param searchVO
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/decms/embed/faq/modifyFaq.do")
	public String modifyFaq(@ModelAttribute("searchVO") FaqinfoVO searchVO, Model model) throws Exception {
		if(!EgovUserDetailsHelper.getAuthorities().contains("ROLE_EMPLOYEE")) {
			return "redirect:/decms/index.do";
		}
		
		FaqinfoVO faq = faqinfoService.selectFaqinfo(searchVO);
		
		model.addAttribute("faqSeCodeList", selectCode("CMS2020"));
		model.addAttribute("faq", faq);

		return "modoo/cms/faq/faqForm";
	}
	
	
	/**
	 * FAQ 수정
	 * @param multiRequest
	 * @param faq
	 * @param bindingResult
	 * @return
	 */
	@RequestMapping(value = "/decms/faq/modifyFaq.json")
	@ResponseBody
	public JsonResult modifyFaq(final MultipartHttpServletRequest multiRequest,
			@Valid FaqinfoVO faq, BindingResult bindingResult) {
		JsonResult jsonResult = new JsonResult();
		LoginVO user = (LoginVO)EgovUserDetailsHelper.getAuthenticatedUser();

		try {
			if(!EgovUserDetailsHelper.getAuthorities().contains("ROLE_EMPLOYEE")) {
				jsonResult.setMessage(egovMessageSource.getMessage("cms.fail.accessDenied")); //접근권한이 없습니다.
				jsonResult.setSuccess(false);
				return jsonResult;
			}
			
			if(!this.isHasErrorsJson(bindingResult, jsonResult, "<br/>")) {
				final List<MultipartFile> fileList = multiRequest.getFiles("atchFile");
				if(!fileList.isEmpty()) {
					String atchFileId = faq.getAtchFileId();
					String prefixPath = "FAQ"; //+ File.separator + article.getBbsId();
					if(StringUtils.isEmpty(atchFileId)) {
						List<FileVO> files = fileMngUtil.parseFileInf(fileList, "FAQ_", 0, "", "", prefixPath);
						atchFileId = fileMngService.insertFileInfs(files);
						faq.setAtchFileId(atchFileId);
					}else {
						FileVO fvo = new FileVO();
						fvo.setAtchFileId(atchFileId);
						int cnt = fileMngService.getMaxFileSN(fvo);
						List<FileVO> files = fileMngUtil.parseFileInf(fileList, "FAQ_", cnt, atchFileId, "", prefixPath);
						fileMngService.updateFileInfs(files);
					}
				}
				
				faq.setQestnCn(CommonUtils.unscript(faq.getQestnCn()));
				faq.setAnswerCn(CommonUtils.unscript(faq.getAnswerCn()));
				faq.setLastUpdusrId(user.getUniqId());

				faqinfoService.updateFaqinfo(faq);
				jsonResult.setSuccess(true);
			}
		} catch(Exception e) {
			LOGGER.error("Exception : " + e);
			jsonResult.setSuccess(false);
			jsonResult.setMessage(egovMessageSource.getMessage("fail.common.update")); //수정이 실패하였습니다.
		}
		
		return jsonResult;
	}
	
	/**
	 * FAQ 삭제
	 * @param faq
	 * @return
	 */
	@RequestMapping(value = "/decms/faq/deleteFaq.json")
	@ResponseBody
	public JsonResult deleteFaq(FaqinfoVO faq) {
		JsonResult jsonResult = new JsonResult();
		LoginVO user = (LoginVO)EgovUserDetailsHelper.getAuthenticatedUser();

		try {
			if(StringUtils.isEmpty(faq.getFaqId())) {
				this.vaildateMessage(egovMessageSource.getMessage("cms.fail.access"), jsonResult); // 잘못된 접근입니다.
				LOGGER.error("popupId 가 없음.");

			} else {
				faq.setLastUpdusrId(user.getUniqId());
				faqinfoService.deleteFaqinfo(faq);
				jsonResult.setSuccess(true);
			}

		} catch(Exception e) {
			LOGGER.error("Exception : " + e);
			jsonResult.setSuccess(false);
			jsonResult.setMessage(egovMessageSource.getMessage("fail.common.delete")); //삭제가 실패하였습니다.
		}
		
		return jsonResult;
	}
}
