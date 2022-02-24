<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/modoo/common/commonTagLibs.jsp"%>
<c:set var="title" value="브랜드"/>
<!DOCTYPE html>
<html>
<head>
	<title>${title}</title>
</head>
<body>
<div class="wrap">
	<div class="sub-contents">
		<section>
			<div class="sub-tit-area">
				<h3 class="sub-tit">${title }</h3>
			</div>
			<ul>
				<c:forEach var="result" items="${resultList }" varStatus="status">
					<li><a href="${CTX_ROOT }/shop/goods/brandGoodsList.do?searchGoodsBrandId=${result.brandId}">${result.brandNm }</a></li>
				</c:forEach>
			</ul>
		</section>
	</div>
</div>
</body>
</html>