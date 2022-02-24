(function() {
	const grid = new tui.Grid({
		el: document.getElementById('data-grid'),
		columns: [
			{ header: '날짜', name: 'de', width:100, align: 'center'},
			{ header: '판매금액', name: 'setleStotAmount', align: 'center', width:120, align: 'right',
				formatter: function(item) { return modooNumberFormat(item.value); }
			},
			{ header: 'Ez 포인트 사용금액', name: 'setlePoint', align: 'center', width:120, align: 'right',
				formatter: function(item) { return modooNumberFormat(item.value); }
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
	function drawPointChart(chartTitle, dataList) {
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
				title: '날짜',
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
			categories.push(item.de);
			itemList1.push(item.setlePoint);
		});
		
		var series = [];
		series.push({'name':'포인트','data':itemList1});
		var chartData = {
				categories: categories,
				series: series
			};

		var chart = tui.chart.lineChart(chartContainer, chartData, chartOptions);
	
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

					drawPointChart('Ez 포인트 분석',result.data.list);
				}
			}
		});
	};
	
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
	
	//엑셀다운로드
	$(document).on('click', '#btnExcelDownload', function(e) {
		e.preventDefault();
		var actionUrl = $(this).attr('href');
		var $form = $('#searchForm');
		var formData = $form.serialize();
		location.href = actionUrl + "?" + formData;
	});
})();