	

//파일 업로드
addFile = function(f){
	var file = f.files;
	
	if (file.length > 4)  {
		modooAlert('파일은 5개까지 올릴 수 있습니다.')
		return false;
	};
	
	$('#inqryAtchFile-list li').empty();
	for(let i=0;i<file.length; i++){
		$('#inqryAtchFile-list ul').append('<li>'+file[i].name+'</li>');
	}
};

function validate(){
	 if(isEmpty($('input[name="cmpnyNm"]').val())){
		modooAlert('회사명을 입력해주세요.')
	 }else if(isEmpty($('input[name="email1"]').val()) || isEmpty($('input[name="email2"]').val())){
	 	modooAlert('담당자이메일을 정확히 입력해주세요.');
		return false;
	}else if(isEmpty($('input[name="telno1"]').val())|| isEmpty($('input[name="telno2"]').val())|| isEmpty($('input[name="telno3"]').val())){
		modooAlert('담당자 전화번호를 정확히 입력해주세요.');
		return false;
	}else if(isEmpty($('input[name="cmpnyCharger"]').val())){
		modooAlert('담당자 이름을 정확히 입력해주세요.');
		return false;
	}else{
		return true;
	}
}
	
	//업체문의등록  클릭
	$(document).on('click','#inqryBtn',function(e){
		$('input[name="cmpnyIntrcn"]').val('');
		$('input[name="goodsIntrcn"]').val('');
		$('input[name="telno1"]').val('');
		$('input[name="telno2"]').val('');
		$('input[name="telno3"]').val('');
		$('input[name="email1"]').val('');
		$('input[name="email2"]').val('');
		$('input[name="cmpnyCharger"]').val('');
		$('input[name="cmpnyNm"]').val('');
		$('#inqryAtchFile').val('');
		$('#inqryAtchFile ul').empty();
		popOpen('inquiryRegist');
	});
	
	//업체문의등록
	$(document).on('click','#inqryRegBtn',function(e){
		e.preventDefault();
		var $self =$("#inqryForm");
		var actionUrl = $self.attr('action');
		var method = $self.attr('method');
		
		if(validate()){
			$self.ajaxSubmit({
				url: actionUrl,
				type: method,
				dataType: 'json',
				success: function(result) {
					modooAlert('등록되었습니다.',function(){
						popClose('inquiryRegist');
					});
				}
			})
		}
			
	});
	

	
	
	
