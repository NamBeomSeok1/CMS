//로그아웃 후 뒤로가기 접근 시
if(Cookies.get("logoutAt") == "Y"){
	location.href = "/index.do";
}

//실서버 키
Kakao.init("5f629775a1d80063052a9619bd5f47ab");
//개발 키
//Kakao.init("6e079fdd5337c130d827e3df54dc293a");

//로그아웃
$(document).on('click', '#btn-logout', function(e) {
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
			Cookies.set("logoutAt", 'Y', { expires: 1 });
			location.href = href;
		});
	}
	
	return false;
});