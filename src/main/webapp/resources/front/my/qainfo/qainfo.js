(function(){
	var userNm=$('#wrterNm').val();
	var target='qaWrite';
	var pageIndex = $('#pageIndex').val();
	var qaSeCode = $('#qaSeCode').val();
	var searchCondition = $('#searchCondition').val();
	var fileCnt = 1;
	var storage = [];
	
	$('.dayCode').val(searchCondition);
	
   // 1:1문의 등록 폼 초기화
	$(document).on('click','.qaRegBtn',function(e){
		e.preventDefault();
		var obj = $('[data-popup="qaWrite"]');
		popOpen(target);
		$('.qaInfoForm').attr('id','qaInfoReg');
		$('.qaInfoForm').attr('action','/qainfo/regstQainfo.json');
		$('.submitBtn').attr('type','submit');
		$('#qestnTyCode').val('');
		$('#qestnCn').val('');
		$('#qaTelno1').val('');
		$('#qaTelno2').val('');
		$('#qaTelno3').val('');
		$('#qa-file').empty();
		$(".fileAdd").val('');
		$('#qaId').val('');
		$('#qestnSj').val(userNm+'님의 1:1질문');
		$("input:checkbox[name='secretAt']").parent('label').removeClass('ui-state-active');
	})

	// 1:1문의등록
	$(document).on('submit','#qaInfoReg',function(e){
		e.preventDefault();
		var actionUrl = '/qainfo/regstQainfo.json';
		var $self = $(this);
		
		if($('#qestnTyCode').val()==null){
			modooAlert('1:1문의 유형을 선택해주세요.');
			//alert('1:1문의 유형을 선택해주세요.');
			return false;
		}else if(isEmpty($('#qaTelno1').val()) || $('#qaTelno1').val().length<3 ||($('#qaTelno1').val()!='010'&&$('#qaTelno1').val()!='011')){
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
					modooAlert(result.message, '확인', function() {
					window.location.reload();
					});
					popClose(target);
				}else{
					modooAlert(result.message);
				}
			}
		})
	})
	
	// QNA수정폼
	$(document).on('click','.updateQaInfo',function(){
		fileCnt = 1;
		var actionUrl = "/qaInfo/qaInfoDetail.json"
		var goodsNm = $(this).data('goodsnm'); 
		var qaId = $(this).data('qaid');	
		 $('.submitBtn').attr('type','button');
		 $('#qestnSj').val('[상품문의]'+userNm+'의 '+goodsNm+'대한 질문');
		 
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
						 $('#secretAt1').val('Y');
					 }else{
						 $("input:checkbox[name='secretAt']").parent('label').removeClass('ui-state-active');
					 }
					 popOpen(target);
					 $('.qaInfoForm').attr('id','qaInfoUpdate');
					 $('.qaInfoForm').attr('action','/qainfo/updateQainfo.json');
					 $('#qaId').val(qaId);
					 var html='';
					 for (var i = 0; i < result.data.imgs.length; i++){
					 var imgSrc = '/fms/getImage.do?atchFileId=' + result.data.imgs[i].atchFileId + '&fileSn=' + result.data.imgs[i].fileSn;
					 html += '<li id="upImg_' + result.data.imgs[i].fileSn +'">';
						html += '<div><img src="' + imgSrc + '" alt=" " /></div>';
						html += '<button type="button" id="upImgDel" class="btn-del-r" data-atchfileid='+result.data.imgs[i].atchFileId+' data-filesn='+result.data.imgs[i].fileSn+'><span class="txt-hide">삭제</span></button>';
						html += '</li>'
					 }
					 $('#qa-file').empty();
					 $('#qa-file').append(html);
				 }
			 })
	});
	
	// QNA수정
	 $(document).on('click','.submitBtn',function(){
		 
		 var actionurl=$('#qaInfoUpdate').attr('action');
		 var method = $('#qaInfoUpdate').attr('method');
		 
		 	$('#qaInfoUpdate').ajaxSubmit({
			url:actionurl,
			method:method,
			dataType:'json',
			processData: false,
			contentType: false,
			type:'post',
			success:function(result){
				if(result.success){
					popClose(target);
					modooAlert(result.message, '확인', function() {
					window.location.reload();
					});
				}else{
					modooAlert(result.message);
				}
			}
		 })
	 })
	
	 //QNA삭제
	 $(document).on('click','.deleteQaInfo',function(e){
		 
		 e.preventDefault();
		 var actionUrl = '/qainfo/deleteQainfo.json';
		 var qaId = $(this).data('qaid');
		 modooConfirm('문의를 삭제하시겠습니까?', '문의 삭제',function(result) {
			 if(result){
				 $.ajax({
					 url:actionUrl,
					 data:{qaId : qaId},
					 dataType:'json',
					 type:'post',
					 success:function(result){
						modooAlert(result.message, '확인', function() {
						window.location.reload();
						});
					 }
				 })
			 }else{
				 return false;
			 }
		 })
	 });

	 
	 //날짜 정렬
	 $(document).on('change','.dayCode',function(){
		var code = $(this).val();
		location.href='/user/my/qainfo.do?qaSeCode='+qaSeCode+'&pageIndex=1&searchKeyword=dayCode&searchCondition='+code;
	 })
	 
	 //이미지UI삭제
	 $(document).on('click','#imgUiDel',function(e) {
		e.preventDefault();
		var fileSn = $(this).data('filesn');
		$('li#img_' + fileSn).remove();
		$('#atchFile').val('');
		storage.pop();
	 })
	 
	 // 기존이미지 삭제
	$(document).on('click','#upImgDel',function(e) {
		e.preventDefault();
		var atchFileId=$(this).data('atchfileid');
		var fileSn=$(this).data('filesn');
		modooConfirm('사진을 삭제하시겠습니까?','사진 삭제',function(result){
			if(result){
				var deleteUrl = '/fms/deleteFile.json';
				$.ajax({
					url:deleteUrl,
					type:'post',
					data:{atchFileId:atchFileId,fileSn:fileSn},
					dataType:'json',
					success:function(result){
						if(result.success){
							modooAlert(result.message);
							$('li#upImg_'+fileSn).remove();
						}else{
							modooAlert(result.message);
						}	
					}
				})
				
			}else{
				return false;
			}
		});
		 
	})
	 
	 //파일 업로드 이미지 처리
	 $(".fileAdd").change(function(e){
	/* ui 액션 처리 */
		
		var get_file = e.target.files;
		var reader = new FileReader();
		var html = '';
		var target = document.getElementById('qa-file');
		
		if($('#qa-file').children('li').length>=1){
			modooAlert('사진은 최대 한개만 업로드 할 수 있습니다.');
		}else{
			if (get_file.length > 1) {
				modooAlert('파일은 한 번에 하나만 등록할 수 있습니다.');
			}else{
				if (storage.length < 2)  {
					reader.onload = (function () {
						return function (e) {
							html += '<li id="img_' + fileCnt +'">';
							html += '<div><img src="' + e.target.result + '" alt=" " /></div>';
							html += '<button  type="button"  id="imgUiDel" class="btn-del-r"  data-filesn=' + fileCnt +'><span class="txt-hide">삭제</span></button>';
							html += '</li>';
							
							fileCnt++;
							$(target).append(html);
							storage.push(e.target.result);
						}
					})();
					
					if(get_file){
						reader.readAsDataURL(get_file[0]);
					}
				} else {
					modooAlert('파일은 최대 1개까지 업로드 할 수 있습니다.');
				}
			}

		}

	
		/* file input 초기화 */
		/*$(".fileAdd").val('');*/
	});
	
	
})();
