package modoo.cms.shop.goods.order.web;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

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
import org.springframework.web.servlet.ModelAndView;

import egovframework.com.cmm.ComDefaultCodeVO;
import egovframework.com.cmm.LoginVO;
import egovframework.com.cmm.service.CmmnDetailCode;
import egovframework.com.cmm.service.EgovCmmUseService;
import egovframework.com.cmm.util.EgovUserDetailsHelper;
import egovframework.rte.psl.dataaccess.util.EgovMap;
import egovframework.rte.ptl.mvc.tags.ui.pagination.PaginationInfo;
import modoo.module.biztalk.service.BiztalkService;
import modoo.module.biztalk.service.BiztalkVO;
import modoo.module.common.service.JsonResult;
import modoo.module.common.web.CommonDefaultController;
import modoo.module.shop.cmpny.service.CmpnyService;
import modoo.module.shop.cmpny.service.CmpnyVO;
import modoo.module.shop.goods.ctgry.service.GoodsCtgryService;
import modoo.module.shop.goods.dlvy.service.OrderDlvyService;
import modoo.module.shop.goods.dlvy.service.OrderDlvyVO;
import modoo.module.shop.goods.hist.service.OrderReqHistService;
import modoo.module.shop.goods.info.service.GoodsCouponService;
import modoo.module.shop.goods.info.service.GoodsCouponVO;
import modoo.module.shop.goods.info.service.GoodsService;
import modoo.module.shop.goods.info.service.GoodsVO;
import modoo.module.shop.goods.order.service.OrderService;
import modoo.module.shop.goods.order.service.OrderVO;
import modoo.module.shop.goods.setle.service.OrderSetleService;
import modoo.module.system.log.conect.service.ConectLogVO;

@Controller("CmsOrderController")
public class CmsOrderController extends CommonDefaultController {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(CmsOrderController.class);
	
	@Resource(name = "orderService")
	OrderService orderService;
	
	@Resource(name = "orderSetleService")
	OrderSetleService orderSetleService;
	
	@Resource(name = "orderDlvyService")
	OrderDlvyService orderDlvyService;
	
	@Resource(name = "goodsService")
	GoodsService goodsService;
	
	@Resource(name = "goodsCouponService")
	GoodsCouponService goodsCouponService;
	
	@Resource(name = "goodsCtgryService")
	GoodsCtgryService goodsCtgryService;
	
	@Resource(name="EgovCmmUseService")
	private EgovCmmUseService cmmUseService;
	
	@Resource(name="orderReqHistService")
	private OrderReqHistService orderReqHistService;
	
	@Resource(name = "biztalkService")
	private BiztalkService biztalkService;
	
	@Resource(name = "cmpnyService")
	private CmpnyService cmpnyService;

	/**
	 * 주문/배송
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/decms/shop/goods/orderDlvyManage.do")
	public String orderDlvyManage(@ModelAttribute("searchVO") OrderDlvyVO searchVO, Model model) throws Exception {
		ComDefaultCodeVO vo = new ComDefaultCodeVO();

		vo.setCodeId("CMS026");
		List<CmmnDetailCode> setleSttusCodeList = cmmUseService.selectCmmCodeDetail(vo);
		vo.setCodeId("CMS022");
		List<CmmnDetailCode> dlvySttusCodeList = cmmUseService.selectCmmCodeDetail(vo);
		vo.setCodeId("CMS021");
		List<CmmnDetailCode> orderSttusCodeList = cmmUseService.selectCmmCodeDetail(vo);
		
		model.addAttribute("setleSttusCodeList", setleSttusCodeList);
		model.addAttribute("dlvySttusCodeList", dlvySttusCodeList);
		model.addAttribute("orderSttusCodeList", orderSttusCodeList);

		return "modoo/cms/shop/goods/dlvy/orderDlvyManage";
	}
	
	/**
	 * 주문배송 이력 팝업
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/decms/shop/goods/orderDlvyHist.do")
	public String orderDlvyHist() throws Exception {

		return "modoo/cms/shop/goods/dlvy/orderDlvyHist";
	}
	
	/**
	 * 주문해지
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/decms/shop/goods/orderStopManage.do")
	public String orderStopManage() throws Exception {

		return "modoo/cms/shop/goods/order/orderStopManage";
	}
	
	/**
	 * 주문취소
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/decms/shop/goods/orderCancelManage.do")
	public String orderCancelManage() throws Exception {

		return "modoo/cms/shop/goods/order/orderCancelManage";
	}
	
	/**
	 * 주문교환
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/decms/shop/goods/orderExchangeManage.do")
	public String orderExchangeManage() throws Exception {

		return "modoo/cms/shop/goods/order/orderExchangeManage";
	}
	
	/**
	 * 주문반품
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/decms/shop/goods/orderRecallManage.do")
	public String orderRecallManage() throws Exception {

		return "modoo/cms/shop/goods/order/orderRecallManage";
	}
	
	/**
	 * 주문/배송 > 주문 현황
	 * @param searchVO
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/decms/shop/goods/getOrderStatusList.json")
	@ResponseBody
	public JsonResult getOrderStatusList(OrderDlvyVO searchVO,
			@RequestParam(name="searchGoodsCtgryId1", required = false) String searchCateCode1,
			@RequestParam(name="searchGoodsCtgryId2", required = false) String searchCateCode2,
			@RequestParam(name="searchGoodsCtgryId3", required = false) String searchCateCode3) throws Exception {
		LoginVO user = (LoginVO)EgovUserDetailsHelper.getAuthenticatedUser();
		JsonResult jsonResult = new JsonResult();
		try {
			if(EgovUserDetailsHelper.getAuthorities().contains("ROLE_SHOP")) {
				if (!EgovUserDetailsHelper.getAuthorities().contains("ROLE_ADMIN")) {
					searchVO.setSearchCmpnyId(user.getCmpnyId());	
				}
				if(StringUtils.isNotEmpty(searchCateCode3)) {
					searchVO.setSearchGoodsCtgryId(searchCateCode3);
				}else if(StringUtils.isNotEmpty(searchCateCode2)) {
					searchVO.setSearchGoodsCtgryId(searchCateCode2);
				}else if(StringUtils.isNotEmpty(searchCateCode1)) {
					searchVO.setSearchGoodsCtgryId(searchCateCode1);
				}

				PaginationInfo paginationInfo = new PaginationInfo();
				searchVO.setPageUnit(propertiesService.getInt("gridPageUnit"));
				this.setPagination(paginationInfo, searchVO);
				
				List<?> resultList = orderDlvyService.selectOrderDlvyList(searchVO);
				jsonResult.put("list",  resultList);
				
				int totalRecordCount = orderDlvyService.selectOrderDlvyListCnt(searchVO);
				paginationInfo.setTotalRecordCount(totalRecordCount);
				jsonResult.put("paginationInfo", paginationInfo);

				// 결제완료
				searchVO.setSearchSetleSttusCode("S");
				int cntSettle = orderDlvyService.selectOrderDlvyListCnt(searchVO);
				jsonResult.put("cntSettle", cntSettle);
				
				/* 배송준비 */
				searchVO.setSearchSetleSttusCode(null);
				// 상품준비중
				searchVO.setSearchDlvySttusCode("DLVY00");
				int cntDlvy = orderDlvyService.selectOrderDlvyListCnt(searchVO);
				jsonResult.put("cntDlvy", cntDlvy);
				
				// 배송준비중
				searchVO.setSearchDlvySttusCode("DLVY01");
				int cntDlvy1 = orderDlvyService.selectOrderDlvyListCnt(searchVO);
				jsonResult.put("cntDlvy1", cntDlvy1);
				
				// 배송중
				searchVO.setSearchDlvySttusCode("DLVY02");
				int cntDlvy2 = orderDlvyService.selectOrderDlvyListCnt(searchVO);
				jsonResult.put("cntDlvy2", cntDlvy2);
				
				// 배송완료
				searchVO.setSearchDlvySttusCode("DLVY03");
				int cntDlvy3 = orderDlvyService.selectOrderDlvyListCnt(searchVO);
				jsonResult.put("cntDlvy3", cntDlvy3);
				
				searchVO.setSearchDlvySttusCode(null);
				EgovMap stat = orderDlvyService.selectOrderStat(searchVO);
				//주문건수
				jsonResult.put("orderCnt", stat.get("cnt"));
				//판매금액
				jsonResult.put("orderAmount", stat.get("setleTotAmount"));
				
				jsonResult.setSuccess(true);
			}
		}catch(Exception e) {
			loggerError(LOGGER, e);
			jsonResult.setSuccess(false);
			jsonResult.setMessage(egovMessageSource.getMessage("fail.common.select")); //조회에 실패하였습니다
		}
		
		return jsonResult;
	}
	
	@RequestMapping(value = "/decms/shop/goods/getOrderStatusListExcel.do")
	public ModelAndView getOrderStatusListExcel(@ModelAttribute("searchVO") OrderDlvyVO searchVO,
			@RequestParam(name="searchCateCode1", required = false) String searchCateCode1,
			@RequestParam(name="searchCateCode2", required = false) String searchCateCode2,
			@RequestParam(name="searchCateCode3", required = false) String searchCateCode3, Model model) throws Exception {
		LoginVO user = (LoginVO)EgovUserDetailsHelper.getAuthenticatedUser();
		Map<String, Object> map = new HashMap<String, Object>();
		
		if(EgovUserDetailsHelper.getAuthorities().contains("ROLE_SHOP")) {
			if (!EgovUserDetailsHelper.getAuthorities().contains("ROLE_ADMIN")) {
				searchVO.setSearchCmpnyId(user.getCmpnyId());	
			}
			if(StringUtils.isNotEmpty(searchCateCode3)) {
				searchVO.setSearchGoodsCtgryId(searchCateCode3);
			}else if(StringUtils.isNotEmpty(searchCateCode2)) {
				searchVO.setSearchGoodsCtgryId(searchCateCode2);
			}else if(StringUtils.isNotEmpty(searchCateCode1)) {
				searchVO.setSearchGoodsCtgryId(searchCateCode1);
			}
		}
		
		searchVO.setRecordCountPerPage(Integer.MAX_VALUE);
		searchVO.setFirstIndex(0);
		List<?> resultList = orderDlvyService.selectOrderDlvyList(searchVO);
		map.put("dataList", resultList);
		map.put("template", "goods_order_dlvy_list.xlsx");
		map.put("fileName", "주문현황");
		
		return new ModelAndView("commonExcelView", map);
	}
	
	
	/**
	 * 주문 관리 > 주문/배송 > 회차별 구독현황
	 * @param searchVO
	 * @return
	 * @throws Exception
	 */
	
	@ResponseBody
	@RequestMapping(value = "/decms/shop/goods/getOrderDlvyHist.json", method=RequestMethod.POST)
	public JsonResult getOrderDlvyHist(OrderDlvyVO searchVO,
			@RequestParam(name="searchCateCode1", required = false) String searchCateCode1,
			@RequestParam(name="searchCateCode2", required = false) String searchCateCode2,
			@RequestParam(name="searchCateCode3", required = false) String searchCateCode3) throws Exception {
		LoginVO user = (LoginVO)EgovUserDetailsHelper.getAuthenticatedUser();
		JsonResult jsonResult = new JsonResult();
		
		try {
			if(!EgovUserDetailsHelper.getAuthorities().contains("ROLE_EMPLOYEE")) {
				if(StringUtils.isEmpty(user.getCmpnyId())) { //업체 매핑이 안되어 있으면.
					jsonResult.setMessage(egovMessageSource.getMessage("cms.fail.not.cmpnyId")); //업체등록이 필요합니다.
					jsonResult.setSuccess(false);
					return jsonResult;
				}
				searchVO.setSearchCmpnyId(user.getCmpnyId());
			}
			
			if(StringUtils.isNotEmpty(searchCateCode3)) {
				searchVO.setSearchGoodsCtgryId(searchCateCode3);
			}else if(StringUtils.isNotEmpty(searchCateCode2)) {
				searchVO.setSearchGoodsCtgryId(searchCateCode2);
			}else if(StringUtils.isNotEmpty(searchCateCode1)) {
				searchVO.setSearchGoodsCtgryId(searchCateCode1);
			}

			PaginationInfo paginationInfo = new PaginationInfo();
			searchVO.setPageUnit(propertiesService.getInt("gridPageUnit"));
			this.setPagination(paginationInfo, searchVO);

			List<?> resultList = orderDlvyService.selectOrderDlvyHist(searchVO);
			jsonResult.put("list",  resultList);
			
			int totalRecordCount = orderDlvyService.selectOrderDlvyHistCnt(searchVO);
			paginationInfo.setTotalRecordCount(totalRecordCount);
			jsonResult.put("paginationInfo", paginationInfo);
			
			jsonResult.setSuccess(true);
			
		}catch(Exception e) {
			loggerError(LOGGER, e);
			jsonResult.setSuccess(false);
			jsonResult.setMessage(egovMessageSource.getMessage("fail.common.select")); //조회에 실패하였습니다
		}
		
		return jsonResult;
	}
	
	/**
	 * 주문 옵션 조회
	 * @param searchVO
	 * @return
	 * @throws Exception
	 */
	
	@ResponseBody
	@RequestMapping(value = "/decms/shop/goods/getOrderItems.json", method=RequestMethod.POST)
	public JsonResult getOrderItems(OrderVO searchVO) throws Exception {
		EgovUserDetailsHelper.getAuthenticatedUser();
		JsonResult jsonResult = new JsonResult();

		try {
			List<?> resultList = orderService.selectOrderItemList(searchVO);
			jsonResult.put("list",  resultList);			
			jsonResult.setSuccess(true);
			
		}catch(Exception e) {
			loggerError(LOGGER, e);
			jsonResult.setSuccess(false);
			jsonResult.setMessage(egovMessageSource.getMessage("fail.common.select")); //조회에 실패하였습니다
		}
		
		return jsonResult;
	}
	
	/**
	 * 주문 쿠폰 조회
	 * @param searchVO
	 * @return
	 * @throws Exception
	 */
	
	@ResponseBody
	@RequestMapping(value = "/decms/shop/goods/getOrderCoupon.json", method=RequestMethod.POST)
	public JsonResult getOrderCoupon(OrderVO searchVO) throws Exception {
		EgovUserDetailsHelper.getAuthenticatedUser();
		JsonResult jsonResult = new JsonResult();

		try {
			GoodsCouponVO goodsCoupon = new GoodsCouponVO();
			goodsCoupon.setOrderNo(searchVO.getOrderNo());
			List<?> resultList = goodsCouponService.selectGoodsCouponList(goodsCoupon);
			jsonResult.put("list",  resultList);			
			jsonResult.setSuccess(true);
			
		}catch(Exception e) {
			loggerError(LOGGER, e);
			jsonResult.setSuccess(false);
			jsonResult.setMessage(egovMessageSource.getMessage("fail.common.select")); //조회에 실패하였습니다
		}
		
		return jsonResult;
	}
	
	
	/**
	 * 상품준비중에서 배송준비중으로 변경
	 * @param searchVO
	 * @param bindingResult
	 * @return
	 */
	
	@ResponseBody
	@RequestMapping(value = "/decms/shop/goods/modifyDlvyStatus.do", method = RequestMethod.POST)
	public JsonResult modifyDlvyStatus(OrderDlvyVO searchVO, BindingResult bindingResult) {
		JsonResult jsonResult = new JsonResult();
		
		LoginVO user = (LoginVO)EgovUserDetailsHelper.getAuthenticatedUser();
		searchVO.setLastUpdusrId(user.getUniqId());
		
		if (searchVO.getInvcNo() != null) {
			searchVO.setInvcNo(searchVO.getInvcNo().replaceAll("-", ""));
		}

		try {
			if(!this.isHasErrorsJson(bindingResult, jsonResult, "<br/>")) {
				orderDlvyService.updateDlvySttusCode(searchVO);
				jsonResult.setSuccess(true);
				jsonResult.setMessage(egovMessageSource.getMessage("success.common.update")); //수정이 성공하였습니다.
			}
		} catch(Exception e) {
			LOGGER.error("Exception : " + e);
			e.printStackTrace();
			jsonResult.setSuccess(false);
			jsonResult.setMessage(egovMessageSource.getMessage("fail.common.update")); //수정이 실패하였습니다.
		}
		
		return jsonResult;
	}
	
	/**
	 * 교환 재발송중으로 변경
	 * @param searchVO
	 * @param bindingResult
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/decms/shop/goods/modifyExchangeStatus.do", method = RequestMethod.POST)
	public JsonResult modifyExchangeStatus(OrderDlvyVO searchVO, BindingResult bindingResult) {
		JsonResult jsonResult = new JsonResult();

		LoginVO user = (LoginVO)EgovUserDetailsHelper.getAuthenticatedUser();
		searchVO.setLastUpdusrId(user.getUniqId());
		
		try {
			if(!this.isHasErrorsJson(bindingResult, jsonResult, "<br/>")) {
				orderDlvyService.updateExchangeStatus(searchVO);
				jsonResult.setSuccess(true);
				jsonResult.setMessage(egovMessageSource.getMessage("success.common.update")); //수정이 성공하였습니다.
			}
		} catch(Exception e) {
			LOGGER.error("Exception : " + e);
			jsonResult.setSuccess(false);
			jsonResult.setMessage(egovMessageSource.getMessage("fail.common.update")); //수정이 실패하였습니다.
		}
		
		return jsonResult;
	}
	
	/**
	 * 송장번호 등록
	 * @param searchVO
	 * @param bindingResult
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/decms/shop/goods/modifyInvcNo.do", method = RequestMethod.POST)
	public JsonResult modifyInvcNo(OrderDlvyVO searchVO, BindingResult bindingResult) {
		JsonResult jsonResult = new JsonResult();
		
		LoginVO user = (LoginVO)EgovUserDetailsHelper.getAuthenticatedUser();
		searchVO.setLastUpdusrId(user.getUniqId());
		searchVO.setDlvySttusCode("DLVY02");
		
		if (searchVO.getInvcNo() != null) {
			searchVO.setInvcNo(searchVO.getInvcNo().replaceAll("-", ""));
		}

		try {
			if(!this.isHasErrorsJson(bindingResult, jsonResult, "<br/>")) {
				orderDlvyService.updateInvcNo(searchVO);
				jsonResult.setSuccess(true);
				jsonResult.setMessage(egovMessageSource.getMessage("success.common.update")); //수정이 성공하였습니다.

				/* 배송시작 고객 알림톡 */
				OrderDlvyVO dlvyInfo = orderDlvyService.selectOrderDlvyByDlvyNo(searchVO);
				
				OrderVO orderVO = new OrderVO();
				orderVO.setOrderNo(dlvyInfo.getOrderNo());
				OrderVO orderInfo = orderService.selectOrder(orderVO);
				
				GoodsVO goods = new GoodsVO();
				goods.setGoodsId(orderInfo.getGoodsId());
				GoodsVO goodsInfo = goodsService.selectGoods(goods);

				BiztalkVO bizTalk = new BiztalkVO();
				bizTalk.setRecipient(orderInfo.getTelno());
				bizTalk.setTmplatCode("template_026");
				/*[모두의 구독] 배송시작 안내
				 * 
				 *주문하신 상품의 배송이 시작되었습니다.
				 *
				 *주문날짜 : #{ORDERDE}
				 *주문번호 : #{ORDERNO}
				 *상품명 : #{PRODUCTNM}
				 *택배사 : #{DLVYCMPNY}
				 *운송장번호 : #{INVCNO}
				 */
				BiztalkVO template = biztalkService.selectBizTalkTemplate(bizTalk);
				String message = template.getTmplatCn();
				SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
				String orderDe = format.format(orderInfo.getOrderPnttm());
				message = message.replaceAll("#\\{ORDERDE\\}", orderDe);
				message = message.replaceAll("#\\{ORDERNO\\}", String.valueOf(orderInfo.getOrderNo()));
				message = message.replaceAll("#\\{PRODUCTNM\\}", goodsInfo.getGoodsNm());
				message = message.replaceAll("#\\{DLVYCMPNY\\}", goodsInfo.getHdryNm());
				message = message.replaceAll("#\\{INVCNO\\}", searchVO.getInvcNo());
				bizTalk.setMessage(message);
				
				BiztalkVO result = biztalkService.sendAlimTalk(bizTalk);
			}
			
		} catch(Exception e) {
			LOGGER.error("Exception : " + e);
			e.printStackTrace();
			jsonResult.setSuccess(false);
			jsonResult.setMessage(egovMessageSource.getMessage("fail.common.update")); //수정이 실패하였습니다.
		}
		
		return jsonResult;
	}
	
	/**
	 * 취소, 교환, 환불 목록
	 * @param searchVO
	 * @return
	 * @throws Exception
	 */

	@ResponseBody
	@RequestMapping(value = "/decms/shop/goods/getOrderReqList.json", method=RequestMethod.POST)
	public JsonResult getOrderCancelList(OrderDlvyVO searchVO) throws Exception {
		JsonResult jsonResult = new JsonResult();
		
		LoginVO user = (LoginVO)EgovUserDetailsHelper.getAuthenticatedUser();
		if (user != null) {
			searchVO.setSearchCmpnyId(user.getCmpnyId());
		}
		
		PaginationInfo paginationInfo = new PaginationInfo();

		this.setPagination(paginationInfo, searchVO);
		
		int totalRecordCount = orderDlvyService.selectOrderReqListCnt(searchVO); // 목록 카운트
		paginationInfo.setTotalRecordCount(totalRecordCount);
		jsonResult.put("paginationInfo", paginationInfo);

		List<?> orderReqList = orderDlvyService.selectOrderReqList(searchVO);
		jsonResult.put("list", orderReqList);
		
		searchVO.setSearchListType(null);
		searchVO.setSearchReqTyCode("C01");
		int cancel01 = orderDlvyService.selectOrderReqListCnt(searchVO); //주문취소접수갯수
		searchVO.setSearchReqTyCode("C02");
		int cancel02 = orderDlvyService.selectOrderReqListCnt(searchVO); //주문취소승인갯수
		//searchVO.setSearchReqTyCode("C03");
		//int cancel03 = orderDlvyService.selectOrderReqListCnt(searchVO); //주문취소반려갯수
		searchVO.setSearchReqTyCode("C04");
		int cancel04 = orderDlvyService.selectOrderReqListCnt(searchVO); //주문취소완료갯수
		
		searchVO.setSearchReqTyCode("E01");
		int exchange01 = orderDlvyService.selectOrderReqListCnt(searchVO); //교환신청접수갯수
		searchVO.setSearchReqTyCode("E02");
		int exchange02 = orderDlvyService.selectOrderReqListCnt(searchVO); //교환신청승인갯수
		//searchVO.setSearchReqTyCode("E03");
		//int exchange03 = orderDlvyService.selectOrderDlvyListCnt(searchVO); //교환신청반려갯수
		searchVO.setSearchReqTyCode("E04");
		int exchange04 = orderDlvyService.selectOrderReqListCnt(searchVO); //교환재발송갯수
		searchVO.setSearchReqTyCode("E05");
		int exchange05 = orderDlvyService.selectOrderReqListCnt(searchVO); //교환완료갯수
		
		searchVO.setSearchReqTyCode("R01");
		int recall01 = orderDlvyService.selectOrderReqListCnt(searchVO); //반품(환불)접수갯수
		//searchVO.setSearchReqTyCode("R02");
		//int recall02 = orderDlvyService.selectOrderReqListCnt(searchVO); //반품(환불)승인갯수
		//searchVO.setSearchReqTyCode("R03");
		//int recall03 = orderDlvyService.selectOrderReqListCnt(searchVO); //반품(환불)반려갯수
		searchVO.setSearchReqTyCode("R05");
		int recall04 = orderDlvyService.selectOrderReqListCnt(searchVO); //반품(환불)완료갯수
		
		searchVO.setSearchListType("STOP");
		searchVO.setSearchReqTyCode("T01");
		int stopReq = orderDlvyService.selectOrderReqListCnt(searchVO); // 구독해지 접수 갯수
		searchVO.setSearchReqTyCode("T02");
		int stopComplete = orderDlvyService.selectOrderReqListCnt(searchVO); // 구독해지 완료 갯수
		
		jsonResult.put("cancel01", cancel01);
		jsonResult.put("cancel02", cancel02);
		jsonResult.put("cancel04", cancel04);
		jsonResult.put("exchange01", exchange01);
		jsonResult.put("exchange02", exchange02);
		jsonResult.put("exchange04", exchange04);
		jsonResult.put("exchange05", exchange05);
		jsonResult.put("recall01", recall01);
		jsonResult.put("recall04", recall04);
		jsonResult.put("stopReq", stopReq);
		jsonResult.put("stopComplete", stopComplete);
		
		return jsonResult;
	}
	
	/**
	 * 취소, 교환, 환불 상태 변경 및 이력 추가
	 * @param searchVO
	 * @param bindingResult
	 * @return
	 */
	
	@ResponseBody
	@RequestMapping(value = "/decms/shop/goods/modifyProcessStatus.do", method = RequestMethod.POST)
	public JsonResult modifyProcessStatus(HttpServletRequest req, OrderDlvyVO searchVO, BindingResult bindingResult) {

		/*2022-02-17 수정 이전 버전
		JsonResult jsonResult = new JsonResult();
		
		LoginVO user = (LoginVO)EgovUserDetailsHelper.getAuthenticatedUser();
		searchVO.setLastUpdusrId(user.getUniqId());
		OrderVO orderVO = new OrderVO();
		orderVO.setOrderNo(searchVO.getOrderNo());
		
		*//* 모바일 여부 확인(이지웰 요청 파라미터) *//*
		String mobileYn = "";
		String userAgent = req.getHeader("User-Agent").toUpperCase();
		
	    if(userAgent.indexOf("MOBILE") > -1) {
	        if(userAgent.indexOf("PHONE") == -1){
	        	mobileYn = "Y"; 
	        } else {
				mobileYn = "Y"; 
			}
	    } else {
	    	mobileYn = "N";
	    }
		
		try {
			
			if(!this.isHasErrorsJson(bindingResult, jsonResult, "<br/>")) {
				orderDlvyService.modifyProcessStatus(searchVO, mobileYn);
				jsonResult.setSuccess(true);
				jsonResult.setMessage(egovMessageSource.getMessage("success.common.update")); //수정이 성공하였습니다.

				*//* 주문취소완료 알림톡 *//*
				System.out.println("-------------"+orderVO.toString());
				OrderVO orderInfo = orderService.selectOrder(orderVO);
				GoodsVO goods = new GoodsVO();
				goods.setGoodsId(orderInfo.getGoodsId());
				GoodsVO goodsInfo = goodsService.selectGoods(goods);
				
				BiztalkVO bizTalk = new BiztalkVO();
				
				if ("C04".equals(searchVO.getReqTyCode())) {
					bizTalk.setTmplatCode("template_003");
					*//* [모두의구독] 주문이 취소되었습니다.
					 * 주문금액 : #{PRICE}
					 * 상품명 : #{PRODUCTNAME}
					 *//*
				} else if ("E05".equals(searchVO.getReqTyCode())) { 
					bizTalk.setTmplatCode("template_007");
				} else if ("R05".equals(searchVO.getReqTyCode())) { 
					bizTalk.setTmplatCode("template_009");
				}
				BiztalkVO template = biztalkService.selectBizTalkTemplate(bizTalk);
				String message = template.getTmplatCn();
				message = message.replaceAll("#\\{PRICE\\}", String.valueOf(orderInfo.getTotAmount())+"원").replaceAll("#\\{PRODUCTNAME\\}", goodsInfo.getGoodsNm());
				message = message.replaceAll("#\\{TEL\\}", String.valueOf(goodsInfo.getCmpnyTelno()));
				bizTalk.setRecipient(orderInfo.getTelno());
				bizTalk.setMessage(message);
				
				BiztalkVO result = biztalkService.sendAlimTalk(bizTalk);
				
				if ("C04".equals(searchVO.getReqTyCode())) {			
					*//* 주문취소완료 CP 알림톡 *//*
					CmpnyVO cmpny = new CmpnyVO();
					cmpny.setCmpnyId(goodsInfo.getCmpnyId());
					CmpnyVO cmpnyInfo = cmpnyService.selectCmpny(cmpny);
					
					bizTalk.setRecipient(cmpnyInfo.getChargerTelno());
					bizTalk.setTmplatCode("template_027");
					BiztalkVO templateCp = biztalkService.selectBizTalkTemplate(bizTalk);
					String messageCp = templateCp.getTmplatCn();
					messageCp = messageCp.replaceAll("#\\{PRODUCTNO\\}", String.valueOf(goodsInfo.getGoodsId())).replaceAll("#\\{PRODUCTNAME\\}", goodsInfo.getGoodsNm());
					messageCp = messageCp.replaceAll("#\\{CUSTOMER\\}", String.valueOf(orderInfo.getOrdrrNm()));
					messageCp = messageCp.replaceAll("#\\{TEL\\}", String.valueOf(goodsInfo.getCmpnyTelno()));
					bizTalk.setMessage(messageCp);
					*//* [모두의구독] 주문이 취소되었습니다. 자세한 사항은 CP 관리자 페이지의 [취소] 메뉴에서 확인하실 수 있습니다.
					* 상품번호 : #{PRODUCTNO}
					* 상품명 : #{PRODUCTNAME}
					* 고객명 : #{CUSTOMER} (#{TEL})
					 *//*
					BiztalkVO resultCp = biztalkService.sendAlimTalk(bizTalk);
				}
				
			}
		} catch(Exception e) {
			LOGGER.error("Exception : " + e);
			jsonResult.setSuccess(false);
			jsonResult.setMessage(egovMessageSource.getMessage("fail.common.update")); //수정이 실패하였습니다.
		}
		
		return jsonResult;*/

		JsonResult jsonResult = new JsonResult();
		searchVO.setClientIp(req.getRemoteAddr());
		LoginVO user = (LoginVO)EgovUserDetailsHelper.getAuthenticatedUser();
		searchVO.setLastUpdusrId(user.getUniqId());
		OrderVO orderVO = new OrderVO();
		orderVO.setOrderNo(searchVO.getOrderNo());

		/* 모바일 여부 확인(이지웰 요청 파라미터) */
		String mobileYn = "";
		String userAgent = req.getHeader("User-Agent").toUpperCase();

		if(userAgent.indexOf("MOBILE") > -1) {
			if(userAgent.indexOf("PHONE") == -1){
				mobileYn = "Y";
			} else {
				mobileYn = "Y";
			}
		} else {
			mobileYn = "N";
		}

		try {

			if(!this.isHasErrorsJson(bindingResult, jsonResult, "<br/>")) {
				orderDlvyService.modifyProcessStatus(req, searchVO, mobileYn, jsonResult);
				jsonResult.setSuccess(true);
				jsonResult.setMessage(egovMessageSource.getMessage("success.common.update")); //수정이 성공하였습니다.

				System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@ jsonResult Data:" + jsonResult.getData().toString());

				if ("C".equals(jsonResult.getData().get("setleSttusCode"))) {
					jsonResult.setMessage("해당 상품에 대한 주문취소가 정상적으로 접수되었습니다. 카드로 결제한 금액의 경우 카드사에 따라 3~5영업일 이내에 환불이 완료됩니다.");

				/*	*//* 주문취소완료 알림톡 *//*
					//System.out.println("-------------"+orderVO.toString());
					OrderVO orderInfo = orderService.selectOrder(orderVO);
					GoodsVO goods = new GoodsVO();
					goods.setGoodsId(orderInfo.getGoodsId());
					GoodsVO goodsInfo = goodsService.selectGoods(goods);

					BiztalkVO bizTalk = new BiztalkVO();

					if ("C04".equals(searchVO.getReqTyCode())) {
						bizTalk.setTmplatCode("template_005");
						*//* 	[모두의구독] 구독이 해지되었습니다.
						 * 상품명 : #{PRODUCTNAME}
						 *//*
					} else if ("E05".equals(searchVO.getReqTyCode())) {
						bizTalk.setTmplatCode("template_007");
					} else if ("R05".equals(searchVO.getReqTyCode())) {
						bizTalk.setTmplatCode("template_009");
					}
					BiztalkVO template = biztalkService.selectBizTalkTemplate(bizTalk);
					String message = template.getTmplatCn();
					message = message.replaceAll("#\\{PRICE\\}", String.valueOf(orderInfo.getTotAmount())+"원").replaceAll("#\\{PRODUCTNAME\\}", goodsInfo.getGoodsNm());
					message = message.replaceAll("#\\{TEL\\}", String.valueOf(goodsInfo.getCmpnyTelno()));
					bizTalk.setRecipient(orderInfo.getTelno());
					bizTalk.setMessage(message);

					BiztalkVO result = biztalkService.sendAlimTalk(bizTalk);

					*//** 결제취소 알림톡 **//*
					*//**
					 * [모두의구독] 결제가 취소되었습니다.
					 * 결제금액 : #{PRICE}
					 * 주문번호: #{ORDERNO}
					 *//*
					bizTalk.setTmplatCode("template_030");
					BiztalkVO template2 = biztalkService.selectBizTalkTemplate(bizTalk);
					message = template2.getTmplatCn();
					message = message.replaceAll("#\\{PRICE\\}", String.valueOf(orderInfo.getTotAmount())+"원").replaceAll("#\\{PRODUCTNAME\\}", goodsInfo.getGoodsNm());
					message = message.replaceAll("#\\{ORDERNO\\}", String.valueOf(orderInfo.getOrderNo()));
					bizTalk.setRecipient(orderInfo.getTelno());
					bizTalk.setMessage(message);
					BiztalkVO result2 = biztalkService.sendAlimTalk(bizTalk);

					if ("C04".equals(searchVO.getReqTyCode())) {
						*//* 주문취소완료 CP 알림톡 *//*
						CmpnyVO cmpny = new CmpnyVO();
						cmpny.setCmpnyId(goodsInfo.getCmpnyId());
						CmpnyVO cmpnyInfo = cmpnyService.selectCmpny(cmpny);

						bizTalk.setRecipient(cmpnyInfo.getChargerTelno());
						bizTalk.setTmplatCode("template_034");
						BiztalkVO templateCp = biztalkService.selectBizTalkTemplate(bizTalk);
						String messageCp = templateCp.getTmplatCn();
						messageCp = messageCp.replaceAll("#\\{PRODUCTNAME\\}", goodsInfo.getGoodsNm());
						bizTalk.setMessage(messageCp);
						*//**
						 * [모두의구독] 구독이 해지되었습니다.
						 * (배송중인 상품의 경우 별도의 환불 절차 없이 다음 회차부터 구독이 해지됩니다.)
						 * * 상품명 : #{PRODUCTNAME}
						 *//*
						BiztalkVO resultCp = biztalkService.sendAlimTalk(bizTalk);
					}*/
				}

			}
		} catch(Exception e) {
			LOGGER.error("Exception : " + e);
			e.printStackTrace();
			jsonResult.setSuccess(false);
			jsonResult.setMessage(egovMessageSource.getMessage("fail.common.update")); //수정이 실패하였습니다.
		}

		return jsonResult;
	}
	
	@ResponseBody
	@RequestMapping(value = "/decms/shop/goods/getOrderItemList.json", method=RequestMethod.POST)
	public JsonResult getOrderItemList(OrderDlvyVO searchVO) throws Exception {
		JsonResult jsonResult = new JsonResult();
		
		LoginVO user = (LoginVO)EgovUserDetailsHelper.getAuthenticatedUser();
		if (user != null) {
			searchVO.setSearchCmpnyId(user.getCmpnyId());	
		}

		List<?> orderReqList = orderDlvyService.selectOrderDlvyList(searchVO);
		jsonResult.put("list", orderReqList);
		
		return jsonResult;
	}

	/**
	 * 교환, 반품 사유 팝업
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/decms/shop/goods/orderReason.do")
	public String orderReason() throws Exception {

		return "modoo/cms/shop/goods/order/orderReason";
	}
	
	/**
	 * 코드명 리턴 메소드
	 * @param codeId
	 * @param code
	 * @return
	 * @throws Exception
	 */
	
	public String getCodeNm(String codeId, String code) throws Exception {
		
		HashMap<String, String> codeMap = new HashMap<String, String>();
		ComDefaultCodeVO vo = new ComDefaultCodeVO();
		vo.setCodeId(codeId);
		vo.setCode(code);
		List<?> codeList = cmmUseService.selectCmmCodeDetail(vo);

		for (int i = 0; i<codeList.size(); i++) {
			CmmnDetailCode codeItem = (CmmnDetailCode) codeList.get(i);
			codeMap.put(codeItem.getCode(), codeItem.getCodeNm());
		}
		
		String codeNm = codeMap.get(code);
		
		return codeNm;
	}
	
}
