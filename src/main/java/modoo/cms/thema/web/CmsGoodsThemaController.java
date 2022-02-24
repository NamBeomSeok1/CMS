package modoo.cms.thema.web;

import java.math.BigDecimal;
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

import egovframework.com.cmm.LoginVO;
import egovframework.com.cmm.util.EgovUserDetailsHelper;
import egovframework.rte.psl.dataaccess.util.EgovMap;
import egovframework.rte.ptl.mvc.tags.ui.pagination.PaginationInfo;
import modoo.module.common.service.FileMngUtil;
import modoo.module.common.service.JsonResult;
import modoo.module.common.web.CommonDefaultController;
import modoo.module.event.service.impl.GoodsEventVO;
import modoo.module.shop.cmpny.service.PrtnrService;
import modoo.module.shop.cmpny.service.PrtnrVO;
import modoo.module.shop.goods.info.service.GoodsService;
import modoo.module.thema.service.GoodsThemaMapngService;
import modoo.module.thema.service.GoodsThemaService;
import modoo.module.thema.service.impl.GoodsThemaMapngVO;
import modoo.module.thema.service.impl.GoodsThemaVO;

@Controller
public class CmsGoodsThemaController extends CommonDefaultController {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(CmsGoodsThemaController.class);
	
	@Resource(name="goodsThemaService")
	private GoodsThemaService goodsThemaService;
	
	@Resource(name="goodsThemaMapngService")
	private GoodsThemaMapngService goodsThemaMapngService;
	
	@Resource(name = "goodsService")
	private GoodsService goodsService;
	
	@Resource(name = "prtnrService")
	private PrtnrService prtnrService;
	
	@Resource(name = "fileMngUtil")
	private FileMngUtil fileMngUtil;
	
	private static final String EZWEL_PRTNR = "PRTNR_0001";
	
	/**
	 * 테마목록
	 * @param searchVO
	 * @param model
	 * @return
	 */
	@RequestMapping("/decms/thema/themaList.json")
	@ResponseBody
	public JsonResult themaList(GoodsThemaVO searchVO,Model model){
		
		JsonResult jsonResult = new JsonResult();
		
		try {
			/** 페이징 처리 */
			PaginationInfo paginationInfo = new PaginationInfo();
			this.setPagination(paginationInfo, searchVO);
			int totalRecordCount = goodsThemaService.selectGoodsThemaListCnt(searchVO);
			paginationInfo.setTotalRecordCount(totalRecordCount);
			
			List<GoodsThemaVO> dataList = goodsThemaService.selectGoodsThemaList(searchVO);
			
			jsonResult.put("list", dataList);
			jsonResult.put("paginationInfo", paginationInfo);
			jsonResult.setSuccess(true);
			
		} catch (Exception e) {
			jsonResult.setSuccess(false);
			LOGGER.error(e.toString());
		}
		
		return jsonResult;
	}
	
	/**
	 * 테마관리이동
	 * @param model
	 * @param goodsThema
	 * @return
	 */
	@RequestMapping(value="/decms/thema/themaManage.do")
	public String themaManage(Model model,GoodsThemaVO goodsThema) throws Exception{
		
		//내부 직원 권한이 아닐때 
		if(!EgovUserDetailsHelper.getAuthorities().contains("ROLE_EMPLOYEE")) {
			return "redirect:/decms/index.do";
		}
		//제휴사 구분
		PrtnrVO prtnrVO = new PrtnrVO();
		List<?> prtnrList = prtnrService.selectPrtnrList(prtnrVO);
		model.addAttribute("prtnrList", prtnrList);
			
		return "modoo/cms/thema/themaManage";
	}

	
	/**
	 * 테마등록이동
	 * @param model
	 * @param goodsThema
	 * @return
	 */
	@RequestMapping(value="/decms/thema/themaForm.do")
	public String themaForm(Model model,GoodsThemaVO goodsThema) throws Exception{
		
		//내부 직원 권한이 아닐때 
		if(!EgovUserDetailsHelper.getAuthorities().contains("ROLE_EMPLOYEE")) {
			return "redirect:/decms/index.do";
		}
		//제휴사 구분
		PrtnrVO prtnrVO = new PrtnrVO();
		List<?> prtnrList = prtnrService.selectPrtnrList(prtnrVO);
		
		goodsThema = goodsThemaService.selectGoodsThema(goodsThema);
		if(goodsThema==null){
			int nextSn = goodsThemaService.selectNextThemaSn(goodsThema);
			model.addAttribute("nextSn",nextSn);
		}
		
		model.addAttribute("prtnrList", prtnrList);
		model.addAttribute("goodsThema", goodsThema);
		
		
		return "modoo/cms/thema/themaForm";
	}
	
	/**
	 * 테마 상품 목록
	 * @param searchVO
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/decms/thema/themaGoodsList.json", method=RequestMethod.GET)
	public JsonResult themaGoodsList(GoodsThemaVO searchVO) {
		JsonResult result = new JsonResult();

		try {

			GoodsThemaMapngVO searchMapngVO = new GoodsThemaMapngVO();
			searchMapngVO.setThemaNo(searchVO.getThemaNo());
			List<?> goodsThemaMapngList = goodsThemaMapngService.selectGoodsThemaMapngList(searchMapngVO);
			result.put("list", goodsThemaMapngList);	
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
	 * 테마 삭제
	 * @param searchVO
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/decms/thema/deleteGoodsThema.do", method=RequestMethod.GET)
	public JsonResult deleteGoodsThema(GoodsThemaVO searchVO) {
		JsonResult result = new JsonResult();
		
		try {
			goodsThemaService.deleteGoodsThema(searchVO);
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
	 * 테마 등록
	 * @param multiRequest
	 * @param goodsThema
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value="/decms/thema/registThema.json", method=RequestMethod.POST)
	public JsonResult registThema(MultipartHttpServletRequest multiRequest, GoodsThemaVO goodsThema, Model model) throws Exception {
		JsonResult result = new JsonResult();
		
		try {
			LoginVO user = (LoginVO)EgovUserDetailsHelper.getAuthenticatedUser();
			goodsThema.setFrstRegisterId(user.getUniqId());
			
			if ("".equals(goodsThema.getPrtnrId())) {
				goodsThema.setPrtnrId(null);
			}

			Integer nextThemaNo = goodsThemaService.selectNextThemaNo();
			goodsThema.setThemaNo(new BigDecimal(nextThemaNo));
			processThemaImage(multiRequest, goodsThema);
			result.put("nextThemaNo", nextThemaNo);
			
			goodsThemaService.insertGoodsThema(goodsThema);
			
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
	 * 테마 상품 목록 등록/수정
	 * @param request
	 * @param goodsList
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value="/decms/thema/registThemaGoods.json", method=RequestMethod.POST)
	public JsonResult registThemaGoods(HttpServletRequest request, GoodsThemaVO goodsThemaVO, @RequestBody List<GoodsThemaMapngVO> goodsList, Model model) throws Exception {
		JsonResult result = new JsonResult();
		
		try {

			GoodsThemaMapngVO searchVO = new GoodsThemaMapngVO();
			searchVO.setThemaNo(goodsThemaVO.getThemaNo());
			
			goodsThemaMapngService.registThemaGoods(searchVO, goodsList);
			
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
	 * 테마 수정
	 * @param multiRequest
	 * @param goodsThema
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value="/decms/thema/modifyThema.json", method=RequestMethod.POST)
	public JsonResult modifyThema(MultipartHttpServletRequest multiRequest, GoodsThemaVO goodsThema, Model model) throws Exception {
		JsonResult result = new JsonResult();
		
		try {
			LoginVO user = (LoginVO)EgovUserDetailsHelper.getAuthenticatedUser();
			goodsThema.setLastUpdusrId(user.getUniqId());
			
			if ("".equals(goodsThema.getPrtnrId())) {
				goodsThema.setPrtnrId(null);
			}
			
			processThemaImage(multiRequest, goodsThema);
			goodsThemaService.updateGoodsThema(goodsThema);
			result.put("nextThemaNo", goodsThema.getThemaNo());
			
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
	 * 테마 이미지 삭제	
	 * @param goodsThema
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value="/decms/thema/deleteThemaImg.json", method=RequestMethod.POST)
	public JsonResult deleteThemaImg(GoodsThemaVO goodsThema, Model model) throws Exception {
		JsonResult result = new JsonResult();
		
		try {
			LoginVO user = (LoginVO)EgovUserDetailsHelper.getAuthenticatedUser();
			goodsThema.setLastUpdusrId(user.getUniqId());

			goodsThemaService.deleteThemaImg(goodsThema);
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
	 * @param goodsThema
	 * @throws Exception
	 */
	void processThemaImage(MultipartHttpServletRequest multiRequest, GoodsThemaVO goodsThema) throws Exception {
		
		// 이미지 수정일 경우
		GoodsThemaVO orgData = goodsThemaService.selectGoodsThema(goodsThema);
		
		//썸네일
		final MultipartFile repFile1 = multiRequest.getFile("themaThumbnailPath");
		if(repFile1 != null && !repFile1.isEmpty()) {
			EgovMap fmap = fileMngUtil.parseImageContentFile(repFile1, 975, 564, null, null);
			String themaThumbnail = (String) fmap.get("orignFileUrl");
			goodsThema.setThemaThumbnail(themaThumbnail);
		}
		
		//메인 배너(PC)
		final MultipartFile repFile3 = multiRequest.getFile("themaMainImgPcPath");
		if(repFile3 != null && !repFile3.isEmpty()) {
			EgovMap fmap = fileMngUtil.parseImageContentFile(repFile3, 1400, 150, null, null);
			String themaMainImgPc = (String) fmap.get("orignFileUrl");
			goodsThema.setThemaMainImgPc(themaMainImgPc);
		}
		
		//메인 배너(MOBILE)
		final MultipartFile repFile4 = multiRequest.getFile("themaMainImgMobPath");
		if(repFile4 != null && !repFile4.isEmpty()) {
			EgovMap fmap = fileMngUtil.parseImageContentFile(repFile4, 1125, 300, null, null);
			String themaMainImgMob = (String) fmap.get("orignFileUrl");
			goodsThema.setThemaMainImgMob(themaMainImgMob);
		}
		
		
	}
	
}
