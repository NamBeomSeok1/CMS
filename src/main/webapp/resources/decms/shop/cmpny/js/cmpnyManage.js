(function() {
	var menuId = $('[name=menuId]').val();
	
	const grid = new tui.Grid({
		el: document.getElementById('data-grid'),
		rowHeight: 'auto',
		columns: [
			{ header: '업체고유번호', name: 'cmpnyId', width: 200, align: 'center'},
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
			{ header: '담당자', name: 'chargerNm', width: 100, align: 'center'},
			{ header: '연락처', name: 'cmpnyTelno', width: 120, align: 'center'},
			{ header: '담당자연락처', name: 'chargerTelno', width: 120, align: 'center'},
			{ header: '상품수', name: 'cmpnyGoodsCo', width: 50, align: 'center'},
			{ header: '입점일', name: 'opnngDe', width:100, align: 'center',
				formatter: function(item) {
					return moment(item.value, 'YYYYMMDD').format('YYYY-MM-DD');
				}
			},
			{ header: '등록상태', name: 'opnngSttusCode', width: 80, align: 'center',
				formatter: function(item) {
					if(item.value == 'R') {
						return '<small class="f-s-1"><span class="badge badge-primary">등록신청</span></small>';
					}else if(item.value == 'A') {
						return '<small class="f-s-1"><span class="badge badge-warning">처리 중</span></small>';
					}else if(item.value == 'C') {
						return '<small class="f-s-1"><span class="badge badge-dark">등록완료</span></small>';
					}else if(item.value == 'D') {
						return '<small class="f-s-1"><span class="badge badge-secondary">등록거부</span></small>';
					}
				}
			},
			{ header: '관리', name: '', width: 100, align: 'center',
				formatter: function(item) {
					return 	'<a href="' + CTX_ROOT + '/decms/shop/cmpny/modifyCmpny.do" class="btn btn-success btn-sm mr-1 btnModify" data-cmpny-id="' + item.row.cmpnyId + '" title="수정">' 
						+ 		'<i class="fas fa-edit"></i></a>' 
						+ 	'<a href="' + CTX_ROOT + '/decms/shop/cmpny/deleteCmpny.json?menuId=' + menuId + '&cmpnyId=' + item.row.cmpnyId + '" class="btn btn-danger btn-sm btnDelete" title="삭제">' 
						+ 		'<i class="fas fa-trash"></i></a>';
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

	var isSearch = true;
	var pagination = grid.getPagination();
	
	var pagination = new tui.Pagination(document.getElementById('data-grid-pagination'), {
		totalItems: 10,
		itemsPerPage: 10,
		visiblePages: 5,
		centerAlign: true
	});
	
	pagination.on('beforeMove', function(e) {
		if(isSearch == false) {
			getDataList(e.page);
		}
	});
	
	// 데이터 목록
	function getDataList(page) {
		var $form = $('#searchForm');
		var actionUrl = $form.attr('action');
		if(isEmpty(page)) {
			//$form.find('#pageIndex').val(1);
		}else if(page > 0) {
			$form.find('#pageIndex').val(page);
		}
		var formData = $form.serialize();
		
		jsonResultAjax({
			url: actionUrl,
			type: 'get',
			data: formData,
			callback: function(result) {
			
				if(result.success) {
					var paginationObj = result.data.paginationInfo;
					
					//if(paginationObj.currentPageNo == 1) {
						pagination.setItemsPerPage(paginationObj.recordCountPerPage);
						pagination.reset(paginationObj.totalRecordCount);
						
						isSearch = true;
						pagination.movePageTo(paginationObj.currentPageNo);
						isSearch = false;
					//}
					grid.resetData(result.data.list);
				}
			}
		});
	};
	
	// 입력폼 초기화
	function initArticleForm(modal) {
		$(modal).on('show.bs.modal', function(event) {
			
			$('#datepicker-opnngDe').datetimepicker({
				locale: 'ko',
				format: 'YYYY-MM-DD'
			});
			
		});
	};
	
	/** 삭제 **/
	function deleteCmpny(actionUrl) {
		jsonResultAjax({
			url: actionUrl,
			type: 'post',
			//dataType: 'json',
			callback: function(result) {
				//if(result.message) {
				//	bootbox.alert({ title: '확인', message: result.message, size: 'small' }); 
				//}
				
				if(result.success) {
					getDataList(1);
				}
			}
		});
	};
	
	$(document).ready(function() {
		getDataList();
	})
	
	$('#datepicker-searchBgnde').datetimepicker({
		locale: 'ko',
		format: 'YYYY-MM-DD'
	});
	
	$('#datepicker-searchEndde').datetimepicker({
		locale: 'ko',
		format: 'YYYY-MM-DD'
	});
	
	$(document).on('click', '.btnModify', function(e) {
		e.preventDefault();
		
		var href = $(this).attr('href');
		var searchFormData = $('#searchForm').serialize();
		location.href = href + '?cmpnyId=' + $(this).data('cmpnyId') + '&' + searchFormData;
	});

	// 검색 submit
	$(document).on('submit', '#searchForm', function(e) {
		e.preventDefault();
		
		getDataList(1);
	});
	
	// 삭제 Click
	$(document).on('click','.btnDelete', function(e) {
		e.preventDefault();
		var actionUrl = $(this).attr('href');
		
		bootbox.confirm({
			title: '삭제확인',
			message: '삭제하시겠습니까?',
			callback: function(result) {
				if(result) {
					deleteCmpny(actionUrl);
				}
			}
		});
	});
	
	// 저장
	$(document).on('submit', '#registForm', function(e) {
		e.preventDefault();
		var $self = $(this);
		
		var actionUrl = $(this).attr('action');
		var method = $(this).attr('method');
		
		$(this).ajaxSubmit({
			url: actionUrl,
			type: method,
			dataType: 'json',
			success: function(result) {
				if(result.message) {
					bootbox.alert({ title: '확인', message: result.message, size: 'small' }); 
				}
				
				if(result.success) {
					$self.parents('.modal').modal('hide');
					getDataList(1);
				}
			}
		});
	});
	
})();