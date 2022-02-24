(function() {
	var menuId = $('[name=menuId]').val();
	
	const grid = new tui.Grid({
		el: document.getElementById('data-grid'),
		rowHeight: 'auto',
		columns: [
			{ header: '브랜드번호', name: 'brandId', width: 200, align: 'center'},
			{ header: '로고', name: 'brandImageThumbPath', width: 120, align: 'center',
				formatter: function(item) {
					if(!isEmpty(item.value)) {
						return '<img src="'+item.value+'" alt="' + item.row.brandNm + '" style="height:30px;"/>';
					}else {
						return '-';
					}
				}
			},
			{ header: '업체 명', name: 'cmpnyNm', width: 200, align: 'center'},
			{ header: '노출구분', name: 'brandExpsrSeCode', width: 60, align: 'center',
				formatter: function(item) {
					if(item.value == 'ALL') {
						return '모두';
					}else if(item.value == 'B2B') {
						return 'B2B';
					}else if(item.value == 'B2C') {
						return 'B2C';
					}else if(item.value == 'NONE') {
						return '미노출';
					}else {
						return '-';
					}
				}
			},
			{ header: '브랜드명', name: 'brandNm', width: 200, align: 'center'},
			{ header: '브랜드 연결 링크', name: 'brandIntLink', width: 200, align: 'center'},
			{ header: '바로가기', name: 'brandLink', width: 60, align: 'center',
				formatter: function(item) {
					if(!isEmpty(item.row.brandId)) {
						return '<a href="/shop/goods/brandGoodsList.do?searchGoodsBrandId='+item.row.brandId+'" target="_blank" class="btn btn-outline-dark btn-sm"><i class="fas fa-link"></i></a>';
					}else {
						return '-';
					}
				}
			},
			{ header: '대표 이미지(PC)', name: 'brandRepImg', width: 150, align: 'center',
				formatter: function(item){
					if (item.value) {
						return '<img src="' + item.value + '" style="width: 100px; height: 50px;">';
					} else {
						return '미등록';
					}
				}},
			{ header: '대표 이미지(MOBILE)', name: 'brandRepImgMob', width: 150, align: 'center',
				formatter: function(item){
					if (item.value) {
						return '<img src="' + item.value + '" style="width: 100px; height: 50px;">';
					} else {
						return '미등록';
					}
				}},
			{ header: '소개 이미지', name: 'brandIntImg', width: 150, align: 'center',
				formatter: function(item){
					if (item.value) {
						return '<img src="' + item.value + '" style="width: 100px; height: 50px;">';
					} else {
						return '미등록';
					}
				}},
			{ header: '관리', name: '', width: 100, align: 'center',
				formatter: function(item) {
					return 	'<a href="' + CTX_ROOT + '/decms/shop/goods/writeBrand.do" class="btn btn-success btn-sm mr-1 btnModify" data-brand-id="' + item.row.brandId + '" title="수정">' 
						+ 		'<i class="fas fa-edit"></i></a>' 
						+ 	'<a href="' + CTX_ROOT + '/decms/shop/goods/deleteGoodsBrand.json?menuId=' + menuId + '&brandId=' + item.row.brandId + '" class="btn btn-danger btn-sm btnBrandDelete" title="삭제">' 
						+ 		'<i class="fas fa-trash"></i></a>';
				}
			},
		],
		columnOptions: {
			frozenCount: 1,
			frozenBorderWidth: 1,
			resizable:true
		}
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
		
		jsonResultAjax({
			url: actionUrl,
			type: 'get',
			data: formData,
			//dataType: 'json',
			callback: function(result) {
				/*
				if(result.message) {
					bootbox.alert({ title: '확인', message: result.message, size: 'small' }); 
				}
				*/
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
	/** 삭제 **/
	function deleteGoodsBrand(actionUrl) {
		jsonResultAjax({
			url: actionUrl,
			type: 'post',
			//dataType: 'json',
			callback: function(result) {
				/*
				if(result.message) {
					bootbox.alert({ title: '확인', message: result.message, size: 'small' }); 
				}
				*/
				
				if(result.success) {
					getDataList(1);
				}
			}
		});
	}
	
	$(document).ready(function() {
		getDataList(1);
	})
	
	$(document).on('click', '.btnModify', function(e) {
		e.preventDefault();
		
		var href = $(this).attr('href');
		var searchFormData = $('#searchForm').serialize();
		location.href = href + '?brandId=' + $(this).data('brandId') + '&' + searchFormData;
	});

	// 검색 submit
	$(document).on('submit', '#searchForm', function(e) {
		e.preventDefault();
		
		getDataList(1);
	});
	
	// 삭제 Click
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
	
	// 정렬 변경
	$(document).on('change', '#searchOrderField', function() {
		getDataList(1);
	});
	
	
})();