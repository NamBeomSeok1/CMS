(function() {
	var exprnUseAt = 'N';
	var compnoDscntUseAtChk = 'N';
	var evtGoodsOrderChkAt = 'N';
	var compnoDscntUseAt = $('#compnoDscntUseAt').val();
	var contents;
	var interval;
	//성인인증여부
	cardCertResult = function (result) {
		if(result == true){
			modooAlert('본인 인증 되었습니다.','',function(){
				window.location.reload();
			});
		}else if(result == false){
			modooAlert('본인 인증이 실패하였습니다.','',function(){
				window.location.reload();
			});
		}else{
			$('.site-body').remove('.loading');
			$('.loading').hide();
		}
	};
	
	//클립보드 복사
	urlClipCopy = function(url) {
	  var textarea = document.createElement('textarea');
	  textarea.value = url;
	  
	  document.body.appendChild(textarea);
	  textarea.select();
	  textarea.setSelectionRange(0,9999);//추가
	 
	  document.execCommand('copy');
	  document.body.removeChild(textarea);
	  toast('URL이 복사되었습니다.');	  
	}

	//카카오 공유하기 버튼
	$(document).on('click','#kakaoShare',function(){
		
		var title=$(this).data('title');
		var description=$(this).data('description');
		var link=$(this).data('link');
		var imageUrl=$(this).data('imageurl');
		
		kakaoShare(title,description,link,imageUrl);
	})

	
	
	//네이버 공유하기
	$(document).on('click','#naverShare',function(){
		
		var title=$(this).data('title');
		var link=$(this).data('link');
		var encUrl = encodeURI(link);
		var encTit = encodeURI(title);
		
		window.open('https://share.naver.com/web/shareView.nhn?url='+encUrl+'&title='+encTit,'네이버 공유하기','window=800,height=700,toolbar=no,menubar=no,scrollbars=no,resizable=yes');
	
	})

	//페이스북 공유하기
	$(document).on('click','#facebookShare',function(){
		
		var title=$(this).data('title');
		var link=$(this).data('link');
		var encUrl = encodeURI(link);
		var encTit = encodeURI(title);
		
		window.open( 'https://www.facebook.com/sharer/sharer.php?u=' + encUrl+'&t='+encTit ,'페이스북공유하기','window=800,height=700,toolbar=no,menubar=no,scrollbars=no,resizable=yes');

	})

	//트위터 공유하기
	$(document).on('click','#twitterShare',function(){
		
		var title=$(this).data('title');
		var link=$(this).data('link');
		var encUrl = encodeURIComponent(link);
		var encTit = encodeURIComponent(title);
		
		window.open( 'https://twitter.com/intent/tweet?text='+encTit+'&url=' + encUrl,'트위터공유하기','window=800,height=700,toolbar=no,menubar=no,scrollbars=no,resizable=yes');
		
	})
	
	//성인인증
	$(document).on('click','.adultCrtBtn',function(e){
		e.preventDefault();
		var OpenOption = 'toolbar=no,location=no,directories=no,status=no,menubar=no,scrollbars=yes,resizable=yes,width=420,height=800,top=100';

		contents = window.open("", "contents", OpenOption);
		
		document.reqfrm.target = "contents"; 
		document.reqfrm.submit();
		$('.loading').show();
		var windowInterval = setInterval(function(){
			
			if(contents.closed){
				$('.site-body').remove('.loading');
				$('.loading').hide();
				clearInterval(windowInterval);
			}
			
		},100)	
	})
	
	
	
	// 총상품금액
	function setTotalPrice() {
		var goodsPc = Number($('#goodsPrice').val());
		var orderCo = Number($('#orderCo').val());
		var exprnPc = Number($('#exprnPc').val());
		var compnoDscntPc = Number($('#compnoDscntPc').val());
		
		var optPc = 0;
		$('#goodsOrderForm .orderOption').each(function(index) {
			var $select = $(this).find('option:selected');
			if(!isEmpty($select.val())) {
				optPc = optPc + Number($select.data('pc'));
				debug(optPc);
			}
		});
		
		
		var totalPc = goodsPc * orderCo + optPc
		
		var compnoDscntVal = Number(0);
		
		if(orderCo>=2 && compnoDscntUseAt=='Y'){
			$('.total-info-list').show();
			compnoDscntUseAtChk='Y';
			totalPc = goodsPc * orderCo + optPc+(compnoDscntPc*orderCo);
			var compnoDscntVal = Number(compnoDscntPc*orderCo);
			$('.compnoDscntPc-area').parent('li').show();
		}else{
			compnoDscntUseAtChk='N';
			$('.compnoDscntPc-area').parent('li').hide();
		}
		$('.compnoDscntPc-area').text(compnoDscntVal.toString().replace(/\B(?=(\d{3})+(?!\d))/g,",")+"원");
		
		
		
		if(exprnUseAt == 'Y'){
			var compnoDscntVal = 0;
			
			$('.total-info-list').show();
			
			if(orderCo>=2 && compnoDscntUseAt=='Y'){
				totalPc = goodsPc * orderCo + optPc+(exprnPc*orderCo)+(compnoDscntPc*orderCo);
			}else{
				totalPc = goodsPc * orderCo + optPc+(exprnPc*orderCo);
			}
			$('.exprnPc-area').text((goodsPc * orderCo + optPc+(exprnPc*orderCo)).toString().replace(/\B(?=(\d{3})+(?!\d))/g,",")+"원");	
		}
		
		 if(exprnUseAt == 'N' && compnoDscntUseAtChk=='N'){
				$('.total-info-list').hide();
		 } 
		
		
		//var numberFormatTotalPc = new Intl.NumberFormat('en-IN', { maximumSignificantDigits: 3 }).format(totalPc);
		$('.totPrice').text(totalPc.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ","));	
	}
	
	//이벤트상품주문 체크
	function evtGoodsOrderChk(goodsId){
		$.ajax({
			url:CTX_ROOT+'/shop/goods/evtGoodsOrderChk.json',
			data:{'goodsId':goodsId},
			type:'POST',
			async: false,
			dataType:'json',
			success:function(result){
				console.log(result);
				if(result.success){
					evtGoodsOrderChkAt = 'Y';
				}else{
					modooAlert(result.message,'확인');
					evtGoodsOrderChkAt = 'N';
				}
			}
		})
	}; 
	
	
	// 장바구니 or 구독하기 요청
	function sendOrderData(orderMode) {
		var $form = $('#goodsOrderForm');
		if(orderMode == 'SBS') {
			$form.attr('action', CTX_ROOT + '/shop/goods/order.do');
			$form.submit();
		}
	}
	
	// 유효성 검사
	function validate($form) {
		var goodsKndCode = $('#goodsKndCode').val();
		var goodsId = $('#goodsId').val();
		
		evtGoodsOrderChk(goodsId);
		if(evtGoodsOrderChkAt=='N'){
			return false;
		}
		
		
		if (goodsKndCode != 'GNR' && goodsKndCode != 'CPN') {
			var sbscrptCycleSeCode = $('#sbscrptCycleSeCode').val();
			if(sbscrptCycleSeCode == 'WEEK') {
				var sbscrptWeekCycle = $('#sbscrptWeekCycle option:selected').val();
				if(isEmpty(sbscrptWeekCycle)) {
					modooAlert('구독주기를 선택하세요!');
					return false;
				}
				var sbscrptDlvyWd = $('#sbscrptDlvyWd option:selected').val();
				if(isEmpty(sbscrptDlvyWd)) {
					modooAlert('정기결제 요일을 선택하세요!');
					return false;
				}
				
			}else if(sbscrptCycleSeCode == 'MONTH') {
				var sbscrptMtCycle = $('#sbscrptMtCycle option:selected').val();
				if(isEmpty(sbscrptMtCycle)) {
					modooAlert('구독주기를 선택하세요!');
					return false;
				}
				var sbscrptDlvyDay = $('#sbscrptDlvyDay').val();
				if(isEmpty(sbscrptDlvyDay)) {
					modooAlert('정기 결제일을 선택하세요!');
					return false;
				}
			}
		}
		
		if($('#orderOption').length > 0){
			var dopt = $('#orderOption option:selected').val();
			if(isEmpty(dopt)){
				modooAlert('기본 옵션은 필수 선택입니다.');
				return false;
			}
		}
		
		if(exprnUseAt != 'Y'){
			if($('#frstOptnEssntlAt').val() == 'Y' && isEmpty($('#fOptOption option:selected').val())) {
				modooAlert('첫 구독옵션은 필수 선택입니다.');
				return false;
			}
		}
		
		var orderCo = $('#orderCo').val();
		if(isEmpty(orderCo) || orderCo == '0') {
			modooAlert('수량을 선택하세요!');
			return false;
		}
		return true;
	}
	
	//장바구니에 담기
	function addCart() {
		var $form = $('#goodsOrderForm');
		
		if(validate($form)){
			var goodsId = $('#goodsId').val();
			var orderCo = Number($('#orderCo').val());
			var goodsKndCode = $('#goodsKndCode').val();
			var jsonData = '';
			var dOpt=$('.dOpt option:selected').val();
			var fOpt=$('.fOpt option:selected').val();
			var aOpt=$('.aOpt option:selected').val();
			var exprnPc = 0;
			var gnrOrderOptions = [aOpt,dOpt,fOpt]; 
			if(gnrOrderOptions==','){
				gnrOrderOptions=null;
			}

			var sbsOrderOptions = [aOpt,dOpt,fOpt]; 
			if(sbsOrderOptions==', ,'){
				sbsOrderOptions=null;
			}

			
			if(exprnUseAt == 'Y'){
				exprnPc = Number($('#exprnPc').val())*orderCo;
			}else{
				exprnUseAt = null;
			}
			
			//일반,체험구매
			if(goodsKndCode=='GNR'){
				jsonData={ 
				'orderCo':orderCo,
				'goodsKndCode':'GNR',
				'goodsId':goodsId,
				'cartItemIdList':gnrOrderOptions,
				'exprnPc':exprnPc,
				'exprnUseAt':exprnUseAt,
				'compnoDscntUseAt':compnoDscntUseAtChk
				}
				//구매상품
			}else if(goodsKndCode=='SBS'){
				var sbscrptCycleSeCode = $('#sbscrptCycleSeCode').val();
				if(sbscrptCycleSeCode=='WEEK'){
					var sbscrptWeekCycle = Number($('#sbscrptWeekCycle option:selected').val());
					var sbscrptDlvyWd = Number($('#sbscrptDlvyWd option:selected').val());
					jsonData={ 
							'orderCo':orderCo,
							'goodsId':goodsId,
							'cartItemIdList':sbsOrderOptions,
							'goodsKndCode':'SBS',
							'sbscrptCycleSeCode':'WEEK',
							'sbscrptWeekCycle':sbscrptWeekCycle,
							'sbscrptDlvyWd':sbscrptDlvyWd,
							'compnoDscntUseAt':compnoDscntUseAtChk
					}
				}else if(sbscrptCycleSeCode=='MONTH'){
					var sbscrptMtCycle = Number($('#sbscrptMtCycle option:selected').val());
					var sbscrptDlvyDay = Number($('#sbscrptDlvyDay').val());
					jsonData={ 
							'orderCo':orderCo,
							'goodsId':goodsId,
							'goodsKndCode':'SBS',
							'sbscrptCycleSeCode':'MONTH',
							'cartItemIdList':sbsOrderOptions,
							'sbscrptMtCycle':sbscrptMtCycle,
							'sbscrptDlvyDay':sbscrptDlvyDay,
							'compnoDscntUseAt':compnoDscntUseAtChk
					}
				}
				//쿠폰상품
			}else if(goodsKndCode=='CPN'){
				jsonData={ 
						'orderCo':orderCo,
						'goodsId':goodsId,
						'goodsKndCode':'CPN',
						'compnoDscntUseAt':compnoDscntUseAtChk
				}
			}
			//console.log(jsonData);
			$.ajax({
				url:CTX_ROOT+'/shop/goods/insertCart.json',
				data:JSON.stringify(jsonData),
				dataType:'json',
				type:'post',
				contentType: 'application/json',
				success:function(result){
					popClose('popupCart');
					if(result.redirectUrl!=null){
						window.location.href = result.redirectUrl; 
					}
					if(result.message) {
						$('[data-popup="popupCartCmplt"]').find('.pop-message').html(result.message);
						popOpen('popupCartCmplt');
					}

					if(result.success){
						$('#cartCnt').remove();
						$('#cart').append('<span id="cartCnt" class="label-notice">'+result.data.cartCnt+'</span>');
					}
				}
			});
		}else {
			popClose('popupCart');
		}
		popClose('popupCart');
	}
	
	//장바구니에 담기
	$(document).on('click', '#btnAddCart', function(e) {
		var $form = $('#goodsOrderForm');
		e.preventDefault();
		popClose('popupCart');
		if(validate($form)){
		//네이버 전환스크립트
		/* var _nasa={};
		 if (window.wcs) _nasa["cnv"] = wcs.cnv("3",1);*/
		 addCart();
		}
	});
	
	
	
	
	// 장바구니 or 구독하기 Click
	$(document).on('click', '.btnOrder', function(e) {
		var orderMode = $(this).data('orderMode');
		$('#orderMode').val(orderMode);
		
		sendOrderData(orderMode);
		return false;
	});
	
	// 주문(장바구니 or 구독하기) Submit;
	$(document).on('submit', '#goodsOrderForm', function(e) {
		if(!validate($(this))) {
			e.preventDefault();
		}
	});
	
	// 업체요청사항 Keypress
	$(document).on('keypress', '.gitemAnswer', function(e) {
		if(e.keycode == '13') return false;
	});
	
	// 수량 변경
	$(document).on('change', '.orderCo', function(e) {
		var orderCo = $(this).val();
		if(isEmpty(orderCo)) {
			$('.orderCo').val('1');
		}
		$('.orderCo').not(this).val(orderCo);
		
		setTotalPrice();
	});
	
	// 수량(+ or -) 변경 Click
	$(document).on('click', '.btn-minus, .btn-plus', function(e) {
		console.log('click');
		
		var orderCo = Number($('.orderCo').val());
		if($(this).hasClass('btn-minus')) {
			orderCo = orderCo - 1;

			if(orderCo < 1) {
				$(this).attr('disabled', true);
				$('.orderCo').val('1');
				return;
			}
		}else {
			orderCo = orderCo + 1;
			$('.btn-minus').attr('disabled', false);
		}
		$('.orderCo').val(orderCo);
		setTotalPrice();
	});
	
	// 옵션 Change
//	$(document).on('change', '.orderOption', function(e) {
//		setTotalPrice();
//	});
	
	// 구매 옵션 처리
	$(document).on('change', '.sbscrptWeekCycle', function(e) {
		var week = $(this).val();
		$('.sbscrptWeekCycle').not(this).val(week);
	});
	$(document).on('change', '.sbscrptDlvyWd', function(e) {
		var week = $(this).val();
		$('.sbscrptDlvyWd').not(this).val(week);
	});
	$(document).on('change', '.sbscrptMtCycle', function(e) {
		var week = $(this).val();
		$('.sbscrptMtCycle').not(this).val(week);
	});
	$(document).on('change', '.sbscrptDlvyDay', function(e) {
		var week = $(this).val();
		$('.sbscrptDlvyDay').not(this).val(week);
	});
	$(document).on('change', '.dOpt', function(e) {
		var opt = $(this).val();
		$('.dOpt').not(this).val(opt);
		setTotalPrice();
	});
	$(document).on('change', '.fOpt', function(e) {
		var week = $(this).val();
		$('.fOpt').not(this).val(week);
		setTotalPrice();
	});
	$(document).on('change', '.aOpt', function(e) {
		var week = $(this).val();
		$('.aOpt').not(this).val(week);
		setTotalPrice();
	});
	
	
	
	$(document).ready(function() {
		setTotalPrice();
	});
	
	var payInfoText = $('.payInfo-text').html();
	
	 $('.btn-option-check').on('click', function () {
         $('.option-check, .option-check .tooltip-area').removeClass('is-active');
         $type = $(this).data('buytype');
         $el = $('[data-buytype="' + $type + '"]');
         
         if($type=='experience'){
        	 $('#goodsKndCode').val('GNR');
				$('.sbscrptWeekCycle').parent('li').hide();
				$('.sbscrptWeekCycle').val(null);
				$('.sbscrptDlvyWd').parent('li').hide();
				$('.sbscrptDlvyWd').val(null);
				$('.sbscrptMtCycle').parent('li').hide();
				$('.sbscrptMtCycle').val(null);
				$('.sbscrptDlvyDay').parent('div').parent('li').hide();
				$('.fOpt').parent('li').hide();
				$('.fOpt').val(null);
				$('.fOpt').next('.msg').hide();
				$('#exprnUseAt').val('Y');
				exprnUseAt = 'Y';
				$('.btnOrderText').text('구매하기');
				$('.exprnPc-area').parent('li').show();
				$('.payInfo-text').text('1회 체험으로 구매하시면, 주문 시 1회 결제만 이루어집니다.');
				$('.dlvyInfo-text').text('결제 후, 2~3일 이내 배송됩니다.');
				setTotalPrice();
         }else{
        	 $('#goodsKndCode').val('SBS');	
				$('.sbscrptWeekCycle').parent('li').show();
				$('.sbscrptWeekCycle').val($('.sbscrptWeekCycle').children('option').first().val());
				$('.sbscrptDlvyWd').parent('li').show();
				$('.sbscrptDlvyWd').val($('.sbscrptDlvyWd').children('option').first().val());
				$('.sbscrptMtCycle').parent('li').show();
				$('.sbscrptMtCycle').val($('.sbscrptMtCycle').children('option').first().val());
				$('.sbscrptDlvyDay').parent('div').parent('li').show();
				$('.btnOrderText').text('구독하기');
				$('.btnOrderText').append('<i class="ico-arr-r sm back wh" aria-hidden="true"></i>');
				$('.fOpt').parent('li').show();
				$('.fOpt').next('.msg').show();
				$('.total-info-list').hide();
				$('.exprnPc-area').parent('li').hide();
				$('.exprnPc-area').text(0);
				$('#exprnUseAt').val('N');
				$('.payInfo-text').html(payInfoText);
				$('.dlvyInfo-text').text('정기결제 후, 2~3일 이내 배송됩니다.');
				exprnUseAt = 'N';
				setTotalPrice();
         }
         
         $el.parent().addClass('is-active');
     });
	
	/*//1회체험
	$(document).on('click','.optioncheck',function(){
	
		    toggleCommonFunc({
		        obj: $('.option-check-area'),
		        className: 'is-active',
		        hasClass: function () {
		        	$('.optioncheck').prop('checked',true).button("refresh");
					$('#goodsKndCode').val('GNR');
					$('.sbscrptWeekCycle').parent('li').hide();
					$('.sbscrptWeekCycle').val(null);
					$('.sbscrptDlvyWd').parent('li').hide();
					$('.sbscrptDlvyWd').val(null);
					$('.sbscrptMtCycle').parent('li').hide();
					$('.sbscrptMtCycle').val(null);
					$('.sbscrptDlvyDay').parent('div').parent('li').hide();
					$('.fOpt').parent('li').hide();
					$('.fOpt').val(null);
					$('.fOpt').next('.msg').hide();
					exprnUseAt = 'Y';
					$('.btnOrderText').text('구매하기');
					$('.exprnPc-area').parent('li').show();
					setTotalPrice();
		        },
		        noneClass: function () {
		        	$('.optioncheck').prop('checked',false).button("refresh");
					$('#goodsKndCode').val('SBS');	
					$('.sbscrptWeekCycle').parent('li').show();
					$('.sbscrptWeekCycle').val($('.sbscrptWeekCycle').children('option').first().val());
					$('.sbscrptDlvyWd').parent('li').show();
					$('.sbscrptDlvyWd').val($('.sbscrptDlvyWd').children('option').first().val());
					$('.sbscrptMtCycle').parent('li').show();
					$('.sbscrptMtCycle').val($('.sbscrptMtCycle').children('option').first().val());
					$('.sbscrptDlvyDay').parent('div').parent('li').show();
					exprnUseAt = 'N';
					$('.btnOrderText').text('구독하기');
					$('.btnOrderText').append('<i class="ico-arr-r sm back wh" aria-hidden="true"></i>');
					$('.fOpt').parent('li').show();
					$('.fOpt').next('.msg').show();
					$('.total-info-list').hide();
					$('.exprnPc-area').parent('li').hide();
					$('.exprnPc-area').text(0);
					exprnUseAt = 'N';
					setTotalPrice();
		        }
		    })
	})*/
	
	
})();