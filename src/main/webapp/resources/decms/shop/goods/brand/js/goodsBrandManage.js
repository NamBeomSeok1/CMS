(function() {
	const grid = new tui.Grid({
		el: document.getElementById('data-brand-grid'),
		rowHeight: 'auto',
		bodyHeight: 450,
		columns: [
			{ header: '로고', name: 'brandImageThumbPath', width: 100, align: 'center',
				formatter:function(item) {
					if(!isEmpty(item.value)) {
						return '<img src="' + item.value + '" alt="' + item.row.cmpnyNm + '" style="height:30px;"/>';
					}else {
						return '-';
					}
				}
			},
			{ header: '브랜드명', name: 'brandNm', width: 100, align: 'center'},
			{ header: '관리', name: 'brandId', width: 100, align: 'center',
				formatter: function(item) {
					return 	'<a href="' + CTX_ROOT + '/decms/shop/goods/goodsBrand.json?brandId=' + item.row.brandId + '" class="btn btn-success btn-sm mr-1 btnBrandModify" data-brand-id="' + item.row.brandId + '" title="수정">' 
					+ 		'<i class="fas fa-edit"></i></a>' 
					+ 	'<a href="' + CTX_ROOT + '/decms/shop/goods/deleteGoodsBrand.json?brandId=' + item.row.brandId + '" class="btn btn-danger btn-sm btnBrandDelete" title="삭제">' 
					+ 		'<i class="fas fa-trash"></i></a>';
				}
			},
		],
		columnOptions: {
			resizable:true
		}
	});
	tui.Grid.applyTheme('striped');
	
	function getDataList() {
		var $form = $('#brandSearchForm');
		var actionUrl = $form.attr('action');
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
	}
	
	// 저장
	function saveBrand($form) {
		var cmpnyId = $('#registForm').find('#cmpnyId').val();
		/*if(isEmpty(cmpnyId)) {
			bootbox.alert({ title: '확인', message: '업체를 선택 후 등록하세요', size: 'small' }); 
		}*/
		$form.find('[name=cmpnyId]').val(cmpnyId);

		var actionUrl = $form.attr('action');
		var method = $form.attr('method');
		
		$form.ajaxSubmit({
			url: actionUrl,
			type: method,
			dataType: 'json',
			success: function(result) {
				console.log(result);
				if(result.message) {
					bootbox.alert({ title: '확인', message: result.message, size: 'small' }); 
				}
				if(result.success) {
					//clearBrandForm();
					closeBrandRegistForm();
					getDataList(1);
				}
			}
		});
	}
	
	function getBrandInfo(actionUrl) {
		var $form = $('#brandForm');
		
		$.ajax({
			url: actionUrl,
			type: 'get',
			dataType: 'json',
			success: function(result) {
				if(result.message) {
					bootbox.alert({ title: '확인', message: result.message, size: 'small' }); 
				}
				
				if(result.success) {
					//console.log(result);
					var obj = result.data.goodsBrand;
					$form.find('[name=brandId]').val(obj.brandId);
					$form.find('[name=cmpnyId]').val(obj.cmpnyId);
					$form.find('[name=brandNm]').val(obj.brandNm);
					$form.find('[name=brandImagePath]').val(obj.brandImagePath);
					$form.find('[name=brandImageThumbPath]').val(obj.brandImageThumbPath);
					$form.find('#brandImage').attr('src', obj.brandImagePath);
					
					if(!isEmpty(obj.brandId)) { // 수정
						$('.brand-title').text('브랜드 수정');
						$form.find('[type=submit]').html('<i class="fas fa-save"></i> 수정');
					}
					
					// PC
					var dekBrandImageList = result.data.goodsBrand.dekBrandImageList;
					$('#brandDesktopFileList').html('');
					dekBrandImageList.forEach(function(item) {
						$('#brandDesktopFileList').append('<div class="brand-image-item"><img src="' + item.brandImageThumbPath + '"/>'
								+ '<a href="' + item.brandImagePath + '" class="btn btn-outline-secondary btn-sm ml-2" target="_blank" title="보기"><i class="fas fa-eye"></i></a>'
								+ '<a href="' + CTX_ROOT + '/decms/shop/goods/deleteGoodsBrandImage.json?brandImageNo=' + item.brandImageNo +'" class="btn btn-sm btn-outline-danger btnRemoveBrandImage ml-1">'
								+ 	'<i class="fas fa-trash"></i></a>'
								+ '</div><hr class="sm"/>');
					});
					// Mobile
					var mobBrandImageList = result.data.goodsBrand.mobBrandImageList;
					$('#brandMobileFileList').html('');
					mobBrandImageList.forEach(function(item) {
						$('#brandMobileFileList').append('<div class="brand-image-item"><img src="' + item.brandImageThumbPath + '"/>'
								+ '<a href="' + item.brandImagePath + '" class="btn btn-outline-secondary btn-sm ml-2" target="_blank" title="삭제"><i class="fas fa-eye"></i></a>'
								+ '<a href="' + CTX_ROOT + '/decms/shop/goods/deleteGoodsBrandImage.json?brandImageNo=' + item.brandImageNo +'" class="btn btn-sm btn-outline-danger btnRemoveBrandImage ml-1">'
								+ 	'<i class="fas fa-trash"></i></a>'
								+ '</div><hr class="sm"/>');
						
					});
				}
			}
		});
	}
	
	function clearBrandForm() {
		var $form = $('#brandForm');
		$form.find('[name=brandId]').val('');
		$form.find('[name=cmpnyId]').val($('#brandSearchForm').find('[name=searchCmpnyId]').val());
		$form.find('[name=brandNm]').val('');
		$form.find('[name=brandImagePath]').val('');
		$form.find('[name=brandImageThumbPath]').val('');
		$form.find('#brandImage').attr('src', '');
		$form.find('[type=file]').val('');
		
		$('.brand-title').text('브랜드 등록');
		$form.find('[type=submit]').html('<i class="fas fa-save"></i> 저장');
		
		$form.find('[name=brandNm]').focus();
		
		$form.find('#brandDesktopFileList').html('');
		$form.find('#brandMobileFileList').html('');
		
	}
	
	function deleteGoodsBrand(actionUrl) {
		$.ajax({
			url: actionUrl,
			type: 'post',
			dataType: 'json',
			success: function(result) {
				if(result.message) {
					bootbox.alert({ title: '확인', message: result.message, size: 'small' }); 
				}
				
				if(result.success) {
					getDataList();
				}
			}
		});
	}
	
	// 작성폼 닫기
	function closeBrandRegistForm() {
		clearBrandForm();
		$('#brand-regist-form').hide();
	}

	// 브랜드 로고 첨부파일 변경 및 선택 
	$(document).on('change', '#brandAtachFile', function(e) {
		var actionUrl = '/decms/fms/singleFileUpload.json';
		
		if(this.files && this.files[0]) {
			if ( /\.(jpe?g|png|gif)$/i.test(this.files[0].name) ) {
				var reader = new FileReader();
				
				reader.onload = function(e) {
					$('#brandImage').attr('src', e.target.result);
				}
				reader.readAsDataURL(this.files[0]);
			}
		}
	});
	
	// 브랜드관리 Click
	$(document).on('click', '.btnBrandManage', function(e) {
		e.preventDefault();
		var cmpnyId = $('#cmpnyId').val();
		/*if(isEmpty(cmpnyId)) {
			bootbox.alert({ title: '확인', message: '업체를 선택 후 등록하세요', size: 'small' }); 
		}else {*/
			var $modal = $($(this).data('target'));
			
			$modal.modal('show');
			
			$modal.on('shown.bs.modal', function(e) {
				grid.refreshLayout();

				var cmpnyId = $('#registForm').find('#cmpnyId').val();
				$('#brandSearchForm').find('[name=searchCmpnyId]').val(cmpnyId);

				getDataList();
			}).on('hide.bs.modal', function(e) {
				grid.clear();
				clearBrandForm();
				
				// goodsForm.js
				getGoodsFormBrandList(cmpnyId);
			});
	/*	}*/
	});
	
	// 브랜드 이미지 삭제
	$(document).on('click', '.btnRemoveBrandImage', function(e) {
		e.preventDefault();
		var actionUrl = $(this).attr('href');
		var $self = $(this);
		
		bootbox.confirm({
			title: '삭제확인',
			message: '삭제하시겠습니까?',
			callback: function(result) {
				if(result) {
					modooAjax({
						url: actionUrl,
						method: 'post',
						callback: function(result) { 
							$self.parents('.brand-image-item').remove();
						}
					});
				}
			}
		});
	});
	
	// 작성폼 닫기 Click
	$(document).on('click', '.btnBrandRegistClose', function(e) {
		closeBrandRegistForm();
	});
	
	// 수정 Click
	$(document).on('click', '.btnBrandModify', function(e) {
		e.preventDefault();
		$('#brand-regist-form').show();
		var actionUrl = $(this).attr('href');
		getBrandInfo(actionUrl);
	});
	
	// 브랜드 추가
	$(document).on('click', '.btnAddBrand', function(e) {
		e.preventDefault();
		
		$('#brand-regist-form').show();
		
		clearBrandForm();
	});
	
	// 브랜드 삭제 Click
	$(document).on('click', '.btnBrandDelete', function(e) {
		e.preventDefault();
		var actionUrl = $(this).attr('href');
		
		bootbox.confirm({
			title: '삭제확인',
			message: '기존 브랜드 선택된 상품은 모두 브랜드 미선택 처리합니다.<br/>삭제하시겠습니까?',
			callback: function(result) {
				if(result) {
					deleteGoodsBrand(actionUrl);
				}
			}
		});
	});
	
	// 브랜드 검색 Submit
	$(document).on('submit', '#brandSearchForm', function(e) {
		e.preventDefault();
		getDataList(1);
	});
	
	
	// 브랜드 저장 Submit
	$(document).on('submit', '#brandForm', function(e) {
		e.preventDefault();
		saveBrand($(this));
	});
})();	