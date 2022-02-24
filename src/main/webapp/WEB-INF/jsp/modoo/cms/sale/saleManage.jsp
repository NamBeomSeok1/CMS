<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/modoo/common/commonTagLibs.jsp"%>
<c:set var="pageTitle" value="특가 관리"/>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>${pageTitle }</title>
	<link rel="stylesheet" type="text/css" href="${CTX_ROOT }/resources/lib/tui/tui-grid/tui-grid.min.css"/>
	<link rel="stylesheet" type="text/css" href="${CTX_ROOT }/resources/lib/tui/tui-pagination/tui-pagination.min.css"/>
	<link rel="stylesheet" type="text/css" href="${CTX_ROOT }/resources/lib/tempusdominus-bootstrap-4/css/tempusdominus-bootstrap-4.min.css"/>
	
	<link href="${CTX_ROOT }/resources/lib/bootstrap-table-master/dist/bootstrap-table.min.css" rel="stylesheet">
	<link href="${CTX_ROOT }/resources/lib/bootstrap-table-master/dist/extensions/reorder-rows/bootstrap-table-reorder-rows.css" rel="stylesheet">
	<link href="${CTX_ROOT }/resources/lib/open-iconic-master/font/css/open-iconic-bootstrap.css" rel="stylesheet">
	
	<style>
		td {
			color: #2F2F2F !important;
		}
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
			<ul class="nav nav-tabs" id="myTab" role="tablist">
				<c:forEach var="prtnr" items="${prtnrList}" varStatus="status">
					<li class="nav-item">
						<c:set var="sharp" value="#" />
				  		<a class="nav-link <c:if test="${status.index eq 0}">active</c:if>" id="${prtnr.prtnrId}-tab" data-toggle="tab" href="${sharp}${prtnr.prtnrId}" role="tab" aria-controls="home" aria-selected="true">${prtnr.prtnrNm}</a>
					</li>
				</c:forEach>
			</ul>
			<div class="tab-content" id="myTabContent">
				<c:forEach var="prtnr" items="${prtnrList}" varStatus="status">
					<div class="tab-pane fade <c:if test="${status.index eq 0}">show active</c:if>" id="${prtnr.prtnrId}" role="tabpanel" aria-labelledby="${prtnr.prtnrId}-tab">
						
						<div class="container-fluid">
							<div class="row">
								<div class="col-sm-11">
									<table class="table table-sm table-bordered"
									    id="table-${prtnr.prtnrId}-tab"
									data-search="true"
									data-use-row-attr-func="true"
									data-reorderable-rows="true"
									data-resizable="true"
									data-height="600">
									<thead>
										<tr>
										  <th scope="col" data-field="expsrOrdr" data-sortable="false">노출<br>순서</th>
										  <th scope="col" data-field="goodsId" data-sortable="false">상품코드</th>
										  <th scope="col" data-field="prtnrId" data-sortable="false" data-formatter="prtnrFormatter">제휴사</th>
										  <th scope="col" data-field="goodsKndCode" data-sortable="false" data-formatter="goodsKindFormatter">종류</th>
										  <th scope="col" data-field="actvtyAt" data-sortable="false" data-formatter="ynFormatter">상태</th>
										  <th scope="col" data-field="goodsImage" data-sortable="false" data-formatter="imageFormatter">이미지</th>
										  <th scope="col" data-field="cmpnyNm" data-sortable="false">업체명</th>
										  <th scope="col" data-field="goodsNm" data-sortable="false">상품명</th>
										  <th scope="col" data-field="labelTyCode" data-sortable="false" data-formatter="labelTyCodeFormatter">라벨종류</th>
										  <th scope="col" data-field="labelText" data-sortable="false">라벨문구</th>
										  <th scope="col" data-field="labelColor" data-sortable="false" data-formatter="bgColorFormatter">라벨<br>색상</th>
										  <th scope="col" data-field="labelTextColor" data-sortable="false" data-formatter="textColorFormatter">글자<br>색상</th>
										  <th scope="col" data-field="goodsPc" data-sortable="false" data-formatter="priceFormatter">판매가</th>
										  <th scope="col" data-field="registSttusCode" data-sortable="false" data-formatter="sttusFormatter">등록상태</th>
										  <th scope="col" data-field="expsrBeginDe" data-sortable="false" data-formatter="dateFormatter">노출시작일</th>
										  <th scope="col" data-field="expsrEndDe" data-sortable="false" data-formatter="dateFormatter">노출종료일</th>
										  <th scope="col" data-field="edit" data-sortable="false" data-formatter="editFormatter">편집</th>
										</tr>
										
									  </thead>
									  <tbody>

									  </tbody>
									</table>
								
								</div>
								
								<div class="col-sm-1">
									<button class="btn btn-sm btn-primary btnAddRecomend">상품 등록</button>
								</div>
							</div>
							<div class="row mt-5">
								<div class="col-sm">
									<button class="btn btn-sm btn-danger btn-area" onclick="saveSaleGoodsData();">저장</button>
									<button class="btn btn-sm btn-secondary btn-area" onclick="location.reload(true);">취소</button>
								</div>
							</div>
						</div>
					
					</div>
				</c:forEach>
			</div>
		</div>
	</div>
	
	<!-- 에디터 모달 -->
	<div class="modal fade" id="editModal" tabindex="-1" role="dialog" aria-labelledby="mySmallModalLabel" aria-hidden="true">
		<div class="modal-dialog modal-sm">
		  <div class="modal-content">
		  		<div class="modal-header">
					<h6 class="modal-title" id="cmpnyListModalLabel">수정</h6>
					<button type="button" class="close" data-dismiss="modal" aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
				</div>
				<div class="modal-body">
					<input type="hidden" id="goods-index">
					<div class="form-group">
						<img src="" style="width: 100px; height: 100px;" id="goodsImage">
						<input type="text" readonly class="form-control-plaintext" id="goodsId" value="">
						<input type="text" readonly class="form-control-plaintext" id="goodsNm" value="">
					</div>
					<div class="form-group">
						<label for="actvtyAt">상태</label>
						<select class="form-control form-control-sm" id="actvtyAt">
					  		<option value="Y">노출중</option>
					  		<option value="N">미노출</option>
						</select>
					</div>
					<div class="form-group">
						<label for="labelTyCode">라벨 종류<span style="color:red;">*</span></label>
						<select class="form-control form-control-sm" id="labelTyCode">
					  		<option value="">==선택==</option>
					  		<option value="F">첫구독라벨</option>
					  		<option value="A">증정라벨</option>
					  		<option value="S">특가라벨</option>
					  		<option value="C">커스텀라벨</option>
						</select>
					</div>
					<div class="form-group">
					    <label for="labelText">라벨 문구</label>
					    <input type="text" class="form-control" id="labelText" placeholder="">
					</div>
					<div class="form-group">
					    <label for="labelColor">라벨 색상<span style="color:red;">*</span></label>
					 	<div class="row">
					 		<div class="col">
					 			<input type="color" class="form-control inputColor" id="labelColorPick">	
					 		</div>
					 		<div class="col">
					 			<input type="text" class="form-control hexValue" id="labelColor" placeholder="#FFFFFF">	
					 		</div>
					 	</div>
					</div>
					<div class="form-group">
					    <label for="labelTextColor">글자 색상<span style="color:red;">*</span></label>
					    <div class="row">
					 		<div class="col">
					 			<input type="color" class="form-control inputColor" id="labelTextColorPick">
					 		</div>
					 		<div class="col">
					 			<input type="text" class="form-control hexValue" id="labelTextColor" placeholder="#FFFFFF">
					 		</div>
					 	</div>
					</div>
					<div class="form-group">
					    <label for="expsrBeginDe" class="required">노출시작일 <span style="color:red;">*</span></label>
					    <div class="input-group input-group-sm" id="expsrBeginDe" data-target-input="nearest">
							<input type="text" name="expsrBeginDe" class="form-control datetimepicker-input" data-target="#eventBeginDt"/>
							<div class="input-group-append" data-target="#expsrBeginDe" data-toggle="datetimepicker">
								<div class="input-group-text"><i class="fas fa-calendar"></i></div>
							</div>
						</div>
					</div>
					<div class="form-group">
					    <label for="expsrEndDe" class="required">노출종료일 <span style="color:red;">*</span></label>
					    <div class="input-group input-group-sm" id="expsrEndDe" data-target-input="nearest">
							<input type="text" name="expsrEndDe" class="form-control datetimepicker-input" data-target="#eventBeginDt"/>
							<div class="input-group-append" data-target="#expsrEndDe" data-toggle="datetimepicker">
								<div class="input-group-text"><i class="fas fa-calendar"></i></div>
							</div>
						</div>
					</div>
					
<!-- 					<div class="form-group"> -->
<!-- 					    <label for="expsrEndDe">노출종료일</label> -->
<!-- 					    <input type="text" class="form-control" id="expsrEndDe" placeholder=""> -->
<!-- 					</div> -->
					<div class="form-group">
					    <button type="button" class="btn btn-sm btn-primary updateRowData">완료</button>
					</div>
				</div>

		  </div>
		</div>
	</div>
		
	<!-- 상품 모달 -->
	<div class="modal fade" id="recomendListModal" tabindex="-1" role="dialog" aria-labelledby="recomendListModalLabel" aria-hidden="true">
		<div class="modal-dialog modal-lg modal-dialog-centered modal-dialog-scrollable modal-dark">
			<div class="modal-content">
				<div class="modal-header">
					<h6 class="modal-title" id="cmpnyListModalLabel">상품목록</h6>
					<button type="button" class="close" data-dismiss="modal" aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
				</div>
				<div class="modal-body">
					<form name="recomendSearchForm" id="recomendSearchForm" method="get" action="${CTX_ROOT}/decms/shop/goods/goodsList.json">
						<fieldset>
							<input type="hidden" id="recomendPageIndex" name="pageIndex" value=""/>
							<input type="hidden" name="searchCmpnyId" value="${goods.cmpnyId }"/>
							<input type="hidden" name="searchNotGoodsId" value="${goods.goodsId }"/>
						</fieldset>
						<div class="form-group row">
							<div class="col-auto">
								<select name="searchPrtnrId" class="custom-select custom-select-sm">
									<option value="">제휴사</option>
									<option value="PRTNR_0000">B2C</option>
									<option value="PRTNR_0001">이지웰</option>
								</select>
							</div>
							<div class="col-auto">
								<label for="searchCondition" class="sr-only">검색분류</label>
								<select name="searchCondition" class="custom-select custom-select-sm">
									<option value="PDN">상품번호</option>
									<option value="GNM">상품명</option>
								</select>
							</div>
							<div class="col-auto">
								<div class="input-group input-group-sm">
									<input type="text" name="searchKeyword" class="form-control" value=""/>
									<span class="input-group-append">
										<button type="submit" class="btn btn-primary"><i class="fas fa-search"></i>검색</button>
									</span>
								</div>
							</div>
						</div>
					</form>
					<div class="recomend-grid">
						<div id="data-recomend-grid" class="mt-3"></div>
						<div id="data-recomend-grid-pagination" class="tui-pagination"></div>
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
		
		<script src="${CTX_ROOT }/resources/lib/TableDnD-master/js/jquery.tablednd.js"></script>
		<script src="${CTX_ROOT }/resources/lib/bootstrap-table-master/dist/bootstrap-table.min.js"></script>
		<script src="${CTX_ROOT }/resources/lib/bootstrap-table-master/dist/extensions/reorder-rows/bootstrap-table-reorder-rows.min.js"></script>  

		<script src="${CTX_ROOT }/resources/decms/sale/saleManage.js"></script>
		<script src="${CTX_ROOT }/resources/decms/sale/saleManageGoodsRecomendList.js"></script>
		
	</javascript>
</body>
</html>