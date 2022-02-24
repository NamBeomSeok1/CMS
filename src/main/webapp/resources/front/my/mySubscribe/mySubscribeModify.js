$(document).ready(function(){
	
	var msg = $('.dlvyMssage').val();
	var text = $('#dlvyMssage').data('text');
	if(msg==0){
		$('.dlvyMssage').next().show();
		$('#dlvyMssage').text(text);
	}else{
		$('.dlvyMssage').next().hide();
		$('#dlvyMssage').val($('.dlvyMssage option:selected').val());
	}
	
})

//옵션변경
function modOrder(orderNo){
	$.ajax({
		url:'/shop/goods/selectOrder.json',
		type:'get',
		data:{orderNo:orderNo},
		dataType:'json',
		success:function(result){
			console.log(result);
			var item = result.data.result;
			var html='';
			html+='	<input type="hidden" id="goodsKndCode" value="'+item.orderKndCode+'">';
			html+='	<input type="hidden" id="sbscrptCycleSeCode" value="'+item.sbscrptCycleSeCode+'">';
			html+='	<input type="hidden" id="orderSttusCode" value="'+item.orderSttusCode+'">';
			if(item.orderKndCode=='SBS'){
				if(item.sbscrptCycleSeCode=="WEEK"){
					html+='<li>'
					html+='<cite>구독주기</cite>'
					html+='	<select title="주기 선택" id="sbscrptWeekCycle">'
					var cycleArr=item.goods.sbscrptWeekCycle.split(',');
					for(let i=0;i<cycleArr.length;i++){
						if(cycleArr[i]==item.sbscrptWeekCycle){
							html+='	<option value="'+item.sbscrptWeekCycle+'" selected>'+item.sbscrptWeekCycle+' 주</option>';
						}else{
							html+='	<option value="'+cycleArr[i]+'">'+cycleArr[i]+' 주</option>';
						}
					}
					html+='	</select>'
					html+='</li>'
						
					html+='<li>'
					html+='<cite>구독요일</cite>'
					html+='<select title="배송요일 선택" id="sbscrptDlvyWd">'
						
					var dlvyWd = item.goods.sbscrptDlvyWd.split(',');
					for(let i=0;i<dlvyWd.length;i++){
					
						var wd='';
						switch(dlvyWd[i]){
						case '2':
							var wd='월';
							break;
						case '3':
							var wd='화';
							break;
						case '4':
							var wd='수';
							break;
						case '5':
							var wd='목';
							break;
						case '6':
							var wd='금';
							break;
						case '7':
							var wd='토';
							break;
						case '1':
							var wd='일';
							break;
						} 
						if(dlvyWd[i]==item.sbscrptDlvyWd){
							html+='	<option value="'+item.sbscrptDlvyWd+'" selected>'+wd+'</option>';
						}else{
							html+='	<option value="'+dlvyWd[i]+'">'+wd+'</option>';
						}
					}
					html+='</select>'
					html+='</li>'
				}else if(item.sbscrptCycleSeCode=="MONTH"){
					html+='<li>'	
					html+='<cite>구독주기</cite>'
					html+='	<select title="주기 선택" id="sbscrptMtCycle">'
					
					var mtCycle=item.goods.sbscrptMtCycle.split(',');
					for(let i=0;i<mtCycle.length;i++){
						if(mtCycle[i]==item.sbscrptMtCycle){
							html+='	<option value="'+item.sbscrptMtCycle+'" selected>'+item.sbscrptMtCycle+' 개월</option>';
						}else{
							html+='	<option value="'+mtCycle[i]+'">'+mtCycle[i]+' 개월</option>';
						}
					}
					html+='	</select>'
					html+='</li>'
						
					html+='	<li>'
					html+='<cite>구독날짜</cite>'
					html+='<div class="datepicker-area">'
					html+='<input type="text" placeholder="배송일을 선택하세요" readonly class="datepicker-input" id="sbscrptDlvyDay" value="'+item.sbscrptDlvyDay+'"/>';
					html+='<button type="button" class="btn-datepicker-toggle"><span class="txt-hide">날짜선택</span></button>'
					html+='</div>'
					html+='</li>'
						}
					}
					html+='<li>'
					html+='<cite>수량</cite>'
					html+='<div class="count">'
					if(item.orderCo==1){
						html+='<button type="button" class="btn-minus" disabled><span class="txt-hide">빼기</span></button>'
					}else{
						html+='<button type="button" class="btn-minus"><span class="txt-hide">빼기</span></button>'
					}
					html+='<input type="text" id="modOrderCo" value="'+item.orderCo+'" title="수량 입력" maxlength="6" />'
					html+='<button type="button" class="btn-plus"><span class="txt-hide">더하기</span></button>'
					html+='</div>'
					html+='</li>'
					if(item.dopt.length!=0){
						html+='<li>'
						html+='<cite>기본옵션</cite>'
						html+='<select title="기본옵션선택" id="ditem">'
						html+='	<option value="">추가안함</option>'
						for(var j=0; j<item.dopt.length;j++){
							if(item.dopt[j].gitemSeCode=='D'){
								if(item.ditem!=null){
									if(item.ditem.gitemId==item.dopt[j].gitemId){
										html+='	<option value="'+item.ditem.gitemId+'" selected>'+item.ditem.gitemNm+'(+'+item.ditem.gitemPc+')</option>'
									}else{
										html+='	<option value="'+item.dopt[j].gitemId+'">'+item.dopt[j].gitemNm+'(+'+item.dopt[j].gitemPc+')</option>'
									}
								}else{
									html+='	<option value="'+item.dopt[j].gitemId+'">'+item.dopt[j].gitemNm+'(+'+item.dopt[j].gitemPc+')</option>'
									}
								}
							}
						html+='</select>'
						html+='</li>'
						}
						
					/*	if(item.fopt.length!=0){
							html+='<li>'
							html+='<cite>첫구독옵션</cite>'
							html+='<select title="첫구독옵션선택" id="fitem">'
							html+='	<option value="">추가안함</option>'
							for(let j=0; j<item.fopt.length;j++){
								if(item.fopt[j].gitemSeCode=='F'){
									if(item.fitem!=null){
										if(item.fitem.gitemId==item.fopt[j].gitemId){
											html+='	<option value="'+item.fitem.gitemId+'" selected>'+item.fitem.gitemNm+'(+'+item.fitem.gitemPc+')</option>'
									}else{
										html+='	<option value="'+item.fopt[j].gitemId+'">'+item.fopt[j].gitemNm+'(+'+item.fopt[j].gitemPc+')</option>'
										}
									}else{
										html+='	<option value="'+item.fopt[j].gitemId+'">'+item.fopt[j].gitemNm+'(+'+item.fopt[j].gitemPc+')</option>'
									}
								}
							}
							html+='</select>'
							html+='</li>'
						}*/
						
						if(item.aopt.length!=0){	
							html+='<li>'
							html+='<cite>추가옵션</cite>'
							html+='<select title="추가옵션선택" id="aitem">'
							html+='	<option value="">추가안함</option>'
							for(let j=0; j<item.aopt.length;j++){
								if(item.aopt[j].gitemSeCode=='A'){
									if(item.aitem!=null){
										if(item.aitem.gitemId==item.aopt[j].gitemId){
											html+='	<option value="'+item.aitem.gitemId+'" selected>'+item.aitem.gitemNm+'(+'+item.aitem.gitemPc+')</option>'
										}else{
											html+='	<option value="'+item.aopt[j].gitemId+'">'+item.aopt[j].gitemNm+'(+'+item.aopt[j].gitemPc+')</option>'
										}
									}else{
										html+='	<option value="'+item.aopt[j].gitemId+'">'+item.aopt[j].gitemNm+'(+'+item.aopt[j].gitemPc+')</option>'
									}
								}
							}
							html+='</select>'
							html+='</li>'
						}
							
				$('.option-list').empty();
				$('.option-list').html(html);
				popOpen('optionEdit');
		}	
	})
}

//주문옵션변경 버튼 클릭
$(document).on('click','#modBtn',function(){
	var orderNo=$('#orderNo').val();
	var goodsKndCode=$('#goodsKndCode').val();
	var orderCo = Number($('#modOrderCo').val());
	var sbscrptCycleSeCode=$('#sbscrptCycleSeCode').val();
	var orderSttusCode=$('#orderSttusCode').val();
	var jsonData = '';
	var dOpt=$('#ditem option:selected').val();
	var fOpt=$('#fitem option:selected').val();
	var aOpt=$('#aitem option:selected').val();
	var islandDlvyChk=$('#islandDlvyChk').val();
	var freeDlvyPc = $('#freeDlvyPc').val();
	var compnoDscntUseAt = $('#compnoDscntUseAt').val();
	
	if(orderCo>=2 && compnoDscntUseAt=='Y'){
		compnoDscntUseAt ='Y';
	}else{
		compnoDscntUseAt ='N';
	}
	
	var gnrOrderOptions = [aOpt,dOpt]; 
	if(gnrOrderOptions==','){
		gnrOrderOptions=null;
	}
	var sbsOrderOptions = [aOpt,dOpt,fOpt]; 
	if(sbsOrderOptions==', ,'){
		sbsOrderOptions=null;
	}
	
	if(goodsKndCode=='GNR'){
		jsonData={ 
		'orderNo':orderNo,
		'orderKndCode':'GNR',
		'orderCo':orderCo,
		'orderItemIdList':gnrOrderOptions,
		'orderSttusCode':orderSttusCode,
		'islandDlvyChk':islandDlvyChk,
		'freeDlvyPc':freeDlvyPc,
		'compnoDscntUseAt':compnoDscntUseAt
		}
	}else if(goodsKndCode=='SBS'){
		var sbscrptCycleSeCode = $('#sbscrptCycleSeCode').val();
		if(sbscrptCycleSeCode=='WEEK'){
			var sbscrptWeekCycle = Number($('#sbscrptWeekCycle option:selected').val());
			var sbscrptDlvyWd = Number($('#sbscrptDlvyWd option:selected').val());
			jsonData={ 
					'orderNo':orderNo,
					'orderCo':orderCo,
					'orderItemIdList':sbsOrderOptions,
					'orderKndCode':'SBS',
					'sbscrptCycleSeCode':'WEEK',
					'sbscrptWeekCycle':sbscrptWeekCycle,
					'sbscrptDlvyWd':sbscrptDlvyWd,
					'orderSttusCode':orderSttusCode,
					'islandDlvyChk':islandDlvyChk,
					'freeDlvyPc':freeDlvyPc,
					'compnoDscntUseAt':compnoDscntUseAt
				}
		}else if(sbscrptCycleSeCode=='MONTH'){
			var sbscrptMtCycle = Number($('#sbscrptMtCycle option:selected').val());
			var sbscrptDlvyDay = Number($('#sbscrptDlvyDay').val());
			jsonData={ 
					'orderNo':orderNo,
					'orderCo':orderCo,
					'orderKndCode':'SBS',
					'sbscrptCycleSeCode':'MONTH',
					'orderItemIdList':sbsOrderOptions,
					'sbscrptMtCycle':sbscrptMtCycle,
					'sbscrptDlvyDay':sbscrptDlvyDay,
					'orderSttusCode':orderSttusCode,
					'islandDlvyChk':islandDlvyChk,
					'freeDlvyPc':freeDlvyPc,
					'compnoDscntUseAt':compnoDscntUseAt
			}
		}
	}
	$.ajax({
		url:'/shop/goods/updateOrder.json',
		data:JSON.stringify(jsonData),
		dataType:'json',
		type:'post',
		contentType: 'application/json',
		success:function(result){
			if(result.success){
				modooAlert(result.message, '확인', function() {
				window.location.reload();
				});
			}else{
				modooAlert(result.message, '', function() {
				window.location.reload();
				});
			}
		}
	})
	
})

//수량(+ or -) 변경 Click
$(document).on('click', '.btn-minus, .btn-plus', function(e) {
	
	var orderCo = Number($('#modOrderCo').val());
	if($(this).hasClass('btn-minus')) {
		orderCo = orderCo - 1;
		
		if(orderCo < 1) {
			$(this).attr('disabled', true);
			$('#modOrderCo').val('1');
			return;
		}
	}else {
		orderCo = orderCo + 1;
		$('.btn-minus').attr('disabled', false);
	}
	$('#modOrderCo').val(orderCo);
});

//배송지변경 클릭 
function modDlvy(orderNo){
	var orderNo=$('#orderNo').val();
	var dlvyAdresNm=$('#dlvyAdresNm').val();
	var dlvyUserNm=$('#dlvyUserNm').val();
	var ordTelno1=$('#ordTelno1').val();
	var ordTelno2=$('#ordTelno2').val();
	var ordTelno3=$('#ordTelno3').val();
	var dlvyZip=$('#dlvyZip').val();
	var dlvyAdres=$('#dlvyAdres').val();
	var dlvyAdresDetail=$('#dlvyAdresDetail').val();
	var dlvyMssage=$('#dlvyMssage').val();
	var islandDlvyChk=$('#islandDlvyChk').val();
	var ordTelno = ordTelno1+'-'+ordTelno2+'-'+ordTelno3;
	var jsonData={ 
		'orderNo':orderNo,
		'dlvyAdresNm':dlvyAdresNm,
		'dlvyUserNm':dlvyUserNm,
		'recptrTelno':ordTelno,
		'dlvyZip':dlvyZip,
		'dlvyMssage':dlvyMssage,
		'dlvyAdresDetail':dlvyAdresDetail,
		'dlvyAdres':dlvyAdres,
		'dlvyMssage':dlvyMssage,
		'islandDlvyChk':islandDlvyChk
	};
	
	if(isEmpty(dlvyUserNm)){
		modooAlert('수령인을 입력해주세요.');
		return false;
	}else if(isEmpty(ordTelno1)||isEmpty(ordTelno2)||isEmpty(ordTelno3)||ordTelno1.length<2||ordTelno2.length<4||ordTelno3.length<4){
		modooAlert('연락처를 정확히 입력해주세요.');
		return false;
	}else if(isEmpty(dlvyAdres)||isEmpty(dlvyZip)||isEmpty(dlvyAdresDetail)){
		modooAlert('배송주소를 정확히 입력해주세요.');
		return false;
	}
	
	$.ajax({
		url:'/shop/goods/updateOrder.json',
		data:JSON.stringify(jsonData),
		dataType:'json',
		type:'post',
		contentType: 'application/json',
		success:function(result){
			if(result.success){
				modooAlert(result.message, '확인', function() {
					window.location.reload();
				});
				}else{
				modooAlert(result.message, function() {
					window.location.reload();
				});
			}
		}
	})
}

//배송 메세지 선택 
$(document).on('change','.dlvyMssage',function(){
	var msg = $('.dlvyMssage').val();
	if(msg==0){
		$(this).next().show();
		$('#dlvyMssage').val('');
	}else{
		$(this).next().hide();
		$('#dlvyMssage').val(msg);
	}
})

$(document).on('click','#zipOpenBtn',function(){
	popOpen('adress');
});
//카드 리스트 출력 
function cardList(){
	var cardInfo = $('#payCardInfo').val();
	var payCardId = $('#payCardId').val();
	$.ajax({
		url:'/card/cardList.json',
		type:'get',
		success:function(result){
            var html='';
            var list = result.data.cardList;
            	if(list.length>0){
		            for(var i=0;i<list.length;i++){
		            	html+='<tr>';
		                html+='<td class="ac">'+list[i].lastCardNo+'</td>';
		                html+='<td class="ac"><em class="spot">'+list[i].cardNm+'</em></td>'
		                if(list[i].cardId == payCardId ){
		                	html+='<td><p class="msg">결제카드</p></td>';
		            	}else{
		            		html+='<td><button type="button" data-cardid="'+list[i].cardId+'" class="btn cardSelBtn">선택하기</button></td>';
		            	}
		                html+='</tr>';
	            	}
            	}else{
            		html+='<td colspan="3" ><p class="none-txt">등록된 카드가 없습니다.</p></td>';
            	}
            $('.cardlist').empty();
            $('.cardlist').html(html);
 
            $('.payedCard').empty();
            $('.payedCard').append(cardInfo);
		}
	})
}

$(document).on('click','.cardSelBtn',function(){
	var orderNo = $('#orderNo').val();
	var cardId = $(this).data('cardid');
	modooConfirm('결제 카드를 바꾸시겠습니까? 다음 회차의 결제는 이 카드로 이루어집니다.','카드 변경',function(result){
	if(result){
		$.ajax({
			url:'/user/my/changeCard.json',
			data:{
				cardId:cardId
				,orderNo:orderNo
			},
			dataType:'json',
			type:'post',
			success:function(result){
				if(result.success){
					modooAlert('변경이 완료 되었습니다.','확인',function(){
						window.location.reload();
					})
				}else{
					modooAlert(result.message);
				}
			}
		})
	}else{
		return false;
	}
})
})


//결제카드 변경 버튼
function changeCardOpen(){
	popOpen('cardSubscribeEdit');
	cardList();
}

var allCheck=false;
//카드 vaild체크1
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
		})
		
		$(document).on('change','.stplat',function(){
			if($('#cardStplat').parent('label').hasClass('ui-state-active')&& $('#indvdlStplat').parent('label').hasClass('ui-state-active')){
				$('#allStplat').parent('label').addClass('ui-state-active');
				allCheck=true;	
			}else{
				$('#allStplat').parent('label').removeClass('ui-state-active');
				allCheck=false;
			}
		})
	}

//카드 vaild체크2
function validation(){
	var cardNoCnt=0;
	var cardUsgpdNo=0;
	var kind = $('input[name="kind"]:checked').data('kind'); 
	//카드 번호 체크
	$('.cardNo').each(function(index){
			cardNoCnt+=$(this).val();
	});
	$('.cardUsgpd').each(function(index){
		cardUsgpdNo+=$(this).val();
	});
	
	if(cardNoCnt.length<15){
		alert('카드 번호를 모두 입력해주세요.');
		return false;
	}else if(cardUsgpdNo.length<5){
		alert('카드 유효기간을 정확히 입력해주세요.');
		return false;
	}else if($('input[name=cardPassword]').val()==null || $('input[name=cardPassword]').val().length<2){
		alert('카드비밀번호를 정확히 입력해주세요.')
		return false;
	}else if($('#password1').val().length<6 && $('#password2').val().length<6){
		alert('비밀번호를 정확히 입력해주세요.');
		return false;
	}else if($('#password1').val().search(/^[0-9]*$/)<0 || 	$('#password2').val().search(/^[0-9]*$/)){
		alert('비밀번호는 숫자로만 입력가능합니다.');
		return false;
	}else if($('#password1').val() != $('#password2').val()){
		alert('비밀번호가 맞지 않습니다.');
		return false;
	}else if(kind == 'first'){
		if($('input[name=brthdy]').val()==null || $('input[name=brthdy]').val().length!=6){
		alert('생년월일을 정확히 입력해주세요.');
		return false;
		}
	}else if(kind == 'second'){
		if($('#bizrnoSize').val()==null || $('#bizrnoSize').val().length!=10){
		alert('사업자등록번호를 정확히 입력해주세요.');
		return false;
		}
	}
	return true;
}	

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

//카드등록버튼
$(document).on('click','#cardWrite',function(){
	$('input[name=cardNo]').val('');
	$('input[name=cardUsgpd]').val('');
	$('input[name=cardPassword]').val('');
	$('input[name=brthdy]').val('');
	$('.stplat').parent('label').removeClass('ui-state-active');
	$('#allStplat').parent('label').removeClass('ui-state-active');
	popOpen2('cardWrite');
	stplatCheck();
})


function cardSubmit() {
		//e.preventDefault();
	var cardNo = $('input[name="cardNo1"]').val()+$('input[name="cardNo2"]').val()+$('input[name="cardNo3"]').val()+$('input[name="cardNo4"]').val()
	var cardUsgPd = $('input[name="cardUsgpd1"]').val()+","+$('input[name="cardUsgpd2"]').val();
	if($('input[name="bassUseAt"]').parent('label').hasClass('ui-state-active')){
		$('input[name="bassUseAt"]').val('Y');
	}else{
		$('input[name="bassUseAt"]').val('N');
	}
	var dataJson = {
		"cardNo" :cardNo,
		"cardUsgpd":cardUsgPd,
		"cardPassword":$('input[name="cardPassword"]').val(),
		"brthdy":$('input[name="brthdy"]').val(),
		"cardNm":$('input[name="cardNm"]').val(),
		"bassUseAt":$('input[name="bassUseAt"]').val(),
		"password":$('input[name="password"]').val()
	};
	
	if(allCheck){
		if(validation()){
			$.ajax({
				url:CTX_ROOT+'/card/cardReg.json',
				type:'POST',
				data:dataJson,
				dataType:'json',
				success: function(result){
					if(result.success){
						alert(result.message)
						popClose('cardWrite');
						cardList();
					}else{
						alert(result.message);
					}
				},
			})
		}else{
			return false;
		}
	}else{
		alert('약관에 동의해주세요.');
	}
}
