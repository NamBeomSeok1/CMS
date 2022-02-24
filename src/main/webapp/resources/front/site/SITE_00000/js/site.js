(function() {
	
	/*
	//모두 alert 공통
	modooAlert = function(message, title) {
		if(message) {
			var $mAlert = $('[data-popup="modooAlert"]');
			if(title) {
				$mAlert.find('.modooAlert-title').text(title);
			}
			$mAlert.find('.modooAlert-content').text(message);
			popOpen('modooAlert');
		}else {
			debug('메시지를 입력하세요.');
		}
	};
	
	// 모두 alert 공통 close
	modooAlertClose = function() {
		var $mAlert = $('[data-popup="modooAlert"]');
		$mAlert.find('.modooAlert-title').text('');
		$mAlert.find('.modooAlert-content').text('');
		popClose('modooAlert');
		return true;
	};
	*/
	
	// 숫자 3자리 콤마
	modooNumberFormat = function(numberStr) {
		return numberStr.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",");	
	};

	modooPopup = function (target) {
		if(event) event.stopPropagation();
		var $pop = $('[data-popup="' + target + '"]'),
			$popBody = $pop.find('.pop-body');
		var isMultiple = false;
		if($('.dim:visible').length > 0) {
			$pop.show().addClass('zindex-pop');
			isMultiple = true;
		}else {
			$pop.show();
			isMultiple = false;
		}
		popPosition($pop);
		TweenMax.fromTo($pop, .3, { css: { 'transform': 'translateY(10%)', 'opacity': '0' } }, { css: { 'transform': 'translateY(0)', 'opacity': '1' } })

		if (scollvCheck($popBody) == true) {
			$popBody.scrollTop(0);
		}
		if(isMultiple) {
			$('.dim').show().addClass('zindex-dim');
		}else {
			$('.dim').show();
		}
		$('body').css({ 'overflow-y': 'hidden' });
	}
	
	// 모두의 구독 공통 Alert
	modooAlert = function(mMessage, mTitle, callback) {
		if(mMessage) {
			var $mAlert = $('[data-popup="modooAlert"]');
			if(mTitle) {
				$mAlert.find('.modooAlert-title').text(mTitle);
			}
			$mAlert.find('.modooAlert-content').html(mMessage);
			
			modooPopup('modooAlert');
			
			$(document).off('click', '.modooAlert-ok').on('click', '.modooAlert-ok', function(e) {
				popClose('modooAlert');
				$mAlert.find('.modooAlert-title').text('');
				$mAlert.find('.modooAlert-content').html('');
				if(typeof callback == 'function') callback(true);
			});
		}
	};
	
	
	
	// 모두의 구독 공통 confirm
	modooConfirm = function(mMessage, mTitle, callback) {
		if(mMessage) {
			var $mAlert = $('[data-popup="modooConfirm"]');
			if(mTitle) {
				$mAlert.find('.modooConfirm-title').text(mTitle);
			}
			$mAlert.find('.modooConfirm-content').text(mMessage);
			
			modooPopup('modooConfirm');
			
			$(document).off('click', '.modooConfirm-cancel').on('click', '.modooConfirm-cancel', function(e) {
				popClose('modooConfirm');
				$mAlert.find('.modooConfirm-title').text('');
				$mAlert.find('.modooConfirm-content').text('');
				if(typeof callback == 'function') callback(false);
			});
			$(document).off('click','.modooConfirm-ok').on('click', '.modooConfirm-ok', function(e) {
				popClose('modooConfirm');
				$mAlert.find('.modooConfirm-title').text('');
				$mAlert.find('.modooConfirm-content').text('');
				if(typeof callback == 'function') callback(true);
			});
		}else {
			debug('메시지를 입력하세요.');
		}
	};
	
	/** 첨부파일  */
	// 첨부파일 삭제
	function deleteAtchFile($el, actionUrl) {
		$.ajax({
			url: actionUrl,
			type: 'post',
			dataType: 'json',
			success: function(result) {
				if(result.message) {
					alert(result.message);
					//bootbox.alert({ title: '확인', message: result.message, size: 'small' }); 
				}
				
				if(result.success) {
					$el.parents('tr').remove();
				}
			}
		});
		
	};

	// 첨부파일 삭제 Click
	$(document).on('click', '.atch_delete', function(e) {
		e.preventDefault();
		
		if(confirm('삭제하시겠습니까?')) {
			var $self = $(this);
			var actionUrl = $(this).attr('href');
		
			deleteAtchFile($self, actionUrl);
			
		}
		
		/*
		bootbox.confirm({
			title: '삭제확인',
			message: '삭제하시겠습니까?',
			callback: function(result) {
				if(result) {
					deleteAtchFile($self, actionUrl);
				}
			}
		});
		*/
	});
	
	/*
	localStorage.setItem('ttt', '0');
	var ttt = localStorage.getItem('ttt');

	console.log('ttt: ' + ttt);
	*/
	
	//검색 autocomplete
	var storeSearchWrdAt = localStorage.getItem('storeSearchWrdAt');
	var storageSearchWrd = JSON.parse(localStorage.getItem('searchWrd'));
	var keywords = [];
	if(globalKeywords.length > 0) {
		keywords = globalKeywords;
	}else if(!isEmpty(storageSearchWrd)) {
		keywords = storageSearchWrd.wrd;
	}
	
	// 인기키워드
	if(!isEmpty(globalHitKeywords)) {
		var $histEl = $('#histsSearchKeywrods');
		globalHitKeywords.forEach(function(item) {
			$histEl.append('<li><button type="button">'+item+'</button></li>');
		});

	}
	
	
	//검색기록 저장 삭제
	function removeSearchWrd() {
		var actionUrl = CTX_ROOT + '/shop/removeSearchKeyword.json';
		$.ajax({
			url: actionUrl,
			type: 'post',
			dataType: 'json',
			success: function(result) {
				if(result.message) {
					alert(result.message);
				}
				if(result.success) {
					$('#latestSearchKeywords').html('');
					localStorage.removeItem('searchWrd');
				}
			}
		});
	};
	
	if(isEmpty(storeSearchWrdAt)) {
		storeSearchWrdAt == 'Y';
		localStorage.setItem('storeSearchWrdAt', 'Y');
	}
	
	//console.log(storeSearchWrdAt);
	//console.log(storageSearchWrd);
	
	
	//검색어 자동저장 여부 기본 (Y : 저장)
	if(isEmpty(storeSearchWrdAt) || storeSearchWrdAt == 'Y') { 
		//storeSearchWrdAt = 'Y';
		$('.btnStopSaveSearchKeywords').text('검색어 자동저장 끄기');
		keywords.forEach(function(item) {
			$('#latestSearchKeywords').append('<li><button type="button">'+item+'</button></li>');
		});
	}else {
		$('.btnStopSaveSearchKeywords').text('검색어 자동저장 켜기');
		keywords = [];
	}
	//console.log(keywords);
	
	//외부에서 검색 시 적용
	setSearchKeywords = function(searchKeyword) {
		storeSearchWrdAt = localStorage.getItem('storeSearchWrdAt');
		
		if(storeSearchWrdAt == 'Y') {
			if(keywords.indexOf(searchKeyword) < 0) {
				keywords.unshift(searchKeyword);
				keywords = keywords.slice(0,9); //최근 검색어 10개 
			
				var searchWrd = {
					wrd : keywords
				};
				
				var jsonSearchWrd = JSON.stringify(searchWrd);
				localStorage.setItem('searchWrd', jsonSearchWrd);

				var storageSearchWrd = JSON.parse(localStorage.getItem('searchWrd'));
				keywords = storageSearchWrd.wrd;
			}
		}
	};

	//검색 (헤더 공통)
	$('.schall .autocomplete').autocomplete({
		appendTo: $('.schall-result-area'),
		source: keywords,
		open: function () {
			$('.schall .keyword-area').hide();
		},
		close: function () {
			$('.schall .keyword-area').show();
			TweenMax.fromTo('.schall .keyword-area', .3, { opacity: 0, x: 20 }, { opacity: 1, x: 0 });
		}
	})._renderItem = function (ul, item) {
        var keyword = this.element.val();
        var regEx = new RegExp(keyword, "ig");
        var hilight = "<em>$&</em>";
        var html = item.label.replace(regEx, hilight);

        return $("<li></li>").html(html)
            .data("item.autocomplete", item)
            .appendTo(ul);
    };

	$(document).on('click', '.btn-schall-toggle, .btn-schall-close', function () {
		toggleCommonFunc({
			obj: $('.schall'),
			className: 'is-active',
			hasClass: function (obj) {
				var tl = new TimelineMax();
				tl.fromTo('.schall', .2, { marginTop: -20, opacity: .5 }, { marginTop: 0, opacity: 1 }).fromTo('.schall .sch-area', .2, { opacity: 0 }, { opacity: 1 }).fromTo('.schall .keyword-area', .3, { opacity: 0, x: 20 }, { opacity: 1, x: 0 });
				$('.lnb-area').css({ 'z-index': '9' });
			},
			noneClass: function (obj) {
				$('.lnb-area').css({ 'z-index': '11' });
			}
		});
	});

	//검색페이지
	$('.sch .autocomplete').autocomplete({
		source: keywords,
	})._renderItem = function (ul, item) {
        var keyword = this.element.val();
        var regEx = new RegExp(keyword, "ig");
        var hilight = "<em>$&</em>";
        var html = item.label.replace(regEx, hilight);

        return $("<li></li>").html(html)
            .data("item.autocomplete", item)
            .appendTo(ul);
    };
	
	
	// 통합검색 submit
	$(document).on('submit', '#goodsSearchForm', function(e) {
		if(isEmpty($(this).find('[name=searchKeyword]').val())) {
			modooAlert('검색어를 입력하세요!');
			return false;
		}
		storeSearchWrdAt = localStorage.getItem('storeSearchWrdAt');
		if(storeSearchWrdAt == 'Y') { //검색어 자동저장 활성일때
			var currSearchWrd = $(this).find('[name=searchKeyword]').val();
			if(currSearchWrd) {
				setSearchKeywords(currSearchWrd);
				
				$('[name=storeSearchWrdAt]').val(storeSearchWrdAt);
			}
		}
	});
	
	// 최근검색어 & 인기검색 Click
	$(document).on('click', '#latestSearchKeywords button, #histsSearchKeywrods button', function(e) {
		e.preventDefault();
		var wrd = $(this).text();
		$('#goodsSearchForm').find('[name=searchKeyword]').val(wrd);
		$('#goodsSearchForm').submit();
	});
	
	// 검색기록 삭제
	$(document).on('click', '.btnDeleSearchKeywords', function(e) {
		e.preventDefault();
		if(!confirm('검색기록을 삭제하시겠습니까?')) {
			return false;
		}
		removeSearchWrd();
		
	});
	
	// 검색어 자동저장 toggle click
	$(document).on('click', '.btnStopSaveSearchKeywords', function(e) {
		e.preventDefault();
		var flag = localStorage.getItem('storeSearchWrdAt');
		
		if(flag == 'Y') {
			$(this).text('검색어 자동저장 켜기');
			localStorage.setItem('storeSearchWrdAt', 'N');
			$('#latestSearchKeywords').html('');
		}else {
			$(this).text('검색어 자동저장 끄기');
			localStorage.setItem('storeSearchWrdAt', 'Y');
		}
		
	});
	
	//로고 클릭 시 '0'으로 저장
	$(document).on('click', '.site-header .logo a', function(e) {
		sessionStorage.setItem('mainScrollTop', '0');
	});
	
	$(document).on('click', '.view-content img', function(e) {
		e.preventDefault();
		var link = encodeURIComponent($(this).attr('src'));
		console.log(link);

		var actionUrl = CTX_ROOT + '/embed/common/imageView.do?imageUrl=' + link;
		console.log(actionUrl);
		window.open(actionUrl);
	});

}) (); 