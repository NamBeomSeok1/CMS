(function() {
	
	var pageUnitReviewTodo = 5;
	var pageSizeReviewTodo = 5;
	var pageIndexReviewTodo = 1;

	var orderNo;
	var goods_id;

	getMyCommentListTodo = function(pageIndexReviewTodo) {
		
		var actionUrl = CTX_ROOT + '/shop/goods/review/myReviewTodoList.json';
	
		var dataJson = {
			'pageUnit' : pageUnitReviewTodo,
			'pageSize' : pageSizeReviewTodo,
			'pageIndex' : pageIndexReviewTodo
		};
	
		$.ajax({
			url:actionUrl,
			data:dataJson,
			dataType:'json',
			contentType:'application/json',
			cache: false,
			type:'get',
			success:function(json){
				
				var list= json.resultList;
				var html='';
				
				html += '<div class="thead"><cite class="col-w200">주문번호</cite><cite>상품정보</cite><cite class="col-w250">주문정보</cite>';
				html += '<cite class="col-w200">결제금액</cite><cite class="col-w150"></cite></div>';
				
				if (list.length > 0) {
					//주문-리뷰 목록
					for(var i=0; i<list.length; i++){
						html += '<div class="tbody">';
						html += '<div class="col-w200">';
			            html += list[i].orderNo;
			            var href = CTX_ROOT + '/user/my/mySubscribeView.do?orderNo=' + list[i].orderNo;
			            html += '<a href="' + href + '" class="block fc-gr sm mt10">상세보기 <i class="ico-arr-r sm back gr" aria-hidden="true"></i></a>';
						html += '</div>';
						html += '<div class="al m-col-block">';
			            html += '<a href="' + CTX_ROOT + '/shop/goods/goodsView.do?goodsId=' + list[i].goodsId + '">';
			            html += '<div class="thumb-area lg">';
			            html += '<figure><img src="' + list[i].goodsImageThumbPath + '" alt="' + list[i].goodsNm + '" /></figure>';
			            html += '<div class="txt-area">';
						html += '<h2 class="tit">[' + list[i].goodsKndCodeNm +'] ' + list[i].goodsNm + '</h2>';
			            if (list[i].goodsKndCode == 'SBS') {
			            	html += '<p class="fc-gr">' + list[i].orderCo + '회차 / ' + list[i].dlvySttusCodeNm + '</p>';
			            }
			            
			            html += '</div>';
			            html += '</div>';
			            html += '</a>';
						html += '</div>';
						html += '<div class="col-w250 al m-col-block">';
			            
			            if (list[i].goodsKndCode == 'SBS') {
				            html += '<ul class="option-info">';
				            html += '<li>';
				            html += '<strong>구독주기 :</strong>';
				            html += '<span>' + list[i].sbscrptCycleSeCodeNm + '</span>';
				            html += '</li>';
				            html += '<li>';
				            html += '<strong>정기결제일 :</strong>';
				            html += '<span>' + (isEmpty(list[i].sbscrptDlvyDayNm)?'-':list[i].sbscrptDlvyDayNm) + '</span>';
				            //html += '<span>' + list[i].sbscrptDlvyDayNm + '</span>';
				            html += '</li>';
				            html += '</ul>';
			            }
	
			            html += '<ul class="option-info">';
			            html += '<li>';
			            html += '<cite>수량 :</cite>';
			            html += '<span>' + list[i].orderCo + '개</span>'
			            html += '</li>';
			            html += '</ul>';
						html += '</div>';
						html += '<div class="col-w200 m-col-block">';
			            html += '<div class="price">';
			            html += '<strong><span>' + list[i].totAmount.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",") + '</span>원</strong>';
			            html += '</div>';
						html += '</div>';
						html += '<div class="col-w150 m-col-block">';
			            html += '<div class="btn-area">';
			            
			            if (!list[i].commentId) {
			            	html += '<button type="button" class="btn spot2" onclick="initCommentPopup(\'add\', \'' + list[i].goodsId + '\', \'' + list[i].orderNo + '\')">상품평쓰기</button>';
			            }
			            
			            html += '</div>';
						html += '</div>';
						html += '</div>';					
					}
				} else {
					html += '<div class="tbody">';
					html += '<p class="none-txt">리뷰 작성 가능한 주문 내역이 없습니다.</p>';
					html += '</div>';
				}
	
				$('#tbody-review-todo').html(html);
	
				//페이징
				pagingReviewTodo($('#paging-review-todo'), json.paginationInfo.totalRecordCount, pageIndexReviewTodo);
			}
		})	
	
	}

	// 페이징
	pagingReviewTodo = function ($pagingSector, totalRecordCount, pageIndex) {
		
		const totalPage = Math.ceil(totalRecordCount/pageSizeReviewTodo); //총 페이지 수  - 전체 데이터 개수/한 페이지에 나타낼 데이타
		const pageGroup = Math.ceil(pageIndex/pageUnitReviewTodo); //페이지 그룹 - 현재 페이지/한 페이지에 보여줄 페이지 수
	
		var lastShowIndex = pageGroup * pageSizeReviewTodo; // 화면에 보여질 마지막 페이지 번호
		if(lastShowIndex > totalPage){
			lastShowIndex = totalPage;
		}
		var firstShowIndex = lastShowIndex - (pageSizeReviewTodo - 1); // 화면에 보여질 첫번째 페이지 번호
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
			htmlStr +='<li class="ppv"><a href="#none" onclick="movePageReviewTodo('+firstIndex+');" title="처음으로"><span class="txt-hide">처음으로</span></a></li>';
			htmlStr +='<li class="pv"><a href="#none" onclick="movePageReviewTodo('+prev+');" title="이전"><span class="txt-hide">이전</span></a></li>';
		}
		//현재 페이지 active
		for(var i=firstShowIndex; i<=lastShowIndex; i++){
			htmlStr += '<li ';
			if(pageIndex == i){
				htmlStr += 'class="is-active" id="currentPage"';
			}
			htmlStr += 'data-page="'+i+'"><a href="#none" onclick="movePageReviewTodo('+i+');" title="to '+i+' page">' +i+ '</a></li>';
		 }
			
		//마지막으로,다음
		if(lastShowIndex < totalPage){
			htmlStr += '<li class="fw"><a href="#none"  onclick="movePageReviewTodo('+next+');" title="다음"><span class="txt-hide">다음</span></a></li>';
			htmlStr += '<li class="ffw"><a href="#none" onclick="movePageReviewTodo('+lastIndex+');"  title="끝으로"><span class="txt-hide">끝으로</span></a></li>';
		}
	   
		$pagingSector.html('');
		$pagingSector.html(htmlStr);
	
	}
	
	//페이지 이동
	movePageReviewTodo = function(currentPage){
	
		pageIndexReviewTodo = currentPage; 
		getMyCommentListTodo(pageIndexReviewTodo);
	}
	
	/** comment.js */
	var score;
	var reviewFormData = new FormData();
	var file_cnt = 0;
	var orderInfo;
	var action;
	var storage = [];
	var commentId;
	var originFiles;
	var atchFileId;

	//리뷰 등록
	$(document).on('click','#commentReg',function(e){
		e.preventDefault();

		var actionUrls = {
			'add' : CTX_ROOT + '/shop/goods/comment/registComment.do',
			'update' : CTX_ROOT + '/shop/goods/comment/modifyComment.do'
		}
	
		var commentCn = $('#commentCn').val();
		var secretAt = $('.agree-area').children('label').children('input').val();
		
		if (secretAt == 'on') {
			secretAt = 'Y';
		} else {
			secretAt = 'N';
		}
	
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
			tempFormData.append('secretAt', secretAt);
			tempFormData.append('cntntsId', goods_id);
			
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
				processData: false,
				contentType: false,
				success:function(result){
					if(result.success){
						modooAlert(result.message,function(){
							popClose('reviewWrite');
						});
						getMyCommentListTodo(pageIndexReviewTodo);
					}else{
						modooAlert(result.message);
					}
				}
				
			});
		}
	});

	initCommentPopup = function(mode, goodsId, order_no){
		goods_id = goodsId;
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

	//파일 업로드 미리보기
	$("#fileAdd").change(function(e){
	
		/* ui 액션 처리 */
		var get_file = e.target.files;
		var reader = new FileReader();
		var html = '';
		var target = document.getElementById('review-file');
	
		if (storage.length < 5)  {
			reader.onload = (function () {
				return function (e) {
					html += '<li id="img_' + file_cnt +'">';
					html += '<div><img src="' + e.target.result + '" alt=" " /></div>';
					html += '<button type="button" class="btn-del-r" onclick="deleteImg(this)" id="img_' + file_cnt +'"><span class="txt-hide">삭제</span></button>';
					html += '</li>';
	
					file_cnt++;
					
					$(target).append(html);
					
					console.log(e);
					console.log(target);
					console.log(html);
					
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

	/** 이벤트 목록 */
	$(document).ready(function(){
		getMyCommentListTodo(pageIndexReviewTodo);
	});
	
	$(document).on('click','#commentClose',function(){
		popClose('reviewWrite');
	});
	
	$(document).on('click','.grade-check-area button',function(){
		score = $(this).data('val');
	});

})();