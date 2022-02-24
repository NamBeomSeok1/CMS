<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/modoo/common/commonTagLibs.jsp"%>
<div class="wrap">
	<div class="sub-contents">
		<div>
			<button type="submit" class="btn-lg spot" data-popup-open="adress">주소 찾기</button>
		</div>
		
	    <div class="popup" data-popup="adress">
	        <div class="pop-header">
	            <h1>주소 찾기</h1>
	            <button type="button" class="btn-pop-close" data-popup-close="adress">닫기</button>
	        </div>
	        <div class="pop-body">
	        	<form name="form" id="form" method="post">
				<input type="hidden" name="currentPage" value="1"/> 
				<input type="hidden" name="countPerPage" value="5"/>
				<input type="hidden" name="resultType" value="json"/>
				<input type="hidden" name="confmKey" value="U01TX0FVVEgyMDIxMDExNDEwMTM1MDExMDY4ODQ="/>
	            <div class="sch-area lg">
	                <input type="text" name="keyword" value="" onkeydown="enterSearch();" placeholder="주소를 입력해주세요." />
	                <button type="submit" class="btn-sch"></button>
	            </div>
	            <p style="display:none;" id="addTotalCnt" class="mb10">총 <em></em>건</p>
	            <p style="display:none;" id="message" class="mb10">주소를 상세히 입력해 주시기 바랍니다.</p>
	            <ul class="border-list">
	            </ul>
	            <ul class="paging">
	            </ul>
	        </div>
	    </div>
	</div>
</div>
<javascript>
<script>
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
		 url :"http://www.juso.go.kr/addrlink/addrLinkApiJsonp.do"  //인터넷망
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
						paging(jsonStr.results.common.totalCount,jsonStr.results.common.currentPage);
					}
				}
			}
		}
	    ,error: function(xhr,status, error){
	    	alert("에러발생");
	    }
	});
})
//주소 적용
$(document).on('click','#addChoice',function(e){
	e.preventDefault();
	var obj = $('[data-popup]').data('popup','adress');
	var $self = $(this);
	var zipCode = $self.data('zipno');
	var roadAddr = $self.data('roadaddr');
	$('#zipCode').val(zipCode);
	$('#roadAddr').val(roadAddr);
	$('.dim').hide();
	$(obj).fadeOut(200);
});

//주소 목록 넣기
function makeListJson(jsonStr){
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
function checkSearchedWord(obj){
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

function enterSearch() {
	var evt_code = (window.netscape) ? ev.which : event.keyCode;
	if (evt_code == 13) {    
		event.keyCode = 0;  
		getAddr(); //jsonp사용시 enter검색 
	} 
}


//페이징처리
function paging(totalCount,currentPage){
	
	const totalPage = Math.ceil(totalCount/5); //총 페이지 수 
	const pageGroup = Math.ceil(currentPage/5); //페이지 그룹

	var last = pageGroup * 5; //화면에 보여질 마지막 번호
	if(last > totalPage){
		last = totalPage;
		
	}
	var first = last - 4; // 화면에 보여질 첫번째 번호
	if(first==0){
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
		 htmlStr +='<li class="ppv"><a href="#none" onclick="movePage('+foreFront+');" title="처음으로"><span class="txt-hide">처음으로</span></a></li>';
		 htmlStr +='<li class="pv"><a href="#none" onclick="movePage('+prev+');" title="이전"><span class="txt-hide">이전</span></a></li>';
		
	}
	//페이지 set
		for(var i=first; i<=last; i++){
			if(currentPage == i ){
				htmlStr += '<li class="is-active"><a href="#none" onclick="movePage('+i+');" title="to '+i+' page">'+i+'</a></li>';
	 		}else{ 
				htmlStr += '<li><a href="#none" onclick="movePage('+i+');" title="to '+i+' page">'+i+'</a></li>'
			}
	 	}
		 
	//마지막으로,다음
	if(last<totalPage){
		if(last==totalPage){
			htmlStr+='';
		}
		htmlStr += '<li class="fw"><a href="#none"  onclick="movePage('+next+');" title="다음"><span class="txt-hide">다음</span></a></li>';
        htmlStr += '<li class="ffw"><a href="#none" onclick="movePage('+rearMost+');"  title="끝으로"><span class="txt-hide">끝으로</span></a></li>';
	}
	
	$('.paging').empty();
	$('.paging').html(htmlStr);
}
//페이지 이동
function movePage(currentPage){
	
	$('input[name="currentPage"]').val(currentPage);
	$('#form').trigger('submit');
	
}

$('input[name="keyword"]').keyup(function(){
	$('input[name="currentPage"]').val(1);
});
</script>	
</javascript>
	
	
	