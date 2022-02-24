(function() {
	const grid = new tui.Grid({
		el: document.getElementById('data-goods-grid'),
		rowHeight: 'auto',
		columns: [
			{ header: '상품코드', name: 'goodsId', width: 180, align: 'center'},
			{ header: '상품명', name: 'goodsNm', width: 120, align: 'center'},
			{ header: '모델명', name: 'modelNm', width: 120, align: 'center'},
			{ header: '상품가격', name: 'wmallPc', width: 50, align: 'center'},
			{ header: '등록일', name: 'frstRegistPnttm', width:100, align: 'center',
				formatter: function(item) {
					return isEmpty(item.value) ? '' : moment(item.value).format('YYYY-MM-DD')
				}
			},
			{ header: '등록상태', name: 'registSttusCode', width: 80, align: 'center',
				formatter: function(item) {
					if(item.value == 'R') {
						return '등록신청';
					}else if(item.value == 'A') {
						return '처리 중';
					}else if(item.value == 'C') {
						return '등록완료';
					}
				}
			},
			{ header: '보기', name: '', width: 100, align: 'center',
				formatter: function(item) {
					return 	'<a href="' + CTX_ROOT + '/decms/shop/goods/modifyGoods.do?goodsId='+item.row.goodsId+'&menuId=goodsManage" class="btn btn-success btn-sm mr-1 btnModify" title="수정">' 
						+ 		'<i class="fas fa-edit"></i></a>';
				}
			},
		],
	});
	
	tui.Grid.applyTheme('clean');
	
	var pagination = grid.getPagination();
	
	var pagination = new tui.Pagination(document.getElementById('data-goods-grid-pagination'), {
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
		var $form = $('#searchGoodsForm');
		var actionUrl = $form.attr('action');
		if(isEmpty(page)) {
			page = $form.find('#goodsPageIndex').val(page);
		}else if(page > 0) {
			$form.find('#goodsPageIndex').val(page);
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
	};
	
	$(document).ready(function() {
		getDataList(1);
	});

})();