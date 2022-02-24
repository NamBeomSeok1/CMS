<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/modoo/common/commonTagLibs.jsp"%>
<c:set var="pageTitle" value="요일/시간대 별 매출"/>
<!DOCTYPE html>
<html>
<head>
	<title>${pageTitle }</title>
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
			<form:form modelAttribute="searchVO" id="searchForm" name="searchForm" method="get" action="${CTX_ROOT }/decms/shop/stats/selngWeekList.json">
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
					<div>
						<button type="button" class="btnDateSearch btn btn-secondary btn-sm" data-begin="${searchMonth6 }" data-end="${searchToday }">최근6개월</button>
						<button type="button" class="btnDateSearch btn btn-secondary btn-sm" data-begin="${searchMonth3 }" data-end="${searchToday }">최근3개월</button>
						<button type="button" class="btnDateSearch btn btn-secondary btn-sm" data-begin="${searchMonth2 }" data-end="${searchToday }">최근2개월</button>
						<button type="button" class="btnDateSearch btn btn-secondary btn-sm" data-begin="${searchMonth1 }" data-end="${searchToday }">최근1개월</button>
						<button type="button" class="btnDateSearch btn btn-secondary btn-sm" data-begin="${searchThisMonth01 }" data-end="${searchThisMonth02 }">이번달</button>
					</div>
				</fieldset>
			</form:form>
			
			<div class="mt-2">
				<div id="chart1"></div>
				<div id="chart2"></div>
				<div id="chart3"></div>
				<div id="chart4"></div>
			</div>

		</div>
	</div>
	<javascript>
		<script src="${CTX_ROOT}/resources/lib/tui/tui-code-snippet/tui-code-snippet.min.js"></script>
		<script src="${CTX_ROOT}/resources/lib/tui/tui-chart/tui-chart-all.min.js"></script>
		<script src="${CTX_ROOT}/resources/lib/tempusdominus-bootstrap-4/js/tempusdominus-bootstrap-4.min.js"></script>
		<script src="${CTX_ROOT}/resources/decms/shop/stats/selng/js/selngWeekManage.js"></script>
	</javascript>
</body>
</html>