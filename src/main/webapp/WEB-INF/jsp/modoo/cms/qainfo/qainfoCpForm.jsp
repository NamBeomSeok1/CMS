<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/modoo/common/commonTagLibs.jsp"%>
<c:set var="pageTitle" value="본사문의 등록"/>
<c:set var="mode" value="insert"/>
<c:set var="actionUrl" value="/decms/popup/writePopup.json"/>
<c:choose>
	<c:when test="${empty qainfo.qaId }">
		<c:set var="pageTitle" value="본사문의 등록"/>
		<c:set var="mode" value="insert"/>
		<c:set var="actionUrl" value="/decms/qainfo/writeCpQainfo.json"/>
	</c:when>
	<c:otherwise>
		<c:set var="pageTitle" value="본사문의 수정"/>
		<c:set var="mode" value="update"/>
		<c:set var="actionUrl" value="/decms/qainfo/modifyCpQainfo.json"/>
	</c:otherwise>
</c:choose>

<form:form modelAttribute="qainfo" id="registForm" name="qainfoForm" cssClass="embedForm" method="post" enctype="multipart/form-data" action="${actionUrl }">
	<fieldset>
		<form:hidden path="siteId"/>
		<form:hidden path="qaId"/>
		<form:hidden path="qaSeCode"/>
	</fieldset>
	<h5>${pageTitle }</h5>
	<p>
		<small>(<i class="fas fa-star text-danger"></i>)는 필수 항목입니다.</small>
	</p>
	<hr class="sm"/>
	
	<div class="form-group row">
		<label for="qestnSj" class="col-sm-2 col-form-label col-form-label-sm required">질문제목</label>
		<form:input path="qestnSj" cssClass="col-sm-10 form-control" maxlength="255" placeholder="질문제목을 입력하세요"/>
	</div>
	<hr class="sm"/>

	<div class="form-group row">
		<label for="wrterId" class="col-sm-2 col-form-label col-form-label-sm required">질문자ID</label>
		<form:input path="wrterId" cssClass="col-sm-4 form-control form-control-sm" readonly="true" />
		<label for="wrterNm" class="col-sm-2 col-form-label col-form-label-sm required">질문자명</label>
		<form:input path="wrterNm" cssClass="col-sm-4 form-control form-control-sm" readonly="true" />
	</div>
	<hr class="sm"/>

	<div class="form-group row">
		<label for="emailAdres" class="col-sm-2 col-form-label col-form-label-sm ">이메일</label>
		<form:input path="emailAdres" cssClass="col-sm-4 form-control form-control-sm" maxlength="60" placeholder="ex) id@yourdomain.kr" />
		<label for="wrterTelno" class="col-sm-2 col-form-label col-form-label-sm ">전화번호</label>
		<form:input path="wrterTelno" cssClass="col-sm-4 form-control form-control-sm" maxlength="18" placeholder="ex) 000-0000-0000" value=""/>
	</div>
	<hr class="sm"/>

	<div class="form-group row">
		<label for="emailAdres" class="col-sm-2 col-form-label col-form-label-sm ">문의유형</label>
		<form:select path="" cssClass="col-sm-4 custom-select custom-select-sm">
			<form:option value="qestnTyCode">=선택=</form:option>
			<c:forEach var="item" items="${qestnTyCodeList2 }">
				<form:option value="${item.code }">${item.codeNm }</form:option>
			</c:forEach>
		</form:select>
	</div>

	<div class="">
		<form:textarea path="qestnCn" cssClass="form-control summernote"/>
	</div>
	<hr class="sm"/>
<%-- 
	<c:if test="${qainfo.qnaProcessSttusCode eq 'C' }">
		<div class="form-group row">
			<label for="answerNm" class="col-sm-2 col-form-label col-form-label-sm">답변자</label>
			<div class="col-sm-4">
				<input type="text" id="answerNm" class="form-control form-control-sm" readonly="readonly" value="${qainfo.answerNm}"/>
			</div>
			<label for="answerDe" class="col-sm-2 col-form-label col-form-label-sm">답변일</label>
			<div class="col-sm-4 p-0">
				<span class="text">
					<fmt:parseDate var="answerDe" value="${qainfo.answerDe }" pattern="yyyyMMddHHmmss"/>
					<fmt:formatDate value="${answerDe }" pattern="yyyy.MM.dd HH:mm:ss"/>
				</span>
			</div>
		</div>
		<hr class="sm"/>
		
		<div class="">
			<c:out value="${qainfo.answerCn }" escapeXml="false"/>
		</div>
		
	</c:if> --%>
	
	<%-- <hr class="sm"/>
	<c:if test="${!empty imgs}">
	<div class="form-group row">
		<label for="qnaImagePath" class="col-sm-2 col-form-label col-form-label-sm">사진</label>
		<div style="text-align:center; display:block!important;">
			<img src="/fms/getImage.do?atchFileId=${imgs[0].atchFileId }&fileSn=${imgs[0].fileSn}" alt="${imgs[0].orignlFileNm}" style="height:200px;" width="300px;">
		</div>
	</div>
	</c:if>
	<hr/>
	
	
	<div class="form-group row">
		<label for="answerNm" class="col-sm-2 col-form-label col-form-label-sm">답변자</label>
		<input type="text" id="answerNm" class="col-sm-4 form-control form-control-sm" readonly="readonly" value="${USER_NAME}"/>
		<label for="answerDe" class="col-sm-2 col-form-label col-form-label-sm">답변일</label>
		<div class="col-sm-4 p-0">
			<div class="input-group input-group-sm" id="datepicker-answerDe" data-target-input="nearest">
				<form:input path="answerDe" cssClass="form-control datetimepicker-input" data-target="#datepicker-answerDe"/>
				<div class="input-group-append" data-target="#datepicker-answerDe" data-toggle="datetimepicker">
					<div class="input-group-text"><i class="fas fa-calendar"></i></div>
				</div>
			</div>
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
	<hr class="sm"/> --%>
	
	<div class="text-right mt-3 mb-3">
		<button type="button" class="btn btn-secondary btn-sm" data-dismiss="modal"><i class="fas fa-ban"></i> 취소</button>
		<button type="submit" class="btn btn-primary btn-sm"><i class="fas fa-save"></i> 저장</button>
	</div>
</form:form>