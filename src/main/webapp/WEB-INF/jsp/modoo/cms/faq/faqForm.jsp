<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/modoo/common/commonTagLibs.jsp"%>
<c:set var="pageTitle" value=" 등록"/>
<c:set var="mode" value="insert"/>
<c:set var="actionUrl" value="/decms/popup/writePopup.json"/>
<c:choose>
	<c:when test="${empty faq.faqId }">
		<c:set var="pageTitle" value="FAQ 등록"/>
		<c:set var="mode" value="insert"/>
		<c:set var="actionUrl" value="/decms/faq/writeFaq.json"/>
	</c:when>
	<c:otherwise>
		<c:set var="pageTitle" value="FAQ 수정"/>
		<c:set var="mode" value="update"/>
		<c:set var="actionUrl" value="/decms/faq/modifyFaq.json"/>
	</c:otherwise>
</c:choose>

<form:form modelAttribute="faq" id="registForm" name="faqForm" cssClass="embedForm" method="post" enctype="multipart/form-data" action="${actionUrl }">
	<fieldset>
		<form:hidden path="siteId"/>
		<form:hidden path="faqId"/>
		<form:hidden path="faqClCode"/>
		<form:hidden path="atchFileId"/>
	</fieldset>
	<h5>${pageTitle }</h5>
	<p>
		(<i class="fas fa-star text-danger"></i>)는 필수 항목입니다.
	</p>
	<hr class="sm"/>
	
	<div class="form-group row">
		<label for="qestnSj" class="col-sm-2 col-form-label col-form-label-sm required">질문제목</label>
		<form:input path="qestnSj" cssClass="col-sm-10 form-control form-control-sm" maxlength="255" placeholder="질문제목을 입력하세요"/>
	</div>
	<hr class="sm"/>

	<div class="form-group row">
		<label for="faqSeCode" class="col-sm-2 col-form-label col-form-label-sm required">질문유형</label>
		<form:select path="faqSeCode" cssClass="col-sm-1 form-control form-control-sm">
			<form:option value="">==선택==</form:option>
				<c:forEach var="faqSeCode" items="${faqSeCodeList}">
					<c:choose>
						<c:when test="${faq.faqSeCode eq faqSeCode.code}">
							<form:option value="${faqSeCode.code}" selected="true">${faqSeCode.codeNm}</form:option>
						</c:when>
						<c:otherwise>
							<form:option value="${faqSeCode.code}">${faqSeCode.codeNm}</form:option>
						</c:otherwise>
					</c:choose>
				</c:forEach>
		</form:select>
	</div>
	<hr class="sm"/>

	<div class="">
		<div>질문</div>
		<form:textarea path="qestnCn" cssClass="form-control summernote"/>
	</div>

	<hr/>

	<div class="">
		<div>답변</div>
		<form:textarea path="answerCn" cssClass="form-control summernote"/>
	</div>
	<hr class="sm"/>
	
	<div class="form-group row">
		<label for="atachFile" class="col-sm-2 col-form-label col-form-label-sm">첨부파일</label>
		<input type="file" id="atchFile" name="atchFile" class="col-sm-5 form-control form-control-sm" multiple/>
		<c:if test="${not empty faq.atchFileId }">
			<div class="offset-sm-2 col-sm-10">
				<c:import url="/fms/seletFileList.do" charEncoding="utf-8">
					<c:param name="paramAtchFileId" value="${faq.atchFileId }"/>
					<c:param name="updateFlag" value="Y"/>
				</c:import>
			</div>
		</c:if>
	</div>
	
	<hr/>
	
	<div class="text-right mt-3 mb-3">
		<button type="button" class="btn btn-secondary btn-sm" data-dismiss="modal"><i class="fas fa-ban"></i> 취소</button>
		<button type="submit" class="btn btn-primary btn-sm"><i class="fas fa-save"></i> 저장</button>
	</div>
</form:form>