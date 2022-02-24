<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/modoo/common/commonTagLibs.jsp"%>
<!DOCTYPE html>
<html>
<head>
	<!-- <meta charset="UTF-8"> -->
	<title>FOXEDU STORE</title>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<meta http-equiv="X-UA-Compatible" content="IE=edge" />
    <meta name="viewport" content="width=device-width,initial-scale=1.0,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no"/>
    <link rel="canonical" href="${pageContext.request.scheme}://${pageContext.request.serverName}${pageContext.request.contextPath}${requestScope['javax.servlet.forward.request_uri']}"/>
	<meta name="robots" content="index, follow"/>
	<meta name="description" content="정기구독 서비스의 모든 것. FOXEDU STORE"/>
	<meta property="og:type" content="website"/>
	<meta property="og:title" content="FOXEDU STORE ${pageTitle }"/>
	<meta property="og:description" content="정기구독 서비스의 모든 것. FOXEDU STORE"/>
	<meta property="og:image" content="${pageContext.request.scheme}://${pageContext.request.serverName}/resources/front/site/${SITE_ID }/image/logo/logo.png"/>
	<meta property="og:url" content="${pageContext.request.scheme}://${pageContext.request.serverName}"/>
	<link rel="apple-touch-icon" sizes="57x57" href="${CTX_ROOT }/resources/front/site/${SITE_ID }/image/logo/favicon/apple-icon-57x57.png"/>
	<link rel="apple-touch-icon" sizes="60x60" href="${CTX_ROOT }/resources/front/site/${SITE_ID }/image/logo/favicon/apple-icon-60x60.png"/>
	<link rel="apple-touch-icon" sizes="72x72" href="${CTX_ROOT }/resources/front/site/${SITE_ID }/image/logo/favicon/apple-icon-72x72.png"/>
	<link rel="apple-touch-icon" sizes="76x76" href="${CTX_ROOT }/resources/front/site/${SITE_ID }/image/logo/favicon/apple-icon-76x76.png"/>
	<link rel="apple-touch-icon" sizes="114x114" href="${CTX_ROOT }/resources/front/site/${SITE_ID }/image/logo/favicon/apple-icon-114x114.png"/>
	<link rel="apple-touch-icon" sizes="120x120" href="${CTX_ROOT }/resources/front/site/${SITE_ID }/image/logo/favicon/apple-icon-120x120.png"/>
	<link rel="apple-touch-icon" sizes="144x144" href="${CTX_ROOT }/resources/front/site/${SITE_ID }/image/logo/favicon/apple-icon-144x144.png"/>
	<link rel="apple-touch-icon" sizes="152x152" href="${CTX_ROOT }/resources/front/site/${SITE_ID }/image/logo/favicon/apple-icon-152x152.png"/>
	<link rel="apple-touch-icon" sizes="180x180" href="${CTX_ROOT }/resources/front/site/${SITE_ID }/image/logo/favicon/apple-icon-180x180.png"/>
	<link rel="icon" type="image/png" sizes="192x192"  href="${CTX_ROOT }/resources/front/site/${SITE_ID }/image/logo/favicon/android-icon-192x192.png"/>
	<link rel="icon" type="image/png" sizes="32x32" href="${CTX_ROOT }/resources/front/site/${SITE_ID }/image/logo/favicon/favicon-32x32.png"/>
	<link rel="icon" type="image/png" sizes="96x96" href="${CTX_ROOT }/resources/front/site/${SITE_ID }/image/logo/favicon/favicon-96x96.png"/>
	<link rel="icon" type="image/png" sizes="16x16" href="${CTX_ROOT }/resources/front/site/${SITE_ID }/image/logo/favicon/favicon-16x16.png"/>
	<link rel="manifest" href="${CTX_ROOT }/resources/front/site/${SITE_ID }/image/logo/favicon/manifest.json"/>
	<meta name="msapplication-TileColor" content="#ffffff"/>
	<meta name="msapplication-TileImage" content="${CTX_ROOT }/resources/front/site/${SITE_ID }/image/logo/favicon/ms-icon-144x144.png"/>
	<meta name="theme-color" content="#ffffff"/>

	<%-- <!--[if lt IE 9]><script src="../../../resources/front/site/SITE_00000/js/html5.js"></script><![endif]-->
    <link rel="stylesheet" type="text/css" href="${CTX_ROOT}/resources/front/site/${SITE_ID }/css/swiper.css"/>
    <link rel="stylesheet" type="text/css" href="${CTX_ROOT}/resources/front/site/${SITE_ID }/css/style.css"/>
    <link rel="stylesheet" type="text/css" href="${CTX_ROOT}/resources/front/site/${SITE_ID }/css/site.css"/>
	<!--[if lt IE 9]><script src="../../../resources/front/site/SITE_00000/js/html5.js"></script><![endif]-->
    <link rel="stylesheet" type="text/css" href="/resources/front/site/SITE_00000/css/swiper.css"/>
    <link rel="stylesheet" type="text/css" href="/resources/front/site/SITE_00000/css/style.css"/>
    <link rel="stylesheet" type="text/css" href="/resources/front/site/SITE_00000/css/site.css"/> --%>
    <script src="/resources/lib/jquery/jquery.min.js"></script>
    <script>
    	$(document).ready(function() {
    		window.location.href='/prtnr/sso/modooIndex.do';
    	});
    </script>
</head>
<body>
	<%-- <!--<section style="text-align:center; margin:100px 0;">
		<a href="${pageContext.request.scheme}://${domain }/prtnr/sso/modooIndex.do" class="btn-lg width spot" target="_blank">FOXEDU STORE</a>
	</section>-->
	<div class="gate">
        <div class="gate-img">
            <img src="/resources/front/site/SITE_00000/image/gate/gate.jpg" alt="아직도 장보러 마느가니? 요즘은 편리한 정기구독이 대세야! FOXEDU STORE 이 세상 정기구독의 모든것 이지웰 복지몰 회원만 누릴 수 있는 합리적인 가격 혜택! 2020년 11월, 쉽고 편리한 정기구독 플랫폼이 우리 곁에 다가왔습니다. 지금 바로 이용해보세요!" class="m-none" />
            <img src="/resources/front/site/SITE_00000/image/gate/m_gate.jpg" alt="아직도 장보러 마느가니? 요즘은 편리한 정기구독이 대세야! FOXEDU STORE 이 세상 정기구독의 모든것 이지웰 복지몰 회원만 누릴 수 있는 합리적인 가격 혜택! 2020년 11월, 쉽고 편리한 정기구독 플랫폼이 우리 곁에 다가왔습니다. 지금 바로 이용해보세요!" class="m-block" />
        </div>
        <div class="gate-fnc">
            <a href="${pageContext.request.scheme}://${domain}/prtnr/sso/modooIndex.do" target="_blank">
                <img src="/resources/front/site/SITE_00000/image/gate/btn_gate.png" alt="FOXEDU STORE 바로가기" class="m-none" />
                <img src="/resources/front/site/SITE_00000/image/gate/m_btn_gate.png" alt="FOXEDU STORE 바로가기" class="m-block" />
            </a>
        </div>
    </div>

	<script src="/resources/lib/jquery/jquery.min.js"></script>
    <script src="/resources/lib/jquery.easing/jquery.easing.min.js"></script>
    <script src="/resources/lib/jquery-ui/jquery-ui.min.js"></script>
    <script src="/resources/lib/jquery-form/jquery.form.min.js"></script>
	<script src="/resources/lib/js-cookie/js.cookie.js"></script>
	<script src="/resources/front/site/SITE_00000/js/ssm.min.js"></script>
    <script src="/resources/front/site/SITE_00000/js/swiper.min.js"></script>
    <script src="/resources/front/site/SITE_00000/js/ScrollMagic.min.js"></script>
    <script src="/resources/front/site/SITE_00000/js/TweenMax.min.js"></script>
    <script src="/resources/front/site/SITE_00000/js/animation.gsap.min.js"></script>
    <script src="/resources/front/site/SITE_00000/js/common.js"></script>
    <script src="/resources/common/js/common.js"></script>
    <script src="/resources/front/site/SITE_00000/js/site.js"></script> --%>
</body>
</html>