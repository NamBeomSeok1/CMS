package modoo.front.user.my.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;


import egovframework.com.cmm.ComDefaultCodeVO;
import egovframework.com.cmm.LoginVO;
import egovframework.com.cmm.service.CmmnDetailCode;
import egovframework.com.cmm.service.EgovCmmUseService;
import egovframework.com.cmm.util.EgovUserDetailsHelper;
import egovframework.rte.psl.dataaccess.util.EgovMap;
import egovframework.rte.ptl.mvc.tags.ui.pagination.PaginationInfo;
import modoo.module.biling.service.Biling;
import modoo.module.biling.service.Encryption;
import modoo.module.card.service.CreditCardService;
import modoo.module.card.service.CreditCardVO;
import modoo.module.common.service.JsonResult;
import modoo.module.common.util.CommonUtils;
import modoo.module.common.web.CommonDefaultController;
import modoo.module.faq.service.FaqinfoService;
import modoo.module.faq.service.FaqinfoVO;
import modoo.module.qainfo.service.QainfoService;
import modoo.module.qainfo.service.QainfoVO;
import modoo.module.shop.goods.dlvy.service.OrderDlvyService;
import modoo.module.shop.goods.dlvy.service.OrderDlvyVO;
import modoo.module.shop.goods.info.service.GoodsItemService;
import modoo.module.shop.goods.info.service.GoodsItemVO;
import modoo.module.shop.goods.info.service.GoodsService;
import modoo.module.shop.goods.info.service.GoodsVO;
import modoo.module.shop.goods.order.service.OrderService;
import modoo.module.shop.goods.order.service.OrderVO;
import modoo.module.shop.goods.setle.service.OrderSetleService;
import modoo.module.shop.goods.setle.service.OrderSetleVO;
import modoo.module.shop.user.service.DlvyAdresService;
import modoo.module.shop.user.service.DlvyAdresVO;

@Controller
public class MyPageController extends CommonDefaultController {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(MyPageController.class);
	
	@Resource(name="qainfoService")
	QainfoService qainfoService;
	
	@Resource(name="EgovCmmUseService")
	private EgovCmmUseService cmmUseService;

	@Resource(name="goodsService")
	private GoodsService goodsService;
	
	@Resource(name="goodsItemService")
	private GoodsItemService goodsItemService;
	
	@Resource(name="creditCardService")
	private CreditCardService creditCardService;
	
	@Resource(name="dlvyAdresService")
	private DlvyAdresService dlvyAdresService;
	
	@Resource(name="orderService")
	private OrderService orderService;

	@Resource(name="orderDlvyService")
	private OrderDlvyService orderDlvyService;

	@Resource(name="orderSetleService")
	private OrderSetleService orderSetleService;
	
	@Resource(name = "faqinfoService")
	private FaqinfoService faqinfoService;
	
	//암호화,복호화
	Encryption encryption = new Encryption();
	

	@RequestMapping(value = "/user/my/myPage.do")
	public String myPage(Model model) throws Exception {
		
		return "modoo/front/user/my/myPage";
	}
	
	/**
	 * 마이페이지 서브메뉴
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/user/my/subMenu.do")
	public String subMyPageMenu(Model model) throws Exception {
		
		return "modoo/front/user/my/subMenu";
	}
	/**
	 * 마이페이지 유저정보
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/user/my/userInfo.do")
	public String myPageUserInfo(Model model) throws Exception {
		
		return "modoo/front/user/my/userInfo";
	}
	/**
	 * 마이페이지 카테고리
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/user/my/myLocation.do")
	public String myPageLocation(Model model) throws Exception {
		
		return "modoo/front/user/my/myCtgryLocation";
	}
	
	/**
	 * 마이페이지 내정보
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/user/my/myInfo.do")
	public String myPageMyInfo(Model model) throws Exception {
		
		try {
			LoginVO user = (LoginVO)EgovUserDetailsHelper.getAuthenticatedUser();
			model.addAttribute("user",user);
			if(user == null){
				return "redirect:/index.do";
			}
			
			CreditCardVO card = new CreditCardVO(); 
			card.setEsntlId(user.getUniqId());
			card.setBassUseAt("Y");
			card.setUseAt("Y");
			CreditCardVO bassUseCard = creditCardService.selectCard(card);
			if(bassUseCard!=null){
				String decCardNo = encryption.decryption(bassUseCard.getCardNo());
				if(decCardNo.length()==15){
					bassUseCard.setLastCardNo(decCardNo.substring(12,15));
				}else{
					bassUseCard.setLastCardNo(decCardNo.substring(12,16));
				}
				model.addAttribute("bassUseCard",bassUseCard);
			}
			
			
			DlvyAdresVO searchVO = new DlvyAdresVO();
			searchVO.setDlvyUserId(user.getId());
			DlvyAdresVO bassDlvyAdres = dlvyAdresService.selectBassDlvyAdres(searchVO);
			
			if (bassDlvyAdres != null) {
				model.addAttribute("bassDlvyAdres", bassDlvyAdres.getDlvyAdres() + ", " + bassDlvyAdres.getDlvyAdresDetail());	
			} else {
				model.addAttribute("bassDlvyAdres", "기본 배송지가 없습니다.");
			}
			
		} catch (Exception e) {
			loggerError(LOGGER, e);
		}

		return "modoo/front/user/my/myInfo";
	}
	
	/**
	 * 마이페이지 내정보 javascript
	 * @param response
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/user/my/myInfoScript.do")
	public String myInfoScript(HttpServletResponse response, Model model) throws Exception {
		response.setContentType("application/javascript");
		return "modoo/front/user/my/myInfoScript";
	}
	
	/*
	 *마이페이지 QNA,1:1문의 이동 
	 * @param model
	 * @param request
	 * @return  
	 */
	@RequestMapping(value="/user/my/qainfo.do")
	public String mypageQna(@ModelAttribute("qaInfo")QainfoVO qainfo,Model model,HttpServletRequest request){
		
		LoginVO user = (LoginVO)EgovUserDetailsHelper.getAuthenticatedUser();

		try {
			
			int waitCnt = 0;
			int completeCnt = 0;
			PaginationInfo paginationInfo = new PaginationInfo(); // 페이징 처리
			ComDefaultCodeVO codeVO = new ComDefaultCodeVO();//코드
			if(request.getParameter("qaSeCode").equals("SITE")){
				qainfo.setQaSeCode("SITE");
				model.addAttribute("qaSeCode","SITE");
				codeVO.setCodeId("CMS019");

			}else if(request.getParameter("qaSeCode").equals("GOODS")){
				qainfo.setQaSeCode("GOODS");
				model.addAttribute("qaSeCode","GOODS");
				codeVO.setCodeId("CMS018");
			}
			
			qainfo.setWrterId(user.getId());
			List<?> qestnTyCodeList = cmmUseService.selectCmmCodeDetail(codeVO);
			model.addAttribute("qestnTyCodeList", qestnTyCodeList);
			qainfo.setPageUnit(propertiesService.getInt("pageUnit")); // src/main/resources/egovframework/spring/com/context-properties.xml
			this.setPagination(paginationInfo, qainfo);
			//답변대기 개수
			qainfo.setQnaProcessSttusCode("R");
			waitCnt=qainfoService.selectQainfoListCnt(qainfo);
			//답변완료 개수
			qainfo.setQnaProcessSttusCode("C");
			completeCnt=qainfoService.selectQainfoListCnt(qainfo);
			
			qainfo.setQnaProcessSttusCode(null);
			int totalRecordCount = qainfoService.selectQainfoListCnt(qainfo); // 목록 카운트
			
			paginationInfo.setTotalRecordCount(totalRecordCount);
			List<?> qaList = qainfoService.selectQainfoList(qainfo);
			
			model.addAttribute("waitCnt", waitCnt);
			model.addAttribute("completeCnt", completeCnt);
			model.addAttribute("paginationInfo", paginationInfo);
			model.addAttribute("qaList",qaList);
			

		} catch (Exception e) {
			loggerError(LOGGER, e);
		}
		return "modoo/front/user/my/qainfo/qainfo";
	}
	
	/**
	 * 내가 쓴 리뷰 페이지 이동
	 * @return
	 */
	
	@RequestMapping(value="/user/my/review.do")
	public String myReview() {
		
		return "modoo/front/user/my/review/review";
	}
	
	/**
	 * 작성 가능한 리뷰 페이지 이동
	 * @return
	 */
	
	@RequestMapping(value="/user/my/reviewTodo.do")
	public String myReviewTodo() {
		
		return "modoo/front/user/my/review/reviewTodo";
	}
	
	/**
	 * 카드관리 이동
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/user/my/cardManage.do")
	public String myCardList(Model model){
		
		try {
			
			LoginVO user = (LoginVO)EgovUserDetailsHelper.getAuthenticatedUser();
			
			CreditCardVO card = new CreditCardVO();
			card.setEsntlId(user.getUniqId());
			
			PaginationInfo paginationInfo = new PaginationInfo();
			card.setPageUnit(propertiesService.getInt("pageUnit"));
			this.setPagination(paginationInfo, card);
			card.setUseAt("Y");
			int totalRecordCount = creditCardService.selectCardListCnt(card);
			paginationInfo.setTotalRecordCount(totalRecordCount);
			List<CreditCardVO> cardList = creditCardService.selectCardList(card);
			
			for(CreditCardVO cvo : cardList){
				String decCardNo = encryption.decryption(cvo.getCardNo());
				if(decCardNo.length()==15){
					cvo.setLastCardNo(decCardNo.substring(12,15));
				}else{
					cvo.setLastCardNo(decCardNo.substring(12,16));
				}
			}
			System.out.println(cardList.toString());
			model.addAttribute("cardList",cardList);
			model.addAttribute("paginationInfo",paginationInfo);
			
		} catch (Exception e) {
			loggerError(LOGGER, e);
		}
		
		return "modoo/front/user/my/card/cardManage";
	}

	/**
	 * 카드등록 폼
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/user/my/cardForm.do")
	public String myCardForm(Model model){
		
		return "modoo/front/user/my/card/cardForm";
	}
	
	/**
	 * 주소 변경 팝업
	 * @return
	 */
	
	@RequestMapping(value="/user/my/deliveryEdit.do")
	public String deliveryEdit() {
		
		return "modoo/front/user/my/deliveryEdit"; 
	}
	
	/**
	 * FAQ 이동
	 * @return
	 */
	
	/*@RequestMapping(value="/user/my/faqList.do")
	public String faq() {
		
		return "modoo/front/user/my/faq/faqList";
	}*/
	
	@RequestMapping(value = "/user/my/faqList.do")
	public String faqList(@ModelAttribute("searchVO")FaqinfoVO searchVO, Model model ) throws Exception {
		
		List<String> searchFaqSeCodeList = new ArrayList<String>();
		if(StringUtils.isEmpty(searchVO.getSearchGroupCode()) || "F01".equals(searchVO.getSearchGroupCode())) {
			searchVO.setSearchGroupCode("F01"); // 주문/결제/취소
			searchFaqSeCodeList.add("FT001");
			searchFaqSeCodeList.add("FT002");
			searchFaqSeCodeList.add("FT003");
		}else if("F02".equals(searchVO.getSearchGroupCode())) { //배송문의
			searchFaqSeCodeList.add("FT004");
		}else if("F03".equals(searchVO.getSearchGroupCode())) { //교환/환불
			searchFaqSeCodeList.add("FT005");
			searchFaqSeCodeList.add("FT006");
		}else if("F04".equals(searchVO.getSearchGroupCode())) { //서비스이용관련
			searchFaqSeCodeList.add("FT007");
		}else if("F05".equals(searchVO.getSearchGroupCode())) { //기타사항
			searchFaqSeCodeList.add("FT008");
		}
		searchVO.setSearchFaqSeCode(searchFaqSeCodeList.toArray(new String[0]));
		
		PaginationInfo paginationInfo = new PaginationInfo();
		searchVO.setPageUnit(propertiesService.getInt("pageUnit"));
		this.setPagination(paginationInfo, searchVO);
		
		List<?> resultList = faqinfoService.selectFaqinfoList(searchVO);
		model.addAttribute("resultList", resultList);
		
		int totalRecordCount = faqinfoService.selectFaqinfoListCnt(searchVO);
		paginationInfo.setTotalRecordCount(totalRecordCount);
		model.addAttribute("paginationInfo", paginationInfo);
		
		return "modoo/front/user/my/faq/faqList";
	}
	
	/**
	 * 구독중
	 * @return
	 */
	
	@RequestMapping(value="/user/my/mySubscribeNow.do")
	public String mySubscribeNow() {
		LoginVO user = (LoginVO)EgovUserDetailsHelper.getAuthenticatedUser();
		if(user == null){
			return "redirect:/index.do";
		}
		
		return "modoo/front/user/my/mySubscribe/mySubscribeNow";
	}
	
	/**
	 * 구독 상세보기
	 * @return
	 * @throws Exception 
	 */
	
	@RequestMapping(value="/user/my/mySubscribeView.do")
	public String mySubscribeView(OrderVO searchVO, Model model) throws Exception {
		LoginVO user = (LoginVO)EgovUserDetailsHelper.getAuthenticatedUser();
		
		//주문정보
		OrderVO orderInfo = orderService.selectOrder(searchVO);
		System.out.println(orderInfo.toString()+"@@");
		model.addAttribute("orderInfo", orderInfo);
		if(orderInfo.getNextSetlede()!=null){
			String year = orderInfo.getNextSetlede().substring(0,4);
			String mt = orderInfo.getNextSetlede().substring(4,6);
			String day = orderInfo.getNextSetlede().substring(6,8);
			String nextSetlede = year+"-"+mt+"-"+day;
			model.addAttribute("nextSetlede", nextSetlede);
		}
		//상품정보
		GoodsVO goodsVO = new GoodsVO();
		goodsVO.setGoodsId(orderInfo.getGoodsId());
		GoodsVO goods = goodsService.selectGoods(goodsVO);
		
		//교환/환불정보
		QainfoVO qainfo = new QainfoVO();
		if (user != null) {
			qainfo.setFrstRegisterId(user.getUniqId());	
		}
		qainfo.setGoodsId(orderInfo.getGoodsId());
		QainfoVO qa = qainfoService.selectRefundInfo(qainfo);
		model.addAttribute("qaInfo", qa);
		
		Integer minUse=0;
		
		if ("SBS".equals(goods.getGoodsKndCode())) {
			if (("WEEK").equals(orderInfo.getSbscrptCycleSeCode())) {
				model.addAttribute("sbscrptCycle", orderInfo.getSbscrptWeekCycle()+"주");
				model.addAttribute("sbscrptDlvyDate", getCodeNm("CMS023", Integer.toString(orderInfo.getSbscrptDlvyWd()))+"요일");
				if(goods.getSbscrptMinUseWeek()!=null){
					minUse=goods.getSbscrptMinUseWeek();
				}
			} else if (("MONTH").equals(orderInfo.getSbscrptCycleSeCode())) {
				model.addAttribute("sbscrptCycle", orderInfo.getSbscrptMtCycle()+"개월");
				model.addAttribute("sbscrptDlvyDate", orderInfo.getSbscrptDlvyDay()+"일");
				if(goods.getSbscrptMinUseMt()!=null){
					minUse=goods.getSbscrptMinUseMt();
				}
			}else{
				minUse=0;
			}
		}
		
		model.addAttribute("minUse", minUse);
		model.addAttribute("goodsInfo", goods);
		
		model.addAttribute("orderStatusNm", orderInfo.getOrderSttusCodeNm());
		
//		if (orderInfo.getReqTyCode() != null) {
//			if("GNR".equals(orderInfo.getOrderKndCode()) && "ST02".equals(orderInfo.getOrderSttusCode()) && !"E01".equals(orderInfo.getReqTyCode()) && !"E02".equals(orderInfo.getReqTyCode())){
//				model.addAttribute("orderStatusNm", "체험구독");
//			} else {
//				model.addAttribute("orderStatusNm", getCodeNm("CMS027", orderInfo.getReqTyCode()));
//			}
//		} else {
//			if("GNR".equals(orderInfo.getOrderKndCode()) && "ST02".equals(orderInfo.getOrderSttusCode())){
//				model.addAttribute("orderStatusNm", "체험구독");
//			} else {
//				model.addAttribute("orderStatusNm", "주문취소완료");
//			}
//		}

		//상품항목정보
		GoodsItemVO goodsItemVO = new GoodsItemVO();
		goodsItemVO.setGoodsId(orderInfo.getGoodsId());
		goodsItemVO.setGitemSeCode("T");
		List<?> goodsItemList = goodsItemService.selectGoodsItemList(goodsItemVO);
		
		model.addAttribute("goodsItemList", goodsItemList);
		
		//주문항목
		List<?> orderItemList = orderService.selectOrderItemList(searchVO);
		model.addAttribute("orderItemList", orderItemList);

		//주문배송(쿠폰인데 배송지 있는주문들도)
		if("GNR".equals(orderInfo.getOrderKndCode())||("CPN".equals(orderInfo.getOrderKndCode())&& orderInfo.getDlvyZip()!=null)){
			OrderDlvyVO orderDlvy = new OrderDlvyVO();
			orderDlvy.setOrderNo(orderInfo.getOrderNo());
			EgovMap orderDlvyList = orderDlvyService.selectGnrOrderDlvy(orderDlvy);
			model.addAttribute("dlvyItem", orderDlvyList);
		}else{
			List<?> orderDlvyList = orderService.selectOrderDlvyList(searchVO);
			model.addAttribute("orderDlvyList", orderDlvyList);
		}
		
		ComDefaultCodeVO vo = new ComDefaultCodeVO();
		vo.setCodeId("CMS024");
		model.addAttribute("gitemSeCodeMap", cmmUseService.selectCmmCodeDetail(vo));
		
		//이용횟수
		int sbsCnt = orderService.selectSbsCnt(searchVO);
		model.addAttribute("sbsCnt", sbsCnt);

		return "modoo/front/user/my/mySubscribe/mySubscribeView";
	}
	
	/**
	 * 구독 상세보기 (구독변경용)
	 * @return
	 * @throws Exception 
	 */
	
	@RequestMapping(value="/user/my/mySubscribeModify.do")
	public String mySubscribeModify(OrderVO searchVO, Model model) throws Exception {
		//주문정보
		OrderVO orderInfo = orderService.selectOrder(searchVO);
		model.addAttribute("orderInfo", orderInfo);

		//주문 카드 정보
		CreditCardVO card = new CreditCardVO();
		card.setCardId(orderInfo.getCardId());
		card = creditCardService.selectCard(card);
		String decCardNoList = encryption.decryption(card.getCardNo());
		card.setLastCardNo(decCardNoList.substring(12,16));
		String payCardInfo = card.getCardNm()+"("+card.getLastCardNo()+")";
		model.addAttribute("payCardInfo",payCardInfo);
		model.addAttribute("payCardId",card.getCardId());
		
		//상품정보
		GoodsVO goodsVO = new GoodsVO();
		goodsVO.setGoodsId(orderInfo.getGoodsId());
		GoodsVO goods = goodsService.selectGoods(goodsVO);
		
		Integer minUse=0;
		
		if ("SBS".equals(goods.getGoodsKndCode())) {
			if (("WEEK").equals(orderInfo.getSbscrptCycleSeCode())) {
				model.addAttribute("sbscrptCycle", orderInfo.getSbscrptWeekCycle()+"주");
				model.addAttribute("sbscrptDlvyDate", getCodeNm("CMS023", Integer.toString(orderInfo.getSbscrptDlvyWd()))+"요일");
				if(goods.getSbscrptMinUseWeek()!=null){
					minUse=goods.getSbscrptMinUseWeek();
				}
			} else if (("MONTH").equals(orderInfo.getSbscrptCycleSeCode())) {
				model.addAttribute("sbscrptCycle", orderInfo.getSbscrptMtCycle()+"개월");
				model.addAttribute("sbscrptDlvyDate", orderInfo.getSbscrptDlvyDay()+"일");
				if(goods.getSbscrptMinUseMt()!=null){
					minUse=goods.getSbscrptMinUseMt();
				}
			}else{
				minUse=0;
			}
		}
		model.addAttribute("minUse", minUse);
		model.addAttribute("orderStatusNm", orderInfo.getOrderSttusCodeNm());
		
//		if (orderInfo.getReqTyCode() != null) {
//			if("GNR".equals(orderInfo.getOrderKndCode()) && "ST02".equals(orderInfo.getOrderSttusCode())){
//				model.addAttribute("orderStatusNm", "체험구독");
//			} else {
//				model.addAttribute("orderStatusNm", getCodeNm("CMS027", orderInfo.getReqTyCode()));
//			}
//		} else {
//			if("GNR".equals(orderInfo.getOrderKndCode()) && "ST02".equals(orderInfo.getOrderSttusCode())){
//				model.addAttribute("orderStatusNm", "체험구독");
//			} else {
//				model.addAttribute("orderStatusNm", getCodeNm("CMS021", orderInfo.getOrderSttusCode()));
//			}
//		}	
		model.addAttribute("goodsInfo", goods);
		
		
		//상품항목정보
		GoodsItemVO goodsItemVO = new GoodsItemVO();
		goodsItemVO.setGoodsId(orderInfo.getGoodsId());
		goodsItemVO.setGitemSeCode("T");
		List<?> goodsItemList = goodsItemService.selectGoodsItemList(goodsItemVO);
		
		model.addAttribute("goodsItemList", goodsItemList);
		
		//주문항목
		List<?> orderItemList = orderService.selectOrderItemList(searchVO);
		model.addAttribute("orderItemList", orderItemList);
		
		ComDefaultCodeVO vo = new ComDefaultCodeVO();
		vo.setCodeId("CMS024");
		model.addAttribute("gitemSeCodeMap", cmmUseService.selectCmmCodeDetail(vo));
		
		return "modoo/front/user/my/mySubscribe/mySubscribeModify";
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

	
	/**
	 * 구독 변경
	 * @return
	 */

	@RequestMapping(value="/user/my/mySubscribeEdit.do")
	public String mySubscribeEdit() {
		
		return "modoo/front/user/my/mySubscribe/mySubscribeEdit";
	}
	
	/**
	 * 구독 해지
	 * @return
	 */
	
	@RequestMapping(value="/user/my/mySubscribeCancel.do")
	public String mySubscribeCancel() {
		LoginVO user = (LoginVO)EgovUserDetailsHelper.getAuthenticatedUser();
		if(user == null){
			return "redirect:/index.do";
		}
		
		return "modoo/front/user/my/mySubscribe/mySubscribeCancel";
	}
	
	/**
	 * 교환/환불 문의 등록 폼
	 * @return
	 */
	@RequestMapping(value="/user/my/refundWrite.do")
	public String refundWrite() {
		LoginVO user = (LoginVO)EgovUserDetailsHelper.getAuthenticatedUser();
		if(user == null){
			return "redirect:/index.do";
		}
		
		return "modoo/front/user/my/myRefund/refundWrite";
	}
	
	/**
	 * 교환/환불 상세
	 * @return
	 */
	@RequestMapping(value="/user/my/myRefundView.do")
	public String myRefundView() {
		LoginVO user = (LoginVO)EgovUserDetailsHelper.getAuthenticatedUser();
		if(user == null){
			return "redirect:/index.do";
		}
		
		return "modoo/front/user/my/myRefund/myRefundView";
	}
	
	/**
	 * 교환/환불 목록
	 * @return
	 */
	@RequestMapping(value="/user/my/myRefund.do")
	public String myRefund() {
		LoginVO user = (LoginVO)EgovUserDetailsHelper.getAuthenticatedUser();
		if(user == null){
			return "redirect:/index.do";
		}
		
		return "modoo/front/user/my/myRefund/myRefund";
	}
	//구독 건너뛰기/해제
	@RequestMapping(value="/user/my/modSbs.json")
	@ResponseBody
	public JsonResult pauseSbs(@RequestParam("dlvyno") java.math.BigDecimal dlvyno,@RequestParam("kind") String kind){
		
		JsonResult jsonResult = new JsonResult();
		
		try {
			if(dlvyno==null){
				jsonResult.setSuccess(false);
				jsonResult.setMessage(egovMessageSource.getMessage("fail.common.update"));
			}else{
				OrderSetleVO searchVO = new OrderSetleVO();
				searchVO.setSearchOrderDlvyNo(dlvyno);
				OrderDlvyVO orderDlvy = orderDlvyService.selectOrderDlvy(searchVO);
				if(orderDlvy!=null){
					searchVO.setOrderSetleNo(orderDlvy.getOrderSetleNo());
					if("pause".equals(kind)){
						searchVO.setSetleSttusCode("P");
					}else if("pauseStop".equals(kind)){
						searchVO.setSetleSttusCode("R");
					}
					orderSetleService.updateOrderSetle(searchVO);
					jsonResult.setSuccess(true);
					jsonResult.setMessage(egovMessageSource.getMessage("success.common.update"));
				}else{
					jsonResult.setSuccess(false);
					jsonResult.setMessage(egovMessageSource.getMessage("fail.common.update"));
				}
				
			}
			
		} catch (Exception e) {
			loggerError(LOGGER, e);
			jsonResult.setSuccess(false);
			jsonResult.setMessage(egovMessageSource.getMessage("fail.common.update"));
		}
		return jsonResult;
	}
	
	/**
	 * 마이구독 > 주문상세 > 회차별 상세보기 페이지 이동
	 * @return
	 */
	@RequestMapping(value="/user/my/subscribeHistory.do")
	public String subscribeHistory() {
		return "modoo/front/user/my/mySubscribe/subscribeHistory";
	}
	
	/**
	 * 마이구독 > 주문상세 > 회차별 상세보기 목록
	 * @param searchVO
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "/user/my/subscribeDetail.json", method = RequestMethod.GET)
	public JsonResult subscribeDetail(OrderDlvyVO searchVO) throws Exception {
		JsonResult jsonResult = new JsonResult();
		boolean isLogin = EgovUserDetailsHelper.isAuthenticated();

		PaginationInfo paginationInfo = new PaginationInfo();

		this.setPagination(paginationInfo, searchVO);
		int totalRecordCount = orderDlvyService.selectSubscribeDetailCnt(searchVO); // 목록 카운트
		paginationInfo.setTotalRecordCount(totalRecordCount);

		List<?> sbsDetail = orderDlvyService.selectSubscribeDetail(searchVO); // 목록
		
		jsonResult.put("list", sbsDetail);
		jsonResult.put("paginationInfo", paginationInfo);
		jsonResult.put("isLogin", isLogin);

		return jsonResult;
	}
	
	
	/**
	 *결제 카드 변경 
	 * @param cardId
	 * @param orderNo
	 * @return
	 */
	@RequestMapping("/user/my/changeCard.json")
	@ResponseBody
	public JsonResult chageCard(@RequestParam("cardId") String cardId,@RequestParam("orderNo") String orderNo){
		
		JsonResult jsonResult = new JsonResult();
		
		try {
			
			CreditCardVO  card = new CreditCardVO();
			OrderVO order = new OrderVO();
			order.setOrderNo(orderNo);
			card.setCardId(cardId);
			card = creditCardService.selectCard(card);
			Biling biling = new Biling();
			
			
			HashMap<String, String> cardMap = new HashMap<>();
			
			cardMap.put("cardNo",card.getCardNo().trim());
			cardMap.put("cardUsgpd",card.getCardUsgpd().trim());
			cardMap.put("cardPassword",card.getCardPassword().trim());
			cardMap.put("brthdy",card.getBrthdy().trim());
			
			HashMap<String, Object> resultMap = biling.CardbilingKey(cardMap);
			String billKey = "";
			if((Boolean)resultMap.get("success")){
				billKey = (String)resultMap.get("billKey");
				jsonResult.setSuccess(true);
				order.setCardId(cardId);
				order.setBillKey(billKey);
				orderService.updateOrder(order);
			}else{
				jsonResult.setMessage("수정이 실패하였습니다.");
				jsonResult.setSuccess(false);
			}
			
		} catch (Exception e) {
			loggerError(LOGGER, e);
			jsonResult.setSuccess(false);
		}
		return jsonResult;
	}
}
