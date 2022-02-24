if (jQuery) (function ($) {
    //event-tabs
    $.extend($.fn, {
        eventTabsFunc: function () {
            var init = function (obj) {
                var $currentTab = $(obj).find('.event-tabs-nav .is-active a').attr('href');
                $($currentTab).show();

                var $currentTabSm = $($currentTab).find('.event-tabs-nav-sm .is-active a').attr('href');
                $($currentTabSm).show();


                $(obj).find('.event-tabs-nav a').on('click', function () {
                    var $li = $(this).parent('li');
                    if ($li.hasClass("is-active") == false) {
                        $(this).parent('li').siblings('li').removeClass("is-active");
                        $(this).parent('li').addClass("is-active");
                    }

                    $(obj).find('.event-tabs-cont').removeClass('is-active');
                    var $activeTab = $(this).attr('href');
                    $($activeTab).addClass('is-active');

                    if ($($activeTab).children().hasClass('event-tabs-sm')) {
                        $('.event-tabs-sm li').removeClass('is-active');
                        $('.event-tabs-cont-sm').removeClass('is-active');
                        $($activeTab).find('.event-tabs-sm li').first().addClass('is-active');
                        $($activeTab).find('.event-tabs-cont-sm-area').find('.event-tabs-cont-sm').first().addClass('is-active');
                        $($activeTab).find('.event-tabs-nav-sm').scrollLeft(0);
                    }
                    else {
                        
                    }

                    $navOffset = $('.event-tabs-nav').offset().top;
                    $headerH = $('.site-header').outerHeight();

                    $('html').animate({
                        scrollTop: $navOffset - $headerH - 30
                    }, 500);

                    $liIndex = $(this).parent('li').index();
                    $liW = $(this).parent('li').outerWidth();
                    $('.event-tabs-nav').animate({
                        scrollLeft: $liW * $liIndex
                    }, 500);

                    return false;
                });

                $('.event-tabs-nav-sm a').on('click', function () {
                    var $li = $(this).parent('li');
                    if ($li.hasClass("is-active") == false) {
                        $(this).parent('li').siblings('li').removeClass("is-active");
                        $(this).parent('li').addClass("is-active");
                    }

                    $('.event-tabs-cont-sm').removeClass('is-active');
                    var $activeTab = $(this).attr('href');
                    $($activeTab).addClass('is-active');

                    $liIndex = $(this).parent('li').index();
                    $liW = $(this).parent('li').outerWidth();
                    $nav = $(this).parent('li').parent('.event-tabs-nav-sm');

                    $nav.animate({
                        scrollLeft: $liW * $liIndex
                    }, 500);

                    return false;
                });
            };
            init(this);
            return $(this);
        }
    });

    $.extend($.fn, {
        chaFunc: function () {
            var init = function accordion(obj) {
                var controllerSticky = new ScrollMagic.Controller();
                var sceneBtnSticky = new ScrollMagic.Scene({
                    triggerElement: '.site-footer',
                    triggerHook: 1
                }).setClassToggle('.img-cha', 'is-sticky').addTo(controllerSticky);
            };
            init(this);
            return $(this);
        }
    });
})(jQuery);

$(document).ready(function () {
   
    var str = '<li class="finish">                   '  
    	+ '	<div>                              '
    	+ '		<a href="#hd">                 '
    	+ '			<cite>현대생활식서</cite>       '
    	+ '			<p>‘바로잡곡6종세트’증정</p>      '
    	+ '		</a>                           '
    	+ '	</div>                             '
    	+ '</li>                                 '
    	+ '<li class="finish">                   '
    	+ '	<a href="#hmh">                    '
    	+ '		<cite>한미멀티비타민</cite>          '
    	+ '		<p>임팩트 멀티비타민 1+1 29,900원</p> '
    	+ '	</a>                               '
    	+ '</li>                                 '
    	+ '<li class="finish">                   '
    	+ '	<a href="#hm">                     '
    	+ '		<cite>한만두</cite>              '
    	+ '		<p>2021명 선착순 설 가격 할인이벤트</p>  '
    	+ '	</a>                               '
    	+ '</li>                                 '
    	+ '<li class="finish">                   '
    	+ '	<a href="#jg">                     '
    	+ '		<cite>침향단</cite>              '
    	+ '		<p>종근당 활력 침향단 1+1</p>        '
    	+ '	</a>                               '
    	+ '</li>                                 '
    	+ '<li class="finish">                   '
    	+ '	<a href="#js">                     '
    	+ '		<cite>장수김</cite>              '
    	+ '		<p>설 선물세트 / 미니김6종 증정</p>     '
    	+ '	</a>                               '
    	+ '</li>                                 '
    	+ '<li class="finish">                   '
    	+ '	<a href="#wg">                     '
    	+ '		<cite>우가청담</cite>             '
    	+ '		<p>미슐랭 명품한우 설 선물세트</p>       '
    	+ '	</a>                               '
    	+ '</li>                                 '
    	+ '<li class="finish">                   '
    	+ '	<a href="#ot">                     '
    	+ '		<cite>오트리</cite>              '
    	+ '		<p>설 선물세트 9종 / 견과류 가격 할인</p>'
    	+ '	</a>                               '
    	+ '</li>                                 '
    	+ '<li class="finish">                   '
    	+ '	<a href="#pp">                     '
    	+ '		<cite>엘레강스펫</cite>            '
    	+ '		<p>가격 할인, 1+1, 증정</p>         '
    	+ '	</a>                               '
    	+ '</li>                                 '
    	+ '<li class="finish">                   '
    	+ '	<a href="#hn">                     '
    	+ '		<cite>어니스틴</cite>             '
    	+ '		<p>2주차프로모션 기획</p>            '
    	+ '	</a>                               '
    	+ '</li>                                 '
    	+ '<li class="finish">                   '
    	+ '	<a href="#id">                     '
    	+ '		<cite>아이두비</cite>             '
    	+ '		<p>생생 수퍼푸드 현미칩 선물 세트</p>    '
    	+ '	</a>                               '
    	+ '</li>                                 '
    	+ '<li class="finish">                   '
    	+ '	<a href="#se">                     '
    	+ '		<cite>씨앤트리</cite>             '
    	+ '		<p>정품 증정 스페셜 이벤트</p>         '
    	+ '	</a>                               '
    	+ '</li>                                 '
    	+ '<li class="finish">                   '
    	+ '	<a href="#sn">                     '
    	+ '		<cite>스낵24</cite>              '
    	+ '		<p>간식서랍 가격 할인</p>            '
    	+ '	</a>                               '
    	+ '</li>                                 '
    	+ '<li class="finish">                   '
    	+ '	<a href="#sw">                     '
    	+ '		<cite>성원상회</cite>             '
    	+ '		<p>제품 구매시 선착순 100명</p>       '
    	+ '	</a>                               '
    	+ '</li>                                 '
    	+ '<li class="finish">                   '
    	+ '	<a href="#sl">                     '
    	+ '		<cite>샐러드판다</cite>            '
    	+ '		<p>첫 구매 고객 20% 할인</p>         '
    	+ '	</a>                               '
    	+ '</li>                                 '
    	+ '<li class="finish">                   '
    	+ '	<a href="#bt">                     '
    	+ '		<cite>보틀웍스</cite>             '
    	+ '		<p>2021년 보틀웍스 설 선물세트</p>     '
    	+ '	</a>                               '
    	+ '</li>                                 '
    	+ '<li class="is-active">                '
    	+ '	<a href="#mh">                     '
    	+ '		<cite>미하이삭스</cite>            '
    	+ '		<p>유튜브 구독 + 좋아요, 1+1 혜택</p>  '
    	+ '	</a>                               '
    	+ '</li>                                 '
    	+ '<li class="finish">                   '
    	+ '	<a href="#md">                     '
    	+ '		<cite>메디에이지</cite>            '
    	+ '		<p>뉴트리에이지 990원</p>            '
    	+ '	</a>                               '
    	+ '</li>                                 '
    	+ '<li class="finish">                   '
    	+ '	<a href="#ra">                     '
    	+ '		<cite>레아라 다이아포스</cite>       '
    	+ '		<p>첫구독시 할인</p>                '
    	+ '	</a>                               '
    	+ '</li>                                 '
    	+ '<li class="finish">                   '
    	+ '	<a href="#dd">                     '
    	+ '		<cite>뜨라래</cite>              '
    	+ '		<p>동원 천지인 홍력천골드 10병</p>      '
    	+ '	</a>                               '
    	+ '</li>                                 '
    	+ '<li>                                  '
    	+ '	<a href="#di">                     '
    	+ '		<cite>다이아프리티</cite>           '
    	+ '		<p>립스틱, 밀키 크림 할인</p>         '
    	+ '	</a>                               '
    	+ '</li>                                 '
    	+ '<li class="finish">                   ';
    
    var today = moment(new Date()).format('YYYY-MM-DD');
	if (today >= '2021-02-15') {
		$('ul.event-tabs-nav').html(str);
	}
    
    //event-tabs
    $('.event-tabs').eventTabsFunc();
    $('.img-cha').chaFunc();
});


//snow
(function () {
    function ready(fn) {
        if (document.readyState != 'loading') {
            fn();
        } else {
            document.addEventListener('DOMContentLoaded', fn);
        }
    }

    function makeSnow(el) {
        var ctx = el.getContext('2d');
        var width = 0;
        var height = 0;
        var particles = [];

        var Particle = function () {
            this.x = this.y = this.dx = this.dy = 0;
            this.reset();
        }

        Particle.prototype.reset = function () {
            this.y = Math.random() * height;
            this.x = Math.random() * width;
            this.dx = (Math.random() * 1) - 0.5;
            this.dy = (Math.random() * 0.5) + 0.5;
        }

        function createParticles(count) {
            if (count != particles.length) {
                particles = [];
                for (var i = 0; i < count; i++) {
                    particles.push(new Particle());
                }
            }
        }

        function onResize() {
            width = window.innerWidth;
            height = window.innerHeight;
            el.width = width;
            el.height = height;

            createParticles((width * height) / 10000);
        }

        function updateParticles() {
            ctx.clearRect(0, 0, width, height);
            ctx.fillStyle = '#fff';

            particles.forEach(function (particle) {
                particle.y += particle.dy;
                particle.x += particle.dx;

                if (particle.y > height) {
                    particle.y = 0;
                }

                if (particle.x > width) {
                    particle.reset();
                    particle.y = 0;
                }

                ctx.beginPath();
                ctx.arc(particle.x, particle.y, 5, 0, Math.PI * 2, false);
                ctx.fill();
            });

            window.requestAnimationFrame(updateParticles);
        }

        onResize();
        updateParticles();

        window.addEventListener('resize', onResize);
    }

    ready(function () {
        var canvas = document.getElementById('snow');
        makeSnow(canvas);
    });
})();