(function() {

	var pageUnit = 5;
	var pageSize = 5;
	var pageIndex = 1; //현재 페이지
	var goods_id;
	
	getSubscribeNowList = function() {
		var dataJson = {
			'pageUnit' : pageUnit,
			'pageSize' : pageSize,
			'pageIndex' : pageIndex,
			'orderSttusCode' : 'ST02'
		};
		
		$.ajax({
			url:CTX_ROOT + '/shop/goods/subscribeNow.json',
			data:dataJson,
			dataType:'json',
			cache:false,
			type:'get',
			success:function(json){
				//console.log('구독중 목록 로딩');

				var list = json.resultList;
				var html='<div class="thead"><cite class="col-w200">주문번호</cite><cite>상품정보</cite>';
				html += '<cite class="col-w250">주문정보</cite><cite class="col-w200">결제금액</cite><cite class="col-w150"></cite></div>';
				
				if (list.length > 0) {
					for(var i=0; i<list.length; i++){
						//console.log('배송정보: ', list[i].apiId, list[i].invcNo, list[i].dlvySttusCode, list[i].dlvySttusCodeNm, list[i].orderDlvyNo);
					
						html += '<div class="tbody">';
						html += '<div class="col-w200">';
						html += list[i].orderNo;
						html += '<br class="m-none" />';
						var href = CTX_ROOT + '/user/my/mySubscribeView.do?orderNo=' + list[i].orderNo + '&menuId=sbs_mySubscribeNow';
						html += '<a href="' + href + '" class="fc-gr sm">상세보기 <i class="ico-arr-r sm back gr" aria-hidden="true"></i></a>';
						html += '</div>';
						html += '<div class="al m-col-block">';
						html += '<a href="/shop/goods/goodsView.do?goodsId=' + list[i].goodsId +'">';
						html += '<div class="thumb-area lg">';
						html += '<figure><img src="../..' + list[i].goodsImageThumbPath + '" alt="' + list[i].goodsNm + '" /></figure>';
						html += '<div class="txt-area">';
						html += '<h2 class="tit">[' + list[i].orderSttusCodeNm + '] ' + list[i].goodsNm + '</h2>';
						html += '<p class="fc-gr">';
	
						if (list[i].orderKndCode == 'SBS') {
							html += list[i].nowOdr + '회차 예정 / ';
							html += list[i].nowOdr-1+'회차 ';
						}
						//html += list[i].dlvySttusCodeNm + '</p>';
						html += refreshDlvySttus(list[i].apiId, list[i].invcNo, list[i].dlvySttusCode, list[i].dlvySttusCodeNm, list[i].orderDlvyNo, list[i].orderKndCode);

						if (list[i].orderKndCode == 'SBS') {
							if(list[i].sbscrptMinUse!=null){
								html += '<p class="fs-sm fc-gr">최소이용주기 : ' + list[i].sbscrptMinUse + '</p>';
							}
						}
						html += '</div>';
						html += '</div>';
						html += '</a>';
						html += '</div>';
						html += '<div class="col-w250 al m-col-block">';
						html += '<ul class="option-info">';
						var nextDay = '';
						if (list[i].orderKndCode == 'SBS') {
							if(list[i].nextSetlede!=null){
								nextDay =list[i].nextSetlede;
							}
							if(list[i].sbscrptCycleSeCode == 'WEEK'){
								html += '<li>';
								html += '<strong>구독주기 :</strong>';
								html += '<span>' + list[i].sbscrptCycle + '</span>';
								html += '</li>';
								html += '<li>';
								html += '<strong>정기결제일 :</strong>';
								html += '<span>' + list[i].sbscrptDlvyWdNm + '요일</span>';
								html += '</li>';
								html += '<li>';
								html += '<strong>다음결제일: </strong>';
								html += '<span>' + nextDay + '</span>';
								html += '</li>';
							}else{
								html += '<li>';
								html += '<strong>구독주기 :</strong>';
								html += '<span>' + list[i].sbscrptCycle + '</span>';
								html += '</li>';
								html += '<li>';
								html += '<strong>정기결제일:</strong>';
								html += '<span>' + list[i].sbscrptDlvyDay + '</span>';
								html += '</li>';
								html += '<li>';
								html += '<strong>다음결제일: </strong>';
								html += '<span>' +  nextDay + '</span>';
								html += '</li>';
							}
						}
						html += '</ul>';
						html += '<ul class="option-info">';
						html += '<li>';
						html += '<cite>수량 :</cite>';
						html += '<span>' + list[i].orderCo + '개</span>';
						html += '</li>';
						html += '</ul>';
						html += '</div>';
						html += '<div class="col-w200 m-col-block">';
						html += '<div class="price">';
						var totAmount = Number(list[i].totAmount);
						html += '<strong><span>' + modooNumberFormat(totAmount) + '</span>원</strong>';
						html += '</div>';
						html += '</div>';
						html += '<div class="col-w150 m-col-block">';
						html += '<div class="btn-area">';
						if (list[i].orderKndCode == 'SBS') {
							html += '<a href="'+CTX_ROOT+'/user/my/mySubscribeModify.do?orderNo=' + list[i].orderNo+'" class="btn">구독변경</a>';
						}
						if (list[i].commentId != null) {
							html += '<a href="#none" id="review-regist" onclick="showCommentDetail(\'' + list[i].commentId + '\')" class="btn spot2">리뷰 확인</a>';
						} else {
							html += '<a href="#none" id="review-regist" onclick="initCommentPopupMyPage(\'add\', \'' + list[i].goodsId + '\', \'' + list[i].orderNo + '\')" class="btn spot2">상품평쓰기</a>';
						}
						if (list[i].orderSttusCode == 'ST02') {
							var href_cancle = CTX_ROOT + '/user/my/mySubscribeView.do?orderNo=' + list[i].orderNo + '&menuId=sbs_mySubscribeNow';
							html += '<a href="' + href_cancle + '" class="btn spot2">취소/교환/반품</a>';
						}
						html += '</div>';
						html += '</div>';
						html += '</div>';
					}
				} else {
					html += '<div class="tbody">';
					html += '<p class="none-txt">주문 중인 상품이 없습니다.</p>';
					html += '</div>';
				}
				$('#table-sbs-now').html(html);
				
				pagingSubscribeNow($('#paging-sbs-now'), json.paginationInfo.totalRecordCount, pageIndex);
			}
		});
		
	}
	
	refreshDlvySttus = function(apiId, invcNo, dlvySttusCode, dlvySttusCodeNm, orderDlvyNo, type) {
		//console.log(apiId, invcNo, dlvySttusCode, dlvySttusCodeNm, orderDlvyNo);
		
		var t_key='yWaX4OwVwrgofOXkfUP4eQ';
		var t_code = apiId;
		var t_invoice = invcNo;
		var dlvy_state = dlvySttusCodeNm;
		
		if(type=='CPN'){
			dlvy_state = '구매완료';
		} else {
			if (t_code && t_invoice) {
				var dataJson = {
					't_key' : t_key,
					't_code' : t_code,
					't_invoice' : t_invoice
				};
				
				$.ajax({
					url: 'https://info.sweettracker.co.kr/api/v1/trackingInfo',
					data: dataJson,
					type: 'GET',
					dataType: 'json',
					async: false,
					success: function(data) {
						if (data.trackingDetails) {
							var lastStatus = data.trackingDetails[data.trackingDetails.length-1];	
						}
						if ((lastStatus == '배송완료' || data.complete) && dlvySttusCode !='DLVY03') {
							//console.log('배송정보 DB update');
							//alert('배송 완료');
							dlvy_state = '배송완료';
							updateDlvyResult(orderDlvyNo);
						} else {
							dlvy_state = dlvySttusCodeNm;
						}
					},
					error : function () {
						dlvy_state = dlvySttusCodeNm;
					}
				});
			}
			
		}
	
		return dlvy_state;
	}

	updateDlvyResult = function(orderDlvyNo) {
		var dataJson = {
			'orderDlvyNo' : orderDlvyNo,
			'dlvySttusCode' : 'DLVY03'
		};
			
		$.ajax({
			url: CTX_ROOT + '/decms/shop/goods/modifyDlvyStatus.do',
			type: 'POST',
			data: dataJson,
			async: false,
			dataType: 'json',
			success: function(result) {
				//console.log(result.message);
			}
		});
	}
	
	pagingSubscribeNow = function($pagingSector, totalRecordCount, pageIndex) {
		
		const totalPage = Math.ceil(totalRecordCount/pageSize); /*총 페이지 수  - 전체 데이터 개수/한 페이지에 나타낼 데이타*/
		const pageGroup = Math.ceil(pageIndex/pageUnit); /* 페이지 그룹 - 현재 페이지/한 페이지에 보여줄 페이지 수*/
	
		var lastShowIndex = pageGroup * pageSize; // 화면에 보여질 마지막 페이지 번호
		if(lastShowIndex > totalPage){
			lastShowIndex = totalPage;
		}
		var firstShowIndex = lastShowIndex - (pageSize - 1); // 화면에 보여질 첫번째 페이지 번호
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
			htmlStr +='<li class="ppv"><a href="#none" onclick="movePage('+firstIndex+');" title="처음으로"><span class="txt-hide">처음으로</span></a></li>';
			htmlStr +='<li class="pv"><a href="#none" onclick="movePage('+prev+');" title="이전"><span class="txt-hide">이전</span></a></li>';
		}
		//현재 페이지 active
		for(var i=firstShowIndex; i<=lastShowIndex; i++){
			htmlStr += '<li ';
			if(pageIndex == i){
				htmlStr += 'class="is-active" id="currentPage"';
			}
			htmlStr += 'data-page="'+i+'"><a href="#none" onclick="movePage('+i+');" title="to '+i+' page">' +i+ '</a></li>';
		 }
			
		//마지막으로,다음
		if(lastShowIndex < totalPage){
			htmlStr += '<li class="fw"><a href="#none"  onclick="movePage('+next+');" title="다음"><span class="txt-hide">다음</span></a></li>';
			htmlStr += '<li class="ffw"><a href="#none" onclick="movePage('+lastIndex+');"  title="끝으로"><span class="txt-hide">끝으로</span></a></li>';
		}
	   
		$pagingSector.html('');
		$pagingSector.html(htmlStr);
	
	}
		
	//페이지 이동
	movePage = function(currentIndex){
		pageIndex = currentIndex; 
		getSubscribeNowList();
	}
	
	/** 리뷰 기능 */
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
	var orderNo;
	var action;
	var storage = [];
	var commentId;
	var originFiles;
	var atchFileId;
	
	initCommentPopupMyPage = function(mode, goodsId, orderId){
		action = mode;
		orderNo = orderId;
		goods_id = goodsId;
		
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
				modooAlert('리뷰 내용을 10자 이상 입력해 주세요.', '' , function() {
				});
				return false;
			}
			if (!score) {
				modooAlert('평점을 입력해 주세요.', '' , function() {
				});
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
						modooAlert(result.message, '' , function() {
							popClose('reviewWrite');
						});
						getSubscribeNowList();
					}else{
						modooAlert(result.message, '' , function() {
							popClose('reviewWrite');
						});
					}
				}
				
			});
		}
	});
	
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
					
					//console.log(e);
					//console.log(target);
					//console.log(html);
					
				}
			})()
	
			if(get_file){
				reader.readAsDataURL(get_file[0]);
			}
	
			/* file form data 처리 */
			storage.push(get_file[0]);
		} else {
			modooAlert('파일은 최대 5개까지 업로드 할 수 있습니다.', '' , function() {
			
			});
		}
	
		/* file input 초기화 */
		$('#fileAdd').val('');

	});
	
	// 리뷰 이미지 삭제
	deleteImg = function(item) {
		
		var id = $(item).attr('id');
		var idx = $(item).index();
		$('li#' + id).remove();
		
		//console.log(idx);
		storage.splice(idx, 1);
		originFiles.splice(idx, 1);
		
		//console.log("파일 목록:", originFiles);
	}
	
	showCommentDetail = function(id) {
		popOpen('reviewView');
		
		var actionUrl = CTX_ROOT + '/shop/goods/comment/commentDetail.json';
		var dataJson = {'commentId' : id}; 
		
		$.ajax({
			url:actionUrl,
			data:dataJson,
			dataType:'json',
			success:function(json){
				console.log(json);
				
				var html = '';
				
				html += '<span class="grade sm">';
	            html += '<span class="grade-per" style="width:' + starPer[json.score] + '"></span>';
	            html += '</span>';
	            html += '<span class="date">' + moment(json.frstRegistPnttm).format('YYYY-MM-DD') + '</span>';
	            html += '<dl>';
				html += '<dt>옵션</dt>';
				html += '<dd>' + '상품명' +'</dd>';
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
	
	/** 이벤트 목록 */
	$(document).ready(function(){
		getSubscribeNowList();
	});
	
	$(document).on('click','#commentClose',function(){
		popClose('reviewWrite');
	});
	
	$(document).on('click','.grade-check-area button',function(){
		score = $(this).data('val');
	});

})();