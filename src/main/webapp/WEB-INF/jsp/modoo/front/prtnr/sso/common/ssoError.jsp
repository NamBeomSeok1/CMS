<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/modoo/common/commonTagLibs.jsp"%>
<!DOCTYPE html>
<html>
<head>
	<title>회원</title>
</head>
<body>
	<c:choose>
		<c:when test="${errCode eq 'ERR01'}"> <%-- session에 값이 없을 때 --%>
			<p>
				회원 연동에 문제가 발생했습니다.
			</p>
			<a href="${CTX_ROOT }/index.do" class="btn-lg width spot">메인페이지 바로가기</a>
		</c:when>
		<c:otherwise>
		</c:otherwise>
	</c:choose>
</body>
</html>