(function() {

	var goodsId=$('input[name=goodsId]').val();
	var goodsNm=$('h2').first().text();
	
	var score;
	var currentPageReview = 1;
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
	
	/** 이벤트 목록 */
	$(document).ready(function(){
		getCommentList(goodsId, currentPageReview);
	})
	$(document).on('change', 'select[name=commentSortOrdr]', function() {
		getCommentList(goodsId, currentPageReview);
	});
	$(document).on('click','.grade-check-area button',function(){
		score = $(this).data('val');
	});
	$(document).on('click','#review-regist',function(){
		initCommentPopup('add');
	});
	$(document).on('click','#review-edit',function(){
		initCommentPopup('update');
	});
	$(document).on('click','#commentClose',function(){
		popClose('reviewWrite');
	});
	$(document).on('click','#commentReg',function(e){
		e.preventDefault();
		registComment();
	});
	$("#fileAdd").change(function(e){
		addImage(e);
	});
	
	/** 리뷰 목록 */
	getCommentList = function(goodsId, currentPageReview){
		$('.review-list').empty();
	
		var commentListUrl = '/shop/goods/comment/commentList.json';
		var commentSortOrdr = parseInt($('select[name=commentSortOrdr]').val());
	
		var dataJson = {
			'searchGoodsId' : goodsId,
			'commentSortOrdr' : commentSortOrdr,
			'firstIndex' : currentPageReview
		};
	
		$.ajax({
			url:commentListUrl,
			data:dataJson,
			dataType:'json',
			cache:false,
			type:'get',
			success:function(json){
				var list= json.resultList;
				
				var html='';
				
				if (list.length > 0) {
					//리뷰 목록
					for(var i=0; i<list.length; i++){
						html += '<li><div class="review-txt-area">';
						html += '<span class="grade sm">';
						html += '<span class="grade-per" style="width:' + starPer[list[i].score] +'"></span>';
						html += '</span><span class="date">' + moment(list[i].frstRegistPnttm).format('YYYY-MM-DD') + '</span>';
						if (list[i].secretAt != 'Y') {
							//html += '<dl><dt>옵션</dt><dd>' + goodsNm + '</dd></dl>';
							/*for(var j=0; j<list[i].options.length; j++){
								html += '<dl><dt>옵션</dt><dd>' + list[i].options[j].goodsNm + '</dd></dl>';
							}*/
						}
						html += '<p>' + list[i].commentCn + '</p><cite>' + list[i].wrterId + '</cite>';
	
						if (list[i].isWriter) {
							// 수정, 삭제 버튼 추가
							html += '<div class="btn-area">';
							html += '<button type="button" class="btn-sm-gr" id="review-edit" onclick="editComment(' + list[i].commentId + ')">수정</button>';
							html += '<button type="button" class="btn-sm-gr" onclick="deleteComment(' + list[i].commentId + ')">삭제</button>';
							html += '</div>';
						}
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
					}
				} else {
					html += '<p class="none-txt">작성된 리뷰가 없습니다.</p>';
				}
	
				$('.review-list').html(html);
				
				//리뷰 평균 값
				var scoreAvg = json.avgScore;
				$('#score-avg').css({'width':starPer[scoreAvg]});
				$('.grade').siblings('p').children('strong').text(scoreAvg.toString() + '.0');
				
				//리뷰 총개수
				$('.review-cnt').text('('+json.paginationInfo.totalRecordCount+')');
				//페이징
				paging($('#review-paging'), json.paginationInfo.totalRecordCount, currentPageReview);
				
				//주문정보
				orderInfo = json.orderInfo;

				if ( json.hasRegistered == false && json.isLogin == true && json.hasOrdered == false ) {
					$('#review-regist').remove();
					//var html='';
					//html += '<select class="border-none" name="commentSortOrdr">';
					//html += '<option value="0">평점순</option>';
					//html += '<option value="1">최신순</option></select>';
					//html += '<button type="button" class="btn spot2" id="review-regist" data-popup-open="reviewWrite">작성하기</button>';
					//$('#fnc-area-review').html(html);
				} else {
					var html='';
					html += '<select class="border-none" name="commentSortOrdr">';
					html += '<option value="0">평점순</option>';
					html += '<option value="1">최신순</option></select>';
					if(json.isLogin == true) {
						html += '<button type="button" class="btn spot2" id="review-regist" data-popup-open="reviewWrite">작성하기</button>';
					}
					$('#fnc-area-review').html(html);
					$('select[name="commentSortOrdr"]').val(commentSortOrdr);
				}
			}
		})
		
	}

	paging = function ($pagingSector, totalCount, currentPage){
	       
		const totalPage = Math.ceil(totalCount/10); //총 페이지 수  - 전체 데이터 개수/한 페이지에 나타낼 데이타
		const pageGroup = Math.ceil(currentPage/5); //페이지 그룹 - 현재 페이지/한 페이지에 보여줄 페이지 수
	
		var last = pageGroup * 5; //화면에 보여질 마지막 번호
		if(last > totalPage){
			last = totalPage;
		}
		var first = last - 4; // 화면에 보여질 첫번째 번호
		if(first==0 || first<0){
			first=1;
		}
	   
		const foreFront = 1;
		const prev = Number(currentPage)-1;
		const next = Number(currentPage)+1;
		const rearMost = totalPage;
	   
		var htmlStr='';
		//처음으로,이전
		if(prev>0){
			 if(first==foreFront){
				 htmlStr +='';
			 }
			 htmlStr +='<li class="ppv"><a href="#none" onclick="movePage('+foreFront+');" title="처음으로"><span class="txt-hide">처음으로</span></a></li>';
			 htmlStr +='<li class="pv"><a href="#none" onclick="movePage('+prev+');" title="이전"><span class="txt-hide">이전</span></a></li>';
		   
		}
		//페이지 set
			for(var i=first; i<=last; i++){
				if(currentPage == i ){
					htmlStr += '<li class="is-active" id="currentPage" data-page="'+i+'"><a href="#none" onclick="movePage('+i+');" title="to '+i+' page">'+i+'</a></li>';
				 }else{
					htmlStr += '<li><a href="#none" onclick="movePage('+i+');" title="to '+i+' page">'+i+'</a></li>'
				}
			 }
			
		//마지막으로,다음
		if(last<totalPage){
			if(last==totalPage){
				htmlStr+='';
			}
			htmlStr += '<li class="fw"><a href="#none"  onclick="movePage('+next+');" title="다음"><span class="txt-hide">다음</span></a></li>';
			htmlStr += '<li class="ffw"><a href="#none" onclick="movePage('+rearMost+');"  title="끝으로"><span class="txt-hide">끝으로</span></a></li>';
		}
	   
		$pagingSector.empty();
		$pagingSector.html(htmlStr);
	
	}

	movePage = function(currentPage){
		currentPageReview = currentPage; 
		getCommentList(goodsId, currentPageReview);
	}
	
	/** 리뷰 삭제 */
	deleteComment = function (id) {
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
						getCommentList(goodsId, currentPageReview);
					}
				});
			}
		});
	}
	
	//리뷰 수정 팝업
	editComment = function(id) {
		initCommentPopup('update');
		
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

	// 이미지 추가
	addImage = function(e) {
		var get_file = e.target.files;
		var reader = new FileReader();
		var html = '';
		var target = document.getElementById('review-file');
		var tid = (new Date()).valueOf();
		
		if (get_file.length > 1) {
			modooAlert('파일은 한 번에 하나만 등록할 수 있습니다.');
		} else {
			var files = $('ul#review-file').children('li');
			if (files.length < 5)  {
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
					storage.push(get_file[0]);
				}
			} else {
				modooAlert('파일은 최대 5개까지 업로드 할 수 있습니다.');
			}
			
		}

		/* file input 초기화 */
		$('#fileAdd').val('');
			
	}
	
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

	/** 리뷰 상세보기 */
	showCommentDetail = function(id) {
		popOpen('reviewView');
		
		var actionUrl = CTX_ROOT + '/shop/goods/comment/commentDetail.json';
		var dataJson = {'commentId' : id}; 
		
		$.ajax({
			url:actionUrl,
			data:dataJson,
			dataType:'json',
			success:function(json){
				//console.log(json);
				
				var html = '';
				
				html += '<span class="grade sm">';
	            html += '<span class="grade-per" style="width:' + starPer[json.score] + '"></span>';
	            html += '</span>';
	            html += '<span class="date">' + moment(json.frstRegistPnttm).format('YYYY-MM-DD') + '</span>';
	            html += '<dl>';
				html += '<dt>옵션</dt>';
				html += '<dd>' + goodsNm +'</dd>';
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
				$('#swiper-wrapper-review').html(imgs);
	
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

	function initCommentPopup(mode){
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
				html += '<figure><img src="' + json.goodsTitleImageThumbPath + '" alt="' + json.goodsNm + '" /></figure>';
				html += '<div class="txt-area">';
				html += '<h2 class="tit">' + json.goodsNm + '</h2>';
				html += '<div class="price"><strong><span>' + json.goodsPc + '</span>원</strong></div>';
				html += '</div>';
				$('#thumb-area-goods-info').html(html);
			}
		});	
	}
	
	/** 리뷰 등록, 수정 */
	registComment = function() {
		var actionUrls = {
			'add' : CTX_ROOT + '/shop/goods/comment/registComment.do',
			'update' : CTX_ROOT + '/shop/goods/comment/modifyComment.do'
		};
	
		var commentCn = $('#commentCn').val();
		var wrterId = $('input[name="wrterId"]').val();
	
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
			tempFormData.append('cntntsId', goodsId);
			tempFormData.append('wrterId', wrterId);
			
			if (atchFileId != undefined) {
				tempFormData.append('atchFileId', atchFileId);	
			}
	
			if (commentId) {
				tempFormData.append('commentId', commentId);	
			}

			/*if (originFiles) {
				for (var i = 0; i< originFiles.length; i++) {
					tempFormData.append('fileSn', originFiles[i].id.split('_')[1]);
					console.log(originFiles[i].id.split('_')[1]);
				}
			}*/
			
			for (var i=0; i < storage.length; i++) {
				tempFormData.append('atchFile', storage[i]);
			}
			
			reviewFormData = tempFormData;
	
			$.ajax({
				url:actionUrls[action],
				type:'POST',
				data:reviewFormData,
				dataType:'json',
				enctype:'multipart/form-data',
				processData: false,
				contentType: false,
				success:function(result){
					if(result.success){
						modooAlert(result.message, '', function(){
							popClose('reviewWrite');
							location.reload(true);
						});
					}else{
						modooAlert(result.message);
					}
				}				
			});
		}
	}

})();