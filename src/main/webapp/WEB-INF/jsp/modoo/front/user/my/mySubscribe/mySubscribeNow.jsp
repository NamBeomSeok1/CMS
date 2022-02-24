<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/modoo/common/commonTagLibs.jsp"%>

	<div class="wrap">
		<c:set var="title" value="주문확인"/>	
		<title>${title}</title>
		<c:import url="/user/my/subMenu.do" charEncoding="utf-8">
			<c:param name="menuId" value="sbs_mySubscribeNow"/>
		</c:import>
	
		<div class="sub-contents">
			<c:import url="/user/my/myLocation.do" charEncoding="utf-8">
				<c:param name="menuId" value="sbs"/>
				<c:param name="subMenuId" value="${title}"/>
			</c:import> 
			 <c:import url="/user/my/userInfo.do" charEncoding="utf-8">
			</c:import>
			<section>
			    <div class="sub-tit-area">
			        <h3 class="sub-tit">주문확인</h3>
			    </div>
		    	<div class="table-type" id="table-sbs-now">
		
		    	</div>
				<ul class="paging" id="paging-sbs-now">
		
				</ul>
		    </section>
		</div>
	</div>


	<c:import url="${CTX_ROOT}/shop/goods/review/reviewWrite.do" charEncoding="utf-9"/>
	<c:import url="${CTX_ROOT}/shop/goods/review/reviewView.do" charEncoding="utf-9"/>
	<javascript>
		<script src="${CTX_ROOT}/resources/front/my/mySubscribe/mySubscribeNow.js"></script>
	</javascript>