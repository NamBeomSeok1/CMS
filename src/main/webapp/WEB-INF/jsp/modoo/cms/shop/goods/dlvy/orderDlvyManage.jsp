<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/modoo/common/commonTagLibs.jsp"%>
<c:set var="pageTitle" value="주문 현황"/>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>${pageTitle}</title>
<link rel="stylesheet" type="text/css" href="${CTX_ROOT}/resources/lib/jquery-ui/jquery-ui.min.css"/>
<link rel="stylesheet" type="text/css" href="${CTX_ROOT}/resources/lib/tui/tui-grid/tui-grid.min.css"/>
<link rel="stylesheet" type="text/css" href="${CTX_ROOT}/resources/lib/tui/tui-pagination/tui-pagination.min.css"/>
<link rel="stylesheet" type="text/css" href="${CTX_ROOT}/resources/lib/tempusdominus-bootstrap-4/css/tempusdominus-bootstrap-4.min.css"/>
<style>
	.goods-info-card {text-align:center; border:1px solid #dfdfdf; box-shadow: 0 .15rem 1.75rem 0 rgba(58,59,69,.15)!important;padding:0.5rem;}
	.goods-info-card .alert-heading {font-size:0.9rem; font-weight:bold;}
	.goods-info-card p {font-size:0.8rem;}
	
	.card-blue {border-left:0.3rem solid #4e73df;}
	.card-blue .alert-heading {color:#4e73df;}
	.card-green {border-left:0.3rem solid #1cc88a;}
	.card-green .alert-heading {color:#1cc88a;}
	
 	.card-red {border-left:0.3rem solid #e74a3b;}
 	.card-red .alert-heading {color:#e74a3b;}
 	.card-yellow {border-left:0.3rem solid #f6c23e;}
 	.card-yellow .alert-heading {color:#f6c23e;}
	
	.goods-tab {}
	.goods-tab .nav-link {font-size:0.8rem; color:#afafaf;}
	.goods-tab .nav-link:hover {color:#666;}
	
	.goods-search-box {border:1px solid #dfdfdf; border-left:0.3rem solid #5a5c69;box-shadow: 0 .15rem 1.75rem 0 rgba(58,59,69,.15)!important;}
	
/* 	.cmpny-complete {background: #f6c23e61 !important;} */
</style>
</head>
<body>
	<div class="card shadow page-wrapper">
		<div class="card-header">
			<h6 class="m-0 font-weight-bold text-primary">${pageTitle}</h6>
		</div>
		<div class="card-body">
		<div class="row">
			<div class="col-sm-2">
				<div class="alert alert-light goods-info-card card-yellow" role="alert">
					<h5 class="alert-heading">전체</h5>
					<hr class="sm"/>
					<p class="mb-1" id="p-cnt-1">0건</p>
				</div>
			</div>
			<div class="col-sm-2">
				<div class="alert alert-light goods-info-card card-yellow" role="alert">
					<h5 class="alert-heading">결제완료</h5>
					<hr class="sm"/>
					<p class="mb-1" id="p-cnt-2">0건</p>
				</div>
			</div>
			<div class="col-sm-2">
				<div class="alert alert-light goods-info-card card-yellow" role="alert">
					<h5 class="alert-heading">상품준비중</h5>
					<hr class="sm"/>
					<p class="mb-1" id="p-cnt-3">0건</p>
				</div>
			</div>
			<div class="col-sm-2">
				<div class="alert alert-light goods-info-card card-yellow" role="alert">
					<h5 class="alert-heading">배송준비중</h5>
					<hr class="sm"/>
					<p class="mb-1" id="p-cnt-4">0건</p>
				</div>
			</div>
			<div class="col-sm-2">
				<div class="alert alert-light goods-info-card card-yellow" role="alert">
					<h5 class="alert-heading">배송중</h5>
					<hr class="sm"/>
					<p class="mb-1" id="p-cnt-5">0건</p>
				</div>
			</div>
			<div class="col-sm-2">
				<div class="alert alert-light goods-info-card card-yellow" role="alert">
					<h5 class="alert-heading">배송완료</h5>
					<hr class="sm"/>
					<p class="mb-1" id="p-cnt-6">0건</p>
				</div>
			</div>
		</div>
		<!-- 검색 -->
		<form:form modelAttribute="searchVO" id="searchForm" name="searchForm" method="get" action="${CTX_ROOT }/decms/shop/goods/getOrderStatusList.json">
			<fieldset>
				<form:hidden path="pageIndex"/>
				<input type="hidden" name="menuId" value="${param.menuId }"/>
			</fieldset>
			<fieldset class="goods-search-box container-fluid rounded pt-3 pb-3 mb-3">
				<div class="row">
					<div class="col-sm-5">
						<div class="mt-1">
							<div class="input-group input-group-sm">
								<div class="input-group-prepend">
									<span class="input-group-text">주문번호</span>
								</div>
								<form:input path="searchOrderNo" cssClass="form-control"/>
							</div>
						</div>
						<div class="mt-1">
							<div class="input-group input-group-sm">
								<div class="input-group-prepend">
									<span class="input-group-text">카테고리</span>
								</div>
								<select name="searchGoodsCtgryId1" class="custom-select custom-select-sm selectCategory" data-dp="1">
								</select>
								<select name="searchGoodsCtgryId2" class="custom-select custom-select-sm selectCategory" data-dp="2">
								</select>
								<select name="searchGoodsCtgryId3" class="custom-select custom-select-sm selectCategory" data-dp="3">
								</select>
							</div>
						</div>
						<div class="mt-1">
							<div class="input-group input-group-sm">
								<div class="input-group-prepend">
									<span class="input-group-text">결제상태</span>
								</div>
								<form:select path="searchSetleSttusCode" cssClass="custom-select custom-select-sm">
									<form:option value="">전체</form:option>
									<c:forEach var="code" items="${setleSttusCodeList}">
										<c:choose>
											<c:when test="${code.code eq 'S'}">
												<form:option value="${code.code}" selected="selected">${code.codeNm}</form:option>
											</c:when>
											<c:otherwise>
												<form:option value="${code.code}">${code.codeNm}</form:option>
											</c:otherwise>
										</c:choose>
									</c:forEach>
								</form:select>
							</div>
						</div>
						<div class="mt-1">
							<div class="input-group input-group-sm">
								<div class="input-group-prepend">
									<span class="input-group-text">주문구분</span>
								</div>
								<form:select path="searchOrderKndCode" cssClass="custom-select custom-select-sm">
									<form:option value="">전체</form:option>
									<form:option value="GNR">일반</form:option>
									<form:option value="SBS">구독</form:option>
									<form:option value="CPN">쿠폰</form:option>
<%-- 									<c:forEach var="code" items="${orderSttusCodeList}"> --%>
<%-- 										<c:choose> --%>
<%-- 											<c:when test="${code.code eq 'ST02'}"> --%>
<%-- 												<form:option value="${code.code}" selected="selected">${code.codeNm}</form:option> --%>
<%-- 											</c:when> --%>
<%-- 											<c:when test="${code.code eq 'ST01'}"> --%>
<%-- <%-- 												<form:option value="${code.code}" selected="selected">${code.codeNm}</form:option> --%> --%>
<%-- 											</c:when> --%>
<%-- 											<c:otherwise> --%>
<%-- 												<form:option value="${code.code}">${code.codeNm}</form:option> --%>
<%-- 											</c:otherwise> --%>
<%-- 										</c:choose> --%>
<%-- 									</c:forEach> --%>
								</form:select>
							</div>
						</div>
					</div>
					<div class="col-sm-5">
						<div class="mt-1">
							<div class="input-group input-group-sm">
								<div class="input-group-prepend">
									<span class="input-group-text">상품명</span>
								</div>
								<form:input path="searchGoodsNm" cssClass="form-control"/>
							</div>
						</div>
						<div class="row">
							<div class="col-sm-6">
								<div class="mt-1">
									<div class="input-group input-group-sm" id="datepicker-searchBgnde" data-target-input="nearest">
										<input name="searchBgnde" class="form-control datetimepicker-input" data-target="#datepicker-searchBgnde"/>
										<div class="input-group-append" data-target="#datepicker-searchBgnde" data-toggle="datetimepicker">
											<div class="input-group-text"><i class="fas fa-calendar"></i></div>
										</div>
									</div>
								</div>
							</div>
							<div class="col-sm-6">
								<div class="mt-1">
									<div class="input-group input-group-sm" id="datepicker-searchEndde" data-target-input="nearest">
										<input name="searchEndde" class="form-control datetimepicker-input" data-target="#datepicker-searchEndde"/>
										<div class="input-group-append" data-target="#datepicker-searchEndde" data-toggle="datetimepicker">
											<div class="input-group-text"><i class="fas fa-calendar"></i></div>
										</div>
									</div>
								</div>
							</div>
						</div>
						<div class="mt-1">
							<div class="input-group input-group-sm">
								<div class="input-group-prepend">
									<span class="input-group-text">배송상태</span>
								</div>
								<form:select path="searchDlvySttusCode" cssClass="custom-select custom-select-sm">
									<form:option value="">전체</form:option>
									<c:forEach var="code" items="${dlvySttusCodeList}">
										<form:option value="${code.code}">${code.codeNm}</form:option>
									</c:forEach>
								</form:select>
							</div>
						</div>
						<c:if test="${fn:contains(USER_ROLE,'ROLE_ADMIN')}">
							<div class="mt-1">
								<div class="input-group input-group-sm">
									<div class="input-group-prepend">
										<span class="input-group-text">업체명</span>
									</div>
									<form:input path="searchCmpnyNm" cssClass="form-control"/>
								</div>
							</div>
						</c:if>
					</div>
					<div class="col-sm-2">
						<button type="submit" id="searchOrderDlvyBtn" class="btn btn-dark btn-block"><i class="fas fa-search"></i> 검색</button>
					</div>
				</div>				
			</fieldset>
		</form:form>

		<!-- 현황 -->
		<div class="row">
			<div class="col-sm-6">
				<div class="alert alert-light goods-info-card card-blue" role="alert">
					<h5 class="alert-heading">주문 건수</h5>
					<hr class="sm"/>
					<p class="mb-1" id="p-order-cnt">0건</p>
				</div>
			</div>
			<div class="col-sm-6">
				<div class="alert alert-light goods-info-card card-green" role="alert">
					<h5 class="alert-heading">판매 금액</h5>
					<hr class="sm"/>
					<p class="mb-1" id="p-order-sum">0원</p>
				</div>
			</div>
		</div>
		<div class="mt-1">
			<div class="row mb-1">
				<div class="col-sm-6">
				</div>
				<div class="col-sm-6 text-right">
					<a href="#none" onclick="location.reload(true);" class="btn btn-info btn-sm">배송상태 새로고침</a>
					<a href="${CTX_ROOT }/decms/shop/goods/getOrderStatusListExcel.do" class="btn btn-success btn-sm btnExcelDownload"><i class="fas fa-file-excel"></i> 엑셀다운로드</a>
				</div>
			</div>
			<div id="data-grid"></div>
			<div id="data-grid-pagination" class="tui-pagination"></div>
		</div>
	</div>
	<c:import url="${CTX_ROOT}/decms/shop/goods/orderDlvyHist.do"></c:import>
	<javascript>
		<script src="${CTX_ROOT}/resources/lib/tui/tui-code-snippet/tui-code-snippet.min.js"></script>
		<script src="${CTX_ROOT}/resources/lib/tui/tui-pagination/tui-pagination.min.js"></script>
		<script src="${CTX_ROOT}/resources/lib/tui/tui-grid/tui-grid.min.js"></script>
		<script src="${CTX_ROOT}/resources/lib/tempusdominus-bootstrap-4/js/tempusdominus-bootstrap-4.min.js"></script>
		<script src="${CTX_ROOT}/resources/lib/jquery-ui/jquery-ui.min.js"></script>
		<script src="${CTX_ROOT}/resources/decms/shop/goods/dlvy/js/orderDlvyManage.js?20210202"></script>
	</javascript>
</body>
</html>