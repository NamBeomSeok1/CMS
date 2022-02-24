(function() {
	
	const grid = new tui.Grid({
		el: document.getElementById('data-grid'),
		rowHeight: 'auto',
		header: {
			height: 110,
			complexColumns: [
				{header: '총결제금액', name: 'titleAmount', childNames:['setleTotAmount','setleCardAmount','setlePoint']},
				{header: '이벤트', name: 'event', childNames:['rsrvmney','couponDscnt','eventDscnt','eventTot']},
				{header: '판매수수료', name: 'setleFee', childNames:['goodsFeeRate','setleFeeTot','setleFeeSplpc','setleFeeVat']},
				{header: '업체정산금액', name: 'cmpnyExcclcTitle', childNames:['cmpnyExcclcAmount','cmpnyExcclcVat','cmpnyExcclcTot']},
				{header: '이지웰', name: 'ezwel', childNames:['orderNum','orderStatus']},
			]
		},
		columns: [
			{ header: '구분', name: 'prtnrSeCode', width: 50, align: 'center',
				formatter: function(item) {
					if(item.value == 'B2C') {
						return '<div class="bg-success text-white">' + item.value + '</div>';
					}else {
						return '<div class="bg-primary text-white">' + item.value + '</div>';
					}
				}
			},
			{ header: '주문번호', name: 'orderNo', width: 150, align: 'center'},
			{ header: '주문일', name: 'setlePnttm', align: 'center', width: 90,
				formatter: function(item) {
					return moment(item.row.setlePnttm).format('YYYY-MM-DD <br/> HH:mm:ss');
				}
			},
			{ header: '상품명/카테고리', name: 'goodsNm', align: 'center',  width: 200,
				formatter: function(item) {
					return (item.value == '') ? item.value : '<a href="#none" onclick="openGoodsPop(\'' + item.row.goodsId + '\')">' + item.value + '</a><br />'
												+'<small class="text-secondary">' + item.row.goodsCtgryNm +'</small>';
				}
			},
			{ header: '모델명', name: 'modelNm', align: 'center', width: 100 },
			{ header: '과세 /면세', name: 'taxtSeCode', align: 'center', width: 30 ,
				formatter: function(item) {
					return (item.value == 'TA01'?'<span class="text-primary">과세</span>':'<span class="text-danger">면세</span>');
				}
			},
			{ header: '판매처', name: 'cmpnyNm', align: 'center', width: 100 },
			{ header: '총 결제금액', name: 'setleTotAmount', align: 'right', width: 70, //총결제금액
				formatter: function(item) { return modooNumberFormat(item.value); }
			},
			{ header: '카드결제', name: 'setleCardAmount', align: 'right', width: 70,
				formatter: function(item) { return modooNumberFormat(item.value); }
			},
			{ header: 'Ez 포인트', name: 'setlePoint', align: 'right', width: 70,
				formatter: function(item) { return modooNumberFormat(item.value); }
			},
			{ header: '적립금', name: 'rsrvmney', align: 'right', width: 60,
				formatter: function(item) { return modooNumberFormat(item.value); }
			},
			{ header: '쿠폰할인', name: 'couponDscnt', align: 'right', width: 60 },
			{ header: '이벤트 할인', name: 'eventDscnt', align: 'right', width: 60 },
			{ header: '이벤트 합계', name: 'eventTot', align: 'right', width: 60 },
			{ header: '판매 수수료율(부가세포함)', name: 'goodsFeeRate', align: 'center', width: 60,
				formatter: function(item) { return item.value + '%'; }
			},
			{ header: '판매 수수료 합계', name: 'setleFeeTot', align: 'right', width: 60,
				formatter: function(item) { return modooNumberFormat(item.value); }
			},
			{ header: '판매 수수료 공급가', name: 'setleFeeSplpc', align: 'right', width: 60,
				formatter: function(item) { return modooNumberFormat(item.value); }
			},
			{ header: '판매 수수료 부가세', name: 'setleFeeVat', align: 'right', width: 60,
				formatter: function(item) { return modooNumberFormat(item.value); }
			},
			{ header: '업체 정산금액', name: 'cmpnyExcclcAmount', align: 'right', width: 80,
				formatter: function(item) { return modooNumberFormat(item.value); }
			},
			{ header: '정산금액부가세', name: 'cmpnyExcclcVat', align: 'right', width: 80,
				formatter: function(item) { return modooNumberFormat(item.value); }
			},
			{ header: '업체정산금액함계', name: 'cmpnyExcclcTot', align: 'right', width: 80,
				formatter: function(item) { return modooNumberFormat(item.value); }
			},
		
			{ header: '주문번호', name: 'orderNum', align: 'center', width: 90 },
			{ header: '상태/금액', name: 'orderStatus', align: 'center', width: 80,
				formatter: function(item) { 
					var orderTotal = (isEmpty(item.row.orderTotal)?'-':modooNumberFormat(item.row.orderTotal));
					return item.row.orderStatusNm + '<br/><small class="text-secondary">' + orderTotal + '<small>'; 
				}
			},
			{ header: '지급일정', name: 'excclcPrarnde', align: 'center', width: 70,
				formatter: function(item) { 
				var dateStr = moment(item.value,'YYYYMMDD').format('YYYY-MM-DD');
					if(item.row.excclcSttusCode == 'CPE01') { //정산대기
						return dateStr + '<br/><span class="badge badge-info">예정</span>';
					}else if(item.row.excclcSttusCode == 'CPE02') { //정산보류
						return dateStr + '<br/><span class="badge badge-warning">보류</span>';
					}else if(item.row.excclcSttusCode == 'CPE03') { //정산완료
						return dateStr + '<br/><span class="badge badge-primary">완료</span>';
					}else {
						return '-';
					}
				}
			},
		],
		columnOptions: {
			frozenCount: 7,
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
	
	// 데이터 목록
	function getDataList(page) {
		if($('#searchPrtnrId').val() == 'PRTNR_0000') {
			grid.hideColumn('ezwel');
		}else {
			grid.showColumn('ezwel');
		}
		
		var $form = $('#searchForm');
		var actionUrl = $form.attr('action');
		if(isEmpty(page) || page == 0) {
			$form.find('#pageIndex').val('1');
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
					
					var totSum = result.data.totSum;
					//console.log(result.data);
					if(!isEmpty(totSum)) {
						setTotSum(totSum);
					}
				}
			}
		});
	};
	
	function setTotSum(totSum) {
		$('#setleCardAmount').text(modooNumberFormat(totSum.setleCardAmount));
		$('#setlePoint').text(modooNumberFormat(totSum.setlePoint));
		$('.setleTotAmount').text(modooNumberFormat(totSum.setleTotAmount));
		$('#setleFeeSplpc').text(modooNumberFormat(totSum.setleFeeSplpc));
		$('#setleFeeVat').text(modooNumberFormat(totSum.setleFeeVat));
		$('.setleFeeTot').text(modooNumberFormat(totSum.setleFeeTot));
		$('#rsrvmney').text(modooNumberFormat(totSum.rsrvmney));
		$('#couponDscnt').text(modooNumberFormat(totSum.couponDscnt));
		$('#eventDscnt').text(modooNumberFormat(totSum.eventDscnt));
		$('#eventTot').text(modooNumberFormat(totSum.eventTot));
		$('#cmpnyExcclcAmount').text(modooNumberFormat(totSum.cmpnyExcclcAmount));
		$('#cmpnyExcclcVat').text(modooNumberFormat(totSum.cmpnyExcclcVat));
		$('.cmpnyExcclcTot').text(modooNumberFormat(totSum.cmpnyExcclcTot));
		
		$('#excclcStdrDe').text(totSum.excclcStdrDe);
		//$('#taxbilDe').text(totSum.taxbilDe);
	}
	
	$(document).ready(function() {
		getDataList(1);
	});
	
	// 검색 submit
	$(document).on('submit', '#searchForm', function(e) {
		e.preventDefault();
		getDataList(1);
	});
	

	//정산기간 선택
	$(document).on('change', '#searchExcclcSePd', function(e) {
		var pd = $(this).val();
		//var pdInfo = $(this).find('option:selected').data('info');
		//$('#taxbilInfo').val(pdInfo);
	});
	
	$(document).on('click', '#btnSearchCmpny', function(e) {
		e.preventDefault();
	});
	
	//엑셀 다운로드
	$(document).on('click', '.btnExcelDownload', function(e) {
		e.preventDefault();
		var actionUrl = $(this).attr('href');
		var formData = $('#searchForm').serialize();
		location.href = actionUrl + '?' + formData;
	});
})();