<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/modoo/common/commonTagLibs.jsp"%>
<c:set var="pageTitle" value="코드상세 등록"/>
<c:set var="mode" value="insert"/>
<c:set var="actionUrl" value="/decms/code/writeCode.json"/>
<c:choose>
	<c:when test="${empty codeDetail.code}">
		<c:set var="pageTitle" value="코드상세 등록"/>
		<c:set var="mode" value="insert"/>
		<c:set var="actionUrl" value="/decms/code/writeCodeDetail.json"/>
	</c:when>
	<c:otherwise>
		<c:set var="pageTitle" value="코드상세 수정"/>
		<c:set var="mode" value="update"/>
		<c:set var="actionUrl" value="/decms/code/modifyCodeDetail.json"/>
	</c:otherwise>
</c:choose>

<form:form modelAttribute="codeDetail" id="registSubForm" name="codeDetailForm" cssClass="embedForm" method="post" action="${actionUrl }">
	<fieldset>
		<form:hidden path="codeIdNm" value="dummy"/>
	</fieldset>
	<h5>${pageTitle }</h5>
	<p>
		(<i class="fas fa-star text-danger"></i>)는 필수 항목입니다.
	</p>
	<hr class="sm"/>
	
	<div class="form-group row">
		<label for="codeId" class="col-sm-2 col-form-label col-form-label-sm required">코드ID</label>
		<form:input path="codeId" cssClass="col-sm-2 form-control form-control-sm" maxlength="6" readonly="true" placeholder="코드ID"/>
	</div>
	<hr class="sm"/>

	<div class="form-group row">
		<label for="code" class="col-sm-2 col-form-label col-form-label-sm required">코드</label>
		<form:input path="code" cssClass="col-sm-3 form-control form-control-sm" maxlength="15" placeholder="코드"/>
	</div>
	<hr class="sm"/>

	<div class="form-group row">
		<label for="codeNm" class="col-sm-2 col-form-label col-form-label-sm required">코드명</label>
		<form:input path="codeNm" cssClass="col-sm-5 form-control form-control-sm" maxlength="60" placeholder="코드명"/>
	</div>
	<hr class="sm"/>

	<div class="form-group row">
		<label for="codeDc" class="col-sm-2 col-form-label col-form-label-sm">코드설명</label>
		<form:input path="codeDc" cssClass="col-sm-10 form-control form-control-sm" maxlength="200" placeholder="코드설명"/>
	</div>
	<hr class="sm"/>

	<div class="form-group row">
		<label for="codeSn" class="col-sm-2 col-form-label col-form-label-sm">코드순번</label>
		<c:choose>
			<c:when test="${mode eq 'insert'}">
				<form:input path="codeSn" cssClass="col-sm-2 form-control form-control-sm text-right" maxlength="3" placeholder="" value="${maxSn+1}"/>
			</c:when>
			<c:when test="${mode eq 'update'}">
				<form:input path="codeSn" cssClass="col-sm-2 form-control form-control-sm text-right" maxlength="3" placeholder="" value="${maxSn}"/>
			</c:when>
		</c:choose>
	</div>
	<hr class="sm"/>
	
	<div class="text-right mt-3 mb-3">
		<button type="button" class="btn btn-secondary btn-sm" data-dismiss="modal"><i class="fas fa-ban"></i> 취소</button>
		<button type="submit" class="btn btn-primary btn-sm"><i class="fas fa-save"></i> 저장</button>
	</div>
</form:form>