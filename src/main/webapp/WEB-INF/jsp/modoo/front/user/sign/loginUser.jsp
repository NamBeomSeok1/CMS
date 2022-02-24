<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/modoo/common/commonTagLibs.jsp"%>

<!DOCTYPE html>
<html lang="ko">

<head>
	<title>딥레이서 관리자</title>
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
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<meta http-equiv="X-UA-Compatible" content="IE=edge" />
	<meta name="viewport" content="width=device-width,initial-scale=1.0,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no" />
	<!--[if lt IE 9]><script src="/resources/front/site/SITE_00000/js/html5.js"></script><![endif]-->
	<link href="/resources/front/site/SITE_00000/css/swiper.css" rel="stylesheet" />
	<link href="/resources/front/site/SITE_00000/css/style.css" rel="stylesheet" />
	<c:if test="${not empty message }"> <script> alert('${message}'); </script> </c:if>
	<script> var CTX_ROOT = '${CTX_ROOT}'; <%-- Document ROOT PATH --%> </script>
    <c:import url="/embed/shop/searchKeywordJs.do" charEncoding="utf-8"/>
    <c:choose>
    <c:when test="${CMS_MODE eq 'DEV' }"> <script> debug = function(str) { console.log(str); }; </script> </c:when>
    <c:otherwise> <script> debug = function(str) { }; </script> </c:otherwise>
    </c:choose>
</head>

<body>
	<c:choose>
		<%-- 
		<c:when test="${not empty USER_ID }">
			${USER_NAME }님 현재 로그인 되어 있습니다.<br/> 추후 기획의도에 따라 수정 및 삭제 해야함.
			<a href="${CTX_ROOT }/user/sign/logout.do" class="btn">로그아웃</a>
		</c:when>
		 --%>
		<c:when test="${loginVO.loginType eq 'id'}">
			<div class="bg-gr table">
				<section class="login">
					<div class="login-wrap">
						<fieldset class="login-area mb10">
							<p class="login-txt"><em class="spot">딥레이서 관리자</em></p>
						</fieldset>
						<form name="loginForm" method="post" action="${CTX_ROOT }/user/sign/actionLogin.do">
							<input type="hidden" name="refUrl" value="${param.refUrl }"/>
							<input type="text" name="id" value="${dev_user }" placeholder="아이디"/>
							<input type="password" name="password" value="${dev_password }" autocomplete="one-time-code" placeholder="비밀번호"/>
							
							<div class="mt10">
								<a href="${CTX_ROOT }/index.do" class="btn">취소</a>
								<button type="submit" class="btn spot">로그인</button>
							</div>
						</form>
					</div>
				</section>
			</div>
		</c:when>
		<c:otherwise>
			<div class="bg-gr table">
				<section class="login">
		            <div class="login-wrap">
		                <fieldset class="login-area">
		                    <legend>sns 로그인</legend>
		                    <h1 class="logo"><img src="/resources/front/site/SITE_00000/image/logo/logo_v.svg" alt="FOXEDU STORE" /></h1>
		                    <button type="button" id="kakao-login-btn" class="sns-kakao">카카오로 로그인</button>
						    <button type="button" id="naver-login-btn" class="sns-naver" data-href="${naverAuthUrl}">네이버로 로그인</button>
		                    <%-- <button type="button" id="customBtn" class="sns-google">구글 계정으로 로그인</button> --%>
						    <%-- <button type="button" id="apple-login-btn" class="sns-apple" style="display:none;">Apple로 로그인</button> --%>
						    <%-- <label><input type="checkbox" /> 자동로그인</label> --%>
		                </fieldset>
		            </div>
		            <form id="loginForm" name="frm" action="/user/sign/snsActionLogin.do" method="post">
					   <input type="hidden" id="userKey" name="id"/>
					   <input type="hidden" id="clientCd" name="clientCd"/>
					   <input type="hidden" id="email" name="email"/>
					   <input type="hidden" id="name" name="name"/>
					   <input type="hidden" id="sexdstn" name="sexdstn"/>
					   <input type="hidden" id="agrde" name="agrde"/>
				   </form>
		        </section>
		   </div>
		</c:otherwise>
	</c:choose>

	<div class="popup-alert" data-popup="modooAlert">
		<div class="pop-header">
			<h1 class="modooAlert-title"></h1>
		</div>
		<div class="pop-body">
			<p class="modooAlert-content"></p>
		</div>
		<div class="pop-footer">
			<button type="button" class="spot2 modooAlert-ok" >확인</button>
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
		<script src="https://apis.google.com/js/platform.js" async defer></script>
		<script src="https://developers.kakao.com/sdk/js/kakao.min.js"></script>
		<script src="/resources/common/js/common.js?v=5"></script>
		<script src="${CTX_ROOT}/resources/front/site/${SITE_ID }/js/site.js?20201111"></script>
		<script src="https://apis.google.com/js/api:client.js"></script>
		<script>
		var logoutAt = getCookie("logoutAt");
		Kakao.init("${KAKAO_KEY}");

		//카카오 로그인 버튼
		$("#kakao-login-btn").click(function(){
			kakaoLogin();
		});

		//카카오 로그인 처리
		function kakaoLogin(){
			Kakao.Auth.cleanup();
			
			//로그아웃 버튼 클릭 후 재로그인 시
			if(logoutAt == "Y"){
				Kakao.Auth.loginForm({
					success: function(res){
						Kakao.API.request({
							  url : "/v2/user/me"
							, success : function(res){
								 $("#userKey").val(res.id);
								 $("#name").val(res.properties.nickname);
									$("#clientCd").val("KAKAO");
									//카카오 선택 사항임
									if(res.kakao_account.email != "undefined"){
									   $("#email").val(res.kakao_account.email);
									}
									if(res.kakao_account.gender != "undefined"){
									   $("#sexdstn").val(res.kakao_account.gender);
									}
									if(res.kakao_account.age_range != "undefined"){
									   $("#agrde").val(res.kakao_account.age_range);
									}
									
									//mobile
									if(isModooApp()){
									   if(isIos()){
										   window.webkit.messageHandlers.setId.postMessage(res.id + "@#" + res.properties.nickname + "@#" + $("#email").val() + "@#" + $("#clientCd").val() + "@#" + $("#sexdstn").val() + "@#" + $("#agrde").val());
									   }else{
										   window.android.putString("userKey", res.id);
										   window.android.putString("name", res.properties.nickname);
											window.android.putString("email", $("#email").val());
											window.android.putString("clientCd", $("#clientCd").val());
											window.android.putString("sexdstn", $("#sexdstn").val());
											window.android.putString("agrde", $("#agrde").val());
									   }
									}
									setCookie("logoutAt", "N", 1);
								   
									$("#loginForm").submit();
							}, fail : function(error){
								alert(JSON.stringify(error));
							}
						});
					},
						fail: function(err) {
						 alert(JSON.stringify(err));
					}
				});
			}else{
				Kakao.Auth.login({
					persistAccessToken: true,
					persistRefreshToken: true,
					success: function(res){
						Kakao.API.request({
							  url : "/v2/user/me"
							, success : function(res){
								 $("#userKey").val(res.id);
								 $("#name").val(res.properties.nickname);
									$("#clientCd").val("KAKAO");
									//카카오 선택 사항임
									if(res.kakao_account.email != "undefined"){
									   $("#email").val(res.kakao_account.email);
									}
									if(res.kakao_account.gender != "undefined"){
									   $("#sexdstn").val(res.kakao_account.gender);
									}
									if(res.kakao_account.age_range != "undefined"){
									   $("#agrde").val(res.kakao_account.age_range);
									}
									
									setCookie("logoutAt", "N", 1);
								   
									$("#loginForm").submit();
							}, fail : function(error){
								alert(JSON.stringify(error));
							}
						});
					},
						fail: function(err) {
						 alert(JSON.stringify(err));
					}
				});
			}
		}

		$(document).ready(function(){
			$("#apple-login-btn").click(function(){
				location.href = "/user/sign/appleAuth.do";
			});
			
			//네이버 로그인 버튼
			$("#naver-login-btn").click(function(){
				var href = $(this).data("href");
				location.href = href;
			});
		});
		</script>
	</javascript>
</body>

</html>

