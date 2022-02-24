(function() {

	const grid = new tui.Grid({
		el: document.getElementById('data-grid'),
		columns: [
			{ header: 'No', name: 'themaNo', width:100, align: 'center'},
			{ header: '테마 명', name: 'themaSj', width:300, align: 'center'},
			{ header: '제휴사', name: 'prtnrId', width:100, align: 'center'
				, formatter: function(item) {
					if (item.value == 'PRTNR_0000') {
						return 'B2C';
					} else if (item.value == 'PRTNR_0001') {
						return '이지웰';
					} else {
						return '모두노출';
					}
				}
			},
			{ header: '썸네일', name: 'themaThumbnail', width:100, align: 'center'
				, formatter: function(item) {
					return '<img src="' + item.value + '" style="height:100%; max-height:30px;">';
				}
			},
			{ header: '테마 노출 기간', name: 'themaBeginDt', width:200, align: 'center'
				, formatter: function(item) {
					return item.value + ' ~ ' + item.row.themaEndDt;
				}
			},
			{ header: '테마 설명', name: 'themaCn', align: 'center'},
			{ header: '테마 순서', name: 'themaSn', width:100, align: 'center'},
			{ header: '상태', name: 'endAt', width:100, align: 'center'
				, formatter: function(item) {
					if (item.value == 'Y') {
						return '종료';
					} else {
						return '진행중';
					}
			}},
			{ header: '등록일자', name: 'frstRegistPnttm', width:100, align: 'center'},
			{ header: '관리', name: 'manage', width:100, align: 'center'
				, formatter: function(item) {
					return 	'<a href="' + CTX_ROOT + '/decms/thema/themaForm.do?themaNo=' + item.row.themaNo + '" class="btn btn-success" title="수정">' 
						+ 		'<i class="fas fa-edit"></i></a>' 
						+ 	'<a href="#" onclick="deleteThema(' + item.row.themaNo +')" class="btn btn-danger" title="삭제">' 
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
	
	$(document).on('click', '#searchBtn', function() {
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
			'pageIndex' :  $('#pageIndex').val()
			, 'searchPrtnrId' : $('select[name="searchPrtnrId"]').val()
			, 'searchEndAt' : $('select[name="searchEndAt"]').val()
			, 'searchCondition' : $('select[name="searchCondition"]').val()
			, 'searchKeyword' : $('input[name="searchKeyword"]').val()
		};

		jsonResultAjax({
			url: CTX_ROOT + '/decms/thema/themaList.json',
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
	
	deleteThema = function(themaNo) {

		var answer = confirm('삭제하시겠습니까?');
		if (answer) {
			jsonResultAjax({
				url: CTX_ROOT + '/decms/thema/deleteGoodsThema.do',
				method: 'get',
				data: {'themaNo' : themaNo},
				callback: function(result) {
					if(result.success) {
						getDataList(1);
					}
				}
				
			});
		}
		
	}
})();