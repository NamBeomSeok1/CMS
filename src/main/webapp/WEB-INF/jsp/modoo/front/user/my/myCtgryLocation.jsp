<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/modoo/common/commonTagLibs.jsp"%>
			
			 <div class="location">
                    <a href="/index.do"><strong>HOME</strong></a>
                    <a href="/user/my/mySubscribeNow.do">마이구독</a>
                   	<c:choose>
	                    <c:when test="${param.menuId eq'sbs'}">
	                    	<a href="#none">주문관리</a>
	                    </c:when>
	                    <c:when test="${param.menuId eq'bbs'}">
	                    	<a href="#none">게시판</a>
	                    </c:when>
	                    <c:when test="${param.menuId eq'cs'}">
	                    	<a href="#none">고객센터</a>
	                    </c:when>
	                    <c:when test="${param.menuId eq 'myInfo'}">
	                    	<a href="#none">내 정보</a>
	                    </c:when>
                   	</c:choose>
                   	<c:if test="${not empty param.subMenuId}">
                    	<a href="#none">${param.subMenuId}</a>
                    </c:if>
                </div>
                