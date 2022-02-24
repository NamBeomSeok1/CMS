<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 <%@ include file="/WEB-INF/jsp/modoo/common/commonTagLibs.jsp"%>
<!DOCTYPE html>
<html>
<head>
	<title>${title}</title>
</head>
<body>
<div class="wrap">
	<c:set var="title" value="내가 작성한 리뷰"/>	
	<c:import url="/user/my/subMenu.do" charEncoding="utf-8">
		<c:param name="menuId" value="bbs_review"/>
	</c:import>

	<div class="sub-contents">
		<c:import url="/user/my/myLocation.do" charEncoding="utf-8">
			<c:param name="menuId" value="bbs"/>
			<c:param name="subMenuId" value="${title}"/>
		</c:import> 
		 <c:import url="/user/my/userInfo.do" charEncoding="utf-8">
		</c:import> 
		<!-- 내가 작성한 리뷰 -->
		<section>
			<div class="sub-tit-area">
				<h3 class="sub-tit">${title}</h3>
			</div>
			<ul class="review-list">
			</ul>
			<ul class="paging" id="paging-review">
			</ul>
		</section>
	</div>
</div>


	<c:import url="${CTX_ROOT}/shop/goods/review/reviewView.do" charEncoding="utf-9"/>
	<c:import url="${CTX_ROOT}/shop/goods/review/reviewWrite.do" charEncoding="utf-9"/>
	<javascript>
		<script src="${CTX_ROOT}/resources/front/my/review/review.js?20201105"></script>
	</javascript>
</body>	
</html>