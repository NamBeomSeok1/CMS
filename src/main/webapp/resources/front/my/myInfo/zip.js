(function() {
	
	$(document).ready(function(){
		if($('#islandDlvyChk').length!=0){
			var zip =  $('#dlvyZip').val();
			islandDlvyChk(zip);
		}
	})
	//주소 목록 넣기
	makeListJson = function(jsonStr) {
		var obj = $('[data-popup]').data('popup','adress');
		var htmlStr = '';
		$(jsonStr.results.juso).each(function(){
			htmlStr += '<li>';
			htmlStr += '<p>['+this.zipNo+']'+this.jibunAddr+'</p>';
			htmlStr += '<p class="fs-sm fc-gr"><span class="label spot3 dlvyAddress">도로명</span> '+this.roadAddr+'</p>';
			htmlStr += '<button type="button" id="addChoice" class="btn-full" data-zipno="'+this.zipNo+'" data-roadaddr="'+this.roadAddr+'"><span class="txt-hide">주소선택</span></button></li>';
			htmlStr += '</li>';
		});
		$(".border-list").html(htmlStr);
		popPosition(obj);
	}
	
	//특수문자, 특정문자열(sql예약어의 앞뒤공백포함) 제거
	checkSearchedWord = function (obj){
		if(obj.value.length >0){
			//특수문자 제거
			var expText = /[%=><]/ ;
			if(expText.test(obj.value) == true){
				alert("특수문자를 입력 할수 없습니다.") ;
				obj.value = obj.value.split(expText).join(""); 
				return false;
			}
			
			//특정문자열(sql예약어의 앞뒤공백포함) 제거
			var sqlArray = new Array(
				//sql 예약어
				"OR", "SELECT", "INSERT", "DELETE", "UPDATE", "CREATE", "DROP", "EXEC",
	             		 "UNION",  "FETCH", "DECLARE", "TRUNCATE" 
			);
			
			var regex;
			for(var i=0; i<sqlArray.length; i++){
				regex = new RegExp( sqlArray[i] ,"gi") ;
				
				if (regex.test(obj.value) ) {
				    alert("\"" + sqlArray[i]+"\"와(과) 같은 특정문자로 검색할 수 없습니다.");
					obj.value =obj.value.replace(regex, "");
					return false;
				}
			}
		}
		return true ;
	}
	
	enterSearch = function() {
		var evt_code = (window.netscape) ? ev.which : event.keyCode;
		var obj = $('[data-popup]').data('popup','adress');
		if (evt_code == 13) {    
			event.keyCode = 0;  
			
			////////////
			
			// 적용예 (api 호출 전에 검색어 체크) 	
			if (!checkSearchedWord(document.form.keyword)) {
				return ;
			}
			var $self = $('#form');
			
			$self.ajaxSubmit({
				 url :"https://www.juso.go.kr/addrlink/addrLinkApiJsonp.do"  //인터넷망
				,type:"post"
				,data:$self.serialize()
				,dataType:"jsonp"
				,crossDomain:true
				,success:function(jsonStr){
					$(".border-list").html("");
					var errCode = jsonStr.results.common.errorCode;
					var errDesc = jsonStr.results.common.errorMessage;
					if(errCode != "0"){
						$('#message').show();
						$('#addTotalCnt').hide();
					}else{
						if(jsonStr != null){
							$('#message').hide();
							$('#addTotalCnt').show();
							$('#addTotalCnt').children('em').text(jsonStr.results.common.totalCount);
							console.log(jsonStr)
							makeListJson(jsonStr);
							if(jsonStr.results.common.totalCount != 0){
								pagingZipPop(jsonStr.results.common.totalCount,jsonStr.results.common.currentPage);				
							}
							popPosition($('#adress'));
						}
					}
				}
				,error: function(xhr,status, error){
					//alert("에러발생");
				}, complete: function(){
					popPosition($('#adress'));
				}
			});
			
			////////////
		} 
	}
	
	//페이징처리
	pagingZipPop = function(totalCount,currentPage){
		
		const totalPage = Math.ceil(totalCount/5); //총 페이지 수 
		const pageGroup = Math.ceil(currentPage/5); //페이지 그룹
	
		var last = pageGroup * 5; //화면에 보여질 마지막 번호
		if(last > totalPage){
			last = totalPage;
			
		}
		var first = last - 4; // 화면에 보여질 첫번째 번호
		if(first < 1){
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
			 htmlStr +='<li class="ppv"><a href="#none" onclick="movePageZipPop('+foreFront+');" title="처음으로"><span class="txt-hide">처음으로</span></a></li>';
			 htmlStr +='<li class="pv"><a href="#none" onclick="movePageZipPop('+prev+');" title="이전"><span class="txt-hide">이전</span></a></li>';
			
		}
		//페이지 set
			for(var i=first; i<=last; i++){
				if(currentPage == i ){
					htmlStr += '<li class="is-active"><a href="#none" onclick="movePageZipPop('+i+');" title="to '+i+' page">'+i+'</a></li>';
		 		}else{ 
					htmlStr += '<li><a href="#none" onclick="movePageZipPop('+i+');" title="to '+i+' page">'+i+'</a></li>'
				}
		 	}
			 
		//마지막으로,다음
		if(last<totalPage){
			if(last==totalPage){
				htmlStr+='';
			}
			htmlStr += '<li class="fw"><a href="#none"  onclick="movePageZipPop('+next+');" title="다음"><span class="txt-hide">다음</span></a></li>';
	        htmlStr += '<li class="ffw"><a href="#none" onclick="movePageZipPop('+rearMost+');"  title="끝으로"><span class="txt-hide">끝으로</span></a></li>';
		}
		
		$('.paging').empty();
		$('.paging').html(htmlStr);
	
	}
	
	//페이지 이동
	movePageZipPop = function(currentPage) {
		$('input[name="currentPage"]').val(currentPage);
		$('#form').trigger('submit');
		
	}

	/** 이벤트 목록 */
	//주소 검색	
	$(document).on('submit','#form',function(e){
			
		var obj = $('[data-popup]').data('popup','adress');
		e.preventDefault();
		// 적용예 (api 호출 전에 검색어 체크) 	
		if (!checkSearchedWord(document.form.keyword)) {
			return ;
		}
		var $self = $('#form');
		
		$self.ajaxSubmit({
			 url :"https://www.juso.go.kr/addrlink/addrLinkApiJsonp.do"  //인터넷망
			,type:"post"
			,data:$self.serialize()
			,dataType:"jsonp"
			,crossDomain:true
			,success:function(jsonStr){
				$(".border-list").html("");
				var errCode = jsonStr.results.common.errorCode;
				var errDesc = jsonStr.results.common.errorMessage;
				if(errCode != "0"){
	 				$('#message').show();
	 				$('#addTotalCnt').hide();
				}else{
					if(jsonStr != null){
	 					$('#message').hide();
		 				$('#addTotalCnt').show();
						$('#addTotalCnt').children('em').text(jsonStr.results.common.totalCount);
						//console.log(jsonStr)
						makeListJson(jsonStr);
						if(jsonStr.results.common.totalCount != 0){
							pagingZipPop(jsonStr.results.common.totalCount,jsonStr.results.common.currentPage);				
						}
					}
				}
				popPosition('adress');
				
			}
		    ,error: function(xhr,status, error){
		    	alert("에러발생");
		    }, complete: function(){
		    	popPosition('adress');
		    }
		});
	})
	
	//주소 적용
	$(document).on('click','#addChoice',function(e){
		e.preventDefault();
		var $self = $(this);
		var zipCode = $self.data('zipno');
		var roadAddr = $self.data('roadaddr');
		$('#dlvyZip').val(zipCode);
		$('#dlvyAdres').val(roadAddr);
		popClose('adress');
		if($('#islandDlvyChk').length!=0){
			islandDlvyChk(zipCode);
		}
	});
	
	//도서산간 체크
	function islandDlvyChk(zipCode){
		var jejuPc = $('#jejuDlvyPc').val();
		var islandPc = $('#islandDlvyPc').val();
		var dataJson = {
				'zip' : zipCode
		};
		var isJeju = false;
		if(Number(zipCode)>=63000){
			isJeju=true;
		}
	        $.ajax({
	            url:CTX_ROOT + '/shop/goods/checkIdsrtsAt.do',
	            type:'POST',
	            data:dataJson,
	            dataType:'json',
	            success:function(result){
	                if (result.data.count > 0) {
	                	if(Number(zipCode)>=63000){
	                		$('#islandDlvyChk').val('jeju');
	                		if(Number(jejuPc)!=0){
	                			$('#islandMsg').show();
	                			$('#islandMsg').text('제주 지역은 추가 배송비'+jejuPc+' 원이 더 추가됩니다.');
	                		}
	            		}else{
	            			$('#islandDlvyChk').val('island');
	                		if(Number(islandPc)!=0){
	                		$('#islandMsg').show();	
	            			$('#islandMsg').text('도서산간지역은 추가 배송비'+islandPc+' 원이 더 추가됩니다.');
	                		}
	            		}
	                }else{
	                	$('#islandDlvyChk').val('N');
	                	$('#islandMsg').hide();
	                	return false;
	                }
	            }
	        });
	}
	
	$('input[name="keyword"]').keyup(function(){
		$('input[name="currentPage"]').val(1);
	});
	
	$(document).on('click','.closeBtn',function(e){
		popClose('deliveryEdit');
	})

})();