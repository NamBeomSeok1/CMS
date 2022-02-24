<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/modoo/common/commonTagLibs.jsp"%>
<c:set var="pageTitle" value="이벤트 관리"/>

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
									<option value="SJ">이벤트명</option>
								</select>
							</div>
							<input name="searchKeyword" class="form-control"/>
							<span class="input-group-append">
								<button type="submit" id="searchBtn" class="btn btn-primary"><i class="fas fa-search"></i> 검색</button>
							</span>
						</div>
					</div>
					<div class="col-auto btn-area">
						<c:url var="writeUrl" value="${CTX_ROOT}/decms/event/eventForm.do"/>
						<a href="<c:out value="${writeUrl}"/>" class="btn btn-primary btn-area" ><i class="fas fa-plus"></i> 신규등록</a>
					</div>
				</div>
<!-- 			    <div class="col-sm-2"> -->
<!-- 			      One of three columns -->
<!-- 			    </div> -->
<!-- 			    <div class="col-sm-2"> -->
<!-- 			      One of three columns -->
<!-- 			    </div> -->
<!-- 			    <div class="col-sm-2"> -->
<!-- 			      One of three columns -->
<!-- 			    </div> -->
<!-- 			    <div class="col-sm-2"> -->
<!-- 			      One of three columns -->
<!-- 			    </div> -->
<!-- 			    <div class="col-sm-2"> -->
<!-- 			      One of three columns -->
<!-- 			    </div> -->
<!-- 			    <div class="col-sm-2"> -->
<!-- 			      One of three columns -->
<!-- 			    </div> -->
			  </div>
			  
<!-- 			  <div class="row btn-area"> -->
<%-- 			  		<a href="${CTX_ROOT}/decms/event/eventForm.do"><button type="button" class="btn btn-info">등록</button></a> --%>
<!-- 			  		<div class="text-right"> -->
<%-- 						<c:url var="writeUrl" value="${CTX_ROOT}/decms/event/eventForm.do"/> --%>
<%-- 						<a href="<c:out value="${writeUrl}"/>" class="btn btn-primary btn-sm" ><i class="fas fa-plus"></i> 신규등록</a> --%>
<!-- 					</div> -->
<!-- 			  </div> -->
			</div>
			
			<div id="data-grid" class="mt-3"></div>
			<div id="data-grid-pagination" class="tui-pagination"></div>
		</div>
	</div>
	
<!-- 	<div class="modal fade" id="bannerModal" tabindex="-1" role="dialog" aria-labelledby="bannerModalLabel" aria-hidden="true"> -->
<!-- 		<div class="modal-dialog modal-xl modal-dialog-centered modal-dialog-scrollable modal-dark"> -->
<!-- 			<div class="modal-content"> -->
<!-- 				<div class="modal-header"> -->
<!-- 					<h6 class="modal-title" id="bannerModalLabel">수정</h6> -->
<!-- 					<button type="button" class="close" data-dismiss="modal" aria-label="Close"> -->
<!-- 						<span aria-hidden="true">&times;</span> -->
<!-- 					</button> -->
<!-- 				</div> -->
<!-- 				<div class="modal-body"> -->
<!-- 					<div class="text-center p-4 modal-spinner"> -->
<!-- 						<i class="fas fa-spinner fa-spin"></i> Loading... -->
<!-- 					</div> -->
<!-- 				</div> -->
<!-- 			</div> -->
<!-- 		</div> -->
<!-- 	</div> -->
	
<!-- 	<div class="modal fade" id="recomendListModal" tabindex="-1" role="dialog" aria-labelledby="recomendListModalLabel" aria-hidden="true"> -->
<!-- 		<div class="modal-dialog modal-lg modal-dialog-centered modal-dialog-scrollable modal-dark"> -->
<!-- 			<div class="modal-content"> -->
<!-- 				<div class="modal-header"> -->
<!-- 					<h6 class="modal-title" id="cmpnyListModalLabel">상품목록</h6> -->
<!-- 					<button type="button" class="close" data-dismiss="modal" aria-label="Close"> -->
<!-- 						<span aria-hidden="true">&times;</span> -->
<!-- 					</button> -->
<!-- 				</div> -->
<!-- 				<div class="modal-body"> -->
<%-- 					<form name="recomendSearchForm" id="recomendSearchForm" method="get" action="${CTX_ROOT}/decms/shop/goods/goodsList.json"> --%>
<!-- 						<fieldset> -->
<!-- 							<input type="hidden" id="recomendPageIndex" name="pageIndex" value=""/> -->
<%-- 							<input type="hidden" name="searchCmpnyId" value="${goods.cmpnyId }"/> --%>
<%-- 							<input type="hidden" name="searchNotGoodsId" value="${goods.goodsId }"/> --%>
<!-- 						</fieldset> -->
<!-- 						<div class="form-group row"> -->
<!-- 							<div class="col-auto"> -->
<!-- 								<select name="searchPrtnrId" class="custom-select custom-select-sm"> -->
<!-- 									<option value="">제휴사</option> -->
<!-- 									<option value="PRTNR_0000">B2C</option> -->
<!-- 									<option value="PRTNR_0001">이지웰</option> -->
<!-- 								</select> -->
<!-- 							</div> -->
<!-- 							<div class="col-auto"> -->
<!-- 								<label for="searchCondition" class="sr-only">검색분류</label> -->
<!-- 								<select name="searchCondition" class="custom-select custom-select-sm"> -->
<!-- 									<option value="GNM">이름</option> -->
<!-- 								</select> -->
<!-- 							</div> -->
<!-- 							<div class="col-auto"> -->
<!-- 								<div class="input-group input-group-sm"> -->
<!-- 									<input type="text" name="searchKeyword" class="form-control" value=""/> -->
<!-- 									<span class="input-group-append"> -->
<!-- 										<button type="submit" class="btn btn-primary"><i class="fas fa-search"></i>검색</button> -->
<!-- 									</span> -->
<!-- 								</div> -->
<!-- 							</div> -->
<!-- 						</div> -->
<!-- 					</form> -->
<!-- 					<div class="recomend-grid"> -->
<!-- 						<div id="data-recomend-grid" class="mt-3"></div> -->
<!-- 						<div id="data-recomend-grid-pagination" class="tui-pagination"></div> -->
<!-- 					</div> -->
<!-- 				</div> -->
<!-- 			</div> -->
<!-- 		</div> -->
<!-- 	</div> -->
	
	<javascript>
		<script src="${CTX_ROOT}/resources/lib/tui/tui-code-snippet/tui-code-snippet.min.js"></script>
		<script src="${CTX_ROOT}/resources/lib/tui/tui-pagination/tui-pagination.min.js"></script>
		<script src="${CTX_ROOT}/resources/lib/tui/tui-grid/tui-grid.min.js"></script>
		<script src="${CTX_ROOT}/resources/lib/tempusdominus-bootstrap-4/js/tempusdominus-bootstrap-4.min.js"></script>
		<script src="${CTX_ROOT}/resources/lib/jquery-ui/jquery-ui.min.js"></script>
		<script src="${CTX_ROOT}/resources/decms/event/js/eventManage.js?202012"></script>
	</javascript>
</body>
</html>