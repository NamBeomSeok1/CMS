var parentGridGoods;

(function() {

	var cmpnyPageIndex = 1;
	var imgObjs = {
		"themaThumbnail": null
		, "themaBannerImg": null
		, "themaMainImgPc": null
		, "themaMainImgMob": null
		, "themaDetailImg": null
		, "themaBrandImgPc": null
		, "themaBrandImgMobile": null
	};
	
	$('#themaBeginDt').datetimepicker({
		locale: 'ko',
		format: 'YYYY-MM-DD',
		defaultDate : new Date()
	});
	
	$('#themaEndDt').datetimepicker({
		locale: 'ko',
		format: 'YYYY-MM-DD',
		defaultDate : new Date()
	});

	const gridGoods = new tui.Grid({
		el: document.getElementById('data-grid'),
		rowHeaders: ['checkbox'],
		bodyHeight: 400,
		scrollY: true,
		columns: [
			{ header: '상품코드', name: 'goodsId', width:150, align: 'center'},
			{ header: '상품명', name: 'goodsNm', width:250, align: 'center'},
			{ header: '적용유형', name: 'themaTyCode', width:300, align: 'center'
			/*, formatter: function(item){
				var text = '없음';
				if (item.value == 'EV01') {
					text = '한 ID당 상품 한 번만 구매 가능';
				}
				console.log(item.row);
				return '<a class="btn btn-sm btn-light" onclick="editThemaTyCode(\'' + item.row.rowKey +'\');">' + text + '</a>';	
			}*/
			 , formatter: 'listItemText'
			 , editor: {
	            type: 'select',
	            options: {
	              listItems: [
	                { text: '', value: '' },
	                { text: '한 ID당 상품 한 번만 구매 가능', value: 'EV01' }
	              ]
	            }
          		}
			}
			/*{ header: '적용범위', name: 'themaRangeSeCode', width:100, align: 'center', formatter: 'listItemText',
	          editor: {
	            type: 'select',
	            options: {
	              listItems: [
	              	{ text: '==미선택==', value: '' },
	                { text: '해당CP', value: 'CP' },
	                { text: '전체', value: 'ALL' }
	              ]
	            }
	        	}
            },
			{ header: '관리', name: 'manage', width:100, align: 'center'},
			{ header: '구분', name: 'h5', width:100, align: 'center'}*/
		],
		columnOptions: {
			frozenCount: 1,
			frozenBorderWidth: 1,
			resizable: true
		}
	});
	
	parentGridGoods = gridGoods;

	tui.Grid.applyTheme('striped');	

	$(document).ready(function() {
		getThemaGoodsList();
		//getImageInfo();
	});

	getThemaGoodsList = function() {
		var themaNo = $('#themaNo').val();
		
		$.ajax({
			url:CTX_ROOT + '/decms/thema/themaGoodsList.json',
			type:'get',
			data:{'themaNo' : themaNo},
			dataType:'json',
			cache: false,
			contentType:'application/json',
			success:function(result){
				gridGoods.resetData(result.data.list);
			}				
		});
	}
	
	editThemaTyCode = function(rowKey) {
		$('#rowKey').val(rowKey);
		$('#editThemaTyPop').modal('show');
	}
	
	changeThemaTyCode = function() {
		var rowKey = $('#rowKey').val();
		var value = $('#themaTyCode').val();
		console.log(rowKey, value);
		gridGoods.setValue(rowKey, 'themaTyCode', value, false);
		$('#editThemaTyPop').modal('hide');
		gridGoods.resetData(gridGoods.getData());
	}
	
	/*getImageInfo = function() {
		
		var themaThumbnail = $('#themaThumbnailOrg').val();
		var themaMainImgPc = $('#themaThumbnailOrg').val();
		var themaMainImgMob = $('#themaThumbnailOrg').val();
		
		console.log(themaThumbnail, themaMainImgPc, themaMainImgMob);
		
		imgObjs['themaThumbnail'] = themaThumbnail;
		imgObjs['themaMainImgPc'] = themaMainImgPc;
		imgObjs['themaMainImgMob'] = themaMainImgMob;
	
	}*/

    /** 이미지 미리보기 */
    $(document).on('change', '#themaThumbnail', function(e) {
		if(this.files && this.files[0]) {
			if ( /\.(jpe?g|png|gif)$/i.test(this.files[0].name) ) {
				imgObjs['themaThumbnail'] = this.files[0];
				var reader = new FileReader();
				
				reader.onload = function(e) {

					$("#themaThumbnailResult").css({
						'background-image': 'url(' + e.target.result +')'
						, 'background-position': 'center center'
						, 'background-origin' : 'padding-box'
						, 'background-size': 'contain'
						, 'background-repeat': 'no-repeat'
					});

					//$("#delete-themaThumbnail").html('가로 453 X 세로 260<button type="button" onclick="deleteImg(\'themaThumbnail\');" class="btn btn-area">삭제</button>');
					$("#themaThumbnail").val('');
					
				}
				reader.readAsDataURL(this.files[0]);
			}
		}
	});
	
	$(document).on('change', '#themaMainImgPc', function(e) {
		if(this.files && this.files[0]) {
			if ( /\.(jpe?g|png|gif)$/i.test(this.files[0].name) ) {
				imgObjs['themaMainImgPc'] = this.files[0];
				var reader = new FileReader();
				
				reader.onload = function(e) {

					$("#themaMainImgPcResult").css({
						'background-image': 'url(' + e.target.result +')'
						, 'background-position': 'center center'
						, 'background-origin' : 'padding-box'
						, 'background-size': 'contain'
						, 'background-repeat': 'no-repeat'
					});

					$("#delete-themaMainImgPc").html('<button type="button" onclick="deleteImg(\'themaMainImgPc\');" class="btn btn-area">삭제</button>');
					$("#themaMainImgPc").val('');
					
				}
				reader.readAsDataURL(this.files[0]);
				
			}
		}
	});
	
	$(document).on('change', '#themaMainImgMob', function(e) {
		if(this.files && this.files[0]) {
			if ( /\.(jpe?g|png|gif)$/i.test(this.files[0].name) ) {
				imgObjs['themaMainImgMob'] = this.files[0];
				var reader = new FileReader();
				
				reader.onload = function(e) {

					$("#themaMainImgMobResult").css({
						'background-image': 'url(' + e.target.result +')'
						, 'background-position': 'center center'
						, 'background-origin' : 'padding-box'
						, 'background-size': 'contain'
						, 'background-repeat': 'no-repeat'
					});

					$("#delete-themaMainImgMob").html('<button type="button" onclick="deleteImg(\'themaMainImgMob\');" class="btn btn-area">삭제</button>');
					$("#themaMainImgMob").val('');
					
				}
				reader.readAsDataURL(this.files[0]);
				
			}
		}
	});

	saveThemaInfo = function(mode) {
		console.log(mode);

		var actionUrl = {
			'insert' : CTX_ROOT + '/decms/thema/registThema.json',
			'update' : CTX_ROOT + '/decms/thema/modifyThema.json',
		}
		
		var themaNo = $('#themaNo').val();
		var themaSj = $('#themaSj').val();
		var cmpnyId = $('#cmpnyId').val();
		var themaBeginDt = $('input[name="themaBeginDt"]').val();
		var themaEndDt = $('input[name="themaEndDt"]').val();
		var endAt = $('#endAt').val();
		var prtnrId = $('#prtnrId').val();
		var themaCn = $('#themaCn').val();
		var themaCnt = $('#themaCnt').val();
		var themaUrl = $('#themaUrl').val();
		var themaSn = $('#themaSn').val();
		
		function validation() {
		
			if (themaSj.length == 0) {
				alert('테마명을 입력해 주세요.');
				return false;
			}
			else if (themaBeginDt.length == 0) {
				alert('테마 시작일시를 입력해 주세요.');
				return false;
			}
			else if (themaEndDt.length == 0) {
				alert('테마 종료일시를 입력해 주세요.');
				return false;
			}
			else if (endAt.length == 0) {
				alert('테마 상태를 선택해 주세요.');
				return false;
			}
			else if (themaSn.length == 0) {
				alert('테마 순서를 선택해 주세요.');
				return false;
			}
			/*else if (prtnrId.length == 0) {
				alert('테마 노출구분을 선택해 주세요.');
				return false;
			}*/
			else if (themaCn.length == 0) {
				alert('테마 설명을 입력해 주세요.');
				return false;
			}
			else if (!imgObjs['themaThumbnail'] && !$('#themaThumbnail').css('background-image')) {
				alert('테마 썸네일을 등록해 주세요.');
				return false;
			}
			
			return true;
		}
		
		if (validation()) {
			var formData = new FormData();
			formData.append('themaNo', themaNo);
			formData.append('themaSj', themaSj);
			formData.append('themaBeginDt', new Date(themaBeginDt));
			formData.append('themaEndDt', new Date(themaEndDt));
			formData.append('endAt', endAt);
			formData.append('prtnrId', prtnrId);
			formData.append('themaCnt', themaCnt);
			formData.append('themaUrl', themaUrl);
			formData.append('themaCn', themaCn);
			formData.append('themaSn', themaSn);
			formData.append("themaThumbnailPath", imgObjs["themaThumbnail"]);
			formData.append("themaMainImgPcPath", imgObjs["themaMainImgPc"]);
			formData.append("themaMainImgMobPath", imgObjs["themaMainImgMob"]);
			
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
						saveThemaGoods(result.data.nextThemaNo);
					}
					
				}				
			});
		}

	}
	
	saveThemaGoods = function(themaNo) {
		
		var goodsArr = [];
		for (i = 0; i<gridGoods.getRowCount(); i++) {
			console.log(gridGoods.getData()[i]);
			var row = gridGoods.getData()[i];
			var rowItem = {
				'themaNo' : themaNo
				, 'goodsId' : row.goodsId
				, 'themaTyCode' : row.themaTyCode
			};
			goodsArr.push(rowItem);
		}

		$.ajax({
			url:CTX_ROOT + '/decms/thema/registThemaGoods.json?themaNo=' + themaNo,
			type:'POST',
			data:JSON.stringify(goodsArr),
			dataType:'json',
			cache: false,
			contentType:'application/json',
			success:function(result){
				console.log(result);
				//alert(11);
				location.href = CTX_ROOT + '/decms/thema/themaManage.do';
			}				
		});
	}
	
	deleteImg = function(key) {
		var themaNo = $('#themaNo').val();
		var dataJson = {
			'themaNo' : themaNo
			, 'imageType' : key
		};
		
		$.ajax({
			url:CTX_ROOT + '/decms/thema/deleteThemaImg.json',
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
	
	deleteCmpny = function() {
		$('#cmpnyId').val('');
		$('#cmpnyNm').val('');
	}
	
	//추천상품 삭제
	$(document).on('click', '.btnDeleteRecomend', function(e) {
		e.preventDefault();
		var checkedList = gridGoods.getCheckedRowKeys();		
		for (var i=0; i<checkedList.length; i++) {
			gridGoods.removeRow(checkedList[i]);
		}
		
	});

})();