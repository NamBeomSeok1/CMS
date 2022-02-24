<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/modoo/common/commonTagLibs.jsp"%>
<c:set var="pageTitle" value="업체 관리"/>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>${pageTitle }</title>
	<link rel="stylesheet" type="text/css" href="${CTX_ROOT }/resources/lib/tui/tui-grid/tui-grid.min.css"/>
	<link rel="stylesheet" type="text/css" href="${CTX_ROOT }/resources/lib/tui/tui-pagination/tui-pagination.min.css"/>
	<link rel="stylesheet" type="text/css" href="${CTX_ROOT }/resources/lib/tempusdominus-bootstrap-4/css/tempusdominus-bootstrap-4.min.css"/>
	
</head>
<body>

	<div class="card shadow page-wrapper">
		<div class="card-header">
			<h6 class="m-0 font-weight-bold text-primary">${pageTitle }</h6>
		</div>
		<div class="card-body">
			<form:form modelAttribute="searchVO" id="searchForm" name="searchForm" method="get" action="${CTX_ROOT }/decms/shop/cmpny/cmpnyList.json">
				<fieldset>
					<form:hidden path="pageIndex"/>
					<input type="hidden" name="menuId" value="${param.menuId }"/>
					<form:hidden path="searchCondition" value="NM"/>
				</fieldset>
				<div class="row">
					<div class="col-md-6">
						<div class="input-group input-group-sm">
							<div class="input-group-prepend">
								<span class="input-group-text">업체명</span>
							</div>
							<form:input path="searchKeyword" cssClass="form-control"/>
							<div class="input-group-append">
								<button type="submit" class="btn btn-dark"><i class="fas fa-search"></i> 검색</button>
							</div>
						</div>
					</div>
					<div class="col-md-6">
						<div class="form-row">
							<div class="col-md-2 text-right">
								<label for="" class="col-form-label">입점일 :</label>
							</div>
							<div class="col-md-4">
								<div class="input-group input-group-sm" id="datepicker-searchBgnde" data-target-input="nearest">
									<form:input path="searchBgnde" cssClass="form-control datetimepicker-input" data-target="#datepicker-searchBgnde"/>
									<div class="input-group-append" data-target="#datepicker-searchBgnde" data-toggle="datetimepicker">
										<div class="input-group-text"><i class="fas fa-calendar"></i></div>
									</div>
								</div>
							</div>
							<div class="col-md-1 text-center">
								<label class="col-form-label">~</label>
							</div>
							<div class="col-md-4">
								<div class="input-group input-group-sm" id="datepicker-searchEndde" data-target-input="nearest">
									<form:input path="searchEndde" cssClass="form-control datetimepicker-input" data-target="#datepicker-searchEndde"/>
									<div class="input-group-append" data-target="#datepicker-searchEndde" data-toggle="datetimepicker">
										<div class="input-group-text"><i class="fas fa-calendar"></i></div>
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>
			</form:form>
			<div id="data-grid" class="mt-3"></div>
			<div class="mt-3 text-right">
				<c:url var="writeUrl" value="/decms/shop/cmpny/writeCmpny.do">
					<c:param name="menuId" value="${param.menuId }"/>
				</c:url>
				<a href="<c:out value="${writeUrl }"/>" class="btn btn-primary btn-sm"><i class="fas fa-plus"></i> 업체등록</a>
			</div>
			<div id="data-grid-pagination" class="tui-pagination"></div>
			
			
		</div>
	</div>
	

	
	<javascript>
		<script src="${CTX_ROOT}/resources/lib/tui/tui-code-snippet/tui-code-snippet.min.js"></script>
		<script src="${CTX_ROOT}/resources/lib/tui/tui-pagination/tui-pagination.min.js"></script>
		<script src="${CTX_ROOT}/resources/lib/tui/tui-grid/tui-grid.min.js"></script>
		<script src="${CTX_ROOT}/resources/lib/tempusdominus-bootstrap-4/js/tempusdominus-bootstrap-4.min.js"></script>
		<script src="${CTX_ROOT}/resources/decms/shop/cmpny/js/cmpnyManage.js?20210115"></script>
	</javascript>
</body>
</html>