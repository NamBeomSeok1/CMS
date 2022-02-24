(function() {
	
	const grid = new tui.Grid({
		el: document.getElementById('data-grid'),
		columns: [
			{ header: '요일', name: 'wdNm', width:100, align: 'center'},
			{ header: '접속 사용자수', name: 'loginCnt', align: 'center', width:120, align: 'right',
				formatter: function(item) { return modooNumberFormat(item.value); }
			},
			{ header: '결제완료 상품개수', name: 'goodsCnt', align: 'center', width:120, align: 'right',
				formatter: function(item) { return modooNumberFormat(item.value); }
			},
			{ header: '결제상품 판매금액', name: 'setleStotAmount', align: 'center', width:120, align: 'right',
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
	function drawWeekChart(chartTitle, dataList) {
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
			yAxis: [{
				title: '횟수',
				chartType: 'column'
			},{
				title: '금액',
				chartType: 'line'
			}],
			xAxis: {
				title: '상품명',
				pointOnColumn: true,
				tickInterval: 'auto'
			},
			series: {
				line: {
					showDot: true
				}
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
				column: {
					colors: ['#83b14e']
				},
				line: {
					colors: ['#333']
				}
			}
		}
		
		var categories = [];
		var itemList1 = [];
		var itemList2 = [];
		var itemList3 = [];
		var itemList4 = [];
		
		dataList.forEach(function(item) {
			categories.push(item.wdNm);
			itemList1.push(item.loginCnt);
			itemList2.push(item.sbscrbCnt);
			itemList3.push(item.goodsCnt);
			itemList4.push(item.setleStotAmount);
		});
		
		var series;
		var column = [];
		var line = [];
		column.push({'name':'로그인휫수','data':itemList1});
		column.push({'name':'신규가입자','data':itemList2});
		column.push({'name':'결제완료상품수','data':itemList3});
		line.push({'name':'결제완료판매금액','data':itemList4});
		series = {'column':column, 'line':line};
		var chartData = {
				categories: categories,
				series: series
			};

		var chart = tui.chart.comboChart(chartContainer, chartData, chartOptions);
	
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

					drawWeekChart('요일별 분석',result.data.list);
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