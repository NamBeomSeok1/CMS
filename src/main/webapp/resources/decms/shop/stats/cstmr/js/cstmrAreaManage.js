(function() {

	const grid = new tui.Grid({
		el: document.getElementById('data-grid'),
		columns: [
			{ header: '배송지역', name: 'areaNm', width:200, align: 'center'},
			{ header: '결제완료 주문수', name: 'orderCnt', align: 'center', width:100, align: 'right',
				formatter: function(item) { return modooNumberFormat(item.value); }
			},
			{ header: '결제완료 상품수', name: 'goodsCnt', align: 'center', width:100, align: 'right',
				formatter: function(item) { return modooNumberFormat(item.value); }
			},
			{ header: '결제상품 판매금액', name: 'setleStotAmount', align: 'center', width:100, align: 'right',
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
	
	//주문수
	function drawOrderChart(chartTitle, dataList) {
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
				title: '주문수'
			},
			xAxis: {
				title: '상품명',
				pointOnColumn: true,
				tickInterval: 'auto'
			},
			series: {
				showLegend: true,
				showLabel: true,
				labelAlign:'center'
			},
			legend: {
				visible: false
			}
		};
		
		var theme = {
			series: {
				series: {
					colors: ['#83b14e', '#458a3f', '#295ba0', '#2a4175', '#289399', '#289399', '#617178', '#8a9a9a', '#516f7d', '#dddddd']
				},
				label: {
					color: '#fff',
					fontFamily: 'sans-serif'
				}
			}
		}
		
		var categories = ['지역별 주문수'];
		var series = [];
		
		dataList.forEach(function(item) {
			series.push({'name':item.areaNm,'data':item.orderCnt});
		});
		
		var chartData = {
				categories: categories,
				series: series
			};

		tui.chart.registerTheme('myTheme', theme);
		chartOptions.theme = 'myTheme';
		var chart = tui.chart.pieChart(chartContainer, chartData, chartOptions);
	
	}
	
	//매출
	function drawSetleChart(chartTitle, dataList) {
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
				title: '주문수'
			},
			xAxis: {
				title: '상품명',
				pointOnColumn: true,
				tickInterval: 'auto'
			},
			series: {
				radiusRange: ['70%', '100%'],
				showLegend: true,
				showLabel: true,
				labelAlign:'center'
			},
			legend: {
				visible: false
			}
		};
		
		var theme = {
			series: {
				series: {
					colors: ['#83b14e', '#458a3f', '#295ba0', '#2a4175', '#289399', '#289399', '#617178', '#8a9a9a', '#516f7d', '#dddddd']
				},
				label: {
					color: '#fff',
					fontFamily: 'sans-serif'
				}
			}
		}
		
		var categories = ['지역별 매출'];
		var series = [];
		
		dataList.forEach(function(item) {
			series.push({'name':item.areaNm,'data':item.setleStotAmount});
		});
		
		var chartData = {
				categories: categories,
				series: series
			};

		tui.chart.registerTheme('myTheme', theme);
		chartOptions.theme = 'myTheme';
		var chart = tui.chart.pieChart(chartContainer, chartData, chartOptions);
	
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

					drawOrderChart('지역별 주문수',result.data.list);
					drawSetleChart('지역별 매출', result.data.list);
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
	
	//엑셀다운로드
	$(document).on('click', '#btnExcelDownload', function(e) {
		e.preventDefault();
		var actionUrl = $(this).attr('href');
		var $form = $('#searchForm');
		var formData = $form.serialize();
		location.href = actionUrl + "?" + formData;
	});
	
})();