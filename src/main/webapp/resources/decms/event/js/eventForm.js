var parentGridGoods;

(function() {

	var cmpnyPageIndex = 1;
	var imgObjs = {
		"eventThumbnail": null
		, "eventBannerImg": null
		, "eventMainImgPc": null
		, "eventMainImgMob": null
		, "eventDetailImg": null
		, "eventBrandImgPc": null
		, "eventBrandImgMobile": null
	};
	
	$('#eventBeginDt').datetimepicker({
		locale: 'ko',
		format: 'YYYY-MM-DD',
		defaultDate : new Date()
	});
	
	$('#eventEndDt').datetimepicker({
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
			{ header: '적용유형', name: 'eventTyCode', width:300, align: 'center'
			/*, formatter: function(item){
				var text = '없음';
				if (item.value == 'EV01') {
					text = '한 ID당 상품 한 번만 구매 가능';
				}
				console.log(item.row);
				return '<a class="btn btn-sm btn-light" onclick="editEventTyCode(\'' + item.row.rowKey +'\');">' + text + '</a>';	
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
			/*{ header: '적용범위', name: 'eventRangeSeCode', width:100, align: 'center', formatter: 'listItemText',
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
		getEventGoodsList();
		//getImageInfo();
	});

	getEventGoodsList = function() {
		var eventNo = $('#eventNo').val();
		
		$.ajax({
			url:CTX_ROOT + '/decms/event/eventGoodsList.json',
			type:'get',
			data:{'eventNo' : eventNo},
			dataType:'json',
			cache: false,
			contentType:'application/json',
			success:function(result){
				gridGoods.resetData(result.data.list);
			}				
		});
	}
	
	editEventTyCode = function(rowKey) {
		$('#rowKey').val(rowKey);
		$('#editEventTyPop').modal('show');
	}
	
	changeEventTyCode = function() {
		var rowKey = $('#rowKey').val();
		var value = $('#eventTyCode').val();
		console.log(rowKey, value);
		gridGoods.setValue(rowKey, 'eventTyCode', value, false);
		$('#editEventTyPop').modal('hide');
		gridGoods.resetData(gridGoods.getData());
	}
	
	/*getImageInfo = function() {
		
		var eventThumbnail = $('#eventThumbnailOrg').val();
		var eventMainImgPc = $('#eventThumbnailOrg').val();
		var eventMainImgMob = $('#eventThumbnailOrg').val();
		
		console.log(eventThumbnail, eventMainImgPc, eventMainImgMob);
		
		imgObjs['eventThumbnail'] = eventThumbnail;
		imgObjs['eventMainImgPc'] = eventMainImgPc;
		imgObjs['eventMainImgMob'] = eventMainImgMob;
	
	}*/

    /** 이미지 미리보기 */
    $(document).on('change', '#eventThumbnail', function(e) {
		if(this.files && this.files[0]) {
			if ( /\.(jpe?g|png|gif)$/i.test(this.files[0].name) ) {
				imgObjs['eventThumbnail'] = this.files[0];
				var reader = new FileReader();
				
				reader.onload = function(e) {

					$("#eventThumbnailResult").css({
						'background-image': 'url(' + e.target.result +')'
						, 'background-position': 'center center'
						, 'background-origin' : 'padding-box'
						, 'background-size': 'contain'
						, 'background-repeat': 'no-repeat'
					});

					//$("#delete-eventThumbnail").html('가로 453 X 세로 260<button type="button" onclick="deleteImg(\'eventThumbnail\');" class="btn btn-area">삭제</button>');
					$("#eventThumbnail").val('');
					
				}
				reader.readAsDataURL(this.files[0]);
			}
		}
	});
	
	$(document).on('change', '#eventMainImgPc', function(e) {
		if(this.files && this.files[0]) {
			if ( /\.(jpe?g|png|gif)$/i.test(this.files[0].name) ) {
				imgObjs['eventMainImgPc'] = this.files[0];
				var reader = new FileReader();
				
				reader.onload = function(e) {

					$("#eventMainImgPcResult").css({
						'background-image': 'url(' + e.target.result +')'
						, 'background-position': 'center center'
						, 'background-origin' : 'padding-box'
						, 'background-size': 'contain'
						, 'background-repeat': 'no-repeat'
					});

					$("#delete-eventMainImgPc").html('<button type="button" onclick="deleteImg(\'eventMainImgPc\');" class="btn btn-area">삭제</button>');
					$("#eventMainImgPc").val('');
					
				}
				reader.readAsDataURL(this.files[0]);
				
			}
		}
	});
	
	$(document).on('change', '#eventMainImgMob', function(e) {
		if(this.files && this.files[0]) {
			if ( /\.(jpe?g|png|gif)$/i.test(this.files[0].name) ) {
				imgObjs['eventMainImgMob'] = this.files[0];
				var reader = new FileReader();
				
				reader.onload = function(e) {

					$("#eventMainImgMobResult").css({
						'background-image': 'url(' + e.target.result +')'
						, 'background-position': 'center center'
						, 'background-origin' : 'padding-box'
						, 'background-size': 'contain'
						, 'background-repeat': 'no-repeat'
					});

					$("#delete-eventMainImgMob").html('<button type="button" onclick="deleteImg(\'eventMainImgMob\');" class="btn btn-area">삭제</button>');
					$("#eventMainImgMob").val('');
					
				}
				reader.readAsDataURL(this.files[0]);
				
			}
		}
	});

	saveEventInfo = function(mode) {
		console.log(mode);

		var actionUrl = {
			'insert' : CTX_ROOT + '/decms/event/registEvent.json',
			'update' : CTX_ROOT + '/decms/event/modifyEvent.json',
		}
		
		var eventNo = $('#eventNo').val();
		var eventSj = $('#eventSj').val();
		var cmpnyId = $('#cmpnyId').val();
		var eventBeginDt = $('input[name="eventBeginDt"]').val();
		var eventEndDt = $('input[name="eventEndDt"]').val();
		var endAt = $('#endAt').val();
		var prtnrId = $('#prtnrId').val();
		var eventCn = $('#eventCn').val();
		var eventCnt = $('#eventCnt').val();
		var eventUrl = $('#eventUrl').val();
		
		function validation() {
		
			if (eventSj.length == 0) {
				alert('이벤트명을 입력해 주세요.');
				return false;
			}
			else if (eventBeginDt.length == 0) {
				alert('이벤트 시작일시를 입력해 주세요.');
				return false;
			}
			else if (eventEndDt.length == 0) {
				alert('이벤트 종료일시를 입력해 주세요.');
				return false;
			}
			else if (endAt.length == 0) {
				alert('이벤트 상태를 선택해 주세요.');
				return false;
			}
			/*else if (prtnrId.length == 0) {
				alert('이벤트 노출구분을 선택해 주세요.');
				return false;
			}*/
			else if (eventCn.length == 0) {
				alert('이벤트 설명을 입력해 주세요.');
				return false;
			}
			else if (!imgObjs['eventThumbnail'] && !$('#eventThumbnail').css('background-image')) {
				alert('이벤트 썸네일을 등록해 주세요.');
				return false;
			}
			
			return true;
		}
		
		if (validation()) {
			var formData = new FormData();
			formData.append('eventNo', eventNo);
			formData.append('eventSj', eventSj);
			formData.append('cmpnyId', cmpnyId);
			formData.append('eventBeginDt', new Date(eventBeginDt));
			formData.append('eventEndDt', new Date(eventEndDt));
			formData.append('endAt', endAt);
			formData.append('prtnrId', prtnrId);
			formData.append('eventCnt', eventCnt);
			formData.append('eventUrl', eventUrl);
			formData.append('eventCn', eventCn);
			formData.append("eventThumbnailPath", imgObjs["eventThumbnail"]);
			formData.append("eventBannerImgPath", imgObjs["eventBannerImg"]);
			formData.append("eventMainImgPcPath", imgObjs["eventMainImgPc"]);
			formData.append("eventMainImgMobPath", imgObjs["eventMainImgMob"]);
			formData.append("eventDetailImgPath", imgObjs["eventDetailImg"]);
			formData.append("eventBrandImgPcPath", imgObjs["eventBrandImgPc"]);
			formData.append("eventBrandImgMobilePath", imgObjs["eventBrandImgMobile"]);
			
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
						saveEventGoods(result.data.nextEventNo);
					}
					
				}				
			});
		}

	}
	
	saveEventGoods = function(eventNo) {
		
		var goodsArr = [];
		for (i = 0; i<gridGoods.getRowCount(); i++) {
			console.log(gridGoods.getData()[i]);
			var row = gridGoods.getData()[i];
			var rowItem = {
				'eventNo' : eventNo
				, 'goodsId' : row.goodsId
				, 'eventTyCode' : row.eventTyCode
			};
			goodsArr.push(rowItem);
		}

		$.ajax({
			url:CTX_ROOT + '/decms/event/registEventGoods.json?eventNo=' + eventNo,
			type:'POST',
			data:JSON.stringify(goodsArr),
			dataType:'json',
			cache: false,
			contentType:'application/json',
			success:function(result){
				console.log(result);
				//alert(11);
				location.href = CTX_ROOT + '/decms/event/eventManage.do';
			}				
		});
	}
	
	deleteImg = function(key) {
		var eventNo = $('#eventNo').val();
		var dataJson = {
			'eventNo' : eventNo
			, 'imageType' : key
		};
		
		$.ajax({
			url:CTX_ROOT + '/decms/event/deleteEventImg.json',
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