(function() {
	
	const gridPerPage = $('#data-grid').data('listCount') || 10;
	const isAdmin =  $('#data-grid').data('isadmin');
	
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
					var subject = (item.row.replyAt == 'Y')? ' <i class="fas fa-reply fa-rotate-180 ml-2"></i> ' + item.value : item.value;
					subject = '<a href="' + CTX_ROOT + '/decms/embed/board/article/viewBoardArticle.do?nttId=' 
						+ item.row.nttId + '" class="btnView text-dark" data-target="#boardArticleModal" title="게시글 상세">' 
						+ subject + '</a>';

					return subject;
				}
			},
			{ header: '작성자', name: 'ntcrNm', width: 120, align: 'center',
				formatter: function(item) {
					return item.value + '<br/>(' + item.row.ntcrId + ')';
				}
			},
			/*
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
			*/
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
			{ header: '관리', name: 'nttId', width:130, align: 'center',
				formatter: function(item) {
					if (item.row.bbsId == 'BBSMSTR_0000000000CP'&& isAdmin == 'Y') {
						return '<a href="#none" onclick="sendAlimTalk(' + item.row.nttId + ')"><span class="btn btn-danger btn-sm">알림톡 발송</span></a>';
					} else {
						return '-';
					}
					
				}
			}
			/*
			{ header: '관리', name: 'nttId', width: 100 , align: 'center',
				formatter: function(item) {
					return 	'<a href="' + CTX_ROOT + '/decms/embed/board/article/modifyBoardArticle.do?nttId=' + item.value + '" class="btn btn-success btn-sm mr-1 btnModify" data-target="#boardArticleModal" title="수정">' 
						+ 		'<i class="fas fa-edit"></i></a>' 
						+ 	'<a href="' + CTX_ROOT + '/decms/board/master/deleteBoardMaster.json?bbsId=' + item.value + '" class="btn btn-danger btn-sm btnDelete" title="삭제">' 
						+ 		'<i class="fas fa-trash"></i></a>';
				}
			},
			*/
		],
		columnOptions: {
			frozenCount: 1,
			frozenBorderWidth: 1,
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
		
		jsonResultAjax({
			url: actionUrl,
			method: 'get',
			data: formData,
			dataType: 'json',
			callback: function(result) {
				
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
	
	/**
	 * 에디터 이미지 업로드
	 * @param image
	 * @param $editor
	 */
	/*summernoteUploadImage = function(image, $editor) {
		var data = new FormData();
		data.append("file", image);

		$.ajax({
			url : CTX_ROOT + '/fms/imageUpload.do',
			cache : false,
			contentType : false,
			processData : false,
			data : data,
			type : 'post',
			dataType : 'json',
			success : function(data) {
				var result = data.result;
				
				if(result.success == true) {
					$editor.summernote('editor.insertImage', result.data.url);
				}else {
					if(result.message) {
						alert(result.message);
					}
				}
			},error :function (){}
		});
	};*/
	
	// 입력폼 초기화
	function initArticleForm(modal) {
		$(modal).on('show.bs.modal', function(event) {
			$('.summernote').summernote({
				lang: 'ko-KR',
				tabsize:2,
				height:300,
				dialogsInBody: true,
				fontNames: ['굴림', '궁서', '맑은고딕', '돋움', '바탕체' ],
				callbacks: {
					onImageUpload : function(files) {
						var $submitForm = $(this).parents('form');
						for(var idx = 0; idx < files.length; idx++) {
							summernoteUploadImage(files[idx], $(this));
						}
					}
				}
			});
			
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
		
		//initArticleForm(modal);
		
		var $modal = $(modal);
		$modal.find('.modal-body .modal-spinner').show();
		
		$modal.find('.modal-body').load(actionUrl, function() {
			//$modal.find('.modal-title').text(title);
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
		
	});
	
	// 보기 Click
	$(document).on('click','.btnView', function(e) {
		e.preventDefault();
		
		var actionUrl = $(this).attr('href');
		var modal = $(this).data('target');
		
		initArticleForm(modal);
		modalShow(actionUrl,'', modal);
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
	
	// 게시글 삭제
	$(document).on('click', '.btnDeleteArticle', function(e) {
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
					$('#boardArticleModal').modal('hide');
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
		
	var files;
	var fileInput;
	
	$(document).on('change', "input[type=file]",  function () {
        fileInput = document.getElementById("atchFile");
        files = fileInput.files;
        
        console.log(files);
        
        var file;
        for (var i = 0; i < files.length; i++) {
            file = files[i];
            console.log(file.name);
            var html = '<li class="list-group-item" id="idx_' + i+ '">' + file.name + '</li>';
            $('ul#file-preview').append(html);
        }

    });

	//공지 알림톡 발송
	sendAlimTalk = function(nttId) {
		var answer = confirm('모든 업체 담당자에게 알림톡이 발송됩니다. 발송하시겠습니까?');
		if (answer) {
			jsonResultAjax({
				url: CTX_ROOT + '/decms/embed/board/article/sendBoardArticleAlimTalk.json',
				method: 'post',
				data : {'nttId' : nttId},
				dataType: 'json',
				callback: function(result) {
					/*if(result.success) {
						alert('알림톡이 발송되었습니다!');
					}*/
				}
			});
		}
		
	}
	
	//첨부파일 미리보기 전체삭제
	deleteAllFile = function() {
		fileInput = document.getElementById("atchFile");
		fileInput.value = '';
		$('ul#file-preview').html('');
	}

}) ();