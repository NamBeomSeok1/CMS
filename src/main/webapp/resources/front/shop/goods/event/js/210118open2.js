(function() {
	var id = '#' + $('#eventBrand').val();
	console.log(id);

	window.onload = function() {
		$('li > a[href="' + id + '"]')[0].click();
	}
	
	//클립보드 복사
	urlClipCopy = function(url) {
	  var textarea = document.createElement('textarea');
	  textarea.value = url;
	  
	  document.body.appendChild(textarea);
	  textarea.select();
	  textarea.setSelectionRange(0,9999);//추가
	 
	  document.execCommand('copy');
	  document.body.removeChild(textarea);
	  toast('URL이 복사되었습니다.');	  
	}

	//카카오 공유하기 버튼
	$(document).on('click','#kakaoShare',function(){
		
		var title=$(this).data('title');
		var description=$(this).data('description');
		var link=$(this).data('link');
		var imageUrl=$(this).data('imageurl');
		
		kakaoShare(title,description,link,imageUrl);
	})

	//네이버 공유하기
	$(document).on('click','#naverShare',function(){
		
		var title=$(this).data('title');
		var link=$(this).data('link');
		var encUrl = encodeURI(link);
		var encTit = encodeURI(title);
		
		window.open('https://share.naver.com/web/shareView.nhn?url='+encUrl+'&title='+encTit,'네이버 공유하기','window=800,height=700,toolbar=no,menubar=no,scrollbars=no,resizable=yes');
	
	})

	//페이스북 공유하기
	$(document).on('click','#facebookShare',function(){
		
		var title=$(this).data('title');
		var link=$(this).data('link');
		var encUrl = encodeURI(link);
		var encTit = encodeURI(title);
		
		window.open( 'https://www.facebook.com/sharer/sharer.php?u=' + encUrl+'&t='+encTit ,'페이스북공유하기','window=800,height=700,toolbar=no,menubar=no,scrollbars=no,resizable=yes');

	})

	//트위터 공유하기
	$(document).on('click','#twitterShare',function(){
		
		var title=$(this).data('title');
		var link=$(this).data('link');
		var encUrl = encodeURIComponent(link);
		var encTit = encodeURIComponent(title);
		
		window.open( 'https://twitter.com/intent/tweet?text='+encTit+'&url=' + encUrl,'트위터공유하기','window=800,height=700,toolbar=no,menubar=no,scrollbars=no,resizable=yes');
	})
})();