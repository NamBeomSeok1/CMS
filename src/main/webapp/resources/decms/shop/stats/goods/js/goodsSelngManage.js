(function() {
	
	const grid = new tui.Grid({
		el: document.getElementById('data-grid'),
		columns: [
			{ header: '상품명', name: 'goodsNm', width:200, align: 'center'},
			{ header: '공급가', name: 'goodsSplpc', align: 'center', width:100, align: 'right',
				formatter: function(item) { return modooNumberFormat(item.value); }
			},
			{ header: '판매가', name: 'goodsPc', align: 'center', width:100, align: 'right',
				formatter: function(item) { return modooNumberFormat(item.value); }
			},
			{ header: '총매출/판매수', name: 'setleStotAmount', align: 'center', width:100, align: 'right',
				formatter: function(item) { return modooNumberFormat(item.value) + '/' + item.row.orderCnt; }
			},
			{ header: '상품판매개시일', name: 'sleDe', align: 'center', width:100, align: 'center'},
			{ header: '판매순위', name: 'goodsId', align: 'center', width:100, align: 'center',
				formatter: function(item) { 
					return item.row.rowKey+1; 
				}
			}
		],
		columnOptions: {
			frozenCount: 1,
			frozenBorderWidth: 1,
			resizable:true
		}
	});
	
	tui.Grid.applyTheme('striped');
	
	//매출
	function drawGoodsSelngChart(chartTitle, dataList) {
		$('#chart1').html('');
		if(dataList.length == 0) return;
		var chartContainer = document.getElementById('chart1');
		var chartWidth = chartContainer.getBoundingClientRect().width;
		var chartOptions = {
			chart: {
				width: chartWidth,
				height: 540,
				title: chartTitle,
				format: '1,000'
			},
			yAxis: {
				title: '매출액'
			},
			xAxis: {
				title: '상품명',
				pointOnColumn: true,
				tickInterval: 'auto'
			},
			series: {
				showDot: false,
				zoomable: true
			},
			tooltip: {
				suffix: ' 원'
			},
			legend: {
				align: 'top'
			}
		};
		
		var theme = {
			series: {
				colors: ['#83b14e']
			}
		}
		
		var categories = [];
		var itemList1 = [];
		
		dataList.forEach(function(item) {
			categories.push(item.goodsNm);
			itemList1.push(item.setleStotAmount);
		});
		
		var series = [];
		series.push({'name':'매출액','data':itemList1});
		var chartData = {
				categories: categories,
				series: series
			};

		var chart = tui.chart.columnChart(chartContainer, chartData, chartOptions);
	
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

		$.ajax({
			url: actionUrl,
			type: 'get',
			data: formData,
			dataType: 'json',
			success: function(result) {
				if(result.message) {
					bootbox.alert({ title: '확인', message: result.message, size: 'small' }); 
				}
				
				if(result.success) {
					totalCount = result.data.totalLogCount;
					grid.resetData(result.data.list);

					drawGoodsSelngChart('상품별 매출',result.data.list);
				}
			}
		});
	}
	
	// 카테고리 목록
	function getSubCategoryList(upperCateId, dp) {
		jsonResultAjax({
			url: CTX_ROOT + '/decms/shop/goods/goodsCtgryList.json',
			type: 'post',
			dataType: 'json',
			data: {searchUpperGoodsCtgryId: upperCateId},
			callback: function(result) {
			
				if(result.success) {
					if(dp == '1') {
						$('[data-dp=2]').html('<option value="">선택</option>');
						result.data.list.forEach(function(item) {
							$('<option value="'+item.goodsCtgryId+'">' + item.goodsCtgryNm + '</option>').appendTo('[data-dp=2]');
						});
					}else if(dp == '2') {
						$('[data-dp=3]').html('<option value="">선택</option>');
						result.data.list.forEach(function(item) {
							$('<option value="'+item.goodsCtgryId+'">' + item.goodsCtgryNm + '</option>').appendTo('[data-dp=3]');
						});
					}
				}
			}
		});
	}
	
	$(document).ready(function() {
		getDataList(1);
	});
	
	$(document).on('submit', '#searchForm', function(e) {
		e.preventDefault();
		
		getDataList(1);
	});
	
	$('#datepicker-searchBgnde').datetimepicker({
		locale: 'ko',
		format: 'YYYY-MM-DD'
	});
	
	$('#datepicker-searchEndde').datetimepicker({
		locale: 'ko',
		format: 'YYYY-MM-DD'
	});
	
	// 검색 버튼 클릭
	$(document).on('click', '.btnDateSearch', function(e) {
		var bgnde = $(this).data('begin');
		var endde = $(this).data('end');
		
		$('#searchBgnde').val(bgnde);
		$('#searchEndde').val(endde);
		getDataList(1);
	});
	
	// 카테고리 선택 Change
	$(document).on('change', '.selectCategory', function(e) {
		var dp = $(this).data('dp');

		if(!isEmpty($(this).val())) {
			var upperCateId = $(this).val();
			if(dp == '1') {
				$('[data-dp=3]').html('<option value="">선택</option>');
				getSubCategoryList(upperCateId, dp);
			}else if(dp == '2') {
				getSubCategoryList(upperCateId, dp);
			}else if(dp == '3') {
				//getDataList(1);
			}
		}else {
			if(dp == '1') {
				$('[data-dp=3]').html('<option value="">선택</option>');
				$('[data-dp=2]').html('<option value="">선택</option>');
			}else if(dp == '2') {
				$('[data-dp=3]').html('<option value="">선택</option>');
			}
			
		}
	});
	
	//엑셀다운로드
	$(document).on('click', '#btnExcelDownload', function(e) {
		e.preventDefault();
		var actionUrl = $(this).attr('href');
		var $form = $('#searchForm');
		var formData = $form.serialize();
		location.href = actionUrl + "?" + formData;
	});
	
})();