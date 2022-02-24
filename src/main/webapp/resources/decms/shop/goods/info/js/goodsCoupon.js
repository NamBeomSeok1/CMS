(function() {
	
	const grid = new tui.Grid({
		el: document.getElementById('data-coupon-grid'),
		bodyHeight: 300,
		rowHeight: 'auto',
		rowHeaders: ['checkbox'],
		columns: [
			{ header: '쿠폰번호', name: 'couponNo', width: 260, align: 'center', 
				editor: {
					type:'text',
					options: {
						maxLength: 60
					}
				} 
			},
			{ header: '판매여부', name: 'sleAt', width: 80, align: 'center',
				formatter: function(item) {
					if(item.value == 'Y') {
						return '<span class="badge badge-secondary">판매완료</span>';
					}else if(item.value == 'N') {
						return '<span class="badge badge-success">판매가능</span>';
					}
				}
			},
		],
		columnOptions: {
			resizable:true
		}
	});
	
	var beforeCouponNo = '';
	
	grid.on('editingStart', function(ev) {
		var item = grid.getRow(ev.rowKey);
		beforeCouponNo = item.couponNo;
	});
	grid.on('editingFinish', function(ev) {
		var item = grid.getRow(ev.rowKey);
		
		if(beforeCouponNo != item.couponNo) {
			if(item.goodsCouponNo) { //수정
				jsonResultAjax({
					url: CTX_ROOT + '/decms/shop/goods/coupon/editCouponNo.json',
					type: 'post',
					data: {	'goodsId': $('#goodsId').val(),
							'goodsCouponNo' : item.goodsCouponNo, 
							'couponNo': item.couponNo
					},
					dataType: 'json',
					callback: function(result) {
						if(result.eventCode == 'RECOVERY') {
							grid.setValue(ev.rowKey, 'couponNo', beforeCouponNo, false);
						}
					}
				});
			}else { //신규
				jsonResultAjax({
					url: CTX_ROOT + '/decms/shop/goods/coupon/addCouponItem.json',
					type: 'post',
					data: {	'goodsId': $('#goodsId').val(),
							'couponNo': item.couponNo
					},
					dataType: 'json',
					callback: function(result) {
						getDataList(0);
					}
				});
			}
		}
	});
	
	tui.Grid.applyTheme('striped');
	
	// 데이터 목록
	function getDataList() {
		jsonResultAjax({
			url: CTX_ROOT + '/decms/shop/goods/couponList.json',
			method: 'get',
			data: {goodsId: $('#goodsId').val()},
			callback: function(result) {
				if(result.success) {
					if(result.data.list) {
						grid.resetData(result.data.list);
						grid.refreshLayout();
					}
				}
			}
		});
	}
	
	// 선택 쿠폰 삭제
	function deleteCouponList(couponJsonList) {
		jsonResultAjax({
			url: CTX_ROOT + '/decms/shop/goods/coupon/deleteCouponList.json',
			type: 'post',
			contentType: 'application/json',
			data: couponJsonList,
			callback: function(result) {
				if(result.success) {
					getDataList(0);
				}
			}
		});
		
	}

	$(document).ready(function() {
		var goodsId = $('#goodsId').val();
		var goodsKndCode = $('input[name=goodsKndCode]:checked').val();
		if(!isEmpty(goodsId) && goodsKndCode == 'CPN') {
			getDataList();
		}

	});
	
	// 쿠폰 엑셀파일 업로드 Click
	$(document).on('click', '#btnUploadCouponExcel', function(e) {
		e.preventDefault();
		
		var actionUrl = CTX_ROOT + '/decms/shop/goods/couponExcelUpload.json'; //$(this).attr('src');
		var goodsId = $('#goodsId').val();

		bootbox.dialog({
			title: '쿠폰 엑셀파일 업로드',
			message: 	'<form id="couponExcelForm" action="' + actionUrl + '" method="post">'
					+	'	<input type="hidden" name="goodsId" value="' + goodsId + '">'
					+	'	<div class="custom-file">'
					+	'		<input type="file" id="couponExcelFile" name="couponExcelFile" class="custom-file-input"/>'
					+	'		<label class="custom-file-label" for="couponExcelFile">파일찾기</label>'
					+	'	</div>'
					+	'</form>',
			buttons: {
				cancel: {
					label: '취소',
					className: 'btn-secondary btn-sm',
					callback: function() { }
				},
				ok: {
					label: '등록',
					className: 'btn-primary btn-sm',
					callback: function() {
						$('#couponExcelForm').submit();
					}
				}
			}
		});
	});
	
	// 쿠폰 엑셀 전송
	$(document).on('submit', '#couponExcelForm', function(e) {
		e.preventDefault();
		var actionUrl = $(this).attr('action');

		$(this).ajaxSubmit({
			type: 'post',
			url: actionUrl,
			enctype: 'multipart/form-data',
			datatype: 'json',
			beforeSend: function() {
				$('.loading').show();
			},
			success: function(result) {
				$('.loading').hide();

				if(result.message) {
					bootbox.alert({ title: '확인', message: result.message, size: 'small',
						callback: function() {
							if(result.redirectUrl) {
								location.href = CTX_ROOT + result.redirectUrl;
							}
						}
					}); 
				}else {
					var resultCnt = result.resultCnt;
					getDataList();
				}

			}
		});
	});
	
	// 파일찾기
	$(document).on('change', '#couponExcelFile', function(e) {
		if(e.target.files.length > 0){
			var fileName =  e.target.files[0].name; //$(this).val();
			$(this).parent().find('.custom-file-label').text(fileName);
		}else {
			$(this).parent().find('.custom-file-label').text('파일찾기');
		}
	});
	
	// 쿠폰삭제 click
	$(document).on('click', '#btnCouponDelete', function(e) {
		e.preventDefault();
		var rows = grid.getCheckedRows();
		if(rows.length == 0) {
			bootbox.alert({ title: '삭제확인', message: '삭제 할 쿠폰을 선택하세요', size: 'small'});
			return false;
		}
		bootbox.confirm({
			title: '쿠폰삭제',
			message: '선택한 쿠폰을 삭제하시겠습니까?',
			callback: function(result) {
				if(result) {
					var couponJsonList = {
						goodsId: $('#goodsId').val(),
						searchGoodsCouponNoList : []
					}
					
					rows.forEach(function(item) {
						couponJsonList.searchGoodsCouponNoList.push(item.goodsCouponNo);
					});
					
					deleteCouponList(JSON.stringify(couponJsonList));
				}
			}
		});
	});

	// 쿠폰 추가
	$(document).on('click', '#btnCouponAppend', function(e) {
		var newRow = [{couponNo:'',sleAt:''}];
		grid.appendRow(newRow,{
			focus:true
		});
	});
	
	
})();