(function() {

	const gridEvent = new tui.Grid({
		el: document.getElementById('data-event-grid'),
		rowHeight: 'auto',
		columns: [
			{ header: 'No', name: 'eventNo', width:100, align: 'center'},
			{ header: '이벤트명', name: 'eventSj', width:300, align: 'center'},
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
			{ header: '썸네일', name: 'eventThumbnail', width:100, align: 'center'
				, formatter: function(item) {
					return '<img src="' + item.value + '" style="height:100%; max-height:30px;">';
				}
			},
			{ header: '이벤트 기간', name: 'eventBeginDt', width:200, align: 'center'
				, formatter: function(item) {
					return item.value + ' ~ ' + item.row.eventEndDt;
				}
			},
			{ header: '이벤트 설명', name: 'eventCn', align: 'center'},
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
					return 	'<a href="#" class="btn btn-success btn-sm mr-1" onclick="addEventBest(\'' + item.row.eventUrl + '\');" >' 
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
	
	var pagination = gridEvent.getPagination();
	
	var pagination = new tui.Pagination(document.getElementById('data-event-grid-pagination'), {
		totalItems: 10,
		itemsPerPage: 10,
		visiblePages: 5,
		centerAlign: true
	});
	
	pagination.on('beforeMove', function(e) {
		getDataList(e.page);
	});

	$(document).on('shown.bs.modal', '#eventSearchModal', function(e) {
		getDataList(1);
		gridEvent.refreshLayout();
	});

	function getDataList(page) {
		var actionUrl = CTX_ROOT + '/decms/event/eventList.json';
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
					gridEvent.resetData(result.data.list);
				}
			}
		});
	}

})();