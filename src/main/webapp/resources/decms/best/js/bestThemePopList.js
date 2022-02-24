(function() {

	const gridTheme = new tui.Grid({
		el: document.getElementById('data-theme-grid'),
		rowHeight: 'auto',
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
			{ header: '선택', name: 'select', width: 100, align: 'center',
				formatter: function(item) {
					return 	'<a href="#" class="btn btn-success btn-sm mr-1" onclick="addThemeBest(\'' + item.row.themaNo + '\');" >' 
						+ 		'<span class="oi oi-check"></span></a>';
				}
			}
		],
		columnOptions: {
			frozenCount: 1,
			frozenBorderWidth: 1,
			resizable: true
		}
	});
	
	var pagination = gridTheme.getPagination();
	
	var pagination = new tui.Pagination(document.getElementById('data-theme-grid-pagination'), {
		totalItems: 10,
		itemsPerPage: 10,
		visiblePages: 5,
		centerAlign: true
	});
	
	pagination.on('beforeMove', function(e) {
		getDataList(e.page);
	});

	$(document).on('shown.bs.modal', '#themeSearchModal', function(e) {
		getDataList(1);
		gridTheme.refreshLayout();
	});

	function getDataList(page) {
		var actionUrl = CTX_ROOT + '/decms/thema/themaList.json';
		var formData = {
			'pageIndex' : page
		};
		
		jsonResultAjax({
			url: actionUrl,
			type: 'get',
			data: formData,
			//dataType: 'json',
			callback: function(result) {
				if(result.success) {
					var paginationObj = result.data.paginationInfo;
					
					if(paginationObj.currentPageNo == 1) {
						pagination.setItemsPerPage(paginationObj.recordCountPerPage);
						pagination.reset(paginationObj.totalRecordCount);
					}
					gridTheme.resetData(result.data.list);
				}
			}
		});
	}

})();