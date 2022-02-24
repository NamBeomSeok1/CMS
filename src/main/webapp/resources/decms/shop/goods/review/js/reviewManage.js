(function() {
	const grid = new tui.Grid({
		el: document.getElementById('data-grid'),
		rowHeight: 'auto',
		columns: [
			{ header: '작성일', name: 'frstRegistPnttm', width:100, align: 'center',
				formatter: function(item) {
					return isEmpty(item.value) ? '' : moment(item.value).format('YYYY-MM-DD');
				}
			},
			{ header: '고객ID', name: 'wrterId', width: 100, align: 'center'},
			{ header: '이름', name: 'wrterNm', width: 100, align: 'center'},
			{ header: '상품명', name: 'goodsNm', width: 350, align: 'center'},
			{ header: '평점', name: 'score', width: 100, align: 'center',
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
			},
			{ header: '관리', name: 'commentId', width: 100, align: 'center',
				formatter: function(item) {
					/*return 	'<a href="' + CTX_ROOT + '/decms/shop/goods/review/modifyReview.do?commentId=' + item.row.commentId + '" class="btn btn-success btn-sm mr-1 btnModify" title="수정">' 
						+ 		'<i class="fas fa-edit"></i></a>' 
						+ 	'<a href="' + CTX_ROOT + '/decms/shop/goods/review/deleteReview.json?commentId=' + item.row.commentId + '" class="btn btn-danger btn-sm btnDelete" title="삭제">' 
						+ 		'<i class="fas fa-trash"></i></a>';*/
					return	'<a data-commentid=' + item.row.commentId + ' class="btn btn-danger btn-sm btnDelete" title="삭제">' 
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
	
	tui.Grid.applyTheme('clean');
	
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
			cache: false,
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
					console.log(result.data.list);
					
					grid.resetData(result.data.list);
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
	
	$('#datepicker-searchBgnde').datetimepicker({
		locale: 'ko',
		format: 'YYYY-MM-DD'
	});
		
	$('#datepicker-searchEndde').datetimepicker({
		locale: 'ko',
		format: 'YYYY-MM-DD'
	});
	
	//상품평삭제
	$(document).on('click','.btnDelete',function(){
		var commentId = Number($(this).data('commentid'));
		//alert(commentId);
		var result= confirm('상품평 삭제하시겠습니까?');
		if(result){
			$.ajax({
				url:CTX_ROOT+'/decms/shop/goods/review/deleteReview.json',
				data:{commentId:commentId},
				dataType:'json',
				success:function(result){
					alert('삭제되었습니다.');
					window.location.reload();
				}
			})
		}
	})
	
})();