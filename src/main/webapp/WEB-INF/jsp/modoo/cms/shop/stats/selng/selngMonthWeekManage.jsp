<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/modoo/common/commonTagLibs.jsp"%>
<c:set var="pageTitle" value="주별 매출"/>
<!DOCTYPE html>
<html>
<head>
	<title>${pageTitle }</title>
	<link rel="stylesheet" type="text/css" href="${CTX_ROOT }/resources/lib/tui/tui-grid/tui-grid.min.css"/>
	<link rel="stylesheet" type="text/css" href="${CTX_ROOT }/resources/lib/tui/tui-chart/tui-chart.min.css"/>
	<link rel="stylesheet" type="text/css" href="${CTX_ROOT }/resources/lib/tempusdominus-bootstrap-4/css/tempusdominus-bootstrap-4.min.css"/>
	<style>
		.cms-search-box {border:1px solid #dfdfdf; border-left:0.3rem solid #5a5c69;box-shadow: 0 .15rem 1.75rem 0 rgba(58,59,69,.15)!important;}
	</style>
</head>
<body>
	<div class="card shadow page-wrapper">
		<div class="card-header">
			<h6 class="m-0 font-weight-bold text-primary">${pageTitle }</h6>
		</div>
		<div class="card-body">
			<form:form modelAttribute="searchVO" id="searchForm" name="searchForm" method="get" action="${CTX_ROOT }/decms/shop/stats/selngMonthWeekList.json">
				<fieldset class="cms-search-box container-fluid rounded pt-3 pb-3 mb-3">
					<div class="row mb-2">
						<div class="col-lg-8">
							<div class="form-row">
								<div class="col-auto">
									<div class="input-group input-group-sm" id="datepicker-searchBgnde" data-target-input="nearest">
										<form:input path="searchBgnde" cssClass="form-control datetimepicker-input" data-target="#datepicker-searchBgnde"/>
										<div class="input-group-append" data-target="#datepicker-searchBgnde" data-toggle="datetimepicker">
											<div class="input-group-text"><i class="fas fa-calendar"></i></div>
										</div>
									</div>
								</div>
								<div class="col-auto">~</div>
								<div class="col-auto">
									<div class="input-group input-group-sm" id="datepicker-searchEndde" data-target-input="nearest">
										<form:input path="searchEndde" cssClass="form-control datetimepicker-input" data-target="#datepicker-searchEndde"/>
										<div class="input-group-append" data-target="#datepicker-searchEndde" data-toggle="datetimepicker">
											<div class="input-group-text"><i class="fas fa-calendar"></i></div>
										</div>
									</div>
								</div>
								<div class="col-auto">
									<button type="submit" class="btn btn-dark btn-sm"><i class="fas fa-search"></i> 검색</button>
								</div>
							</div>
						</div>
						<div class="col-lg-4">
						</div>
					</div>
					<div class="row">
						<div class="col-sm-2">
							<div class="input-group input-group-sm">
								<div class="input-group-prepend"><span class="input-group-text">기간선택</span></div>
								<select id="searchWeek" class="custom-select custom-select-sm">
									<option value="">선택하세요.</option>
									<option value="1" data-begin="${searchWeek11 }" data-end="${searchWeek12 }">최근 1주전</option>
									<option value="2" data-begin="${searchWeek21 }" data-end="${searchWeek22 }">최근 2주전</option>
									<option value="3" data-begin="${searchWeek31 }" data-end="${searchWeek32 }">최근 3주전</option>
									<option value="4" data-begin="${searchWeek41 }" data-end="${searchWeek42 }">최근 4주전</option>
								</select>
							</div>
						</div>
					</div>
				</fieldset>
			</form:form>
			
			<div class="mt-2">
				<div id="chart1"></div>
				<div id="chart2"></div>
				<div class="text-right">
					<a href="${CTX_ROOT }/decms/shop/stats/selngMonthWeekListExcel.do" id="btnExcelDownload" class="btn btn-success btn-sm"><i class="fas fa-file-excel"></i> 엑셀 다운로드</a>
				</div>
				<div id="data-grid" class="mt-3"></div>
			</div>

		</div>
	</div>
	<javascript>
		<script src="${CTX_ROOT}/resources/lib/tui/tui-code-snippet/tui-code-snippet.min.js"></script>
		<script src="${CTX_ROOT}/resources/lib/tui/tui-grid/tui-grid.min.js"></script>
		<script src="${CTX_ROOT}/resources/lib/tui/tui-chart/tui-chart-all.min.js"></script>
		<script src="${CTX_ROOT}/resources/lib/tempusdominus-bootstrap-4/js/tempusdominus-bootstrap-4.min.js"></script>
		<script src="${CTX_ROOT}/resources/decms/shop/stats/selng/js/selngMonthWeekManage.js"></script>
	</javascript>
</body>
</html>