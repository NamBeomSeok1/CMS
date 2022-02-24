package modoo.front.shop.user.dlvy.web;

import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import egovframework.com.cmm.LoginVO;
import egovframework.com.cmm.util.EgovUserDetailsHelper;
import egovframework.rte.ptl.mvc.tags.ui.pagination.PaginationInfo;
import modoo.module.common.service.JsonResult;
import modoo.module.common.web.CommonDefaultController;
import modoo.module.shop.user.service.DlvyAdresService;
import modoo.module.shop.user.service.DlvyAdresVO;

@Controller("UserDlvyAdresController")
public class UserDlvyAdresController extends CommonDefaultController {
	
	@Resource(name="dlvyAdresService")
	DlvyAdresService dlvyAdresService;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(UserDlvyAdresController.class);
	
	/**
	 * 배송지목록 : json
	 * @param searchVO
	 * @return
	 */
	@RequestMapping(value = "/shop/goods/user/dlvyAdresList.json", method=RequestMethod.GET)
	@ResponseBody
	public HashMap<String, Object> dlvyAdresList(@ModelAttribute("searchVO") DlvyAdresVO searchVO) {
		HashMap<String, Object> jsonResult = new HashMap<String, Object>();
		LoginVO loginVO = (LoginVO) EgovUserDetailsHelper.getAuthenticatedUser();
		
		if (loginVO != null) {
			searchVO.setDlvyUserId(loginVO.getId());
		}
		
		try {
			PaginationInfo paginationInfo = new PaginationInfo(); // 페이징 처리

			searchVO.setPageUnit(propertiesService.getInt("pageUnit")); // src/main/resources/egovframework/spring/com/context-properties.xml
			this.setPagination(paginationInfo, searchVO);
			
			int totalRecordCount = dlvyAdresService.selectDlvyAdresListCnt(searchVO); // 목록 카운트
			paginationInfo.setTotalRecordCount(totalRecordCount);
			
			List<?> addressList = dlvyAdresService.selectDlvyAdresList(searchVO);
			jsonResult.put("paginationInfo", paginationInfo);
			jsonResult.put("resultList", addressList);
			
		}catch(Exception e) {
			
		}
		
		return jsonResult;
		
	}
	
	/**
	 * 배송지상세 : json
	 * @param searchVO
	 * @return
	 */
	@RequestMapping(value = "/shop/goods/user/dlvyAdresDetail.json", method=RequestMethod.GET)
	@ResponseBody
	public JsonResult dlvyAdresDetail(@ModelAttribute("searchVO") DlvyAdresVO searchVO, @RequestParam("adresNo") java.math.BigDecimal adresNo) {
		
		JsonResult jsonResult = new JsonResult();
		LoginVO loginVO = (LoginVO) EgovUserDetailsHelper.getAuthenticatedUser();
		
		if (loginVO != null) {
			searchVO.setDlvyUserId(loginVO.getId());
			searchVO.setDadresNo(adresNo);
		}
		try {
			
			DlvyAdresVO dlvyInfo = dlvyAdresService.selectDlvyAdres(searchVO);
			jsonResult.put("dlvyInfo", dlvyInfo);
			
		}catch(Exception e) {
			loggerError(LOGGER, e);
		}
		
		return jsonResult;
		
	}
	
	/**
	 * 배송지 등록
	 * @param searchVO
	 * @param bindingResult
	 * @return
	 * @throws Exception
	 */
	
	@RequestMapping(value = "/shop/goods/user/registAddress.do", method=RequestMethod.POST)
	@ResponseBody
	public JsonResult registAddress(DlvyAdresVO searchVO, BindingResult bindingResult) throws Exception {
		JsonResult jsonResult = new JsonResult();
		LoginVO loginVO = (LoginVO) EgovUserDetailsHelper.getAuthenticatedUser();
		
		if (loginVO != null) {
			searchVO.setDlvyUserId(loginVO.getId());
			searchVO.setFrstRegisterId(loginVO.getUniqId());
		}
		
		try {
			if(!this.isHasErrorsJson(bindingResult, jsonResult,"<br/>")) {
				
				if(StringUtils.isEmpty(searchVO.getBassDlvyAt())) {
					searchVO.setBassDlvyAt("N");
				}
				
				dlvyAdresService.insertDlvyAdres(searchVO);
				dlvyAdresService.updateBassDlvyAdres(searchVO);
				jsonResult.setSuccess(true);
				jsonResult.setMessage(egovMessageSource.getMessage("success.common.insert"));
			}
		} catch(Exception e) {
			loggerError(LOGGER, e);
			jsonResult.setSuccess(false);
			jsonResult.setMessage(egovMessageSource.getMessage("fail.common.insert"));
		}
	
		return jsonResult;
	}
	
	/**
	 * 배송지 삭제
	 * @param searchVO
	 * @param bindingResult
	 * @return
	 * @throws Exception
	 */
	
	@RequestMapping(value = "/shop/goods/user/deleteAddress.do", method=RequestMethod.POST)
	@ResponseBody
	public JsonResult deleteAddress(DlvyAdresVO searchVO, BindingResult bindingResult) throws Exception {
		JsonResult jsonResult = new JsonResult();
		
		try {
			if(!this.isHasErrorsJson(bindingResult, jsonResult,"<br/>")) {
				dlvyAdresService.deleteDlvyAdres(searchVO);
				jsonResult.setSuccess(true);
				jsonResult.setMessage(egovMessageSource.getMessage("success.common.delete"));
			}
		} catch(Exception e) {
			loggerError(LOGGER, e);
			jsonResult.setSuccess(false);
			jsonResult.setMessage(egovMessageSource.getMessage("fail.common.delete"));
		}
	
		return jsonResult;
	}
	
	/**
	 * 기본 배송지 수정
	 * @param searchVO
	 * @param bindingResult
	 * @return
	 * @throws Exception
	 */
	
	@RequestMapping(value = "/shop/goods/user/modifyBassAddress.do", method=RequestMethod.POST)
	@ResponseBody
	public JsonResult modifyBassAddress(DlvyAdresVO searchVO, BindingResult bindingResult) throws Exception {
		JsonResult jsonResult = new JsonResult();
		LoginVO loginVO = (LoginVO)EgovUserDetailsHelper.getAuthenticatedUser();
		
		try {
			if(!this.isHasErrorsJson(bindingResult, jsonResult,"<br/>")) {
				dlvyAdresService.updateBassDlvyAdres(searchVO);
				jsonResult.setSuccess(true);
				jsonResult.setMessage(egovMessageSource.getMessage("success.common.update"));
				
				searchVO.setDlvyUserId(loginVO.getUniqId());
				DlvyAdresVO dlvyAdres = dlvyAdresService.selectBassDlvyAdres(searchVO);
				jsonResult.put("bassDlvyAdre", dlvyAdres);
			}
		} catch(Exception e) {
			loggerError(LOGGER, e);
			jsonResult.setSuccess(false);
			jsonResult.setMessage(egovMessageSource.getMessage("fail.common.update"));
		}

		return jsonResult;
	}

}
