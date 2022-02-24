<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/modoo/common/commonTagLibs.jsp"%>
<!doctype html>
<html lang="ko-KR">
<head>
	<title><sitemesh:write property="title"/></title>
	<meta charset="utf-8"/>
	<meta content="IE=edge" http-equiv="X-UA-Compatible"/>
	<meta content="width=device-width, initial-scale=1, shrink-to-fit=no" name="viewport"/>
	<link rel="stylesheet" type="text/css" href="${CTX_ROOT}/resources/decms/template/css/sb-admin-2.min.css"/>
	<link rel="stylesheet" type="text/css" href="${CTX_ROOT}/resources/lib/fontawesome-free/css/all.min.css"/>
	<link rel="stylesheet" type="text/css" href="${CTX_ROOT}/resources/lib/summernote/summernote-bs4.min.css"/>
	<link rel="stylesheet" type="text/css" href="${CTX_ROOT}/resources/decms/common/css/decms.css?20201230"/>
	<link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Nunito:200,200i,300,300i,400,400i,600,600i,700,700i,800,800i,900,900i"/>
	<script> var CTX_ROOT = '${CTX_ROOT}'; <%-- Document ROOT PATH --%> </script>
	<c:choose>
    <c:when test="${CMS_MODE eq 'DEV' }"> <script> debug = function(str) { console.log(str); }; </script> </c:when>
    <c:otherwise> <script> debug = function(str) { }; </script> </c:otherwise>
    </c:choose>
	<sitemesh:write property="head"/>
</head>
<body>
<c:choose>
	<c:when test="${fn:contains(USER_ROLE,'ROLE_ADMIN')}"> <c:set var="gradientCss" value="bg-gradient-primary"/> </c:when>
	<c:otherwise> <c:set var="gradientCss" value="bg-gradient-cmpny"/> </c:otherwise>
</c:choose>
<c:choose>
<c:when test="${fn:contains(USER_ROLE,'ROLE_SHOP')}">
	<div id="wrapper">
		<ul class="navbar-nav ${gradientCss } sidebar sidebar-dark accordion " id="accordionSidebar">
			<a id="decms-logo" class="sidebar-brand d-flex align-items-center justify-content-center" href="${CTX_ROOT}/decms/index.do">
				<div class="sidebar-brand-icon rotate-n-15">
					<img src="${CTX_ROOT}/resources/decms/common/image/logo/cms_logo.png" alt="LOGO"/>
				</div>
				<div class="sidebar-brand-text mx-3">FOXEDU STORE 
					<c:choose>
						<c:when test="${fn:contains(USER_ROLE,'ROLE_ADMIN')}">
							<sup>cms</sup>
						</c:when>
						<c:when test="${fn:contains(USER_ROLE,'ROLE_SHOP')}">
							<sup>CP 관리</sup>
						</c:when>
					</c:choose>
				</div>
			</a>
			<hr class="sidebar-divider my-0"/>

			<li class="nav-item active">
				<c:if test="${fn:contains(USER_ROLE,'ROLE_SHOP') and fn:contains(USER_ROLE, 'ROLE_EMPLOYEE')}">
					<a class="nav-link" href="${CTX_ROOT}/index.do" target="_blank">
						<i class="fas fa-fw fa-home"></i>
						<span>HOME</span>
					</a>
				</c:if>
				<a class="nav-link" href="${CTX_ROOT}/decms/index.do">
					<i class="fas fa-fw fa-tachometer-alt"></i>
					<span>Dashboard</span>
				</a>
			</li>
			<hr class="sidebar-divider"/>


			<li class="nav-item">
				<c:if test="${fn:contains(USER_ROLE,'ROLE_EMPLOYEE')}">
					<a class="nav-link collapsed" href="#" data-toggle="collapse" data-target="#collapseSite" aria-expanded="true" aria-controls="collapseSite">
						<i class="fas fa-fw fa-cog"></i>
						<span>사이트관리</span>
					</a>
					<div id="collapseSite" class="collapse" aria-labelledby="headingTwo" data-parent="#accordionSidebar">
						<div class="bg-white py-2 collapse-inner rounded">
							<c:url var="goodsCtgryManageUrl" value="/decms/shop/goods/goodsCtgryManage.do">
								<c:param name="menuId" value="goodsCtgryManage"/>
							</c:url>
							<a class="collapse-item" href="<c:out value="${goodsCtgryManageUrl }"/>" data-menu-id="goodsCtgryManage">카테고리관리</a>
						</div>
						<div class="bg-white py-2 collapse-inner rounded">
							<h6 class="collapse-header">추천관리</h6>
							<c:url var="todayspickUrl" value="/decms/banner/bannerManage.do">
								<c:param name="menuId" value="todaysPickManage"/>
								<c:param name="searchSeCode" value="BANN003"/>
							</c:url>
							<a class="collapse-item" href="${todayspickUrl}" data-menu-id="todaysPickManage">모두's Pick</a>
							<c:url var="indpickUrl" value="/decms/banner/bannerManage.do">
								<c:param name="menuId" value="indPickManage"/>
								<c:param name="searchSeCode" value="BANN004"/>
							</c:url>
							<a class="collapse-item" href="${indpickUrl}" data-menu-id="indPickManage">개인추천</a>
						</div>
						<div class="bg-white py-2 collapse-inner rounded">
							<a class="collapse-item" href="#" data-menu-id="bestGoodsManage">BEST 구독</a>
							
							<c:url var="brandManageUrl" value="/decms/shop/goods/brandManage.do">
								<c:param name="menuId" value="brandManage"/>
							</c:url>
							<a class="collapse-item" href="<c:out value="${brandManageUrl }"/>" data-menu-id="brandManage">브랜드관</a>
						</div>
						<div class="bg-white py-2 collapse-inner rounded">
							<h6 class="collapse-header">게시판</h6>
							<%-- <a class="collapse-item" href="#" data-menu-id="none">교환/반품</a>
							<c:url var="qainfoManageUrl" value="/decms/qainfo/qainfoManage.do">
								<c:param name="menuId" value="qainfoManage"/>
							</c:url>
							 <a class="collapse-item" href="<c:out value="${qainfoManageUrl }"/>" data-menu-id="qainfoManage">Q&amp;A</a> --%>
							<%-- <c:url var="faqManageUrl" value="/decms/faq/faqManage.do">
								<c:param name="menuId" value="faqManage"/>
							</c:url>
							<a class="collapse-item" href="<c:out value="${faqManageUrl }"/>" data-menu-id="faqManage">FAQ</a> --%>
						</div> 
					</div>
					<a class="nav-link collapsed" href="#" data-toggle="collapse" data-target="#collapseCP" aria-expanded="true" aria-controls="collapseCP">
						<i class="fas fa-fw fa-cog"></i>
						<span>CP관리</span>
					</a>
					<div id="collapseCP" class="collapse" aria-labelledby="headingTwo" data-parent="#accordionSidebar">
						<div class="bg-white py-2 collapse-inner rounded">
							<c:url var="cmpnyManageUrl" value="/decms/shop/cmpny/cmpnyManage.do">
								<c:param name="menuId" value="cmpnyManage"/>
							</c:url>
							<a class="collapse-item" href="<c:out value="${cmpnyManageUrl }"/>" data-menu-id="cmpnyManage">업체등록</a> <%--2020.10.07 오영석 차장 수정요청으로 업체관리에서 업체등록으로 명칭변경 --%>
						</div>
					</div>
				</c:if>
				<c:if test="${fn:contains(USER_ROLE,'ROLE_SHOP') and not fn:contains(USER_ROLE, 'ROLE_EMPLOYEE')}">
					<a class="nav-link collapsed" href="#" data-toggle="collapse" data-target="#collapseCP" aria-expanded="true" aria-controls="collapseCP">
						<i class="fas fa-fw fa-cog"></i>
						<span>CP관리</span>
					</a>
					<div id="collapseCP" class="collapse" aria-labelledby="headingTwo" data-parent="#accordionSidebar">
						<div class="bg-white py-2 collapse-inner rounded">
							<c:url var="cmpnyManage" value="/decms/shop/cmpny/modifyCmpny.do">
								<c:param name="menuId" value="cmpnyManage"/>
							</c:url>
							<a class="collapse-item" href="<c:out value="${cmpnyManage }"/>" data-menu-id="cmpnyManage">정보변경</a>
							<c:url var="brandManageUrl" value="/decms/shop/goods/brandManage.do">
								<c:param name="menuId" value="brandManage"/>
							</c:url>
							<a class="collapse-item" href="<c:out value="${brandManageUrl }"/>" data-menu-id="brandManage">브랜드관</a>
						</div>
					</div>
				</c:if>
				<a class="nav-link collapsed" href="#" data-toggle="collapse" data-target="#collapseGoods" aria-expanded="true" aria-controls="collapseGoods">
					<i class="fas fa-fw fa-cog"></i>
					<span>상품관리</span>
				</a>
				<div id="collapseGoods" class="collapse" aria-labelledby="headingTwo" data-parent="#accordionSidebar">
					<div class="bg-white py-2 collapse-inner rounded">
						<c:url var="goodsManageUrl" value="/decms/shop/goods/goodsManage.do">
							<c:param name="menuId" value="goodsManage"/>
						</c:url>
						<a class="collapse-item" href="<c:out value="${goodsManageUrl }"/>" data-menu-id="goodsManage">상품</a>
					</div>
				</div>

				<a class="nav-link collapsed" href="#" data-toggle="collapse" data-target="#collapseOrderDlvy" aria-expanded="true" aria-controls="collapseOrderDlvy">
					<i class="fas fa-fw fa-cog"></i>
					<span>주문/배송</span>
				</a>
				<div id="collapseOrderDlvy" class="collapse" aria-labelledby="headingTwo" data-parent="#accordionSidebar">
					<div class="bg-white py-2 collapse-inner rounded">
						<c:url var="orderDlvyManageUrl" value="${CTX_ROOT}/decms/shop/goods/orderDlvyManage.do">
							<c:param name="menuId" value="orderDlvyManage"/>
						</c:url>
						<a class="collapse-item" href="<c:out value="${orderDlvyManageUrl}"/>" data-menu-id="orderDlvyManage">주문 현황</a>
					</div>
					<div class="bg-white py-2 collapse-inner rounded">
						<h6 class="collapse-header">해지/취소/교환/반품</h6>
						<c:url var="orderStopManageUrl" value="${CTX_ROOT}/decms/shop/goods/orderStopManage.do">
							<c:param name="menuId" value="orderStopManage"/>
						</c:url>
						<a class="collapse-item" href="<c:out value="${orderStopManageUrl}"/>" data-menu-id="orderStopManage">해지</a>
						
						<c:url var="orderCancelManageUrl" value="${CTX_ROOT}/decms/shop/goods/orderCancelManage.do">
							<c:param name="menuId" value="orderCancelManage"/>
						</c:url>
						<a class="collapse-item" href="<c:out value="${orderCancelManageUrl}"/>" data-menu-id="orderCancelManage">취소</a>
						<c:url var="orderExchangeManageUrl" value="${CTX_ROOT}/decms/shop/goods/orderExchangeManage.do">
							<c:param name="menuId" value="orderExchangeManage"/>
						</c:url>
						<a class="collapse-item" href="<c:out value="${orderExchangeManageUrl}"/>" data-menu-id="orderExchangeManage">교환</a>
						<c:url var="orderRecallManageUrl" value="${CTX_ROOT}/decms/shop/goods/orderRecallManage.do">
							<c:param name="menuId" value="orderRecallManage"/>
						</c:url>
						<a class="collapse-item" href="<c:out value="${orderRecallManageUrl}"/>" data-menu-id="orderRecallManage">반품</a>
					</div>
				</div>
				<a class="nav-link collapsed" href="#" data-toggle="collapse" data-target="#collapseExcclc" aria-expanded="true" aria-controls="collapseExcclc">
					<i class="fas fa-fw fa-cog"></i>
					<span>정산관리</span>
				</a>
				<div id="collapseExcclc" class="collapse" aria-labelledby="headingTwo" data-parent="#accordionSidebar">
					<div class="bg-white py-2 collapse-inner rounded">
						<c:if test="${fn:contains(USER_ROLE,'ROLE_EMPLOYEE')}">
							<c:url var="excclcHedofcUrl" value="/decms/shop/goods/excclcHedofcManage.do">
								<c:param name="menuId" value="excclcHedofcManage"/>
							</c:url>
							<a class="collapse-item" href="<c:out value="${excclcHedofcUrl }"/>" data-menu-id="excclcHedofcManage">이지웰정산</a>
							<c:url var="excclcCpManageUrl" value="/decms/shop/goods/excclcManage.do">
								<c:param name="menuId" value="excclcCpManage"/>
							</c:url>
							<a class="collapse-item" href="<c:out value="${excclcCpManageUrl }"/>" data-menu-id="excclcCpManage">CP정산내역</a>
							<c:url var="excclcCpDetailManageUrl" value="/decms/shop/goods/excclcCpManage.do">
								<c:param name="menuId" value="excclcCpDetailManage"/>
							</c:url>
							<a class="collapse-item" href="<c:out value="${excclcCpDetailManageUrl }"/>" data-menu-id="excclcCpDetailManage">CP정산내역상세</a>
						</c:if>
						<c:if test="${fn:contains(USER_ROLE,'ROLE_SHOP') and not fn:contains(USER_ROLE, 'ROLE_EMPLOYEE')}">
							<c:url var="cpExcclcManageUrl" value="/decms/shop/goods/cpExcclcManage.do">
								<c:param name="menuId" value="cpExcclcManage"/>
							</c:url>
							<a class="collapse-item" href="<c:out value="${cpExcclcManageUrl }"/>" data-menu-id="excclcCpManage">정산내역</a>
						</c:if>
					</div>
				</div>
				<a class="nav-link collapsed" href="#" data-toggle="collapse" data-target="#collapseStats" aria-expanded="true" aria-controls="collapseStats">
					<i class="fas fa-fw fa-cog"></i>
					<span>통계관리</span>
				</a>
				<c:if test="${fn:contains(USER_ROLE,'ROLE_EMPLOYEE')}"> <%-- 개발까지 임시로 CP 닫음 --%>
				<div id="collapseStats" class="collapse" aria-labelledby="headingTwo" data-parent="#accordionSidebar">
					<div class="bg-white py-2 collapse-inner rounded">
						<h6 class="collapse-header">매출분석</h6>
						<c:url var="selngDayManageUrl" value="/decms/shop/stats/selngDayManage.do">
							<c:param name="menuId" value="selngDayManage"/>
						</c:url>
						<a class="collapse-item" href="<c:out value="${selngDayManageUrl }"/>" data-menu-id="selngDayManage">일별매출</a>
						<c:url var="selngWeekManageUrl" value="/decms/shop/stats/selngWeekManage.do">
							<c:param name="menuId" value="selngWeekManage"/>
						</c:url>
						<a class="collapse-item" href="<c:out value="${selngWeekManageUrl }"/>" data-menu-id="selngWeekManage">요일/시간대별매출</a>
						<c:url var="selngMonthWeekManageUrl" value="/decms/shop/stats/selngMonthWeekManage.do">
							<c:param name="menuId" value="selngMonthWeekManage"/>
						</c:url>
						<a class="collapse-item" href="<c:out value="${selngMonthWeekManageUrl }"/>" data-menu-id="selngMonthWeekManage">주별매출</a>
						<c:url var="selngMonthManageUrl" value="/decms/shop/stats/selngMonthManage.do">
							<c:param name="menuId" value="selngMonthManage"/>
						</c:url>
						<a class="collapse-item" href="<c:out value="${selngMonthManageUrl }"/>" data-menu-id="selngMonthManage">월별매출</a>
						<c:url var="selngAgeManageUrl" value="/decms/shop/stats/selngAgeManage.do">
							<c:param name="menuId" value="selngAgeManage"/>
						</c:url>
						<a class="collapse-item" href="<c:out value="${selngAgeManageUrl }"/>" data-menu-id="selngAgeManage">연령별매출</a>
						<c:url var="selngSexdstnManageUrl" value="/decms/shop/stats/selngSexdstnManage.do">
							<c:param name="menuId" value="selngSexdstnManage"/>
						</c:url>
						<a class="collapse-item" href="<c:out value="${selngSexdstnManageUrl }"/>" data-menu-id="selngSexdstnManage">성별매출</a>
					</div>
					<div class="bg-white py-2 collapse-inner rounded">
						<h6 class="collapse-header">상품분석</h6>
						<c:url var="goodsSelngManageUrl" value="/decms/shop/stats/goodsSelngManage.do">
							<c:param name="menuId" value="goodsSelngManage"/>
						</c:url>
						<a class="collapse-item" href="<c:out value="${goodsSelngManageUrl }"/>" data-menu-id="goodsSelngManage">상품매출순위</a>
						<c:url var="cmpnySelngManageUrl" value="/decms/shop/stats/cmpnySelngManage.do">
							<c:param name="menuId" value="cmpnySelngManage"/>
						</c:url>
						<a class="collapse-item" href="<c:out value="${cmpnySelngManageUrl }"/>" data-menu-id="cmpnySelngManage">입점사매출순위</a>
						<c:url var="goodsStsfdgSelngManageUrl" value="/decms/shop/stats/goodsStsfdgSelngManage.do">
							<c:param name="menuId" value="goodsStsfdgSelngManage"/>
						</c:url>
						<a class="collapse-item" href="<c:out value="${goodsStsfdgSelngManageUrl}"/>" data-menu-id="goodsStsfdgSelngManage">만족도순위</a>
					</div>
					<div class="bg-white py-2 collapse-inner rounded">
						<h6 class="collapse-header">고객분석</h6>
						<c:url var="cstmrWeekSelngManageUrl" value="/decms/shop/stats/cstmrWeekManage.do">
							<c:param name="menuId" value="cstmrWeekSelngManage"/>
						</c:url>
						<a class="collapse-item" href="<c:out value="${cstmrWeekSelngManageUrl }"/>" data-menu-id="cstmrWeekSelngManage">요일별분석</a>
						<c:url var="cstmrHourSelngManageUrl" value="/decms/shop/stats/cstmrHourManage.do">
							<c:param name="menuId" value="cstmrHourSelngManage"/>
						</c:url>
						<a class="collapse-item" href="<c:out value="${cstmrHourSelngManageUrl }"/>" data-menu-id="cstmrHourSelngManage">시간대별분석</a>
						<c:url var="cstmrAreaSelngManageUrl" value="/decms/shop/stats/cstmrAreaManage.do">
							<c:param name="menuId" value="cstmrAreaSelngManage"/>
						</c:url>
						<a class="collapse-item" href="<c:out value="${cstmrAreaSelngManageUrl }"/>" data-menu-id="cstmrAreaSelngManage">배송지역별분석</a>
						<c:url var="cstmrEzpointSelngManageUrl" value="/decms/shop/stats/cstmrEzpointManage.do">
							<c:param name="menuId" value="cstmrEzpointSelngManage"/>
						</c:url>
						<a class="collapse-item" href="<c:out value="${cstmrEzpointSelngManageUrl }"/>" data-menu-id="cstmrEzpointSelngManage">Ez포인트사용분석</a>
					</div>
				</div>
				</c:if>
				<a class="nav-link collapsed" href="#" data-toggle="collapse" data-target="#collapseManage" aria-expanded="true" aria-controls="collapseManage">
					<i class="fas fa-fw fa-cog"></i>
					<span>운영관리</span>
				</a>
				<div id="collapseManage" class="collapse" aria-labelledby="headingTwo" data-parent="#accordionSidebar">
					<div class="bg-white py-2 collapse-inner rounded">
						<h6 class="collapse-header">고객대응</h6>
						<c:url var="reviewManageUrl" value="/decms/shop/goods/review/reviewManage.do">
							<c:param name="menuId" value="reviewManage"/>
						</c:url>
						<a class="collapse-item" href="<c:out value="${reviewManageUrl }"/>" data-menu-id="reviewManage">상품평관리</a>
						<c:url var="qainfoManageUrl" value="/decms/qainfo/qainfoManage.do">
							<c:param name="menuId" value="qainfoManage"/>
							<c:param name="qaSeCode" value="goods"/>
						</c:url>
						<a class="collapse-item" href="<c:out value="${qainfoManageUrl }"/>" data-menu-id="qainfoManage">상품Q&amp;A</a>
						<c:if test="${fn:contains(USER_ROLE,'ROLE_EMPLOYEE')}">
							<c:url var="siteFaqManageUrl" value="/decms/faq/faqManage.do">
								<c:param name="menuId" value="siteFaqManage"/>
								<c:param name="searchFaqClCode" value="SITE"/>
							</c:url>
							<a class="collapse-item" href="<c:out value="${siteFaqManageUrl }"/>" data-menu-id="siteFaqManage">FAQ</a>
							<c:url var="siteQainfoManageUrl" value="/decms/qainfo/qainfoManage.do">
								<c:param name="menuId" value="siteQainfoManage"/>
								<c:param name="qaSeCode" value="SITE"/>
							</c:url>
							<a class="collapse-item" href="<c:out value="${siteQainfoManageUrl }"/>" data-menu-id="siteQainfoManage">1:1문의</a>
							<c:url var="siteNoticeManageUrl" value="/decms/board/article/boardList.do">
								<c:param name="menuId" value="siteNoticeManage"/>
								<c:param name="bbsId" value="BBSMSTR_000000000000"/>
							</c:url>
							<a class="collapse-item" href="<c:out value="${siteNoticeManageUrl }"/>" data-menu-id="siteNoticeManage">공지사항관리</a>
						</c:if>
					</div>
					<c:choose>
						<c:when test="${fn:contains(USER_ROLE,'ROLE_EMPLOYEE')}">
							<div class="bg-white py-2 collapse-inner rounded">
								<h6 class="collapse-header">CP대응</h6>
								<c:url var="cpNoticeManageUrl" value="/decms/board/article/boardList.do">
									<c:param name="menuId" value="cpNoticeManage"/>
									<c:param name="bbsId" value="BBSMSTR_0000000000CP"/>
								</c:url>
								<a class="collapse-item" href="<c:out value="${cpNoticeManageUrl }"/>" data-menu-id="cpNoticeManage">CP공지</a>
								<c:url var="cpQainfoManageUrl" value="/decms/qainfo/qainfoManage.do">
									<c:param name="menuId" value="cpQainfoManage"/>
									<c:param name="qaSeCode" value="CP"/>
								</c:url>
								<a class="collapse-item" href="<c:out value="${cpQainfoManageUrl }"/>" data-menu-id="cpQainfoManage">고객처리현황</a>
								<c:url var="cpFaqManageUrl" value="/decms/faq/faqManage.do">
									<c:param name="menuId" value="cpFaqManage"/>
									<c:param name="searchFaqClCode" value="CP"/>
								</c:url>
								<a class="collapse-item" href="<c:out value="${cpFaqManageUrl }"/>" data-menu-id="cpFaqManage">CP FAQ</a>
							</div>
							<div class="bg-white py-2 collapse-inner rounded">
								<h6 class="collapse-header">운영대응</h6>
								<c:url var="cmpnyInqryManageUrl" value="/decms/cmpny/inqryManage.do">
									<c:param name="menuId" value="cmpnyInqryManage"/>
<%-- 									<c:param name="bbsId" value="BBSMSTR_0000000000CP"/> --%>
								</c:url>
								<a class="collapse-item" href="${cmpnyInqryManageUrl}" data-menu-id="cmpnyInqryManage">입점문의관리</a>
								<a class="collapse-item" href="#" data-menu-id="x">관리자관리</a>
								<a class="collapse-item" href="#" data-menu-id="x">쿠폰관리</a>
								<c:url var="eventManageUrl" value="/decms/event/eventManage.do">
									<c:param name="menuId" value="eventManage"/>
<%-- 									<c:param name="bbsId" value="BBSMSTR_0000000000CP"/> --%>
								</c:url>
								<a class="collapse-item" href="${eventManageUrl}" data-menu-id="eventManage">이벤트관리</a>
							</div>
							<div class="bg-white py-2 collapse-inner rounded">
								<h6 class="collapse-header">SMS알림관리</h6>
								<a class="collapse-item" href="#" data-menu-id="x">SMS문구</a>
								<a class="collapse-item" href="#" data-menu-id="x">발송내역</a>
							</div>
						</c:when>
						<c:otherwise>
							<div class="bg-white py-2 collapse-inner rounded">
								<h6 class="collapse-header">본사대응</h6>
								<c:url var="headOfficeNoticeManageUrl" value="/decms/board/article/boardList.do">
									<c:param name="menuId" value="headOfficeNoticeManage"/>
									<c:param name="bbsId" value="BBSMSTR_0000000000CP"/>
								</c:url>
								<a class="collapse-item" href="<c:out value="${headOfficeNoticeManageUrl }"/>" data-menu-id="headOfficeNoticeManage">본사공지</a>
								<c:url var="cpQainfoManageUrl" value="/decms/qainfo/qainfoManage.do">
									<c:param name="menuId" value="cpQainfoManage"/>
									<c:param name="qaSeCode" value="CP"/>
								</c:url>
								<a class="collapse-item" href="<c:out value="${cpQainfoManageUrl }"/>" data-menu-id="cpQainfoManage">본사Q&amp;A</a>
								<c:url var="faqManageUrl" value="/decms/faq/faqCpList.do">
									<c:param name="menuId" value="cpFaqManage"/>
									<c:param name="searchFaqClCode" value="CP"/>
								</c:url>
								<a class="collapse-item" href="<c:out value="${faqManageUrl }"/>" data-menu-id="cpFaqManage">FAQ</a>

							</div>
							<div class="bg-white py-2 collapse-inner rounded">
								<h6 class="collapse-header">운영관리</h6>
								<a class="collapse-item" href="#" data-menu-id="x">알림관리</a>
							</div>
						</c:otherwise>
					</c:choose>
				</div>
					
					
				<c:if test="${fn:contains(USER_ROLE,'ROLE_ADMIN')}">
					<a class="nav-link collapsed" href="#" data-toggle="collapse" data-target="#collapseSystem" aria-expanded="true" aria-controls="collapseSystem">
						<i class="fas fa-fw fa-cog"></i>
						<span>CMS 관리</span>
					</a>
					<div id="collapseSystem" class="collapse" aria-labelledby="headingTwo" data-parent="#accordionSidebar">
						<div class="bg-white py-2 collapse-inner rounded">
							<c:url var="siteManageUrl" value="/decms/site/info/siteManage.do">
								<c:param name="menuId" value="siteManage"/>
							</c:url>
							<a class="collapse-item" href="<c:out value="${siteManageUrl }"/>" data-menu-id="siteManage">사이트</a>
						</div>

						<div class="bg-white py-2 collapse-inner rounded">
							<h6 class="collapse-header">프로그램관리</h6>
							<c:url var="boardMasterUrl" value="/decms/board/master/boardMasterManage.do">
								<c:param name="menuId" value="boardMaster"/>
							</c:url>
							<a class="collapse-item" href="<c:out value="${boardMasterUrl }"/>" data-menu-id="boardMaster">게시판</a>
							<c:url var="bannerManageUrl" value="/decms/banner/bannerManage.do">
								<c:param name="menuId" value="bannerManage"/>
							</c:url>
							<a class="collapse-item" href="<c:out value="${bannerManageUrl }"/>" data-menu-id="bannerManage">배너</a>
							<c:url var="popupManageUrl" value="/decms/popup/popupManage.do">
								<c:param name="menuId" value="popupManage"/>
							</c:url>
							<a class="collapse-item" href="<c:out value="${popupManageUrl }"/>" data-menu-id="popupManage">팝업</a>
						</div>

						<div class="bg-white py-2 collapse-inner rounded">
							<h6 class="collapse-header">회원관리</h6>
							<c:url var="mberManageUserUrl" value="/decms/mber/mberManage.do">
								<c:param name="menuId" value="mberManageUser"/>
								<c:param name="searchUserType" value="USER"/>
							</c:url>
							<a class="collapse-item" href="<c:out value="${mberManageUserUrl }"/>" data-menu-id="mberManageUser">일반회원</a>
							<c:url var="mberManageShopUrl" value="/decms/mber/mberManage.do">
								<c:param name="menuId" value="mberManageShop"/>
								<c:param name="searchUserType" value="SHOP"/>
							</c:url>
							<a class="collapse-item" href="<c:out value="${mberManageShopUrl }"/>" data-menu-id="mberManageShop">업체회원</a>
							<c:url var="mberManageAdminUrl" value="/decms/mber/mberManage.do">
								<c:param name="menuId" value="mberManageAdmin"/>
								<c:param name="searchUserType" value="ADMIN"/>
							</c:url>
							<a class="collapse-item" href="<c:out value="${mberManageAdminUrl }"/>" data-menu-id="mberManageAdmin">관리자</a>
						</div>
						
<!-- 						<div class="bg-white py-2 collapse-inner rounded"> -->
<!-- 							<h6 class="collapse-header">메시지관리</h6> -->
<%-- 							<c:url var="alimtalkUrl" value="/decms/alimtalk/alimtalkManage.do"> --%>
<%-- 								<c:param name="menuId" value="alimtalkMaster"/> --%>
<%-- 							</c:url> --%>
<%-- 							<a class="collapse-item" href="<c:out value="${alimtalkUrl }"/>" data-menu-id="alimtalkMaster">알림톡 발송내역</a> --%>
<!-- 						</div> -->
						
						
						<div class="bg-white py-2 collapse-inner rounded">
							<h6 class="collapse-header">코드</h6>
							<c:url var="commonCodeManageUrl" value="/decms/code/commonCodeManage.do">
								<c:param name="menuId" value="commonCodeManage"/>
							</c:url>
							<a class="collapse-item" href="<c:url value="${commonCodeManageUrl }"/>" data-menu-id="commonCodeManage">공통코드</a>
							<c:url var="bankManageUrl" value="/decms/shop/bank/bankManage.do">
								<c:param name="menuId" value="bankManage"/>
							</c:url>
							<a class="collapse-item" href="<c:url value="${bankManageUrl }"/>" data-menu-id="bankManage">은행관리</a>
							<c:url var="hdryCmpnyManageUrl" value="/decms/shop/hdry/hdryCmpnyManage.do">
								<c:param name="menuId" value="hdryCmpnyManage"/>
							</c:url>
							<a class="collapse-item" href="<c:url value="${hdryCmpnyManageUrl }"/>" data-menu-id="hdryCmpnyManage">택배사관리</a>
							<c:url var="idsrtsManageUrl" value="/decms/shop/idsrts/idsrtsManage.do">
								<c:param name="menuId" value="idsrtsManage"/>
							</c:url>
							<a class="collapse-item" href="<c:url value="${idsrtsManageUrl }"/>" data-menu-id="idsrtsManage">도서산간지역관리</a>
						</div>

						<div class="bg-white py-2 collapse-inner rounded">
							<h6 class="collapse-header">통계</h6>
							<c:url var="conectLogManageUrl" value="/decms/system/log/conectLogManage.do">
								<c:param name="menuId" value="conectLogManage"/>
							</c:url>
							<a class="collapse-item" href="<c:out value="${conectLogManageUrl }"/>" data-menu-id="conectLogManage">접속통계</a>
							<c:url var="loginLogManageUrl" value="/decms/system/log/loginLogManage.do">
								<c:param name="menuId" value="loginLogManage"/>
							</c:url>
							<a class="collapse-item" href="<c:out value="${loginLogManageUrl }"/>" data-menu-id="loginLogManage">로그인 로그</a>
							<c:url var="bbsSummaryUrl" value="/decms/system/sts/bst/bbsSummary.do">
								<c:param name="menuId" value="bbsSummary"/>
							</c:url>
							<a class="collapse-item" href="<c:out value="${bbsSummaryUrl }"/>" data-menu-id="bbsSummary">게시물 통계</a>
						</div>
					</div>
				</c:if>
			</li>
			<hr class="sidebar-divider d-none d-md-block"/>

			<div class="text-center d-none d-md-inline">
				<button class="rounded-circle border-0" id="sidebarToggle"></button>
			</div>
		</ul>
		<div id="content-wrapper" class="d-flex flex-column">
			<div id="content">
				<nav class="navbar navbar-expand navbar-light bg-white topbar mb-2 static-top shadow">
					<button id="sidebarToggleTop" class="btn btn-link d-md-none rounded-circle mr-3">
						<i class="fa fa-bars"></i>
					</button>
					<ul class="navbar-nav ml-auto">
						<li class="nav-item dropdown no-arrow">
							<a class="nav-link dropdown-toggle" href="#" id="userDropdown" role="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
								<span class="mr-2 d-none d-lg-inline text-gray-600 small">${USER_NAME } (${USER_ID})</span>
								<img class="img-profile rounded-circle" src="/resources/decms/common/image/user.png"/>
							</a>
							<div class="dropdown-menu dropdown-menu-right shadow animated--grow-in" aria-labelledby="userDropdown">
								<a class="dropdown-item" href="${CTX_ROOT}/user/sign/logout.do" data-event="confirm" data-message="로그아웃 하시겠습니까?">
									<i class="fas fa-sign-out-alt fa-sm fa-fw mr-2 text-gray-400"></i> Logout
								</a>
							</div>
						</li>

					</ul>
				</nav>

				<section id="content-view">
					<sitemesh:write property="body"/>
				</section>
			</div>

			<footer class="sticky-footer bg-white">
				<div class="container my-auto">
					<div class="copyright text-center my-auto">
						<span>Copyright &copy; CMS 2020</span>
					</div>
				</div>
			</footer>

		</div>
	</div>
</c:when>
<c:otherwise>
	<sitemesh:write property="body"/>
</c:otherwise>
</c:choose>
	<script src="${CTX_ROOT}/resources/lib/jquery/jquery.min.js"></script>
	<script src="${CTX_ROOT}/resources/lib/axios/axios.min.js"></script>
	<script src="${CTX_ROOT}/resources/lib/jquery-form/jquery.form.min.js"></script>
	<script src="${CTX_ROOT}/resources/lib/popperjs/umd/popper.min.js"></script>
	<script src="${CTX_ROOT}/resources/lib/bootstrap/js/bootstrap.min.js"></script>
	<script src="${CTX_ROOT}/resources/lib/jquery.easing/jquery.easing.min.js"></script>
	<script src="${CTX_ROOT}/resources/lib/bootbox/bootbox.all.min.js"></script>
	<script src="${CTX_ROOT}/resources/decms/template/js/sb-admin-2.min.js"></script>
	<script src="${CTX_ROOT}/resources/lib/summernote/summernote-bs4.min.js"></script>
	<script src="${CTX_ROOT}/resources/lib/summernote/lang/summernote-ko-KR.min.js"></script>
	<script src="${CTX_ROOT}/resources/lib/moment/moment-with-locales.min.js"></script>
	<script src="${CTX_ROOT}/resources/decms/common/js/script.js?20201229"></script>
	<sitemesh:write property="javascript"/>

	<div class="loading" style="display:none;">
		<p><img src="/resources/decms/common/image/loading.gif" alt="loading"/><br/>잠시만 기다리세요.</p>
	</div>
</body>
</html>