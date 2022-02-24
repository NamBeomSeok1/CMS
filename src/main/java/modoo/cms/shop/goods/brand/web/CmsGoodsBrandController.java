package modoo.cms.shop.goods.brand.web;

import java.util.ArrayList;
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

import egovframework.com.cmm.EgovMessageSource;
import egovframework.com.cmm.LoginVO;
import egovframework.com.cmm.util.EgovUserDetailsHelper;
import egovframework.com.utl.fcc.service.EgovDateUtil;
import egovframework.rte.psl.dataaccess.util.EgovMap;
import egovframework.rte.ptl.mvc.tags.ui.pagination.PaginationInfo;
import modoo.module.common.service.FileMngUtil;
import modoo.module.common.service.JsonResult;
import modoo.module.common.util.CommonUtils;
import modoo.module.common.web.CommonDefaultController;
import modoo.module.shop.cmpny.service.CmpnyService;
import modoo.module.shop.cmpny.service.CmpnyVO;
import modoo.module.shop.goods.brand.service.GoodsBrandImageService;
import modoo.module.shop.goods.brand.service.GoodsBrandImageVO;
import modoo.module.shop.goods.brand.service.GoodsBrandService;
import modoo.module.shop.goods.brand.service.GoodsBrandVO;
import modoo.module.shop.goods.info.service.GoodsService;

@Controller
public class CmsGoodsBrandController extends CommonDefaultController {

	private static final Logger LOGGER = LoggerFactory.getLogger(CmsGoodsBrandController.class);
	
	@Resource(name="cmpnyService")
	CmpnyService cmpnyService;

	
	private static final Integer DEK_WIDTH = 1100;
	private static final Integer DEK_HEIGHT = 500;
	private static final Integer MOB_WIDTH = 1200;
	private static final Integer MOB_HEIGHT = 600;
	private static final Integer REP_WIDTH = 1920;
	private static final Integer REP_HEIGHT = 415;
	private static final Integer INT_WIDTH = 1400;
	private static final Integer EVT_WEB_HEIGHT = 150;
	private static final Integer EVT_WEB_WIDTH = 1400;
	private static final Integer EVT_MOB_HEIGHT = 231;
	private static final Integer EVT_MOB_WIDTH = 975;


	/** EgovMessageSource */
	@Resource(name = "egovMessageSource")
	protected EgovMessageSource egovMessageSource;
	
	@Resource(name = "goodsBrandService")
	private GoodsBrandService goodsBrandService;
	
	@Resource(name = "goodsBrandImageService")
	private GoodsBrandImageService goodsBrandImageService;
	
	@Resource(name = "fileMngUtil")
	private FileMngUtil fileMngUtil;
	
	/**
	 * 상품 브랜드 관리
	 * @param searchVO
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/decms/embed/shop/goods/goodsBrandManage.do")
	public String goodsBrandManage(@ModelAttribute("searchVO") GoodsBrandVO searchVO, Model model) throws Exception {
		return "modoo/cms/shop/goods/brand/goodsBrandManage";
	}
	
	/**
	 * 브랜드 관리
	 * @param searchVO
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/decms/shop/goods/brandManage.do")
	public String BrandManage(@ModelAttribute("searchVO") GoodsBrandVO searchVO,Model model,HttpServletRequest request) throws Exception {
		model.addAttribute("menuId",request.getParameter("menuId"));
		return "modoo/cms/shop/brand/brandManage";
	}
	
	/**
	 * 브랜드 목록
	 * @param searchVO
	 * @return
	 */
	@RequestMapping(value = "/decms/shop/goods/goodsBrandList.json")
	@ResponseBody
	public JsonResult goodsBrandList(GoodsBrandVO searchVO) {
		LoginVO user = (LoginVO)EgovUserDetailsHelper.getAuthenticatedUser();
		JsonResult jsonResult = new JsonResult();
		
		try {
			if(!EgovUserDetailsHelper.getAuthorities().contains("ROLE_EMPLOYEE")) {
				if(StringUtils.isNotEmpty(user.getCmpnyId())) {
					searchVO.setSearchCmpnyId(user.getCmpnyId());
				}else {
					jsonResult.setMessage(egovMessageSource.getMessage("cms.fail.not.cmpnyId")); //업체등록이 필요합니다.
					jsonResult.setSuccess(false);
					return jsonResult;
				}
			}

			if(StringUtils.isNotEmpty(searchVO.getSearchBgnde())) {
				searchVO.setSearchBgnde(EgovDateUtil.validChkDate(searchVO.getSearchBgnde()));
			}
			if(StringUtils.isNotEmpty(searchVO.getSearchEndde())) {
				searchVO.setSearchEndde(EgovDateUtil.validChkDate(searchVO.getSearchEndde()));
			}
			
			searchVO.setSearchOrderType("DESC");
			
			PaginationInfo paginationInfo = new PaginationInfo();
			searchVO.setPageUnit(propertiesService.getInt("gridPageUnit"));
			this.setPagination(paginationInfo, searchVO);
			
			List<?> resultList = goodsBrandService.selectGoodsBrandList(searchVO);
			System.out.println(resultList.toString());
			jsonResult.put("list", resultList);
			
			int totalRecordCount = goodsBrandService.selectGoodsBrandListCnt(searchVO);
			paginationInfo.setTotalRecordCount(totalRecordCount);
			jsonResult.put("paginationInfo", paginationInfo);
			
			jsonResult.setSuccess(true);
		} catch(Exception e) {
			loggerError(LOGGER, e);
			jsonResult.setSuccess(false);
			jsonResult.setMessage(egovMessageSource.getMessage("fail.common.select")); //조회에 실패하였습니다.
		}
		
		return jsonResult;
	}
	
	/**
	 * 브랜드등록/수정 폼
	 * @param searchVO
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/decms/shop/goods/writeBrand.do")
	public String writeBrand(@ModelAttribute("searchVO") GoodsBrandVO searchVO, Model model,
			HttpServletRequest request) throws Exception {
		LoginVO user = (LoginVO)EgovUserDetailsHelper.getAuthenticatedUser();
		
		if(!StringUtils.isEmpty(searchVO.getBrandId())){ // 수정
			//searchVO.setBrandId(request.getParameter("brandId"));
			GoodsBrandVO brand = goodsBrandService.selectGoodsBrand(searchVO);
			model.addAttribute("brand",brand);
		}else { // 신규등록
			GoodsBrandVO brand = new GoodsBrandVO();
			if(!EgovUserDetailsHelper.getAuthorities().contains("ROLE_EMPLOYEE")) {
				if(StringUtils.isEmpty(user.getCmpnyId())) {
					return "modoo/common/error/error";
				}
				//
				CmpnyVO cmpny = new CmpnyVO();
				cmpny.setCmpnyId(user.getCmpnyId());
				cmpny = cmpnyService.selectCmpny(cmpny);
				
				brand.setCmpnyNm(cmpny.getCmpnyNm());
				brand.setCmpnyId(user.getCmpnyId());
			}
			model.addAttribute("brand",brand);
		}
		model.addAttribute("menuId",request.getParameter("menuId"));
		
		return "modoo/cms/shop/brand/brandForm";
	}
	/**
	 * 브랜드 저장
	 * @param goodsBrand
	 * @param bindingResult
	 * @return
	 */
	@RequestMapping(value = "/decms/shop/goods/saveGoodsBrand.json", method = RequestMethod.POST)
	@ResponseBody
	public JsonResult writeGoodsBrand(final MultipartHttpServletRequest multiRequest,
			@Valid GoodsBrandVO goodsBrand, BindingResult bindingResult,
			@RequestParam(value = "isForm", required = false) String isForm,
			@RequestParam(value = "menuId") String menuId) {
		LoginVO user = (LoginVO)EgovUserDetailsHelper.getAuthenticatedUser();
		JsonResult jsonResult = new JsonResult();
		
		try {

			if(!EgovUserDetailsHelper.getAuthorities().contains("ROLE_EMPLOYEE")) {
				if(StringUtils.isNotEmpty(user.getCmpnyId())) {
					goodsBrand.setCmpnyId(user.getCmpnyId());
				}else {
					jsonResult.setMessage(egovMessageSource.getMessage("cms.fail.not.cmpnyId")); //업체등록이 필요합니다.
					jsonResult.setSuccess(false);
					return jsonResult;
				}
			}
			
			if(!this.isHasErrorsJson(bindingResult, jsonResult, "<br/>")) {
				goodsBrand.setBrandIntCn(CommonUtils.unscript(goodsBrand.getBrandIntCn()));
				goodsBrand.setBrandIntLink(CommonUtils.unscript(goodsBrand.getBrandIntLink()));
				
				if(goodsBrand.getSvcAdres()== null  || goodsBrand.getSvcAdres().length() == 0) {
					jsonResult.setMessage("교환/반품 주소는 필수 사항입니다.");
					jsonResult.setSuccess(false);
					return jsonResult;
				} else if(goodsBrand.getSvcHdryNm()== null  || goodsBrand.getSvcHdryNm().length() == 0) {
					jsonResult.setMessage("교환/반품 택배사명은 필수 사항입니다.");
					jsonResult.setSuccess(false);
					return jsonResult;
				} else if(goodsBrand.getRtngudDlvyPc() == null) {
					jsonResult.setMessage("반품 배송비는 필수 사항입니다.");
					jsonResult.setSuccess(false);
					return jsonResult;
				} else if(goodsBrand.getExchngDlvyPc() == null) {
					jsonResult.setMessage("교환 배송비는 필수 사항입니다.");
					jsonResult.setSuccess(false);
					return jsonResult;
				}
				
				final MultipartFile atchFile = multiRequest.getFile("atchLogoFile");
				if(atchFile != null && !atchFile.isEmpty()) {
					EgovMap fmap = fileMngUtil.parseImageContentFile(atchFile, null, null);
					String brandImagePath = (String) fmap.get("orignFileUrl");
					String brandImageThumbPath = (String) fmap.get("thumbUrl");
					goodsBrand.setBrandImagePath(brandImagePath);
					goodsBrand.setBrandImageThumbPath(brandImageThumbPath);
				}
				
				// 대표이미지 (컴퓨터)
				final MultipartFile repFile = multiRequest.getFile("atchRepFile");
				if(repFile != null && !repFile.isEmpty()) {
					List<GoodsBrandImageVO> repBrandImageList = new ArrayList<GoodsBrandImageVO>();
					EgovMap fmap = fileMngUtil.parseImageContentFile(repFile, REP_WIDTH, REP_HEIGHT, null, null);
					String brandImagePath = (String) fmap.get("orignFileUrl");
					String brandImageThumbPath = (String) fmap.get("thumbUrl");
					GoodsBrandImageVO brandImage = new GoodsBrandImageVO(); 
					brandImage.setBrandImageSeCode("REP"); // 대표이미지
					brandImage.setBrandImagePath(brandImagePath);
					brandImage.setBrandImageThumbPath(brandImageThumbPath);
					repBrandImageList.add(brandImage);
					goodsBrand.setRepBrandImageList(repBrandImageList);
				}
				
				// 대표이미지 (모바일)
				final MultipartFile repMobFile = multiRequest.getFile("atchRepMobFile");
				if(repMobFile != null && !repMobFile.isEmpty()) {
					List<GoodsBrandImageVO> repMobBrandImageList = new ArrayList<GoodsBrandImageVO>();
					EgovMap fmap = fileMngUtil.parseImageContentFile(repMobFile, MOB_WIDTH, MOB_HEIGHT, null, null);
					String brandImagePath = (String) fmap.get("orignFileUrl");
					String brandImageThumbPath = (String) fmap.get("thumbUrl");
					
					GoodsBrandImageVO brandImage = new GoodsBrandImageVO(); 
					brandImage.setBrandImageSeCode("REPMOB"); // 대표모바일이미지
					brandImage.setBrandImagePath(brandImagePath);
					brandImage.setBrandImageThumbPath(brandImageThumbPath);
					repMobBrandImageList.add(brandImage);
					goodsBrand.setRepMobBrandImageList(repMobBrandImageList);
				}

				// 이벤트 배너 (컴퓨터)
				final MultipartFile evtFile = multiRequest.getFile("atchEvtFile");
				if(evtFile != null && !evtFile.isEmpty()) {
					List<GoodsBrandImageVO> evtBrandImageList = new ArrayList<GoodsBrandImageVO>();
					EgovMap fmap = fileMngUtil.parseImageContentFile(evtFile, null, null, null, null);
					String brandImagePath = (String) fmap.get("orignFileUrl");
					String brandImageThumbPath = (String) fmap.get("thumbUrl");
					GoodsBrandImageVO brandImage = new GoodsBrandImageVO(); 
					brandImage.setBrandImageSeCode("EVT"); // 대표이미지
					brandImage.setBrandImagePath(brandImagePath);
					brandImage.setBrandImageThumbPath(brandImageThumbPath);
					evtBrandImageList.add(brandImage);
					goodsBrand.setEvtBrandImageList(evtBrandImageList);
				}
				
				// 이벤트 배너 (모바일)
				final MultipartFile evtMobFile = multiRequest.getFile("atchEvtMobFile");
				if(evtMobFile != null && !evtMobFile.isEmpty()) {
					List<GoodsBrandImageVO> evtMobBrandImageList = new ArrayList<GoodsBrandImageVO>();
					EgovMap fmap = fileMngUtil.parseImageContentFile(evtMobFile, null, null, null, null);
					String brandImagePath = (String) fmap.get("orignFileUrl");
					String brandImageThumbPath = (String) fmap.get("thumbUrl");
					
					GoodsBrandImageVO brandImage = new GoodsBrandImageVO(); 
					brandImage.setBrandImageSeCode("EVTMOB"); // 대표모바일이미지
					brandImage.setBrandImagePath(brandImagePath);
					brandImage.setBrandImageThumbPath(brandImageThumbPath);
					evtMobBrandImageList.add(brandImage);
					goodsBrand.setEvtMobBrandImageList(evtMobBrandImageList);
				}
				
				/*   // 목록으로 받을때 (추후 변경 가능성 있음)
				final List<MultipartFile> repFileList = multiRequest.getFiles("atchRepFile");
				if(!repFileList.isEmpty()) {
					List<GoodsBrandImageVO> repBrandImageList = new ArrayList<GoodsBrandImageVO>();
					for(MultipartFile mFile : repFileList) {
						GoodsBrandImageVO brandImage = new GoodsBrandImageVO(); 
						EgovMap fmap = fileMngUtil.parseImageContentFile(mFile, REP_WIDTH, REP_HEIGHT, null, null);
						String brandImagePath = (String) fmap.get("orignFileUrl");
						String brandImageThumbPath = (String) fmap.get("thumbUrl");
						brandImage.setBrandImageSeCode("REP"); // 대표이미지
						brandImage.setBrandImagePath(brandImagePath);
						brandImage.setBrandImageThumbPath(brandImageThumbPath);
						repBrandImageList.add(brandImage);
					}
					goodsBrand.setRepBrandImageList(repBrandImageList);
				}
				*/
				
				// 소개이미지
				final MultipartFile intFile = multiRequest.getFile("atchIntFile");
				if(intFile != null && !intFile.isEmpty()) {
					List<GoodsBrandImageVO> intBrandImageList = new ArrayList<GoodsBrandImageVO>();
					EgovMap fmap = fileMngUtil.parseImageContentFile(intFile, INT_WIDTH, null, null, null);
					String brandImagePath = (String) fmap.get("orignFileUrl");
					String brandImageThumbPath = (String) fmap.get("thumbUrl");
					
					GoodsBrandImageVO brandImage = new GoodsBrandImageVO(); 
					brandImage.setBrandImageSeCode("INT"); // 소개 이미지
					brandImage.setBrandImagePath(brandImagePath);
					brandImage.setBrandImageThumbPath(brandImageThumbPath);
					intBrandImageList.add(brandImage);
					
					goodsBrand.setIntBrandImageList(intBrandImageList);
				}
				/*   // 목록으로 받을때 (추후 변경 가능성 있음)
				final List<MultipartFile> intFileList = multiRequest.getFiles("atchIntFile");
				if(!intFileList.isEmpty()) {
					List<GoodsBrandImageVO> intBrandImageList = new ArrayList<GoodsBrandImageVO>();
					for(MultipartFile mFile : intFileList) {
						GoodsBrandImageVO brandImage = new GoodsBrandImageVO(); 
						EgovMap fmap = fileMngUtil.parseImageContentFile(mFile, INT_WIDTH, null, null, null);
						String brandImagePath = (String) fmap.get("orignFileUrl");
						String brandImageThumbPath = (String) fmap.get("thumbUrl");
						brandImage.setBrandImageSeCode("INT"); // 소개 이미지
						brandImage.setBrandImagePath(brandImagePath);
						brandImage.setBrandImageThumbPath(brandImageThumbPath);
						intBrandImageList.add(brandImage);
					}
					goodsBrand.setIntBrandImageList(intBrandImageList);
				}
				*/
				
				if(StringUtils.isEmpty(goodsBrand.getBrandId())) {
					goodsBrandService.insertGoodsBrand(goodsBrand);
				}else {
					goodsBrandService.updateGoodsBrand(goodsBrand);
				}
				
				//브랜드 메뉴 다시 읽기
				goodsBrandService.reloadGoodsBrandGroupList();
				
				
				jsonResult.setSuccess(true);
				if(("Y").equals(isForm)){
					jsonResult.setRedirectUrl("/decms/shop/goods/brandManage.do?menuId="+menuId);
				}
			}

		} catch(Exception e) {
			loggerError(LOGGER, e);
			jsonResult.setSuccess(false);
			jsonResult.setMessage(egovMessageSource.getMessage("fail.common.insert")); //생성이 실패하였습니다.
		}
		
		return jsonResult;
		
	}
	
	/**
	 * 브랜드 상세
	 * @param goodsBrand
	 * @return
	 */
	@RequestMapping(value = "/decms/shop/goods/goodsBrand.json")
	@ResponseBody
	public JsonResult goodsBrand(GoodsBrandVO searchVO) {
		JsonResult jsonResult = new JsonResult();
		try {
			GoodsBrandVO goodsBrand = goodsBrandService.selectGoodsBrand(searchVO);
			
			jsonResult.put("goodsBrand", goodsBrand);
			jsonResult.setSuccess(true);
		} catch(Exception e) {
			loggerError(LOGGER, e);
			jsonResult.setSuccess(false);
			jsonResult.setMessage(egovMessageSource.getMessage("fail.common.select")); //조회에 실패하였습니다.
		}
		
		return jsonResult;
	}
	
	/**
	 * 브랜드 삭제
	 * @param goodsBrand
	 * @return
	 */
	@RequestMapping(value = "/decms/shop/goods/deleteGoodsBrand.json")
	@ResponseBody
	public JsonResult deleteGoodsBrand(GoodsBrandVO goodsBrand) {
		LoginVO user = (LoginVO)EgovUserDetailsHelper.getAuthenticatedUser();
		JsonResult jsonResult = new JsonResult();

		try {
			if(StringUtils.isEmpty(goodsBrand.getBrandId())) {
				this.vaildateMessage(egovMessageSource.getMessage("cms.fail.access"), jsonResult); // 잘못된 접근입니다.
				LOGGER.error("brandId 가 없음.");
			}else {
				if(!EgovUserDetailsHelper.getAuthorities().contains("ROLE_EMPLOYEE")) {
					if(StringUtils.isEmpty(user.getCmpnyId())) {
						jsonResult.setMessage(egovMessageSource.getMessage("cms.fail.not.cmpnyId")); //업체등록이 필요합니다.
						jsonResult.setSuccess(false);
						return jsonResult;
					}

					GoodsBrandVO checkVO = goodsBrandService.selectGoodsBrand(goodsBrand);
					if(!user.getCmpnyId().equals(checkVO.getCmpnyId())) { //내자신의 것이 아니면
						jsonResult.setMessage(egovMessageSource.getMessage("cms.fail.accessDenied")); //접근 권한이 없습니다.
						jsonResult.setSuccess(false);
						return jsonResult;
					}
				}
				
				goodsBrandService.deleteGoodsBrand(goodsBrand);
				
				//브랜드 메뉴 다시 읽기
				goodsBrandService.reloadGoodsBrandGroupList();
				
				jsonResult.setSuccess(true);
				jsonResult.setMessage(egovMessageSource.getMessage("success.common.delete")); //정상적으로 삭제되었습니다.
			}
		
		} catch(Exception e) {
			loggerError(LOGGER, e);
			jsonResult.setSuccess(false);
			jsonResult.setMessage(egovMessageSource.getMessage("fail.common.delete")); //삭제가 실패하였습니다.
		}
		
		return jsonResult;
	}
	
	/**
	 * 브랜드이미지 삭제
	 * @param brandImage
	 * @return
	 */
	@RequestMapping(value = "/decms/shop/goods/deleteGoodsBrandImage.json")
	@ResponseBody
	public JsonResult deleteGoodsBrandImage(GoodsBrandImageVO brandImage) {
		LoginVO user = (LoginVO)EgovUserDetailsHelper.getAuthenticatedUser();
		JsonResult jsonResult = new JsonResult();
		try {
			if(brandImage.getBrandImageNo() == null) {
				this.vaildateMessage(egovMessageSource.getMessage("cms.fail.access"), jsonResult); // 잘못된 접근입니다.
				LOGGER.error("brandImageNo 가 없음.");
			}else {
				if(!EgovUserDetailsHelper.getAuthorities().contains("ROLE_EMPLOYEE")) {
					if(StringUtils.isEmpty(user.getCmpnyId())) {
						jsonResult.setMessage(egovMessageSource.getMessage("cms.fail.not.cmpnyId")); //업체등록이 필요합니다.
						jsonResult.setSuccess(false);
						return jsonResult;
					}
					
					String cmpnyId = goodsBrandImageService.selectCheckGoodsBrandCmpnyId(brandImage);
					if(!cmpnyId.equals(user.getCmpnyId())) { //내자신의 것이 아니면
						jsonResult.setMessage(egovMessageSource.getMessage("cms.fail.accessDenied")); //접근 권한이 없습니다.
						jsonResult.setSuccess(false);
						return jsonResult;
					}
				}
				
				goodsBrandImageService.deleteGoodsBrandImage(brandImage);
				
				jsonResult.setSuccess(true);
				jsonResult.setMessage(egovMessageSource.getMessage("success.common.delete")); //정상적으로 삭제되었습니다.
			}
			
		} catch(Exception e) {
			loggerError(LOGGER, e);
			jsonResult.setSuccess(false);
			jsonResult.setMessage(egovMessageSource.getMessage("fail.common.delete")); //삭제가 실패하였습니다.
		}
		
		return jsonResult;
	}

}
