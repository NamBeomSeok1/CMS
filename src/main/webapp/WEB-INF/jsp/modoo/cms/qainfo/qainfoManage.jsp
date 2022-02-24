<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/modoo/common/commonTagLibs.jsp"%>
<c:set var="pageTitle" value="질문답변 관리"/>
<c:set var="gridId" value="data-grid"/>
<c:set var="actionUrl" value="${CTX_ROOT }/decms/qainfo/qainfoList.json"/>
<c:choose>
	<c:when test="${param.qaSeCode eq 'goods' }">
		<c:set var="pageTitle" value="상품Q&A"/>
		<c:set var="gridId" value="data-grid"/>
	</c:when>
	<c:when test="${param.qaSeCode eq 'SITE' }">
		<c:set var="pageTitle" value="1:1문의"/>
		<c:set var="gridId" value="data-site-grid"/>
	</c:when>
	<c:when test="${param.qaSeCode eq 'CP' }">
		<c:set var="pageTitle" value="본사Q&amp;A"/>
		<c:set var="gridId" value="data-cp-grid"/>
		<c:set var="actionUrl" value="${CTX_ROOT }/decms/qainfo/qainfoCpList.json"/>
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
	<link rel="stylesheet" type="text/css" href="${CTX_ROOT }/resources/lib/tui/tui-date-picker/tui-date-picker.min.css"/>
	<style>
		.tui-datepicker {z-index:1;}
		.qa-search-box {
			border: 1px solid #dfdfdf;
				border-left-color: rgb(223, 223, 223);
				border-left-style: solid;
				border-left-width: 1px;
			border-left: 0.3rem solid #5a5c69;
			box-shadow: 0 .15rem 1.75rem 0 rgba(58,59,69,.15) !important;
		}
	</style>
</head>
<body>

	<div class="card shadow page-wrapper">
		<div class="card-header">
			<h6 class="m-0 font-weight-bold text-primary">${pageTitle }</h6>
		</div>
		<div class="card-body">
			<form:form modelAttribute="searchVO" id="searchForm" name="searchForm" method="get" action="${actionUrl }">
				<fieldset>
					<form:hidden path="pageIndex"/>
					<form:hidden path="qaSeCode"/>
				</fieldset>
				<div class="qa-search-box container rounded pt-3 pb-3 mb-3">
					<c:choose>
						<c:when test="${param.qaSeCode eq 'goods'}">
							<div class="form-row mb-2">
								<div class="col-sm-4">
									<div class="input-group input-group-sm">
										<div class="input-group-prepend">
											<span class="input-group-text">상품명</span>
										</div>
										<form:input path="searchGoodsNm" cssClass="form-control"/>
									</div>
								</div>
								<div class="col-sm-4">
									<div class="input-group input-group-sm">
										<div class="input-group-prepend">
											<span class="input-group-text">문의 유형</span>
										</div>
										<form:select path="searchQestnTyCode" class="custom-select custom-select-sm">
											<form:option value="">=전체=</form:option>
											<c:forEach var="item" items="${qestnTyCodeList1 }">
												<form:option value="${item.code }">${item.codeNm }</form:option>
											</c:forEach>
										</form:select>
									</div>
								</div>
								<div class="col-sm-4">
									<div class="input-group input-group-sm">
										<div class="input-group-prepend">
											<span class="input-group-text">상태</span>
										</div>
										<form:select path="searchQnaProcessSttusCode" class="custom-select custom-select-sm">
											<form:option value="">=전체=</form:option>
											<c:forEach var="item" items="${qaSttusCodeList }">
												<form:option value="${item.code }">${item.codeNm }</form:option>
											</c:forEach>
										</form:select>
									</div>
								</div>
							</div>
						</c:when>
						<c:when test="${param.qaSeCode eq 'SITE' }">
							<div class="form-row mb-2">
								<div class="col-sm-4">
									<div class="input-group input-group-sm">
										<div class="input-group-prepend">
											<span class="input-group-text">상태</span>
										</div>
										<form:select path="searchQnaProcessSttusCode" class="custom-select custom-select-sm">
											<form:option value="">=전체=</form:option>
											<c:forEach var="item" items="${qaSttusCodeList }">
												<form:option value="${item.code }">${item.codeNm }</form:option>
											</c:forEach>
										</form:select>
									</div>
								</div>
								<div class="col-sm-8">
									<div class="row">
										<div class="col-sm-1 text text-right">
											기간
										</div>
										<div class="col-sm-5">
											<div class="input-group input-group-sm" id="datepicker-searchBgnde" data-target-input="nearest">
												<form:input path="searchBgnde" cssClass="form-control datetimepicker-input" data-target="#datepicker-searchBgnde"/>
												<div class="input-group-append" data-target="#datepicker-searchBgnde" data-toggle="datetimepicker">
													<div class="input-group-text"><i class="fas fa-calendar"></i></div>
												</div>
											</div>
										</div>
										<div class="col-sm-1 text-center">~</div>
										<div class="col-sm-5">
											<div class="input-group input-group-sm" id="datepicker-searchEndde" data-target-input="nearest">
												<form:input path="searchEndde" cssClass="form-control datetimepicker-input" data-target="#datepicker-searchEndde"/>
												<div class="input-group-append" data-target="#datepicker-searchEndde" data-toggle="datetimepicker">
													<div class="input-group-text"><i class="fas fa-calendar"></i></div>
												</div>
											</div>
										</div>
									</div>
								</div>
							</div>
							<div class="form-row mb-2">
								<div class="col-4">
									<div class="input-group input-group-sm">
										<div class="input-group-prepend">
											<span class="input-group-text">문의 유형</span>
										</div>
										<form:select path="searchQestnTyCode" class="custom-select custom-select-sm">
											<form:option value="">=전체=</form:option>
											<c:forEach var="item" items="${qestnTyCodeList2 }">
												<form:option value="${item.code }">${item.codeNm }</form:option>
											</c:forEach>
										</form:select>
									</div>
								</div>
							</div>
						</c:when>
						<c:when test="${param.qaSeCode eq 'CP' }">
							<c:if test="${fn:contains(USER_ROLE,'ROLE_EMPLOYEE')}">
								<div class="form-row mb-2">
									<div class="col-sm-4">
										<div class="input-group input-group-sm">
											<div class="input-group-prepend">
												<span class="input-group-text">상태</span>
											</div>
											<form:select path="searchQnaProcessSttusCode" class="custom-select custom-select-sm">
												<form:option value="">=전체=</form:option>
												<c:forEach var="item" items="${qaSttusCodeList }">
													<form:option value="${item.code }">${item.codeNm }</form:option>
												</c:forEach>
											</form:select>
										</div>
									</div>
									<div class="col-sm-8">
										<div class="input-group input-group-sm">
											<div class="input-group-prepend">
												<span class="input-group-text">업체명</span>
											</div>
											<form:input path="searchCmpnyNm" cssClass="form-control form-control-sm"/>
										</div>
									</div>
								</div>
							</c:if>
							<div class="form-row mb-2">
								<div class="col-4">
									<div class="input-group input-group-sm">
										<div class="input-group-prepend">
											<span class="input-group-text">문의 유형</span>
										</div>
										<form:select path="searchQestnTyCode" class="custom-select custom-select-sm">
											<form:option value="">=전체=</form:option>
											<c:forEach var="item" items="${qestnTyCodeList2 }">
												<form:option value="${item.code }">${item.codeNm }</form:option>
											</c:forEach>
										</form:select>
									</div>
								</div>
								<div class="col-sm-8">
									<div class="row">
										<div class="col-sm-1 text text-right">
											기간
										</div>
										<div class="col-sm-5">
											<div class="input-group input-group-sm" id="datepicker-searchBgnde" data-target-input="nearest">
												<form:input path="searchBgnde" cssClass="form-control datetimepicker-input" data-target="#datepicker-searchBgnde"/>
												<div class="input-group-append" data-target="#datepicker-searchBgnde" data-toggle="datetimepicker">
													<div class="input-group-text"><i class="fas fa-calendar"></i></div>
												</div>
											</div>
										</div>
										<div class="col-sm-1 text-center">~</div>
										<div class="col-sm-5">
											<div class="input-group input-group-sm" id="datepicker-searchEndde" data-target-input="nearest">
												<form:input path="searchEndde" cssClass="form-control datetimepicker-input" data-target="#datepicker-searchEndde"/>
												<div class="input-group-append" data-target="#datepicker-searchEndde" data-toggle="datetimepicker">
													<div class="input-group-text"><i class="fas fa-calendar"></i></div>
												</div>
											</div>
										</div>
									</div>
								</div>
							</div>
						</c:when>
					</c:choose>
					<div class="row">
						<div class="col-lg-8">
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
										<form:input path="qnaProcessSttusCode" cssClass="form-control form-control-sm"/>
										<span class="input-group-append">
											<button type="submit" class="btn btn-primary btn-sm"><i class="fas fa-search"></i>검색</button>
										</span>
									</div>
								</div>
								<%-- <div class="col-auto">
									<div class="text-right">
									<ul class="nav nav-tabs" id="myTab" role="tablist">
									  <li class="nav-item" role="presentation">
										<a class="nav-link active" id="goodsQna" data-toggle="tab" href="#home" role="tab" aria-controls="home" aria-selected="true">상품 QNA</a>
									  </li>
									  <li class="nav-item" role="presentation">
										<a class="nav-link" id="oneToOneQna" data-toggle="tab" href="#profile" role="tab" aria-controls="profile" aria-selected="false">1:1 문의</a>
									  </li>
									</ul>
										<button class="btn btn-primary btn-sm" id="goodsQna">상품 QNA</button>
										<a href="#" class="btn btn-primary btn-sm" id="oneToOneQna">1:1 문의</a>
									</div>		
								</div> --%>
							</div>
						</div>
						<div class="col-lg-4">
						<c:if test="${searchVO.qaSeCode eq 'CP' }">
							<c:if test="${fn:contains(USER_ROLE,'ROLE_SHOP') and not fn:contains(USER_ROLE,'ROLE_EMPLOYEE')}">
								<div class="text-right">
									<c:url var="writeUrl" value="/decms/embed/qainfo/writeCpQainfo.do">
										<c:param name="qaSeCode" value="CP"/>
									</c:url>
									<a href="<c:out value="${writeUrl }"/>" class="btn btn-primary btn-sm btnAdd" data-target="#qainfoModal"><i class="fas fa-plus"></i> 본사문의</a>
								</div>
							</c:if>
						</c:if>
						</div>
					</div>
				</div>
			</form:form>
			<!-- <div id="data-grid" class="mt-3"></div> -->
			<div id="${gridId }" class="mt-3"></div>
			<div id="data-grid-pagination" class="tui-pagination"></div>
		</div>
	</div>
	
	<div class="modal fade" id="qainfoModal" tabindex="-1" role="dialog" aria-labelledby="qainfoModalLabel" aria-hidden="true">
		<div class="modal-dialog modal-xl modal-dialog-centered modal-dialog-scrollable modal-dark">
			<div class="modal-content">
				<div class="modal-header">
					<h6 class="modal-title" id="qainfoModalLabel">수정</h6>
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
		<script src="${CTX_ROOT}/resources/lib/tui/tui-date-picker/tui-date-picker.min.js"></script>
		<c:choose>
		<c:when test="${searchVO.qaSeCode eq 'CP' and (fn:contains(USER_ROLE,'ROLE_SHOP') and (not fn:contains(USER_ROLE,'ROLE_EMPLOYEE')) ) }">
			<script src="${CTX_ROOT}/resources/decms/qainfo/js/qainfoCp.js"></script>
		</c:when>
		<c:otherwise>
			<script src="${CTX_ROOT}/resources/decms/qainfo/js/qainfoManage.js?202011"></script>
		</c:otherwise>
		</c:choose>
	</javascript>
</body>
</html>