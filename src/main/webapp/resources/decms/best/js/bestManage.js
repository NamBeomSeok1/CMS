$(function() {
	
	const grid = new tui.Grid({
		el: document.getElementById('data-grid'),
		columns: [
			{ header: '노출순서', name: 'expsrOrdr', width:100, align: 'center'},
			{ header: '제휴사', name: 'prtnrId', width:100, align: 'center'
				, formatter: function(item) {
					if (item.value == 'PRTNR_0000') {
						return 'B2C';
					} else if (item.value == 'PRTNR_0001') {
						return '이지웰';
					} else {
						return '미노출';
					}
				}
			},
			{ header: '상태', name: 'actvtyAt', width:100, align: 'center'
				, formatter: function(item) {
					if (item.value == 'Y') {
						return '진행중';
					} else {
						return '종료';
					}
			}},
			{ header: '썸네일', name: 'bestThumbnail', width:100, align: 'center'
				, formatter: function(item) {
					return '<img src="' + item.value + '" style="height:100%; max-height:30px;">';
				}
			},
			{ header: '대표명칭', name: 'reprsntSj', width:170, align: 'center'},
			{ header: '대표문구', name: 'reprsntText', width:400, align: 'center'},
			{ header: '바로가기', name: 'bestUrl', width:100, align: 'center'
				, formatter: function(item) {
					if(!isEmpty(item.value)) {
						return '<a href="'+item.value+'" target="_blank" class="btn btn-outline-dark btn-sm"><i class="fas fa-link"></i></a>';
					}else {
						return '-';
					}
				}
			},
			{ header: '이벤트 기간', name: 'expsrBeginDe', width:160, align: 'center'
				, formatter: function(item) {
					return moment(item.value).format('YYYY-MM-DD') + ' ~ ' + moment(item.row.expsrEndDe).format('YYYY-MM-DD');
				}
			},
			{ header: '등록일자', name: 'frstRegistPnttm', width:100, align: 'center'
				, formatter: function(item) {
					return moment(item.value).format('YYYY-MM-DD');
				}
			},
			{ header: '관리', name: 'manage', width:100, align: 'center'
				, formatter: function(item) {
					return 	'<a href="' + CTX_ROOT + '/decms/best/bestForm.do?bestNo=' + item.row.bestNo + '" class="btn btn-success" title="수정">' 
						+ 		'<i class="fas fa-edit"></i></a>' 
						+ 	'<a href="#" onclick="deleteBest(' + item.row.bestNo +')" class="btn btn-danger" title="삭제">' 
						+ 		'<i class="fas fa-trash"></i></a>';
				}
			}
		],
		columnOptions: {
			frozenCount: 1,
			frozenBorderWidth: 1,
			resizable: true
		}
	});
	
	$(document).ready(function() {
		getDataList(1);
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

	getDataList = function(page) {
		if(isEmpty(page)) {
			page = $('#pageIndex').val();
		}else if(page > 0) {
			$('#pageIndex').val(page);
		}
		var paramKeyValue = {
			'pageIndex' :  page
			, 'searchPrtnrId' : $('#myTab > li > a.active').attr('id')
		};

		jsonResultAjax({
			url: CTX_ROOT + '/decms/best/bestList.json',
			method: 'get',
			data: paramKeyValue,
			callback: function(result) {
				if(result.success) {
					var paginationObj = result.data.paginationInfo;
					
					if(paginationObj.currentPageNo == 1) {
						pagination.setItemsPerPage(paginationObj.recordCountPerPage);
						pagination.reset(paginationObj.totalRecordCount);
					}
					
					grid.resetData(result.data.list);
					
				}
			}
			
		});
	};
	
	deleteBest = function(eventNo) {

		var answer = confirm('삭제하시겠습니까?');
		if (answer) {
			jsonResultAjax({
				url: CTX_ROOT + '/decms/best/deleteBest.do',
				method: 'get',
				data: {'bestNo' : bestNo},
				callback: function(result) {
					if(result.success) {
						getDataList(1);
					}
				}
				
			});
		}
		
	}
	
	$(document).on('click', '#myTab', function() {
		getDataList(1);
	});

	registBest = function() {
		location.href= CTX_ROOT + '/decms/best/bestForm.do?prtnrId=' + $('#myTab > li > a.active').attr('id');	
	}

})