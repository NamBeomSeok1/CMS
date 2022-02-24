var parentGridGoods;

(function() {

	var cmpnyPageIndex = 1;
	var imgObjs = {
		"bestThumbnail": null
		, "bestImgPc": null
		, "bestImgMob": null
	};
	
	$('#expsrBeginDe').datetimepicker({
		locale: 'ko',
		format: 'YYYY-MM-DD',
		defaultDate : new Date()
	});
	
	$('#expsrEndDe').datetimepicker({
		locale: 'ko',
		format: 'YYYY-MM-DD',
		defaultDate : new Date()
	});

    /** 이미지 미리보기 */
    $(document).on('change', '#bestThumbnail', function(e) {
		if(this.files && this.files[0]) {
			if ( /\.(jpe?g|png|gif)$/i.test(this.files[0].name) ) {
				imgObjs['bestThumbnail'] = this.files[0];
				var reader = new FileReader();
				
				reader.onload = function(e) {

					$("#bestThumbnailResult").css({
						'background-image': 'url(' + e.target.result +')'
						, 'background-position': 'center center'
						, 'background-origin' : 'padding-box'
						, 'background-size': 'contain'
						, 'background-repeat': 'no-repeat'
					});

					$("#bestThumbnail").val('');
					
				}
				reader.readAsDataURL(this.files[0]);
			}
		}
	});
	
	$(document).on('change', '#bestImgPc', function(e) {
		if(this.files && this.files[0]) {
			if ( /\.(jpe?g|png|gif)$/i.test(this.files[0].name) ) {
				imgObjs['bestImgPc'] = this.files[0];
				var reader = new FileReader();
				
				reader.onload = function(e) {

					$("#bestImgPcResult").css({
						'background-image': 'url(' + e.target.result +')'
						, 'background-position': 'center center'
						, 'background-origin' : 'padding-box'
						, 'background-size': 'contain'
						, 'background-repeat': 'no-repeat'
					});

					$("#delete-bestImgPc").html('<button type="button" onclick="deleteImg(\'bestImgPc\');" class="btn btn-area">삭제</button>');
					$("#bestImgPc").val('');
					
				}
				reader.readAsDataURL(this.files[0]);
				
			}
		}
	});
	
	$(document).on('change', '#bestImgMob', function(e) {
		if(this.files && this.files[0]) {
			if ( /\.(jpe?g|png|gif)$/i.test(this.files[0].name) ) {
				imgObjs['bestImgMob'] = this.files[0];
				var reader = new FileReader();
				
				reader.onload = function(e) {

					$("#bestImgMobResult").css({
						'background-image': 'url(' + e.target.result +')'
						, 'background-position': 'center center'
						, 'background-origin' : 'padding-box'
						, 'background-size': 'contain'
						, 'background-repeat': 'no-repeat'
					});

					$("#delete-bestImgMob").html('<button type="button" onclick="deleteImg(\'bestImgMob\');" class="btn btn-area">삭제</button>');
					$("#bestImgMob").val('');
					
				}
				reader.readAsDataURL(this.files[0]);
				
			}
		}
	});

	saveBestInfo = function(mode) {
		console.log(mode);

		var actionUrl = {
			'insert' : CTX_ROOT + '/decms/best/registBest.json',
			'update' : CTX_ROOT + '/decms/best/modifyBest.json',
		}
		
		var bestNo = $('#bestNo').val();
		var bestTyCode = $('#bestTyCode').val();
		var bestUrl = $('#bestUrl').val();
		var reprsntSj = $('#reprsntSj').val();
		var reprsntText = $('#reprsntText').val();
		var actvtyAt = $('#actvtyAt').val();
		var prtnrId = $('#prtnrId').val();
		var expsrOrdr = $('#expsrOrdr').val();
		var expsrBeginDe = $('input[name="expsrBeginDe"]').val();
		var expsrEndDe = $('input[name="expsrEndDe"]').val();
		
		function validation() {
			
			if (bestUrl.length == 0) {
				alert('대표URL을 입력해 주세요.');
				return false;
			}
			else if (reprsntSj.length == 0) {
				alert('대표 제목을 입력해 주세요.');
				return false;
			}
			else if (reprsntText.length == 0) {
				alert('대표 문구를 입력해 주세요.');
				return false;
			}
			else if (expsrBeginDe.length == 0) {
				alert('노출 시작일을 입력해 주세요.');
				return false;
			}
			else if (expsrEndDe.length == 0) {
				alert('노출 종료일을 입력해 주세요.');
				return false;
			}
			else if (actvtyAt.length == 0) {
				alert('노출 상태를 선택해 주세요.');
				return false;
			}
			else if (!imgObjs['bestThumbnail'] && !$('#bestThumbnail').css('background-image')) {
				alert('리스트 썸네일을 등록해 주세요.');
				return false;
			}
			
			return true;
		}
		
		if (validation()) {
			var formData = new FormData();
			formData.append('bestNo', bestNo);
			formData.append('bestTyCode', bestTyCode);
			formData.append('bestUrl', bestUrl);
			formData.append('reprsntSj', reprsntSj);
			formData.append('reprsntText', reprsntText);
			formData.append('actvtyAt', actvtyAt);
			formData.append('prtnrId', prtnrId);
			formData.append('expsrOrdr', expsrOrdr);
			formData.append('expsrBeginDe', new Date(expsrBeginDe));
			formData.append('expsrEndDe', new Date(expsrEndDe));
			formData.append("bestThumbnailPath", imgObjs["bestThumbnail"]);
			formData.append("bestImgPcPath", imgObjs["bestImgPc"]);
			formData.append("bestImgMobPath", imgObjs["bestImgMob"]);
			
			$.ajax({
				url:actionUrl[mode],
				type:'POST',
				data:formData,
				dataType:'json',
				cache: false,
				enctype:'multipart/form-data',
				processData: false,
				contentType: false,
				success:function(result){
					if (result.success) {
						
					}
					
				}				
			});
		}

	}
	
	deleteImg = function(key) {
		var bestNo = $('#bestNo').val();
		var dataJson = {
			'bestNo' : bestNo
			, 'imageType' : key
		};
		
		$.ajax({
			url:CTX_ROOT + '/decms/best/deleteBestImg.json',
			type:'POST',
			data:dataJson,
			dataType:'json',
			cache: false,
			success:function(result){
				if (result.success) {
					imgObjs[key] = null;
					console.log(imgObjs);
					$('#' + key + 'Result').empty();
					$('#' + key + 'Result').css({'background-image' : ''});
					$('#delete-' + key).empty();
					
					alert('삭제되었습니다.');
				}

			}				
		});

	}

	$(document).on('click', '#bestUrl', function() {
		var bestTyCode = $('#bestTyCode').val();
		if (bestTyCode.length == 0) {
			alert('대표 유형을 선택하세요.');
			$('#bestTyCode').focus();
		} else if (bestTyCode == 'B') {
			$('#brandSearchModal').modal('show');
		} else if (bestTyCode == 'E') {
			$('#eventSearchModal').modal('show');
		} else if (bestTyCode == 'T') {
			$('#themeSearchModal').modal('show');
		} else if (bestTyCode == 'P') {
			// 기획전 검색 팝업
		} else if (bestTyCode == 'G') {
			// 공동구매 검색 팝업
		}
	});
	
	addBrandBest = function(brandId) {
		var brandUrl = '/shop/goods/brandGoodsList.do?searchGoodsBrandId=' + brandId;
		$('#bestUrl').val(brandUrl);
		$('.modal').modal('hide');
	}
	
	addEventBest = function(eventUrl) {
		$('#bestUrl').val(eventUrl);
		$('.modal').modal('hide');
	}
	
	addThemeBest = function(themeNo) {
		//$('#bestUrl').val(themeNo);
		$('.modal').modal('hide');
	}
	
	deleteUrl = function() {
		$('#bestUrl').val('');
	}

})();