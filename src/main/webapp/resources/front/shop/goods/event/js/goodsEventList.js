(function() {

	const pageUnitEvent = 6;
	var pageNow = 1;

	$(document).ready(function(){
		getDataList(1);
	});
	
	$(document).on('change', 'select[name="searchOrder"]', function(){
		reloadDataList(1);
	});

	getDataList = function(pageIndex) {
		var searchOrder = $('select[name="searchOrder"]').val();
		var dataJson = {
			'pageIndex' : pageNow,
			'pageUnit' : pageUnitEvent,
			'searchOrder' : searchOrder
		};
	
		$.ajax({
			url:CTX_ROOT + '/shop/event/eventList.json',
			type:'GET',
			data:dataJson,
			dataType:'json',
			success:function(result){
				var list = result.data.list;
				var cnt = result.data.paginationInfo.totalRecordCount;
				var html = '';
				
				for (var i=0; i<list.length; i++){
					if (list[i].endAt == 'Y') {
						html += '<li class="finish">';
						html += '<a href="#none" onclick="modooAlert(\'종료된 이벤트입니다.\', \'\', function() {});">';
					} else {
						html += '<li>';
						html += '<a href="' + list[i].eventUrl + '">';
					}
					/*if (list[i].eventUrl && list[i].endAt == 'N') {
						
					} else {
						
					}*/
					html += '<div class="event-img-area">';
					html += '<img src="' + list[i].eventThumbnail + '" alt=" " />';
					html += '</div>';
					html += '<div class="event-txt-area">';
					html += '<span class="time">' + list[i].eventBeginDt + ' ~ ' + list[i].eventEndDt + '</span>';
					html += '<h3>' + list[i].eventSj + '</h3>';
					html += '<p>' + list[i].eventCn + '</p>';
					html += '<div class="user-profile">';
					
					if (list[i].cmpnyNm) {
						html += '<i style="background-image:url("' + list[i].eventThumbnail + '")"></i>';
						html += '<cite>' + list[i].cmpnyNm + '</cite>';
					} else {
						html += '<i style="background-image:url(\'/resources/front/site/SITE_00000/image/logo/modoo_logo_simple.png\')"></i>';
						html += '<cite>모두의구독</cite>';
					}
					html += '</div>';
					html += '</div>';
					html += '</a>';
					html += '</li>';
					
					if (i+1 == pageUnitEvent) {
						break;
					}
				}
				$('#eventList').append(html);
				$('#eventCnt').text(cnt);

				//더보기 버튼
				if (cnt > pageUnitEvent * pageIndex) {
					var htmlStr = '';
					htmlStr += '<div class="btn-area">';
	                htmlStr += '<a href="#none" class="btn-lg width spot3" id="show-more">더보기<i class="ico-arr-b sm back" aria-hidden="true"></i></a>';
	            	htmlStr += '</div>';
					$('#show-more-area').html(htmlStr);
				} else {
					$('#show-more-area').html('');
				}
				
			}						
		});
	
	}
	
	reloadDataList = function(pageIndex) {
		var searchOrder = $('select[name="searchOrder"]').val();
		var dataJson = {
			'pageIndex' : pageNow,
			'pageUnit' : pageUnitEvent,
			'searchOrder' : searchOrder
		};
	
		$.ajax({
			url:CTX_ROOT + '/shop/event/eventList.json',
			type:'GET',
			data:dataJson,
			dataType:'json',
			success:function(result){
				var list = result.data.list;
				var cnt = result.data.paginationInfo.totalRecordCount;
				console.log(list);
				console.log(cnt);
				var html = '';
				
				/*if (cnt == '0') {
					html += '<p>진행 중인 이벤트가 없습니다.</p>'; 
					$('#eventList').html(html);
				} else {
				
				}*/
				
				for (var i=0; i<list.length; i++){
					if (list[i].endAt == 'Y') {
						html += '<li class="finish">';
					} else {
						html += '<li>';
					}
					if (list[i].eventUrl && list[i].endAt != 'Y') {
						html += '<a href="' + list[i].eventUrl + '">';
					} else {
						html += '<a href="#none" onclick="modooAlert(\'종료된 이벤트입니다.\', \'\', function() {});">';
					}
					html += '<div class="event-img-area">';
					html += '<img src="' + list[i].eventThumbnail + '" alt=" " />';
					html += '</div>';
					html += '<div class="event-txt-area">';
					html += '<span class="time">' + list[i].eventBeginDt + ' ~ ' + list[i].eventEndDt + '</span>';
					html += '<h3>' + list[i].eventSj + '</h3>';
					html += '<p>' + list[i].eventCn + '</p>';
					html += '<div class="user-profile">';
					
					if (list[i].cmpnyNm) {
						html += '<i style="background-image:url("' + list[i].eventThumbnail + '")"></i>';
						html += '<cite>' + list[i].cmpnyNm + '</cite>';
					} else {
						html += '<i style="background-image:url(\'/resources/front/site/SITE_00000/image/logo/modoo_logo_simple.png\')"></i>';
						html += '<cite>모두의구독</cite>';
					}
					html += '</div>';
					html += '</div>';
					html += '</a>';
					html += '</li>';
					
					if (i+1 == pageUnitEvent) {
						break;
					}
				}
				$('#eventList').html(html);
				$('#eventCnt').text(cnt);

				//더보기 버튼
				if (cnt > pageUnitEvent * pageIndex) {
					var htmlStr = '';
					htmlStr += '<div class="btn-area">';
	                htmlStr += '<a href="#none" class="btn-lg width spot3" id="show-more">더보기<i class="ico-arr-b sm back" aria-hidden="true"></i></a>';
	            	htmlStr += '</div>';
					$('#show-more-area').html(htmlStr);
				} else {
					$('#show-more-area').html('');
				}
				
			}						
		});
	
	}
	
	$(document).on('click', '#show-more', function() {
		pageNow++;
		getDataList(pageNow);
	})	
})();