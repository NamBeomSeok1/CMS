(function(){
var target='cardWrite';
var allCheck=false;
var chkCnt = 0;

//valid체크
function stplatCheck(){
	//약관동의체크
	$(document).on('change','#allStplat',function(){
		if($(this).parent('label').hasClass('ui-state-active')){
			$('.stplat').parent('label').addClass('ui-state-active');
			allCheck=true;
		}else{
			$('.stplat').parent('label').removeClass('ui-state-active');
			allCheck=false;
		}
	});
	
	$(document).on('change','.stplat',function(){
		if($('#cardStplat').parent('label').hasClass('ui-state-active')&& $('#indvdlStplat').parent('label').hasClass('ui-state-active')){
			$('#allStplat').parent('label').addClass('ui-state-active');
			allCheck=true;	
		}else{
			$('#allStplat').parent('label').removeClass('ui-state-active');
			allCheck=false;
		}
	});
}

function validation(){
	var cardNoCnt=0;
	var cardUsgpdNo=0;
	var kind = $('input[name="kind"]:checked').data('kind'); 
	//카드 번호 체크
	$('input[name=cardNo]').each(function(index){
		cardNoCnt+=$(this).val();
	});
	$('input[name=cardUsgpd]').each(function(index){
		cardUsgpdNo+=$(this).val();
	});
	
	if(cardNoCnt.length<16){
		modooAlert('카드 번호를 모두 입력해주세요.');
		return false;
	}else if(cardUsgpdNo.length<5){
		modooAlert('카드 유효기간을 정확히 입력해주세요.');
		return false;
	}else if($('input[name=cardPassword]').val()==null || $('input[name=cardPassword]').val().length<2){
		modooAlert('카드비밀번호를 정확히 입력해주세요.')
		return false;
	}else if($('#password1').val().length<6 && $('#password2').val().length<6 ){
			modooAlert('비밀번호를 정확히 입력해주세요.');
			return false;
	}else if($('#password1').val().search(/^[0-9]*$/)<0 || 	$('#password2').val().search(/^[0-9]*$/)){
		modooAlert('비밀번호는 숫자로만 입력가능합니다.');
		return false;
	}else if($('#password1').val() != $('#password2').val()){
		modooAlert('비밀번호가 맞지 않습니다.');
		return false;
	}else if(kind == 'first'){
		if($('input[name=brthdy]').val()==null || $('input[name=brthdy]').val().length!=6){
			modooAlert('생년월일을 정확히 입력해주세요.');
			return false;
		}
	}else if(kind == 'second'){
		if($('#bizrnoSize').val()==null || $('#bizrnoSize').val().length!=10){
			modooAlert('사업자등록번호를 정확히 입력해주세요.');
			return false;
		}
	}
	return true;
}	
//카드 등록 폼
$(document).on('click','#cardWrite',function(){
	$('input[name=cardNo]').val('');
	$('input[name=cardUsgpd]').val('');
	$('input[name=cardPassword]').val('');
	$('input[name=brthdy]').val('');
	$('.stplat').parent('label').removeClass('ui-state-active');
	$('#allStplat').parent('label').removeClass('ui-state-active');
	popOpen(target);
	stplatCheck();
})


//카드 등록
$(document).on('submit','#cardForm',function(e){
	e.preventDefault();
	var url = $(this).attr('action');
	var method = $(this).attr('method');
	if(allCheck){
		if(!validation()==false){
			$(this).ajaxSubmit({
				url:url,
				type:method,
				data:$(this).serialize(),
				dataType:'json',
				success: function(result){
					if(result.success){
						//console.log(result);
						modooAlert(result.message, '확인', function() {
						window.location.reload();
						});
					}else{
						modooAlert(result.message);
					}
				}
			});
		}else{'모든항목을 제대로 입력해주세요.'}
	}else{
		modooAlert('약관에 동의해주세요.');
	}
})

/*//카드리스트 출력
function cardList(){
	
	if($('.showCl').hasClass('on')){
		$('.cardSection').empty();
		$('.showCl').removeClass('on');
		$('.showCl').text('카드 보기');
	}else{
	$.ajax({
		url:CTX_ROOT+'/card/cardList.json',
		type:'post',
		dataType:'json',
		success: function(result){
			//console.log(result);
			if(result.success){
				var htmlStr ='';
				for(var i=0;i<result.data.cardList.length;i++){
					htmlStr+='<div class="cardItem">';	
					htmlStr+='<a  class="cardInfo"  href="#" data-cardno='+result.data.cardList[i].cardNo+' data-cardusgpd='+result.data.cardList[i].cardUsgpd+' data-cardpassword='+result.data.cardList[i].cardPassword+' data-brthdy='+result.data.cardList[i].brthdy+'>';
					htmlStr+='<img src="data:image/png;base64,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" style="width:200px; height:100px;"/></a><br/>';
					htmlStr+='<span>****-****-****-'+result.data.cardList[i].lastCardNo+'</span><br/>';
					htmlStr+='<a href="#" class="cardDEL" data-cardid="'+result.data.cardList[i].cardId+'">삭제</a></div>';
					$('.showCl').text('카드 접기');	
					$('.showCl').addClass('on');	
				}
				$('.cardSection').html(htmlStr);
			}else{
				$('.cardSection').append('<span>'+result.message+'</span>');
				$('.showCl').addClass('on');	
				}
			}
		})
	}
}
*/

//카드 목록
function cardList(){
	$.ajax({
		url:'/card/cardList.json',
		dataType:'json',
		type:'post',
		success:function(result){
			var html='';
			var list = result.data.cardList;
			for(var i=0;i<list.length;i++){
				html+='<tr>'
				html+='<td class="ac">'+list[i].lastCardNo+'</td>';
				html+='<td class="ac"><em class="spot">'+list[i].cardNm+'</em></td>'
				html+='<td><button type="button" data-cardid="'+list[i].cardId+'" class="btn">선택하기</button></td></tr>';
			}
			$('.card-list').empty();
			$('.card-list').html(html);
		}
	})
}

$(document).on('click','#changeCard',function(){
	popOpen('cardSubscribeEdit');
	cardList();
})

//카드 정보 삽입
$(document).on('click','.cardInfo',function(){
	
	var cardNo = $(this).data('cardno');
	var cardExpire = $(this).data('cardusgpd');
	var cardPw = $(this).data('cardpassword');
	var regNo = $(this).data('brthdy');
	
	$(this).parent().siblings().children('.cardInfo').removeClass('check');
	$(this).addClass('check');
	
	$('input[name="cardNumber"]').val(cardNo);
	$('input[name="cardExpire"]').val(cardExpire);
	$('input[name="cardPw"]').val(cardPw);
	$('input[name="regNo"]').val(regNo);
})

//카드 수정 폼
$(document).on('click','#cardUpForm',function(){
	
	var $self = $(this);
	var cardId = $(this).data('cardid');
	var cardNm = $(this).data('cardnm');
	var bassUseAt = $(this).data('bassuseat');
	var cardNo = $(this).data('cardno');
	
	$('#cardId').val(cardId);
	$('#cardNm').val(cardNm);
	$('#lastCardNo').empty();
	$('#prevPassword').val('');
	$('#password-mod1').val('');
	$('#password-mod2').val('');
	$('.modPassword-area').hide();
	$('#pwdChangeBtn').attr('checked',false);
	$('#pwdChangeBtn').parent('label').removeClass('ui-state-active');
	$('.prevPassword').hide();
	$('#lastCardNo').append('('+cardNo+')<br /><em class="spot">'+cardNm+'</em>');
	if(bassUseAt == 'Y'){
		$('#bassUseAt').parent('label').addClass('ui-state-active');
		$('#bassUseAt').attr('checked',true);
	}
	popOpen('cardEdit');
})
//비밀번호 변경 체크
$(document).on('change','#pwdChangeBtn',function(){
	if($(this).is(':checked')){
		$('.prevPassword').show();
	}else{
		$('.prevPassword').hide();
	}
})
//비밀번호 체크
$(document).on('click','#passwordChkBtn',function(e){
	e.preventDefault();
	var password = $('#prevPassword').val();
	var cardId = $('#cardId').val();
	
	$.ajax({
		url:'/shop/goods/checkPassword.json',
		type:'post',
		data:{
			password:password,
				cardId:cardId
			},
		dataType:'json',
		success:function(result){
			if(result.success){
				modooAlert('확인되었습니다.','',function(){
					chkCnt+=1;
					$('.modPassword-area').show();
				})
			}else{
				modooAlert('비밀번호가 맞지 않습니다.');
				
			}
		}
	});
});


//카드 수정
$(document).on('submit','#cardUpdate',function(e){
	e.preventDefault();
	
	var url = $(this).attr('action');

	if($('#bassUseAt').parent('label').hasClass('ui-state-active')){
		$('#bassUseAt').val('Y');
	}
	/*if($('#password-mod2').val().length>1 || $('#password-mod1').val().length>1){*/
	if(chkCnt==1){
		if($('#password-mod1').val().length<6 && $('#password-mod2').val().length<6 || $('#password-mod1').val()=='' || $('#password-mod2').val()=='' ){
			modooAlert('비밀번호를 정확히 입력해주세요.');
			return false;
		}else if($('#password-mod1').val().search(/^[0-9]*$/)<0 || 	$('#password-mod2').val().search(/^[0-9]*$/)){
			modooAlert('비밀번호는 숫자로만 입력가능합니다.');
			return false;
		}else if($('#password-mod1').val() != $('#password-mod2').val()){
			modooAlert('비밀번호가 맞지 않습니다.');
			return false;
		}
	}
		
		$(this).ajaxSubmit({
			url: url,
			type: 'post',
			dataType: 'json',
			success:function(res){
				modooAlert(res.message, '확인', function() {
					window.location.reload();
				});
			}
		});
	});


//카드 삭제클릭
$(document).on('click','#cardDel',function(){
	var cardId = $(this).data('cardid');
	modooConfirm('카드를 삭제 하시겠습니까?','카드 삭제',function(result){
		if(result){
			popOpen('passwordChk');
			$('#delPasswordChkBtn').data('cardid',cardId);
		}else{
			return false;
		}
	})
})


$(document).on('click','#delPasswordChkBtn',function(){
	var password = $('#passwordChk').val();
	const cardid = $(this).data('cardid');

		$.ajax({
			url:CTX_ROOT+'/shop/goods/checkPassword.json',
			type:'post',
			data:{
				'password':password,
				'cardId':cardid
			},
			dataType:'json',
			success:function(result){
				if(result.success){
					$.ajax({
						url:CTX_ROOT+'/card/cardDelete.json',
						type:'post',
						data:{cardId:cardid},
						dataType:'json',
						success:function(res){
							if(res.success){
								modooAlert('삭제완료되었습니다.','확인',function(){
									window.location.reload();
								});
							}else{
								modooAlert(res.message,'확인',function(){
									return false;
								});
							}
						}
					})
				}else{
					modooAlert('비밀번호가 맞지 않습니다.');
					return false;
				}
			}	
	});
})
//카드 구분
$(document).on('click','input[name="kind"]',function(){
	var kind = $(this).data('kind');
	var html = '';
	if(kind == 'first'){
		$('#bizrno').hide();
		$('#bizrno').next().empty();
		$('#bizrno').next().hide();
		$('#brthdy').next().empty();
		$('#brthdy').next().append('<input type="number" name="brthdy" maxlength="6" class="p10 al" placeholder="6자리 입력" title="6자리 입력" />');
		$('#brthdy').next().show();
		$('#brthdy').show();
	}else if(kind == 'second'){
		$('#brthdy').hide();
		$('#brthdy').next().empty();
		$('#brthdy').next().hide();
		$('#bizrno').next().empty();
		$('#bizrno').next().append('<input type="number" name="brthdy" id="bizrnoSize" autocomplete="off" maxlength="10" class="p10 al" placeholder=" - 를 빼고 입력해주세요"/>');
		$('#bizrno').next().show();
		$('#bizrno').show();
	}
})

})();
	
