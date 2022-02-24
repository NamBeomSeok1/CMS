var goodsPc=0;
var totPc=0;
var dlvyPc=0;
var optPc=0;
var dscntPc=0;
var orderCo=0;
var cartNoList = {};

window.onload = function () {
	$('.selCart').attr('checked',false);
	$('.selCart').parent('label').removeClass('ui-state-active');
	clearPrice();
}



//주문하기
$(document).on('click','.orderBtn',function(){
	if($('.selCart:checked').length==0){
		modooAlert('주문할 상품을 선택해주세요.');
		return false;
	}
})
//장바구니 목록 개수
function showCartCnt(){
		
	$.ajax({
		url:'/shop/goods/selectCartList.json',
		type:'GET',
		success:function(result){
			if(result.success){
				$('#cartCnt').remove();
				$('#cart').append('<span id="cartCnt" class="label-notice">'+result.totCartCnt+'</span>');
			}
		}
	})
}

// 금액계산
function setPrice() {

	goodsPc=0;
	totPc=0;
	dscntPc=0;
	dlvyPc=0;
	optPc=0;
	orderCo=0;
	
	for(let i=0; i<$('.selCart').length;i++){
		orderCo+=Number($('#orderCo'+i).val());
		goodsPc+=Number($('#goodsPc'+i).val());
		totPc+=Number($('#cartPc'+i).val());
		dlvyPc+=Number($('#dlvyPc'+i).val());
		optPc+=Number($('#optPc'+i).val());
		dscntPc+=Number($('#dscntPc'+i).val());
	}
	
	$('.bassPc').text(goodsPc.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ","));
	$('.optPc').text(optPc.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ","));
	$('.dlvyPc').text(dlvyPc.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ","));
	$('.totPc').text(totPc.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ","));
	$('.dscntPc').text(dscntPc.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ","));
	$('.orderCo').text($('.selCart').length);
	
}

function insertPrice(){
	
	$('.bassPc').text(goodsPc.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ","));
	$('.optPc').text(optPc.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ","));
	$('.dlvyPc').text(dlvyPc.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ","));
	$('.totPc').text(totPc.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ","));
	$('.dscntPc').text(dscntPc.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ","));
	$('.orderCo').text($('.selCart:checked').length);
}
//초기화
function clearPrice() {


	$('.bassPc').text(0);
	$('.optPc').text(0);
	$('.dlvyPc').text(0);
	$('.totPc').text(0);
	$('.dscntPc').text(0);
	$('.orderCo').text(0);
	
	goodsPc=0;
	totPc=0;
	dlvyPc=0;
	dscntPc=0;
	optPc=0;
	orderCo=0;
	
	$('.selCart').attr('checked',false);
}


//주문변경
function modCart(cartNo){
	$.ajax({
		url:'/shop/goods/selectCart.json',
		type:'get',
		data:{cartNo:cartNo},
		dataType:'json',
		success:function(result){
			console.log(result);
			var item = result.data.result;
			var html='';
			html+='	<input type="hidden" id="cartNo" value="'+item.cartNo+'">';
			if(item.exprnUseAt=='Y'){
				html+='	<input type="hidden" id="goodsKndCode" value="GNR">';
			}else{
				html+='	<input type="hidden" id="goodsKndCode" value="'+item.goodsKndCode+'">';
			}
			html+='	<input type="hidden" id="sbscrptCycleSeCode" value="'+item.sbscrptCycleSeCode+'">';
			
			if(item.goodsKndCode=='SBS'){
				if(item.sbscrptCycleSeCode=="WEEK"){
					html+='<li>'	
					html+='<cite>구독주기</cite>'
					html+='	<select title="주기 선택" id="sbscrptWeekCycle">'
					var cycleArr=[];
					if(item.goods.sbscrptWeekCycle.indexOf(",")>0){
						cycleArr=item.goods.sbscrptWeekCycle.split(',');
					}else{
						cycleArr=item.goods.sbscrptWeekCycle;
					}
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
					
					var mtCycle=[];
					if(item.goods.sbscrptMtCycle.indexOf(",")>0){
						mtCycle=item.goods.sbscrptMtCycle.split(',');
					}else{
						mtCycle=item.goods.sbscrptMtCycle;
					}
					for(let i=0;i<mtCycle.length;i++){
						if(mtCycle[i]==item.sbscrptMtCycle){
							html+='	<option value="'+item.sbscrptMtCycle+'" selected>'+item.sbscrptMtCycle+' 월</option>';
						}else{
							html+='	<option value="'+mtCycle[i]+'">'+mtCycle[i]+' 월</option>';
						}
					}
					html+='	</select>'
					html+='</li>'
						
					html+='	<li>'
					html+='<cite>정기결제일</cite>'
					html+='<div class="datepicker-area">'
					html+='<input type="text" placeholder="정기결제일을 선택하세요" readonly class="datepicker-input" id="sbscrptDlvyDay" value="'+item.sbscrptDlvyDay+'"/>';
					//html+='<button type="button" class="btn-datepicker-toggle"><span class="txt-hide">날짜선택</span></button>'
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
				//폭스구독권(수강권)
				if(item.goods.goodsCtgryId=='GCTGRY_0000000000032'){
					
					html+='<button type="button" class="btn-plus" disabled><span class="txt-hide">더하기</span></button>'
				}else{
					html+='<button type="button" class="btn-plus"><span class="txt-hide">더하기</span></button>'
				}
				
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
						if(item.fopt.length!=0){
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
						}
							
				$('.option-list').empty();
				$('.option-list').html(html);
				popOpen('optionEdit');
		}	
	})
}
//주문정보변경
$(document).on('click','#modBtn',function(){
	var cartNo=Number($('#cartNo').val());
	var goodsKndCode=$('#goodsKndCode').val();
	var orderCo = Number($('#modOrderCo').val());
	var sbscrptCycleSeCode=$('#sbscrptCycleSeCode').val();
	var jsonData = '';
	var dOpt=$('#ditem option:selected').val();
	var fOpt=$('#fitem option:selected').val();
	var aOpt=$('#aitem option:selected').val();
	var compnoDscntUseAt = $('#compnoDscntUseAt').val();
	var gnrOrderOptions = [aOpt,dOpt,fOpt]; 
	if(gnrOrderOptions==','){
		gnrOrderOptions=null;
	}
	var sbsOrderOptions = [aOpt,dOpt,fOpt]; 
	if(sbsOrderOptions==', ,'){
		sbsOrderOptions=null;
	}
	
	if(orderCo>=2 && compnoDscntUseAt=='Y'){
		compnoDscntUseAt = 'Y';	
	}else{
		compnoDscntUseAt = 'N';
	}
	
	if(goodsKndCode=='GNR'){
		jsonData={ 
		'cartNo':cartNo,
		'goodsKndCode':'GNR',
		'orderCo':orderCo,
		'cartItemIdList':gnrOrderOptions,
		'compnoDscntUseAt':compnoDscntUseAt
		}
	}else if(goodsKndCode=='SBS'){
		var sbscrptCycleSeCode = $('#sbscrptCycleSeCode').val();
		if(sbscrptCycleSeCode=='WEEK'){
			var sbscrptWeekCycle = Number($('#sbscrptWeekCycle option:selected').val());
			var sbscrptDlvyWd = Number($('#sbscrptDlvyWd option:selected').val());
			jsonData={ 
					'cartNo':cartNo,
					'orderCo':orderCo,
					'cartItemIdList':sbsOrderOptions,
					'goodsKndCode':'SBS',
					'sbscrptCycleSeCode':'WEEK',
					'sbscrptWeekCycle':sbscrptWeekCycle,
					'sbscrptDlvyWd':sbscrptDlvyWd,
					'compnoDscntUseAt':compnoDscntUseAt
					
				}
		}else if(sbscrptCycleSeCode=='MONTH'){
			var sbscrptMtCycle = Number($('#sbscrptMtCycle option:selected').val());
			var sbscrptDlvyDay = Number($('#sbscrptDlvyDay').val());
			jsonData={ 
					'cartNo':cartNo,
					'orderCo':orderCo,
					'goodsKndCode':'SBS',
					'sbscrptCycleSeCode':'MONTH',
					'cartItemIdList':sbsOrderOptions,
					'sbscrptMtCycle':sbscrptMtCycle,
					'sbscrptDlvyDay':sbscrptDlvyDay,
					'compnoDscntUseAt':compnoDscntUseAt
			}
		}
	}else if(goodsKndCode=='CPN'){
		jsonData={ 
			'cartNo':cartNo,
			'orderCo':orderCo,
			'goodsKndCode':'CPN',
			'compnoDscntUseAt':compnoDscntUseAt
		}
	}
	$.ajax({
		url:'/shop/goods/updateCart.json',
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
				modooAlert(result.message,'', function() {
				window.location.reload();
				});
			}
		}
	})
	
})

//장바구니 주문하기
$(document).on('submit','#sendOrder',function(){
	
	var selGoodsIdArr = [];

	$('.selCart:checked').each(function(index,item){
		selGoodsIdArr.push($(item).data('goodsid'));
	})
	
	const result = selGoodsIdArr.some(function(x){
		return selGoodsIdArr.indexOf(x) !== selGoodsIdArr.lastIndexOf(x); 
	});
	
	if(result){
		modooAlert('같은 상품의 체험구독과 구독상품을 함께 주문 할 수 없습니다.');
		return false;
	}
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


//장바구니 전체선택
$(document).on('change','#allSelect',function(){
	
	if($(this).is(':checked')){
		$('.selCart').attr('checked',true);
		$('.selCart').parent('label').addClass('ui-state-active');
		setPrice();
		/*$('.selCart').each(function(index,item){
			cartNoList[$(item).data('cartid')]=$(item).data('cartid');
		})*/
		
	}else{
		$('.selCart').attr('checked',false);
		$('.selCart').parent('label').removeClass('ui-state-active');
		clearPrice();
		/*cartNoList={};*/
	}
	
})

//장바구니 선택 
$(document).on('change','.selCart',function(){
	var cartChkCnt= $('.selCart').length;
	var i = $(this).data('selcartno');
	
	if($(this).is(':checked')){
		$(this).attr('checked',true);
		goodsPc+=Number($('#goodsPc'+i).val());
		totPc+=Number($('#cartPc'+i).val());
		dscntPc+=Number($('#dscntPc'+i).val());
		dlvyPc+=Number($('#dlvyPc'+i).val());
		optPc+=Number($('#optPc'+i).val());
		insertPrice();
		/*cartNoList[$(this).data('cartid')]=$(this).data('cartid');*/
		
	}else{
		$(this).attr('checked',false);
		goodsPc-=Number($('#goodsPc'+i).val());
		totPc-=Number($('#cartPc'+i).val());
		dscntPc-=Number($('#dscntPc'+i).val());
		dlvyPc-=Number($('#dlvyPc'+i).val());
		optPc-=Number($('#optPc'+i).val());
		insertPrice();
		/*delete cartNoList[$(this).data('cartid')];*/
	}
	
	if($('.selCart:checked').length==cartChkCnt){
		$('#allSelect').attr('checked',true);
		$('#allSelect').parent('label').addClass('ui-state-active');
	}else{
		$('#allSelect').attr('checked',false);
		$('#allSelect').parent('label').removeClass('ui-state-active');
	}
	
})

//장바구니 삭제
function deleteCart(){
	
	//삭제 리스트
	var delCartNoList = [];
	
	$('.selCart:checked').each(function(index,item){
		delCartNoList.push($(item).data('cartid'));
	})
	if(delCartNoList==''){
		modooAlert('삭제할 장바구니를 선택해주세요.');
		return false;
	}
	var jsonData = JSON.stringify(delCartNoList);

	modooConfirm('삭제하시겠습니까?','장바구니 삭제',function(result){
		if(result){
			$.ajax({
				url:'/shop/goods/deleteCart.json',
				data:{delCartNoList:delCartNoList},
				traditional:true,
				type:'post',
				success:function(result){
					if(result.success){
						modooAlert('상품이 삭제되었습니다.', '확인', function() {
						window.location.reload();
						});
					}else{
						mdooAlert(result.message);
						return false;
					}
				}
			})
		}else{
			return false;
		}
	});
		
}

