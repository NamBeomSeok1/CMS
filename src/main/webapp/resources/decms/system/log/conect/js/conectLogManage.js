(function() {
	
	var B2CTotalCount = 0;
	var B2BTotalCount = 0;
	
	const B2Cgrid = new tui.Grid({
		el: document.getElementById('B2C-data-grid'),
		columns: [
			{ header: '일시', name: 'creatDt', width:120, align: 'center',
				formatter: function(item) {
					if(item.value == 'TOT') {
						return '합계';
					}else return isEmpty(item.value) ? '' : moment(item.value).format('YYYY-MM-DD');
				}
			},
			{ header: '카운트', name: 'sumCount', align: 'center', width:80 },
			{ header: '비율', name: '', align: 'center',
				formatter: function(item) {
					if(item.row.creatDt == 'TOT') {
						return '';
					}else {
						var ratio = Math.round((item.row.sumCount / B2CTotalCount) * 100 * 10) / 10;
						var progress =    '<div class="progress" style="margin-top:7px;">';
							progress += '<div class="progress-bar progress-bar-striped progress-bar-animated bg-success" role="progressbar"';
							progress += '		aria-valuenow="' + ratio + '"';
							progress += '		aria-valuemin="0" aria-valuemax="100"';
							progress += '		style="width:' + ratio + '%;"';
							progress += '</div>'
							progress += '<span>' + ratio + '%</span>';
							progress += '</div>';
						return progress;
					}
				}
			},
		],
		columnOptions: {
			frozenCount: 1,
			frozenBorderWidth: 2,
		}
	});
	
	const B2Bgrid = new tui.Grid({
		el: document.getElementById('B2B-data-grid'),
		columns: [
			{ header: '일시', name: 'creatDt', width:120, align: 'center',
				formatter: function(item) {
					if(item.value == 'TOT') {
						return '합계';
					}else return isEmpty(item.value) ? '' : moment(item.value).format('YYYY-MM-DD');
				}
			},
			{ header: '카운트', name: 'sumCount', align: 'center', width:80 },
			{ header: '비율', name: '', align: 'center',
				formatter: function(item) {
					if(item.row.creatDt == 'TOT') {
						return '';
					}else {
						var ratio = Math.round((item.row.sumCount / B2CTotalCount) * 100 * 10) / 10;
						var progress =    '<div class="progress" style="margin-top:7px;">';
						progress += '		<div class="progress-bar progress-bar-striped progress-bar-animated bg-success" role="progressbar"';
						progress += '		aria-valuenow="' + ratio + '"';
						progress += '		aria-valuemin="0" aria-valuemax="100"';
						progress += '		style="width:' + ratio + '%;"';
						progress += '</div>'
							progress += '<span>' + ratio + '%</span>';
						progress += '</div>';
						return progress;
					}
				}
			},
			],
			columnOptions: {
				frozenCount: 1,
				frozenBorderWidth: 2,
			}
	});
	
	tui.Grid.applyTheme('clean');
	
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
					console.log(result);
					B2CTotalCount = result.data.b2ctotallogcount;
					B2Cgrid.resetData(result.data.b2clist);
					B2CTotalCount = result.data.b2btotallogcount;
					B2Bgrid.resetData(result.data.b2blist);
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