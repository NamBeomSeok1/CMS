(function() {

	var grid;
	var pagination;
	var pageIndex = 1;
	
	initGrid = function() {
		grid = new tui.Grid({
			el: document.getElementById('data-grid'),
			rowHeight: 'auto',
			//scrollX: false,
			//scrollY: false,
			columns: [
				{ header: '주문번호',	name: 'orderNo', align: 'center', width: 160 },
				{ header: '주문일', name: 'orderPnttm', align: 'center', width: 150
				, formatter: function(item) {
						return moment(item.row.orderPnttm).format('YYYY-MM-DD HH:mm:ss');
					}
				},
				{ header: '상품명/카테고리', name: 'goodsNm', align: 'center'
				, formatter: function(item) {
						return (item.value == '') ? item.value : '<a href="#none" onclick="openGoodsPop(\'' + item.row.goodsId + '\')">' + item.value + '</a><br /><small class="text-secondary">'+item.row.goodsCtgryNm+'</small>';
					}
				},
				{ header: '모델명', name: 'modelNm', align: 'center', width: 100 },
				{ header: '판매자', name: 'brandNm', align: 'center', width: 100 },
				{ header: '판매가', name: 'sleAmount', align: 'right', width: 100 ,
					formatter: function(item) {
						return item.value.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ',');
					}
				},
				{ header: '배송료', name: 'dlvyAmount', align: 'right', width: 100 ,
					formatter: function(item) {
						return item.value.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ',');
					}
				},
				{ header: '진행상태', name: 'reqTyCodeNm', align: 'center', width: 100},
				{ header: '교환사유', name: 'cancelReason', align: 'center', width: 100, 
					formatter: function(item) {
						return '<a href="#none" onclick="showExchangeReason(\'' + item.row.orderDlvyNo + '\', \'' + item.row.ordrrNm + '\',\'' + item.row.goodsNm + '\')">보기</a>';
					}
				},
				{ header: '처리상태',	name: 'reqTyCode', align: 'center', width: 150, 
					formatter: function(item) {
						if (item.value == 'E01') {
							var html='';
							html += '<button type="button" class="btn btn-primary btn-sm" onclick="updateProcessStatus(\'' + item.row.orderDlvyNo + '\',' + true + ');">승인</button>';
							html += '<button type="button" class="btn btn-danger btn-sm" onclick="updateProcessStatus(\'' + item.row.orderDlvyNo + '\',' + false + ');">삭제</button>';
							return html;
						} else if (item.value == 'E02') {
							return '승인';
						} else if (item.value == 'E04') {
							return '교환재발송중';
						} else if (item.value == 'E05') {
							return '완료';
						}
					}
				},
				{ header: '배송상태',	name: 'dlvySttusCodeNm', align: 'center', width: 100,
					formatter : function(item) {
						if (item.row.dlvySttusCode == 'DLVY00')
							return '<a href="#none" onclick="initAlert(\'' + item.row.orderDlvyNo + '\'); ">' + item.value + '</a>';
						else if (item.row.dlvySttusCode == 'DLVY01') {
							//return '<a href="#none" onclick="initEditPop(\'' + item.row.orderDlvyNo + '\'); ">' + item.value + '</a>';
							return item.value;
						} else if (item.row.dlvySttusCode == 'DLVY02') {
							return '<a href="#none" onclick="showDlvyStatus(\'' + item.row.hdryId + '\',\'' + item.row.invcNo + '\'); ">' + item.value + '</a>';
						} else if (item.row.dlvySttusCode == 'DLVY03') {
							return item.row.dlvySttusCodeNm;
						}
					}
				},
				{ header: '주문자', name: 'ordrrNm', align: 'center', width: 100,
					formatter: function(item) {
						return '<div>' + item.value + '</div><div>' + item.row.telno + '</div>';
					}
				},
				{ header: '상품 수령 장소', name: 'dlvyAdres', align: 'center', width: 500 }			
			],
			columnOptions: {
		        resizable: true
		      },
			onGridUpdated: function(ev) {
				refreshDlvySttus(grid);
			}
		});

		pagination = grid.getPagination();
		
		pagination = new tui.Pagination(document.getElementById('data-grid-pagination'), {
			totalItems: 10,
			itemsPerPage: 10,
			visiblePages: 5,
			centerAlign: true
		});
		
		pagination.on('beforeMove', function(e) {
			getGridRowData(e.page);
		});
		
		getGridRowData(pageIndex);
	} // initGrid

	getGridRowData = function(pageIndex) {
		var searchCondition = $('select[name="searchCondition"]').val();
		var searchKeyword = $('input[name="searchKeyword"]').val();
		var searchOrderNo, searchOrdrrId, searchGoodsNm, searchGoodsId;
		
		if (searchCondition == "1") {
			searchOrderNo = searchKeyword;
		} else if (searchCondition == "2") {
			searchOrdrrId = searchKeyword;
		} else if (searchCondition == "3") {
			searchGoodsNm = searchKeyword;
		} else if (searchCondition == "4") {
			searchGoodsId = searchKeyword;
		}
		
		var dataJson = {
			'pageIndex' : pageIndex,
			'searchListType' : 'EXCHANGE',
			'searchOrderNo' : searchOrderNo,
			'searchOrdrrNm' : searchOrdrrId,
			'searchGoodsNm' : searchGoodsNm,
			'searchGoodsId' : searchGoodsId
		};
		
		$.ajax({
			url: '/decms/shop/goods/getOrderReqList.json',
			type: 'POST',
			data: dataJson,
			dataType: 'json',
			success: function(result) {
				setDlvyStat(result);
			
				var paginationObj = result.data.paginationInfo;
				if (paginationObj.currentPageNo == 1) {
				
					pagination.setItemsPerPage(paginationObj.recordCountPerPage);
					pagination.reset(paginationObj.totalRecordCount);
				}
				grid.resetData(result.data.list);
	
			}
		});
	} // getGridRowData
	
	setDlvyStat = function(result) {
		var canceAll = result.data.cancel01 + result.data.cancel02 + result.data.cancel04;
		var cancel04 = result.data.cancel04;
		var exchange01 = result.data.exchange01;
		var exchange02 = result.data.exchange02;
		var exchange04 = result.data.exchange04;
		var exchange05 = result.data.exchange05;
		var recall01 = result.data.recall01;
		var recall04 = result.data.recall04;
					
		$('#p-order-c1').text(canceAll.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ',') + '건');
		$('#p-order-c4').text(cancel04.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ',') + '건');
		$('#p-order-e1').text(exchange01.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ',') + '건');
		$('#p-order-e2').text(exchange02.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ',') + '건');
		$('#p-order-e4').text(exchange04.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ',') + '건');
		$('#p-order-e5').text(exchange05.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ',') + '건');
		$('#p-order-r1').text(recall01.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ',') + '건');
		$('#p-order-r4').text(recall04.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ',') + '건');
	}

	updateProcessStatus = function(orderDlvyNo, flag) {
		var statusCode = '';
		var answer = false;
		
		if (flag) {
			statusCode = 'E02';
			answer = confirm('해당 주문의 교환신청을 승인하시겠습니까?\n확인을 선택하시면 교환승인이 처리됩니다.');
		} else {
			statusCode = 'E03';
			answer = confirm('해당 주문의 교환 신청을 삭제하시겠습니까?\n확인을 선택하시면 교환 신청 내역이 삭제됩니다.');
		}
		
		if (answer) {
			var dataJson = {
				'orderDlvyNo' : orderDlvyNo,
				'reqTyCode' : statusCode,
				'orderReqSttusCode' : 'E'
			};
			
			$.ajax({
				url: '/decms/shop/goods/modifyProcessStatus.do',
				type: 'POST',
				data: dataJson,
				dataType: 'json',
				success: function(result) {
					alert(result.message);
					getGridRowData(pageIndex);
				}
			});
		}
	}

	initEditPop = function(orderDlvyNo){
		$('#editOrderDlvy').modal('show');
		$('input[name="orderDlvyNo"]').val(orderDlvyNo);
	}
	
	checkInvcNo = function(input){
		var invcNo = $(input).val();
		var regexp = /[0-9]$/;
		if( !regexp.test(invcNo) ) {
			alert('숫자만 입력하실 수 있습니다.');
			$(input).val('');
		}
	}
	
	updateInvcNo = function(){
		var dataJson = {
			'orderDlvyNo' : $('input[name="orderDlvyNo"]').val(),
			'invcNo' : $('input[name="invcNo"]').val()
		};
			
		$.ajax({
			url: CTX_ROOT + '/decms/shop/goods/modifyInvcNo.do',
			type: 'POST',
			data: dataJson,
			dataType: 'json',
			success: function(result) {
				updateExchangeDlvyStatus($('input[name="orderDlvyNo"]').val());
			}
		});
	}

	showDlvyStatus =  function(hdryId, invcNo) {
		var width = 640;
		var height = 600;
		
		var left = (window.screen.width / 2) - (width / 2);
		var top = (window.screen.height / 2) - (height / 2);

		var name = '_blank';
	    var option = 'width = ' + width + ' , height = ' + height + ', top = ' + top + ', left = ' + left + ', location = no';
		window.open('https://tracker.delivery/#/' + hdryId + '/' + invcNo, name, option);
	}
	
	updateExchangeDlvyStatus = function(orderDlvyNo) {
		var dataJson = {
			'orderDlvyNo' : orderDlvyNo
		};
	
		$.ajax({
			url: CTX_ROOT + '/decms/shop/goods/modifyExchangeStatus.do',
			type: 'POST',
			data: dataJson,
			dataType: 'json',
			success: function(result) {
				alert(result.message);
				getGridRowData(1);
			}
		});
	}
	
	modalShow = function(title, modal) {
		var $modal = $(modal);
		$modal.find('.modal-body .modal-spinner').show();
		
		$modal.on('hidden.bs.modal', function(e) {
			$(this).find('.modal-body .modal-spinner').hide();
			$(this).find('#modal-body-action').html('');
		});
		
		$modal.find('#modal-title-action').text(title);
		$modal.modal('show');
	};
	
	showExchangeReason = function(orderDlvyNo, ordrrNm, goodsNm){
		var dataJson = {
			'searchOrderDlvyNo' : orderDlvyNo,
			'qaSeCode' : 'EX'
		};
		
		$.ajax({
			url: CTX_ROOT + '/decms/qainfo/getReasonCn.json',
			type: 'POST',
			data: dataJson,
			dataType: 'json',
			success: function(result) {
				if (result.data.qaInfo) {
					var modal = $('#orderActionReason');
					modalShow(ordrrNm + '님의 ' + goodsNm + '에 대한 교환 신청 사유', modal);
					var html = '';
					html += '<div style="height:100px; overflow: scroll;"><p style="padding: 10px;">' + result.data.qaInfo.qestnCn + '</p></div>';
					html += '<hr>';
					html += '<h6>첨부 이미지</h6>';
					var imgs = result.data.imgs;
					html += '<div style="height:500px; overflow: scroll; padding: 10px;">';
					
					for (var i=0; i<imgs.length; i++) {
						var imgSrc = '/fms/getImage.do?atchFileId=' + imgs[i].atchFileId + '&fileSn=' + imgs[i].fileSn;
						html += '<img style="padding-bottom: 10px; max-width: 100%;" src="' + imgSrc + '">';
					}
					
					html += '</div>';
					$('#modal-body-action').html(html);
				}
			}
		});
	}
	
	refreshDlvySttus = function(grid) {
		var dlvyState = '';
		
		for (var i=0; i<grid.getRowCount(); i++) {
			var apiId = grid.getData()[i].apiId;
			var invcNo = grid.getData()[i].invcNo;
			var dlvySttusCode = grid.getData()[i].dlvySttusCode;
			var dlvySttusCodeNm = grid.getData()[i].dlvySttusCodeNm;
			var orderDlvyNo = grid.getData()[i].orderDlvyNo;
		
			$.ajax({
				url: 'https://apis.tracker.delivery/carriers/' + apiId + '/tracks/' + invcNo,
				type: 'GET',
				dataType: 'json',
				async: false,
				success: function(result) {
					if (result.state.id == 'delivered' && dlvySttusCode != 'DLVY03') {
						dlvyState = '배송완료';
						updateDlvyResult(orderDlvyNo);
					}
					if(dlvySttusCode == 'DLVY03') {
						dlvyState = '배송완료';	
					} else if (dlvySttusCode == 'DLVY00') {
						dlvyState = '<a href="#none" onclick="initAlert(\'' + orderDlvyNo + '\'); ">' + dlvySttusCodeNm + '</a>'
					} else if (dlvySttusCode == 'DLVY01') {
						//dlvyState = '<a href="#none" onclick="initEditPop(\'' + orderDlvyNo + '\'); ">배송준비중</a>';
						dlvyState = dlvySttusCodeNm;
					} else if (dlvySttusCode == 'DLVY02') {
						dlvyState = '<a href="#none" onclick="showDlvyStatus(\'' + apiId + '\',\'' + invcNo + '\'); ">배송중</a>';
					}
				},
				error:function(request,status,error){
					console.log("code:"+request.status+"\n"+"message:"+request.responseText+"\n"+"error:"+error);
					if(dlvySttusCode == 'DLVY03') {
						dlvyState = '배송완료';
					} else if (dlvySttusCode == 'DLVY00') {
						dlvyState = '<a href="#none" onclick="initAlert(\'' + orderDlvyNo + '\'); ">' + dlvySttusCodeNm + '</a>'
					} else if (dlvySttusCode == 'DLVY01') {
						//dlvyState = '<a href="#none" onclick="initEditPop(\'' + orderDlvyNo + '\'); ">배송준비중</a>';
						dlvyState = dlvySttusCodeNm;
					} else if (dlvySttusCode == 'DLVY02') {
						dlvyState = '<a href="#none" onclick="showDlvyStatus(\'' + apiId + '\',\'' + invcNo + '\'); ">배송중</a>';
					}
				},
				complete: function() {
					grid.setValue(i, 'dlvySttusCodeNm', dlvyState, false);
				}
			});
		}
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
			}
		});
	}
	
	openGoodsPop = function(goodsId) {
		var name = '';
	    var option = 'width = ' + window.innerWidth + ', height = ' + window.innerHeight + ', top = 0, left = 0, location = no';
		window.open('/shop/goods/goodsView.do?goodsId=' + goodsId, name, option);
	}
	
	/** 이벤트 목록 */
	$(document).ready(function() {
		tui.Grid.applyTheme('clean');
		initGrid();
	});
	
	// 검색 submit
	$(document).on('click', '#searchOrderDlvyBtn', function(e) {
		e.preventDefault();
		getGridRowData(1);
	});
	
	$(document).on('click', '#editOrderDlvyBtn', function() {
		updateInvcNo();
	});
})();