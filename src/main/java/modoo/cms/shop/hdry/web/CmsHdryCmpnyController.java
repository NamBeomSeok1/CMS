package modoo.cms.shop.hdry.web;

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

import egovframework.com.cmm.EgovMessageSource;
import egovframework.com.cmm.util.EgovUserDetailsHelper;
import egovframework.rte.ptl.mvc.tags.ui.pagination.PaginationInfo;
import modoo.module.common.service.JsonResult;
import modoo.module.common.util.SiteDomainHelper;
import modoo.module.common.web.CommonDefaultController;
import modoo.module.shop.hdry.service.HdryCmpnyService;
import modoo.module.shop.hdry.service.HdryCmpnyVO;

@Controller
public class CmsHdryCmpnyController extends CommonDefaultController {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(CmsHdryCmpnyController.class);

	/** EgovMessageSource */
	@Resource(name = "egovMessageSource")
	protected EgovMessageSource egovMessageSource;
	
	@Resource(name = "hdryCmpnyService")
	private HdryCmpnyService hdryCmpnyService;
	
	/**
	 * 택배사관리
	 * @param searchVO
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/decms/shop/hdry/hdryCmpnyManage.do")
	public String hdryCmpnyManage(@ModelAttribute("searchVO") HdryCmpnyVO searchVO, Model model) throws Exception {
		//내부 직원 권한이 아닐때 
		if(!EgovUserDetailsHelper.getAuthorities().contains("ROLE_EMPLOYEE")) {
			return "redirect:/decms/index.do";
		}
		
		return "modoo/cms/shop/hdry/hdryCmpnyManage";
	}
	
	/**
	 * 택배사목록
	 * @param searchVO
	 * @return
	 */
	@RequestMapping(value = "/decms/shop/hdry/hdryCmpnyList.json")
	@ResponseBody
	public JsonResult hdryCmpnyList(HdryCmpnyVO searchVO) {
		JsonResult jsonResult = new JsonResult();
		
		try {
			//내부 직원 권한이 아닐때 
			if(!EgovUserDetailsHelper.getAuthorities().contains("ROLE_EMPLOYEE")) {
				jsonResult.setMessage(egovMessageSource.getMessage("cms.fail.accessDenied")); //접근 권한이 없습니다.
				jsonResult.setSuccess(false);
			}else {
				PaginationInfo paginationInfo = new PaginationInfo();
				searchVO.setPageUnit(propertiesService.getInt("gridPageUnit"));
				this.setPagination(paginationInfo, searchVO);
				
				List<?> resultList = hdryCmpnyService.selectHdryCmpnyList(searchVO);
				jsonResult.put("list", resultList);
				
				int totalRecordCount = hdryCmpnyService.selectHdryCmpnyListCnt(searchVO);
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
	 * 택배사 등록 폼
	 * @param searchVO
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/decms/embed/shop/hdry/writeHdryCmpny.do")
	public String writeHdryCmpny(@ModelAttribute("searchVO") HdryCmpnyVO searchVO, Model model) throws Exception {
		//내부 직원 권한이 아닐때 
		if(!EgovUserDetailsHelper.getAuthorities().contains("ROLE_EMPLOYEE")) {
			return "redirect:/decms/index.do";
		}

		model.addAttribute("hdryCmpny", new HdryCmpnyVO());
		return "modoo/cms/shop/hdry/hdryCmpnyForm";
	}
	
	/**
	 * 택배사 저장
	 * @param hdryCmpny
	 * @param bindingResult
	 * @return
	 */
	@RequestMapping(value = "/decms/shop/hdry/writeHdryCmpny.json", method = RequestMethod.POST)
	@ResponseBody
	public JsonResult writeHdryCmpny(@Valid HdryCmpnyVO hdryCmpny, BindingResult bindingResult) {
		JsonResult jsonResult = new JsonResult();
		
		try {
			//내부 직원 권한이 아닐때 
			if(!EgovUserDetailsHelper.getAuthorities().contains("ROLE_EMPLOYEE")) {
				jsonResult.setMessage(egovMessageSource.getMessage("cms.fail.accessDenied")); //접근 권한이 없습니다.
				jsonResult.setSuccess(false);
			}else {
				if(!this.isHasErrorsJson(bindingResult, jsonResult, "<br/>")) {
					hdryCmpnyService.insertHdryCmpny(hdryCmpny);
					jsonResult.setSuccess(true);
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
	 * 택배사 수정 폼
	 * @param searchVO
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/decms/embed/shop/hdry/modifyHdryCmpny.do")
	public String modifyHdryCmpny(@ModelAttribute("searchVO") HdryCmpnyVO searchVO, Model model) throws Exception {
		//내부 직원 권한이 아닐때 
		if(!EgovUserDetailsHelper.getAuthorities().contains("ROLE_EMPLOYEE")) {
			return "redirect:/decms/index.do";
		}

		HdryCmpnyVO hdryCmpny = hdryCmpnyService.selectHdryCmpny(searchVO);
		model.addAttribute("hdryCmpny", hdryCmpny);
		return "modoo/cms/shop/hdry/hdryCmpnyForm";
	}

	/**
	 * 택배사 수정
	 * @param hdryCmpny
	 * @param bindingResult
	 * @return
	 */
	@RequestMapping(value = "/decms/shop/hdry/modifyHdryCmpny.json", method = RequestMethod.POST)
	@ResponseBody
	public JsonResult modifyHdryCmpny(@Valid HdryCmpnyVO hdryCmpny, BindingResult bindingResult) {
		JsonResult jsonResult = new JsonResult();
		
		try {
			//내부 직원 권한이 아닐때 
			if(!EgovUserDetailsHelper.getAuthorities().contains("ROLE_EMPLOYEE")) {
				jsonResult.setMessage(egovMessageSource.getMessage("cms.fail.accessDenied")); //접근 권한이 없습니다.
				jsonResult.setSuccess(false);
			}else {
				if(!this.isHasErrorsJson(bindingResult, jsonResult, "<br/>")) {
					if(StringUtils.isEmpty(hdryCmpny.getHdryId())) {
						this.vaildateMessage(egovMessageSource.getMessage("cms.fail.access"), jsonResult); // 잘못된 접근입니다.
						LOGGER.error("hdryId 가 없음.");
					}else {
						hdryCmpnyService.updateHdryCmpny(hdryCmpny);
						jsonResult.setSuccess(true);
					}
				}
			}
		} catch(Exception e) {
			loggerError(LOGGER, e);
			jsonResult.setSuccess(false);
			jsonResult.setMessage(egovMessageSource.getMessage("fail.common.update")); //수정이 실패하였습니다
		}
		
		return jsonResult;
	}
	
	/**
	 * 택배사 삭제
	 * @param hdryCmpny
	 * @return
	 */
	@RequestMapping(value = "/decms/shop/hdry/deleteHdryCmpny.json", method = RequestMethod.POST)
	@ResponseBody
	public JsonResult deleteHdryCmpny(HdryCmpnyVO hdryCmpny) {
		JsonResult jsonResult = new JsonResult();
		try {
			//내부 직원 권한이 아닐때 
			if(!EgovUserDetailsHelper.getAuthorities().contains("ROLE_EMPLOYEE")) {
				jsonResult.setMessage(egovMessageSource.getMessage("cms.fail.accessDenied")); //접근 권한이 없습니다.
				jsonResult.setSuccess(false);
			}else {
				if(StringUtils.isEmpty(hdryCmpny.getHdryId())) {
					this.vaildateMessage(egovMessageSource.getMessage("cms.fail.access"), jsonResult); // 잘못된 접근입니다.
					LOGGER.error("hdryId 가 없음.");
				}else if("HDRY_99999".equals(hdryCmpny.getHdryId())) {
					this.vaildateMessage(egovMessageSource.getMessage("hdryCmpny.fail.neverDelete"), jsonResult); // 삭제가 불가능하며 기본값입니다.
					LOGGER.error("HDRY_99999는 기본값으로 삭제가 불가능 합니다.");
				}else {
					hdryCmpnyService.deleteHdryCmpny(hdryCmpny);
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
	
	/**
	 * 업체별 택배사 목록 조회
	 * @param searchVO
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/decms/shop/hdry/goodsCmpnyHdryList.json", method = RequestMethod.GET)
	public JsonResult goodsCmpnyHdryList(HdryCmpnyVO searchVO) {
		JsonResult jsonResult = new JsonResult();
		
		List<HdryCmpnyVO> hdryList = hdryCmpnyService.selectGoodsHdryList(searchVO);
		jsonResult.put("list", hdryList);
		jsonResult.setSuccess(true);
		
		return jsonResult;
	}
}
