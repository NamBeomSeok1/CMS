<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/modoo/common/commonTagLibs.jsp"%>
<c:set var="CTX_ROOT" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html lang="ko">
<head>
    <title><sitemesh:write property="title"/></title>
    <meta charset="utf-8"/>
	<meta http-equiv="X-UA-Compatible" content="IE=edge" />
    <meta name="viewport" content="width=device-width,initial-scale=1.0,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no"/>
    <link rel="canonical" href="${HTTP}://${pageContext.request.serverName}${pageContext.request.contextPath}${requestScope['javax.servlet.forward.request_uri']}"/>
	<meta name="robots" content="index, follow"/>
	<meta name="description" content="폭스에듀의 스토어입니다. "/>
	<meta property="og:type" content="website"> 
	<meta property="og:title" content="FOXEDU STORE">
	<meta property="og:description" content="폭스에듀의 스토어입니다.">
	<meta property="og:image" content="${BASE_URL }/resources/front/site/${SITE_ID }/image/logo/favicon/apple-icon-180x180.png">
	<meta property="og:url" content="${BASE_URL}">
	
	<sitemesh:write property="metatag"/>
	<link rel="apple-touch-icon" sizes="57x57" href="${BASE_URL}/resources/front/site/${SITE_ID }/image/logo/favicon/apple-icon-57x57.png"/>
	<link rel="apple-touch-icon" sizes="60x60" href="${BASE_URL }/resources/front/site/${SITE_ID }/image/logo/favicon/apple-icon-60x60.png"/>
	<link rel="apple-touch-icon" sizes="72x72" href="${BASE_URL }/resources/front/site/${SITE_ID }/image/logo/favicon/apple-icon-72x72.png"/>
	<link rel="apple-touch-icon" sizes="76x76" href="${BASE_URL }/resources/front/site/${SITE_ID }/image/logo/favicon/apple-icon-76x76.png"/>
	<link rel="apple-touch-icon" sizes="114x114" href="${BASE_URL }/resources/front/site/${SITE_ID }/image/logo/favicon/apple-icon-114x114.png"/>
	<link rel="apple-touch-icon" sizes="120x120" href="${BASE_URL }/resources/front/site/${SITE_ID }/image/logo/favicon/apple-icon-120x120.png"/>
	<link rel="apple-touch-icon" sizes="144x144" href="${BASE_URL }/resources/front/site/${SITE_ID }/image/logo/favicon/apple-icon-144x144.png"/>
	<link rel="apple-touch-icon" sizes="152x152" href="${BASE_URL }/resources/front/site/${SITE_ID }/image/logo/favicon/apple-icon-152x152.png"/>
	<link rel="apple-touch-icon" sizes="180x180" href="${BASE_URL }/resources/front/site/${SITE_ID }/image/logo/favicon/apple-icon-180x180.png"/>
	<link rel="icon" type="image/png" sizes="192x192"  href="${BASE_URL }/resources/front/site/${SITE_ID }/image/logo/favicon/android-icon-192x192.png"/>
	<link rel="icon" type="image/png" sizes="32x32" href="${BASE_URL }/resources/front/site/${SITE_ID }/image/logo/favicon/favicon-32x32.png"/>
	<link rel="icon" type="image/png" sizes="96x96" href="${BASE_URL }/resources/front/site/${SITE_ID }/image/logo/favicon/favicon-96x96.png"/>
	<link rel="icon" type="image/png" sizes="16x16" href="${BASE_URL }/resources/front/site/${SITE_ID }/image/logo/favicon/favicon-16x16.png"/>
	<link rel="manifest" href="${BASE_URL }/resources/front/site/${SITE_ID }/image/logo/favicon/manifest.json"/>
	<meta name="msapplication-TileColor" content="#ffffff"/>
	<meta name="msapplication-TileImage" content="${BASE_URL }/resources/front/site/${SITE_ID }/image/logo/favicon/ms-icon-144x144.png"/>
	<meta name="theme-color" content="#ffffff"/>
	<meta name="NaverBot" content="All"/>
	<meta name="NaverBot" content="index,follow"/>
	<meta name="Yeti" content="All"/>
	<meta name="Yeti" content="index,follow"/>
	<meta name="naver-site-verification" content="94eb5cb3e03b4bbdde3ae93c8ac41fa493a6ee0d" />
	<meta name="google-site-verification" content="x-3-24IR-l_kz8apC5VANnT7XxSFRaWSo7fkIFjt4J8" />
	<!--[if lt IE 9]><script src="../../../resources/front/site/SITE_00000/js/html5.js"></script><![endif]-->
    <link rel="stylesheet" type="text/css" href="${CTX_ROOT}/resources/front/site/${SITE_ID }/css/style.css"/>
    <%-- <link rel="stylesheet" type="text/css" href="${CTX_ROOT}/resources/front/site/${SITE_ID }/css/site.css"/> --%>
    <script> var CTX_ROOT = '${CTX_ROOT}'; <%-- Document ROOT PATH --%> </script>
    <c:import url="/embed/shop/searchKeywordJs.do" charEncoding="utf-8"/>
    <c:choose>
    <c:when test="${CMS_MODE eq 'DEV' }"> <script> debug = function(str) { console.log(str); }; </script> </c:when>
    <c:otherwise> <script> debug = function(str) { }; </script> </c:otherwise>
    </c:choose>
    <sitemesh:write property="head"/>
</head>
<body>
    <div class="accessibility">
        <a href="#contents">콘텐츠 바로가기</a>
    </div>
    <header class="site-header">
		<c:import url="/header.do" charEncoding="utf-8">
			<c:param name="IS_MAIN" value="Y"/>
		</c:import>
    </header>
	
	<c:set var="baseURL" value="${fn:replace(req.requestURL, fn:substring(req.requestURI, 0, fn:length(req.requestURI)), req.contextPath)}" />
	<c:set var="path" value="${requestScope['javax.servlet.forward.servlet_path']}" /> 
	
	<div class="site-body" role="main">
		<div class="contents" id="contents">
			<sitemesh:write property="body"/>
		</div>
		<a href="#" class="btn-top"><span class="txt-hide">처음으로</span></a>
	</div><!-- //site-body -->
	
	<!--문의등록 팝업-->
	<c:import url="${CTX_ROOT}/cmpny/inqryForm.do"></c:import>
	
	<footer class="site-footer">
		<c:import url="/footer.do" charEncoding="utf-8">
			<c:param name="IS_MAIN" value="Y"/>
		</c:import>
	</footer>
	
	<div class="pop-brand">
		<ul class="brand-list">
			<c:import url="/embed/shop/brandMenuList.do" charEncoding="utf-8"/>
		</ul>
		<button type="button" class="btn-brand-close"><span class="txt-hide">닫기</span></button>
	</div>
	
	<div class="popup-alert" data-popup="modooAlert">
		<div class="pop-header">
			<h1 class="modooAlert-title">&nbsp;</h1>
		</div>
		<div class="pop-body">
			<p class="modooAlert-content"></p>
		</div>
		<div class="pop-footer">
			<button type="button" class="spot2 modooAlert-ok" >확인</button>
		</div>
	</div>
	
	<div class="popup-alert zindex-pop" data-popup="modooAlert2">
		<div class="pop-header">
			<h1 class="modooAlert2-title">&nbsp;</h1>
		</div>
		<div class="pop-body">
			<p class="modooAlert2-content"></p>
		</div>
		<div class="pop-footer">
			<button type="button" class="spot2 modooAlert2-ok" >확인</button>
		</div>
	</div>
	
	<div class="popup-alert" data-popup="modooConfirm">
		<div class="pop-header">
			<h1 class="modooConfirm-title">&nbsp;</h1>
		</div>
		<div class="pop-body">
			<p class="modooConfirm-content"></p>
		</div>
		<div class="pop-footer">
			<button type="button" class="modooConfirm-cancel">취소</button>
			<button type="button" class="spot2 modooConfirm-ok">확인</button>
		</div>
	</div>
	
    <!--토스트 메세지-->
    <div class="toast">
        <div></div>
    </div>
	<div class="dim"></div>
	<div class="loading"><span>로딩중...</span></div>

	<sitemesh:write property="template"/>
	
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
    <script src="${CTX_ROOT}/resources/front/site/${SITE_ID }/js/cmpnyInqry.js"></script>
    <script src="${CTX_ROOT}/resources/front/site/${SITE_ID }/js/site.js"></script>
    <script src="https://developers.kakao.com/sdk/js/kakao.min.js"></script>
	<script type="text/javascript">
		Kakao.init("${KAKAO_KEY}");
	</script>
    <c:if test="${not empty USER_ID }"> <%-- 로그아웃(모든 페이지에서 로그아웃 처리 가능 하도록 설정) --%>
		<script src="${CTX_ROOT}/user/my/myInfoScript.do"></script>
	</c:if>
    <sitemesh:write property="javascript"/>
    
	<%--POPUP 테스트 --%>
	<c:if test="${not empty popupList }">
		<c:forEach var="popup" items="${popupList }">
			<c:if test="${popup.actvtyAt eq 'Y'}">
				<c:choose>
					<c:when test="${popup.popupSeCode eq 'LAYER' }">
						<div class="popup-item" style="display:none;" data-popup="${popup.popupId }" data-popup-id="${popup.popupId }">
							<div class="pop-body">
								<c:choose>
								<c:when test="${not empty popup.popupLink and popup.popupLink ne '#'}">
									<div class="item-img-area">
										<a href="${popup.popupLink }">
											<img src="${popup.popupImgPath }" alt="${popup.popupSj }"/>
										</a>
									</div>
									<c:if test="${not empty popup.popupCn }">
										<div class="item-txt-area">
											<a href="${popup.popupLink }" style="color:#f5f5f5;">
<%-- 												<c:out value="${popup.popupCn }" escapeXml="false"/> --%>
												<modoo:crlf content="${popup.popupCn }"></modoo:crlf>
											</a>
										</div>
									</c:if>
								</c:when>
								<c:otherwise>
									<div class="item-img-area">
										<img src="${popup.popupImgPath }" alt="${popup.popupSj }"/>
									</div>
									<c:if test="${not empty popup.popupCn }">
										<div class="item-txt-area">
											<c:out value="${popup.popupCn }" escapeXml="false"/>
										</div>
									</c:if>
								</c:otherwise>
								</c:choose>
							</div>
							<div class="pop-footer ">
								<label class="m-0 mr-5" style="vertical-align: middle;">
									<input type="checkbox" class="popup-today-check" value="${popup.popupId }"/>오늘 하루 열지 않기
								</label>
								<button type="button" class="btn-pop-close btn-close" data-popup-close="${popup.popupId }" data-id="${popup.popupId}">닫기</button>
							</div>
						</div>
					</c:when>
					<c:when test="${popup.popupSeCode eq 'WINDOW' }">
						<c:url var="popupUrl" value="/popup/windowPopup.do">
							<c:param name="popupId" value="${popup.popupId }"/>
						</c:url>
						<script>
							noticePopup('${popup.popupId}','<c:out value="${popupUrl}"/>', 'POPUP_${status.index}',${popup.popupWidth}, ${popup.popupHeight}, ${popup.popupLeft}, ${popup.popupTop});
						</script>
					</c:when>
				</c:choose>
			</c:if>
		</c:forEach>
	</c:if>

</body>
</html>
