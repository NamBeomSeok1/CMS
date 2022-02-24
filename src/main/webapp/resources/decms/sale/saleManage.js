$(function() {
	/** 컴포넌트 생성 **/
	//제휴사별 테이블 생성
	createTableDnd = function() {
		//var dataJson = {};
		$.ajax({
	        url : CTX_ROOT + '/decms/best/prtnrList.json',
	        type : 'GET',
	        dataType : 'json',
	        success : function(json){
	            for (var i=0; i<json.data.list.length; i++) {
	            	$('#table-' + json.data.list[i].prtnrId + '-tab').bootstrapTable({
	            		onReorderRowsDrop: function (row) {
	            			sortRowOrder();
				       }
	            	});
	            	// 제휴사별 데이터 load
	            	loadTableData(json.data.list[i].prtnrId);
	            }
	        }
	    });		
	}
	
	loadTableData = function(prtnrId) {
		var dataJson = {
			'prtnrId' : prtnrId
		};
		$.ajax({
	        url : CTX_ROOT + '/decms/sale/saleGoodsList.json',
	        type : 'GET',
	        data: dataJson,
	        dataType : 'json',
	        success : function(json){
	        	console.log(json.data.list);
	        	$('#table-' + prtnrId + '-tab').bootstrapTable('load', json.data.list);
	        }
	    });	
	}
	
	createTableDnd();
	
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
	
	/** 이벤트 및 function **/
	sortRowOrder = function() {
		var activeTableId = '#table-' + $('#myTab > li > a.active').attr('id');
		var row_cnt = $(activeTableId).bootstrapTable('getData').length;
		for (var i=0; i < row_cnt; i++) {
			$(activeTableId).bootstrapTable('updateRow', {index: i, row: {'expsrOrdr' : i+1}});
		}
	}
	
	$(document).on('change', '.inputColor', function() {
		var colorVal = $(this).val();
		$(this).parent().parent().find('.hexValue').val(colorVal);
	});
	
	$(document).on('change', '.hexValue', function() {
		var hex = $(this).val();
		$(this).parent().parent().find('.inputColor').val(hex);
	});
	
	addSaleGoods = function(goodsId) {
		
		var activeTableId = '#table-' + $('#myTab > li > a.active').attr('id');
		var row_cnt = $(activeTableId).bootstrapTable('getData').length;
		
		var dataJson = {
			'goodsId' : goodsId
		};
		$.ajax({
	        url : CTX_ROOT + '/decms/shop/goods/goodsInfo.json',
	        type : 'GET',
	        data: dataJson,
	        dataType : 'json',
	        success : function(json){	        	
	        	var goodsInfo = json.data.goods;
	        	var prtnrId = $('#myTab > li > a.active').attr('id').replace('-tab', '');
	        	if (goodsInfo.prtnrId != prtnrId) {
	        		alert('제휴사가 다릅니다.');
	        	} else {
	        		$(activeTableId).bootstrapTable('insertRow', {
		    			index: row_cnt,
		    			row: {
		    				expsrOrdr : row_cnt+1,
		    				goodsId : goodsInfo.goodsId,
		    				prtnrId : goodsInfo.prtnrId,
		    				goodsKndCode : goodsInfo.goodsKndCode,
		    				actvtyAt : 'N',
		    				goodsImage : goodsInfo.goodsTitleImageThumbPath,
		    				cmpnyNm : goodsInfo.cmpnyNm,
		    				goodsNm : goodsInfo.goodsNm,
		    				labelTyCode : '',
							labelText : '',
							labelColor : '',
							labelTextColor : '',
		    				goodsPc : goodsInfo.goodsPc,
		    				registSttusCode : goodsInfo.registSttusCode,
		    				expsrBeginDe : '',
		    				expsrEndDe : '',
		    				edit: ''
		    			}
		    		});

		    		$('#recomendListModal').modal('hide');
	        	}
	        }
	    });	

	}
	
	updateSaleGoodsPop = function(index) {
		var activeTableId = '#table-' + $('#myTab > li > a.active').attr('id');
		var row = $(activeTableId).bootstrapTable('getData')[index];

		$('#goods-index').val(index);
		$('#goodsId').val(row.goodsId);
		$('#goodsNm').val(row.goodsNm);
		$('#goodsImage').attr('src', row.goodsImage);
		$('#labelTyCode').val(row.labelTyCode);
		$('#labelColor').val(row.labelColor);
		$('#labelColorPick').val(row.labelColor);
		$('#labelTextColor').val(row.labelTextColor);
		$('#labelTextColorPick').val(row.labelTextColor);
		$('#labelText').val(row.labelText);
		if (row.expsrBeginDe) {
			$('input[name="expsrBeginDe"]').val(moment(row.expsrBeginDe).format('YYYY-MM-DD')); ;	
		}
		if (row.expsrEndDe) {
			$('input[name="expsrEndDe"]').val(moment(row.expsrEndDe).format('YYYY-MM-DD')); ;	
		}
		$('#actvtyAt').val(row.actvtyAt);
		
		var modalObj = $('#editModal');
		modalObj.modal('show');

	}
	
	$(document).on('click', '.updateRowData', function() {
		var idx = $('#goods-index').val();
		var activeTableId = '#table-' + $('#myTab > li > a.active').attr('id');
		
		if (validationForm()) {
			
			$(activeTableId).bootstrapTable('updateRow', {
		        index: idx,
		        row: {
		        	labelTyCode : $('#labelTyCode').val(),
		        	labelText : $('#labelText').val(),
		        	labelColor : $('#labelColor').val(),
		        	labelTextColor : $('#labelTextColor').val(),
		        	expsrBeginDe : $('input[name="expsrBeginDe"]').val(),
		        	expsrEndDe : $('input[name="expsrEndDe"]').val(),
		        	actvtyAt : $('#actvtyAt').val()
		        }
			});
			
			var modalObj = $('#editModal');
			modalObj.modal('hide');

		}

	});
	
	deleteSaleGoods = function(index) {
		var activeTableId = '#table-' + $('#myTab > li > a.active').attr('id');
		$(activeTableId).bootstrapTable('remove', {
	        field: '$index',
	        values: [index]
	      });
		sortRowOrder();
	}

	saveSaleGoodsData = function() {
		var prtnrId = $('#myTab > li > a.active').attr('id').replace('-tab', '');
		var activeTableId = '#table-' + $('#myTab > li > a.active').attr('id');
		var tb_data = $(activeTableId).bootstrapTable('getData');
		
		if (validationTable()) {
			var goodsArr = [];
			for (i = 0; i<tb_data.length; i++) {
				console.log(tb_data[i]);
				var row = tb_data[i];
				var rowItem = {
					'expsrOrdr' : row.expsrOrdr,
					'goodsId' : row.goodsId,
					'prtnrId' : row.prtnrId,
					'actvtyAt' : row.actvtyAt,
					'labelTyCode' : row.labelTyCode,
					'labelText' : row.labelText,
					'labelColor' : row.labelColor,
					'labelTextColor' : row.labelTextColor,
					'expsrBeginDe' : row.expsrBeginDe,
					'expsrEndDe': row.expsrEndDe
				};
				goodsArr.push(rowItem);
			}

			$.ajax({
				url:CTX_ROOT + '/decms/sale/saveSaleGoods.json?prtnrId=' + prtnrId,
				type:'POST',
				data:JSON.stringify(goodsArr),
				dataType:'json',
				cache: false,
				contentType:'application/json',
				success:function(result){
					console.log(result);
					alert('저장되었습니다.');
					location.reload(true);
				}				
			});			
			
		}
	}
	
	validationForm = function() {
		if (!$('#labelTyCode').val()) {
			alert('라벨 종류는 필수 입력 항목입니다.');
			$('#labelTyCode').focus();
			return false;
		} /*else if (!$('#labelText').val()) {
			alert('라벨 문구는 필수 항목입니다.');
			$('#labelText').focus();
			return false;
		}*/ else if (!$('#labelColor').val()) {
			alert('라벨 색상은 필수 입력 항목입니다.');
			$('#labelColor').focus();
			return false;
		} else if (!$('#labelTextColor').val()) {
			alert('글자 색상은 필수 입력 항목입니다.');
			$('#labelTextColor').focus();
			return false;
		} else if (!$('input[name="expsrBeginDe"]').val()) {
			alert('노출시작일은 필수 입력 항목입니다.');
			$('input[name="expsrBeginDe"]').focus();
			return false;
		} else if (!$('input[name="expsrEndDe"]').val()) {
			alert('노출종료일은 필수 입력 항목입니다.');
			$('input[name="expsrEndDe"]').focus();
			return false;
		}
		
		return true;
	}
	
	validationTable = function() {
		var activeTableId = '#table-' + $('#myTab > li > a.active').attr('id');
		var tb_data = $(activeTableId).bootstrapTable('getData');
		
		for (var i=0; i<tb_data.length; i++) {

			if (!tb_data[i].labelTyCode) {
				alert('라벨 종류는 필수 입력 항목입니다.');
				return false;
				break;
			} else if (!tb_data[i].labelColor) {
				alert('라벨 색상은 필수 입력 항목입니다.');
				return false;
				break;
			} else if (!tb_data[i].labelTextColor) {
				alert('글자 색상은 필수 입력 항목입니다.');
				return false;
				break;
			} else if (!tb_data[i].expsrBeginDe) {
				alert('노출시작일은 필수 항목입니다.');
				return false;
				break;
			} else if (!tb_data[i].expsrEndDe) {
				alert('노출종료일은 필수 항목입니다.');
				return false;
				break;
			}
		}
		
		return true;
	}
	
	/** table formatter **/
	labelTyCodeFormatter = function(value, row) {
		var labelTyCode = {
			'' : '',
			'F' : '첫구독라벨',
			'A' : '증정라벨',
			'S' : '특가라벨',
			'C' : '커스텀라벨'
		}
		return labelTyCode[value];
	}
	
	bgColorFormatter = function(value, row) {
		return '<span class="oi oi-tags" style="font-size: 30px !important; color: ' + value + ';"></span>';
	}
	
	textColorFormatter = function(value, row) {
		return '<span class="oi oi-text" style="font-size: 17px !important; color: ' + value + ';"></span>';
	}
	
	imageFormatter = function(value, row) {
		return '<img src="' + value +'" style="width: 50px; height: 50px;">';
	}
	
	editFormatter = function(value, row, index) {
		var btn = '<button type="button" class="btn btn-sm btn-success" onclick="updateSaleGoodsPop(' + index + ');">수정</button>';
		btn += '<button type="button" class="btn btn-sm btn-danger" onclick="deleteSaleGoods(' + index + ');">삭제</button>';
		return btn;
	}
	
	priceFormatter = function(value, row) {
		if (value) {
			return value.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ',');
		} else {
			return '';	
		}
	}
	
	prtnrFormatter = function(value, row) {
		var prtnr = {
			'PRTNR_0000' : 'B2C',
			'PRTNR_0001' : '이지웰'
		}
		
		return prtnr[value];
	}
	
	goodsKindFormatter = function(value, row) {
		var kind = {
			'SBS' : '구독',
			'GNR' : '일반',
			'CPN' : '쿠폰'
		}
		return kind[value];
	}
	
	sttusFormatter = function(value, row) {
		var sttus = {
			'A' : '처리중',
			'C' : '등록완료',
			'D' : '등록거부',
			'R' : '등록대기'
		}
		return sttus[value];
	}
	
	ynFormatter = function(value, row) {
		var sttus = {
				'Y' : '노출중',
				'N' : '미노출'
			}
		return sttus[value];
	}
	
	dateFormatter = function(value, row) {
		if (value) {
			return moment(value).format('YYYY-MM-DD');	
		} else {
			return '';
		}
	}

  })