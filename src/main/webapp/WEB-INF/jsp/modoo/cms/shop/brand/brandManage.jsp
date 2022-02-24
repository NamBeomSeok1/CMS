<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ include file="/WEB-INF/jsp/modoo/common/commonTagLibs.jsp"%>
<c:set var="pageTitle" value="브랜드 관리"/>
<html>
<head>
	<meta charset="UTF-8">
	<title>${pageTitle }</title>
	<link rel="stylesheet" type="text/css" href="${CTX_ROOT }/resources/lib/tui/tui-grid/tui-grid.min.css"/>
	<link rel="stylesheet" type="text/css" href="${CTX_ROOT }/resources/lib/tui/tui-pagination/tui-pagination.min.css"/>
	<link rel="stylesheet" type="text/css" href="${CTX_ROOT }/resources/lib/tempusdominus-bootstrap-4/css/tempusdominus-bootstrap-4.min.css"/>
	<style>
		 select {
			 font-family: 'Font Awesome 5 Free', 'Noto Sans KR'
			 }
	</style>
</head>
<body>

	<div class="card shadow page-wrapper">
		<div class="card-header">
			<h6 class="m-0 font-weight-bold text-primary">${pageTitle }</h6>
		</div>
		<div class="card-body">
			<form:form modelAttribute="searchVO" id="searchForm" name="searchForm" method="get" action="${CTX_ROOT }/decms/shop/goods/goodsBrandList.json">
				<fieldset>
					<form:hidden path="pageIndex"/>
					<input type="hidden" name="menuId" value="${param.menuId}"/>
				</fieldset>
				<div class="row">
					<div class="col-md-6">
						<div class="input-group input-group-sm">
							<div class="input-group-prepend">
								<form:select path="searchCondition" cssClass="custom-select custom-select-sm">
									<form:option value="NM">브랜드명</form:option>
									<form:option value="CNM">업체명</form:option>
								</form:select>
							</div>
							<form:input path="searchKeyword" cssClass="form-control"/>
							<div class="input-group-append">
								<button type="submit" class="btn btn-dark"><i class="fas fa-search"></i> 검색</button>
							</div>
						</div>
					</div>
					<div class="offset-md-4 col-md-2">
						<div class="form-row">
							<div class="input-group input-group-sm">
								<div class="input-group-prepend">
									<span class="input-group-text">정렬</span>
								</div>
								<form:select path="searchOrderField" cssClass="custom-select custom-select-sm">
									<form:option value="">=선택=</form:option>
									<form:option value="S_BRAND_NM_UP">브랜드명 ▲</form:option>
									<form:option value="S_BRAND_NM_DOWN">브랜드명 ▼</form:option>
								</form:select>
							</div>
						</div> 
					</div>
				</div>
			</form:form>
			<div id="data-grid" class="mt-3"></div>
			<div class="mt-3 text-right">
				<c:url var="writeUrl" value="/decms/shop/goods/writeBrand.do">
					<c:param name="menuId" value="${param.menuId }"/>
				</c:url>
				<a href="${writeUrl}" class="btn btn-primary btn-sm"><i class="fas fa-plus"></i> 브랜드등록</a>
			</div>
			<div id="data-grid-pagination" class="tui-pagination"></div>
		</div>
	</div>
	
	<javascript>
		<script src="${CTX_ROOT}/resources/lib/tui/tui-code-snippet/tui-code-snippet.min.js"></script>
		<script src="${CTX_ROOT}/resources/lib/tui/tui-pagination/tui-pagination.min.js"></script>
		<script src="${CTX_ROOT}/resources/lib/tui/tui-grid/tui-grid.min.js"></script>
		<script src="${CTX_ROOT}/resources/lib/tempusdominus-bootstrap-4/js/tempusdominus-bootstrap-4.min.js"></script>
		<script src="${CTX_ROOT}/resources/decms/shop/brand/js/brandManage.js?20201124"></script>
		<%-- <script src="${CTX_ROOT}/resources/decms/shop/goods/brand/js/goodsBrandManage.js"></script> --%>
	</javascript>
</body>
</html>