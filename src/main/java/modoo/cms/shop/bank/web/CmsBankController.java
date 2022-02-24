package modoo.cms.shop.bank.web;

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
import modoo.module.common.web.CommonDefaultController;
import modoo.module.shop.bank.service.BankService;
import modoo.module.shop.bank.service.BankVO;

@Controller
public class CmsBankController extends CommonDefaultController {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(CmsBankController.class);
	
	/** EgovMessageSource */
	@Resource(name = "egovMessageSource")
	protected EgovMessageSource egovMessageSource;
	
	@Resource(name = "bankService")
	private BankService bankService;
	
	
	/**
	 * 은행 관리
	 * @param searchVO
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/decms/shop/bank/bankManage.do")
	public String bankManage(@ModelAttribute("searchVO") BankVO searchVO, Model model) throws Exception {
		//내부 직원 권한이 아닐때 
		if(!EgovUserDetailsHelper.getAuthorities().contains("ROLE_EMPLOYEE")) {
			return "redirect:/decms/index.do";
		}
		
		return "modoo/cms/shop/bank/bankManage";
	}
	
	/**
	 * 은행 목록
	 * @param searchVO
	 * @return
	 */
	@RequestMapping(value = "/decms/shop/bank/bankList.json")
	@ResponseBody
	public JsonResult bankList(BankVO searchVO) {
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
				
				List<BankVO> resultList = bankService.selectBankList(searchVO);
				jsonResult.put("list", resultList);
				
				int totalRecordCount = bankService.selectBankListCnt(searchVO);
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
	 * 은행 등록 폼
	 * @param searchVO
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/decms/embed/shop/bank/writeBank.do")
	public String writeBank(@ModelAttribute("searchVO") BankVO searchVO, Model model) throws Exception {
		//내부 직원 권한이 아닐때 
		if(!EgovUserDetailsHelper.getAuthorities().contains("ROLE_EMPLOYEE")) {
			return "redirect:/decms/index.do";
		}	
		
		model.addAttribute("bank", new BankVO());
		return "modoo/cms/shop/bank/bankForm";
	}
	
	/**
	 * 은행 저장
	 * @param bank
	 * @param bindingResult
	 * @return
	 */
	@RequestMapping(value = "/decms/shop/bank/writeBank.json", method = RequestMethod.POST)
	@ResponseBody
	public JsonResult writeBank(@Valid BankVO bank, BindingResult bindingResult) {
		JsonResult jsonResult = new JsonResult();
		
		try {
			//내부 직원 권한이 아닐때 
			if(!EgovUserDetailsHelper.getAuthorities().contains("ROLE_EMPLOYEE")) {
				jsonResult.setMessage(egovMessageSource.getMessage("cms.fail.accessDenied")); //접근 권한이 없습니다.
				jsonResult.setSuccess(false);
			}else {
				if(!this.isHasErrorsJson(bindingResult, jsonResult, "<br/>")) {
					bankService.insertBank(bank);
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
	 * 은행 수정 폼
	 * @param searchVO
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/decms/embed/shop/bank/modifyBank.do")
	public String modifyBank(@ModelAttribute("searchVO") BankVO searchVO, Model model) throws Exception {
		//내부 직원 권한이 아닐때 
		if(!EgovUserDetailsHelper.getAuthorities().contains("ROLE_EMPLOYEE")) {
			return "redirect:/decms/index.do";
		}
				
		BankVO bank = bankService.selectBank(searchVO);
		model.addAttribute("bank", bank);

		return "modoo/cms/shop/bank/bankForm";
	}

	/**
	 * 은행 수정
	 * @param bank
	 * @param bindingResult
	 * @return
	 */
	@RequestMapping(value = "/decms/shop/bank/modifyBank.json", method = RequestMethod.POST)
	@ResponseBody
	public JsonResult modifyBank(@Valid BankVO bank, BindingResult bindingResult) {
		JsonResult jsonResult = new JsonResult();
		
		try {
			//내부 직원 권한이 아닐때 
			if(!EgovUserDetailsHelper.getAuthorities().contains("ROLE_EMPLOYEE")) {
				jsonResult.setMessage(egovMessageSource.getMessage("cms.fail.accessDenied")); //접근 권한이 없습니다.
				jsonResult.setSuccess(false);
			}else {
				if(!this.isHasErrorsJson(bindingResult, jsonResult, "<br/>")) {
					
					if(StringUtils.isEmpty(bank.getBankId())) {
						this.vaildateMessage(egovMessageSource.getMessage("cms.fail.access"), jsonResult); // 잘못된 접근입니다.
						LOGGER.error("bankId 가 없음.");
					}else if(bankService.selectBankNameCheckCnt(bank) > 0) {
						this.vaildateMessage(egovMessageSource.getMessage("common.isExist.msg"), jsonResult); // 이미 존재하거나 과거에 등록이 되었던 상태입니다.
						LOGGER.error(bank.getBankNm() + "은 이미 등록된 은행입니다.");
					}else {
						bankService.updateBank(bank);
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
	 * 은행 삭제
	 * @param bank
	 * @return
	 */
	@RequestMapping(value = "/decms/shop/bank/deleteBank.json")
	@ResponseBody
	public JsonResult deleteBank(BankVO bank) {
		JsonResult jsonResult = new JsonResult();
		
		try {
			//내부 직원 권한이 아닐때 
			if(!EgovUserDetailsHelper.getAuthorities().contains("ROLE_EMPLOYEE")) {
				jsonResult.setMessage(egovMessageSource.getMessage("cms.fail.accessDenied")); //접근 권한이 없습니다.
				jsonResult.setSuccess(false);
			}else {
				if(StringUtils.isEmpty(bank.getBankId())) {
					this.vaildateMessage(egovMessageSource.getMessage("cms.fail.access"), jsonResult); // 잘못된 접근입니다.
					LOGGER.error("bankId 가 없음.");
				}else {
					bankService.deleteBank(bank);
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
