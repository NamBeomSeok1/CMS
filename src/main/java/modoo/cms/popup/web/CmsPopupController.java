package modoo.cms.popup.web;

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

import modoo.module.common.service.JsonResult;
import modoo.module.common.util.SiteDomainHelper;
import modoo.module.common.web.CommonDefaultController;
import modoo.module.popup.service.PopupService;
import modoo.module.popup.service.PopupVO;
import egovframework.com.cmm.LoginVO;
import egovframework.com.cmm.service.EgovCmmUseService;
import egovframework.com.cmm.util.EgovUserDetailsHelper;
import egovframework.com.utl.fcc.service.EgovDateUtil;
import egovframework.rte.ptl.mvc.tags.ui.pagination.PaginationInfo;

@Controller
public class CmsPopupController extends CommonDefaultController {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(CmsPopupController.class);
	
	@Resource(name = "popupService")
	private PopupService popupService;
	
	/**
	 * 팝업 관리
	 * @param searchVO
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/decms/popup/popupManage.do")
	public String popupManage(@ModelAttribute("searchVO") PopupVO searchVO, Model model) throws Exception {
		//내부 직원 권한이 아닐때 
		if(!EgovUserDetailsHelper.getAuthorities().contains("ROLE_EMPLOYEE")) {
			return "redirect:/decms/index.do";
		}
		
		return "modoo/cms/popup/popupManage";
	}
	
	/**
	 * 팝업 목록
	 * @param searchVO
	 * @return
	 */
	@RequestMapping(value = "/decms/popup/popupList.json")
	@ResponseBody
	public JsonResult popupList(PopupVO searchVO) {
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
				searchVO.setPageUnit(propertiesService.getInt("gridPageUnit"));
				this.setPagination(paginationInfo, searchVO);
				
				List<?> resultList = popupService.selectPopupList(searchVO);
				jsonResult.put("list", resultList);
				
				int totalRecordCount = popupService.selectPopupListCnt(searchVO);
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
	 * 팝업 작성 폼
	 * @param searchVO
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/decms/embed/popup/writePopup.do")
	public String writePopup(@ModelAttribute("searchVO") PopupVO searchVO, Model model) throws Exception {
		//내부 직원 권한이 아닐때 
		if(!EgovUserDetailsHelper.getAuthorities().contains("ROLE_EMPLOYEE")) {
			return "redirect:/decms/index.do";
		}

		PopupVO popup = new PopupVO();
		if(StringUtils.isEmpty(searchVO.getSiteId())) {
			popup.setSiteId(SiteDomainHelper.getSiteId());
		}
		
		popup.setPopupSeCode("LAYER"); //레이어
		popup.setPopupLeft(0);
		popup.setPopupTop(0);
		popup.setPopupWidth(400);
		popup.setPopupHeight(300);
		popup.setPopupBgnDate(EgovDateUtil.getToday());
		popup.setPopupBgnHour("0");
		popup.setPopupBgnMin("0");
		popup.setPopupEndDate(EgovDateUtil.getToday());
		popup.setPopupEndHour("23");
		popup.setPopupEndMin("50");
		popup.setPopupLink("#");
		popup.setActvtyAt("N"); //활성여부
		
		model.addAttribute("popup", popup);
		return "modoo/cms/popup/popupForm";
	}
	
	/**
	 * 팝업 저장
	 * @param popup
	 * @param bindingResult
	 * @return
	 */
	@RequestMapping(value = "/decms/popup/writePopup.json", method = RequestMethod.POST)
	@ResponseBody
	public JsonResult writePopup(@Valid PopupVO popup, BindingResult bindingResult) {
		JsonResult jsonResult = new JsonResult();
		LoginVO user = (LoginVO)EgovUserDetailsHelper.getAuthenticatedUser();
		
		try {
			//내부 직원 권한이 아닐때 
			if(!EgovUserDetailsHelper.getAuthorities().contains("ROLE_EMPLOYEE")) {
				jsonResult.setMessage(egovMessageSource.getMessage("cms.fail.accessDenied")); //접근 권한이 없습니다.
				jsonResult.setSuccess(false);
			}else {
				if(!this.isHasErrorsJson(bindingResult, jsonResult, "<br/>")) {
					
					String bgnde = EgovDateUtil.validChkDate(popup.getPopupBgnDate());
					String endde = EgovDateUtil.validChkDate(popup.getPopupEndDate());
					
					if(EgovDateUtil.getDaysDiff(bgnde, endde) < 0) {
						jsonResult.setSuccess(false);
						jsonResult.setMessage(egovMessageSource.getMessage("cms.fail.endSmallDate")); //종료일이 시작일 보다 작을 수 없습니다.

					}else {
						bgnde = bgnde + popup.getPopupBgnHour() + popup.getPopupBgnMin() + "00";
						popup.setPopupBgnde(bgnde);
						endde = endde + popup.getPopupEndHour() + popup.getPopupEndMin() + "00";
						popup.setPopupEndde(endde);
						
						if(popup.getActvtyAt() == null) popup.setActvtyAt("N");
						
						if(popup.getPopupLeft() == null) {
							popup.setPopupLeft(0);
						}
						
						if(popup.getPopupTop() == null) {
							popup.setPopupTop(0);
						}
					
						popup.setFrstRegisterId(user.getUniqId());
						popupService.insertPopup(popup);
						
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
	 * 팝업 수정 폼
	 * @param searchVO
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/decms/embed/popup/modifyPopup.do")
	public String modifyPopup(@ModelAttribute("searchVO") PopupVO searchVO, Model model) throws Exception {
		//내부 직원 권한이 아닐때 
		if(!EgovUserDetailsHelper.getAuthorities().contains("ROLE_EMPLOYEE")) {
			return "redirect:/decms/index.do";
		}
		
		PopupVO popup = new PopupVO();
		
		popup = popupService.selectPopup(searchVO);
		model.addAttribute("popup", popup);
		
		return "modoo/cms/popup/popupForm";
	}
	
	/**
	 * 팝업 수정
	 * @param popup
	 * @param bindingResult
	 * @return
	 */
	@RequestMapping(value = "/decms/popup/modifyPopup.json")
	@ResponseBody
	public JsonResult modifyPopup(@Valid PopupVO popup, BindingResult bindingResult) {
		JsonResult jsonResult = new JsonResult();
		LoginVO user = (LoginVO)EgovUserDetailsHelper.getAuthenticatedUser();

		try {
			//내부 직원 권한이 아닐때 
			if(!EgovUserDetailsHelper.getAuthorities().contains("ROLE_EMPLOYEE")) {
				jsonResult.setMessage(egovMessageSource.getMessage("cms.fail.accessDenied")); //접근 권한이 없습니다.
				jsonResult.setSuccess(false);
			}else {
				if(!this.isHasErrorsJson(bindingResult, jsonResult, "<br/>")) {
					
					if(StringUtils.isEmpty(popup.getPopupId())) {
						this.vaildateMessage(egovMessageSource.getMessage("cms.fail.access"), jsonResult); // 잘못된 접근입니다.
						LOGGER.error("popupId 가 없음.");
					}else {
						
						String bgnde = EgovDateUtil.validChkDate(popup.getPopupBgnDate());
						String endde = EgovDateUtil.validChkDate(popup.getPopupEndDate());

						if(EgovDateUtil.getDaysDiff(bgnde, endde) < 0) {
							jsonResult.setSuccess(false);
							jsonResult.setMessage(egovMessageSource.getMessage("cms.fail.endSmallDate")); //종료일이 시작일 보다 작을 수 없습니다.

						}else {
							bgnde = bgnde + popup.getPopupBgnHour() + popup.getPopupBgnMin() + "00";
							popup.setPopupBgnde(bgnde);
							endde = endde + popup.getPopupEndHour() + popup.getPopupEndMin() + "00";
							popup.setPopupEndde(endde);
							
							if(popup.getActvtyAt() == null) popup.setActvtyAt("N");
							
							if(popup.getPopupLeft() == null) {
								popup.setPopupLeft(0);
							}
							
							if(popup.getPopupTop() == null) {
								popup.setPopupTop(0);
							}
							
							popup.setLastUpdusrId(user.getUniqId());
							
							popupService.updatePopup(popup);
							jsonResult.setSuccess(true);
						}
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
	 * 팝업 삭제
	 * @param popup
	 * @return
	 */
	@RequestMapping(value = "/decms/popup/deletePopup.json")
	@ResponseBody
	public JsonResult deletePopup(PopupVO popup) {
		JsonResult jsonResult = new JsonResult();
		LoginVO user = (LoginVO)EgovUserDetailsHelper.getAuthenticatedUser();

		try {
			//내부 직원 권한이 아닐때 
			if(!EgovUserDetailsHelper.getAuthorities().contains("ROLE_EMPLOYEE")) {
				jsonResult.setMessage(egovMessageSource.getMessage("cms.fail.accessDenied")); //접근 권한이 없습니다.
				jsonResult.setSuccess(false);
			}else {
				if(StringUtils.isEmpty(popup.getPopupId())) {
					this.vaildateMessage(egovMessageSource.getMessage("cms.fail.access"), jsonResult); // 잘못된 접근입니다.
					LOGGER.error("popupId 가 없음.");

				} else {
					popup.setLastUpdusrId(user.getUniqId());
					popupService.deletePopup(popup);
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
