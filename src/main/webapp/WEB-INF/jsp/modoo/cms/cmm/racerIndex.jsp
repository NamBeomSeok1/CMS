<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/modoo/common/commonTagLibs.jsp"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>modoo CMS</title>
	<link rel="stylesheet" type="text/css" href="${CTX_ROOT}/resources/lib/jquery-ui/jquery-ui.min.css"/>
	<link rel="stylesheet" type="text/css" href="${CTX_ROOT}/resources/lib/tui/tui-grid/tui-grid.min.css"/>
	<link rel="stylesheet" type="text/css" href="${CTX_ROOT}/resources/lib/tui/tui-pagination/tui-pagination.min.css"/>
	<link rel="stylesheet" type="text/css" href="${CTX_ROOT}/resources/lib/tempusdominus-bootstrap-4/css/tempusdominus-bootstrap-4.min.css"/>
	<style>
		.btn-area {
			float: right;
		}
	</style>
</head>
<body>
<fieldset class="container-fluid rounded pt-3 pb-3 mb-3">
<div class="col-sm-6">
	<h5 class="card-title">순위 기간</h5>
	<div class="col-sm-4">
		<div class="mt-1">
			<div class="input-group input-group-sm" id="datepicker-searchBgnde" data-target-input="nearest">
				<input name="searchBgnde" class="form-control datetimepicker-input" data-target="#datepicker-searchBgnde" placeholder="시작일" value="${filter.frstPnttm}"/>
				<div class="input-group-append" data-target="#datepicker-searchBgnde" data-toggle="datetimepicker">
					<div class="input-group-text"><i class="fas fa-calendar"></i></div>
				</div>
			</div>
		</div>
	</div>
	&nbsp ~
	<div class="col-sm-4">
		<div class="input-group input-group-sm" id="datepicker-searchEndde" data-target-input="nearest">
			<input name="searchEndde" class="form-control datetimepicker-input" data-target="#datepicker-searchEndde" placeholder="종료일" value="${filter.lastPnttm}"/>
			<div class="input-group-append" data-target="#datepicker-searchEndde" data-toggle="datetimepicker">
				<div class="input-group-text"><i class="fas fa-calendar"></i></div>
			</div>
		</div>
	</div>
	<div class="col-sm-4">
		<div class="mt-2">
			<div class="input-group input-group-sm">
				<div class="input-group-prepend">
					<span class="input-group-text">1~10 이름</span>
				</div>
				<select id="dplctAt" class="custom-select custom-select-sm">
					<c:if test="${empty filter}">
						<option value="Y" selected>중복허용</option>
						<option value="N">중복허용X</option>
					</c:if>
					<c:if test="${!empty filter}">
						<c:choose>
							<c:when test="${filter.dplctAt eq 'Y'}">
								<option value="Y" selected>중복허용</option>
								<option value="N">중복허용X</option>
							</c:when>
							<c:otherwise>
								<option value="Y" >중복허용</option>
								<option value="N" selected>중복허용X</option>
							</c:otherwise>
						</c:choose>
					</c:if>
				</select>
			</div>
			<a href="javascript: addFilter();" class="btn btn-dark btn-block">검색 설정</a>
		</div>
	</div>
</div>
</fieldset>
<div class="col-sm-12">
	<div class="card">
		<div class="card-body">
			<h5 class="card-title">순위</h5>
			<p class="card-text">화면에는 10위까지 노출됩니다.</p>
			<div id="data-qainfo"></div>
			<div class="col-sm-3">
				이름 : <input type="text" id="usrNm" class="form-control form-control-sm" value=""/>
				</br>
				<span>기록:</span>
				<div class="col-sm-12">
					<input type="text" placeholder="한자리수면 앞에 0붙여주세요 ex)5분->05"  maxlength="2" id="rcord1" class="form-control" value=""/>분
					<input type="text" placeholder="한자리수면 앞에 0붙여주세요 ex)5초->05" maxlength="2" id="rcord2" class="form-control" value=""/>초
					<input type="text" placeholder="한자리수면 앞에 00붙여주세요 ex)005초->" maxlength="3" id="rcord3" class="form-control" value=""/>밀리초
				</div>
				<a href="javascript: addBbs();" class="btn btn-primary btn-sm btn-area">등록</a>
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
	<script src="${CTX_ROOT}/resources/decms/bbs.js"></script>
</javascript>

</body>
</html>