package modoo.cms.best.web;

import java.math.BigDecimal;
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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import egovframework.com.cmm.ComDefaultCodeVO;
import egovframework.com.cmm.EgovMessageSource;
import egovframework.com.cmm.LoginVO;
import egovframework.com.cmm.service.EgovCmmUseService;
import egovframework.com.cmm.util.EgovUserDetailsHelper;
import egovframework.com.utl.fcc.service.EgovDateUtil;
import egovframework.rte.psl.dataaccess.util.EgovMap;
import egovframework.rte.ptl.mvc.tags.ui.pagination.PaginationInfo;
import modoo.module.banner.service.BannerService;
import modoo.module.banner.service.BannerVO;
import modoo.module.best.service.BestService;
import modoo.module.best.service.impl.BestVO;
import modoo.module.biztalk.service.BiztalkService;
import modoo.module.biztalk.service.BiztalkVO;
import modoo.module.common.service.FileMngUtil;
import modoo.module.common.service.JsonResult;
import modoo.module.common.util.SiteDomainHelper;
import modoo.module.common.web.CommonDefaultController;
import modoo.module.event.service.impl.GoodsEventVO;
import modoo.module.shop.cmpny.service.PrtnrCmpnyService;
import modoo.module.shop.cmpny.service.PrtnrCmpnyVO;
import modoo.module.shop.goods.info.service.GoodsService;
import modoo.module.shop.goods.info.service.GoodsVO;

@Controller
public class CmsBestController extends CommonDefaultController {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(CmsBestController.class);

	/** EgovMessageSource */
	@Resource(name = "egovMessageSource")
	protected EgovMessageSource egovMessageSource;
	
	@Resource(name = "bannerService")
	private BannerService bannerService;
	
	@Resource(name = "goodsService")
	private GoodsService goodsService;
	
	/** EgovCmmUseService */
	@Resource(name = "EgovCmmUseService")
	private EgovCmmUseService cmmUseService;
	
	@Resource(name = "prtnrCmpnyService")
	private PrtnrCmpnyService prtnrCmpnyService;
	
	@Resource(name = "bestService")
	private BestService bestService;
	
	@Resource(name = "fileMngUtil")
	private FileMngUtil fileMngUtil;

	@RequestMapping(value = "/decms/best/bestManage.do")
	public String bestManage(Model model) throws Exception {
		//내부 직원 권한이 아닐때 
		if(!EgovUserDetailsHelper.getAuthorities().contains("ROLE_EMPLOYEE")) {
			return "redirect:/decms/index.do";
		}
		
		//제휴사 목록
		List<?> prtnrList = prtnrCmpnyService.selectPrtnrCmpnyList(new PrtnrCmpnyVO());
		model.addAttribute("prtnrList", prtnrList);
		
	
		return "modoo/cms/best/bestManage";
	}
	
	@ResponseBody
	@RequestMapping(value = "/decms/best/prtnrList.json")
	public JsonResult prtnrList() throws Exception {
		JsonResult jsonResult = new JsonResult();

		//제휴사 목록
		List<?> prtnrList = prtnrCmpnyService.selectPrtnrCmpnyList(new PrtnrCmpnyVO());

		jsonResult.put("list", prtnrList);
		
		return jsonResult;
	}
	
	@RequestMapping(value = "/decms/best/bestForm.do")
	public String bestForm(BestVO searchVO, Model model) throws Exception {
		//내부 직원 권한이 아닐때 
		if(!EgovUserDetailsHelper.getAuthorities().contains("ROLE_EMPLOYEE")) {
			return "redirect:/decms/index.do";
		}
		
		//제휴사 목록
		List<?> prtnrList = prtnrCmpnyService.selectPrtnrCmpnyList(new PrtnrCmpnyVO());
		model.addAttribute("prtnrList", prtnrList);
		BestVO best = bestService.selectBest(searchVO);
		model.addAttribute("best", best);
		
		return "modoo/cms/best/bestForm";
	}
	
	@ResponseBody
	@RequestMapping(value="/decms/best/bestList.json", method=RequestMethod.GET)
	public JsonResult bestList(BestVO searchVO, Model model) throws Exception {
		JsonResult result = new JsonResult();
		
		try {
			
			/** 페이징 처리 */
			PaginationInfo paginationInfo = new PaginationInfo();
			this.setPagination(paginationInfo, searchVO);
			int totalRecordCount = bestService.selectBestListCnt(searchVO);
			paginationInfo.setTotalRecordCount(totalRecordCount);
			
			List<?> dataList = bestService.selectBestList(searchVO);
			
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

	@ResponseBody
	@RequestMapping(value="/decms/best/registBest.json", method=RequestMethod.POST)
	public JsonResult registBest(MultipartHttpServletRequest multiRequest, BestVO bestVO, Model model) throws Exception {
		JsonResult result = new JsonResult();
		
		try {
			LoginVO user = (LoginVO)EgovUserDetailsHelper.getAuthenticatedUser();
			bestVO.setFrstRegisterId(user.getUniqId());
			if ("".equals(bestVO.getPrtnrId())) {
				bestVO.setPrtnrId(null);
			}

			processEventImage(multiRequest, bestVO);
			
			Integer nextBestNo = bestService.selectNextBestNo();
			bestVO.setBestNo(new BigDecimal(nextBestNo));
			bestService.insertBest(bestVO);
			result.setMessage("");
			result.setSuccess(true);
			result.put("nextBestNo", nextBestNo);
			
		}catch (Exception e) {
			e.printStackTrace();
			result.setMessage("");
			result.setSuccess(false);
		}
	
		return result;
	}
	
	@ResponseBody
	@RequestMapping(value="/decms/best/modifyBest.json", method=RequestMethod.POST)
	public JsonResult modifyBest(MultipartHttpServletRequest multiRequest, BestVO bestVO, Model model) throws Exception {
		JsonResult result = new JsonResult();
		
		try {
			LoginVO user = (LoginVO)EgovUserDetailsHelper.getAuthenticatedUser();
			bestVO.setLastUpdusrId(user.getUniqId());
			if ("".equals(bestVO.getPrtnrId())) {
				bestVO.setPrtnrId(null);
			}
			
			processEventImage(multiRequest, bestVO);
			bestService.updateBest(bestVO);
			
			result.put("nextBestNo", bestVO.getBestNo());
			result.setMessage("");
			result.setSuccess(true);
			
		}catch (Exception e) {
			e.printStackTrace();
			result.setMessage("");
			result.setSuccess(false);
		}
	
		return result;
	}
	
	@ResponseBody
	@RequestMapping(value="/decms/best/deleteBestImg.json", method=RequestMethod.POST)
	public JsonResult deleteBestImg(BestVO bestVO, Model model) throws Exception {
		JsonResult result = new JsonResult();
		
		try {
			LoginVO user = (LoginVO)EgovUserDetailsHelper.getAuthenticatedUser();
			bestVO.setLastUpdusrId(user.getUniqId());

			bestService.deleteBestImg(bestVO);
			result.setMessage("");
			result.setSuccess(true);
			
		}catch (Exception e) {
			e.printStackTrace();
			result.setMessage("");
			result.setSuccess(false);
		}
	
		return result;
	}
	
	void processEventImage(MultipartHttpServletRequest multiRequest, BestVO searchVO) throws Exception {
		
		//썸네일
		final MultipartFile repFile1 = multiRequest.getFile("bestThumbnailPath");
		if(repFile1 != null && !repFile1.isEmpty()) {
			EgovMap fmap = fileMngUtil.parseImageContentFile(repFile1, 975, 564, null, null);
			String bestThumbnail = (String) fmap.get("orignFileUrl");
			searchVO.setBestThumbnail(bestThumbnail);
		}
		
		//메인 배너(PC)
		final MultipartFile repFile3 = multiRequest.getFile("bestImgPcPath");
		if(repFile3 != null && !repFile3.isEmpty()) {
			EgovMap fmap = fileMngUtil.parseImageContentFile(repFile3, 975, 564, null, null);
			String bestImgPc = (String) fmap.get("orignFileUrl");
			searchVO.setBestImgPc(bestImgPc);
		}
		
		//메인 배너(MOBILE)
		final MultipartFile repFile4 = multiRequest.getFile("bestImgMobPath");
		if(repFile4 != null && !repFile4.isEmpty()) {
			EgovMap fmap = fileMngUtil.parseImageContentFile(repFile4, 975, 564, null, null);
			String bestImgMob = (String) fmap.get("orignFileUrl");
			searchVO.setBestImgMob(bestImgMob);
		}

	}

}
