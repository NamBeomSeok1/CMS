<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/modoo/common/commonTagLibs.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Error</title>
</head>
<body>
<c:choose>
	<c:when test="${not empty errorMessage }">
		<script> alert('${errorMessage}'); </script>
		<c:if test="${not empty errorRedirectUrl }">
			<script>location.href = '${errorRedirectUrl}';</script>
		</c:if>
	</c:when>
	<c:otherwise>
		Error
	</c:otherwise>
</c:choose>
</body>
</html>