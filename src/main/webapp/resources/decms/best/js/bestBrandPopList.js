(function() {

	const gridBrand = new tui.Grid({
		el: document.getElementById('data-brand-grid'),
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
			{ header: '바로가기', name: 'brandLink', width: 60, align: 'center',
				formatter: function(item) {
					if(!isEmpty(item.row.brandId)) {
						return '<a href="/shop/goods/brandGoodsList.do?searchGoodsBrandId='+item.row.brandId+'" target="_blank" class="btn btn-outline-dark btn-sm"><i class="fas fa-link"></i></a>';
					}else {
						return '-';
					}
				}
			},
			{ header: '선택', name: 'select', width: 100, align: 'center',
				formatter: function(item) {
					return 	'<a href="#" class="btn btn-success btn-sm mr-1" onclick="addBrandBest(\'' + item.row.brandId + '\');" >' 
						+ 		'<span class="oi oi-check"></span></a>';
				}
			}
		],
		columnOptions: {
			frozenCount: 1,
			frozenBorderWidth: 1,
			resizable:true
		}
	});
	
	var pagination = gridBrand.getPagination();
	
	var pagination = new tui.Pagination(document.getElementById('data-brand-grid-pagination'), {
		totalItems: 10,
		itemsPerPage: 10,
		visiblePages: 5,
		centerAlign: true
	});
	
	pagination.on('beforeMove', function(e) {
		getDataList(e.page);
	});

	$(document).on('shown.bs.modal', '#brandSearchModal', function(e) {
		getDataList(1);
		gridBrand.refreshLayout();
	});

	function getDataList(page) {
		var actionUrl = CTX_ROOT + '/decms/shop/goods/goodsBrandList.json';
		var formData = {
			'pageIndex' : page
		};
		
		jsonResultAjax({
			url: actionUrl,
			type: 'get',
			data: formData,
			//dataType: 'json',
			callback: function(result) {
				if(result.success) {
					var paginationObj = result.data.paginationInfo;
					
					if(paginationObj.currentPageNo == 1) {
						pagination.setItemsPerPage(paginationObj.recordCountPerPage);
						pagination.reset(paginationObj.totalRecordCount);
					}
					gridBrand.resetData(result.data.list);
				}
			}
		});
	}

})();