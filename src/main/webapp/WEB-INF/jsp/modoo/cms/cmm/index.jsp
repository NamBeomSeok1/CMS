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
<div class="container-fluid">
<p style="color: blue !important;">※대시보드에서는 단순 현황 조회만 가능합니다. 더 많은 기능을 이용하시려면 [더 보기]를 눌러 상세 페이지로 이동해 주세요.</p>
<input type="hidden" id="isAdmin"  value="${IS_ADMIN}"/>
<c:choose>
<c:when test="${IS_ADMIN eq 'Y'}">
<!-- 	<h5>관리자 Dashboard</h5> -->
	<div class="row">
		<div class="col-sm-6">
			<div class="card">
			  <div class="card-body">
			    <h5 class="card-title">등록 대기중인 업체 <span class="badge badge-secondary" id="cmpny-cnt">0</span> </h5>
			    <p class="card-text">현재 등록 대기중인 업체의 목록이 최신순으로 10건까지 노출됩니다.</p>
					<div id="data-cmpny"></div>
			    <a href="${CTX_ROOT}/decms/shop/cmpny/cmpnyManage.do?menuId=cmpnyManage" class="btn btn-primary btn-sm btn-area">더 보기</a>
			  </div>
			</div>
		</div>
		<div class="col-sm-6">
			<div class="card">
			  <div class="card-body">
			    <h5 class="card-title">등록 대기중인 상품 <span class="badge badge-secondary" id="goods-cnt">0</span></h5>
			    <p class="card-text">현재 등록 대기중인 상품의 목록이 최신순으로 10건까지 노출됩니다.</p>
					<div id="data-goods"></div>
			    <a href="${CTX_ROOT}/decms/shop/goods/goodsManage.do?menuId=goodsManage" class="btn btn-primary btn-sm btn-area">더 보기</a>
			  </div>
			</div>
		</div>
	</div>
	<div class="row">
	<div class="col-sm-6">
			<div class="card">
			  <div class="card-body">
			    <h5 class="card-title">1:1 문의 <span class="badge badge-secondary" id="qainfo-cnt">0</span></h5>
			    <p class="card-text">답변을 기다리고 있는 1:1문의 목록이 최신순으로 10건까지 노출됩니다.</p>
					<div id="data-qainfo"></div>
			    <a href="${CTX_ROOT}/decms/qainfo/qainfoManage.do?menuId=siteQainfoManage&qaSeCode=SITE" class="btn btn-primary btn-sm btn-area">더 보기</a>
			  </div>
			</div>
		</div>
		<div class="col-sm-6">
			<div class="card">
			  <div class="card-body">
			    <h5 class="card-title">입점문의</h5>
			    <p class="card-text">입점문의가 최신순으로 5건까지 표시됩니다.</p>
					<div id="data-inqry"></div>
			    <a href="${CTX_ROOT}/decms/cmpny/inqryManage.do?menuId=cmpnyInqryManage" class="btn btn-primary btn-sm btn-area">더 보기</a>
			  </div>
			</div>
		</div>
	</div>
	
</c:when>
<c:otherwise>
<!-- 	<h5>CP관리자 Dashboard</h5> -->
	<div class="row">
		<div class="col-sm-6">
			<div class="card">
			  <div class="card-body">
			    <h5 class="card-title">CP 공지</h5>
			    <p class="card-text">최신순으로 5개까지 표시됩니다.</p>
					<div id="data-notice-cp"></div>
			    <a href="${CTX_ROOT}/decms/board/article/boardList.do?menuId=cpNoticeManage&bbsId=BBSMSTR_0000000000CP" class="btn btn-primary btn-sm btn-area">더 보기</a>
			  </div>
			</div>
		</div>
		<div class="col-sm-6">
			<div class="card">
			  <div class="card-body">
			    <h5 class="card-title">주문 현황 <span class="badge badge-secondary" id="order-dlvy-cnt">0</span></h5>
			    <p class="card-text">아직 배송이 시작되지 않은 주문 목록입니다.</p>
					<div id="data-order-dlvy"></div>
			    <a href="${CTX_ROOT}/decms/shop/goods/orderDlvyManage.do?menuId=orderDlvyManage" class="btn btn-primary btn-sm btn-area">더 보기</a>
			  </div>
			</div>
		</div>
	</div>
	<div class="row mt-1">
		<div class="col-sm">
			<div class="card">
			  <div class="card-body">
			    <h5 class="card-title">취소 신청 <span class="badge badge-secondary" id="order-cancel-cnt">0</span></h5>
			    <p class="card-text">아직 처리되지 않은 취소 신청 목록입니다.</p>
					<div id="data-order-cancel"></div>
			    <a href="${CTX_ROOT}/decms/shop/goods/orderCancelManage.do?menuId=orderCancelManage" class="btn btn-primary btn-sm btn-area">더 보기</a>
			  </div>
			</div>
		</div>
		<div class="col-sm">
			<div class="card">
			  <div class="card-body">
			    <h5 class="card-title">해지 신청 <span class="badge badge-secondary" id="order-stop-cnt">0</span></h5>
			    <p class="card-text">아직 처리되지 않은 해지 신청 목록입니다.</p>
					<div id="data-order-stop"></div>
			    <a href="${CTX_ROOT}/decms/shop/goods/orderStopManage.do?menuId=orderStopManage" class="btn btn-primary btn-sm btn-area">더 보기</a>
			  </div>
			</div>
		</div>
		<div class="col-sm">
			<div class="card">
			  <div class="card-body">
			    <h5 class="card-title">교환 신청 <span class="badge badge-secondary" id="order-exchange-cnt">0</span></h5>
			    <p class="card-text">아직 처리되지 않은 교환 신청 목록입니다.</p>
					<div id="data-order-exchange"></div>
			    <a href="${CTX_ROOT}/decms/shop/goods/orderExchangeManage.do?menuId=orderExchangeManage" class="btn btn-primary btn-sm btn-area">더 보기</a>
			  </div>
			</div>
		</div>
		<div class="col-sm">
			<div class="card">
			  <div class="card-body">
			    <h5 class="card-title">반품 신청 <span class="badge badge-secondary" id="order-refund-cnt">0</span></h5>
			    <p class="card-text">아직 처리되지 않은 반품 신청 목록입니다.</p>
					<div id="data-order-refund"></div>
			    <a href="${CTX_ROOT}/decms/shop/goods/orderRecallManage.do?menuId=orderRecallManage" class="btn btn-primary btn-sm btn-area">더 보기</a>
			  </div>
			</div>
		</div>
	</div>
	<div class="row">
		<div class="col-sm">
			<div class="card">
			  <div class="card-body">
			    <h5 class="card-title">최근 상품평</h5>
			    <p class="card-text">최신순으로 5건까지 표시됩니다.</p>
					<div id="data-review"></div>
			    <a href="${CTX_ROOT}/decms/shop/goods/review/reviewManage.do?menuId=reviewManage" class="btn btn-primary btn-sm btn-area">더 보기</a>
			  </div>
			</div>
		</div>
		<div class="col-sm">
			<div class="card">
			  <div class="card-body">
			    <h5 class="card-title">상품 Q&A <span class="badge badge-secondary" id="qna-cnt">0</span></h5>
			    <p class="card-text">아직 답변하지 않은 상품 Q&A입니다.</p>
					<div id="data-qna"></div>
			    <a href="${CTX_ROOT}/decms/qainfo/qainfoManage.do?menuId=qainfoManage&qaSeCode=goods" class="btn btn-primary btn-sm btn-area">더 보기</a>
			  </div>
			</div>
		</div>
	</div>

</c:otherwise>
</c:choose>
</div>
<javascript>
	<script src="${CTX_ROOT}/resources/lib/tui/tui-code-snippet/tui-code-snippet.min.js"></script>
	<script src="${CTX_ROOT}/resources/lib/tui/tui-pagination/tui-pagination.min.js"></script>
	<script src="${CTX_ROOT}/resources/lib/tui/tui-grid/tui-grid.min.js"></script>
	<script src="${CTX_ROOT}/resources/lib/tempusdominus-bootstrap-4/js/tempusdominus-bootstrap-4.min.js"></script>
	<script src="${CTX_ROOT}/resources/lib/jquery-ui/jquery-ui.min.js"></script>
	<script src="${CTX_ROOT}/resources/decms/dashboard.js"></script>
</javascript>

</body>
</html>