(function() {
	
	const grid = new tui.Grid({
		el: document.getElementById('data-grid'),
		rowHeight: 'auto',
		header: {
			height: 110,
			complexColumns: [
				{header: '총결제금액', name: 'titleAmount', childNames:['setleTotAmount','setleCardAmount','setlePoint']},
				{header: '이벤트 할인', name: 'event', childNames:['rsrvmney','couponDscnt']},
				{header: '이지웰 수수료', name: 'titleEzwel', childNames:['ezwelFee','ezwelFeeAmount']},
				{header: '실정산금액', name: 'titleReal', childNames:['useRsrvmney','useCouponAmount','useEzwelFeeAmount','realTotAmount']},
				{header: '주문취소', name: 'titleCancel', childNames:['cancelPnttm','cancelAmount']},
			]
		},
		columns: [
			{ header: '정산상태', name: 'excclcSttusCode', width: 90, align: 'center',
				formatter: function(item) {
					if(item.value == 'CPE01') {
						return '정산대기';
					}else if(item.value == 'CPE02') {
						return '정산보류';
					}else if(item.value == 'CPE03') {
						return '정산완료';
					}
				}
			},
			{ header: '주문번호', name: 'orderNo', width: 150, align: 'center'},
			{ header: '이지웰 주문번호', name: 'orderNum', width: 90, align: 'center'},
			{ header: '주문일', name: 'setlePnttm', align: 'center', width: 100,
				formatter: function(item) {
					return moment(item.value).format('YYYY-MM-DD <br/> HH:mm:ss');
				}
			},
			{ header: '주문자명', name: 'ordrrNm', width: 90, align: 'center'},
			{ header: '판매처', name: 'cmpnyNm', align: 'center', width: 100 },
			{ header: '과세 /면세', name: 'taxtSeCode', align: 'center', width: 30 ,
				formatter: function(item) {
					return (item.value == 'TA01'?'<span class="text-primary">과세</span>':'<span class="text-danger">면세</span>');
				}
			},
			{ header: '상품명/카테고리', name: 'goodsNm', align: 'center',  width: 200,
				formatter: function(item) {
					return (item.value == '') ? item.value : '<a href="#none" onclick="openGoodsPop(\'' + item.row.goodsId + '\')">' + item.value + '</a><br />'
												+'<small class="text-secondary">' + item.row.goodsCtgryNm +'</small>';
				}
			},
			{ header: '수량', name: 'orderCo', width: 60, align: 'right'},
			{ header: '배송비', name: 'dlvyAmount', width: 70, align: 'right',
				formatter: function(item) { return modooNumberFormat(item.value); }
			},
			{ header: '결제금액', name: 'setleTotAmount', align: 'right', width: 70,
				formatter: function(item) { return modooNumberFormat(item.value); }
			},
			{ header: '카드결제금액', name: 'setleCardAmount', align: 'right', width: 70,
				formatter: function(item) { return modooNumberFormat(item.value); }
			},
			{ header: 'Ez 포인트', name: 'setlePoint', align: 'right', width: 70,
				formatter: function(item) { return modooNumberFormat(item.value); }
			},
			{ header: '적립금', name: 'rsrvmney', align: 'right', width: 60,
				formatter: function(item) { return modooNumberFormat(item.value); }
			},
			{ header: '쿠폰할인', name: 'couponDscnt', align: 'right', width: 60 },
			{ header: '수수료율', name: 'ezwelFee', align: 'right', width: 60,
				formatter: function(item) { return modooNumberFormat(item.value) + '%'; }
			},
			{ header: '수수료 금액', name: 'ezwelFeeAmount', align: 'right', width: 60,
				formatter: function(item) { return modooNumberFormat(item.value); }
			},
			{ header: '적립금', name: 'useRsrvmney', align: 'right', width: 60,
				formatter: function(item) { return modooNumberFormat(item.value); }
			},
			{ header: '쿠폰', name: 'useCouponAmount', align: 'right', width: 60,
				formatter: function(item) { return modooNumberFormat(item.value); }
			},
			{ header: 'Ez 포인트 사용금액', name: 'useEzwelFeeAmount', align: 'right', width: 60,
				formatter: function(item) { return modooNumberFormat(item.value); }
			},
			{ header: '실정산금액', name: 'realTotAmount', align: 'right', width: 60,
				formatter: function(item) { return modooNumberFormat(item.value); }
			},
			{ header: '주문취소일', name: 'cancelPnttm', align: 'center', width: 100,
				formatter: function(item) {
					return (item.value==null)?'-':moment(item.value).format('YYYY-MM-DD <br/> HH:mm:ss');
				}
			},
			{ header: '취소금액', name: 'cancelAmount', align: 'right', width: 60,
				formatter: function(item) { return (item.value==null)?'-':modooNumberFormat(item.value); }
			},
			{ header: '정산일정', name: 'excclcPnttm', align: 'center', width: 100,
				formatter: function(item) {
					return (item.value==null)?'-':moment(item.value).format('YYYY-MM-DD <br/> HH:mm:ss');
				}
			},
		],
		summary: { 
			height:40,
			position: 'bottom',
			columnContent: {
				orderNum: { template: function() { return '총 합게';} },
				orderCo: { template: function() { return '0'; } },
				dlvyAmount: { template: function() { return '0'; } },
				setleTotAmount: { template: function() { return '0'; } },
			}
		},
		columnOptions: {
			frozenCount: 3,
			frozenBorderWidth: 1,
			resizable:true
		}
	});
	
	tui.Grid.applyTheme('striped');
	
	var pagination = grid.getPagination();
	
	var pagination = new tui.Pagination(document.getElementById('data-grid-pagination'), {
		totalItems: 10,
		itemsPerPage: 10,
		visiblePages: 5,
		centerAlign: true
	});
	
	pagination.on('beforeMove', function(e) {
		getDataList(e.page);
	});
	
	function setTotal(sumObj) {
		grid.setSummaryColumnContent('orderCo', '<span>' + sumObj.orderCo + '<span>');
		grid.setSummaryColumnContent('dlvyAmount', '<span>' + modooNumberFormat(sumObj.dlvyAmount) + '<span>');
		grid.setSummaryColumnContent('setleTotAmount', '<span>' + modooNumberFormat(sumObj.setleTotAmount) + '<span>');
		
		grid.setSummaryColumnContent('setleCardAmount', '<span>' + modooNumberFormat(sumObj.setleCardAmount) + '<span>');
		grid.setSummaryColumnContent('setlePoint', '<span>' + modooNumberFormat(sumObj.setlePoint) + '<span>');
		grid.setSummaryColumnContent('rsrvmney', '<span>' + modooNumberFormat(sumObj.rsrvmney) + '<span>');
		grid.setSummaryColumnContent('couponDscnt', '<span>' + modooNumberFormat(sumObj.couponDscnt) + '<span>');
		
		grid.setSummaryColumnContent('ezwelFeeAmount', '<span>' + modooNumberFormat(sumObj.ezwelFeeAmount) + '<span>');
		grid.setSummaryColumnContent('useRsrvmney', '<span>' + modooNumberFormat(sumObj.useRsrvmney) + '<span>');
		grid.setSummaryColumnContent('useCouponAmount', '<span>' + modooNumberFormat(sumObj.useCouponAmount) + '<span>');
		grid.setSummaryColumnContent('useEzwelFeeAmount', '<span>' + modooNumberFormat(sumObj.useEzwelFeeAmount) + '<span>');
		grid.setSummaryColumnContent('realTotAmount', '<span>' + modooNumberFormat(sumObj.realTotAmount) + '<span>');
		grid.setSummaryColumnContent('cancelAmount', '<span>' + modooNumberFormat(sumObj.cancelAmount) + '<span>');
	
		$('#tot-setleTotAmount').text(modooNumberFormat(sumObj.setleTotAmount));
		$('#tot-ezwelFeeAmount').text(modooNumberFormat(sumObj.ezwelFeeAmount));
		$('#tot-setlePoint').text(modooNumberFormat(sumObj.setlePoint));
		$('#tot-rsrvmney').text(modooNumberFormat(sumObj.rsrvmney));
		
		$('#tot-bil').text(modooNumberFormat(sumObj.realTotAmount));
	}
	
	// 데이터 목록
	function getDataList(page) {
		var $form = $('#searchForm');
		var actionUrl = $form.attr('action');
		if(isEmpty(page)) {
			page = $form.find('#pageIndex').val(page);
		}else if(page > 0) {
			$form.find('#pageIndex').val(page);
		}
		var formData = $form.serialize();
		
		jsonResultAjax({
			url: actionUrl,
			type: 'get',
			data: formData,
			callback: function(result) {
			
				if(result.success) {
					var paginationObj = result.data.paginationInfo;
					
					if(paginationObj.currentPageNo == 1) {
						pagination.setItemsPerPage(paginationObj.recordCountPerPage);
						pagination.reset(paginationObj.totalRecordCount);
					}
					grid.resetData(result.data.list);
					setTotal(result.data.total);
				}
			}
		});
	};
	
	$(document).ready(function() {
		getDataList(1);
	});
	
	// 검색 submit
	$(document).on('submit', '#searchForm', function(e) {
		e.preventDefault();
		
		getDataList(1);
	});
	
	//업체 자동완성
	if($('#searchCmpnyNm').length > 0) {
		var uiCmpnyList;
		$('#searchCmpnyNm').autocomplete({
			minLength: 1,
			source : function(request, response) {
				jsonResultAjax({
					url: CTX_ROOT + '/decms/shop/cmpny/cmpnyAllList.json',
					type: 'post',
					//dataType: 'json',
					data: {cmpnyNm : request.term},
					callback: function(result) {
						//if(result.message) {
						//	console.log(result.message);
						//}
						
						if(result.success) {
							response(
								$.map(result.data.list, function(item) {
									return { 
										label: item.cmpnyNm,
										value: item.cmpnyId+ '||' + item.mberId
									}
								})
							);
							
							uiCmpnyList = result.data.list;
						}
					}
				});
			},
			change: function( event, ui ) {
				$cmpnyNm = $('#searchCmpnyNm');
				if(cmpnyNm != $cmpnyNm.val()) {
					$cmpnyNm.removeClass('cmpny-complete');
				}
			},
			close: function( event, ui) {
				//console.log(cmpnyNm,$('#searchCmpnyNm').val() );
				$('#searchCmpnyNm').val('');
				$('#searchCmpnyId').val('');
				
			},
			focus: function(event, ui ) {
				return false;
			},
			select: function(event, ui ) {
				var info = ui.item.value.split('||');
				cmpnyNm = ui.item.label;
				cmpnyId = info[0];
				cmpnyMberId = info[1];
				
				$('#searchCmpnyNmResult').val(cmpnyNm);
				$('input[name="searchCmpnyId"]').val(cmpnyId);
				$('#cmpnyUserId').val(cmpnyMberId);
				
				getDataList(1);
				return false;
			}
		}).autocomplete( "instance" )._renderItem = function( ul, item ) {
			return $('<li>')
				.append('<div>' + item.label + '</div>')
				.appendTo(ul);
		};
		
		$(document).on('keydown', '#searchCmpnyNm', function(e) {
			var searchCmpnyNm = $(this).val();
			
			if(e.keyCode == 13) {
				uiCmpnyList.forEach(function(el) {
					if(searchCmpnyNm == el.cmpnyNm) {
						$('#searchCmpnyNm').autocomplete('close');
						
						$('#searchCmpnyNmResult').val(el.cmpnyNm);
						$('input[name="searchCmpnyId"]').val(el.cmpnyId);
						$('#cmpnyUserId').val(el.mberId);
						
						getDataList(1);
						return;
					}

				});
				uiCmpnyList = [];
				e.preventDefault();
			}
		});
	}
	
	//엑셀 다운로드
	$(document).on('click', '.btnExcelDownload', function(e) {
		e.preventDefault();
		var actionUrl = $(this).attr('href');
		var formData = $('#searchForm').serialize();
		location.href = actionUrl + '?' + formData;
	});
})();