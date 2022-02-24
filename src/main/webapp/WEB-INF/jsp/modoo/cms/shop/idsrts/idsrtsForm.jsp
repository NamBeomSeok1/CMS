<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/modoo/common/commonTagLibs.jsp"%>
<c:set var="pageTitle" value="도서산간지역 등록"/>
<c:set var="mode" value="insert"/>
<c:set var="actionUrl" value="/decms/shop/hdry/writeHdryCmpny.json"/>
<c:choose>
	<c:when test="${empty idsrts.zip}">
		<c:set var="pageTitle" value="도서산간지역 등록"/>
		<c:set var="mode" value="insert"/>
		<c:set var="actionUrl" value="/decms/shop/idsrts/writeIdsrts.json"/>
	</c:when>
	<c:otherwise>
		<c:set var="pageTitle" value="도서산간지역 수정"/>
		<c:set var="mode" value="update"/>
		<c:set var="actionUrl" value="/decms/shop/idsrts/modifyIdsrts.json"/>
	</c:otherwise>
</c:choose>

<form:form modelAttribute="idsrts" id="registForm" name="idsrtsForm" cssClass="embedForm" method="post" enctype="multipart/form-data" action="${actionUrl }">
	<h5>${pageTitle}</h5>
	<p>
		(<i class="fas fa-star text-danger"></i>)는 필수 항목입니다.
	</p>
	<hr class="sm"/>
	<div class="form-group row">
		<label for="hdryNm" class="col-sm-2 col-form-label col-form-label-sm required">우편번호</label>
		<form:input path="zip" cssClass="col-sm-2 form-control form-control-sm" maxlength="5" placeholder="우편번호를 입력하세요"/>
	</div>
	<hr class="sm"/>
	<div class="form-group row">
		<label for="hdryTelno" class="col-sm-2 col-form-label col-form-label-sm required">주소</label>
		<form:input path="adres" cssClass="col-sm-7 form-control form-control-sm" maxlength="255" placeholder="주소를 입력하세요"/>
	</div>
	<hr class="sm"/>

	<div class="text-right mt-3 mb-3">
		<button type="button" class="btn btn-secondary btn-sm" data-dismiss="modal"><i class="fas fa-ban"></i> 취소</button>
		<button type="submit" class="btn btn-primary btn-sm"><i class="fas fa-save"></i> 저장</button>
	</div>
</form:form>
