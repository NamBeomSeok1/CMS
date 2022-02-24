(function() {

	var grid;
	var pagination;
	var pageIndex = 1;

	initGrid = function() {
		grid = new tui.Grid({
			el: document.getElementById('data-grid'),
			rowHeight: 'auto',
			//scrollX: true,
			//scrollY: true,
			columns: [
				{ header: '주문번호',	name: 'orderNo', align: 'center', width: 160 },
				{ header: '주문일시', name: 'orderPnttm', align: 'center', width: 150
				, formatter: function(item) {
						return moment(item.row.orderPnttm).format('YYYY-MM-DD HH:mm:ss');
					}
				},
				{ header: '취소일시',	name: 'cancelPnttm', align: 'center', width: 150
				, formatter: function(item) {
						if (item.value) {
							return moment(item.value).format('YYYY-MM-DD HH:mm:ss');
						} else {
							return '';
						}
					}
				},
				{ header: '취소금액(카드)',	name: 'cancelAmount', align: 'right', width: 100,
					formatter: function(item) {
						return item.value.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ',');
					}
				},
				{ header: '취소자',	name: 'cancelUsrId', align: 'center', width: 100 },
				{ header: '제휴사', name: 'prtnrNm', align: 'center', width: 70 ,
					formatter: function(item) {
						if(item.row.prtnrId == 'PRTNR_0000') {
							return '<div class="bg-warning text-white">' + item.value + '</div>';
						}else {
							return '<div class="bg-secondary text-white">' + item.value + '</div>';
						}
					}
				},
				{ header: '상품명/카테고리', name: 'goodsNm', align: 'center', width: 300,
					formatter: function(item) {
						return (item.value == '') ? item.value : '<a href="#none" onclick="openGoodsPop(\'' + item.row.goodsId + '\')">' + item.value + '</a><br /><small class="text-secondary">'+item.row.goodsCtgryNm+'</small>';
					}
				},
				{ header: '모델명', name: 'modelNm', align: 'center', width: 100 },
				{ header: '판매자', name: 'brandNm', align: 'center', width: 100 },
				{ header: '판매가', name: 'sleAmount', align: 'right', width: 100,
					formatter: function(item) {
						return item.value.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ',');
					}
				},
				{ header: '배송료', name: 'dlvyAmount', align: 'right', width: 100,
					formatter: function(item) {
						return item.value.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ',');
					}
				},
				{ header: '진행상태', name: 'reqTyCodeNm', align: 'center', width: 100},
				{ header: '처리상태',	name: 'reqTyCode', align: 'center', width: 150,
					formatter: function(item) {
						if (item.value.indexOf('1') > -1) {
							var html='';
							html += '<button type="button" class="btn btn-primary btn-sm" onclick="updateProcessStatus(\'' + item.row.orderDlvyNo + '\',\'' + item.row.orderNo + '\',\'' + item.row.orderGroupNo + '\',' + true + ');">승인</button>';
							html += '<button type="button" class="btn btn-danger btn-sm" onclick="updateProcessStatus(\'' + item.row.orderDlvyNo + '\',\'' + item.row.orderNo + '\',\'' + item.row.orderGroupNo + '\',' + false + ');">삭제</button>';
							return html;
						} else {
							return '완료';
						}
					}
				},
				{ header: '결제상태',	name: 'setleSttusCodeNm', align: 'center', width: 100 },
				{ header: '주문자', name: 'ordrrNm', align: 'center', 
					formatter: function(item) {
						return '<div>' + item.value + '</div><div>' + item.row.telno + '</div>';
					}
				},
				{ header: '상품 수령 장소', name: 'dlvyAdres', align: 'center', width: 500 }			
			],
			columnOptions: {
		        resizable: true
		      }
		});

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
			'searchListType' : 'CANCEL',
			'searchOrderNo' : searchOrderNo,
			'searchOrdrrNm' : searchOrdrrId,
			'searchGoodsNm' : searchGoodsNm,
			'searchGoodsId' : searchGoodsId
		};
		
		$.ajax({
			url: '/decms/shop/goods/getOrderReqList.json',
			type: 'POST',
			data: dataJson,
			cache:false,
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

	updateProcessStatus = function(orderDlvyNo, orderNo, orderGroupNo, flag) {
		var statusCode = '';
		var answer = false;
		
		console.log(orderDlvyNo, orderNo, orderGroupNo, flag);
		
		if (flag) {
			statusCode = 'C04';
			answer = confirm('해당 주문의 취소신청을 승인하시겠습니까?\n확인을 선택하시면 주문취소가 처리됩니다.');
		} else {
			statusCode = 'C03';
			answer = confirm('해당 주문의 취소신청을 삭제하시겠습니까?\n확인을 선택하시면 주문취소가 삭제됩니다.');
		}
			
		if (answer) {
			var dataJson = {
				'orderDlvyNo' : orderDlvyNo,
				'reqTyCode' : statusCode,
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
					getGridRowData(pageIndex);
				}
			});
		}
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

})();