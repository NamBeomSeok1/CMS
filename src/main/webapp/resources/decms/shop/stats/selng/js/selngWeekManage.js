(function() {
	
	function drawWeekOrderChart(chartTitle, dataList) {
		$('#chart1').html('');
		var chartContainer = document.getElementById('chart1');
		var chartWidth = chartContainer.getBoundingClientRect().width;
		
		var chartOptions = {
			chart: {
				width: chartWidth,
				height: 540,
				title: chartTitle
			},
			yAxis: {
				title: '건수'
			},
			xAxis: {
				title: '요일',
				pointOnColumn: true,
				tickInterval: 'auto'
			},
			series: {
				showDot: false,
				zoomable: true
			},
			tooltip: {
				suffix: ' 건'
			},
			legend: {
				align: 'top'
			}
		};
		
		var theme = {
			series: {
				colors: [ '#83b14e' ]
			}
		};
		
		var categories = [];
		var itemList1 = [];
		
		dataList.forEach(function(item) {
			categories.push(item.wn);
			itemList1.push(item.cnt);
		});
		
		var series = [];
		series.push({'name':'주문','data':itemList1});
		var chartData = {
				categories: categories,
				series: series
			};

		tui.chart.registerTheme('myTheme', theme);
		chartOptions.theme = 'myTheme';
		var chart = tui.chart.columnChart(chartContainer, chartData, chartOptions);
	}
	
	function drawWeekSelngChart(chartTitle, dataList) {
		$('#chart2').html('');
		var chartContainer = document.getElementById('chart2');
		var chartWidth = chartContainer.getBoundingClientRect().width;
		
		var chartOptions = {
			chart: {
				width: chartWidth,
				height: 540,
				title: chartTitle,
				format: '1,000'
			},
			yAxis: {
				title: '매출'
			},
			xAxis: {
				title: '요일',
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
				colors: [ '#458a3f' ]
			}
		};
		
		var categories = [];
		var itemList1 = [];
		
		dataList.forEach(function(item) {
			categories.push(item.wn);
			itemList1.push(item.setleStotAmount);
		});
		
		var series = [];
		series.push({'name':'매출','data':itemList1});
		var chartData = {
				categories: categories,
				series: series
			};

		tui.chart.registerTheme('myTheme', theme);
		chartOptions.theme = 'myTheme';
		var chart = tui.chart.columnChart(chartContainer, chartData, chartOptions);
	}
	
	function drawHourOrderChart(chartTitle, dataList) {
		$('#chart3').html('');
		var chartContainer = document.getElementById('chart3');
		var chartWidth = chartContainer.getBoundingClientRect().width;
		
		var chartOptions = {
			chart: {
				width: chartWidth,
				height: 540,
				title: chartTitle,
				format: '1,000'
			},
			yAxis: {
				title: '주문(건)'
			},
			xAxis: {
				title: '시간',
				pointOnColumn: true,
				tickInterval: 'auto'
			},
			series: {
				showDot: false,
				zoomable: true
			},
			tooltip: {
				suffix: ' 건'
			},
			legend: {
				align: 'top'
			}
		};
		
		var theme = {
			series: {
				colors: [ '#295ba0' ]
			}
		};
		
		var categories = [];
		var itemList1 = [];
		
		dataList.forEach(function(item) {
			categories.push(item.hrNm);
			itemList1.push(item.cnt);
		});
		
		var series = [];
		series.push({'name':'주문','data':itemList1});
		var chartData = {
				categories: categories,
				series: series
			};

		tui.chart.registerTheme('myTheme', theme);
		chartOptions.theme = 'myTheme';
		var chart = tui.chart.columnChart(chartContainer, chartData, chartOptions);
	}
	
	function drawHourSelngChart(chartTitle, dataList) {
		$('#chart4').html('');
		var chartContainer = document.getElementById('chart4');
		var chartWidth = chartContainer.getBoundingClientRect().width;
		
		var chartOptions = {
			chart: {
				width: chartWidth,
				height: 540,
				title: chartTitle,
				format: '1,000'
			},
			yAxis: {
				title: '매출'
			},
			xAxis: {
				title: '시간',
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
				colors: [ '#2a4175' ]
			}
		};
		
		var categories = [];
		var itemList1 = [];
		
		dataList.forEach(function(item) {
			categories.push(item.hrNm);
			itemList1.push(item.setleStotAmount);
		});
		
		var series = [];
		series.push({'name':'매출','data':itemList1});
		var chartData = {
				categories: categories,
				series: series
			};

		tui.chart.registerTheme('myTheme', theme);
		chartOptions.theme = 'myTheme';
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
					drawWeekOrderChart('요일별 주문건수',result.data.list);
					drawWeekSelngChart('요일별 매출', result.data.list);
					drawHourOrderChart('시간대별 주문건수', result.data.resultHourList);
					drawHourSelngChart('시간대별 매출', result.data.resultHourList);
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
})();