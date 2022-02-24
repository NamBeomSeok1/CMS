(function() {

	const grid = new tui.Grid({
		el: document.getElementById('data-grid'),
		rowHeight: 'auto',
		columns: [
			{ header: '주문번호', name: 'orderNo', align: 'center', width: 160, 
				formatter: function(item) {
					if(item.row.orderKndCode == 'GNR') {
						return item.value;
					}else if(item.row.orderKndCode == 'SBS') {
						return (item.value == '') ? item.value : '<a href="#none" onclick="initDlvyPop(\'' + item.value + '\')" >' + item.value + '</a>';	
					}else if(item.row.orderKndCode == 'CPN'){
						return (item.value == '') ? item.value : '<a href="#none" onclick="initCouponPop(\'' + item.value + '\')" >' + item.value + '</a>';
					}
				}
			},
			{ header: '차수', name: 'orderOdr', align: 'center', width: 100,
				formatter: function(item) {
					if (item.row.orderKndCode == 'SBS') {
							return item.value + ' 회차';
					} else {
						return '';
					}
				} 
			},
			{ header: '결제일', name: 'setlePnttm', align: 'center', width: 100,
				formatter: function(item) {
					return moment(item.row.setlePnttm).format('YYYY-MM-DD <br/> HH:mm:ss');
				}
			},
			{ header: '제휴사', name: 'prtnrNm', align: 'center', width: 70 ,
				formatter: function(item) {
					if(item.row.prtnrId == 'PRTNR_0000') {
						return '<div class="bg-warning text-white">' + item.value + '</div>';
					}else {
						return '<div class="bg-secondary text-white">' + item.value + '</div>';
					}
				}
			},
			{ header: '구분', name: 'orderKndCode', align: 'center', width: 50,
				formatter: function(item) {
					if(item.value == 'GNR') {
						return '<span class="badge badge-success">일반</span>';
					}else if(item.value == 'SBS') {
						return '<span class="badge badge-primary">구독</span>';
					}else if(item.value == 'CPN') {
						return '<span class="badge badge-info">쿠폰</span>';
					}
				}
			},
			{ header: '상품명/카테고리', name: 'goodsNm', align: 'center', width:300, 
				formatter: function(item) {
					return (item.value == '') ? item.value : '<a href="#none" onclick="openGoodsPop(\'' + item.row.goodsId + '\')">' + item.value + '</a><br />'
												+'<small class="text-secondary">' + item.row.goodsCtgryNm +'</small>';
				}
			},
			{ header: '결제주기', align: 'center', width: 70,
				formatter: function(item) {
					if (item.row.goodsKndCode == 'SBS') {
						if(item.row.sbscrptCycleSeCode == 'WEEK') {
							return item.row.sbscrptWeekCycle + ' 주';
						}else if(item.row.sbscrptCycleSeCode == 'MONTH') {
							return item.row.sbscrptMtCycle + ' 개월';
						}
					} else {
						return '';
					}
				}
			},
			{ header: '결제날짜(요일)', name: 'sbscrptDlvyDay', align: 'center', width: 110,
				formatter: function(item) {
				if (item.row.goodsKndCode == 'SBS') {
					if(item.row.sbscrptCycleSeCode == 'WEEK') {
						return '<strong>' + item.row.sbscrptDlvyWdNm + '</strong> 요일';
					}else if(item.row.sbscrptCycleSeCode == 'MONTH') {
						return item.row.sbscrptDlvyDay + ' 일';
					}
				} else {
					return '';
				}
			}
			},
			{ header: '모델명', name: 'modelNm', align: 'center', width: 100 },
			{ header: '판매자', name: 'cmpnyNm', align: 'center', width: 100 },
			{ header: '총결제금액', name: 'setleTotAmount', align: 'right', width: 80,
				formatter: function(item) {
					return item.value.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ',');
				}
			},
			{ header: '상품가격', name: 'goodsAmount', align: 'right', width: 80,
				formatter: function(item) {
					return item.value.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ',');
				}
			},
			{ header: '수량', name: 'orderCo', align: 'right', width: 60 },
			{ header: '옵션', name: 'orderItem', align: 'left', width: 90 },
			{ header: '할인가격', name: 'dscntAmount', align: 'right', width: 80,
				formatter: function(item) {
					return item.value.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ',');
				}
			},
			{ header: '배송비', name: 'dlvyAmount', align: 'right', width: 80,
				formatter: function(item) {
					return item.value.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ',');
				}
			},
			{ header: '사용포인트', name: 'setlePoint', align: 'right', width: 80,
				formatter: function(item) {
					return item.value.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ',');
				}
			},
			{ header: '카드결제금액', name: 'setleCardAmount', align: 'right', width: 80,
				formatter: function(item) {
					return item.value.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ',');
				}
			},
			{ header: '주문상태', name: 'orderSttusCodeNm', align: 'center', width: 100 ,
				formatter:function(item){
					if(item.row.orderKndCode == 'GNR' && item.value!='체험구독'){
						return '주문완료';
					}else if(item.row.orderKndCode == 'CPN'){
						return '발송완료';
					}else{
						return item.value;
					}
				}
			},
			{ header: '요청처리상태', name: 'reqTyCodeNm', align: 'center', width: 100	},
			{ header: '결제상태', name: 'setleSttusCodeNm', align: 'center', width: 100 },
			{ header: '배송상태', name: 'dlvySttusCodeNm', align: 'center', width: 100 , 
				formatter:function(item){
					if(item.row.orderKndCode == 'CPN' && item.row.dlvyZip == null){
						return '발송완료';
					}else{
						return item.value;
					}
				}
			},
			{ header: '송장번호', name: 'invcNo', align: 'center', width: 100 , formatter: function(item) {
				return '<a href="#none" onclick="initEditPop(\'' + item.row.orderDlvyNo + '\', \'update\', \'' + item.value + '\'); ">' + item.value + '</a>';
			}},
			{ header: '배송업체', name: 'hdryNm', align: 'center', width: 130 },
			{ header: '주문자', name: 'ordrrNm', align: 'center', width:120, 
				formatter: function(item) {
					return '<div>' + item.value + '</div><div>' + item.row.telno + '</div>';
				}
			},
			{ header: '상품 수령 장소', name: 'dlvyAdres', width:500,
				formatter: function(item) {
					return '(' + item.row.dlvyZip + ') ' + item.value + ' ' + item.row.dlvyAdresDetail;
				}
			},
			{ header: '배송메시지', name: 'dlvyMssage', width:200},
			{ header: '관리', name: 'manage', align: 'center', width:150,
				formatter: function(item) {
					var btn = '<a href="#none" onclick="cancelOrder(\'' + item.row.orderDlvyNo + '\',\'' + item.row.orderNo  + '\',\'' + item.row.orderGroupNo + '\');"><span class="btn btn-danger btn-sm">주문취소</span></a>';
					if(item.row.orderKndCode == 'SBS'){
						btn += '<a href="#none" onclick="stopSubscribe(\'' + item.row.orderDlvyNo + '\',\'' + item.row.orderNo  + '\',\'' + item.row.orderGroupNo + '\');"><span class="btn btn-warning btn-sm">구독해지</span></a>';
					}
					return btn;
				}
			}
		],
		columnOptions: {
			frozenCount: 1,
			frozenBorderWidth: 1,
			resizable:true
		},
		onGridUpdated: function(ev) {
			refreshDlvyStatus(grid);
		}
	});

	var gridPop;
	var gridCpPop;
	
	tui.Grid.applyTheme('striped');

	var pagination = new tui.Pagination(document.getElementById('data-grid-pagination'), {
		totalItems: 10,
		itemsPerPage: 10,
		visiblePages: 5,
		centerAlign: true
	});
	
	pagination.on('beforeMove', function(e) {
		getDataList(e.page);
	});

	// 데이터 목록
	getDataList = function(page) {
		var $form = $('#searchForm');
		var actionUrl = $form.attr('action');
		var method = $form.attr('method');
		if(isEmpty(page)) {
			page = $form.find('#pageIndex').val(page);
		}else if(page > 0) {
			$form.find('#pageIndex').val(page);
		}

		var paramKeyValue = $form.serialize();
		
		jsonResultAjax({
			url: actionUrl,
			method: method,
			data: paramKeyValue,
			callback: function(result) {
				if(result.success) {
					var paginationObj = result.data.paginationInfo;
					
					if(paginationObj.currentPageNo == 1) {
						pagination.setItemsPerPage(paginationObj.recordCountPerPage);
						pagination.reset(paginationObj.totalRecordCount);
					}

					$('#p-order-cnt').text(result.data.orderCnt.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ',') + '건');
					$('#p-order-sum').text(result.data.orderAmount.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ',') + '원');
					$('#p-cnt-1').text(paginationObj.totalRecordCount.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ',') + '건');
					$('#p-cnt-2').text(result.data.cntDlvy.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ',') + '건');
					$('#p-cnt-3').text(result.data.cntDlvy.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ',') + '건');
					$('#p-cnt-4').text(result.data.cntDlvy1.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ',') + '건');
					$('#p-cnt-5').text(result.data.cntDlvy2.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ',') + '건');
					$('#p-cnt-6').text(result.data.cntDlvy3.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ',') + '건');
					
					grid.resetData(result.data.list);
					
				}
			}
			
		});
	};
	
	function loadCategoryInfo(){
		var dataJson = {};
		$.ajax({
			url: CTX_ROOT + '/decms/shop/goods/actGoodsCtgryList.json',
			type: 'GET',
			data: dataJson,
			dataType: 'json',
			success: function(result) {
				categories = result.data.list;
				initCategorySelection();
			}
		});
	}
	
	function initCategorySelection(){
		var select1 = $('select[name=searchGoodsCtgryId1]');
		var select2 = $('select[name=searchGoodsCtgryId2]');
		var select3 = $('select[name=searchGoodsCtgryId3]');
		
		var optionAll = '<option value="">전체</option>';

		select1.empty();
		select1.append(optionAll);

		categories.forEach(function(element){
			if (element.upperGoodsCtgryId == 'GCTGRY_0000000000000'){
		  		var option = $('<option value="' + element.goodsCtgryId +'">' + element.goodsCtgryNm + '</option>');
	        	select1.append(option);
	      	}
		});
		select1.change( function() {
			select2.empty();
			select2.append(optionAll);
			
			var selected = select1.val();
			
			categories.forEach(function(element){
				if (element.upperGoodsCtgryId == selected){
			  		var option = $('<option value="' + element.goodsCtgryId +'">' + element.goodsCtgryNm + '</option>');
	            	select2.append(option);
	          	}
			});
		});
		select2.change( function() {
			select3.empty();
			select3.append(optionAll);
		
			var selected = select2.val();

			categories.forEach(function(element){
				if (element.upperGoodsCtgryId == selected){
			  		var option = $('<option value="' + element.goodsCtgryId +'">' + element.goodsCtgryNm + '</option>');
	            	select3.append(option);
	          	}
			});
		});
		
		$(document).on('click', 'select[name=searchGoodsCtgryId2]', function() {
			if ( select1.val() == '' ) {
				alert('대분류를 먼저 선택해주세요.');
				select1.focus();
			}
		});
		$(document).on('click', 'select[name=searchGoodsCtgryId3]', function() {
			if ( select1.val() == '' ) {
				alert('대분류를 먼저 선택해주세요.');
				select1.focus();
			} else if ( select2.val() == '' ) {
				alert('중분류를 먼저 선택해주세요.');
				select2.focus();
			}
		});
	} 
	
	openGoodsPop = function(goodsId) {
		var name = '';
	    var option = 'width = ' + window.innerWidth + ', height = ' + window.innerHeight + ', top = 0, left = 0, location = no';
		window.open('/shop/goods/goodsView.do?goodsId=' + goodsId, name, option);
	}

	initEditPop = function(orderDlvyNo, mode, invcNo){
		console.log(invcNo);
		$('#editOrderDlvy').modal('show');
		$('input[name="orderDlvyNo"]').val(orderDlvyNo);
		if (mode == 'update') {
			$('#mySmallModalLabel').text('송장번호 수정');
			$('input[name="invcNo"]').val(invcNo);
		}
	}
	
	initAlert = function(orderDlvyNo){
		var answer = confirm('배송준비중 상태로 변경하시겠습니까?');
		if (answer) {
			updateDlvyStatus(orderDlvyNo);
		}
	}
	
	updateDlvyStatus = function(orderDlvyNo){
		var dataJson = {
			'orderDlvyNo' : orderDlvyNo,
			'dlvySttusCode' : 'DLVY01'
		};
			
		$.ajax({
			url: CTX_ROOT + '/decms/shop/goods/modifyDlvyStatus.do',
			type: 'POST',
			data: dataJson,
			dataType: 'json',
			success: function(result) {
				alert(result.message);
				getDataList(1);
				location.reload();
			}
		});
	}
	
	checkInvcNo = function(input){
		var invcNo = $(input).val();
		var regexp = /[0-9]$/;
		
		if( !regexp.test(invcNo) ) {
			alert('숫자만 입력하실 수 있습니다.');
			$(input).val(invcNo);
		}
	}
	
	updateInvcNo = function(){
		var orderDlvyNo = $('input[name="orderDlvyNo"]').val();
		var invcNo = $('input[name="invcNo"]').val();	

		var dataJson = {
			'orderDlvyNo' : orderDlvyNo,
			'invcNo' : invcNo
		};
		
		console.log(dataJson);
			
		$.ajax({
			url: CTX_ROOT + '/decms/shop/goods/modifyInvcNo.do',
			type: 'POST',
			data: dataJson,
			dataType: 'json',
			success: function(result) {
				alert(result.message);
				$('#editOrderDlvy').modal('hide');
				getDataList(pageIndex);
			}
		});
	}

	showDlvyStatus =  function(hdryId, invcNo, errorMsg) {
		//console.log('==================', error);
		if (errorMsg) {
			//console.log(error);
			alert(errorMsg);
		} else {
			if (invcNo != 'null') {
				var width = 640;
				var height = 600;
				
				var left = (window.screen.width / 2) - (width / 2);
				var top = (window.screen.height / 2) - (height / 2);
		
				var name = '_blank';
			    var option = 'width = ' + width + ' , height = ' + height + ', top = ' + top + ', left = ' + left + ', location = no';
				window.open('https://tracker.delivery/#/' + hdryId + '/' + invcNo, name, option);		
			} else {
				alert('송장번호가 없어 배송조회가 불가능합니다.');
			}
	
		}
	
	}
	
	modalShow = function(title, modal) {
		var $modal = $(modal);
		$modal.find('.modal-body .modal-spinner').show();
		
		$modal.on('hidden.bs.modal', function(e) {
			$(this).find('.modal-body .modal-spinner').hide();
			$(this).find('#modal-body-large').html('');
		});
		
		$modal.find('#modal-title-large').text(title);
		$modal.modal('show');
	};
	
	initCouponPop = function(orderNo){
		var modal = $('#orderCouponModal');
		modalShow('주문쿠폰현황',modal);
		
		var html = '';
		html += '<p>주문번호 : ' + orderNo + '</p>';
		html += '<div id="data-grid-pop"></div>';
		html += '<div id="data-grid-pagination-pop" class="tui-pagination"></div>';
		
		$('#modal-body-large-coupon').html(html);
		
		gridCpPop = new tui.Grid({
			el: document.getElementById('data-grid-pop'),
			rowHeight: 'auto',
			scrollX: true,
			scrollY: true,
			columns: [
				{ header: '쿠폰번호', name: 'couponNo', align: 'center', width: 500},
				],				
			onGridMounted: function(ev) {
				getOrderCouponList(orderNo);
			},
			columnOptions: {
		        resizable: true
		      }
		});
		
		modal.on('shown.bs.modal',function(e) {
			gridCpPop.refreshLayout();
		});
	
	}
	//쿠폰 리스트
	getOrderCouponList = function(orderNo){
		var $form = $('#searchForm');
	
		var dataJson = {
			//"pageIndex" : $form.find('#pageIndex').val(),
			"orderNo" : orderNo
		};
			
		jsonResultAjax({
			url: CTX_ROOT + '/decms/shop/goods/getOrderCoupon.json',
			type: 'POST',
			data: dataJson,
			//dataType: 'json',
			callback: function(result) {
				gridCpPop.resetData(result.data.list);
			}
		});
	
	}
	
	initDlvyPop = function(orderNo) {
		var modal = $('#orderDlvyModal');
		modalShow('주문별 구독현황', modal);
		
		var html = '';
		html += '<p>주문번호 : ' + orderNo + '</p>';
		html += '<div id="data-grid-pop"></div>';
		html += '<div id="data-grid-pagination-pop" class="tui-pagination"></div>';
		
		$('#modal-body-large').html(html);
			
		gridPop = new tui.Grid({
			el: document.getElementById('data-grid-pop'),
			rowHeight: 'auto',
			scrollX: true,
			scrollY: true,
			columns: [
				{ header: '회차', name: 'orderOdr', align: 'center', width: 30 },
				{ header: '결제일', name: 'setlePnttm', align: 'center', width: 130,
					formatter: function(item) {
						if (item.row.setleSttusCode == 'R') {
							return moment(item.row.setlePnttm).format('YYYY-MM-DD') + ' (예정)';
						} else {
							return moment(item.row.setlePnttm).format('YYYY-MM-DD HH:mm:ss');
						}
					}
				},
				{ header: '총결제금액', name: 'setleTotAmount', align: 'right', width: 80,
					formatter: function(item) {
						return item.value.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ',');
					}
				},
				{ header: '상품가격', name: 'goodsAmount', align: 'right', width: 80,
					formatter: function(item) {
						return item.value.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ',');
					}
				},
				{ header: '배송비', name: 'dlvyAmount', align: 'right', width: 80,
					formatter: function(item) {
						return item.value.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ',');
					}
				},
				{ header: '사용포인트', name: 'setlePoint', align: 'right', width: 80,
					formatter: function(item) {
						return item.value.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ',');
					}
				},
				{ header: '카드결제금액', name: 'setleCardAmount', align: 'right', width: 80,
					formatter: function(item) {
						return item.value.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ',');
					}
				},
				{ header: '상품명', name: 'goodsNm', align: 'center', width: 100 },
				{ header: '옵션', name: 'orderInfo', align: 'center', width: 150 },
				{ header: '수량', name: 'orderCo', align: 'center', width: 40 },
				{ header: '상품 수령 장소', name: 'dlvyAdres', width:500,
					formatter: function(item) {
						return '(' + item.row.dlvyZip + ') ' + item.value + ' ' + item.row.dlvyAdresDetail;
					}
				},
				{ header: '배송주기', align: 'center', width: 60,
					formatter: function(item) {
						if(item.row.sbscrptCycleSeCode == 'WEEK') {
							return item.row.sbscrptWeekCycle + ' 주';
						}else if(item.row.sbscrptCycleSeCode == 'MONTH') {
							return item.row.sbscrptMtCycle + ' 개월';
						}
					}
				},
				{ header: '배송일', name: 'sbscrptDlvyDay', align: 'center', width: 60,
					formatter: function(item) {
						if(item.row.sbscrptCycleSeCode == 'WEEK') {
							return '<strong>' + item.row.sbscrptDlvyWdNm + '</strong> 요일';
						}else if(item.row.sbscrptCycleSeCode == 'MONTH') {
							return item.row.sbscrptDlvyDay + ' 일';
						}
					}
				},
				{ header: '배송현황',	name: 'dlvySttusCodeNm', align: 'center', width: 70 }],				
			onGridMounted: function(ev) {
				getOrderDlvyHist(orderNo);
			},
			onGridUpdated: function(ev) {
				getOrderOptions(gridPop);
			},
			columnOptions: {
		        resizable: true
		      }
		});
		
		modal.on('shown.bs.modal',function(e) {
			gridPop.refreshLayout();
		});
	
	}
	
	getOrderDlvyHist = function(orderNo){
		var $form = $('#searchForm');
	
		var dataJson = {
			//"pageIndex" : $form.find('#pageIndex').val(),
			"orderNo" : orderNo
		};
			
		jsonResultAjax({
			url: CTX_ROOT + '/decms/shop/goods/getOrderDlvyHist.json',
			type: 'POST',
			data: dataJson,
			//dataType: 'json',
			callback: function(result) {
				gridPop.resetData(result.data.list);
			}
		});
	
	}
	
	getOrderOptions = function(grid) {
		var gridData = grid.getData();

		for (var i=0; i<grid.getRowCount(); i++) {
			var dataJson = {
				"orderNo" : gridData[i].orderNo
			};
			var options = '';
		
			$.ajax({
				url: CTX_ROOT + '/decms/shop/goods/getOrderItems.json',
				type: 'POST',
				dataType: 'json',
				data: dataJson,
				async: false,
				success: function(result) {
					//console.log(result.data.list);
					for (var j=0; j<result.data.list.length; j++) {
						options += '<div>' + result.data.list[j].gitemNm + '</div>';
					}
					
					grid.setValue(i, 'option', options, false);
				},
				complete: function() {
						
				}
			});
		}
	}
	
	refreshDlvyStatus = function(grid) {
		var dlvy_state = '';
	
		for (var i=0; i<grid.getRowCount(); i++) {
			var apiId = grid.getData()[i].apiId;
			var invcNo = grid.getData()[i].invcNo;
			var dlvySttusCode = grid.getData()[i].dlvySttusCode;
			var dlvySttusCodeNm = grid.getData()[i].dlvySttusCodeNm;
			var orderDlvyNo = grid.getData()[i].orderDlvyNo;
			var errormsg = null;
			
			var dataJson = {
				't_key' : 'skpVJme06E8exdxEaRpgmw',
				't_code' : apiId,
				't_invoice' : invcNo
			};
		
			$.ajax({
				url: 'https://info.sweettracker.co.kr/api/v1/trackingInfo',
				data: dataJson,
				type: 'GET',
				async: false,
				cache: false,
				dataType: 'json',
				success: function(data){
					//console.log(data.trackingDetails);
					
					if (data.trackingDetails) {
						var lastStatus = data.trackingDetails[data.trackingDetails.length-1];	
					}
					if ((lastStatus == '배송완료' || data.complete) && dlvySttusCode !='DLVY03') {
						//console.log('배송정보 DB update');
						//alert('배송 완료');
						dlvy_state = '배송완료';
						updateDlvyResult(orderDlvyNo);
					} else if (!data.status && data.msg) {
						//console.log('data.status:', data.status);
						//console.log('data.msg:', data.msg);
						errormsg = data.msg;
						//alert(data.msg);
					}
					
					if(dlvySttusCode == 'DLVY03') {
						dlvy_state = '<a href="#none" onclick="showDlvyPopup(\'' + apiId + '\',\'' + invcNo + '\', null); ">배송완료</a>';
					} else if (dlvySttusCode == 'DLVY00') {
						dlvy_state = '<a href="#none" onclick="initAlert(\'' + orderDlvyNo + '\'); ">' + dlvySttusCodeNm + '</a>'
					} else if (dlvySttusCode == 'DLVY01') {
						dlvy_state = '<a href="#none" onclick="initEditPop(\'' + orderDlvyNo + '\', \'add\', \'\'); ">배송준비중</a>';
					} else if (dlvySttusCode == 'DLVY02') {
						dlvy_state = '<a href="#none" onclick="showDlvyPopup(\'' + apiId + '\',\'' + invcNo + '\', \'' + errormsg + '\'); ">배송중</a>';
					}
				},
				error: function (request, status, error){
					//console.log(request, status, error);
					var errormsg = '시스템 장애 발생. 관리자에게 문의하세요.';
			    	if(dlvySttusCode == 'DLVY03') {
						dlvy_state = '<a href="#none" onclick="showDlvyPopup(\'' + apiId + '\',\'' + invcNo + '\', null); ">배송완료</a>';
					} else if (dlvySttusCode == 'DLVY00') {
						dlvy_state = '<a href="#none" onclick="initAlert(\'' + orderDlvyNo + '\'); ">' + dlvySttusCodeNm + '</a>'
					} else if (dlvySttusCode == 'DLVY01') {
						dlvy_state = '<a href="#none" onclick="initEditPop(\'' + orderDlvyNo + '\', \'add\', \'\'); ">배송준비중</a>';
					} else if (dlvySttusCode == 'DLVY02') {
						dlvy_state = '<a href="#none" onclick="showDlvyPopup(\'' + apiId + '\',\'' + invcNo + '\', \'' + errormsg + '\'); ">배송중</a>';
					}
				},
			   	complete: function() {
			   		grid.setValue(i, 'dlvySttusCodeNm', dlvy_state, false);
			   	}
			});
		} // for

	} // refreshDlvyStatus

	showDlvyPopup = function(t_code, t_invoice, error) {		
		var t_key='yWaX4OwVwrgofOXkfUP4eQ';
		var _width = 500;
		var _height = 500;
		var _left = Math.ceil(( window.screen.width - _width )/2);
		var _top = Math.ceil(( window.screen.width - _height )/2) - _height; 
		var win = window.open('http://info.sweettracker.co.kr/tracking/3?t_key=' + t_key + '&t_code=' + t_code + '&t_invoice=' + t_invoice,'dlvyviewer','width=' + _width + ',height=' + _height + ', left=' + _left + ', top=' + _top);
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
				console.log(result.message);
				$('#editOrderDlvy').modal('hide');
				//location.reload(true);
			}
		});
	}
	
	cancelOrder = function(orderDlvyNo, orderNo, orderGroupNo) {
		var answer = confirm('해당 회차의 결제 금액이 환불처리되며 구독이 해지됩니다. 주문을 취소하시겠습니까?');
		if (answer) {
			if (answer) {
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
						alert(result.message);
						getDataList(pageIndex);
					}
				});
			}
		}
	}

	stopSubscribe = function(orderDlvyNo, orderNo, orderGroupNo) {
		var answer = confirm('구독이 해지됩니다. 주문을 취소하시겠습니까?');
			
		if (answer) {
			var dataJson = {
				'orderDlvyNo' : orderDlvyNo,
				'reqTyCode' : 'T02',
				'orderReqSttusCode' : 'T',
				'searchOrderGroupNo' : orderGroupNo,
				'orderNo' : orderNo
			};
				
			$.ajax({
				url: '/shop/goods/stopSubscribeConfirm.do',
				type: 'POST',
				data: dataJson,
				dataType: 'json',
				success: function(result) {
					alert(result.message);
					getDataList(pageIndex);
				}
			});
		}
	}
	
	/** 이벤트 처리 */
	$(document).ready(function() {
		loadCategoryInfo();
		getDataList(1);
	});
	
	// 검색 submit
	$(document).on('submit', '#searchForm', function(e) {
		e.preventDefault();
		getDataList(1);
	});
	
	var beforeAWeek = new Date();
	beforeAWeek.setDate(beforeAWeek.getDate() - 7);
	
	$('#datepicker-searchBgnde').datetimepicker({
		locale: 'ko',
		format: 'YYYY-MM-DD',
		//defaultDate : new Date((new Date()).getFullYear(), (new Date()).getMonth(), 1)
		defaultDate : beforeAWeek
	});

	$('#datepicker-searchEndde').datetimepicker({
		locale: 'ko',
		format: 'YYYY-MM-DD',
		defaultDate : new Date()
	});
	
	$(document).on('click', '#editOrderDlvyBtn', function() {
		var orderDlvyNo = $('input[name="orderDlvyNo"]').val();
		var invcNo = $('input[name="invcNo"]').val();
		if ($('#withoutInvcNo').is(":checked")) {
			updateInvcNo();
			updateDlvyResult(orderDlvyNo);
		} else {
			updateInvcNo();
		}
	});
	
	//엑셀다운로드
	$(document).on('click', '.btnExcelDownload', function(e) {
		e.preventDefault();
		var actionUrl = $(this).attr('href');
		var formData = $('#searchForm').serialize();
		location.href = actionUrl + '?' + formData;
	});
	
})();
