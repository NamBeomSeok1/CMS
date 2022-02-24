(function() {
	const grid = new tui.Grid({
		el: document.getElementById('data-recomend-grid'),
		rowHeight: 'auto',
		columns: [
			{ header: '상품코드', name: 'goodsId', width: 180, align: 'center',
				formatter:function(item){
					return '<a href="/shop/goods/goodsView.do?goodsId='+item.value+'" target="_blank"">'+item.value+'</a>';
				}
			},
			{ header: '제휴사', name: 'prtnrNm', width: 80, align: 'center'},
			{ header: '이미지', name: 'goodsTitleImagePath', width: 60, align: 'center',
				formatter: function(item) {
					return (item.value == '')?'':'<img src="' + item.value + '" alt="" style="height:100%; max-height:30px;"/>';
				}
			},
			{ header: '상품명/카테고리', name: 'goodsNm', align: 'center',
				formatter: function(item) {
					return item.value+' / '+item.row.goodsCtgryNm;
				}
			},
			{ header: '모델명', name: 'modelNm', width: 120, align: 'center'},
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
			},
			{ header: '선택', name: '', width: 100, align: 'center',
				formatter: function(item) {
					return '<button type="button" class="btn btn-success btn-sm btnSelectRecomend" ' 
					+ 'data-goods-id="' + item.row.goodsId + '" data-goods-nm="' + item.row.goodsNm + '" data-prtnr-id="' + item.row.prtnrId + '" data-evt-txt="' + item.row.goodsIntrcn + '"data-regist-sttus-code="'+ item.row.registSttusCode + '"><i class="fas fa-check"></i></button>';
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
	
	var pagination = new tui.Pagination(document.getElementById('data-recomend-grid-pagination'), {
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
		var $form = $('#recomendSearchForm');
		var actionUrl = $form.attr('action');
		if(isEmpty(page)) {
			page = $form.find('#recomendPageIndex').val(page);
		}else if(page > 0) {
			$form.find('#recomendPageIndex').val(page);
		}

		//var paramKeyValue = $form.serializeObject();
		var paramKeyValue = $form.serialize();
		jsonResultAjax({
			url: actionUrl,
			method: 'get',
			data: paramKeyValue,
			callback: function(result) {
				var paginationObj = result.data.paginationInfo;
				
				if(paginationObj.currentPageNo == 1) {
					pagination.setItemsPerPage(paginationObj.recordCountPerPage);
					pagination.reset(paginationObj.totalRecordCount);
				}
				grid.resetData(result.data.list);
			}
		});
	};
	
	function reIndexRecomend() {
		var $target = $('#recomend-list').children('.recomend-item');
		
		$target.each(function(index, item) {
			var sn = index + 1;
			$(item).find('.figure-num').text(sn);
			$(item).find('.goodsRecomendNo').attr('name', 'goodsRecomendList[' + index + '].goodsRecomendNo');
			$(item).find('.recomendGoodsSn').attr('name', 'goodsRecomendList[' + index + '].recomendGoodsSn');
			$(item).find('.recomendGoodsId').attr('name', 'goodsRecomendList[' + index + '].recomendGoodsId');
		});
	}
	
	function deleteRecomend(actionUrl) {
		if(actionUrl != '#') {
			modooAjax({
				url: actionUrl,
				method: 'post',
				callback: function(result) {
				}
			});
			
		}
	}
	
		
	// 검색 submit
	$(document).on('submit', '#recomendSearchForm', function(e) {
		e.preventDefault();
		
		getDataList(1);
	});
	
	// 추천상품 Click
	$(document).on('click', '.btnAddRecomend', function(e) {
		e.preventDefault();
		
		var $modal = $('#recomendListModal');
		
		$modal.modal('show');
		
		$modal.on('shown.bs.modal', function(e) {
			var brandId = '';
			if($('input[name=prtnrId]:checked').length > 0) {
				brandId = $('input[name=brandId]:checked').val();
				console.log(brandId);
				
				$('#recomendSearchForm').find('select[name=searchGoodsBrandId]').val(brandId);
			}
			grid.refreshLayout();
		});
	});
	
	//선택 Click
	$(document).on('click', '.btnSelectRecomend', function(e) {
		e.preventDefault();
		var goodsId = $(this).data('goodsId'),
			goodsNm = $(this).data('goodsNm'),
			registCode = $(this).data('regist-sttus-code');

		if(registCode=='R'){
			alert('등록대기 중인 상품입니다.');
			return false;
		} else {
			addSaleGoods(goodsId);
		}
		
	});

	$('#recomend-list').sortable({
		handle: '.figure-header',
		revert: true,
		stop: function(evt, ui) {
			reIndexRecomend();
		}
	});
})();	