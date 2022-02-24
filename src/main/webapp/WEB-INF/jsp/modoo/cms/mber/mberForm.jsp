<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/modoo/common/commonTagLibs.jsp"%>
<c:set var="pageTitle" value="회원정보 등록"/>
<c:set var="mode" value="insert"/>
<c:url var="actionUrl" value="/decms/mber/writeMber.json"/>

<c:choose>
	<c:when test="${empty mber.esntlId }">
		<c:set var="pageTitle" value="회원정보 등록"/>
		<c:set var="mode" value="insert"/>
		<c:url var="actionUrl" value="/decms/mber/writeMber.json"/>
	</c:when>
	<c:otherwise>
		<c:set var="pageTitle" value="회원정보 수정"/>
		<c:set var="mode" value="modify"/>
		<c:url var="actionUrl" value="/decms/mber/modifyMber.json"/>
	</c:otherwise>
</c:choose>

<form:form modelAttribute="mber" id="registForm" name="mberForm" method="post" action="${actionUrl }">
	<fieldset>
		<form:hidden path="esntlId"/>
		<form:hidden path="siteId"/>
		<input type="hidden" id="checkDuplMberId" name="checkDuplMberId" value=""/>
	</fieldset>
	<h5>${pageTitle }</h5>
	<p>
		(<i class="fas fa-star text-danger"></i>)는 필수 항목입니다.
	</p>
	<div class="form-row">
		<div class="form-group col-md-6">
			<label for="mberNm" class="required">회원명</label>
			<form:input path="mberNm" cssClass="form-control form-control-sm"/>
		</div>
		<div class="form-group col-md-6">
			<label for="mberId" class="required">회원ID</label>
			<c:choose>
				<c:when test="${mode eq 'insert' }">
					<div class="row">
						<div class="col-8">
							<form:input path="mberId" cssClass="form-control form-control-sm" placeholder="ID"/>
						</div>
						<div class="col-4">
							<a href="${CTX_ROOT }/decms/mber/checkDuplMberId.json" class="btn btn-dark btn-sm btnCheckDuplMberId"><i class="fas fa-search"></i> ID 중복 확인</a>
						</div>
					</div>
				</c:when>
				<c:otherwise>
					<input type="text" id="mberId" name="mberId" class="form-control form-control-sm" value="${mber.mberId }" readonly="readonly"/>
				</c:otherwise>
			</c:choose>
		</div>
	</div>
	<c:choose>
		<c:when test="${mode eq 'insert' }">
			<div class="form-row">
				<div class="form-group col-md-6">
					<label for="password" class="required">비밀번호</label>
					<form:password path="password" cssClass="form-control form-control-sm" placeholder="8자 이상 20자 이하" autocomplete="one-time-code" maxlength="20"/>
				</div>
				<div class="form-group col-md-6">
					<label for="repassword" class="required">비밀번호 확인</label>
					<form:password path="repassword" cssClass="form-control form-control-sm" placeholder="8자 이상 20자 이하" autocomplete="one-time-code" maxlength="20"/>
				</div>
			</div>
		</c:when>
		<c:otherwise>
			<div class="form-group col-md-6">
				<label for="password">비밀번호 변경</label>
				<div>
					<form:hidden path="password" value="dummypassword"/>
					<form:hidden path="repassword" value="dummypassword"/>
					<a href="${CTX_ROOT }/decms/mber/changePassword.json" class="btn btn-success btn-sm btnChagePasswd"><i class="fas fa-key"></i> 비밀번호 변경</a>
					<c:if test="${mber.lockAt eq 'Y' }">
						<c:url var="unlockMberUrl" value="/decms/mber/unlockMber.json">
							<c:param name="esntlId" value="${mber.esntlId }"/>
						</c:url>
						<a href="<c:out value="${unlockMberUrl }"/>" class="btn btn-dark btn-sm btnUnlock"><i class="fas fa-lock"></i> 잠김 풀기</a>
					</c:if>
				</div>
			</div>
		</c:otherwise>
	</c:choose>
	<div class="form-row">
		<div class="form-group col-md-6">
			<label for="email" class="required">이메일</label>
			<form:input path="email" cssClass="form-control form-control-sm"/>
		</div>
		<div class="form-group col-md-6">
			<label for="mberSttus">회원상태</label>
			<form:select path="mberSttus" cssClass="form-control custom-select custom-select-sm">
				<c:forEach var="sttus" items="${mberSttusList }">
					<form:option value="${sttus.code }">${sttus.codeNm }</form:option>
				</c:forEach>
			</form:select>
		</div>
	</div>
	<div class="form-row">
		<div class="form-group col-md-6">
			<label for="mberTyCode">회원유형</label>
			<c:choose>
				<c:when test="${mode eq 'insert' }"> 
					<input type="text" id="mberTyCode" value="-" class="form-control form-control-sm" readonly="readonly"/>
				</c:when>
				<c:otherwise>
					<c:forEach var="mberTyCode" items="${mberTyCodeList }">
						<c:if test="${mberTyCode.code eq mber.mberTyCode }">
							<input type="text" id="mberTyCode" class="form-control form-control-sm" value="${mberTyCode.codeNm }" readonly="readonly"/>
						</c:if>
					</c:forEach>
				</c:otherwise>
			</c:choose>
		</div>
		<div class="form-group col-md-6">
			<label for="authorCode">회원권한</label>
			<form:select path="authorCode" cssClass="custom-select custom-select-sm">
				<c:forEach var="item" items="${authorList }">
					<form:option value="${item.authorCode }">${item.authorNm }</form:option>
				</c:forEach>
			</form:select>
		</div>
	</div>
	<div class="form-row">
		<div class="form-group col-md-6">
			<label for="sbscrbDe">가입일</label>
			<c:choose>
				<c:when test="${mode eq 'insert' }">
					<input type="text" id="sbscrbDe" class="form-control form-control-sm" value="-" readonly="readonly"/>
				</c:when>
				<c:otherwise>
					<input type="text" id="sbscrbDe" class="form-control form-control-sm" value="<fmt:formatDate pattern="yyyy년 MM월 dd일 HH:mm:ss" value="${mber.sbscrbDe}" />"/>
				</c:otherwise>
			</c:choose>
		</div>
		<div class="form-group col-md-6">
			<label for="lock">잠김여부</label>
			<c:choose>
				<c:when test="${mber.lockAt eq 'Y' }">
					<div class="row">
						<div class="col-8">
							<input type="text" id="lock" class="form-control form-control-sm" value="잠김일 : <fmt:formatDate pattern="yyyy.MM.dd HH:mm:ss" value="${mber.lockLastPnttm}" />" readonly="readonly"/>
						</div>
						<div class="col-4">
							<%-- <c:url var="unlockMberUrl" value="/decms/mber/unlockMber.json">
								<c:param name="esntlId" value="${mber.esntlId }"/>
							</c:url>
							<a href="<c:out value="${unlockMberUrl }"/>" class="btn btn-dark btn-sm btnUnlock"><i class="fas fa-lock"></i> 잠김 풀기</a> --%>
						</div>
					</div>
				</c:when>
				<c:otherwise>
					<input type="text" id="lock" class="form-control form-control-sm" value="-" readonly="readonly"/>
				</c:otherwise>
			</c:choose>
		</div>
	</div>
	<div class="text-right mt-3 mb-3">
		<button type="button" class="btn btn-secondary btn-sm btnMberFormClose" data-dismiss="modal"><i class="fas fa-ban"></i> 닫기</button>
		<button type="submit" class="btn btn-primary btn-sm"><i class="fas fa-save"></i> 저장</button>
	</div>
</form:form>