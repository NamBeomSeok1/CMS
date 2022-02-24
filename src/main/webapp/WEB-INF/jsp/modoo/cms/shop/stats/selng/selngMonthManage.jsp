<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/modoo/common/commonTagLibs.jsp"%>
<c:set var="pageTitle" value="월별 매출"/>
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
			<form:form modelAttribute="searchVO" id="searchForm" name="searchForm" method="get" action="${CTX_ROOT }/decms/shop/stats/selngMonthList.json">
				<fieldset class="cms-search-box container-fluid rounded pt-3 pb-3 mb-3">
					<div class="row mb-2">
						<div class="col-lg-8">
							<div class="form-row">
								<div class="col-auto form-inline">
									<div class="input-group input-group-sm">
										<select name="searchBgnYear" class="custom-select custom-select-sm">
											<c:forEach var="item" begin="2020" end="${currYear }">
												<option value="${item }" <c:if test="${item eq searchBgnYear }">selected="selected"</c:if>>${item }</option>
											</c:forEach>
										</select>
										<div class="input-group-append">
											<span class="input-group-text">년</span>
										</div>
									</div>
									<div class="input-group input-group-sm">
										<select name="searchBgnMonth" class="custom-select custom-select-sm">
											<c:forEach var="item" begin="1" end="12">
												<option value="<fmt:formatNumber pattern="00" value="${item}" />" <c:if test="${item eq searchBgnMonth }">selected="selected"</c:if>>${item }</option>
											</c:forEach>
										</select>
										<div class="input-group-append">
											<span class="input-group-text">월</span>
										</div>
									</div>
								</div>
								<div class="col-auto">~</div>
								<div class="col-auto form-inline">
									<div class="input-group input-group-sm">
										<select name="searchEndYear" class="custom-select custom-select-sm">
											<c:forEach var="item" begin="2020" end="${currYear }">
												<option value="${item }" <c:if test="${item eq searchEndYear }">selected="selected"</c:if>>${item }</option>
											</c:forEach>
										</select>
										<div class="input-group-append">
											<span class="input-group-text">년</span>
										</div>
									</div>
									<div class="input-group input-group-sm">
										<select name="searchEndMonth" class="custom-select custom-select-sm">
											<c:forEach var="item" begin="1" end="12">
												<option value="<fmt:formatNumber pattern="00" value="${item}" />" <c:if test="${item eq searchEndMonth }">selected="selected"</c:if>>${item }</option>
											</c:forEach>
										</select>
										<div class="input-group-append">
											<span class="input-group-text">월</span>
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
				</fieldset>
			</form:form>
			
			<div class="mt-2">
				<div id="chart1"></div>
				<div id="chart2"></div>
				<div class="text-right">
					<a href="${CTX_ROOT }/decms/shop/stats/selngMonthListExcel.do" id="btnExcelDownload" class="btn btn-success btn-sm"><i class="fas fa-file-excel"></i> 엑셀 다운로드</a>
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
		<script src="${CTX_ROOT}/resources/decms/shop/stats/selng/js/selngMonthManage.js"></script>
	</javascript>
</body>
</html>