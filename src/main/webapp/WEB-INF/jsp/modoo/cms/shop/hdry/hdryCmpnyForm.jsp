<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/modoo/common/commonTagLibs.jsp"%>
<c:set var="pageTitle" value="택배사 등록"/>
<c:set var="mode" value="insert"/>
<c:set var="actionUrl" value="/decms/shop/hdry/writeHdryCmpny.json"/>
<c:choose>
	<c:when test="${empty hdryCmpny.hdryId }">
		<c:set var="pageTitle" value="택배사 등록"/>
		<c:set var="mode" value="insert"/>
		<c:set var="actionUrl" value="/decms/shop/hdry/writeHdryCmpny.json"/>
	</c:when>
	<c:otherwise>
		<c:set var="pageTitle" value="택배사 수정"/>
		<c:set var="mode" value="update"/>
		<c:set var="actionUrl" value="/decms/shop/hdry/modifyHdryCmpny.json"/>
	</c:otherwise>
</c:choose>

<form:form modelAttribute="hdryCmpny" id="registForm" name="hdryCmpnyForm" cssClass="embedForm" method="post" enctype="multipart/form-data" action="${actionUrl }">
	<fieldset>
		<form:hidden path="hdryId"/>
	</fieldset>
	<h5>${pageTitle }</h5>
	<p>
		(<i class="fas fa-star text-danger"></i>)는 필수 항목입니다.
	</p>
	<hr class="sm"/>
	<div class="form-group row">
		<label for="hdryNm" class="col-sm-2 col-form-label col-form-label-sm required">택배사명</label>
		<form:input path="hdryNm" cssClass="col-sm-7 form-control form-control-sm" maxlength="30" placeholder="택배사명을 입력하세요"/>
	</div>
	<hr class="sm"/>
	<div class="form-group row">
		<label for="hdryTelno" class="col-sm-2 col-form-label col-form-label-sm">전화번호</label>
		<form:input path="hdryTelno" cssClass="col-sm-4 form-control form-control-sm" maxlength="18" placeholder="ex) 000-0000-0000"/>
	</div>
	<hr class="sm"/>

	<div class="text-right mt-3 mb-3">
		<button type="button" class="btn btn-secondary btn-sm" data-dismiss="modal"><i class="fas fa-ban"></i> 취소</button>
		<button type="submit" class="btn btn-primary btn-sm"><i class="fas fa-save"></i> 저장</button>
	</div>
</form:form>
