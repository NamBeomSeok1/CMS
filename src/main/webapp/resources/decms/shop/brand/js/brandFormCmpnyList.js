(function() {
	
	const grid = new tui.Grid({
		el: document.getElementById('data-cmpny-grid'),
		rowHeight: 'auto',
		columns: [
			{ header: '로고', name: 'cmpnyLogoPath', width: 120, align: 'center',
				formatter: function(item) {
					if(!isEmpty(item.value)) {
						return '<img src="' + item.value + '" alt="' + item.row.cmpnyNm + '" style="height:30px;"/>';
					}else {
						return '-';
					}
				}
			},
			{ header: '업체명', name: 'cmpnyNm', width: 200, align: 'center'},
			{ header: '입점일', name: 'opnngDe', width:100, align: 'center',
				formatter: function(item) {
					return moment(item.value, 'YYYYMMDD').format('YYYY-MM-DD');
				}
			},
			{ header: '등록상태', name: 'opnngSttusCode', width: 80, align: 'center',
				formatter: function(item) {
					if(item.value == 'R') {
						return '등록신청';
					}else if(item.value == 'A') {
						return '처리 중';
					}else if(item.value == 'C') {
						return '등록완료';
					}
				}
			},
			{ header: '선택', name: '', width: 100, align: 'center',
				formatter: function(item) {
					return '<button type="button" class="btn btn-success btn-sm btnSelectCmpny" data-cmpny-id="' + item.row.cmpnyId + '" data-cmpny-nm="' + item.row.cmpnyNm 
						+ '" data-policy-cn="' + item.row.cmpnyDlvyPolicyCn + '">'
						+ '<i class="fas fa-check"></i></button>';
				}
			},
		],
		columnOptions: {
			frozenCount: 1,
			frozenBorderWidth: 1,
			resizable:true
		}
	});
	
	tui.Grid.applyTheme('striped');
	
	var pagination = grid.getPagination();
	
	var pagination = new tui.Pagination(document.getElementById('data-cmpny-grid-pagination'), {
		totalItems: 10,
		itemsPerPage: 10,
		visiblePages: 5,
		centerAlign: true
	});
	
	pagination.on('beforeMove', function(e) {
		getDataList(e.page);
	});
	
	// 데이터 목록
	function getDataList(page) {
		var $form = $('#cmpnySearchForm');
		var actionUrl = $form.attr('action');
		if(isEmpty(page)) {
			page = $form.find('#cmpnyPageIndex').val(page);
		}else if(page > 0) {
			$form.find('#cmpnyPageIndex').val(page);
		}
		var formData = $form.serialize();
		
		jsonResultAjax({
			url: actionUrl,
			type: 'get',
			data: formData,
			//dataType: 'json',
			callback: function(result) {
				/*
				if(result.message) {
					bootbox.alert({ title: '확인', message: result.message, size: 'small' }); 
				}
				*/
				
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
	
	// 업체 선택
	$(document).on('click', '.btnSelectCmpny', function(e) {
		e.preventDefault();
		var cmpnyId = $(this).data('cmpnyId');
		var cmpnyNm = $(this).data('cmpnyNm');
		var policyCn = $(this).data('policyCn');
		
		$('#cmpnyId').val(cmpnyId);
		$('#cmpnyNm').val(cmpnyNm);
		$('#cmpnyListModal').modal('hide');
		
		
	});
	
	// 업체 사용자 검색 Submit
	$(document).on('submit', '#cmpnySearchForm', function(e) {
		e.preventDefault();
		getDataList(1);
	});
	
	// 업체검색 Click
	$(document).on('click', '.btnSearchCmpny', function(e) {
		e.preventDefault();
		var $modal = $($(this).data('target'));
		
		$modal.modal('show');
		
		$modal.on('shown.bs.modal', function(e) {
			grid.refreshLayout();
		});
	});
	
})();