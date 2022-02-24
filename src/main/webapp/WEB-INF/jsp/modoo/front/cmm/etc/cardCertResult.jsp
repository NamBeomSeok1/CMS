<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/modoo/common/commonTagLibs.jsp"%>
<!DOCTYPE html>

<html>
<head>
	<meta http-equiv="Content-Script-Type" content="text/javascript">
	<meta http-equiv="Content-Style-Type" content="text/css">
	<meta http-equiv="X-UA-Compatible" content="IE=Edge, chrome=1"/>
	<meta name="viewport" content="width=device-width,initial-scale=1.0,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no">
</head>
<body onunload="closeWindow();">
	<c:choose>
		<c:when test="${result eq 'true'}">
			<script>
				close();
				opener.cardCertResult(true);
			</script>
		</c:when>
		<c:when test="${result eq 'false'}">
			<script>
				close();
				opener.cardCertResult(false);
			</script>
		</c:when>
		<c:when test="${result eq 'cancle'}">
			<script>
				close();
				opener.cardCertResult('close');
			</script>
		</c:when>
	</c:choose>
</body>
</html>