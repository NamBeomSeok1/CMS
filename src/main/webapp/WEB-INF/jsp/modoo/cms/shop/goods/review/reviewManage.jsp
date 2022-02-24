<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/modoo/common/commonTagLibs.jsp"%>
<c:set var="pageTitle" value="상품평관리"/>
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
			<form:form modelAttribute="searchVO" id="searchForm" name="searchForm" method="get" action="${CTX_ROOT }/decms/shop/goods/review/reviewList.json">
				<fieldset>
					<form:hidden path="pageIndex"/>
					<input type="hidden" name="menuId" value="${param.menuId }"/>
				</fieldset>
				<div class="form-row">
					<div class="col-sm-6 mb-1">
						<div class="input-group input-group-sm">
							<div class="input-group-prepend">
								<span class="input-group-text">상품평</span>
							</div>
							<form:input path="searchCommentCn" cssClass="form-control form-control-sm" placeholder="구매자가 쓴 글 내용"/>
						</div>
					</div>
					<div class="col-sm-6 mb-1">
						<div class="input-group input-group-sm">
							<div class="input-group-prepend">
								<span class="input-group-text">상품명</span>
							</div>
							<form:input path="searchGoodsNm" cssClass="form-control form-control-sm" placeholder="제품의 이름"/>
						</div>
					</div>
					<div class="col-sm-6 mb-1">
						<div class="input-group input-group-sm">
							<div class="input-group-prepend">
								<span class="input-group-text">ID</span>
							</div>
							<form:input path="searchWrterId" cssClass="form-control form-control-sm"/>
						</div>
					</div>
					<div class="col-sm-6 mb-1">
						<div class="row">
							<div class="col-sm-5">
								<div class="input-group input-group-sm" id="datepicker-searchBgnde" data-target-input="nearest">
									<form:input path="searchBgnde" cssClass="form-control datetimepicker-input" data-target="#datepicker-searchBgnde" placeholder="시작일"/>
									<div class="input-group-append" data-target="#datepicker-searchBgnde" data-toggle="datetimepicker">
										<div class="input-group-text"><i class="fas fa-calendar"></i></div>
									</div>
								</div>
							</div>
							<div class="col-sm-2 text-center">~</div>
							<div class="col-sm-5">
								<div class="input-group input-group-sm" id="datepicker-searchEndde" data-target-input="nearest">
									<form:input path="searchEndde" cssClass="form-control datetimepicker-input" data-target="#datepicker-searchEndde" placeholder="종료일"/>
									<div class="input-group-append" data-target="#datepicker-searchEndde" data-toggle="datetimepicker">
										<div class="input-group-text"><i class="fas fa-calendar"></i></div>
									</div>
								</div>
							</div>
						</div>
					</div>
					<div class="col-sm-6 mb-1">
						<div class="input-group input-group-sm">
							<div class="input-group-prepend">
								<span class="input-group-text">이름</span>
							</div>
							<form:input path="searchWrterNm" cssClass="form-control form-control-sm"/>
						</div>
					</div>
					<div class="col-sm-3 mb-1">
						<div class="input-group input-group-sm">
							<div class="input-group-prepend">
								<span class="input-group-text">평점</span>
							</div>
							<form:select path="searchScore" cssClass="custom-select custom-select-sm">
								<form:option value="">선택</form:option>
								<form:option value="1">1 점</form:option>
								<form:option value="2">2 점</form:option>
								<form:option value="3">3 점</form:option>
								<form:option value="4">4 점</form:option>
								<form:option value="5">5 점</form:option>
							</form:select>
						</div>
					</div>
					<div class="col-sm-3 mb-1 text-right">
						<button type="submit" class="btn btn-dark btn-sm"><i class="fas fa-search"></i> 검색</button>
					</div>
					<%-- <div class="col-md-6">
						<div class="input-group input-group-sm">
							<div class="input-group-prepend">
								<span class="input-group-text">업체명</span>
							</div>
							<form:input path="searchKeyword" cssClass="form-control"/>
							<div class="input-group-append">
								<button type="submit" class="btn btn-dark"><i class="fas fa-search"></i> 검색</button>
							</div>
						</div>
					</div> --%>
				</div>
			</form:form>
			<div id="data-grid" class="mt-3"></div>
			<div id="data-grid-pagination" class="tui-pagination"></div>
			
			
		</div>
	</div>
	

	
	<javascript>
		<script src="${CTX_ROOT}/resources/lib/tui/tui-code-snippet/tui-code-snippet.min.js"></script>
		<script src="${CTX_ROOT}/resources/lib/tui/tui-pagination/tui-pagination.min.js"></script>
		<script src="${CTX_ROOT}/resources/lib/tui/tui-grid/tui-grid.min.js"></script>
		<script src="${CTX_ROOT}/resources/lib/tempusdominus-bootstrap-4/js/tempusdominus-bootstrap-4.min.js"></script>
		<script src="${CTX_ROOT}/resources/decms/shop/goods/review/js/reviewManage.js"></script>
	</javascript>
</body>
</html>