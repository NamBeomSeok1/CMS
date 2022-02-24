<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/modoo/common/commonTagLibs.jsp"%>
<c:set var="pageTitle" value="FAQ 관리"/>
<c:choose>
	<c:when test="${searchVO.searchFaqClCode eq 'SITE' }"> 
		<c:set var="pageTitle" value="FAQ"/>
	</c:when>
	<c:when test="${searchVO.searchFaqClCode eq 'CP' }"> 
		<c:set var="pageTitle" value="CP FAQ"/>
	</c:when>
</c:choose>
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
			<form:form modelAttribute="searchVO" id="searchForm" name="searchForm" method="get" action="${CTX_ROOT }/decms/faq/faqList.json">
				<fieldset>
					<form:hidden path="pageIndex"/>
					<form:hidden path="searchFaqClCode"/>
				</fieldset>
				<div class="row">
					<div class="col-lg-6">
						<div class="form-row">
							<div class="col-auto">
								<label for="searchCondition" class="sr-only">분류</label>
								<form:select path="searchCondition" cssClass="custom-select custom-select-sm">
									<form:option value="">=분류선택=</form:option>
									<form:option value="QSJ">질문제목</form:option>
									<form:option value="QCN">질문내용</form:option>
									<form:option value="ACN">답변내용</form:option>
								</form:select>
							</div>
							<div class="col-auto">
								<div class="input-group">
									<form:input path="searchKeyword" cssClass="form-control form-control-sm"/>
									<span class="input-group-append">
										<button type="submit" class="btn btn-primary btn-sm"><i class="fas fa-search"></i>검색</button>
									</span>
								</div>
							</div>
						</div>
					</div>
					<div class="col-lg-6">
						<div class="text-right">
							<c:url var="writeUrl" value="/decms/embed/faq/writeFaq.do">
								<c:param name="faqClCode" value="${searchVO.searchFaqClCode }"/>
							</c:url>
							<a href="<c:out value="${writeUrl }"/>" class="btn btn-primary btn-sm btnAdd" data-target="#faqModal"><i class="fas fa-plus"></i> FAQ등록</a>
						</div>
					</div>
				</div>
			</form:form>
			<div id="data-grid" class="mt-3"></div>
			<div id="data-grid-pagination" class="tui-pagination"></div>
		</div>
	</div>
	
	<div class="modal fade" id="faqModal" tabindex="-1" role="dialog" aria-labelledby="faqModalLabel" aria-hidden="true">
		<div class="modal-dialog modal-xl modal-dialog-centered modal-dialog-scrollable modal-dark">
			<div class="modal-content">
				<div class="modal-header">
					<h6 class="modal-title" id="faqModalLabel">수정</h6>
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
		<script src="${CTX_ROOT}/resources/decms/faq/js/faqManage.js?2020"></script>
	</javascript>
</body>
</html>