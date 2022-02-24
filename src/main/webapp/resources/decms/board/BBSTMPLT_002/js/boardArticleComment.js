(function() {
	var commentList; //data list
	var $commentList; // #commentList
	
	var commentTemplate; // #commentTemplate
	var commentFormTemplate; // #commentFormTemplate
	
	// 댓글 폼 초기화
	clearCommentForm = function() {
		$('#commentForm').find('#commentCn').val('');
	};
	
	makeCommentItemTag = function(item) {
		var dateStr = moment(item.frstRegistPnttm).format('YYYY-MM-DD hh:mm:ss');
		var commentCn = item.commentCn.replace(/(?:\r\n|\r|\n)/g, '<br>');
		var html = commentTemplate.replace(/{NAME}/gi,item.wrterNm)
								.replace(/{DATE}/gi,dateStr)
								.replace(/{ID}/gi,item.commentId)
								.replace(/{CONTENT}/gi, commentCn);
		return html;
		//$commentList.append(html);
	};
	
	makeCommentFormTag = function(item) {
		var dateStr = moment(item.frstRegistPnttm).format('YYYY-MM-DD hh:mm:ss');
		var commentCn = item.commentCn; 
		var html = commentFormTemplate.replace(/{NAME}/gi,item.wrterNm)
								.replace(/{DATE}/gi,dateStr)
								.replace(/{ID}/gi,item.commentId)
								.replace(/{USER_ID}/gi,item.wrterId)
								.replace(/{CONTENT}/gi, commentCn);
		return html;
	};
	
	// 댓글 목록
	getCommentList = function(page) {
		var pageNo = isEmpty(page)? 1 : page;
		
		commentTemplate = $('#commentTemplate').html();
		commentFormTemplate = $('#commentFormTemplate').html();
		
		if($('#commentForm').length > 0) {
			var $form = $('#commentSearchForm');
			var actionUrl = $form.attr('action');
			var formData = $form.serialize();
			
			$commentList = $('#commentList');
			$commentList.html('');
			
			$.ajax({
				url: actionUrl,
				method: 'get',
				data: formData,
				dataType: 'json',
				success: function(result) {
					if(result.message) {
						bootbox.alert({ title: '확인', message: result.message, size: 'small' }); 
					}

					if(result.success) {
						var paginationObj = result.data.paginationInfo;
						$('#comment-tot').text(paginationObj.totalRecordCount);
						commentList = result.data.resultList;

						commentList.forEach(function(item) {
							var html = makeCommentItemTag(item);
							$commentList.append(html);
						});

					}
				}
				
			});
		}
	};
	
	
	//댓글 삭제
	deleteComment = function(actionUrl) {
		$.ajax({
			url: actionUrl,
			type: 'post',
			dataType: 'json',
			success: function(result) {
				if(result.message) {
					bootbox.alert({ title: '확인', message: result.message, size: 'small' }); 
				}
				
				if(result.success) {
					getCommentList(1);
				}
			}
		});
	};
	
	// 댓글 저장
	$(document).on('submit', '#commentForm', function(e) {
		e.preventDefault();
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
					clearCommentForm();
					getCommentList(1);
				}
			}
		});
	});
	
	// 댓글 삭제 Click
	$(document).on('click', '.btnDeleteComment', function(e) {
		e.preventDefault();
		var actionUrl = $(this).attr('href');
		
		bootbox.confirm({
			title: '삭제확인',
			message: '삭제하시겠습니까?',
			callback: function(result) {
				if(result) {
					deleteComment(actionUrl);
				}
			}
		});
	});

	// 댓글 수정 Click
	$(document).on('click', '.btnEditComment', function(e) {
		e.preventDefault();
		var $commentItem = $(this).parents('.comment-item');
		 $commentItem.find('.comment-content').hide();
		
		var commentId = $(this).data('id');
		var comment = $.grep(commentList, function(e){ return e.commentId == commentId; })[0];

		var html = makeCommentFormTag(comment);
		$commentItem.find('.card-body').append(html);

	});
	
	// 댓글 수정 취소 Click
	$(document).on('click', '.btnModifyCancel', function(e) {
		e.preventDefault();
		$(this).parents('.comment-item').find('.comment-content').show();
		$(this).parents('[name=commentModifyForm]').remove();
	});
	
	// 댓글 수정
	$(document).on('submit', '.comment-modify-form', function(e) {
		e.preventDefault();
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
					var comment = result.data.result;
					var html = makeCommentItemTag(comment);
					
					commentList.forEach(function(item) {
						if(item.commentId == comment.commentId) {
							item.wrterId = comment.wrterId;
							item.wrterNm = comment.wrterNm;
							item.commentCn = comment.commentCn;
							
							var $cItem = $(makeCommentItemTag(item));
							$('[data-comment-id='+item.commentId+']').html( $cItem.html() );
							
						}
					});
					
				}
			}
		});
	});

}) ();