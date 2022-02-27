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
						return '<button class="removeBtn" onclick="deleteBbs('+item.row.bbsNo+');">삭제</button>';
					}},
			],
			columnOptions: {
				frozenCount: 1,
				frozenBorderWidth: 2,
			}
		});
		getQainfoList();

		$('#datepicker-searchBgnde').datetimepicker({
			locale: 'ko',
			format: 'YYYY-MM-DD'
		});

		$('#datepicker-searchEndde').datetimepicker({
			locale: 'ko',
			format: 'YYYY-MM-DD'
		});


	});
	var grid_qainfo;
	addFilter=function (){

		var frstPnttm = $('input[name=searchBgnde]').val();
		var lastPnttm = $('input[name=searchEndde]').val();
		var dplctAt = $('#dplctAt').val();
		if(frstPnttm==''||lastPnttm==''||dplctAt==''){
			alert('검색 값을 정확히 입력해주세요.');
			return false;
		}else{
			var data = {
				'frstPnttm':frstPnttm,
				'lastPnttm':lastPnttm,
				'dplctAt':dplctAt
			};
			$.ajax({
				url:CTX_ROOT + '/decms/bbs/writeFilter.json',
				type:'POST',
				data:data,
				dataType:'json',
				success:function(result){
					alert('검색 설정이 변경되었습니다.');
					$.ajax({
						url:CTX_ROOT + '/decms/bbs/bbsList.json',
						type:'GET',
						success:function(result){
							var list = result.data.list;
							grid_qainfo.resetData(list);
						}
					});
				}
			});
		}

	}
	getQainfoList = function() {
		$.ajax({
			url:CTX_ROOT + '/decms/bbs/bbsList.json',
			type:'GET',
			success:function(result){

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
		if($('#dplctAt').val()=='N'){
			alert('중복허용으로 바꾼 후 삭제가 가능합니다.');
			return false;
		}
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

							var list = result.data.list;
							grid_qainfo.resetData(list);
						}
					});
				}
			});
		}
	}








})();