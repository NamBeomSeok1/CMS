<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/modoo/common/commonTagLibs.jsp"%>
<c:forEach var="brand" items="${brandMenuList}">
	<li>
		<cite>${brand.wrd }</cite>
		<div>
			<c:forEach var="item" items="${brand.goodsBrandList }">
				<a href="${CTX_ROOT }/shop/goods/brandGoodsList.do?searchGoodsBrandId=${item.brandId}">${item.brandNm }</a>
			</c:forEach>
		</div>
	</li>
</c:forEach>