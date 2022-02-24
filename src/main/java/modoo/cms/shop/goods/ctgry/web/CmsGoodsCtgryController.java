package modoo.cms.shop.goods.ctgry.web;

import java.util.ArrayList;
import java.util.HashMap;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import egovframework.com.cmm.EgovMessageSource;
import egovframework.com.cmm.util.EgovUserDetailsHelper;
import egovframework.rte.psl.dataaccess.util.EgovMap;
import modoo.module.common.service.JsonResult;
import modoo.module.common.web.CommonDefaultController;
import modoo.module.shop.goods.ctgry.service.GoodsCtgryService;
import modoo.module.shop.goods.ctgry.service.GoodsCtgryVO;
import modoo.module.shop.goods.info.service.GoodsService;
import modoo.module.shop.goods.info.service.GoodsVO;

@Controller
public class CmsGoodsCtgryController extends CommonDefaultController {

	private static final Logger LOGGER = LoggerFactory.getLogger(CmsGoodsCtgryController.class);
	
	private static final String ROOT_CTGRY_ID = "GCTGRY_0000000000000"; //최상위 카타고리ID
	
	@Resource(name = "goodsCtgryService")
	private GoodsCtgryService goodsCtgryService;
	
	@Resource(name = "goodsService")
	private GoodsService goodsService;
	
	/**
	 * 상품카테고리 관리
	 * @param searchVO
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/decms/shop/goods/goodsCtgryManage.do")
	String ctgryManage(@ModelAttribute("searchVO") GoodsCtgryVO searchVO, Model model) throws Exception {
		//내부 직원 권한이 아닐때 
		if(!EgovUserDetailsHelper.getAuthorities().contains("ROLE_EMPLOYEE")) {
			return "redirect:/decms/index.do";
		}
		
		return "modoo/cms/shop/goods/ctgry/goodsCtgryManage";
	}
	
	
	/**
	 * 상품카테고리 트리 목록
	 * @param searchVO
	 * @return
	 */
	@RequestMapping(value = "/decms/shop/goods/goodsCtgryTreeList.json")
	@ResponseBody
	public JsonResult goodsCtgryTreeList(GoodsCtgryVO searchVO) {
		JsonResult jsonResult = new JsonResult();

		try {
			List<GoodsCtgryVO> ctgryList = goodsCtgryService.selectGoodsCtgryTreeList(searchVO);
			
			List<GoodsCtgryVO> resultList = new ArrayList<>();
			for(GoodsCtgryVO i : ctgryList){
				GoodsVO goods = new GoodsVO();
				goods.setSearchGoodsCtgryId(i.getGoodsCtgryId());
				int goodsListCnt = goodsService.selectGoodsListCnt(goods);
				i.setGoodsListCnt(goodsListCnt);
				resultList.add(i);
			}
			jsonResult.put("list", resultList);
			
			jsonResult.put("list", ctgryList);
			jsonResult.setSuccess(true);
			
		} catch(Exception e) {
			loggerError(LOGGER, e);
			jsonResult.setSuccess(false);
			jsonResult.setMessage(egovMessageSource.getMessage("fail.common.select")); //조회에 실패하였습니다.
		}
		
		return jsonResult;
	}
	
	/**
	 * 상품카테고리 상품목록
	 * @param searchVO
	 * @param model
	 * @param searchVO
	 * @return
	 */
//	@RequestMapping(value = "/decms/shop/goods/ctgryGoodsList.do")
//	public String ctgryGoodsList(@ModelAttribute("searchVO")GoodsVO searchVO, Model model,@RequestParam("ctgryId")String ctgryId) {
//		
//		model.addAttribute("ctgryId",ctgryId);
//		return "modoo/cms/shop/goods/ctgry/ctgryGoodsList";
//		
//	}
	/**
	 * 상품카테고리 목록
	 * @param searchVO
	 * @return
	 */
	@RequestMapping(value = "/decms/shop/goods/goodsCtgryList.json")
	@ResponseBody
	public JsonResult goodsCtgryList(GoodsCtgryVO searchVO) {
		JsonResult jsonResult = new JsonResult();

		try {
			List<GoodsCtgryVO> resultList = goodsCtgryService.selectGoodsCtgryList(searchVO);
			jsonResult.put("list", resultList);
			jsonResult.setSuccess(true);
		} catch(Exception e) {
			loggerError(LOGGER, e);
			jsonResult.setSuccess(false);
			jsonResult.setMessage(egovMessageSource.getMessage("fail.common.select")); //조회에 실패하였습니다.
		}
		
		return jsonResult;
		
	}
	
	/**
	 * 활성 상품카테고리 목록
	 * @param searchVO
	 * @return
	 */
	@RequestMapping(value = "/decms/shop/goods/actGoodsCtgryList.json")
	@ResponseBody
	public JsonResult ActGoodsCtgryList(GoodsCtgryVO searchVO) {
		JsonResult jsonResult = new JsonResult();
		
		try {
			searchVO.setActvtyAt("Y");
			List<GoodsCtgryVO> resultList = goodsCtgryService.selectActGoodsCtgryList(searchVO);
			jsonResult.put("list", resultList);
			
			jsonResult.setSuccess(true);
			
		} catch(Exception e) {
			loggerError(LOGGER, e);
			jsonResult.setSuccess(false);
			jsonResult.setMessage(egovMessageSource.getMessage("fail.common.select")); //조회에 실패하였습니다.
		}
		
		return jsonResult;
		
	}
	
	/**
	 * 상품카테고리 등록 폼
	 * @param searchVO
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/decms/embed/shop/goods/writeGoodsCtgry.do")
	public String writeGoodsCtgry(@ModelAttribute("searchVO") GoodsCtgryVO searchVO, Model model) throws Exception {
		//내부 직원 권한이 아닐때 
		if(!EgovUserDetailsHelper.getAuthorities().contains("ROLE_EMPLOYEE")) {
			return "redirect:/decms/index.do";
		}

		GoodsCtgryVO goodsCtgry = new GoodsCtgryVO();
		goodsCtgry.setGoodsCtgrySn(0);
		if(StringUtils.isEmpty(searchVO.getUpperGoodsCtgryId())) {
			goodsCtgry.setUpperGoodsCtgryId(ROOT_CTGRY_ID);
		}else {
			goodsCtgry.setUpperGoodsCtgryId(searchVO.getUpperGoodsCtgryId());
		}
		goodsCtgry.setCtgryExpsrSeCode("ALL"); //모두노출 초기값
		model.addAttribute("goodsCtgry", goodsCtgry);
		
		return "modoo/cms/shop/goods/ctgry/goodsCtgryForm";
	}
	
	/**
	 * 상품카테고리 저장
	 * @param goodsCtgry
	 * @param bindingResult
	 * @return
	 */
	@RequestMapping(value = "/decms/shop/goods/writeGoodsCtgry.json", method = RequestMethod.POST)
	@ResponseBody
	public JsonResult writeGoodsCtgry(@Valid GoodsCtgryVO goodsCtgry, BindingResult bindingResult) {
		JsonResult jsonResult = new JsonResult();
		try {
			//내부 직원 권한이 아닐때 
			if(!EgovUserDetailsHelper.getAuthorities().contains("ROLE_EMPLOYEE")) {
				jsonResult.setMessage(egovMessageSource.getMessage("cms.fail.accessDenied")); //접근 권한이 없습니다.
				jsonResult.setSuccess(false);
			}else {
				if(!this.isHasErrorsJson(bindingResult, jsonResult, "<br/>")) {
					
					if(StringUtils.isEmpty(goodsCtgry.getUpperGoodsCtgryId())) {
						goodsCtgry.setUpperGoodsCtgryId(ROOT_CTGRY_ID);
					}
					
					if(goodsCtgry.getActvtyAt()==null) goodsCtgry.setActvtyAt("N");
					goodsCtgryService.insertGoodsCtgry(goodsCtgry);
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
	 * 상품카테고리 수정 폼
	 * @param searchVO
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/decms/embed/shop/goods/modifyGoodsCtgry.do")
	public String modifyGoodsCtgry(@ModelAttribute("searchVO") GoodsCtgryVO searchVO, Model model) throws Exception {
		//내부 직원 권한이 아닐때 
		if(!EgovUserDetailsHelper.getAuthorities().contains("ROLE_EMPLOYEE")) {
			return "redirect:/decms/index.do";
		}

		GoodsCtgryVO goodsCtgry = goodsCtgryService.selectGoodsCtgry(searchVO);
		model.addAttribute("goodsCtgry", goodsCtgry);
		
		return "modoo/cms/shop/goods/ctgry/goodsCtgryForm";
	}
	
	/**
	 * 상품카테고리 수정
	 * @param goodsCtgry
	 * @return
	 */
	@RequestMapping(value = "/decms/shop/goods/modifyGoodsCtgry.json", method = RequestMethod.POST)
	@ResponseBody
	public JsonResult modifyGoodsCtgry(@Valid GoodsCtgryVO goodsCtgry, BindingResult bindingResult) {
		JsonResult jsonResult = new JsonResult();
		try {
			//내부 직원 권한이 아닐때 
			if(!EgovUserDetailsHelper.getAuthorities().contains("ROLE_EMPLOYEE")) {
				jsonResult.setMessage(egovMessageSource.getMessage("cms.fail.accessDenied")); //접근 권한이 없습니다.
				jsonResult.setSuccess(false);
			}else {
				if(!this.isHasErrorsJson(bindingResult, jsonResult, "<br/>")) {
					if(goodsCtgry.getActvtyAt()==null) {
						goodsCtgry.setActvtyAt("Y");
						if("NONE".equals(goodsCtgry.getCtgryExpsrSeCode())){
							goodsCtgry.setActvtyAt("N");
						}
					}
					goodsCtgryService.updateGoodsCtgry(goodsCtgry);
					jsonResult.setSuccess(true);
					jsonResult.setMessage(egovMessageSource.getMessage("success.common.update")); //정상적으로 수정되었습니다.
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
	 * 상품카테고리 삭제
	 * @param goodsCtgry
	 * @return
	 */
	@RequestMapping(value = "/decms/shop/goods/deleteGoodsCtgry.json", method = RequestMethod.POST)
	@ResponseBody
	public JsonResult deleteGoodsCtgry(GoodsCtgryVO goodsCtgry) {
		JsonResult jsonResult = new JsonResult();
		try {
			//내부 직원 권한이 아닐때 
			if(!EgovUserDetailsHelper.getAuthorities().contains("ROLE_EMPLOYEE")) {
				jsonResult.setMessage(egovMessageSource.getMessage("cms.fail.accessDenied")); //접근 권한이 없습니다.
				jsonResult.setSuccess(false);
			}else {
				if(StringUtils.isEmpty(goodsCtgry.getGoodsCtgryId())) {
					this.vaildateMessage(egovMessageSource.getMessage("cms.fail.access"), jsonResult); // 잘못된 접근입니다.
					LOGGER.error("goodsCtgryId 가 없음.");
				}
				
				goodsCtgryService.deleteGoodsCtgry(goodsCtgry);
				jsonResult.setMessage(egovMessageSource.getMessage("success.common.delete")); //정상적으로 삭제되었습니다.
				jsonResult.setSuccess(true);
			}
		} catch(Exception e) {
			loggerError(LOGGER, e);
			jsonResult.setSuccess(false);
			jsonResult.setMessage(egovMessageSource.getMessage("fail.common.delete")); //삭제가 실패하였습니다.
		}
		
		return jsonResult;
		
	}
}
