<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/modoo/common/commonTagLibs.jsp"%>
<script>
	var globalKeywords = [ <c:forEach var="keyword" items="${keywordList}" varStatus="status"> '${keyword.srchwrd}' <c:if test="${not status.last}">,</c:if> </c:forEach> ];
	var globalHitKeywords = [ <c:forEach var="keyword" items="${hitWrdList}" varStatus="status"> '${keyword.srchwrd}' <c:if test="${not status.last}">,</c:if> </c:forEach> ];
</script>