(function() {
	
	// 삭제
	function deleteArticle(actionUrl) {
		$.ajax({
			url: actionUrl,
			type: 'post',
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
	
	// 삭제 Click
	$(document).on('click', '.btnDelete', function(e) {
		e.preventDefault();
		
		if(confirm('삭제하시겠습니까?')) {
			var actionUrl = $(this).attr('href');
			deleteArticle(actionUrl);
		}
	});
	
}) (); 	