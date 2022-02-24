<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/modoo/common/commonTagLibs.jsp"%>
<c:set var="pageTitle" value="베스트 등록"/>
<c:set var="mode" value="insert"/>
<c:if test="${not empty best.bestNo}">
	<c:set var="pageTitle" value="베스트 수정"/>
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
	<link href="${CTX_ROOT }/resources/lib/open-iconic-master/font/css/open-iconic-bootstrap.css" rel="stylesheet">
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
					    <div class="form-row">
					    <div class="form-group col-sm-4">
					    	<label for="bestTyCode">대표유형</label>
						      <select id="bestTyCode" class="form-control">
						        <option value="" <c:if test="${best.bestTyCode eq null}">selected</c:if>>==선택==</option>
						    	<option value="B" <c:if test="${best.bestTyCode eq 'B'}">selected</c:if>>브랜드관</option>
						    	<option value="E" <c:if test="${best.bestTyCode eq 'E'}">selected</c:if>>이벤트관</option>
						    	<option value="T" <c:if test="${best.bestTyCode eq 'T'}">selected</c:if>>테마관</option>
						    	<option value="P" <c:if test="${best.bestTyCode eq 'P'}">selected</c:if>>기획전</option>
						    	<option value="G" <c:if test="${best.bestTyCode eq 'G'}">selected</c:if>>공동구매</option>
						      </select>
					    </div>
					    <div class="form-group col-sm-8">
					    	<label for="bestUrl" class="required">대표URL</label>
					    	<a href="#"><button type="button" onclick="deleteUrl();" class="btn btn-sm btn-danger">삭제</button></a>
					      <input type="text" class="form-control" id="bestUrl" placeholder="Search..." readonly="readonly" value="${best.bestUrl}">
					    </div>
					  </div>
					  	<label for="reprsntSj" class="required">대표제목</label>
					    <input type="text" class="form-control" id="reprsntSj" value="${best.reprsntSj}"  placeholder="직접 입력(10자 이내)">
					    <input type="hidden" id="bestNo" value="${best.bestNo}">
					</div>
				</div>
				<div class="col-sm">
					  <div class="form-group">
					    <label for="reprsntText" class="required">대표 문구</label>
					    <input type="text" class="form-control" id="reprsntText" value="${best.reprsntText}" placeholder="15자 이내">
					  </div>
				</div>
			</div>
			<div class="row">
				<div class="col-sm">
					<div class="form-group">
					    <label for="expsrBeginDe" class="required">노출 시작일</label>
					    <div class="input-group input-group-sm" id="expsrBeginDe" data-target-input="nearest">
							<input type="text" name="expsrBeginDe" class="form-control datetimepicker-input" data-target="#expsrBeginDe" value="<fmt:formatDate value="${best.expsrBeginDe}" pattern="yyyy-MM-dd" />"/>
							<div class="input-group-append" data-target="#expsrBeginDe" data-toggle="datetimepicker">
								<div class="input-group-text"><i class="fas fa-calendar"></i></div>
							</div>
						</div>
					</div>
				</div>
				<div class="col-sm">
					<div class="form-group">
					    <label for="expsrEndDe" class="required">노출 종료일</label>
					    <div class="input-group input-group-sm" id="expsrEndDe" data-target-input="nearest">
							<input type="text" name="expsrEndDe" class="form-control datetimepicker-input" data-target="#expsrEndDe" value="<fmt:formatDate value="${best.expsrEndDe}" pattern="yyyy-MM-dd" />"/>
							<div class="input-group-append" data-target="#expsrEndDe" data-toggle="datetimepicker">
								<div class="input-group-text"><i class="fas fa-calendar"></i></div>
							</div>
						</div>
					</div>
				</div>
				<div class="col-sm">
					<div class="form-group">
				      <label for="actvtyAt" class="required">노출 상태</label>
				      <select id="actvtyAt" class="form-control">
				        <option value="" <c:if test="${best.actvtyAt eq null}">selected</c:if>>==선택==</option>
				        <option value="Y" <c:if test="${best.actvtyAt eq 'Y'}">selected</c:if>>진행중</option>
				        <option value="N" <c:if test="${best.actvtyAt eq 'N'}">selected</c:if>>종료</option>
				      </select>
				    </div>
				</div>
				<div class="col-sm">
					<div class="form-group">
					    <label for="prtnrId">노출구분(제휴사)</label>
					    <select class="form-control" id="prtnrId" name="prtnrId" disabled="disabled">
							<option value="" <c:if test="${best.prtnrId eq null}">selected</c:if>>미노출</option>
							<c:forEach var="prtnr" items="${prtnrList}">
								<option value="${prtnr.prtnrId}" <c:if test="${prtnr.prtnrId eq best.prtnrId || prtnr.prtnrId eq param.prtnrId}">selected</c:if>>${prtnr.prtnrNm}</option>
							</c:forEach>
					    </select>
					  </div>
				</div>
				<div class="col-sm">
					<div class="form-group">
					    <label for="expsrOrdr">노출 순서</label>
					    <input type="text" class="form-control" id="expsrOrdr" value="${best.expsrOrdr}">
					</div>
				</div>
			</div>
			
			<div class="row">
				<div class="col-sm-3">
					<div class="form-group">
					    <label for="bestThumbnail" class="required">리스트 썸네일 : 가로 975 X 세로 564</label>
					    <input type="file" class="form-control-file" id="bestThumbnail">
					    <div id="delete-bestThumbnail">
<%-- 					    	<c:if test="${not empty best.eventThumbnail}"> --%>
<!-- 					    		<button type="button" onclick="deleteImg('eventThumbnail');" class="btn btn-area">삭제</button> -->
<%-- 					    	</c:if> --%>
					    </div>
					    <div id="bestThumbnailResult" class="img-result" style="background-position: center center;
							background-origin : padding-box;
							background-size: contain;
							background-repeat: no-repeat;
							border: 1px dashed gray;
							width: 100%;
							height: 250px;
							background-image: url('${best.bestThumbnail}');">
					    </div>
					 </div>
				</div>
				<div class="col-sm-3">
					<div class="form-group">
					    <label for="bestImgPc">메인 배너(PC)</label>
					    <input type="file" class="form-control-file" id="bestImgPc">
					    <div id="delete-bestImgPc">
					    	<c:if test="${not empty best.bestImgPc}">
					    		<button type="button" onclick="deleteImg('bestImgPc');" class="btn btn-area">삭제</button>
					    	</c:if>
					    </div>
					    <div id="bestImgPcResult" class="img-result" style="
					    	background-position: center center;
							background-origin : padding-box;
							background-size: contain;
							background-repeat: no-repeat;
							border: 1px dashed gray;
							width: 100%;
							height: 250px;
							background-image: url('${best.bestImgPc}');">
					    </div>
					 </div>
				</div>
				<div class="col-sm-3">
					<div class="form-group">
					    <label for="bestImgMob">메인 배너(MOBILE)</label>
					    <input type="file" class="form-control-file" id="bestImgMob">
					    <div id="delete-bestImgMob">
					    	<c:if test="${not empty best.bestImgMob}">
					    		<button type="button" onclick="deleteImg('bestImgMob');" class="btn btn-area">삭제</button>
					    	</c:if>
					    </div>
					    <div id="bestImgMobResult" class="img-result" style="
						    background-position: center center;
							background-origin : padding-box;
							background-size: contain;
							background-repeat: no-repeat;
							border: 1px dashed gray;
							width: 100%;
							height: 250px;
							background-image: url('${best.bestImgMob}');">
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
			<div class="row">
				<div class="col-sm">
					<button type="button" class="btn btn-primary btn-area" onclick="saveBestInfo('${mode}');">저장</button>
				</div>
			</div>
		</div>
		</form:form>
	</div>
</div>

	<!-- 브랜드관 Modal -->
	<div class="modal fade bd-example-modal-lg" id="brandSearchModal" tabindex="-1" role="dialog" aria-labelledby="brandSearchModalLabel" aria-hidden="true">
		<div class="modal-dialog modal-lg" role="document">
			<div class="modal-content">
			<div class="modal-header">
				<h5 class="modal-title" id="brandSearchModalLabel">브랜드 선택</h5>
				<button type="button" class="close" data-dismiss="modal" aria-label="Close">
				<span aria-hidden="true">&times;</span>
				</button>
			</div>
			<div class="modal-body">
				<div class="brand-grid">
					<div id="data-brand-grid" class="mt-3"></div>
					<div id="data-brand-grid-pagination" class="tui-pagination"></div>
				</div>
			</div>
			<div class="modal-footer">
			
			</div>
			</div>
		</div>
	</div>
	
	<!-- 이벤트관 Modal -->
	<div class="modal fade bd-example-modal-lg" id="eventSearchModal" tabindex="-1" role="dialog" aria-labelledby="eventSearchModalLabel" aria-hidden="true">
		<div class="modal-dialog modal-lg" role="document">
			<div class="modal-content">
			<div class="modal-header">
				<h5 class="modal-title" id="eventSearchModalLabel">이벤트 선택</h5>
				<button type="button" class="close" data-dismiss="modal" aria-label="Close">
				<span aria-hidden="true">&times;</span>
				</button>
			</div>
			<div class="modal-body">
				<div class="event-grid">
					<div id="data-event-grid" class="mt-3"></div>
					<div id="data-event-grid-pagination" class="tui-pagination"></div>
				</div>
			</div>
			<div class="modal-footer">
			
			</div>
			</div>
		</div>
	</div>
	
	<!-- 테마관 Modal -->
	<div class="modal fade bd-example-modal-lg" id="themeSearchModal" tabindex="-1" role="dialog" aria-labelledby="themeSearchModalLabel" aria-hidden="true">
		<div class="modal-dialog modal-lg" role="document">
			<div class="modal-content">
			<div class="modal-header">
				<h5 class="modal-title" id="themeSearchModalLabel">테마 선택</h5>
				<button type="button" class="close" data-dismiss="modal" aria-label="Close">
				<span aria-hidden="true">&times;</span>
				</button>
			</div>
			<div class="modal-body">
				<div class="theme-grid">
					<div id="data-theme-grid" class="mt-3"></div>
					<div id="data-theme-grid-pagination" class="tui-pagination"></div>
				</div>
			</div>
			<div class="modal-footer">
			
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
		<script src="${CTX_ROOT}/resources/decms/best/js/bestForm.js?202012"></script>
		<script src="${CTX_ROOT}/resources/decms/best/js/bestBrandPopList.js"></script>
		<script src="${CTX_ROOT}/resources/decms/best/js/bestEventPopList.js"></script>
		<script src="${CTX_ROOT}/resources/decms/best/js/bestThemePopList.js"></script>
	</javascript>

</body>
</html>