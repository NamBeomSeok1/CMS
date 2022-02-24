<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/modoo/common/commonTagLibs.jsp"%>
<c:set var="pageTitle" value=" 등록"/>
<c:set var="mode" value="insert"/>
<c:set var="actionUrl" value="/decms/popup/writePopup.json"/>


	
	<div class="form-group row">
		<label for="qestnSj" class="col-sm-2 col-form-label col-form-label-sm required">질문제목</label>
		<div class="text-sm">
			<c:out value="${faq.qestnSj }"/>
		</div>
	</div>
	<hr class="sm"/>

	<div class="form-group row">
		<label for="faqSeCode" class="col-sm-2 col-form-label col-form-label-sm required">질문유형</label>
		<div class="text-sm">
			<c:forEach var="faqSeCode" items="${faqSeCodeList}">
				<c:if test="${faq.faqSeCode eq faqSeCode.code }">${faqSeCode.codeNm} </c:if>
			</c:forEach>
		</div>
	</div>
	<hr class="sm"/>

	<div class="">
		<div><span class="badge badge-primary">질문</span></div>
		<div class="border text-sm p-3">
			<c:out value="${faq.qestnCn }" escapeXml="false"/>
		</div>
	</div>

	<hr/>

	<div class="">
		<div><span class="badge badge-success">답변</span></div>
		<div class="border text-sm p-3">
			<c:out value="${faq.answerCn }" escapeXml="false"/>
		</div>
	</div>
	<hr class="sm"/>
	
	<div class="form-group row">
		<label for="atachFile" class="col-sm-2 col-form-label col-form-label-sm">첨부파일</label>
		<c:if test="${not empty faq.atchFileId }">
			<div class="offset-sm-2 col-sm-10">
				<c:import url="/fms/seletFileList.do" charEncoding="utf-8">
					<c:param name="paramAtchFileId" value="${faq.atchFileId }"/>
					<c:param name="updateFlag" value="N"/>
				</c:import>
			</div>
		</c:if>
	</div>
	
	<hr/>
	
	<div class="text-right mt-3 mb-3">
		<button type="button" class="btn btn-secondary btn-sm" data-dismiss="modal"><i class="fas fa-ban"></i> 닫기</button>
	</div>