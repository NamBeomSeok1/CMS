<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/modoo/common/commonTagLibs.jsp"%>
<c:set var="pageTitle" value="상품 관리"/>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>${pageTitle }</title>
	<link rel="stylesheet" type="text/css" href="${CTX_ROOT}/resources/lib/jquery-ui/jquery-ui.min.css"/>
	<link rel="stylesheet" type="text/css" href="${CTX_ROOT}/resources/lib/tui/tui-grid/tui-grid.min.css"/>
	<link rel="stylesheet" type="text/css" href="${CTX_ROOT}/resources/lib/tui/tui-pagination/tui-pagination.min.css"/>
	<link rel="stylesheet" type="text/css" href="${CTX_ROOT}/resources/lib/tempusdominus-bootstrap-4/css/tempusdominus-bootstrap-4.min.css"/>
	<style>
		.goods-info-card {text-align:center; border:1px solid #dfdfdf; box-shadow: 0 .15rem 1.75rem 0 rgba(58,59,69,.15)!important;padding:0.5rem;}
		.goods-info-card .alert-heading {font-size:0.9rem; font-weight:bold;}
		.goods-info-card p {font-size:0.8rem;}
		.card-blue {border-left:0.3rem solid #4e73df;}
		.card-blue .alert-heading {color:#4e73df;}
		.card-green {border-left:0.3rem solid #1cc88a;}
		.card-green .alert-heading {color:#1cc88a;}
		.card-red {border-left:0.3rem solid #e74a3b;}
		.card-red .alert-heading {color:#e74a3b;}
		.card-yellow {border-left:0.3rem solid #f6c23e;}
		.card-yellow .alert-heading {color:#f6c23e;}
		
		.goods-tab {}
		.goods-tab .nav-link {font-size:0.8rem; color:#afafaf;}
		.goods-tab .nav-link:hover {color:#666;}
		
		.goods-search-box {border:1px solid #dfdfdf; border-left:0.3rem solid #5a5c69;box-shadow: 0 .15rem 1.75rem 0 rgba(58,59,69,.15)!important;}
		
		.cmpny-complete {background: #f6c23e61 !important;}
	</style>
	<c:if test="${IS_ADMIN ne 'Y'}">
		<style>.btnDelete {display:none;}</style>
	</c:if>
</head>
<body>
	<div class="card shadow page-wrapper">
		<div class="card-header">
			<h6 class="m-0 font-weight-bold text-primary">${pageTitle }</h6>
		</div>
		<div class="card-body">
			<div class="row">
				<div class="col-md-3">
					<div class="alert alert-light goods-info-card card-blue" role="alert">
						<h5 class="alert-heading">등록상품</h5>
						<hr class="sm"/>
						<p class="mb-1"><c:out value="${sttusCntinfo.tcnt }"/> 개</p>
					</div>
				</div>
				<div class="col-md-3">
					<div class="alert alert-light goods-info-card card-green" role="alert">
						<h5 class="alert-heading">판매상품</h5>
						<hr class="sm"/>
						<p class="mb-1"><c:out value="${sttusCntinfo.ccnt }"/> 개</p>
					</div>
				</div>
				<div class="col-md-3">
					<div class="alert alert-light goods-info-card card-yellow" role="alert">
						<h5 class="alert-heading">등록대기</h5>
						<hr class="sm"/>
						<p class="mb-1"><c:out value="${sttusCntinfo.rcnt }"/> 개</p>
					</div>
				</div>
				<div class="col-md-3">
					<div class="alert alert-light goods-info-card card-red" role="alert">
						<h5 class="alert-heading">판매종료</h5>
						<hr class="sm"/>
						<p class="mb-1"><c:out value="${sttusCntinfo.ecnt }"/> 개</p>
					</div>
				</div>
			</div>
			<form:form modelAttribute="searchVO" id="searchForm" name="searchForm" method="get" action="${CTX_ROOT }/decms/shop/goods/goodsList.json">
				<fieldset>
					<input type="hidden" name="menuId" value="${param.menuId }"/>
					<input type="hidden" id="isAdmin"  value="${IS_ADMIN}"/>
					<input type="hidden" name="searchManageAt"  value="${IS_ADMIN}"/>
					<form:hidden path="pageIndex"/>
					<form:hidden path="searchCmpnyId"/>
				</fieldset>
				<fieldset class="goods-search-box container-fluid rounded pt-3 pb-3 mb-3">
					<div class="row">
						<div class="col-lg-6">
							<div class="form-row">
								<div class="input-group input-group-sm">
									<div class="input-group-prepend">
										<span class="input-group-text">카테고리</span>
									</div>
									<form:select path="searchCateCode1" cssClass="custom-select custom-select-sm selectCategory" data-dp="1">
										<form:option value="">선택</form:option>
										<c:forEach var="item" items="${cate1List }">
											<form:option value="${item.goodsCtgryId }">${item.goodsCtgryNm }</form:option>
										</c:forEach>
									</form:select>
									<form:select path="searchCateCode2" cssClass="custom-select custom-select-sm selectCategory" data-dp="2">
										<form:option value="">선택</form:option>
										<c:forEach var="item" items="${cate2List }">
											<form:option value="${item.goodsCtgryId }">${item.goodsCtgryNm }</form:option>
										</c:forEach>
									</form:select>
									<form:select path="searchCateCode3" cssClass="custom-select custom-select-sm selectCategory" data-dp="3">
										<form:option value="">선택</form:option>
										<c:forEach var="item" items="${cate3List }">
											<form:option value="${item.goodsCtgryId }">${item.goodsCtgryNm }</form:option>
										</c:forEach>
									</form:select>
								</div>
							</div>
							<div class="form-row mt-1">
								<div class="input-group input-group-sm">
									<div class="input-group-prepend">
										<form:select path="searchCondition" cssClass="form-control custom-select custom-select-sm rounded-0">
											<form:option value="GNM">상품명</form:option>
											<form:option value="MNM">모델명</form:option>
										</form:select>
									</div>
									<form:input path="searchKeyword" cssClass="form-control "/>
									<div class="input-group-append">
										<form:select path="searchPrtnrId" cssClass="custom-select custom-select-sm rounded-0">
											<form:option value="">=제휴사=</form:option>
											<form:option value="PRTNR_0000">B2C</form:option>
											<form:option value="PRTNR_0001">이지웰</form:option>
										</form:select>
									</div>
								</div>
							</div>
							<div class="form-row mt-1">
								<div class="input-group input-group-sm">
									<div class="input-group-prepend">
										<span class="input-group-text">상품번호</span>
									</div>
									<form:input path="searchGoodsId" cssClass="form-control form-control-sm"/>
								</div>
							</div>
						</div>
						<div class="col-lg-4">
							<c:choose>
								<c:when test="${fn:contains(USER_ROLE,'ROLE_EMPLOYEE')}">
									<div class="input-group input-group-sm">
										<div class="input-group-prepend">
											<span class="input-group-text">업체명</span>
										</div>
										<form:input path="searchCmpnyNm" cssClass="form-control form-control-sm" placeholder="검색 할 업체명을 입력하세요."/>
									</div>
								</c:when>
								<c:otherwise>
								</c:otherwise>
							</c:choose>
							
							<div class="mt-1">
								<div class="input-group input-group-sm">
									<div class="input-group-prepend">
										<span class="input-group-text">등록상태</span>
									</div>
									<form:select path="searchRegistSttusCode" cssClass="custom-select custom-select-sm">
										<form:option value="">전체</form:option>
										<c:forEach var="item" items="${registSttusCodeList }">
											<form:option value="${item.code }">${item.codeNm }</form:option>
										</c:forEach>
									</form:select>
								</div>
							</div>
						</div>
						<div class="col-lg-2">
							<button type="submit" class="btn btn-dark btn-block"><i class="fas fa-search"></i> 검색</button>
						</div>
					</div>
				</fieldset>
				<div class="row">
					<div class="col-lg-6">
						<div class="btn-group" role="group">
							<button id="btnGroupDrop1" type="button" class="btn btn-danger dropdown-toggle" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
								일괄 변경
							</button>
							<div class="dropdown-menu" aria-labelledby="btnGroupDrop1">
								<a class="dropdown-item" href="#" onclick="changeCheckedGoodsState('R')">등록 대기</a>
								<a class="dropdown-item" href="#" onclick="changeCheckedGoodsState('C')">등록 완료</a>
								<a class="dropdown-item" href="#" onclick="changeCheckedGoodsState('E')">판매 종료</a>
							</div>
						</div>
						<a href="${CTX_ROOT }/decms/shop/goods/goodsListExcel.do" class="btn btn-success btn-sm btnExcelDownload"><i class="fas fa-file-excel"></i> 엑셀다운로드</a>
					</div>
					<div class="col-lg-6">
						<div class="text-right">
							<c:url var="writeUrl" value="/decms/shop/goods/writeGoods.do">
								<c:param name="menuId" value="${param.menuId }"/>
								<c:param name="searchCmpnyId" value="${searchVO.searchCmpnyId }"/>
								<c:param name="searchCateCode1" value="${searchVO.searchCateCode1 }"/>
								<c:param name="searchCateCode2" value="${searchVO.searchCateCode2 }"/>
								<c:param name="searchCateCode3" value="${searchVO.searchCateCode3 }"/>
								<c:param name="searchRegistSttusCode" value="${searchVO.searchRegistSttusCode }"/>
								<c:param name="searchCondition" value="${searchVO.searchCondition }"/>
								<c:param name="searchKeyword" value="${searchVO.searchKeyword }"/>
								<c:param name="searchCmpnyNm" value="${searchVO.searchCmpnyNm }"/>
								<c:param name="pageIndex" value="${searchVO.pageIndex }"/>
							</c:url>
							<a href="<c:out value="${writeUrl }"/>" class="btn btn-primary btn-sm"><i class="fas fa-plus"></i> 상품등록</a>
						</div>
					</div>
				</div>
				<div class="mt-3">
					<ul class="nav nav-tabs goods-tab">
						<li class="nav-item">
							<a href="#" class="nav-link active" data-code="">전체</a>
						</li>
						<li class="nav-item">
							<a href="#" class="nav-link" data-code="C">등록완료</a>
						</li>
						<li class="nav-item">
							<a href="#" class="nav-link" data-code="R">등록대기</a>
						</li>
						<li class="nav-item">
							<a href="#" class="nav-link" data-code="E">판매종료</a>
						</li>
					</ul>
					<div class="row mt-1">
						<div class="col-md-2">
							<div class="input-group input-group-sm">
								<div class="input-group-prepend">
									<span class="input-group-text">목록</span>
								</div>
								<form:select path="pageUnit" cssClass="custom-select custom-select-sm">
									<form:option value="10">10개</form:option>
									<form:option value="20">20개</form:option>
									<form:option value="30">30개</form:option>
								</form:select>
							</div>
						</div>
						<div class="offset-md-6 col-md-2">
							<label for="" class="sr-only">정렬순서</label>
							<form:select path="searchOrderField" cssClass="custom-select custom-select-sm">
								<form:option value="">신상품순</form:option>
								<form:option value="RDCNT">조회수</form:option>
								<form:option value="GNM">상품명</form:option>
								<form:option value="MNM">모델명</form:option>
								<form:option value="HPC">판매가</form:option>
								<form:option value="DPC">배송비</form:option>
								<form:option value="SLC">판매순</form:option>
							</form:select>
						</div>
						<div class="col-md-2">
							<label for="" class="sr-only">정렬순서</label>
							<form:select path="searchOrderField2" cssClass="custom-select custom-select-sm">
								<form:option value="">내림차순</form:option>
								<form:option value="ASC">오름차순</form:option>
							</form:select>
						</div>
					</div>
				</div>
			</form:form>
			<div class="mt-1">
				<div id="data-grid"></div>
				<div id="data-grid-pagination" class="tui-pagination"></div>
			</div>
		</div>
	</div>
	<javascript>
		<script src="${CTX_ROOT}/resources/lib/tui/tui-code-snippet/tui-code-snippet.min.js"></script>
		<script src="${CTX_ROOT}/resources/lib/tui/tui-pagination/tui-pagination.min.js"></script>
		<script src="${CTX_ROOT}/resources/lib/tui/tui-grid/tui-grid.min.js"></script>
		<script src="${CTX_ROOT}/resources/lib/tempusdominus-bootstrap-4/js/tempusdominus-bootstrap-4.min.js"></script>
		<script src="${CTX_ROOT}/resources/lib/jquery-ui/jquery-ui.min.js"></script>
		<script src="${CTX_ROOT}/resources/decms/shop/goods/info/js/goodsManage.js?20210115"></script>
	</javascript>
</body>
</html>