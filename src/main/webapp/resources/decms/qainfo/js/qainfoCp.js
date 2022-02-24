(function() {
	var grid;
	var gridEl;
	
	grid = new tui.Grid({
		el: document.getElementById('data-cp-grid'),
		columns: [
			{ header: '작성일', name: 'frstRegistPnttm', width:100, align: 'center',
				formatter: function(item) {
					return isEmpty(item.value) ? '' : moment(item.value).format('YYYY-MM-DD');
				}
			},
			{ header: '작성자', name: 'wrterNm', width: 100, align: 'center', },
			{ header: '문의유형', name: 'qestnTyNm', width: 100, align: 'center' },
			{ header: '질문제목', name: 'qestnSj',
				formatter: function(item) {
					return '<a href="' + CTX_ROOT + '/decms/embed/qainfo/viewCpQainfo.do?qaId=' + item.row.qaId + '" class="btnView" data-target="#qainfoModal">' + item.value + '</a>';
				}
			},
			{ header: '상태', name: 'qnaProcessSttusCode', width:100, align: 'center',
				formatter: function(item) {
					var retValue = '';
					switch(item.value) {
					case 'R':
						retValue = '<span class="text-danger">답변대기</span>';
						break;
					case 'C':
						retValue = '<span class="text-primary">답변완료</span>';
						break;
					}
					return retValue;
				}
			},
			{ header: '수정', name: 'qaId', width: 100, align: 'center',
				formatter: function(item) {
					if(item.row.qnaProcessSttusCode != 'C') {
						return 	'<a href="' + CTX_ROOT + '/decms/embed/qainfo/modifyCpQainfo.do?qaId=' + item.value + '" class="btn btn-success btn-sm mr-1 btnModify" data-target="#qainfoModal" title="수정">' 
							+ 		'<i class="fas fa-edit"></i></a>';
					}else {
						return '-';
					}
				}
			},

		],
		columnOptions: {
			frozenCount: 1,
			frozenBorderWidth: 2,
		}
	});
	
	
	tui.Grid.applyTheme('clean');
	
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
	
	// 입력폼 초기화
	function initArticleForm(modal) {
		$(modal).on('show.bs.modal', function(event) {
			initWebEditor();
			
			/*
			$('#datepicker-writngDe').datetimepicker({
				locale: 'ko',
				format: 'YYYY-MM-DD HH:mm:ss',
				icons: {
					time: 'fas fa-clock'
				}

			});
			$('#datepicker-answerDe').datetimepicker({
				locale: 'ko',
				format: 'YYYY-MM-DD HH:mm:ss',
				icons: {
					time: 'fas fa-clock'
				}
			});
			*/
		});
	}
	
	// 삭제
	function deleteQainfo(actionUrl) {
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
	};
	
	$(document).ready(function() {
		getDataList(1);
	});
	
	//상품QNA목록 
	$(document).on('click','#goodsQna',function(e){
		e.preventDefault();
		$('#qaSeCode').val('goods');
		getDataList(1);
	})
	//1:1문의
	$(document).on('click','#oneToOneQna',function(e){
		e.preventDefault();
		$('#qaSeCode').val('SITE');
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
		
		initArticleForm(modal);
		modalShow(actionUrl, '질문답변 등록', modal);
	});
	
	// 수정 Click
	$(document).on('click', '.btnModify', function(e) {
		e.preventDefault();

		var actionUrl = $(this).attr('href');
		var modal = $(this).data('target');
		
		initArticleForm(modal);
		modalShow(actionUrl,'질문답변 수정', modal);
	});

	// 뷰 Click
	$(document).on('click', '.btnView', function(e) {
		e.preventDefault();

		var actionUrl = $(this).attr('href');
		var modal = $(this).data('target');
		
		modalShow(actionUrl,'질문상세', modal);
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
					deleteQainfo(actionUrl);
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
	

	$('#datepicker-searchBgnde').datetimepicker({
		locale: 'ko',
		format: 'YYYY-MM-DD'
	});
		
	$('#datepicker-searchEndde').datetimepicker({
		locale: 'ko',
		format: 'YYYY-MM-DD'
	});
})();