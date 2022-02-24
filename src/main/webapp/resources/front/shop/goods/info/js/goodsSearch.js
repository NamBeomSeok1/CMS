(function() {
	
	// 정렬 변경
	$(document).on('change', '#searchOrderField', function(e) {
		$('#searchOrderForm').submit();
	});
	
	
	// 연관어 검색 클릭
	$(document).on('click', '.btnKeywordNm', function(e) {
		var keyword = $(this).text();
		var $searchForm = $('#searchGoodsForm');
		$searchForm.find('#searchKeyword').val(keyword);
		
		$searchForm.submit();
	});
	
	$(document).on('submit', '#searchGoodsForm', function() {
		var storeSearchWrdAt = localStorage.getItem('storeSearchWrdAt'); //검색 자동저장 여부

		// 자동 저장 기본
		if(isEmpty(storeSearchWrdAt)) {
			storeSearchWrdAt = 'Y';
		}

		$('[name=storeSearchWrdAt]').val(storeSearchWrdAt);
	});
	
})();