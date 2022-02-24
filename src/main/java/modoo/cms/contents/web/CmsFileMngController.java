package modoo.cms.contents.web;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import modoo.module.common.service.FileMngUtil;
import modoo.module.common.service.JsonResult;
import modoo.module.common.web.CommonDefaultController;
import egovframework.com.cmm.EgovMessageSource;
import egovframework.com.cmm.service.FileVO;
import egovframework.rte.psl.dataaccess.util.EgovMap;

@Controller
public class CmsFileMngController extends CommonDefaultController {

	private static final Logger LOGGER = LoggerFactory.getLogger(CmsFileMngController.class);

	/** EgovMessageSource */
	@Resource(name = "egovMessageSource")
	protected EgovMessageSource egovMessageSource;
	
	@Resource(name = "fileMngUtil")
	private FileMngUtil fileMngUtil;
	
	/**
	 * 싱글 파일 업로드
	 * @param multiRequest
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/decms/fms/singleFileUpload.json", method = RequestMethod.POST)
	@ResponseBody
	public JsonResult singleFileUpload(final MultipartHttpServletRequest multiRequest) {
		JsonResult jsonResult = new JsonResult();
		try {
			
			final MultipartFile atchFile = multiRequest.getFile("atchFile");
			String fileUrl = fileMngUtil.parseImageContentsFile(atchFile);
			
			jsonResult.put("fileUrl", fileUrl);
			
			jsonResult.setSuccess(true);
		} catch(Exception e) {
			loggerError(LOGGER, e);
			jsonResult.setSuccess(false);
			jsonResult.setMessage(egovMessageSource.getMessage("errors.file.transfer")); //파일전송중 오류가 발생했습니다.
		}
		return jsonResult;
	}
	
	/**
	 * 싱글 이미지 파일 업로드 : 썸네일 변환
	 * @param multiRequest
	 * @param width
	 * @param height
	 * @return
	 */
	@RequestMapping(value = "/decms/fms/imageFileUpload.json", method = RequestMethod.POST)
	@ResponseBody
	public JsonResult imageFileUpload(final MultipartHttpServletRequest multiRequest,
			@RequestParam(name = "w", required = false) Integer width,
			@RequestParam(name = "h", required = false) Integer height) {
		JsonResult jsonResult = new JsonResult();
		try {
			
			final MultipartFile atchFile = multiRequest.getFile("atchFile");
			if(width == null) width = 150;
			if(height == null) width = 150;
			EgovMap fileUrl = fileMngUtil.parseImageContentFile(atchFile, width, height);
			
			jsonResult.put("orignImagePath", fileUrl.get("orignFileUrl"));
			jsonResult.put("thumbImagePath", fileUrl.get("thumbUrl"));
			
			jsonResult.setSuccess(true);
		} catch(Exception e) {
			loggerError(LOGGER, e);
			jsonResult.setSuccess(false);
			jsonResult.setMessage(egovMessageSource.getMessage("errors.file.transfer")); //파일전송중 오류가 발생했습니다.
		}
		return jsonResult;
	}

}
