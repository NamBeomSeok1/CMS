<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
  <%@ include file="/WEB-INF/jsp/modoo/common/commonTagLibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
 <link rel="stylesheet" type="text/css" href="${CTX_ROOT}/resources/front/cmm/etc/sub.css?20201102">
 <link rel="stylesheet" type="text/css" href="${CTX_ROOT}/resources/front/site/${SITE_ID }/css/style.css?20201007"/> 
<script type="text/javascript"></script>
<title>${title}</title>
</head>
<body>
<div class="wrap">
	<div class="sub-contents">
	<h2 class="sub-tit">${title}</h2>
	   <div class="terms">${content}</div>
	</div>
</div>
</body>
</html>