(function() {
	
	const grid = new tui.Grid({
		el: document.getElementById('data-grid'),
		rowHeight: 'auto',
		treeColumnOptions: {
			name: 'goodsCtgryNm'
		},
		columns: [
			{ header: '카테고리명', name: 'goodsCtgryNm', width: 320 },
			{ header: '노출구분', name:'ctgryExpsrSeCode', width:80, align:'center',
				formatter: function(item) {
					if(item.value == 'ALL') {
						return '<div class="bg-primary text-white">모두노출</div>';
					}else if(item.value == 'B2B') {
						return '<div class="bg-success text-white">B2B</div>';
					}else if(item.value == 'B2C') {
						return '<div class="bg-info text-white">B2C</div>';
					}else if(item.value == 'NONE') {
						return '<div class="bg-secondary text-white">미노출</div>';
					}
				}
			},
			/*{ header: '노출 여부', name:'actvtyAt', width:80, align:'center',
				formatter: function(item) {
					if(item.value == 'Y') {
						return '<i class="far fa-circle"></i>';
					}else {
						return '<i class="fas fa-times"></i>';
					}
				}
			},*/
			{ header: '관리', name: 'goodsCtgryId', width: 130, align: 'right',
				formatter: function(item) {
					var appendBtn = '<a href="' + CTX_ROOT + '/decms/embed/shop/goods/writeGoodsCtgry.do?upperGoodsCtgryId=' + item.value + '" class="btn btn-primary btn-sm btnAddChild mr-3"  data-target="#goodsCtgryModal">'
							+		'<i class="fas fa-plus"></i></a>';
					
					var ctrlTag =	'<a href="' + CTX_ROOT + '/decms/embed/shop/goods/modifyGoodsCtgry.do?goodsCtgryId=' + item.value + '" class="btn btn-success btn-sm mr-1 btnModify"  data-target="#goodsCtgryModal" title="수정">' 
								+ 		'<i class="fas fa-edit"></i></a>' 
								+ 	'<a href="' + CTX_ROOT + '/decms/shop/goods/deleteGoodsCtgry.json?goodsCtgryId=' + item.value + '" class="btn btn-danger btn-sm btnDelete" title="삭제">' 
								+ 		'<i class="fas fa-trash"></i></a>';
					
					if(grid.getDepth(item.row.rowKey) < 3) {
						ctrlTag = appendBtn + ctrlTag;
					}
					
					return ctrlTag;
				}
			},
			{ header:'상품 리스트', name: 'goodsListCnt', width: 130, align: 'center', 
				formatter: function(item){
					//console.log(item);
					if(item.value > 0) {
						if(item.row.upperGoodsCtgryId =='GCTGRY_0000000000000'){
						return 	'<button type="button" class="btn btn-primary btn-sm w-100 btnGoodsList" data-target="#goodsListModal" data-ctgry-id="' + item.row.goodsCtgryId + '">'
								+	'<i class="fas fa-shopping-bag"></i> 상품 리스트(' + item.value + ')개'
								+	'</button>';
							}
						return '';
					}
					/*
					if(item.value>0) {
					var goodsListBtn = '<button class="btn btn-primary btn-sm" id="goodsList" data-target="#goodsListModal" data-ctgryid="'+item.row.__storage__.goodsCtgryId+'">상품 리스트 ('+item.value+')개</button>';
					return goodsListBtn;
					}
					return;
					*/
				}
			},
		],
		columnOptions: {
			resizable:true
		}
	});
	tui.Grid.applyTheme('striped');
	
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
					grid.resetData(result.data.list);
				}
			}
		});
	};
	
	//상품 리스트 클릭
	/*
	$(document).on('click','#goodsList',function(){
		
		var ctgryId = $(this).data('ctgryid');
		var actionUrl = CTX_ROOT + '/decms/embed/shop/goods/ctgryGoodsList.do?ctgryId=' + ctgryId;
		var modal = $(this).data('target');
		
		modalShow(actionUrl, '상품 리스트', modal);
	})
	*/

	
	// 삭제
	function deleteGoodsCtgry(actionUrl) {
		$.ajax({
			url: actionUrl,
			type: 'post',
			dataType: 'json',
			success: function(result) {
				if(result.message) {
					bootbox.alert({ title: '확인', message: result.message, size: 'small' }); 
				}
				
				if(result.success) {
					getDataList();
				}
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
	
	// 등록 Click 
	$(document).on('click', '.btnAdd', function(e) {
		e.preventDefault();
		
		var actionUrl = $(this).attr('href');
		var modal = $(this).data('target');
		
		modalShow(actionUrl, '상품카테고리 등록', modal);
	});
	
	// 수정 Click
	$(document).on('click','.btnModify', function(e) {
		e.preventDefault();
		
		var actionUrl = $(this).attr('href');
		var modal = $(this).data('target');
		
		modalShow(actionUrl,'상품카테고리 수정', modal);
		
	
	});

	// 삭제 Click
	$(document).on('click','.btnDelete', function(e) {
		e.preventDefault();
		var actionUrl = $(this).attr('href');
		
		bootbox.confirm({
			title: '삭제확인',
			message: '하위 항목이 모두 삭제 처리됩니다.<br/>삭제하시겠습니까?',
			callback: function(result) {
				if(result) {
					deleteGoodsCtgry(actionUrl);
				}
			}
		});
	});
	
	//하위 카테고리 추가 Click
	$(document).on('click', '.btnAddChild', function(e) {
		e.preventDefault();
		var actionUrl = $(this).attr('href');
		var modal = $(this).data('target');
		
		modalShow(actionUrl, '상품카테고리 등록', modal);
		
	});
	
	// 저장
	$(document).on('submit', '#registForm', function(e) {
		e.preventDefault();
		var $self = $(this);
		
		var actionUrl = $(this).attr('action');
		var method = $(this).attr('method');
		
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
	});
})();	