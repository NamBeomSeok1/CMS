<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/modoo/common/commonTagLibs.jsp"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title><c:out value="${popup.popupSj }"/></title>
	<link rel="stylesheet" type="text/css" href="${CTX_ROOT}/resources/common/css/default.css"/>
</head>
<body>
	<div>
		<c:out value="${popup.popupCn }" escapeXml="false"/>
	</div>
	<div class="popup-footer checkbox" style="position:fixed; width:100%; bottom:0; left:0; padding:5px 10px; background:rgba(255,255,255,0.2); font-size:0.8rem;">
		<label>
			<input type="checkbox" class="window-popup-today-check" value="${popup.popupId }"/>오늘 하루 열지 않기
		</label>
	</div>
	
	<script src="${CTX_ROOT}/resources/lib/jquery/jquery.min.js"></script>
	<script src="${CTX_ROOT}/resources/lib/js-cookie/js.cookie.js"></script>
	<script src="${CTX_ROOT}/resources/common/js/common.js"></script>
</body>
</html>