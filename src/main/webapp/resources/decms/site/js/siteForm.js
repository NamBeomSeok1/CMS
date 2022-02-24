(function() {
	
	$(document).on('submit', '#registForm', function(e) {
		e.preventDefault();
		var actionUrl = $(this).attr('action');
		
		$(this).ajaxSubmit({
			url: actionUrl,
			type: 'post',
			dataType: 'json',
			success: function(data) {
				if(data.message) {
					bootbox.alert({
						title: '확인',
						message: data.message,
						size: 'small'
					}); 
				}
				
				if(!data.success) {
					validErrorFocus(data.errorList);
				}

				//console.log(data);
			}
		});
	});
	
})();