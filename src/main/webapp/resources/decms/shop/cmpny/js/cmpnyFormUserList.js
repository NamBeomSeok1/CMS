(function() {
	const grid = new tui.Grid({
		el: document.getElementById('data-member-grid'),
		rowHeight: 'auto',
		bodyHeight: 450,
		columns: [
			{ header: '이름', name: 'mberNm', width: 100, align: 'center'},
			{ header: 'ID', name: 'mberId', width: 100, align: 'center'},
			{ header: '업체', name: 'cmpnyNm', width: 150, align: 'center',
				formatter: function(item) {
					return isEmpty(item.value)?'-':item.value;
				}
			},
			{ header: '선택', name: 'cmpnyId', width: 100, align: 'center',
				formatter: function(item) {
					if(isEmpty(item.row.cmpnyId)) {
						return '<button type="button" class="btn btn-success btn-sm btnSelectMber" data-mber-id="' + item.row.mberId + '" data-esntl-id="' + item.row.esntlId + '"><i class="fas fa-check"></i></button>';
					}else {
						return '<span>-</span>';
					}
				}
			},
		]
	});
	tui.Grid.applyTheme('clean');

	var pagination = grid.getPagination();
	
	var pagination = new tui.Pagination(document.getElementById('data-member-grid-pagination'), {
		totalItems: 10,
		itemsPerPage: 10,
		visiblePages: 5,
		centerAlign: true
	});
	
	pagination.on('beforeMove', function(e) {
		getDataList(e.page);
	});
	
	// 외부에서 사용자 목록 호출
	reloadUserDataList = function() {
		getDataList(1);
	}
	
	function getDataList(page) {
		var $form = $('#mberSearchForm');
		var actionUrl = $form.attr('action');
		if(isEmpty(page)) {
			page = $form.find('#pageIndex').val(page);
		}else if(page > 0) {
			$form.find('#pageIndex').val(page);
		}
		var formData = $form.serialize();
		
		jsonResultAjax({
			url: actionUrl,
			method: 'get',
			data: formData,
			dataType: 'json',
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
	}
	
	//사용자 찾기 Click
	$(document).on('click', '.btnSearchMber', function(e) {
		e.preventDefault();
		var $modal = $($(this).data('target'));
		
		$modal.modal('show');
		
		$modal.on('shown.bs.modal', function(e) {
			//initGrid();
			grid.refreshLayout();
			//getDataList();;

		});
	});
	
	// 업체 사용자 검색 Submit
	$(document).on('submit', '#mberSearchForm', function(e) {
		e.preventDefault();
		getDataList(1);
	});
	
	// 업체 사용자 선택 Click
	$(document).on('click', '.btnSelectMber', function(e) {
		e.preventDefault();
		
		var mberId = $(this).data('mberId');
		var esntlId = $(this).data('esntlId');
		
		$('#cmpnyMberId').val(mberId);
		$('#cmpnyUserEsntlId').val(esntlId);
		
		//비밀번호 비활성화
		$('#cmpnyMberPassword').attr('readonly', true).val('');
		$('#cmpnyMberRePassword').attr('readonly', true).val('');
		
		$('#mberListModal').modal('hide');
	});
	
})();







//- 사용자 관리 --------------------------------------------------------------------------------------------------------------------------------------------
(function() {

	// 사용자 추가
	$(document).on('click', '.btnAddUser', function(e) {
		e.preventDefault();
		var actionUrl = $(this).attr('href');
		
		$.ajax({
			url: actionUrl,
			type: 'get',
			dataType: 'html',
			success: function(result) {
				$('#member-form').html(result);
				$('#member-form').find('.btnMberFormClose').removeAttr('data-dismiss');
				
				$('#member-form').slideDown(200);
				$('#member-list').hide();
				$('#mberListModalLabel').text('업체사용자목록 > 사용자 관리');
				$('#authorCode option[value!=ROLE_SHOP]').remove();
			}
		});
	});

	// 사용자추가 입력 닫기 Click
	$(document).on('click', '.btnMberFormClose', function() {
		$('#member-form').hide();
		$('#member-list').slideDown(200);
		$('#mberListModalLabel').text('업체사용자목록');
	});

	// 중복 ID 체크
	$(document).on('click', '.btnCheckDuplMberId', function(e) {
		e.preventDefault();
		var actionUrl = $(this).attr('href');
		var mberId = $('#mberId').val();
		
		jsonResultAjax({
			url: actionUrl,
			type: 'post',
			data: {'mberId': mberId},
			//dataType: 'json',
			callback: function(result) {
				/*
				if(result.message) {
					bootbox.alert({ title: '확인', message: result.message, size: 'small' }); 
				}
				*/

				if(result.success) {
					$('#checkDuplMberId').val('Y');
				}else {
					$('#checkDuplMberId').val('');
				}
			}
		});
	});
	
	// 회원ID 변경
	$(document).on('change', '#mberId', function() {
		$('#checkDuplMberId').val('');
	});
	
	// 저장
	$(document).on('submit', '[name=mberForm]', function(e) {
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
					var mber = result.data.mber;
					$('#cmpnyMberId').val(mber.mberId);
					$('#cmpnyUserEsntlId').val(mber.esntlId);
					
					$self.parents('.modal').modal('hide');

				}
			}
		});
	});
})();
