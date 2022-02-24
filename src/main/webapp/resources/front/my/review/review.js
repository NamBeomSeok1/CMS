(function() {
	
	var pageUnitReview = 5;
	var pageSizeReview = 5;
	var pageIndexReview = 1;
	
	var score;
	var starPer = {
		0 : '00%',
		1 : '20%',
		2 : '40%',
		3 : '60%',
		4 : '80%',
		5 : '100%'
	};
	var reviewFormData = new FormData();
	var file_cnt = 0;
	var orderInfo;
	var action;
	var storage = [];
	var commentId;
	var originFiles;
	var atchFileId;
	var cntntsId;

	getMyCommentList = function(pageIndexReview) {

		var dataJson = {
			'pageUnit' : pageUnitReview,
			'pageSize' : pageSizeReview,
			'pageIndex' : pageIndexReview
		};
	
		$.ajax({
			url: CTX_ROOT + '/shop/goods/review/myReviewList.json',
			data:dataJson,
			dataType:'json',
			contentType: 'application/json',
			type:'get',
			cache: false,
			success:function(json){
				//console.log(json);
				
				var list= json.resultList;
				var html='';
				
				if (list.length > 0) {
					//리뷰 목록
					for(var i=0; i<list.length; i++){
						//console.log(list[i].goodsInfo);
	
						html += '<li><div class="review-txt-area">';
						html += '<span class="grade sm">';
						html += '<span class="grade-per" style="width:' + starPer[list[i].score] +'"></span>';
						html += '</span><span class="date">' + moment(list[i].frstRegistPnttm).format('YYYY-MM-DD') + '</span>';
						html += '<dl>';
						html += '<dt>상품</dt>';
						html += '<dd><a href="/shop/goods/goodsView.do?goodsId=' + list[i].goodsInfo.goodsId +'">' + list[i].goodsInfo.goodsNm + '</a></dd>';
						html += '</dl>';
						html += '<p>' + list[i].commentCn + '</p>';
						html += '<div class="btn-area">';
						html += '<button type="button" class="btn-sm-gr" id="review-edit" onclick="editComment(' + list[i].commentId + ',\'' + list[i].goodsInfo.goodsId + '\')">수정</button>';
						html += '<button type="button" class="btn-sm-gr" onclick="deleteComment(' + list[i].commentId + ')">삭제</button>';
						html += '</div>';
						html += '</div>';
						html += '<div class="review-img-area" onclick="showCommentDetail(' + list[i].commentId +')">';
						html += '<button type="button" data-code="' + list[i].commentId +'" class="btn-full"><span class="txt-hide">리뷰상세보기</span></button>';
	
						for(var j=0; j<list[i].imgs.length; j++){
							var imgSrc = '/fms/getImage.do?atchFileId=' + list[i].imgs[j].atchFileId + '&fileSn=' + list[i].imgs[j].fileSn;
							
							html += '<figure>';
							html += '<img src="' + imgSrc + '" alt="이미지 ' + list[i].imgs[j].fileSn + ' 썸네일"/>';
							html += '</figure>';
							
							if (j == 1 && list[i].imgs.length > 2) {
								var etc = list[i].imgs.length - 2;
								html += '<span style="cursor:pointer">+' + etc +'</span>';	
							break;
							}
						}
	
						html += '</div></li>';
					//	
						//페이징
						pagingReview($('#paging-review'), json.paginationInfo.totalRecordCount, pageIndexReview);
					}
				} else {
					html += '<li>';
					html += '<p class="none-txt">작성된 리뷰가 없습니다.</p>';
					html += '</li>';
				}
	
				$('.review-list').html(html);
			}
		})	
	
	}
	
	// 페이징
	pagingReview = function($pagingSector, totalRecordCount, pageIndex) {
		
		const totalPage = Math.ceil(totalRecordCount/pageSizeReview); //총 페이지 수  - 전체 데이터 개수/한 페이지에 나타낼 데이타
		const pageGroup = Math.ceil(pageIndex/pageUnitReview); //페이지 그룹 - 현재 페이지/한 페이지에 보여줄 페이지 수
	
		var lastShowIndex = pageGroup * pageSizeReview; // 화면에 보여질 마지막 페이지 번호
		if(lastShowIndex > totalPage){
			lastShowIndex = totalPage;
		}
		var firstShowIndex = lastShowIndex - (pageSizeReview - 1); // 화면에 보여질 첫번째 페이지 번호
		if(firstShowIndex < 1){
			firstShowIndex = 1;
		}
	
		const firstIndex = 1; // 첫 페이지
		const prev = Number(pageIndex)-1; // 이전 페이지
		const next = Number(pageIndex)+1; // 다음 페이지
		const lastIndex = totalPage; // 마지막 페이지
	   
		var htmlStr='';
		//처음으로,이전
		if(prev > 0){
			htmlStr +='<li class="ppv"><a href="#none" onclick="movePageReview('+firstIndex+');" title="처음으로"><span class="txt-hide">처음으로</span></a></li>';
			htmlStr +='<li class="pv"><a href="#none" onclick="movePageReview('+prev+');" title="이전"><span class="txt-hide">이전</span></a></li>';
		}
		//현재 페이지 active
		for(var i=firstShowIndex; i<=lastShowIndex; i++){
			htmlStr += '<li ';
			if(pageIndex == i){
				htmlStr += 'class="is-active" id="currentPage"';
			}
			htmlStr += 'data-page="'+i+'"><a href="#none" onclick="movePageReview('+i+');" title="to '+i+' page">' +i+ '</a></li>';
		 }
			
		//마지막으로,다음
		if(lastShowIndex < totalPage){
			htmlStr += '<li class="fw"><a href="#none"  onclick="movePageReview('+next+');" title="다음"><span class="txt-hide">다음</span></a></li>';
			htmlStr += '<li class="ffw"><a href="#none" onclick="movePageReview('+lastIndex+');"  title="끝으로"><span class="txt-hide">끝으로</span></a></li>';
		}
	   
		$pagingSector.html('');
		$pagingSector.html(htmlStr);
	
	}
			
	//페이지 이동
	movePageReview = function(currentPage){
	
		pageIndexReview = currentPage; 
		getMyCommentList(pageIndexReview);
	}

	/** comment.js */
	//리뷰 수정 팝업
	editComment = function(id, goodsId) {
		initCommentPopup('update', goodsId);
		
		var actionUrl = '/shop/goods/comment/commentDetail.json';
		var dataJson = {'commentId' : id};
		
		$.ajax({
			url:actionUrl,
			data:dataJson,
			cache: false,
			dataType:'json',
			success:function(json){
				
				commentId = json.commentId;
				atchFileId = json.atchFileId;
				
				$('#commentCn').val(json.commentCn);
				for (var i = 1; i<=json.score ; i++) {
					$('.grade-check button[data-val='+ i +']').addClass('is-active');
				}
				if (json.secretAt == 'Y') {
					$('.grade-check button').value = 'on';
				}
				score = json.score;
				
				$('input[name="wrterId"]').val(json.wrterId);
				
				var target = document.getElementById('review-file');
				$(target).empty();

				var html = '';
				for (var i = 0; i < json.imgs.length; i++){
					var imgSrc = '/fms/getImage.do?atchFileId=' + json.imgs[i].atchFileId + '&fileSn=' + json.imgs[i].fileSn;
					
					html += '<li id="img_' + json.imgs[i].fileSn +'">';
					html += '<div><img src="' + imgSrc + '" alt=" " /></div>';
					html += '<button type="button" class="btn-del-r" onclick="deleteRealImg(this, \'' + json.imgs[i].atchFileId + '\', \'' + json.imgs[i].fileSn +'\')" id="img_' + json.imgs[i].fileSn +'"><span class="txt-hide">삭제</span></button>';
					html += '</li>';
				}
				$(target).html(html);
				
				/** 이미지 수정 */
				if (json.imgs.length > 0) {
					originFiles = $(target).children('li');
					file_cnt = json.imgs[json.imgs.length-1].fileSn;
				}

				console.log(originFiles, file_cnt);
	
			}
		});
	
	}
	
	//리뷰 상세 
	commentDetail = function(id){
		var actionUrl = '/shop/goods/comment/commentDetail.json';
		var dataJson = {'commentId' : id}; 
		
		$.ajax({
			url:actionUrl,
			data:dataJson,
			dataType:'json',
			success:function(json){
				//console.log(json);
			}
		});
	}
	
	//파일 업로드 미리보기
	$("#fileAdd").change(function(e){
	
		/* ui 액션 처리 */
		var get_file = e.target.files;
		var reader = new FileReader();
		var html = '';
		var target = document.getElementById('review-file');
		var tid = (new Date()).valueOf();
	
		if (storage.length < 5)  {
			reader.onload = (function () {
				return function (e) {
					html += '<li id="img_' + tid +'">';
					html += '<div><img src="' + e.target.result + '" alt=" " /></div>';
					html += '<button type="button" class="btn-del-r" onclick="deleteImg(this)" id="img_' + tid +'"><span class="txt-hide">삭제</span></button>';
					html += '</li>';

					$(target).append(html);
				}
			})()
	
			if(get_file){
				reader.readAsDataURL(get_file[0]);
			}
	
			/* file form data 처리 */
			storage.push(get_file[0]);
		} else {
			modooAlert('파일은 최대 5개까지 업로드 할 수 있습니다.');
		}
	
		/* file input 초기화 */
		$('#fileAdd').val('');
		
		//logFormData(reviewFormData);
	});

	deleteRealImg = function(item, atchFileId, fileSn) {
		var actionUrl = CTX_ROOT + '/fms/deleteFile.json';
		var dataJson = {
			'atchFileId' : atchFileId
			, 'fileSn' : fileSn
		}; 
		
		$.ajax({
			url:actionUrl,
			data:dataJson,
			type:'post',
			dataType:'json',
			success:function(json){
				deleteImg(item);
			}
		});
	}
	
	// 이미지 삭제
	deleteImg = function(item) {
		var id = $(item).attr('id');
		var idx = $(item).index();
		$('li#' + id).remove();
		
		storage.splice(id-1, 1);
		originFiles.splice(id-1, 1);
		
		console.log('storage:', storage);
		console.log('originFiles:',originFiles);
		
		modooAlert('삭제되었습니다.' , '', function() {
		
		});
	}

	showCommentDetail = function(id) {
		popOpen('reviewView');
		
		var actionUrl = CTX_ROOT + '/shop/goods/comment/commentDetail.json';
		var dataJson = {'commentId' : id}; 
		
		$.ajax({
			url:actionUrl,
			data:dataJson,
			dataType:'json',
			cache: false,
			success:function(json){
	
				var html = '';
				
				html += '<span class="grade sm">';
	            html += '<span class="grade-per" style="width:' + starPer[json.score] + '"></span>';
	            html += '</span>';
	            html += '<span class="date">' + moment(json.frstRegistPnttm).format('YYYY-MM-DD') + '</span>';
	            html += '<dl>';
	            
	            /*if (json.secretAt != 'Y') {
		            html += '<dt>옵션</dt>';
					html += '<dd>' + json.goodsNm +'</dd>';
	            }*/
	
	            html += '</dl>';
	            html += '<p>' + json.commentCn +'</p>';
	            html += '<cite>' + json.mberId +'</cite>';
				
				$('#review-detail-contents').html(html);
	
				if (json.imgs.length > 0) {
					var bigImg = '<img src="' + '/fms/getImage.do?atchFileId=' + json.imgs[0].atchFileId + '&fileSn=' + json.imgs[0].fileSn + '" alt="이미지1 썸네일" />';
					$('#review-detail-img').html(bigImg);
				}
	
				var imgs = '';
				for (var i = 0; i < json.imgs.length ; i++) {
	
					imgs += '<li class="swiper-slide">';
					imgs += '<a href="#none">';
					imgs += '<img src="' + '/fms/getImage.do?atchFileId=' + json.imgs[i].atchFileId + '&fileSn=' + json.imgs[i].fileSn + '" id="slide_' + i + '" onclick="selectImg(this)" alt="이미지' + i+1 + ' 썸네일" />';
					imgs += '</a>';
					imgs += '</li>';
	
				}
				$('.swiper-wrapper').html(imgs);
	
			}, complete : function() {
				popReviewSwiper();
			}
		});
	
	}

	selectImg = function(item) {
		var id = $(item).attr('id');
		var src = $('img#' + id).attr('src');
		$('#review-detail-img').children('img').attr('src', src);
	}

	initCommentPopup = function(mode, goodsId){
		action = mode;
		
		popOpen('reviewWrite');
	
		// 입력값 초기화
		$('#commentCn').text('');
		$('.grade-check button').removeClass('is-active');
		$('#review-file').empty();
		$('.grade-check button').value = 'off';
	
		if (mode == 'update') {
			$('#review-pop-title').children('h1').text('리뷰수정');
			$('#commentReg').text('수정하기');	
		} else if  (mode == 'add') {
			$('#review-pop-title').children('h1').text('리뷰작성');
			$('#commentReg').text('등록하기');	
		}
	
		var dataJson = {
			'goodsId' : goodsId
		};
	
		$.ajax({
			url:CTX_ROOT + '/shop/goods/comment/goodsInfo.json',
			data:dataJson,
			dataType:'json',
			type:'get',
			cache: false,
			success:function(json){
				var html = '';
				html += '<figure><img src="' + json.goodsTitleImageThumbPath  + '" alt="' + json.goodsNm + '" /></figure>';
				html += '<div class="txt-area">';
				html += '<h2 class="tit">' + json.goodsNm + '</h2>';
				html += '<div class="price"><strong><span>' + json.goodsPc + '</span>원</strong></div>';
				html += '</div>';
				$('#thumb-area-goods-info').html(html);
			}
		});	
	}
	
	//리뷰 등록
	$(document).on('click','#commentReg',function(e){
		e.preventDefault();

		var actionUrls = {
			'add' : CTX_ROOT + '/shop/goods/comment/registComment.do',
			'update' : CTX_ROOT + '/shop/goods/comment/modifyComment.do'
		}
	
		var commentCn = $('#commentCn').val();
		/*var secretAt = $('.agree-area').children('label').children('input').val();
		if (secretAt == 'on') {
			secretAt = 'Y';
		} else {
			secretAt = 'N';
		}*/
	
		function validation() {
	
			if (commentCn.length < 10) {
				modooAlert('리뷰 내용을 10자 이상 입력해 주세요.');
				return false;
			}
			if (!score) {
				modooAlert('평점을 입력해 주세요.');
				return false;
			}
	
			return true;
		}
	
		if (validation()) {

			// formData 초기화
			var tempFormData = new FormData();
			
			tempFormData.append('score', score);
			tempFormData.append('commentCn', commentCn);
			//tempFormData.append('secretAt', secretAt);
			tempFormData.append('cntntsId', cntntsId);
			
			var wrterId = $('input[name="wrterId"]').val();
			tempFormData.append('wrterId', wrterId);
			
			if (atchFileId != undefined) {
				tempFormData.append('atchFileId', atchFileId);	
			}
	
			if (commentId) {
				tempFormData.append('commentId', commentId);	
			}
			
			for (var i =0; i < storage.length; i++) {
				tempFormData.append('atchFile', storage[i]);
			}
			
			if (originFiles) {
				for (var i = 0; i< originFiles.length; i++) {
					tempFormData.append('fileSn', originFiles[i].id.split('_')[1]);
				}
			}
			
			reviewFormData = tempFormData;
	
			$.ajax({
				url:actionUrls[action],
				type:'POST',
				data:reviewFormData,
				dataType:'json',
				enctype:'multipart/form-data',
				cache: false,
				processData: false,
				contentType: false,
				success:function(result){
					if(result.success){
						modooAlert(result.message, '',function(){
							popClose('reviewWrite');
							location.reload(true);
						});
					}else{
						modooAlert(result.message, '',function(){});
					}
				}
				
			});
		}
	});

	//리뷰 삭제
	deleteComment = function(id) {
		var actionUrl = '/shop/goods/comment/deleteComment.do';
		var dataJson = {
			'commentId' : id
		};
		
		modooConfirm('리뷰를 삭제하시겠습니까?' ,'확인', function(result) {
			if(result) {
				$.ajax({
					url:actionUrl,
					data:dataJson,
					method:'post',
					dataType:'json',
					success:function(json){
						modooAlert(json.message);
						getMyCommentList(pageIndexReview);
					}
				});
			}else {
				//console.log('cancel...........');
			}
		});

	}
	
	/** 이벤트 목록 */
	$(document).ready(function(){
		getMyCommentList(pageIndexReview);
	});
	$(document).on('ready', function(){
		getMyCommentList(pageIndexReview);
	});
	$(document).on('click','#commentClose',function(){
		popClose('reviewWrite');
	});

})();