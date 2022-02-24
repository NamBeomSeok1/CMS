<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ include file="/WEB-INF/jsp/modoo/common/commonTagLibs.jsp"%>
<c:set var="pageTitle" value="CP정산내역상세"/>
<html>
<head>
	<meta charset="UTF-8">
	<title>${pageTitle }</title>
	<link rel="stylesheet" type="text/css" href="${CTX_ROOT}/resources/lib/jquery-ui/jquery-ui.min.css"/>
	<link rel="stylesheet" type="text/css" href="${CTX_ROOT}/resources/lib/tui/tui-grid/tui-grid.min.css"/>
	<link rel="stylesheet" type="text/css" href="${CTX_ROOT}/resources/lib/tui/tui-pagination/tui-pagination.min.css"/>
	<link rel="stylesheet" type="text/css" href="${CTX_ROOT}/resources/lib/tempusdominus-bootstrap-4/css/tempusdominus-bootstrap-4.min.css"/>
	<link rel="stylesheet" type="text/css" href="${CTX_ROOT}/resources/decms/shop/goods/excclc/css/excclcCpManage.css?"/>
	<style>
		.tui-grid-cell-header {
			padding: 0px;
			font-size: 11px !important;
		}
	</style>
</head>
<body>

	<div class="card shadow page-wrapper">
		<div class="card-header">
			<h6 class="m-0 font-weight-bold text-primary">${pageTitle }</h6>
		</div>
		
		<div class="card-body">
			<form:form modelAttribute="searchVO" id="searchForm" name="searchForm" method="get" action="${CTX_ROOT }/decms/shop/goods/excclcCpList.json">
				<fieldset>
					<form:hidden path="pageIndex"/>
					<input type="hidden" name="menuId" value="${param.menuId}"/>
				</fieldset>
				<fieldset class="cms-search-box container-fluid rounded pt-3 pb-3 mb-3">
					<div class="row">
						<div class="col-md-7">
							<div class="form-row mb-2">
								<div class="col-md-6">
									<div class="input-group input-group-sm">
										<div class="input-group-prepend"><span class="input-group-text">정산구분</span></div>
										<form:select path="searchPrtnrId" cssClass="custom-select custom-select-sm">
											<form:option value="">전체</form:option>
											<form:option value="PRTNR_0001">이지웰</form:option>
											<form:option value="PRTNR_0000">B2C</form:option>
										</form:select>
									</div>
								</div>
								<div class="col-md-6">
									<div class="input-group input-group-sm">
										<div class="input-group-prepend"><span class="input-group-text">입점사명</span></div>
										<input type="text" id="searchCmpnyNmResult" class="form-control form-control-sm" readonly="readonly" value="${cmpnyNm }"/>
										<c:if test="${EXCCLC_MODE eq 'MNG' }">
											<input type="hidden" id="searchCmpnyId" name="searchCmpnyId"/>
											<button type="button" id="btnRemoveCmpnyInfo" class="btn btn-secondary btn-sm rounded-0"><i class="fas fa-times"></i></button>
											<input type="text" id="searchCmpnyNm" class="form-control form-control-sm" placeholder="업체명을 입력하세요"/>
											<div class="input-group-append">
												<button type="button" id="btnSearchCmpny" class="btn btn-dark btn-sm"><i class="fas fa-search"></i> 업체검색</button>
											</div>
										</c:if>
									</div>
								</div>
							</div>
							<div class="form-row mb-2">
								<div class="col-md-6">
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
										<form:select path="searchExcclcSePd" cssClass="custom-select custom-select-sm">
											<form:option value="">정산기간선택</form:option>
											<form:option value="PD01" data-info="익월 초 4~8일">당월 1일 ~ 말일</form:option>
											<form:option value="PD02" data-info="당월 18일">당월 1일 ~ 15일</form:option>
											<form:option value="PD03" data-info="익월 3일">당월 16일 ~ 말일</form:option>
										</form:select>
									</div>
								</div>
								<c:if test="${EXCCLC_MODE eq 'MNG' }">
									<div class="col-md-6">
										<div class="input-group input-group-sm">
											<div class="input-group-prepend"><span class="input-group-text">세금계산서 제출기간</span></div>
											<input type="text" id="taxbilInfo" class="form-control form-control-sm" readonly="readonly"/>
										</div>
									</div>
								</c:if>
							</div>
							
						</div>
						<div class="col-md-5">
							<c:if test="${EXCCLC_MODE eq 'MNG' }">
								<div class="mb-2">
									<div class="input-group input-group-sm">
										<div class="input-group-prepend"><span class="input-group-text">입점사ID</span></div>
										<input type="text" id="cmpnyUserId" class="form-control form-control-sm" readonly="readonly" value=""/>
										<%-- <form:input path="searchCmpnyUserId" cssClass="form-control form-control-sm" placeholder="업체사용자ID"/> --%>
									</div>
								</div>
							</c:if>
							<div class="mb-2">
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
							<div class="text-right">
								<button type="submit" class="btn btn-dark btn-sm"><i class="fas fa-search"></i> 검색</button>
							</div>
						</div>
					</div>
				</fieldset>
				<fieldset class="cms-search-box container-fluid rounded pt-3 pb-3 mb-3">
					<table class="excclc-info">
						<tr>
							<th>정산기준일</th>
							<td id="excclcStdrDe"></td>
							
							<c:if test="${EXCCLC_MODE eq 'MNG' }">
								<th>세금계산서 제출기간</th>
								<td id="taxbilDe"></td>
							</c:if>

							<th>A. 총 결제금액</th>
							<td class="setleTotAmount">0</td>

							<th>B. 판매 수수료 금액 합계</th>
							<td class="setleFeeTot">0</td>
							
							<th>C. 업체 정산금액 합계</th>
							<td class="cmpnyExcclcTot">0</td>
						</tr>
					</table>

				</fieldset>
				<fieldset class="cms-search-box container-fluid rounded pt-3 pb-3 mb-3">
					<table class="excclc-sum">
						<tbody>
							<tr>
								<th>카드결제 계</th>
								<td id="setleCardAmount">0</td>
								<th>판매수수료 공급가</th>
								<td id="setleFeeSplpc">0</td>
								<th>적립금 계</th>
								<td id="rsrvmney">0</td>
								<th>업체 정산금액</th>
								<td id="cmpnyExcclcAmount">0</td>
							</tr>
							<tr>
								<th>Ez포인트결제 계</th>
								<td id="setlePoint">0</td>
								<th>판매수수료 부가세</th>
								<td id="setleFeeVat">0</td>
								<th>쿠폰 할인 계</th>
								<td id="couponDscnt">0</td>
								<th>정산금액 부가세</th>
								<td id="cmpnyExcclcVat">0</td>
							</tr>
							<tr>
								<th rowspan="2">총 결제금액</th>
								<td rowspan="2" class="setleTotAmount">0</td>
								<th rowspan="2">판매수수료 금액 합계</th>
								<td rowspan="2" class="setleFeeTot">0</td>
								<th>이벤트 할인 계</th>
								<td id="eventDscnt">0</td>
								<th rowspan="2">업체 정산금액 합계</th>
								<td rowspan="2" class="cmpnyExcclcTot">0</td>
							</tr>
							<tr>
								<th>이벤트 합계</th>
								<td id="eventTot">0</td>
							</tr>
						</tbody>
					</table>
					
				</fieldset>
			</form:form>
			<div class="mt-1">
				<div class="row">
					<div class="col-md-6"></div>
					<div class="col-md-6"></div>
				</div>
				<div id="data-grid"></div>
				<div id="data-grid-pagination" class="tui-pagination"></div>
				<div class="row">
					<div class="col-sm-6">
						<a href="${CTX_ROOT }/decms/shop/goods/excclcExcelList.do" class="btnExcelDownload btn btn-success btn-sm"><i class="fas fa-file-excel "></i> 엑셀다운로드</a>
					</div>
					<div class="col-sm-6 text-right">
						<c:if test="${EXCCLC_MODE eq 'MNG' }">
							<button type="button" class="btnExcclcChange btn btn-primary btn-sm" data-code="CPE03">정산하기</button>
							<button type="button" class="btnExcclcChange btn btn-warning btn-sm" data-code="CPE02">정산보류</button>
						</c:if>
					</div>
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
		<c:choose>
		<c:when test="${EXCCLC_MODE eq 'MNG' }"> <%--관리자 javascript --%>
			<c:choose>
			<c:when test="${param.menuId eq 'excclcCpManage' }">
				<script src="${CTX_ROOT}/resources/decms/shop/goods/excclc/js/excclcManage.js?20201218"></script>
			</c:when>
			<c:when test="${param.menuId eq 'excclcCpDetailManage' }">
				<script src="${CTX_ROOT}/resources/decms/shop/goods/excclc/js/excclcCpManage.js?20201218"></script>
			</c:when>
			</c:choose>
		</c:when>
		<c:otherwise> <%--CP javascript --%>
			<script src="${CTX_ROOT}/resources/decms/shop/goods/excclc/js/cpExcclcManage.js?20201215"></script>
		</c:otherwise>
		</c:choose>
	</javascript>
</body>
</html>