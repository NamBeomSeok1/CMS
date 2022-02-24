<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/modoo/common/commonTagLibs.jsp"%>
<c:set var="pageTitle" value="${boardMaster.bbsNm }"/>
<c:set var="mode" value="insert"/>
<c:set var="actionUrl" value="/decms/board/article/writeBoardArticle.json"/>
<c:choose>
	<c:when test="${writeMode eq 'reply' }">
		<c:set var="pageTitle" value="${boardMaster.bbsNm } 답글 등록"/>
		<c:set var="mode" value="insert"/>
		<c:set var="actionUrl" value="/decms/board/article/replyBoardArticle.json"/>
	</c:when>
	<c:when test="${empty article.nttId}">
		<c:set var="pageTitle" value="${boardMaster.bbsNm } 게시글 등록"/>
		<c:set var="mode" value="insert"/>
		<c:set var="actionUrl" value="/decms/board/article/writeBoardArticle.json"/>
	</c:when>
	<c:otherwise>
		<c:set var="pageTitle" value="${boardMaster.bbsNm } 게시글 수정"/>
		<c:set var="mode" value="update"/>
		<c:set var="actionUrl" value="/decms/board/article/modifyBoardArticle.json"/>
	</c:otherwise>
</c:choose>

<form:form modelAttribute="article" id="registForm" name="articleForm" cssClass="embedForm mt-2" method="post" enctype='multipart/form-data' action="${actionUrl }">
	<fieldset>
		<form:hidden path="bbsId"/>
		<form:hidden path="nttId"/>
		<form:hidden path="atchFileId"/>
		<form:hidden path="replyAt"/>
		<form:hidden path="parntscttNo"/>
		<form:hidden path="sortOrdr"/>
		<form:hidden path="replyLc"/>
	</fieldset>
	<h5>${pageTitle }</h5>
	<p>
		(<i class="fas fa-star text-danger"></i>)는 필수 항목입니다.
	</p>
	<hr class="sm"/>
	
	<div class="form-group row">
		<label for="nttSj" class="col-sm-2 col-form-label col-form-label-sm required">제목</label>
		<form:input path="nttSj" cssClass="col-sm-10 form-control form-control-sm" maxlength="255" placeholder="제목을 입력하세요"/>
	</div>
	<hr class="sm"/>	
	<div class="form-group row">
		<label for="ntcrNm" class="col-sm-2 col-form-label col-form-label-sm">작성자</label>
		<form:input path="ntcrNm" cssClass="col-sm-4 form-control form-control-sm" maxlength="30" placeholder="작성자명"/>
		<label for="ntcrId" class="col-sm-2 col-form-label col-form-label-sm">작성자ID</label>
		<form:input path="ntcrId" cssClass="col-sm-4 form-control form-control-sm" maxlength="30" placeholder="ID"/>
	</div>
	<hr class="sm"/>	

<c:if test="${article.replyAt ne 'Y'}">
	<div class="form-group row">
		<label for="" class="col-sm-2 col-form-label col-form-label-sm">옵션</label>
		<div class="col-sm-10">
			<div class="custom-control custom-switch mb-1">
				<c:choose>
					<c:when test="${boardMaster.noticeAt eq 'Y' }">
						<form:checkbox path="noticeAt" cssClass="custom-control-input" value="Y"/>
					</c:when>
					<c:otherwise>
						<form:checkbox path="noticeAt" cssClass="custom-control-input" disabled="true" value="Y"/>
					</c:otherwise>
				</c:choose>
				<label for="noticeAt1" class="custom-control-label">공지</label>
			</div>
			<div class="custom-control custom-switch mb-1">
				<c:choose>
					<c:when test="${boardMaster.secretAt eq 'Y' }">
						<form:checkbox path="secretAt" cssClass="custom-control-input" value="Y"/>
					</c:when>
					<c:otherwise>
						<form:checkbox path="secretAt" cssClass="custom-control-input" disabled="true" value="Y"/>
					</c:otherwise>
				</c:choose>
				<label for="secretAt1" class="custom-control-label">비밀글</label>
			</div>
		</div>
	</div>
	<hr class="sm"/>
</c:if>

	<%-- <div class="form-group row">
		<c:set var="secretAt" value="false"/>
		<c:if test="${boardMaster.secretAt eq 'Y' }">
			<c:set var="secretAt" value="true"/>
		</c:if>
		<label for="password" class="col-sm-2 col-form-label">비밀번호</label>
		<form:password path="password" cssClass="col-sm-4 form-control" disabled="${not secreAt }" maxlength="20" placeholder="8~20자 비밀번호"/>
	</div>
	<hr class="sm"/>	 --%>

	<div class="form-group row">
		<c:set var="usgpdAt" value="false"/>
		<c:if test="${boardMaster.usgpdAt eq 'Y' }">
			<c:set var="usgpdAt" value="true"/>
		</c:if>
		<label for="ntceBgnde" class="col-sm-2 col-form-label col-form-label-sm">게시시작일</label>
		<div class="col-sm-4">
			<div class="input-group input-group-sm" id="datepicker-ntceBgnde" data-target-input="nearest">
				<form:input path="ntceBgnde" cssClass="form-control datetimepicker-input" data-target="#datepicker-ntceBgnde" disabled="${not usgpdAt }"/>
				<div class="input-group-append" data-target="#datepicker-ntceBgnde" data-toggle="datetimepicker">
					<div class="input-group-text"><i class="fas fa-calendar"></i></div>
				</div>
			</div>
		</div>
		<label for="ntceEndde" class="col-sm-2 col-form-label col-form-label-sm">게시종료일</label>
		<div class="col-sm-4">
			<div class="input-group input-group-sm" id="datepicker-ntceEndde" data-target-input="nearest">
				<form:input path="ntceEndde" cssClass="form-control datetimepicker-input" data-target="#datepicker-ntceEndde" disabled="${not usgpdAt }"/>
				<div class="input-group-append" data-target="#datepicker-ntceEndde" data-toggle="datetimepicker">
					<div class="input-group-text"><i class="fas fa-calendar"></i></div>
				</div>
			</div>
		</div>
	</div>
	<hr class="sm"/>	
	
	
	<div class="">
		<form:textarea path="nttCn" cssClass="form-control summernote"/>
	</div>
	<hr class="sm"/>

	<div class="form-group row">
		<c:set var="fileAtachDisabled" value="disabled"/>
		<c:if test="${boardMaster.fileAtachAt eq 'Y' }">
			<c:set var="fileAtachDisabled" value=""/>
		</c:if>
		<label for="atachFile" class="col-sm-2 col-form-label col-form-label-sm">첨부파일</label>
		<input type="file" id="atchFile" name="atchFile" class="col-sm-5 form-control form-control-sm" ${fileAtachDisabled } multiple/>
		<c:if test="${not empty article.atchFileId }">
			<div class="offset-sm-2 col-sm-10">
				<c:import url="/fms/seletFileList.do" charEncoding="utf-8">
					<c:param name="paramAtchFileId" value="${article.atchFileId }"/>
					<c:param name="updateFlag" value="Y"/>
				</c:import>
			</div>
		</c:if>
		<button type="button" class="btn btn-danger btn-sm" onclick="deleteAllFile()">첨부파일 초기화</button>
	</div>
	<hr class="sm"/>
	
	<div class="form-group row">
		<label for="atachFile" class="col-sm-2 col-form-label col-form-label-sm">첨부파일 미리보기</label>
		<div>
			<ul class="list-group list-group-flush" id="file-preview"></ul>
		</div>
	</div>
	<hr class="sm"/>

	<div class="text-right mt-3 mb-3">
		<button type="button" class="btn btn-secondary btn-sm" data-dismiss="modal"><i class="fas fa-ban"></i> 취소</button>
		<c:if test="${(boardMaster.replyAt eq 'Y') and (mode eq 'update') and (article.replyLc eq '0')}">
			<a href="${CTX_ROOT }/decms/embed/board/article/replyBoardArticle.do?nttId=${article.nttId}" class="btn btn-dark btn-sm btnReply" data-target="#boardArticleModal"><i class="fas fa-reply"></i> 답장</a>
		</c:if>
		<button type="submit" class="btn btn-primary btn-sm"><i class="fas fa-save"></i> 저장</button>
	</div>
</form:form>

<c:if test="${not empty article.nttId }">
<hr class="sm"/>
<div class="">
	<c:import url="/decms/comment/commentManage.do" charEncoding="utf-8">
		<c:param name="cntntsSeCode" value="BBS"/>
		<c:param name="searchCntntsId" value="${article.nttId }"/>
	</c:import>
</div>
<hr class="sm"/>
</c:if>