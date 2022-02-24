package modoo.cms.banner.web;

import java.util.List;

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

import egovframework.com.cmm.ComDefaultCodeVO;
import egovframework.com.cmm.EgovMessageSource;
import egovframework.com.cmm.LoginVO;
import egovframework.com.cmm.service.EgovCmmUseService;
import egovframework.com.cmm.util.EgovUserDetailsHelper;
import egovframework.com.utl.fcc.service.EgovDateUtil;
import egovframework.rte.ptl.mvc.tags.ui.pagination.PaginationInfo;
import modoo.module.banner.service.BannerService;
import modoo.module.banner.service.BannerVO;
import modoo.module.common.service.JsonResult;
import modoo.module.common.util.SiteDomainHelper;
import modoo.module.common.web.CommonDefaultController;
import modoo.module.shop.goods.info.service.GoodsService;
import modoo.module.shop.goods.info.service.GoodsVO;

@Controller
public class CmsBannerController extends CommonDefaultController {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(CmsBannerController.class);

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
	
	
	/**
	 * 배너 관리
	 * @param searchVO
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/decms/banner/bannerManage.do")
	public String bannerManage(@ModelAttribute("searchVO") BannerVO searchVO, Model model) throws Exception {
		//내부 직원 권한이 아닐때 
		if(!EgovUserDetailsHelper.getAuthorities().contains("ROLE_EMPLOYEE")) {
			return "redirect:/decms/index.do";
		}
		
		//배너구분 코드 목록
		ComDefaultCodeVO codeVO = new ComDefaultCodeVO();
		codeVO.setCodeId("CMS004");
		List<?> bannerSeCodeList = cmmUseService.selectCmmCodeDetail(codeVO);
		model.addAttribute("bannerSeCodeList", bannerSeCodeList);
	
		return "modoo/cms/banner/bannerManage";
	}

	/**
	 * 배너 목록
	 * @param searchVO
	 * @return
	 */
	@RequestMapping(value = "/decms/banner/bannerList.json")
	@ResponseBody
	public JsonResult bannerList(BannerVO searchVO) {
		JsonResult jsonResult = new JsonResult();
		
		try {
			//내부 직원 권한이 아닐때 
			if(!EgovUserDetailsHelper.getAuthorities().contains("ROLE_EMPLOYEE")) {
				jsonResult.setMessage(egovMessageSource.getMessage("cms.fail.accessDenied")); //접근 권한이 없습니다.
				jsonResult.setSuccess(false);
			}else {
				if(StringUtils.isEmpty(searchVO.getSiteId())) {
					searchVO.setSearchSiteId(SiteDomainHelper.getSiteId());
				}
				
				PaginationInfo paginationInfo = new PaginationInfo();
				if(searchVO.getPageUnit() <= 10) {
					searchVO.setPageUnit(propertiesService.getInt("gridPageUnit"));
				}
				this.setPagination(paginationInfo, searchVO);
				
				List<?> resultList = null;
				
				if("todaysPickManage".equals(searchVO.getMenuId())){
					resultList = bannerService.selectTodaysPickList(searchVO);
				}else{
					resultList = bannerService.selectBannerList(searchVO);
				}
				jsonResult.put("list", resultList);
				
				int totalRecordCount = bannerService.selectBannerListCnt(searchVO);
				paginationInfo.setTotalRecordCount(totalRecordCount);
				jsonResult.put("paginationInfo", paginationInfo);
				jsonResult.setSuccess(true);
			}
		} catch(Exception e) {
			loggerError(LOGGER, e);
			jsonResult.setSuccess(false);
			jsonResult.setMessage(egovMessageSource.getMessage("fail.common.select")); //조회에 실패하였습니다.
		}
		
		return jsonResult;
	}
	
	/**
	 * 배너 작성 폼
	 * @param searchVO
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/decms/embed/banner/writeBanner.do")
	public String writeBanner(@ModelAttribute("searchVO") BannerVO searchVO, Model model) throws Exception {
		//내부 직원 권한이 아닐때 
		if(!EgovUserDetailsHelper.getAuthorities().contains("ROLE_EMPLOYEE")) {
			return "redirect:/decms/index.do";
		}
		
		BannerVO banner = new BannerVO();
		if(StringUtils.isEmpty(searchVO.getSiteId())) {
			banner.setSiteId(SiteDomainHelper.getSiteId());
		}
		
		//배너구분 코드 목록
		ComDefaultCodeVO codeVO = new ComDefaultCodeVO();
		codeVO.setCodeId("CMS004");
		List<?> bannerSeCodeList = cmmUseService.selectCmmCodeDetail(codeVO);
		model.addAttribute("bannerSeCodeList", bannerSeCodeList);

		//배너타입코드 목록
		codeVO.setCodeId("CMS032");
		List<?> bannerTyCodeList = cmmUseService.selectCmmCodeDetail(codeVO);
		model.addAttribute("bannerTyCodeList", bannerTyCodeList);
		
		banner.setSortNo(0);
		banner.setBannerBgnDate(EgovDateUtil.getToday());
		banner.setBannerBgnHour("0");
		banner.setBannerBgnMin("0");
		banner.setBannerEndDate(EgovDateUtil.getToday());
		banner.setBannerEndHour("23");
		banner.setBannerEndMin("50");
		banner.setBannerWindowAt("N"); // 2020.10.28 오영석 차장 요청으로 새창여부 unchecked
		banner.setActvtyAt("Y");
		banner.setBcrnClor("#ffffff");
		banner.setDfk("월,화,수,목,금,토,일");
		banner.setFontClor("#303030");
		banner.setBannerTyCode("GOODS"); //배너타입코드
		banner.setBannerLbl("모두's PICK"); //라벨
		banner.setBannerLblClor("#8648b9"); //라벤컬러
		banner.setPrtnrId("PRTNR_0000"); // B2C

		model.addAttribute("banner", banner);

		return "modoo/cms/banner/bannerForm";
	}
	
	/**
	 * 배너 저장
	 * @param banner
	 * @param bindingResult
	 * @return
	 */
	@RequestMapping(value = "/decms/banner/writeBanner.json", method = RequestMethod.POST)
	@ResponseBody
	public JsonResult writeBanner(@Valid BannerVO banner, BindingResult bindingResult) {
		JsonResult jsonResult = new JsonResult();
		LoginVO user = (LoginVO)EgovUserDetailsHelper.getAuthenticatedUser();
		
		try {
			//내부 직원 권한이 아닐때 
			if(!EgovUserDetailsHelper.getAuthorities().contains("ROLE_EMPLOYEE")) {
				jsonResult.setMessage(egovMessageSource.getMessage("cms.fail.accessDenied")); //접근 권한이 없습니다.
				jsonResult.setSuccess(false);
			}else {
				if(!this.isHasErrorsJson(bindingResult, jsonResult, "<br/>")) {
					String bgnde = EgovDateUtil.validChkDate(banner.getBannerBgnDate());
					String endde = EgovDateUtil.validChkDate(banner.getBannerEndDate());
					
					if(EgovDateUtil.getDaysDiff(bgnde, endde) < 0) {
						jsonResult.setSuccess(false);
						jsonResult.setMessage(egovMessageSource.getMessage("cms.fail.endSmallDate")); //종료일이 시작일 보다 작을 수 없습니다.
						
					}else {
						bgnde = bgnde + banner.getBannerBgnHour() + banner.getBannerBgnMin();
						banner.setBannerBgnde(bgnde);
						endde = endde + banner.getBannerEndHour() + banner.getBannerEndMin();
						banner.setBannerEndde(endde);
						
						if(banner.getActvtyAt() == null) banner.setActvtyAt("N");
						
						if(StringUtils.isNotEmpty(banner.getBannerTyCode()) && "BANN003".equals(banner.getBannerSeCode())) { //메인배너(Today's Pick)
							if("EVENT".equals(banner.getBannerTyCode())) { //이벤트
								banner.setGoodsId(null); //상품ID 초기화
							}else if("GOODS".equals(banner.getBannerTyCode())) {
								GoodsVO goods = new GoodsVO();
								goods.setGoodsId(banner.getGoodsId());
								goods = goodsService.selectGoods(goods);
								banner.setPrtnrId(goods.getPrtnrId());
							}
						}else if("BANN004".equals(banner.getBannerSeCode())) { //개인추천
							GoodsVO goods = new GoodsVO();
							goods.setGoodsId(banner.getGoodsId());
							goods = goodsService.selectGoods(goods);
							banner.setPrtnrId(goods.getPrtnrId());
						}else {
							banner.setPrtnrId(null);
							banner.setGoodsId(null);
							banner.setBannerTyCode(null);
						}
						
						banner.setFrstRegisterId(user.getUniqId());
						
						bannerService.insertBanner(banner);
						jsonResult.setSuccess(true);
					}
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
	 * 배너 수정 폼
	 * @param searchVO
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/decms/embed/banner/modifyBanner.do")
	public String modifyBanner(@ModelAttribute("searchVO") BannerVO searchVO, Model model) throws Exception {
		//내부 직원 권한이 아닐때 
		if(!EgovUserDetailsHelper.getAuthorities().contains("ROLE_EMPLOYEE")) {
			return "redirect:/decms/index.do";
		}
		
		//배너구분 코드 목록
		ComDefaultCodeVO codeVO = new ComDefaultCodeVO();
		codeVO.setCodeId("CMS004");
		List<?> bannerSeCodeList = cmmUseService.selectCmmCodeDetail(codeVO);
		model.addAttribute("bannerSeCodeList", bannerSeCodeList);
		
		//배너타입코드 목록
		codeVO.setCodeId("CMS032");
		List<?> bannerTyCodeList = cmmUseService.selectCmmCodeDetail(codeVO);
		model.addAttribute("bannerTyCodeList", bannerTyCodeList);
		
		BannerVO banner = bannerService.selectBanner(searchVO);
		if(StringUtils.isEmpty(banner.getBcrnClor())) {
			banner.setBcrnClor("#ffffff");
		}
		if(StringUtils.isEmpty(banner.getBannerLblClor())) {
			banner.setBannerLblClor("#8648b9"); //라벤컬러
		}
		if(StringUtils.isEmpty(banner.getFontClor())) {
			banner.setFontClor("#303030");
		}
		model.addAttribute("banner", banner);

		return "modoo/cms/banner/bannerForm";
	}
	
	/**
	 * 배너 수정
	 * @param banner
	 * @param bindingResult
	 * @return
	 */
	@RequestMapping(value = "/decms/banner/modifyBanner.json", method = RequestMethod.POST)
	@ResponseBody
	public JsonResult modifyBanner(@Valid BannerVO banner, BindingResult bindingResult) {
		JsonResult jsonResult = new JsonResult();
		LoginVO user = (LoginVO)EgovUserDetailsHelper.getAuthenticatedUser();
		
		try {
			//내부 직원 권한이 아닐때 
			if(!EgovUserDetailsHelper.getAuthorities().contains("ROLE_EMPLOYEE")) {
				jsonResult.setMessage(egovMessageSource.getMessage("cms.fail.accessDenied")); //접근 권한이 없습니다.
				jsonResult.setSuccess(false);
			}else {
				if(!this.isHasErrorsJson(bindingResult, jsonResult, "<br/>")) {
					String bgnde = EgovDateUtil.validChkDate(banner.getBannerBgnDate());
					String endde = EgovDateUtil.validChkDate(banner.getBannerEndDate());
					
					if(EgovDateUtil.getDaysDiff(bgnde, endde) < 0) {
						jsonResult.setSuccess(false);
						jsonResult.setMessage(egovMessageSource.getMessage("cms.fail.endSmallDate")); //종료일이 시작일 보다 작을 수 없습니다.
						
					}else {
						bgnde = bgnde + banner.getBannerBgnHour() + banner.getBannerBgnMin();
						banner.setBannerBgnde(bgnde);
						endde = endde + banner.getBannerEndHour() + banner.getBannerEndMin();
						banner.setBannerEndde(endde);

						if(banner.getActvtyAt() == null) banner.setActvtyAt("N");
						
						if(StringUtils.isNotEmpty(banner.getBannerTyCode()) && "BANN003".equals(banner.getBannerSeCode())) { //메인배너(Today's Pick)
							if("EVENT".equals(banner.getBannerTyCode())) { //이벤트
								banner.setGoodsId(null); //상품ID 초기화
							}else if("GOODS".equals(banner.getBannerTyCode())) {
								GoodsVO goods = new GoodsVO();
								goods.setGoodsId(banner.getGoodsId());
								goods = goodsService.selectGoods(goods);
								banner.setPrtnrId(goods.getPrtnrId());
							}
						}else if("BANN004".equals(banner.getBannerSeCode())) { //개인추천
							GoodsVO goods = new GoodsVO();
							goods.setGoodsId(banner.getGoodsId());
							goods = goodsService.selectGoods(goods);
							banner.setPrtnrId(goods.getPrtnrId());
						}else {
							banner.setPrtnrId(null);
							banner.setGoodsId(null);
							banner.setBannerTyCode(null);
						}

						banner.setLastUpdusrId(user.getUniqId());
						
						bannerService.updateBanner(banner);
						jsonResult.setSuccess(true);
					}
					
				}
			}
		} catch(Exception e) {
			loggerError(LOGGER, e);
			jsonResult.setSuccess(false);
			jsonResult.setMessage(egovMessageSource.getMessage("fail.common.update")); //수정이 실패하였습니다.
		}
		
		return jsonResult;
	}

	/**
	 * 배너 삭제
	 * @param banner
	 * @return
	 */
	@RequestMapping(value = "/decms/banner/deleteBanner.json")
	@ResponseBody
	public JsonResult deleteBanner(BannerVO banner) {
		JsonResult jsonResult = new JsonResult();
		LoginVO user = (LoginVO)EgovUserDetailsHelper.getAuthenticatedUser();
		
		try {
			//내부 직원 권한이 아닐때 
			if(!EgovUserDetailsHelper.getAuthorities().contains("ROLE_EMPLOYEE")) {
				jsonResult.setMessage(egovMessageSource.getMessage("cms.fail.accessDenied")); //접근 권한이 없습니다.
				jsonResult.setSuccess(false);
			}else {
				if(StringUtils.isEmpty(banner.getBannerId())) {
					this.vaildateMessage(egovMessageSource.getMessage("cms.fail.access"), jsonResult); // 잘못된 접근입니다.
					LOGGER.error("bannerId 가 없음.");
				}else {
					banner.setLastUpdusrId(user.getUniqId());
					bannerService.deleteBanner(banner);
					jsonResult.setSuccess(true);
				}
			}
		} catch(Exception e) {
			loggerError(LOGGER, e);
			jsonResult.setSuccess(false);
			jsonResult.setMessage(egovMessageSource.getMessage("fail.common.delete")); //삭제가 실패하였습니다.
		}
		
		return jsonResult;
	}
}
