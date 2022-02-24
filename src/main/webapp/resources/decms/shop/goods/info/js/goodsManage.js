(function() {
	
	var cmpnyList = [];
	var isAdmin =$('#isAdmin').val();
	const grid = new tui.Grid({
		el: document.getElementById('data-grid'),
		rowHeight: 'auto',
		rowHeaders: ['checkbox'],
		columns: [
			{ header: '상품코드', name: 'goodsId', width: 150, align: 'center',
				formatter: function(item) {
					if(item.row.registSttusCode == 'C' || item.row.registSttusCode == 'R'){
						return '<a href="/shop/goods/goodsView.do?goodsId=' + item.value + '" target="_blank">' + item.value + '</a>';
					}else{
						return '<span>' + item.value + '</span>';
					}

				}
			},
			{ header: '제휴사', name: 'prtnrNm', width: 60, align: 'center'},
			{ header: '종류', name: 'goodsKndCode', width: 60, align: 'center',
				formatter: function(item) {
					return (item.value == 'SBS')?'구독':'<span class="text-secondary">일반</span>';
				}
			},
			{ header: '업체명', name: 'cmpnyNm', width: 200, align: 'center'},
			{ header: '이미지', name: 'goodsTitleImagePath', width: 60, align: 'center',
				formatter: function(item) {
					return (item.value == '')?'':'<img src="' + item.value + '" alt="" style="height:100%; max-height:30px;"/>';
				}
			},
			{ header: '상품명/카테고리', name: 'goodsNm', align: 'center',
				formatter: function(item) {
					return item.value+'<br/><small class="text-secondary">'+item.row.goodsCtgryNm+'</small>';
				}
			},
			{ header: '품절여부', name: 'soldOutAt', width: 80, align: 'center', 
				formatter: function(item){
					if (item.value == 'Y') {
						return '<p style="color: red;">품절</p>';
					} else {
						return '<p>-</p>';
					}
				}},
			{ header: '모델명', name: 'modelNm', width: 100, align: 'center'},
			{ header: '공급사', name: 'makr', width: 110, align: 'center'},
			{ header: '베스트구독상품', name: 'bestOrdr', width: 100, align: 'center',
				formatter:function(item){
					if(item.value==1){
						return 'O';
					}else{
						return 'X';
					}
				}
			},
			{ header: '수수료율', name: 'goodsFeeRate', width: 110, align: 'center',
				formatter: function(item) {
				return item.value+'%';
				}
			},
			{ header: '판매가', name: 'goodsPc', width: 90, align: 'center', 
				formatter: function(item) {
					if (item.value) {
						return modooNumberFormat(item.value);
					} else {
						return 0;
					}
					
				}
			},
			{ header: '배송비', name: 'dlvyPc', width: 70, align: 'center', 
				formatter: function(item) {
					if (item.value) {
						return modooNumberFormat(item.value);
					} else {
						return 0;
					}
				}
			},
			{ header: '조회수', name: 'rdcnt', width: 50, align: 'center'},
			{ header: '판매수', name: 'sleCo', width: 50, align: 'center'},
			{ header: '등록상태', name: 'registSttusCode', width: 80, align: 'center',
				formatter: function(item) {
					if(item.value == 'R') {
						return '<span class="text-danger">등록대기</span>';
					}else if(item.value == 'C') {
						return '<span class="text-primary">등록완료</span>';
					}else if(item.value == 'E') {
						return '판매종료';
					}
				}
			},
			{ header: '관리', name: '', width: 80, align: 'center',
				formatter: function(item) {
					var res = '';
					 	res+='<a href="' + CTX_ROOT + '/decms/shop/goods/modifyGoods.do?goodsId=' + item.row.goodsId + '" class="btn btn-success btn-sm mr-1 btnModify" title="수정">'; 
						res+='<i class="fas fa-edit"></i></a>';
						res+='<a href="' + CTX_ROOT + '/decms/shop/goods/deleteGoods.json?goodsId=' + item.row.goodsId + '" class="btn btn-danger btn-sm btnDelete" title="삭제">' 
						res+='<i class="fas fa-trash"></i></a>';
						return res;
				}
			},
			/*
			{ header: '관리', name: '', width: 80, align: 'center',
				formatter: function(item) {
					var res = '';
					 	res+='<a href="' + CTX_ROOT + '/decms/shop/goods/modifyGoods.do?goodsId=' + item.row.goodsId + '" class="btn btn-success btn-sm mr-1 btnModify" title="수정">'; 
						res+='<i class="fas fa-edit"></i></a>';
						if(isAdmin=='Y'){
							res+= 	'<a href="' + CTX_ROOT + '/decms/shop/goods/deleteGoods.json?goodsId=' + item.row.goodsId + '" class="btn btn-danger btn-sm btnDelete" title="삭제">' 
							res+= 		'<i class="fas fa-trash"></i></a>';
						}
						return res;
				}
			},
			*/
		],
		columnOptions: {
			frozenCount: 1,
			frozenBorderWidth: 1,
			resizable:true
		}
	});
	
	// 상품 등록상태 일괄 변경
	changeCheckedGoodsState = function(act) {
		var action = {
			'C' : '등록 완료',
			'R' : '등록 대기',
			'E' : '판매 종료'
		};
	
		var rowKeys = grid.getCheckedRows();
		
		if (rowKeys.length == 0) {
			alert('선택한 상품이 없습니다.');
			return false;
		}
		
		var goodsIds = [];
		var msg = '<div>[주의] 아래 상품의 상태가 일괄적으로 <span style="color: red; font-weight: bold;">' + action[act] + '</span>로 변경됩니다. 변경하시겠습니까?</div><div><ol>';
		
		for (var i=0; i<rowKeys.length; i++) {
			goodsIds.push(grid.getData()[i].goodsId);
			var goodsNm = grid.getData()[i].goodsNm;
			var cmpnyNm = grid.getData()[i].cmpnyNm;
			msg += '<li>' + goodsNm + '(' + cmpnyNm + ')</li>';
		}
		
		msg += '</ol></div>';
		
		bootbox.confirm({
			title : '상품 일괄 수정',
			message: msg,
			buttons: {
				confirm: {
					label: '예',
					className: 'btn-danger'
				},
				cancel: {
					label: '아니오',
					className: 'btn-secondary'
				}
			},
			callback: function (result) {
				if (result) {
					var dataJson = {
						'goodsIds' : goodsIds,
						'action' : act
					};

					jsonResultAjax({
						url: CTX_ROOT + '/decms/shop/goods/modifyMultipleGoods.json',
						type: 'post',
						data: dataJson,
						dataType: 'json',
						callback: function(result) {
							getDataList(1);
						}
					});

				}
			}
		});
	}
	
	tui.Grid.applyTheme('striped');
	
	var isSearch = true;
	var pagination = grid.getPagination();
	var gridPageUnit = $('#pageUnit').val();
	if(!gridPageUnit) gridPageUnit = 10;
	
	var pagination = new tui.Pagination(document.getElementById('data-grid-pagination'), {
		totalItems: 10,
		itemsPerPage: gridPageUnit,
		visiblePages: 5,
		centerAlign: true
	});
	
	pagination.on('beforeMove', function(e) {
		if(isSearch == false) {
			getDataList(e.page);
		}
	});
	
	// 데이터 목록
	function getDataList(page) {
		var $form = $('#searchForm');
		var actionUrl = $form.attr('action');
		var method = $form.attr('method');
		if(isEmpty(page)) {
			//page = $form.find('#pageIndex').val(page);
		}else if(page > 0) {
			$form.find('#pageIndex').val(page);
		}

		//var formData = new FormData($form[0]);
		//var paramKeyValue = $form.serializeObject();
		var paramKeyValue = $form.serialize();
		
	
		jsonResultAjax({
			url: actionUrl,
			method: method,
			data: paramKeyValue,
			callback: function(result) {
				if(result.success) {
					var paginationObj = result.data.paginationInfo;
					
					//if(paginationObj.currentPageNo == 1) {
						pagination.setItemsPerPage(paginationObj.recordCountPerPage);
						pagination.reset(paginationObj.totalRecordCount);

						isSearch = true;
						pagination.movePageTo(paginationObj.currentPageNo);
						isSearch = false;
					//}
					grid.resetData(result.data.list);
				}
			}
		});
	};
	
	/** 삭제 **/
	function deleteGoods(actionUrl) {
		jsonResultAjax({
			url: actionUrl,
			type: 'post',
			dataType: 'json',
			callback: function(result) {
				//if(result.message) {
				//	bootbox.alert({ title: '확인', message: result.message, size: 'small' }); 
				//}
				
				if(result.success) {
					getDataList(1);
				}
			}
		});
	};
	
	// 업체 전체 목록
	function getCmpnyAllList() {
		jsonResultAjax({
			url: CTX_ROOT + '/decms/shop/cmpny/cmpnyAllList.json',
			type: 'post',
			dataType: 'json',
			callback: function(result) {
				//if(result.message) {
				//	bootbox.alert({ title: '확인', message: result.message, size: 'small' }); 
				//}
				
				if(result.success) {
					cmpnyList = result.data.list;
				}
			}
		});
	}
	
	// 카테고리 목록
	function getSubCategoryList(upperCateId, dp) {
		jsonResultAjax({
			url: CTX_ROOT + '/decms/shop/goods/goodsCtgryList.json',
			type: 'post',
			dataType: 'json',
			data: {searchUpperGoodsCtgryId: upperCateId},
			callback: function(result) {
				//if(result.message) {
				//	bootbox.alert({ title: '확인', message: result.message, size: 'small' }); 
				//}
				
				if(result.success) {
					if(dp == '1') {
						$('[data-dp=2]').html('<option value="">선택</option>');
						result.data.list.forEach(function(item) {
							$('<option value="'+item.goodsCtgryId+'">' + item.goodsCtgryNm + '</option>').appendTo('[data-dp=2]');
						});
					}else if(dp == '2') {
						$('[data-dp=3]').html('<option value="">선택</option>');
						result.data.list.forEach(function(item) {
							$('<option value="'+item.goodsCtgryId+'">' + item.goodsCtgryNm + '</option>').appendTo('[data-dp=3]');
						});
					}
				}
			}
		});
		
	}

	$(document).ready(function() {
		//getCmpnyAllList(); 
		getDataList();
	});

	// 검색 submit
	$(document).on('submit', '#searchForm', function(e) {
		e.preventDefault();
		
		getDataList(1);
		
	});
	
	// Tab Click
	$(document).on('click', '.goods-tab .nav-link', function(e) {
		e.preventDefault();
		$('.goods-tab .nav-link').removeClass('active');
		$(this).addClass('active');

		var code = $(this).data('code');
		
		$('#searchRegistSttusCode').val(code);
		
		getDataList(1);
		
	});
	
	// 수정 Click
	$(document).on('click', '.btnModify', function(e) {
		e.preventDefault();
		var formData = $('#searchForm').serialize();
		var actionUrl = $(this).attr('href');
		location.href = actionUrl + '&' + formData;
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
					deleteGoods(actionUrl);
				}
			}
		});
	});
	
	// 저장 Submit
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
	
	// 카테고리 선택 Change
	$(document).on('change', '.selectCategory', function(e) {
		var dp = $(this).data('dp');

		if(!isEmpty($(this).val())) {
			var upperCateId = $(this).val();
			if(dp == '1') {
				$('[data-dp=3]').html('<option value="">선택</option>');
				getSubCategoryList(upperCateId, dp);
			}else if(dp == '2') {
				getSubCategoryList(upperCateId, dp);
			}else if(dp == '3') {
				//getDataList(1);
			}
		}else {
			if(dp == '1') {
				$('[data-dp=3]').html('<option value="">선택</option>');
				$('[data-dp=2]').html('<option value="">선택</option>');
			}else if(dp == '2') {
				$('[data-dp=3]').html('<option value="">선택</option>');
			}
			
		}
	});
	
	//정렬순서 변경
	$(document).on('change', '#searchOrderField', function() {
		getDataList(1);
	});
	
	//정렬순서 변경 - 오름차순, 내림차순
	$(document).on('change', '#searchOrderField2', function() {
		getDataList(1);
	});
	
	//목록갯수
	$(document).on('change', '#pageUnit', function() {
		getDataList(1);
	});
	
	var cmpnyNm = '';
	var cmpnyId = '';
	
	//엑셀다운로드
	$(document).on('click', '.btnExcelDownload', function(e) {
		e.preventDefault();
		var actionUrl = $(this).attr('href');
		var formData = $('#searchForm').serialize();
		location.href = actionUrl + '?' + formData;
	});

	// 업체명 자동완성
	/*
	if($('#searchCmpnyNm').length > 0) {
		$('#searchCmpnyNm').autocomplete({
			minLength: 1,
			source : function(request, response) {
				jsonResultAjax({
					url: CTX_ROOT + '/decms/shop/cmpny/cmpnyAllList.json',
					type: 'post',
					//dataType: 'json',
					data: {cmpnyNm : request.term},
					callback: function(result) {
						//if(result.message) {
						//	console.log(result.message);
						//}
						
						if(result.success) {
							response(
								$.map(result.data.list, function(item) {
									return { 
										label: item.cmpnyNm,
										value: item.cmpnyId
									}
								})
							);
						}
					}
				});
			},
			change: function( event, ui ) {
				$cmpnyNm = $('#searchCmpnyNm');
				if(cmpnyNm != $cmpnyNm.val()) {
					$cmpnyNm.removeClass('cmpny-complete');
				}
			},
			close: function( event, ui) {
				//console.log(cmpnyNm,$('#searchCmpnyNm').val() );
				if(cmpnyNm != $('#searchCmpnyNm').val()) {
					$('#searchCmpnyNm').val('');
					$('#searchCmpnyId').val('');
				}
			},
			focus: function(event, ui ) {
				return false;
			},
			select: function(event, ui ) {
				cmpnyNm = ui.item.label;
				cmpnyId = ui.item.value;
				
				$('#searchCmpnyNm').val(ui.item.label);
				$('#searchCmpnyId').val(ui.item.value);
				
				$('#searchCmpnyNm').addClass('cmpny-complete');
				
				return false;
			}
		}).autocomplete( "instance" )._renderItem = function( ul, item ) {
			return $('<li>')
				.append('<div>' + item.label + '</div>')
				.appendTo(ul);
		};
	}
	*/
	
})();