<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/modoo/common/commonTagLibs.jsp"%>
<!DOCTYPE html>
<html lang="ko">

<head>
<script async src="https://www.googletagmanager.com/gtag/js?id=AW-448483818"></script>
		<script>
	  window.dataLayer = window.dataLayer || [];
	  function gtag(){dataLayer.push(arguments);}
	  gtag('js', new Date());
	
	  gtag('config', 'AW-448483818');
</script>
	<title>FOXEDU STORE</title>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<meta name="Author" content="">
	<meta name="Keywords" content="">
	<meta name="Description" content="">
	<meta http-equiv="X-UA-Compatible" content="IE=edge" />
	<meta name="viewport"
		  content="width=device-width,initial-scale=1.0,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no" />
	<!--[if lt IE 9]><script src="/resources/front/site/SITE_00000/js/html5.js"></script><![endif]-->
	<link href="/resources/front/site/SITE_00000/css/swiper.css" rel="stylesheet" />
	<link href="/resources/front/site/SITE_00000/css/style.css" rel="stylesheet" />
	<style>
		.popup .pop-body {border:1px solid #aaa; border-top:none;}
	</style>
</head>

<body>
	<div class="bg-gr table">	
		<section class="login">
			<form:form modelAttribute="mberAgre" name="mberAgreForm" method="post" action="/prtnr/sso/mberAgre.do">
				<div class="login-wrap">
					<div class="login-header">
						<h1><img src="/resources/front/site/SITE_00000/image/logo/logo.svg" alt="FOXEDU STORE" /></h1>
					</div>
					<div class="login-body">
						<div class="agree-all">
							<label><input type="checkbox" id="checkAll"/><strong>전체 동의하기</strong></label>
							<p class="msg">환영합니다. 원활한 서비스 이용을 위해 동의해주세요.</p>
						</div>
		 				<ul class="agree-list">
							<li>
								<%-- 이거는 왜 들어가는거지? --%>
								<label><input type="checkbox" id="ageOver" name="ageOver" class="chk"/>[필수] 만 14세 이상입니다</label>
							</li>
							<li>
								<label><form:checkbox path="termsCondAt" value="Y" cssClass="chk"/>[필수] 서비스 이용약관 동의</label>
								<p class="msg"><strong>본 약관에는 마케팅 정보 수신에 대한 동의에 관한 내용</strong>이 포함되어 있으며, '설정'에서 수신 거부로 변경할 수 있습니다.</p>
								<button type="button" class="btnLink" data-src="${CTX_ROOT }/terms.do?menuNm=use&title=이용약관">보기 <i class="ico-arr-r sm gr back" aria-hidden="true"></i></button>
							</li>
							<li>
								<label><form:checkbox path="privInfoAt" value="Y" cssClass="chk"/>[필수] 개인정보 수집 및 이용 동의</label>
								<p class="msg">서비스 이용과 관련하여 수집 이용하는 개인정보를 안내해드립니다.</p>
								<button type="button" class="btnLink" data-src="${CTX_ROOT }/terms.do?menuNm=info&title=개인정보처리방침">보기 <i class="ico-arr-r sm gr back" aria-hidden="true"></i></button>
							</li>
							<%-- <li>
								<label><form:checkbox path="eventMarktAt" value="Y" cssClass="chk"/>[선택] 이벤트 마케팅 수신동의</label>
								<p class="msg">이벤트 마케팅 수신동의~~~</p>
								<button type="button">보기 <i class="ico-arr-r sm gr back" aria-hidden="true"></i></button>
							</li> --%>
							<%-- 이 부분은 왜 있는거지?? 
							<li class="bg">
								<cite>[선택] 선택 제공 항목</cite>
								<ul>
									<li>
										<label><input type="checkbox" />CI(연계정보)</label>
									</li>
									<li>
										<label><input type="checkbox" />배송지정보(수령인명, 배송지 주소, 전화번호)</label>
									</li>
								</ul>
							</li>
							 --%>
						</ul>
						<div class="btn-area">
							<button type="submit" class="btn-lg width">시작하기</button>
						</div>
					</div>
				</div>
		</form:form>
	</section>
	</div>
	
	<div class="popup" data-popup="pop-agree">
		<div class="pop-header">
			<h1>약관 동의</h1>
			<button type="button" class="btn-pop-close" data-popup-close="pop-agree">닫기</button>
		</div>
		<div class="pop-body">
			<div id="popMessage">-</div>
			<div class="btn-table-area">
				<button type="button" class="btn-lg spot width btn-pop-close" data-popup-close="pop-agree">닫기</button>
			</div>
		</div>
	</div>

	<javascript>
		<script src="/resources/lib/jquery/jquery.js"></script>
		<script src="/resources/lib/jquery.easing/jquery.easing.js"></script>
		<script src="/resources/lib/jquery-ui/jquery-ui.min.js"></script>
		<script src="/resources/front/site/SITE_00000/js/ssm.min.js"></script>
		<script src="/resources/front/site/SITE_00000/js/swiper.min.js"></script>
		<script src="/resources/front/site/SITE_00000/js/ScrollMagic.min.js"></script>
		<script src="/resources/front/site/SITE_00000/js/TweenMax.min.js"></script>
		<script src="/resources/front/site/SITE_00000/js/animation.gsap.min.js"></script>
		<script src="/resources/front/site/SITE_00000/js/common.js"></script>
		<script src="/resources/front/ptnr/sso/common/agree.js?202011"></script>
 	</javascript>	
</body>

</html>