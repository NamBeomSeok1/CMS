package modoo.cms.cmpnyInqry.web;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartRequest;

import egovframework.com.cmm.LoginVO;
import egovframework.com.cmm.service.EgovFileMngService;
import egovframework.com.cmm.service.EgovFileMngUtil;
import egovframework.com.cmm.service.FileVO;
import egovframework.com.cmm.util.EgovUserDetailsHelper;
import egovframework.rte.ptl.mvc.tags.ui.pagination.PaginationInfo;
import modoo.module.biztalk.service.BiztalkService;
import modoo.module.biztalk.service.BiztalkVO;
import modoo.module.common.service.FileMngUtil;
import modoo.module.common.service.JsonResult;
import modoo.module.common.web.CommonDefaultController;
import modoo.module.shop.cmpny.service.CmpnyInqryService;
import modoo.module.shop.cmpny.service.CmpnyInqryVO;

@Controller("cmpnyInqryController")
public class CmpnyInqryController extends CommonDefaultController{

	Logger LOGGER = LoggerFactory.getLogger(CmpnyInqryController.class);
	
	@Resource(name="cmpnyInqryService")
	CmpnyInqryService cmpnyInqryService;
	
	@Resource(name = "fileMngUtil")
	private FileMngUtil fileMngUtil;
	
	@Resource(name = "EgovFileMngService")
	private EgovFileMngService fileMngService;
	
	@Resource(name = "biztalkService")
	private BiztalkService biztalkService;
	
	/**
	 * 입점문의 등록
	 * @param cmpnyInqry
	 * @return
	 */
	@RequestMapping(value="/inqryReg.json")
	@ResponseBody
	public JsonResult cmpnyInqryInsert(CmpnyInqryVO cmpnyInqry,MultipartRequest multiRequest){
		
		JsonResult jsonResult = new JsonResult();
		LoginVO user = (LoginVO)EgovUserDetailsHelper.getAuthenticatedUser();
		
		try {
			String email = cmpnyInqry.getEmail1()+"@"+cmpnyInqry.getEmail2();
			String telno = cmpnyInqry.getTelno1()+"-"+cmpnyInqry.getTelno2()+"-"+cmpnyInqry.getTelno3();
			
			cmpnyInqry.setTelno(telno);
			cmpnyInqry.setCmpnyEmail(email);
			if(user!=null){
				cmpnyInqry.setEsntlId(user.getUniqId());
			}
			//파일 업로드
			final List<MultipartFile> fileList = multiRequest.getFiles("atchFile");

			for (MultipartFile file : fileList) {
				System.out.println(file.getOriginalFilename());
			}
			String atchFileId = ""; 
			String prefixPath = "INQRY";
			if(!fileList.isEmpty()) {
				List<FileVO> files = fileMngUtil.parseFileInf(fileList, "INQRY_", 0, "", "", prefixPath); // 저장경로 : src/main/resources/egovframework/egovProps/globals.properties -> Globals.fileStorePath 참고
				atchFileId = fileMngService.insertFileInfs(files);
				cmpnyInqry.setAtchFileId(atchFileId); // 첨부파일고유ID
			}
			//파일 업로드 끝
			cmpnyInqryService.insertCmpnyInqry(cmpnyInqry);
			jsonResult.setSuccess(true);
			jsonResult.setMessage("등록이 완료되었습니다.");
			
			/* 입점문의 알림톡 */
			BiztalkVO bizTalk = new BiztalkVO();
			bizTalk.setRecipient("010-4806-1787");
			bizTalk.setTmplatCode("template_020");
			/*[모두의구독] 입점문의가 접수되었습니다.
			* 업체명: #{CMPNYNM}
			* 담당자: #{CHARGERNM} (#{TEL}) */
			BiztalkVO template = biztalkService.selectBizTalkTemplate(bizTalk);
			String message = template.getTmplatCn();
			message = message.replaceAll("#\\{CMPNYNM\\}", cmpnyInqry.getCmpnyNm());
			message = message.replaceAll("#\\{CHARGERNM\\}", cmpnyInqry.getCmpnyCharger());
			message = message.replaceAll("#\\{TEL\\}", cmpnyInqry.getTelno());
			
			bizTalk.setMessage(message);
			
			BiztalkVO result = biztalkService.sendAlimTalk(bizTalk);
			
		
		} catch (Exception e) {
			loggerError(LOGGER, e);
			jsonResult.setSuccess(false);
			jsonResult.setMessage("등록이 실패하였습니다.");
		}
		
		return jsonResult;
	}

	/**
	 * 입점문의 관리 페이지 이동
	 * @return
	 */
	@RequestMapping(value="/decms/cmpny/inqryManage.do")
	public String inqryManage() {
		LoginVO user = (LoginVO)EgovUserDetailsHelper.getAuthenticatedUser();
		if(user == null){
			return "redirect:/index.do";
		}
		
		return "modoo/cms/shop/cmpny/inqryManage";
	}
	
	/**
	 * 입점문의 목록
	 * @param searchVO
	 * @return
	 */
	@RequestMapping(value="/decms/cmpny/inqryList.json", method=RequestMethod.GET)
	@ResponseBody
	public JsonResult inqryList(CmpnyInqryVO searchVO){
		JsonResult jsonResult = new JsonResult();
		
		try {

			PaginationInfo paginationInfo = new PaginationInfo();
			searchVO.setPageUnit(propertiesService.getInt("gridPageUnit"));
			this.setPagination(paginationInfo, searchVO);
			
			List<?> cmpnyInqryList = cmpnyInqryService.selectCmpnyInqryList(searchVO);
			jsonResult.put("list", cmpnyInqryList);
			
			int totalRecordCount = cmpnyInqryService.selectCmpnyInqryListCnt(searchVO);
			paginationInfo.setTotalRecordCount(totalRecordCount);
			jsonResult.put("paginationInfo", paginationInfo);
			
			jsonResult.setSuccess(true);
			
		} catch (Exception e) {
			loggerError(LOGGER, e);
			jsonResult.setSuccess(false);
			//jsonResult.setMessage("등록이 실패하였습니다.");
		}
		
		return jsonResult;
	}
	
	/**
	 * 입점문의 등록(조회) 폼 페이지 이동
	 * @return
	 */
	@RequestMapping(value="/cmpny/inqryForm.do")
	public String inqryForm() {
		
		return "modoo/cms/shop/cmpny/inqryForm";
	}
	
	/**
	 * 입점문의 상세 조회
	 * @param searchVO
	 * @return
	 */
	@RequestMapping(value="/decms/cmpny/inqryDetail.json", method=RequestMethod.GET)
	@ResponseBody
	public JsonResult inqryDetail(CmpnyInqryVO searchVO){
		JsonResult jsonResult = new JsonResult();
		
		try {
			CmpnyInqryVO cmpnyInqry = cmpnyInqryService.selectCmpnyInqry(searchVO);
			if(cmpnyInqry.getAtchFileId()!=null){
			FileVO fvo = new FileVO();
				fvo.setAtchFileId(cmpnyInqry.getAtchFileId());
				List<FileVO> files = fileMngService.selectFileInfs(fvo);
				jsonResult.put("files", files);
			}
			jsonResult.put("cmpnyInqry", cmpnyInqry);
			jsonResult.setSuccess(true);
			//jsonResult.setMessage("등록이 성공하였습니다.");
		} catch (Exception e) {
			loggerError(LOGGER, e);
			jsonResult.setSuccess(false);
			//jsonResult.setMessage("등록이 실패하였습니다.");
		}
		
		return jsonResult;
	}
}
