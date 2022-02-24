(function() {
	
	const grid = new tui.Grid({
		el: document.getElementById('data-grid'),
		columns: [
			{ header: '팝업제목', name: 'popupSj' },
			{ header: '팝업링크', name: 'popupLink', width:200 },
			{ header: '기간', name: 'popupBgnde', width:250, align: 'center',
				formatter: function(item) {
					var dateStr = moment(item.row.popupBgnde,'YYYYMMDDhhmmss').format('YYYY-MM-DD HH:mm')  + ' ~ ' 
								+ moment(item.row.popupEndde,'YYYYMMDDhhmmss').format('YYYY-MM-DD HH:mm');
					return dateStr;
				}
			},
			{ header: '활성', name: 'actvtyAt', width: 40, align: 'center',
				formatter: function(item) {
					return (item.value == 'Y')?'<span class="badge badge-primary">O</span>':'<span class="badge badge-secondary">X</span>';
				}
			},
			{ header: '생성일', name: 'frstRegistPnttm', width:100, align: 'center',
				formatter: function(item) {
					return isEmpty(item.value) ? '' : moment(item.value).format('YYYY-MM-DD');
				}
			},
			{ header: '관리', name: 'popupId', width: 130, align: 'center',
				formatter: function(item) {
					return 	'<a href="' + CTX_ROOT + '/decms/embed/popup/modifyPopup.do?popupId=' + item.value + '" class="btn btn-success btn-sm mr-1 btnModify" data-target="#popupModal" title="수정">' 
						+ 		'<i class="fas fa-edit"></i></a>' 
						+ 	'<a href="' + CTX_ROOT + '/decms/popup/deletePopup.json?popupId=' + item.value + '" class="btn btn-danger btn-sm btnDelete" title="삭제">' 
						+ 		'<i class="fas fa-trash"></i></a>';
				}
			},
		]
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
	};

	// 입력폼 초기화
	function initArticleForm(modal) {
		$(modal).on('show.bs.modal', function(event) {
			//initWebEditor();
			/*$('.summernote').summernote({
				lang: 'ko-KR',
				tabsize:2,
				height:300,
				dialogsInBody: true,
				callbacks: {
					onImageUpload : function(files) {
						var $submitForm = $(this).parents('form');
						for(var idx = 0; idx < files.length; idx++) {
							summernoteUploadImage(files[idx], $(this));
						}
					}
				}
			});*/
			
			$('#datepicker-popupBgnDate').datetimepicker({
				locale: 'ko',
				format: 'YYYY-MM-DD'
			});
			
			$('#datepicker-popupEndDate').datetimepicker({
				locale: 'ko',
				format: 'YYYY-MM-DD'
			});
			
		});
	};
	
	// 삭제
	function deletePopup(actionUrl) {
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
	
	// 검색 submit
	$(document).on('submit', '#searchForm', function(e) {
		e.preventDefault();
		
		getDataList(1);
	});
	
	// 등록 Click 
	$(document).on('click', '.btnAddPopup', function(e) {
		e.preventDefault();
		
		var actionUrl = $(this).attr('href');
		var modal = $(this).data('target');
		
		initArticleForm(modal);
		modalShow(actionUrl, '팝업 등록', modal);
	});
	
	// 수정 Click
	$(document).on('click', '.btnModify', function(e) {
		e.preventDefault();

		var actionUrl = $(this).attr('href');
		var modal = $(this).data('target');
		
		initArticleForm(modal);
		modalShow(actionUrl,'팝업 수정', modal);
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
					deletePopup(actionUrl);
				}
			}
		});
	});
	
	//이미지 찾기
	/*$(document).on('click', '.btnImageFinder', function(e) {
		e.preventDefault();
	});*/
	
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