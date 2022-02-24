<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/modoo/common/commonTagLibs.jsp"%>
<c:set var="pageTitle" value="은행 등록"/>
<c:set var="mode" value="insert"/>
<c:set var="actionUrl" value="/decms/shop/bank/writeBank.json"/>
<c:choose>
	<c:when test="${empty bank.bankId }">
		<c:set var="pageTitle" value="은행 등록"/>
		<c:set var="mode" value="insert"/>
		<c:set var="actionUrl" value="/decms/shop/bank/writeBank.json"/>
	</c:when>
	<c:otherwise>
		<c:set var="pageTitle" value="은행 수정"/>
		<c:set var="mode" value="update"/>
		<c:set var="actionUrl" value="/decms/shop/bank/modifyBank.json"/>
	</c:otherwise>
</c:choose>

<form:form modelAttribute="bank" id="registForm" name="bankForm" cssClass="embedForm" method="post" enctype="multipart/form-data" action="${actionUrl }">
	<fieldset>
		<form:hidden path="bankId"/>
	</fieldset>
	<h5>${pageTitle }</h5>
	<p>
		(<i class="fas fa-star text-danger"></i>)는 필수 항목입니다.
	</p>
	<hr class="sm"/>
	<div class="form-group row">
		<label for="bankNm" class="col-sm-2 col-form-label col-form-label-sm required">은행명</label>
		<form:input path="bankNm" cssClass="col-sm-7 form-control form-control-sm" maxlength="30" placeholder="은행명을 입력하세요"/>
	</div>
	<hr class="sm"/>

	<div class="text-right mt-3 mb-3">
		<button type="button" class="btn btn-secondary btn-sm" data-dismiss="modal"><i class="fas fa-ban"></i> 취소</button>
		<button type="submit" class="btn btn-primary btn-sm"><i class="fas fa-save"></i> 저장</button>
	</div>
</form:form>
