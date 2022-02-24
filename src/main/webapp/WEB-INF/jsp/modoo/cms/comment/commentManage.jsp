<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/modoo/common/commonTagLibs.jsp"%>

<div class="">
	<div class="mb-2">
		Comment ( <span id="comment-tot">0</span> )
	</div>
	
	<form id="commentSearchForm" name="commentSearchForm" method="get" action="${CTX_ROOT }/decms/comment/commentList.json">
		<fieldset>
			<input type="hidden" name="pageIndex" value="1"/>
			<input type="hidden" name="cntntsSeCode" value="${param.cntntsSeCode }"/>
			<input type="hidden" name="searchCntntsId" value="${param.searchCntntsId }"/>
		</fieldset>
	</form>

	<div id="commentList">
		<%-- comment list --%>
	</div>

	<form id="commentForm" name="commentForm" method="post" action="${CTX_ROOT }/decms/comment/writeComment.json">
		<fieldset>
			<input type="hidden" id="cntntsSeCode" name="cntntsSeCode" value="${param.cntntsSeCode }"/>
			<input type="hidden" id="cntntsId" name="cntntsId" value="${param.searchCntntsId }"/>
		</fieldset>
		<div class="form-group row mb-2">
			<div class="input-group input-group-sm col-sm-6">
				<div class="input-group-prepend">
					<span class="input-group-text">Name</span>
				</div>
				<input type="text" id="writerNm" name="wrterNm" class="form-control form-control-sm" value="${USER_NAME }"/>
			</div>
			<div class="input-group input-group-sm col-sm-6">
				<div class="input-group-prepend">
					<span class="input-group-text">ID</span>
				</div>
				<input type="text" id="writerId" name="wrterId" class="form-control form-control-sm" value="${USER_ID }"/>
			</div>
		</div>
		<div class="form-group row mb-2">
			<div class="input-group input-group-sm col-sm-12">
				<div class="input-group-prepend">
					<span class="input-group-text">내용</span>
				</div>
				<textarea class="form-control form-control-sm" id="commentCn" name="commentCn"></textarea>
			</div>
		</div>
		<div class="text-right">
			<button type="submit" class="btn btn-primary">댓글등록</button> 
		</div>
	</form>
</div>

<script id="commentTemplate" type="text/template">
	<div class="comment-item card border-left-primary mb-3" data-comment-id="{ID}">
		<div class="card-body">
			<div class="comment-content">
				<div class="comment row">
					<div class="col-md-8">
						<div class="text-xs font-weight-bold text-primary text-uppercase mb-1"><span class="comment-name">{NAME}</span> (<span class="comment-date">{DATE}</span>)</div>
					</div>
					<div class="col-md-4 text-right">
						<a href="${CTX_ROOT }/decms/comment/deleteComment.json?commentId={ID}" class="btn btn-danger btn-sm btnDeleteComment"><i class="fas fa-trash"></i></a>
						<button type="button" class="btn btn-dark btn-sm btnEditComment" data-id="{ID}"><i class="fas fa-edit"></i></button>
					</div>
				</div>
				<div>
					{CONTENT}
				</div>
			</div>
		</div>
	</div>
</script>
<script id="commentFormTemplate" type="text/template">
	<form name="commentModifyForm" class="comment-modify-form" method="post" action="${CTX_ROOT}/decms/comment/modifyComment.json">
		<fieldset>
			<input type="hidden" name="commentId" value="{ID}"/>
		</fieldset>
		<div class="form-group row mb-2">
			<div class="input-group input-group-sm col-sm-6">
				<div class="input-group-prepend">
					<span class="input-group-text">Name</span>
				</div>
				<input type="text" id="writerNm" name="wrterNm" class="form-control form-control-sm" value="{NAME}"/>
			</div>
			<div class="input-group input-group-sm col-sm-6">
				<div class="input-group-prepend">
					<span class="input-group-text">ID</span>
				</div>
				<input type="text" id="writerId" name="wrterId" class="form-control form-control-sm" value="{USER_ID}"/>
			</div>
		</div>
		<div>
			<textarea name="commentCn" class="form-control">{CONTENT}</textarea>
		</div>
		<div class="text-right mt-2">
			<button type="button" class="btn btn-secondary btn-sm btnModifyCancel" data-id="{ID}">취소</button>
			<button type="submit" class="btn btn-primary btn-sm">저장</button>
		</div>

	</form>
</script>
