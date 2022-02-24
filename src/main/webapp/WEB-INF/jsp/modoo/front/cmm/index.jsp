<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/modoo/common/commonTagLibs.jsp"%>
<!DOCTYPE html>
<html>
<head>
	<title>Index</title>
</head>
<body>
	index page
	<div>
		<div>
		USER : ${USER_ID } - ${USER_NAME }
		</div>
		<c:choose>
			<c:when test="${empty USER }">
				<a href="${CTX_ROOT }/user/sign/loginUser.do">LOGIN</a>
			</c:when>
			<c:otherwise>
				<a href="${CTX_ROOT }/user/sign/logout.do">LOGOUT</a>
				<br/>
				<a href="${CTX_ROOT }/decms/index.do">CMS</a>
			</c:otherwise>
		</c:choose>
	</div>
	
	<div>
		<c:forEach var="item" items="${roleList }">
			<div><c:out value="${item }"/></div>
		</c:forEach>
	</div>
	
	<%-- principal : <sec:authentication property="principal"/>

principal.username : <sec:authentication property="principal.username"/>

principal.password : <sec:authentication property="principal.password"/>


principal.enabled : <sec:authentication property="principal.enabled"/>

principal.accountNonExpired : <sec:authentication property="principal.accountNonExpired"/> --%>



<javascript>
	<script src="${CTX_ROOT }/resources/front/site/${SITE_ID }/js/site.js"></script>
</javascript>

</body>
</html>
