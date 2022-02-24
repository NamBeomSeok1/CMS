<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/modoo/common/commonTagLibs.jsp"%>
<c:set var="pageTitle" value="게시판 등록"/>
<c:set var="mode" value="insert"/>
<c:set var="actionUrl" value="/decms/board/master/writeBoardMaster.json"/>
<c:choose>
	<c:when test="${empty boardMaster.bbsId }">
		<c:set var="pageTitle" value="게시판 등록"/>
		<c:set var="mode" value="insert"/>
		<c:set var="actionUrl" value="/decms/board/master/writeBoardMaster.json"/>
	</c:when>
	<c:otherwise>
		<c:set var="pageTitle" value="게시판 수정"/>
		<c:set var="mode" value="update"/>
		<c:set var="actionUrl" value="/decms/board/master/modifyBoardMaster.json"/>
	</c:otherwise>
</c:choose>

<form:form modelAttribute="boardMaster" id="registForm" name="boardMasterForm" cssClass="embedForm" method="post" action="${actionUrl }">
	<fieldset>
		<form:hidden path="bbsId"/>
		<form:hidden path="siteId"/>
	</fieldset>
	<h5>${pageTitle }</h5>
	<p>
		(<i class="fas fa-star text-danger"></i>)는 필수 항목입니다.
	</p>
	<hr class="sm"/>

	<div class="form-group row">
		<label for="bbsNm" class="col-sm-2 col-form-label col-form-label-sm required">게시판명</label>
		<form:input path="bbsNm" cssClass="col-sm-4 form-control form-control-sm" placeholder="게시판명을 입력하세요"/>
	</div>
	<hr class="sm"/>

	<div class="form-group row">
		<label for="bbsTmplatCode" class="col-sm-2 col-form-label col-form-label-sm">게시판 템플릿</label>
		<form:select path="bbsTmplatCode" cssClass="col-sm-4 custom-select custom-select-sm">
			<c:forEach var="item" items="${bbsTmplatCodeList }">
				<form:option value="${item.code }">${item.codeNm }</form:option>
			</c:forEach>
		</form:select>
		<label for="ctgryMasterId" class="col-sm-2 col-form-label col-form-label-sm">카테고리</label>
		<form:select path="ctgryMasterId" cssClass="col-sm-4 custom-select custom-select-sm">
			<form:option value="">없음</form:option>
		</form:select>
	</div>
	<hr class="sm"/>

	<div class="form-group row">
		<label for="" class="col-sm-2 col-form-label col-form-label-sm">사용옵션</label>
		<div class="col-sm-4">
			<div class="custom-control custom-switch mb-1">
				<form:checkbox path="noticeAt" cssClass="custom-control-input" value="Y"/>
				<label for="noticeAt1" class="custom-control-label">공지</label>
			</div>
			<div class="custom-control custom-switch mb-1">
				<form:checkbox path="secretAt" cssClass="custom-control-input" value="Y"/>
				<label for="secretAt1" class="custom-control-label">비밀글</label>
			</div>
			<div class="custom-control custom-switch mb-1">
				<form:checkbox path="annymtyAt" cssClass="custom-control-input" value="Y"/>
				<label for="annymtyAt1" class="custom-control-label">익명</label>
			</div>
			<div class="custom-control custom-switch mb-1">
				<form:checkbox path="usgpdAt" cssClass="custom-control-input" value="Y"/>
				<label for="usgpdAt1" class="custom-control-label">사용기간</label>
			</div>
			<div class="custom-control custom-switch mb-1">
				<form:checkbox path="replyAt" cssClass="custom-control-input" value="Y"/>
				<label for="replyAt1" class="custom-control-label">답글</label>
			</div>
			<div class="custom-control custom-switch mb-1">
				<form:checkbox path="commentAt" cssClass="custom-control-input" value="Y"/>
				<label for="commentAt1" class="custom-control-label">댓글</label>
			</div>
			<div class="custom-control custom-switch mb-1">
				<form:checkbox path="fileAtachAt" cssClass="custom-control-input" value="Y"/>
				<label for="fileAtachAt1" class="custom-control-label">첨부파일</label>
			</div>
		</div>
		<label for="" class="col-sm-2 col-form-label col-form-label-sm">권한</label>
		<div class="col-sm-4">
			<div class="input-group input-group-sm mb-1">
				<div class="input-group-prepend">
					<label for="listAuthorCode" class="input-group-text">목록권한</label>
				</div>
				<form:select path="listAuthorCode" cssClass="custom-select">
					<form:option value="ROLE_ANONYMOUS">모든사용자</form:option>
					<c:forEach var="item" items="${authorList }">
						<form:option value="${item.authorCode }">${item.authorNm }</form:option>
					</c:forEach>
				</form:select>
			</div>
			<div class="input-group input-group-sm mb-1">
				<div class="input-group-prepend">
					<label for="redingAuthorCode" class="input-group-text">읽기권한</label>
				</div>
				<form:select path="redingAuthorCode" cssClass="custom-select">
					<form:option value="ROLE_ANONYMOUS">모든사용자</form:option>
					<c:forEach var="item" items="${authorList }">
						<form:option value="${item.authorCode }">${item.authorNm }</form:option>
					</c:forEach>
				</form:select>
			</div>
			<div class="input-group input-group-sm mb-1">
				<div class="input-group-prepend">
					<label for="writngAuthorCode" class="input-group-text">쓰기권한</label>
				</div>
				<form:select path="writngAuthorCode" cssClass="custom-select">
					<form:option value="ROLE_ANONYMOUS">모든사용자</form:option>
					<c:forEach var="item" items="${authorList }">
						<form:option value="${item.authorCode }">${item.authorNm }</form:option>
					</c:forEach>
				</form:select>
			</div>
			<div class="input-group input-group-sm mb-1">
				<div class="input-group-prepend">
					<label for="updtAuthorCode" class="input-group-text">수정권한</label>
				</div>
				<form:select path="updtAuthorCode" cssClass="custom-select">
					<form:option value="ROLE_ANONYMOUS">모든사용자</form:option>
					<c:forEach var="item" items="${authorList }">
						<form:option value="${item.authorCode }">${item.authorNm }</form:option>
					</c:forEach>
				</form:select>
			</div>
			<div class="input-group input-group-sm mb-1">
				<div class="input-group-prepend">
					<label for="deleteAuthorCode" class="input-group-text">삭제권한</label>
				</div>
				<form:select path="deleteAuthorCode" cssClass="custom-select">
					<form:option value="ROLE_ANONYMOUS">모든사용자</form:option>
					<c:forEach var="item" items="${authorList }">
						<form:option value="${item.authorCode }">${item.authorNm }</form:option>
					</c:forEach>
				</form:select>
			</div>
			<div class="input-group input-group-sm mb-1">
				<div class="input-group-prepend">
					<label for="replyAuthorCode" class="input-group-text">답장권한</label>
				</div>
				<form:select path="replyAuthorCode" cssClass="custom-select">
					<form:option value="ROLE_ANONYMOUS">모든사용자</form:option>
					<c:forEach var="item" items="${authorList }">
						<form:option value="${item.authorCode }">${item.authorNm }</form:option>
					</c:forEach>
				</form:select>
			</div>
			<div class="input-group input-group-sm mb-1">
				<div class="input-group-prepend">
					<label for="downloadAuthorCode" class="input-group-text">다운로드권한</label>
				</div>
				<form:select path="downloadAuthorCode" cssClass="custom-select">
					<form:option value="ROLE_ANONYMOUS">모든사용자</form:option>
					<c:forEach var="item" items="${authorList }">
						<form:option value="${item.authorCode }">${item.authorNm }</form:option>
					</c:forEach>
				</form:select>
			</div>
			<div class="input-group input-group-sm mb-1">
				<div class="input-group-prepend">
					<label for="commentAuthorCode" class="input-group-text">댓글권한</label>
				</div>
				<form:select path="commentAuthorCode" cssClass="custom-select">
					<form:option value="ROLE_ANONYMOUS">모든사용자</form:option>
					<c:forEach var="item" items="${authorList }">
						<form:option value="${item.authorCode }">${item.authorNm }</form:option>
					</c:forEach>
				</form:select>
			</div>
		</div>
	</div>
	<hr class="sm"/>

	<div class="form-group row">
		<label for="commentSortOrderType" class="col-sm-2 col-form-label col-form-label-sm">댓글 정렬방식</label>
		<form:select path="commentSortOrderType" cssClass="col-sm-4 custom-select custom-select-sm">
			<form:option value="ASC">ASC</form:option>
			<form:option value="DESC">DESC</form:option>
		</form:select>
	</div>
	<hr class="sm"/>

	<div class="form-group row">
		<label for="atchFileCo" class="col-sm-2 col-form-label col-form-label-sm">첨부파일갯수</label>
		<form:select path="atchFileCo" cssClass="col-sm-4 custom-select custom-select-sm">
			<form:option value="">제한없음</form:option>
			<form:option value="1">1</form:option>
			<form:option value="2">2</form:option>
			<form:option value="3">3</form:option>
			<form:option value="4">4</form:option>
			<form:option value="5">5</form:option>
		</form:select>
		<label for="atchFileSize" class="col-sm-2 col-form-label col-form-label-sm">최대첨부파일사이즈</label>
		<div class="col-sm-4">
			<div class="input-group input-group-sm">
				<form:input path="atchFileSize" cssClass="form-control text-right" maxlength="10"/>
				<div class="input-group-append">
					<span class="input-group-text">MB</span>
				</div>
			</div>
		</div>
	</div>
	<hr class="sm"/>
	
	<div class="form-group row">
		<label for="listCo" class="col-sm-2 col-form-label col-form-label-sm">게시글 페이지당 갯수</label>
		<form:select path="listCo" cssClass="col-sm-4 custom-select custom-select-sm">
			<form:option value="10">10</form:option>
			<form:option value="12">12</form:option>
			<form:option value="16">16</form:option>
			<form:option value="20">20</form:option>
			<form:option value="30">30</form:option>
		</form:select>
	</div>
	
	
	<div class="text-right mt-3 mb-3">
		<button type="button" class="btn btn-secondary btn-sm" data-dismiss="modal"><i class="fas fa-ban"></i> 닫기</button>
		<button type="submit" class="btn btn-primary btn-sm"><i class="fas fa-save"></i> 저장</button>
	</div>
</form:form>