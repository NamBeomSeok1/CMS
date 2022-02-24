<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/modoo/common/commonTagLibs.jsp"%>
	<div class="file-list">
	<c:forEach var="file" items="${resultList }">
		<a href="${CTX_ROOT }/fms/downloadFile.do?atchFileId=${file.atchFileId}&amp;fileSn=${file.fileSn}">
			<i class="ico-file" aria-hidden="true"></i>
			[첨부파일] <c:out value="${file.orignlFileNm }"/>
		</a>
	</c:forEach>
	</div>