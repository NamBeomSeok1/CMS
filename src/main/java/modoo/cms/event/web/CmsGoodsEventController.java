package modoo.cms.event.web;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.MultipartRequest;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import egovframework.com.cmm.EgovMessageSource;
import egovframework.com.cmm.LoginVO;
import egovframework.com.cmm.service.EgovCmmUseService;
import egovframework.com.cmm.util.EgovUserDetailsHelper;
import egovframework.rte.psl.dataaccess.util.EgovMap;
import egovframework.rte.ptl.mvc.tags.ui.pagination.PaginationInfo;
import modoo.module.common.service.FileMngUtil;
import modoo.module.common.service.JsonResult;
import modoo.module.common.web.CommonDefaultController;
import modoo.module.event.service.GoodsEventMapngService;
import modoo.module.event.service.GoodsEventService;
import modoo.module.event.service.impl.GoodsEventMapngVO;
import modoo.module.event.service.impl.GoodsEventVO;
import modoo.module.shop.cmpny.service.PrtnrService;
import modoo.module.shop.cmpny.service.PrtnrVO;
import modoo.module.shop.goods.brand.service.GoodsBrandImageVO;
import modoo.module.shop.goods.info.service.GoodsService;

@Controller
public class CmsGoodsEventController extends CommonDefaultController {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(CmsGoodsEventController.class);

	/** EgovMessageSource */
	@Resource(name = "egovMessageSource")
	protected EgovMessageSource egovMessageSource;
	
	@Resource(name = "goodsEventService")
	private GoodsEventService goodsEventService;
	
	@Resource(name = "goodsEventMapngService")
	private GoodsEventMapngService goodsEventMapngService;
	
	@Resource(name = "goodsService")
	private GoodsService goodsService;
	
	@Resource(name = "prtnrService")
	private PrtnrService prtnrService;
	
	/** EgovCmmUseService */
	@Resource(name = "EgovCmmUseService")
	private EgovCmmUseService cmmUseService;
	
	@Resource(name = "fileMngUtil")
	private FileMngUtil fileMngUtil;
	
	/**
	 * 이벤트 목록
	 * @param searchVO
	 * @param model
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/decms/event/eventList.json", method=RequestMethod.GET)
	public JsonResult eventList(GoodsEventVO searchVO, Model model) {
		JsonResult result = new JsonResult();

		try {
			
			/** 페이징 처리 */
			PaginationInfo paginationInfo = new PaginationInfo();
			this.setPagination(paginationInfo, searchVO);
			int totalRecordCount = goodsEventService.selectGoodsEventListCnt(searchVO);
			paginationInfo.setTotalRecordCount(totalRecordCount);
			
			List<?> dataList = goodsEventService.selectGoodsEventList(searchVO);
			
			result.put("list", dataList);
			result.put("paginationInfo", paginationInfo);
			result.setMessage("");
			result.setSuccess(true);
			
		}catch (Exception e) {
			e.printStackTrace();
			result.setMessage("");
			result.setSuccess(false);
		}
	
		return result;
	}
	
	/**
	 * 이벤트 관리 페이지
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/decms/event/eventManage.do")
	public String eventManage(Model model) throws Exception {
		//내부 직원 권한이 아닐때 
		if(!EgovUserDetailsHelper.getAuthorities().contains("ROLE_EMPLOYEE")) {
			return "redirect:/decms/index.do";
		}
		
		//제휴사 구분
		PrtnrVO prtnrVO = new PrtnrVO();
		List<?> prtnrList = prtnrService.selectPrtnrList(prtnrVO);
		model.addAttribute("prtnrList", prtnrList);
		
		return "modoo/cms/event/eventManage";
	}
	
	/**
	 * 이벤트 등록/수정 폼
	 * @param searchVO
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/decms/event/eventForm.do")
	public String eventForm(GoodsEventVO searchVO, Model model) throws Exception {
		//내부 직원 권한이 아닐때 
		if(!EgovUserDetailsHelper.getAuthorities().contains("ROLE_EMPLOYEE")) {
			return "redirect:/decms/index.do";
		}

		//제휴사 구분
		PrtnrVO prtnrVO = new PrtnrVO();
		List<?> prtnrList = prtnrService.selectPrtnrList(prtnrVO);
		model.addAttribute("prtnrList", prtnrList);
		
		GoodsEventVO goodsEventVO = goodsEventService.selectGoodsEvent(searchVO);

		model.addAttribute("prtnrList", prtnrList);
		model.addAttribute("goodsEvent", goodsEventVO);
		
		return "modoo/cms/event/eventForm";
	}
	
	/**
	 * 이벤트 상품 목록
	 * @param searchVO
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/decms/event/eventGoodsList.json", method=RequestMethod.GET)
	public JsonResult eventGoodsList(GoodsEventVO searchVO) {
		JsonResult result = new JsonResult();

		try {

			GoodsEventMapngVO searchMapngVO = new GoodsEventMapngVO();
			searchMapngVO.setEventNo(searchVO.getEventNo());
			List<?> goodsEventMapngList = goodsEventMapngService.selectGoodsEventMapngList(searchMapngVO);
			result.put("list", goodsEventMapngList);	
			result.setMessage("");
			result.setSuccess(true);
			
		}catch (Exception e) {
			e.printStackTrace();
			result.setMessage("");
			result.setSuccess(false);
		}
	
		return result;
	}

	/**
	 * 이벤트 삭제
	 * @param searchVO
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/decms/event/deleteGoodsEvent.do", method=RequestMethod.GET)
	public JsonResult deleteGoodsEvent(GoodsEventVO searchVO) {
		JsonResult result = new JsonResult();
		
		try {
			goodsEventService.deleteGoodsEvent(searchVO);
			result.setMessage("");
			result.setSuccess(true);
		}catch (Exception e) {
			e.printStackTrace();
			result.setMessage("");
			result.setSuccess(false);
		}
	
		return result;
	}
	
	/**
	 * 이벤트 등록
	 * @param multiRequest
	 * @param goodsEvent
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value="/decms/event/registEvent.json", method=RequestMethod.POST)
	public JsonResult registEvent(MultipartHttpServletRequest multiRequest, GoodsEventVO goodsEvent, Model model) throws Exception {
		JsonResult result = new JsonResult();
		
		try {
			LoginVO user = (LoginVO)EgovUserDetailsHelper.getAuthenticatedUser();
			goodsEvent.setFrstRegisterId(user.getUniqId());
			if ("".equals(goodsEvent.getCmpnyId())) {
				goodsEvent.setCmpnyId(null);
			}
			if ("".equals(goodsEvent.getPrtnrId())) {
				goodsEvent.setPrtnrId(null);
			}

			processEventImage(multiRequest, goodsEvent);
			
			Integer nextEventNo = goodsEventService.selectNextEventNo();
			goodsEvent.setEventNo(new BigDecimal(nextEventNo));
			goodsEventService.insertGoodsEvent(goodsEvent);
			result.setMessage("");
			result.setSuccess(true);
			result.put("nextEventNo", nextEventNo);
			
		}catch (Exception e) {
			e.printStackTrace();
			result.setMessage("");
			result.setSuccess(false);
		}
	
		return result;
	}
	
	/**
	 * 이벤트 상품 목록 등록/수정
	 * @param request
	 * @param goodsList
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value="/decms/event/registEventGoods.json", method=RequestMethod.POST)
	public JsonResult registEventGoods(HttpServletRequest request, GoodsEventVO goodsEventVO, @RequestBody List<GoodsEventMapngVO> goodsList, Model model) throws Exception {
		JsonResult result = new JsonResult();
		
		try {

			/*GoodsEventMapngVO searchVO = new GoodsEventMapngVO();
			searchVO.setEventNo(goodsEventVO.getEventNo());
			goodsEventMapngService.deleteGoodsEventMapngList(searchVO);
			
			for (GoodsEventMapngVO goodsEventMapngVO : goodsList) {
				goodsEventMapngService.insertGoodsEventMapng(goodsEventMapngVO);
			}*/
			
			GoodsEventMapngVO searchVO = new GoodsEventMapngVO();
			searchVO.setEventNo(goodsEventVO.getEventNo());
			
			goodsEventMapngService.registEventGoods(searchVO, goodsList);
			
			result.setMessage("");
			result.setSuccess(true);
			
		}catch (Exception e) {
			e.printStackTrace();
			result.setMessage("");
			result.setSuccess(false);
		}
	
		return result;
	}
	
	/**
	 * 이벤트 수정
	 * @param multiRequest
	 * @param goodsEvent
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value="/decms/event/modifyEvent.json", method=RequestMethod.POST)
	public JsonResult modifyEvent(MultipartHttpServletRequest multiRequest, GoodsEventVO goodsEvent, Model model) throws Exception {
		JsonResult result = new JsonResult();
		
		try {
			LoginVO user = (LoginVO)EgovUserDetailsHelper.getAuthenticatedUser();
			goodsEvent.setLastUpdusrId(user.getUniqId());
			if ("".equals(goodsEvent.getCmpnyId())) {
				goodsEvent.setCmpnyId(null);
			}
			if ("".equals(goodsEvent.getPrtnrId())) {
				goodsEvent.setPrtnrId(null);
			}
			
			processEventImage(multiRequest, goodsEvent);
			goodsEventService.updateGoodsEvent(goodsEvent);
			
			result.put("nextEventNo", goodsEvent.getEventNo());
			result.setMessage("");
			result.setSuccess(true);
			
		}catch (Exception e) {
			e.printStackTrace();
			result.setMessage("");
			result.setSuccess(false);
		}
	
		return result;
	}
	
	/**
	 * 이벤트 이미지 삭제	
	 * @param goodsEvent
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value="/decms/event/deleteEventImg.json", method=RequestMethod.POST)
	public JsonResult deleteEventImg(GoodsEventVO goodsEvent, Model model) throws Exception {
		JsonResult result = new JsonResult();
		
		try {
			LoginVO user = (LoginVO)EgovUserDetailsHelper.getAuthenticatedUser();
			goodsEvent.setLastUpdusrId(user.getUniqId());

			goodsEventService.deleteEventImg(goodsEvent);
			result.setMessage("");
			result.setSuccess(true);
			
		}catch (Exception e) {
			e.printStackTrace();
			result.setMessage("");
			result.setSuccess(false);
		}
	
		return result;
	}
	
	/**
	 * 이미지 처리 메소드
	 * @param multiRequest
	 * @param goodsEvent
	 * @throws Exception
	 */
	void processEventImage(MultipartHttpServletRequest multiRequest, GoodsEventVO goodsEvent) throws Exception {
		
		// 이미지 수정일 경우
		GoodsEventVO orgData = goodsEventService.selectGoodsEvent(goodsEvent);
		
		//썸네일
		final MultipartFile repFile1 = multiRequest.getFile("eventThumbnailPath");
		if(repFile1 != null && !repFile1.isEmpty()) {
			EgovMap fmap = fileMngUtil.parseImageContentFile(repFile1, 975, 564, null, null);
			String eventThumbnail = (String) fmap.get("orignFileUrl");
			goodsEvent.setEventThumbnail(eventThumbnail);
		}
		
		//배너
		/*final MultipartFile repFile2 = multiRequest.getFile("eventBannerImgPath");
		if(repFile2 != null && !repFile2.isEmpty()) {
			EgovMap fmap = fileMngUtil.parseImageContentFile(repFile2, 453, 260, null, null);
			String eventBannerImg = (String) fmap.get("orignFileUrl");
			goodsEvent.setEventBannerImg(eventBannerImg);
		}*/
		
		//메인 배너(PC)
		final MultipartFile repFile3 = multiRequest.getFile("eventMainImgPcPath");
		if(repFile3 != null && !repFile3.isEmpty()) {
			EgovMap fmap = fileMngUtil.parseImageContentFile(repFile3, 1400, 150, null, null);
			String eventMainImgPc = (String) fmap.get("orignFileUrl");
			goodsEvent.setEventMainImgPc(eventMainImgPc);
		}
		
		//메인 배너(MOBILE)
		final MultipartFile repFile4 = multiRequest.getFile("eventMainImgMobPath");
		if(repFile4 != null && !repFile4.isEmpty()) {
			EgovMap fmap = fileMngUtil.parseImageContentFile(repFile4, 1125, 300, null, null);
			String eventMainImgMob = (String) fmap.get("orignFileUrl");
			goodsEvent.setEventMainImgMob(eventMainImgMob);
		}
		
		//상세페이지
		final MultipartFile repFile5 = multiRequest.getFile("eventDetailImgPath");
		if(repFile5 != null && !repFile5.isEmpty()) {
			EgovMap fmap = fileMngUtil.parseImageContentFile(repFile5, 453, 260, null, null);
			String eventDetailImg = (String) fmap.get("orignFileUrl");
			if (orgData != null) {
				if (orgData.getEventDetailImg() != null) {
					if (!orgData.getEventDetailImg().equals(goodsEvent.getEventDetailImg())) {	
						goodsEvent.setEventDetailImg(eventDetailImg);
					}	
				}
			} else {
				goodsEvent.setEventDetailImg(eventDetailImg);
			}
		}
		
		//브랜드관 배너(PC)
		final MultipartFile repFile6 = multiRequest.getFile("eventBrandImgPcPath");
		if(repFile6 != null && !repFile6.isEmpty()) {
			EgovMap fmap = fileMngUtil.parseImageContentFile(repFile6, 1400, 150, null, null);
			String eventBrandImgPc = (String) fmap.get("orignFileUrl");
			if (orgData != null) {
				if (orgData.getEventBrandImgPc() != null) {
					if (!orgData.getEventBrandImgPc().equals(goodsEvent.getEventBrandImgPc())) {	
						goodsEvent.setEventBrandImgPc(eventBrandImgPc);
					}	
				}
			} else {
				goodsEvent.setEventBrandImgPc(eventBrandImgPc);
			}
		}
		
		//브랜드관 배너(MOBILE)
		final MultipartFile repFile7 = multiRequest.getFile("eventBrandImgMobilePath");
		if(repFile7 != null && !repFile7.isEmpty()) {
			EgovMap fmap = fileMngUtil.parseImageContentFile(repFile7, 1125, 300, null, null);
			String eventBrandImgMobile = (String) fmap.get("orignFileUrl");
			if (orgData != null) {
				if (orgData.getEventBrandImgMobile() != null) {
					if (!orgData.getEventBrandImgMobile().equals(goodsEvent.getEventBrandImgMobile())) {	
						goodsEvent.setEventBrandImgMobile(eventBrandImgMobile);
					}	
				}
			} else {
				goodsEvent.setEventBrandImgMobile(eventBrandImgMobile);
			}
		}
	}
	
}
