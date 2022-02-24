<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/modoo/common/commonTagLibs.jsp"%>
<!-- <script> -->
var logoutAt = getCookie("logoutAt");
Kakao.init("${KAKAO_KEY}");

//ios에서 메세지 호출 후 callback
function iosGetMember(str){
	var messageSpl = str.split("@#"),
		userKey = "",
		email = "",
		clientCd = "",
		name = "",
		sexdstn = "",
		agrde = "";
	
	if(str != ''){
		userKey = messageSpl[0],
		name = messageSpl[1],
		email = messageSpl[2],
		clientCd = messageSpl[3],
		sexdstn = messageSpl[4],
		agrde = messageSpl[5];
	}
	
	if(userKey != "" && clientCd != ""){
		$("#userKey").val(userKey);
		$("#email").val(email);
		$("#clientCd").val(clientCd);
		$("#name").val(name);
		$("#sexdstn").val(sexdstn);
		$("#agrde").val(agrde);
		
		$("#loginForm").submit();
	}
}

//모바일로 초기 접속 시 체크
if(isModooApp()){
	var userKey = "",
		email = "",
		clientCd = "",
		name = "",
		sexdstn = "",
		agrde = "",
		message = "";
	
	if(isIos()){
		window.webkit.messageHandlers.getId.postMessage("info");
	}else{
		userKey = window.android.getString("userKey"),
		email = window.android.getString("email"),
		clientCd = window.android.getString("clientCd"),
		name = window.android.getString("name"),
		sexdstn = window.android.getString("sexdstn"),
		agrde = window.android.getString("agrde");
		
		if(userKey != null && userKey != '' && clientCd != null && clientCd != ''){
			$("#userKey").val(userKey);
			$("#email").val(email);
			$("#clientCd").val(clientCd);
			$("#name").val(name);
			$("#sexdstn").val(sexdstn);
			$("#agrde").val(agrde);
			
			$("#loginForm").submit();
		}
	}
}else if(isMobile() && logoutAt == "N"){
	kakaoLogin();
}

if(isIos()){
	$("#apple-login-btn").show();
}

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
	}
}

var googleUser = {};
var startApp = function() {
	gapi.load('auth2', function(){
		auth2 = gapi.auth2.init({
			client_id: '740455072460-1uk7rr5uflg43ijc9vvqi4c41a4l8c5r.apps.googleusercontent.com',
			cookiepolicy: 'single_host_origin',
		});
		attachSignin(document.getElementById('customBtn'));
	});
};

function attachSignin(element) {
  auth2.attachClickHandler(element, {},
	  function(googleUser) {
		  var profile = googleUser.getBasicProfile();
		  
		  $("#userKey").val(profile.getId());
		  $("#clientCd").val("GOOGLE");
		  $("#email").val(profile.getEmail());
		  $("#name").val(profile.getName());
		  $("#loginForm").submit();
	  }, function(error) {
		alert(JSON.stringify(error, undefined, 2));
	  });
}

if(document.getElementById('customBtn')) {
	startApp();
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
<%-- </script> --%>