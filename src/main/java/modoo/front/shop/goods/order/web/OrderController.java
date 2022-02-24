package modoo.front.shop.goods.order.web;

import com.inicis.std.util.HttpUtil;
import com.inicis.std.util.ParseUtil;
import com.inicis.std.util.SignatureUtil;
import egovframework.com.cmm.ComDefaultCodeVO;
import egovframework.com.cmm.LoginVO;
import egovframework.com.cmm.service.*;
import egovframework.com.cmm.util.EgovUserDetailsHelper;
import egovframework.com.utl.sim.service.EgovFileScrty;
import egovframework.rte.psl.dataaccess.util.EgovMap;
import egovframework.rte.ptl.mvc.tags.ui.pagination.PaginationInfo;
import modoo.module.biling.service.Encryption;
import modoo.module.biling.service.EzwelFunc;
import modoo.module.biztalk.service.BiztalkService;
import modoo.module.biztalk.service.BiztalkVO;
import modoo.module.card.service.CreditCardService;
import modoo.module.card.service.CreditCardVO;
import modoo.module.common.service.FileMngUtil;
import modoo.module.common.service.JsonResult;
import modoo.module.common.util.CommonUtils;
import modoo.module.common.util.SiteDomainHelper;
import modoo.module.common.web.CommonDefaultController;
import modoo.module.mber.info.service.MberService;
import modoo.module.mber.info.service.MberVO;
import modoo.module.qainfo.service.QainfoVO;
import modoo.module.shop.cmpny.service.CmpnyService;
import modoo.module.shop.cmpny.service.CmpnyVO;
import modoo.module.shop.goods.cart.service.CartItem;
import modoo.module.shop.goods.cart.service.CartService;
import modoo.module.shop.goods.cart.service.CartVO;
import modoo.module.shop.goods.dlvy.service.OrderDlvyVO;
import modoo.module.shop.goods.info.service.*;
import modoo.module.shop.goods.order.service.OrderGroupVO;
import modoo.module.shop.goods.order.service.OrderService;
import modoo.module.shop.goods.order.service.OrderVO;
import modoo.module.shop.goods.setle.service.OrderSetleService;
import modoo.module.shop.idsrts.service.IdsrtsService;
import modoo.module.shop.idsrts.service.IdsrtsVO;
import modoo.module.shop.user.service.DlvyAdresService;
import modoo.module.shop.user.service.DlvyAdresVO;
import org.apache.commons.httpclient.DefaultHttpMethodRetryHandler;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
public class OrderController extends CommonDefaultController {

	
	private static final Logger LOGGER = LoggerFactory.getLogger(OrderController.class);
	@Resource(name = "goodsService")
	private GoodsService goodsService;
	
	@Resource(name = "cartService")
	private CartService cartService;
	
	@Resource(name="creditCardService")
	private CreditCardService cardService;
	
	@Resource(name = "dlvyAdresService")
	private DlvyAdresService dlvyAdresService;
	
	@Resource(name = "orderService")
	private OrderService orderService;
	
	@Resource(name = "EgovCmmUseService")
	private EgovCmmUseService EgovCmmUseService;

	@Resource(name = "orderSetleService")
	private OrderSetleService orderSetleService;
	
	@Resource(name = "idsrtsService")
	private IdsrtsService idsrtsService;
	
	@Resource(name = "fileMngUtil")
	private FileMngUtil fileMngUtil;
	
	@Resource(name = "EgovFileMngService")
	private EgovFileMngService fileMngService;

	@Resource(name = "mberService")
	private MberService mberService;

	@Resource(name = "cmpnyService")
	private CmpnyService cmpnyService;


	@Resource(name = "biztalkService")
	private BiztalkService biztalkService;
	
	@Resource(name = "goodsCouponService")
	private GoodsCouponService goodsCouponService;
	//이지웰 포인트
	private java.math.BigDecimal ezwelPoint=null;
	private static final String EZWEL_GROUP_ID = "GROUP_00000000000001";
	/**
	 * 단일(바로) 주문 (상품 주문 전 단계)
	 * 해당 상품을 STN_CART에 넣고 CART_ADD_AT = 'N' 으로 등록
	 * @param order
	 * @param redirectAttributes
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/shop/goods/order.do")
	public String order(@ModelAttribute("goodsCart") CartVO cart,
			final RedirectAttributes redirectAttributes, Model model) throws Exception {
		if(!EgovUserDetailsHelper.isAuthenticated()) {
			return "redirect:/index.do";
		}
		LoginVO user = (LoginVO)EgovUserDetailsHelper.getAuthenticatedUser();
		GoodsVO goods = new GoodsVO();
		goods.setGoodsId(cart.getGoodsId());
		goods = goodsService.selectGoods(goods);
		
		cart.setSbscrptCycleSeCode(goods.getSbscrptCycleSeCode());
		if(StringUtils.isEmpty(cart.getGoodsId())) {
			return "redirect:/index.do";
		}
		//1회 체험 여부
		if(!"Y".equals(cart.getExprnUseAt())){
			cart.setExprnPc(null);
			if(StringUtils.isNotEmpty(cart.getSbscrptCycleSeCode()) && goods.getGoodsKndCode().equals("SBS")){
				if("WEEK".equals(cart.getSbscrptCycleSeCode())) {
					if(cart.getSbscrptWeekCycle() == null || cart.getSbscrptDlvyWd() == null) {
						model.addAttribute("goods", goods);
						return "modoo/front/shop/goods/info/goodsView";
					}
				}else if("MONTH".equals(cart.getSbscrptCycleSeCode())) {
					if(cart.getSbscrptMtCycle() == null || cart.getSbscrptDlvyDay() == null) {
						model.addAttribute("goods", goods);
						return "modoo/front/shop/goods/info/goodsView";
					}
				}
			}else{
				cart.setSbscrptCycleSeCode(null);
				cart.setSbscrptMtCycle(null);
				cart.setSbscrptDlvyWd(null);
				cart.setSbscrptWeekCycle(null);
				cart.setSbscrptDlvyDay(null);
			}
		}else{
			//1회체험이면 주기 null처리
			cart.setSbscrptCycleSeCode(null);
			cart.setSbscrptMtCycle(null);
			cart.setSbscrptDlvyWd(null);
			cart.setSbscrptWeekCycle(null);
			cart.setSbscrptDlvyDay(null);
		}		
		//복수구매할인
		if(cart.getOrderCo()>=2 && "Y".equals(goods.getCompnoDscntUseAt())){
			cart.setCompnoDscntUseAt("Y");
		}else{
			cart.setCompnoDscntUseAt("N");
		}
		
		if(cart.getOrderCo() == null || cart.getOrderCo() <= 0) {
			return "redirect:/shop/goods/goodsView.do?goodsId=" + goods.getGoodsId();
		}
		
		if(!"Y".equals(cart.getExprnUseAt())){
			//첫구독 필수 검사
			if("Y".equals(goods.getFrstOptnEssntlAt())) {
				List<CartItem> cartItemList = cart.getCartItemList();
				List<GoodsItemVO> fGitemList = goods.getfGitemList();
				int cnt = 0;
				for(GoodsItemVO gitem : fGitemList) {
					for(CartItem citem : cartItemList) {
						if(citem.getGitemId().equals(gitem.getGitemId())) {
							cnt++;
						}
					}
				}
				if(cnt == 0) {
					model.addAttribute("goods", goods);
					return "redirect:/shop/goods/goodsView.do?goodsId=" + goods.getGoodsId();
				}
			}
		}
		
		
		cart.setOrdrrId(user.getId());
		cart.setCartAddAt("N"); // 단일 구독(결제) 
		java.math.BigDecimal cartno =  cartService.insertCart(cart);
		
		
		//redirectAttributes.addAttribute("cartno","1,2");
		
		redirectAttributes.addAttribute("cartno", cartno);
		
		

		
		return "redirect:/shop/goods/goodsOrder.do";
	}


	/**
	 * --상품주문 (구독) : 단일 주문 또는 장바구니 > 주문하기 버튼 클릭
	 * @param cartno
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/shop/goods/goodsOrder.do")
	public String goodsOrder(@RequestParam(name = "cartno", required = false) List<Integer> cartno
			, Model model,HttpServletRequest req) throws Exception {
		//암호화,복호화
		Encryption encryption = new Encryption();
		if(!EgovUserDetailsHelper.isAuthenticated()) {
			return "redirect:/index.do";
		}

		LoginVO user = (LoginVO)EgovUserDetailsHelper.getAuthenticatedUser();
		MberVO mber = new MberVO();
		if(cartno == null || cartno.size() == 0) {
			LOGGER.info("cartno 값이 들어 오지 않음. 사용자ID : " + user.getId());
			model.addAttribute("errorMessage","존재하지 않는 주문입니다.");
			return "modoo/common/error/error";
		}


		/*// 포인트 조회
		System.out.println("=========================포인트 조회======================================");
		LOGGER.info("유저정보"+user.getClientCd()+user.getGroupId()+user.getUserKey());

		if(EZWEL_GROUP_ID.equals(user.getGroupId())||"USRCNFRM_00000000128".equals(user.getUniqId())){
			mber.setEsntlId(user.getUniqId());
			mber = mberService.selectMber(mber);
			if("Y".equals(mber.getPointYn())){ //TODO 개발서버 이지웰 결제 테스트
				ezwelPoint=ezwelFunc.ezwelPointSearch(user.getUserKey(),user.getClientCd());
				model.addAttribute("ezwelPoint",ezwelPoint);
			}
			*//*ezwelPoint=ezwelFunc.ezwelPointSearch(user.getUserKey(),user.getClientCd());
			 *//**//*ezwelPoint=new java.math.BigDecimal(2000);*//**//*
			model.addAttribute("ezwelPoint",ezwelPoint);*//*
		} else if(PartnerGroupId.EXANADU.getCode().equals(user.getGroupId())){
			System.out.println("----------------------이제너두 포인트 조회----------------------");
			ezwelPoint=ExanaduSSO.pointSearch(req);
			System.out.println("이제너두 포인트:" + ezwelPoint);
			//ezwelPoint=new java.math.BigDecimal(50); //TODO 복합결제 테스트
			model.addAttribute("ezwelPoint", ezwelPoint);
		}*/

		//카드
		CreditCardVO card = new CreditCardVO();
		card.setEsntlId(user.getUniqId());
		card.setBassUseAt("Y");
		card.setUseAt("Y");
		CreditCardVO bassUseCard = cardService.selectCard(card);//기본사용카드
		if(bassUseCard !=null){
			String decCardNo = encryption.decryption(bassUseCard.getCardNo());
			if(decCardNo.length()==15){
				bassUseCard.setLastCardNo(decCardNo.substring(12,15));
			}else{
				bassUseCard.setLastCardNo(decCardNo.substring(12,16));
			}
		}
		model.addAttribute("bassUseCard",bassUseCard);

		card.setBassUseAt("N");
		card.setUseAt("Y");
		List<CreditCardVO> cardList = cardService.selectCardList(card);//카드리스트
		if(cardList!=null){
			for(CreditCardVO cvo : cardList){
				String decCardNoList = encryption.decryption(cvo.getCardNo());
				if(decCardNoList.length()==15){
					cvo.setLastCardNo(decCardNoList.substring(12,15));
				}else{
					cvo.setLastCardNo(decCardNoList.substring(12,16));
				}
			}
			model.addAttribute("cardList",cardList);
		}

		// 가장 최신 주문 정보 불러오기
		OrderVO searchVO = new OrderVO();
		searchVO.setOrdrrId(user.getId());
		searchVO=orderService.selectOrder(searchVO);
		String ordEmail = null;
		String ordTelno = null;
		if(searchVO!=null){
			if(StringUtils.isNotEmpty(searchVO.getOrdrrEmail())){
				ordEmail=searchVO.getOrdrrEmail();
				model.addAttribute("ordEmail", ordEmail);
			}else if(StringUtils.isEmpty(searchVO.getOrdrrEmail())){
				if(StringUtils.isNotEmpty(user.getEmail()) && user.getEmail().contains("@")){
					ordEmail=user.getEmail();
				}else{
					ordEmail=" @ ";
				}
				model.addAttribute("ordEmail",ordEmail);
			}
			if(StringUtils.isNotEmpty(searchVO.getTelno())){
				ordTelno=searchVO.getTelno();
				model.addAttribute("ordTelno",ordTelno);
			}else if(StringUtils.isEmpty(searchVO.getTelno())){
				ordTelno=" - - ";
				model.addAttribute("ordTelno",ordTelno);
			}
		}else{
			if(StringUtils.isNotEmpty(user.getEmail()) && user.getEmail().contains("@")){
				ordEmail=user.getEmail();
			}else{
				ordEmail=" @ ";
			}
			model.addAttribute("ordEmail",ordEmail);
			ordTelno=" - - ";
			model.addAttribute("ordTelno",ordTelno);
		}
		// 최근 배송지 목록 불러오기
		DlvyAdresVO dlvyAdres = new DlvyAdresVO();
		dlvyAdres.setDlvyUserId(user.getId());
		HashMap<String, Object> dlvyList = new HashMap<>();
		List<DlvyAdresVO> recentDlvyAdresList = dlvyAdresService.selectRecentUseDlvyAdres(dlvyAdres);
		dlvyList.put("recentDlvyAdresList", recentDlvyAdresList);

		// 기본배송지
		dlvyAdres.setDlvyUserId(user.getId());
		DlvyAdresVO bassDlvyAdres = dlvyAdresService.selectBassDlvyAdres(dlvyAdres);
		dlvyList.put("bassDlvy", bassDlvyAdres);
		model.addAttribute("dlvyList", dlvyList);

		CartVO searchCartVO = new CartVO();
		if("Y".equals(req.getParameter("isCart"))){
			searchCartVO.setCartAddAt("Y");
		}
		searchCartVO.setSearchOrdrrId(user.getId());
		searchCartVO.setSearchCartNoList(cartno);

		List<?> cartList = cartService.selectCartList(searchCartVO);

		EgovMap cart = (EgovMap)cartList.get(0);
		/* 모바일 여부 확인 */
		String mobileYn = CommonUtils.getMobile(req);
		model.addAttribute("mobileYn",mobileYn);

		/*일반상품,구독상품 체크---->일반상품일때만 간편결제 노출*/
		if(StringUtils.isNotEmpty((String)cart.get("sbscrptCycleSeCode"))){
			model.addAttribute("orderKnd","SBS");
		}else{
			model.addAttribute("orderKnd","GNR");
		}

		//쿠폰 한 개 상품체크
		Boolean isCoupon = false;
		Iterator ctIter = cartList.iterator();
		while(ctIter.hasNext()==true){
			EgovMap ct = (EgovMap)ctIter.next();
			if("CPN".equals(ct.get("goodsKndCode")) &&cartList.size()==1){
				isCoupon=true;
			}
		}
		model.addAttribute("isCoupon", isCoupon);

		// 장바구니에 목록이 없을때
		if(cartList == null || cartList.size() == 0) {
			String noStr = "";
			for(Integer no : cartno) {
				noStr += no + ",";
			}
			LOGGER.info("이미 주문이 되었거나 주문자가 등록하지 않은 목록을 요청 한 것으로 보임.  사용자ID : " + user.getId() + " (" + noStr + ")" + ", cartno : " + cartno);
			return "modoo/common/error/error";
		}
		model.addAttribute("cartList", cartList);

		if(req.getParameter("resultMsg")!=null){
			model.addAttribute("resultMsg",req.getParameter("resultMsg"));
		}
		/*
		order.setSbscrptCycleSeCode(goods.getSbscrptCycleSeCode()); //구독주기
		*/
		return "modoo/front/shop/order/goodsOrder";
	}

	/* 기존걸로 테스트 하기 위해 주석처리
	*//**
	 * 상품주문 (구독) : 단일 주문 또는 장바구니 주문
	 * @param order
	 * @param model
	 * @return
	 * @throws Exception
	 *//*
	@RequestMapping(value = "/shop/goods/goodsOrder.do")
	public String goodsOrder(@RequestParam(name = "cartno", required = false) List<Integer> cartno, Model model,HttpServletRequest req) throws Exception {
		//이니시스 단일결제 건 결과 값
		String pgResult = req.getParameter("pg");
		model.addAttribute("pgResult", pgResult);
		if(pgResult != null && "result".equals(req.getParameter("pg"))) {
			String successYn = "Y";
			String message = "성공";
			
			//#############################
			// 인증결과 파라미터 일괄 수신
			//#############################
			req.setCharacterEncoding("UTF-8");

			Map<String,String> paramMap = new Hashtable<String,String>();

			Enumeration elems = req.getParameterNames();

			String temp = "";

			while(elems.hasMoreElements())
			{
				temp = (String) elems.nextElement();
				paramMap.put(temp, req.getParameter(temp));
			}
			
			System.out.println("paramMap : "+ paramMap.toString());
			
			//#####################
			// 인증이 성공일 경우만
			//#####################
			if("0000".equals(paramMap.get("resultCode"))){
				System.out.println("####인증성공/승인요청####");

				//############################################
				// 1.전문 필드 값 설정(***가맹점 개발수정***)
				//############################################
				
				String mid 		= paramMap.get("mid");						// 가맹점 ID 수신 받은 데이터로 설정
				String signKey	= EgovProperties.getProperty("INICIS.signKey");	// 가맹점에 제공된 키(이니라이트키) (가맹점 수정후 고정) !!!절대!! 전문 데이터로 설정금지
				String timestamp= SignatureUtil.getTimestamp();				// util에 의해서 자동생성
				String charset 	= "UTF-8";								    // 리턴형식[UTF-8,EUC-KR](가맹점 수정후 고정)
				String format 	= "JSON";								    // 리턴형식[XML,JSON,NVP](가맹점 수정후 고정)
				String authToken= paramMap.get("authToken");			    // 취소 요청 tid에 따라서 유동적(가맹점 수정후 고정)
				String authUrl	= paramMap.get("authUrl");				    // 승인요청 API url(수신 받은 값으로 설정, 임의 세팅 금지)
				String netCancel= paramMap.get("netCancelUrl");			 	// 망취소 API url(수신 받은 값으로 설정, 임의 세팅 금지)
				String ackUrl 	= paramMap.get("checkAckUrl");			    // 가맹점 내부 로직 처리후 최종 확인 API URL(수신 받은 값으로 설정, 임의 세팅 금지)		
				String merchantData = paramMap.get("merchantData");			// 가맹점 관리데이터 수신
				
				//#####################
				// 2.signature 생성
				//#####################
				Map<String, String> signParam = new HashMap<String, String>();

				signParam.put("authToken",	authToken);		// 필수
				signParam.put("timestamp",	timestamp);		// 필수

				// signature 데이터 생성 (모듈에서 자동으로 signParam을 알파벳 순으로 정렬후 NVP 방식으로 나열해 hash)
				String signature = SignatureUtil.makeSignature(signParam);

	      		String price = "";  // 가맹점에서 최종 결제 가격 표기 (필수입력아님)
	      		
			    // 1. 가맹점에서 승인시 주문번호가 변경될 경우 (선택입력) 하위 연결.  
			    // String oid = "";             
	      
				//#####################
				// 3.API 요청 전문 생성
				//#####################
				Map<String, String> authMap = new Hashtable<String, String>();

				authMap.put("mid"			    ,mid);			  // 필수
				authMap.put("authToken"		,authToken);	// 필수
				authMap.put("signature"		,signature);	// 필수
				authMap.put("timestamp"		,timestamp);	// 필수
				authMap.put("charset"		  ,charset);		// default=UTF-8
				authMap.put("format"		  ,format);		  // default=XML
	      		//authMap.put("price" 		,price);		    // 가격위변조체크기능 (선택사용)
	      
				System.out.println("##승인요청 API 요청##");

				HttpUtil httpUtil = new HttpUtil();

				try{
					//#####################
					// 4.API 통신 시작
					//#####################
					String authResultString = "";

					authResultString = httpUtil.processHTTP(authMap, authUrl);
					
					//############################################################
					//5.API 통신결과 처리(***가맹점 개발수정***)
					//############################################################
					String test = authResultString.replace(",", "&").replace(":", "=").replace("\"", "").replace(" ","").replace("\n", "").replace("}", "").replace("{", "");
					
					Map<String, String> resultMap = new HashMap<String, String>();
					
					resultMap = ParseUtil.parseStringToMap(test); //문자열을 MAP형식으로 파싱
									
					System.out.println("resultMap == " + resultMap);
					
					*//*************************  결제보안 강화 2016-05-18 START ****************************//*
					Map<String , String> secureMap = new HashMap<String, String>();
					secureMap.put("mid"			, mid);								//mid
					secureMap.put("tstamp"		, timestamp);						//timestemp
					secureMap.put("MOID"		, resultMap.get("MOID"));			//MOID
					secureMap.put("TotPrice"	, resultMap.get("TotPrice"));		//TotPrice
					
					// signature 데이터 생성 
					String secureSignature = SignatureUtil.makeSignatureAuth(secureMap);
					*//*************************  결제보안 강화 2016-05-18 END ****************************//*

					if("0000".equals(resultMap.get("resultCode")) && secureSignature.equals(resultMap.get("authSignature")) ){	//결제보안 강화 2016-05-18
					   *//*****************************************************************************
				       * 여기에 가맹점 내부 DB에 결제 결과를 반영하는 관련 프로그램 코드를 구현한다.
					   
						 [중요!] 승인내용에 이상이 없음을 확인한 뒤 가맹점 DB에 해당건이 정상처리 되었음을 반영함
								  처리중 에러 발생시 망취소를 한다.
				       ******************************************************************************//*
						
						//결과정보
						resultMap.get("resultCode"); //결과코드 0000이 성공
						resultMap.get("resultMsg"); //결과내용
					} else {
						successYn = "N";
						resultMap.get("resultCode"); //결과코드
						message = resultMap.get("resultMsg"); //결과내용
						
						//결제보안키가 다른 경우
						if (!secureSignature.equals(resultMap.get("authSignature")) && "0000".equals(resultMap.get("resultCode"))) {
							//결과정보
							message = "데이터 위변조 체크 실패";
							
							//망취소
							if ("0000".equals(resultMap.get("resultCode"))) {
								throw new Exception("데이터 위변조 체크 실패");
							}
						}
					}
						
					//공통 부분만 - DB저장 필요한 것들 값 가져다가 하기
					resultMap.get("tid"); //거래번호
					resultMap.get("payMethod"); //결제방법(지불수단)
					resultMap.get("TotPrice"); //결제완료금액
					resultMap.get("MOID"); //주문 번호
					resultMap.get("applDate"); //승인날짜
					resultMap.get("applTime"); //승인시간
					if("HPP".equals(resultMap.get("payMethod"))){ //휴대폰
						resultMap.get("HPP_Corp"); //통신사
						resultMap.get("payDevice"); //결제장치
						resultMap.get("HPP_Num"); //휴대폰번호
					}else{//카드
						int  quota=Integer.parseInt(resultMap.get("CARD_Quota"));
						if(resultMap.get("EventCode")!=null){
							resultMap.get("EventCode"); //이벤트 코드
						}
						
						resultMap.get("CARD_Num"); //카드번호
						resultMap.get("applNum"); //승인번호
						resultMap.get("CARD_Quota"); //할부기간
						
						if("1".equals(resultMap.get("CARD_Interest")) || "1".equals(resultMap.get("EventCode"))){
							System.out.println("무이자"); //할부 유형
						}else if(quota > 0 && !"1".equals(resultMap.get("CARD_Interest"))){
							System.out.println("유이자 *유이자로 표시되더라도 EventCode 및 EDI에 따라 무이자 처리가 될 수 있습니다."); //할부 유형
						}
							
						if("1".equals(resultMap.get("point"))){
							System.out.println("사용"); //포인트 사용 여부
						}else{
							System.out.println("미사용"); //포인트 사용 여부
						}
						resultMap.get("CARD_Code"); //카드 종류
						resultMap.get("CARD_BankCode"); //카드 발급사
						
						if(resultMap.get("OCB_Num")!=null && resultMap.get("OCB_Num") != ""){
							resultMap.get("OCB_Num"); //카드번호
							resultMap.get("OCB_SaveApplNum"); //OK CASHBAG 적립 승인번호
							resultMap.get("OCB_PayPrice"); //포인트지불금액
							
						}
						if(resultMap.get("GSPT_Num")!=null && resultMap.get("GSPT_Num") != ""){
							resultMap.get("OCB_Num"); //카드번호
							resultMap.get("GSPT_Remains"); //GS&Point 잔여한도
							resultMap.get("GSPT_ApplPrice"); //승인금액
						}
						
						if(resultMap.get("UNPT_CardNum")!=null && resultMap.get("UNPT_CardNum") != ""){
							resultMap.get("OCB_Num"); //카드번호
							resultMap.get("UPNT_UsablePoint"); //U-Point 가용포인트
							resultMap.get("UPNT_PayPrice"); //U-Point 포인트지불금액
						}
				    }
					
					// 수신결과를 파싱후 resultCode가 "0000"이면 승인성공 이외 실패
					// 가맹점에서 스스로 파싱후 내부 DB 처리 후 화면에 결과 표시

					// payViewType을 popup으로 해서 결제를 하셨을 경우
					// 내부처리후 스크립트를 이용해 opener의 화면 전환처리를 하세요

					//throw new Exception("강제 Exception");
					
					
					//주문처리 시작
					
					
					
				} catch (Exception ex) {
					//####################################
					// 실패시 처리(***가맹점 개발수정***)
					//####################################

					//---- db 저장 실패시 등 예외처리----//
					System.out.println(ex);

					//#####################
					// 망취소 API
					//#####################
					String netcancelResultString = httpUtil.processHTTP(authMap, netCancel);	// 망취소 요청 API url(고정, 임의 세팅 금지)

					System.out.println("## 망취소 API 결과 ##");

					// 취소 결과 확인
					System.out.println(netcancelResultString.replaceAll("<", "&lt;").replaceAll(">", "&gt;"));
					
					successYn = "N";
					message = "실패"; //결과내용
				}
			}else{
				//#############
				// 인증 실패시
				//#############
				successYn = "N";
				message = "실패"; //결과내용
				
				System.out.println(paramMap.toString());
			}
			
			model.addAttribute("successYn",successYn);
			model.addAttribute("message",message);
		}
				
		//암호화,복호화
		Encryption encryption = new Encryption();
		//이지웰 기능
		EzwelFunc ezwelFunc = new EzwelFunc();
		*//*
		if(!EgovUserDetailsHelper.isAuthenticated()) {
			return "redirect:/index.do";
		}
		*//*
		LoginVO user = (LoginVO)EgovUserDetailsHelper.getAuthenticatedUser();
		MberVO mber = new MberVO();
		if(cartno == null || cartno.size() == 0) {
			LOGGER.info("cartno 값이 들어 오지 않음. 사용자ID : " + user.getId());
			return "modoo/common/error/error";
		}
		//ezwel포인트조회
		if(EZWEL_GROUP_ID.equals(user.getGroupId())){
			mber.setEsntlId(user.getUniqId());
			mber = mberService.selectMber(mber);
			if("Y".equals(mber.getPointYn())){
			ezwelPoint=ezwelFunc.ezwelPointSearch(user.getUserKey(),user.getClientCd());
			*//*ezwelPoint=new BigDecimal(200);*//*
			model.addAttribute("ezwelPoint",ezwelPoint);
			}
		}

		//카드 - 빌링
		*//*
		CreditCardVO card = new CreditCardVO();
		card.setEsntlId(user.getUniqId());
		card.setBassUseAt("Y");
		card.setUseAt("Y");
		CreditCardVO bassUseCard = cardService.selectCard(card);//기본사용카드
		if(bassUseCard !=null){
			String decCardNo = encryption.decryption(bassUseCard.getCardNo());
			System.out.println(decCardNo.length()+"@@");
			if(decCardNo.length()==15){
				bassUseCard.setLastCardNo(decCardNo.substring(12,15));
			}else{
				bassUseCard.setLastCardNo(decCardNo.substring(12,16));
			}
		}
		model.addAttribute("bassUseCard",bassUseCard);
			
		card.setBassUseAt("N");
		card.setUseAt("Y");
		List<CreditCardVO> cardList = cardService.selectCardList(card);//카드리스트
		if(cardList!=null){
		for(CreditCardVO cvo : cardList){
			String decCardNoList = encryption.decryption(cvo.getCardNo());
			if(decCardNoList.length()==15){
				cvo.setLastCardNo(decCardNoList.substring(12,15));
			}else{
				cvo.setLastCardNo(decCardNoList.substring(12,16));
			}
		}
		model.addAttribute("cardList",cardList);
		}
		*//*
		//가장 최신 주문 정보
		OrderVO searchVO = new OrderVO();
		searchVO.setOrdrrId(user.getId());
		searchVO=orderService.selectOrder(searchVO);
		String ordEmail = null;
		String ordTelno = null;
		if(searchVO!=null){
            if(StringUtils.isNotEmpty(searchVO.getOrdrrEmail())){
                ordEmail=searchVO.getOrdrrEmail();
                model.addAttribute("ordEmail", ordEmail);
            }else if(StringUtils.isEmpty(searchVO.getOrdrrEmail())){
            	 if(StringUtils.isNotEmpty(user.getEmail()) && user.getEmail().contains("@")){
                     ordEmail=user.getEmail();
                 }else{
                     ordEmail=" @ ";
                 }
                model.addAttribute("ordEmail",ordEmail);
            }
            if(StringUtils.isNotEmpty(searchVO.getTelno())){
                ordTelno=searchVO.getTelno();
                model.addAttribute("ordTelno",ordTelno);
            }else if(StringUtils.isEmpty(searchVO.getTelno())){
                ordTelno=" - - ";
                model.addAttribute("ordTelno",ordTelno);
            }
        }else{
            if(StringUtils.isNotEmpty(user.getEmail()) && user.getEmail().contains("@")){
                ordEmail=user.getEmail();
            }else{
                ordEmail=" @ ";
            }
            model.addAttribute("ordEmail",ordEmail);
            ordTelno=" - - ";
            model.addAttribute("ordTelno",ordTelno);
        }
		//촤근 배송지 목록
		DlvyAdresVO dlvyAdres = new DlvyAdresVO();
		dlvyAdres.setDlvyUserId(user.getId());
		HashMap<String, Object> dlvyList = new HashMap<>();
		List<DlvyAdresVO> recentDlvyAdresList = dlvyAdresService.selectRecentUseDlvyAdres(dlvyAdres);
		dlvyList.put("recentDlvyAdresList", recentDlvyAdresList);
		//기본배송지
		dlvyAdres.setDlvyUserId(user.getId());
		DlvyAdresVO bassDlvyAdres = dlvyAdresService.selectBassDlvyAdres(dlvyAdres);
		dlvyList.put("bassDlvy", bassDlvyAdres);
		model.addAttribute("dlvyList", dlvyList);
		
		
		CartVO searchCartVO = new CartVO();
		 if("Y".equals(req.getParameter("isCart"))){
			 searchCartVO.setCartAddAt("Y");
		 }
		searchCartVO.setSearchOrdrrId(user.getId());
		searchCartVO.setSearchCartNoList(cartno);
		
		List<?> cartList = cartService.selectCartList(searchCartVO);
		
		//쿠폰 한 개 상품체크
		Boolean isCoupon = false;
		Iterator ctIter = cartList.iterator();
		while(ctIter.hasNext()==true){
			EgovMap ct = (EgovMap)ctIter.next();
			if("CPN".equals(ct.get("goodsKndCode")) &&cartList.size()==1){
				isCoupon=true;
				//이벤트 상품 배송지 체크 
				*//*
				if("GOODS_00000000002002".equals(ct.get("goodsId"))||"GOODS_00000000002012".equals(ct.get("goodsId"))||"GOODS_00000000002021".equals(ct.get("goodsId"))){
					isCoupon=false;
				}
				*//*
			}
		}
		model.addAttribute("isCoupon", isCoupon);
		
		
		// 장바구니에 목록이 없을때 
		if(cartList == null || cartList.size() == 0) {
			String noStr = "";
			for(Integer no : cartno) {
				noStr += no + ",";
			}
			LOGGER.info("이미 주문이 되었거나 주문자가 등록하지 않은 목록을 요청 한 것으로 보임.  사용자ID : " + user.getId() + " (" + noStr + ")" + ", cartno : " + cartno);
			return "modoo/common/error/error";
		}
		model.addAttribute("cartList", cartList);

		*//*
		order.setSbscrptCycleSeCode(goods.getSbscrptCycleSeCode()); //구독주기
		*//*

		return "modoo/front/shop/order/goodsOrder";
	}*/
	
	/**
	 * 주문 처리(이니시스)
	 * @param order
	 * @param redirectAttributes
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/embed/shop/goods/pgcall.do", method = RequestMethod.POST)
	public String pgcall(HttpServletRequest req, OrderGroupVO orderGroup, final Model model) throws Exception {
		model.addAttribute("orderGroup", orderGroup);
		
		//상품정보 - 해당 부분은 가져와야 함
		String goodName = "";
		String subscriptionAt = "N";
		int price = 0;
		for(int i = 0; i < orderGroup.getOrderList().size(); i++) {
			GoodsVO goodsVO = new GoodsVO();
			OrderVO orderVO = orderGroup.getOrderList().get(i);
			
			goodsVO.setGoodsId(orderVO.getGoodsId());
			GoodsVO goods = goodsService.selectGoods(goodsVO);
			
			if(i == 0) {
				goodName = goods.getGoodsNm();
				if("SBS".equals(orderVO.getOrderKndCode())) {
					subscriptionAt = "Y";
				}
			}
			price += goods.getGoodsPc().intValue() * orderGroup.getOrderList().get(i).getOrderCo();
			
		}
		
		if(orderGroup.getOrderList().size() > 1) {
			goodName += " 외 " + (orderGroup.getOrderList().size() -1) + "건";
		}
		
		model.addAttribute("price", price);
		model.addAttribute("goodname", goodName);
		model.addAttribute("subscriptionAt", subscriptionAt);
		
		//이니시스 - 일반결제창 모듈 생성
		String mid = EgovProperties.getProperty("INICIS.mid");
		String iv = EgovProperties.getProperty("INICIS.iv");
		String key = EgovProperties.getProperty("INICIS.key");
		String signKey = EgovProperties.getProperty("INICIS.signKey");
		//이니시스 - 구독결제창 모듈 생성
		if("Y".equals(subscriptionAt)) {
			mid = EgovProperties.getProperty("INICIS.subscription.mid");
			iv = EgovProperties.getProperty("INICIS.subscription.iv");
			key = EgovProperties.getProperty("INICIS.subscription.key");
			signKey = EgovProperties.getProperty("INICIS.subscription.signKey");
		}
		String timestamp = SignatureUtil.getTimestamp();
		String oid = mid + "_" + timestamp;
		String mKey = SignatureUtil.hash(signKey, "SHA-256");
		
		model.addAttribute("mid", mid);
		model.addAttribute("iv", iv);
		model.addAttribute("key", key);
		model.addAttribute("signKey", signKey);
		model.addAttribute("timestamp", timestamp);
		model.addAttribute("oid", oid);
		model.addAttribute("mKey", mKey);

		// 2.signature 생성
		Map<String, String> signParam = new HashMap<String, String>();

		signParam.put("oid", oid); 						// 필수
		signParam.put("price", Integer.toString(price));// 필수
		signParam.put("timestamp",	timestamp);			// 필수
		
		// signature 데이터 생성 (모듈에서 자동으로 signParam을 알파벳 순으로 정렬후 NVP 방식으로 나열해 hash)
		String signature = SignatureUtil.makeSignature(signParam);
		model.addAttribute("signature", signature);
		
		String protocol = "Y".equals(EgovProperties.getProperty("Globals.SslAt")) ? "https://" : "http://";
		String siteDomain = protocol + SiteDomainHelper.getDomain() + "/INIpayStdSample"; //가맹점 도메인 입력
		model.addAttribute("siteDomain", siteDomain);
		
		//주문자 정보
		String ordName = orderGroup.getOrdName();
		String ordTelno = orderGroup.getOrdTelno1() + "-" + orderGroup.getOrdTelno2() + "-" + orderGroup.getOrdTelno3();
		String ordEmail = orderGroup.getOrdEmail1() +"@"+ orderGroup.getOrdEmail2();
		
		model.addAttribute("ordName", ordName);
		model.addAttribute("ordTelno", ordTelno);
		model.addAttribute("ordEmail", ordEmail);
		
		//모바일 여부 확인 
		String mobileYn = "";
		String userAgent = req.getHeader("User-Agent").toUpperCase();
		
	    if(userAgent.indexOf("MOBILE") > -1) {
	        if(userAgent.indexOf("PHONE") == -1){
	        	mobileYn = "Y"; 
	        }
		else{
			mobileYn = "Y"; 
			}
	    }else{
	    	mobileYn = "N";
	    }
	    model.addAttribute("mobileYn", mobileYn);
		
		return "modoo/front/shop/order/pgcall";
	}
	
	/**
	 * 주문 처리 - 일반결제 완료 후
	 * @param order
	 * @param redirectAttributes
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/shop/goods/sendOrderSingle.json", method = RequestMethod.POST)
	@ResponseBody
	public JsonResult sendOrderSingle(HttpServletRequest req, OrderGroupVO orderGroup, final Model model) throws Exception {
		JsonResult jsonResult = new JsonResult();
		if(!EgovUserDetailsHelper.isAuthenticated()) {
			jsonResult.setRedirectUrl("/index.do");
		}
		
		LoginVO user = (LoginVO)EgovUserDetailsHelper.getAuthenticatedUser();
		
		DlvyAdresVO dlvyAdres = orderGroup.getDlvyInfo();
		
		jsonResult.setSuccess(true);
		
		if(StringUtils.isEmpty(dlvyAdres.getDlvyZip())) { // 우편번호
			LOGGER.info("우편번호가 없음. 사용자ID : " + user.getId());
			jsonResult.setSuccess(false);
			jsonResult.setMessage("주문정보가 올바르지 않습니다.");
		}else if(StringUtils.isEmpty(dlvyAdres.getDlvyAdres())) {
			LOGGER.info("배송기본주소가 없음. 사용자ID : " + user.getId());
			jsonResult.setSuccess(false);
			jsonResult.setMessage("주문정보가 올바르지 않습니다.");
		}else if(StringUtils.isEmpty(dlvyAdres.getDlvyAdresDetail())) {
			LOGGER.info("배송상세주소가 없음. 사용자ID : " + user.getId());
			jsonResult.setSuccess(false);
			jsonResult.setMessage("주문정보가 올바르지 않습니다.");
		}else if(StringUtils.isEmpty(dlvyAdres.getDlvyUserNm())) {
			LOGGER.info("수령자 이름이 없음. 사용자ID : " + user.getId());
			jsonResult.setSuccess(false);
			jsonResult.setMessage("주문정보가 올바르지 않습니다.");
		}else if(StringUtils.isEmpty(dlvyAdres.getTelno1()) || StringUtils.isEmpty(dlvyAdres.getTelno2()) || StringUtils.isEmpty(dlvyAdres.getTelno3())) {
			LOGGER.info("수령자 전화번호가 없음. 사용자ID : " + user.getId());
			jsonResult.setSuccess(false);
			jsonResult.setMessage("주문정보가 올바르지 않습니다.");
		}else if(StringUtils.isEmpty(orderGroup.getOrdTelno1()) || StringUtils.isEmpty(orderGroup.getOrdTelno2()) || StringUtils.isEmpty(orderGroup.getOrdTelno3())) {
			LOGGER.info("주문자 전화번호가 없음. 사용자ID : " + user.getId());
			jsonResult.setSuccess(false);
			jsonResult.setMessage("주문정보가 올바르지 않습니다.");
		}else if(StringUtils.isEmpty(orderGroup.getOrdName())) {
			LOGGER.info("주문자 이름 없음. 사용자ID : " + user.getId());
			jsonResult.setSuccess(false);
			jsonResult.setMessage("주문정보가 올바르지 않습니다.");
		}else if(StringUtils.isEmpty(orderGroup.getOrdEmail1())|| StringUtils.isEmpty(orderGroup.getOrdEmail2())) {
			LOGGER.info("주문자 이메일 없음. 사용자ID : " + user.getId());
			jsonResult.setSuccess(false);
			jsonResult.setMessage("주문정보가 올바르지 않습니다.");
		}
		if("exist".equals(orderGroup.getDlvyInfo().getBassDlvyAt())){
			orderGroup.setNewDlvyInfo(false); //신규 배송주소 아님 
		}else{
			orderGroup.setNewDlvyInfo(true); //신규 배송주소
		}
		
		//수령인연락처
		String recptrTelno = dlvyAdres.getTelno1() + "-" + dlvyAdres.getTelno2() + "-" + dlvyAdres.getTelno3();
		dlvyAdres.setTelno(recptrTelno);
		dlvyAdres.setDlvyUserId(user.getId());
		dlvyAdres.setFrstRegisterId(user.getUniqId());
		String dlvyMssage=""; 
		if(dlvyAdres.getDlvyMssage().contains(",")){
			dlvyMssage = dlvyAdres.getDlvyMssage().substring(0,dlvyAdres.getDlvyMssage().indexOf(','));
		}else{
			dlvyMssage = dlvyAdres.getDlvyMssage();
		}
		dlvyAdres.setDlvyMssage(dlvyMssage);
		orderGroup.setDlvyInfo(dlvyAdres);
		
		//주문자 정보
		String ordTelno = orderGroup.getOrdTelno1() + "-" + orderGroup.getOrdTelno2() + "-" + orderGroup.getOrdTelno3();
		String ordEmail = orderGroup.getOrdEmail1() +"@"+ orderGroup.getOrdEmail2();
		
		if(jsonResult.isSuccess()){
			//할인
			for(OrderVO order: orderGroup.getOrderList()) {
				
				if(!"Y".equals(order.getExprnUseAt())){
					order.setExprnUseAt(null);
				}
				//수령인연락처
				order.setTelno(ordTelno);
				order.setOrdrrSexdstn(user.getSexdstn());
				order.setOrdrrAgrde(user.getAgrde()!=null?user.getAgrde():"0");
				order.setOrdrrEmail(ordEmail);
				order.setDlvyAdres(dlvyAdres.getDlvyAdres());
				order.setDlvyAdresDetail(dlvyAdres.getDlvyAdresDetail());
				order.setDlvyAdresNm(dlvyAdres.getDlvyAdresNm());
				order.setRecptrTelno(dlvyAdres.getTelno());
				order.setDlvyUserNm(dlvyAdres.getDlvyUserNm());
				order.setDlvyMssage(dlvyAdres.getDlvyMssage());
				order.setDlvyZip(dlvyAdres.getDlvyZip());
				// 도서산간지역 검사
				if("island".equals(orderGroup.getIslandChk())){
					order.setIslandDlvyAmount(order.getIslandDlvyAmount());
				}else if("jeju".equals(orderGroup.getIslandChk())){
					order.setIslandDlvyAmount(order.getJejuDlvyAmount());
				}else{
					order.setIslandDlvyAmount(new BigDecimal(0));
				}
				order.setOrderSttusCode("ST02"); //구독요청
				order.setIndvdlinfoAgreAt( (orderGroup.getIndvdlinfoAgreAt()==null?"N":orderGroup.getIndvdlinfoAgreAt()) ); // 개인정보 동의여부
				order.setPurchsCndAgreAt( (orderGroup.getPurchsCndAgreAt()==null?"N":orderGroup.getPurchsCndAgreAt()) ); // 구매조건 동의여부
				
				//적립금은 쿼리에서 계산
				//order.setRsrvmney(rsrvmney);
				order.setOrdrrId(user.getId());
				order.setOrdrrNm(orderGroup.getOrdName());
				order.setFrstRegisterId(user.getUniqId());
			}
			
			HashMap<String, Object> payMap = new HashMap<>();
			//유저정보
			payMap.put("userTelno",ordTelno);
			payMap.put("userEmail",ordEmail);
			payMap.put("userName",orderGroup.getOrdName());
			payMap.put("userId",user.getId());
			
			if(orderGroup.getPayMethod().equals("card")){
				payMap.put("payMethod", orderGroup.getPayMethod());
				
				//카드 빌링프로세스(기존)
				//HashMap<String, Object> resultMap = orderService.insertOrderGroup(orderGroup, payMap);
				//카드사 결제 승인 이후 정보저장
				HashMap<String, Object> resultMap = orderService.insertOrderGroupPayFinish(orderGroup, payMap);
				
				//성공
				if((Boolean)resultMap.get("success")==true){
					jsonResult.put("orderGroupNo", orderGroup.getOrderGroupNo());
					jsonResult.setSuccess(true);
					jsonResult.setRedirectUrl("/shop/goods/orderCmplt.do");
				//실패
				}else if((Boolean)resultMap.get("success")==false){
					LOGGER.error("카드결제, 에러 메세지"+(String)resultMap.get("resultMsg"));
					jsonResult.setSuccess(false);
					jsonResult.setMessage((String)resultMap.get("resultMsg"));
				}
			}
		}	
		if(orderGroup.getOrderList() == null || orderGroup.getOrderList().size() == 0) {
			LOGGER.info("주문 목록이 없음. 사용자ID : " + user.getId());
			jsonResult.setSuccess(false);
		}
		
		return jsonResult;
	}
	
	/**
	 * 주문 처리
	 * @param order
	 * @param redirectAttributes
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/shop/goods/sendOrder.json", method = RequestMethod.POST)
	@ResponseBody
	public JsonResult sendOrder(HttpServletRequest req, OrderGroupVO orderGroup, final Model model) throws Exception {
		JsonResult jsonResult = new JsonResult();
		if(!EgovUserDetailsHelper.isAuthenticated()) {
			jsonResult.setRedirectUrl("/index.do");
		}
		
		/* 모바일 여부 확인 */
		String mobileYn = "";
		String userAgent = req.getHeader("User-Agent").toUpperCase();
		
	    if(userAgent.indexOf("MOBILE") > -1) {
	        if(userAgent.indexOf("PHONE") == -1){
	        	mobileYn = "Y"; 
	        }
		else{
			mobileYn = "Y"; 
			}
	    }else{
	    	mobileYn = "N";
	    }

		//이지웰 기능
		EzwelFunc ezwelFunc = new EzwelFunc();
		
		LoginVO user = (LoginVO)EgovUserDetailsHelper.getAuthenticatedUser();
		
		DlvyAdresVO dlvyAdres = orderGroup.getDlvyInfo();
		CreditCardVO creditCard = orderGroup.getCreditCard();
		//쿠폰 상품 한개일때 배송지 체크 x
		Boolean isCoupon = false;
		
		jsonResult.setSuccess(true);
		
		List<String> odGoodsKndCode = new ArrayList<String>(); 
		Iterator odIter = orderGroup.getOrderList().iterator();
		while(odIter.hasNext()==true){
			OrderVO od = (OrderVO)odIter.next();
			odGoodsKndCode.add(od.getOrderKndCode());
		}
		if(odGoodsKndCode.size()==1 && odGoodsKndCode.contains("CPN")){
			isCoupon=true;
			//이벤트 상품 배송지 체크 
			/*for(OrderVO od : orderGroup.getOrderList()){
				if("GOODS_00000000002002".equals(od.getGoodsId())||"GOODS_00000000002012".equals(od.getGoodsId())||"GOODS_00000000002021".equals(od.getGoodsId())){
					isCoupon=false;
				}
			}*/
		}
		
		
		//카드 유무
		if(creditCard.getCardId()!=null){
			creditCard.setEsntlId(user.getUniqId());
			creditCard = cardService.selectCard(creditCard);
			orderGroup.setCreditCard(creditCard);
		}else{
			LOGGER.info("카드번호가 없다. 사용자ID : " + user.getId());
			jsonResult.setSuccess(false);
			jsonResult.setMessage("주문정보가 올바르지 않습니다.");
		}
		
		if(isCoupon == true){
			if(StringUtils.isEmpty(orderGroup.getOrdName())) {
				LOGGER.info("주문자 이름 없음. 사용자ID : " + user.getId());
				jsonResult.setSuccess(false);
				jsonResult.setMessage("주문정보가 올바르지 않습니다.");
			}else if(StringUtils.isEmpty(orderGroup.getOrdEmail1())|| StringUtils.isEmpty(orderGroup.getOrdEmail2())) {
				LOGGER.info("주문자 이메일 없음. 사용자ID : " + user.getId());
				jsonResult.setSuccess(false);
				jsonResult.setMessage("주문정보가 올바르지 않습니다.");
			}
		}else{
			/*if(dlvyAdres.getDadresNo() != null) {
				dlvyAdres = dlvyAdresService.selectDlvyAdres(dlvyAdres);
				if(dlvyAdres == null || StringUtils.isEmpty(dlvyAdres.getDlvyAdresDetail())) { //상세 주소 있나검사
					LOGGER.info("등록된 주소가 올바르지 않다. 사용자ID : " + user.getId() + " , dadresNo : " + dlvyAdres.getDadresNo());
					jsonResult.setSuccess(false);
					jsonResult.setMessage("주문정보가 올바르지 않습니다.");
				}
			}*/
			if(StringUtils.isEmpty(dlvyAdres.getDlvyZip())) { // 우편번호
				LOGGER.info("우편번호가 없음. 사용자ID : " + user.getId());
				jsonResult.setSuccess(false);
				jsonResult.setMessage("주문정보가 올바르지 않습니다.");
			}else if(StringUtils.isEmpty(dlvyAdres.getDlvyAdres())) {
				LOGGER.info("배송기본주소가 없음. 사용자ID : " + user.getId());
				jsonResult.setSuccess(false);
				jsonResult.setMessage("주문정보가 올바르지 않습니다.");
			}else if(StringUtils.isEmpty(dlvyAdres.getDlvyAdresDetail())) {
				LOGGER.info("배송상세주소가 없음. 사용자ID : " + user.getId());
				jsonResult.setSuccess(false);
				jsonResult.setMessage("주문정보가 올바르지 않습니다.");
			}else if(StringUtils.isEmpty(dlvyAdres.getDlvyUserNm())) {
				LOGGER.info("수령자 이름이 없음. 사용자ID : " + user.getId());
				jsonResult.setSuccess(false);
				jsonResult.setMessage("주문정보가 올바르지 않습니다.");
			}else if(StringUtils.isEmpty(dlvyAdres.getTelno1()) || StringUtils.isEmpty(dlvyAdres.getTelno2()) || StringUtils.isEmpty(dlvyAdres.getTelno3())) {
				LOGGER.info("수령자 전화번호가 없음. 사용자ID : " + user.getId());
				jsonResult.setSuccess(false);
				jsonResult.setMessage("주문정보가 올바르지 않습니다.");
			}else if(StringUtils.isEmpty(orderGroup.getOrdTelno1()) || StringUtils.isEmpty(orderGroup.getOrdTelno2()) || StringUtils.isEmpty(orderGroup.getOrdTelno3())) {
				LOGGER.info("주문자 전화번호가 없음. 사용자ID : " + user.getId());
				jsonResult.setSuccess(false);
				jsonResult.setMessage("주문정보가 올바르지 않습니다.");
			}else if(StringUtils.isEmpty(orderGroup.getOrdName())) {
				LOGGER.info("주문자 이름 없음. 사용자ID : " + user.getId());
				jsonResult.setSuccess(false);
				jsonResult.setMessage("주문정보가 올바르지 않습니다.");
			}else if(StringUtils.isEmpty(orderGroup.getOrdEmail1())|| StringUtils.isEmpty(orderGroup.getOrdEmail2())) {
				LOGGER.info("주문자 이메일 없음. 사용자ID : " + user.getId());
				jsonResult.setSuccess(false);
				jsonResult.setMessage("주문정보가 올바르지 않습니다.");
			}
			if("exist".equals(orderGroup.getDlvyInfo().getBassDlvyAt())){
				orderGroup.setNewDlvyInfo(false); //신규 배송주소 아님 
			}else{
				orderGroup.setNewDlvyInfo(true); //신규 배송주소
			}
			
			//수령인연락처
			String recptrTelno = dlvyAdres.getTelno1() + "-" + dlvyAdres.getTelno2() + "-" + dlvyAdres.getTelno3();
			dlvyAdres.setTelno(recptrTelno);
			dlvyAdres.setDlvyUserId(user.getId());
			dlvyAdres.setFrstRegisterId(user.getUniqId());
			String dlvyMssage=""; 
			if(dlvyAdres.getDlvyMssage().contains(",")){
				dlvyMssage = dlvyAdres.getDlvyMssage().substring(0,dlvyAdres.getDlvyMssage().indexOf(','));
			}else{
				dlvyMssage = dlvyAdres.getDlvyMssage();
			}
			dlvyAdres.setDlvyMssage(dlvyMssage);
			orderGroup.setDlvyInfo(dlvyAdres);
		}
		//쿠폰개수체크
		int couponChk = 0;
		StringBuffer couponGoodsNm = new StringBuffer();
		for(OrderVO order: orderGroup.getOrderList()) {
			//쿠폰개수확인
			if("CPN".equals(order.getOrderKndCode())){
				GoodsCouponVO goodsCoupon = new GoodsCouponVO();
				goodsCoupon.setGoodsId(order.getGoodsId());
				goodsCoupon.setOrderCo(order.getOrderCo());
				List<GoodsCouponVO> goodsCouponList = goodsCouponService.selectSleGoodsCoupon(goodsCoupon);
				if(goodsCouponList.size()<order.getOrderCo()){
					GoodsVO goods = new GoodsVO();
					goods.setGoodsId(order.getGoodsId());
					goods = goodsService.selectGoods(goods);
					couponGoodsNm.append(goods.getGoodsNm());
					couponChk++;
				}
			}
		}
		
		if(couponChk>0){
			LOGGER.info("발급할 쿠폰번호가없거나 부족함 상품:"+couponGoodsNm);
			jsonResult.setSuccess(false);
			jsonResult.setMessage("발급 가능한 쿠폰의 갯수가 부족합니다.판매자에게 문의해주세요. 상품 : "+couponGoodsNm);
		}
		
		//주문자 정보
		String ordTelno = orderGroup.getOrdTelno1() + "-" + orderGroup.getOrdTelno2() + "-" + orderGroup.getOrdTelno3();
		String ordEmail = orderGroup.getOrdEmail1() +"@"+ orderGroup.getOrdEmail2();
		
		if(jsonResult.isSuccess()){
			//할인
			for(OrderVO order: orderGroup.getOrderList()) {
				
				if(!"Y".equals(order.getExprnUseAt())){
					order.setExprnUseAt(null);
				}
				//수령인연락처
				order.setTelno(ordTelno);
				order.setOrdrrSexdstn(user.getSexdstn());
				order.setOrdrrAgrde(user.getAgrde()!=null?user.getAgrde():"0");
				order.setOrdrrEmail(ordEmail);
				order.setDlvyAdres(dlvyAdres.getDlvyAdres());
				order.setDlvyAdresDetail(dlvyAdres.getDlvyAdresDetail());
				order.setDlvyAdresNm(dlvyAdres.getDlvyAdresNm());
				order.setRecptrTelno(dlvyAdres.getTelno());
				order.setDlvyUserNm(dlvyAdres.getDlvyUserNm());
				order.setDlvyMssage(dlvyAdres.getDlvyMssage());
				order.setDlvyZip(dlvyAdres.getDlvyZip());
				// 도서산간지역 검사
				if("island".equals(orderGroup.getIslandChk())){
					order.setIslandDlvyAmount(order.getIslandDlvyAmount());
				}else if("jeju".equals(orderGroup.getIslandChk())){
					order.setIslandDlvyAmount(order.getJejuDlvyAmount());
				}else{
					order.setIslandDlvyAmount(new BigDecimal(0));
				}
				order.setOrderSttusCode("ST02"); //구독요청
				order.setIndvdlinfoAgreAt( (orderGroup.getIndvdlinfoAgreAt()==null?"N":orderGroup.getIndvdlinfoAgreAt()) ); // 개인정보 동의여부
				order.setPurchsCndAgreAt( (orderGroup.getPurchsCndAgreAt()==null?"N":orderGroup.getPurchsCndAgreAt()) ); // 구매조건 동의여부
				
				//적립금은 쿼리에서 계산
				//order.setRsrvmney(rsrvmney);
				order.setOrdrrId(user.getId());
				order.setOrdrrNm(orderGroup.getOrdName());
				order.setFrstRegisterId(user.getUniqId());
			}
			
			HashMap<String, Object> payMap = new HashMap<>();
			//유저정보
			payMap.put("userTelno",ordTelno);
			payMap.put("userEmail",ordEmail);
			payMap.put("userName",orderGroup.getOrdName());
			payMap.put("userId",user.getId());
			payMap.put("mobileYn",mobileYn); //모바일Y/N
			//카드정보
			payMap.put("cardNo", creditCard.getCardNo());
			payMap.put("brthdy", creditCard.getBrthdy());
			payMap.put("cardPassword", creditCard.getCardPassword());
			payMap.put("cardUsgpd", creditCard.getCardUsgpd());
			payMap.put("cardId", creditCard.getCardId());
			//이지웰 정보
			/*
			payMap.put("userKey",user.getUserKey());
			payMap.put("clientCd",user.getClientCd());
			payMap.put("rcverNm",dlvyAdres.getDlvyUserNm());//수령인이름
			payMap.put("rcverMobile",dlvyAdres.getTelno());//수령인 번호
			payMap.put("dlvrPost",dlvyAdres.getDlvyZip());//배송지 우편번호
			payMap.put("dlvrAddr1",dlvyAdres.getDlvyAdres());//배송지 기본주소
			payMap.put("dlvrAddr2",dlvyAdres.getDlvyAdresDetail());////배송지 상세주소
			payMap.put("orderRequest",dlvyAdres.getDlvyMssage());//주문시 요청사항
			*/
			//결제 방식(card,point,both)
			//포인트결제
			if(orderGroup.getPayMethod().equals("point")){
				//포인트가 0원이 아니면 실행
				/*
				ezwelPoint=ezwelFunc.ezwelPointSearch(user.getUserKey(),user.getClientCd());
				if(ezwelPoint.compareTo(BigDecimal.ZERO)!=0){
					
					payMap.put("payMethod","point");
					payMap.put("userEmail",ordEmail);
					HashMap<String, Object> resultMap = orderService.insertOrderGroup(orderGroup, payMap);
					
					//성공
					if((Boolean)resultMap.get("success")==true){
						jsonResult.put("orderGroupNo", orderGroup.getOrderGroupNo());
						jsonResult.setSuccess(true);
						orderAlimTalk(orderGroup.getOrderGroupNo());
						jsonResult.setRedirectUrl("/shop/goods/orderCmplt.do");
						//실패
					}else if((Boolean)resultMap.get("success")==false){
						LOGGER.error("포인트결제, 에러 메세지"+(String)resultMap.get("resultMsg"));
						jsonResult.setSuccess(false);
						jsonResult.setMessage((String)resultMap.get("resultMsg"));
					}
				}else{
				LOGGER.error("포인트가 없음 포인트:"+ezwelPoint);
				jsonResult.setSuccess(false);
				jsonResult.setMessage("사용할 수 있는 포인트가 없습니다.");
				}	
				*/
			//카드,포인트 결제
			}else if(orderGroup.getPayMethod().equals("both")){
				
					payMap.put("payMethod","both");
					payMap.put("userEmail",ordEmail);
					payMap.put("useEzwelPoint",ezwelPoint);
					HashMap<String, Object> resultMap = orderService.insertOrderGroup(orderGroup, payMap);
					
					//성공
					if((Boolean)resultMap.get("success")==true){
						jsonResult.put("orderGroupNo", orderGroup.getOrderGroupNo());
						jsonResult.setSuccess(true);
						orderAlimTalk(orderGroup.getOrderGroupNo());
						jsonResult.setRedirectUrl("/shop/goods/orderCmplt.do");
					//실패
					}else if((Boolean)resultMap.get("success")==false){
						LOGGER.error("양쪽결제, 에러 메세지"+(String)resultMap.get("resultMsg"));
						jsonResult.setSuccess(false);
						jsonResult.setMessage((String)resultMap.get("resultMsg"));
					}
			//카드결제
			}else if(orderGroup.getPayMethod().equals("card")){
				if(EZWEL_GROUP_ID.equals(user.getGroupId())){
					payMap.put("isEzwel", true);
				}else{
					payMap.put("isEzwel", false);
				}
				//카드결제 빌링정보 넣기
				payMap.put("payMethod","card");
				//카드 빌링프로세스
				HashMap<String, Object> resultMap = orderService.insertOrderGroup(orderGroup, payMap);
				//성공
				if((Boolean)resultMap.get("success")==true){
					jsonResult.put("orderGroupNo", orderGroup.getOrderGroupNo());
					jsonResult.setSuccess(true);
					//알림톡 생성 시 해당 주석 삭제 후 템플릿에 맞춰서 개발하기
					//orderAlimTalk(orderGroup.getOrderGroupNo());
					jsonResult.setRedirectUrl("/shop/goods/orderCmplt.do");
				//실패
				}else if((Boolean)resultMap.get("success")==false){
					LOGGER.error("카드결제, 에러 메세지"+(String)resultMap.get("resultMsg"));
					jsonResult.setSuccess(false);
					jsonResult.setMessage((String)resultMap.get("resultMsg"));
				}
			}
		}	
		if(orderGroup.getOrderList() == null || orderGroup.getOrderList().size() == 0) {
			LOGGER.info("주문 목록이 없음. 사용자ID : " + user.getId());
			jsonResult.setSuccess(false);
		}
		
		return jsonResult;
	}

	/**
	 * 주문 처리(이니시스 모듈)
	 * @param orderGroup
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/shop/goods/sendOrderPayment.json", method = RequestMethod.POST)
	@ResponseBody
	public JsonResult sendOrderPayment(HttpServletRequest req, OrderGroupVO orderGroup, final Model model) throws Exception {
		JsonResult jsonResult = new JsonResult();
		if(!EgovUserDetailsHelper.isAuthenticated()) {
			jsonResult.setRedirectUrl("/index.do");
		}
		System.out.println("orderGroup===========" + orderGroup.getOrderGroupNo());
		/* 모바일 여부 확인 */
		String mobileYn = CommonUtils.getMobile(req);
		//이지웰 기능
		EzwelFunc ezwelFunc = new EzwelFunc();

		LoginVO user = (LoginVO)EgovUserDetailsHelper.getAuthenticatedUser();

		DlvyAdresVO dlvyAdres = orderGroup.getDlvyInfo();
		CreditCardVO creditCard = orderGroup.getCreditCard();
		//쿠폰 상품 한개일때 배송지 체크 x
		Boolean isCoupon = false;

		jsonResult.setSuccess(true);

		List<String> odGoodsKndCode = new ArrayList<String>();
		Iterator odIter = orderGroup.getOrderList().iterator();
		while(odIter.hasNext()==true){
			OrderVO od = (OrderVO)odIter.next();
			odGoodsKndCode.add(od.getOrderKndCode());
		}
		if(odGoodsKndCode.size()==1 && odGoodsKndCode.contains("CPN")){
			isCoupon=true;
			//이벤트 상품 배송지 체크
			for(OrderVO od : orderGroup.getOrderList()){
				if("GOODS_00000000002002".equals(od.getGoodsId())||"GOODS_00000000002012".equals(od.getGoodsId())||"GOODS_00000000002021".equals(od.getGoodsId())){
					isCoupon=false;
				}
			}
		}


		if(isCoupon == true){
			if(StringUtils.isEmpty(orderGroup.getOrdName())) {
				LOGGER.info("주문자 이름 없음. 사용자ID : " + user.getId());
				jsonResult.setSuccess(false);
				jsonResult.setMessage("주문정보가 올바르지 않습니다.");
			}else if(StringUtils.isEmpty(orderGroup.getOrdEmail1())|| StringUtils.isEmpty(orderGroup.getOrdEmail2())) {
				LOGGER.info("주문자 이메일 없음. 사용자ID : " + user.getId());
				jsonResult.setSuccess(false);
				jsonResult.setMessage("주문정보가 올바르지 않습니다.");
			}
		}else{
			if(StringUtils.isEmpty(dlvyAdres.getDlvyZip())) { // 우편번호
				LOGGER.info("우편번호가 없음. 사용자ID : " + user.getId());
				jsonResult.setSuccess(false);
				jsonResult.setMessage("주문정보가 올바르지 않습니다.");
			}else if(StringUtils.isEmpty(dlvyAdres.getDlvyAdres())) {
				LOGGER.info("배송기본주소가 없음. 사용자ID : " + user.getId());
				jsonResult.setSuccess(false);
				jsonResult.setMessage("주문정보가 올바르지 않습니다.");
			}else if(StringUtils.isEmpty(dlvyAdres.getDlvyAdresDetail())) {
				LOGGER.info("배송상세주소가 없음. 사용자ID : " + user.getId());
				jsonResult.setSuccess(false);
				jsonResult.setMessage("주문정보가 올바르지 않습니다.");
			}else if(StringUtils.isEmpty(dlvyAdres.getDlvyUserNm())) {
				LOGGER.info("수령자 이름이 없음. 사용자ID : " + user.getId());
				jsonResult.setSuccess(false);
				jsonResult.setMessage("주문정보가 올바르지 않습니다.");
			}else if(StringUtils.isEmpty(dlvyAdres.getTelno1()) || StringUtils.isEmpty(dlvyAdres.getTelno2()) || StringUtils.isEmpty(dlvyAdres.getTelno3())) {
				LOGGER.info("수령자 전화번호가 없음. 사용자ID : " + user.getId());
				jsonResult.setSuccess(false);
				jsonResult.setMessage("주문정보가 올바르지 않습니다.");
			}else if(StringUtils.isEmpty(orderGroup.getOrdTelno1()) || StringUtils.isEmpty(orderGroup.getOrdTelno2()) || StringUtils.isEmpty(orderGroup.getOrdTelno3())) {
				LOGGER.info("주문자 전화번호가 없음. 사용자ID : " + user.getId());
				jsonResult.setSuccess(false);
				jsonResult.setMessage("주문정보가 올바르지 않습니다.");
			}else if(StringUtils.isEmpty(orderGroup.getOrdName())) {
				LOGGER.info("주문자 이름 없음. 사용자ID : " + user.getId());
				jsonResult.setSuccess(false);
				jsonResult.setMessage("주문정보가 올바르지 않습니다.");
			}else if(StringUtils.isEmpty(orderGroup.getOrdEmail1())|| StringUtils.isEmpty(orderGroup.getOrdEmail2())) {
				LOGGER.info("주문자 이메일 없음. 사용자ID : " + user.getId());
				jsonResult.setSuccess(false);
				jsonResult.setMessage("주문정보가 올바르지 않습니다.");
			}
			if("exist".equals(orderGroup.getDlvyInfo().getBassDlvyAt())){
				orderGroup.setNewDlvyInfo(false); //신규 배송주소 아님
			}else{
				orderGroup.setNewDlvyInfo(true); //신규 배송주소
			}

			//수령인연락처
			String recptrTelno = dlvyAdres.getTelno1() + "-" + dlvyAdres.getTelno2() + "-" + dlvyAdres.getTelno3();
			dlvyAdres.setTelno(recptrTelno);
			dlvyAdres.setDlvyUserId(user.getId());
			dlvyAdres.setFrstRegisterId(user.getUniqId());
			String dlvyMssage="";
			if(dlvyAdres.getDlvyMssage().contains(",")){
				dlvyMssage = dlvyAdres.getDlvyMssage().substring(0,dlvyAdres.getDlvyMssage().indexOf(','));
			}else{
				dlvyMssage = dlvyAdres.getDlvyMssage();
			}
			dlvyAdres.setDlvyMssage(dlvyMssage);
			orderGroup.setDlvyInfo(dlvyAdres);
		}
		//쿠폰개수체크
		int couponChk = 0;
		StringBuffer couponGoodsNm = new StringBuffer();
		for(OrderVO order: orderGroup.getOrderList()) {
			//쿠폰개수확인
			if("CPN".equals(order.getOrderKndCode())){
				GoodsCouponVO goodsCoupon = new GoodsCouponVO();
				goodsCoupon.setGoodsId(order.getGoodsId());
				goodsCoupon.setOrderCo(order.getOrderCo());
				List<GoodsCouponVO> goodsCouponList = goodsCouponService.selectSleGoodsCoupon(goodsCoupon);
				if(goodsCouponList.size()<order.getOrderCo()){
					GoodsVO goods = new GoodsVO();
					goods.setGoodsId(order.getGoodsId());
					goods = goodsService.selectGoods(goods);
					couponGoodsNm.append(goods.getGoodsNm());
					couponChk++;
				}
			}
		}

		if(couponChk>0){
			LOGGER.info("발급할 쿠폰번호가없거나 부족함 상품:"+couponGoodsNm);
			jsonResult.setSuccess(false);
			jsonResult.setMessage("발급 가능한 쿠폰의 갯수가 부족합니다.판매자에게 문의해주세요. 상품 : "+couponGoodsNm);
		}

		//주문자 정보
		String ordTelno = orderGroup.getOrdTelno1() + "-" + orderGroup.getOrdTelno2() + "-" + orderGroup.getOrdTelno3();
		String ordEmail = orderGroup.getOrdEmail1() +"@"+ orderGroup.getOrdEmail2();
		//-------------------INIPAY,INIMODULE-----------------------------
		if(jsonResult.isSuccess()){
			//할인
			//장바구니 번호 Arr
			ArrayList<String> cartnoArr = new ArrayList<String>();
			for(OrderVO order: orderGroup.getOrderList()) {
				cartnoArr.add(String.valueOf(order.getCartNo()));
				if(!"Y".equals(order.getExprnUseAt())){
					order.setExprnUseAt(null);
				}
				//수령인연락처
				order.setTelno(ordTelno);
				order.setOrdrrSexdstn(user.getSexdstn());
				order.setOrdrrAgrde(user.getAgrde()!=null?user.getAgrde():"0");
				order.setOrdrrEmail(ordEmail);
				order.setDlvyAdres(dlvyAdres.getDlvyAdres());
				order.setDlvyAdresDetail(dlvyAdres.getDlvyAdresDetail());
				order.setDlvyAdresNm(dlvyAdres.getDlvyAdresNm());
				order.setRecptrTelno(dlvyAdres.getTelno());
				order.setDlvyUserNm(dlvyAdres.getDlvyUserNm());
				order.setDlvyMssage(dlvyAdres.getDlvyMssage());
				order.setDlvyZip(dlvyAdres.getDlvyZip());
				// 도서산간지역 검사
				if("island".equals(orderGroup.getIslandChk())){
					order.setIslandDlvyAmount(order.getIslandDlvyAmount());
				}else if("jeju".equals(orderGroup.getIslandChk())){
					order.setIslandDlvyAmount(order.getJejuDlvyAmount());
				}else{
					order.setIslandDlvyAmount(new BigDecimal(0));
				}
				order.setOrderSttusCode("ST02"); //구독요청
				order.setIndvdlinfoAgreAt( (orderGroup.getIndvdlinfoAgreAt()==null?"N":orderGroup.getIndvdlinfoAgreAt()) ); // 개인정보 동의여부
				order.setPurchsCndAgreAt( (orderGroup.getPurchsCndAgreAt()==null?"N":orderGroup.getPurchsCndAgreAt()) ); // 구매조건 동의여부

				//적립금은 쿼리에서 계산
				//order.setRsrvmney(rsrvmney);
				order.setOrdrrId(user.getId());
				order.setOrdrrNm(orderGroup.getOrdName());
				order.setFrstRegisterId(user.getUniqId());
			}

			HashMap<String, Object> resultMap = orderService.insertPaymentOrderGroup(req, orderGroup);
			resultMap.put("gopayMethod", orderGroup.getGoPayMethod());
			resultMap.put("payMethod", orderGroup.getPayMethod());

			StringBuilder cartnoStr = new StringBuilder();

			for(int i=0;i<cartnoArr.size();i++){
				if(i==0){
					cartnoStr.append("?cartno="+cartnoArr.get(i));
				}
				cartnoStr.append("&cartno="+cartnoArr.get(i));
			}
			resultMap.put("cartnoStr", cartnoStr);

			// 페이지 URL에서 고정된 부분을 적는다.
			// Ex) returnURL이 http://localhost:8080INIpayStdSample/INIStdPayReturn.jsp 라면
			// http://localhost:8080/INIpayStdSample 까지만 기입한다.

			//결제 방식(mobileYn=Y----> pc모듈,mobileYn=N----> 모바일모듈)
			req.getHeader("x-forwarded-proto");
			if(mobileYn.equals(("N"))){
				jsonResult.put("buyername", orderGroup.getOrdName());
				jsonResult.put("buyeremail", ordEmail);
				jsonResult= orderService.paymentInfo(jsonResult,resultMap,req);
			}else{
				String siteDomain = req.getScheme()+"://"+req.getServerName();

				if(req.getServerName().contains("modoo.ai")){
					siteDomain = "https://"+req.getServerName(); //가맹점 도메인 입력
				}

				String requestURL = siteDomain+"/shop/goods/inisisMobileResponse.do"+cartnoStr+"&orderGroupNo="+resultMap.get("oid")+"&payMethod="+orderGroup.getPayMethod()+"&gopayMethod="+orderGroup.getGoPayMethod();
				//String requestURL = siteDomain+"/inisic/INIStdPayReturn.jsp";

				jsonResult.put("P_NEXT_URL", requestURL);
				jsonResult.put("P_INI_PAYMENT",orderGroup.getGoPayMethod() );
				jsonResult.put("requestUrl","https://mobile.inicis.com/smart/payment/" );

				if(!"CARD,BANK,VBANK,MOBILE".contains(orderGroup.getGoPayMethod())){
					jsonResult.put("requestUrl", orderGroup.getGoPayMethod());
				}
				jsonResult.put("P_MID", EgovProperties.getProperty("INICIS.mid"));
				jsonResult.put("P_OID", String.valueOf(resultMap.get("oid")));
				jsonResult.put("P_GOODS", String.valueOf(resultMap.get("goodname")));
				jsonResult.put("P_AMT", String.valueOf(resultMap.get("price")));
				jsonResult.put("P_UNAME", orderGroup.getOrdName());
				jsonResult.put("P_NOTI_URL", "");

				String P_RESERVED="";
				if("CARD".equals(orderGroup.getGoPayMethod())){
					P_RESERVED="&below1000=Y";
				}else if("BANK".equals(orderGroup.getGoPayMethod())){
					P_RESERVED="&d_kwpy=Y&bank_receipt=N";
				}else if("MOBILE".equals(orderGroup.getGoPayMethod())){
					//P_RESERVED="&hpp_corp=[SKT, KTF, LGT]";
					jsonResult.put("P_HPP_METHOD", "2");
				}
				jsonResult.put("P_RESERVED", "twotrs_isp=Y&block_isp=Y&twotrs_isp_noti=N"+P_RESERVED);
			}

		}
		if(orderGroup.getOrderList() == null || orderGroup.getOrderList().size() == 0) {
			LOGGER.info("주문 목록이 없음. 사용자ID : " + user.getId());
			jsonResult.setSuccess(false);
		}

		return jsonResult;
	}

	/**
	 * 모바일 이니시스 response
	 * @param request
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/shop/goods/inisisMobileResponse.do")
	public String inisisMobileResponse(HttpServletRequest request, Model model,RedirectAttributes redirectAttributes) throws Exception{

		LoginVO user = (LoginVO)EgovUserDetailsHelper.getAuthenticatedUser();
		String resultUrl = "";
		OrderVO order = new OrderVO();

		request.setCharacterEncoding("euc-kr");
		/***주문정보**/
		java.math.BigDecimal orderGroupNo = new BigDecimal(request.getParameter("orderGroupNo"));
		String payMethod = request.getParameter("payMethod");
		String goPayMethod = request.getParameter("gopayMethod");
		order.setOrderGroupNo(orderGroupNo);
		order = orderService.selectOrder(order);
		LOGGER.info(order.toString()+"유저정보");
		/********이니시스 결제 통신결과 map**************/
		Map<String,String> paramMap = new Hashtable<String,String>();
		/********이니시스 승인요청 통신결과 map**************/
		Map<String, String> resultMap = new HashMap<String, String>();
		/********결제정보 저장map**************/
		HashMap<String, Object> infoMap = new HashMap<>();
		/********결제결과map**************/
		HashMap<String, Object> payResult = new HashMap<>();
		infoMap.put("orderGroupNo", orderGroupNo);
		infoMap.put("payMethod", payMethod);
		infoMap.put("gopayMethod", goPayMethod);
		infoMap.put("isEzwel", false);
		List<OrderVO> orderList = orderService.selectMyOrderList(order);

		Enumeration elems = request.getParameterNames();
		String temp = "";
		StringBuilder cartnoStr = new StringBuilder();

		while(elems.hasMoreElements())
		{
			temp = (String) elems.nextElement();
			if("cartno".equals(temp)){
				if(StringUtils.isEmpty(cartnoStr)){
					cartnoStr.append("?cartno="+request.getParameter(temp));
				}else{
					cartnoStr.append("&cartno="+request.getParameter(temp));
				}
			}
		}

		String P_STATUS =request.getParameter("P_STATUS");       // 인증 상태
		String P_RMESG1 = request.getParameter("P_RMESG1");      // 인증 결과 메시지
		String P_TID = request.getParameter("P_TID");                   // 인증 거래번호
		String P_REQ_URL = request.getParameter("P_REQ_URL");    // 결제요청 URL
		String P_NOTI = request.getParameter("P_NOTI");              // 기타주문정보
		System.out.println(P_STATUS+"@@");
		System.out.println(P_RMESG1+"@@");
		System.out.println(P_TID+"@@");
		System.out.println(P_REQ_URL+"@@");
		System.out.println(P_NOTI+"@@");
		String P_MID = EgovProperties.getProperty("INICIS.mid");
		// 승인요청을 위한 P_MID, P_TID 세팅

		if("01".equals(P_STATUS)){ // 인증결과가 실패일 경우
			payResult.put("success", false);
			payResult.put("resultMsg",P_RMESG1);
			payResult.put("resultCode", "R004");
			orderService.saveOrderSetleInfo(infoMap, payResult, orderList);
			redirectAttributes.addFlashAttribute("resultMsg",P_RMESG1);
			resultUrl = "redirect:/shop/goods/goodsOrder.do"+cartnoStr;
		}else {

			// 승인요청할 데이터
			P_REQ_URL = P_REQ_URL + "?P_TID=" + P_TID + "&P_MID=" + P_MID;

			HttpClient client = new HttpClient();

			GetMethod method = new GetMethod(P_REQ_URL);

			method.getParams().setParameter(HttpMethodParams.RETRY_HANDLER,
					new DefaultHttpMethodRetryHandler(3, false));

			HashMap<String, String> map = new HashMap<String, String>();

			try {
				int statusCode = client.executeMethod(method);

				if (statusCode != HttpStatus.SC_OK) {
					System.out.print("Method failed: " + method.getStatusLine());
					payResult.put("success", false);
					payResult.put("resultMsg",P_RMESG1);
					payResult.put("resultCode", "R004");
					orderService.saveOrderSetleInfo(infoMap, payResult, orderList);
				}


				// -------------------- 승인결과 수신 -------------------------------------------------

				byte[] responseBody = method.getResponseBody();
				String[] values = new String(responseBody,"euc-kr").split("&");

				HashMap<String,String> returnMap =new HashMap<String,String>();
				for( int x = 0; x < values.length; x++ )
				{
					System.out.print(values[x]);// 승인결과를 출력


					String pair[] = values[x].split("=");
					if (pair.length>1) {
						returnMap.put(pair[0], pair[1]);
					}else{
						returnMap.put(pair[0], "");
					}
				}

				String returnPayMethod = String.valueOf(returnMap.get("P_TYPE"));

				//모바일 결제 성공시
				if("00".equals(returnMap.get("P_STATUS"))){
					/* 모바일 여부 확인 */
					String mobileYn = "";
					String userAgent = request.getHeader("User-Agent").toUpperCase();

					if(userAgent.indexOf("MOBILE") > -1) {
						if(userAgent.indexOf("PHONE") == -1){
							mobileYn = "Y";
						}
						else{
							mobileYn = "Y";
						}
					}else{
						mobileYn = "N";
					}

					/*MberVO mber = new MberVO();
					mber.setEsntlId(user.getUniqId());
					mber = mberService.selectMber(mber);
					System.out.println("이지웰 포인트사용여부"+mber.getPointYn());*/
					/*if(EZWEL_GROUP_ID.equals(user.getGroupId())){
						infoMap.put("isEzwel", true);
						infoMap.put("isExanadu", false);
						java.math.BigDecimal iniPayPrice = new BigDecimal(returnMap.get("P_AMT"));
						infoMap.put("iniCode", returnMap.get("P_TID"));
						infoMap.put("mobileYn", mobileYn);
						infoMap.put("partnerId",PartnerGroupId.EZWEL.getCode());
						*//*****이지웰 결제(both) 이지웰 결제부터 저장까지******//*
						orderService.paymentEzwelPointPay(infoMap);

					}else if(user.getGroupId().equals(PartnerGroupId.BENEPIA.getCode())){
						infoMap.put("isEzwel", true);
						infoMap.put("isExanadu", false);
						java.math.BigDecimal iniPayPrice = new BigDecimal(returnMap.get("P_AMT"));
						infoMap.put("iniCode", returnMap.get("P_TID"));
						infoMap.put("mobileYn", mobileYn);

						infoMap.put("partnerId",PartnerGroupId.BENEPIA.getCode());
						infoMap.put("resultCode",returnMap.get("P_STATUS"));
						infoMap.put("resultMsg",returnMap.get("P_RMESG1"));
						if("both".equals(infoMap.get("payMethod"))){
							KcpInfoVO kcpInfoVO= new KcpInfoVO();

							kcpInfoVO= BenepiaAccountService.setKcpInfo(kcpInfoVO);
							infoMap.put("kcpInfo",kcpInfoVO);
						}
						orderService.paymentBenepiaPointPay(infoMap);

					}else if(user.getGroupId().equals(PartnerGroupId.EXANADU.getCode())){
						infoMap.put("isEzwel", false);
						infoMap.put("isExanadu", true);
						java.math.BigDecimal iniPayPrice = new BigDecimal(returnMap.get("P_AMT"));
						infoMap.put("iniCode", returnMap.get("P_TID"));
						infoMap.put("mobileYn", mobileYn);

						infoMap.put("partnerId",PartnerGroupId.EXANADU.getCode());
						infoMap.put("resultCode",returnMap.get("P_STATUS"));
						infoMap.put("resultMsg",returnMap.get("P_RMESG1"));

						orderService.paymentExanaduPointPay(request, infoMap);
					}else*/
						//카드결제정보
						infoMap.put("isExanadu", false);
						infoMap.put("iniCode",returnMap.get("P_TID"));
						payResult.put("success", true);
						payResult.put("resultMsg","이니시스 결제 정상처리되었습니다.");
						payResult.put("resultCode", returnMap.get("P_STATUS"));
					orderService.saveOrderSetleInfo(infoMap, payResult, orderList);

					/*goodsService.updateGoodsListSleCo(orderGroupNo);//상품 주문 수 증가*/
					orderAlimTalk(orderGroupNo);
					resultUrl="redirect:/shop/goods/orderCmplt.do"+cartnoStr+"&orderGroupNo="+orderGroupNo;

				}else{//인증실패했을때
					payResult.put("success", false);
					payResult.put("resultCode", "R004");
					payResult.put("resultMsg",returnMap.get("P_RMESG1"));
					orderService.saveOrderSetleInfo(infoMap, payResult, orderList);
					redirectAttributes.addFlashAttribute("resultMsg",returnMap.get("P_RMESG1"));
					resultUrl = "redirect:/shop/goods/goodsOrder.do"+cartnoStr;
				}

			} catch (HttpException e) {
				System.out.print("Fatal protocol violation: " + e.getMessage());
				payResult.put("success", false);
				payResult.put("resultMsg","모바일 결제 통신오류"+e.getMessage());
				payResult.put("resultCode", "R004");
				orderService.saveOrderSetleInfo(infoMap, payResult, orderList);
				redirectAttributes.addFlashAttribute("resultMsg","모바일 결제 통신오류"+e.getMessage());
				resultUrl = "redirect:/shop/goods/goodsOrder.do"+cartnoStr;
				e.printStackTrace();
			} catch (IOException e) {
				System.out.print("Fatal transport error: " + e.getMessage());
				payResult.put("success", false);
				payResult.put("resultMsg","모바일 결제 통신오류"+e.getMessage());
				payResult.put("resultCode", "R004");
				orderService.saveOrderSetleInfo(infoMap, payResult, orderList);
				redirectAttributes.addFlashAttribute("resultMsg","모바일 결제 통신오류"+e.getMessage());
				resultUrl = "redirect:/shop/goods/goodsOrder.do"+cartnoStr;
				e.printStackTrace();
			} finally {
				method.releaseConnection();
			}

		}
		/*BenepiaAccountService.removeBenepiaUserInfo();*/
		return resultUrl;
	}

	/**
	 * pc이니시스 response
	 * @param request
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/shop/goods/inisisResponse.do")
	public String inisisResponse(HttpServletRequest request, Model model,RedirectAttributes redirectAttributes) throws Exception{

		LoginVO user = (LoginVO)EgovUserDetailsHelper.getAuthenticatedUser();

		String resultUrl = "";
		OrderVO order = new OrderVO();
		request.setCharacterEncoding("UTF-8");
		/***주문정보**/
		java.math.BigDecimal orderGroupNo = new BigDecimal(request.getParameter("orderGroupNo"));
		String payMethod = request.getParameter("payMethod");
		String gopayMethod = request.getParameter("gopayMethod");
		order.setOrderGroupNo(orderGroupNo);
		order = orderService.selectOrder(order);
		LOGGER.info(order.toString()+"유저정보");
		/********이니시스 결제 통신결과 map**************/
		Map<String,String> paramMap = new Hashtable<String,String>();
		/********이니시스 승인요청 통신결과 map**************/
		Map<String, String> resultMap = new HashMap<String, String>();
		/********결제정보 저장map**************/
		HashMap<String, Object> infoMap = new HashMap<>();
		/********결제결과map**************/
		HashMap<String, Object> payResult = new HashMap<>();
		infoMap.put("orderGroupNo", orderGroupNo);
		infoMap.put("payMethod", payMethod);
		infoMap.put("gopayMethod", gopayMethod);
		infoMap.put("isEzwel", false);
		List<OrderVO> orderList = orderService.selectMyOrderList(order);
		StringBuilder cartnoStr = new StringBuilder();
		try{

			//#############################
			// 인증결과 파라미터 일괄 수신
			//#############################


			Enumeration elems = request.getParameterNames();
			String temp = "";


			while(elems.hasMoreElements())
			{
				temp = (String) elems.nextElement();
				paramMap.put(temp, request.getParameter(temp));
				if("cartno".equals(temp)){
					if(StringUtils.isEmpty(cartnoStr)){
						cartnoStr.append("?cartno="+request.getParameter(temp));
					}else{
						cartnoStr.append("&cartno="+request.getParameter(temp));
					}
				}
			}

			System.out.println("paramMap : "+ paramMap.toString());

			//#####################
			// 인증이 성공일 경우만
			//#####################
			if("0000".equals(paramMap.get("resultCode"))){

				System.out.println("####인증성공/승인요청####");
				System.out.println("<br/>");
				System.out.println("####인증성공/승인요청####");

				//############################################
				// 1.전문 필드 값 설정(***가맹점 개발수정***)
				//############################################

				String mid 		= paramMap.get("mid");						// 가맹점 ID 수신 받은 데이터로 설정
				String signKey	= EgovProperties.getProperty("INICIS.signKey");		// 가맹점에 제공된 키(이니라이트키) (가맹점 수정후 고정) !!!절대!! 전문 데이터로 설정금지
				String timestamp= SignatureUtil.getTimestamp();				// util에 의해서 자동생성
				String charset 	= "UTF-8";								    // 리턴형식[UTF-8,EUC-KR](가맹점 수정후 고정)
				String format 	= "JSON";								    // 리턴형식[XML,JSON,NVP](가맹점 수정후 고정)
				String authToken= paramMap.get("authToken");			    // 취소 요청 tid에 따라서 유동적(가맹점 수정후 고정)
				String authUrl	= paramMap.get("authUrl");				    // 승인요청 API url(수신 받은 값으로 설정, 임의 세팅 금지)
				String netCancel= paramMap.get("netCancelUrl");			 	// 망취소 API url(수신 받은 값으로 설정, 임의 세팅 금지)
				String ackUrl 	= paramMap.get("checkAckUrl");			    // 가맹점 내부 로직 처리후 최종 확인 API URL(수신 받은 값으로 설정, 임의 세팅 금지)
				String merchantData = paramMap.get("merchantData");			// 가맹점 관리데이터 수신

				//#####################
				// 2.signature 생성
				//#####################
				Map<String, String> signParam = new HashMap<String, String>();

				signParam.put("authToken",	authToken);		// 필수
				signParam.put("timestamp",	timestamp);		// 필수

				// signature 데이터 생성 (모듈에서 자동으로 signParam을 알파벳 순으로 정렬후 NVP 방식으로 나열해 hash)
				String signature = SignatureUtil.makeSignature(signParam);

				String price = "";  // 가맹점에서 최종 결제 가격 표기 (필수입력아님)

				// 1. 가맹점에서 승인시 주문번호가 변경될 경우 (선택입력) 하위 연결.
				// String oid = "";

				//#####################
				// 3.API 요청 전문 생성
				//#####################
				Map<String, String> authMap = new Hashtable<String, String>();

				authMap.put("mid"			    ,mid);			  // 필수
				authMap.put("authToken"		,authToken);	// 필수
				authMap.put("signature"		,signature);	// 필수
				authMap.put("timestamp"		,timestamp);	// 필수
				authMap.put("charset"		  ,charset);		// default=UTF-8
				authMap.put("format"		  ,format);		  // default=XML
				//authMap.put("price" 		,price);		    // 가격위변조체크기능 (선택사용)

				System.out.println("##승인요청 API 요청##");

				HttpUtil httpUtil = new HttpUtil();

				try{
					//#####################
					// 4.API 통신 시작
					//#####################

					String authResultString = "";

					authResultString = httpUtil.processHTTP(authMap, authUrl);

					//############################################################
					//5.API 통신결과 처리(***가맹점 개발수정***)
					//############################################################
					System.out.println("## 승인 API 결과 ##");

					String test = authResultString.replace(",", "&").replace(":", "=").replace("\"", "").replace(" ","").replace("\n", "").replace("}", "").replace("{", "");

					//out.println("<pre>"+authResultString.replaceAll("<", "&lt;").replaceAll(">", "&gt;")+"</pre>");


					resultMap = ParseUtil.parseStringToMap(test); //문자열을 MAP형식으로 파싱

					System.out.println("resultMap == " + resultMap);

					/*************************  결제보안 강화 2016-05-18 START ****************************/
					Map<String , String> secureMap = new HashMap<String, String>();
					secureMap.put("mid"			, mid);								//mid
					secureMap.put("tstamp"		, timestamp);						//timestemp
					secureMap.put("MOID"		, resultMap.get("MOID"));			//MOID
					secureMap.put("TotPrice"	, resultMap.get("TotPrice"));		//TotPrice

					// signature 데이터 생성
					String secureSignature = SignatureUtil.makeSignatureAuth(secureMap);
					/*************************  결제보안 강화 2016-05-18 END ****************************/

					if("0000".equals(resultMap.get("resultCode")) && secureSignature.equals(resultMap.get("authSignature"))){	//결제보안 강화 2016-05-18

						System.out.println("-------------이니시스 승인성공---------------");
						/*****************************************************************************
						 * 여기에 가맹점 내부 DB에 결제 결과를 반영하는 관련 프로그램 코드를 구현한다.

						 [중요!] 승인내용에 이상이 없음을 확인한 뒤 가맹점 DB에 해당건이 정상처리 되었음을 반영함
						 처리중 에러 발생시 망취소를 한다.
						 ******************************************************************************/
						/****************이지웰 결제*****************/
				/*		MberVO mber = new MberVO();
						mber.setEsntlId(user.getUniqId());
						mber = mberService.selectMber(mber);
						System.out.println("이지웰 포인트사용여부"+mber.getPointYn());*/
						/*if(EZWEL_GROUP_ID.equals(user.getGroupId())&&"Y".equals(mber.getPointYn())){
							*//* 모바일 여부 확인 *//*
							String mobileYn = "";
							String userAgent = request.getHeader("User-Agent").toUpperCase();

							if(userAgent.indexOf("MOBILE") > -1) {
								if(userAgent.indexOf("PHONE") == -1){
									mobileYn = "Y";
								}
								else{
									mobileYn = "Y";
								}
							}else{
								mobileYn = "N";
							}
							HashMap<String, Object> ezlInfoMap = new HashMap<>();
							infoMap.put("isEzwel", true);
							infoMap.put("isExanadu", false);
							infoMap.put("mobileYn", mobileYn);
							java.math.BigDecimal iniPayPrice = new BigDecimal(resultMap.get("TotPrice"));
							infoMap.put("iniCode", (String)resultMap.get("tid"));
							infoMap.put("partnerId",PartnerGroupId.EZWEL.getCode());
							*//*****이지웰 결제(both) 이지웰 결제부터 저장까지******//*
							orderService.paymentEzwelPointPay(infoMap);
						}else if(user.getGroupId().equals(PartnerGroupId.BENEPIA.getCode())){
							*//* 모바일 여부 확인 *//*
							String mobileYn = "";
							String userAgent = request.getHeader("User-Agent").toUpperCase();

							if(userAgent.indexOf("MOBILE") > -1) {
								if(userAgent.indexOf("PHONE") == -1){
									mobileYn = "Y";
								}
								else{
									mobileYn = "Y";
								}
							}else{
								mobileYn = "N";
							}
							System.out.println(infoMap.toString()+"@@@@@@@@@@@@@");
							HashMap<String, Object> ezlInfoMap = new HashMap<>();
							infoMap.put("isExanadu", false);
							infoMap.put("mobileYn", mobileYn);
							java.math.BigDecimal iniPayPrice = new BigDecimal(resultMap.get("TotPrice"));
							infoMap.put("iniCode", (String)resultMap.get("tid"));
							infoMap.put("partnerId",PartnerGroupId.BENEPIA.getCode());
							//결제리스폰정보
							infoMap.put("resultMsg",resultMap.get("resultMsg"));
							infoMap.put("resultCode",resultMap.get("resultCode"));
							if("both".equals(infoMap.get("payMethod"))){

								KcpInfoVO kcpInfoVO= new KcpInfoVO();
								kcpInfoVO= BenepiaAccountService.setKcpInfo(kcpInfoVO);
								infoMap.put("kcpInfo",kcpInfoVO);
							}

							orderService.paymentBenepiaPointPay(infoMap);
						}else if(user.getGroupId().equals(PartnerGroupId.EXANADU.getCode())){
							System.out.println("----------------------------간편결제 이제너두 ------------------------------");

							*//* 모바일 여부 확인 *//*
							String mobileYn = "";
							String userAgent = request.getHeader("User-Agent").toUpperCase();

							if(userAgent.indexOf("MOBILE") > -1) {
								if(userAgent.indexOf("PHONE") == -1){
									mobileYn = "Y";
								}
								else{
									mobileYn = "Y";
								}
							}else{
								mobileYn = "N";
							}

							infoMap.put("isEzwel", false);
							infoMap.put("isExanadu", true);
							infoMap.put("mobileYn", mobileYn);
							java.math.BigDecimal iniPayPrice = new BigDecimal(resultMap.get("TotPrice"));
							infoMap.put("iniCode", (String)resultMap.get("tid"));
							infoMap.put("partnerId",PartnerGroupId.EXANADU.getCode());
							*//*****이제너두 결제(both) 이지웰 결제부터 저장까지******//*
							orderService.paymentExanaduPointPay(request, infoMap);
						}*/
						/*else{*/
							//카드결제정보
							infoMap.put("isExanadu", false);
							infoMap.put("iniCode",resultMap.get("tid"));
							payResult.put("success", true);
							payResult.put("resultMsg",resultMap.get("resultMsg"));
							payResult.put("resultCode",resultMap.get("resultCode"));
							orderService.saveOrderSetleInfo(infoMap, payResult, orderList);
						/*}*/

						/*goodsService.updateGoodsListSleCo(orderGroupNo);//상품 주문 수 증가*/
						/*orderAlimTalk(orderGroupNo);*/
						resultUrl="redirect:/shop/goods/orderCmplt.do"+cartnoStr+"&orderGroupNo="+orderGroupNo;

						/*if("VBank".equals(resultMap.get("payMethod"))){ //가상계좌

					}else if("DirectBank".equals(resultMap.get("payMethod"))){ //실시간계좌이체

					}else if("iDirectBank".equals(resultMap.get("payMethod"))){ //실시간계좌이체

					}else if("HPP".equals(resultMap.get("payMethod"))){ //휴대폰

					}else if("DGCL".equals(resultMap.get("payMethod"))){//게임문화상품권

						if(!"".equals(resultMap.get("GAMG_Num2"))){
						}
						if(!"".equals(resultMap.get("GAMG_Num3"))){
						}
						if(!"".equals(resultMap.get("GAMG_Num4"))){
						}
						if(!"".equals(resultMap.get("GAMG_Num5"))){
						}
						if(!"".equals(resultMap.get("GAMG_Num6"))){
						}

					}else if("KWPY".equals(resultMap.get("payMethod"))){ //뱅크월렛 카카오

					}else if("Culture".equals(resultMap.get("payMethod"))){//문화 상품권

					}else if("Bookcash".equals(resultMap.get("payMethod"))){//도서문화상품권

					}else if("HPMN".equals(resultMap.get("payMethod"))){//해피머니

					}else{//카드
						int  quota=Integer.parseInt(resultMap.get("CARD_Quota"));

				    }
						 */


					}else{
						throw new Exception("강제 Exception");
					}
				} catch (Exception ex) {
					ex.printStackTrace();
					System.out.println(ex.getMessage());
					//####################################
					// 실패시 처리(***가맹점 개발수정***)
					//####################################
					payResult.put("success", false);
					payResult.put("resultMsg","결제 중 문제가 발생하여 결제가 취소되었습니다.");
					payResult.put("resultCode", "R004");
					orderService.saveOrderSetleInfo(infoMap, payResult, orderList);

					//---- db 저장 실패시 등 예외처리----//
					//#####################
					// 망취소 API
					//#####################
					String netcancelResultString = httpUtil.processHTTP(authMap, netCancel);	// 망취소 요청 API url(고정, 임의 세팅 금지)


					System.out.println("## 망취소 API 결과 ##");

					// 취소 결과 확인
					System.out.println("<p>"+netcancelResultString.replaceAll("<", "&lt;").replaceAll(">", "&gt;")+"</p>");
					redirectAttributes.addFlashAttribute("resultMsg","결제 중 문제가 발생하여 결제가 취소되었습니다.");
					resultUrl = "redirect:/shop/goods/goodsOrder.do"+cartnoStr;
				}

			}else{

				payResult.put("success", false);
				payResult.put("resultMsg",resultMap.get("resultMsg"));
				payResult.put("resultCode", "R004");
				orderService.saveOrderSetleInfo(infoMap, payResult, orderList);
				//#############
				// 인증 실패시
				//#############
				System.out.println("####인증실패####");

				System.out.println("<p>"+resultMap.toString()+"</p>");
				redirectAttributes.addFlashAttribute("resultMsg",resultMap.get("resultMsg"));
				resultUrl = "redirect:/shop/goods/goodsOrder.do"+cartnoStr;

			}

		}catch(Exception e){

			payResult.put("success", false);
			payResult.put("resultMsg",resultMap.get("resultMsg"));
			payResult.put("resultCode", "R004");
			orderService.saveOrderSetleInfo(infoMap, payResult, orderList);
			resultUrl = "redirect:/shop/goods/goodsOrder.do"+cartnoStr;
		}
		/*BenepiaAccountService.removeBenepiaUserInfo();*/
		return resultUrl;
	}
	
	
	@RequestMapping(value = "/shop/goods/orderCmplt.do")
	public String orderCmplt(OrderVO order, Model model,@RequestParam("orderGroupNo") BigDecimal orderGroupNo,HttpServletRequest request) throws Exception {
		if(!EgovUserDetailsHelper.isAuthenticated()) {
			return "redirect:/index.do";
		}
		System.out.println("======================/shop/goods/orderCmplt.do=====================");
		Enumeration params = request.getParameterNames();
		while (params.hasMoreElements()) {
			String name = (String) params.nextElement();
			System.out.print(name + " : " + request.getParameter(name) + "     ");
		}

		if(!EgovUserDetailsHelper.isAuthenticated()) {
			return "redirect:/index.do";
		}
		LoginVO user = (LoginVO)EgovUserDetailsHelper.getAuthenticatedUser();

		Enumeration elems = request.getParameterNames();
		String temp = "";

		while(elems.hasMoreElements())
		{
			temp = (String) elems.nextElement();
			if("cartno".equals(temp) && !("undefined".equals(request.getParameter(temp))) && StringUtils.isNotEmpty(request.getParameter(temp))){
				/*장바구니 N으로 바꾸기 */
				CartVO cart = new CartVO();
				cart.setCartNo(new BigDecimal(request.getParameter(temp)));
				cartService.updateCartClose(cart);
			}

		}



		order.setOrdrrId(user.getId());
		order.setOrderGroupNo(orderGroupNo);
		List<OrderVO> resultList = orderService.selectMyOrderList(order);

		//수강권 상품 쿠폰 발급
		for(int i =0;i<resultList.size();i++){
			OrderVO od =  resultList.get(i);
			if(StringUtils.isNotEmpty(od.getGoods().getVchCode())){
				GoodsCouponVO goodsCouponVO = new GoodsCouponVO();
				goodsCouponVO.setOrdrrId(user.getId());
				goodsCouponVO.setOrderNo(od.getOrderNo());
				goodsCouponVO.setOrdrrId(od.getOrdrrId());
				goodsCouponVO.setCouponAddPd(Integer.valueOf(od.getGoods().getVchValidPd()));
				goodsCouponVO.setCouponPdTy(od.getGoods().getVchPdTy());
				goodsCouponVO.setCouponKndCode(od.getGoods().getVchCode());
				goodsCouponVO.setGoodsId(od.getGoodsId());
				goodsCouponVO.setCouponNm(od.getGoods().getGoodsNm());
				goodsCouponService.insertGoodsCoupon(goodsCouponVO);
			}
		}
		
		model.addAttribute("resultList", resultList);
		return "modoo/front/shop/order/orderCmplt";
	}

	/**
	 * 이니시스 모듈(닫기)--->주문데이터삭제
	 * @throws Exception
	 */
	@RequestMapping(value="/shop/goods/closeInisis.do")
	public String deleteOrder(HttpServletRequest req) throws Exception{

		LoginVO user = (LoginVO)EgovUserDetailsHelper.getAuthenticatedUser();

		OrderVO order = new OrderVO();
		/*order.setOrdrrId(user.getId());*/
		order.setOrderGroupNo(new BigDecimal(req.getParameter("orderGroupNo")));

		orderService.deleteOrderGroup(order);

		return "modoo/front/shop/order/inisis/close";
	}


	/**
	 * 주문완료 비즈톡
	 *  
	 */
	public void orderAlimTalk(java.math.BigDecimal orderGroupNo){
		
		try {
		OrderVO order = new OrderVO();	
		
		LoginVO user = (LoginVO)EgovUserDetailsHelper.getAuthenticatedUser();
		
		order.setOrdrrId(user.getId());
		order.setOrderGroupNo(orderGroupNo);
		//주문목록
		List<OrderVO> resultList = orderService.selectMyOrderList(order);
		
		//주문완료 비즈톡
		java.util.Iterator<OrderVO> iter = resultList.iterator();
		
		String telno = "";
		java.math.BigDecimal price = new BigDecimal(0);
		String productName = "";
		String orderNo = "";
		EgovMap map = new EgovMap();
		//주문상품이름
		if(resultList.size()>1){
			if(resultList.get(0)!=null){
				productName = resultList.get(0).getGoods().getGoodsNm()+"외 "+(resultList.size()-1)+"개의 상품";
			}
		}else if(resultList.size()==1){
			if(resultList.get(0)!=null){
				productName = resultList.get(0).getGoods().getGoodsNm();
			}
		}else{
			throw new Exception();
		}
		
		while(iter.hasNext()){
			OrderVO od = iter.next();
			if("CPN".equals(od.getOrderKndCode())){
				couponAlimtalk(od);
			}
			telno = od.getTelno();
			price = price.add(od.getTotAmount().add(od.getDscntAmount()));
			orderNo = od.getOrderNo();
			map.put("goodsId",od.getGoodsId());
			
			//관리자-업체 알림톡 
			/*[모두의구독]\n주문이 접수되었습니다. 
			 * 주문 확인 후 상품 배송 부탁드립니다.
			 * \n주문일시 : #{ORDERDT}
			 * \n주문번호 : #{ORDERNO}
			 * \n상품명: #{PRODUCTNAME}
			 */
			BiztalkVO bizTalk2 = new BiztalkVO();
		
			bizTalk2.setTmplatCode("template_017");
			CmpnyVO cmpny = cmpnyService.selectOrderCmpnyTelno(map);
			bizTalk2.setRecipient(cmpny.getChargerTelno());
			System.out.println();
			//알림톡 템플릿 내용 조회
			BiztalkVO template = biztalkService.selectBizTalkTemplate(bizTalk2);
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
			String message2 = template.getTmplatCn();
			message2 = message2.replaceAll("#\\{ORDERDT\\}",dateFormat.format(new Date())).replaceAll("#\\{ORDERNO\\}",od.getOrderNo()).replaceAll("#\\{PRODUCTNAME\\}",od.getGoods().getGoodsNm());
			bizTalk2.setMessage(message2);
			biztalkService.sendAlimTalk(bizTalk2);
		}
		
		//주문완료 비즈톡
		BiztalkVO bizTalk = new BiztalkVO();
		
		bizTalk.setRecipient(telno);
		bizTalk.setTmplatCode("template_002");
		/*[모두의구독] 주문이 완료되었습니다.
		* 주문금액 : #{PRICE}
		* 상품명 : #{PRODUCTNAME}
		*/
		//알림톡 템플릿 내용 조회
		BiztalkVO template = biztalkService.selectBizTalkTemplate(bizTalk);
		String message = template.getTmplatCn();
		message = message.replaceAll("#\\{PRICE\\}", String.valueOf(price)+"원").replaceAll("#\\{PRODUCTNAME\\}", productName);
		bizTalk.setMessage(message);
		
		BiztalkVO result = biztalkService.sendAlimTalk(bizTalk);
		
		} catch (Exception e) {
			loggerError(LOGGER, e);
		}
	}
	
	/**
	 * 쿠폰알림톡
	 * @param goodsId
	 */
	public void couponAlimtalk(OrderVO order){
		
		try {
			EgovMap map = new EgovMap();
			GoodsVO goods = new GoodsVO();
			goods.setGoodsId(order.getGoodsId());
			map.put("goodsId", order.getGoodsId());
			goods = goodsService.selectGoods(goods);
			String telno = order.getTelno();
			GoodsCouponVO goodsCoupon = new GoodsCouponVO();
			StringBuilder couponNoInfo = new StringBuilder(); 
			goodsCoupon.setOrderNo(order.getOrderNo());
			List<GoodsCouponVO> odCouponList = goodsCouponService.selectGoodsCouponList(goodsCoupon);
			for(GoodsCouponVO gc : odCouponList){
				couponNoInfo.append(gc.getCouponNo()+" , ");
			}
			BiztalkVO bizTalk = new BiztalkVO();
			bizTalk.setTmplatCode("template_023");
			/*[모두의구독] 주문이 완료되었습니다.
			* 주문금액 : #{PRICE}
			* 상품명 : #{PRODUCTNAME}
			* 쿠폰번호: #{COUPONNO}

			* 이용안내 *
			#{USECN}*/
			BiztalkVO template = biztalkService.selectBizTalkTemplate(bizTalk);
			String message = template.getTmplatCn();
			message = message.replaceAll("#\\{PRICE\\}", String.valueOf(order.getTotAmount().add(order.getDscntAmount()))+"원").replaceAll("#\\{PRODUCTNAME\\}",goods.getGoodsNm()).replaceAll("#\\{COUPONNO\\}",couponNoInfo.toString()).replaceAll("#\\{USECN\\}", goods.getMngrMemo());
			bizTalk.setMessage(message);
			bizTalk.setRecipient(telno);
			biztalkService.sendAlimTalk(bizTalk);
			
			//쿠폰등록알림비즈톡
			GoodsCouponVO searchCoupon = new GoodsCouponVO();
			searchCoupon.setGoodsId(order.getGoodsId());
			int selCouponCnt = goodsCouponService.selectGoodsCouponCnt(searchCoupon);
			
			if(selCouponCnt<10){
				bizTalk.setTmplatCode("template_024");
				/*[모두의구독] 사용자가 구매 가능한 상품의
				쿠폰 갯수가 10개이하입니다.
				쿠폰을 추가로 등록해주세요.

				* 상품명 : #{PRODUCTNAME}	*/
				template = biztalkService.selectBizTalkTemplate(bizTalk);
				CmpnyVO cmpny = cmpnyService.selectOrderCmpnyTelno(map);
				bizTalk.setRecipient(cmpny.getChargerTelno());
				System.out.println(cmpny.getChargerTelno()+"@@");
				message=template.getTmplatCn();
				message = message.replaceAll("#\\{PRODUCTNAME\\}", goods.getGoodsNm());
				bizTalk.setMessage(message);
				System.out.println(bizTalk.toString()+"@@");
				biztalkService.sendAlimTalk(bizTalk);
			} 
		}catch (Exception e) {
			loggerError(LOGGER, e);
		}
		
	}
	
	
	/**
	 * 마이페이지 > 구독중
	 * @param searchVO
	 * @return
	 * @throws Exception
	 */
	
	@ResponseBody
	@RequestMapping(value = "/shop/goods/subscribeNow.json", method=RequestMethod.GET)
	public HashMap<String, Object> getSubscribeNowList(OrderVO searchVO) throws Exception {
		HashMap<String, Object> json = new HashMap<String, Object>();
		boolean isLogin = EgovUserDetailsHelper.isAuthenticated();
				
		if (isLogin) {
			LoginVO loginVO = (LoginVO) EgovUserDetailsHelper.getAuthenticatedUser();
			searchVO.setOrdrrId(loginVO.getId());
		}

		PaginationInfo paginationInfo = new PaginationInfo();

		this.setPagination(paginationInfo, searchVO);
		int totalRecordCount = orderService.selectSubscribeNowListCnt(searchVO); // 목록 카운트
		paginationInfo.setTotalRecordCount(totalRecordCount);

		List<?> orderList = orderService.selectSubscribeNowList(searchVO); // 목록

		json.put("resultList", orderList);
		json.put("paginationInfo", paginationInfo);

		return json;
	}

  	/**
	 * 주문취소 접수
	 * @param searchVO
	 * @param bindingResult
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/shop/goods/cancelOrder.do", method = RequestMethod.POST)
	public JsonResult cancelOrder(OrderVO searchVO, BindingResult bindingResult) {
		JsonResult jsonResult = new JsonResult();

		try {
			if(!this.isHasErrorsJson(bindingResult, jsonResult, "<br/>")) {
				LoginVO user = (LoginVO)EgovUserDetailsHelper.getAuthenticatedUser();	
				searchVO.setLastUpdusrId(user.getUniqId());
				
				orderService.cancelOrder(searchVO);
				jsonResult.setSuccess(true);
				jsonResult.setMessage(egovMessageSource.getMessage("success.common.update")); //수정이 성공하였습니다.
				
				/* 주문취소접수 고객 알림톡 */
				OrderVO orderInfo = orderService.selectOrder(searchVO);
				GoodsVO goods = new GoodsVO();
				goods.setGoodsId(orderInfo.getGoodsId());
				GoodsVO goodsInfo = goodsService.selectGoods(goods);

				BiztalkVO bizTalk = new BiztalkVO();
				bizTalk.setRecipient(orderInfo.getTelno());
				bizTalk.setTmplatCode("template_014");
				/*[모두의구독] 주문 취소가 접수되었습니다.
				 * 상품명 : #{PRODUCTNAME} 
				 * 고객센터 : #{TEL}
				 */
				BiztalkVO template = biztalkService.selectBizTalkTemplate(bizTalk);
				String message = template.getTmplatCn();
				message = message.replaceAll("#\\{PRODUCTNAME\\}", goodsInfo.getGoodsNm());
				message = message.replaceAll("#\\{TEL\\}", String.valueOf(goodsInfo.getCmpnyTelno()));
				bizTalk.setMessage(message);
				
				BiztalkVO result = biztalkService.sendAlimTalk(bizTalk);
				
				/* 주문취소접수 CP 알림톡 */
				CmpnyVO cmpny = new CmpnyVO();
				cmpny.setCmpnyId(goodsInfo.getCmpnyId());
				CmpnyVO cmpnyInfo = cmpnyService.selectCmpny(cmpny);
				
				bizTalk.setRecipient(cmpnyInfo.getChargerTelno());
				bizTalk.setTmplatCode("template_021");
				/*[모두의구독] 주문 취소가 접수되었습니다. 자세한 사항은 CP 관리자 페이지의 [취소] 메뉴에서 확인하실 수 있습니다.
				 * 상품명 : #{PRODUCTNAME} 
				 * 고객센터 : #{TEL}
				 */
				BiztalkVO resultCp = biztalkService.sendAlimTalk(bizTalk);
			}
		} catch(Exception e) {
			LOGGER.error("Exception : " + e);
			jsonResult.setSuccess(false);
			jsonResult.setMessage(egovMessageSource.getMessage("fail.common.update")); //수정이 실패하였습니다.
		}
		
		return jsonResult;
	}
	
	/**
	 * 교환/반품접수
	 * @param searchVO
	 * @param bindingResult
	 * @return
	 * @throws Exception 
	 */
	@ResponseBody
	@RequestMapping(value = "/shop/goods/refundOrder.do", method = RequestMethod.POST)
	public JsonResult refundOrder(OrderDlvyVO dlvyVO, QainfoVO qainfo,
			final MultipartHttpServletRequest multiRequest, BindingResult bindingResult) throws Exception {
		JsonResult jsonResult = new JsonResult();
		LoginVO loginVO = (LoginVO) EgovUserDetailsHelper.getAuthenticatedUser();
		
		final List<MultipartFile> fileList = multiRequest.getFiles("atchFile");

		for (MultipartFile file : fileList) {
			System.out.println("파일 목록");
			System.out.println(file.getOriginalFilename());
		}
		
		String atchFileId = "";
		if(!fileList.isEmpty()) {
			String prefixPath = File.separator + "QAINFO";
			List<FileVO> files = fileMngUtil.parseFileInf(fileList, "QAINFO_", 0, "", "", prefixPath); // 저장경로 : src/main/resources/egovframework/egovProps/globals.properties -> Globals.fileStorePath 참고
			atchFileId = fileMngService.insertFileInfs(files);
			qainfo.setAtchFileId(atchFileId); // 첨부파일고유ID
		}

		try {
			if(!this.isHasErrorsJson(bindingResult, jsonResult, "<br/>")) {
				qainfo.setWrterId(loginVO.getId());
				qainfo.setFrstRegisterId(loginVO.getUniqId());
				qainfo.setWrterNm(loginVO.getName());
				
				OrderVO searchVO = new OrderVO();
				searchVO.setOrderNo(dlvyVO.getOrderNo());
				OrderVO orderInfo = orderService.selectOrder(searchVO);
				qainfo.setGoodsId(orderInfo.getGoodsId());

				if ("EX".equals(qainfo.getQaSeCode())) {
					dlvyVO.setReqTyCode("E01");
					dlvyVO.setOrderReqSttusCode("E");
				} else if ("RF".equals(qainfo.getQaSeCode())) {
					dlvyVO.setReqTyCode("R01");
					dlvyVO.setOrderReqSttusCode("R");
				}
				
				orderService.refundOrder(dlvyVO, qainfo);
				jsonResult.setSuccess(true);
				jsonResult.setMessage(egovMessageSource.getMessage("success.common.update")); //수정이 성공하였습니다.
				
				/* 교환/반품 접수 알림톡 */
				GoodsVO goods = new GoodsVO();
				goods.setGoodsId(orderInfo.getGoodsId());
				GoodsVO goodsInfo = goodsService.selectGoods(goods);

				BiztalkVO bizTalk = new BiztalkVO();
				bizTalk.setRecipient(orderInfo.getTelno());
				
				if ("EX".equals(qainfo.getQaSeCode())) {
					bizTalk.setTmplatCode("template_006");
				} else if ("RF".equals(qainfo.getQaSeCode())) {
					bizTalk.setTmplatCode("template_008");
				}

				BiztalkVO template = biztalkService.selectBizTalkTemplate(bizTalk);
				String message = template.getTmplatCn();
				message = message.replaceAll("#\\{PRICE\\}", String.valueOf(orderInfo.getTotAmount())+"원").replaceAll("#\\{PRODUCTNAME\\}", goodsInfo.getGoodsNm());
				bizTalk.setMessage(message);
				
				BiztalkVO result = biztalkService.sendAlimTalk(bizTalk);
			}
		} catch(Exception e) {
			e.printStackTrace();
			LOGGER.error("Exception : " + e);
			jsonResult.setSuccess(false);
			jsonResult.setMessage(egovMessageSource.getMessage("fail.common.update")); //수정이 실패하였습니다.
		}
		
		return jsonResult;
	}
	
	/**
	 * 마이구독 > 교환/환불 목록
	 * 
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/shop/goods/myRefundList.json", method = RequestMethod.GET)
	public JsonResult getMyRefundList(OrderVO searchVO) throws Exception {
		JsonResult jsonResult = new JsonResult();
		boolean isLogin = EgovUserDetailsHelper.isAuthenticated();
				
		if (isLogin) {
			LoginVO loginVO = (LoginVO) EgovUserDetailsHelper.getAuthenticatedUser();
			searchVO.setOrdrrId(loginVO.getId());
		}

		PaginationInfo paginationInfo = new PaginationInfo();

		this.setPagination(paginationInfo, searchVO);
		int totalRecordCount = orderService.selectMyRefundListCnt(searchVO); // 목록 카운트
		paginationInfo.setTotalRecordCount(totalRecordCount);

		List<?> orderList = orderService.selectMyRefundList(searchVO); // 목록

		jsonResult.put("resultList", orderList);
		jsonResult.put("paginationInfo", paginationInfo);
		jsonResult.put("isLogin", isLogin);

		return jsonResult;
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
		List<?> codeList = EgovCmmUseService.selectCmmCodeDetail(vo);

		for (int i = 0; i<codeList.size(); i++) {
			CmmnDetailCode codeItem = (CmmnDetailCode) codeList.get(i);
			codeMap.put(codeItem.getCode(), codeItem.getCodeNm());
		}
		
		String codeNm = codeMap.get(code);
		
		return codeNm;
	}
	
	/**
	 *주문 상세 
	 * @param orderNo
	 * @return
	 */
	@RequestMapping("/shop/goods/selectOrder.json")
	@ResponseBody
	public JsonResult selectCart(@RequestParam("orderNo") String orderNo){
		
		JsonResult result = new JsonResult();
		
		try {
			OrderVO searchVO = new OrderVO();
			searchVO.setOrderNo(orderNo);
			EgovMap orderMap = orderService.selectModOrder(searchVO);
			
			if(orderMap!=null){
				result.put("result", orderMap);
				
				result.setMessage("success.common.select");
				result.setSuccess(true);
			}
		} catch (Exception e) {
			loggerError(LOGGER, e);
			result.setMessage("fail.common.select");
			result.setSuccess(false);
		}
		return result;
	}
	/*
	 * 구독옵션 수정
	 * @param cart
	 * @return
	 *  
	 */
	@RequestMapping(value="/shop/goods/updateOrder.json" , method=RequestMethod.POST)
	@ResponseBody
	public JsonResult deleteCart(@RequestBody OrderVO order){

		JsonResult result = new JsonResult();
		LoginVO user = (LoginVO)EgovUserDetailsHelper.getAuthenticatedUser();
		try {
			if("SBS".equals(order.getOrderKndCode())){
				if("WEEK".equals(order.getSbscrptCycleSeCode())) {
					if(order.getSbscrptWeekCycle() == null || order.getSbscrptDlvyWd() == null) {
						result.setSuccess(false);
						result.setMessage("수정이 실패하였습니다");
					}
				}else if("MONTH".equals(order.getSbscrptCycleSeCode())) {
					if(order.getSbscrptMtCycle() == null || order.getSbscrptDlvyDay() == null) {
						result.setSuccess(false);
						result.setMessage("수정이 실패하였습니다");
					}
				}
			}
			
			if(order.getOrderCo() == null || order.getOrderCo() <= 0) {
				result.setSuccess(false);
				result.setMessage("수정이 실패하였습니다");
			}
				
				OrderVO searchVO = orderService.selectOrder(order);
				order.setLastUpdusrId(user.getUniqId());
				order.setOrderPnttm(searchVO.getOrderPnttm());
				
				orderService.updateSbsOrder(order);
				result.setSuccess(true);
				result.setMessage("수정되었습니다.");
				
			
		} catch (Exception e) {
			loggerError(LOGGER, e);
			result.setSuccess(false);
			result.setMessage("수정이 실패하였습니다");
		}
		return result;
	}
	
	/** 
	 * 구독해지접수
	 * @param searchVO
	 * @return
	 */
	@RequestMapping(value="/shop/goods/stopSubscribe.do", method=RequestMethod.POST)
	@ResponseBody
	public JsonResult stopSubscribe(OrderVO searchVO){
		JsonResult jsonResult = new JsonResult();
		LoginVO user = (LoginVO)EgovUserDetailsHelper.getAuthenticatedUser();	
		searchVO.setLastUpdusrId(user.getUniqId());
		
		try {
			orderService.stopSubscribeRequest(searchVO);
			jsonResult.setMessage("success.common.update");
			jsonResult.setSuccess(true);

			/* 구독해지 접수 알림톡 */
			OrderVO orderInfo = orderService.selectOrder(searchVO);
			GoodsVO goods = new GoodsVO();
			goods.setGoodsId(orderInfo.getGoodsId());
			GoodsVO goodsInfo = goodsService.selectGoods(goods);

			BiztalkVO bizTalk = new BiztalkVO();
			bizTalk.setRecipient(orderInfo.getTelno());
			bizTalk.setTmplatCode("template_004");
			/*[모두의구독] 구독 해지가 접수되었습니다.
			 * 상품명 : #{PRODUCTNAME}
			 */
			BiztalkVO template = biztalkService.selectBizTalkTemplate(bizTalk);
			String message = template.getTmplatCn();
			message = message.replaceAll("#\\{PRODUCTNAME\\}", goodsInfo.getGoodsNm());
			bizTalk.setMessage(message);
			
			BiztalkVO result = biztalkService.sendAlimTalk(bizTalk);
			
		} catch (Exception e) {
			loggerError(LOGGER, e);
			jsonResult.setMessage("fail.common.update");
			jsonResult.setSuccess(false);
		}
		return jsonResult;
	}
	
	/**
	 * 구독해지확인
	 * @param searchVO
	 * @return
	 */
	@RequestMapping(value="/shop/goods/stopSubscribeConfirm.do", method=RequestMethod.POST)
	@ResponseBody
	public JsonResult stopSubscribeConfirm(OrderVO searchVO){
		JsonResult jsonResult = new JsonResult();
		LoginVO user = (LoginVO)EgovUserDetailsHelper.getAuthenticatedUser();	
		searchVO.setLastUpdusrId(user.getUniqId());
		
		try {
			orderService.stopSubscribeConfirm(searchVO);
			jsonResult.setMessage("success.common.update");
			jsonResult.setSuccess(true);
			
			/* 구독해지 완료 알림톡 */
			OrderVO orderInfo = orderService.selectOrder(searchVO);
			GoodsVO goods = new GoodsVO();
			goods.setGoodsId(orderInfo.getGoodsId());
			GoodsVO goodsInfo = goodsService.selectGoods(goods);

			BiztalkVO bizTalk = new BiztalkVO();
			bizTalk.setRecipient(orderInfo.getTelno());
			bizTalk.setTmplatCode("template_005");
			/*[모두의구독] 구독이 해지되었습니다.
			 * 상품명 : #{PRODUCTNAME}
			 */
			BiztalkVO template = biztalkService.selectBizTalkTemplate(bizTalk);
			String message = template.getTmplatCn();
			message = message.replaceAll("#\\{PRODUCTNAME\\}", goodsInfo.getGoodsNm());
			bizTalk.setMessage(message);
			
			BiztalkVO result = biztalkService.sendAlimTalk(bizTalk);
			
		} catch (Exception e) {
			loggerError(LOGGER, e);
			jsonResult.setMessage("fail.common.update");
			jsonResult.setSuccess(false);
		}
		return jsonResult;
	}
	/**
	 * 구독해지 취소
	 * @param searchVO
	 * @return
	 */
	@RequestMapping(value="/shop/goods/stopSubscribeCancel.do", method=RequestMethod.POST)
	@ResponseBody
	public JsonResult stopSubscribeCancel(OrderVO searchVO){
		JsonResult result = new JsonResult();
		LoginVO user = (LoginVO)EgovUserDetailsHelper.getAuthenticatedUser();	
		searchVO.setLastUpdusrId(user.getUniqId());
		
		try {
			orderService.stopSubscribeCancel(searchVO);
			result.setMessage("success.common.update");
			result.setSuccess(true);
		} catch (Exception e) {
			loggerError(LOGGER, e);
			result.setMessage("fail.common.update");
			result.setSuccess(false);
		}
		return result;
	}

	/**
	 * 우편번호로 도서산간지역 여부 확인
	 * @param searchVO
	 * @return
	 */
	@RequestMapping(value="/shop/goods/checkIdsrtsAt.do", method=RequestMethod.POST)
	@ResponseBody
	public JsonResult checkIdsrtsAt(IdsrtsVO searchVO){
		JsonResult result = new JsonResult();

		try {
			int count = idsrtsService.selectIdsrtsCheckCnt(searchVO);
			result.put("count", count);
			result.setMessage("success.common.update");
			
			result.setSuccess(true);
		} catch (Exception e) {
			loggerError(LOGGER, e);
			result.setMessage("fail.common.update");
			result.setSuccess(false);
		}
		return result;
	}
	
	/**
	 * 간편비밀번호체크
	 * @param password
	 * @return
	 */
	@RequestMapping(value="/shop/goods/checkPassword.json",method=RequestMethod.POST)
	@ResponseBody
	public JsonResult checkPassword(@RequestParam("password") String password,@RequestParam("cardId") String cardId){
		
		JsonResult jsonResult = new JsonResult();
		
		try {
			LoginVO user = (LoginVO)EgovUserDetailsHelper.getAuthenticatedUser();
			CreditCardVO searchVO = new CreditCardVO();
			String enpassword = EgovFileScrty.encryptPassword(password.trim(),user.getUniqId());
			
			searchVO.setCardId(cardId);
			searchVO = cardService.selectCard(searchVO);
			if(enpassword.equals(searchVO.getPassword())){
				jsonResult.setSuccess(true);
			}else{
				jsonResult.setSuccess(false);
			}
			
		} catch (Exception e) {
			loggerError(LOGGER, e);
			jsonResult.setSuccess(false);
		}
		return jsonResult;
	}
	/**
	 * 1회체험구매여부
	 * @param goodsId
	 * @return
	 */
	@RequestMapping("/shop/goods/checkExprnCnt.json")
	@ResponseBody
	public JsonResult checkExprnCnt(@RequestParam("goodsId")String goodsId){
		
		JsonResult jsonResult = new JsonResult();
		
		try {
			LoginVO user = (LoginVO)EgovUserDetailsHelper.getAuthenticatedUser();
			
			OrderVO searchVO = new OrderVO();
			searchVO.setGoodsId(goodsId);
			searchVO.setOrdrrId(user.getId());
			
			int chkCnt = orderService.selectExprnCnt(searchVO);
			
			jsonResult.put("chkCnt", chkCnt);
			
		} catch (Exception e) {
			loggerError(LOGGER, e);
		}
		return jsonResult;
	}
	/**
	 * 이벤트상품구매여부
	 * @param goodsId
	 * @return
	 */
	@RequestMapping("/shop/goods/evtGoodsOrderChk.json")
	@ResponseBody
	public JsonResult evtGoodsOrderChk(@RequestParam("goodsId") String goodsId){
		
		LoginVO user = (LoginVO)EgovUserDetailsHelper.getAuthenticatedUser();
		
		JsonResult jsonResult = new JsonResult();
		
		try {
			OrderVO searchVO = new OrderVO();
			searchVO.setGoodsId(goodsId);
			searchVO.setOrdrrId(user.getId());
			int evtGoodsOrderCnt = orderService.selectEvtGoodsOrderCnt(searchVO);
			
			if(evtGoodsOrderCnt>0){
				jsonResult.setSuccess(false);
				jsonResult.setMessage("본 상품은 1회만 구매가능합니다.");
			}else{
				jsonResult.setSuccess(true);
			}
			
		} catch (Exception e) {
			loggerError(LOGGER, e);
		}
		return jsonResult;
	}
}
