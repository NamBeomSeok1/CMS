<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/modoo/common/commonTagLibs.jsp"%>
<c:set var="pageTitle" value="${boardMaster.bbsNm }"/>


<h5>${pageTitle }</h5>

<div class="card board-view">
	<div class="card-header">
		<strong><c:out value="${article.nttSj }"/></strong>
	</div>
	
	<div class="card-body">
		<div class="row">
			<label class="col-sm-2 col-form-label col-form-label-sm">작성자</label>
			<div class="col-sm-4">
				<span class="text-sm"><c:out value="${article.ntcrNm }"/></span>
			</div>
			<label for="ntcrId" class="col-sm-2 col-form-label col-form-label-sm">작성자ID</label>
			<div class="col-sm-4">
				<span><c:out value="${article.ntcrId }"/></span>
			</div>
		</div>
		<hr class="sm"/>	
		<c:if test="${article.replyAt ne 'Y'}">
			<c:if test="${boardMaster.noticeAt eq 'Y'  }">
				<div class="row">
					<label class="col-sm-2 col-form-label col-form-label-sm">분류</label>
					<div class="col-sm-10">
						<span class="badge badge-primary">공지</span>
					</div>
				</div>
			</c:if>
			<hr class="sm"/>	
		</c:if>
		<div class="pt-2 pb-2">
			<div class="articleCn">
				<c:out value="${article.nttCn }" escapeXml="false"/>
			</div>
		</div>
		<hr class="sm"/>
		
		<c:if test="${not empty article.atchFileId }">
			<div class="row">
				<div class="col-sm-12">
					<c:import url="/fms/seletFileList.do" charEncoding="utf-8">
						<c:param name="paramAtchFileId" value="${article.atchFileId }"/>
						<c:param name="updateFlag" value="N"/>
					</c:import>
				</div>
			</div>
		</c:if>
	</div>
	<div class="card-footer">
		<div class="row">
			<div class="col-sm-4">
				<c:if test="${boardAuth.deleteAt eq 'Y' or article.frstRegisterId eq loginUser.uniqId}">
					<a href="${CTX_ROOT }/decms/board/deleteArticle.json?nttId=${article.nttId}" class="btn btn-danger btn-sm btnDeleteArticle">
						<i class="fas fa-trash"></i> 삭제
					</a>
				</c:if>
			</div>
			<div class="col-sm-8 text-right">
				<button type="button" class="btn btn-secondary btn-sm" data-dismiss="modal"><i class="fas fa-ban"></i> 닫기</button>
				<c:if test="${(boardMaster.replyAt eq 'Y') and (mode eq 'update') and (article.replyLc eq '0')}">
					<a href="${CTX_ROOT }/decms/embed/board/article/replyBoardArticle.do?nttId=${article.nttId}" class="btn btn-dark btn-sm btnReply" data-target="#boardArticleModal">
						<i class="fas fa-reply"></i> 답장</a>
				</c:if>
				
				<c:if test="${boardAuth.updtAt eq 'Y' or article.frstRegisterId eq loginUser.uniqId}">
					<a href="${CTX_ROOT }/decms/embed/board/article/modifyBoardArticle.do?nttId=${article.nttId}" class="btn btn-primary btn-sm btnModify" data-target="#boardArticleModal">
						<i class="fas fa-edit"></i> 수정</a>
				</c:if>
			</div>
		</div>

	</div>
</div>


<%-- 
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
	</div>
	<hr class="sm"/>
	
	<div class="text-right mt-3 mb-3">
		<button type="button" class="btn btn-secondary btn-sm" data-dismiss="modal"><i class="fas fa-ban"></i> 취소</button>
		<c:if test="${(boardMaster.replyAt eq 'Y') and (mode eq 'update') and (article.replyLc eq '0')}">
			<a href="${CTX_ROOT }/decms/embed/board/article/replyBoardArticle.do?nttId=${article.nttId}" class="btn btn-dark btn-sm btnReply" data-target="#boardArticleModal"><i class="fas fa-reply"></i> 답장</a>
		</c:if>
		<button type="submit" class="btn btn-primary btn-sm"><i class="fas fa-save"></i> 저장</button>
	</div>

<c:if test="${not empty article.nttId }">
<hr class="sm"/>
<div class="">
	<c:import url="/decms/comment/commentManage.do" charEncoding="utf-8">
		<c:param name="cntntsSeCode" value="BBS"/>
		<c:param name="searchCntntsId" value="${article.nttId }"/>
	</c:import>
</div>
<hr class="sm"/>
</c:if> --%>
