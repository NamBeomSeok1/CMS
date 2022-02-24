var goodsId = $('#goodsId').val();
var target='qaWrite';
$(document).ready(function(){
	qainfoList(1);
});

	// QA 리스트
	function qainfoList(pageIndex){
		var actionUrl = '/qainfo/qainfoList.json';
		$.ajax({
			url:actionUrl,
			data:{goodsId:goodsId,pageIndex:pageIndex},
			dataType:'json',
			cache: false,
			success:function(result){
				if(result.success){
				var list = result.data.list;
				var html ='';
				//QNA총 개수 
				/*$('.anchor-detail03').find('em').text('('+result.data.paginationInfo.totalRecordCount+')');*/
				$('.qaTotalCount').text('('+result.data.paginationInfo.totalRecordCount+')');
				if(result.data.paginationInfo.totalRecordCount==0){
					html='<li><p class="none-txt">작성된 Q&amp;A가 없습니다.</p></li>';
					$('.qa-list').html(html);
					
				}else{
					for(var i=0;i<list.length;i++){
							html+='<li id="qa-item">'
							html+='<div class="accordion-tit-area">';
								//답변대기
								if(list[i].qnaProcessSttusCode == 'R'){
									html+='<strong>답변대기</strong>';
								//답변완료
								}else if(list[i].qnaProcessSttusCode == 'C'){
									html+='<button type="button" class="btn-accordion-toggle"><span class="txt-hide">토글버튼</span></button>';
									html+='<strong class="fc-spot">답변완료</strong>';
								}
								//질문 항목
								for(var j=0;j<result.data.qestnTyCodeList.length;j++){
									if(list[i].qestnTyCode==result.data.qestnTyCodeList[j].code && list[i].secretAt == 'N' ){
										html+='<div>['+result.data.qestnTyCodeList[j].codeNm+']'+ list[i].qestnCn+'</div>';
										
									}else if(list[i].qestnTyCode==result.data.qestnTyCodeList[j].code && list[i].secretAt == 'Y' && list[i].isLogin==false){
										html+='<div>'+list[i].qestnCn+'</div>';
										
									}else if(list[i].qestnTyCode==result.data.qestnTyCodeList[j].code && list[i].secretAt == 'Y' && list[i].isLogin==true){
										html+='<div>['+result.data.qestnTyCodeList[j].codeNm+']'+ list[i].qestnCn+'</div>';
									}
								}
								html+='<cite>'+list[i].wrterNm+'</cite>';
								html+='<span class="date">'+moment(list[i].frstRegistPnttm).format('YYYY-MM-DD')+'</span>';
								//수정/삭제(권한)
								if(list[i].isLogin==true){
									html += '<div class="btn-area">';
									if(list[i].qnaProcessSttusCode == 'R'){
										html += '<button type="button" data-qaid='+list[i].qaId+' class="btn-sm-gr updateQaInfo ">수정</button>';
										html += '<button type="button" data-qaid='+list[i].qaId+' class="btn-sm-gr deleteQaInfo">삭제</button>';
									}else if(list[i].qnaProcessSttusCode == 'C'){
										html += '<button type="button" data-qaid='+list[i].qaId+'  class="btn-sm-gr deleteQaInfo">삭제</button>';
									}
									html += '</div>';
								}
								html+='</div>';
								//답변 내용
								if(list[i].qnaProcessSttusCode == 'C'){
									if(list[i].isLogin==true){
									html+='<div class="accordion-txt-area">';
									html+='<em>RE :</em>';
									html+='<div>';
									html+=list[i].answerCn;
									html+='</div>';
									html+='</div>';
								}else if(list[i].isLogin==false && list[i].secretAt=='Y'){
									html+='<div class="accordion-txt-area">';
									html+=list[i].qestnCn;
									html+='</div>';
								}else{
									html+='<div class="accordion-txt-area">';
									html+='<em>RE :</em>';
									html+='<div>';
									html+=list[i].answerCn;
									html+='</div>';
									html+='</div>';
								}
								html+='</li>';
							}
						}
						$('.qa-list').html(html);
						$('.accordion').accordionFunc();
						qaPaging($('#qa-paging'),result.data.paginationInfo.totalRecordCount,pageIndex);
					}
				}
			}
		})
	}

	//페이징
	function qaPaging($pagingSector,totalCount,currentPage){
		const totalPage = Math.ceil(totalCount/10); //총 페이지 수  - 전체 데이터 개수/한 페이지에 나타낼 데이타
		const pageGroup = Math.ceil(currentPage/5); //페이지 그룹 - 현재 페이지/한 페이지에 보여줄 페이지 수
	
		var last = pageGroup * 5; //화면에 보여질 마지막 번호
		if(last > totalPage){
			last = totalPage;
		}
		var first = last - 4; // 화면에 보여질 첫번째 번호
		if(first==0 || first<0){
			first=1;
		}
		
		const foreFront = 1;
		const prev = Number(currentPage)-1;
		const next = Number(currentPage)+1;
		const rearMost = totalPage;
		
		var htmlStr='';
		//처음으로,이전
		if(prev>0){
			 if(first==foreFront){
				 htmlStr +='';
			 }
			 htmlStr +='<li class="ppv"><a href="#none" onclick="moveQaPage('+foreFront+');" title="처음으로"><span class="txt-hide">처음으로</span></a></li>';
			 htmlStr +='<li class="pv"><a href="#none" onclick="moveQaPage('+prev+');" title="이전"><span class="txt-hide">이전</span></a></li>';
			
		}
		//페이지 set
			for(var i=first; i<=last; i++){
				if(currentPage == i ){
					htmlStr += '<li class="is-active" id="currentPage" data-page="'+i+'"><a href="#none" onclick="moveQaPage('+i+');" title="to '+i+' page">'+i+'</a></li>';
		 		}else{ 
					htmlStr += '<li><a href="#none" onclick="moveQaPage('+i+');" title="to '+i+' page">'+i+'</a></li>'
				}
		 	}
			 
		//마지막으로,다음
		if(last<totalPage){
			if(last==totalPage){
				htmlStr+='';
			}
			htmlStr += '<li class="fw"><a href="#none"  onclick="moveQaPage('+next+');" title="다음"><span class="txt-hide">다음</span></a></li>';
	        htmlStr += '<li class="ffw"><a href="#none" onclick="moveQaPage('+rearMost+');"  title="끝으로"><span class="txt-hide">끝으로</span></a></li>';
		}
		
		$pagingSector.empty();
		$pagingSector.html(htmlStr);
	}
	//페이지 이동
	function moveQaPage(currentPage){
		$('.qa-list').empty();
		qainfoList(currentPage);
	}
	

   // QA등록 폼 
	$(document).on('click','.qaRegBtn',function(e){
		e.preventDefault();
		popOpen(target);
		$('.qaInfoForm').attr('id','qaInfoReg');
		 $('.qaInfoForm').attr('action','/qainfo/regstQainfo.json');
		 $('.submitBtn').attr('type','submit');
		 $('#qestnTyCode').val('');
		 $('#qestnCn').val('');
		 $('#qaId').val('');
		 $('#qaTelno1').val('');
		 $('#qaTelno2').val('');
		 $('#qaTelno3').val('');
		 $("input:checkbox[name='secretAt']").parent('label').removeClass('ui-state-active');
	})

	// QA등록
	$(document).on('submit','#qaInfoReg',function(e){
		e.preventDefault();
		var actionUrl = '/qainfo/regstQainfo.json';
		var $self = $(this);
		
		if($('#qestnTyCode').val()==null){
			modooAlert('1:1문의 유형을 선택해주세요.');
			//alert('1:1문의 유형을 선택해주세요.');
			return false;
		}else if(isEmpty($('#qaTelno1').val()) || $('#qaTelno1').val().length<3 ||( $('#qaTelno1').val()!='010'&&$('#qaTelno1').val()!='011')){
			modooAlert('휴대전화번호만 입력 가능합니다.');
			return false;
		}else if(isEmpty($('#qaTelno2').val()) || $('#qaTelno2').val().length<4){
			modooAlert('연락처를 정확히 입력해 주세요.');
			return false;
		}else if(isEmpty($('#qaTelno3').val()) || $('#qaTelno3').val().length<4){
			modooAlert('연락처를 정확히 입력해 주세요.');
			return false;
		}
		
		$self.ajaxSubmit({
			url:actionUrl,
			type:'post',
			dataType:'json',
			success:function(result){
				if(result.success){
					modooAlert(result.message,function(){
						popClose(target);
					});
					$('.qa-list').empty();
					qainfoList(1);
				}else{
					modooAlert(result.message);
				}
			}
			
		})
		
	})
	
	// QNA수정폼
	$(document).on('click','.updateQaInfo',function(){
		 
		var actionUrl = "/qaInfo/qaInfoDetail.json"
		var qaId = $(this).data('qaid');	
		 $('.submitBtn').attr('type','button');
		 
			 $.ajax({
				 url:actionUrl,
				 type:'post',
				 data:{qaId : qaId},
				 dataType:'json',
				 success:function(result){
					 var qaInfo = result.data.qaInfo;
					 if(!isEmpty(qaInfo.wrterTelno)){
						 var telno = qaInfo.wrterTelno.split('-');
						 $('#qaTelno1').val(telno[0]);
						 $('#qaTelno2').val(telno[1]);
						 $('#qaTelno3').val(telno[2]);
					 }
					 $('#qestnTyCode').val(qaInfo.qestnTyCode);
					 $('#qestnCn').val(qaInfo.qestnCn);
					 if(qaInfo.secretAt=='Y'){
						 $("input:checkbox[name='secretAt']").parent('label').addClass('ui-state-active');
					 }else{
						 $("input:checkbox[name='secretAt']").parent('label').removeClass('ui-state-active');
					 }
					 popOpen(target);
					 $('.qaInfoForm').attr('id','qaInfoUpdate');
					 $('.qaInfoForm').attr('action','/qainfo/updateQainfo.json');
					 $('#qaId').val(qaId);
				 }
			 })
	});
	
	// QNA수정
	 $(document).on('click','.submitBtn',function(){
		 
		 var actionurl=$('#qaInfoUpdate').attr('action');
		 /*var data = $('#qaInfoUpdate').serialize();*/
		 var form = document.getElementById('qaInfoUpdate');
		 var formData = new FormData(form);
		 $.ajax({
			url:actionurl,
			dataType:'json',
			data:formData,
			type:'post',
			processData: false,
			contentType: false,
			success:function(result){
				if(result.success){
					modooAlert(result.message, '', function(){
						popClose(target);
					});
					$('.qa-list').empty();
					qainfoList(1);
				}else{
					modooAlert(result.message, '', function() {
					
					});
				}
			}
		 })
	 })
	
	 //QNA삭제
	 $(document).on('click','.deleteQaInfo',function(e){
		 
		 e.preventDefault();
		 var actionUrl = '/qainfo/deleteQainfo.json';
		 var qaId = $(this).data('qaid');
		 modooConfirm('질문을 삭제하시겠습니까?','질문 삭제',function(result){
			 if(result){
			 $.ajax({
				 url:actionUrl,
				 data:{qaId : qaId},
				 dataType:'json',
				 type:'post',
				 success:function(result){
					 modooAlert(result.message, '', function(){

					});
					 $('.qa-list').empty();
					qainfoList(1);
				 }
			 })
			 }else{
				 return false;
			 }
		 });
		 
	 });


