<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
 <%@ include file="/WEB-INF/jsp/modoo/common/commonTagLibs.jsp"%>
<!DOCTYPE html>
<html>
<head>
	<title>${title}</title>
</head>
<body>
<div class="wrap">
	<c:import url="${CTX_ROOT}/shop/goods/review/reviewWrite.do" charEncoding="utf-9"/>
	<c:set var="title" value="작성 가능한 리뷰"/>	
	<c:import url="/user/my/subMenu.do" charEncoding="utf-8">
		<c:param name="menuId" value="bbs_todo"/>
	</c:import>

	<div class="sub-contents">
		<c:import url="/user/my/myLocation.do" charEncoding="utf-8">
			<c:param name="menuId" value="bbs"/>
			<c:param name="subMenuId" value="${title}"/>
		</c:import> 
		 <c:import url="/user/my/userInfo.do" charEncoding="utf-8">
		</c:import> 
		<!-- 작성 가능한 리뷰 -->
		<section>
			<div class="sub-tit-area">
			    <h3 class="sub-tit">${title}<span style="text-align: left; padding-left: 10px;">상품 당 하나의 주문건에만 리뷰를 작성할 수 있습니다.</span></h3>
			</div>
			<div class="table-type" id="tbody-review-todo">
	
			</div>
			<ul class="paging" id="paging-review-todo">
			</ul>
			</section>
	</div>
</div>


	<javascript>
		<script src="${CTX_ROOT}/resources/front/my/review/reviewTodo.js?20201110"></script>
	</javascript>
</body>
</html>