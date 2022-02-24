(function() {
	
	const gridPerPage = $('#data-grid').data('listCount') || 10;
	//const Grid = tui.Grid;
	
	const grid = new tui.Grid({
		el: document.getElementById('data-grid'),
		rowHeight: 'auto',
		columns: [
			{ header: 'No.', name: 'rn', width: 50, align: 'center',
				formatter: function(item) {
					//var pageInfo = instance.getPagination()._options;
					//console.log(instance.store);
					//return instance.getPaginationTotalCount()- ( (pageInfo.page-1) * pageInfo.itemsPerPage + item.row.rowKey); 
					return item.value;
				}
			},
			{ header: '분류', name: 'ctgryId', width: 100, align: 'center',
				formatter: function(item) {
					if(item.row.noticeAt == 'Y') {
						return '공지';
					}
				}
			},
			{ header: '제목', name: 'nttSj',
				formatter: function(item) {
					return (item.row.replyAt == 'Y')? ' <i class="fas fa-reply fa-rotate-180 ml-2"></i> ' + item.value : item.value;
				}
			},
			{ header: '작성자', name: 'ntcrNm', width: 120, align: 'center',
				formatter: function(item) {
					return item.value + '<br/>(' + item.row.ntcrId + ')';
				}
			},
			{ header: '게시시작일', name: 'ntceBgnde', width: 100, align: 'center',
				formatter: function(item) {
					var cssClass = (item.row.bbscttAt == 'N')? 'text-danger' : '';
					return isEmpty(item.value) ? '':'<span class="' + cssClass + '">' + moment(item.value,'YYYYMMDD').format('YYYY-MM-DD') + '</span>';
				}
			},
			{ header: '게시종료일', name: 'ntceEndde', width: 100, align: 'center',
				formatter: function(item) {
					var cssClass = (item.row.bbscttAt == 'N')? 'text-danger' : '';
					return isEmpty(item.value) ? '':'<span class="' + cssClass + '">' + moment(item.value,'YYYYMMDD').format('YYYY-MM-DD') + '</span>';
				}
			},
			{ header: '첨부', name: 'atchFileId', width:50, align: 'center', 
				formatter: function(item) {
					return !isEmpty(item.value)?'<i class="fas fa-check-circle"></i>':'<i class="fas fa-times"></i>';
				}
			},
			{ header: '생성일', name: 'frstRegistPnttm', width:130, align: 'center',
				formatter: function(item) {
					return isEmpty(item.value) ? '' : moment(item.value).format('YYYY-MM-DD hh:mm:ss');
				}
			},
			{ header: '관리', name: 'nttId', width: 100 , align: 'center',
				formatter: function(item) {
					return 	'<a href="' + CTX_ROOT + '/decms/embed/board/article/modifyBoardArticle.do?nttId=' + item.value + '" class="btn btn-success btn-sm mr-1 btnModify" data-target="#boardArticleModal" title="수정">' 
						+ 		'<i class="fas fa-edit"></i></a>' 
						+ 	'<a href="' + CTX_ROOT + '/decms/board/deleteArticle.json?nttId=' + item.value + '" class="btn btn-danger btn-sm btnDelete" title="삭제">' 
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
	/*
	pagination.on('afterMove', function(evt) {
	     var currentPage = evt.page;
	     console.log(currentPage);
	     //getDataList(currentPage);
	});
	*/
	

	/*
	grid.on('scrollEnd', function(data) {
		console.log(data);
	});
	*/
	
	//grid.applyTheme('clean');
	
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
			
			$('#datepicker-ntceBgnde').datetimepicker({
				locale: 'ko',
				format: 'YYYY-MM-DD'
			});
			$('#datepicker-ntceEndde').datetimepicker({
				locale: 'ko',
				format: 'YYYY-MM-DD'
			});
			
			getCommentList(1);
			$(this).off('show.bs.modal');
		});
	}
	
	$(document).ready(function() {
		getDataList(1);
	});
	
	// 검색 Submit
	$(document).on('submit', '#searchForm', function(e) {
		e.preventDefault();
		
		getDataList(1);
	});
	
	// 글쓰기 Click
	$(document).on('click', '.btnWrite', function(e) {
		e.preventDefault();
		
		var actionUrl = $(this).attr('href');
		var modal = $(this).data('target');
		
		initArticleForm(modal);
		modalShow(actionUrl, '글쓰기', modal);

	});
	
	// 수정 Click
	$(document).on('click','.btnModify', function(e) {
		e.preventDefault();
		
		var actionUrl = $(this).attr('href');
		var modal = $(this).data('target');
		
		initArticleForm(modal);
		modalShow(actionUrl,'글수정', modal);
	});
	
	$(document).on('click', '.btnDelete', function(e) {
		e.preventDefault();
		
		if(!confirm('삭제하시겠습니까?')) {
			return false;
		}
		var actionUrl = $(this).attr('href');
		
		jsonResultAjax({
			url: actionUrl,
			method: 'post',
			dataType: 'json',
			callback: function(result) {
				if(result.success) {
					getDataList(1);
				}
			}
		});
	});
	
	// 답장 Click
	$(document).on('click', '.btnReply', function(e) {
		e.preventDefault();
		var actionUrl = $(this).attr('href');
		var $modal = $($(this).data('target'));
		
		$modal.find('.modal-body').load(actionUrl, function() {
			initWebEditor();
			$('#datepicker-ntceBgnde').datetimepicker({
				locale: 'ko',
				format: 'YYYY-MM-DD'
			});
			$('#datepicker-ntceEndde').datetimepicker({
				locale: 'ko',
				format: 'YYYY-MM-DD'
			});
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

}) ();