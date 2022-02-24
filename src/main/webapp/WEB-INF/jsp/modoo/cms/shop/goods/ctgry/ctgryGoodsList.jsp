<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/modoo/common/commonTagLibs.jsp"%>
	<div>
		<c:url var="writeUrl" value="/decms/shop/goods/writeGoods.do">
				<c:param name="menuId" value="goodsMenu"/>
				<c:param name="searchCmpnyId" value="${searchVO.searchCmpnyId }"/>
				<c:param name="searchCateCode1" value="${searchVO.searchCateCode1 }"/>
				<c:param name="searchCateCode2" value="${searchVO.searchCateCode2 }"/>
				<c:param name="searchCateCode3" value="${searchVO.searchCateCode3 }"/>
				<c:param name="searchRegistSttusCode" value="${searchVO.searchRegistSttusCode }"/>
				<c:param name="searchCondition" value="${searchVO.searchCondition }"/>
				<c:param name="searchKeyword" value="${searchVO.searchKeyword }"/>
		</c:url>
		<a href="<c:out value="${writeUrl}"/>" class="btn btn-primary btn-sm"><i class="fas fa-plus"></i> 상품등록</a>
	</div>
	<div class="mt-1">
		<div id="data-goodsGrid"></div>
		<div id="data-goods-grid-pagination" class="tui-pagination"></div>
		<div id="searchCtgryId" data-ctgryid="${ctgryId}" style="display:none;"></div>
	</div>

	<%-- 
	<javascript>
		<script src="${CTX_ROOT}/resources/lib/tui/tui-code-snippet/tui-code-snippet.min.js"></script>
		<script src="${CTX_ROOT}/resources/lib/tui/tui-pagination/tui-pagination.min.js"></script>
		<script src="${CTX_ROOT}/resources/lib/tui/tui-grid/tui-grid.min.js"></script>
		<script src="${CTX_ROOT}/resources/lib/tempusdominus-bootstrap-4/js/tempusdominus-bootstrap-4.min.js"></script>
		<script src="${CTX_ROOT}/resources/lib/jquery-ui/jquery-ui.min.js"></script>
		<script src="${CTX_ROOT}/resources/decms/shop/goods/ctgry/js/ctgryGoodsList.js"></script>
	</javascript>
	--%>
