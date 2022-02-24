<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/modoo/common/commonTagLibs.jsp"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="utf-8"/>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<meta http-equiv="X-UA-Compatible" content="IE=edge" />
    <meta name="viewport" content="width=device-width,initial-scale=1.0" /> 
    <link rel="canonical" href="${pageContext.request.scheme}://${pageContext.request.serverName}${pageContext.request.contextPath}${requestScope['javax.servlet.forward.request_uri']}"/>
	<meta name="robots" content="index, follow"/>
	<meta name="description" content="FOXEDU STORE"/>
	<meta property="og:type" content="website"/>
	<meta property="og:title" content="FOXEDU STORE ${pageTitle }"/>
	<meta property="og:description" content="FOXEDU STORE"/>
	<title>이미지보기</title>
	<link rel="stylesheet" type="text/css" href="${CTX_ROOT}/resources/front/site/${SITE_ID }/css/style.css?20201119"/>

</head>
<body>
	<div class="image-content">
		<img src="${imageUrl }" />
		<a id="btnCloseWindow" class="btn-close-image" href="#none">이미지 닫기</a>
	</div>

	<script src="${CTX_ROOT}/resources/lib/jquery/jquery.min.js"></script>
	<script>

		$(document).on('click', '#btnCloseWindow', function(e) {
			e.preventDefault();
			self.close();
		});
		
	</script>
</body>
</html>