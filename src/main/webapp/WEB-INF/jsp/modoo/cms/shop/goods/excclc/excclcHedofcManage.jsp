<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ include file="/WEB-INF/jsp/modoo/common/commonTagLibs.jsp"%>
<c:set var="pageTitle" value="이지웰정산"/>
<html>
<head>
	<meta charset="UTF-8">
	<title>${pageTitle }</title>
	<link rel="stylesheet" type="text/css" href="${CTX_ROOT}/resources/lib/jquery-ui/jquery-ui.min.css"/>
	<link rel="stylesheet" type="text/css" href="${CTX_ROOT}/resources/lib/tui/tui-grid/tui-grid.min.css"/>
	<link rel="stylesheet" type="text/css" href="${CTX_ROOT}/resources/lib/tui/tui-pagination/tui-pagination.min.css"/>
	<link rel="stylesheet" type="text/css" href="${CTX_ROOT}/resources/lib/tempusdominus-bootstrap-4/css/tempusdominus-bootstrap-4.min.css"/>
	<link rel="stylesheet" type="text/css" href="${CTX_ROOT}/resources/decms/shop/goods/excclc/css/excclcHedofcManage.css?"/>
</head>
<body>

	<div class="card shadow page-wrapper">
		<div class="card-header">
			<h6 class="m-0 font-weight-bold text-primary">${pageTitle }</h6>
		</div>
		<div class="card-body">
			<form:form modelAttribute="searchVO" id="searchForm" name="searchForm" method="get" action="${CTX_ROOT }/decms/shop/goods/excclcHedofcList.json">
				<fieldset>
					<form:hidden path="pageIndex"/>
					<input type="hidden" name="menuId" value="${param.menuId}"/>
				</fieldset>
				<fieldset class="cms-search-box container-fluid rounded pt-3 pb-3 mb-3">
					<div class="row mb-2">
						<div class="col-md-3">
							<div class="input-group input-group-sm">
								<div class="input-group-prepend"><span class="input-group-text">정산구분</span></div>
								<form:select path="searchPrtnrId" cssClass="custom-select custom-select-sm">
									<form:option value="">전체</form:option>
									<form:option value="PRTNR_0001">이지웰</form:option>
									<form:option value="PRTNR_0000">B2C</form:option>
								</form:select>
							</div>
						</div>
						<div class="col-md-4">
							<div class="input-group input-group-sm">
								<div class="input-group-prepend"><span class="input-group-text">입점사명</span></div>
								<input type="text" id="searchCmpnyNmResult" class="form-control form-control-sm" readonly="readonly" value="${cmpnyNm }"/>
								<input type="hidden" id="searchCmpnyId" name="searchCmpnyId"/>
								<button type="button" id="btnRemoveCmpnyInfo" class="btn btn-secondary btn-sm rounded-0"><i class="fas fa-times"></i></button>
								<input type="text" id="searchCmpnyNm" class="form-control form-control-sm" placeholder="업체명을 입력하세요"/>
								<div class="input-group-append">
									<button type="button" id="btnSearchCmpny" class="btn btn-dark btn-sm"><i class="fas fa-search"></i> 업체검색</button>
								</div>
							</div>
						</div>
						<div class="col-md-5">
							<div class="input-group input-group-sm">
								<div class="input-group-prepend"><span class="input-group-text">입점사ID</span></div>
								<input type="text" id="cmpnyUserId" class="form-control form-control-sm" readonly="readonly" value=""/>
							</div>
						</div>
					</div>
					<div class="row">
						<div class="col-md-3">
							<div class="input-group input-group-sm">
								<div class="input-group-prepend"><span class="input-group-text">정산기간</span></div>
								<form:select path="searchExcclcYear" cssClass="custom-select custom-select-sm">
									<c:forEach var="year" begin="${minYear }" end="${maxYear }">
										<form:option value="${year }">${year } 년</form:option>
									</c:forEach>
								</form:select>
								<form:select path="searchExcclcMonth" cssClass="custom-select custom-select-sm">
									<form:option value="">정산월선택</form:option>
									<c:forEach var="month" begin="1" end="12">
										<form:option value="${month }">${month } 월</form:option>
									</c:forEach>
								</form:select>
							</div>
						</div>
						<div class="col-md-4">
							<div class="input-group input-group-sm">
								<div class="input-group-prepend">
									<span class="input-group-text">정산상태</span>
								</div>
								<form:select path="searchExcclcSttusCode" cssClass="custom-select custom-select-sm">
									<form:option value="">전체</form:option>
									<c:forEach var="code" items="${excclcSttusCodeList }">
										<form:option value="${code.code }">${code.codeNm }</form:option>
									</c:forEach>
								</form:select>
							</div>
						</div>
						<div class="col-md-5 text-right">
							<button type="submit" class="btn btn-dark btn-sm"><i class="fas fa-search"></i> 검색</button>
						</div>
					</div>
				</fieldset>
				<fieldset class="cms-search-box container-fluid rounded pt-3 pb-3 mb-3">
					<div class="">
						<table class="excclc-sum">
							<colgroup>
								<col width="300">
								<col width="">
							</colgroup>
							<tbody>
								<tr>
									<th>총 주문금액 계</th>
									<td id="tot-setleTotAmount"></td>
								</tr>
								<tr>
									<th>수수료합계(5%)</th>
									<td id="tot-ezwelFeeAmount"></td>
								</tr>
								<tr>
									<th>Ez포인트 사용계</th>
									<td id="tot-setlePoint"></td>
								</tr>
								<tr>
									<th>적립금 사용계</th>
									<td id="tot-rsrvmney"></td>
								</tr>
								<tr>
									<th>쿠폰 사용계</th>
									<td id="">0</td>
								</tr>
								<tr>
									<th>상계금액(계산서 발행금액)</th>
									<td id="tot-bil"></td>
								</tr>
							</tbody>
						</table>
					</div>
				</fieldset>
			</form:form>
			<div class="mt-1">
				<div id="data-grid"></div>
				<div id="data-grid-pagination" class="tui-pagination"></div>
			</div>
			<div class="row">
				<div class="col-sm-6">
					<a href="${CTX_ROOT }/decms/shop/goods/ezwelExcclcExcelList.do" class="btnExcelDownload btn btn-success btn-sm"><i class="fas fa-file-excel "></i> 엑셀다운로드</a>
				</div>
			</div>
		</div>
	</div>
	
	<javascript>
		<script src="${CTX_ROOT}/resources/lib/tui/tui-code-snippet/tui-code-snippet.min.js"></script>
		<script src="${CTX_ROOT}/resources/lib/tui/tui-pagination/tui-pagination.min.js"></script>
		<script src="${CTX_ROOT}/resources/lib/tui/tui-grid/tui-grid.min.js"></script>
		<script src="${CTX_ROOT}/resources/lib/tempusdominus-bootstrap-4/js/tempusdominus-bootstrap-4.min.js"></script>
		<script src="${CTX_ROOT}/resources/lib/jquery-ui/jquery-ui.min.js"></script>
		<script src="${CTX_ROOT}/resources/decms/shop/goods/excclc/js/excclcHedofcManage.js?"></script>
	</javascript>
</body>
</html>