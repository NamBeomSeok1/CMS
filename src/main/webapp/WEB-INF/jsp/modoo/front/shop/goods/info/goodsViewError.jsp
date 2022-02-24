<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/modoo/common/commonTagLibs.jsp"%>
<!DOCTYPE html>
<html>
<head>
	<meta proerty="og:title" content="${goods.goodsNm}">
	<meta proerty="og:description" content="${goods.goodsIntrcn}">
	<c:forEach var="imgItem" items="${goods.goodsImageList }">
	<meta proerty="og:image" content="${imgItem.goodsLrgeImagePath }">
	</c:forEach>
	<title>FOXEDU STORE</title>
</head>
<body>
<div class="wrap">
	<div class="sub-contents">
		
	</div>
</div>

	<c:if test="${ERR_CODE eq 'NONE' }">
		<div class="popup-alert" data-popup="popupGoodsError">
			<div class="pop-body">
				<p class="pop-message">등록된 상품이 없거나 품절되었습니다.</p>
			</div>
			<div class="pop-footer">
				<button type="button" class="spot2 button-link" data-src="${CTX_ROOT }/index.do">메인페이지로 이동</button>
			</div>
		</div>
	</c:if>
	<javascript>
		<script>
			$(document).ready(function() {
				popOpen('popupGoodsError');
			});
		</script>
	</javascript>
</body>
</html>