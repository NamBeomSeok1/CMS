(function() {
	// 저장
	$(document).on('submit','#brandForm',function(e){
		
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
					if(result.redirectUrl!=null){
						location.href= CTX_ROOT + result.redirectUrl;
					}
				}
			}
		});
		
	})	
	
	
	// 브랜드 로고 첨부파일 변경 및 선택 
	$(document).on('change', '#brandAtachFile', function(e) {
		var actionUrl = '/decms/fms/singleFileUpload.json';
		
		if(this.files && this.files[0]) {
			if ( /\.(jpe?g|png|gif)$/i.test(this.files[0].name) ) {
				var reader = new FileReader();
				
				reader.onload = function(e) {
					$('#brandLogo').attr('src', e.target.result);
				}
				reader.readAsDataURL(this.files[0]);
			}
		}
	});
	// 브랜드 컴퓨터 대표 이미지 첨부파일 변경 및 선택 
	$(document).on('change', '#brandDesktopFile', function(e) {
		var actionUrl = '/decms/fms/singleFileUpload.json';
		
		if(this.files && this.files[0]) {
			if ( /\.(jpe?g|png|gif)$/i.test(this.files[0].name) ) {
				var reader = new FileReader();
				
				reader.onload = function(e) {
					$('#brandDekImage').attr('src', e.target.result);
				}
				reader.readAsDataURL(this.files[0]);
			}
		}
	});
	// 브랜드 모바일 대표 이미지 첨부파일 변경 및 선택 
	$(document).on('change', '#brandMobileFile', function(e) {
		var actionUrl = '/decms/fms/singleFileUpload.json';
		
		if(this.files && this.files[0]) {
			if ( /\.(jpe?g|png|gif)$/i.test(this.files[0].name) ) {
				var reader = new FileReader();
				
				reader.onload = function(e) {
					$('#brandMobImage').attr('src', e.target.result);
				}
				reader.readAsDataURL(this.files[0]);
			}
		}
	});
	
	// 이벤트 PC 이미지 첨부파일 변경 및 선택 
	$(document).on('change', '#eventDesktopFile', function(e) {
		var actionUrl = '/decms/fms/singleFileUpload.json';
		
		if(this.files && this.files[0]) {
			if ( /\.(jpe?g|png|gif)$/i.test(this.files[0].name) ) {
				var reader = new FileReader();
				
				reader.onload = function(e) {
					$('#evtDekImage').attr('src', e.target.result);
				}
				reader.readAsDataURL(this.files[0]);
			}
		}
	});
	// 이벤트 PC 모바일 첨부파일 변경 및 선택 
	$(document).on('change', '#eventMobileFile', function(e) {
		var actionUrl = '/decms/fms/singleFileUpload.json';
		
		if(this.files && this.files[0]) {
			if ( /\.(jpe?g|png|gif)$/i.test(this.files[0].name) ) {
				var reader = new FileReader();
				
				reader.onload = function(e) {
					$('#evtMobImage').attr('src', e.target.result);
				}
				reader.readAsDataURL(this.files[0]);
			}
		}
	});
	
	// 브랜드 소개 이미지 첨부파일 변경 및 선택 
	$(document).on('change', '#brandIntroImage', function(e) {
		var actionUrl = '/decms/fms/singleFileUpload.json';
		var img = new Image();
		
		if(this.files && this.files[0]) {
			if ( /\.(jpe?g|png|gif)$/i.test(this.files[0].name) ) {
				var reader = new FileReader();
				
				reader.onload = function(e) {
					$('#brandIntroImg').attr('src', e.target.result);
					/*img.src=e.target.result;
					alert(img.height);
					if(img.height < 415 || img.height > 2000){
						alert("높이 사이즈가415~2000사이인 이미지를 올려주세요.");
						return false;
					}*/
					
				}
				reader.readAsDataURL(this.files[0]);
				
			}
		}
	});
/*	
	// 업체 선택
	$(document).on('click', '.btnSelectCmpny', function(e) {
		e.preventDefault();
		var cmpnyId = $(this).data('cmpnyId');
		var cmpnyNm = $(this).data('cmpnyNm');
		var policyCn = $(this).data('policyCn');
		
		$('#cmpnyId').val(cmpnyId);
		$('#cmpnyNm').val(cmpnyNm);
		$('#dlvyPolicySeCode1').attr('data-policy-cn', policyCn);
		$('#cmpnyListModal').modal('hide');
		
		// goodsForm.js
		getGoodsFormBrandList(cmpnyId);
		
		// 제휴사 목록
		getPrtnrCmpnyList(cmpnyId);
		
	});
	
	
	// 업체검색 Click
	$(document).on('click', '.btnSearchCmpny', function(e) {
		e.preventDefault();
		var $modal = $($(this).data('target'));
		
		$modal.modal('show');
		
		$modal.on('shown.bs.modal', function(e) {
			grid.refreshLayout();
		});
	});*/
	
	checkMaxLength = function(input) {
		if (input.value.length >= 10) {
			alert('10자 이상 입력할 수 없습니다.');
			input.value = input.value.substr(0, 9);
		}
	}
	
})();	