(function() {
	
	const grid = new tui.Grid({
		el: document.getElementById('data-grid'),
		rowHeight: 'auto',
		columns: [
			{ header: '고유ID', name: 'bankId', width: 120, align: 'center'},
			{ header: '은행명', name: 'bankNm', width: 220, align: 'center'},
			{ header: '관리', name: '', width: 100, align: 'center',
				formatter: function(item) {
					return 	'<a href="' + CTX_ROOT + '/decms/embed/shop/bank/modifyBank.do?bankId=' + item.row.bankId + '" class="btn btn-success btn-sm mr-1 btnModify" data-target="#bankModal" title="수정">' 
						+ 		'<i class="fas fa-edit"></i></a>' 
						+ 	'<a href="' + CTX_ROOT + '/decms/shop/bank/deleteBank.json?bankId=' + item.row.bankId + '" class="btn btn-danger btn-sm btnDelete" title="삭제">' 
						+ 		'<i class="fas fa-trash"></i></a>';
				}
			},
		],
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
		
		jsonResultAjax({
			url: actionUrl,
			type: 'get',
			data: formData,
			//dataType: 'json',
			callback: function(result) {
				if(result.message) {
					bootbox.alert({ title: '확인', message: result.message, size: 'small' }); 
				}
				
				if(result.success) {
					grid.resetData(result.data.list);
				}
			}
		});
	};
	
	/** 삭제 **/
	function deleteBank(actionUrl) {
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
	
	$(document).ready(function() {
		getDataList(1);
	})

	// 검색 submit
	$(document).on('submit', '#searchForm', function(e) {
		e.preventDefault();
		
		getDataList(1);
	});
	
	// 등록 Click 
	$(document).on('click', '.btnAdd', function(e) {
		e.preventDefault();
		
		var actionUrl = $(this).attr('href');
		var modal = $(this).data('target');
		
		modalShow(actionUrl, '은행 등록', modal);
	});
	
	// 수정 Click
	$(document).on('click','.btnModify', function(e) {
		e.preventDefault();
		
		var actionUrl = $(this).attr('href');
		var modal = $(this).data('target');
		
		modalShow(actionUrl,'은행 수정', modal);
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
					deleteBank(actionUrl);
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