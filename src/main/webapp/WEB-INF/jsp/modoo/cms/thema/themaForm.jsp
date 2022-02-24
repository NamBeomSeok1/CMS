<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/modoo/common/commonTagLibs.jsp"%>
<c:set var="pageTitle" value="테마등록"/>
<c:set var="mode" value="insert"/>
<c:if test="${not empty goodsThema}">
	<c:set var="pageTitle" value="테마수정"/>
	<c:set var="mode" value="update"/>
</c:if>
<html>
<head>
	<meta charset="UTF-8">
	<title>${pageTitle}</title>
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
<body>

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
					    <label for="themaSj" class="required">테마명</label>
					    <input type="text" class="form-control" id="themaSj" value="${goodsThema.themaSj}">
					    <input type="hidden" id="themaNo" value="${goodsThema.themaNo}">
					</div>
				</div>
			</div>
			<div class="row">
				<div class="col-sm">
					<div class="form-group">
					    <label for="themaBeginDt" class="required">테마 시작일시</label>
					    <div class="input-group input-group-sm" id="themaBeginDt" data-target-input="nearest">
					    	<input type="text" name="themaBeginDt" class="form-control datetimepicker-input" data-target="#themaBeginDt" value="<fmt:formatDate value="${goodsThema.themaBeginDt}" pattern="yyyy-MM-dd" />"/>	
							<div class="input-group-append" data-target="#themaBeginDt" data-toggle="datetimepicker">
								<div class="input-group-text"><i class="fas fa-calendar"></i></div>
							</div>
						</div>
					</div>
				</div>
				<div class="col-sm">
					<div class="form-group">
					    <label for="themaEndDt" class="required">테마 종료일시</label>
					    <div class="input-group input-group-sm" id="themaEndDt" data-target-input="nearest">
							<input type="text" name="themaEndDt" class="form-control datetimepicker-input" data-target="#themaEndDt" value="<fmt:formatDate value="${goodsThema.themaEndDt}" pattern="yyyy-MM-dd" />"/>
							<div class="input-group-append" data-target="#themaEndDt" data-toggle="datetimepicker">
								<div class="input-group-text"><i class="fas fa-calendar"></i></div>
							</div>
						</div>
					</div>
				</div>
				<div class="col-sm">
					<div class="form-group">
				      <label for="endAt" class="required">테마 상태</label>
				      <select id="endAt" class="form-control">
				        <option value="" <c:if test="${empty goodsThema.endAt}">selected</c:if>>==선택==</option>
				        <option value="N" <c:if test="${goodsThema.endAt eq 'N'}"> selected </c:if> >진행중</option>
				        <option value="Y" <c:if test="${goodsThema.endAt eq 'Y'}"> selected </c:if> >종료</option>
				      </select>
				    </div>
				</div>
				<div class="col-sm">
					<div class="form-group">
					    <label for="prtnrId">테마 노출구분(제휴사)</label>
					    <select class="form-control" id="prtnrId" name="prtnrId">
							<option value="" <c:if test="${goodsThema.prtnrId eq null}">selected</c:if>>모두 노출</option>
							<c:forEach var="prtnr" items="${prtnrList}">
								<option value="${prtnr.prtnrId}" <c:if test="${prtnr.prtnrId eq goodsThema.prtnrId}">selected</c:if>>${prtnr.prtnrNm}</option>
							</c:forEach>
					    </select>
					  </div>
				</div>
				<div class="col-sm">
					<div class="form-group">
 					    <label for="themaCnt">테마 순서</label> 
 					    <c:if test="${mode eq 'update'}">
					    	<input type="number" min="0" name="themaSn" class="form-control" id="themaSn" value="${goodsThema.themaSn}">
					    </c:if>
 					    <c:if test="${mode eq 'insert'}">
					    	<input type="number" min="0" name="themaSn" class="form-control" id="themaSn" value="${nextSn}">
					    </c:if>
					</div>
				</div> 
			</div>
			
			<div class="row">
				<div class="col-sm">
					<div class="form-group">
					    <label for="themaUrl">테마 URL</label>
					    <small>(FOXEDU STORE 내 페이지가 아닌 외부 URL일 경우 https:// 부터 전체 URL 입력)</small>
					    <div class="input-group mb-3">
						  <div class="input-group-prepend">
						    <span class="input-group-text" id="basic-addon3">https://store.foxedu.co.kr</span>
						  </div>
						  <input type="text" class="form-control" id="themaUrl" aria-describedby="basic-addon3" value="${goodsThema.themaUrl}">
						</div>
					</div>
					  <div class="form-group">
					    <label for="themaCn" class="required">테마 대표문구</label>
					    <textarea class="form-control" maxlength="15" placeholder="15자 이내" id="themaCn" rows="1">${goodsThema.themaCn}</textarea>
					  </div>
				</div>
				<div class="col-sm">
					<div class="form-group">
					    <label for="data-grid">테마 상품 목록</label>
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
					    <label for="themaThumbnail" class="required">리스트 썸네일 : 가로 453 X 세로 260</label>
					    <input type="file" class="form-control-file" id="themaThumbnail">
					    <div id="delete-themaThumbnail">
<%-- 					    	<c:if test="${not empty goodsThema.themaThumbnail}"> --%>
<!-- 					    		<button type="button" onclick="deleteImg('themaThumbnail');" class="btn btn-area">삭제</button> -->
<%-- 					    	</c:if> --%>
					    </div>
					    <div id="themaThumbnailResult" class="img-result" style="background-position: center center;
							background-origin : padding-box;
							background-size: contain;
							background-repeat: no-repeat;
							border: 1px dashed gray;
							width: 100%;
							height: 250px;
							background-image: url('${goodsThema.themaThumbnail}');">
					    </div>
					 </div>
				</div>
				<div class="col-sm-3">
					<div class="form-group">
					    <label for="themaMainImgPc">메인 배너(PC) : 가로 1400 X 세로 150</label>
					    <input type="file" class="form-control-file" id="themaMainImgPc">
					    <div id="delete-themaMainImgPc">
					    	<c:if test="${not empty goodsThema.themaMainImgPc}">
					    		<button type="button" onclick="deleteImg('themaMainImgPc');" class="btn btn-area">삭제</button>
					    	</c:if>
					    </div>
					    <div id="themaMainImgPcResult" class="img-result" style="
					    	background-position: center center;
							background-origin : padding-box;
							background-size: contain;
							background-repeat: no-repeat;
							border: 1px dashed gray;
							width: 100%;
							height: 250px;
							background-image: url('${goodsThema.themaMainImgPc}');">
					    </div>
					 </div>
				</div>
				<div class="col-sm-3">
					<div class="form-group">
					    <label for="themaMainImgMob">메인 배너(MOBILE) : 가로 975 X 세로 240</label>
					    <input type="file" class="form-control-file" id="themaMainImgMob">
					    <div id="delete-themaMainImgMob">
					    	<c:if test="${not empty goodsThema.themaMainImgMob}">
					    		<button type="button" onclick="deleteImg('themaMainImgMob');" class="btn btn-area">삭제</button>
					    	</c:if>
					    </div>
					    <div id="themaMainImgMobResult" class="img-result" style="
						    background-position: center center;
							background-origin : padding-box;
							background-size: contain;
							background-repeat: no-repeat;
							border: 1px dashed gray;
							width: 100%;
							height: 250px;
							background-image: url('${goodsThema.themaMainImgMob}');">
					    </div>
					    
					 </div>
				</div>
			</div>
			
			
			<div class="row">
				<div class="col-sm">
					<button type="button" class="btn btn-primary btn-area" onclick="saveThemaInfo('${mode}');">저장</button>
				</div>
			</div>
		</div>
		</form:form>
	</div>
</div>

	<%-- <!-- 업체 모달  -->
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
	</div> --%>
	
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

	<!-- 테마 유형 편집 -->
	<div class="modal fade bd-example-modal-sm" id="editThemaTyPop" tabindex="-1" role="dialog" aria-labelledby="mySmallModalLabel" aria-hidden="true">
	  <button type="button" class="btn btn-primary" data-toggle="modal" data-target=".bd-example-modal-sm">Small modal</button>
	  <div class="modal-dialog modal-sm">
	    <div class="modal-content">
	    	<input type="hidden" id="rowKey">
	    	<select id="themaTyCode">
	    		<option value="">없음</option>
	    		<option value="EV01">한 ID당 상품 한 번만 구매 가능</option>
	    	</select>
	    	<button type="button" onclick="changeThemaTyCode();" class="btn btn-primary">적용</button>
	    </div>
	  </div>
	</div>
	
	<javascript>
		<script src="${CTX_ROOT}/resources/lib/tui/tui-code-snippet/tui-code-snippet.min.js"></script>
		<script src="${CTX_ROOT}/resources/lib/tui/tui-pagination/tui-pagination.min.js"></script>
		<script src="${CTX_ROOT}/resources/lib/tui/tui-grid/tui-grid.min.js"></script>
		<script src="${CTX_ROOT}/resources/lib/tempusdominus-bootstrap-4/js/tempusdominus-bootstrap-4.min.js"></script>
		<script src="${CTX_ROOT}/resources/lib/jquery-ui/jquery-ui.min.js"></script>
		<script src="${CTX_ROOT}/resources/decms/thema/js/themaForm.js?202012"></script>
		<script src="${CTX_ROOT}/resources/decms/thema/js/themaFormGoodsRecomendList.js"></script>
	</javascript>

</body>
</html>