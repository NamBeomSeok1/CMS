(function() {
	
	const grid = new tui.Grid({
		el: document.getElementById('data-grid'),
		columns: [
			{ header: '성별', name: 'sexdstn', width:120, align: 'center'},
			{ header: '주문건수', name: 'orderCnt', align: 'center', width:100, align: 'right',
				formatter: function(item) { return modooNumberFormat(item.value); }
			},
			{ header: '결제건수', name: 'setleCnt', align: 'center', width:100, align: 'right',
				formatter: function(item) { return modooNumberFormat(item.value); }
			},
			{ header: '총매출액', name: 'setleStotAmount', align: 'center', width:100, align: 'right',
				formatter: function(item) { return modooNumberFormat(item.value); }
			},
			{ header: '취소건수', name: 'cancelCnt', align: 'center', width:100, align: 'right',
				formatter: function(item) { return modooNumberFormat(item.value); }
			},
			{ header: '취소금액', name: 'setleCtotAmount', align: 'center', width:100, align: 'right',
				formatter: function(item) { return modooNumberFormat(item.value); }
			},
			{ header: '배송비', name: 'dlvyAmount', align: 'center', width:100, align: 'right',
				formatter: function(item) { return modooNumberFormat(item.value); }
			},
			{ header: '결제수수료', name: 'setleFeeTot', align: 'center', width:100, align: 'right',
				formatter: function(item) { return modooNumberFormat(item.value); }
			},
			{ header: 'Ez포인트', name: 'setlePoint', align: 'center', width:100, align: 'right',
				formatter: function(item) { return modooNumberFormat(item.value); }
			},
			{ header: '적립금', name: 'rsrvmney', align: 'center', width:100, align: 'right',
				formatter: function(item) { return modooNumberFormat(item.value); }
			},
			{ header: '쿠폰할인', name: 'couponDscnt', align: 'center', width:100, align: 'right',
				formatter: function(item) { return modooNumberFormat(item.value); }
			},
			{ header: '이벤트할인', name: 'eventDscnt', align: 'center', width:100, align: 'right',
				formatter: function(item) { return modooNumberFormat(item.value); }
			},
		],
		columnOptions: {
			frozenCount: 1,
			frozenBorderWidth: 1,
			resizable:true
		}
	});
	
	tui.Grid.applyTheme('striped');
	
	function drawSexdstnOrderChart(chartTitle, dataList) {
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
				title: '건수'
			},
			xAxis: {
				title: '성별',
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
				colors: [ '#e74a3b', '#4e73df', '#20c9a6' ]
			}
		};
		
		var categories = [];
		var itemList1 = [];
		var itemList2 = [];
		var itemList3 = [];
		
		dataList.forEach(function(item) {
			categories.push(item.sexdstn);
			itemList1.push(item.orderCnt);
			itemList2.push(item.setleCnt);
			itemList3.push(item.cancelCnt);
		});
		
		var series = [];
		series.push({'name':'주문','data':itemList1});
		series.push({'name':'결제','data':itemList2});
		series.push({'name':'취소','data':itemList3});
		var chartData = {
				categories: categories,
				series: series
			};

		tui.chart.registerTheme('myTheme', theme);
		chartOptions.theme = 'myTheme';
		var chart = tui.chart.columnChart(chartContainer, chartData, chartOptions);
	}
	
	//매출
	function drawSexdstnSelngChart(chartTitle, dataList) {
		$('#chart2').html('');
		if(dataList.length == 0) return;
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
				title: '건수'
			},
			xAxis: {
				title: '성별',
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
				colors: [ '#e74a3b', '#4e73df', '#20c9a6' ]
			}
		}
		
		var categories = [];
		var itemList1 = [];
		var itemList2 = [];
		var itemList3 = [];
		var itemList4 = [];
		var itemList5 = [];
		var itemList6 = [];
		var itemList7 = [];
		var itemList8 = [];
		var itemList9 = [];
		var itemList10 = [];
		var itemList11 = [];
		
		dataList.forEach(function(item) {
			categories.push(item.sexdstn);
			itemList1.push(item.orderCnt);
			itemList2.push(item.setleCnt);
			itemList3.push(item.setleStotAmount);
			itemList4.push(item.cancelCnt);
			itemList5.push(item.setleCtotAmount);
			itemList6.push(item.dlvyAmount);
			itemList7.push(item.setleFeeTot);
			itemList8.push(item.setlePoint);
			itemList9.push(item.rsrvmney);
			itemList10.push(item.couponDscnt);
			itemList11.push(item.eventDscnt);
		});
		
		var series = [];
		series.push({'name':'주문건수','data':itemList1});
		series.push({'name':'결제건수','data':itemList2});
		series.push({'name':'총매출액','data':itemList3});
		series.push({'name':'취소건수','data':itemList4});
		series.push({'name':'취소금액','data':itemList5});
		series.push({'name':'배송비','data':itemList6});
		series.push({'name':'결제수수료','data':itemList7});
		series.push({'name':'Ez포인트','data':itemList8});
		series.push({'name':'적립금','data':itemList9});
		series.push({'name':'쿠폰할인','data':itemList10});
		series.push({'name':'이벤트할인','data':itemList11});
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
					
					drawSexdstnOrderChart('성별 주문건수',result.data.list);
					drawSexdstnSelngChart('성별 매출',result.data.list);
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
})();