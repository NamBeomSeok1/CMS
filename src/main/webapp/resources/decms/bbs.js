(function() {
	var isAdmin =$('#isAdmin').val();

	$(document).ready(function(){
		grid_qainfo = new tui.Grid({
			el: document.getElementById('data-qainfo'),
			bodyHeight: 500,
			columns: [
				{ header: '등수', name:'rn', width: 100, align: 'center',
					formatter:function (item){
						return '<span>'+Number(item.row.rn)+'</span>';
					},
					},
				{ header: '이름', name: 'usrNm', width: 100, align: 'center', },
				{ header: '기록', name: 'rcord', width:300, align: 'center',	},
				{ header: '참여횟수', name: 'partcptnCo' ,width: 100, align: 'center'},
				{ header: '등록일', name: 'regstPnttm', width: 100, align: 'center',
					formatter: function(item) {
						/*return isEmpty(item.value) ? '' : moment(item.value).format('YY-MM-DD');*/
						return item.value;
					}},
				{ header: '관리', name: '', width: 100, align: 'center',
					formatter: function(item) {
						return '<button class="removeBtn" data-no="'+item.row.bbsNo+'" onclick="deleteBbs(\'ONE\',this);">삭제</button>';
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

	grid_qainfo2 = new tui.Grid({
		el: document.getElementById('data-qainfo2'),
		bodyHeight: 250,
		columns: [
			{ header: '등수', width: 100, align: 'center',
				formatter:function (item){
					return '<span>'+Number(item.row.rn)+'</span>';
				},
			},

			{ header: '이름', name: 'usrNm', width: 100, align: 'center', },
			{ header: '기록', name: 'rcord', width:300, align: 'center',	},
			{ header: '참여횟수', name: 'partcptnCo' ,width: 100, align: 'center'},
			{ header: '등록일', name: 'regstPnttm', width: 100, align: 'center',
				formatter: function(item) {
					/*return isEmpty(item.value) ? '' : moment(item.value).format('YY-MM-DD');*/
					return item.value;
				}},
		],
		columnOptions: {
			frozenCount: 1,
			frozenBorderWidth: 2,
		}
	});
	getQainfoList2();

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
	var grid_qainfo2;
	addFilter=function (){

		var frstPnttm = $('input[name=searchBgnde]').val();
		var lastPnttm = $('input[name=searchEndde]').val();
		var dplctAt = $('#dplctAt').val();
		var dateUseAt = '';
		if($('#dateUseAt').prop('checked')){
			dateUseAt = 'Y';
		}else{
			dateUseAt = 'N';
		}
		console.log(dateUseAt);
		if(frstPnttm==''||lastPnttm==''||dplctAt==''){
			alert('검색 값을 정확히 입력해주세요.');
			return false;
		}else{
			var data = {
				'frstPnttm':frstPnttm,
				'lastPnttm':lastPnttm,
				'dplctAt':dplctAt,
				'dateUseAt':dateUseAt
			};
			$.ajax({
				url:CTX_ROOT + '/decms/bbs/writeFilter.json',
				type:'POST',
				data:data,
				dataType:'json',
				success:function(result){
					alert('검색 설정이 변경되었습니다.');
					getQainfoList();
					getQainfoList2();
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


	getQainfoList2 = function() {
		$.ajax({
			url:CTX_ROOT + '/bbs/bbsList.json',
			type:'GET',
			success:function(result){

				var list = result.data.list;
				grid_qainfo2.resetData(list);
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

					getQainfoList();
					getQainfoList2();
				}
			});
		}

	};

	$(document).on('click','#dateUseAt',function (e) {
		if($(this).prop('checked')){
			$('#beginDate').attr('disabled',false);
			$('#endDate').attr('disabled',false);
		}else{
			$('#beginDate').attr('disabled',true);
			$('#endDate').attr('disabled',true);
		}
	});

	deleteBbs=function(type,e){
		var result = confirm('삭제하시겠습니까?');
		/*if($('#dplctAt').val()=='N'){
			alert('중복허용으로 바꾼 후 삭제가 가능합니다.');
			return false;
		}*/
		if(result){
			var data = new Array();
			if (type == 'ONE'){
				data.push(e.dataset.no);
			}else if(type == 'ALL'){
				var removeBtnArr = document.querySelectorAll('.removeBtn');
				if(removeBtnArr.length > 0){
					removeBtnArr.forEach(function (item){
						data.push(item.dataset.no);
					});
				}else{
					alert('삭제할 목록이 없습니다.');
					return false;
				}
			}
			$.ajax({
				url:CTX_ROOT + '/decms/bbs/deleteBbs.json',
				type:'POST',
				data:{
					'bbsNoList':data
				},
				dataType:'json',
				traditional:true,
				success:function(result){
					console.log(result);

					getQainfoList();
					getQainfoList2();
				}
			});
		}
	}








})();