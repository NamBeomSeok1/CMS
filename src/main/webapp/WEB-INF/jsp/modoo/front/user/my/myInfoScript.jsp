<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/modoo/common/commonTagLibs.jsp"%>
//로그아웃 후 뒤로가기 접근 시
<c:if test="${empty USER_ID}">
	if(getCookie("logoutAt") == "Y" && window.location.pathname.indexOf("myInfo.do") > -1){
		location.href = "/index.do";
	}
</c:if>

<%-- Kakao.init("${KAKAO_KEY}"); --%>

//로그아웃
$(document).on('click', '.btn-logout', function(e) {
	//e.preventDefault();
	var logoutAt = false,
		href= $(this).attr("href");
	
	if(isModooApp()){
		if(isIos()){
			window.webkit.messageHandlers.setId.postMessage("");
			logoutAt = true;
		}else{
			window.android.putString("userKey", "");
        	window.android.putString("name", "");
			window.android.putString("email", "");
			window.android.putString("clientCd", "");
			window.android.putString("sexdstn", "");
			window.android.putString("agrde", "");
			logoutAt = true;
		}
	}else{
		logoutAt = true;
	}
	
	if(logoutAt){
		Kakao.Auth.logout(function(response) {
			//Cookies.set("logoutAt", 'Y', { expires: 1 });
			setCookie("logoutAt", "Y", 1);
			
			location.href = href;
		});
	}
	
	return false;
});