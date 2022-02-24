<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/modoo/common/commonTagLibs.jsp"%>
<c:set var="title" value="이벤트"/>
<!DOCTYPE html>
<html>
<head>
	<title>${title}</title>
</head>
<body>
<div class="wrap">
    <div class="sub-contents">
        <c:import url="/shop/event/goodsEventLocation.do" charEncoding="utf-8">
        	<c:param name="isEventHome" value="true"/>
        </c:import>
        <h2 class="txt-hide">이벤트관</h2>
        <div class="sub-tit-area">
            <p><span class="inlin-block m-block"><strong>이벤트관</strong></span> <em id="eventCnt">0</em>개의 이벤트</p>
            <div class="fnc-area">
                <select class="border-none" name="searchOrder">
                    <option value="1">최근등록순</option>
                    <option value="2">마감임박순</option>
                </select>
            </div>
        </div>
        <div class="border-top" id="eventWrapper">
            <ul class="event-list" id="eventList">
            </ul>
            <div class="btn-area" id="show-more-area">
<!-- 	                <a href="#none" class="btn-lg width spot3">더보기<i class="ico-arr-b sm back" aria-hidden="true"></i></a> -->
            </div>
        </div>
    </div>
</div>
	<javascript>
		<script src="${CTX_ROOT}/resources/front/shop/goods/event/js/goodsEventList.js"></script>
	</javascript>
</body>
</html>