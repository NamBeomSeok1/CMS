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
	<hr/>
		<div class="card">
			<div class="card-body">
				<h5 class="card-title">등록</h5>
				<div class="col-sm-3">
					<div class="form-group row">
						<div class="input-group input-group-sm">
						<div class="input-group-prepend">
							<div class="input-group-text">이름</div>
						</div>
						<input type="text" id="usrNm" class="form-control form-control-sm" value=""/>
						</div>
						<div class="input-group input-group-sm">
							<input type="text" placeholder="한자리수면 앞에 0붙여주세요 ex)5분->05"  maxlength="2" id="rcord1" class="form-control form-control-sm inputNumber" value=""/>
							<div class="input-group-append">
								<span class="input-group-text">분</span>
							</div>
						</div>
						<div class="input-group input-group-sm">
							<input type="text" placeholder="한자리수면 앞에 0붙여주세요 ex)5분->05"  maxlength="2" id="rcord2" class="form-control form-control-sm inputNumber" value=""/>
							<div class="input-group-append">
								<span class="input-group-text">초</span>
							</div>
						</div>
						<div class="input-group input-group-sm">
							<input type="text" placeholder="한자리수면 앞에 00붙여주세요 ex)5분->05"  maxlength="3" id="rcord3" class="form-control form-control-sm inputNumber" value=""/>
							<div class="input-group-append">
								<span class="input-group-text">밀리초</span>
							</div>
						</div>
				</div>
				<a href="javascript: addBbs();" class="btn btn-primary btn-sm btn-area">등록</a>
			</div>
		</div>
	</fieldset>
	<div class="col-sm-12">
		<div class="card">
			<div class="card-body">
				<h5 class="card-title">순위</h5>
				<p class="card-text">딥레이서 홈페이지에 나오는 기록입니다. (중복허용을 하지 않으면 같은 이름으로 등록된 기록 중 제일 빠른 기록으로 나옵니다.)</p>
				<div class="card mt-2">
					<div class="card-body">
						<div class="col-sm-5">
						<h5 class="card-title">순위 기간 설정</h5>
							<c:set var="dateDisabled" value="disabled"/>
							<c:set var="dateChecked" value=""/>
								<c:if test="${filter.dateUseAt eq 'Y'}">
									<c:set var="dateChecked" value="checked"/>
									<c:set var="dateDisabled" value=""/>
								</c:if>
						<div class="form-group row">
							<div class="col-sm-3">
								<div class="input-group input-group-sm" id="datepicker-searchBgnde" data-target-input="nearest">
									<input name="searchBgnde" id="beginDate" class="form-control datetimepicker-input" data-target="#datepicker-searchBgnde" placeholder="시작일" value="${filter.frstPnttm}" ${dateDisabled}/>
									<div class="input-group-append" data-target="#datepicker-searchBgnde" data-toggle="datetimepicker">
										<div class="input-group-text"><i class="fas fa-calendar"></i></div>
									</div>
								</div>
							</div>
							&nbsp ~ &nbsp
							<div class="col-sm-3">
								<div class="input-group input-group-sm" id="datepicker-searchEndde" data-target-input="nearest">
									<input name="searchEndde" id="endDate" class="form-control datetimepicker-input" data-target="#datepicker-searchEndde" placeholder="종료일" value="${filter.lastPnttm}" ${dateDisabled}/>
									<div class="input-group-append" data-target="#datepicker-searchEndde" data-toggle="datetimepicker">
										<div class="input-group-text"><i class="fas fa-calendar"></i></div>
									</div>
								</div>
							</div>
							<div class="custom-control custom-control-sm custom-checkbox custom-control-inline">
								<input type="checkbox" id="dateUseAt" name="dateUseAt" class="custom-control-input" value="Y" ${dateChecked}>
								<label class="custom-control-label" for="dateUseAt"><small>기간 설정</small></label>
							</div>
							<div class="col-sm-3">
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
							</div>
							<div class="btn-area">
								<a href="javascript: addFilter();" class="btn btn-dark btn-block">설정</a>
							</div>
						</div>
					</div>
					</div>
				</div>

				<div id="data-qainfo2">

				</div>
			</div>
			<hr/>
				<div class="card-body">
					<h5 class="card-title">전체 목록</h5>
					<p class="card-text">기간 설정과 상관없이 등록된 전체 목록입니다.</p>
					<p class="btn-area">
						<button class="btn btn-info" id="excelDown" onclick="javascript:location.href='${CTX_ROOT}/decms/bbs/bbsExcelDownload.do'">전체 목록 엑셀다운로드</button>
						<button class="btn btn-danger" onclick="javascript:deleteBbs('ALL')">전체 목록 삭제</button>
					</p>
					<div id="data-qainfo">
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
	<script src="${CTX_ROOT}/resources/decms/bbs.js"></script>
</javascript>

</body>
</html>