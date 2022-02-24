(function() {
	
	const Grid = tui.Grid;
	
	const instance = new Grid({
		el: document.getElementById('data-grid'),
		columns: [
			{ header: '게시판명', name: 'bbsNm', align: 'center' },
			{ header: '공지', name: 'noticeAt', width:50, align: 'center', formatter: function(item) {return item.value=='Y'?'<i class="fas fa-check-circle"></i>':'<i class="fas fa-times"></i>';}},
			{ header: '비밀', name: 'secretAt', width:50, align: 'center', formatter: function(item) {return item.value=='Y'?'<i class="fas fa-check-circle"></i>':'<i class="fas fa-times"></i>';}},
			{ header: '익명', name: 'annymtyAt', width:50, align: 'center', formatter: function(item) {return item.value=='Y'?'<i class="fas fa-check-circle"></i>':'<i class="fas fa-times"></i>';}},
			{ header: '기간', name: 'usgpdAt', width:50, align: 'center', formatter: function(item) {return item.value=='Y'?'<i class="fas fa-check-circle"></i>':'<i class="fas fa-times"></i>';}},
			{ header: '답장', name: 'replyAt', width:50, align: 'center', formatter: function(item) {return item.value=='Y'?'<i class="fas fa-check-circle"></i>':'<i class="fas fa-times"></i>';}},
			{ header: '댓글', name: 'commentAt', width:50, align: 'center', formatter: function(item) {return item.value=='Y'?'<i class="fas fa-check-circle"></i>':'<i class="fas fa-times"></i>';}},
			{ header: '첨부', name: 'fileAtachAt', width:50, align: 'center', formatter: function(item) {return item.value=='Y'?'<i class="fas fa-check-circle"></i>':'<i class="fas fa-times"></i>';}},
			{ header: '생성일', name: 'frstRegistPnttm', width:120, align: 'center',
				formatter: function(item) {
					return isEmpty(item.value) ? '' : moment(item.value).format('YYYY-MM-DD');
				}
			},
			{ header: '관리', name: 'bbsId', width: 150 , align: 'center',
				formatter: function(item) {
					return 	'<a href="' + CTX_ROOT + '/decms/embed/board/master/modifyBoardMaster.do?bbsId=' + item.value + '" class="btn btn-success btn-sm mr-1 btnModify" data-target="#boardMasterModal" title="수정">' 
						+ 		'<i class="fas fa-edit"></i></a>' 
						+ 	'<a href="' + CTX_ROOT + '/decms/board/master/deleteBoardMaster.json?bbsId=' + item.value + '" class="btn btn-danger btn-sm btnDelete" title="삭제">' 
						+ 		'<i class="fas fa-trash"></i></a>'
						+ 	'<a href="' + CTX_ROOT + '/decms/embed/board/article/boardManage.do?bbsId=' + item.value + '" class="btn btn-dark btn-sm ml-3 btnView" title="게시판 보기">' 
						+ 		'<i class="fas fa-external-link-alt"></i></a>';
				}
			},
		],
		columnOptions: {
			frozenCount: 2,
			frozenBorderWidth: 2,
		},
		pageOptions: {
			useClient: true,
			perPage: 30
		}
	});
	
	Grid.applyTheme('clean');
	
	getDataList = function(page) {
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
					var pagination = result.data.paginationInfo;
					
					const pageState = {page:pagination.currentPageNo, totalCount: pagination.totalRecordCount, perPage: pagination.recordCountPerPage};
					instance.resetData(result.data.list, pageState);
				}
				
			}
		});
	};
	
	/** 삭제 **/
	deleteBoardMaster = function(actionUrl) {
		$.ajax({
			url: actionUrl,
			type: 'post',
			dataType: 'json',
			success: function(result) {
				if(result.message) {
					bootbox.alert({ title: '확인', message: result.message, size: 'small' }); 
				}
				
				if(result.success) {
					getDataList();
				}
			}
		});
	};
	
	$(document).ready(function() {
		getDataList(1);
	});
	
	// 검색 Submit
	$(document).on('submit', '#searchForm', function(e) {
		e.preventDefault();
		
		getDataList(1);
	});
	
	// 등록 Click
	$(document).on('click', '.btnAddBoard', function(e) {
		e.preventDefault();
		
		var actionUrl = $(this).attr('href');
		var modal = $(this).data('target');
		
		modalShow(actionUrl, '게시판 등록', modal);
	});
	
	// 수정 Click
	$(document).on('click','.btnModify', function(e) {
		e.preventDefault();
		
		var actionUrl = $(this).attr('href');
		var modal = $(this).data('target');
		
		modalShow(actionUrl,'게시판 수정', modal);
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
					deleteBoardMaster(actionUrl);
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
	
	// 게시글 뷰 Click
	$(document).on('click', '.btnView', function(e) {
		e.preventDefault();
		var actionUrl = $(this).attr('href');
    	popupWindow(actionUrl, '게시판', 1000, 700);
	});
	
	
})();