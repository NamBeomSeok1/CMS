(function() {

	var pageUnit = 5;
	var pageSize = 5;
	var pageIndex = 1; //현재 페이지

	getSubscribeCancelList = function(page) {
		var dataJson = {
			'pageUnit' : pageUnit,
			'pageSize' : pageSize,
			'pageIndex' : pageIndex,
			'orderSttusCode' : 'ST04'
		};
		
		console.log(page);
	
		$.ajax({
			url:CTX_ROOT + '/shop/goods/subscribeNow.json',
			data:dataJson,
			dataType:'json',
			cache:false,
			type:'get',
			success:function(json){
				console.log(json);
	
				var list = json.resultList;
				var html='<div class="thead"><cite class="col-w200">주문번호</cite><cite>상품정보</cite>';
				html += '<cite class="col-w250">주문정보</cite><cite class="col-w200">결제금액</cite><cite class="col-w150"></cite></div>';
				
				if (list.length > 0) {
					for(var i=0; i<list.length; i++){
						html += '<div class="tbody">';
						html += '<div class="col-w200">';
						html += list[i].orderNo;
						html += '<br class="m-none" />';
						var href = CTX_ROOT + '/user/my/mySubscribeView.do?orderNo=' + list[i].orderNo + '&menuId=sbs_mySubscribeCancel';
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
						}
		
						html += list[i].orderSttusCodeNm + '</p>';
						html += '</div>';
						html += '</div>';
						html += '</a>';
						html += '</div>';
						
						html += '<div class="col-w250 al m-col-block">';
						html += '<ul class="option-info">';
						if (list[i].orderKndCode == 'SBS') {
							if(list[i].sbscrptCycleSeCode == 'WEEK'){
								html += '<li>';
								html += '<strong>구독주기 :</strong>';
								html += '<span>' + list[i].sbscrptCycle + '</span>';
								html += '</li>';
								html += '<li>';
								html += '<strong>정기결제일 :</strong>';
								html += '<span>' + list[i].sbscrptDlvyWdNm + '요일</span>';
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
						html += '<strong><span>' + list[i].setleTotAmount + '</span>원</strong>';
						html += '</div>';
						html += '</div>';
						html += '<div class="col-w150 m-col-block">';
						if (list[i].orderSttusCode == 'ST01') {
							html += '<div class="btn-area">';					
							html += '<a href="#noen" class="btn" onclick="cancelSubscribe(\'' + list[i].orderNo + '\')">구독해지 취소</a>';
							html += '</div>';
						}
						html += '</div>';
						html += '</div>';
					}
				} else {
					html += '<div class="tbody">';
					html += '<p class="none-txt">주문취소한 상품이 없습니다.</p>';
					html += '</div>';
				}
				$('#table-sbs-cancel').html(html);
				
				pagingSubscribeCancel($('#paging-sbs-cancel'), json.paginationInfo.totalRecordCount, pageIndex);
			}
		});
		
	}
	
	pagingSubscribeCancel = function($pagingSector, totalRecordCount, pageIndex) {
		
		const totalPage = Math.ceil(totalRecordCount/pageSize); /* 총 페이지 수  - 전체 데이터 개수/한 페이지에 나타낼 데이타 */
		const pageGroup = Math.ceil(pageIndex/pageUnit); /* 페이지 그룹 - 현재 페이지/한 페이지에 보여줄 페이지 수 */
	
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
	movePage = function(currentIndex) {
		pageIndex = currentIndex; 
		getSubscribeCancelList(pageIndex);
	}
	
	cancelSubscribe = function(id) {
		modooConfirm('구독해지 요청을 취소하시겠습니까?' ,'', function(result) {
			if(result) {
				var dataJson = {
					"orderNo" : id
				};
				$.ajax({
					url:CTX_ROOT + '/shop/goods/stopSubscribeCancel.do',
					data:dataJson,
					method:'post',
					dataType:'json',
					success:function(json){
						modooAlert('구독해지 요청이 취소되었습니다.');
						location.href = CTX_ROOT + '/user/my/mySubscribeNow.do';
					}
				});
			}
		});
	}
	
	/** 이벤트 목록 */
	$(document).ready(function(){
		getSubscribeCancelList(1);
	});

})();