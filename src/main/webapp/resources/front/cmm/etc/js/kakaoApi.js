(function(){
	//카카오 공유
	kakaoShare = function(title,description,link,imageUrl){
		var link = encodeURI(link);
		
		Kakao.Link.sendDefault({
			  objectType: 'feed',
			  content: {
			    title: title,
			    description: description,
			    imageUrl: imageUrl,
			    link: {
			      mobileWebUrl: link,
			      webUrl: link,
			      androidExecParams: 'test',
			    },
			  },
			 /* social: {
			    likeCount: 10,
			    commentCount: 20,
			    sharedCount: 30,
			  },*/
			  buttons: [
			    {
			      title: '상품 바로가기',
			      link: {
			        mobileWebUrl: link,
			        webUrl: link,
			      },
			    },
			  ]
			});
		}

	
})();