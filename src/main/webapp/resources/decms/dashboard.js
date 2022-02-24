(function() {
	var isAdmin =$('#isAdmin').val();
	
	$(document).ready(function(){
		//관리자
		if (isAdmin == 'Y') {
			initGridAdmin();
		} else {
			//CP
			initGridCp();
		}
	});
	
	var grid_cp;
	var grid_goods;
	var grid_qainfo;
	var grid_inqry;
	
	initGridAdmin = function() {
		grid_cp = new tui.Grid({
			el: document.getElementById('data-cmpny'),
			rowHeight: 'auto',
			bodyHeight: 240,
			columns: [
				{ header: '업체고유번호', name: 'cmpnyId', width: 150, align: 'center'},
				{ header: '로고', name: 'cmpnyLogoPath', width: 120, align: 'center',
					formatter: function(item) {
						if(!isEmpty(item.value)) {
							return '<img src="' + item.value + '" alt="' + item.row.cmpnyNm + '" style="height:30px;"/>';
						}else {
							return '-';
						}
					}
				},
				{ header: '업체명', name: 'cmpnyNm', width: 200, align: 'center'},
				{ header: '담당자', name: 'chargerNm', width: 70, align: 'center'},
				{ header: '연락처', name: 'cmpnyTelno', width: 100, align: 'center'},
				{ header: '담당자연락처', name: 'chargerTelno', width: 100, align: 'center'},
				//{ header: '상품수', name: 'cmpnyGoodsCo', width: 50, align: 'center'},
				{ header: '입점일', name: 'opnngDe', width:100, align: 'center',
					formatter: function(item) {
						return moment(item.value, 'YYYYMMDD').format('YYYY-MM-DD');
					}
				},
				{ header: '등록상태', name: 'opnngSttusCode', width: 80, align: 'center',
					formatter: function(item) {
						if(item.value == 'R') {
							return '<small class="f-s-1"><span class="badge badge-primary">등록신청</span></small>';
						}else if(item.value == 'A') {
							return '<small class="f-s-1"><span class="badge badge-warning">처리 중</span></small>';
						}else if(item.value == 'C') {
							return '<small class="f-s-1"><span class="badge badge-dark">등록완료</span></small>';
						}else if(item.value == 'D') {
							return '<small class="f-s-1"><span class="badge badge-secondary">등록거부</span></small>';
						}
					}
				}
			],
			columnOptions: {
				frozenCount: 1,
				frozenBorderWidth: 1,
				resizable:true
			}
		});
		
		grid_goods = new tui.Grid({
			el: document.getElementById('data-goods'),
			rowHeight: 'auto',
			bodyHeight: 240,
			//rowHeaders: ['checkbox'],
			columns: [
				{ header: '상품코드', name: 'goodsId', width: 150, align: 'center',
					formatter: function(item) {
						if(item.row.registSttusCode == 'C'){
							return '<a href="/shop/goods/goodsView.do?goodsId=' + item.value + '" target="_blank">' + item.value + '</a>';
						}else{
							return '<span>' + item.value + '</span>';
						}
	
					}
				},
				{ header: '제휴사', name: 'prtnrNm', width: 60, align: 'center'},
				{ header: '종류', name: 'goodsKndCode', width: 60, align: 'center',
					formatter: function(item) {
						return (item.value == 'SBS')?'구독':'<span class="text-secondary">일반</span>';
					}
				},
				{ header: '업체명', name: 'cmpnyNm', width: 200, align: 'center'},
				{ header: '이미지', name: 'goodsTitleImagePath', width: 60, align: 'center',
					formatter: function(item) {
						return (item.value == '')?'':'<img src="' + item.value + '" alt="" style="height:100%; max-height:30px;"/>';
					}
				},
				{ header: '상품명/카테고리', name: 'goodsNm', align: 'center', width: 200,
					formatter: function(item) {
						return item.value+'<br/><small class="text-secondary">'+item.row.goodsCtgryNm+'</small>';
					}
				},
				{ header: '품절여부', name: 'soldOutAt', width: 80, align: 'center', 
					formatter: function(item){
						if (item.value == 'Y') {
							return '<p style="color: red;">품절</p>';
						} else {
							return '<p>-</p>';
						}
					}},
				{ header: '모델명', name: 'modelNm', width: 100, align: 'center'},
				{ header: '공급사', name: 'makr', width: 110, align: 'center'},
				{ header: '수수료율', name: 'goodsFeeRate', width: 110, align: 'center',
					formatter: function(item) {
					return item.value+'%';
					}
				},
				{ header: '판매가', name: 'goodsPc', width: 90, align: 'center', 
					formatter: function(item) {
						if (item.value) {
							return modooNumberFormat(item.value);
						} else {
							return 0;
						}
						
					}
				},
				{ header: '배송비', name: 'dlvyPc', width: 70, align: 'center', 
					formatter: function(item) {
						if (item.value) {
							return modooNumberFormat(item.value);
						} else {
							return 0;
						}
					}
				},
				{ header: '조회수', name: 'rdcnt', width: 50, align: 'center'},
				{ header: '판매수', name: 'sleCo', width: 50, align: 'center'},
				{ header: '등록상태', name: 'registSttusCode', width: 80, align: 'center',
					formatter: function(item) {
						if(item.value == 'R') {
							return '<span class="text-danger">등록대기</span>';
						}else if(item.value == 'C') {
							return '<span class="text-primary">등록완료</span>';
						}else if(item.value == 'E') {
							return '판매종료';
						}
					}
				}
			],
			columnOptions: {
				frozenCount: 1,
				frozenBorderWidth: 1,
				resizable:true
			}
		});
		
		grid_qainfo = new tui.Grid({
			el: document.getElementById('data-qainfo'),
			bodyHeight: 240,
			columns: [
				{ header: '작성일', name: 'frstRegistPnttm', width:100, align: 'center',
					formatter: function(item) {
						return isEmpty(item.value) ? '' : moment(item.value).format('YYYY-MM-DD');
					}
				},
				{ header: '상태', name: 'qnaProcessSttusCode', width:100, align: 'center',
					formatter: function(item) {
						var retValue = '';
						switch(item.value) {
						case 'R':
							retValue = '<span class="text-danger">답변대기</span>';
							break;
						case 'C':
							retValue = '<span class="text-primary">답변완료</span>';
							break;
						}
						return retValue;
					}
				},
				{ header: '작성자', name: 'wrterNm', width: 100, align: 'center', },
				{ header: '문의유형', name: 'qestnTyNm', width: 100, align: 'center' },
				{ header: '질문제목', name: 'qestnSj' }
			],
			columnOptions: {
				frozenCount: 1,
				frozenBorderWidth: 2,
			}
		});
		
		grid_inqry = new tui.Grid({
			el: document.getElementById('data-inqry'),
			rowHeight: 'auto',
			bodyHeight: 240,
			columns: [
				{ header: 'No', name: 'inqryId', width: 70, align: 'center', formatter: function(item) {
						return parseInt(item.row.rowKey)+1;
					}
				},
				{ header: '문의 일시', name: 'frstRegistPnttm', width: 100, align: 'center',
					formatter: function(item) {
						return moment(item.value, 'YYYYMMDD').format('YYYY-MM-DD');
					}
				},
				{ header: '업체명', name: 'cmpnyNm', width: 200, align: 'center'},
				{ header: '담당자', name: 'cmpnyCharger', width: 120, align: 'center'},
				{ header: '연락처', name: 'telno', width: 150, align: 'center'},
				{ header: '이메일주소', name: 'cmpnyEmail', width: 200, align: 'center'}/*,
				{ header: '입점내용', name: 'cmpnyIntrcn', width: 100, align: 'center',
					formatter: function(item) {
						return '<a href="#none" onclick="showInqryDetail(\'' + item.row.inqryId + '\');">상세보기</a>';
					}
				}*/
			],
			columnOptions: {
				frozenCount: 1,
				frozenBorderWidth: 2,
			}
		});
	
		getCpList();
		getGoodsList();
		getQainfoList();
		getInqryList();
	}

	getCpList = function() {
		var dataJson = {
			'searchOpnngSttusCode' : 'R'
		};
		$.ajax({
			url:CTX_ROOT + '/decms/shop/cmpny/cmpnyList.json',
			type:'GET',
			data:dataJson,
			dataType:'json',
			success:function(result){
				var cnt = result.data.paginationInfo.totalRecordCount;
				if (cnt > 0) {
					$('#cmpny-cnt').text(cnt);
					$('#cmpny-cnt').removeClass('badge-secondary');
					$('#cmpny-cnt').addClass('badge-danger');
				}
				var list = result.data.list;
				grid_cp.resetData(list);
			}				
		});
	}
	
	getGoodsList = function() {
		var dataJson = {
			'searchRegistSttusCode' : 'R'
		};
		$.ajax({
			url:CTX_ROOT + '/decms/shop/goods/goodsList.json',
			type:'GET',
			data:dataJson,
			dataType:'json',
			success:function(result){
				//$('#goods-cnt').text(result.data.paginationInfo.totalRecordCount);
				
				var cnt = result.data.paginationInfo.totalRecordCount;
				if (cnt > 0) {
					$('#goods-cnt').text(cnt);
					$('#goods-cnt').removeClass('badge-secondary');
					$('#goods-cnt').addClass('badge-danger');
				}
				
				var list = result.data.list;
				grid_goods.resetData(list);
			}				
		});
	}
	
	getQainfoList = function() {
		var dataJson = {
			'searchQnaProcessSttusCode' : 'R'
			, 'qaSeCode' : 'SITE'
		};
		$.ajax({
			url:CTX_ROOT + '/decms/qainfo/qainfoList.json',
			type:'GET',
			data:dataJson,
			dataType:'json',
			success:function(result){
				var cnt = result.data.paginationInfo.totalRecordCount;
				if (cnt > 0) {
					$('#qainfo-cnt').text(cnt);
					$('#qainfo-cnt').removeClass('badge-secondary');
					$('#qainfo-cnt').addClass('badge-danger');
				}
				
				var list = result.data.list;
				grid_qainfo.resetData(list);
			}				
		});
	}
	
	getInqryList = function() {
		var dataJson = {
			//'searchQnaProcessSttusCode' : 'R'
			//, 'qaSeCode' : 'SITE'
		};
		$.ajax({
			url:CTX_ROOT + '/decms/cmpny/inqryList.json',
			type:'GET',
			data:dataJson,
			dataType:'json',
			success:function(result){
				
				var list = result.data.list;
				grid_inqry.resetData(list);
			}				
		});
	}

	var grid_cp_notice;
	var grid_order_dlvy;
	var grid_order_exchange;
	var grid_order_refund;
	var grid_qna;
	var grid_review;
	var grid_order_stop;
	var grid_order_cancel;
	
	initGridCp = function() {
		grid_cp_notice = new tui.Grid({
		el: document.getElementById('data-notice-cp'),
		rowHeight: 'auto',
		bodyHeight: 240,
		//scrollX: true,
		//scrollY: true,
		columns: [
			{ header: 'No.', name: 'rn', width: 50, align: 'center',
				formatter: function(item) { 
					return item.row.rowKey+1;
				}
			},
			{ header: '분류', name: 'ctgryId', width: 50, align: 'center',
				formatter: function(item) {
					if(item.row.noticeAt == 'Y') {
						return '공지';
					}
				}
			},
			{ header: '제목', name: 'nttSj'/*
				formatter: function(item) {
					var subject = (item.row.replyAt == 'Y')? ' <i class="fas fa-reply fa-rotate-180 ml-2"></i> ' + item.value : item.value;
					subject = '<a href="' + CTX_ROOT + '/decms/embed/board/article/viewBoardArticle.do?nttId=' 
						+ item.row.nttId + '" class="btnView text-dark" data-target="#boardArticleModal" title="게시글 상세">' 
						+ subject + '</a>';

					return subject;
				}*/
			},
			{ header: '작성자', name: 'ntcrNm', width: 80, align: 'center',
				formatter: function(item) {
					return item.value + '<br/>(' + item.row.ntcrId + ')';
				}
			},
			/*
			{ header: '게시시작일', name: 'ntceBgnde', width: 100, align: 'center',
				formatter: function(item) {
					var cssClass = (item.row.bbscttAt == 'N')? 'text-danger' : '';
					return isEmpty(item.value) ? '':'<span class="' + cssClass + '">' + moment(item.value,'YYYYMMDD').format('YYYY-MM-DD') + '</span>';
				}
			},
			{ header: '게시종료일', name: 'ntceEndde', width: 100, align: 'center',
				formatter: function(item) {
					var cssClass = (item.row.bbscttAt == 'N')? 'text-danger' : '';
					return isEmpty(item.value) ? '':'<span class="' + cssClass + '">' + moment(item.value,'YYYYMMDD').format('YYYY-MM-DD') + '</span>';
				}
			},
			*/
			{ header: '첨부', name: 'atchFileId', width:50, align: 'center', 
				formatter: function(item) {
					return !isEmpty(item.value)?'<i class="fas fa-check-circle"></i>':'<i class="fas fa-times"></i>';
				}
			},
			{ header: '생성일', name: 'frstRegistPnttm', width:130, align: 'center',
				formatter: function(item) {
					return isEmpty(item.value) ? '' : moment(item.value).format('YYYY-MM-DD hh:mm:ss');
				}
			}/*,
			{ header: '관리', name: 'nttId', width:130, align: 'center',
				formatter: function(item) {
					if (item.row.bbsId == 'BBSMSTR_0000000000CP'&& isAdmin == 'Y') {
						return '<a href="#none" onclick="sendAlimTalk(' + item.row.nttId + ')"><span class="btn btn-danger btn-sm">알림톡 발송</span></a>';
					} else {
						return '-';
					}
					
				}
			}*/
			/*
			{ header: '관리', name: 'nttId', width: 100 , align: 'center',
				formatter: function(item) {
					return 	'<a href="' + CTX_ROOT + '/decms/embed/board/article/modifyBoardArticle.do?nttId=' + item.value + '" class="btn btn-success btn-sm mr-1 btnModify" data-target="#boardArticleModal" title="수정">' 
						+ 		'<i class="fas fa-edit"></i></a>' 
						+ 	'<a href="' + CTX_ROOT + '/decms/board/master/deleteBoardMaster.json?bbsId=' + item.value + '" class="btn btn-danger btn-sm btnDelete" title="삭제">' 
						+ 		'<i class="fas fa-trash"></i></a>';
				}
			},
			*/
		],
		columnOptions: {
	        resizable: true
	      }
		});
		
		grid_order_dlvy = new tui.Grid({
			el: document.getElementById('data-order-dlvy'),
			rowHeight: 'auto',
			bodyHeight: 240,
			columns: [
				{ header: '주문번호', name: 'orderNo', align: 'center', width: 140/*, 
					formatter: function(item) {
						if(item.row.orderKndCode == 'GNR') {
							return item.value;
						}else if(item.row.orderKndCode == 'SBS') {
							return (item.value == '') ? item.value : '<a href="#none" onclick="initDlvyPop(\'' + item.value + '\')" >' + item.value + '</a>';	
						}
					}*/
				},
				{ header: '결제일', name: 'setlePnttm', align: 'center', width: 100,
					formatter: function(item) {
						if (item.value) {
							return moment(item.row.setlePnttm).format('YYYY-MM-DD <br/> HH:mm:ss');
						} else {
							return item.value;
						}
						
					}
				},
				{ header: '제휴사', name: 'prtnrNm', align: 'center', width: 70 ,
					formatter: function(item) {
						if(item.row.prtnrId == 'PRTNR_0000') {
							return '<div class="bg-success text-white">' + item.value + '</div>';
						}else {
							return '<div class="bg-primary text-white">' + item.value + '</div>';
						}
					}
				},
				{ header: '구분', name: 'orderKndCode', align: 'center', width: 50,
					formatter: function(item) {
						if(item.value == 'GNR') {
							return '<span class="badge badge-success">일반</span>';
						}else if(item.value == 'SBS') {
							return '<span class="badge badge-primary">구독</span>';
						}
					}
				},
				{ header: '상품명/카테고리', name: 'goodsNm', align: 'center', width:300, 
					formatter: function(item) {
						return (item.value == '') ? item.value : '<a href="#none" onclick="openGoodsPop(\'' + item.row.goodsId + '\')">' + item.value + '</a><br />'
													+'<small class="text-secondary">' + item.row.goodsCtgryNm +'</small>';
					}
				},
				{ header: '결제주기', align: 'center', width: 70,
					formatter: function(item) {
						if (item.row.goodsKndCode == 'SBS') {
							if(item.row.sbscrptCycleSeCode == 'WEEK') {
								return item.row.sbscrptWeekCycle + ' 주';
							}else if(item.row.sbscrptCycleSeCode == 'MONTH') {
								return item.row.sbscrptMtCycle + ' 개월';
							}
						} else {
							return '';
						}
					}
				},
				{ header: '결제날짜(요일)', name: 'sbscrptDlvyDay', align: 'center', width: 110,
					formatter: function(item) {
					if (item.row.goodsKndCode == 'SBS') {
						if(item.row.sbscrptCycleSeCode == 'WEEK') {
							return '<strong>' + item.row.sbscrptDlvyWdNm + '</strong> 요일';
						}else if(item.row.sbscrptCycleSeCode == 'MONTH') {
							return item.row.sbscrptDlvyDay + ' 일';
						}
					} else {
						return '';
					}
				}
				},
				{ header: '모델명', name: 'modelNm', align: 'center', width: 100 },
				{ header: '수량', name: 'orderCo', align: 'right', width: 60 },
				{ header: '옵션', name: 'orderItem', align: 'left', width: 90 },
				{ header: '판매자', name: 'cmpnyNm', align: 'center', width: 100 },
				{ header: '총결제금액', name: 'setleTotAmount', align: 'right', width: 80,
					formatter: function(item) {
						return item.value.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ',');
					}
				},
				{ header: '상품가격', name: 'goodsAmount', align: 'right', width: 80,
					formatter: function(item) {
						return item.value.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ',');
					}
				},
				{ header: '배송비', name: 'dlvyAmount', align: 'right', width: 80,
					formatter: function(item) {
						return item.value.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ',');
					}
				},
				{ header: '사용포인트', name: 'setlePoint', align: 'right', width: 80,
					formatter: function(item) {
						return item.value.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ',');
					}
				},
				{ header: '카드결제금액', name: 'setleCardAmount', align: 'right', width: 80,
					formatter: function(item) {
						return item.value.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ',');
					}
				},
				{ header: '주문상태', name: 'orderSttusCodeNm', align: 'center', width: 100 },
				{ header: '요청처리상태', name: 'reqTyCodeNm', align: 'center', width: 100	},
				{ header: '결제상태', name: 'setleSttusCodeNm', align: 'center', width: 100 },
				{ header: '배송상태', name: 'dlvySttusCodeNm', align: 'center', width: 100 },
				{ header: '주문자', name: 'ordrrNm', align: 'center', width:120, 
					formatter: function(item) {
						return '<div>' + item.value + '</div><div>' + item.row.telno + '</div>';
					}
				},
				{ header: '상품 수령 장소', name: 'dlvyAdres', width:500,
					formatter: function(item) {
						return '(' + item.row.dlvyZip + ') ' + item.value + ' ' + item.row.dlvyAdresDetail;
					}
				},
				{ header: '배송메시지', name: 'dlvyMssage', width:200}
			],
			columnOptions: {
				frozenCount: 1,
				frozenBorderWidth: 1,
				resizable:true
			}
		});
		
	grid_order_cancel = new tui.Grid({
		el: document.getElementById('data-order-cancel'),
		rowHeight: 'auto',
		bodyHeight: 100,
		//scrollX: true,
		//scrollY: true,
		columns: [
			{ header: '주문번호',	name: 'orderNo', align: 'center', width: 160 },
			{ header: '주문일', name: 'orderPnttm', align: 'center', width: 100
			, formatter: function(item) {
					return moment(item.row.orderPnttm).format('YYYY-MM-DD HH:mm:ss');
				}
			},
			{ header: '제휴사', name: 'prtnrNm', align: 'center', width: 70 ,
				formatter: function(item) {
					if(item.row.prtnrId == 'PRTNR_0000') {
						return '<div class="bg-success text-white">' + item.value + '</div>';
					}else {
						return '<div class="bg-primary text-white">' + item.value + '</div>';
					}
				}
			},
			{ header: '상품명/카테고리', name: 'goodsNm', align: 'center', width: 300,
				formatter: function(item) {
					return (item.value == '') ? item.value : '<a href="#none" onclick="openGoodsPop(\'' + item.row.goodsId + '\')">' + item.value + '</a><br /><small class="text-secondary">'+item.row.goodsCtgryNm+'</small>';
				}
			},
			{ header: '모델명', name: 'modelNm', align: 'center', width: 100 },
			{ header: '판매자', name: 'brandNm', align: 'center', width: 100 },
			{ header: '판매가', name: 'goodsPc', align: 'right', width: 100,
				formatter: function(item) {
					return item.value.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ',');
				}
			},
			{ header: '배송료', name: 'dlvyAmount', align: 'right', width: 100,
				formatter: function(item) {
					return item.value.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ',');
				}
			},
			{ header: '진행상태', name: 'reqTyCodeNm', align: 'center', width: 100},
			{ header: '처리상태',	name: 'reqTyCode', align: 'center', width: 150,
				formatter: function(item) {
					if (item.value.indexOf('1') > -1) {
						var html='';
						html += '<button type="button" class="btn btn-primary btn-sm" onclick="updateProcessStatus(\'' + item.row.orderDlvyNo + '\',\'' + item.row.orderNo + '\',\'' + item.row.orderGroupNo + '\',' + true + ');">승인</button>';
						html += '<button type="button" class="btn btn-danger btn-sm" onclick="updateProcessStatus(\'' + item.row.orderDlvyNo + '\',\'' + item.row.orderNo + '\',\'' + item.row.orderGroupNo + '\',' + false + ');">삭제</button>';
						return html;
					} else {
						return '완료';
					}
				}
			},
			{ header: '결제상태',	name: 'setleSttusCodeNm', align: 'center', width: 100 },
			{ header: '주문자', name: 'ordrrNm', align: 'center', 
				formatter: function(item) {
					return '<div>' + item.value + '</div><div>' + item.row.telno + '</div>';
				}
			},
			{ header: '상품 수령 장소', name: 'dlvyAdres', align: 'center', width: 500 }			
		],
		columnOptions: {
	        resizable: true
	      }
	});
		
	grid_order_stop = new tui.Grid({
		el: document.getElementById('data-order-stop'),
		rowHeight: 'auto',
		bodyHeight: 100,
		//scrollX: false,
		//scrollY: false,
		columns: [
			{ header: '주문번호',	name: 'orderNo', align: 'center', width: 160 },
			{ header: '주문일', name: 'orderPnttm', align: 'center', width: 100
			, formatter: function(item) {
					return moment(item.row.orderPnttm).format('YYYY-MM-DD HH:mm:ss');
				}
			},
			{ header: '제휴사', name: 'prtnrNm', align: 'center', width: 70 ,
				formatter: function(item) {
					if(item.row.prtnrId == 'PRTNR_0000') {
						return '<div class="bg-success text-white">' + item.value + '</div>';
					}else {
						return '<div class="bg-primary text-white">' + item.value + '</div>';
					}
				}
			},
			{ header: '상품명/카테고리', name: 'goodsNm', align: 'center', width: 300,
				formatter: function(item) {
					return (item.value == '') ? item.value : '<a href="#none" onclick="openGoodsPop(\'' + item.row.goodsId + '\')">' + item.value + '</a><br /><small class="text-secondary">'+item.row.goodsCtgryNm+'</small>';
				}
			},
			{ header: '모델명', name: 'modelNm', align: 'center', width: 100 },
			{ header: '판매자', name: 'brandNm', align: 'center', width: 100 },
			{ header: '판매가', name: 'goodsPc', align: 'right', width: 100,
				formatter: function(item) {
					return item.value.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ',');
				}
			},
			{ header: '배송료', name: 'dlvyAmount', align: 'right', width: 100,
				formatter: function(item) {
					return item.value.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ',');
				}
			},
			{ header: '진행상태', name: 'orderSttusCodeNm', align: 'center', width: 80},
			{ header: '처리상태',	name: 'reqTyCode', align: 'center', width: 100,
				formatter: function(item) {
					if ('ST01'.indexOf(item.row.orderSttusCode) > -1) {
						var html='';
						html += '<button type="button" class="btn btn-primary btn-sm" onclick="updateProcessStatus(\'' + item.row.orderDlvyNo + '\',\'' + item.row.orderNo + '\',\'' + item.row.orderGroupNo + '\',' + true + ');">확인</button>';
						return html;
					} else {
						return '완료';
					}
				}
			},
			{ header: '결제상태',	name: 'setleSttusCodeNm', align: 'center', width: 80 },
			{ header: '배송상태',	name: 'dlvySttusCodeNm', align: 'center', width: 80 },
			{ header: '주문자', name: 'ordrrNm', align: 'center', width: 130,
				formatter: function(item) {
					return '<div>' + item.value + '</div><div>' + item.row.telno + '</div>';
				}
			},
			{ header: '상품 수령 장소', name: 'dlvyAdres', align: 'center', width: 500 }			
		],
		columnOptions: {
        	resizable: true
      }
	});	
		
		grid_review = new tui.Grid({
			el: document.getElementById('data-review'),
			rowHeight: 'auto',
			bodyHeight: 240,
			columns: [
				{ header: '작성일', name: 'frstRegistPnttm', width:80, align: 'center',
					formatter: function(item) {
						return isEmpty(item.value) ? '' : moment(item.value).format('YYYY-MM-DD');
					}
				},
				{ header: '고객ID', name: 'wrterId', width: 100, align: 'center'},
				//{ header: '이름', name: 'wrterNm', width: 100, align: 'center'},
				//{ header: '상품명', name: 'goodsNm', width: 200, align: 'center'},
				{ header: '평점', name: 'score', width: 70, align: 'center',
					formatter: function(item) {
						var star = '';
						console.log(item.value);
						switch(item.value) {
							case 1 : star = '★☆☆☆☆'; break;
							case 2 : star = '★★☆☆☆'; break;
							case 3 : star = '★★★☆☆'; break;
							case 4 : star = '★★★★☆'; break;
							case 5 : star = '★★★★★'; break;
							default : start = '-';
						}
						return star;
					}
				},
				{ header: '상품평', name: 'commentCn' },
				{ header: '바로가기', name: 'reviewLink', width: 60, align: 'center',
					formatter: function(item) {
						if(!isEmpty(item.row.cntntsId)) {
							return '<a href="/shop/goods/goodsView.do?goodsId='+item.row.cntntsId+'" target="_blank" class="btn btn-outline-dark btn-sm"><i class="fas fa-link"></i></a>';
						}else {
							return '-';
						}
					}
				}
			],
			columnOptions: {
				frozenCount: 1,
				frozenBorderWidth: 1,
				resizable:true
			}
		});	
		
		grid_qna = new tui.Grid({
			el: document.getElementById('data-qna'),
			bodyHeight: 240,
			columns: [
				{ header: '작성일', name: 'frstRegistPnttm', width:100, align: 'center',
					formatter: function(item) {
						return isEmpty(item.value) ? '' : moment(item.value).format('YYYY-MM-DD');
					}
				},
				{ header: '상품명', name: 'goodsNm', width: 120, align: 'center' },
				{ header: '작성자', name: 'wrterNm', width: 100, align: 'center', },
				{ header: '문의유형', name: 'qestnTyNm', width: 100, align: 'center' },
				{ header: '질문제목', name: 'qestnSj' }/*,
				{ header: '상태', name: 'qnaProcessSttusCode', width:100, align: 'center',
					formatter: function(item) {
						var retValue = '';
						switch(item.value) {
						case 'R':
							retValue = '<span class="text-danger">답변대기</span>';
							break;
						case 'C':
							retValue = '<span class="text-primary">답변완료</span>';
							break;
						}
						return retValue;
					}
				},
				{ header: '관리', name: 'qaId', width: 100, align: 'center',
					formatter: function(item) {
						return 	'<a href="' + CTX_ROOT + '/decms/embed/qainfo/modifyQainfo.do?qaId=' + item.value + '" class="btn btn-success btn-sm mr-1 btnModify" data-target="#qainfoModal" title="수정">' 
							+ 		'<i class="fas fa-edit"></i></a>' 
							+ 	'<a href="' + CTX_ROOT + '/decms/qainfo/deleteQainfo.json?qaId=' + item.value + '" class="btn btn-danger btn-sm btnDelete" title="삭제">' 
							+ 		'<i class="fas fa-trash"></i></a>';
					}
				},*/
			],
			columnOptions: {
				frozenCount: 1,
				frozenBorderWidth: 2,
			}
		});
		
		grid_order_exchange = new tui.Grid({
			el: document.getElementById('data-order-exchange'),
			rowHeight: 'auto',
			bodyHeight: 100,
			//scrollX: false,
			//scrollY: false,
			columns: [
				{ header: '주문번호',	name: 'orderNo', align: 'center', width: 160 },
				{ header: '주문일', name: 'orderPnttm', align: 'center', width: 150
				, formatter: function(item) {
						return moment(item.row.orderPnttm).format('YYYY-MM-DD HH:mm:ss');
					}
				},
				{ header: '상품명/카테고리', name: 'goodsNm', align: 'center'
				, formatter: function(item) {
						return (item.value == '') ? item.value : '<a href="#none" onclick="openGoodsPop(\'' + item.row.goodsId + '\')">' + item.value + '</a><br /><small class="text-secondary">'+item.row.goodsCtgryNm+'</small>';
					}
				},
				{ header: '모델명', name: 'modelNm', align: 'center', width: 100 },
				{ header: '판매자', name: 'brandNm', align: 'center', width: 100 },
				{ header: '판매가', name: 'goodsPc', align: 'right', width: 100 ,
					formatter: function(item) {
						return item.value.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ',');
					}
				},
				{ header: '배송료', name: 'dlvyAmount', align: 'right', width: 100 ,
					formatter: function(item) {
						return item.value.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ',');
					}
				},
				{ header: '진행상태', name: 'reqTyCodeNm', align: 'center', width: 100},
				{ header: '교환사유', name: 'cancelReason', align: 'center', width: 100, 
					formatter: function(item) {
						return '<a href="#none" onclick="showExchangeReason(\'' + item.row.orderDlvyNo + '\', \'' + item.row.ordrrNm + '\',\'' + item.row.goodsNm + '\')">보기</a>';
					}
				},
				{ header: '처리상태',	name: 'reqTyCode', align: 'center', width: 150, 
					formatter: function(item) {
						if (item.value == 'E01') {
							var html='';
							html += '<button type="button" class="btn btn-primary btn-sm" onclick="updateProcessStatus(\'' + item.row.orderDlvyNo + '\',' + true + ');">승인</button>';
							html += '<button type="button" class="btn btn-danger btn-sm" onclick="updateProcessStatus(\'' + item.row.orderDlvyNo + '\',' + false + ');">삭제</button>';
							return html;
						} else if (item.value == 'E02') {
							return '승인';
						} else if (item.value == 'E04') {
							return '교환재발송중';
						} else if (item.value == 'E05') {
							return '완료';
						}
					}
				},
				{ header: '배송상태',	name: 'dlvySttusCodeNm', align: 'center', width: 100,
					formatter : function(item) {
						if (item.row.dlvySttusCode == 'DLVY00')
							return '<a href="#none" onclick="initAlert(\'' + item.row.orderDlvyNo + '\'); ">' + item.value + '</a>';
						else if (item.row.dlvySttusCode == 'DLVY01') {
							//return '<a href="#none" onclick="initEditPop(\'' + item.row.orderDlvyNo + '\'); ">' + item.value + '</a>';
							return item.value;
						} else if (item.row.dlvySttusCode == 'DLVY02') {
							return '<a href="#none" onclick="showDlvyStatus(\'' + item.row.hdryId + '\',\'' + item.row.invcNo + '\'); ">' + item.value + '</a>';
						} else if (item.row.dlvySttusCode == 'DLVY03') {
							return item.row.dlvySttusCodeNm;
						}
					}
				},
				{ header: '주문자', name: 'ordrrNm', align: 'center', width: 100,
					formatter: function(item) {
						return '<div>' + item.value + '</div><div>' + item.row.telno + '</div>';
					}
				},
				{ header: '상품 수령 장소', name: 'dlvyAdres', align: 'center', width: 500 }			
			],
			columnOptions: {
		        resizable: true
		      }
		});	
			
		grid_order_refund = new tui.Grid({
			el: document.getElementById('data-order-refund'),
			rowHeight: 'auto',
			bodyHeight: 100,
			//scrollX: false,
			//scrollY: false,
			columns: [
				{ header: '주문번호',	name: 'orderNo', align: 'center', width: 160},
				{ header: '주문일', name: 'orderPnttm', align: 'center', width: 100
				, formatter: function(item) {
						return moment(item.row.orderPnttm).format('YYYY-MM-DD HH:mm:ss');
					}
				},
				{ header: '상품명/카테고리', name: 'goodsNm', align: 'center'
				, formatter: function(item) {
						return (item.value == '') ? item.value : '<a href="#none" onclick="openGoodsPop(\'' + item.row.goodsId + '\')">' + item.value + '</a><br /><small class="text-secondary">'+item.row.goodsCtgryNm+'</small>';
					}
				},
				{ header: '모델명', name: 'modelNm', align: 'center', width: 100 },
				{ header: '판매자', name: 'brandNm', align: 'center', width: 100 },
				{ header: '판매가', name: 'goodsPc', align: 'right', width: 100 ,
					formatter: function(item) {
						return item.value.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ',');
					}
				},
				{ header: '배송료', name: 'dlvyAmount', align: 'right', width: 100 ,
					formatter: function(item) {
						return item.value.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ',');
					}
				},
				{ header: '진행상태', name: 'reqTyCodeNm', align: 'center', width: 150},
				{ header: '반품사유', name: 'cancelReason', align: 'center', width: 100, 
					formatter: function(item) {
						return '<a href="#none" onclick="showRefundReason(\'' + item.row.orderDlvyNo + '\', \'' + item.row.ordrrNm + '\',\'' + item.row.goodsNm + '\')">보기</a>';
					}
				},
				{ header: '처리상태',	name: 'reqTyCode', align: 'center', width: 150,
					formatter: function(item) {
						if (item.value == 'R01') {
							var html='';
							html += '<button type="button" class="btn btn-primary btn-sm" onclick="updateProcessStatus(\'' + item.row.orderDlvyNo + '\',' + true + ');">승인</button>';
							html += '<button type="button" class="btn btn-danger btn-sm" onclick="updateProcessStatus(\'' + item.row.orderDlvyNo + '\',' + false + ');">삭제</button>';
							return html;
						} else if (item.value == 'R02') {
							return '승인';
						} else if (item.value == 'R04') {
							return '<a href="#none" onclick="initAlert(\'' + item.row.orderDlvyNo + '\'); ">물품 수거중</a>';
						} else if (item.value == 'R05') {
							return '완료';
						}
					}
				},
				{ header: '배송상태',	name: 'dlvySttusCodeNm', align: 'center', width: 100,
					formatter : function(item) {
						return item.value;
					}
				},
				{ header: '주문자', name: 'ordrrNm', align: 'center', width: 100,
					formatter: function(item) {
						return '<div>' + item.value + '</div><div>' + item.row.telno + '</div>';
					}
				},
				{ header: '상품 수령 장소', name: 'dlvyAdres', align: 'center', width: 500 }			
			],
			columnOptions: {
		        resizable: true
		      }
		});	
		
		getCpNoticeList();
		getOrderDlvyList();
		getOrderCancelList();
		getOrderStopList();
		getOrderExchangeList();
		getOrderRefundList();
		getReviewList();
		getQnaList();
	} //initGridCp

	getCpNoticeList = function() {
		var dataJson = {
			'searchBbsId' : 'BBSMSTR_0000000000CP'
		};
		$.ajax({
			url:CTX_ROOT + '/decms/board/article/boardList.json',
			type:'GET',
			data: dataJson, 
			dataType:'json',
			success:function(result){
				var list = result.data.list;
				grid_cp_notice.resetData(list);
				grid_cp_notice.removeRow(5);
				grid_cp_notice.removeRow(6);
				grid_cp_notice.removeRow(7);
				grid_cp_notice.removeRow(8);
				grid_cp_notice.removeRow(9);
			}				
		});
	
	}
	
	getOrderDlvyList = function() {
		var dataJson = {
			'searchDlvySttusCode' : 'DLVY00'
			, 'searchSetleSttusCode' : 'S'
		};
		
		$.ajax({
			url:CTX_ROOT + '/decms/shop/goods/getOrderStatusList.json',
			type:'GET',
			data:dataJson,
			dataType:'json',
			success:function(result){
				var cnt = result.data.paginationInfo.totalRecordCount;
				if (cnt > 0) {
					$('#order-dlvy-cnt').text(cnt);
					$('#order-dlvy-cnt').removeClass('badge-secondary');
					$('#order-dlvy-cnt').addClass('badge-danger');
				}
				
				var list = result.data.list;
				grid_order_dlvy.resetData(list);
			}				
		});
	}
	
	getOrderCancelList = function() {
		var dataJson = {
			'searchListType' : 'CANCEL',
			'searchReqTyCode' : 'C01'
		};
		
		$.ajax({
			url:CTX_ROOT + '/decms/shop/goods/getOrderReqList.json',
			type:'POST',
			data:dataJson,
			dataType:'json',
			success:function(result){
				var cnt = result.data.paginationInfo.totalRecordCount;
				if (cnt > 0) {
					$('#order-cancel-cnt').text(cnt);
					$('#order-cancel-cnt').removeClass('badge-secondary');
					$('#order-cancel-cnt').addClass('badge-danger');
				}
				var list = result.data.list;
				grid_order_cancel.resetData(list);
			}				
		});
	
	getOrderStopList = function() {
		var dataJson = {
			'searchListType' : 'STOP',
			'searchReqTyCode' : 'T01'
		};
		
		$.ajax({
			url:CTX_ROOT + '/decms/shop/goods/getOrderReqList.json',
			type:'POST',
			data:dataJson,
			dataType:'json',
			success:function(result){
				var cnt = result.data.paginationInfo.totalRecordCount;
				if (cnt > 0) {
					$('#order-stop-cnt').text(cnt);
					$('#order-stop-cnt').removeClass('badge-secondary');
					$('#order-stop-cnt').addClass('badge-danger');
				}
				
				var list = result.data.list;
				grid_order_stop.resetData(list);
			}				
		});
	
	}
	
	getOrderExchangeList = function() {
		var dataJson = {
			'searchListType' : 'EXCHANGE',
			'searchReqTyCode' : 'E01'
		};
	
		$.ajax({
			url:CTX_ROOT + '/decms/shop/goods/getOrderReqList.json',
			type:'POST',
			data:dataJson,
			dataType:'json',
			success:function(result){
				var cnt = result.data.paginationInfo.totalRecordCount;
				if (cnt > 0) {
					$('#order-exchange-cnt').text(cnt);
					$('#order-exchange-cnt').removeClass('badge-secondary');
					$('#order-exchange-cnt').addClass('badge-danger');
				}
				
				var list = result.data.list;
				grid_order_exchange.resetData(list);
			}				
		});
		
		}
	}
	
	getOrderRefundList = function() {
		var dataJson = {
			'searchListType' : 'RECALL',
			'searchReqTyCode' : 'R01'
		};
	
		$.ajax({
			url:CTX_ROOT + '/decms/shop/goods/getOrderReqList.json',
			type:'POST',
			data:dataJson,
			dataType:'json',
			success:function(result){
				var cnt = result.data.paginationInfo.totalRecordCount;
				if (cnt > 0) {
					$('#order-refund-cnt').text(cnt);
					$('#order-refund-cnt').removeClass('badge-secondary');
					$('#order-refund-cnt').addClass('badge-danger');
				}
				var list = result.data.list;
				grid_order_exchange.resetData(list);
			}				
		});

	}
	
	getReviewList = function() {
		/*var dataJson = {

		};*/
		
		$.ajax({
			url:CTX_ROOT + '/decms/shop/goods/review/reviewList.json',
			type:'GET',
			//data:dataJson,
			dataType:'json',
			success:function(result){
				var list = result.data.list;
				grid_review.resetData(list);
				grid_review.removeRow(5);
				grid_review.removeRow(6);
				grid_review.removeRow(7);
				grid_review.removeRow(8);
				grid_review.removeRow(9);
			}				
		});
	
	}
	
	getQnaList = function() {
		var dataJson = {
			'searchQnaProcessSttusCode' : 'R'
			, 'qaSeCode' : 'goods'
		};
		
		$.ajax({
			url:CTX_ROOT + '/decms/qainfo/qainfoList.json',
			type:'GET',
			data:dataJson,
			dataType:'json',
			success:function(result){
				var cnt = result.data.paginationInfo.totalRecordCount;
				if (cnt > 0) {
					$('#qna-cnt').removeClass('badge-secondary');
					$('#qna-cnt').addClass('badge-danger');
					$('#qna-cnt').text(cnt);
				}
				var list = result.data.list;
				grid_qna.resetData(list);
			}				
		});
	}
	
	
	openGoodsPop = function(goodsId) {
		var name = '';
	    var option = 'width = ' + window.innerWidth + ', height = ' + window.innerHeight + ', top = 0, left = 0, location = no';
		window.open('/shop/goods/goodsView.do?goodsId=' + goodsId, name, option);
	}


})();