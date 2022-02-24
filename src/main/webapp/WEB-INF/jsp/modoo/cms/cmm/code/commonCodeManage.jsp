<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/modoo/common/commonTagLibs.jsp"%>
<c:set var="pageTitle" value="공통코드 관리"/>
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
			<div class="row">
				<div class="col-md-6">
					<form:form modelAttribute="searchVO" id="searchForm" name="searchForm" method="get" action="${CTX_ROOT }/decms/code/codeList.json">
						<fieldset>
							<form:hidden path="pageIndex"/>
						</fieldset>
						<div class="row">
							<div class="col-lg-8">
								<div class="form-row">
									<div class="col-4">
										<label for="searchCondition" class="sr-only">분류</label>
										<form:select path="searchCondition" cssClass="form-control custom-select custom-select-sm">
											<form:option value="NM">코드명</form:option>
										</form:select>
									</div>
									<div class="col-8">
										<div class="input-group input-group-sm">
											<form:input path="searchKeyword" cssClass="form-control form-control-sm"/>
											<span class="input-group-append">
												<button type="submit" class="btn btn-primary"><i class="fas fa-search"></i>검색</button>
											</span>
										</div>
									</div>
								</div>
							</div>
							<div class="col-lg-4">
								<div class="text-right">
									<c:url var="writeUrl" value="/decms/embed/code/writeCode.do"/>
									<a href="<c:out value="${writeUrl }"/>" class="btn btn-primary btn-sm btnAdd" data-target="#codeModal"><i class="fas fa-plus"></i> 신규등록</a>
								</div>
							</div>
						</div>
					</form:form>
					<div id="data-grid" class="mt-3"></div>
					<div id="data-grid-pagination" class="tui-pagination"></div>
				</div>
				<div class="col-md-6">
					<div class="row">
						<div class="col-sm-6">
							<h6>상세코드</h6>
						</div>
						<div class="col-sm-6 text-right">
							<a href="${CTX_ROOT }/decms/embed/code/writeCodeDetail.do" class="btn btn-dark btn-sm btnAddCodeDetail" data-target="#codeDetailModal"><i class="fas fa-plus"></i> 상세코드 등록</a>
						</div>
					</div>
					<hr class="sm"/>
					<div>
						<form:form modelAttribute="searchDetailVO" id="searchSubForm" name="searchSubForm" method="get" action="${CTX_ROOT }/decms/code/codeDetailList.json">
							<fieldset>
								<form:hidden path="pageIndex" id="subPageIndex" name="pageIndex"/>
								<form:hidden path="codeId"/>
							</fieldset>
						</form:form>
						<div id="data-sub-grid" class="mt-3"></div>
					</div>
				</div>
			</div>
		</div>
	</div>
	
	<div class="modal fade" id="codeModal" tabindex="-1" role="dialog" aria-labelledby="codeModalLabel" aria-hidden="true">
		<div class="modal-dialog modal-xl modal-dialog-centered modal-dialog-scrollable modal-dark">
			<div class="modal-content">
				<div class="modal-header">
					<h6 class="modal-title" id="codeModalLabel">수정</h6>
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
	<div class="modal fade" id="codeDetailModal" tabindex="-1" role="dialog" aria-labelledby="codeDetailModalLabel" aria-hidden="true">
		<div class="modal-dialog modal-xl modal-dialog-centered modal-dialog-scrollable modal-dark">
			<div class="modal-content">
				<div class="modal-header">
					<h6 class="modal-title" id="codeDetailModalLabel">수정</h6>
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
		<script src="${CTX_ROOT}/resources/lib/tui/tui-code-snippet/tui-code-snippet.min.js"></script>
		<script src="${CTX_ROOT}/resources/lib/tui/tui-pagination/tui-pagination.min.js"></script>
		<script src="${CTX_ROOT}/resources/lib/tui/tui-grid/tui-grid.min.js"></script>
		<script src="${CTX_ROOT}/resources/lib/tempusdominus-bootstrap-4/js/tempusdominus-bootstrap-4.min.js"></script>
		<script src="${CTX_ROOT}/resources/decms/code/commonCodeManage.js"></script>
		<script src="${CTX_ROOT}/resources/decms/code/commonCodeDetailManage.js"></script>
	</javascript>
</body>
</html>