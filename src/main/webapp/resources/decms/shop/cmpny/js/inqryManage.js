(function() {

	var menuId = $('[name=menuId]').val();
	var pageIndex = 1;
	
	const grid = new tui.Grid({
		el: document.getElementById('data-grid'),
		rowHeight: 'auto',
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
			{ header: '이메일주소', name: 'cmpnyEmail', width: 200, align: 'center'},
			{ header: '입점내용', name: 'cmpnyIntrcn', width: 100, align: 'center',
				formatter: function(item) {
					return '<a href="#none" onclick="showInqryDetail(\'' + item.row.inqryId + '\');">상세보기</a>';
				}
			}
			/*{ header: '처리상태', name: 'status1', width: 200, align: 'center'},
			{ header: '처리현황', name: 'status2', width: 200, align: 'center',
				formatter: function(item) {
					return '<a href="#none" onclick="alert(\'준비중\');"><span class="btn btn-primary btn-sm">승인</span></a><a href="#none" onclick="alert(\'준비중\');"><span class="btn btn-danger btn-sm">삭제</span></a>';
				}
			}*/
		],
		columnOptions: {
			frozenCount: 1,
			frozenBorderWidth: 2,
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
	
	/** 입점문의 목록 */
	function getDataList(page) {
		var actionUrl = '/decms/cmpny/inqryList.json';
		var dataJson = {
			'pageIndex' : pageIndex
		};
		
		jsonResultAjax({
			url: actionUrl,
			type: 'get',
			data: dataJson,
			//dataType: 'json',
			callback: function(result) {
			
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
	
	/** 입점문의 상세보기 */
	showInqryDetail = function(id){
		var actionUrl = '/decms/cmpny/inqryDetail.json';
		var dataJson = {
			'inqryId' : id
		};
		
		jsonResultAjax({
			url: actionUrl,
			type: 'get',
			data: dataJson,
			//dataType: 'json',
			callback: function(result) {
				console.log(result.data.cmpnyInqry);
				var cmpnyInqry = result.data.cmpnyInqry;
				var msg = '';
				msg += '<div><h6><span class="badge badge-light">회사명</span></h6></div>';
				msg += '<div><p>' + cmpnyInqry.cmpnyNm + '</p></div>';
				msg += '<div><h6><span class="badge badge-light">회사소개</span></h6></div>';
				msg += '<div><p>' + cmpnyInqry.cmpnyIntrcn + '</p></div>';
				msg += '<div><h6><span class="badge badge-light">상품소개</span></h6></div>';
				msg += '<div><p>' + cmpnyInqry.goodsIntrcn + '</p></div>';
				msg += '<div><h6><span class="badge badge-light">담당자</span></h6></div>';
				msg += '<div><p>' + cmpnyInqry.cmpnyCharger + '</p></div>';
				msg += '<div><h6><span class="badge badge-light">연락처</span></h6></div>';
				msg += '<div><p>' + cmpnyInqry.telno + '</p></div>';
				msg += '<div><h6><span class="badge badge-light">이메일</span></h6></div>';
				msg += '<div><p>' + cmpnyInqry.cmpnyEmail + '</p></div>';
				msg += '<div><h6><span class="badge badge-light">문의 일시</span></h6></div>';
				msg += '<div><p>' + moment(cmpnyInqry.frstRegistPnttm, 'YYYYMMDD').format('YYYY-MM-DD') + '</p></div>';
				if(result.data.files!=null){
					var files = result.data.files;
					msg += '<div><h6><span class="badge badge-light">첨부파일</span></h6></div>';
					msg += '<div><ul>';
					console.log(files);
					for(var i=0;i<files.length;i++){
						msg+='<li>';
						msg+='<p>'+files[i].orignlFileNm+'</p>';
						msg+='<a href="/fms/downloadFile.do?atchFileId='+files[i].atchFileId+'&fileSn='+files.fileSn+'" class="atch_download">다운로드</a>';
						msg+='<li>';
					}
					msg += '</ul></div>';
				}
				
				var dialog = bootbox.dialog({
				    title: cmpnyInqry.cmpnyNm + '의 입점 문의',
				    message: msg,
				    size: 'large',
				    buttons: {
				        ok: {
				            label: "확인",
				            className: 'btn-info',
				            callback: function(){
				                //console.log('Custom OK clicked');
				            }
				        }
				    }
				});
				
			}
		});
	}
	
	/** 이벤트 목록 */	
	$(document).ready(function() {
		getDataList(1);
	})
	
})();