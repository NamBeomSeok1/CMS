<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/modoo/common/commonTagLibs.jsp"%>
<c:set var="pageTitle" value="테마 관리"/>

<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>${pageTitle }</title>
	<link rel="stylesheet" type="text/css" href="${CTX_ROOT }/resources/lib/tui/tui-grid/tui-grid.min.css"/>
	<link rel="stylesheet" type="text/css" href="${CTX_ROOT }/resources/lib/tui/tui-pagination/tui-pagination.min.css"/>
	<link rel="stylesheet" type="text/css" href="${CTX_ROOT }/resources/lib/tempusdominus-bootstrap-4/css/tempusdominus-bootstrap-4.min.css"/>
	
	<style>
		.btn-area {
			float: right;
		}
	</style>
</head>
<body>
	<div class="card shadow page-wrapper">
		<div class="card-header">
			<h6 class="m-0 font-weight-bold text-primary">${pageTitle }</h6>
		</div>
		<div class="card-body">
			<div class="container-fluid">
			  <div class="row">
			  <div class="form-row">
			  		<input type="hidden" name="pageIndex" id="pageIndex">
					<div class="col-auto">
						<label for="searchPrtnrId" class="sr-only">제휴사</label>
						<select name="searchPrtnrId" class="custom-select custom-select-sm">
							<option value="">제휴사전체</option>
							<c:forEach var="prtnr" items="${prtnrList }">
								<option value="${prtnr.prtnrId }">${prtnr.prtnrNm}</option>
							</c:forEach>
						</select>
					</div>
					<div class="col-auto">
						<label for="searchEndAt" class="sr-only">구분</label>
						<select name="searchEndAt" class="custom-select custom-select-sm">
							<option value="">=전체=</option>
							<option value="N">진행중</option>
							<option value="Y">종료</option>
						</select>
					</div>
					<div class="col-auto">
						<div class="input-group input-group-sm">
							<div class="input-group-prepend">
								<select name="searchCondition" class="custom-select custom-select-sm rounded-0">
									<option value="">=분류선택=</option>
									<option value="SJ">테마 명</option>
								</select>
							</div>
							<input name="searchKeyword" class="form-control"/>
							<span class="input-group-append">
								<button type="submit" id="searchBtn" class="btn btn-primary"><i class="fas fa-search"></i> 검색</button>
							</span>
						</div>
					</div>
					<div class="col-auto btn-area">
						<c:url var="writeUrl" value="${CTX_ROOT}/decms/thema/themaForm.do"/>
						<a href="<c:out value="${writeUrl}"/>" class="btn btn-primary btn-area" ><i class="fas fa-plus"></i> 신규등록</a>
					</div>
				</div>
			  </div>
			  
			</div>
			
			<div id="data-grid" class="mt-3"></div>
			<div id="data-grid-pagination" class="tui-pagination"></div>
		</div>
	</div>
	
	
	<javascript>
		<script src="${CTX_ROOT}/resources/lib/tui/tui-code-snippet/tui-code-snippet.min.js"></script>
		<script src="${CTX_ROOT}/resources/lib/tui/tui-pagination/tui-pagination.min.js"></script>
		<script src="${CTX_ROOT}/resources/lib/tui/tui-grid/tui-grid.min.js"></script>
		<script src="${CTX_ROOT}/resources/lib/tempusdominus-bootstrap-4/js/tempusdominus-bootstrap-4.min.js"></script>
		<script src="${CTX_ROOT}/resources/lib/jquery-ui/jquery-ui.min.js"></script>
		<script src="${CTX_ROOT}/resources/decms/thema/js/themaManage.js?202012"></script>
	</javascript>
</body>
</html>