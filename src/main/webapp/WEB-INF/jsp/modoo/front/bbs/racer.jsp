<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<c:set var="CTX_ROOT" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html lang="ko">

<head>
    <title>딥레이서 순위 현황</title>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <meta name="Author" content="">
    <meta name="Keywords" content="">
    <meta name="Description" content="">
    <meta name="format-detection" content="telephone=no">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width,initial-scale=1.0,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no" />
    <link href="${CTX_ROOT}/resources/front/bbs/css/style.css" rel="stylesheet" type="text/css"/>
</head>

<body>
<div class="rank">
    <div class="wrap">
        <div class="rank-etc">
            <figure>
                <img src="/resources/front/bbs/images/logo_foxedulc_wh.svg" alt="FOX EDU, FOX LC" />
            </figure>
        </div>
        <header class="rank-header">
            <figure>

                <img src="/resources/front/bbs/images/tit.svg" alt="1st AWS DeepRacer League FOX LEARNING CENTER @ Daejeon" />
            </figure>
            <div class="date">
                <i></i>
             <%--   <span>2022-02-23 ~ 2022-02-23</span>--%>
            </div>
        </header>
        <div class="rank-body">
            <div class="rank-tit">
                <span>Position</span>
                <span>Racer <em>#Number</em></span>
                <span>Time</span>
            </div>
            <ul class="rank-list is-animation">

            </ul>
        </div>
    </div>
</div>
<script src="../../resources/lib/jquery/jquery.js"></script>
<script>
    $(window).on('load', function () {

        getBbsList();
        setInterval(function () {
            getBbsList();
        }, 10000);


        });
        function loop() {
            $('.rank-list li').each(function (i) {
                $(this).delay((i++) * 300)
                    .animate({ 'top': '5%', 'opacity': '0' }, 300)
                    .animate({ 'top': '0', 'opacity': '1' }, 300);
            });
        }

    function getBbsList() {
        $.ajax({
            url:'/bbs/bbsList.json',
            type:'GET',
            success:function(result){
                var html='';
                for(var i=0; i<result.data.list.length;i++){
                    var data = result.data.list[i];
                    console.log(parseInt(i)===0);
                    var cssClass = parseInt(i)===0||parseInt(i)===1||parseInt(i)===2?'rank'+parseInt(i+1):'';

                    html+='<li class='+cssClass+'>';
                    html+=  '<div>';
                    html+=' <span class="num">'+(parseInt(i)+1)+'</span>';
                    /*html+=' <cite>'+data.usrNm+' <strong>#'+data.partcptnCo+'</strong></cite>';*/
                    html+=' <cite>'+data.usrNm+'</cite>';
                    html+=' <span class="time">'+data.rcord+'</span>';
                    html+=  '<div>'
                    html+='</li>'
                }
                $('.rank-list li').remove();
                $('.rank-list').append(html);
                loop();
            }
        });
    }

</script>
</body>

</html>