<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/modoo/common/commonTagLibs.jsp"%>
<c:set var="pageTitle" value="교환/환불"/>
<!DOCTYPE html>
<html lang="ko">
<head>
    <title>${pageTitle}</title>
</head>
<body>
<div class="wrap">
	<c:set var="title" value="교환/환불"/>	
	<c:import url="/user/my/subMenu.do" charEncoding="utf-8">
		<c:param name="menuId" value="refund"/>
	</c:import>


	<div class="sub-contents">
		<c:import url="/user/my/myLocation.do" charEncoding="utf-8">
			<c:param name="menuId" value="refund"/>
		</c:import> 
		<c:import url="/user/my/userInfo.do" charEncoding="utf-8">
		</c:import>
	
		<section>
		    <div class="sub-tit-area">
		        <h3 class="sub-tit">${title}</h3>
		    </div>
		    <div class="table-type" id="table-refund">
		
		    </div>
		    <ul class="paging" id="paging-refund">
			</ul>
		</section>
	</div>
</div>


	<javascript>
		<script src="${CTX_ROOT}/resources/front/my/myRefund/myRefund.js"></script>
	</javascript>
</body>
</html>