<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/modoo/common/commonTagLibs.jsp"%>
<c:set var="pageTitle" value="브랜드 등록"/>
<c:set var="mode" value="insert"/>
<%-- <c:set var="actionUrl" value="/decms/shop/goods/saveGoodsBrand.json"/ --%>>
<c:choose>
	<c:when test="${empty brand.brandId }">
		<c:set var="pageTitle" value="브랜드 등록"/>
		<c:set var="mode" value="insert"/>
	</c:when>
	<c:otherwise>
		<c:set var="pageTitle" value="브랜드 수정"/>
		<c:set var="mode" value="update"/>
	</c:otherwise>
</c:choose>
<html>
<head>
	<meta charset="UTF-8">
	<title>${pageTitle }</title>
	<link rel="stylesheet" type="text/css" href="${CTX_ROOT }/resources/lib/tui/tui-grid/tui-grid.min.css"/>
	<link rel="stylesheet" type="text/css" href="${CTX_ROOT }/resources/lib/tui/tui-pagination/tui-pagination.min.css"/>
	<link rel="stylesheet" type="text/css" href="${CTX_ROOT }/resources/lib/tempusdominus-bootstrap-4/css/tempusdominus-bootstrap-4.min.css"/>
	<link rel="stylesheet" type="text/css" href="${CTX_ROOT }/resources/decms/shop/goods/info/css/goodsForm.css?20200914"/>
	<style>
	#brand-image-file {
		width: 250px!important;
	</style>
</head>
<body>
	<div class="card shadow page-wrapper">
		<div class="card-header">
			<h6 class="m-0 font-weight-bold text-primary">${pageTitle }</h6>
		</div>
		
		<div class="card-body">
			<div id="brand-regist-form">
				<%-- <c:url var="actionUrl" value="/decms/shop/goods/saveGoodsBrand.json">
					<c:param name="isForm" value="Y"/>
					<c:param name="menuId" value="${param.menuId}"/>
				</c:url> --%>
				<form:form modelAttribute="brand" name="brandForm" id="brandForm" class="embedForm" method="post" enctype="multipart/form-data" action="${CTX_ROOT }/decms/shop/goods/saveGoodsBrand.json">
					<fieldset>
						<input type="hidden" name="isForm" value="Y"/>
						<input type="hidden" name="menuId" value="${param.menuId }"/>
						<form:hidden path="brandId"/>
						<form:hidden path="brandImagePath"/>
						<form:hidden path="brandImageThumbPath"/>
						<form:hidden path="cmpnyId"/>
						
						<%-- <input type="hidden" name="brandId" value="${brand.brandId}">
						<input type="hidden" name="brandImagePath" value="${brand.brandImagePath}">
						<input type="hidden" name="brandImageThumbPath" value="${brand.brandImageThumbPath}"> --%>
					</fieldset>
					<p>사진은 .jpg, .png, .jpeg 형식만 등록 가능합니다.</p>
					<c:if test="${fn:contains(USER_ROLE, 'ROLE_EMPLOYEE') }">
						<div class="form-group row">
							<label for="brandImage" class="col-sm-2 col-form-label col-form-label-sm">업체</label>
							<div class="input-group input-group-sm col-sm-4">
								<form:input path="cmpnyNm" cssClass="form-control form-control-sm bg-light" readonly="true" placeholder="업체를 선택하세요!" value="${brand.cmpnyNm}"/>
								<div class="input-group-append">
									<button type="button" class="btn btn-dark btn-sm btnSearchCmpny" data-target="#cmpnyListModal"><i class="fas fa-search"></i></button>
								</div>
							</div>
						</div>
						<hr class="sm">
					</c:if>

					<div class="form-group row">
						<label for="brandNm" class="col-sm-2 col-form-label col-form-label-sm required">브랜드명</label>
						<div class="col-sm-10">
							<form:input path="brandNm" cssClass="form-control form-control-sm"/>
						</div>
					</div>
					<hr class="sm">

					<div class="form-group row">
						<label for="brandExpsrSeCode" class="col-sm-2 col-form-label col-form-label-sm required">브랜드노출구분</label>
						<div class="col-sm-3">
							<form:select path="brandExpsrSeCode" cssClass="custom-select custom-select-sm">
								<form:option value="ALL">모두노출</form:option>
								<form:option value="B2B">B2B만 노출</form:option>
								<form:option value="B2C">B2C만 노출</form:option>
								<form:option value="NONE">미노출</form:option>
							</form:select>
						</div>
						<div class="col-sm-7"></div>
					</div>
					<hr class="sm">

					<div class="form-group row">
						<label for="brandLogo" class="col-sm-2 col-form-label col-form-label-sm required">브랜드로고</label>
						<div class="col-sm-6 ">
							<input type="file" id="brandAtachFile" accept=".jpg, .png, .jpeg" name="atchLogoFile" class="form-control form-control-sm">
						</div>
						<div class="col-sm-4">
							<div id="brand-image-file">
								<img id="brandLogo" src="${brand.brandImageThumbPath}" alt="">
							</div>
						</div>
					</div>
					<hr class="sm">
					<div class="form-group row">
						<label for="brandDesktopFile" class="col-sm-2 col-form-label col-form-label-sm required">브랜드 대표 이미지(컴퓨터,모바일)</label>
						<div class="col-sm-5">
							<div>
							<small>컴퓨터</small>
								<input type="file" id="brandDesktopFile" name="atchRepFile" accept=".jpg, .png, .jpeg" class="form-control form-control-sm"/>
							</div>
							<div>
							<small>모바일</small>
								<input type="file" id="brandMobileFile"  accept=".jpg, .png, .jpeg" name="atchRepMobFile" class="form-control form-control-sm"/>
							</div>
						</div>
						<div class="col-sm-5">
						<small>컴퓨터[SIZE : 1920 x 415(자동생성)]</small>
							<div id="brand-image-file" style="width:300px!important; height:150px!important;">
							<c:if test="${empty brand.repBrandImageList[0].brandImagePath}">
								<img id="brandDekImage" src="${CTX_ROOT }/resources/decms/common/image/brand_noimg_web.jpeg" alt="<c:out value="${brand.brandNm }"/>" style="width:250px; height:100px;"/>
							</c:if>
							<c:if test="${not empty brand.repBrandImageList[0].brandImagePath}">
								<img id="brandDekImage" src="${CTX_ROOT }${brand.repBrandImageList[0].brandImagePath}" alt="" style="width:250px; height:100px;">
							</c:if>
								<%-- <a href="${brand.dekBrandImageList[0].brandImagePath}" class="btn btn-dark btn-sm rounded-0 btnImgView" title="이미지 원본보기"><i class="fas fa-eye"></i></a>
								 <a href="${CTX_ROOT }/decms/shop/goods/deleteGoodsBrandImage.json?brandImageNo=${brand.dekBrandImageList[0].brandImageNo}" 
								class="btn btn-dark btn-sm rounded-0 btnDeleteImg" title="삭제" data-se-code="GNR"><i class="fas fa-times"></i></a>  --%>
							</div>
						<small>모바일[SIZE : 1200 x 600]</small>
							<div id="brand-image-file" style="height:150px!important; width:200px!important;">
							<c:if test="${empty brand.repMobBrandImageList[0].brandImagePath}">
								<img id="brandMobImage" src="${CTX_ROOT }/resources/decms/common/image/brand_noimg_mob.jpeg" alt="<c:out value="${brand.brandNm }"/>" style="width:100px; height:100px;"/>
							</c:if>
							<c:if test="${not empty brand.repMobBrandImageList[0].brandImagePath}">
								<img id="brandMobImage" src="${CTX_ROOT }${brand.repMobBrandImageList[0].brandImagePath}" alt="" style="width:100px; height:100px;">
							</c:if>
								<%-- <a href="${brand.dekBrandImageList[0].brandImagePath}" class="btn btn-dark btn-sm rounded-0 btnImgView" title="이미지 원본보기"><i class="fas fa-eye"></i></a>
								 <a href="${CTX_ROOT }/decms/shop/goods/deleteGoodsBrandImage.json?brandImageNo=${brand.dekBrandImageList[0].brandImageNo}" 
								class="btn btn-dark btn-sm rounded-0 btnDeleteImg" title="삭제" data-se-code="GNR"><i class="fas fa-times"></i></a>  --%>
							</div>
						</div>
					</div>
					<hr class="sm">
					<div class="form-group row">
						<label for="brandImage" class="col-sm-2 col-form-label col-form-label-sm">이벤트 배너(컴퓨터,모바일)
							<p>※이벤트 관리 페이지에서 진행중인 이벤트 상품이 1개 이상 등록되어 있을 경우에만 브랜드 상품 목록에 노출됩니다.</p>
						</label>
						<div class="col-sm-5">
							<div>
							<small>컴퓨터</small>
								<input type="file" id="eventDesktopFile" name="atchEvtFile" class="form-control form-control-sm"/>
							</div>
							<div>
							<small>모바일</small>
								<input type="file" id="eventMobileFile" name="atchEvtMobFile" class="form-control form-control-sm"/>
							</div>
						</div>
						<div class="col-sm-5">
						<small>컴퓨터[SIZE : 1400 x 150(자동생성)]</small>
							<div id="brand-image-file" style="width:300px!important; height:150px!important;">
							<c:if test="${empty brand.evtBrandImageList[0].brandImagePath}">
								<img id="evtDekImage" src="${CTX_ROOT }/resources/decms/common/image/brand_noimg_web.jpeg" alt="<c:out value="${brand.brandNm }"/>" style="width:250px; height:100px;"/>
							</c:if>
							<c:if test="${not empty brand.evtBrandImageList[0].brandImagePath}">
								<img id="evtDekImage" src="${CTX_ROOT }${brand.evtBrandImageList[0].brandImagePath}" alt="" style="width:250px; height:100px;">
							</c:if>
								<%-- <a href="${brand.dekBrandImageList[0].brandImagePath}" class="btn btn-dark btn-sm rounded-0 btnImgView" title="이미지 원본보기"><i class="fas fa-eye"></i></a>
								 <a href="${CTX_ROOT }/decms/shop/goods/deleteGoodsBrandImage.json?brandImageNo=${brand.dekBrandImageList[0].brandImageNo}" 
								class="btn btn-dark btn-sm rounded-0 btnDeleteImg" title="삭제" data-se-code="GNR"><i class="fas fa-times"></i></a>  --%>
							</div>
						<small>모바일[SIZE : 975 x 231]</small>
							<div id="brand-image-file" style="height:150px!important; width:200px!important;">
							<c:if test="${empty brand.evtMobBrandImageList[0].brandImagePath}">
								<img id="evtMobImage" src="${CTX_ROOT }/resources/decms/common/image/brand_noimg_mob.jpeg" alt="<c:out value="${brand.brandNm }"/>" style="width:100px; height:100px;"/>
							</c:if>
							<c:if test="${not empty brand.evtMobBrandImageList[0].brandImagePath}">
								<img id="evtMobImage" src="${CTX_ROOT }${brand.evtMobBrandImageList[0].brandImagePath}" alt="" style="width:100px; height:100px;">
							</c:if>
								<%-- <a href="${brand.dekBrandImageList[0].brandImagePath}" class="btn btn-dark btn-sm rounded-0 btnImgView" title="이미지 원본보기"><i class="fas fa-eye"></i></a>
								 <a href="${CTX_ROOT }/decms/shop/goods/deleteGoodsBrandImage.json?brandImageNo=${brand.dekBrandImageList[0].brandImageNo}" 
								class="btn btn-dark btn-sm rounded-0 btnDeleteImg" title="삭제" data-se-code="GNR"><i class="fas fa-times"></i></a>  --%>
							</div>
						</div>
					</div>
					 <hr class="sm">
					<div class="form-group row">
						<label for="brandImage" class="col-sm-2 col-form-label col-form-label-sm required">브랜드 소개 이미지</label>
						<div class="col-sm-5">
							<div>
								<input type="file" id="brandIntroImage" accept=".jpg, .png, .jpeg" name="atchIntFile" class="form-control form-control-sm" />
							</div>
						</div>
						<div class="col-sm-5">
						<small>[SIZE : 1400 x (최소 415~최대2000)]</small>
							<div id="brand-image-file" style="height:200px!important;">
								<img id="brandIntroImg" src="${brand.intBrandImageList[0].brandImagePath}" alt="">
								<%-- <a href="${brand.dekBrandImageList[0].brandImagePath}" class="btn btn-dark btn-sm rounded-0 btnImgView" title="이미지 원본보기"><i class="fas fa-eye"></i></a>
								 <a href="${CTX_ROOT }/decms/shop/goods/deleteGoodsBrandImage.json?brandImageNo=${brand.dekBrandImageList[0].brandImageNo}" 
								class="btn btn-dark btn-sm rounded-0 btnDeleteImg" title="삭제" data-se-code="GNR"><i class="fas fa-times"></i></a>  --%>
							</div>
						</div>
					</div>	
					<hr class="sm">
					<div class="form-group row">
						<label for="brandIntSj" class="col-sm-2 col-form-label col-form-label-sm">브랜드 소개 제목</label>
						<div class="col-sm-10">
							<form:input path="brandIntSj" cssClass="form-control form-control-sm"/>
						</div>
					</div>
					<hr class="sm">
					<div class="form-group row">
						<label for="brandIntCn" class="col-sm-2 col-form-label col-form-label-sm">브랜드 소개 글</label>
						<div class="col-sm-10">
							<form:textarea path="brandIntCn" class="form-control form-control-sm" rows="10"/>
						</div>
					</div>
				<%-- 	<hr class="sm">
					<div class="form-group row">
						<label for="brandIntLink" class="col-sm-2 col-form-label col-form-label-sm">브랜드 연결 링크</label>
						<div class="col-sm-10">
							<form:input path="brandIntLink" cssClass="form-control form-control-sm"/>
						</div>
					</div> --%>
					<hr class="sm">
					<div class="form-group row">
						<label for="brandIntGoods" class="col-sm-2 col-form-label col-form-label-sm">브랜드 대표 품목</label>
						<div class="col-sm-10">
							<form:input path="brandIntGoods" cssClass="form-control form-control-sm" placeholder="ex) 신선식품, 곡류, 즙 및 과일류, 생활용품 등 (10자 이내)" onkeyup="checkMaxLength(this)"/>
						</div>
					</div>
			 		<hr class="sm">
					<div class="form-group row brandGoods">
						<label for="brandBtcGoodsId" class="col-sm-2 col-form-label col-form-label-sm">B2C대표상품</label>
						<div class="col-sm-4">
							<div class="input-group input-group-sm">
								<input type="text" id="btcGoodsNm" class="form-control form-control-sm" value="${brand.brandBtcGoods.goodsNm}" readonly="readonly">
								<form:hidden path="brandBtcGoodsId" cssClass="form-control form-control-sm" maxlength="20" value="${brand.brandBtcGoodsId }"/>
								<div class="input-group-append">
									<button type="button" class="btn btn-dark btn-sm btnAddRecomend" data-prtnrid='PRTNR_0000' data-target="#recomendListModal"><i class="fas fa-plus"></i> 상품찾기</button>
								</div>
							</div>
						</div>
					</div>  
			 		<hr class="sm">
					<div class="form-group row brandGoods">
						<label for="brandBtbGoodsId" class="col-sm-2 col-form-label col-form-label-sm">B2B대표상품</label>
						<div class="col-sm-4">
							<div class="input-group input-group-sm">
								<input type="text" id="btbGoodsNm" class="form-control form-control-sm" value="${brand.brandBtbGoods.goodsNm}" readonly="readonly">
								<form:hidden path="brandBtbGoodsId" cssClass="form-control form-control-sm" maxlength="20" value="${brand.brandBtbGoodsId }"/>
								<div class="input-group-append">
									<button type="button" class="btn btn-dark btn-sm btnAddRecomend" data-prtnrid='PRTNR_0001' data-target="#recomendListModal"><i class="fas fa-plus"></i> 상품찾기</button>
								</div>
							</div>
						</div>
					</div>  
					<hr class="sm">

				<div class="form-group row">
					<label for="svcAdres" class="col-sm-2 col-form-label col-form-label-sm required">교환/반품주소</label>
					<form:input path="svcAdres" cssClass="col-sm-10 form-control form-control-sm" maxlength="200" placeholder=""/>
				</div>
				<hr class="sm"/>
				<div class="form-group row">
					<label for="svcHdryNm" class="col-sm-2 col-form-label col-form-label-sm required">교환/반품 택배사명</label>
					<form:input path="svcHdryNm" cssClass="col-sm-4 form-control form-control-sm" maxlength="20" placeholder=""/>
				</div>
				<hr class="sm"/>
				<div class="form-group row">
					<label for="rtngudDlvyPc" class="col-sm-2 col-form-label col-form-label-sm required">반품 배송비</label>
					<form:input path="rtngudDlvyPc" cssClass="col-sm-4 form-control form-control-sm inputNumber" maxlength="20" placeholder=""/>
					<label for="exchngDlvyPc" class="col-sm-2 col-form-label col-form-label-sm required">교환 배송비</label>
					<form:input path="exchngDlvyPc" cssClass="col-sm-4 form-control form-control-sm inputNumber" maxlength="20" placeholder=""/>
				</div>
				<hr class="sm"/>
					
					<div class="text-right mt-4">
						<button type="submit" class="btn btn-dark btn-sm"><i class="fas fa-save"></i> 저장</button>
					</div>
				</form:form>
			</div>
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
							<%-- <input type="hidden" name="searchCmpnyId" value="${brand.cmpnyId}"/> --%>
							<input type="hidden" name="searchGoodsBrandId" value="${brand.brandId}"/>
							<input type="hidden" name="searchNotGoodsId" value=""/>
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
									<option value="GNM">이름</option>
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
		<script src="${CTX_ROOT}/resources/decms/shop/brand/js/brandForm.js"></script>
		<script src="${CTX_ROOT}/resources/decms/shop/brand/js/brandFormCmpnyList.js"></script>
		<script src="${CTX_ROOT}/resources/decms/shop/brand/js/brandFormRecomendList.js?20201223"></script>
	</javascript>
			
</body>
</html>