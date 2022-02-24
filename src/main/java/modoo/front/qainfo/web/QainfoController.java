package modoo.front.qainfo.web;

import java.io.File;
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
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.MultipartRequest;

import egovframework.com.cmm.ComDefaultCodeVO;
import egovframework.com.cmm.LoginVO;
import egovframework.com.cmm.service.EgovCmmUseService;
import egovframework.com.cmm.service.EgovFileMngService;
import egovframework.com.cmm.service.FileVO;
import egovframework.com.utl.fcc.service.EgovDateUtil;
import egovframework.rte.fdl.access.service.EgovUserDetailsHelper;
import egovframework.rte.psl.dataaccess.util.EgovMap;
import egovframework.rte.ptl.mvc.tags.ui.pagination.PaginationInfo;
import modoo.module.biztalk.service.BiztalkService;
import modoo.module.biztalk.service.BiztalkVO;
import modoo.module.common.service.FileMngUtil;
import modoo.module.common.service.JsonResult;
import modoo.module.common.util.CommonUtils;
import modoo.module.common.util.SiteDomainHelper;
import modoo.module.common.web.CommonDefaultController;
import modoo.module.qainfo.service.QainfoService;
import modoo.module.qainfo.service.QainfoVO;
import modoo.module.shop.cmpny.service.CmpnyService;
import modoo.module.shop.cmpny.service.CmpnyVO;
import modoo.module.shop.goods.info.service.GoodsService;
import modoo.module.shop.goods.info.service.GoodsVO;

@Controller("frontQaInfoController")
public class QainfoController extends CommonDefaultController{

	private static final Logger LOGGER =  LoggerFactory.getLogger(QainfoController.class);
	
	@Resource(name="qainfoService")
	private QainfoService qainfoService;
	
	@Resource(name="EgovCmmUseService")
	private EgovCmmUseService cmmUseService;
	
	@Resource(name = "goodsService")
	private GoodsService goodsService;
	
	@Resource(name = "fileMngUtil")
	private FileMngUtil fileMngUtil;
	
	@Resource(name = "EgovFileMngService")
	private EgovFileMngService fileMngService;

	@Resource(name = "biztalkService")
	private BiztalkService biztalkService;
	
	@Resource(name = "cmpnyService") 
	private CmpnyService cmpnyService;
	
	/*
	 * 사용자 질답 목록
	 *@param searchVO
	 *@param request
	 *@return 
	 */
	@RequestMapping(value="/qainfo/qainfoList.json")
	@ResponseBody
	public JsonResult qainfoList(QainfoVO searchVO,HttpServletRequest request){
		
		JsonResult jsonResult = new JsonResult();
		
		LoginVO user = (LoginVO)EgovUserDetailsHelper.getAuthenticatedUser();
		
		try{
			if(StringUtils.isEmpty(searchVO.getSiteId())){
				searchVO.setSiteId(SiteDomainHelper.getSiteId());
			}
			
			PaginationInfo paginationInfo = new PaginationInfo();
			searchVO.setPageUnit(propertiesService.getInt("gridPageUnit"));
			int totalRecordcount = qainfoService.selectQainfoListCnt(searchVO);
			List<EgovMap> resultList = (List<EgovMap>) qainfoService.selectQainfoList(searchVO);
			
			
			//상품 qna리스트
			if(request.getParameter("goodsId")!=null){
				searchVO.setGoodsId(request.getParameter("goodsId"));
				searchVO.setQaSeCode("GOODS");
				
				ComDefaultCodeVO codeVO = new ComDefaultCodeVO();
				codeVO.setCodeId("CMS018");
				List<?> qestnTyCodeList = cmmUseService.selectCmmCodeDetail(codeVO);
				jsonResult.put("qestnTyCodeList", qestnTyCodeList);
				
				this.setPagination(paginationInfo, searchVO);
				totalRecordcount=qainfoService.selectQainfoListCnt(searchVO);
				if(totalRecordcount<10){
					searchVO.setFirstIndex(0);
				}
				resultList = (List<EgovMap>) qainfoService.selectQainfoList(searchVO);
				paginationInfo.setTotalRecordCount(totalRecordcount);
				
				//이름 ***처리
				for(EgovMap qa: resultList){
					boolean isLogin = false;
					String qestnCn = null;
					qa.put("wrterNm",(qa.get("wrterId").toString().substring(0,1)+"****"+qa.get("wrterId").toString().substring(qa.get("wrterId").toString().length()-1,qa.get("wrterId").toString().length())));
					
					if(user==null || !user.getUniqId().equals(qa.get("frstRegisterId"))){
						qestnCn=("Y".equals(qa.get("secretAt"))) ? "비밀 글입니다." : (String) qa.get("qestnCn");
						isLogin = false;
					}else if(qa.get("frstRegisterId").equals(user.getUniqId())){
						qestnCn=(String)qa.get("qestnCn");
						isLogin = true;
					}
					qa.put("qestnCn", qestnCn);
					qa.put("isLogin", isLogin);
				}
			}
			
			jsonResult.put("paginationInfo", paginationInfo);
			jsonResult.put("list", resultList);
			
			jsonResult.setSuccess(true);
			
		}catch (Exception e) {
			loggerError(LOGGER, e);
			jsonResult.setSuccess(false);
			jsonResult.setMessage(egovMessageSource.getMessage("fail.common.select"));
		}
		return jsonResult;
	}
	
	 /* 상품 QNA 폼
	 * @param searchVO
	 * @param model
	 * @param request
	 * @return
	 */
	
	@RequestMapping(value="/shop/goods/qainfo/qaInfo.do")
	public String moveQainfo(@ModelAttribute("qaInfo") QainfoVO searchVO, Model model,HttpServletRequest request) throws Exception {
				
		/*	GoodsVO goods = new GoodsVO();
			goods.setGoodsId(request.getParameter("goodsId"));
			GoodsVO goodsInfo = goodsService.selectGoods(goods);
			model.addAttribute("goods",goodsInfo);*/
			
			ComDefaultCodeVO codeVO = new ComDefaultCodeVO();
			codeVO.setCodeId("CMS018");
			List<?> qestnTyCodeList = cmmUseService.selectCmmCodeDetail(codeVO);
			model.addAttribute("qestnTyCodeList",qestnTyCodeList);
				
			return "modoo/front/shop/goods/info/qaInfo/qainfo";
	}
	/* 상품 QNA 리스트폼
	 * @param searchVO
	 * @param model
	 * @param request
	 * @return
	 */
	
	@RequestMapping(value="/shop/goods/qainfo/qaInfoList.do")
	public String qainfoList(@ModelAttribute("qaInfo") QainfoVO searchVO, Model model,HttpServletRequest request) throws Exception {
		
		GoodsVO goods = new GoodsVO();
		goods.setGoodsId(request.getParameter("goodsId"));
		GoodsVO goodsInfo = goodsService.selectGoods(goods);
		
		PaginationInfo paginationInfo = new PaginationInfo();
		searchVO.setPageUnit(propertiesService.getInt("gridPageUnit"));
		searchVO.setGoodsId(request.getParameter("goodsId"));
		
		this.setPagination(paginationInfo, searchVO);
		int totalRecordcount = qainfoService.selectQainfoListCnt(searchVO);
		paginationInfo.setTotalRecordCount(totalRecordcount);
		
		model.addAttribute("paginationInfo", paginationInfo);
		model.addAttribute("goods",goodsInfo);
		
		return "modoo/front/shop/goods/info/qaInfo/qaInfoList";
	}
	
	
	 /* 상품 QNA 상세
	 * @param searchVO
	 * @return
	 */
	@RequestMapping("/qaInfo/qaInfoDetail.json")
	@ResponseBody
	public JsonResult detailQaInfo(QainfoVO searchVO,@RequestParam("qaId")String qaId){
		
		JsonResult jsonResult = new JsonResult();
		
		
		
		
		
		// TODO : 비밀글일때 나의 글인지 체크하는것이 없어 구현해야한다.
		
		
		
		
		
		
		
		
		
		
		
		try {
			searchVO.setQaId(qaId);
			searchVO = qainfoService.selectQainfo(searchVO);
			List<?> imgs = qainfoService.selectImageList(searchVO);
			if(searchVO!=null){
				
				jsonResult.put("imgs", imgs);
				jsonResult.put("qaInfo", searchVO);
				jsonResult.setMessage(egovMessageSource.getMessage("success.common.select"));
				jsonResult.setSuccess(true);
				
			}
		} catch (Exception e) {
			loggerError(LOGGER, e);
			jsonResult.setSuccess(false);
			jsonResult.setMessage(egovMessageSource.getMessage("fail.common.select"));
		}
		
		return jsonResult;
		
	}
	
	/*
	 * 사용자 질답 저장
	 * @param qainfo
	 * @param bindingResult
	 * @return
	 */
	@RequestMapping(value="/qainfo/regstQainfo.json", method=RequestMethod.POST)
	@ResponseBody
	public JsonResult writeQainfo(@Valid QainfoVO qainfo, BindingResult bindingResult, MultipartHttpServletRequest multiRequest){
		
		JsonResult jsonResult = new JsonResult();
		
		Boolean isLogin = EgovUserDetailsHelper.isAuthenticated();
		
		if(isLogin){
			LoginVO user = (LoginVO)EgovUserDetailsHelper.getAuthenticatedUser();

			try {
				if(!this.isHasErrorsJson(bindingResult, jsonResult,"<br/>")){
					
					if(StringUtils.isEmpty(qainfo.getSiteId())){
						qainfo.setSiteId(SiteDomainHelper.getSiteId());
					}
					
					jsonResult.setSuccess(true);
					
					if(qainfo.getGoodsId()!=null){
						qainfo.setQaSeCode("GOODS"); //상품문의
						QainfoVO searchVO = new QainfoVO();
						searchVO.setWrterId(user.getId());
						searchVO.setGoodsId(qainfo.getGoodsId());
						int chkQaCnt = qainfoService.selectQainfoListCnt(searchVO);
						if(chkQaCnt>0){
							jsonResult.setSuccess(false);
							jsonResult.setMessage("한 상품당 하나의 질문만 할 수 있습니다.");
						}
						if(qainfo.getSecretAt()==null)qainfo.setSecretAt("N");
						qainfo.setQestnSj(CommonUtils.unscript(qainfo.getQestnSj()));
					
					}else if(qainfo.getGoodsId()==null){
						
						qainfo.setQaSeCode("SITE");  //1:1문의
						//파일 업로드
						final List<MultipartFile> fileList = multiRequest.getFiles("atchFile");

						System.out.println("파일"+fileList);
						
						String atchFileId = "";
						if(!fileList.isEmpty()) {
							
							String prefixPath = "QNA";
							List<FileVO> files = fileMngUtil.parseFileInf(fileList, "QNA_", 0, "", "", prefixPath); // 저장경로 : src/main/resources/egovframework/egovProps/globals.properties -> Globals.fileStorePath 참고
							atchFileId = fileMngService.insertFileInfs(files);
							qainfo.setAtchFileId(atchFileId); // 첨부파일고유ID
						}
					}
					if(StringUtils.isNotEmpty(qainfo.getTelno1()) && StringUtils.isNotEmpty(qainfo.getTelno2()) && StringUtils.isNotEmpty(qainfo.getTelno3())){
						qainfo.setWrterTelno(qainfo.getTelno1()+"-"+qainfo.getTelno2()+"-"+qainfo.getTelno3());
					}
					qainfo.setQestnTyCode(qainfo.getQestnTyCode());
					qainfo.setQestnCn(CommonUtils.unscript(qainfo.getQestnCn()));
					qainfo.setFrstRegisterId(user.getUniqId());
					qainfo.setEmailAdres(user.getEmail());
					qainfo.setWrterNm(user.getName());
					qainfo.setWrterId(user.getId());
					qainfo.setWritngDe(CommonUtils.validChkDateTime(EgovDateUtil.getToday()+"000000"));
					qainfo.setFrstRegistPnttm(EgovDateUtil.getToday());
					qainfo.setQnaProcessSttusCode("R");
					if(jsonResult.isSuccess()){
						qainfoService.insertQainfo(qainfo);
						jsonResult.setSuccess(true);
						//jsonResult.setMessage(egovMessageSource.getMessage("success.common.insert"));
						if("SITE".equals(qainfo.getQaSeCode())) {
							jsonResult.setMessage("1:1문의가 등록되었습니다.");
						}else if("GOODS".equals(qainfo.getQaSeCode())) {
							jsonResult.setMessage("Q&A가 정상적으로 등록되었습니다.");
						}else {
							jsonResult.setMessage("정상적으로 등록되었습니다.");
						}
					}
					
					BiztalkVO bizTalk = new BiztalkVO();
					/* 상품 문의 등록 알림톡 */
					if ("GOODS".equals(qainfo.getQaSeCode())) {
						GoodsVO goods = new GoodsVO();
						goods.setGoodsId(qainfo.getGoodsId());
						GoodsVO goodsInfo = goodsService.selectGoods(goods);
						
						CmpnyVO cmpny = new CmpnyVO();
						cmpny.setCmpnyId(goodsInfo.getCmpnyId());
						CmpnyVO cmpnyInfo = cmpnyService.selectCmpny(cmpny);
						bizTalk = new BiztalkVO();
						bizTalk.setRecipient(cmpnyInfo.getChargerTelno());
						bizTalk.setTmplatCode("template_015");
						/*[모두의구독] 문의가 등록되었습니다. * 상품명 : #{PRODUCTNAME}
						 * 
						 */
						BiztalkVO template = biztalkService.selectBizTalkTemplate(bizTalk);
						String message = template.getTmplatCn();
						message = message.replaceAll("#\\{PRODUCTNAME\\}", qainfo.getFrstRegistPnttm());
						bizTalk.setMessage(message);
					}
					/* 1:1 문의 등록 알림톡 */
					else if ("SITE".equals(qainfo.getQaSeCode())) {
						
						CmpnyVO cmpny = new CmpnyVO();
						cmpny.setCmpnyId("CMPNY_00000000000042");
						CmpnyVO cmpnyInfo = cmpnyService.selectCmpny(cmpny);
						bizTalk = new BiztalkVO();
						bizTalk.setRecipient(cmpnyInfo.getChargerTelno());
						bizTalk.setTmplatCode("template_018");
						/*[모두의구독] 1:1문의가 등록되었습니다.
						 * 등록일시 : #{REGDATETIME}
						 */
						BiztalkVO template = biztalkService.selectBizTalkTemplate(bizTalk);
						String message = template.getTmplatCn();
						message = message.replaceAll("#\\{REGDATETIME\\}", qainfo.getFrstRegistPnttm());
						bizTalk.setMessage(message);
						
						/* 임시, 추후 부담당자 추가 시 삭제 예정 */
						BiztalkVO result = biztalkService.sendAlimTalk(bizTalk);
						bizTalk.setRecipient("01048061787");
					}
					
					BiztalkVO result = biztalkService.sendAlimTalk(bizTalk);
				
				}
			} catch (Exception e) {
				LOGGER.error("Exception: "+e);
				jsonResult.setMessage(egovMessageSource.getMessage("fail.common.insert"));
				jsonResult.setSuccess(false);
			}
		}else{
			jsonResult.setMessage(egovMessageSource.getMessage("fail.common.login"));
		}
			return jsonResult;
	}

	/*
	 * 사용자 질문 수정
	 * @param qainfo
	 * @param bindingResult
	 * @return
	 */
	@RequestMapping(value="/qainfo/updateQainfo.json", method=RequestMethod.POST)
	@ResponseBody
	public JsonResult updateQainfo(@Valid QainfoVO qainfo, BindingResult bindingResult, MultipartRequest multiRequest){
	
		JsonResult jsonResult = new JsonResult();
		
		Boolean isLogin = EgovUserDetailsHelper.isAuthenticated();
		
		if(isLogin){
			LoginVO user = (LoginVO)EgovUserDetailsHelper.getAuthenticatedUser();
		
			try {
				if(!this.isHasErrorsJson(bindingResult, jsonResult,"<br/>")){
					
					if(qainfo.getGoodsId()!=null){
						qainfo.setQestnTyCode(qainfo.getQestnTyCode());
						if(qainfo.getSecretAt()==null)qainfo.setSecretAt("N");
					
					}else if(qainfo.getGoodsId()==null){
						qainfo.setQaSeCode("SITE");  //1:1문의
						
						//파일 업로드
						final List<MultipartFile> fileList = multiRequest.getFiles("atchFile");

						for (MultipartFile file : fileList) {
							System.out.println(file.getOriginalFilename());
						}
						
						String atchFileId = qainfo.getAtchFileId();
						String prefixPath = "QNA";
						if(!fileList.isEmpty()) {
							
							if(StringUtils.isEmpty(atchFileId)){
								List<FileVO> files = fileMngUtil.parseFileInf(fileList, "QNA_", 0, "", "", prefixPath); // 저장경로 : src/main/resources/egovframework/egovProps/globals.properties -> Globals.fileStorePath 참고
								atchFileId = fileMngService.insertFileInfs(files);
								qainfo.setAtchFileId(atchFileId); // 첨부파일고유ID
							}else{
								FileVO fvo = new FileVO();
								fvo.setAtchFileId(atchFileId);
								int cnt = fileMngService.getMaxFileSN(fvo);
								List<FileVO> files = fileMngUtil.parseFileInf(fileList, "QNA_", cnt, atchFileId, "", prefixPath);
								fileMngService.updateFileInfs(files);
							}
						}
						//파일 업로드 끝
					}
					
					// TODO : 사용자 페이지에서 ID, 이름을 받아 전송되는것을 방지하기 위해 처리했는데. 데이터 베이스 저장 할때는 WRTER_NM, WRTER_ID, EMAIL은 수정하면 안되게 처리해야한다.
					qainfo.setWrterNm(user.getName());
					qainfo.setWrterId(user.getId());
					qainfo.setEmailAdres(user.getEmail());
					
					if(StringUtils.isNotEmpty(qainfo.getTelno1()) && StringUtils.isNotEmpty(qainfo.getTelno2()) && StringUtils.isNotEmpty(qainfo.getTelno3())){
						qainfo.setWrterTelno(qainfo.getTelno1()+"-"+qainfo.getTelno2()+"-"+qainfo.getTelno3());
					}
					
					qainfo.setQestnSj(CommonUtils.unscript(qainfo.getQestnSj()));
					qainfo.setQestnCn(CommonUtils.unscript(qainfo.getQestnCn()));
					qainfo.setLastUpdusrId(user.getUniqId());
					qainfoService.updateQainfoQestn(qainfo);
					jsonResult.setSuccess(true);
					//jsonResult.setMessage(egovMessageSource.getMessage("success.common.update"));
					if("SITE".equals(qainfo.getQaSeCode())) {
						jsonResult.setMessage("1:1문의가 수정되었습니다.");
					}else if("GOODS".equals(qainfo.getQaSeCode())) {
						jsonResult.setMessage("Q&A가 정상적으로 수정되었습니다.");
					}else {
						jsonResult.setMessage("정상적으로 수정되었습니다.");
					}
					
				}
			} catch (Exception e) {
				LOGGER.error("Exception: "+e);
				jsonResult.setMessage(egovMessageSource.getMessage("fail.common.update"));
				jsonResult.setSuccess(false);
			}
		}else{
			jsonResult.setMessage(egovMessageSource.getMessage("fail.common.login"));
		}
		return jsonResult;
	}
	
	/**
	 * 질답 삭제
	 * @param qainfo
	 * @return
	 */
	@RequestMapping(value = "/qainfo/deleteQainfo.json")
	@ResponseBody
	public JsonResult deleteQainfo(QainfoVO qainfo,@RequestParam("qaId") String qaId){
		JsonResult jsonResult = new JsonResult();
		
		Boolean isLogin = EgovUserDetailsHelper.isAuthenticated();
		
		if(isLogin){
			LoginVO user = (LoginVO)EgovUserDetailsHelper.getAuthenticatedUser();

			try {
				if(StringUtils.isEmpty(qainfo.getQaId()) || StringUtils.isEmpty(qaId)) {
					this.vaildateMessage(egovMessageSource.getMessage("cms.fail.access"), jsonResult); // 잘못된 접근입니다.
					LOGGER.error("qaId 가 없음.");
				}else {
					
					
					
					// TODO : 질답글이 내가 쓴 글인지 확인 후 삭제 처리
					
					
					
					
					
					
					
					
					
					qainfo.setLastUpdusrId(user.getUniqId());
					qainfoService.deleteQainfo(qainfo);
					jsonResult.setSuccess(true);
					jsonResult.setMessage(egovMessageSource.getMessage("success.common.delete")); //삭제가 성공하였습니다.
				}
	
			} catch(Exception e) {
				loggerError(LOGGER, e);
				jsonResult.setSuccess(false);
				jsonResult.setMessage(egovMessageSource.getMessage("fail.common.delete")); //삭제가 실패하였습니다.
			}
		}else{
			jsonResult.setMessage(egovMessageSource.getMessage("fail.common.login"));//로그인 정보가 올바르지 않습니다.
		}
		return jsonResult;
	}

	
}
