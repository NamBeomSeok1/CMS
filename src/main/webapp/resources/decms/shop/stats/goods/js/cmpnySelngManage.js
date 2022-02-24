(function() {
	
	//매출
	function drawCmpnySelngChart(chartTitle, dataList) {
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
				title: '업체명',
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
			categories.push(item.cmpnyNm);
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
					//grid.resetData(result.data.list);

					drawCmpnySelngChart('업체별 매출',result.data.list);
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
	
	//업체 자동완성
	if($('#searchCmpnyNm').length > 0) {
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
	}
	
	//입점사명 clear
	$(document).on('click', '#btnRemoveCmpnyInfo', function(e) {
		e.preventDefault();
		$('#searchCmpnyNmResult').val('');
		$('#searchCmpnyNm').val('');
		$('#searchCmpnyId').val('');
		getDataList(1);
	});
})();