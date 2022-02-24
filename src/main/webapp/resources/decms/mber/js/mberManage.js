(function() {
	// Grid 설정
	const Grid = tui.Grid;
	
	const grid = new Grid({
		el: document.getElementById('data-grid'),
		columns: [
			{ header: '회원명', name: 'mberNm', align: 'center' },
			{ header: 'ID', name: 'mberId', align: 'center' },
			{ header: '제휴사', name: 'groupId', align: 'center',
				formatter : function(item){
					if(item.value=='GROUP_00000000000001'){
						return '이지웰';
					}else if(item.value=='GROUP_00000000000002' || item.value=='GROUP_00000000000005' || item.value=='GROUP_00000000000004'){
						return 'B2C';
					}else{
						return '-';
					}
				}
			},
			{ header: 'Email', name: 'email', align: 'center' },
			{ header: '가입일', name: 'sbscrbDe', align: 'center', 
				formatter: function(item) {
					return isEmpty(item.value) ? '' : moment(item.value).format('YYYY-MM-DD');
				} 
			},
			{ header: '잠김여부', name: 'lockAt', align: 'center',
				formatter: function(item) {
					//console.log(item.row);
					if(item.value === 'Y') {
						return '<span class="badge badge-danger">잠김(' + item.row.lockCnt + ')</span>';
					}else {
						return '';
					}
				}
			},
			{ header: '잠김시점', name: 'lockLastPnttm', align: 'center', 
				formatter: function(item) {
					return !isEmpty(item.value) && item.row.lockAt == 'Y' ?  moment(item.value).format('YYYY-MM-DD') : '';
				}
			},
			{ header: '관리', name: 'esntlId', align: 'center',
				formatter: function(item) {
					return 	'<a href="' + CTX_ROOT + '/decms/embed/mber/modifyMber.do?esntlId=' + item.value + '" class="btn btn-success btn-sm mr-1 btnModify" data-target="#mberModal">' 
						+ 		'<i class="fas fa-edit"></i> 수정</a>' 
						+ 	'<a href="' + CTX_ROOT + '/decms/mber/deleteMber.json?esntlId=' + item.value + '" class="btn btn-danger btn-sm btnDelete">' 
						+ 		'<i class="fas fa-trash"></i> 삭제</a>';
				}
			},
		],
		columnOptions: {
			frozenCount: 2,
			frozenBorderWidth: 1 
		}
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
	
	
	/** 데이터 검색 목록 **/
	getDataList = function(page) {
		var $form = $('#searchForm');
		var actionUrl = $form.attr('action');
		if(isEmpty(page)) {
			page = $form.find('#pageIndex').val(page);
		}else if(page > 0) {
			$form.find('#pageIndex').val(page);
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
					$('#totalCnt').text(paginationObj.totalRecordCount+'명');
				}
				
			}
		});
	}
	
	/** 삭제 **/
	deleteMber = function(actionUrl) {
		jsonResultAjax({
			url: actionUrl,
			type: 'post',
			//dataType: 'json',
			callback: function(result) {
				/*
				if(result.message) {
					bootbox.alert({ title: '확인', message: result.message, size: 'small' }); 
				}
				*/
				
				if(result.success) {
					getDataList();
				}
			}
		});
	};
	
	/** 모달 Show **/
	/*
	modalShow = function(actionUrl, title, modal) {
		var $modal = $(modal);
		$modal.find('.modal-body .modal-spinner').show();
		
		$modal.on('hidden.bs.modal', function(e) {
			$(this).find('.modal-body .modal-spinner').hide();
		});
		
		$modal.find('.modal-body').load(actionUrl, function() {
			$modal.find('.modal-title').text(title);
			$modal.modal('show');
		});
	};
	*/
	
	
	$(document).ready(function() {
		getDataList(1);
	});
	
	// 검색 Submit
	$(document).on('submit', '#searchForm', function(e) {
		e.preventDefault();
		
		getDataList(1);
	});
	
	// 등록 Click
	$(document).on('click', '.btnAddMber', function(e) {
		e.preventDefault();

		var actionUrl = $(this).attr('href');
		var modal = $(this).data('target');
		
		modalShow(actionUrl,'회원 등록', modal);
	});
	
	// 수정 Click
	$(document).on('click','.btnModify', function(e) {
		e.preventDefault();
		
		var actionUrl = $(this).attr('href');
		var modal = $(this).data('target');
		
		modalShow(actionUrl,'회원 수정', modal);
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
					deleteMber(actionUrl);
				}
			}
		});
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

	// 비밀번호 변경 Click 
	$(document).on('click','.btnChagePasswd', function(e) {
		e.preventDefault();
		
		var actionUrl = $(this).attr('href');
		
		bootbox.dialog({
			title: '비밀번호 변경',
			message:	'<form id="changePasswordForm" action="' + actionUrl +'" method="post">' 
					+	'<input type="password" name="password" class="form-control mb-1" value="" placeholder="비밀번호"/>' 
					+ 	'<input type="password" name="repassword" class="form-control" value="" placeholder="비밀번호 확인"/>' 
					+ 	'</form>',
			buttons: {
				cancel: {
					label: '취소',
					className: 'btn-secondary',
					callback: function() {
					}
				},
				ok: {
					label: '변경',
					className: 'btn-primary',
					callback: function() {
						$('#changePasswordForm').submit();
					}
				}
			}
			
		});
	});
	
	// 비밀번호 전송
	$(document).on('submit', '#changePasswordForm', function(e) {
		e.preventDefault();

		var actionUrl = $(this).attr('action');
		var formData = {
			'esntlId': $('#esntlId').val(), 
			'mberId': $('#mberId').val(),
			'password': $(this).find('[name=password]').val(), 
			'repassword': $(this).find('[name=repassword]').val()
		};
		
		jsonResultAjax({
			url: actionUrl,
			type: 'post',
			data: formData,
			callback: function(result) {
				
				if(result.success) {
					
				}
			}
		});
	});
	
	// 잠김 풀기
	$(document).on('click', '.btnUnlock', function(e) {
		e.preventDefault();
		var actionUrl = $(this).attr('href');
		var $modal = $(this).parents('.modal');
		
		jsonResultAjax({
			url: actionUrl,
			type: 'post',
			callback: function(result) {
			
				if(result.success) {
					$modal.modal('hide');
					getDataList(1);
				}
			}
		})
	});
	

})();