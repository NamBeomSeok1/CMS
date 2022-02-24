if (window.NodeList && !NodeList.prototype.forEach) {
  NodeList.prototype.forEach = Array.prototype.forEach;
}

/*
 * 알림 팝업
 */
function noticePopup(popupId, url, title, w, h, x, y) {
	if(Cookies.get(popupId) != 'Y') {
		popupWindow(url, title, w, h, x, y);
	}
}

/*
 * 윈도우 팝업
 */
function popupWindow(url, title, w, h, x, y) {
	var screenX = typeof window.screenX != 'undefined' ? window.screenX : window.screenLeft;
	var	screenY = typeof window.screenY != 'undefined' ? window.screenY : window.screenTop;
	var left = screenX + x; //(x || Math.round((window.screen.width/2)-(w/2)));
	var top = screenY + y; //(y ||  Math.round((window.screen.height/2)-(h/2)));
	var popup =window.open(url, title, 'toolbar=no, location=no, directories=no, status=no, '
		+ 'menubar=no, scrollbars=yes, resizable=yes, copyhistory=no, width=' + w
		+ ', height=' + h + ', top=' + top + ', left=' + left);

	if(popup == null) {
		alert('팝업 차단 기능 혹은 팝업차단 프로그램이 동작중입니다. \n팝업차단 기능을 해제한 후 이용하시기 바랍니다.');
	}

	popup.focus();
}

if (!String.prototype.trim) {
	String.prototype.trim = function () {
		return this.replace(/^[\s\uFEFF\xA0]+|[\s\uFEFF\xA0]+$/g, '');
	};
}

//IOS체크
function isIos() {
	return /iPhone|iPad|iPod/i.test(navigator.userAgent) || navigator.platform === 'MacIntel' && navigator.maxTouchPoints > 1;
}

//모바일여부
function isMobile() {
    return /Android|webOS|iPhone|iPad|iPod|BlackBerry|IEMobile|Opera Mini/i.test(navigator.userAgent) || navigator.platform === 'MacIntel' && navigator.maxTouchPoints > 1;
}

//모두의구독 앱으로 접속 여부
function isModooApp(){
	var userAgent = navigator.userAgent.toLowerCase(),
		modooAt = false;
	
	if(userAgent.indexOf("modoo") > -1){
		modooAt = true;
	}
	return modooAt;
}

//쿠키 저장
var setCookie = function(name, value, day) {
    var date = new Date();
    date.setTime(date.getTime() + day * 60 * 60 * 24 * 1000);
    document.cookie = name + '=' + value + ';expires=' + date.toUTCString() + ';path=/';
};

//쿠키 조회
var getCookie = function(name) {
    var value = document.cookie.match('(^|;) ?' + name + '=([^;]*)(;|$)');
    return value? value[2] : null;
};

/** funcitons **/
// Empty 여부
var isEmpty = function(x) {
	if( (typeof x) == 'undefined') {
		return true;
	}else if( (typeof x) == 'number') {
		return (
			(x == null) ||
			(x == false) ||
			(x.length == 0) ||
			(x == "") ||
			(!/[^\s]/.test(x)) ||
			(/^\s*$/.test(x))
		);
	}else if ((typeof x) == 'string') {
		if(x.length > 0) return false;
		return (
			(x == null) ||
			(x == false) ||
			(x.length == 0) ||
			(x == "") ||
			(x.replace(/\s/g,"") == "") ||
			(!/[^\s]/.test(x)) ||
			(/^\s*$/.test(x))
		);
	}else if((typeof x) == 'object') {
		return (
			(x == null)
		)
	}
}

// Number 처리
$(document).on('keyup keypress', '.inputNumber', function(e) {
	var verified = (e.which == 8 || e.which == undefined || e.which == 0) ? null : String.fromCharCode(e.which).match(/[^0-9]/);
    if (verified) {e.preventDefault();}
});

// 버튼 링크
$(document).on('click', '.button-link', function(e) {
	e.preventDefault();
	var src = $(this).data('src');
	if(!isEmpty(src)) {
		location.href = src;
	}
});

(function() {
	/*
	if($('.popup-today-check').length > 0) {
		$('.popup-today-check').click(function() {
			if($(this).is(':checked')) {
				alert(1111);
				var popupId = $(this).val();
				Cookies.set(popupId, 'Y', { expires: 1 });
				$('[data-popup-id="' + popupId + '"]').slideUp('fast');
			}
		});
	}
	*/
	/**
	 * 레이어 팝업 : 오늘하루 열지 않기
	 */
	$(document).on('click', '.popup-today-check', function() {
		if($(this).is(':checked')) {
			var popupId = $(this).val();
			Cookies.set(popupId, 'Y', { expires: 1 });
			$('[data-popup-id="' + popupId + '"]').slideUp('fast');
			$('.dim').hide();
		}
	});
	
	/**
	 * 레이어 팝업 닫기
	 */
	/*$(document).on('click', '.btn-close-popup', function(e) {
		e.preventDefault();
		var popupId = $(this).data('id');
		$('[data-popup-id="' + popupId + '"]').slideUp('fast');
	});*/
	
	/**
	 * 윈두우 팝업: 오늘하루 열지 않기
	 */
	$(document).on('click', '.window-popup-today-check', function() {
		if($(this).is(':checked')) {
			var popupId = $(this).val();
			Cookies.set(popupId, 'Y', { expires: 1 });
			$('[data-popup-id="' + popupId + '"]').slideUp('fast');
			self.close();
		}
	});
	
})();

$(document).ready(function(){
	/**
	 * 레이어 팝업
	 */
	if($('.popup-item').length > 0) {
		$('.popup-item').each(function(index) {
			var popupId = $(this).data('popupId');
			if(Cookies.get(popupId) != 'Y') {
				$(this).show();
			}
		});
	}
});