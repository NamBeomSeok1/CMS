(function(){
	var order_no;
	var site_id = $('input[name="siteId"]').val();
	
	/** 이벤트 처리 */
	$(document).on('click','#refund-regist',function(){
		exchangeOrRecallOrder(order_no);
		alert(23);
	});

	/** 교환, 환불 */
	var storage = [];
	var file_cnt = 0;

	initExchangeOrRecallPop = function(orderNo) {
		order_no = orderNo;
		popOpen('reFundWrite');
	}
	
	exchangeOrRecallOrder = function(orderNo) {
		var refundFormData = new FormData();
		
		refundFormData.append('orderNo', orderNo);
		refundFormData.append('qestnSj', '제목');
		refundFormData.append('qestnCn', $('#qestnCn').val());
		refundFormData.append('qaSeCode', $('select[name="qaSeCode"]').val());
		refundFormData.append('qestnTyCode', 'QT999');
		refundFormData.append('secretAt', 'N');
		refundFormData.append('siteId', site_id);
		
		for (var i =0; i < storage.length; i++) {
			refundFormData.append('atchFile', storage[i]);
		}

		if (validation()) {
			$.ajax({
				url: CTX_ROOT + '/shop/goods/refundOrder.do',
				type: 'POST',
				data: refundFormData,
				dataType: 'json',
				enctype:'multipart/form-data',
				processData: false,
				contentType: false,
				success: function(result) {
					modooAlert(result.message,function(){
						location.href = CTX_ROOT + '/user/my/myRefund.do';
					});
				}
			});
		}
		
		function validation() {
		
			if ($('select[name="qaSeCode"]').val().length == 0) {
				modooAlert('교환/환불 유형을 선택해주세요.');
				return false;
			}
			if ($('#qestnCn').val().length == 0) {
				modooAlert('사유를 입력해주세요.');
				return false;
			}
	
			return true;
		}
	}
	
	$("#fileAdd").change(function(e){
		var get_file = e.target.files;
		var reader = new FileReader();
		var html = '';
		var target = document.getElementById('file-refund');
	
		if (storage.length < 3)  {
			reader.onload = (function () {
				return function (e) {
					html += '<li id="img_' + file_cnt +'">';
					html += '<div><img src="' + e.target.result + '" alt=" " /></div>';
					html += '<button type="button" class="btn-del-r" onclick="deleteImg(this)" id="img_' + file_cnt +'"><span class="txt-hide">삭제</span></button>';
					html += '</li>';
	
					file_cnt++;
					$(target).append(html);
				}
			})()
	
			if(get_file){
				reader.readAsDataURL(get_file[0]);
			}
			storage.push(get_file[0]);
		} else {
			modooAlert('파일은 최대 3개까지 업로드 할 수 있습니다.');
		}
		
		$('#fileAdd').val('');
	});

	deleteImg = function(item) {
		var id = $(item).attr('id');
		var idx = $(item).index();
		$('li#' + id).remove();
		storage.splice(idx, 1);
	}

	/** 배송 조회 */
	showDlvyStatus =  function(hdryId, invcNo) {
		var t_key='yWaX4OwVwrgofOXkfUP4eQ';
		var t_code = hdryId;
		var t_invoice = invcNo;
		var errorMsg = null;
	
		var dataJson = {
			't_key' : t_key,
			't_code' : t_code,
			't_invoice' : t_invoice
		};
	
		$.ajax({
			url: 'http://info.sweettracker.co.kr/api/v1/trackingInfo',
			type: 'GET',
			data: dataJson,
			dataType: 'json',
			async: false,
			success: function(data) {
				if (!data.status && data.msg) {
					//console.log('data.status:', data.status);
					//console.log('data.msg:', data.msg);
					errorMsg = data.msg;
					//alert(data.msg);
				}
			},
			error:function(request,status,error){
				console.log(request, status, error);
				errorMsg = '시스템 장애 발생. 관리자에게 문의하십시오.';
			},
			complete: function() {
				if (errorMsg) {
					modooAlert('배송을 추적할 수 없습니다. 판매자에게 문의하십시오.', '', function() {
					
					});
				} else {
					var _width = 500;
					var _height = 500;
					var _left = Math.ceil(( window.screen.width - _width )/2);
					var _top = Math.ceil(( window.screen.width - _height )/2) - _height; 
					var win = window.open('http://info.sweettracker.co.kr/tracking/3?t_key=' + t_key + '&t_code=' + t_code + '&t_invoice=' + t_invoice,'dlvyviewer','width=' + _width + ',height=' + _height + ', left=' + _left + ', top=' + _top);
				}
			}
		});
	
	}

	/** 주문 취소(상품준비중) */
	cancelOrderDirect = function(orderNo, orderGroupNo, orderDlvyNo,type) {
		var cancleStr = '주문을 취소하시겠습니까?';
		if(type=='SBS'){
			cancleStr = '구독을 해지하시겠습니까?';
		}
		modooConfirm(cancleStr ,'', function(result) {
			if(result) {
				var dataJson = {
					'orderDlvyNo' : orderDlvyNo,
					'reqTyCode' : 'C04',
					'orderReqSttusCode' : 'C',
					'searchOrderGroupNo' : orderGroupNo,
					'orderNo' : orderNo
				};

				$.ajax({
					url: '/decms/shop/goods/modifyProcessStatus.do',
					type: 'POST',
					enctype: 'multipart/form-data',
					data: dataJson,
					dataType: 'json',
					success: function(result) {
						//modooAlert('해당 상품에 대한 주문취소가 정상적으로 접수되었습니다. 카드사에 따라 3~5영업일 이내에 환불이 완료됩니다.', '', function(){
						modooAlert(result.message, '', function(){
							if(type=='CPN'){
								location.href = CTX_ROOT + '/user/my/mySubscribeNow.do';
							} else{
								location.href = CTX_ROOT + '/user/my/mySubscribeCancel.do';
							}
						});
					}
				});
			}
		});
	/*	var cancleStr = '주문을 취소하시겠습니까?';
		if(type=='SBS'){
		 cancleStr = '자동으로 구독 해지가 됩니다. 취소하시겠습니까?';	
		}
		modooConfirm(cancleStr ,'', function(result) {
			if(result) {
				var dataJson = {
					'orderDlvyNo' : orderDlvyNo,
					'reqTyCode' : 'C04',
					'orderReqSttusCode' : 'C',
					'searchOrderGroupNo' : orderGroupNo,
					'orderNo' : orderNo
				};
				
				$.ajax({
					url: '/decms/shop/goods/modifyProcessStatus.do',
					type: 'POST',
					data: dataJson,
					dataType: 'json',
					success: function(result) {
						modooAlert('해당 상품에 대한 주문취소가 정상적으로 접수되었습니다. 카드사에 따라 3~5영업일 이내에 환불이 완료됩니다.', '', function(){
							location.href = CTX_ROOT + '/user/my/mySubscribeCancel.do';
						});
					}
				});
			}
		});*/
	};
	
	/** 주문취소 (관리자 승인 필요) */
	cancelOrder = function(orderNo,type) {
		var cancleStr = '주문을 취소하시겠습니까?';
		if(type=='SBS'){
		 cancleStr = '자동으로 구독 해지가 됩니다. 취소하시겠습니까?';	
		}else if(type=='CPN'){
		cancleStr = '쿠폰사용여부를 확인 후, 취소가 완료됩니다. 취소요청하시겠습니까?';
		}
		modooConfirm(cancleStr ,'', function(result) {
			if(result) {
				var dataJson = {
					'orderNo' : orderNo
				};
			
				$.ajax({
					url: CTX_ROOT + '/shop/goods/cancelOrder.do',
					type: 'POST',
					data: dataJson,
					cache: false,
					dataType: 'json',
					success: function(result) {
						modooAlert('해당 상품에 대한 주문취소가 접수되었습니다. 확인 후 연락 드리겠습니다.', '', function(){
							location.href = CTX_ROOT + '/user/my/mySubscribeNow.do';
						});
					}
				});
			}
		});
	}

	/** 구독해지 */
	stopSubscribe = function(orderNo) {
		modooConfirm('구독 해지를 요청하시겠습니까? 관리자가 확인하면 해지를 취소할 수 없습니다.', '', function(result) {
			if(result) {
				var dataJson = {
					'orderNo' : orderNo
				};
				
				$.ajax({
					url: CTX_ROOT + '/shop/goods/stopSubscribe.do',
					type: 'POST',
					data: dataJson,
					dataType: 'json',
					success: function(result) {
						modooAlert('구독 해지 요청이 정상적으로 접수되었습니다.', '', function(){
							location.href = CTX_ROOT + '/user/my/mySubscribeCancel.do';
						});
					}
			});
		}
	});
}

	/** 회차별 상세보기 */
	var pageUnitHist = 5;
	var pageSizeHist = 5;
	var pageIndexHist = 1; //현재 페이지
	
	showDlvyHistory = function(orderNo) {
		order_no = orderNo;
	
		var dataJson = {
			'pageUnit' : pageUnitHist,
			'pageSize' : pageSizeHist,
			'pageIndex' : pageIndexHist,
			'orderNo' : orderNo
		};

		$.ajax({
			url:'/user/my/subscribeDetail.json',
			data: dataJson,
			dataType:'json',
			type:'get',
			success:function(result){
				var list = result.data.list;
				var html = '';
				
				if (list.length > 0) {
					for (var i=0; i<list.length; i++) {
						html += '<tr>';
						html += '<td data-title="회차">' + list[i].orderOdr + '</td>';
						html += '<td data-title="결제주기">' + list[i].sbscrptCycle + '</td>';
						html += '<td data-title="결제날짜(요일)">' + list[i].sbscrptDlvyDay + '</td>';
						html += '<td data-title="상품명" class="al">' + list[i].goodsNm + '</td>';
						html += '<td data-title="수량 / 옵션" class="al">';
						html += '<ul class="option-info">';
						html += '<li>';
						html += '<cite>수량 :</cite>';
						html += '<span>' + list[i].orderCo + '</span>';
						html += '</li>';
						html += '</ul>';
						html += '<ul class="option-info">';
						
						if (list[i].orderInfo != null) {
							html += '<li>';
							html += '<cite>' + list[i].orderInfo+' </cite>';
							html += '</li>';
						}
						
						html += '</ul>';
						html += '</td>';
						html += '<td data-title="배송지" class="al">' + list[i].dlvyAdres + '</td>';
						html += '<td data-title="결제일">' + list[i].setlePnttm;
						html += '<td data-title="결제금액">' + list[i].setleAmount + '원';
						
						if (list[i].setleSttusCode == 'R') {
							html += '<span class="block fc-gr fs-sm">(예정)</span>'
						}
						
						html += '</td>';
						html += '</tr>';
						
					}
					pagingHistory($('#paging-history'), result.data.paginationInfo.totalRecordCount, pageIndexHist);
				} else {
					html += '<tr><td colspan="7"><p class="none-txt">진행중인 구독이 없습니다.</p></td></tr>';
				}
				$('#tbody-history').html(html);
				popOpen('subscribeHistory');
			}
		});
	
	}
	
	pagingHistory = function($pagingSector, totalRecordCount, pageIndex) {
		
		const totalPage = Math.ceil(totalRecordCount/pageSizeHist); /*총 페이지 수  - 전체 데이터 개수/한 페이지에 나타낼 데이타*/
		const pageGroup = Math.ceil(pageIndexHist/pageUnitHist); /* 페이지 그룹 - 현재 페이지/한 페이지에 보여줄 페이지 수*/
	
		var lastShowIndex = pageGroup * pageSizeHist; // 화면에 보여질 마지막 페이지 번호
		if(lastShowIndex > totalPage){
			lastShowIndex = totalPage;
		}
		var firstShowIndex = lastShowIndex - (pageSizeHist - 1); // 화면에 보여질 첫번째 페이지 번호
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
			htmlStr +='<li class="ppv"><a href="#none" onclick="movePageHist('+firstIndex+');" title="처음으로"><span class="txt-hide">처음으로</span></a></li>';
			htmlStr +='<li class="pv"><a href="#none" onclick="movePageHist('+prev+');" title="이전"><span class="txt-hide">이전</span></a></li>';
		}
		//현재 페이지 active
		for(var i=firstShowIndex; i<=lastShowIndex; i++){
			htmlStr += '<li ';
			if(pageIndex == i){
				htmlStr += 'class="is-active" id="currentPage"';
			}
			htmlStr += 'data-page="'+i+'"><a href="#none" onclick="movePageHist('+i+');" title="to '+i+' page">' +i+ '</a></li>';
		 }
			
		//마지막으로,다음
		if(lastShowIndex < totalPage){
			htmlStr += '<li class="fw"><a href="#none"  onclick="movePageHist('+next+');" title="다음"><span class="txt-hide">다음</span></a></li>';
			htmlStr += '<li class="ffw"><a href="#none" onclick="movePageHist('+lastIndex+');"  title="끝으로"><span class="txt-hide">끝으로</span></a></li>';
		}
	   
		$pagingSector.html('');
		$pagingSector.html(htmlStr);
		popPosition('subscribeHistory');
	
	}
		
	//페이지 이동
	movePageHist = function(currentIndex){
		pageIndexHist = currentIndex; 
		showDlvyHistory(order_no);
	}
		
	/** 건너뛰기/일시정지 해제 */
	$(document).on('click','.sbsModBtn',function(){
		var dlvyNo = $(this).data('dlvyno');
		var kind = $(this).data('kind');
		if(kind=='pause'){
			modooConfirm('이번 회차 구독을 다음 회차로 건너뛰시겠습니까?','구독 건너뛰기',function(result){
			if(result){
				$.ajax({
					url:'/user/my/modSbs.json',
					data:{dlvyno:dlvyNo,
						  kind:kind
					},
					dataType:'json',
					type:'get',
					success:function(result){
						if(result.success){
							modooAlert('건너뛰기가 정상적으로 처리되었습니다.', '', function(){
								window.location.reload();
							});
						}else{
							modooAlert(result.message);
						}
					}
				})
				}else{
					return false;
				}	
			})
		}else{
			modooConfirm('건너뛰기를 취소하시겠습니까? 해당회차 정기결제일에 결제가 이루어집니다.','구독 건너뛰기 해제',function(result){
				if(result){
					$.ajax({
						url:'/user/my/modSbs.json',
						data:{dlvyno:dlvyNo,
							  kind:kind
						},
						dataType:'json',
						type:'get',
						success:function(result){
							if(result.success){
								modooAlert('건너뛰기가 해제가 정상적으로 처리되었습니다.', '', function(){
									window.location.reload();
								});
							}else{
								modooAlert(result.message);
							}
						}
					})
					}else{
						return false;
					}	
				})
		}
	});
	

})();