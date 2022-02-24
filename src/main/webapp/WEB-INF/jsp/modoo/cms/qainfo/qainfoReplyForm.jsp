<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/modoo/common/commonTagLibs.jsp"%>
<c:set var="pageTitle" value="답변 등록"/>

<form:form modelAttribute="qainfo" id="registForm" name="qainfoForm" cssClass="embedForm" method="post" enctype="multipart/form-data" action="${CTX_ROOT }/decms/qainfo/saveReplyQainfo.json">
	<fieldset>
		<form:hidden path="siteId"/>
		<form:hidden path="qaId"/>
	</fieldset>
	<h5>${pageTitle }</h5>
	<p>
		(<i class="fas fa-star text-danger"></i>)는 필수 항목입니다.
	</p>
	<hr class="sm"/>
	
	<div class="form-group row">
		<label for="qestnSj" class="col-sm-2 col-form-label col-form-label-sm required">질문제목</label>
		<div class="text">
			<c:out value="${qainfo.qestnSj }"/>
		</div>
	</div>
	<hr class="sm"/>

	<div class="form-group row">
		<label for="wrterNm" class="col-sm-2 col-form-label col-form-label-sm required">질문자명</label>
		<div class="col-sm-4 text">
			<c:out value="${qainfo.wrterNm }"/>
		</div>
		<label for="writngDe" class="col-sm-2 col-form-label col-form-label-sm required">작성일</label>
		<div class="col-sm-4 text">
			<fmt:parseDate var="wDate" value="${qainfo.writngDe }" pattern="yyyyMMddHHmmss"/>
			<fmt:formatDate value="${wDate }" pattern="yyyy.MM.dd HH:mm:ss"/>
		</div>
	</div>
	<hr class="sm"/>

	<div class="form-group row">
		<label for="emailAdres" class="col-sm-2 col-form-label col-form-label-sm ">이메일</label>
		<div class="col-sm-4 text">
			<c:out value="${qainfo.emailAdres }"/>
		</div>
		<label for="wrterTelno" class="col-sm-2 col-form-label col-form-label-sm ">전화번호</label>
		<div class="col-sm-4 text">
			<c:out value="${qainfo.wrterTelno }"/>
		</div>
	</div>
	<hr class="sm"/>

	<div class="" style="min-height:100px;">
		<c:out value="${qainfo.qestnCn }" escapeXml="false"/>
	</div>

	<hr/>
	
	<div class="form-group row">
		<label for="answerNm" class="col-sm-2 col-form-label col-form-label-sm">답변자</label>
		<input type="text" id="answerNm" class="col-sm-4 form-control form-control-sm" readonly="readonly" value="${USER_NAME}"/>
		<label for="answerDe" class="col-sm-2 col-form-label col-form-label-sm">답변일</label>
		<div class="col-sm-4 text">
			<fmt:parseDate var="answerDe" value="${qainfo.answerDe }" pattern="yyyyMMddHHmmss"/>
			<fmt:formatDate value="${answerDe }" pattern="yyyy.MM.dd HH:mm:ss"/>
		</div>
	</div>
	<hr class="sm"/>

	<div class="form-group row">
		<label for="qnaProcessSttusCode" class="col-sm-2 col-form-label col-form-label-sm">진행상태</label>
		<form:select path="qnaProcessSttusCode" class="col-sm-4 custom-select custom-select-sm">
			<c:forEach var="item" items="${qaSttusCodeList }">
				<form:option value="${item.code }">${item.codeNm }</form:option>
			</c:forEach>
		</form:select>
	</div>
	<hr class="sm"/>
	
	<div class="">
		<form:textarea path="answerCn" cssClass="form-control summernote"/>
	</div>
	<hr class="sm"/>
	
	<div class="text-right mt-3 mb-3">
		<button type="button" class="btn btn-secondary btn-sm" data-dismiss="modal"><i class="fas fa-ban"></i> 취소</button>
		<button type="submit" class="btn btn-primary btn-sm"><i class="fas fa-save"></i> 저장</button>
	</div>
</form:form>