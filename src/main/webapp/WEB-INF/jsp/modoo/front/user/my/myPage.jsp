<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/modoo/common/commonTagLibs.jsp"%>
<!DOCTYPE html>
<html>
<head>
	<title>MY PAGE</title>
</head>
<body>
<c:import url="/user/my/subMenu.do" charEncoding="utf-8">
</c:import>
<div class="wrap">
	<div class="sub-contents">
		<c:import url="/user/my/myLocation.do" charEncoding="utf-8">
		</c:import> 
		<c:import url="/user/my/userInfo.do" charEncoding="utf-8">
		</c:import> 
	</div>
</div>
</body>
</html>