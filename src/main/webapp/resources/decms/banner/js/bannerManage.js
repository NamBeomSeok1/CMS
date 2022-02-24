(function() {
	
	const grid = new tui.Grid({
		el: document.getElementById('data-grid'),
		rowHeight: 'auto',
		columns: [
			/*
			{ header: 'No.', name: 'rn', width: 50, align: 'center',
				formatter: function(item) {
					return item.value;
				}
			},
			*/
			{ header: '제휴사', name: 'prtnrNm', width: 80, align: 'center'},
			{ header: '구분', name: 'bannerSeCodeNm', width: 100, align: 'center'},
			{ header: '이미지', name: 'bannerPath', width: 120,
				formatter: function(item) {
					return '<div style="overflow:hidden; height:35px;"><img src="'+ item.value + '" style="max-width:100%;"/></div>';
				}
			},
			{ header: '배너제목', name: 'bannerNm',
				formatter: function(item) {
					return item.value + '<br/>( ' + ( isEmpty(item.row.bannerLink)?'':item.row.bannerLink) + ' )';
				}
			},
			{ header: '바로가기', name: 'bannerLink', width: 60, align: 'center',
				formatter: function(item) {
					if(!isEmpty(item.value)) {
						return '<a href="' + item.value + '" target="_blank" class="btn btn-outline-dark btn-sm"><i class="fas fa-link"></i></a>';
					}else {
						return '-';
					}
				}
			},
			{ header: '기간', name: 'bannerBgnde', width: 250, align: 'center',
				formatter: function(item) {
					var dateStr = moment(item.row.bannerBgnde,'YYYYMMDDhhmmss').format('YYYY-MM-DD HH:mm')  + ' ~ ' 
								+ moment(item.row.bannerEndde,'YYYYMMDDhhmmss').format('YYYY-MM-DD HH:mm');
					return dateStr;
				}
			},
			{ header: '활성', name: 'actvtyAt', width: 40, align: 'center',
				formatter: function(item) {
					if(item.value == 'Y') {
						return '<div class="bg-primary text-white pt-1 pb-1"><i class="far fa-circle"></i></div>';
					}else {
						return '<div class="bg-secondary text-white pt-1 pb-1"><i class="fas fa-times"></i></div>';
					}
				}
			},
			{ header: '생성일', name: 'frstRegistPnttm', width: 100, align: 'center',
				formatter: function(item) {
					return isEmpty(item.value) ? '' : moment(item.value).format('YYYY-MM-DD');
				}
			},
			{ header: '관리', name: 'bannerId', width: 100, align: 'center',
				formatter: function(item) {
					return 	'<a href="/decms/embed/banner/modifyBanner.do?bannerId=' + item.value + '" class="btn btn-success btn-sm mr-1 btnModify" data-target="#bannerModal" title="수정">' 
						+ 		'<i class="fas fa-edit"></i></a>' 
						+ 	'<a href="/decms/banner/deleteBanner.json?bannerId=' + item.value + '" class="btn btn-danger btn-sm btnDelete" title="삭제">' 
						+ 		'<i class="fas fa-trash"></i></a>';
				}
			},
		],
		columnOptions: {
			frozenCount: 1,
			frozenBorderWidth: 1,
			resizable:true
		}
	});
	
	tui.Grid.applyTheme('striped');
	
	var pagination = grid.getPagination();
	var gridPageUnit = $('#pageUnit').val();
	if(!gridPageUnit) gridPageUnit = 10;
	
	var pagination = new tui.Pagination(document.getElementById('data-grid-pagination'), {
		totalItems: 10,
		itemsPerPage: gridPageUnit,
		visiblePages: 5,
		centerAlign: true
	});
	
	pagination.on('beforeMove', function(e) {
		getDataList(e.page);
	});
	
	// 데이터 목록
	function getDataList(page) {
		var $form = $('#searchForm');
		var actionUrl = $form.attr('action');
		if(isEmpty(page)) {
			page = $form.find('#pageIndex').val(page);
		}else if(page > 0) {
			$form.find('#pageIndex').val(page);
		}
		var formData = $form.serialize();
		
		$.ajax({
			url: actionUrl,
			type: 'get',
			data: formData,
			dataType: 'json',
			success: function(result) {
				if(result.message) {
					bootbox.alert({ title: '확인', message: result.message, size: 'small' }); 
				}
				
				if(result.success) {
					var paginationObj = result.data.paginationInfo;
					
					if(paginationObj.currentPageNo == 1) {
						pagination.setItemsPerPage(paginationObj.recordCountPerPage);
						pagination.reset(paginationObj.totalRecordCount);
					}
					grid.resetData(result.data.list);
				}
			}
		});
	};
	
	// 입력폼 초기화
	function initArticleForm(modal) {
		$(modal).on('show.bs.modal', function(event) {
			initWebEditor();
			
			$('#datepicker-bannerBgnDate').datetimepicker({
				locale: 'ko',
				format: 'YYYY-MM-DD'
			});
			
			$('#datepicker-bannerEndDate').datetimepicker({
				locale: 'ko',
				format: 'YYYY-MM-DD'
			});
			
			var type = $("#bannerSeCode").val();
			
			if(type == "BANN003"){
				$(".type2").show();
				if($('input[name=bannerTyCode]:checked').val() == 'EVENT') {
					$('.bannerGoods').hide();
				}else {
					$('.bannerSeCodeList').find('input').attr('disabled', true);
				}
			}else if(type == "BANN004"){
				$(".type2, .type3no").hide();
				$(".type3").show();
				$('.bannerSeCodeList').find('input').attr('disabled', false);
			}
		});
	};
	
	/** 삭제 **/
	function deleteBanner(actionUrl) {
		$.ajax({
			url: actionUrl,
			type: 'post',
			dataType: 'json',
			success: function(result) {
				if(result.message) {
					bootbox.alert({ title: '확인', message: result.message, size: 'small' }); 
				}
				
				if(result.success) {
					getDataList(1);
				}
			}
		});
	};
	
	$(document).ready(function() {
		getDataList(1);
	});

	// 검색 submit
	$(document).on('submit', '#searchForm', function(e) {
		e.preventDefault();
		
		getDataList(1);
	});
	
	//목록갯수
	$(document).on('change', '#pageUnit', function() {
		getDataList(1);
	});
	
	// 등록 Click 
	$(document).on('click', '.btnAdd', function(e) {
		e.preventDefault();
		
		var actionUrl = $(this).attr('href');
		var modal = $(this).data('target');
		
		initArticleForm(modal);
		modalShow(actionUrl, '배너 등록', modal);
	});
	
	// 수정 Click
	$(document).on('click','.btnModify', function(e) {
		e.preventDefault();
		
		var actionUrl = $(this).attr('href');
		var modal = $(this).data('target');
		
		initArticleForm(modal);
		modalShow(actionUrl,'배너 수정', modal);
	});
	
	// 삭제 Click
	$(document).on('click','.btnDelete', function(e) {
		e.preventDefault();
		var actionUrl = $(this).attr('href');
		
		bootbox.confirm({
			title: '삭제확인',
			message: '삭제하시겠습니까?',
			callback: function(result) {
				if(result) {
					deleteBanner(actionUrl);
				}
			}
		});
	});
	
	// 저장
	$(document).on('submit', '#registForm', function(e) {
		e.preventDefault();
		var $self = $(this);
		
		var actionUrl = $(this).attr('action');
		var method = $(this).attr('method');
		var type = $("#bannerSeCode").val();
		var submitAt = true;
		
		//투데이스 픽
		if(type == "BANN003"){
			if(!$("#bannerMPath").val()){
				submitAt = false;
				alert("모바일 배너를 입력해주세요.");
			}else if(!$("#evtTxt").val()){
				submitAt = false;
				alert("한줄소개를 입력해주세요.");
			}else if(!$("#goodsId").val() && $('input[name=bannerTyCode]:checked').val() == 'GOODS'){
				submitAt = false;
				alert("상품ID를 입력해주세요.");
			//}else if($("input[name=dfk]:checked").length == 0){
			//	submitAt = false;
			//	alert("요일을 선택해주세요.");
			}
		}
		
		if(submitAt){
			$(this).ajaxSubmit({
				url: actionUrl,
				type: method,
				dataType: 'json',
				success: function(result) {
					if(result.message) {
						bootbox.alert({ title: '확인', message: result.message, size: 'small' }); 
					}
					
					if(result.success) {
						$self.parents('.modal').modal('hide');
						getDataList(1);
					}
				}
			});
		}
	});
	
	//배너링크 - 투데이스 픽
	$(document).on('change', '#goodsId', function(e) {
		$('#bannerLink').val(CTX_ROOT + '/shop/goods/goodsView.do?goodsId=' + $(this).val());
	});
	
	//배너구분 변경
	$(document).on('change', '#bannerSeCode', function(e) {
		var val = $(this).val();
		
		if(val == "BANN003"){
			$(".type2").show();
			if($('input[name=bannerTycode]:checked').val() == 'GOODS') {
				$('.bannerSeCodeList').find('input').attr('disabled', true);
				$('input[name="bannerLbl"]').val('모두\'s PICK');
			}else {
				$('.bannerSeCodeList').find('input').attr('disabled', false);
			}
		}else if(val == "BANN004"){
			$(".type2, .type3no").hide();
			$(".type3").show();
			$('.bannerSeCodeList').find('input').attr('disabled', false);
		}else{
			$(".type3").show();
			$(".type2").hide();
		}
	});
	
	//배너배경 색
	/*
	$(document).on('keyup', '#bcrnClor', function(e) {
		$(this).css({"background-color" : $(this).val()});
	});
	*/ 
	$(document).on('change', '.inputColor', function() {
		var colorVal = $(this).val();
		$(this).parent().find('.input-group-text').val(colorVal);
	});
	$(document).on('change', '.hexValue', function() {
		var hex = $(this).val();
		$(this).parent().parent().find('.inputColor').val(hex);
	});
	
	//요일 전체 선택
	$(document).on('change', '#dfkAllCheck', function() {
		var isChecked = $(this).is(':checked');
		if($(this).is(':checked')) {
			console.log($('.check-dfk'));
			$('.check-dfk').prop('checked',true);
		}else {
			$('.check-dfk').prop('checked',false);
		}
	});
	
	//요일 선택
	$(document).on('change', '.check-dfk', function() {
		var cnt = $('input[name=dfk]:checked').length;
		if(cnt <= 7) {
			$('#dfkAllCheck').prop('checked', false);
		}
	});
	
	//배너타입코드
	$(document).on('change', '.radioBannerTyCode', function() {
		if($(this).is(':checked')) {
			if($(this).val() == 'GOODS') {
				$('.bannerGoods').show();
				$('.bannerSeCodeList').find('input').attr('disabled', true);
				$('input[name="bannerLbl"]').val('모두\'s PICK');
			}else if($(this).val() == 'EVENT') {
				$('.bannerGoods').hide();
				$('.bannerSeCodeList').find('input').attr('disabled', false);
				$('input[name="bannerLbl"]').val('EVENT');
			}
		}
	});
	
})();