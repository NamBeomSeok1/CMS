(function() {
	var isAdmin =$('#isAdmin').val();

	$(document).ready(function(){
		grid_qainfo = new tui.Grid({
			el: document.getElementById('data-qainfo'),
			bodyHeight: 240,
			columns: [
				{ header: '등수', width: 100, align: 'center',
					formatter:function (item){
						return item.row.rowKey+1;
						}
					},
				{ header: '이름', name: 'usrNm', width: 100, align: 'center', },
				{ header: '기록', name: 'rcord', width:300, align: 'center',	},
				{ header: '참여횟수', name: 'partcptnCo' ,width: 100, align: 'center'},
				{ header: '등록일', name: 'regstPnttm', width: 100, align: 'center',
					formatter: function(item) {
						return isEmpty(item.value) ? '' : moment(item.value).format('YY-MM-DD');
					}},
				{ header: '관리', name: '', width: 100, align: 'center',
					formatter: function(item) {
						return '<button onclick="deleteBbs('+item.row.bbsNo+');">삭제</button>';
					}},
			],
			columnOptions: {
				frozenCount: 1,
				frozenBorderWidth: 2,
			}
		});
		getQainfoList();


	});
	var grid_qainfo;
	getQainfoList = function() {
		$.ajax({
			url:CTX_ROOT + '/decms/bbs/bbsList.json',
			type:'GET',
			success:function(result){
				console.log(result);
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

	addBbs=function (){

		var usrNm = $('#usrNm').val();
		var rcord = '';
		var min = $('#rcord1').val();
		var sec = $('#rcord2').val();
		var milSec = $('#rcord3').val();
		rcord = min+':'+sec+'.'+milSec;
		console.log(isEmpty(usrNm));
		console.log(isEmpty(min));
		console.log(isEmpty(sec));
		console.log(isEmpty(milSec));
		if(usrNm==''||min==''||sec==''||milSec==''){
			alert('값을 정확히 입력해주세요.');
		}else{
			if(sec.length<2 || min.length<2 || milSec.length<3){
				alert('분,초,밀리초의 길이를 다시 확인해주세요.');
				return false;
			}
			var data = {
				'usrNm':usrNm,
				'rcord':rcord
			};
			$.ajax({
				url:CTX_ROOT + '/decms/bbs/writeBbs.json',
				type:'POST',
				data:data,
				dataType:'json',
				success:function(result){
					 $('#usrNm').val('');
					 $('#rcord1').val('');
					 $('#rcord2').val('');
					 $('#rcord3').val('');

					$.ajax({
						url:CTX_ROOT + '/decms/bbs/bbsList.json',
						type:'GET',
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
			});
		}

	}

	deleteBbs=function(bbsNo){
		var result = confirm('삭제하시겠습니까?');
		if(result){
			var data={
				'bbsNo':bbsNo
			};
			$.ajax({
				url:CTX_ROOT + '/decms/bbs/deleteBbs.json',
				type:'POST',
				data:data,
				dataType:'json',
				success:function(result){
					console.log(result);

					$.ajax({
						url:CTX_ROOT + '/decms/bbs/bbsList.json',
						type:'GET',
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
			});
		}
	}








})();