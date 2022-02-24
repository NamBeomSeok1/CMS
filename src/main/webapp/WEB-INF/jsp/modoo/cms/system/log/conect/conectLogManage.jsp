<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/modoo/common/commonTagLibs.jsp"%>
<c:set var="pageTitle" value="접속통계"/>
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
			<form:form modelAttribute="searchVO" id="searchForm" name="searchForm" method="get" action="${CTX_ROOT }/decms/system/log/conectLogCountList.json">
				<fieldset>
					<form:hidden path="pageIndex"/>
				</fieldset>
				<div class="row">
					<div class="col-lg-8">
						<div class="form-row">
							<div class="col-md-3 col-sm-3">
								<div class="input-group input-group-sm" id="datepicker-searchBgnde" data-target-input="nearest">
									<form:input path="searchBgnde" cssClass="form-control datetimepicker-input" data-target="#datepicker-searchBgnde"/>
									<div class="input-group-append" data-target="#datepicker-searchBgnde" data-toggle="datetimepicker">
										<div class="input-group-text"><i class="fas fa-calendar"></i></div>
									</div>
								</div>
							</div>
							<div class="col-md-3 col-sm-3">
								<div class="input-group input-group-sm" id="datepicker-searchEndde" data-target-input="nearest">
									<form:input path="searchEndde" cssClass="form-control datetimepicker-input" data-target="#datepicker-searchEndde"/>
									<div class="input-group-append" data-target="#datepicker-searchEndde" data-toggle="datetimepicker">
										<div class="input-group-text"><i class="fas fa-calendar"></i></div>
									</div>
								</div>
							</div>
							<div class="col-md-4 col-sm-6">
								<button type="submit" class="btn btn-primary btn-sm"><i class="fas fa-search"></i>검색</button>
							</div>
						</div>
					</div>
					<div class="col-lg-4 text-right">
						<a href="${CTX_ROOT }/decms/system/log/conectLogCountListExcel.do" id="btnExcelDownload" class="btn btn-success btn-sm" data-form="#searchForm">
							<i class="fas fa-file-excel"></i> 엑셀다운로드
						</a>
					</div>
				</div>
			</form:form>
			<div>
			<div class="row">
				<div class="col-6">
				  <h5 class="m-3 font-weight-bold text-primary">B2C</h5>
					<div id="B2C-data-grid" class="mt-3"></div>
					<div id="B2C-data-grid-pagination" class="tui-pagination"></div>	
				</div>
				<div class="col-6">
					<h5 class="m-3 font-weight-bold text-primary">이지웰</h5>
					<div id="B2B-data-grid" class="mt-3"></div>
					<div id="B2B-data-grid-pagination" class="tui-pagination"></div>	
				</div>
			</div>
		</div>
	</div>
	
	<javascript>
		<script src="${CTX_ROOT}/resources/lib/tui/tui-code-snippet/tui-code-snippet.min.js"></script>
		<script src="${CTX_ROOT}/resources/lib/tui/tui-pagination/tui-pagination.min.js"></script>
		<script src="${CTX_ROOT}/resources/lib/tui/tui-grid/tui-grid.min.js"></script>
		<script src="${CTX_ROOT}/resources/lib/tempusdominus-bootstrap-4/js/tempusdominus-bootstrap-4.min.js"></script>
		<script src="${CTX_ROOT}/resources/decms/system/log/conect/js/conectLogManage.js"></script>
	</javascript>
</body>
</html>