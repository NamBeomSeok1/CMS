(function() {
	
	// 정렬 변경
	$(document).on('change', '#searchOrderField', function(e) {
		$('#searchForm').submit();
	});
	
})();