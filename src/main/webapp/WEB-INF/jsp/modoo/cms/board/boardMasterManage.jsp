<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/modoo/common/commonTagLibs.jsp"%>
<c:set var="pageTitle" value="게시판 관리"/>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>${pageTitle }</title>
	<link rel="stylesheet" type="text/css" href="${CTX_ROOT }/resources/lib/tui/tui-grid/tui-grid.min.css"/>
	<link rel="stylesheet" type="text/css" href="${CTX_ROOT }/resources/lib/tui/tui-pagination/tui-pagination.min.css"/>
</head>
<body>

	<div class="card shadow page-wrapper">
		<div class="card-header">
			<h6 class="m-0 font-weight-bold text-primary">${pageTitle }</h6>
		</div>
		<div class="card-body">
			<form:form modelAttribute="searchVO" id="searchForm" name="searchForm" method="get" action="${CTX_ROOT }/decms/board/master/boardMasterList.json">
				<fieldset>
					<form:hidden path="pageIndex"/>
				</fieldset>
				<div class="row">
					<div class="col-lg-6">
						<div class="form-row">
							<div class="col-auto">
								<label for="searchCondition" class="sr-only">분류</label>
								<form:select path="searchCondition" cssClass="custom-select custom-select-sm">
									<form:option value="">=분류선택=</form:option>
									<form:option value="NM">게시판명</form:option>
								</form:select>
							</div>
							<div class="col-auto">
								<div class="input-group input-group-sm">
									<form:input path="searchKeyword" cssClass="form-control form-control-sm"/>
									<span class="input-group-append">
										<button type="submit" class="btn btn-primary"><i class="fas fa-search"></i>검색</button>
									</span>
								</div>
							</div>
						</div>
					</div>
					<div class="col-lg-6">
						<div class="text-right">
							<c:url var="writeUrl" value="/decms/embed/board/master/writeBoardMaster.do"/>
							<a href="<c:out value="${writeUrl }"/>" class="btn btn-primary btn-sm btnAddBoard" data-target="#boardMasterModal"><i class="fas fa-plus"></i> 신규등록</a>
						</div>
					</div>
				</div>
			</form:form>
			<div id="data-grid" class="mt-3"></div>
		</div>
	</div>

	<div class="modal fade" id="boardMasterModal" tabindex="-1" role="dialog" aria-labelledby="boardMasterModalLabel" aria-hidden="true">
		<div class="modal-dialog modal-xl modal-dialog-centered modal-dialog-scrollable modal-dark">
			<div class="modal-content">
				<div class="modal-header">
					<h6 class="modal-title" id="boardMasterModalLabel">수정</h6>
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
		<script src="${CTX_ROOT}/resources/decms/board/js/boardMasterManage.js"></script>
	</javascript>
</body>
</html>