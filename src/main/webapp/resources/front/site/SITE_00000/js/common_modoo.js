/*		
	date : 2020-08-24
	author :  yangHyo	
	site : 모두의구독
*/

var ie = /MSIE/.test(navigator.userAgent);
ieVer = ie ? parseInt(navigator.userAgent.split('MSIE')[1].split(';')[0]) : false;

if (typeof Object.assign != 'function') {
    // Must be writable: true, enumerable: false, configurable: true
    Object.defineProperty(Object, "assign", {
        value: function assign(target, varArgs) { // .length of function is 2
            'use strict';
            if (target == null) { // TypeError if undefined or null
                throw new TypeError('Cannot convert undefined or null to object');
            }

            var to = Object(target);

            for (var index = 1; index < arguments.length; index++) {
                var nextSource = arguments[index];

                if (nextSource != null) { // Skip over if undefined or null
                    for (var nextKey in nextSource) {
                        // Avoid bugs when hasOwnProperty is shadowed
                        if (Object.prototype.hasOwnProperty.call(nextSource, nextKey)) {
                            to[nextKey] = nextSource[nextKey];
                        }
                    }
                }
            }
            return to;
        },
        writable: true,
        configurable: true
    });
}

/**
 * @param opt {
 * obj : 토글 클래스가 붙을 오브젝트,
 * className : 토글시 클래스명,
 * hasClass : 요소가 클래스를 가지고 있을 때 함수를 실행한다,
 * noneClass : 요소가 클래스를 가지고 있지 않을 때 함수를 실행한다,
 * }
 */
var toggleCommonFunc = function (opt) {
    opt.obj = opt.obj;

    $(opt.obj).toggleClass(opt.className);

    if ($(opt.obj).hasClass(opt.className)) {
        opt.hasClass()

    } else {
        opt.noneClass();
    }
}

/**
 *
 * @param opt {
 * pc : function
 * mo : function
 * ta : fucntion
 * }
 */
var ssmFunc = function (opt) {
    var ssmPc = (opt.hasOwnProperty('pc') && opt.pc) ? true : false;
    var ssmTa = (opt.hasOwnProperty('ta') && opt.ta) ? true : false;
    var ssmMo = (opt.hasOwnProperty('mo') && opt.mo) ? true : false;

    if (ssmPc) {
        ssm.addState({
            id: 'pc',
            query: '(min-width: 1280px)',
            onEnter: function () {
                opt.pc();
            },
        });
    }
    if (ssmTa) {
        ssm.addState({
            id: 'ta',
            query: '(min-width: 768px) and (max-width: 1279px)',
            onEnter: function () {
                opt.ta();
            },
        });
    }
    if (ssmMo) {
        ssm.addState({
            id: 'mo',
            query: '(max-width: 767px)',
            onEnter: function () {
                opt.mo();
            },
        });
    }
};

if (jQuery) (function ($) {
    //common
    //$.extend($.fn, {
    //    Func: function () {
    //        var init = function (obj) {

    //        };
    //        $(this).each(function () {
    //            init(this);
    //        });
    //        return $(this);
    //    }
    //});

    //component
    //accordion
    $.extend($.fn, {
        accordionFunc: function () {
            var init = function (obj) {
                var $btn = $(obj).find('li').find('.btn-accordion-toggle');
                $(document).on('click', $btn, function (e) {
                    var $target = $(e.target);
                    if ($target.is($btn)) {
                        $target.closest('li').siblings('li').removeClass('is-active');
                        toggleCommonFunc({
                            obj: $target.closest('li'),
                            className: 'is-active',
                            hasClass: function (obj) {
                                //$(obj).removeClass('is-active');
                                TweenMax.fromTo('.accordion-txt-area', .5, { opacity: 0 }, { opacity: 1 });
                            },
                            noneClass: function (obj) {
                                TweenMax.fromTo('.accordion-txt-area', .5, { opacity: 1 }, { opacity: 0 });
                            }
                        });
                    }
                });
            };
            init(this);
            return $(this);
        }
    });

    //popup
    $.extend($.fn, {
        popupFunc: function () {
            var init = function (obj) {
                $('.btn-pop-close').on('click', function (e) {
                    var target = $(this).attr('data-popup-close');
                    $pop = $('[data-popup="' + target + '"]')
                    // 이중팝업 hide
                    if ($pop.hasClass('zindex-pop') == true) {
                        $pop.removeClass('zindex-pop').hide();
                        $('.dim').removeClass('zindex-dim');
                    }
                    // 이중팝업 제외 hide
                    else {
                        popClose(target);
                    }
                });

                $('.dim').on('click', function (e) {
                    //alert 팝업 제외 dim hide 
                    //이중팝업 dim hide
                    if ($(this).hasClass('zindex-dim') == true && $('.popup-alert').is(':visible') == false) {
                        $('[data-popup].zindex-pop').removeClass('zindex-pop').hide();
                        $(this).removeClass('zindex-dim');
                    }
                    //이중팝업 제외 dim hide
                    else if ($(this).hasClass('zindex-dim') == false && $('.popup-alert').is(':visible') == false) {
                        $('[data-popup]').fadeOut(200);
                        $('body').css({ 'overflow-y': 'auto' });
                        $(this).hide();
                    }
                });

                //배너팝업 모바일 dim show     
                //if ($(obj).hasClass('popup-item') == true) {
                //    mDimShow($(obj));
                //}

                //$(window).on('resize', function () {
                //    if ($(obj).hasClass('popup-item') == true) {
                //        //배너팝업 모바일 dim show
                //        mDimShow($(obj));
                //    }
                //    else {
                //        //배너팝업 제외 position resize
                //        popPosition($(obj));
                //    }
                //});
            };
            init(this);
            return $(this);
        }
    });


    //popup
    $.extend($.fn, {
        popupItemFunc: function () {
            var init = function (obj) {
                //배너팝업 모바일 dim show 
                if ($(obj).length) {
                    mDimShow($(obj));
                }               
            };
            init(this);
            return $(this);
        }
    });

    //txt-count
    $.extend($.fn, {
        txtCountFunc: function () {
            var init = function (obj) {
                $textarea = $(obj).find('textarea');
                $textarea.keyup(function (e) {
                    var content = $(this).val(),
                        $area = $(this).siblings('.txt-count').find('span');
                    $area.html(content.length);

                    return false;
                });
            };
            $(this).each(function () {
                init(this);
            });
            return (this);
        }
    });

    //tabs-noraml
    $.extend($.fn, {
        tabsFunc: function () {
            var init = function (obj) {
                var $currentTab = $(obj).find('.tabs-nav .is-active a').attr('href');
                $($currentTab).show();

                $(obj).find('.tabs-nav a').on('click', function () {
                    var $li = $(this).parent('li');
                    if ($li.hasClass("is-active") == false) {
                        $(this).parent('li').siblings('li').removeClass("is-active");
                        $(this).parent('li').addClass("is-active");
                    }

                    $(obj).find('.tabs-cont').hide();
                    var $activeTab = $(this).attr('href');
                    $($activeTab).show();

                    return false;
                });
            };
            $(this).each(function () {
                init(this);
            });
            return $(this);
        }
    });

    //$.extend($.fn, {
    //    maxLengthFunc: function () {
    //        var init = function (obj) {
    //            console.log('number');
    //            if (obj.value.length > obj.maxLength) {
    //                obj.value = obj.value.slice(0, obj.maxLength);
    //            }
    //        };
    //        $(this).each(function () {
    //            init(this);
    //        });
    //        return $(this);
    //    }
    //});

    $.extend($.fn, {
        mnVisualSwiper: function () {
            var init = function (obj) {
                $area = $('.mn-visaul-swiper');
                $obj = $(obj);
                $slide = $(obj).find('.swiper-wrapper').find('.swiper-slide');
                $progressbar = $area.find('.swiper-progress-bar');
                $pagin = $area.find('.swiper-pagination');
                $btnplay = $area.find('.swiper-playstop .btn-play');
                $btnstop = $area.find('.swiper-playstop .btn-stop');
                $btnprev = $area.find('.swiper-button-prev');
                $btnnext = $area.find('.swiper-button-next'),
                autoplay = true;

                var swiper = new Swiper($obj, {
                    fadeEffect: { crossFade: true },
                    effect: 'fade',
                    //autoplayDisableOnInteraction: true,
                    speed: 500,
                    loop: true,
                    autoplay: {
                        delay: 7000,
                        disableOnInteraction: false // 버튼 클릭 시 자동 슬라이드 정지.
                    },
                    pagination: {
                        el: $pagin,
                        type: "fraction"
                    },
                    navigation: {
                        nextEl: $btnnext,
                        prevEl: $btnprev
                    },
                    on: {
                        init: function () {
                            $progressbar.addClass("animate");
                        },
                        slideChangeTransitionStart: function () {
                            /* color change */
                            var $color = $(obj).find('.swiper-slide-active').find('a').css('color');
                            $('.mn-visaul-swiper .swiper-pagination').css({ 'color': $color });
                            $progressbar.children().css({ 'background': $color });
                            $btnplay.find('span').css({ 'border-left-color': $color });
                            $btnstop.find('span').css({ 'border-color': $color });
                            $('.swiper-button-prev, .swiper-button-next, .swiper-button-prev span, .swiper-button-next span').css({ 'border-color': $color });

                            //progressbar animation
                            $progressbar.removeClass("animate");

                            //txt animation
                            $('.visual-txt-area').css({ 'opacity': 0 });
                            var tl = new TimelineMax();
                            var $el = $(obj).find('.swiper-slide-active .visual-txt-area');
                            tl.fromTo($el, .4, { xPercent: 50, opacity: 0 }, { xPercent: 0, opacity: 1 }, .4);
                        },
                        slideChangeTransitionEnd: function () {
                            if (autoplay) {
                                $progressbar.addClass("animate");
                            }
                            else {
                                $progressbar.removeClass("animate");
                            }
                        },
                        touchMove: function () {
                            var $color = $(obj).find('.swiper-slide-active').find('a').css('color');
                            $('.mn-visaul-swiper .swiper-pagination').css({ 'color': $color });
                            $progressbar.children().css({ 'background': $color });
                            $btnplay.find('span').css({ 'border-left-color': $color });
                            $btnstop.find('span').css({ 'border-color': $color });

                            $('.swiper-button-prev').css({ 'border-color': $color });
                            $('.swiper-button-next').css({ 'border-color': $color });
                            $('.swiper-button-prev').find('span').css({ 'border-color': $color });
                            $('.swiper-button-next').find('span').css({ 'border-color': $color });
                            $progressbar.removeClass("animate");

                            //mn-visual
                            $('.visual-txt-area').css({ 'opacity': 0 });

                            var tl = new TimelineMax();
                            var $el = $(obj).find('.swiper-slide-active .visual-txt-area');

                            tl.fromTo($el, .4, { xPercent: 50, opacity: 0 }, { xPercent: 0, opacity: 1 }, .4);
                        },
                        touchEnd: function () {
                            if (autoplay) {
                                $progressbar.addClass("animate");
                            }
                            else {
                                $progressbar.removeClass("animate");
                            }
                        }
                    }
                });

                //$btnprev.on('click', function () {
                //    mnSwiperStop();
                //});
                //$btnnext.on('click', function () {
                //    mnSwiperStop();
                //});
                $btnplay.on('click', function () {
                    mnSwiperStop();
                });
                $btnstop.on('click', function () {
                    mnSwiperPlay();
                });

                var changeStart = function () {
                    /* color change */
                    var $color = $(obj).find('.swiper-slide-active').find('a').css('color');
                    $('.mn-visaul-swiper .swiper-pagination').css({ 'color': $color });
                    $progressbar.children().css({ 'background': $color });
                    $btnplay.find('span').css({ 'border-left-color': $color });
                    $btnstop.find('span').css({ 'border-color': $color });

                    $('.swiper-button-prev, .swiper-button-next, .swiper-button-prev span, .swiper-button-next span').css({ 'border-color': $color });
                    //$('.swiper-button-next').css({ 'border-color': $color });
                    //$('.swiper-button-prev').find('span').css({ 'border-color': $color });
                    //$('.swiper-button-next').find('span').css({ 'border-color': $color });

                    //progressbar animation
                    $progressbar.removeClass("animate");

                    //txt animation
                    $('.visual-txt-area').css({ 'opacity': 0 });
                    var tl = new TimelineMax();
                    var $el = $(obj).find('.swiper-slide-active .visual-txt-area');
                    tl.fromTo($el, .4, { xPercent: 50, opacity: 0 }, { xPercent: 0, opacity: 1 }, .4);
                }
                var changeEnd = function () {
                    if (autoplay) {
                        $progressbar.addClass("animate");
                    }
                    else {
                        $progressbar.removeClass("animate");
                    }
                }
                var mnSwiperStop = function () {
                    autoplay = false;
                    swiper.autoplay.stop();
                    $btnplay.hide();
                    $btnstop.css({ 'display': 'block' });
                    $progressbar.removeClass("animate");
                }

                var mnSwiperPlay = function () {
                    autoplay = true;
                    $progressbar.removeClass("animate");
                    swiper.autoplay.start();
                    $btnstop.hide();
                    $btnplay.css({ 'display': 'block' });
                    $progressbar.addClass("animate");
                }
            }
            init(this);
            return $(this);
        }
    });

    //mn-recomend swiper
    $.extend($.fn, {
        mnRecoSwiper: function () {
            var init = function (obj) {
                $area = $('.mn-recommend-swiper');
                $obj = $(obj);
                $slide = $(obj).find('.swiper-wrapper').find('.swiper-slide');
                $pagin = $area.find('.swiper-pagination');
                $btnprev = $area.find('.swiper-button-prev');
                $btnnext = $area.find('.swiper-button-next');

                var swiper = new Swiper($obj, {
                    slidesPerView: 3,
                    spaceBetween: 40,
                    initialSlide: 4,
                    loop: $slide.length > 3 ? true : false,
                    navigation: {
                        nextEl: $btnnext,
                        prevEl: $btnprev
                    },
                    //speed: 700,
                    //autoplay: {
                    //    delay: 3000,
                    //    //disableOnInteraction: false 
                    //},
                    breakpoints: {
                        1279: {
                            slidesPerView: 2,
                            //initialSlide: 2,
                            loopAdditionalSlides: 2,
                            loopedSlides: 2,
                            speed: 800,
                            autoplay: {
                                delay: 3000

                            }
                        },
                        767: {
                            slidesPerView: 1,
                            spaceBetween: 20,
                            loopAdditionalSlides: 1,
                            loopedSlides: 1,
                            //speed: 800,
                            //autoplay: {
                            //    delay: 3000,
                            //}
                        }
                    }
                });
            }
            init(this);
            return $(this);
        }
    });

    //mn-event swiper
    $.extend($.fn, {
        mnEventSwiper: function () {
            var init = function (obj) {
                $area = $('.mn-event-swiper');
                $obj = $(obj);
                $slide = $(obj).find('.swiper-wrapper').find('.swiper-slide');
                $pagin = $area.find('.swiper-pagination');
                $btnprev = $area.find('.swiper-button-prev');
                $btnnext = $area.find('.swiper-button-next');

                var swiper = new Swiper($obj, {
                    slidesPerView: 3,
                    centeredSlides: 0,
                    loop: $slide.length > 3 ? true : false,
                    navigation: {
                        nextEl: $btnnext,
                        prevEl: $btnprev
                    },
                    //pagination: {
                    //    el: $pagin,
                    //    clickable: true
                    //},
                    speed: 700,
                    autoplay: {
                        delay: 3000,
                        //disableOnInteraction: false 
                    },
                    breakpoints: {
                        1279: {
                            slidesPerView: 2.2,
                            spaceBetween: 0,
                            //initialSlide: 2,
                            loopAdditionalSlides: 2,
                            loopedSlides: 2,
                            speed: 800,
                            autoplay: {
                                delay: 3000

                            }
                        },
                        767: {
                            slidesPerView: 1.2,
                            //initialSlide: 1,
                            spaceBetween: 0,
                            loopAdditionalSlides: 1,
                            loopedSlides: 1,
                            speed: 800,
                            autoplay: {
                                delay: 3000,
                            }
                        }
                    }
                });
            }
            init(this);
            return $(this);
        }
    });

    //product-detail-top Swiper
    $.extend($.fn, {
        productDetailSwiper: function () {
            var init = function (obj) {
                $area = $('.product-detail-top .top-img-area')
                $obj = $(obj);
                $slide = $(obj).find(".swiper-slide");
                $pagin = $area.find('.swiper-pagination');
                $btnprev = $area.find('.swiper-button-prev');
                $btnnext = $area.find('.swiper-button-next');

                if ($slide.length == 1) {
                    $(".product-detail-top .swiper-button-prev, .product-detail-top .swiper-button-next").hide();
                }
                else {
                    var swiper = new Swiper($obj, {
                        slidesPerView: 1,
                        loop: true,
                        navigation: {
                            nextEl: $btnnext,
                            prevEl: $btnprev
                        },
                        pagination: {
                            el: $pagin,
                            clickable: true
                        }
                    });
                }
            }
            init(this);
            return $(this);
        }
    });

    $.extend($.fn, {
        productRecoSwiper: function () {
            var init = function (obj) {
                $area = $('.product-recommend')
                $obj = $(obj);
                $slide = $(obj).find(".swiper-slide");
                $pagin = $area.find('.swiper-pagination');
                $btnprev = $area.find('.swiper-button-prev');
                $btnnext = $area.find('.swiper-button-next');

                var swiper = new Swiper($obj, {
                    slidesPerView: 'auto',
                    navigation: {
                        nextEl: $btnnext,
                        prevEl: $btnprev
                    }
                });
            }
            init(this);
            return $(this);
        }
    });

    //best Swiper
    $.extend($.fn, {
        bestSwiper: function () {
            var init = function (obj) {
                $area = $('.best-swiper')
                $obj = $(obj);
                $slide = $(obj).find(".swiper-slide");
                //$pagin = $area.find('.swiper-pagination');
                $btnprev = $area.find('.swiper-button-prev');
                $btnnext = $area.find('.swiper-button-next');

                var swiper = new Swiper($obj, {
                    slidesPerView: 'auto',
                    spaceBetween: 40,
                    navigation: {
                        nextEl: $btnnext,
                        prevEl: $btnprev
                    }
                });
            }
            init(this);
            return $(this);
        }
    });

    //Func
    //headerFunc
    $.extend($.fn, {
        headerFunc: function () {
            var init = function (obj) {
                //top banner 유무 체크
                if ($(obj).children().is('.banner-top')) {
                    $(obj).addClass('has-banner');
                }
                else {
                    $(obj).removeClass('has-banner');
                }

                var headerH = $('.site-header').height();
                //console.log(headerH);
                //hedaer sticky
                var controllerSticky = new ScrollMagic.Controller();
                var sceneBtnSticky = new ScrollMagic.Scene({
                    offset: 1
                }).setClassToggle(".site-header", "is-sticky").addTo(controllerSticky).on('enter', function () {
                    $('.site-body').css({ 'padding-top': headerH + 10 });
                }).on('leave', function () {
                    $('.site-body').css({ 'padding-top': '10px' });
                });
            };
            init(this);
            return $(this);
        }
    });

    //gnbFunc
    $.extend($.fn, {
        gnbFunc: function () {
            var init = function (obj) {
                $li = $(obj).children('ul').children('li');
                $($li).hover(function () {
                    if ($(this).length > 0) {
                        $(this).children('.subnav').show();
                    }
                }, function () {
                    if ($(this).length > 0) {
                        $(this).children('.subnav').hide();
                        // blur : 스크롤 상단으로 이동
                        var scrollH = $(this).children('.subnav').prop('scrollHeight'),
                            elH = $(this).children('.subnav').height();
                        if (scrollH > elH) {
                            $(this).children('.subnav').scrollTop(0);
                        }
                    }
                });
            };
            init(this);
            return $(this);
        }
    });

    //mMenuFunc
    $.extend($.fn, {
        mMenuFunc: function () {
            var init = function (obj) {
                //$('.m-btn-menu-toggle').on('click', function () {
                //    toggleCommonFunc({
                //        obj: $('.m-menu'),
                //        className: 'is-active',
                //        hasClass: function (obj) {
                //            $('.lnb-area').css({ 'z-index': '9' });
                //            $('body').css({ 'overflow-y': 'hidden' });
                //            var tl = new TimelineMax();
                //            tl.fromTo($('.m-menu'), .2, { marginTop: -20, opacity: .5 }, { marginTop: 0, opacity: 1 }).fromTo('.m-util', .2, { opacity: 0 }, { opacity: 1 }).fromTo('.m-menu nav', .3, { opacity: 0, y: 20 }, { opacity: 1, y: 0 });

                //            //모든 메뉴 열기
                //            $('.m-menu > nav > div > ul > li').removeClass('is-active');
                //            //스크롤 상단 이동
                //            $('.m-menu nav').scrollTop(0);

                //            //메뉴 닫기
                //            $('.btn-menu-close').on('click', function () {
                //                $('.m-menu').removeClass('is-active');
                //                $('.lnb-area').css({ 'z-index': '11' });
                //                $('body').css({ 'overflow-y': 'auto' });
                //            });

                //            //브랜드관 열기
                //            $('.btn-brand-open').on('click', function () {
                //                $('.m-menu').removeClass('is-active');
                //                $('.pop-brand').show();

                //                var tl = new TimelineMax();
                //                tl.fromTo($('.pop-brand'), .2, { marginTop: -20, opacity: .5 }, { marginTop: 0, opacity: 1 }).fromTo('.brand-list', .3, { opacity: 0, y: 20 }, { opacity: 1, y: 0 });

                //                return false;
                //            });
                //        },
                //        noneClass: function (obj) {
                //            $('.lnb-area').css({ 'z-index': '11' });
                //        }
                //    });
                //});

                //$('.pop-brand .btn-brand-close').on('click', function () {
                //    $('.pop-brand .brand-list').scrollTop(0);
                //    $('.pop-brand').hide();
                //    $('body').css({ 'overflow-y': 'auto' });

                //    return false;
                //});

                $('.btn-menu-accordion').on('click', function () {
                    $area = $(this).parent('li');
                    $active = $('.m-menu').find('.is-active');
                    $last = $('.m-menu > nav > div > ul > li').last();
                    toggleCommonFunc({
                        obj: $area,
                        className: 'is-active',
                        hasClass: function (obj) {
                            $active.removeClass('is-active');
                            if ($area.is($last)) {
                                innerHeight = $(window).height();
                                $(window).scrollTop(innerHeight);
                            }
                        },
                        noneClass: function (obj) {
                          
                        }
                    });

                    return false;
                });
            };
            init(this);
            return $(this);
        }
    });


    //foooterFunc
    $.extend($.fn, {
        footerFunc: function () {
            var init = function accordion(obj) {
                // footer info show& hide
                $('.site-footer .company-info cite').on('click', function () {
                    toggleCommonFunc({
                        obj: $('.company-info'),
                        className: 'is-active',
                        hasClass: function (obj) {
                        },
                        noneClass: function (obj) {
                        }
                    });
                });
            };
            init(this);
            return $(this);
        }
    });

    //btnTopFunc
    $.extend($.fn, {
        btnTopFunc: function () {
            var init = function accordion(obj) {
                var controllerSticky = new ScrollMagic.Controller();
                var sceneBtnSticky = new ScrollMagic.Scene({
                    triggerElement: '.site-footer',
                    triggerHook: 1
                }).setClassToggle('.btn-top', 'is-sticky').addTo(controllerSticky);
                var sceneBtnSticky2 = new ScrollMagic.Scene({
                    offset: 100
                }).setClassToggle('.btn-top', 'is-active').addTo(controllerSticky);

                //모바일 구매하기 있을 경우
                if ($(".btn-option-toggle").css("display") == "block") {
                    $('.btn-top').addClass('position-b')
                }
            };
            init(this);
            return $(this);
        }
    });

    //lnbFunc
    $.extend($.fn, {
        lnbFunc: function () {
            var init = function (obj) {
                var tit = $(obj).find('.lnb').children('cite').html();
                // 모바일 lnb 토글버튼 텍스트
                var btnTxt = $(obj).find('.lnb').children('ul').children('li').children('a.is-active').html();

                if ($(obj).find('.lnb').children('ul').children('li').children('a').hasClass('is-active') == true) {
                    $('.btn-lnb-toggle').html(btnTxt);
                }
                else {
                    $('.btn-lnb-toggle').html(tit);
                }

                // 모바일 lnb 토글
                //$('.btn-lnb-toggle, .btn-lnb-close').on('click', function () {
                //    toggleCommonFunc({
                //        obj: $(obj),
                //        className: 'is-active',
                //        hasClass: function (obj) {
                //            //$('.dim').show();
                //            var tl = new TimelineMax();
                //            tl.fromTo($('.lnb-area .lnb'), .2, { marginTop: -20, opacity: .5 }, { marginTop: 0, opacity: 1 }).fromTo('.lnb-area .lnb > ul', .3, { opacity: 0, y: 20 }, { opacity: 1, y: 0 });
                //            $('.lnb-area .lnb > ul').scrollTop(0);
                //            $('body').css({ 'overflow-y': 'hidden' });
                //        },
                //        noneClass: function (obj) {
                //            //$('.dim').hide();
                //            $('body').css({ 'overflow-y': 'auto' });
                //        }
                //    });
                //});

                //모바일 헤더 lnb
                $('.btn-lnb-toggle, .btn-lnb-close').on('click', function () {
                    toggleCommonFunc({
                        obj: $('.m-lnb-area'),
                        className: 'is-active',
                        hasClass: function (obj) {
                            //$('.dim').show();
                            var tl = new TimelineMax();
                            tl.fromTo($('.m-lnb-area .lnb'), .2, { marginTop: -20, opacity: .5 }, { marginTop: 0, opacity: 1 }).fromTo('.m-lnb-area .lnb > ul', .3, { opacity: 0, y: 20 }, { opacity: 1, y: 0 });
                            $('.m-lnb-area .lnb > ul').scrollTop(0);
                            $('body').css({ 'overflow-y': 'hidden' });
                        },
                        noneClass: function (obj) {
                            //$('.dim').hide();
                            $('body').css({ 'overflow-y': 'auto' });
                        }
                    });
                });
                $('.dim').on('click', function () {
                    if ($(obj).hasClass('.is-active')) {
                        $(obj).removeClass('.is-active');
                    }
                });
            };
            init(this);
            return $(this);
        }
    });

    //productDetail
    $.extend($.fn, {
        productDetailFunc: function () {
            var init = function accordion(obj) {
                // 상품 상세정보 필수정보 더보기
                $('.detail-info .txt-more button').on('click', function () {
                    toggleCommonFunc({
                        obj: $('.detail-info .txt-more'),
                        className: 'is-active',
                        hasClass: function (obj) {
                        },
                        noneClass: function (obj) {
                        }
                    });
                });

                // 상품 상세정보 이미지 더보기
                $('.detail-info .img-more .btn-lg').on('click', function () {
                    toggleCommonFunc({
                        obj: $('.detail-info .info-img-area'),
                        className: 'is-active',
                        hasClass: function () {
                            $('.detail-info .img-more .btn-lg').hide();
                        },
                        noneClass: function () {
                            $('.detail-info .img-more .btn-lg').show();
                        }
                    });
                });

                //상품 상세정보 이미지 사이즈 작을경우 상품더보기 숨김
                var imgarea = $('.detail-info .img-area').outerHeight();
                var imgBox = $('.detail-info .img-box').outerHeight();
                if (imgarea > imgBox) {
                    $('.info-img-area').addClass('is-active');
                    $('.detail-info .img-more .btn-lg').hide();
                }

                // 상품상세 탭 스크롤
                var controller = new ScrollMagic.Controller();
                $('.product-detail-area section').each(function () {
                    var $this = $(this);
                    var id = $this.attr('id');
                    new ScrollMagic.Scene({
                        triggerElement: this,
                        triggerHook: .2,
                        //offset: '100',
                        duration: $this.outerHeight()
                    }).on('enter', function (e) {
                        $(".tabs-nav li").removeClass("is-active");
                        $('.tabs-nav li a[href="#' + id + '"]').parent('li').addClass("is-active");
                    }).addTo(controller);
                });
                $(document).on("click", ".product-detail .tabs-nav li a", function (e) {
                    var $li = $(this).parent('li');
                    if ($li.hasClass("is-active") == false) {
                        $(".tabs-nav li").removeClass("is-active");
                        $(this).parent('li').addClass("is-active");
                    }
                });
                var scenetabsSticky = new ScrollMagic.Scene({
                    triggerElement: '.product-detail',
                    triggerHook: 'onLeave'
                }).setClassToggle('.product-detail .tabs-nav', 'is-sticky').addTo(controller);
                var sceneBuySticky = new ScrollMagic.Scene({
                    triggerElement: '.product-detail',
                    triggerHook: 'onLeave'
                }).setClassToggle('.product-detail .product-buy-area', 'is-sticky').addTo(controller);
                var sceneBottomSticky = new ScrollMagic.Scene({
                    triggerElement: '.site-footer',
                    triggerHook: 1
                }).setClassToggle('.product-detail .product-buy-area', 'is-sticky-b').addTo(controller);

                //모바일 구매하기 토글
                $('.product-detail .product-buy-area .btn-option-toggle').on('click', function () {
                    toggleCommonFunc({
                        obj: $('.product-detail .product-buy-area'),
                        className: 'is-active',
                        hasClass: function () {
                            //$('body').css({ 'overflow-y': 'hidden' });
                            TweenMax.fromTo('.product-detail .product-buy-area .toggle-area', .3, { css: { 'transform': 'translateY(100%)', 'opacity': '0' } }, { css: { 'transform': 'translateY(0)', 'opacity': '1' } })
                            //$('.dim').show();
                        },
                        noneClass: function () {
                            //$('body').css({ 'overflow-y': 'auto' });
                            //$('.dim:not(.zindex-dim)').hide();
                        }
                    });
                });

                //$('.dim').on('click', function () {
                //    if ($('.product-detail .product-buy-area').hasClass('is-active')) {
                //        $('.product-detail .product-buy-area').removeClass('is-active');
                //        $(this).hide();
                //    }
                //});

                /* 체험구독 */
                //$('.option-check-area .optioncheck').on('click', function () {
                //    if ($(this).is(':checked')) {
                //        $('.option-check-area .optioncheck').prop('checked', true).button("refresh");
                //    }
                //    else {
                //        $('.option-check-area .optioncheck').prop('checked', false).button("refresh");
                //    }

                //    toggleCommonFunc({
                //        obj: $('.option-check-area'),
                //        className: 'is-active',
                //        hasClass: function () {

                //        },
                //        noneClass: function () {

                //        }
                //    });
                //});
            };
            init(this);
            return $(this);
        }
    });

    //* stkcky-cont
    $.extend($.fn, {
        stickyFunc: function () {
            var init = function (obj) {
                ssmFunc({
                    pc: function () {
                        var controller = new ScrollMagic.Controller({});
                        var sceneSticky = new ScrollMagic.Scene({
                            triggerElement: '.sticky-cont',
                            triggerHook: 'onLeave'
                        }).setClassToggle('.sticky-area', 'is-sticky').addTo(controller);
                        var sceneBottomSticky = new ScrollMagic.Scene({
                            triggerElement: '.site-footer',
                            triggerHook: 1
                        }).setClassToggle('.sticky-area', 'is-sticky-b').addTo(controller);

                        // sticky-area.is-sticky-b Height
                        var stickyPos = function () {
                            documentH = $(document).height(); // 문서 전체 높이
                            documentScrT = $(document).scrollTop(); // 문서 전체 높이 중 스크롤 위치
                            windowH = $(window).height(); // 창 높이
                            windowW = $(window).width();
                            footerH = $(".site-footer").height();

                            gap = documentH - footerH - windowH;
                            bottom = documentScrT - gap;
                            if (documentScrT > gap && windowW >= 1280) {
                                $(".sticky-area.is-sticky-b").css({
                                    'height': windowH - bottom - footerH - 80 + 'px'
                                });
                            } else {
                                $(".sticky-area.is-sticky-b").css("height", "auto");
                            }
                        }
                        $(window).on('load scroll resize', function () {
                            stickyPos();
                        });
                    },
                    ta: function () {
                        $(".sticky-area").removeClass('is-sticky-b, is-sticky');
                    },
                    mo: function () {
                        $(".sticky-area").removeClass('is-sticky-b, is-sticky');
                    }
                });
            };
            init(this);
            return $(this);
        }
    });

    $.extend($.fn, {
        gradeCheckFunc: function () {
            var init = function () {
                //상품평
                $('.grade-check button').on('click', function () {
                    $('.grade-check button').removeClass('is-active');
                    $(this).addClass('is-active').prevAll().addClass('is-active');
                    TweenMax.fromTo('.grade-check .is-active', .5, { scale: 1.4 }, { scale: 1 });
                });
            };
            init(this);
            return $(this);
        }
    });

    //pop Review View Thumb
    $.extend($.fn, {
        popReviewFunc: function () {
            var init = function () {
                $('.img-list a').on('click', function () {
                    var thumb = $(this).find('img'),
                        thumbSrc = thumb.attr('src'),
                        img = $('.img-area img');
                    img.attr('src', thumbSrc);
                });
            };
            init(this);
            return $(this);
        }
    });

    //날짜선택
    $.extend($.fn, {
        datepickerFunc: function () {
            var init = function () {
                $(document).on('click', '.btn-datepicker-toggle', function (e) {
                    e.stopPropagation();
                    var $btn = $(e.target);
                    toggleCommonFunc({
                        obj: $('.layer-datepicker'),
                        className: 'is-active',
                        hasClass: function () {
                            $('.layer-datepicker button').on('click', function () {
                                $('.layer-datepicker button').removeClass('is-current');
                                $(this).addClass('is-current');

                                //.datepicker-input 입력
                                $txt = $(this).find('span').text();
                                $('.datepicker-input').attr('value', $txt);

                                //닫기
                                layerClose();
                            });
                        },
                        noneClass: function () {
                        }
                    });
                    layerPosition($btn);
                });
                $(document).on('click scroll resize', function () {
                    layerClose();
                });

                var layerPosition = function (obj) {
                    var $target = $(obj),
                        offset = $target.offset(),
                        layerWidth = $('.layer-datepicker').outerWidth(),
                        //tartWidth = $target.outerWidth(),
                        top = offset.top,
                        left = offset.left,
                        bottom = top + $target.outerHeight() - 1,
                        right = left + $target.outerWidth() - layerWidth + 1;
                    $('.layer-datepicker').css({
                        'top': top,
                        'left': right
                    })
                };
                var layerClose = function () {
                    $('.layer-datepicker').removeClass('is-active').css({
                        'top': 'auto',
                        'left': 'auto'
                    });

                }
            };
            init(this);
            return $(this);
        }
    });

    //tooltip
    $.extend($.fn, {
        tooltipFunc: function () {
            var init = function (obj) {
                var $btn = $(obj).children('.btn-tooltip'),
                    $btnClose = $(obj).find('.btn-close');
                $btn.add($btnClose).on('click', function () {
                    $(obj).toggleClass('is-active');
                });
            };
            $(this).each(function () {
                init(this);
            });
            return $(this);
        }
    });


    $.extend($.fn, {
        layerpopupSmFunc: function () {
            var init = function (obj) {
                var $btn = $(obj).children('.btn-layerpopup-sm'),
                    $btnClose = $(obj).find('.btn-close');
                $btn.add($btnClose).on('click', function () {
                    $(obj).toggleClass('is-active');
                });
            };
            $(this).each(function () {
                init(this);
            });
            return $(this);
        }
    });


    $.extend($.fn, {
        brandFunc: function () {
            var init = function (obj) {
                // 상품상세 탭 스크롤
                var controller = new ScrollMagic.Controller();
                $('.brand > section').each(function () {
                    var $this = $(this);
                    var id = $this.attr('id');
                    new ScrollMagic.Scene({
                        triggerElement: this,
                        triggerHook: .2,
                        //offset: '100',
                        duration: $this.outerHeight()
                    }).on('enter', function (e) {
                        $(".brand-nav a").removeClass("is-active");
                        $('.brand-nav a[href="#' + id + '"]').addClass("is-active");
                    }).addTo(controller);
                });
                $(document).on("click", ".brand-nav li a", function (e) {
                    var $li = $(this).parent('li');
                    if ($li.hasClass("is-active") == false) {
                        $(".brand-nav a").removeClass("is-active");
                        $(this).addClass("is-active");
                    }
                });
                var scene = new ScrollMagic.Scene({
                    triggerElement: '.brand-nav',
                    triggerHook: 'onEnter'
                }).setClassToggle('.brand-nav', 'is-sticky').addTo(controller);
                var scene = new ScrollMagic.Scene({
                    triggerElement: '.site-footer',
                    triggerHook: 1
                }).setClassToggle('.brand-nav', 'is-sticky-b').addTo(controller);

            };
            $(this).each(function () {
                init(this);
            });
            return $(this);
        }
    });

    //$.extend($.fn, {
    //    mnAnimation: function () {
    //        var init = function (obj) {
    //            //mn-visual
    //            var tl = new TimelineMax();
    //            var $el = $(obj).find('.visual-txt-area').children();

    //            $el.each(function () {
    //                if ($(this).hasClass('btn-lg') == true) {
    //                    tl.fromTo($(this), .4, { opacity: 0 }, { opacity: 1 });
    //                }
    //                else {
    //                    tl.fromTo($(this), .4, { yPercent: 30, opacity: 0 }, { yPercent: 0, opacity: 1 })
    //                }
    //            });
    //        };
    //        init(this);
    //        return $(this);
    //    }
    //});

    $.extend($.fn, {
        brandAnimation: function () {
            var init = function (obj) {
                var tl = new TimelineMax();
                var $el = $(obj).find('.visual-txt-area > *:not(.btn-intro)').children();
                $el.each(function () {
                    tl.fromTo($(this), .4, { yPercent: 20, opacity: 0 }, { yPercent: 0, opacity: 1 });
                });
            };
            init(this);
            return $(this);
        }
    });

    $.extend($.fn, {
        brandIntroAnimation: function () {
            var init = function (obj) {
                var tl = new TimelineMax();
                var $el = $(obj).children();
                $el.each(function () {
                    if ($(this).hasClass('logo-brand') == true || $(this).hasClass('img-full') == true) {
                        tl.fromTo($(this), .4, { yPercent: 20, opacity: 0 }, { yPercent: 0, opacity: 1 });
                    }
                    else {
                        tl.fromTo($(this), .4, { xPercent: 20, opacity: 0 }, { xPercent: 0, opacity: 1 })
                    }
                });
            };
            init(this);
            return $(this);
        }
    });

    $.extend($.fn, {
        mnBestAnimation: function () {
            var init = function (obj) {
                //var tl = new TimelineMax({});
                //var $li = $('.product-list.col4  > li');

                //$.each($li, function () {
                //    tl.staggerFromTo([$(this)], .3, { css: { 'transform': 'translateY(20%)', 'opacity': '0' } }, { css: { 'transform': 'translateX(0)', 'opacity': '1' } });
                //});
                var tl = new TimelineMax();
                var $li = $('.mn-product .product-list.col4  > li');
                $.each($li, function () {
                    tl.fromTo($(this), .15, { yPercent: 20, opacity: 0 }, { yPercent: 0, opacity: 1 })
                });

                var tlMain = new TimelineMax();
                tlMain.fromTo($('.product-best .best-img-area'), .4, { xPercent: -10, opacity: 0 }, { xPercent: 0, opacity: 1 })
                    .fromTo($('.product-best .best-txt-area'), .4, { xPercent: 10, opacity: 0 }, { xPercent: 0, opacity: 1 })
                    .fromTo($('.product-best .best-img-area .label-num'), .4, { opacity: 0 }, { opacity: 1 });

                var controller1 = new ScrollMagic.Controller();
                var scene1 = new ScrollMagic.Scene({
                    triggerElement: '.mn-product .product-best',
                    triggerHook: .7
                }).setTween(tlMain).addTo(controller1);

                var scene2 = new ScrollMagic.Scene({
                    triggerElement: '.mn-product .product-list.col4',
                    triggerHook: .7
                }).setTween(tl).addTo(controller1);

            };
            init(this);
            return $(this);
        }
    });


    $.extend($.fn, {
        sitemapAnimation: function () {
            var init = function (obj) {
                var tl = new TimelineMax();
                var $el = $(obj).children();
                $.each($el, function () {
                    tl.fromTo($(this), .3, { xPercent: -20, opacity: 0 }, { xPercent: 0, opacity: 1 });
                });
            };
            init(this);
            return $(this);
        }
    });

    //$.extend($.fn, {
    //    popupItemAnimation: function () {
    //        var init = function (obj) {
    //            // popup-item animation
    //            var tl = new TimelineMax;
    //            ssmFunc({
    //                pc: function () {
    //                    tl.fromTo(
    //                        $(obj), .7,
    //                        { xPercent: 150 },
    //                        { xPercent: 0, delay: .7 }).set($(obj), { clearProps: 'all' });
    //                },
    //                ta: function () {
    //                    tl.fromTo(
    //                        $(obj), .7,
    //                        { xPercent: 150 },
    //                        { xPercent: 0, delay: .7 }).set($(obj), { clearProps: 'all' });
    //                },
    //                mo: function () {
    //                    tl.staggerFromTo(
    //                        $(obj), .7,
    //                        { css: { 'margin-top': '10%' } },
    //                        { css: { 'margin-top': '0' } }).set($(obj), { clearProps: 'all' });
    //                }
    //            });
    //        };
    //        init(this);
    //        return $(this);
    //    }
    //});

})(jQuery);


//pop review Swiper
var popReviewSwiper = function () {
    $('.popup-review').css('display', 'block');
    $obj = $('.popup-review .swiper-container');
    $slide = $obj.find(".swiper-slide");

    $area = $('.popup-review')
    //$pagin = $area.find('.swiper-pagination');
    $btnprev = $area.find('.swiper-button-prev');
    $btnnext = $area.find('.swiper-button-next');

    if ($slide.length < 1) {
        $(".popup-review .swiper-button-prev, .popup-review .swiper-button-next").hide();
    }
    else {
        var swiper = new Swiper($obj, {
            slidesPerView: 'auto',
            spaceBetween: 10,
            freeMode: true,
            navigation: {
                nextEl: $btnnext,
                prevEl: $btnprev
            }
        });
    }
}

//세로스크롤 여부 체크
var scollvCheck = function (obj) {
    return $(obj).get(0) ? $(obj).get(0).scrollHeight > $(obj).innerHeight() : false;
}

//popup center
var popPosition = function (obj) {
    var w = parseInt($(obj).outerWidth()) / 2,
        h = parseInt($(obj).outerHeight()) / 2;
    var position = $(obj).css({ marginLeft: -w, marginTop: -h });
}

//pop open
var popOpen = function (target) {
    var $pop = $('[data-popup="' + target + '"]'),
        $popBody = $pop.find('.pop-body');
    $pop.show();
    popPosition($pop);
    TweenMax.fromTo($pop, .3, { css: { 'transform': 'translateY(10%)', 'opacity': '0' } }, { css: { 'transform': 'translateY(0)', 'opacity': '1' } })

    if (scollvCheck($popBody) == true) {
        $popBody.scrollTop(0);
    }

    $('.dim').show();
    $('body').css({ 'overflow-y': 'hidden' });
    //if ($pop.hasClass('popup-alert') == false) {        
    //}
}

//pop open 이중팝업
var popOpen2 = function (target) {
    event.stopPropagation();
    var $pop = $('[data-popup="' + target + '"]'),
        $popBody = $pop.find('.pop-body');
    $pop.show().addClass('zindex-pop');
    popPosition($pop);
    TweenMax.fromTo($pop, .3, { css: { 'transform': 'translateY(10%)', 'opacity': '0' } }, { css: { 'transform': 'translateY(0)', 'opacity': '1' } })

    if (scollvCheck($popBody) == true) {
        $popBody.scrollTop(0);
    }
    $('.dim').show().addClass('zindex-dim');
    $('body').css({ 'overflow-y': 'hidden' });
}

//popup close
var popClose = function (target) {
    var $pop = $('[data-popup="' + target + '"]');
    $pop.fadeOut(200);
    if ($pop.hasClass('zindex-pop') == false) {
        $('body').css({ 'overflow-y': 'auto' });
        $('.dim:not(.zindex-dim)').hide();
    }
    else if ($pop.hasClass('zindex-pop') == true) {
        $pop.removeClass('zindex-pop');
        $('.dim').removeClass('zindex-dim');
    }
}


//모바일 dim show
var mDimShow = function (obj) {
    windowW = $(window).width();
    if (windowW < 767 && $(obj).css('display') == 'block') {
        $('.dim').show();
    }
    else {
        $('.dim').hide();
    }
}

//윈도우 팝업
var popWinOpen = function (url, userwidth, userheight) {
    window.open(url, "새창팝업", "toolbar=no, menubar=no, scrollbars=yes, resizable=no, location=no, width=" + userwidth + ",height=" + userheight + ", left=0, top=0");
}

//토스트 메세지
var toastopen = false;
var toast = function (msg) {
    var $msg = msg;
    if (toastopen == false) {
        $('.toast').addClass('is-open', function () {
            $(this).find('div').html($msg);
            TweenMax.fromTo($(this), .4, { yPercent: 100, opacity: 0 }, { yPercent: 0, opacity: 1 });
            toastopen = true;
        });
        setTimeout(function () {
            $('.toast').removeClass('is-open', function () {
                TweenMax.fromTo($(this), .3, { yPercent: 0, opacity: 1 }, { yPercent: 100, opacity: 0 });
            });
            toastopen = false;
        }, 2500);    
    }
} 

$(document).ready(function () {
    //jquery-ui
    $(".tabs:not(.normal)").tabs();
    //$('select:not(select[multiple]):not(.normal)').selectmenu();
    $("input[type=checkbox]:not(.normal), input[type=radio]:not(.normal)").checkboxradio();
    $(".datepicker").datepicker({
        numberOfMonths: 6, //달력 개수
        //showButtonPanel: true,
        //closeText: '취소',
        //prevText: '이전달',
        //nextText: '다음달',
        //currentText: '오늘',
        monthNamesShort: ['01월', '02월', '03월', '04월', '05월', '06월', '07월', '08월', '09월', '10월', '11월', '12월'],//달력의 월 부분 Tooltip 텍스트
        monthNames: ['01월', '02월', '03월', '04월', '05월', '06월', '07월', '08월', '09월', '10월', '11월', '12월'], //달력의 월 부분 텍스트 
        dayNamesMin: ['일', '월', '화', '수', '목', '금', '토'], //달력의 요일 부분 텍스트
        dayNames: ['일요일', '월요일', '화요일', '수요일', '목요일', '금요일', '토요일'], //달력의 요일 부분 Tooltip 텍스트
        dateFormat: 'yy-mm-dd',
        firstDay: 0, //주가 시작되는 요일(0: 일요일, 1: 월요일)
        minDate: 0, // 선택할수있는 최소날짜, ( 0 : 오늘 이전 날짜 선택 불가)
        duration: 200,
        showOtherMonths: true, //빈 공간에 현재월의 앞뒤월의 날짜를 표시
        showMonthAfterYear: true, //년도 먼저 나오고, 뒤에 월 표시
        yearSuffix: '년',
        monthSuffix: '월',
        //onSelect: function (dateText, inst) {
        //   
        //}
    });
    $(window).on('scroll', function () {
        $('#ui-datepicker-div').hide();
    });

    //component
    //accordionFunc 
    $('.accordion').accordionFunc();
    //popup
    $('.popup, .popup-lg, .popup-review, .popup-agree, .popup-alert').popupFunc();
    $('.popup-item').popupItemFunc();

    //input number length
    $('input[type=number]').on('input', function () {
        var $this = $(this);
        var maxlength = $this.attr('maxlength');
        var value = $this.val();
        if (value && value.length >= maxlength) {
            $this.val(value.substr(0, maxlength));
        }
    });
    //input focus auto
    $(".focus-auto input").on('input', function () {
        var $this = $(this);
        var maxlength = $this.attr('maxlength');
        var value = $this.val();
        if (value && value.length == maxlength) {
            $(this).next('input').focus();
        }
    });

    //text count
    $('.txt-count-area').txtCountFunc();

    //tabs-normal
    $('.tabs-normal').tabsFunc();

    //Swiper
    $('.mn-visual .swiper-container').mnVisualSwiper();
    $('.mn-recommend .swiper-container').mnRecoSwiper();
    $('.mn-event .swiper-container').mnEventSwiper();
    $('.product-detail-top .swiper-container').productDetailSwiper();
    $('.product-recommend .swiper-container').productRecoSwiper();
    //$('.best-swiper .swiper-container').bestSwiper();

    //func
    $('.site-header').headerFunc();
    $('.site-gnb').gnbFunc();
    $('.m-menu').mMenuFunc();
    $('.site-footer').footerFunc();
    $('.btn-top').btnTopFunc();
    $('.lnb-area').lnbFunc();
    $('.product-detail').productDetailFunc();
    $('.grade-check').gradeCheckFunc();
    $('.popup-review').popReviewFunc();
    $('.sticky-cont').stickyFunc();
    $('.datepicker-area').datepickerFunc();
    $('.tooltip-area').tooltipFunc();
    $('.layerpopup-sm-area').layerpopupSmFunc();
    //$('.brand').brandFunc();

    //animation
    //$('.mn-visual').mnAnimation();
    $('.brand-visual').brandAnimation();
    $('.brand-intro').brandIntroAnimation();
    $('.mn-product').mnBestAnimation();
    $('.sitemap').sitemapAnimation();
    //$('.popup-item').popupItemAnimation();
});