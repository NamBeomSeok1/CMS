<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/modoo/common/commonTagLibs.jsp"%>
<c:set var="pageTitle" value="게시물통계"/>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>${pageTitle }</title>
	<link rel="stylesheet" type="text/css" href="${CTX_ROOT }/resources/lib/tui/tui-grid/tui-grid.min.css"/>
	<link rel="stylesheet" type="text/css" href="${CTX_ROOT }/resources/lib/tui/tui-chart/tui-chart.min.css"/>
	<link rel="stylesheet" type="text/css" href="${CTX_ROOT }/resources/lib/tempusdominus-bootstrap-4/css/tempusdominus-bootstrap-4.min.css"/>
</head>
<body>

	<div class="card shadow page-wrapper">
		<div class="card-header">
			<h6 class="m-0 font-weight-bold text-primary">${pageTitle }</h6>
		</div>
		<div class="card-body">
			<form:form modelAttribute="searchVO" id="searchForm" name="searchForm" method="get" action="${CTX_ROOT }/decms/system/sts/bst/bbsSummaryList.json">
				<fieldset>
					<form:hidden path="pageIndex"/>
					<form:hidden path="statsSe"/>
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
						<a href="${CTX_ROOT }/decms/system/sts/bst/bbsSummaryListExcel.do" id="btnExcelDownload" class="btn btn-success btn-sm" data-form="#searchForm">
							<i class="fas fa-file-excel"></i> 엑셀다운로드
						</a>
					</div>
				</div>
			</form:form>
			<div id="chart1"></div>
			<div id="data-grid" class="mt-3"></div>
		</div>
	</div>
	
	<javascript>
		<script src="${CTX_ROOT}/resources/lib/tui/tui-code-snippet/tui-code-snippet.min.js"></script>
		<script src="${CTX_ROOT}/resources/lib/tui/tui-grid/tui-grid.min.js"></script>
		<script src="${CTX_ROOT}/resources/lib/tui/tui-chart/tui-chart-all.min.js"></script>
		<script src="${CTX_ROOT}/resources/lib/tempusdominus-bootstrap-4/js/tempusdominus-bootstrap-4.min.js"></script>
		<script src="${CTX_ROOT}/resources/decms/system/sts/bst/bbsSummary.js"></script>
	</javascript>
</body>
</html>