<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/modoo/common/commonTagLibs.jsp"%>
<c:set var="pageTitle" value="회원 관리"/>
<c:choose>
	<c:when test="${param.searchUserType eq 'USER' }"><c:set var="pageTitle" value="일반회원 관리"/></c:when>
	<c:when test="${param.searchUserType eq 'SHOP' }"><c:set var="pageTitle" value="업체회원 관리"/></c:when>
	<c:when test="${param.searchUserType eq 'ADMIN' }"><c:set var="pageTitle" value="관리자 관리"/></c:when>
</c:choose>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>${pageTitle } </title>
	<link rel="stylesheet" type="text/css" href="${CTX_ROOT }/resources/lib/tui/tui-grid/tui-grid.min.css"/>
	<link rel="stylesheet" type="text/css" href="${CTX_ROOT }/resources/lib/tui/tui-pagination/tui-pagination.min.css"/>
</head>
<body>

	<div class="card shadow page-wrapper">
		<div class="card-header">
			<h6 class="m-0 font-weight-bold text-primary">${pageTitle}</h6>
		</div>
		<div class="card-body">
			<form:form modelAttribute="searchVO" id="searchForm" name="searchForm" method="get" action="${CTX_ROOT }/decms/mber/mberList.json">
				<fieldset>
					<form:hidden path="searchUserType"/>
					<form:hidden path="pageIndex"/>
				</fieldset>
				<div class="row">
					<div class="col-lg-6">
						<div class="form-row">
							<div class="col-auto">
								<label for="searchCondition" class="sr-only">분류</label>
								<form:select path="searchCondition" cssClass="custom-select custom-select-sm">
									<form:option value="">=분류선택=</form:option>
									<form:option value="NM">이름</form:option>
									<form:option value="ID">ID</form:option>
								</form:select>
							</div>
							<c:if test="${param.searchUserType eq 'USER' }">
								<div class="col-auto">
									<label for="searchCondition" class="sr-only">제휴사</label>
									<form:select path="searchGroupId" cssClass="custom-select custom-select-sm">
										<form:option value="">=제휴사=</form:option>
										<form:option value="B2C">B2C</form:option>
										<form:option value="B2B">이지웰</form:option>
									</form:select>
								</div>
							</c:if>
							<div class="col-auto">
								<div class="input-group input-group-sm">
									<form:input path="searchKeyword" cssClass="form-control"/>
									<span class="input-group-append">
										<button type="submit" class="btn btn-primary"><i class="fas fa-search"></i>검색</button>
									</span>
								</div>
							</div>
							<p>회원 수:
								<span id="totalCnt"></span>
							</p>
						</div>
					</div>
					<div class="col-lg-6">
						<div class="text-right">
							<c:url var="writeUrl" value="/decms/embed/mber/writeMber.do">
								<c:param name="searchUserType" value="${param.searchUserType }"/>
							</c:url>
							<a href="<c:out value="${writeUrl }"/>" class="btn btn-primary btn-sm btnAddMber" data-target="#mberModal"><i class="fas fa-plus"></i> 신규등록</a>
						</div>
					</div>
				</div>
			</form:form>
			<div id="data-grid" class="mt-3"></div>
			<div id="data-grid-pagination" class="tui-pagination"></div>
		</div>
	</div>
	
	<div class="modal fade" id="mberModal" tabindex="-1" role="dialog" aria-labelledby="mberModalLabel" aria-hidden="true">
		<div class="modal-dialog modal-xl modal-dialog-centered modal-dialog-scrollable modal-dark">
			<div class="modal-content">
				<div class="modal-header">
					<h6 class="modal-title" id="mberModalLabel">수정</h6>
					<button type="button" class="close" data-dismiss="modal" aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
				</div>
				<div class="modal-body">
					<div class="text-center p-4 modal-spinner">
						<i class="fas fa-spinner fa-spin"></i> Loading...
					</div>
				</div>
			</div>
		</div>
	</div>
	
	<javascript>
		<script src="${CTX_ROOT }/resources/lib/tui/tui-code-snippet/tui-code-snippet.min.js"></script>
		<script src="${CTX_ROOT }/resources/lib/tui/tui-pagination/tui-pagination.min.js"></script>
		<script src="${CTX_ROOT}/resources/lib/tui/tui-grid/tui-grid.min.js"></script>
		<script src="${CTX_ROOT}/resources/decms/mber/js/mberManage.js"></script>
	</javascript>
</body>
</html>