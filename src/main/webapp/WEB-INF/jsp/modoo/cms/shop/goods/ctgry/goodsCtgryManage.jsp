<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/modoo/common/commonTagLibs.jsp"%>
<c:set var="pageTitle" value="상품카테고리 관리"/>
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
			<form:form modelAttribute="searchVO" id="searchForm" name="searchForm" method="get" action="${CTX_ROOT }/decms/shop/goods/goodsCtgryTreeList.json">
				<fieldset>
					<input type="hidden" name="menuId" value="${param.menuId }"/>
				</fieldset>
			</form:form>
			<div class="mt-3 text-right">
				<c:url var="writeUrl" value="/decms/embed/shop/goods/writeGoodsCtgry.do">
				</c:url>
				<a href="<c:out value="${writeUrl }"/>" class="btn btn-primary btn-sm btnAdd" data-target="#goodsCtgryModal"><i class="fas fa-plus"></i> 카테고리등록</a>
			</div>

			<small class="text-danger"><i class="fas fa-info"></i> 카테고리는 캐쉬로 저장되어 이중화 또는 개발모드에서 다를 수 있습니다.</small>
			<div id="data-grid" class="mt-3"></div>
			<div id="data-grid-pagination" class="tui-pagination"></div>
		</div>
	</div>
	
	<div class="modal fade" id="goodsCtgryModal" tabindex="-1" role="dialog" aria-labelledby="goodsCtgryModalLabel" aria-hidden="true">
		<div class="modal-dialog modal-xl modal-dialog-centered modal-dialog-scrollable modal-dark">
			<div class="modal-content">
				<div class="modal-header">
					<h6 class="modal-title" id="goodsCtgryModalLabel">수정</h6>
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

	<div class="modal fade" id="goodsListModal" tabindex="-1" role="dialog" aria-labelledby="goodsListModalLabel" aria-hidden="true">
		<div class="modal-dialog modal-xl modal-dialog-centered modal-dialog-scrollable modal-dark">
			<div class="modal-content">
				<div class="modal-header">
				<h6 class="modal-title" id="goodsListModalLabel"></h6>
					<button type="button" class="close" data-dismiss="modal" aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
				</div>
				<div class="modal-body">
					<!-- <div class="text-center p-4 modal-spinner">
						<i class="fas fa-spinner fa-spin"></i> Loading...
					</div> -->
					<form id="ctgryGoodsSearchForm" name="ctgryGoodsSearchForm" method="get" action="${CTX_ROOT }/decms/shop/goods/goodsList.json">
						<input type="hidden" id="ctgryGoods-pageIndex" name="pageIndex" value="1"/>
						<input type="hidden" id="ctgryGoods-searchGoodsCtgryId" name="searchGoodsCtgryId" value=""/>
					</form>
					<div id="data-goodsGrid"></div>
					<div id="data-goods-grid-pagination" class="tui-pagination"></div>
				</div>
			</div>
		</div>
	</div>


	<javascript>
		<script src="${CTX_ROOT}/resources/lib/tui/tui-code-snippet/tui-code-snippet.min.js"></script>
		<script src="${CTX_ROOT}/resources/lib/tui/tui-pagination/tui-pagination.min.js"></script>
		<script src="${CTX_ROOT}/resources/lib/tui/tui-grid/tui-grid.min.js"></script>
		<script src="${CTX_ROOT}/resources/decms/shop/goods/ctgry/js/goodsCtgryManage.js?20200924"></script>
		<script src="${CTX_ROOT}/resources/decms/shop/goods/ctgry/js/ctgryGoodsList.js"></script>
	</javascript>
</body>
</html>