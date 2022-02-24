<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/modoo/common/commonTagLibs.jsp"%>
<c:set var="pageTitle" value="코드 등록"/>
<c:set var="mode" value="insert"/>
<c:set var="actionUrl" value="/decms/code/writeCode.json"/>
<c:choose>
	<c:when test="${mode eq 'insert' }">
		<c:set var="pageTitle" value="코드 등록"/>
		<c:set var="mode" value="insert"/>
		<c:set var="actionUrl" value="/decms/code/writeCode.json"/>
	</c:when>
	<c:otherwise>
		<c:set var="pageTitle" value="코드 수정"/>
		<c:set var="mode" value="update"/>
		<c:set var="actionUrl" value="/decms/code/modifyCode.json"/>
	</c:otherwise>
</c:choose>

<form:form modelAttribute="code" id="registForm" name="codeForm" cssClass="embedForm" method="post" action="${actionUrl }">
	<h5>${pageTitle }</h5>
	<p>
		(<i class="fas fa-star text-danger"></i>)는 필수 항목입니다.
	</p>
	<hr class="sm"/>
	
	<div class="form-group row">
		<label for="clCode" class="col-sm-2 col-form-label col-form-label-sm required">분류코드</label>
		<form:select path="clCode" cssClass="col-sm-2 custom-select custom-select-sm">
			<form:option value="CMS"/>
		</form:select>
	</div>
	<hr class="sm"/>
	
	<div class="form-group row">
		<label for="codeId" class="col-sm-2 col-form-label col-form-label-sm required">코드ID</label>
		<form:input path="codeId" cssClass="col-sm-2 form-control form-control-sm" maxlength="6" placeholder="코드ID"/>
	</div>
	<hr class="sm"/>

	<div class="form-group row">
		<label for="codeIdNm" class="col-sm-2 col-form-label col-form-label-sm required">코드명</label>
		<form:input path="codeIdNm" cssClass="col-sm-6 form-control form-control-sm" maxlength="60" placeholder="코드명을 입력하세요"/>
	</div>
	<hr class="sm"/>
	
	<div class="form-group row">
		<label for="codeIdDc" class="col-sm-2 col-form-label col-form-label-sm">코드설명</label>
		<form:input path="codeIdDc" cssClass="col-sm-10 form-control form-control-sm" maxlength="255" placeholder=""/>
	</div>
	<hr class="sm"/>

	<div class="text-right mt-3 mb-3">
		<button type="button" class="btn btn-secondary btn-sm" data-dismiss="modal"><i class="fas fa-ban"></i> 취소</button>
		<button type="submit" class="btn btn-primary btn-sm"><i class="fas fa-save"></i> 저장</button>
	</div>
</form:form>