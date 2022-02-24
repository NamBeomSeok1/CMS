(function() {
	
	var mainScrollTop = 0;
	
	// 
	function getBestGoodsList(actionUrl, nextPageIndex) {
		$.ajax({
			url: actionUrl,
			type: 'get',
			dataType: 'html',
			cache: false,
			data: {pageIndex: nextPageIndex, listMode: 'list'},
			success: function(result) {
				result = result.trim();
				
				if(!isEmpty(result)) {
					$('#btnBestMore').data('nextPage', Number(nextPageIndex) + 1);
					$('#mainProductList').append(result);
				}else {
					$('#btnBestMore').hide();
				}
				
				var goodsItemCnt = $('.goods-item').length;
				var goodsTotalCnt = $('#totalCount').val();
				
				if(goodsTotalCnt <= goodsItemCnt) {
					$('#btnBestMore').hide();
				}
				
				sessionStorage.setItem('bestGoodsLastPageIndex', nextPageIndex);
			
				$(window).scrollTop(mainScrollTop);
			}
		});
		
	}
	
	function getMainBestGoodsList(lastPageIndex) {
		$.ajax({
			url: CTX_ROOT + '/embed/shop/goods/bestGoodsList.do',
			type: 'get',
			dataType: 'html',
			cache: false,
			data: {lastPageIndex: lastPageIndex},
			success: function(result) {
				result = result.trim();
				
				if(!isEmpty(result)) {
					$('#btnBestMore').data('nextPage', Number(lastPageIndex) + 1);
					$('#bestProduct').append(result);
				}else {
					$('#btnBestMore').hide();
				}
				
				var goodsItemCnt = $('.goods-item').length;
				var goodsTotalCnt = $('#totalCount').val();
				
				if(goodsTotalCnt <= goodsItemCnt) {
					$('#btnBestMore').hide();
				}
				
				mainScrollTop = sessionStorage.getItem('mainScrollTop');
				$(window).scrollTop(mainScrollTop);;
				
			}
		});
		
	}
	
	//메인 상품
	function getMainGoodsList(searchGoodsCtgryId) {
		$.ajax({
			url: CTX_ROOT + '/embed/shop/goods/mainGoodsList.do',
			type: 'get',
			dataType: 'html',
			cache: false,
			data: {searchGoodsCtgryId: searchGoodsCtgryId},
			success: function(result) {
				result = result.trim();
				
				if(!isEmpty(result)) {
					if(searchGoodsCtgryId == 'GCTGRY_0000000000032'){
						$('#sbsGoods').append(result);
					}else if(searchGoodsCtgryId == 'GCTGRY_0000000000033'){
						$('#gdsGoods').append(result);
					}else{
						$('#gnrGoods').append(result);
					}
				}
			}
		});
		
	}
	
	//더보기
	$(document).on('click', '#btnBestMore', function(e) {
		var actionUrl = $(this).data('src');
		var nextPageIndex = $(this).data('nextPage');
		
		mainScrollTop = $(window).scrollTop();
		getBestGoodsList(actionUrl, nextPageIndex);

	});
	
	$(document).on('click', '.goods-item a', function(e) {
		mainScrollTop = $(window).scrollTop();
		sessionStorage.setItem('mainScrollTop', mainScrollTop);
	});
	
	$(document).ready(function() {
		/*
		var bestGoodsLastPageIndex = sessionStorage.getItem('bestGoodsLastPageIndex');
		//console.log('bestGoodsLastPageIndex = ' + bestGoodsLastPageIndex);
		
		if(!bestGoodsLastPageIndex) {
			bestGoodsLastPageIndex = 1;
			sessionStorage.setItem('bestGoodsLastPageIndex', bestGoodsLastPageIndex);
		}
		
		var actionUrl = CTX_ROOT + '/embed/shop/goods/bestGoodsList.do';
		getMainBestGoodsList(bestGoodsLastPageIndex);
		*/
		
		//폭스구독권
		getMainGoodsList('GCTGRY_0000000000032');
		
		//폭스굿즈
		getMainGoodsList('GCTGRY_0000000000033');
		
		//폭스교육상품
		getMainGoodsList('GCTGRY_0000000000034');
	});
	
})();