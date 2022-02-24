package modoo.cms.mber.web;

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
import modoo.module.mber.author.service.MberAuthorService;
import modoo.module.mber.author.service.MberAuthorVO;
import modoo.module.mber.info.service.MberService;
import modoo.module.mber.info.service.MberVO;
import egovframework.com.cmm.ComDefaultCodeVO;
import egovframework.com.cmm.EgovMessageSource;
import egovframework.com.cmm.service.EgovCmmUseService;
import egovframework.com.cmm.util.EgovUserDetailsHelper;
import egovframework.rte.ptl.mvc.tags.ui.pagination.PaginationInfo;

@Controller
public class CmsMberController extends CommonDefaultController {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(CmsMberController.class);
	
	private static final String DEFAULT_GROUP_ID = "GROUP_00000000000000";
	
	/** EgovMessageSource */
	@Resource(name = "egovMessageSource")
	protected EgovMessageSource egovMessageSource;
	
	@Resource(name = "mberService")
	private MberService mberService;
	
	/** EgovCmmUseService */
	@Resource(name = "EgovCmmUseService")
	private EgovCmmUseService cmmUseService;
	
	@Resource(name = "mberAuthorService")
	private MberAuthorService mberAuthorService;
	
	
	/**
	 * 사용자 관리
	 * @param searchVO
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/decms/mber/mberManage.do", method = RequestMethod.GET)
	public String mberManage(@ModelAttribute("searchVO") MberVO searchVO, Model model) throws Exception {
		//내부 직원 권한이 아닐때 
		if(!EgovUserDetailsHelper.getAuthorities().contains("ROLE_EMPLOYEE")) {
			return "redirect:/decms/index.do";
		}
		return "modoo/cms/mber/mberManage";
	}
	
	
	/**
	 * 사용자 목록
	 * @param searchVO
	 * @return
	 */
	@RequestMapping(value = "/decms/mber/mberList.json")
	@ResponseBody
	public JsonResult mberList(MberVO searchVO) {
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
				
				List<?> resultList = mberService.selectMberList(searchVO);
				jsonResult.put("list", resultList);
				
				int totalRecordCount = mberService.selectMberListCnt(searchVO);
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
	 * 사용자 등록 폼
	 * @param searchVO
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/decms/embed/mber/writeMber.do")
	public String writeMber(@ModelAttribute("searchVO") MberVO searchVO, Model model) throws Exception {
		//내부 직원 권한이 아닐때 
		if(!EgovUserDetailsHelper.getAuthorities().contains("ROLE_EMPLOYEE")) {
			return "redirect:/decms/index.do";
		}
		
		// 회원 상태 코드 목록
		ComDefaultCodeVO codeVO = new ComDefaultCodeVO();
		codeVO.setCodeId("CMS002");
		List<?> mberSttusList = cmmUseService.selectCmmCodeDetail(codeVO);
		model.addAttribute("mberSttusList", mberSttusList);
		
		// 회원 유형 코드 목록
		codeVO.setCodeId("CMS001");
		List<?> mberTyCodeList  = cmmUseService.selectCmmCodeDetail(codeVO);
		model.addAttribute("mberTyCodeList", mberTyCodeList);
		
		// 사용 권한 목록
		MberAuthorVO ma = new MberAuthorVO();
		List<MberAuthorVO> authorList = mberAuthorService.selectUsedAuthorList(ma);
		model.addAttribute("authorList", authorList);
		
		MberVO mber = new MberVO();
		mber.setSiteId(SiteDomainHelper.getSiteId());
		mber.setMberSttus("P");

		if(StringUtils.isEmpty(searchVO.getSearchUserType())) {
			searchVO.setSearchUserType("USER");
		}

		if(searchVO.getSearchUserType().equals("USER")) {
			mber.setAuthorCode("ROLE_USER");
		}else if(searchVO.getSearchUserType().equals("SHOP")) {
			mber.setAuthorCode("ROLE_SHOP");
		}else if(searchVO.getSearchUserType().equals("ADMIN")) {
			mber.setAuthorCode("ROLE_ADMIN");
		}
		model.addAttribute("mber", mber);
		
		return "modoo/cms/mber/mberForm";
	}
	
	/**
	 * 사용자 저장
	 * @param mber
	 * @param bindingResult
	 * @return
	 */
	@RequestMapping(value = "/decms/mber/writeMber.json", method = RequestMethod.POST)
	@ResponseBody 
	public JsonResult writeMber(@Valid MberVO mber, BindingResult bindingResult, String checkDuplMberId) {
		JsonResult jsonResult = new JsonResult();
		
		try {
			//내부 직원 권한이 아닐때 
			if(!EgovUserDetailsHelper.getAuthorities().contains("ROLE_EMPLOYEE")) {
				jsonResult.setMessage(egovMessageSource.getMessage("cms.fail.accessDenied")); //접근 권한이 없습니다.
				jsonResult.setSuccess(false);
			}else {
				if(!this.isHasErrorsJson(bindingResult, jsonResult, "<br/>")) {
					int cnt = mberService.selectCheckDuplMberIdCnt(mber);
					if(cnt > 0) {
						this.vaildateMessage(egovMessageSource.getMessage("mberVO.fail.useMberId"), jsonResult); //이미 등록된 ID 입니다.
					}else if(StringUtils.isEmpty(checkDuplMberId) || !checkDuplMberId.equals("Y")) {
						this.vaildateMessage(egovMessageSource.getMessage("mberVO.fail.ckeckDuplMberId"), jsonResult); //ID 중복 확인이 필요합니다.
					}else if(!mber.getPassword().equals(mber.getRepassword())) {
						this.vaildateMessage(egovMessageSource.getMessage("mberVO.fail.diffPassword"), jsonResult); //비밀번호와 비밀번호확인이 다릅니다.
					}else {

						if(mber.getAuthorCode().contains("_USER")) {
							mber.setMberTyCode("USR01");
							mber.setGroupId(DEFAULT_GROUP_ID); //일반그룹
						}else if(mber.getAuthorCode().contains("_SHOP")) {
							mber.setMberTyCode("USR02");
							mber.setGroupId(DEFAULT_GROUP_ID); //일반그룹
						}else if(mber.getAuthorCode().contains("_EMPLOYEE")) {
							mber.setMberTyCode("USR80");
						}else if(mber.getAuthorCode().contains("_ADMIN")) {
							mber.setMberTyCode("USR99");
						}
						mberService.insertMber(mber);
						
						MberVO newMber = mberService.selectMber(mber);
						jsonResult.put("mber", newMber);

						jsonResult.setSuccess(true);
						jsonResult.setMessage(egovMessageSource.getMessage("success.common.insert")); //정상적으로 등록되었습니다.
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
	 * 회원 수정 폼
	 * @param searchVO
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/decms/embed/mber/modifyMber.do")
	public String modifyMber(@ModelAttribute("searchVO") MberVO searchVO, Model model) throws Exception {
		//내부 직원 권한이 아닐때 
		if(!EgovUserDetailsHelper.getAuthorities().contains("ROLE_EMPLOYEE")) {
			return "redirect:/decms/index.do";
		}
		
		MberVO mber = mberService.selectMber(searchVO);
		model.addAttribute("mber", mber);
		
		// 회원 상태 코드 목록
		ComDefaultCodeVO codeVO = new ComDefaultCodeVO();
		codeVO.setCodeId("CMS002");
		List<?> mberSttusList = cmmUseService.selectCmmCodeDetail(codeVO);
		model.addAttribute("mberSttusList", mberSttusList);

		// 회원 유형 코드 목록
		codeVO.setCodeId("CMS001");
		List<?> mberTyCodeList  = cmmUseService.selectCmmCodeDetail(codeVO);
		model.addAttribute("mberTyCodeList", mberTyCodeList);
		
		// 사용 권한 목록
		MberAuthorVO ma = new MberAuthorVO();
		List<MberAuthorVO> authorList = mberAuthorService.selectUsedAuthorList(ma);
		model.addAttribute("authorList", authorList);
		
		return "modoo/cms/mber/mberForm";
	}
	
	/**
	 * 회원 수정
	 * @param mber
	 * @param bindingResult
	 * @return
	 */
	@RequestMapping(value = "/decms/mber/modifyMber.json", method = RequestMethod.POST)
	@ResponseBody 
	public JsonResult modifyMber(@Valid MberVO mber, BindingResult bindingResult) {
		JsonResult jsonResult = new JsonResult();
		
		try {
			//내부 직원 권한이 아닐때 
			if(!EgovUserDetailsHelper.getAuthorities().contains("ROLE_EMPLOYEE")) {
				jsonResult.setMessage(egovMessageSource.getMessage("cms.fail.accessDenied")); //접근 권한이 없습니다.
				jsonResult.setSuccess(false);
			}else {
				if(!this.isHasErrorsJson(bindingResult, jsonResult, "<br/>")) {
					
					if(mber.getAuthorCode().contains("_USER")) {
						mber.setMberTyCode("USR01");
						mber.setGroupId(DEFAULT_GROUP_ID); //일반그룹
					}else if(mber.getAuthorCode().contains("_SHOP")) {
						mber.setMberTyCode("USR02");
						mber.setGroupId(DEFAULT_GROUP_ID); //일반그룹
					}else if(mber.getAuthorCode().contains("_EMPLOYEE")) {
						mber.setMberTyCode("USR80");
					}else if(mber.getAuthorCode().contains("_ADMIN")) {
						mber.setMberTyCode("USR99");
					}

					mberService.updateMber(mber);
					
					jsonResult.setSuccess(true);
					jsonResult.setMessage(egovMessageSource.getMessage("success.common.update")); //정상적으로 수정되었습니다.
				}
			}
		}catch (Exception e) {
			loggerError(LOGGER, e);
			jsonResult.setSuccess(false);
			jsonResult.setMessage(egovMessageSource.getMessage("fail.common.update")); //수정이 실패하였습니다.
			
		}
		
		return jsonResult;

	}
	
	/**
	 * 회원 ID 중복 확인
	 * @param mber
	 * @return
	 */
	@RequestMapping(value = "/decms/mber/checkDuplMberId.json")
	@ResponseBody
	public JsonResult checkDuplMberId(MberVO mber) {
		JsonResult jsonResult = new JsonResult();
		
		try {
			//내부 직원 권한이 아닐때 
			if(!EgovUserDetailsHelper.getAuthorities().contains("ROLE_EMPLOYEE")) {
				jsonResult.setMessage(egovMessageSource.getMessage("cms.fail.accessDenied")); //접근 권한이 없습니다.
				jsonResult.setSuccess(false);
			}else {
				if(StringUtils.isEmpty(mber.getMberId())) {
					this.vaildateMessage(egovMessageSource.getMessage("NotEmpty.mberVO.mberId"), jsonResult); //ID를 입력하세요!
				}else {
					int cnt = mberService.selectCheckDuplMberIdCnt(mber);
					
					if(cnt > 0) {
						this.vaildateMessage(egovMessageSource.getMessage("mberVO.fail.useMberId"), jsonResult); //이미 등록된 ID 입니다.
					}else {
						jsonResult.setSuccess(true);
						jsonResult.setMessage(egovMessageSource.getMessage("mberVO.success.useMberId")); //사용가능한 ID 입니다.
					}
				}
			}
		} catch(Exception e) {
			loggerError(LOGGER, e);
			jsonResult.setSuccess(false);
			jsonResult.setMessage(egovMessageSource.getMessage("fail.request.msg")); //요청처리를 실패하였습니다.
		}
		
		return jsonResult;
	}
	
	
	/**
	 * 비밀번호 수정
	 * @param mber
	 * @return
	 */
	@RequestMapping(value = "/decms/mber/changePassword.json")
	@ResponseBody
	public JsonResult changePassword(MberVO mber) {
		JsonResult jsonResult = new JsonResult();
		try {
			//내부 직원 권한이 아닐때 
			if(!EgovUserDetailsHelper.getAuthorities().contains("ROLE_EMPLOYEE")) {
				jsonResult.setMessage(egovMessageSource.getMessage("cms.fail.accessDenied")); //접근 권한이 없습니다.
				jsonResult.setSuccess(false);
			}else {
				if(StringUtils.isEmpty(mber.getEsntlId()) || StringUtils.isEmpty(mber.getMberId())) {
					this.vaildateMessage(egovMessageSource.getMessage("cms.fail.access"), jsonResult); // 잘못된 접근입니다.
					LOGGER.error("EsntlId or MberId 가 없음.");
				}else if(StringUtils.isEmpty(mber.getPassword())) {
					this.vaildateMessage(egovMessageSource.getMessage("NotEmpty.mberVO.password"), jsonResult); //비밀번호를 입력하세요!
				}else if(StringUtils.isEmpty(mber.getRepassword())) {
					this.vaildateMessage(egovMessageSource.getMessage("NotEmpty.mberVO.repassword"), jsonResult); //비밀번호 확인을 입력하세요!
				}else if(mber.getPassword().length() < 8 || mber.getPassword().length() > 20) {
					this.vaildateMessage(egovMessageSource.getMessage("Size.mberVO.password"), jsonResult); //비밀번호는 8자 이상 20자 이하로 입력하세요!
				}else if(mber.getRepassword().length() < 8 || mber.getRepassword().length() > 20) {
					this.vaildateMessage(egovMessageSource.getMessage("Size.mberVO.repassword"), jsonResult); //비밀번호 확인은 8자 이상 20자 이하로 입력하세요!
				}else if(!mber.getPassword().equals(mber.getRepassword())) {
					this.vaildateMessage(egovMessageSource.getMessage("fail.user.passwordUpdate2"), jsonResult); //비밀번호와 비밀번호 확인이 일치하지 않습니다.
				}else {
					mberService.updatePassword(mber);
					jsonResult.setSuccess(true);
					jsonResult.setMessage(egovMessageSource.getMessage("success.common.update")); //정상적으로 수정되었습니다.
				}
			}
		} catch(Exception e) {
			loggerError(LOGGER, e);
			jsonResult.setSuccess(false);
			jsonResult.setMessage(egovMessageSource.getMessage("fail.request.msg")); //요청처리를 실패하였습니다.
			
		}
		
		return jsonResult;
	}
	
	/**
	 * 잠김 해제
	 * @param mber
	 * @return
	 */
	@RequestMapping(value = "/decms/mber/unlockMber.json")
	@ResponseBody
	public JsonResult unlockMber(MberVO mber) {
		JsonResult jsonResult = new JsonResult();
		
		try {
			//내부 직원 권한이 아닐때 
			if(!EgovUserDetailsHelper.getAuthorities().contains("ROLE_EMPLOYEE")) {
				jsonResult.setMessage(egovMessageSource.getMessage("cms.fail.accessDenied")); //접근 권한이 없습니다.
				jsonResult.setSuccess(false);
			}else {
				if(!StringUtils.isNotEmpty(mber.getEsntlId())) {
					this.vaildateMessage(egovMessageSource.getMessage("cms.fail.access"), jsonResult); // 잘못된 접근입니다.
					LOGGER.error("EsntlId 가 없음.");
				}else {
					mberService.updateLockIncorrect(mber);
					jsonResult.setSuccess(true);
					jsonResult.setMessage(egovMessageSource.getMessage("success.common.update")); //정상적으로 수정되었습니다.
				}
			}
		} catch(Exception e) {
			loggerError(LOGGER, e);
			jsonResult.setSuccess(false);
			jsonResult.setMessage(egovMessageSource.getMessage("fail.request.msg")); //요청처리를 실패하였습니다.
		}
		
		return jsonResult;
	}
	
	/**
	 * 회원 삭제
	 * @param mber
	 * @return
	 */
	@RequestMapping(value = "/decms/mber/deleteMber.json")
	@ResponseBody
	public JsonResult deleteMber(MberVO mber) {
		JsonResult jsonResult = new JsonResult();
		
		try {
			//내부 직원 권한이 아닐때 
			if(!EgovUserDetailsHelper.getAuthorities().contains("ROLE_EMPLOYEE")) {
				jsonResult.setMessage(egovMessageSource.getMessage("cms.fail.accessDenied")); //접근 권한이 없습니다.
				jsonResult.setSuccess(false);
			}else {
				if(StringUtils.isEmpty(mber.getEsntlId())) {
					this.vaildateMessage(egovMessageSource.getMessage("cms.fail.access"), jsonResult); // 잘못된 접근입니다.
					LOGGER.error("EsntlId 가 없음.");
				}else {
					//mberService.deleteMber(mber);
					mber.setMberSttus("D");
					mberService.updateMberSttus(mber);

					jsonResult.setSuccess(true);
					jsonResult.setMessage(egovMessageSource.getMessage("success.common.delete")); //정상적으로 삭제되었습니다.
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
