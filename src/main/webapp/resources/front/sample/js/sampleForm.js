(function() {
	
	// 저장
	function saveArticle($form) {
		
		var actionUrl = $form.attr('action');
		var method = $form.attr('method') || 'post';

		$form.ajaxSubmit({
			url: actionUrl,
			type: method,
			dataType: 'json',
			success: function(result) {
				if(result.message) {
					alert(result.message);
				}
				
				if(result.success) {
					if(result.redirectUrl) {
						location.href = result.redirectUrl;
					}
				}
				
			}
		});
	}
	
	// 저장 Click
	$(document).on('submit', '#registForm', function(e) {
		e.preventDefault();
		if(!confirm('저장 하시겠습니까?')) {
			return false;
		}
		
		saveArticle($(this));
	});
	
})();