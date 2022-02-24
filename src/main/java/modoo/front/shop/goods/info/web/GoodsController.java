package modoo.front.shop.goods.info.web;

import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import egovframework.com.cmm.ComDefaultCodeVO;
import egovframework.com.cmm.LoginVO;
import egovframework.com.cmm.service.EgovCmmUseService;
import egovframework.com.cmm.service.EgovProperties;
import egovframework.com.cmm.util.EgovUserDetailsHelper;
import egovframework.rte.ptl.mvc.tags.ui.pagination.PaginationInfo;
import modoo.module.biling.service.IniFunc;
import modoo.module.common.service.JsonResult;
import modoo.module.common.web.CommonDefaultController;
import modoo.module.mber.info.service.MberService;
import modoo.module.mber.info.service.MberVO;
import modoo.module.shop.cmpny.service.CmpnyService;
import modoo.module.shop.cmpny.service.CmpnyVO;
import modoo.module.shop.goods.brand.service.GoodsBrandService;
import modoo.module.shop.goods.brand.service.GoodsBrandVO;
import modoo.module.shop.goods.cart.service.CartVO;
import modoo.module.shop.goods.ctgry.service.GoodsCtgryService;
import modoo.module.shop.goods.ctgry.service.GoodsCtgryVO;
import modoo.module.shop.goods.info.service.GoodsService;
import modoo.module.shop.goods.info.service.GoodsVO;
import modoo.module.shop.goods.keyword.service.GoodsKeywordService;
import modoo.module.shop.goods.order.service.OrderService;
import modoo.module.shop.goods.order.service.OrderVO;
import modoo.module.shop.goods.sch.service.GoodsSearchService;
import modoo.module.shop.goods.sch.service.GoodsSearchVO;

@Controller
public class GoodsController extends CommonDefaultController {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(GoodsController.class);
	
	private static final Integer BEST_FIRST_UNIT = 15;
	private static final Integer BEST_PAGE_UNIT = 30;
	private static final Integer CTGRY_PAGE_UNIT = 12;
	private static final Integer SEARCH_PAGE_UNIT = 12;
	
	//private static final String ROOT_CTGRY_ID = "GCTGRY_0000000000000";
	
	//private static final String DEFAULT_GROUP_ID = "GROUP_00000000000000";
	private static final String EZWEL_GROUP_ID = "GROUP_00000000000001";
	private static final String DEFAULT_PRTNR_ID = "PRTNR_0000";
	private static final String EZWEL_PRTNR_ID = "PRTNR_0001";
	
	@Resource(name = "goodsService")
	private GoodsService goodsService;
	
	@Resource(name = "goodsCtgryService")
	private GoodsCtgryService goodsCtgryService;
	
	@Resource(name = "goodsSearchService")
	private GoodsSearchService goodsSearchService;
	
	@Resource(name = "goodsKeywordService")
	private GoodsKeywordService goodsKeywordService;
	
	@Resource(name = "EgovCmmUseService")
	private EgovCmmUseService cmmUseService;
	
	@Resource(name = "cmpnyService")
	private CmpnyService cmpnyService;

	@Resource(name = "orderService")
	private OrderService orderService;
	
	@Resource(name = "mberService")
	private MberService mberService;
	
	@Resource(name = "goodsBrandService")
	private GoodsBrandService goodsBrandService;
	
	/**
	 * 베스트 상품 목록
	 * @param searchVO
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/embed/shop/goods/bestGoodsList.do")
	public String bestGoodsList(@ModelAttribute("searchVO") GoodsVO searchVO,
		Integer lastPageIndex, Model model) throws Exception {
		LoginVO user = (LoginVO)EgovUserDetailsHelper.getAuthenticatedUser();

		if(EgovUserDetailsHelper.isAuthenticated()) {
			if(!EgovUserDetailsHelper.getAuthorities().contains("ROLE_SHOP")) {
				if(EZWEL_GROUP_ID.equals(user.getGroupId())) {
					searchVO.setSearchPrtnrId(EZWEL_PRTNR_ID);
				}else {
					searchVO.setSearchPrtnrId(DEFAULT_PRTNR_ID);
				}
			}
		}else {
			searchVO.setSearchPrtnrId(DEFAULT_PRTNR_ID);
		}
		
		/* 이벤트 관련 권한 정보 */
		if (user != null) {
			if(EZWEL_GROUP_ID.equals(user.getGroupId())) {
				searchVO.setSearchEventPrtnrId(EZWEL_PRTNR_ID);
			}else {
				searchVO.setSearchEventPrtnrId(DEFAULT_PRTNR_ID);
			}
		} else {
			searchVO.setSearchEventPrtnrId(DEFAULT_PRTNR_ID);
		}
		
		// 구독 상품과 BEST 상품 노출
		searchVO.setSearchMainBestGoodsAt("Y");
		
		PaginationInfo paginationInfo = new PaginationInfo();
		if(lastPageIndex == null || lastPageIndex == 1) {
			if(lastPageIndex != null && lastPageIndex == 1) {
				searchVO.setPageIndex(1);
			}
			if(searchVO.getPageIndex() == 1) {
				searchVO.setRecordCountPerPage(BEST_FIRST_UNIT);
				searchVO.setFirstIndex(0);
			}else {
				searchVO.setRecordCountPerPage(30);
				searchVO.setFirstIndex(BEST_PAGE_UNIT * (searchVO.getPageIndex()-1) - BEST_FIRST_UNIT);
			}
		}else {
			searchVO.setPageUnit(BEST_PAGE_UNIT * lastPageIndex - BEST_FIRST_UNIT);
			this.setPagination(paginationInfo, searchVO);
			int pageIndex = (int)Math.round((float)searchVO.getPageUnit() / (float)BEST_PAGE_UNIT);
			searchVO.setPageIndex(pageIndex);
		}
		
		/*
		if(lastPageIndex == null ||  lastPageIndex == 1) {
			searchVO.setPageUnit(BEST_PAGE_UNIT);
		}else {
			searchVO.setPageUnit(BEST_PAGE_UNIT * lastPageIndex);
		}
		this.setPagination(paginationInfo, searchVO);
		*/
		
		List<?> resultList = goodsService.selectBestGoodsList(searchVO);
		model.addAttribute("resultList", resultList);
		
		int totalRecordCount = goodsService.selectBestGoodsListCnt(searchVO);
		paginationInfo.setTotalRecordCount(totalRecordCount);
		model.addAttribute("paginationInfo", paginationInfo);
		
		model.addAttribute("prtnrId", searchVO.getSearchEventPrtnrId());
		
		return "modoo/front/shop/goods/info/bestGoodsList";
	}
	
	/**
	 * 메인 상품들 목록
	 * @param searchVO
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/embed/shop/goods/mainGoodsList.do")
	public String mainGoodsList(@ModelAttribute("searchVO") GoodsVO searchVO, Model model) throws Exception {
		LoginVO user = (LoginVO)EgovUserDetailsHelper.getAuthenticatedUser();

		if(EgovUserDetailsHelper.isAuthenticated()) {
			if(!EgovUserDetailsHelper.getAuthorities().contains("ROLE_SHOP")) {
				if(EZWEL_GROUP_ID.equals(user.getGroupId())) {
					searchVO.setSearchPrtnrId(EZWEL_PRTNR_ID);
				}else {
					searchVO.setSearchPrtnrId(DEFAULT_PRTNR_ID);
				}
			}
		}else {
			searchVO.setSearchPrtnrId(DEFAULT_PRTNR_ID);
		}
		
		List<?> resultList = goodsService.selectMainGoodsList(searchVO);
		model.addAttribute("resultList", resultList);
		
		//int totalRecordCount = goodsService.selectMainGoodsListCnt(searchVO);
		
		model.addAttribute("prtnrId", searchVO.getSearchEventPrtnrId());
		
		return "modoo/front/shop/goods/info/mainGoodsList";
	}
	
	/**
	 * 상품 상세
	 * @param searchVO
	 * @param model
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/shop/goods/goodsView.do")
	public String goodsView(@ModelAttribute("searchVO") GoodsVO searchVO, Model model,HttpServletRequest request) throws Exception {
		LoginVO user = (LoginVO)EgovUserDetailsHelper.getAuthenticatedUser();
		if(EgovUserDetailsHelper.isAuthenticated()) {
			model.addAttribute("user", user);
			if(!EgovUserDetailsHelper.getAuthorities().contains("ROLE_SHOP")) {
				if(EZWEL_GROUP_ID.equals(user.getGroupId())) {
					searchVO.setSearchPrtnrId(EZWEL_PRTNR_ID);
				}else {
					searchVO.setSearchPrtnrId(DEFAULT_PRTNR_ID);
				}
			}
		}else {
			searchVO.setSearchPrtnrId(DEFAULT_PRTNR_ID);
		}
		
		//요일코드
		ComDefaultCodeVO codeVO = new ComDefaultCodeVO();
		codeVO.setCodeId("CMS023");
		List<?> wdCodeList = cmmUseService.selectCmmCodeDetail(codeVO);
		model.addAttribute("wdCodeList", wdCodeList);
		
		if(!EgovUserDetailsHelper.getAuthorities().contains("ROLE_SHOP")) { //일반 유저는 못보게 처리
			searchVO.setSearchRegistSttusCode("C"); //등록완료
		}else {
			model.addAttribute("viewMode", "Y");
		}
		
		//상품 상세
		GoodsVO goods = goodsService.selectGoods(searchVO);
		if(goods == null) {
			model.addAttribute("ERR_CODE", "NONE");
			return "modoo/front/shop/goods/info/goodsViewError";
		}
		model.addAttribute("goods", goods);
		
		//성인인증여부
		if("Y".equals(goods.getAdultCrtAt()) && user!=null){
			MberVO mber = new MberVO();
			mber.setEsntlId(user.getUniqId());
			mber = mberService.selectMber(mber);
			if(mber!=null){
				model.addAttribute("adultCrtYn" , mber.getAdultCrtYn());
			}else{
				model.addAttribute("adultCrtYn" , "N");
			}
			//이니시스 본인인증
			HashMap<String,String> iniCertMap = new HashMap(); 
			String mid			= EgovProperties.getProperty("INICIS.card.mid");									// [필수] 가맹점 MID
			String siteurl		= request.getServerName();						// [필수] 가맹점 도메인 -> (URL Encoding 대상필드)
			String tradeid		= "test12345";							// [필수] 가맹점 거래번호
			String diCode		= "Test001DI";							// [옵션] 웹 사이트 코드
			String mstr			= "a=1|b=2";							// [옵션] 가맹점 콜백변수 -> (URL Encoding 대상필드)
			String closeUrl		= "https://"+siteurl+"/embed/cardCertResult.do";				// [필수] 취소, 닫기버튼 클릭 시 호출되는 가맹점 페이지 URL -> (URL Encoding 대상필드)
			String okUrl		= "https://"+siteurl+"/embed/cardCertResult.do";	// [필수] 인증응답 결과 전달용 가맹점 완료 페이지 URL	 -> (URL Encoding 대상필드)

			/* URL Encoding 대상필드 셋팅 */
			siteurl		= URLEncoder.encode(siteurl, "UTF-8");
			mstr		= URLEncoder.encode(mstr, "UTF-8");
			closeUrl	= URLEncoder.encode(closeUrl, "UTF-8");
			okUrl		= URLEncoder.encode(okUrl, "UTF-8");
			
			iniCertMap.put("mid", mid);
			iniCertMap.put("siteurl", siteurl);
			iniCertMap.put("tradeid", tradeid);
			iniCertMap.put("diCode", diCode);
			iniCertMap.put("mstr", mstr);
			iniCertMap.put("closeUrl", closeUrl);
			iniCertMap.put("okUrl", okUrl);
			model.addAttribute("iniCertInfo",iniCertMap);
		}
		
		int chkCnt = 0;
		//체험 구독 횟수
		if(user!=null){
			OrderVO order = new OrderVO();
			order.setGoodsId(goods.getGoodsId());
			order.setOrdrrId(user.getId());
			chkCnt = orderService.selectExprnCnt(order);
		}
		model.addAttribute("exprnChkCnt", chkCnt);
		
		
		//업체 정보
		CmpnyVO cmpny = new CmpnyVO();
		cmpny.setCmpnyId(goods.getCmpnyId());
		cmpny = cmpnyService.selectCmpny(cmpny);
		model.addAttribute("cmpny", cmpny);
		
		//브랜드 정보
		GoodsBrandVO brand = new GoodsBrandVO();
		brand.setBrandId(goods.getBrandId());
		brand = goodsBrandService.selectGoodsBrand(brand);
		model.addAttribute("brand", brand);
		
		//기본배송정책 (판매자가 입력하지 않을때)
		model.addAttribute("systemDlvyPolicyCn", EgovProperties.getProperty("GOODS.plicy.cn"));
		
		//조회수 증가
		goodsService.updateGoodsRdcnt(goods);
		
		//주문
		CartVO goodsCart = new CartVO();
		goodsCart.setOrderCo(1);
		
		model.addAttribute("goodsCart", goodsCart);
		
		/* 이벤트 관련 권한 정보 */
		if (user != null) {
			if(EZWEL_GROUP_ID.equals(user.getGroupId())) {
				searchVO.setSearchPrtnrId(EZWEL_PRTNR_ID);
			}else {
				searchVO.setSearchPrtnrId(DEFAULT_PRTNR_ID);
			}
		} else {
			searchVO.setSearchPrtnrId(DEFAULT_PRTNR_ID);
		}
		GoodsVO goodsEventInfo = goodsService.selectGoods(searchVO);
		model.addAttribute("goodsEventInfo", goodsEventInfo);
		
		return "modoo/front/shop/goods/info/goodsView";
	}
	
	
	/**
	 * 성인인증
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("/embed/cardCertResult.do")
	public String cardCertResult(HttpServletRequest request,Model model){
		
		IniFunc iniFunc = new IniFunc();
		LoginVO user = (LoginVO)EgovUserDetailsHelper.getAuthenticatedUser();
		
		try {
			String MID = EgovProperties.getProperty("INICIS.card.mid");		
			String SEEDKEY =EgovProperties.getProperty("INISIS.card.seedKey");
			String SEEDIV =EgovProperties.getProperty("INISIS.card.seedIv");
			String Resultcd		= request.getParameter("Resultcd");		// 결과 코드
			String Resultmsg	= request.getParameter("Resultmsg");	// 결과 메시지 -> URLEncoding
			String Transid		= request.getParameter("Transid");		// 트랜잭션 ID
			String Tradeid		= request.getParameter("Tradeid");		// 가맹점 거래번호
			String MSTR			= request.getParameter("MSTR");			// 가맹점 콜백변수 -> URLEncoding
			String Signdate		= request.getParameter("Signdate");		// 인증일자
			String Name			= request.getParameter("Name");			// 이름 -> [SEED 암호화]			ex) 홍길동
			String Socialno		= request.getParameter("Socialno");		// 생년월일 -> [SEED 암호화]		ex) 20190101
			String Sex			= request.getParameter("Sex");			// 성별 -> [SEED 암호화]			ex) M
			String Foreigner	= request.getParameter("Foreigner");	// 외국인 여부 -> [SEED 암호화]	ex) L
			String Ci			= request.getParameter("Ci");			// Ci -> 기 암호화				comment : 기 암호화 된 데이터로 그대로 사용.
			String Di			= request.getParameter("Di");			// Di -> 기 암호화				comment : 기 암호화 된 데이터로 그대로 사용.
			String DI_CODE		= request.getParameter("DI_CODE");		// 웹 사이트 코드
			String Mac			= request.getParameter("Mac");			// Hash Value -> [sha256]		ex)mid값+signdate값+Ci값+Tradeid값+Transid값
		
			if(StringUtils.isNotEmpty(Resultmsg)){
				Resultmsg = URLDecoder.decode(Resultmsg, "UTF-8");
			}else{
				model.addAttribute("result","cancle");
				return "modoo/front/cmm/etc/cardCertResult";
			}
			if(!"".equals(MSTR)) MSTR = URLDecoder.decode(MSTR, "UTF-8");
	
			if("0000".equals(Resultcd)){ // 결콰 코드 (0000)인 경우 - 성공
	
				// signature 생성(순서주의:연동규약서 참고)
				String oriMac = MID+Signdate+Ci+Tradeid+Transid;
				oriMac = iniFunc.encrypteSHA256(oriMac); // oriMac 데이터 sha-256 적용
	
				/* 데이터 위변조 체크 가능 */
				if(!oriMac.equals(Mac)){
					/*
					 * 데이터 위변조 에러 발생
					 */
					model.addAttribute("result",false);
				}else{
					/*
					 * 인증 성공 시 가맹점 DB처리
					 */
					MberVO mber = new MberVO();
					mber.setEsntlId(user.getUniqId());
					mber.setAdultCrtYn("Y");
					mberService.updateMber(mber);
					model.addAttribute("result",true);
					LOGGER.info("카드 본인확인 서비스 인증 성공");
				}
			}else{ // 결과코드 (0000) 아닌 경우 - 인증 실패
				/*
				 * 인증 실패 시 가맹점 DB처리
				 */
				model.addAttribute("result",false);
				 LOGGER.info("카드 본인확인 서비스 인증 실패");
			}
		
		} catch (Exception e) {
			loggerError(LOGGER, e);
			 LOGGER.info("카드 본인확인 서비스 에러 발생"+e);
			 model.addAttribute("result",false);
		}
		return "modoo/front/cmm/etc/cardCertResult";
	}
	
	/**
	 * 판매자 정보 페이지
	 * @param model
	 * @return
	 * @throws Exception
	 */
	/*@RequestMapping(value = "/shop/goods/goodsGuidance.do")
	public String goodsGuidance(GoodsVO searchVO, Model model) throws Exception {
		CmpnyVO cmpny = new CmpnyVO();
		cmpny.setCmpnyId(searchVO.getCmpnyId());
		
		cmpny = cmpnyService.selectCmpny(cmpny);
		model.addAttribute("cmpny", cmpny);
		
		return "modoo/front/shop/goods/info/goodsGuidance";
	}*/
	
	/**
	 * 상품 카테고리 목록
	 * @param searchVO
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/shop/goods/goodsCtgryList.do")
	public String goodsCtgryList(@ModelAttribute("searchVO") GoodsVO searchVO, Model model) throws Exception {
		LoginVO user = (LoginVO)EgovUserDetailsHelper.getAuthenticatedUser();
		if(EgovUserDetailsHelper.isAuthenticated()) {
			if(!EgovUserDetailsHelper.getAuthorities().contains("ROLE_SHOP")) {
				if(EZWEL_GROUP_ID.equals(user.getGroupId())) {
					searchVO.setSearchPrtnrId(EZWEL_PRTNR_ID);
				}else {
					searchVO.setSearchPrtnrId(DEFAULT_PRTNR_ID);
				}
			}
		}else {
			searchVO.setSearchPrtnrId(DEFAULT_PRTNR_ID);
		}
		
		/* 이벤트 관련 권한 정보 */
		if (user != null) {
			if(EZWEL_GROUP_ID.equals(user.getGroupId())) {
				searchVO.setSearchEventPrtnrId(EZWEL_PRTNR_ID);
			}else {
				searchVO.setSearchEventPrtnrId(DEFAULT_PRTNR_ID);
			}
		} else {
			searchVO.setSearchEventPrtnrId(DEFAULT_PRTNR_ID);
		}
		
		//예외 (패션 > 크리에이터패션) GCTGRY_0000000000027
		System.out.println(searchVO.getSearchGoodsCtgryId()+"@@");
		if("GCTGRY_0000000000002".equals(searchVO.getSearchGoodsCtgryId()) || (StringUtils.isNotEmpty(searchVO.getSearchSubCtgryId()) && "GCTGRY_0000000000027".equals(searchVO.getSearchSubCtgryId()))) {
			// 일반상품이지만 카테고리 메뉴에 노출 할 수 있도록 처리함 2020.12.03
		}/*else {
			// 구독상품만 노출
			searchVO.setSearchKndCode("SBS");
		}*/

		// 구독상품만 노출
		//searchVO.setSearchKndCode("SBS");
		
		GoodsCtgryVO ctgry = new GoodsCtgryVO();
		ctgry.setSearchPrtnrId(searchVO.getSearchPrtnrId());
		List<GoodsCtgryVO> ctgryMenuList = goodsCtgryService.selectGoodsCtgryTreeList(ctgry);
		model.addAttribute("ctgryMenuList", ctgryMenuList);
		
		/*
		GoodsCtgryVO ctgry2 = new GoodsCtgryVO();
		ctgry2.setGoodsCtgryId(searchVO.getGoodsCtgryId());
		ctgry2.setSearchPrtnrId(searchVO.getSearchPrtnrId());
		List<GoodsCtgryVO> subCtgryList = goodsCtgryService.selectSubCtgryList(ctgry2);
		model.addAttribute("subCtgryList", subCtgryList);
		*/
		
		PaginationInfo paginationInfo = new PaginationInfo();
		searchVO.setPageUnit(CTGRY_PAGE_UNIT);
		//this.setPagination(paginationInfo, searchVO);
		this.setPagination(paginationInfo, searchVO, 5); //2020.11.25 수정요청
		
		//검색 ORDER
		if(StringUtils.isNotEmpty(searchVO.getSearchOrderField())) {
			if("LPC".equals(searchVO.getSearchOrderField())) {
				searchVO.setSearchOrderType("ASC");
			} else if( "HPC".equals(searchVO.getSearchOrderField())
					|| "RDCNT".equals(searchVO.getSearchOrderField()) 
					|| "LAT".equals(searchVO.getSearchOrderField()) 
					|| "SEL".equals(searchVO.getSearchOrderField()) ) {
				searchVO.setSearchOrderType("DESC");
			}
		} else {
			searchVO.setSearchOrderField("RDCNT"); //기본 인기순
			searchVO.setSearchOrderType("DESC");
		}
		
		searchVO.setSearchRegistSttusCode("C"); //등록완료
		
		List<?> resultList = goodsService.selectGoodsList(searchVO);
		model.addAttribute("resultList", resultList);

		int totalRecordCount = goodsService.selectGoodsListCnt(searchVO);
		paginationInfo.setTotalRecordCount(totalRecordCount);
		model.addAttribute("paginationInfo", paginationInfo);
		
		return "modoo/front/shop/goods/info/goodsCtgryList";
	}
	
	/**
	 * 상품 통합검색
	 * @param searchVO
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/shop/goods/goodsSearch.do")
	public String goodsSearch(@ModelAttribute("searchVO") GoodsVO searchVO,
			String storeSearchWrdAt,
			HttpSession session, Model model) throws Exception {
		LoginVO user = (LoginVO)EgovUserDetailsHelper.getAuthenticatedUser();
		if(EgovUserDetailsHelper.isAuthenticated()) {
			if(!EgovUserDetailsHelper.getAuthorities().contains("ROLE_SHOP")) {
				if(EZWEL_GROUP_ID.equals(user.getGroupId())) {
					searchVO.setSearchPrtnrId(EZWEL_PRTNR_ID);
				}else {
					searchVO.setSearchPrtnrId(DEFAULT_PRTNR_ID);
				}
			}
		}else {
			searchVO.setSearchPrtnrId(DEFAULT_PRTNR_ID);
		}
		
		// 구독상품만 노출
		//searchVO.setSearchKndCode("SBS");
		
		PaginationInfo paginationInfo = new PaginationInfo();
		searchVO.setPageUnit(SEARCH_PAGE_UNIT);
		this.setPagination(paginationInfo, searchVO);
		
		//검색 ORDER
		if(StringUtils.isNotEmpty(searchVO.getSearchOrderField())) {
			if("LPC".equals(searchVO.getSearchOrderField())) {
				searchVO.setSearchOrderType("ASC");
			} else if( "HPC".equals(searchVO.getSearchOrderField())
					|| "RDCNT".equals(searchVO.getSearchOrderField()) 
					|| "LAT".equals(searchVO.getSearchOrderField()) 
					|| "SEL".equals(searchVO.getSearchOrderField()) ) {
				searchVO.setSearchOrderType("DESC");
			}
		} else {
			searchVO.setSearchOrderField("RDCNT"); //기본 인기순
			searchVO.setSearchOrderType("DESC");
		}

		
		// 띄워쓰기로 검색어 분리
		List<String> searchWordList = new ArrayList<String>();
		if(StringUtils.isNotEmpty(searchVO.getSearchKeyword())) {
			searchWordList = Arrays.asList(searchVO.getSearchKeyword().split(" "));
		}
		searchVO.setSearchGoodsKeyword(searchWordList);

		searchVO.setSearchRegistSttusCode("C"); //등록완료
		
		//키워드 중심으로 연관어 목록 추출
		List<?> keywordList = goodsKeywordService.selectRelationGoodsKeywordList(searchVO);
		model.addAttribute("keywordList", keywordList);
		
		
		List<?> resultList = goodsService.selectGoodsList(searchVO);
		model.addAttribute("resultList", resultList);

		int totalRecordCount = goodsService.selectGoodsListCnt(searchVO);
		paginationInfo.setTotalRecordCount(totalRecordCount);
		model.addAttribute("paginationInfo", paginationInfo);
		
		
		//통합검색 시 저장 처리
		if(StringUtils.isNotEmpty(searchVO.getSearchKeyword()) && StringUtils.isNotEmpty(storeSearchWrdAt) && "Y".equals(storeSearchWrdAt)) {
			GoodsSearchVO goodsSearch = new GoodsSearchVO();
			if(user != null) {
				goodsSearch.setEsntlId(user.getUniqId());
			}
			goodsSearch.setSessionId(session.getId());
			goodsSearch.setSrchwrd(searchVO.getSearchKeyword());
			goodsSearchService.insertGoodsSearch(goodsSearch);
		}

		return "modoo/front/shop/goods/info/goodsSearch";
	}
	

}
