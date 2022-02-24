<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/modoo/common/commonTagLibs.jsp"%>
<c:set var="pageTitle" value="해지/취소/교환/반품"/>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>${pageTitle}</title>
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
	
/* 	.goods-tab {} */
/* 	.goods-tab .nav-link {font-size:0.8rem; color:#afafaf;} */
/* 	.goods-tab .nav-link:hover {color:#666;} */
	
 	.goods-search-box {border:1px solid #dfdfdf; border-left:0.3rem solid #5a5c69;box-shadow: 0 .15rem 1.75rem 0 rgba(58,59,69,.15)!important;}
	
/* 	.cmpny-complete {background: #f6c23e61 !important;} */
</style>
</head>
<body>
<div class="card shadow page-wrapper">
	<div class="card-header">
		<h6 class="m-0 font-weight-bold text-primary">교환</h6>
	</div>
	<div class="card-body">	
		<div class="row">
			<div class="col-sm-3">
				<div class="alert alert-light goods-info-card card-green" role="alert">
					<h5 class="alert-heading">주문취소</h5>
					<hr class="sm"/>
					<div class="row">
						<div class="col-md-6">
							<h5 class="alert-heading">취소내역</h5>
							<p id="p-order-c1">0건</p>
						</div>
  						<div class="col-md-6">
  							<h5 class="alert-heading">취소완료</h5>
  							<p id="p-order-c4">0건</p>
  						</div>
					</div>
				</div>
			</div>
			<div class="col-sm-6">
				<div class="alert alert-light goods-info-card card-red" role="alert">
					<h5 class="alert-heading">교환처리</h5>
					<hr class="sm"/>
					<div class="row">
						<div class="col-sm-3">
							<h5 class="alert-heading">교환신청(처리대기)</h5>
							<p id="p-order-e1">0건</p>
						</div>
  						<div class="col-sm-3">
  							<h5 class="alert-heading">교환승인</h5>
  							<p id="p-order-e2">0건</p>
  						</div>
  						<div class="col-sm-3">
  							<h5 class="alert-heading">교환 재발송중</h5>
  							<p id="p-order-e4">0건</p>
  						</div>
  						<div class="col-sm-3">
  							<h5 class="alert-heading">교환 완료</h5>
  							<p id="p-order-e5">0건</p>
  						</div>
  					</div>
				</div>
			</div>
			<div class="col-sm-3">
				<div class="alert alert-light goods-info-card card-green" role="alert">
					<h5 class="alert-heading">반품(환불)</h5>
					<hr class="sm"/>
						<div class="row">
							<div class="col-md-6">
								<h5 class="alert-heading">반품(환불)신청</h5>
								<p id="p-order-r1">0건</p>
							</div>
	 						<div class="col-md-6">
	 							<h5 class="alert-heading">반품(환불)완료</h5>
	 							<p id="p-order-r4">0건</p>
	 						</div>
						</div>
				</div>
			</div>	
		</div>
	<!-- 검색 -->
	<fieldset class="goods-search-box container-fluid rounded pt-3 pb-3 mb-3">
		<div class="row">
			<div class="col-sm-11">
				<div class="mt-1">
					<div class="input-group input-group-sm">
						<div class="input-group-prepend">
							<select name="searchCondition" class="form-control custom-select custom-select-sm rounded-0">
								<option value="">전체</option>
								<option value="1">주문번호</option>
								<option value="2">주문자명</option>
								<option value="3">상품명</option>
								<option value="4">상품번호</option>
							</select>
						</div>
						<input name="searchKeyword" class="form-control "/>
					</div>
				</div>
			</div>
			<div class="col-sm-1">
				<button type="button" id="searchOrderDlvyBtn" class="btn btn-dark btn-block"><i class="fas fa-search"></i> 검색</button>
			</div>
			</div>				
	</fieldset><!-- 검색 -->
	
	<div class="mt-1">
		<div id="data-grid"></div>
		<div id="data-grid-pagination" class="tui-pagination"></div>
	</div>
	
	</div>
</div>
	<c:import url="${CTX_ROOT}/decms/shop/goods/orderDlvyHist.do"></c:import>
	<c:import url="${CTX_ROOT}/decms/shop/goods/orderReason.do"></c:import>
	<javascript>
		<script src="${CTX_ROOT}/resources/lib/tui/tui-code-snippet/tui-code-snippet.min.js"></script>
		<script src="${CTX_ROOT}/resources/lib/tui/tui-pagination/tui-pagination.min.js"></script>
		<script src="${CTX_ROOT}/resources/lib/tui/tui-grid/tui-grid.min.js"></script>
		<script src="${CTX_ROOT}/resources/lib/tempusdominus-bootstrap-4/js/tempusdominus-bootstrap-4.min.js"></script>
		<script src="${CTX_ROOT}/resources/lib/jquery-ui/jquery-ui.min.js"></script>
		<script src="${CTX_ROOT}/resources/decms/shop/goods/order/js/orderExchangeManage.js?201022"></script>
	</javascript>
</body>
</html>