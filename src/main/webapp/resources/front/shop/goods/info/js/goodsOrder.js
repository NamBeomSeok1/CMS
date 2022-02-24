(function() {
	var totalPc = Number($('.totAmount').data('totpc'));//총가격
	var leftPoint = Number($('#leftPoint').data('leftpoint'));//이지웰포인트
	var islandDlvyPc = Number(0);//추가배송비용
	var zip = $('input[name="dlvyInfo.dlvyZip"]').val();//우편번호
	var $form = $('#sendOrderForm');
	//주문종류
	var orderKnd = $('#orderKnd').val();
	//쿠폰상품1개일때
	var isCoupon = $('#isCoupon').val();
	function validator() {
		var dadresNo = $('#dadresNo').val();
		if ($form) {
			if (isEmpty(dadresNo)) { //신규배송지
				if (isEmpty($form.find('[name="dlvyInfo.dlvyUserNm"]').val())) {
					modooAlert('수령인 이름을 입력하세요!');
					return false;
				} else if (isEmpty($form.find('[name="ordName"]').val())) {
					modooAlert('주문자의 이름을 입력해주세요.');
					return false;
				} else if (isEmpty($form.find('[name="ordEmail1"]').val()) || isEmpty($form.find('[name="ordEmail2"]').val())) {
					modooAlert('주문자의 이메일을 정확히 입력해주세요.');
					return false;
				} else if (isEmpty($form.find('[name="ordTelno1"]').val()) || $form.find('[name="ordTelno1"]').val().length < 3 || ($form.find('[name="ordTelno1"]').val() != '010' && $form.find('[name="ordTelno1"]').val() != '011')) {
					modooAlert('휴대전화번호만 입력 가능합니다.');
					return false;
				} else if (isEmpty($form.find('[name="ordTelno2"]').val()) || $form.find('[name="ordTelno2"]').val().length < 4) {
					modooAlert('주문자 연락처 두번째 자리를 정확히 입력하세요!');
					return false;
				} else if (isEmpty($form.find('[name="ordTelno3"]').val()) || $form.find('[name="ordTelno3"]').val().length < 4) {
					modooAlert('주문자 연락처 마지막 자리를 정확히  입력하세요!');
					return false;
				} else if (isEmpty($form.find('[name="dlvyInfo.telno1"]').val()) || $form.find('[name="dlvyInfo.telno1"]').val().length < 2) {
					modooAlert('수령인 연락처 첫번째 자리를 정확히 입력하세요!');
					return false;
				} else if (isEmpty($form.find('[name="dlvyInfo.telno2"]').val()) || $form.find('[name="dlvyInfo.telno2"]').val().length < 4) {
					modooAlert('수령인 연락처 두번째 자리를 정확히 입력하세요!');
					return false;
				} else if (isEmpty($form.find('[name="dlvyInfo.telno3"]').val()) || $form.find('[name="dlvyInfo.telno3"]').val().length < 4) {
					modooAlert('수령인 연락처 마지막 자리를 정확히 입력하세요!');
					return false;
				} else if (isEmpty($form.find('[name="dlvyInfo.dlvyZip"]').val()) || isEmpty($form.find('[name="dlvyInfo.dlvyAdres"]').val())) {
					modooAlert('우편번호를 입력하세요!');
					return false;
				} else if (isEmpty($form.find('[name="dlvyInfo.dlvyAdresDetail"]').val())) {
					modooAlert('상세 주소를 입력하세요!');
					return false;
					/*}else if(isEmpty($form.find('[name="dlvyInfo.dlvyMssage1"]').val()) && isEmpty($form.find('[name="dlvyInfo.dlvyMssage2"]').val())) {
                        alert('배송메세지를 입력하세요!');
                        return false;*/
				} else if (!$form.find('[name="indvdlinfoAgreAt"]').is(':checked')) {
					modooAlert('개인정보 제 3자 제공 동의 후 주문이 가능합니다.');
					return false;
				} else if (!$form.find('[name="purchsCndAgreAt"]').is(':checked')) {
					modooAlert('구매조건 확인 및 결제대행 서비스 약관 동의 후 주문이 가능합니다.');
					return false;
				}else if($('input:radio:checked[name="payment"]').val() == 'cardBill' && $('#payCard').val() == '0'){
					$('input[name=cardNo]').val('');
					$('input[name=cardUsgpd]').val('');
					$('input[name=cardPassword]').val('');
					$('input[name=brthdy]').val('');
					$('.stplat').parent('label').removeClass('ui-state-active');
					$('#allStplat').parent('label').removeClass('ui-state-active');
					popOpen('cardWrite');
					stplatCheck();
					return false;
				};

				var isAnswerFlag = true;
				$('.gitemAnswer').each(function (index) {
					if (isEmpty($(this).val())) {
						isAnswerFlag = false;
					}
				});

				if (isAnswerFlag == false) {
					modooAlert('업체 요청사항을 입력하세요!');
					return false;
				}
			} else { // 기존 배송지

			}
		}
		return true;
	}

	//쿠폰 상품 1개일 때 valildation
	function couponValidation() {
		if (isEmpty($form.find('[name="ordName"]').val())) {
			modooAlert('주문자의 이름을 입력해주세요.');
			return false;
		} else if (isEmpty($form.find('[name="ordEmail1"]').val()) || isEmpty($form.find('[name="ordEmail2"]').val())) {
			modooAlert('주문자의 이메일을 정확히 입력해주세요.');
			return false;
		} else if (isEmpty($form.find('[name="ordTelno1"]').val()) || ($form.find('[name="ordTelno1"]').val() != '010' && $form.find('[name="ordTelno1"]').val() != '011')) {
			modooAlert('휴대전화번호만 입력 가능합니다.');
			return false;
		} else if (isEmpty($form.find('[name="ordTelno2"]').val()) || $form.find('[name="ordTelno2"]').val().length < 4) {
			modooAlert('주문자 연락처 두번째 자리를 정확히 입력하세요!');
			return false;
		} else if (isEmpty($form.find('[name="ordTelno3"]').val()) || $form.find('[name="ordTelno3"]').val().length < 4) {
			modooAlert('주문자 연락처 마지막 자리를 정확히  입력하세요!');
			return false;
		} else if (!$form.find('[name="indvdlinfoAgreAt"]').is(':checked')) {
			modooAlert('개인정보 제 3자 제공 동의 후 주문이 가능합니다.');
			return false;
		} else if (!$form.find('[name="purchsCndAgreAt"]').is(':checked')) {
			modooAlert('구매조건 확인 및 결제대행 서비스 약관 동의 후 주문이 가능합니다.');
			return false;
		}else if($('input:radio:checked[name="payment"]').val() == 'cardBill' && $('#payCard').val() == '0'){
			$('input[name=cardNo]').val('');
			$('input[name=cardUsgpd]').val('');
			$('input[name=cardPassword]').val('');
			$('input[name=brthdy]').val('');
			$('.stplat').parent('label').removeClass('ui-state-active');
			$('#allStplat').parent('label').removeClass('ui-state-active');
			popOpen('cardWrite');
			stplatCheck();
			return false;
		};

		var isAnswerFlag = true;
		$('.gitemAnswer').each(function (index) {
			if (isEmpty($(this).val())) {
				isAnswerFlag = false;
			}
		});

		if (isAnswerFlag == false) {
			modooAlert('업체 요청사항을 입력하세요!');
			return false;
		}
		return true;
	}


	/*	//포인트 결제체크 해제
        $(document).on('change', 'input[name="pointUseAt"]', function () {
            var totalPoint = totalPc + leftPoint;
            //포인트결제체크
            //포인트가 총 금액보다 작을때
            if (totalPc >= leftPoint) {
                if ($(this).is(':checked')) {
                    leftPoint = Number($('#leftPoint').data('leftpoint'));//이지웰포인트
                    var cardPc = 0;
                    if ($('#islandChk').val() == 'Y') {
                        leftPoint = totalPc - leftPoint - islandDlvyPc;
                        cardPc = totalPc - leftPoint;
                    } else {
                        leftPoint = totalPc - leftPoint;
                        cardPc = totalPc - leftPoint;
                    }
                    $("#leftPoint").empty();
                    $("#leftPoint").append('잔여 포인트 <em>0</em> 원');
                    $(".pointPayPc").text(leftPoint.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ","));
                    $('.cardPayPc').text(cardPc.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ","));
                    $('#payMethod').val('both');
                } else {
                    leftPoint = Number($('#leftPoint').data('leftpoint'));//이지웰포인트
                    if ($('#islandChk').val() == 'Y') {
                        leftPoint = totalPc - leftPoint - islandDlvyPc;
                    } else {
                        leftPoint = totalPc - leftPoint;
                    }
                    $("#leftPoint").empty();
                    $("#leftPoint").append('잔여 포인트 <em>' + leftPoint.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",") + '</em> 원');
                    $(".pointPayPc").text("0");
                    $('.cardPayPc').text(totalPc.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ","));
                    $('#payMethod').val('card');
                }
                //총가격이 포인트보다 작을때
            } else if (totalPc < leftPoint) {
                if ($(this).is(':checked')) {
                    $("#leftPoint").empty();
                    $("#leftPoint").append('잔여 포인트 <em>' + leftPoint.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",") + '</em> 원');
                    $(".pointPayPc").text(totalPc.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ","));
                    $('.cardPayPc').text("0");
                    $('#payMethod').val('point');
                } else {
                    $("#leftPoint").empty();
                    $("#leftPoint").append('잔여 포인트 <em>' + totalPoint.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",") + '</em> 원');
                    $(".pointPayPc").text("0");
                    $('.cardPayPc').text(totalPc.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ","));
                    $('#payMethod').val('card');
                }
                //결제체크해제
            }
        });*/

	// 주문하기 - PG결제 창 오픈
		$(document).on('submit', '#sendOrderPgForm', function (e) {
            e.preventDefault();
            $self = $(this);
            var actionUrl = $(this).attr('action');
            var method = $(this).attr('method');
            var cardId = $('#payCard').val();
            if (isCoupon) {
                if (!couponValidation()) {
                    return false;
                }
            } else {
                if (!validator()) {
                    return false;
                }
            }
            var $self = $(this);
            modooConfirm('주문하시겠습니까?', '', function (result) {
                $("#inicisFrm").remove();

                if (result) {
                    $('.loading').show();
                    $self.ajaxSubmit({
                        url: actionUrl,
                        type: method,
                        dataType: 'html',
                        success: function (result) {
                            $("body").append(result);
                            $('.loading').hide();
                        }
                    });
                }
            });
        });

	/*// 주문하기
	$(document).on('submit', '#sendOrderForm', function(e) {
		e.preventDefault();
		$self = $(this);
		var actionUrl = $(this).attr('action');
		var method=$(this).attr('method');
		var cardId = $('#payCard').val();
		/!*
		if(isCoupon){
			if(!couponValidation()){
				return false;
			}
		}else{
			if(!validator()) {
				return false;
			}
		}
		*!/
		var $self = $(this);
		//이니시스 일반결제 용
		$('.loading').show();
		$self.ajaxSubmit({
			url: actionUrl,
			type: method,
			dataType:'json',
			success: function(result) {
				if(result.success) {
					window.location.href=CTX_ROOT+result.redirectUrl+"?orderGroupNo="+result.data.orderGroupNo;
				}else{
					modooAlert(result.message);
					$('.site-body').remove('.loading');
					$('.loading').hide();
				}
			}
		});
	});*/
	// 주문하기
	$(document).on('submit', '#sendOrderForm', function (e) {
		e.preventDefault();
		$self = $(this);
		var actionUrl = $(this).attr('action');
		var method = $(this).attr('method');
		var cardId = $('#payCard').val();
		var payment = $('input:radio:checked[name="payment"]').val();//payMethod(카드,계좌이체,실시간,포인트단건)*!/*/
		//var payment = 'cardPayment';//payMethod(카드,계좌이체,실시간,포인트단건)
		var payMethod = $('#payMethod').val();//payMethod(이니시스,모듈)

		if (isCoupon) {
			if (!couponValidation()) {
				return false;
			}
		} else {
			if (!validator()) {
				return false;
			}
		}

		var selectCard = $('#payCard').val();


		var $self = $(this);
		//이니시스 일반결제 용
		modooConfirm('주문하시겠습니까?','', function(result) {
			if(result) {
				if(payment == 'cardBill'){//이니시스api(정기카드결제)
					popOpen('passwordChk');
					var btn = document.getElementById('passwordChkBtn');
					btn.onclick = function(){
						var password = $('#passwordChk').val();
						$.ajax({
							url:'/shop/goods/checkPassword.json',
							enctype: 'multipart/form-data',
							type:'post',
							data:{
								password:password,
								cardId:cardId
							},
							dataType:'json',
							success:function(result){
								if(result.success){
									popClose('passwordChk');
									sbsOrderAjax();
								}else{
									modooAlert('비밀번호가 맞지 않습니다.');
								}
							}
						})
					}
				}else if(payment == 'cardPayment'){//이니시스 모듈
					if(isEmpty($('input[name="goPayMethod"]').val())){
						modooAlert('간편결제 방식을 선택해주세요.');
						return false;
					}
					//$('.loading').show();
					actionUrl = "/shop/goods/sendOrderPayment.json"
					$self.ajaxSubmit({
						url: actionUrl,
						type: method,
						enctype: 'multipart/form-data',
						dataType:'json',
						success: function(result) {
							if(result.success) {
								if(isMobile() || isModooApp()){
									inisisMbilePopup(result.data);
								}else{
									inisisModuleSubmit(result.data);
								}
							}else{
								modooAlert(result.message);
							}
						}
					});
				}
			}
		});

		/*if(isEmpty($('input[name="goPayMethod"]').val())){
			modooAlert('간편결제 방식을 선택해주세요.');
			return false;
		}
		//$('.loading').show();
		actionUrl = "/shop/goods/sendOrderPayment.json";
		$self.ajaxSubmit({
			url: actionUrl,
			type: method,
			enctype: 'multipart/form-data',
			dataType:'json',
			success: function(result) {
				if(result.success) {
					if(isMobile() || isModooApp()){
						inisisMbilePopup(result.data);
					}else{
						inisisModuleSubmit(result.data);
					}
				}else{
					modooAlert(result.message);
				}
			}
		});*/
	});

	//빌링(주문통신)
	var sbsOrderAjax=function (){
		var $self = $('#sendOrderForm');
		var actionUrl = $self.attr('action');
		var method=$self.attr('method');
		$('.loading').show();
		$self.ajaxSubmit({
			url: actionUrl,
			type: method,
			enctype: 'multipart/form-data',
			success: function(result) {
				if (result.success) {
					window.location.href = CTX_ROOT + result.redirectUrl + "?orderGroupNo=" + result.data.orderGroupNo + "&cartno=" + result.data.cartNo;
				} else {
					modooAlert(result.message);
					$('.site-body').remove('.loading');
					$('.loading').hide();
				}
			}
		});
	}

	//이니시스 모듈 모바일,pc 구분
	$(document).on('click','[name="payment"]',function(){
		var previousValue = $(this).val();
		if(previousValue=="cardPayment"){
			$("#payment1").hide();
			if(isMobile() || isModooApp()){
				$("#payment3").show();
			}else{
				$("#payment2").show();
			}
		}else{
			$("#payment2").hide();
			$("#payment3").hide();
			$("#payment1").show();
		}
	});

	//이니시스 모듈 주문 방법 선택
	$(document).on('change','.goPayMethod',function(){
		$('input[name="goPayMethod"]').val($(this).val());
	});

	//이니시스 모듈 결제(PC)
	var inisisModuleSubmit = function(success){
		console.log(success);
		$('body #SendPayForm_id').remove();
		let form = document.createElement("form");
		form.setAttribute("method","POST");
		form.setAttribute("enctype","multipart/form-data");
		form.setAttribute("id","SendPayForm_id");

		let version = document.createElement("input");
		version.setAttribute('type','hidden');
		version.setAttribute('name','version');
		version.setAttribute('value','1.0');

		let mid = document.createElement("input");
		mid.setAttribute('type','hidden');
		mid.setAttribute('name','mid');
		mid.setAttribute('value',success.mid);

		let goodname = document.createElement("input");
		goodname.setAttribute('type','hidden');
		goodname.setAttribute('name','goodname');
		goodname.setAttribute('value',success.goodname);

		let oid = document.createElement("input");
		oid.setAttribute('type','hidden');
		oid.setAttribute('name','oid');
		oid.setAttribute('value',success.oid);

		let price = document.createElement("input");
		price.setAttribute('type','hidden');
		price.setAttribute('name','price');
		price.setAttribute('value',success.price);

		let currency = document.createElement("input");
		currency.setAttribute('type','hidden');
		currency.setAttribute('name','currency');
		currency.setAttribute('value','WON');

		let buyertel = document.createElement("input");
		buyertel.setAttribute('type','hidden');
		buyertel.setAttribute('name','buyertel');
		buyertel.setAttribute('value',success.buyertel);

		let buyeremail = document.createElement("input");
		buyeremail.setAttribute('type','hidden');
		buyeremail.setAttribute('name','buyeremail');
		buyeremail.setAttribute('value',success.buyeremail);

		let timestamp = document.createElement("input");
		timestamp.setAttribute('type','hidden');
		timestamp.setAttribute('name','timestamp');
		timestamp.setAttribute('value',success.timestamp);

		let signature = document.createElement("input");
		signature.setAttribute('type','hidden');
		signature.setAttribute('name','signature');
		signature.setAttribute('value',success.signature);

		let returnUrl = document.createElement("input");
		returnUrl.setAttribute('type','hidden');
		returnUrl.setAttribute('name','returnUrl');
		returnUrl.setAttribute('value',success.returnUrl);

		let buyername = document.createElement("input");
		buyername.setAttribute('type','hidden');
		buyername.setAttribute('name','buyername');
		buyername.setAttribute('value',success.buyername);

		let mKey = document.createElement("input");
		mKey.setAttribute('type','hidden');
		mKey.setAttribute('name','mKey');
		mKey.setAttribute('value',success.mKey);

		let gopaymethod = document.createElement("input");
		gopaymethod.setAttribute('type','hidden');
		gopaymethod.setAttribute('name','gopaymethod');
		gopaymethod.setAttribute('value',success.gopaymethod);

		let pageViewType = document.createElement("input");
		pageViewType.setAttribute('type','hidden');
		pageViewType.setAttribute('name','pageViewType');
		pageViewType.setAttribute('value',"");

		let closeUrl = document.createElement("input");
		closeUrl.setAttribute('type','hidden');
		closeUrl.setAttribute('name','closeUrl');
		closeUrl.setAttribute('value',success.closeUrl);

		let popupUrl = document.createElement("input");
		popupUrl.setAttribute('type','hidden');
		popupUrl.setAttribute('name','popupUrl');
		popupUrl.setAttribute('value',success.popupUrl);

		let acceptmethod = document.createElement("input");
		acceptmethod.setAttribute('type','hidden');
		acceptmethod.setAttribute('name','acceptmethod');
		acceptmethod.setAttribute('value',success.acceptmethod);

		let merchantData = document.createElement("input");
		merchantData.setAttribute('type','hidden');
		merchantData.setAttribute('name','merchantData');
		merchantData.setAttribute('value',success.merchantData);


		form.appendChild(acceptmethod);
		form.appendChild(version);
		form.appendChild(mKey);
		form.appendChild(closeUrl);
		form.appendChild(merchantData);
		form.appendChild(popupUrl);
		form.appendChild(goodname);
		form.appendChild(returnUrl);
		form.appendChild(pageViewType);
		form.appendChild(returnUrl);
		form.appendChild(signature);
		form.appendChild(timestamp);
		form.appendChild(buyeremail);
		form.appendChild(buyertel);
		form.appendChild(currency);
		form.appendChild(price);
		form.appendChild(oid);
		form.appendChild(mid);
		form.appendChild(gopaymethod);
		form.appendChild(buyername);

		document.body.appendChild(form);
		INIStdPay.pay('SendPayForm_id');

	}


	//이니시스 모듈 결제(모바일)
	var inisisMbilePopup = function(success){

		$('body #mobileweb').remove();
		let form = document.createElement("form");
		form.setAttribute("method","POST");
		form.setAttribute("id","mobileweb");
		form.acceptCharset = "euc-kr"

		let mid = document.createElement("input");
		mid.setAttribute('type','hidden');
		mid.setAttribute('name','P_MID');
		mid.setAttribute('value',success.pMid);

		let goodname = document.createElement("input");
		goodname.setAttribute('type','hidden');
		goodname.setAttribute('name','P_GOODS');
		goodname.setAttribute('value',success.pGoods);

		let oid = document.createElement("input");
		oid.setAttribute('type','hidden');
		oid.setAttribute('name','P_OID');
		oid.setAttribute('value',success.pOid);

		let price = document.createElement("input");
		price.setAttribute('type','hidden');
		price.setAttribute('name','P_AMT');
		price.setAttribute('value',success.pAmt);

		let returnUrl = document.createElement("input");
		returnUrl.setAttribute('type','hidden');
		returnUrl.setAttribute('name','P_NEXT_URL');
		returnUrl.setAttribute('value',success.pNextUrl);

		let buyername = document.createElement("input");
		buyername.setAttribute('type','hidden');
		buyername.setAttribute('name','P_UNAME');
		buyername.setAttribute('value',success.pUname);

		let gopaymethod = document.createElement("input");
		gopaymethod.setAttribute('type','hidden');
		gopaymethod.setAttribute('name','P_INI_PAYMENT');
		gopaymethod.setAttribute('value',success.pIniPayment);

		let preserved = document.createElement("input");
		preserved.setAttribute('type','hidden');
		preserved.setAttribute('name','P_RESERVED');
		preserved.setAttribute('value',success.pReserved);

		let notiurl = document.createElement("input");
		notiurl.setAttribute('type','hidden');
		notiurl.setAttribute('name','P_NOTI_URL');
		notiurl.setAttribute('value',success.pNotiUrl);

		let hppmethod = document.createElement("input");
		hppmethod.setAttribute('type','hidden');
		hppmethod.setAttribute('name','P_HPP_METHOD');
		hppmethod.setAttribute('value',success.pHppMethod);

		let pNoti = document.createElement("input");
		pNoti.setAttribute('type','hidden');
		pNoti.setAttribute('name','P_NOTI');
		pNoti.setAttribute('value',success.pNoti);

		form.appendChild(goodname);
		form.appendChild(returnUrl);
		form.appendChild(pNoti);
		form.appendChild(price);
		form.appendChild(oid);
		form.appendChild(mid);
		form.appendChild(gopaymethod);
		form.appendChild(buyername);
		form.appendChild(preserved);
		form.appendChild(notiurl);
		form.appendChild(hppmethod);

		document.body.appendChild(form);

		var pop_title = "contents" ;

		/*window.open("", pop_title) ;
        form.action = success.requestUrl;
        form.target = pop_title;
        form.submit();*/

		/*contents =  window.open("", pop_title);*/
		form.action = success.requestUrl;
		form.target = "_self";
		form.submit();
		//취소시 주문데이터 삭제
		/*	var windowInterval = setInterval(function(){
				if(contents.closed){
					window.clearInterval(windowInterval);
					$.ajax({
						url:'/shop/goods/closeInisis.do',
						data:{orderGroupNo:success.pOid},
						dataType:'json',
						type:'post',
						complete:function(){
							window.location.reload();
						}
					})
				}

			},300)	*/
	}

	// 주문하기 - 기존꺼
	/*
	$(document).on('submit', '#sendOrderForm', function(e) {
		e.preventDefault();
		$self = $(this);
		var actionUrl = $(this).attr('action');
		var method=$(this).attr('method');
		var cardId = $('#payCard').val();
		if(isCoupon){
			if(!couponValidation()){
				return false;
			}
		}else{
			if(!validator()) {
				return false;
			}
		}
		var $self = $(this);
		
		modooConfirm('주문하시겠습니까?','', function(result) {
			if(result) {
				popOpen('passwordChk');
				var btn = document.getElementById('passwordChkBtn');
				btn.onclick = function(){
				var password = $('#passwordChk').val();
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
								popClose('passwordChk');
								$('.loading').show();
								$self.ajaxSubmit({
									url: actionUrl,
									type: method,
									dataType:'json',
									success: function(result) {
										if(result.success) {
											window.location.href=CTX_ROOT+result.redirectUrl+"?orderGroupNo="+result.data.orderGroupNo;
										}else{
											modooAlert(result.message);
											$('.site-body').remove('.loading');
											$('.loading').hide();
										}
									}
								});
							}else{
								modooAlert('비밀번호가 맞지 않습니다.');
							}
						}
					})
				}
			}
		});
		
		/*
		if(!confirm('주문하시겠습니까?')) {
			return false;
		}else{ 
			$('.loading').show();
			$(this).ajaxSubmit({
				url: actionUrl,
				type: method,
				dataType:'json',
				success: function(result) {
					console.log(result);
					if(result.success) {
						window.location.href=CTX_ROOT+result.redirectUrl+"?orderGroupNo="+result.data.orderGroupNo;
						
					}else{
						alert(result.message);
						$('.site-body').remove('.loading');
						$('.loading').hide();
					}
				}
			})
		}
		* /
		
	});
	*/

	/*//패스워드 체크
	function passwordChk(){
		popOpen('passwordChk');
		var isChk = false;
		var password = $('#passwordChk').val();
		var cardId = $('#payCard').val();
		var btn = document.getElementById('passwordChkBtn');
		btn.onclick = function(){
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
						isChk=true;
					}else{
						isChk=false;
					}
				}
			})
		}
		if(isChk){
			return true;
		}else{
			return false;
		}
	}*/


	//기본,신규배송지 선택
	$(document).on('click','#deliveryTab',function(e){
		e.preventDefault();
		var type = $(this).data('type');
		$(this).parent('li').addClass('is-active');
		$(this).parent('li').siblings('li').removeClass('is-active');
		if(type=='exist'){
			$('#delivery2').hide();
			$('#delivery1').show();
			var adresNo=$('input[name=delivery]:checked').data('id');
			dlvyDetail(adresNo);
			$('input[name="dlvyInfo.bassDlvyAt"]').attr('checked',false);
			$('input[name="dlvyInfo.bassDlvyAt"]').val('exist');
			islandDlvyChk('00000');
		}else if(type='new'){
			$('#delivery1').hide();
			$('#delivery2').show();
			$form.find('[name="dlvyInfo.dlvyUserNm"]').val('');
			$form.find('[name="dlvyInfo.dlvyAdresNm"]').val('');
			$form.find('[name="dlvyInfo.telno1"]').val('');
			$form.find('[name="dlvyInfo.telno2"]').val('');
			$form.find('[name="dlvyInfo.telno3"]').val('');
			$form.find('[name="dlvyInfo.dlvyZip"]').val('');
			$form.find('[name="dlvyInfo.dlvyAdres"]').val('');
			$form.find('[name="dlvyInfo.dlvyAdresDetail"]').val('');
			$('#delivery2 [name="dlvyInfo.dlvyMssage"]').val('');
			$('input[name="dlvyInfo.bassDlvyAt"]').parent('label').removeClass('ui-state-active');
			$('input[name="dlvyInfo.bassDlvyAt"]').val('N');
			$('input[name="dlvyInfo.bassDlvyAt"]').attr('checked',false);
			islandDlvyChk('00000');
		}
	})

	//배송지 지정
	$(document).on('change','input[name=delivery]',function(){
		var adresNo = $(this).data('id');
		dlvyDetail(adresNo);
	})

	//배송지 상세	
	function dlvyDetail(adresNo){
		$.ajax({
			url:'/shop/goods/user/dlvyAdresDetail.json',
			data:{adresNo:adresNo},
			dataType:'json',
			success:function(result){
				var item = result.data.dlvyInfo;
				$('#dlvyAdresNm').html(item.dlvyUserNm+' '+'('+item.dlvyAdresNm+')');
				$form.find('[name="dlvyInfo.dlvyUserNm"]').val(item.dlvyUserNm);
				$form.find('[name="dlvyInfo.dlvyAdresNm"]').val(item.dlvyAdresNm);
				$('#dlvyTelno').text(item.telno);
				var telList = item.telno.split('-');
				$form.find('[name="dlvyInfo.telno1"]').val(telList[0]);
				$form.find('[name="dlvyInfo.telno2"]').val(telList[1]);
				$form.find('[name="dlvyInfo.telno3"]').val(telList[2]);
				$('#dlvyZip').text('('+item.dlvyZip+') '+item.dlvyAdres+', '+item.dlvyAdresDetail);
				$form.find('[name="dlvyInfo.dlvyZip"]').val(item.dlvyZip);
				$form.find('[name="dlvyInfo.dlvyAdres"]').val(item.dlvyAdres);
				$form.find('[name="dlvyInfo.dlvyAdresDetail"]').val(item.dlvyAdresDetail);
				islandDlvyChk(item.dlvyZip);
			},
			complete:function(){
				$('input[name="dlvyInfo.bassDlvyAt"]').val('exist');
				$('input[name="dlvyInfo.bassDlvyAt"]').attr('checked',true);
			}
		})
	}


	$(document).on('click','#zipOpenBtn',function(){
		popOpen('adress');
	})

	//주소 적용
	$(document).on('click','#addChoice',function(e){
		e.preventDefault();
		var obj = $('[data-popup]').data('popup','adress');
		var $self = $(this);
		islandDlvyChk('00000');
		var zip = $self.data('zipno');
		var adress = $self.data('roadaddr');
		$form.find('[name="dlvyInfo.dlvyZip"]').val(zip);
		$form.find('[name="dlvyInfo.dlvyAdres"]').val(adress);
		popClose('adress');
		islandDlvyChk(zip);
	});
	//비용추가
	function islandDlvyInsert(isJeju){
		if(isJeju){
			$('#islandChk').val('jeju');
			$('.jejuDlvyPc').each(function(index,item){
				islandDlvyPc += Number($(item).val());
			})
		}else{
			$('#islandChk').val('island');
			$('.islandDlvyPc').each(function(index,item){
				islandDlvyPc += Number($(item).val());
			})
		}
		if(islandDlvyPc!=0){
			$('.price-info-list').append('<li class="islandPc"><cite>추가배송비</cite><div>'+islandDlvyPc+'원</div></li>');
			totalPc+=Number(islandDlvyPc);
			totalPcInsert();
		}
	}

	function totalPcInsert(){
		$('.totAmount').empty();
		$('.totAmount').text(totalPc.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ","));
		if(!isNaN(leftPoint)){
			if(totalPc<leftPoint){
				//총가격이 포인트보다 적을때
				if($('input[name="pointUseAt"]').is(':checked')){
					leftPoint=Number($('#leftPoint').data('leftpoint'));//이지웰포인트
					leftPoint-=Number(islandDlvyPc);
					$('.pointPayPc').empty();
					$('.pointPayPc').text(totalPc.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ","));
					$('#leftPoint').empty();
					$("#leftPoint").append('잔여 포인트 <em>'+leftPoint.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",")+'</em> 원');
				}else{
					$('.cardPayPc').empty();
					$('.cardPayPc').text(totalPc.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ","));
				}
			}else{
				//포인트가 총가격보다 적을때
				leftPoint=Number($('#leftPoint').data('leftpoint'));//이지웰포인트
				var cardPc=totalPc-leftPoint;
				if($('input[name="pointUseAt"]').is(':checked')){
					$('.cardPayPc').empty();
					$('.cardPayPc').text(cardPc.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ","));
				}else{
					$('.cardPayPc').empty();
					$('.cardPayPc').text(cardPc.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ","));
				}
			}
		}else{
			$('.cardPayPc').empty();
			$('.cardPayPc').text(totalPc.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ","));
		}

	}
	//도서산간 체크
	function islandDlvyChk(zipCode){
		var dataJson = {
			'zip' : zipCode
		};
		var isJeju = false;
		if(Number(zipCode)>=63000){
			isJeju=true;
		}
		$.ajax({
			url:CTX_ROOT + '/shop/goods/checkIdsrtsAt.do',
			type:'POST',
			data:dataJson,
			dataType:'json',
			success:function(result){
				if (result.data.count > 0) {
					$('.islandPc').remove();
					islandDlvyInsert(isJeju);
					/*if(Number(zipCode)>=63000){
                        $('#islandChk').val('jeju');
                    }else{
                        $('#islandChk').val('island');
                    }*/
				}else{
					$('.islandPc').remove();
					totalPc=Number(totalPc)-Number(islandDlvyPc);
					islandDlvyPc=Number(0);
					totalPcInsert();
					$('#islandChk').val('N');
					return false;
				}
			}
		});
	}

	$(document).ready(function(){
		//결제 방식(모바일,pc)
		/*if(isMobile() || isModooApp()){
			$("#payment3").show();
		}else{
			$("#payment2").show();
		}*/

		islandDlvyChk(zip);
		//배송
		$('input[name="dlvyInfo.bassDlvyAt"]').val('');
		if($('#deliveryTab').parent('li').hasClass('is-active')){
			if($('#deliveryTab').data('type')=='new'){
				$('input[name="dlvyInfo.bassDlvyAt"]').attr('checked',false);
				$('input[name="dlvyInfo.bassDlvyAt"]').val('N');
			}else if($('#deliveryTab').data('type')=='exist'){
				$('input[name="dlvyInfo.bassDlvyAt"]').val('exist');
			}
		}
		$('input[name="dlvyInfo.bassDlvyAt"]').change(function(e){
			if($('input[name="dlvyInfo.bassDlvyAt"').is(':checked')){
				$('input[name="dlvyInfo.bassDlvyAt"').val('Y');
			}else{
				$('input[name="dlvyInfo.bassDlvyAt"').val('N');
			}
		})
		//포인트유무
		if(leftPoint!=null){
			if(totalPc>=leftPoint){
				$('#payMethod').val('both');
				if(leftPoint=="0"){
					$('input[name="pointUseAt"]').parent('label').removeClass('ui-state-active');
					$('input[name="pointUseAt"]').attr('disabled','disabled');
					$('#payMethod').val('card');
					/*$('.cardPayPc').text(totalPc.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ","));*/
				}
			}else if(totalPc<leftPoint){
				$('#payMethod').val('point');
			}
		}else{
			$('#payMethod').val('card');
		}
		$('[name="dlvyInfo.dlvyMssage"]').val("배송 전에 미리 연락바랍니다.");
	})

	//배송 메세지 선택 
	$(document).on('change','.dlvyMssage',function(){
		var msg = $(this).val();
		if(msg==0){
			$(this).next().show();
			$('[name="dlvyInfo.dlvyMssage"]').val('');
		}else{
			$(this).next().hide();
			$('[name="dlvyInfo.dlvyMssage"]').val(msg);
		}
	})

})();
//전역
var allCheck=false;

//주소 검색	
$(document).on('submit','#form',function(e){

	var obj = $('[data-popup]').data('popup','adress');
	e.preventDefault();
	// 적용예 (api 호출 전에 검색어 체크) 	
	if (!checkSearchedWord(document.form.keyword)) {
		return ;
	}
	var $self = $('#form');

	$self.ajaxSubmit({
		url :"https://www.juso.go.kr/addrlink/addrLinkApiJsonp.do"  //인터넷망
		,type:"post"
		,data:$self.serialize()
		,dataType:"jsonp"
		,crossDomain:true
		,success:function(jsonStr){
			$(".border-list").html("");
			var errCode = jsonStr.results.common.errorCode;
			var errDesc = jsonStr.results.common.errorMessage;
			if(errCode != "0"){
				$('#message').show();
				$('#addTotalCnt').hide();
			}else{
				if(jsonStr != null){
					$('#message').hide();
					$('#addTotalCnt').show();
					$('#addTotalCnt').children('em').text(jsonStr.results.common.totalCount);
					makeListJson(jsonStr);
					if(jsonStr.results.common.totalCount != 0){
						paging(jsonStr.results.common.totalCount,jsonStr.results.common.currentPage);
					}
				}
			}
		}
		,error: function(xhr,status, error){
			modooAlert("에러발생");
		}
	});
})

//주소 목록 넣기
function makeListJson(jsonStr){
	var obj = $('[data-popup]').data('popup','adress');
	var htmlStr = '';
	$(jsonStr.results.juso).each(function(){
		htmlStr += '<li>';
		htmlStr += '<p>['+this.zipNo+']'+this.jibunAddr+'</p>';
		htmlStr += '<p class="fs-sm fc-gr"><span class="label spot3 dlvyAddress">도로명</span> '+this.roadAddr+'</p>';
		htmlStr += '<button type="button" id="addChoice" class="btn-full" data-zipno="'+this.zipNo+'" data-roadaddr="'+this.roadAddr+'"><span class="txt-hide">주소선택</span></button></li>';
		htmlStr += '</li>';
	});
	$(".border-list").html(htmlStr);
	popPosition(obj);
}

//특수문자, 특정문자열(sql예약어의 앞뒤공백포함) 제거
function checkSearchedWord(obj){
	if(obj.value.length >0){
		//특수문자 제거
		var expText = /[%=><]/ ;
		if(expText.test(obj.value) == true){
			modooAlert("특수문자를 입력 할수 없습니다.") ;
			obj.value = obj.value.split(expText).join("");
			return false;
		}

		//특정문자열(sql예약어의 앞뒤공백포함) 제거
		var sqlArray = new Array(
			//sql 예약어
			"OR", "SELECT", "INSERT", "DELETE", "UPDATE", "CREATE", "DROP", "EXEC",
			"UNION",  "FETCH", "DECLARE", "TRUNCATE"
		);

		var regex;
		for(var i=0; i<sqlArray.length; i++){
			regex = new RegExp( sqlArray[i] ,"gi") ;

			if (regex.test(obj.value) ) {
				alert("\"" + sqlArray[i]+"\"와(과) 같은 특정문자로 검색할 수 없습니다.");
				obj.value =obj.value.replace(regex, "");
				return false;
			}
		}
	}
	return true ;
}

function enterSearch(ev) {
	var evt_code = (window.netscape) ? ev.which : event.keyCode;
	if (evt_code == 13) {
		event.keyCode = 0;
		getAddr(); //jsonp사용시 enter검색 
	}
}


//페이징처리
function paging(totalCount,currentPage){

	const totalPage = Math.ceil(totalCount/5); //총 페이지 수 
	const pageGroup = Math.ceil(currentPage/5); //페이지 그룹

	var last = pageGroup * 5; //화면에 보여질 마지막 번호
	if(last > totalPage){
		last = totalPage;

	}
	var first = last - 4; // 화면에 보여질 첫번째 번호
	if(first < 1){
		first=1;
	}

	const foreFront = 1;
	const prev = Number(currentPage)-1;
	const next = Number(currentPage)+1;
	const rearMost = totalPage;

	var htmlStr='';
	//처음으로,이전
	if(prev>0){
		if(first==foreFront){
			htmlStr +='';
		}
		htmlStr +='<li class="ppv"><a href="#none" onclick="movePageZipPop('+foreFront+');" title="처음으로"><span class="txt-hide">처음으로</span></a></li>';
		htmlStr +='<li class="pv"><a href="#none" onclick="movePageZipPop('+prev+');" title="이전"><span class="txt-hide">이전</span></a></li>';

	}
	//페이지 set
	for(var i=first; i<=last; i++){
		if(currentPage == i ){
			htmlStr += '<li class="is-active"><a href="#none" onclick="movePageZipPop('+i+');" title="to '+i+' page">'+i+'</a></li>';
		}else{
			htmlStr += '<li><a href="#none" onclick="movePageZipPop('+i+');" title="to '+i+' page">'+i+'</a></li>'
		}
	}

	//마지막으로,다음
	if(last<totalPage){
		if(last==totalPage){
			htmlStr+='';
		}
		htmlStr += '<li class="fw"><a href="#none"  onclick="movePageZipPop('+next+');" title="다음"><span class="txt-hide">다음</span></a></li>';
		htmlStr += '<li class="ffw"><a href="#none" onclick="movePageZipPop('+rearMost+');"  title="끝으로"><span class="txt-hide">끝으로</span></a></li>';
	}

	$('.paging').empty();
	$('.paging').html(htmlStr);
}
//페이지 이동
function movePageZipPop(currentPage){
	$('input[name="currentPage"]').val(currentPage);
	$('#form').trigger('submit');

}

$('input[name="keyword"]').keyup(function(){
	$('input[name="currentPage"]').val(1);
});

//최근 배송지 삭제
$(document).on('click','.dlvyDelBtn',function(e){
	e.preventDefault();
	var dlvyNo = $(this).data('id');
	var $self = $(this);

	modooConfirm('배송지를 삭제하시겠습니까?','배송지 삭제',function(result){
		if(result){
			$.ajax({
				url:'/shop/goods/user/deleteAddress.do',
				data:{dadresNo:dlvyNo},
				dataType:'json',
				type:'POST',
				success:function(result){
					modooAlert(result.message);
					$self.parent('li').remove();
				}
			});
		}else{
			return false;
		}
	});
});

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
		modooAlert('카드 번호를 모두 입력해주세요.');
		return false;
	}else if(cardUsgpdNo.length<5){
		modooAlert('카드 유효기간을 정확히 입력해주세요.');
		return false;
	}else if($('input[name=cardPassword]').val()==null || $('input[name=cardPassword]').val().length<2){
		modooAlert('카드비밀번호를 정확히 입력해주세요.')
		return false;
	}else if($('#password1').val().length<6 && $('#password2').val().length<6){
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
	popOpen('cardWrite');
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
						modooAlert(result.message,'확인',function(){
							popClose('cardWrite');
							cardList();
						});
					}else{
						modooAlert(result.message);
					}
				},
			})
		}else{
			return false;
		}
	}else{
		modooAlert('약관에 동의해주세요.');
	}
}
//카드 목록
function cardList(){

	$.ajax({
		url:CTX_ROOT+'/card/cardList.json',
		type:'GET',
		cache: false,
		success:function(result){
			console.log(result);
			$('#payCard').empty();
			$('#payCard').val('');
			var item=result.data.cardList;
			var html='';
			for(var i=0;i<item.length;i++){
				if(item.bassUseAt=='Y'){
					html+='<option value="'+item[i].cardId+'">'+item[i].cardNm+'('+item[i].lastCardNo+')</option>';
					$('#payCard').append(html);
				}
				html+='<option value="'+item[i].cardId+'">'+item[i].cardNm+'('+item[i].lastCardNo+')</option>';
			}
			$('#payCard').append(html);
		}
	})
}

