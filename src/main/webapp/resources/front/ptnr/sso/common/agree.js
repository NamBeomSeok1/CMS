$(document).ready(function () {
	//퍼블리싱 리스트용
	popPosition($('.popup-agree'));
	
	$("#checkAll").click(function(){
		if($(this).is(":checked")){
			$(".chk").prop("checked", false);
			$(".chk").click();
		}else{
			$(".chk").prop("checked", true);
			$(".chk").click();
		}
	});
	
	//이용약관 검사
	$(document).on('submit', '#mberAgre', function(e) {
		if( !$('#ageOver').is(':checked')) {
			popMessage = '만 14세 이상은 필수 항목입니다.';
			$('#popMessage').text(popMessage);
			popOpen('pop-agree');
			return false;
		}
		if( !$('#termsCondAt1').is(':checked')) {
			popMessage = '서비스 이용약관 동의는 필수 항목입니다.';
			$('#popMessage').text(popMessage);
			popOpen('pop-agree');
			return false;
		}
		if( !$('#privInfoAt1').is(':checked')) {
			popMessage = '개인정보 수집 및 이용 동의는 필수 항목입니다.';
			$('#popMessage').text(popMessage);
			popOpen('pop-agree');
			return false;
		}
		//구글 전환스크립트
		 gtag('event', 'conversion', {'send_to': 'AW-448483818/h3WpCJXUs_EBEOqj7dUB',
			'value':1.0,
		 	});
		//네이버 전환스크립트
		 /*var _nasa={};
		 if (window.wcs) _nasa["cnv"] = wcs.cnv("2",1);*/
	});
	
	//버튼 링크 Click
	$(document).on('click','.btnLink', function(e) {
		e.preventDefault();
		var actionUrl = $(this).data('src');
		if(actionUrl) {
			location.href = actionUrl;
			//window.open(actionUrl);
		}
	});

});