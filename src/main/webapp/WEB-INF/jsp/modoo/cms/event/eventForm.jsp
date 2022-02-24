<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/modoo/common/commonTagLibs.jsp"%>
<c:set var="pageTitle" value="이벤트 등록"/>
<c:set var="mode" value="insert"/>
<c:if test="${not empty goodsEvent.eventNo}">
	<c:set var="pageTitle" value="이벤트 수정"/>
	<c:set var="mode" value="update"/>
</c:if>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>${pageTitle }</title>
	<link rel="stylesheet" type="text/css" href="${CTX_ROOT }/resources/lib/jquery-ui/jquery-ui.min.css"/>
	<link rel="stylesheet" type="text/css" href="${CTX_ROOT }/resources/lib/tui/tui-grid/tui-grid.min.css"/>
	<link rel="stylesheet" type="text/css" href="${CTX_ROOT }/resources/lib/tui/tui-pagination/tui-pagination.min.css"/>
	<link rel="stylesheet" type="text/css" href="${CTX_ROOT }/resources/lib/tempusdominus-bootstrap-4/css/tempusdominus-bootstrap-4.min.css"/>
	<style>
		.btn-area {
			float: right;
		}
	</style>
</head>
<body onclick="loadEventInfo('${mode}', '${param.eventNo}');">

<div class="card shadow page-wrapper">
	<div class="card-header">
		<h6 class="m-0 font-weight-bold text-primary">${pageTitle }</h6>
	</div>
	<div class="card-body">
		<form:form>
		<div class="container-fluid">
			<div class="row">
				<div class="col-sm">
					<div class="form-group">
					    <label for="eventSj" class="required">이벤트명</label>
					    <input type="text" class="form-control" id="eventSj" value="${goodsEvent.eventSj}">
					    <input type="hidden" id="eventNo" value="${goodsEvent.eventNo}">
					</div>
				</div>
				<div class="col-sm">
					<div class="form-group">
					    <label for="cmpnyNm">업체 선택</label>
					    <a href="#"><button type="button" onclick="deleteCmpny();" class="btn btn-sm btn-danger">업체 삭제</button></a>
					    <input type="text" class="form-control" id="cmpnyNm" readonly="readonly" value="${goodsEvent.cmpnyNm}" placeholder="미선택 시 FOXEDU STORE">
					    <input type="hidden" id="cmpnyId" value="${goodsEvent.cmpnyId}">
					</div>
				</div>
			</div>
			<div class="row">
				<div class="col-sm">
					<div class="form-group">
					    <label for="eventBeginDt" class="required">이벤트 시작일시</label>
					    <div class="input-group input-group-sm" id="eventBeginDt" data-target-input="nearest">
							<input type="text" name="eventBeginDt" class="form-control datetimepicker-input" data-target="#eventBeginDt" value="<fmt:formatDate value="${goodsEvent.eventBeginDt}" pattern="yyyy-MM-dd" />"/>
							<div class="input-group-append" data-target="#eventBeginDt" data-toggle="datetimepicker">
								<div class="input-group-text"><i class="fas fa-calendar"></i></div>
							</div>
						</div>
					</div>
				</div>
				<div class="col-sm">
					<div class="form-group">
					    <label for="eventEndDt" class="required">이벤트 종료일시</label>
					    <div class="input-group input-group-sm" id="eventEndDt" data-target-input="nearest">
							<input type="text" name="eventEndDt" class="form-control datetimepicker-input" data-target="#eventEndDt" value="<fmt:formatDate value="${goodsEvent.eventEndDt}" pattern="yyyy-MM-dd" />"/>
							<div class="input-group-append" data-target="#eventEndDt" data-toggle="datetimepicker">
								<div class="input-group-text"><i class="fas fa-calendar"></i></div>
							</div>
						</div>
					</div>
				</div>
				<div class="col-sm">
					<div class="form-group">
				      <label for="endAt" class="required">이벤트 상태</label>
				      <select id="endAt" class="form-control">
				        <option value="" <c:if test="${goodsEvent.endAt eq null}">selected</c:if>>==선택==</option>
				        <option value="N" <c:if test="${goodsEvent.endAt eq 'N'}">selected</c:if>>진행중</option>
				        <option value="Y" <c:if test="${goodsEvent.endAt eq 'Y'}">selected</c:if>>종료</option>
				      </select>
				    </div>
				</div>
				<div class="col-sm">
					<div class="form-group">
					    <label for="prtnrId">이벤트 노출구분(제휴사)</label>
					    <select class="form-control" id="prtnrId" name="prtnrId">
							<option value="" <c:if test="${goodsEvent.prtnrId eq null}">selected</c:if>>미노출</option>
							<c:forEach var="prtnr" items="${prtnrList}">
								<option value="${prtnr.prtnrId}" <c:if test="${prtnr.prtnrId eq goodsEvent.prtnrId}">selected</c:if>>${prtnr.prtnrNm}</option>
							</c:forEach>
					    </select>
					  </div>
				</div>
				<div class="col-sm">
					<div class="form-group">
<!-- 					    <label for="eventCnt">수량 제한(선착순)</label> -->
					    <input type="hidden" class="form-control" id="eventCnt" value="${goodsEvent.eventCnt}">
					</div>
				</div>
			</div>
			
			<div class="row">
				<div class="col-sm">
					<div class="form-group">
					    <label for="eventUrl">이벤트 URL</label>
					    <small>(FOXEDU STORE 내 페이지가 아닌 외부 URL일 경우 https:// 부터 전체 URL 입력)</small>
					    <div class="input-group mb-3">
						  <div class="input-group-prepend">
						    <span class="input-group-text" id="basic-addon3">https://FOXEDU STORE</span>
						  </div>
						  <input type="text" class="form-control" id="eventUrl" aria-describedby="basic-addon3" value="${goodsEvent.eventUrl}">
						</div>
					</div>
					  <div class="form-group">
					    <label for="eventCn" class="required">이벤트 설명</label>
					    <textarea class="form-control" id="eventCn" rows="3">${goodsEvent.eventCn}</textarea>
					  </div>
				</div>
				<div class="col-sm">
					<div class="form-group">
					    <label for="data-grid">이벤트 상품 목록</label>
					    <p>(이벤트 상품을 추가하면 해당 상품 브랜드에 [사이트 관리 > 브랜드 관리]에서 등록한 이벤트 배너가 노출됩니다.)</p>
				    <a href="#"><button type="button" class="btn btn-sm btn-danger btn-area btnDeleteRecomend">상품 삭제</button></a>
					<a href="#"><button type="button" class="btn btn-sm btn-info btn-area btnAddRecomend">상품 추가</button></a>
					<div id="data-grid"></div>
					<div id="data-grid-pagination" class="tui-pagination"></div>
					</div>
				</div>
			</div>
			
			<div class="row">
				<div class="col-sm-3">
					<div class="form-group">
					    <label for="eventThumbnail" class="required">리스트 썸네일 : 가로 975 X 세로 564</label>
					    <input type="file" class="form-control-file" id="eventThumbnail">
					    <div id="delete-eventThumbnail">
<%-- 					    	<c:if test="${not empty goodsEvent.eventThumbnail}"> --%>
<!-- 					    		<button type="button" onclick="deleteImg('eventThumbnail');" class="btn btn-area">삭제</button> -->
<%-- 					    	</c:if> --%>
					    </div>
					    <div id="eventThumbnailResult" class="img-result" style="background-position: center center;
							background-origin : padding-box;
							background-size: contain;
							background-repeat: no-repeat;
							border: 1px dashed gray;
							width: 100%;
							height: 250px;
							background-image: url('${goodsEvent.eventThumbnail}');">
					    </div>
					 </div>
				</div>
				<div class="col-sm-3">
					<div class="form-group">
					    <label for="eventMainImgPc">메인 배너(PC) : 가로 1400 X 세로 150</label>
					    <input type="file" class="form-control-file" id="eventMainImgPc">
					    <div id="delete-eventMainImgPc">
					    	<c:if test="${not empty goodsEvent.eventMainImgPc}">
					    		<button type="button" onclick="deleteImg('eventMainImgPc');" class="btn btn-area">삭제</button>
					    	</c:if>
					    </div>
					    <div id="eventMainImgPcResult" class="img-result" style="
					    	background-position: center center;
							background-origin : padding-box;
							background-size: contain;
							background-repeat: no-repeat;
							border: 1px dashed gray;
							width: 100%;
							height: 250px;
							background-image: url('${goodsEvent.eventMainImgPc}');">
					    </div>
					 </div>
				</div>
				<div class="col-sm-3">
					<div class="form-group">
					    <label for="eventMainImgMob">메인 배너(MOBILE) : 가로 975 X 세로 240</label>
					    <input type="file" class="form-control-file" id="eventMainImgMob">
					    <div id="delete-eventMainImgMob">
					    	<c:if test="${not empty goodsEvent.eventMainImgMob}">
					    		<button type="button" onclick="deleteImg('eventMainImgMob');" class="btn btn-area">삭제</button>
					    	</c:if>
					    </div>
					    <div id="eventMainImgMobResult" class="img-result" style="
						    background-position: center center;
							background-origin : padding-box;
							background-size: contain;
							background-repeat: no-repeat;
							border: 1px dashed gray;
							width: 100%;
							height: 250px;
							background-image: url('${goodsEvent.eventMainImgMob}');">
					    </div>
					    
					 </div>
				</div>
				<div class="col-sm-3">
<!-- 					<div class="form-group"> -->
<!-- 					    <label for="eventBannerImg">배너</label> -->
<!-- 					    <input type="file" class="form-control-file" id="eventBannerImg"> -->
<!-- 					    <div id="eventBannerImgResult" class="img-result" style="border: 1px dashed gray; width: 100%; height: 250px;"></div> -->
<!-- 					 </div> -->
				</div>
			</div>
			
<!-- 			<div class="row"> -->
<!-- 				<div class="col-sm-3"> -->
<!-- 					<div class="form-group"> -->
<!-- 					    <label for="eventDetailImg">상세페이지</label> -->
<!-- 					    <input type="file" class="form-control-file" id="eventDetailImg"> -->
<!-- 					    <div id="eventDetailImgResult" class="img-result" style="border: 1px dashed gray; width: 100%; height: 250px;"> -->
<!-- 					    	가로 1400 X 세로 제한없음 -->
<!-- 					    </div> -->
<!-- 					 </div> -->
<!-- 				</div> -->
<!-- 				<div class="col-sm-3"> -->
<!-- 					<div class="form-group"> -->
<!-- 					    <label for="eventBrandImgPc">브랜드관 배너(PC)</label> -->
<!-- 					    <input type="file" class="form-control-file" id="eventBrandImgPc"> -->
<!-- 					    <div id="eventBrandImgPcResult" class="img-result" style="border: 1px dashed gray; width: 100%; height: 250px;"> -->
<!-- 					    	가로 1400 X 세로 150 -->
<!-- 					    </div> -->
<!-- 					 </div> -->
<!-- 				</div> -->
<!-- 				<div class="col-sm-3"> -->
<!-- 					<div class="form-group"> -->
<!-- 					    <label for="eventBrandImgMobile">브랜드관 배너(MOBILE)</label> -->
<!-- 					    <input type="file" class="form-control-file" id="eventBrandImgMobile"> -->
<!-- 					    <div id="eventBrandImgMobileResult" class="img-result" style="border: 1px dashed gray; width: 100%; height: 250px;"> -->
<!-- 					    	가로 1125 X 세로 300 -->
<!-- 					    </div> -->
<!-- 					 </div> -->
<!-- 				</div> -->
<!-- 				<div class="col-sm-3"> -->
<!-- 				</div> -->
<!-- 			</div> -->
			
			<div class="row">
				<div class="col-sm">
					<button type="button" class="btn btn-primary btn-area" onclick="saveEventInfo('${mode}');">저장</button>
				</div>
			</div>
<!-- 		<div class="row"> -->
<!-- 			<div class="col-sm"> -->
<!-- 				<a href="#"><button type="button" class="btn btn-info btn-area" onclick="initEditPop();">배너 등록</button></a> -->
<!-- 				<div class="form-group"> -->
<!-- 				    <label for="data-grid-banner">이벤트 배너 관리</label> -->
<!-- 				<div id="data-grid-banner"></div> -->
<!-- 				<div id="data-grid-pagination-banner" class="tui-pagination"></div> -->
<!-- 				</div> -->
<!-- 			</div> -->
<!-- 		</div> -->
<!-- 		<div class="row"> -->
<!-- 			<div class="col-sm"> -->
<!-- 				<a href="#"><button type="button" class="btn btn-info btn-area" onclick="initEditPop();">상품 등록</button></a> -->
<!-- 				<div class="form-group"> -->
<!-- 				    <label for="data-grid">이벤트 상품 목록</label> -->
<!-- 				<div id="data-grid"></div> -->
<!-- 				<div id="data-grid-pagination" class="tui-pagination"></div> -->
<!-- 				</div> -->
<!-- 			</div> -->
<!-- 		</div> -->
		</div>
		</form:form>
	</div>
</div>

	<!-- 업체 모달  -->
	<div class="modal fade" id="cmpnyListModal" tabindex="-1" role="dialog" aria-labelledby="cmpnyListModalLabel" aria-hidden="true">
		<div class="modal-dialog modal-lg modal-dialog-centered modal-dialog-scrollable modal-dark">
			<div class="modal-content">
				<div class="modal-header">
					<h6 class="modal-title" id="cmpnyListModalLabel">업체목록</h6>
					<button type="button" class="close" data-dismiss="modal" aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
				</div>
				<div class="modal-body">
					<form name="cmpnySearchForm" id="cmpnySearchForm" method="get" action="${CTX_ROOT}/decms/shop/cmpny/cmpnyList.json">
						<fieldset>
							<input type="hidden" id="cmpnyPageIndex" name="pageIndex" value=""/>
						</fieldset>
						<div class="form-group row">
							<div class="col-auto">
								<label for="searchCondition" class="sr-only">검색분류</label>
								<select name="searchCondition" class="custom-select custom-select-sm">
									<option value="NM">이름</option>
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
<!-- 							<div class="col-auto"> -->
<!-- 								<button type="submit" class="btn btn-success" id="selectCmpny">업체 선택</button> -->
<!-- 							</div> -->
						</div>
					</form>
					<div class="cmpny-grid">
						<div id="data-cmpny-grid" class="mt-3"></div>
						<div id="data-cmpny-grid-pagination" class="tui-pagination"></div>
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

	<!-- 이벤트 유형 편집 -->
	<div class="modal fade bd-example-modal-sm" id="editEventTyPop" tabindex="-1" role="dialog" aria-labelledby="mySmallModalLabel" aria-hidden="true">
	  <button type="button" class="btn btn-primary" data-toggle="modal" data-target=".bd-example-modal-sm">Small modal</button>
	  <div class="modal-dialog modal-sm">
	    <div class="modal-content">
	    	<input type="hidden" id="rowKey">
	    	<select id="eventTyCode">
	    		<option value="">없음</option>
	    		<option value="EV01">한 ID당 상품 한 번만 구매 가능</option>
	    	</select>
	    	<button type="button" onclick="changeEventTyCode();" class="btn btn-primary">적용</button>
	    </div>
	  </div>
	</div>
	
	<javascript>
		<script src="${CTX_ROOT}/resources/lib/tui/tui-code-snippet/tui-code-snippet.min.js"></script>
		<script src="${CTX_ROOT}/resources/lib/tui/tui-pagination/tui-pagination.min.js"></script>
		<script src="${CTX_ROOT}/resources/lib/tui/tui-grid/tui-grid.min.js"></script>
		<script src="${CTX_ROOT}/resources/lib/tempusdominus-bootstrap-4/js/tempusdominus-bootstrap-4.min.js"></script>
		<script src="${CTX_ROOT}/resources/lib/jquery-ui/jquery-ui.min.js"></script>
		<script src="${CTX_ROOT}/resources/decms/event/js/eventForm.js?202012"></script>
		<script src="${CTX_ROOT}/resources/decms/event/js/eventFormCmpnyList.js?202012"></script>
		<script src="${CTX_ROOT}/resources/decms/event/js/eventFormGoodsRecomendList.js"></script>
	</javascript>

</body>
</html>