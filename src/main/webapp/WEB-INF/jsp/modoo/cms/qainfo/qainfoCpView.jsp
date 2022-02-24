<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/modoo/common/commonTagLibs.jsp"%>
<c:set var="pageTitle" value="본사문의 상세"/>

<div>
	<div class="row">
		<div class="col-sm-12">
			<div class="input-group input-group-sm">
				<div class="input-group-prepend">
					<span class="input-group-text">질문제목</span>
				</div>
				<input type="text" class="form-control bg-white" readonly="readonly" value="${qainfo.qestnSj }"/>
			</div>
		</div>
	</div>
	<hr class="sm"/>
	<div class="row">
		<div class="col-sm-6">
			<div class="input-group input-group-sm">
				<div class="input-group-prepend">
					<span class="input-group-text">질문자ID</span>
				</div>
				<input type="text" class="form-control bg-white" readonly="readonly" value="${qainfo.wrterId }"/>
			</div>
		</div>
		<div class="col-sm-6">
			<div class="input-group input-group-sm">
				<div class="input-group-prepend">
					<span class="input-group-text">질문자명</span>
				</div>
				<input type="text" class="form-control bg-white" readonly="readonly" value="${qainfo.wrterNm }"/>
			</div>
		</div>
	</div>
	<hr class="sm"/>
	
	<div class="alert alert-light mt-3" role="alert">
		<strong class="alert-heading">질문</strong>
		<br/>
		<br/>
		<c:out value="${qainfo.qestnCn }" escapeXml="false"/>
	</div>
	<hr class="sm"/>

	<c:if test="${qainfo.qnaProcessSttusCode eq 'C' }">
		<div class="alert alert-success" role="alert">
			<strong class="alert-heading">답변 [${qainfo.answerNm }, 
					<fmt:parseDate var="answerDe" value="${qainfo.answerDe }" pattern="yyyyMMddHHmmss"/>
					<fmt:formatDate value="${answerDe }" pattern="yyyy.MM.dd "/>]</strong>
			<br/>
			<br/>
			<c:out value="${qainfo.answerCn }" escapeXml="false"/>
		</div>
		<hr class="sm"/>
	</c:if>


	<div class="text-right mt-3 mb-3">
		<button type="button" class="btn btn-secondary btn-sm" data-dismiss="modal"><i class="fas fa-ban"></i> 닫기</button>
	</div>	
</div>