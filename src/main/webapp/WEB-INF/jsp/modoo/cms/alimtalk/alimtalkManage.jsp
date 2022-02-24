<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/modoo/common/commonTagLibs.jsp"%>
<c:set var="pageTitle" value="알림톡 발송내역"/>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>${pageTitle }</title>
	<link rel="stylesheet" type="text/css" href="${CTX_ROOT }/resources/lib/tui/tui-grid/tui-grid.min.css"/>
	<link rel="stylesheet" type="text/css" href="${CTX_ROOT }/resources/lib/tui/tui-pagination/tui-pagination.min.css"/>
	<link rel="stylesheet" type="text/css" href="${CTX_ROOT }/resources/lib/tempusdominus-bootstrap-4/css/tempusdominus-bootstrap-4.min.css"/>
	
	<style>
		#banner-img {display:inline-block; border:2px dotted #afafaf; width:300px; height:80px;}
		#banner-m-img {display:inline-block; border:2px dotted #afafaf; width:300px; height:80px;}
		.dfkAllCheckBox {padding:2px 4px; border:1px solid #bbb; background:#eee;}
	</style>
</head>
<body>

	<div class="card shadow page-wrapper">
		<div class="card-header">
			<h6 class="m-0 font-weight-bold text-primary">${pageTitle }</h6>
		</div>
		<div class="card-body">
<%-- 			<div class="search-form" <c:if test="${searchVO.menuId eq 'todaysPickManage' or searchVO.menuId eq 'indPickManage'}">style="display:none;"</c:if>> --%>
<%-- 				<form:form modelAttribute="searchVO" id="searchForm" name="searchForm" method="get" action="${CTX_ROOT }/decms/banner/bannerList.json"> --%>
<!-- 					<fieldset> -->
<%-- 						<form:hidden path="pageIndex"/> --%>
<%-- 						<form:hidden path="menuId"/> --%>
<!-- 					</fieldset> -->
<!-- 					<div class="row mb-2"> -->
<!-- 						<div class="col-lg-8"> -->
<!-- 							<div class="form-row"> -->
<!-- 								<div class="col-auto"> -->
<!-- 									<label for="searchPrtnrId" class="sr-only">제휴사</label> -->
<%-- 									<form:select path="searchPrtnrId" cssClass="custom-select custom-select-sm"> --%>
<%-- 										<form:option value="">제휴사전체</form:option> --%>
<%-- 										<form:option value="PRTNR_0000">B2C</form:option> --%>
<%-- 										<form:option value="PRTNR_0001">이지웰</form:option> --%>
<%-- 									</form:select> --%>
<!-- 								</div> -->
<!-- 								<div class="col-auto"> -->
<!-- 									<label for="searchSeCode" class="sr-only">구분</label> -->
<%-- 									<form:select path="searchSeCode" cssClass="custom-select custom-select-sm"> --%>
<%-- 										<form:option value="">=구분=</form:option> --%>
<%-- 										<c:forEach var="code" items="${bannerSeCodeList }"> --%>
<%-- 											<form:option value="${code.code }">${code.codeNm }</form:option> --%>
<%-- 										</c:forEach> --%>
<%-- 									</form:select> --%>
<!-- 								</div> -->
<!-- 								<div class="col-auto"> -->
<!-- 									<div class="input-group input-group-sm"> -->
<!-- 										<div class="input-group-prepend"> -->
<%-- 											<form:select path="searchCondition" cssClass="custom-select custom-select-sm rounded-0"> --%>
<%-- 												<form:option value="">=분류선택=</form:option> --%>
<%-- 												<form:option value="SJ">제목</form:option> --%>
<%-- 											</form:select> --%>
<!-- 										</div> -->
<%-- 										<form:input path="searchKeyword" cssClass="form-control"/> --%>
<!-- 										<span class="input-group-append"> -->
<!-- 											<button type="submit" class="btn btn-primary"><i class="fas fa-search"></i> 검색</button> -->
<!-- 										</span> -->
<!-- 									</div> -->
<!-- 								</div> -->
<!-- 							</div> -->
<!-- 						</div> -->
<!-- 						<div class="col-lg-4"> -->
<!-- 							<div class="text-right"> -->
<%-- 								<c:url var="writeUrl" value="/decms/embed/banner/writeBanner.do"/> --%>
<%-- 								<a href="<c:out value="${writeUrl }"/>" class="btn btn-primary btn-sm btnAdd" data-target="#bannerModal"><i class="fas fa-plus"></i> 신규등록</a> --%>
<!-- 							</div> -->
<!-- 						</div> -->
<!-- 					</div> -->
<!-- 					<div class="row"> -->
<!-- 						<div class="col-md-2"> -->
<!-- 							<div class="input-group input-group-sm"> -->
<!-- 								<div class="input-group-prepend"> -->
<!-- 									<span class="input-group-text">목록</span> -->
<!-- 								</div> -->
<%-- 								<form:select path="pageUnit" cssClass="custom-select custom-select-sm"> --%>
<%-- 									<form:option value="10">10개</form:option> --%>
<%-- 									<form:option value="20">20개</form:option> --%>
<%-- 									<form:option value="30">30개</form:option> --%>
<%-- 								</form:select> --%>
<!-- 							</div> -->
<!-- 						</div> -->
<!-- 					</div> -->
<%-- 				</form:form> --%>
<!-- 			</div> -->
<!-- 			<div id="data-grid" class="mt-3"></div> -->
<!-- 			<div id="data-grid-pagination" class="tui-pagination"></div> -->
		</div>
	</div>
	
	<javascript>
		<script src="${CTX_ROOT}/resources/lib/tui/tui-code-snippet/tui-code-snippet.min.js"></script>
		<script src="${CTX_ROOT}/resources/lib/tui/tui-pagination/tui-pagination.min.js"></script>
		<script src="${CTX_ROOT}/resources/lib/tui/tui-grid/tui-grid.min.js"></script>
		<script src="${CTX_ROOT}/resources/lib/tempusdominus-bootstrap-4/js/tempusdominus-bootstrap-4.min.js"></script>
		<script src="${CTX_ROOT}/resources/lib/jquery-ui/jquery-ui.min.js"></script>
		<script src="${CTX_ROOT}/resources/decms/banner/js/bannerManage.js?202012"></script>
		<script src="${CTX_ROOT}/resources/decms/shop/goods/info/js/goodsFormRecomendList.js?202012"></script>
	</javascript>
</body>
</html>