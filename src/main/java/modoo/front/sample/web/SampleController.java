package modoo.front.sample.web;

import java.util.List;
import java.util.Locale;

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

import egovframework.com.cmm.service.EgovFileMngService;
import egovframework.com.cmm.service.FileVO;
import egovframework.rte.ptl.mvc.tags.ui.pagination.PaginationInfo;
import modoo.module.common.service.FileMngUtil;
import modoo.module.common.service.JsonResult;
import modoo.module.common.util.CommonUtils;
import modoo.module.common.util.DoubleSubmitHelper;
import modoo.module.common.web.CommonDefaultController;
import modoo.module.sample.service.SampleService;
import modoo.module.sample.service.SampleVO;

@Controller	// 또는 @Controller("SampleController")
public class SampleController extends CommonDefaultController {

	private static final Logger LOGGER = LoggerFactory.getLogger(SampleController.class);
	
	@Resource(name = "sampleService")
	private SampleService sampleService;
	
	@Resource(name = "fileMngUtil")
	private FileMngUtil fileMngUtil;
	
	@Resource(name = "EgovFileMngService")
	private EgovFileMngService fileMngService;
	
	/**
	 * 샘플 목록
	 * @param searchVO
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/sample/sampleList.do")
	public String sampleList(@ModelAttribute("searchVO") SampleVO searchVO, Model model) throws Exception {
		
		PaginationInfo paginationInfo = new PaginationInfo(); // 페이징 처리
		searchVO.setPageUnit(propertiesService.getInt("pageUnit")); // src/main/resources/egovframework/spring/com/context-properties.xml
		this.setPagination(paginationInfo, searchVO);
		
		List<SampleVO> resultList = sampleService.selectSampleList(searchVO); // 목록
		int totalRecordCount = sampleService.selectSampleListCnt(searchVO); // 목록 카운트
		paginationInfo.setTotalRecordCount(totalRecordCount);
		
		model.addAttribute("resultList", resultList);
		model.addAttribute("paginationInfo", paginationInfo);
		
		return "modoo/front/sample/sampleList";
	}
	
	/**
	 * 샘플 작성 폼
	 * @param searchVO
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/smaple/writeSample.do", method = RequestMethod.GET) // 또는 @GetMapping(value="/sample/writeSample.do")
	public String writeSample(@ModelAttribute("searchVO") SampleVO searchVO, Model model) throws Exception {

		model.addAttribute("sample", new SampleVO());
		return "modoo/front/sample/sampleForm";
	}
	
	/**
	 * 샘플 저장
	 * @param searchVO
	 * @param sample
	 * @param bindingResult
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/sample/writeSample.json", method = RequestMethod.POST) // 또는 @PostMapping(value = "/sample/writeSample.json")
	@ResponseBody
	public JsonResult writeSample(final MultipartHttpServletRequest multiRequest, // enctype="multipart/form-data"
							@ModelAttribute("searchVO") SampleVO searchVO,
							@Valid SampleVO sample, 
							BindingResult bindingResult,  // Validation 대상 뒤에 꼭 BidingResult 쓰기, Valdation 메시지 설정 : src/main/resources/egovframework/message/modoo/message_ko.properties
							Model model) { // throws Exception 생략
		JsonResult jsonResult = new JsonResult();
		try {
			if(!DoubleSubmitHelper.checkAndSaveToken()) {
				this.vaildateMessage(egovMessageSource.getMessage("cms.fail.access"), jsonResult); // 잘못된 접근입니다.
				jsonResult.setRedirectUrl("/sample/sampleList.do");
			}else {
				if(!this.isHasErrorsJson(bindingResult, jsonResult)) {
					
					//------ 첨부파일 --------- start
					final List<MultipartFile> fileList = multiRequest.getFiles("atchFile"); // form -> <input type="file" name="atchFile" multiple/> : 이름이 같아야 함.
					String atchFileId = "";
					if(!fileList.isEmpty()) {
						String prefixPath = "SAMPLE";
						List<FileVO> files = fileMngUtil.parseFileInf(fileList, "SAMPLE_", 0, "", "", prefixPath); // 저장경로 : src/main/resources/egovframework/egovProps/globals.properties -> Globals.fileStorePath 참고
						atchFileId = fileMngService.insertFileInfs(files);
						sample.setAtchFileId(atchFileId); // 첨부파일고유ID
					}
					//------ 첨부파일 --------- end
					
					sample.setSampleSj(CommonUtils.unscript(sample.getSampleSj()));
					
					sampleService.insertSample(sample);
					jsonResult.setSuccess(true);
					jsonResult.setMessage(egovMessageSource.getMessage("success.common.insert")); // 정상적으로 등록되었습니다. src/main/resources/egovframework/message/com/message-common_ko.properties
					String redirectUrl = "/sample/sampleList.do"
							+ "?pageIndex=" + searchVO.getPageIndex()
							+ "&searchCondition" + searchVO.getSearchCondition()
							+ "&searchKeyword=" + searchVO.getSearchKeyword();
					jsonResult.setRedirectUrl(redirectUrl);
				}
			}
		}catch(Exception e) {
			LOGGER.error("Exception : " + e);
			jsonResult.setSuccess(false);
			jsonResult.setMessage(egovMessageSource.getMessage("fail.common.insert")); //생성이 실패하였습니다.
		}
		
		return jsonResult;
	}
	
	/**
	 * 샘플 상세
	 * @param searchVO
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/sample/viewSample.do")
	public String viewSample(@ModelAttribute("searchVO") SampleVO searchVO, Model model) throws Exception {
		SampleVO sample = sampleService.selectSample(searchVO);
		model.addAttribute("sample", sample);
		
		return "modoo/front/sample/sampleView";
	}
	
	/**
	 * 샘플 수정 폼
	 * @param searchVO
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/sample/modifySample.do", method = RequestMethod.GET) // 또는 @GetMapping(value = "/sample/modifySample.do")
	public String modifySample(@ModelAttribute("searchVO") SampleVO searchVO, Model model) throws Exception {
		SampleVO sample = sampleService.selectSample(searchVO);
		model.addAttribute("sample", sample);
		
		return "modoo/front/sample/sampleForm";
	}
	
	/**
	 * 샘플 수정
	 * @param searchVO
	 * @param sample
	 * @param bindingResult
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/sample/modifySample.json", method = RequestMethod.POST) // 또는 @PostMapping(value = "/sample/modifySample.json")
	@ResponseBody
	public JsonResult modifySample(final MultipartHttpServletRequest multiRequest,
							@ModelAttribute("searchVO") SampleVO searchVO,
							@Valid SampleVO sample, 
							BindingResult bindingResult, // Validation 대상 뒤에 꼭 BidingResult 쓰기, Valdation 메시지 설정 : src/main/resources/egovframework/message/modoo/message_ko.properties
							Model model) { // throws Exception 생략
		JsonResult jsonResult = new JsonResult();
		
		try {
			if(!DoubleSubmitHelper.checkAndSaveToken() || StringUtils.isEmpty(sample.getSampleId())) {
				this.vaildateMessage(egovMessageSource.getMessage("cms.fail.access"), jsonResult); // 잘못된 접근입니다.
				jsonResult.setRedirectUrl("/sample/sampleList.do");
			}else {
				if(!this.isHasErrorsJson(bindingResult, jsonResult)) {
					
					//------ 첨부파일 --------- start
					final List<MultipartFile> fileList = multiRequest.getFiles("atchFile");
					if(!fileList.isEmpty()) {
						String atchFileId = sample.getAtchFileId();
						String prefixPath = "SAMPLE";
						
						if(StringUtils.isEmpty(atchFileId)) {
							List<FileVO> files = fileMngUtil.parseFileInf(fileList, "SAMPLE_", 0, "", "", prefixPath);
							atchFileId = fileMngService.insertFileInfs(files);
							sample.setAtchFileId(atchFileId);
						}else {
							FileVO fvo = new FileVO();
							fvo.setAtchFileId(atchFileId);
							int cnt = fileMngService.getMaxFileSN(fvo);
							List<FileVO> files = fileMngUtil.parseFileInf(fileList, "SAMPLE_", cnt, atchFileId, "", prefixPath);
							fileMngService.updateFileInfs(files);
						}
					}
					//------ 첨부파일 --------- end
					
					sample.setSampleSj(CommonUtils.unscript(sample.getSampleSj()));
					
					sampleService.updateSample(sample);
					jsonResult.setSuccess(true);
					jsonResult.setMessage(egovMessageSource.getMessage("success.common.update")); // 정상적으로 수정되었습니다. src/main/resources/egovframework/message/com/message-common_ko.properties
					String redirectUrl = "/sample/sampleList.do"
										+ "?pageIndex=" + searchVO.getPageIndex()
										+ "&searchCondition" + searchVO.getSearchCondition()
										+ "&searchKeyword=" + searchVO.getSearchKeyword();
					jsonResult.setRedirectUrl(redirectUrl);
				}
			}
		}catch(Exception e) {
			LOGGER.error("Exception : " + e);
			jsonResult.setSuccess(false);
			jsonResult.setMessage(egovMessageSource.getMessage("fail.common.update")); //수정이 실패하였습니다.
		}
		
		return jsonResult;
	}
	
	/**
	 * 샘플 삭제
	 * @param searchVO
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/sample/deleteSample.json")
	@ResponseBody
	public JsonResult deleteSample(@ModelAttribute("searchVO") SampleVO searchVO, Model model) throws Exception {
		JsonResult jsonResult = new JsonResult();
		
		try {
			if(StringUtils.isEmpty(searchVO.getSampleId())) {
				this.vaildateMessage(egovMessageSource.getMessage("cms.fail.access"), jsonResult); // 잘못된 접근입니다.
				jsonResult.setRedirectUrl("/sample/sampleList.do");
			}else {
				
				sampleService.deleteSample(searchVO);
				jsonResult.setSuccess(true);
				jsonResult.setMessage(egovMessageSource.getMessage("success.common.delete")); // 정상적으로 삭제되었습니다. src/main/resources/egovframework/message/com/message-common_ko.properties
				String redirectUrl = "/sample/sampleList.do"
						+ "?pageIndex=" + searchVO.getPageIndex()
						+ "&searchCondition" + searchVO.getSearchCondition()
						+ "&searchKeyword=" + searchVO.getSearchKeyword();
				jsonResult.setRedirectUrl(redirectUrl);
			}
			
		}catch(Exception e) {
			LOGGER.error("Exception : " + e);
			jsonResult.setSuccess(false);
			jsonResult.setMessage(egovMessageSource.getMessage("fail.common.delete")); //삭제가 실패하였습니다.
		}

		return jsonResult;
	}
	
	
}
