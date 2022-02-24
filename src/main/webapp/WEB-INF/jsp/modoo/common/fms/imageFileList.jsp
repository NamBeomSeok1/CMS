<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/modoo/common/commonTagLibs.jsp"%>
<c:if test="${not empty resultList }">
	<ul>
	<c:forEach var="file" items="${resultList }" varStatus="status">
		<li>
			<img src="/fms/getImage.do?atchFileId=${file.atchFileId }&fileSn=${file.fileSn}" alt="${file.orignlFileNm}">
		</li>
	</c:forEach>
	</ul>
</c:if>