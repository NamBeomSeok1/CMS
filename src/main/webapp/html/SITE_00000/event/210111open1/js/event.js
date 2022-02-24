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
    	modooAlert('종료된 이벤트입니다.', '', function() {
        	location.href= CTX_ROOT + "/shop/event/goodsEventList.do";
        });
    	
    	var canvas = document.getElementById('snow');
        makeSnow(canvas);
    });
})();