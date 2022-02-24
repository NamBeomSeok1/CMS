(function() {
	var keywordList = [];
	
	var editor = ace.edit('mvpSourcEditor');
	editor.setTheme('ace/theme/twilight');
    editor.session.setMode('ace/mode/html');
    editor.setOption('wrap',true);
    editor.setAutoScrollEditorIntoView(true);
    
    editor.getSession().on('change', function() {
    	$('#mvpSourcCn').val(editor.getSession().getValue());
    	$('#viewMvpSource').html(editor.getSession().getValue());
    });
	
	function reIndexGoodsItem(gitemType) {
		var listNm = '';
		if(gitemType == 'D') {
			listNm = 'dGitemList';
		}else if(gitemType == 'A') {
			listNm = 'aGitemList';
		}else if(gitemType == 'F') {
			listNm = 'fGitemList';
		}else if(gitemType == 'Q') {
			listNm = 'qGitemList';
		}
		$('[data-gitem="' + gitemType + '"]').find('.optn-item').each(function(index) {
			$(this).find('.gitemId').attr('name', listNm + '[' + index + '].gitemId');
			$(this).find('.gitemSn').attr('name', listNm + '[' + index + '].gitemSn').val(index+1);
			$(this).find('.gitemNm').attr('name', listNm + '[' + index + '].gitemNm');
			$(this).find('.gitemPc').attr('name', listNm + '[' + index + '].gitemPc');
			$(this).find('.gitemSttusCode').attr('name', listNm + '[' + index + '].gitemSttusCode');
			//$(this).find('.gitemCo').attr('name','goodsItemList[' + index + '].gitemCo');
		});
		
		if($('[data-gitem="' + gitemType + '"]').find('.optn-item').length == 1) {
			$el = $('.goodsItem').first();
			
			$el.find('.gitemId').val('');
			$el.find('.gitemNm').val('');
			$el.find('.gitemSn').val('1');
			//$el.find('.gitemCo').val('1');
		}
	}
	
	function reIndexGoodsKeyword() {
		$('.keyword-item').each(function(index) {
			$(this).find('.goodsKeywordNo').attr('name', 'goodsKeywordList[' + index + '].goodsKeywordNo');
			$(this).find('.keywordNm').attr('name', 'goodsKeywordList[' + index + '].keywordNm');
		});
	}
	
	// 업체 선택 후 브랜드 목록 호출
	getGoodsFormBrandList = function(cmpnyId) {
		/*
		$.aj-------------ax({
			url: CTX_ROOT + '/decms/shop/goods/goodsBrandList.json',
			type: 'get',
			data: {searchCmpnyId: cmpnyId},
			dataType: 'json',
			success: function(result) {
				if(result.message) {
					bootbox.alert({ title: '확인', message: result.message, size: 'small' }); 
				}
				
				if(result.success) {
					$('#brandId').html('<option value="">선택</option>');
					result.data.list.forEach(function(item) {
						$('<option value="' + item.brandId + '">' + item.brandNm + '</option>').appendTo('#brandId');
					});
				}
			}
		});
		*/
		jsonResultAjax({
			url: CTX_ROOT + '/decms/shop/goods/goodsBrandList.json',
			type: 'get',
			data: {searchCmpnyId: cmpnyId},
			dataType: 'json',
			callback: function(result) {
				
				if(result.success) {
					$('#brandId').html('<option value="">선택</option>');
					result.data.list.forEach(function(item) {
						$('<option value="' + item.brandId + '">' + item.brandNm + '</option>').appendTo('#brandId');
					});
				}
			}
		});
	};
	
	// 업체 선택 후 택배사 목록 호출
	getGoodsHdryCmpnyList = function(cmpnyId) {
		jsonResultAjax({
			url: CTX_ROOT + '/decms/shop/hdry/goodsCmpnyHdryList.json',
			type: 'get',
			data: {searchCmpnyId: cmpnyId},
			dataType: 'json',
			callback: function(result) {
				if(result.success) {
					$('#hdryId').html('<option value="">선택</option>');
					result.data.list.forEach(function(item) {
						$('<option value="' + item.hdryId + '">' + item.hdryNm + '</option>').appendTo('#hdryId');
					});
				}
			}
		});
	};
	
	// 상품 선택 후 관련 추천상품 추가 (goodsFormRecomend.js)
	getGoodsInfo = function(goodsId) {
		/*
		modooAjax({
			url: CTX_ROOT + '/decms/shop/goods/goodsInfo.json',
			method: 'get',
			param: {goodsId: goodsId},
			callback: function(result) {
				var goodsInfo = result.data.goods;
				var $template = $('#recomendGoodsTemplate');
				var $target = $('#recomend-list');
				var tag = $template.html();
				
				tag = tag.replace(/{SN}/gi, $target.find('.recomend-item').length+1); 
				tag = tag.replace(/{INDEX}/gi, $target.find('.recomend-item').length); 
				tag = tag.replace(/{GOODS_ID}/gi, goodsInfo.goodsId); 
				tag = tag.replace(/{ORIGIN}/gi, goodsInfo.goodsTitleImagePath); 
				tag = tag.replace(/{THUMB}/gi, goodsInfo.goodsTitleImageThumbPath); 
				tag = tag.replace(/{TIT}/gi, goodsInfo.goodsNm); 
				tag = tag.replace(/{PC}/gi, goodsInfo.goodsPc); 
				
				$target.append($(tag));

				$('#recomendListModal').modal('hide');
			}
		});
		*/
		jsonResultAjax({
			url: CTX_ROOT + '/decms/shop/goods/goodsInfo.json',
			method: 'get',
			data: {goodsId: goodsId},
			callback: function(result) {
				var goodsInfo = result.data.goods;
				var $template = $('#recomendGoodsTemplate');
				var $target = $('#recomend-list');
				var tag = $template.html();
				
				tag = tag.replace(/{SN}/gi, $target.find('.recomend-item').length+1); 
				tag = tag.replace(/{INDEX}/gi, $target.find('.recomend-item').length); 
				tag = tag.replace(/{GOODS_ID}/gi, goodsInfo.goodsId); 
				tag = tag.replace(/{ORIGIN}/gi, goodsInfo.goodsTitleImagePath); 
				tag = tag.replace(/{THUMB}/gi, goodsInfo.goodsTitleImageThumbPath); 
				tag = tag.replace(/{TIT}/gi, goodsInfo.goodsNm); 
				tag = tag.replace(/{PC}/gi, goodsInfo.goodsPc); 
				
				$target.append($(tag));

				$('#recomendListModal').modal('hide');
			}
		});
	};
	
	// 재휴사 목록
	getPrtnrCmpnyList = function(cmpnyId) {
		/*
		modooAjax({
			url: CTX_ROOT + '/decms/shop/goods/prtnrCmpnyList.json',
			method: 'get',
			param: {cmpnyId: cmpnyId},
			callback: function(result) {
				var list = result.data.list;
				$('#pcmapngId').html('');
				if(list.length > 0) {
					list.forEach(function(item) {
						$('#pcmapngId').append('<option value="' + item.pcmapngId + '">' + item.prtnrNm + '</option>');
					});
				}else {
					$('#pcmapngId').html('=제휴사가 없습니다.=');
				}
			}
		});
		*/
		jsonResultAjax({
			url: CTX_ROOT + '/decms/shop/goods/prtnrCmpnyList.json',
			method: 'get',
			data: {cmpnyId: cmpnyId},
			callback: function(result) {
				var list = result.data.list;
				$('#pcmapngId').html('');
				if(list.length > 0) {
					list.forEach(function(item) {
						var pcCnt = 0;
						if(!isEmpty(item.pcmapngId)) {
							pcCnt++;
							$('#pcmapngId').append('<option value="' + item.pcmapngId + '">' + item.prtnrNm + '</option>');
						}
						if(pcCnt == 0) {
							$('#pcmapngId').html('<option value="">=제휴사가 없습니다.=</option>');
						}
					});
				}else {
					$('#pcmapngId').html('<option value="">=제휴사가 없습니다.=</option>');
				}
			}
		});
	};

	//수강권상품 보이기
	$(document).on('change','input:checkbox[name="vchUseAt"]',function(){
		if($(this).prop('checked')){
			$('#vchArea').show();
		}else{
			$('#vchArea').hide();
		}
	});


	// 키워드 등록
	function writeKeyword(keyword) {
		$('#keyword').val('');

		if(keywordList.indexOf(keyword) >= 0 ) {
			bootbox.alert({ title: '확인', message: '중복 키워드가 있습니다.', size: 'small' }); 
			return false;
		}

		keywordList.push(keyword);
		
		var $template = $('#goodsKeywordTemplate');
		var tag = $template.html();
		
		tag = tag.replace(/{KEYWORD}/gi, keyword);
		tag = tag.replace(/{INDEX}/gi, $('#keyword-list').find('.keyword-item').length); 
		$(tag).appendTo('#keyword-list');
	}
	
	// 상품이미지 업로드 Dialog 
	function dialogGoodsImage(target, actionUrl) {
	
		//var actionUrl = '/decms/fms/imageFileUpload.json';
		bootbox.dialog({
			title: '상품 이미지 등록',
			message: '<form id="goodsImageForm" name="goodsImageForm" method="post" enctype="multipart/form-data" action="'+actionUrl+'" >'
					+'	<input type="hidden" id="orign-img-url" value=""/>'
					+'	<input type="hidden" id="thumb-img-url" value=""/>'
					+'	<input type="file" id="goodsImageAtchFile" name="atchFile" class="form-control" data-target="' + target + '" multiple/>'
					+'	<div class="dialog-contents"></div>'
					+'</form>',
			buttons: {
				close: {
					label: '닫기',
					className: 'btn-secondary btn-sm',
					callback: function(){
						this.modal('hide');
					}
				},
				ok: {
					label: '적용',
					className: 'btn-primary btn-sm',
					callback: function(){
						this.modal('hide');
					}
				}
			}
		});
	}
	
	// 상품이미지 추가
	function addGoodsImage($target, fileList) {
		var $template = $('#goodsImageTemplate');
		
		if($target.attr('id') == 'goods-dc-image-list') {
			$template = $('#gdcImageTemplate');
		}else if($target.attr('id') == 'goods-image-list') {
			$template = $('#gnrImageTemplate');
		}else if($target.attr('id') == 'goods-event-image-list') {
			$template = $('#evtImageTemplate');
		}
		
		/*
		else if($target.attr('id') == 'goods-mob-image-list') {
			$template = $('#mobImageTemplate');
		}else if($target.attr('id') == 'goods-ban-image-list') {
			$template = $('#banImageTemplate');
		}
		*/
		
		var templateTag = $template.html();
		
		fileList.forEach(function(item) {
			var tag = templateTag;
			tag = tag.replace(/{SN}/gi, $target.find('.img-item').length+1); 
			tag = tag.replace(/{INDEX}/gi, $target.find('.img-item').length); 
			tag = tag.replace(/{ORIGIN}/gi, item.orignFileUrl); 
			tag = tag.replace(/{THUMB}/gi, item.thumbUrl); 
			tag = tag.replace(/{LRGE}/gi, item.lrgeFileUrl); 
			tag = tag.replace(/{MIDDL}/gi, item.middlFileUrl); 
			tag = tag.replace(/{SMALL}/gi, item.smallFileUrl); 
			$target.append($(tag));
		});
		
	
		/*
		tag = tag.replace(/{SN}/gi, $target.find('.img-item').length+1); 
		tag = tag.replace(/{INDEX}/gi, $target.find('.img-item').length); 
		//tag = tag.replace(/{ORIGIN}/gi, fileInfo.orignImagePath); 
		//tag = tag.replace(/{THUMB}/gi, fileInfo.thumbImagePath); 
		tag = tag.replace(/{ORIGIN}/gi, fileInfo.orignImagePath); 
		tag = tag.replace(/{THUMB}/gi, fileInfo.thumbImagePath); 
		tag = tag.replace(/{LRGE}/gi, fileInfo.lrgeFileUrl); 
		tag = tag.replace(/{MIDDL}/gi, fileInfo.thumbImagePath); 
		tag = tag.replace(/{SMALL}/gi, fileInfo.thumbImagePath); 
		
		$target.append($(tag));
		*/
		
	}

	// 상품이미지 삭제
	function deleteImg($target, imageSeCode) {
		var actionUrl = $target.attr('href');
		//console.log(imageSeCode);
		//console.log($target);
		if(actionUrl != '#') {
			$target.parents('.img-item').remove();
			jsonResultAjax({
				url: actionUrl,
				method: 'post',
				callback: function(result) {
					if(imageSeCode == 'GDC') {
						gdcRearrangement($('#goods-dc-image-list').find('.img-item'), 'gdcImageList');
					}else if(imageSeCode == 'GNR') {
						gdcRearrangement($('#goods-image-list').find('.img-item'), 'goodsImageList');
					}else if(imageSeCode == 'EVT') {
						gdcRearrangement($('#goods-event-image-list').find('.img-item'), 'evtImageList');
					}
				}
			});
		}else {
			$target.parents('.img-item').remove();
			if(imageSeCode == 'GDC') {
				gdcRearrangement($('#goods-dc-image-list').find('.img-item'), 'gdcImageList');
			}else if(imageSeCode == 'GNR') {
				gdcRearrangement($('#goods-image-list').find('.img-item'), 'goodsImageList');
			}else if(imageSeCode == 'EVT') {
				gdcRearrangement($('#goods-event-image-list').find('.img-item'), 'evtImageList');
			}
			
		}
		
	}
	
	function getSubCategoryList(upperCateId, dp) {
		/*
		modooAjax({
			url: CTX_ROOT + '/decms/shop/goods/goodsCtgryList.json',
			method : 'get',
			param: {searchUpperGoodsCtgryId: upperCateId},
			callback: function(result) {
				if(dp == '1') {
					$('[data-dp=2]').html('<option value="">선택</option>');
					result.data.list.forEach(function(item) {
						$('<option value="'+item.goodsCtgryId+'">' + item.goodsCtgryNm + '</option>').appendTo('[data-dp=2]');
					});
				}else if(dp == '2') {
					$('[data-dp=3]').html('<option value="">선택</option>');
					result.data.list.forEach(function(item) {
						$('<option value="'+item.goodsCtgryId+'">' + item.goodsCtgryNm + '</option>').appendTo('[data-dp=3]');
					});
				}
			}
		});
		*/
		jsonResultAjax({
			url: CTX_ROOT + '/decms/shop/goods/goodsCtgryList.json',
			method : 'get',
			data: {searchUpperGoodsCtgryId: upperCateId},
			callback: function(result) {
				if(dp == '1') {
					$('[data-dp=2]').html('<option value="">선택</option>');
					result.data.list.forEach(function(item) {
						$('<option value="'+item.goodsCtgryId+'">' + item.goodsCtgryNm + '</option>').appendTo('[data-dp=2]');
					});
				}else if(dp == '2') {
					$('[data-dp=3]').html('<option value="">선택</option>');
					result.data.list.forEach(function(item) {
						$('<option value="'+item.goodsCtgryId+'">' + item.goodsCtgryNm + '</option>').appendTo('[data-dp=3]');
					});
				}
			}
		});
	}
	
	function gdcRearrangement($target, listNm) {
		$target.each(function(index,item) {
			var sn = index + 1;
			$(item).find('.goods-num').text(sn);
			$(item).find('.goodsImageNo').attr('name', listNm + '[' + index + '].goodsImageNo');
			$(item).find('.goodsImageSeCode').attr('name', listNm + '[' + index + '].goodsImageSeCode');
			$(item).find('.goodsImageSn').attr('name', listNm + '[' + index + '].goodsImageSn').val(sn);
			$(item).find('.goodsImagePath').attr('name', listNm + '[' + index + '].goodsImagePath');
			$(item).find('.goodsImageThumbPath').attr('name', listNm + '[' + index + '].goodsImageThumbPath');
			$(item).find('.goodsLrgeImagePath').attr('name', listNm + '[' + index + '].goodsLrgeImagePath');
			
			$(item).find('.goodsMiddlImagePath').attr('name', listNm + '[' + index + '].goodsMiddlImagePath');
			$(item).find('.goodsSmallImagePath').attr('name', listNm + '[' + index + '].goodsSmallImagePath');
		});
	}
	function sbsValidate(){
		
			var sbscrpt = null;
			var sbscrptCycle = null;
			var sbscrptWeek = null;
			if($('#week').hasClass('active')==true){
				sbscrpt = 'WEEK';
			}else if($('#month').hasClass('active')==true){
				sbscrpt = 'MONTH';
			}
			if(sbscrpt == 'WEEK') {
				sbscrptCycle = $('input:checkbox[name="sbscrptWeekCycle"]:checked').length;
				sbscrptWeek = $('input:checkbox[name="sbscrptDlvyWd"]:checked').length;
				if(sbscrptCycle==0){
					alert('구독 주기를 선택해주세요.');
					return false;
				}else if(sbscrptWeek==0){
					alert('구독 요일을 선택해주세요.');
					return false;
				}
			}else { // MONTH
				sbscrptCycle = $('input:checkbox[name="sbscrptMtCycle"]:checked').length;
				if(sbscrptCycle==0){
					alert('구독 주기를 선택해주세요.');
					return false;
				}
			}
			return true;
		}
	
	
	// 저장
	function saveGoods() {
		var $form = $('#registForm');
		var actionUrl = $form.attr('action');
		var method = $form.attr('method');
		var formData = new FormData($form[0]);
		
		if(isEmpty($('#goodsFeeRate').val())) {
			if(confirm('수수료율이 없습니다. 진행하시겠습니까?')) {
				$('#goodsFeeRate').val('0');
				var goodsPc = Number($('#goodsPc').val());
				$('#goodsSplpc').val(goodsPc);
			}else {
				return false;
			}
		}
		var kndCode = $('input:radio[name="goodsKndCode"]:checked').val();
		
		if($('#islandDlvyPc').val() == '0') {
			if(!confirm('도서산간 추가배송비가 0원 입니다. 진행하시겠습니까?')) {
				return false;
			}
		}
		
		if(kndCode=='SBS'){
			if(sbsValidate()){
				jsonResultAjax({
					url: actionUrl,
					method: method,
					formData: formData,
					callback: function(result) {
						//console.log(result);
					}
				});
			}
		}else{
			jsonResultAjax({
				url: actionUrl,
				method: method,
				formData: formData,
				callback: function(result) {
					console.log(result);
				}
			})
		}
	}
	
	// 상품항목 삭제
	function deleteGoodsItem(actionUrl, gitemType) {
		jsonResultAjax({
			url: actionUrl,
			method: 'post',
			callback: function(result) {
				reIndexGoodsItem(gitemType);
			}
		});
	}
	
	// 상품키워드 삭제
	function deleteKeyword(actionUrl) {
		jsonResultAjax({
			url: actionUrl,
			method: 'post',
			callback: function(result) {
				reIndexGoodsKeyword();
			}
		});
	}
	
	//카테고리선택
	$(document).on('change', '.selectCategory', function() {
		var dp = $(this).data('dp');

		if(!isEmpty($(this).val())) {
			var upperCateId = $(this).val();
			if(dp == '1') {
				$('[data-dp=3]').html('<option value="">선택</option>');
				getSubCategoryList(upperCateId, dp);
			}else if(dp == '2') {
				getSubCategoryList(upperCateId, dp);
			}else if(dp == '3') {
				//
			}
		}else {
			if(dp == '1') {
				$('[data-dp=3]').html('<option value="">선택</option>');
				$('[data-dp=2]').html('<option value="">선택</option>');
			}else if(dp == '2') {
				$('[data-dp=3]').html('<option value="">선택</option>');
			}
			
		}
	});
	
	// 옵션추가 Click
	$(document).on('click', '.btnAddItem', function(e) {
		e.preventDefault();
		if($('.optn-item').length >= 15) {
			bootbox.alert({ title: '확인', message: '16개 이상 추가 할 수 없습니다.<br/> 관리자에게 문의하세요!', size: 'small' }); 
			return false;
		}
		
		var itemType = $(this).data('target');
		var $target = $('[data-gitem="' + itemType + '"]');
		
		var $template;
		console.log(itemType);
		if(itemType == 'Q') {
			$template = $('#goodsQestnItemTemplate');
		}else {
			$template = $('#goodsItemTemplate');
		}
		var tag = $template.html();
		tag = tag.replace(/{NO}/gi, $target.find('.optn-item').length+1); 
		tag = tag.replace(/{INDEX}/gi,$target.find('.optn-item').length);
		
		
		$target.append($(tag));
		reIndexGoodsItem(itemType);
	});
	
	// 옵션 삭제 Click 
	$(document).on('click', '.btnRemoveGitem', function(e) {
		e.preventDefault();
		var $self = $(this);
		var actionUrl = $(this).attr('href');
		
		var gitemType = $(this).parents('[data-gitem]').data('gitem');

		bootbox.confirm({
			title: '삭제확인',
			message: '삭제하시겠습니까?',
			callback: function(result) {
				if(result) {
					if(isEmpty(actionUrl)){
						$self.parents('.optn-item').remove();
						reIndexGoodsItem(gitemType);;
					}else {
						if($('.optn-item').length == 1) {
							$self.parents('.optn-item').find('.gitemNm').val('');
							$self.parents('.optn-item').find('.gitemCo').val('1');
						}else {
							$self.parents('.optn-item').remove();
							
						}
						deleteGoodsItem(actionUrl, gitemType);
					}
				}
			}
		});
		
	
	});
	
	// 옵션 사용여부 Change
	$(document).on('change', 'input[name=optnUseAt]', function() {
		if($(this).val() == 'Y') {
			$('#optn-type-list').show();
		}else {
			$('#optn-type-list').hide();
		}
	});
	
	// 옵션별 사용여부 Change
	$(document).on('change', '.radio-OptnUseAt', function() {
		var $target = $(this).parents('.goods-optn');
		console.log($target);
		if($(this).val() == 'Y') {
			$target.find('.optn-item').find('input').attr('disabled', false);
			$target.find('.btn').attr('disabled', false).removeClass('disabled');
		}else {
			$target.find('.optn-item').find('input').attr('disabled', true);
			$target.find('.btn').attr('disabled', true).addClass('disabled');
		}
		
	});
	
	
	// 입력 텍스트 초기화 Click
	$(document).on('click', '.btnRemoveTxt', function(e) {
		e.preventDefault();
		var $target = $(this).parents('.input-group').find('input');
		var value = $(this).data('value');
		$target.val(value);
	})
	
	// 키워드 등록 Click
	$(document).on('click', '.btnAddKeyword', function(e) {
		var keyword = $('#keyword').val();
		if(!isEmpty(keyword)) {
			writeKeyword(keyword);
			$('#keyword').focus();
		}
	});

	// 키워드 엔터 Keydown
	$(document).on('keydown', '#keyword', function(e) {
		if(e.keyCode == 13) {
			var keyword = $('#keyword').val();
			if(!isEmpty(keyword)) {
				writeKeyword(keyword);
			}
			e.preventDefault();
		}
	});
	
	// 키워드 삭제 Click
	$(document).on('click', '.btnRemoveKeyword', function(e) {
		e.preventDefault();
		var href = $(this).attr('href');
		var $self = $(this);
		if(href == '#') {
			$self.parent('.keyword-item').remove();
			reIndexGoodsKeyword();
		}else {
			bootbox.confirm({
				title: '삭제확인',
				message: '등록된 키워드 입니다.<br/>삭제하시겠습니까?',
				callback: function(result) {
					if(result) {
						deleteKeyword(href);
						$self.parent('.keyword-item').remove();
					}
				}
			});
		}
	});
	
	// 상품이미지 추가 Click
	$(document).on('click', '.btnAddImg', function(e) {
		e.preventDefault();
		var src = $(this).data('src');
		var target = $(this).data('target');
		dialogGoodsImage(target, src);
	});
	/*
	$(document).on('click', '.btnAddImg', function(e) {
		e.preventDefault();
		var target = $(this).data('target');
		dialogGoodsImage(target);
	});
	*/
	
	// 상품이미지 삭제 Click
	$(document).on('click', '.btnDeleteImg', function(e) {
		e.preventDefault();
		var actionUrl = $(this).attr('href');
		var $self = $(this);
		var imageSeCode = $(this).data('seCode');

		bootbox.confirm({
			title: '삭제확인',
			message: '삭제하시겠습니까?',
			callback: function(result) {
				if(result) {
					deleteImg($self, imageSeCode);
				}
			}
		});
	});
	
	// 상품 이미지 선택
	$(document).on('change', '#goodsImageAtchFile', function(e) {
		e.preventDefault();
		var $form = $(this).parents('form');
		var actionUrl = $form.attr('action');
		var method = $form.attr('method');
		var $target = $($(this).data('target'));
		
		var file = $(this).val();

		if(!isEmpty(file)) {
			$form.ajaxSubmit({
				url: actionUrl,
				type: method,
				dataType: 'json',
				success: function(result) {
					if(result.message) {
						bootbox.alert({ title: '확인', message: result.message, size: 'small' }); 
					}
					
					if(result.success) {
						//var fileInfo = result.data;
						//addGoodsImage($target, fileInfo);
						var fileList = result.data.fileList;
						addGoodsImage($target, fileList);
						bootbox.hideAll();
					}
				}
			});
		}
	});
	
	// 상품 설명 이미지 보기 Click
	$(document).on('click', '.btnImgView', function(e) {
		e.preventDefault();
		popupWindow($(this).attr('href'), $(this).attr('title'), window.screen.width, window.screen.height);
	});
	
	// 이벤트 이미지 정렬
	$('#goods-event-image-list').sortable({
		handle: '.evt-img-header',
		revert: true,
		stop: function(evt, ui) {
			gdcRearrangement($(this).find('.img-item'), 'evtImageList');
		}
	});
	
	// 상품 설명 이미지 정렬
	$('#goods-dc-image-list').sortable({
		handle: '.gdc-img-header',
		revert: true,
		stop: function(evt, ui) {
			gdcRearrangement($(this).find('.img-item'), 'gdcImageList');
		}
	});
	
	// 상품이미지 정렬
	$('#goods-image-list').sortable({
		handle: '.img-header',
		revert: true,
		stop: function(evt, ui) {
			gdcRearrangement($(this).find('.img-item'), 'goodsImageList');
		}
	});
	
	/*
	// PC 대표 이미지 정렬
	$('#goods-pc-image-list').sortable({
		handle: '.pc-img-header',
		revert: true,
		stop: function(evt, ui) {
			gdcRearrangement($(this).find('.img-item'), 'pcImageList');
		}
	});
	
	// 모바일 대표 이미지 정렬
	$('#goods-mob-image-list').sortable({
		handle: '.img-header',
		revert: true,
		stop: function(evt, ui) {
			gdcRearrangement($(this).find('.img-item'), 'mobImageList');
		}
	});

	// 모바일 배너 이미지 정렬
	$('#goods-ban-image-list').sortable({
		handle: '.img-header',
		revert: true,
		stop: function(evt, ui) {
			gdcRearrangement($(this).find('.img-item'), 'banImageList');
		}
	});
	*/
	
	// 구독주기 방법 Click 
	$(document).on('click', '.sbscrptCycleSe', function() {
		var sbscrpt = $(this).data('sbscrpt');
		$('#sbscrptCycleSeCode').val(sbscrpt);
		$('.sbscrptCycleSe').not(this).removeClass('active');
		$(this).addClass('active');
		
		if(sbscrpt == 'WEEK') {
			$('.sbscrptWeek').find('input, select').attr('disabled', false);
			$('.sbscrptMonth').find('input, select, button').attr('disabled', true);
			$('.sbscrptMonth').find('input[type=checkbox]').prop("checked", false);
			$('.sbscrptMonth').find('.btnSbcrptDlvyDay').removeClass('active');
			$('.sbscrptMonth').find('select').val('');
			$('#sbscrptDlvyDay').val('');
		}else { // MONTH
			$('.sbscrptWeek').find('input, select').attr('disabled', true);
			$('.sbscrptMonth').find('input, select, button').attr('disabled', false);
			$('.sbscrptWeek').find('input[type=checkbox]').prop("checked", false);
			$('.sbscrptWeek').find('select').val('');
		}
	});
	
	// 구독 주기 Change
	/*
	$(document).on('change', '.check-sbscrptCicle', function() {
		$('.check-sbscrptCicle').not(this).prop('checked', false);
	});
	*/
	
	// 요일 Change
	/*
	$(document).on('change', 'input[name=sbscrptDlvyWd]', function() {
		$('input[name=sbscrptDlvyWd]').not(this).prop('checked', false);
	});
	*/
	
	//공급가 계산 - 실제 값은 서버에서 계산
	$(document).on('change', '#goodsPc, #goodsFeeRate', function() {
		var goodsPc = Number($('#goodsPc').val());
		var rate = Number($('#goodsFeeRate').val());
		var goodsSplpc = goodsPc - (goodsPc * rate / 100);
		$('#goodsSplpc').val(goodsSplpc);
		
	});
	
	// 배송일 Click
	$(document).on('click', '.btnSbcrptDlvyDay', function(e) {
		e.preventDefault();
		var day = $(this).data('day');
		$('#sbscrptDlvyDay').val(day);
		$('.btnSbcrptDlvyDay').not(this).removeClass('active');
		$(this).addClass('active');
		
	});
	
	// 배송정책 변경
	$(document).on('change', '.dlvyPolicySeCode', function() {
		if($(this).is(':checked')) {
			var code = $(this).val();
			var policyCn = $(this).data('policyCn');

			if(isEmpty(policyCn)) {
				$('#dlvyPolicyCn').val('');
			}else {
				$('#dlvyPolicyCn').val(policyCn);
			}
			
			if(code == 'DP03') {
				$('#dlvyPolicyCn').attr('readonly', false);
			}else {
				$('#dlvyPolicyCn').attr('readonly', true);
			}
		}
	});
	
	//배송비 선택
	$(document).on('change', 'input[name=dlvySeCode]', function() {
		if(this.checked) {
			if($(this).val() == 'DS03') { 
				$('#dlvyPc').attr('readonly', true).val('0');
				$('#freeDlvyPc').attr('readonly', true).val('0');
			}else {
				$('#dlvyPc').attr('readonly',false);
				$('#freeDlvyPc').attr('readonly', false);
			}
			
		}
	});
	
	// 저장 Submit
	$(document).on('submit', '#registForm', function(e) {
		e.preventDefault();
		saveGoods();
	});
	
	//일반상품,쿠폰상품일 때 구독옵션 사용x
	$(document).ready(function(e){
		if($('input:radio[id="goodsKndCode2"]').is(':checked') || $('#readGoodsKndCode').val()=='일반상품'){
			$('input:checkbox[name="exprnUseAt"]').attr('disabled',true);
			$('input[name="exprnPc"]').val(0);
			$('input[name="exprnPc"]').attr('readonly',true);
			$('.sbscrpt-cycle').hide();
			$('.sbscrptWeek').parent().hide();
			$('.sbscrptWeek').find('input[type=checkbox]').prop("checked", false);
			$('.sbscrptWeek').find('select').val('');
			$('.sbscrptMonth').find('input[type=checkbox]').prop("checked", false);
			$('.sbscrptMonth').find('select').val('');
			//$('#optn-type-list .goods-optn:nth-child(5) :input').attr('disabled',true);
			$('#optn-type-list .fOptn').hide();
			$('#goods-coupon').hide();
		}else if($('input:radio[id="goodsKndCode1"]').is(':checked')){
			$('.sbscrptWeek').parent().show();
			$('input:checkbox[name="exprnUseAt"]').removeAttr('disabled');
			$('input[name="exprnPc"]').attr('readonly',false);
			$('#goods-coupon').hide();
		}else if($('input:radio[id="goodsKndCode3"]' ||$('#readGoodsKndCode').val()=='쿠폰상품').is(':checked')) { //쿠폰상품
			$('.sbscrptWeek').parent().hide();
			$('input:checkbox[name="exprnUseAt"]').attr('disabled',true);
			$('input[name="exprnPc"]').val(0);
			$('input[name="exprnPc"]').attr('readonly',true);
			$('.sbscrptWeek').find('input[type=checkbox]').prop("checked", false);
			$('.sbscrptWeek').find('select').val('');
			$('.sbscrptMonth').find('input[type=checkbox]').prop("checked", false);
			$('.sbscrptMonth').find('select').val('');
			$('#goods-coupon').show();
			$('#optn-type-list .fOptn').hide();
		}

		$('input[name="goodsKndCode"]').click(function(){
			if($('input:radio[id="goodsKndCode2"]').is(':checked')){
				$('.sbscrptWeek').parent().hide();
				$('input:checkbox[name="exprnUseAt"]').attr('disabled',true);
				$('input[name="exprnPc"]').attr('readonly',true);
				$('input[name="exprnPc"]').val('');
				//$('#optn-type-list .goods-optn:nth-child(5) :input').attr('disabled',true);
				$('#optn-type-list .fOptn').hide();
				$('.sbscrptWeek').find('input[type=checkbox]').prop("checked", false);
				$('.sbscrptWeek').find('select').val('');
				$('.sbscrptMonth').find('input[type=checkbox]').prop("checked", false);
				$('.sbscrptMonth').find('select').val('');
				$('#goods-coupon').hide();
			}else if($('input:radio[id="goodsKndCode1"]').is(':checked')){
				$('.sbscrptWeek').parent().show();
				//$('#optn-type-list .goods-optn:nth-child(5) :input').attr('disabled',false);
				$('#optn-type-list .fOptn').show();
				$('input:checkbox[name="exprnUseAt"]').removeAttr('disabled');
				$('input[name="exprnPc"]').attr('readonly',false);
				$('#goods-coupon').hide();
			}else if($('input:radio[id="goodsKndCode3"]').is(':checked')) { //쿠폰상품
				$('#optn-type-list .fOptn').hide();
				$('input:checkbox[name="exprnUseAt"]').attr('disabled',true);
				$('input[name="exprnPc"]').attr('readonly',true);
				$('input[name="exprnPc"]').val(0);
				$('.sbscrptWeek').parent().hide();
				$('.sbscrptWeek').find('input[type=checkbox]').prop("checked", false);
				$('.sbscrptWeek').find('select').val('');
				$('.sbscrptMonth').find('input[type=checkbox]').prop("checked", false);
				$('.sbscrptMonth').find('select').val('');
				$('#goods-coupon').show();
			}
		});
	});
	
	
	//체험 구독 체크
	$(document).on('change','input[name="exprnUseAt"]',function(){
		
		var chk = $(this).val();
		if($(this).is(':checked')==true){
			$('#exprnPc').attr('disabled',false);
		}else{
			$('#exprnPc').val(0);
			$('#exprnPc').attr('disabled',true);
		}
	})

	//복수구매할인여부 체크
	$(document).on('change','input[name="compnoDscntUseAt"]',function(){
		
		var chk = $(this).val();
		if($(this).is(':checked')==true){
			$('#compnoDscntPc').attr('disabled',false);
			
		}else{
			$('#compnoDscntPc').val(0);
			$('#compnoDscntPc').attr('disabled',true);
		}
	})
	
	//시중가 노출 여부
	$(document).on('change','input[name="mrktUseAt"]',function(){
		var goodsPc = Number($('input[name="goodsPc"]').val());
		var mrktPc = Number($('input[name="mrktPc"]').val());
		console.log(goodsPc);
		console.log(mrktPc);
		if(isEmpty($('input[name="mrktPc"]').val())){
			alert('시중가를 입력해주세요.');
			$(this).prop('checked',false);
			return false;
		}else if(goodsPc>mrktPc){
			alert('시중가보다 판매가가 더 높습니다.');
			$(this).prop('checked',false);
			return false;
		}
	})
})();