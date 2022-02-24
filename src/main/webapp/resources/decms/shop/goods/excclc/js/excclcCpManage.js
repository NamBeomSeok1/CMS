(function() {
	
	const grid = new tui.Grid({
		el: document.getElementById('data-grid'),
		rowHeight: 'auto',
		rowHeaders: ['checkbox'],
		header: {
			height: 90,
			complexColumns: [
				{header: 'a.상품가', name: 'sAmount', childNames:['orderGoodsPc','goodsTaxamt']},
				{header: 'b.옵션가', name: 'oAmount', childNames:['optnPc','optnTaxamt']},
				{header: 'c.배송비', name: 'dAmount', childNames:['dlvyAmount','dlvyTaxamt']},
				{header: '총결제금액(a+b+c)', name: 'totAmount', childNames:['setleTotAmount','setleCardAmount','setlePoint']},
				{header: '총결제금액', name: 'titleAmount', childNames:['sAmount','oAmount','dAmount','totAmount']},
				{header: '이벤트', name: 'event', childNames:['rsrvmney','couponDscnt','eventDscnt','eventTot']},
				{header: '판매수수료', name: 'setleFee', childNames:['goodsFeeRate','setleFeeTot','setleFeeSplpc','setleFeeVat']},
				{header: '업체정산금액', name: 'cmpnyExcclcTitle', childNames:['cmpnyExcclcAmount','cmpnyExcclcVat','cmpnyExcclcTot']},
				{header: '이지웰', name: 'ezwel', childNames:['orderNum','orderStatus']},
			]
		},
		columns: [
			{ header: '구분', name: 'prtnrSeCode', width: 50, align: 'center',
				formatter: function(item) {
					if(item.value == 'B2C') {
						return '<div class="bg-success text-white">' + item.value + '</div>';
					}else {
						return '<div class="bg-primary text-white">' + item.value + '</div>';
					}
				}
			},
			{ header: '주문번호', name: 'orderNo', width: 150, align: 'center'},
			{ header: '주문일', name: 'setlePnttm', align: 'center', width: 90,
				formatter: function(item) {
					return moment(item.row.setlePnttm).format('YYYY-MM-DD <br/> HH:mm:ss');
				}
			},
			{ header: '상품명/카테고리', name: 'goodsNm', align: 'center',  width: 200,
				formatter: function(item) {
					return (item.value == '') ? item.value : '<a href="#none" onclick="openGoodsPop(\'' + item.row.goodsId + '\')">' + item.value + '</a><br />'
												+'<small class="text-secondary">' + item.row.goodsCtgryNm +'</small>';
				}
			},
			{ header: '모델명', name: 'modelNm', align: 'center', width: 100 },
			{ header: '과세 /면세', name: 'taxtSeCode', align: 'center', width: 30 ,
				formatter: function(item) {
					return (item.value == 'TA01'?'<span class="text-primary">과세</span>':'<span class="text-danger">면세</span>');
				}
			},
			{ header: '판매처', name: 'cmpnyNm', align: 'center', width: 100 },
			{ header: '공급가액', name: 'orderGoodsPc', align: 'right', width: 60, //상품가
				formatter: function(item) { return modooNumberFormat(item.value); }
			},
			{ header: '세액', name: 'goodsTaxamt', align: 'right', width: 60,
				formatter: function(item) { return modooNumberFormat(item.value); }
			},
			{ header: '공급가액', name: 'optnPc', align: 'right', width: 60, //옵션가
				formatter: function(item) { return modooNumberFormat(item.value); }
			},
			{ header: '세액', name: 'optnTaxamt', align: 'right', width: 60,
				formatter: function(item) { return modooNumberFormat(item.value); }
			},
			{ header: '공급가액', name: 'dlvyAmount', align: 'right', width: 60, // 배송비
				formatter: function(item) { return modooNumberFormat(item.value); }
			},
			{ header: '세액', name: 'dlvyTaxamt', align: 'right', width: 60,
				formatter: function(item) { return modooNumberFormat(item.value); }
			},
			{ header: '총 결제금액', name: 'setleTotAmount', align: 'right', width: 70, //총결제금액
				formatter: function(item) { return modooNumberFormat(item.value); }
			},
			{ header: '카드결제', name: 'setleCardAmount', align: 'right', width: 70,
				formatter: function(item) { return modooNumberFormat(item.value); }
			},
			{ header: 'Ez 포인트', name: 'setlePoint', align: 'right', width: 70,
				formatter: function(item) { return modooNumberFormat(item.value); }
			},
			{ header: '적립금', name: 'rsrvmney', align: 'right', width: 60,
				formatter: function(item) { return modooNumberFormat(item.value); }
			},
			{ header: '쿠폰할인', name: 'couponDscnt', align: 'right', width: 60 },
			{ header: '이벤트 할인', name: 'eventDscnt', align: 'right', width: 60 },
			{ header: '이벤트 합계', name: 'eventTot', align: 'right', width: 60 },
			{ header: '판매 수수료율(부가세포함)', name: 'goodsFeeRate', align: 'center', width: 60,
				formatter: function(item) { return item.value + '%'; }
			},
			{ header: '판매 수수료 합계', name: 'setleFeeTot', align: 'right', width: 60,
				formatter: function(item) { return modooNumberFormat(item.value); }
			},
			{ header: '판매 수수료 공급가', name: 'setleFeeSplpc', align: 'right', width: 60,
				formatter: function(item) { return modooNumberFormat(item.value); }
			},
			{ header: '판매 수수료 부가세', name: 'setleFeeVat', align: 'right', width: 60,
				formatter: function(item) { return modooNumberFormat(item.value); }
			},
			{ header: '업체 정산금액', name: 'cmpnyExcclcAmount', align: 'right', width: 80,
				formatter: function(item) { return modooNumberFormat(item.value); }
			},
			{ header: '정산금액부가세', name: 'cmpnyExcclcVat', align: 'right', width: 80,
				formatter: function(item) { return modooNumberFormat(item.value); }
			},
			{ header: '업체정산금액함계', name: 'cmpnyExcclcTot', align: 'right', width: 80,
				formatter: function(item) { return modooNumberFormat(item.value); }
			},
		
			{ header: '주문번호', name: 'orderNum', align: 'center', width: 90 },
			{ header: '상태/금액', name: 'orderStatus', align: 'center', width: 80,
				formatter: function(item) { 
					var orderTotal = (isEmpty(item.row.orderTotal)?'-':modooNumberFormat(item.row.orderTotal));
					return item.row.orderStatusNm + '<br/><small class="text-secondary">' + orderTotal + '<small>'; 
				}
			},
			{ header: '지급일정', name: 'excclcPrarnde', align: 'center', width: 70,
				formatter: function(item) { 
				var dateStr = moment(item.value,'YYYYMMDD').format('YYYY-MM-DD');
					if(item.row.excclcSttusCode == 'CPE01') { //정산대기
						return dateStr + '<br/><span class="badge badge-info">예정</span>';
					}else if(item.row.excclcSttusCode == 'CPE02') { //정산보류
						return dateStr + '<br/><span class="badge badge-warning">보류</span>';
					}else if(item.row.excclcSttusCode == 'CPE03') { //정산완료
						return dateStr + '<br/><span class="badge badge-primary">완료</span>';
					}else {
						return '-';
					}
				}
			},
		],
		columnOptions: {
			frozenCount: 7,
			frozenBorderWidth: 1,
			resizable:true
		}
	});
	
	grid.on('click', function(ev) {
		if(ev.columnName == 'excclcPrarnde') {
			var rowItem = grid.getRow(ev.rowKey);
			var excclcPrarnde = moment(rowItem.excclcPrarnde,'YYYYMMDD').format('YYYY-MM-DD');
			if(rowItem.excclcSttusCode == 'CPE03') {
				bootbox.alert({ title: '지급일 수정', message: '정산완료 항목은 지급일을 수정 할 수 없습니다.', size: 'small' });
			}else {
				bootbox.dialog({
					title: '지급일 수정',
					message: '<form id="" method="post" action="' + CTX_ROOT + '/">'
						+ 	'	<div id="datepicker-excclc">'
						+ 	'	</div>'
						+ 	'</form>',
					buttons: {
						close: {
							label: '닫기',
							className: 'btn-secondary btn-sm',
							callback: function() {
								this.modal('hide');
							}
						},
						ok: {
							label: '변경하기',
							className: 'btn-primary btn-sm',
							callback: function() {
								var excDate = $('#datepicker-excclc').data("date");
								if(!isEmpty(excDate)) {
									var orderSetle = {
										orderSetleNo : rowItem.orderSetleNo,
										excclcPrarnde : excDate
									} 
									//console.log(orderSetle);
									saveExcclcPrarnde(JSON.stringify(orderSetle));
								}
								this.modal('hide');
							}
						}
					},
					onShown: function(e) {
						$('#datepicker-excclc').datetimepicker({
							locale: 'ko',
							format: 'YYYY-MM-DD',
							inline: true,
							sideBySide: true,
							defaultDate: excclcPrarnde
						});
					}
				});
			}
		}
	});
	
	tui.Grid.applyTheme('striped');
	
	var pagination = grid.getPagination();
	
	var pagination = new tui.Pagination(document.getElementById('data-grid-pagination'), {
		totalItems: 10,
		itemsPerPage: 10,
		visiblePages: 5,
		centerAlign: true
	});
	
	pagination.on('beforeMove', function(e) {
		getDataList(e.page);
	});
	
	// 데이터 목록
	function getDataList(page) {
		if($('#searchPrtnrId').val() == 'PRTNR_0000') {
			grid.hideColumn('ezwel');
		}else {
			grid.showColumn('ezwel');
		}
		
		var $form = $('#searchForm');
		var actionUrl = $form.attr('action');
		if(isEmpty(page) || page == 0) {
			$form.find('#pageIndex').val('1');
		}else if(page > 0) {
			$form.find('#pageIndex').val(page);
		}
		var formData = $form.serialize();
		
		jsonResultAjax({
			url: actionUrl,
			type: 'get',
			data: formData,
			callback: function(result) {
				if(result.success) {
					var paginationObj = result.data.paginationInfo;
					
					if(paginationObj.currentPageNo == 1) {
						pagination.setItemsPerPage(paginationObj.recordCountPerPage);
						pagination.reset(paginationObj.totalRecordCount);
					}
					grid.resetData(result.data.list);
					
					var totSum = result.data.totSum;
					//console.log(result.data);
					if(!isEmpty(totSum)) {
						setTotSum(totSum);
					}
				}
			}
		});
	};
	
	function setTotSum(totSum) {
		$('#setleCardAmount').text(modooNumberFormat(totSum.setleCardAmount));
		$('#setlePoint').text(modooNumberFormat(totSum.setlePoint));
		$('.setleTotAmount').text(modooNumberFormat(totSum.setleTotAmount));
		$('#setleFeeSplpc').text(modooNumberFormat(totSum.setleFeeSplpc));
		$('#setleFeeVat').text(modooNumberFormat(totSum.setleFeeVat));
		$('.setleFeeTot').text(modooNumberFormat(totSum.setleFeeTot));
		$('#rsrvmney').text(modooNumberFormat(totSum.rsrvmney));
		$('#couponDscnt').text(modooNumberFormat(totSum.couponDscnt));
		$('#eventDscnt').text(modooNumberFormat(totSum.eventDscnt));
		$('#eventTot').text(modooNumberFormat(totSum.eventTot));
		$('#cmpnyExcclcAmount').text(modooNumberFormat(totSum.cmpnyExcclcAmount));
		$('#cmpnyExcclcVat').text(modooNumberFormat(totSum.cmpnyExcclcVat));
		$('.cmpnyExcclcTot').text(modooNumberFormat(totSum.cmpnyExcclcTot));
		
		$('#excclcStdrDe').text(totSum.excclcStdrDe);
		$('#taxbilDe').text(totSum.taxbilDe);
	}
	
	//정산 or 보류 처리
	function saveExcclc(excclc) {
		jsonResultAjax({
			url: CTX_ROOT + '/decms/shop/goods/saveExcclcCp.json',
			type: 'post',
			contentType: 'application/json',
			data: excclc,
			callback: function(result) {
				getDataList(0);
			}
		});
	}
	
	//지급일정 변경
	function saveExcclcPrarnde(orderSetle) {
		jsonResultAjax({
			url: CTX_ROOT + '/decms/shop/goods/saveExcclcPrarnde.json',
			type: 'post',
			contentType: 'application/json',
			data: orderSetle,
			callback: function(result) {
				getDataList(0);
			}
		});
	}
	
	$(document).ready(function() {
		getDataList(1);
	});
	
	// 검색 submit
	$(document).on('submit', '#searchForm', function(e) {
		e.preventDefault();
		
		getDataList(1);
	});
	
	//정산기간 선택
	$(document).on('change', '#searchExcclcSePd', function(e) {
		var pd = $(this).val();
		var pdInfo = $(this).find('option:selected').data('info');
		$('#taxbilInfo').val(pdInfo);
	});
	
	$(document).on('click', '#btnSearchCmpny', function(e) {
		e.preventDefault();
	});
	
	//업체 자동완성
	if($('#searchCmpnyNm').length > 0) {
		var uiCmpnyList;
		$('#searchCmpnyNm').autocomplete({
			minLength: 1,
			source : function(request, response) {
				jsonResultAjax({
					url: CTX_ROOT + '/decms/shop/cmpny/cmpnyAllList.json',
					type: 'post',
					//dataType: 'json',
					data: {cmpnyNm : request.term},
					callback: function(result) {
						//if(result.message) {
						//	console.log(result.message);
						//}
						
						if(result.success) {
							response(
								$.map(result.data.list, function(item) {
									return { 
										label: item.cmpnyNm,
										value: item.cmpnyId+ '||' + item.mberId
									}
								})
							);
							
							uiCmpnyList = result.data.list;
						}
					}
				});
			},
			change: function( event, ui ) {
				$cmpnyNm = $('#searchCmpnyNm');
				if(cmpnyNm != $cmpnyNm.val()) {
					$cmpnyNm.removeClass('cmpny-complete');
				}
			},
			close: function( event, ui) {
				//console.log(cmpnyNm,$('#searchCmpnyNm').val() );
				$('#searchCmpnyNm').val('');
				$('#searchCmpnyId').val('');
				
			},
			focus: function(event, ui ) {
				return false;
			},
			select: function(event, ui ) {
				var info = ui.item.value.split('||');
				cmpnyNm = ui.item.label;
				cmpnyId = info[0];
				cmpnyMberId = info[1];
				
				$('#searchCmpnyNmResult').val(cmpnyNm);
				$('input[name="searchCmpnyId"]').val(cmpnyId);
				$('#cmpnyUserId').val(cmpnyMberId);
				
				getDataList(1);
				return false;
			}
		}).autocomplete( "instance" )._renderItem = function( ul, item ) {
			return $('<li>')
				.append('<div>' + item.label + '</div>')
				.appendTo(ul);
		};
		
		$(document).on('keydown', '#searchCmpnyNm', function(e) {
			var searchCmpnyNm = $(this).val();
			
			if(e.keyCode == 13) {
				uiCmpnyList.forEach(function(el) {
					if(searchCmpnyNm == el.cmpnyNm) {
						$('#searchCmpnyNm').autocomplete('close');
						
						$('#searchCmpnyNmResult').val(el.cmpnyNm);
						$('input[name="searchCmpnyId"]').val(el.cmpnyId);
						$('#cmpnyUserId').val(el.mberId);
						
						getDataList(1);
						return;
					}

				});
				uiCmpnyList = [];
				e.preventDefault();
			}
		});
	}
	
	// 정산 or 보류
	$(document).on('click', '.btnExcclcChange', function(e) {
		e.preventDefault();
		var rows = grid.getCheckedRows();
		var sttusCode = $(this).data('code');
		var confirmTitle = '정산처리';
		var confirmMessage = '정산처리 하시겠습니까?';
		if(sttusCode == 'CPE02') {
			confirmTitle = '정산보류';
			confirmMessage = '정산보류 하시겠습니까?';
		}

		bootbox.confirm({
			title: confirmTitle,
			message: confirmMessage,
			callback: function(result) {
				if(result) {
					var excclc = {
						excclcSttusCode: sttusCode,
						orderSetleNoList: []
					};
					
					rows.forEach(function(item){
						excclc.orderSetleNoList.push(item.orderSetleNo);
					});
					

					saveExcclc(JSON.stringify(excclc));
						
				}
			}
		});
		
	});
	
	//입점사명 clear
	$(document).on('click', '#btnRemoveCmpnyInfo', function(e) {
		e.preventDefault();
		$('#searchCmpnyNmResult').val('');
		$('#searchCmpnyNm').val('');
		$('#searchCmpnyId').val('');
		getDataList(1);
	});
	
	//엑셀 다운로드
	$(document).on('click', '.btnExcelDownload', function(e) {
		e.preventDefault();
		var actionUrl = $(this).attr('href');
		var formData = $('#searchForm').serialize();
		location.href = actionUrl + '?' + formData;
	});
	
})();