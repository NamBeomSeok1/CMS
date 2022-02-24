<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/modoo/common/commonTagLibs.jsp"%>
<c:if test="${not empty resultList }">
	<c:choose>
	<c:when test="${updateFlag eq 'Y' }">
		<table class="atchFileList">
			<thead>
				<tr>
					<th>파일명</th>
					<th>용량</th>
					<th></th>
				</tr>
			</thead>
			<tbody>
				<c:forEach var="file" items="${resultList }">
					<tr>
						<td class="atch_name"><c:out value="${file.orignlFileNm }"/></td>
						<td class="atch_size"><fmt:formatNumber maxFractionDigits="0" value="${(file.fileMg div 1024) }"/> KB</td>
						<td class="atch_ctrl">
							<a href="${CTX_ROOT }/fms/downloadFile.do?atchFileId=${file.atchFileId}&amp;fileSn=${file.fileSn}" class="atch_download">다운로드</a> |
							<a href="${CTX_ROOT }/fms/deleteFile.json?atchFileId=${file.atchFileId}&amp;fileSn=${file.fileSn}" class="atch_delete">삭제</a>
						</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</c:when>
	<c:otherwise>
		<table class="atchFileList">
			<thead>
				<tr>
					<th>첨부파일</th>
					<th>용량</th>
					<th></th>
				</tr>
			</thead>
			<tbody>
				<c:forEach var="file" items="${resultList }">
					<tr>
						<td class="atch_name"><c:out value="${file.orignlFileNm }"/></td>
						<td class="atch_size"><fmt:formatNumber maxFractionDigits="0" value="${(file.fileMg div 1024) }"/> KB</td>
						<td class="atch_ctrl"><a href="${CTX_ROOT }/fms/downloadFile.do?atchFileId=${file.atchFileId}&amp;fileSn=${file.fileSn}" class="atch_download">다운로드</a></td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</c:otherwise>
	</c:choose>
</c:if>
