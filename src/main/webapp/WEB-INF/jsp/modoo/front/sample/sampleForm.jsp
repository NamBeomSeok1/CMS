<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/modoo/common/commonTagLibs.jsp"%>
<%@ taglib prefix="double-submit" uri="http://www.egovframe.go.kr/tags/double-submit/jsp" %>
<c:choose>
	<c:when test="${empty sample.sampleId }">
		<c:set var="pageTitle" value="샘플 쓰기"/>
		<c:url var="actionUrl" value="/sample/writeSample.json"/>
	</c:when>
	<c:otherwise>
		<c:set var="pageTitle" value="샘플 수정"/>
		<c:url var="actionUrl" value="/sample/modifySample.json"/>
	</c:otherwise>
</c:choose>
<!DOCTYPE html>
<html>
<head>
	<title>${pageTitle }</title>
</head>
<body>
	<header>
		<h1>${pageTitle}</h1>
	</header>
	<hr/>

	<section>
		<form:form modelAttribute="sample" id="registForm" name="sampleForm" method="post" enctype="multipart/form-data" action="${actionUrl }">
			<form:hidden path="sampleId"/> <%-- 샘플고유ID --%>
			<form:hidden path="atchFileId"/> <%-- 첨부파일고유ID --%>
			<input type="hidden" name="searchCondition" value="<c:out value="${searchVO.searchCondition }"/>"/>
			<input type="hidden" name="searchKeyword" value="<c:out value="${searchVO.searchKeyword }"/>"/>
			<input type="hidden" name="pageIndex" value="<c:out value="${searchVO.pageIndex }"/>"/>
			<double-submit:preventer/>
			<fieldset>
				<div>
					<label for="sampleSj">제목</label>
					<br/>
					<form:input path="sampleSj" cssClass="" size="100" maxlength="255"/>
				</div>
				<br/>
				<div>
					<label for="sampleCn">내용</label>
					<br/>
					<form:textarea path="sampleCn" cols="50" rows="6" cssClass=""/>
				</div>
				<br/>
				<div>
					<label for="atchFile">첨부파일</label>
					<input type="file" id="atchFile" name="atchFile" multiple/> <%-- multiple일때 여러파일 선택해서 업로드 --%>
					<c:if test="${not empty sample.atchFileId }">
						<div>
							<c:import url="/fms/seletFileList.do" charEncoding="utf-8">
								<c:param name="paramAtchFileId" value="${sample.atchFileId }"/>
								<c:param name="updateFlag" value="Y"/>
							</c:import>
						</div>
					</c:if>
				</div>
			</fieldset>
			<div>
				<c:url var="listUrl" value="/sample/sampleList.do">
					<c:param name="searchCondition" value="${searchVO.searchCondition }"/>
					<c:param name="searchKeyword" value="${searchVO.searchKeyword }"/>
					<c:param name="pageIndex" value="${searchVO.pageIndex }"/>
				</c:url>
				<a href="<c:out value="${listUrl }"/>">취소</a>
				<button type="submit">저장</button>
			</div>
		</form:form>
	</section>
	
	<javascript>
		<script src="${CTX_ROOT}/resources/front/sample/js/sampleForm.js?20200812"></script> <%-- 자바스크립트 수정 시 의미 없는 파라미터 추가 및 변경 필요 (Browser가 신규 파일이라고 인식하기 위해) --%>
	</javascript>
</body>
</html>