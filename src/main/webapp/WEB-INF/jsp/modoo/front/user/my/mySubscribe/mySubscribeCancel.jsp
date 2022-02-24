<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/modoo/common/commonTagLibs.jsp"%>
<!DOCTYPE html>
<html>
<body>
<div class="wrap">
	<c:set var="title" value="주문취소"/>	
	<c:import url="/user/my/subMenu.do" charEncoding="utf-8">
		<c:param name="menuId" value="sbs_mySubscribeCancel"/>
	</c:import>
	<title>${title}</title>

	<div class="sub-contents">
		<c:import url="/user/my/myLocation.do" charEncoding="utf-8">
			<c:param name="menuId" value="sbs"/>
			<c:param name="subMenuId" value="${title }"/>
		</c:import> 
		 <c:import url="/user/my/userInfo.do" charEncoding="utf-8">
		</c:import>
	    <section>
	        <div class="sub-tit-area">
	            <h3 class="sub-tit">주문취소</h3>
	        </div>
	        <div class="table-type" id="table-sbs-cancel">
	        </div>
	        <ul class="paging" id="paging-sbs-cancel">
	        </ul>
	    </section>
	</div>
</div>


	<javascript>
		<script src="${CTX_ROOT}/resources/front/my/mySubscribe/mySubscribeCancel.js?20201116-1"></script>
	</javascript>
</body>
</html>