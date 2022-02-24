(function() {

	$('#datepicker-opnngDe').datetimepicker({
		locale: 'ko',
		format: 'YYYY-MM-DD'
	});
	
	function submitForm($form) {
		var actionUrl = $(this).attr('action');
		var method = $(this).attr('method');
		
		$('#chargerEmail').val($('#chargerEmail').val().trim());
		
		$form.ajaxSubmit({
			url: actionUrl,
			type: method,
			dataType: 'json',
			success: function(result) {
				if(result.message) {
					bootbox.alert({ title: '확인', message: result.message, size: 'small',
						callback: function() {
							if(result.success) {
								if(!isEmpty(result.redirectUrl)) {
									location.href = CTX_ROOT + result.redirectUrl;
								}
							}
						}
					}); 
				}
			}
		});
	}
	
	function initUserPassword() {
		var esntlId = $('#cmpnyUserEsntlId').val();
		var mberId = $('#cmpnyMberId').val();
		var password = $('[name=changePassword]').val();
		var actionUrl = CTX_ROOT + '/decms/shop/cmpny/initPasswordUser.json';
		
		jsonResultAjax({
			url: actionUrl,
			type: 'post',
			data: {cmpnyUserEsntlId: esntlId, cmpnyMberId: mberId, cmpnyMberPassword: password},
			dataType: 'json',
			callback: function(result) {
				/*
				if(result.message) {
					bootbox.alert({ title: '확인', message: result.message, size: 'small' }); 
				}
				*/
				
				$('[name=changePassword]').val('');
			}
		});
	}
	
	function changeSttus(cmpnyId, sttusCode) {
		var actionUrl = CTX_ROOT + '/decms/shop/cmpny/changeSttusCode.json';

		jsonResultAjax({
			url: actionUrl,
			type: 'post',
			data: {'cmpnyId': cmpnyId, 'opnngSttusCode': sttusCode},
			//dataType: 'json',
			callback: function(result) {
				/*
				if(result.message) {
					bootbox.alert({ title: '확인', message: result.message, size: 'small',
						callback: function() {
							location.reload(true);
						}
					}); 
				}
				*/
			}
		});
	}
	
	function deleteCmpny(actionUrl) {
		jsonResultAjax({
			url: actionUrl,
			type: 'post',
			//dataType: 'json',
			callback: function(result) {
				/*
				if(result.message) {
					bootbox.alert({ title: '확인', message: result.message, size: 'small',
						callback: function() {
							if(!isEmpty(result.redirectUrl)) {
								location.href = result.redirectUrl;
							}
						}
					}); 
				}
				*/
			}
		});
	}
	
	// 저장 Submit
	$(document).on('submit', '[name=cmpnyForm]', function(e) {
		e.preventDefault();
		var $self = $(this);
		
		var oldEsntlId = $('[name=oldEsntlId]').val();
		var cmpnyUserEsntlId = $('#cmpnyUserEsntlId').val();
		if(!isEmpty(oldEsntlId) && cmpnyUserEsntlId != oldEsntlId) {
			bootbox.confirm({
				title: '확인',
				message: '사용자ID 가 변경되어 이전 사용자ID와 업체정보 연결이 초기화 됩니다.<br/> 진행하시겠습니까?',
				callback: function(result) {
					if(result) {
						submitForm($self);
					}
				}
			});
		}else {
			submitForm($self);
		}
		
	});
	
	// 택배사 선택 radio
	$(document).on('change','.radioHdryId', function(e) {
		if($(this).prop('checked')) {
			var hdryNm = $(this).parent().find('label').text();
			var svcHdryNm = $('#svcHdryNm').val();
			if(isEmpty(svcHdryNm)) {
				$('#svcHdryNm').val(hdryNm);
			}else {
				if(confirm('교환/반품 택배사명을 ' + hdryNm + '으로  변경하시겠습니까?')) {
					$('#svcHdryNm').val(hdryNm);
				}
			}
		}
	});
	
	// 사용자ID Change
	$(document).on('change', '#cmpnyMberId', function(e) {
		$('#cmpnyMberPassword').attr('readonly', false);
		$('#cmpnyMberRePassword').attr('readonly', false);
		$('#cmpnyUserEsntlId').val(''); // 불러온 사용자고유ID 초기화
	});
	
	
	// 비밀번호 초기화 Click 
	$(document).on('click', '.btnInitPassword', function(e) {
		e.preventDefault();

		var password = $(this).parents('.input-group').find('[name=changePassword]').val();
		if(isEmpty(password)) {
			bootbox.alert({ title: '비밀번호 확인', message: '비밀번호를 입력하세요!', size: 'small' }); 
			return false;
		}else if(password.length < 8 || password.length > 20) {
			bootbox.alert({ title: '비밀번호 확인', message: '8자 이상 또는 20자 이하로 입력하세요!', size: 'small' }); 
			return false;
		}
		
		var userId = $('#cmpnyMberId').val();
		
		bootbox.confirm({
			title: '비밀번호 변경확인',
			message: userId + '사용자의 비밀번호를 초기화 합니다.<br/> 진행하시겠습니까?',
			callback: function(result) {
				if(result) {
					initUserPassword();
				}
			}
		});
	});
	
	// 상태변경 선택 Click 
	$(document).on('click', '.btnChangeSttus', function(e) {
		e.preventDefault();
		
		var cmpnyId = $('#cmpnyId').val();
		var sttusCode = $(this).data('code');
		var sttusText = $(this).text();
		var sttusMessage = sttusText + ' 상태로 변경하시겠습니까?';
		
		bootbox.confirm({
			title: '상태변경',
			message: sttusMessage,
			callback: function(result) {
				if(result) {
					changeSttus(cmpnyId, sttusCode);
				}
			}
		});
	});
	
	// 삭제 Click
	$(document).on('click', '.btnDelete', function(e) {
		e.preventDefault();
		
		var actionUrl = $(this).attr('href');
		bootbox.confirm({
			title: '삭제확인',
			message: '삭제하시겠습니까?',
			callback: function(result) {
				if(result) {
					deleteCmpny(actionUrl);
				}
			}
		});
	});

	// 제휴사 변경
	$(document).on('change', '.pcmapngAt', function(e) {
		var $self = $(this);
		var isChecked = $self.is(':checked');
		
		if(!isChecked) {
			bootbox.confirm({
				title: '제휴사변경',
				message: '제휴사 해제시 해상 상품은 자동으로 상품 목록에서 삭제됩니다.<br/>진행 하시겠습니까?',
				callback: function(result) {
					if(!result) {
						$self.prop('checked', true);
					}
				}
			});
		}
	});
	
	
	//---------------
	
	//비밀번호 변경 Click
	$(document).on('click', '#btnChangePwd', function(e) {
		e.preventDefault();
		var actionUrl = $(this).data('src');
		bootbox.dialog({
			title: '비밀번호 변경',
			message:	'<form id="changePasswordForm" action="' + actionUrl +'" method="post">' 
					+	'<div class="row mb-3">' 
					+	'	<div class="col-12">' 
					+	'		<div class="input-group input-group-sm">' 
					+	'			<div class="input-group-prepend"><span class="input-group-text" style="min-width:120px;">기존 비밀번호</span></div>' 
					+	'			<input type="password" name="oldPassword" class="form-control" autocomplete="one-time-code" value="" placeholder="기존 비밀번호를 입력하세요."/>' 
					+	'		</div>' 
					+	'	</div>' 
					+	'</div>' 
					+	'<div class="row">' 
					+	'	<div class="col-12 mb-1">' 
					+	'		<div class="input-group input-group-sm">' 
					+	'			<div class="input-group-prepend"><span class="input-group-text" style="min-width:120px;">새 비밀번호</span></div>' 
					+	'			<input type="password" name="password" class="form-control" autocomplete="one-time-code" value="" placeholder="새 비밀번호"/>' 
					+	'		</div>' 
					+	'	</div>' 
					+	'	<div class="col-12 mb-1">' 
					+	'		<div class="input-group input-group-sm">' 
					+	'			<div class="input-group-prepend"><span class="input-group-text" style="min-width:120px;">새 비밀번호 확인</span></div>' 
					+	'			<input type="password" name="repassword" class="form-control" autocomplete="one-time-code" value="" placeholder="새 비밀번호 확인"/>' 
					+	'		</div>' 
					+	'	</div>' 
					+	'</div>' 
					+ 	'</form>',
			buttons: {
				cancel: {
					label: '취소',
					className: 'btn-secondary btn-sm',
					callback: function() {
					}
				},
				ok: {
					label: '변경',
					className: 'btn-primary btn-sm',
					callback: function() {
						$('#changePasswordForm').submit();
					}
				}
			}
		});
		
	});
	
	// 새 비밀번호확인 엔터
	$(document).on('keydown', '[name=repassword]', function(e) {
		if(e.keyCode == 13) {
			$('#changePasswordForm').submit();
		}
	});
	
	// 비밀번호 변경 전송
	$(document).on('submit', '#changePasswordForm', function(e) {
		e.preventDefault();
		var actionUrl = $(this).attr('action');
		var formData = $(this).serialize();
		
		jsonResultAjax({
			url: actionUrl,
			type: 'post',
			data: formData,
			callback: function(result) {
				bootbox.hideAll();
			}
		});
		
	});
	
})();