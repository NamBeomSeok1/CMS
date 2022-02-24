if (!String.prototype.trim) {
  String.prototype.trim = function () {
    return this.replace(/^[\s\uFEFF\xA0]+|[\s\uFEFF\xA0]+$/g, '');
  };
}

//숫자 3자리 콤마
modooNumberFormat = function(numberStr) {
	return numberStr.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",");	
};

$(document).on('click', '[data-event]', function(e) {
	e.preventDefault();
	var title = $(this).data('title') || '확인';
	var message = $(this).data('message');
	var actionUrl = $(this).attr('href');

	switch($(this).data('event')) {
		case 'alert':
			if(!message) {
				console.log('메시지가 없습니다.');
				return false;
			}
			bootbox.alert({ title: title, message: message, size: 'small', 
				callback: function(result) {
					if(!isEmpty(actionUrl)) location.href = actionUrl;
				}
			}); 
			break;
		case 'confirm':
			if(!message) {
				console.log('메시지가 없습니다.');
				return false;
			}
			bootbox.confirm({
				title: title,
				message: message,
				callback: function(result) {
					if(result) {
						if(!isEmpty(actionUrl)) location.href = actionUrl;
					}
				}
			});
			break;
		case 'upload':
			var actionUrl = CTX_ROOT + '/decms/fms/singleFileUpload.json';
			if(isEmpty($(this).data('targetUrl')) || isEmpty($(this).data('targetImg'))) {
				console.log('"target"이 없습니다.');
				return false;
			}
			var $targetUrl = $($(this).data('targetUrl'));
			var $targetImg = $($(this).data('targetImg'));
			bootbox.dialog({
				title: title,
				message: '<form id="dialogFileUploadForm" name="dialogFileUploadForm" method="post" enctype="multipart/form-data" action="'+actionUrl+'">'
						+'	<input type="hidden" id="dialog-img-url" value=""/>'
						+'	<input type="file" id="dialogAtchFile" name="atchFile" class="form-control"/>'
						+'	<div class="dialog-contents"></div>'
						+'</form>',
				buttons: {
					close: {
						label: '닫기',
						className: 'btn-secondary btn-sm',
						callback: function(){
							this.modal('hide');
						}
					},
					ok: {
						label: '적용',
						className: 'btn-primary btn-sm',
						callback: function(){
							var url = $('#dialogFileUploadForm').find('#dialog-img-url').val();
							$targetUrl.val(url);
							$targetImg.html('<img src="' + url + '" style="max-width:100%; max-height:100%;" />');
							this.modal('hide');
						}
					}
				}
			});
			break;
	}
	
});

//파일 업로드
$(document).on('change', '#dialogAtchFile', function(e) {
	e.preventDefault();

	var $form = $(this).parents('form');
	var actionUrl = $form.attr('action');
	var method = $form.attr('method');
	
	var file = $(this).val();

	if(!isEmpty(file)) {
		
		$form.ajaxSubmit({
			url: actionUrl,
			type: method,
			dataType: 'json',
			success: function(result) {
				if(result.message) {
					bootbox.alert({ title: '확인', message: result.message, size: 'small' }); 
				}
				
				if(result.success) {
					$form.find('#dialog-img-url').val(result.data.fileUrl);
					$form.find('.dialog-contents').html('<img src="' + result.data.fileUrl + '" style="max-width:100%;" />');
				}
			}
		});
	}
});

// Number 처리
$(document).on('keyup keypress', '.inputNumber', function(e) {
	var verified = (e.which == 8 || e.which == undefined || e.which == 0) ? null : String.fromCharCode(e.which).match(/[^0-9-]/);
    if (verified) {e.preventDefault();}
});

/** funcitons **/
// Empty 여부
var isEmpty = function(x) {
	if( (typeof x) == 'undefined') {
		return true;
	}else if( (typeof x) == 'number') {
		return (
			(x == null) ||
			(x == false) ||
			(x.length == 0) ||
			(x == "") ||
			(!/[^\s]/.test(x)) ||
			(/^\s*$/.test(x))
		);
	}else if ((typeof x) == 'string') {
		return (
			(x == null) ||
			(x == false) ||
			(x.length == 0) ||
			(x == "") ||
			(x.replace(/\s/g,"") == "") ||
			(!/[^\s]/.test(x)) ||
			(/^\s*$/.test(x))
		);
	}else if((typeof x) == 'object') {
		return (
			(x == null)
		)
	}
}

// 팝업
popupWindow = function(url, title, w, h) {
	var screenX = typeof window.screenX != 'undefined' ? window.screenX : window.screenLeft;
	var	screenY = typeof window.screenY != 'undefined' ? window.screenY : window.screenTop;
    var left = Math.round((window.screen.width/2)-(w/2)) + screenX;
    var top = Math.round((window.screen.height/2)-(h/2)) + screenY;
    var popup =window.open(url, title, 'toolbar=no, location=no, directories=no, status=no, '
            + 'menubar=no, scrollbars=yes, resizable=yes, copyhistory=no, width=' + w 
            + ', height=' + h + ', top=' + top + ', left=' + left);

    popup.focus();
};
    


/** Validation Error 발생 eleemnt에 focus 이동 */
validErrorFocus = function(list) {
	if(list != null) {
		list.forEach(function(item, index) {
			if(item.param != null) {
				var $el = $('[name=' + item.param +']').first();
				$el.addClass('required');
				
				if(index == 0) {
					$('html,body').animate({scrollTop: $el.offset().top-30}, 200, function() {
						$el.focus();
					});
				}
			}

		});
	}
};

/** 첨부파일  */
// 첨부파일 삭제
deleteAtchFile = function($el, actionUrl) {
	$.ajax({
		url: actionUrl,
		type: 'post',
		dataType: 'json',
		success: function(result) {
			if(result.message) {
				bootbox.alert({ title: '확인', message: result.message, size: 'small' }); 
			}
			
			if(result.success) {
				$el.parents('tr').remove();
			}
		}
	});
	
};

// 첨부파일 삭제 Click
$(document).on('click', '.atch_delete', function(e) {
	e.preventDefault();
	var $self = $(this);
	var actionUrl = $(this).attr('href');
	
	bootbox.confirm({
		title: '삭제확인',
		message: '삭제하시겠습니까?',
		callback: function(result) {
			if(result) {
				deleteAtchFile($self, actionUrl);
			}
		}
	});
});

/** Web Editor: summernote **/
initWebEditor = function() {
	$('.summernote').summernote({
		lang: 'ko-KR',
		tabsize:2,
		height:300,
		dialogsInBody: true,
		toolbar: [
			['style', ['style']],
			['font', ['bold', 'underline', 'clear']],
			['fontname', ['fontname']],
			['color', ['color']],
			['para', ['ul', 'ol', 'paragraph']],
			['table', ['table']],
			['view', ['fullscreen', 'codeview', 'help']],
		]
	});
	
};
initWebEditor();


/** bootbox **/
bootbox.setDefaults({
	locale: 'ko'
});

/** menu active **/
if($('#accordionSidebar').find('.collapse-item').length > 0) {
	
	var params = {};
	window.location.search.replace(/[?&]+([^=&]+)=([^&]*)/gi, function(str, key, value) { params[key] = value; });
	
	var $menu = $('[data-menu-id=' + params.menuId +']');
	$menu.addClass('active');
	var collapseId = $menu.parents('.collapse').attr('id');
	
	$menu.parents('.collapse').collapse('show');
}

/** 모달 Show **/
modalShow = function(actionUrl, title, modal) {
	var $modal = $(modal);
	$modal.find('.modal-body .modal-spinner').show();
	
	$modal.on('hidden.bs.modal', function(e) {
		$(this).find('.modal-body .modal-spinner').hide();
		$(this).find('.modal-body').html('');
	});
	
	$modal.find('.modal-body').load(actionUrl, function() {
		$modal.find('.modal-title').text(title);
		$modal.modal('show');
	});
	
};

/** Ajax : JsonResult **/
modooAjax = function(option) {
	var actionUrl = option.url;
	var reqMethod = option.method || 'get';
	var reqParams = option.param;
	var formData = option.formData;
	var callback = option.callback;
	var fail = option.fail;
	
	axios({
		method: reqMethod,
		url: actionUrl,
		params: reqParams,
		data: formData
	}).then(function (res) {
		var result = res.data;
		if(result.message) {
			if(typeof(bootbox) != 'undefined') {
				bootbox.alert({ title: '확인', message: result.message, size: 'small',
					callback: function() {
						if(result.redirectUrl) {
							location.href = CTX_ROOT + result.redirectUrl;
						}
					}
				}); 
			}else {
				alert(result.message);
				if(result.returnUrl) {
					location.href = CTX_ROOT + result.returnUrl;
				}
			}
		}
		if(result.success) {
			callback(res.data);
		}
	}).catch(function (err) {
		console.log(err);
		fail(err);
	});
};

/** Ajax : json */
jsonResultAjax = function(option) {
	var actionUrl = option.url;
	var ajaxMethod = option.method;
	var ajaxType = option.type;
	var ajaxData = option.data;
	var callback = option.callback;
	var formData = option.formData;
	
	var cType = 'application/x-www-form-urlencoded; charset=UTF-8';
	if(!isEmpty(option.contentType)) {
		cType = option.contentType;
	}
	var pData = true;

	if(!isEmpty(formData)) {
		cType = false;
		pData = false;
		
		ajaxData = formData;
	}
	
	if(!isEmpty(ajaxType)) {
		ajaxMethod = ajaxType;
	}
	
	$.ajax({
		url: actionUrl,
		method: ajaxMethod,
		data: ajaxData,
		dataType: 'json',
		cache: false,
		contentType: cType,
		processData: pData,
		beforeSend: function() {
			$('.loading').show();
		},
		success: function(result) {
			$('.loading').hide();
			if(result.message) {
				if(typeof(bootbox) != 'undefined') {
					bootbox.alert({ title: '확인', message: result.message, size: 'small',
						callback: function() {
							if(result.redirectUrl) {
								location.href = CTX_ROOT + result.redirectUrl;
							}
						}
					}); 
				}else {
					alert(result.message);
					if(result.returnUrl) {
						location.href = CTX_ROOT + result.returnUrl;
					}
				}
			}
			
			if(result.returnUrl) {
				location.href = CTX_ROOT + result.returnUrl;
			}else {
				callback(result);
			}
		},
		error: function(request, status, err) {
			debug(request);
			debug(status);
			debug(err);
		}
	});
};

/**
 * 에디터 이미지 업로드
 * @param image
 * @param $editor
 */
summernoteUploadImage = function(image, $editor) {
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
};

(function($,undefined){
	$.fn.serializeObject = function(){
		var obj = {};

		$.each( this.serializeArray(), function(i,o){
		  var n = o.name,
			v = o.value;

			obj[n] = obj[n] === undefined ? v
			  : $.isArray( obj[n] ) ? obj[n].concat( v )
			  : [ obj[n], v ];
		});

		return obj;
	};

})(jQuery);