<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/modoo/common/commonTagLibs.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>모바일메뉴</title>
	<meta charset="utf-8"/>
	<meta http-equiv="X-UA-Compatible" content="IE=edge" />
	<meta name="viewport" content="width=device-width,initial-scale=1.0,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no"/>
	<link rel="canonical" href="${HTTP}://${pageContext.request.serverName}${pageContext.request.contextPath}${requestScope['javax.servlet.forward.request_uri']}"/>
	<meta name="robots" content="index, follow"/>
	<meta name="description" content="정기구독 서비스의 모든 것. FOXEDU STORE"/>
	<meta property="og:type" content="website"/>
	<meta property="og:title" content="FOXEDU STORE ${pageTitle }"/>
	<meta property="og:description" content="정기구독 서비스의 모든 것. FOXEDU STORE"/>
	<meta name="naver-site-verification" content="bc5cd8bb87b7ec5c4f212f5b52dba7d75174553d" />
	<link rel="stylesheet" type="text/css" href="${CTX_ROOT}/resources/front/site/${SITE_ID }/css/swiper.css"/>
	<link rel="stylesheet" type="text/css" href="${CTX_ROOT}/resources/front/site/${SITE_ID }/css/style.css?20201119"/>
	<link rel="stylesheet" type="text/css" href="${CTX_ROOT}/resources/front/site/${SITE_ID }/css/site.css?2020"/>
</head>
<body>
	<div class="m-menu">
		<div class="m-util">
			<a href="${CTX_ROOT }/shop/goods/cart.do" id="cart">
				<i class="ico-cart" aria-hidden="true"></i>
				<span class="txt-hide">장바구니</span>
				<c:choose>
					<c:when test="${not empty cartCnt}">
						<c:set var="isNotice" value="label-notice"/>
					</c:when>
					<c:otherwise>
						<c:set var="isNotice" value=""/>
					</c:otherwise>
				</c:choose>
				<span id="cartCnt" class="${isNotice}">${cartCnt}</span>
			</a>
			<c:if test="${empty USER_ID}">
			 <a href="/user/sign/loginUser.do">
				<i class="ico-login" aria-hidden="true"></i>
				<span>로그인</span>
			</a>
			</c:if>
			<c:if test="${!empty USER_ID}">
			<a href="/user/sign/logout.do" class="btn-logout">
				<i class="ico-logout" aria-hidden="true"></i>
				<span>로그아웃</span>
			</a>
			</c:if>
		</div>
		
		<nav>
		   <div>
			   <cite>카테고리</cite>
			   <ul>
			   <c:choose>
			   		<c:when test="${ctgryMenuList.size() > 0}">
				   		<c:forEach items="${ctgryMenuList}" var="ctgryMenu">
				   			<%-- 임시로 폭스 부분만 나오게 설정 --%>
							<c:if test="${fn:contains(ctgryMenu.goodsCtgryNm, '폭스')}">
						   		<li>
								   <button type="button" class="btn-menu-accordion">${ctgryMenu.goodsCtgryNm}</button>
								   <ul>
									   <li><a href="${CTX_ROOT}/shop/goods/goodsCtgryList.do?searchGoodsCtgryId=${ctgryMenu.goodsCtgryId}">전체</a></li>
									   <c:forEach items="${ctgryMenu._children}" var="subCtgry">
									   		<li><a href="${CTX_ROOT}/shop/goods/goodsCtgryList.do?searchGoodsCtgryId=${subCtgry.upperGoodsCtgryId}&searchSubCtgryId=${subCtgry.goodsCtgryId}">${subCtgry.goodsCtgryNm}</a></li>
									   </c:forEach>
								   </ul>
							   	</li>
						   	</c:if>
					   	</c:forEach>
			   		</c:when>
			   		<c:otherwise>
			   			카테고리가 없습니다.
			   		</c:otherwise>
			   </c:choose>
			   </ul>
		   </div>
		   		<%-- <a href="${CTX_ROOT}/shop/goods/brandList.do"><button type="button" class="btn-menu">브랜드</button></a> --%>
		   		<a href="${CTX_ROOT}/shop/event/goodsEventList.do"><button type="button" class="btn-menu">이벤트</button></a>
		   <div>
			   <cite>마이페이지</cite>
			   <ul>
				   <c:if test="${not empty USER_ID }">
					   <li>
						   <button type="button" class="btn-menu-accordion">주문관리</button>
						   <ul>
							   <li><a href="${CTX_ROOT}/user/my/mySubscribeNow.do?menuId=sbs_mySubscribeNow" class="is-active">주문확인</a></li>
							   <li><a href="${CTX_ROOT}/user/my/mySubscribeCancel.do?menuId=sbs_mySubscribeCancel">주문취소</a></li>
						   </ul>
					   </li>
					   <li>
						   <a href="${CTX_ROOT}/user/my/myRefund.do?menuId=refund">교환/환불</a>
					   </li>
					   <li>
						   <button type="button" class="btn-menu-accordion">게시판</button>
						   <ul>
							   <li><a href="${CTX_ROOT}/user/my/qainfo.do?qaSeCode=GOODS&menuId=bbs_goodsQna">Q&amp;A</a></li>
							   <li><a href="${CTX_ROOT}/user/my/review.do?menuId=bbs_review">내가 작성한 리뷰</a></li>
							   <li><a href="${CTX_ROOT}/user/my/reviewTodo.do?menuId=bbs_todo">작성 가능한 리뷰</a></li>
						   </ul>
					   </li>
					</c:if>
					<li>
						<button type="button" class="btn-menu-accordion">고객센터</button>
						<ul>
					   		<li><a href="${CTX_ROOT}/board/boardList.do?bbsId=BBSMSTR_000000000000&menuId=cs_BBSMSTR_000000000000">공지사항</a></li>
							<li><a href="${CTX_ROOT}/user/my/faqList.do?menuId=cs_FAQ">FAQ</a></li>
							<c:if test="${not empty USER_ID }">
								<li><a href="${CTX_ROOT}/user/my/qainfo.do?qaSeCode=SITE&menuId=cs_siteQna">1:1 문의</a></li>
						   </c:if>
					   </ul>
					</li>
					<c:if test="${not empty USER_ID }">
					   <li>
						   <button type="button" class="btn-menu-accordion">내 정보</button>
						   <ul>
							   <li><a href="${CTX_ROOT}/user/my/myInfo.do?menuId=myInfo_myDetail">내정보 보기</a></li>
							   <li><a href="${CTX_ROOT}/user/my/cardManage.do?menuId=myInfo_cardManage">카드 등록/관리</a></li>
						   </ul>
					   </li>
					</c:if>
			   </ul>
		   </div>
		</nav>
		<a href="${oldUrl}" class="btn-menu-close"><span class="txt-hide">닫기</span></a>
	</div>
	
 	<script src="${CTX_ROOT}/resources/lib/jquery/jquery.min.js"></script>
    <script src="${CTX_ROOT}/resources/lib/jquery.easing/jquery.easing.min.js"></script>
    <script src="${CTX_ROOT}/resources/lib/jquery-ui/jquery-ui.min.js"></script>
    <script src="${CTX_ROOT}/resources/lib/jquery-form/jquery.form.min.js"></script>
	<script src="${CTX_ROOT}/resources/lib/js-cookie/js.cookie.js"></script>
	<script src="${CTX_ROOT}/resources/lib/moment/moment-with-locales.min.js"></script>
	<script src="${CTX_ROOT}/resources/front/site/SITE_00000/js/ssm.min.js"></script>
    <script src="${CTX_ROOT}/resources/front/site/SITE_00000/js/swiper.min.js"></script>
    <script src="${CTX_ROOT}/resources/front/site/SITE_00000/js/ScrollMagic.min.js"></script>
    <script src="${CTX_ROOT}/resources/front/site/SITE_00000/js/TweenMax.min.js"></script>
    <script src="${CTX_ROOT}/resources/front/site/SITE_00000/js/animation.gsap.min.js"></script>
    <script src="${CTX_ROOT}/resources/common/js/common.js?20201022"></script>
    <script src="${CTX_ROOT}/resources/front/site/SITE_00000/js/common.js?2020"></script>
    <script src="${CTX_ROOT}/resources/front/site/${SITE_ID }/js/site.js?20201125-1"></script>
    <script src="https://developers.kakao.com/sdk/js/kakao.min.js"></script>
    
</body>
</html>