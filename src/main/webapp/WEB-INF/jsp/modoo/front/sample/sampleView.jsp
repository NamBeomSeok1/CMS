<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/modoo/common/commonTagLibs.jsp"%>
<c:set var="pageTitle" value="샘플 보기"/>
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

	<article>
		<div>
			<span>제목 : <c:out value="${sample.sampleSj }"/></span>
		</div>
		<hr/>

		<div>
			<span>내용</span>
			<br/>
			<br/>
			<c:out value="${sample.sampleCn }"/>
		</div>
		<hr/>
		
		<c:if test="${not empty sample.atchFileId }">
			<div>
				<c:import url="/fms/seletFileList.do" charEncoding="utf-8">
					<c:param name="paramAtchFileId" value="${sample.atchFileId }"/>
					<c:param name="updateFlag" value="N"/>
				</c:import>
			</div>
			<hr/>
		</c:if>

		<div>
			<c:url var="listUrl" value="/sample/sampleList.do">
				<c:param name="searchCondition" value="${searchVO.searchCondition }"/>
				<c:param name="searchKeyword" value="${searchVO.searchKeyword }"/>
				<c:param name="pageIndex" value="${searchVO.pageIndex }"/>
			</c:url>
			<a href="<c:out value="${listUrl }"/>">목록</a>
			<c:url var="modifyUrl" value="/sample/modifySample.do">
				<c:param name="searchCondition" value="${searchVO.searchCondition }"/>
				<c:param name="searchKeyword" value="${searchVO.searchKeyword }"/>
				<c:param name="pageIndex" value="${searchVO.pageIndex }"/>
				<c:param name="sampleId" value="${sample.sampleId }"/>
			</c:url>
			<a href="<c:out value="${modifyUrl }"/>">수정</a>
			<c:url var="deleteUrl" value="/sample/deleteSample.json">
				<c:param name="searchCondition" value="${searchVO.searchCondition }"/>
				<c:param name="searchKeyword" value="${searchVO.searchKeyword }"/>
				<c:param name="sampleId" value="${sample.sampleId }"/>
			</c:url>
			<a href="<c:out value="${deleteUrl }"/>" class="btnDelete">삭제</a>
		</div>
	</article>
	
	<javascript>
		<script src="${CTX_ROOT}/resources/front/sample/js/sampleView.js?20200812"></script> <%-- 자바스크립트 수정 시 의미 없는 파라미터 추가 및 변경 필요 (Browser가 신규 파일이라고 인식하기 위해) --%>
	</javascript>
</body>
</html>