<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/modoo/common/commonTagLibs.jsp"%>
<c:set var="pageTitle" value="상품카테고리 등록"/>
<c:set var="mode" value="insert"/>
<c:set var="actionUrl" value="/decms/shop/goods/writeGoodsCtgry.json"/>
<c:choose>
	<c:when test="${empty goodsCtgry.goodsCtgryId }">
		<c:set var="pageTitle" value="상품카테고리 등록"/>
		<c:set var="mode" value="insert"/>
		<c:set var="actionUrl" value="/decms/shop/goods/writeGoodsCtgry.json"/>
	</c:when>
	<c:otherwise>
		<c:set var="pageTitle" value="상품카테고리 수정"/>
		<c:set var="mode" value="update"/>
		<c:set var="actionUrl" value="/decms/shop/goods/modifyGoodsCtgry.json"/>
	</c:otherwise>
</c:choose>

<form:form modelAttribute="goodsCtgry" id="registForm" name="goodsCtgryForm" cssClass="embedForm" enctype="multipart/form-data"  method="post" action="${actionUrl }">
	<fieldset>
		<form:hidden path="goodsCtgryId"/>
		<form:hidden path="upperGoodsCtgryId"/>
	</fieldset>
	<p>
		<small>(<i class="fas fa-star text-danger"></i>)는 필수 항목입니다.</small>
	</p>
	<hr class="sm"/>
	<div class="form-group row">
		<label for="goodsCtgryNm" class="col-sm-2 col-form-label col-form-label-sm required">카테고리명</label>
		<div class="col-sm-7">
			<form:input path="goodsCtgryNm" cssClass="form-control form-control-sm" maxlength="255" placeholder="카테고리명을 입력하세요"/>
		</div>
	</div>
	<hr class="sm"/>

	<div class="form-group row">
		<label for="goodsCtgrySn" class="col-sm-2 col-form-label col-form-label-sm required">카테고리 순번</label>
		<div class="col-sm-2">
			<form:input path="goodsCtgrySn" cssClass="form-control form-control-sm text-right" maxlength="3" placeholder=""/>
		</div>
	</div>
	<hr class="sm"/>
	<%-- <div class="form-group row">
		<label for="actvtyAt" class="col-sm-2 col-form-label col-form-label-sm required">노출 여부</label>
		<div class="col-sm-2">
			<div class="custom-control custom-switch ">
				<form:checkbox path="actvtyAt" cssClass="custom-control-input" value="Y"/>
				<label class="custom-control-label" for="actvtyAt1">카테고리 활성</label>
			</div>
		</div>
	</div>
	<hr class="sm"/> --%>
	<div class="form-group row">
		<label for="ctgryExpsrSeCode" class="col-sm-2 col-form-label col-form-label-sm required">카테고리노출구분</label>
		<div class="col-sm-3">
		<form:select path="ctgryExpsrSeCode" cssClass="custom-select custom-select-sm" value="${goodsCtgry.ctgryExpsrSeCode}">
			<form:option value="ALL">모두노출</form:option>
			<form:option value="B2B">B2B만 노출</form:option>
			<form:option value="B2C">B2C만 노출</form:option>
			<form:option value="NONE">미노출</form:option>
		</form:select>
		</div>
		<div class="col-sm-7"></div>
	</div>
	<hr class="sm"/>
	<%-- <div class="form-group row">
		<label for="actvtyAt" class="col-sm-2 col-form-label required">기본 정렬 설정</label>
		<form:select path="bassSortCode" cssClass="custom-select custom-select-sm">
			<form:option value="1">정렬1</form:option>
			<form:option value="2">정렬2</form:option>
			<form:option value="3">정렬3</form:option>
		</form:select>
	</div> --%>
	<div class="form-group row">
		<label for="ctgryImagePath" class="col-sm-2 col-form-label col-form-label-sm required">카테고리 이미지</label>
		<div class="input-group input-group-sm col-sm-10">
			<div class="input-group-prepend">
				<span class="input-group-text"><i class="fas fa-image"></i> </span>
			</div>
			<form:input path="ctgryImagePath" cssClass="form-control" placeholder="ex) /contents/ctgry1.png"/>
			<div class="input-group-append">
				<a href="#" class="btn btn-outline-secondary" data-event="upload" data-target-url="#ctgryImagePath" data-target-img="#ctgry-img"><i class="fas fa-upload"></i></a>
			</div>
		</div>
		<div class="offset-2 col-10 mt-2">
			<div id="ctgry-img">
				<c:if test="${not empty goodsCtgry.ctgryImagePath}">
					<img src="<c:out value="${goodsCtgry.ctgryImagePath}"/>" style="max-width:100%; max-height:100%;"/>
				</c:if>
				<%-- 
				<img src="/resources/front/banner/image/sample.png" style="max-height:100px;"/>
				--%>
			</div>
		<!-- <hr class="sm"/>
			<div class="form-group row">
				<label for="bassExhbiCode" class="col-sm-2 col-form-label required">기본 진열 설정(모바일)</label>
			</div> -->
		</div>
	</div>
	<hr class="sm"/>

	<div class="text-right mt-3">
		<button type="button" class="btn btn-secondary btn-sm" data-dismiss="modal"><i class="fas fa-ban"></i> 취소</button>
		<button type="submit" class="btn btn-primary btn-sm"><i class="fas fa-save"></i> 저장</button>
	</div>
</form:form>
