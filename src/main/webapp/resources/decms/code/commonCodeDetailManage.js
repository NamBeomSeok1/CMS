(function() {
	const grid = new tui.Grid({
		el: document.getElementById('data-sub-grid'),
		columns: [
			{ header: '상세코드', name:'code', width: 120, align: 'center'},
			{ header: '상세코드명', name: 'codeNm', width: 100, align: 'center'},
			{ header: '상세코드설명', name: 'codeDc' },
			{ header: '상세코드순서', name: 'codeSn' },
			{ header: '생성일', name: 'frstRegistPnttm', width:100, align: 'center',
				formatter: function(item) {
					return isEmpty(item.value) ? '' : moment(item.value).format('YYYY-MM-DD');
				}
			},
			{ header: '관리', name: '', width: 100, align: 'center',
				formatter: function(item) {
					return 	'<a href="' + CTX_ROOT + '/decms/embed/code/modifyCodeDetail.do?codeId=' + item.row.codeId + '&code=' + item.row.code + '" class="btn btn-success btn-sm mr-1 btnModifyCodeDetail" data-target="#codeModal" title="수정">' 
						+ 		'<i class="fas fa-edit"></i></a>' 
						+ 	'<a href="' + CTX_ROOT + '/decms/code/deleteCodeDetail.json?codeId=' + item.row.codeId + '&code=' + item.row.code + '" class="btn btn-danger btn-sm btnDeleteCodeDetail" title="삭제">' 
						+ 		'<i class="fas fa-trash"></i></a>';
				}
			},
		],
		columnOptions: {
			frozenCount: 1,
			frozenBorderWidth: 2,
		}
	});
	
	tui.Grid.applyTheme('clean');
	
	// 데이터 목록
	function getDataList(page) {
		var $form = $('#searchSubForm');
		var actionUrl = $form.attr('action');
		if(isEmpty(page)) {
			page = $form.find('#subPageIndex').val(page);
		}else if(page > 0) {
			$form.find('#subPageIndex').val(page);
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
					grid.resetData(result.data.list);
				}
			}
		});
	};
	
	function deleteCodeDetail(actionUrl) {
		$.ajax({
			url: actionUrl,
			type: 'post',
			dataType: 'json',
			success: function(result) {
				if(result.message) {
					bootbox.alert({ title: '확인', message: result.message, size: 'small' }); 
				}
				
				if(result.success) {
					getDataList(1);
				}
			}
		});
	}
	
	// 외부에서 호출
	searchCodeDetailList = function() {
		getDataList(1);
	}

	$(document).ready(function() {
		getDataList(1);
	});
	
	// 등록 Click 
	$(document).on('click', '.btnAddCodeDetail', function(e) {
		e.preventDefault();
		
		var codeId = $('#searchSubForm').find('#codeId').val();
		var actionUrl = $(this).attr('href') + '?codeId=' + codeId;
		var modal = $(this).data('target');
		
		console.log(codeId);
		if(isEmpty(codeId)) {
			bootbox.alert({ title: '확인', message: '코드를 선택하세요', size: 'small' }); 
		}else {
			modalShow(actionUrl, '코드상세 등록', modal);
		}
	});
	
	// 수정 Click
	$(document).on('click', '.btnModifyCodeDetail', function(e) {
		e.preventDefault();

		var actionUrl = $(this).attr('href');
		var modal = $(this).data('target');
		
		modalShow(actionUrl,'코드상세 수정', modal);
	});
	
	// 삭제 Click
	$(document).on('click','.btnDeleteCodeDetail', function(e) {
		e.preventDefault();
		var actionUrl = $(this).attr('href');
		
		bootbox.confirm({
			title: '삭제확인',
			message: '삭제하시겠습니까?',
			callback: function(result) {
				if(result) {
					deleteCodeDetail(actionUrl);
				}
			}
		});
	});
	
	// 저장
	$(document).on('submit', '#registSubForm', function(e) {
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
	
}) ();