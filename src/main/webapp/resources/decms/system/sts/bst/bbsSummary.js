(function() {
	
	const grid = new tui.Grid({
		el: document.getElementById('data-grid'),
		columns: [
			{ header: '날짜', name: 'occrrncDe', width:120, align: 'center',
				formatter: function(item) {
					return isEmpty(item.value) ? '' : moment(item.value).format('YYYY-MM-DD');
				}
			},
			{ header: '게시물수', name: 'creatCo', align: 'center', width:100, align: 'right' },
			{ header: '총 조회수', name: 'totRdcnt', align: 'center', width:100, align: 'right' },
			{ header: '평균 조회수', name: 'avrgRdcnt', align: 'center', width:100, align: 'right' },
			{ header: '첨부파일수', name: 'totAtchFileCo', align: 'center', width:110, align: 'right' },
			{ header: '첨부파일크기 (Byte)', name: 'totAtchFileSize', align: 'center', width:120, align: 'right',
				formatter: function(item) {
					return new Intl.NumberFormat().format(item.value);
				}
			},
		],
		summary:{
			height: 100,
			position: 'bottom',
			columnContent: {
				creatCo: {
					template: function(valueMap) {
						return `${valueMap.sum}`;
					}
				},
				totRdcnt: {
					template: function(valueMap) {
						return `${valueMap.sum}`;
					}
				},
				avrgRdcnt: {
					template: function(valueMap) {
						return `${valueMap.sum}`;
					}
				},
				totAtchFileCo: {
					template: function(valueMap) {
						return `${valueMap.sum}`;
					}
				},
				totAtchFileSize: {
					template: function(valueMap) {
						var fileSize = valueMap.sum;
						var fileUnit = '';
						if(fileSize > (1024*1024)) {
							fileSize = fileSize / (1024*1024);
							fileUnit = ' MB';
						}else if(fileSize > 1024) {
							fileSize = fileSize / 1024;
							fileUnit = ' KB';
						}

						valueMap.sum = Intl.NumberFormat('ko-KR',{maximumSignificantDigits: 2}).format(fileSize);
						return `${valueMap.sum}` + fileUnit;
					}
				},
			}
		},
		columnOptions: {
			frozenCount: 1,
			frozenBorderWidth: 1,
		}
	});
	
	tui.Grid.applyTheme('striped');
	
	//-------- chart
	/*
	var chart = document.getElementById('chart1');
	var chartData = {
			
	};
	var chartOptions = {
		chart: {
			width: 1160,
			height: 540,
			title: '24-hr Average Temperature'
		},
		yAxis: {
			title: 'Temperature (Celsius)',
		},
		xAxis: {
			title: 'Month',
			pointOnColumn: true,
			dateFormat: 'MMM',
			tickInterval: 'auto'
		},
		series: {
			showDot: false,
			zoomable: true
		},
		tooltip: {
			suffix: ' 건'
		}
	};
	var theme = {
		series: {
			colors: [
				'#83b14e', '#458a3f', '#295ba0', '#2a4175', '#289399',
				'#289399', '#617178', '#8a9a9a', '#516f7d', '#dddddd'
			]
		}
	};
	var chart = tui.chart.lineChart(chart, chartData, chartOptions);
	*/
	function drawChart(chartTitle, dataList) {
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
				title: '날짜',
				pointOnColumn: true,
				tickInterval: 'auto'
			},
			series: {
				showDot: false,
				zoomable: true
			},
			tooltip: {
				suffix: ' 건'
			}
		};

		var theme = {
			series: {
				colors: [ '#83b14e', '#458a3f', '#295ba0', '#2a4175', '#289399', '#289399', '#617178', '#8a9a9a' ]
			}
		};
		
		var categories = [];
		var itemList1 = [];
		var itemList2 = [];
		var itemList3 = [];
		var itemList4 = [];
		
		dataList.forEach(function(item) {
			categories.push(moment(item.occrrncDe).format('MM.DD'));
			itemList1.push(item.creatCo);
			itemList2.push(item.totRdcnt);
			itemList3.push(item.avrgRdcnt);
			itemList4.push(item.totAtchFileCo);
		});
		
		var series = [];
		series.push({'name':'게시물수','data':itemList1});
		series.push({'name':'총조회수','data':itemList2});
		series.push({'name':'평균조회수','data':itemList3});
		series.push({'name':'첨부파일수','data':itemList4});
		var chartData = { categories, series };

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

					drawChart('게시물통계',result.data.list);
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
	
	
	$('#datepicker-searchBgnde').datetimepicker({
		locale: 'ko',
		format: 'YYYY-MM-DD'
	});
	
	$('#datepicker-searchEndde').datetimepicker({
		locale: 'ko',
		format: 'YYYY-MM-DD'
	});
	
	//엑셀 다운로드 click 
	$(document).on('click', '#btnExcelDownload', function(e) {
		e.preventDefault();
		var actionUrl = $(this).attr('href');
		var $form = $($(this).data('form'));
		var formData = $form.serialize();
		
		location.href = actionUrl + "?" + formData;
	});

})();