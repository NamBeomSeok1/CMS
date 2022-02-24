(function() {

	var currentPageFAQ = 1;
	var faqSeCode = {
		'#faq1' : 'FT001,FT002,FT003',
		'#faq2' : 'FT004',
		'#faq3' : 'FT005,FT006',
		'#faq4' : 'FT007',
		'#faq5' : 'FT999'
	};
	
	$(document).ready(function(){
		getFAQList(faqSeCode['#faq1'], currentPageFAQ, $('#accordion-faq1'));
	});
	
	$(document).on('click', 'a.ui-tabs-anchor', function(){
		var href = $('li.ui-state-active').children('a').attr('href');
		getFAQList(faqSeCode[href], currentPageFAQ, $('#accordion' + href.replace("#", "-")));
	});
	
	function getFAQList(code, currentPageFAQ, accordion) {
		var dataJson = {
			'currentPageNo' : currentPageFAQ,
			'faqSeCode' : code
		};
		$.ajax({
			url:CTX_ROOT + '/decms/faq/faqList.json',
			data:dataJson,
			dataType:'json',
			type:'get',
			success:function(json){
	
				var list = json.data.list;
				var html='';
				if (list.length > 0) {
					for(var i=0; i<list.length; i++){
						html += '<li><div class="accordion-tit-area">';
						html += '<button type="button" class="btn-accordion-toggle"><span class="txt-hide">토글버튼</span></button><div>';
						html += '[' + getCodeName(json.data.faqSeCodeList, list[i].faqSeCode) + '] ' + list[i].qestnSj;
						html += '</div></div><div class="accordion-txt-area"><div>';
						html += list[i].answerCn;
						html += '</div></div></li>';
					}
				} else {
					html += '<li><p class="none-txt">작성된 FAQ가 없습니다.</p></li>';
				}
				
				accordion.html(html);
	
				var pid = accordion.attr('id').replace('accordion', 'paging');
				pagingFAQ($('#' + pid), json.data.paginationInfo.totalRecordCount, currentPageFAQ);
			},
			complete : function() {
				$('.accordion').accordionFunc();
			}
		});
	
	}
	
	function getCodeName(list, val) {
		for (var i=0; i<list.length; i++) {
			if (list[i].code == val) {
				return list[i].codeNm;
				break;
			}
		}
		
		return '';
	}
	
	function pagingFAQ($pagingSector, totalCount, currentPage){
	       
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
			 htmlStr +='<li class="ppv"><a href="#none" onclick="movePageFAQ('+foreFront+');" title="처음으로"><span class="txt-hide">처음으로</span></a></li>';
			 htmlStr +='<li class="pv"><a href="#none" onclick="movePageFAQ('+prev+');" title="이전"><span class="txt-hide">이전</span></a></li>';
		   
		}
		//페이지 set
			for(var i=first; i<=last; i++){
				if(currentPage == i ){
					htmlStr += '<li class="is-active" id="currentPage" data-page="'+i+'"><a href="#none" onclick="movePageFAQ('+i+');" title="to '+i+' page">'+i+'</a></li>';
				 }else{
					htmlStr += '<li><a href="#none" onclick="movePageFAQ('+i+');" title="to '+i+' page">'+i+'</a></li>'
				}
			 }
			
		//마지막으로,다음
		if(last<totalPage){
			if(last==totalPage){
				htmlStr+='';
			}
			htmlStr += '<li class="fw"><a href="#none"  onclick="movePageFAQ('+next+');" title="다음"><span class="txt-hide">다음</span></a></li>';
			htmlStr += '<li class="ffw"><a href="#none" onclick="movePageFAQ('+rearMost+');"  title="끝으로"><span class="txt-hide">끝으로</span></a></li>';
		}
	   
		$pagingSector.empty();
		$pagingSector.html(htmlStr);
	
	}
		
	//페이지 이동
	function movePageFAQ(currentPage){
		var href = $('li.ui-state-active').children('a').attr('href');
		$('.paging').empty();
		currentPageFAQ = currentPage;
		getFAQList(faqSeCode[href], currentPageFAQ, accordion)
	}

})();