(function() {

	var currentPageAddressPop = 0;

	modifyBassAddress = function() {
		
		var dadresNo = $('input:radio[name="delivery"]:checked').val();
		var actionUrl = CTX_ROOT + '/shop/goods/user/modifyBassAddress.do';
		var dataJson = {
			'dadresNo' : dadresNo
		};
		
		modooConfirm('기본 배송지를 변경하시겠습니까?' ,'확인', function(result) {
			if(result) {
				$.ajax({
					url:actionUrl,
					data:dataJson,
					dataType:'json',
					type:'POST',
					success:function(json){
						modooAlert(json.message,'확인',function(){
							window.location.reload();
						});
						var str = json.data.bassDlvyAdre.dlvyAdres + ', ' + json.data.bassDlvyAdre.dlvyAdresDetail;
						str += '<div class="btn-area ar"><button type="button" class="btn" onclick="initAddressPop();"id="changeAddress">변경하기</button></div>';
						
						$('#bassDlvyAdres').html(str);
					}
				});
			}else {
				//console.log('cancel...........');
			}
		});
	
	}
	
	initAddressPop = function() {
		popOpen('deliveryEdit');
		getMyAddressList(currentPageAddressPop);
	}
	
	deleteAddress = function(id) {
		var actionUrl = CTX_ROOT + '/shop/goods/user/deleteAddress.do';
		var dataJson = {
			'dadresNo' : id
		};
		
		modooConfirm('배송지를 삭제하시겠습니까?', '배송지 삭제',function(result) {
			if(result) {
				$.ajax({
					url:actionUrl,
					data:dataJson,
					dataType:'json',
					type:'POST',
					success:function(json){
						modooAlert(json.message, '확인', function() {
							window.location.reload();
						});
					}
				});	
			}
		});
	}
	
	registAddress = function () {
		var actionUrl = CTX_ROOT + '/shop/goods/user/registAddress.do';
		
		var dataJson = {
			'dlvyAdresNm': $('#dlvyAdresNm').val()
			, 'dlvyZip': $('#dlvyZip').val()
			, 'dlvyAdres': $('#dlvyAdres').val()
			, 'dlvyAdresDetail': $('#dlvyAdresDetail').val()
			, 'telno': $('#telno1').val() + '-' + $('#telno2').val() + '-' + $('#telno3').val()
			, 'dlvyUserNm': $('#dlvyUserNm').val()
			, 'bassDlvyAt' : $('#bassDlvyAt').val()
		};
	
		function validation() {
			
			if ($('#dlvyUserNm').val().length == 0) {
				modooAlert('수령인을 입력하세요.');
				return false;
			}
			if ($('#telno1').val().length == 0 || $('#telno2').val().length == 0 || $('#telno3').val().length == 0) {
				modooAlert('연락처를 입력하세요.');
				return false;
			}
			if ($('#dlvyZip').val().length == 0 ) {
				modooAlert('배송주소를 입력하세요.');
				return false;
			}
			if (isEmpty($('#dlvyAdresDetail').val())) {
				modooAlert('상세주소를 입력하세요.');
				return false;
			}
			
			return true;
		}
		
		if (validation()) {
		
			$.ajax({
				url:actionUrl,
				data:dataJson,
				dataType:'json',
				type:'POST',
				success:function(json){
					$('#dlvyAdresNm').val('');
					$('#dlvyZip').val('');
					$('#dlvyAdres').val('');
					$('#dlvyAdresDetail').val('');
					$('#telno1').val('');
					$('#telno2').val('');
					$('#telno3').val('');
					$('#dlvyUserNm').val('');
					$('#bassDlvyAt').val('N');
					
					if(json.message) {
						modooAlert(json.message, '확인', function() {
							popClose('deliveryEdit');	
							window.location.reload();
						});
					}else {
						popClose('deliveryEdit');	
						window.location.reload();
					}
					
				}
			});
		}
	}
	
	getMyAddressList = function(currentPageAddressPop) {
		var actionUrl = CTX_ROOT + '/shop/goods/user/dlvyAdresList.json';
		var dataJson = {
			'firstIndex' : currentPageAddressPop
		};
	
		$.ajax({
			url:actionUrl,
			data:dataJson,
			dataType:'json',
			type:'get',
			success:function(json){
				
				var list= json.resultList;
				var html='';
				
				if (list.length > 0) {
					//주문-리뷰 목록
					for(var i=0; i<list.length; i++){
						html += '<li>';
	                    html += '<label><input type="radio" name="delivery" value=' + list[i].dadresNo ;
						if (list[i].bassDlvyAt == 'Y') {
							html += ' checked';
						}
						html += ' /></label>';
	                    html += '<div class="txt-area">';
	                    html += '<strong>' + list[i].dlvyAdresNm + ' │ ' + list[i].dlvyUserNm;
						if (list[i].bassDlvyAt == 'Y') {
							html += '<span>[ 기본 배송지 ]</span>';
						}
						html += '</strong>';
	                    html += '<p>' + list[i].dlvyAdres + '</p>';
	                    html += '<p class="fc-gr">' + list[i].telno + '</p>';
	                    html += '</div>';
	                    html += '<button type="button" class="btn-del" onclick="deleteAddress(' + list[i].dadresNo +')"><span class="txt-hide">삭제</span></button>';
	                    html += '</li>';	
					}
				} else {
					html += '<p class="none-txt">등록된 배송지가 없습니다.</p>';
				}
				
				$('#address-list').html(html);
				//console.log($('#deliveryEdit').height());
				popPosition($('#deliveryEdit'));
				
	
				//페이징
				pagingAddressPop($('#paging-review-todo'), json.paginationInfo.totalRecordCount, currentPageAddressPop);
			},
			complete: function() {
				popPosition($('#deliveryEdit'));
				$("input[type=radio]").checkboxradio();
			}
		})	
	
	}

	// 페이징
	pagingAddressPop = function ($pagingSector, totalCount, currentPage){
	       
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
			 htmlStr +='<li class="ppv"><a href="#none" onclick="movePageAddressPop('+foreFront+');" title="처음으로"><span class="txt-hide">처음으로</span></a></li>';
			 htmlStr +='<li class="pv"><a href="#none" onclick="movePageAddressPop('+prev+');" title="이전"><span class="txt-hide">이전</span></a></li>';
		   
		}
		//페이지 set
			for(var i=first; i<=last; i++){
				if(currentPage == i ){
					htmlStr += '<li class="is-active" id="currentPage" data-page="'+i+'"><a href="#none" onclick="movePageAddressPop('+i+');" title="to '+i+' page">'+i+'</a></li>';
				 }else{
					htmlStr += '<li><a href="#none" onclick="movePageAddressPop('+i+');" title="to '+i+' page">'+i+'</a></li>'
				}
			 }
			
		//마지막으로,다음
		if(last<totalPage){
			if(last==totalPage){
				htmlStr+='';
			}
			htmlStr += '<li class="fw"><a href="#none"  onclick="movePageAddressPop('+next+');" title="다음"><span class="txt-hide">다음</span></a></li>';
			htmlStr += '<li class="ffw"><a href="#none" onclick="movePageAddressPop('+rearMost+');"  title="끝으로"><span class="txt-hide">끝으로</span></a></li>';
		}
	   
		$pagingSector.empty();
		$pagingSector.html(htmlStr);
	
	}
		
	//페이지 이동
	movePageAddressPop = function (currentPage){
		$('.review-list').empty();
		currentPageReviewTodo = currentPage; 
		getMyCommentTodoList(currentPageReviewTodo);
	}
	
		/** 이벤트 목록 */
	$('.tabs ul li').on('click', function () {
		popPosition($('.popup'));
	});
	
	$('#registAddress').on('click', function () {
		var activeTab = $('.ui-tabs-active a').attr('href');
		if ( activeTab == '#deliveryEdit1') {
			modifyBassAddress();
		} else if ( activeTab == '#deliveryEdit2') {
			registAddress();	
		}
		//window.location.reload();
	});
	
	$('#findAddress').on('click', function () {
		popOpen2('adress');
	});
	
	/*$(document).on('click','#changeAddress',function(e){
		e.preventDefault();
		popOpen('deliveryEdit');
		getMyAddressList(currentPageAddressPop);
		
	})*/
	
	$(document).on('change','#bassDlvyAt',function(){
		if($(this).is(':checked')==true){
			$(this).val('Y');
		}else{
			$(this).val('N');
		}
	})

})();