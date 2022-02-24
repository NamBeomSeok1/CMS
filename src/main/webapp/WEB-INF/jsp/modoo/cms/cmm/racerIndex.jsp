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
<div class="col-sm-12">
	<div class="card">
		<div class="card-body">
			<h5 class="card-title">순위</h5>
			<p class="card-text">기록이 빠른 순으로 10위까지 노출됩니다.</p>
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