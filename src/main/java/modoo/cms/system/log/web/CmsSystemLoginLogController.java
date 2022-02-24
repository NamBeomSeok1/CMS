package modoo.cms.system.log.web;

import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import modoo.module.common.service.JsonResult;
import modoo.module.common.web.CommonDefaultController;
import modoo.module.system.log.login.service.LoginLogService;
import modoo.module.system.log.login.service.LoginLogVO;
import egovframework.com.cmm.util.EgovUserDetailsHelper;
import egovframework.com.utl.fcc.service.EgovDateUtil;
import egovframework.rte.ptl.mvc.tags.ui.pagination.PaginationInfo;

@Controller
public class CmsSystemLoginLogController extends CommonDefaultController {

	private static final Logger LOGGER = LoggerFactory.getLogger(CmsSystemLoginLogController.class);
	
	@Resource(name = "loginLogService")
	private LoginLogService loginLogService;

	/**
	 * 로그인 로그
	 * @param searchVO
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/decms/system/log/loginLogManage.do")
	public String loginLogManage(@ModelAttribute("searchVO") LoginLogVO searchVO, Model model) throws Exception {
		//내부 직원 권한이 아닐때 
		if(!EgovUserDetailsHelper.getAuthorities().contains("ROLE_EMPLOYEE")) {
			return "redirect:/decms/index.do";
		}
		
		String searchBgnde = EgovDateUtil.formatDate(EgovDateUtil.getToday(), "-");
        String searchEndde = EgovDateUtil.formatDate(EgovDateUtil.getToday(), "-");
        searchVO.setSearchBgnde(searchBgnde);
        searchVO.setSearchEndde(searchEndde);
        
		return "modoo/cms/system/log/login/loginLogManage";
	}
	
	/**
	 * 로그인 로그 목록
	 * @param searchVO
	 * @return
	 */
	@RequestMapping(value = "/decms/system/log/login/loginLogList.json")
	@ResponseBody
	public JsonResult loginLogList(LoginLogVO searchVO) {
		JsonResult jsonResult = new JsonResult();
		
		try {
			//내부 직원 권한이 아닐때 
			if(!EgovUserDetailsHelper.getAuthorities().contains("ROLE_EMPLOYEE")) {
				jsonResult.setMessage(egovMessageSource.getMessage("cms.fail.accessDenied")); //접근 권한이 없습니다.
				jsonResult.setSuccess(false);
			}else {
				searchVO.setSearchCondition("DAY");
				searchVO.setSearchBgnde(EgovDateUtil.validChkDate(searchVO.getSearchBgnde()));
				searchVO.setSearchEndde(EgovDateUtil.validChkDate(searchVO.getSearchEndde()));
				
				PaginationInfo paginationInfo = new PaginationInfo();
				searchVO.setPageUnit(propertiesService.getInt("gridPageUnit"));
				this.setPagination(paginationInfo, searchVO);
				
				List<?> resultList = loginLogService.selectLoginLogList(searchVO);
				jsonResult.put("list", resultList);
				
				int totalRecordCount = loginLogService.selectLoginLogListCnt(searchVO);
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
}
