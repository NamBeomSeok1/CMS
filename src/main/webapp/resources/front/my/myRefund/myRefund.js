(function() {
	var pageUnitRefund = 5;
	var pageSizeRefund = 5;
	var pageIndexRefund = 1;

	getMyRefundList = function(){
		var dataJson = {
			'pageUnit' : pageUnitRefund,
			'pageSize' : pageSizeRefund,
			'pageIndex' : pageIndexRefund
		};
		
		$.ajax({
			url:CTX_ROOT + '/shop/goods/myRefundList.json',
			data:dataJson,
			dataType:'json',
			cache:false,
			type:'get',
			success:function(json){
				console.log(json);
	
				var list = json.data.resultList;
				var html='';
				html += '<div class="thead">';
				html += '<cite class="col-w200">주문번호</cite>';
				html += '<cite>상품정보</cite>';
				html += '<cite class="col-w300">주문정보</cite>';
				html += '<cite class="col-w150">접수</cite>';
				html += '<cite class="col-w150">완료</cite>';
				html += '<cite class="col-w150">상태</cite>';
				html += '</div>';
				
				if (list.length > 0) {
					for(var i=0; i<list.length; i++){
						
						html +='<div class="tbody">';
						html +='<div class="col-w200">';
						html += list[i].orderNo + '<br class="m-none" />';
						var href = CTX_ROOT + '/user/my/mySubscribeView.do?orderNo=' + list[i].orderNo+"&menuNm=refund";
						html +='<a href="' + href + '" class="fc-gr sm">상세보기 <i class="ico-arr-r sm back gr" aria-hidden="true"></i></a>';
						html +='</div>';
						html +='<div class="al m-col-block">';
						html +='<a href="/shop/goods/goodsView.do?goodsId=' + list[i].goodsId +'">';
						html +='<div class="thumb-area lg">';
						html += '<figure><img src="' + list[i].goodsImageThumbPath + '" alt="' + list[i].goodsNm + '" /></figure>';
						html +='<div class="txt-area">';
						html +='<h2 class="tit">[' + list[i].orderSttusCodeNm + '] ' + list[i].goodsNm + '</h2>';
						
						if (list[i].goodsKndCode == 'SBS') {
							html +='<p class="fc-gr">' + list[i].nowOdr +'회차 / ' + list[i].dlvySttusCodeNm + '</p>';
						}

						html +='				</div>';
						html +='			</div>';
						html +='		</a>';
						html +='	</div>';
						html +='	<div class="al m-col-block col-w300">';
						
						if (list[i].goodsKndCode == 'SBS') {
							html +='		<ul class="option-info">';
							html +='			<li>';
							html +='				<strong>구독주기 :</strong>';
							html +='				<span>' + list[i].sbscrptCycleSeCodeNm +'</span>';
							html +='			</li>';
							html +='			<li>';
							html +='				<strong>정기결제일 :</strong>';
							html +='				<span>' + list[i].sbscrptDlvyDay +'</span>';
							html +='			</li>';
							html +='		</ul>';
						}
						html +='		<ul class="option-info">';
						html +='			<li>';
						html +='				<cite>수량 :</cite>';
						html +='				<span>' + list[i].orderCo + '개</span>';
						html +='			</li>';
						html +='		</ul>';
						html +='	</div>';
						html +='	<div class="col-w150">';
						//html +='		<span class="fc-gr block">교환접수</span>';
						html += moment(list[i].frstRegistPnttm).format('YYYY-MM-DD') ;
						html +='	</div>';
						html +='	<div class="col-w150">';
						//html +='		<span class="fc-gr block">교환처리</span>';
						if (list[i].lastUpdtPnttm != null) {
							html += moment(list[i].lastUpdtPnttm).format('YYYY-MM-DD');
						}
						html +='	</div>';
						html +='	<div class="col-w150">';
						html +='		<em>' + list[i].reqTyCodeNm + '</em>';
						html +='	</div>';
						html +='</div>';
					}
				} else {
					html += '<div class="tbody">';
					html += '<p class="none-txt">교환 및 환불 내역이 없습니다.</p>';
					html += '</div>';
				}
				$('#table-refund').html(html);
				
				pagingRefund($('#paging-refund'), json.data.paginationInfo.totalRecordCount, pageIndexRefund);
			}
		});
		
	}

	// 페이징
	pagingRefund = function($pagingSector, totalRecordCount, pageIndex) {
		
		const totalPage = Math.ceil(totalRecordCount/pageSizeRefund); //총 페이지 수  - 전체 데이터 개수/한 페이지에 나타낼 데이타
		const pageGroup = Math.ceil(pageIndex/pageUnitRefund); //페이지 그룹 - 현재 페이지/한 페이지에 보여줄 페이지 수
	
		var lastShowIndex = pageGroup * pageSizeRefund; // 화면에 보여질 마지막 페이지 번호
		if(lastShowIndex > totalPage){
			lastShowIndex = totalPage;
		}
		var firstShowIndex = lastShowIndex - (pageSizeRefund - 1); // 화면에 보여질 첫번째 페이지 번호
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
			htmlStr +='<li class="ppv"><a href="#none" onclick="movePageRefund('+firstIndex+');" title="처음으로"><span class="txt-hide">처음으로</span></a></li>';
			htmlStr +='<li class="pv"><a href="#none" onclick="movePageRefund('+prev+');" title="이전"><span class="txt-hide">이전</span></a></li>';
		}
		//현재 페이지 active
		for(var i=firstShowIndex; i<=lastShowIndex; i++){
			htmlStr += '<li ';
			if(pageIndex == i){
				htmlStr += 'class="is-active" id="currentPage"';
			}
			htmlStr += 'data-page="'+i+'"><a href="#none" onclick="movePageRefund('+i+');" title="to '+i+' page">' +i+ '</a></li>';
		 }
			
		//마지막으로,다음
		if(lastShowIndex < totalPage){
			htmlStr += '<li class="fw"><a href="#none"  onclick="movePageRefund('+next+');" title="다음"><span class="txt-hide">다음</span></a></li>';
			htmlStr += '<li class="ffw"><a href="#none" onclick="movePageRefund('+lastIndex+');"  title="끝으로"><span class="txt-hide">끝으로</span></a></li>';
		}
	   
		$pagingSector.html('');
		$pagingSector.html(htmlStr);
	
	}
	
	//페이지 이동
	movePageRefund = function(currentPage){
		pageIndexRefund = currentPage; 
		getMyRefundList(pageIndexRefund);
	}
	
	/** 이벤트 목록 */
	$(document).ready(function(){
		getMyRefundList();
	});

})();