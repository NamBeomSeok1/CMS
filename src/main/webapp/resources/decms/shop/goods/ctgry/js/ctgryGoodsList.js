(function() {
	
	const goodsGrid = new tui.Grid({
		el: document.getElementById('data-goodsGrid'),
		rowHeight: 'auto',
		bodyHeight: 'auto',
		columns: [
			{ header: '상품코드', name: 'goodsId', width: 180, align: 'center'},
			{ header: '업체명', name: 'cmpnyNm', width: 100, align: 'center'},
			{ header: '이미지', name: 'goodsTitleImagePath', width: 100, align: 'center',
				formatter: function(item) {
					return (item.value == '')?'':'<img src="' + item.value + '" alt="" style="height:100%; max-height:30px;"/>';
				}
			},
			{ header: '상품명/카테고리', name: 'goodsNm', width:200,align: 'center',
				formatter: function(item) {
					return item.value+'<br/><small class="text-secondary">'+item.row.goodsCtgryNm+'</small>';
				}
			},
			{ header: '모델명', name: 'modelNm', width: 100, align: 'center'},
			{ header: '공급사', name: 'makr', width: 110, align: 'center'},
			{ header: '판매가', name: 'wmallPc', width: 100, align: 'center'},
			{ header: '조회수', name: 'rdcnt', width: 50, align: 'center'},
			{ header: '판매수', name: 'sleCo', width: 50, align: 'center'},
			{ header: '등록상태', name: 'registSttusCode', width: 80, align: 'center',
				formatter: function(item) {
					if(item.value == 'R') {
						return '등록대기';
					}else if(item.value == 'C') {
						return '등록완료';
					}else if(item.value == 'E') {
						return '판매종료';
					}
				}
			}
		],
		columnOptions: {
			frozenCount: 1,
			frozenBorderWidth: 2,
		}
	});
	
	
	var pagination = goodsGrid.getPagination();
	
	var pagination = new tui.Pagination(document.getElementById('data-goods-grid-pagination'), {
		totalItems: 10,
		itemsPerPage: 10,
		visiblePages: 5,
		centerAlign: true
	});
	
	pagination.on('beforeMove', function(e) {
		getGoodsList(e.page);
	});
	
	// 데이터 목록
	function getGoodsList(page) {
	 	if(isEmpty(page)) {
			page = $('#ctgryGoods-pageIndex').val(page);
		}else if(page > 0) {
			$('#ctgryGoods-pageIndex').val(page);
		}

		//var ctgryId = $('#searchCtgryId').data('ctgryid');
	 	var formData = $('#ctgryGoodsSearchForm').serialize();
		$.ajax({
			url: '/decms/shop/goods/goodsList.json',
			type: 'get',
			data: formData,
			dataType:'json',
			success: function(result) {
				if(result.success) {
					var paginationObj = result.data.paginationInfo;
					
					if(paginationObj.currentPageNo == 1) {
						pagination.setItemsPerPage(paginationObj.recordCountPerPage);
						pagination.reset(paginationObj.totalRecordCount);
					}
					console.log(result.data.list);
					goodsGrid.resetData(result.data.list);
				}
			}
			
		});
	};
	/*
	$(document).ready(function(){
		goodsGrid.refreshLayout();
		getGoodsList(1);
		$('.modal-body .navbar-nav').remove();
		$('.modal-body footer').remove();
	})
	*/
	
	//상품 목록 Click
	$(document).on('click', '.btnGoodsList', function() {
		var ctgryId = $(this).data('ctgryId');
		$('#ctgryGoods-searchGoodsCtgryId').val(ctgryId);
		
		var $modal = $($(this).data('target'));
		$modal.modal('show');
		$modal.on('shown.bs.modal', function(e) {
			goodsGrid.refreshLayout();
			getGoodsList(1);
		}).on('hide.bs.modal', function(e) {
			goodsGrid.clear();
		});
		
	});
})();	