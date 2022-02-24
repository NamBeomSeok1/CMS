<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/modoo/common/commonTagLibs.jsp"%>
<c:set var="pageTitle" value="샘플 목록"/>
<!DOCTYPE html>
<html>
<head>
	<title>${pageTitle }</title>
</head>
<body>
	<header>
		<h1>${pageTitle }</h1>
	</header>
	<hr/>
	
	<section>
		<form:form modelAttribute="searchVO" name="searchForm" method="get" action="/sample/sampleList.do">
			<fieldset>
				<form:select path="searchCondition">
					<form:option value="SJ">제목</form:option>
					<form:option value="CN">내용</form:option>
				</form:select>
				<form:input path="searchKeyword" size="50" maxlength="100"/>
				<button type="submit">검색</button>
			</fieldset>
		</form:form>
	</section>

	<section>
		<div>
			총 : <c:out value="${paginationInfo.totalRecordCount }"/> 개
		</div>
		<ul>
			<c:forEach var="result" items="${resultList }" varStatus="status">
				<li>
					<span>
						<c:out value="${paginationInfo.totalRecordCount - ((searchVO.pageIndex-1) * searchVO.pageUnit + status.count) + 1}"/>
					</span>
					<c:url var="viewUrl" value="/sample/viewSample.do">
						<c:param name="searchCondition" value="${searchVO.searchCondition }"/>
						<c:param name="searchKeyword" value="${searchVO.searchKeyword }"/>
						<c:param name="pageIndex" value="${searchVO.pageIndex }"/>
						<c:param name="sampleId" value="${result.sampleId }"/>
					</c:url>
					<a href="<c:out value="${viewUrl }"/>"><c:out value="${result.sampleSj }"/></a>
					<span><fmt:formatDate pattern="yyyy년 MM월 dd일 HH:mm:ss" value="${result.registPnttm}" /></span>
				</li>
			</c:forEach>
			<c:if test="${empty resultList }">
				<li>
					<p>No data.</p>
				</li>
			</c:if>
		</ul>
		<hr/>
		
		<nav>
			<c:url var="pageUrl" value="/sample/sampleList.do">
				<c:param name="searchCondition" value="${searchVO.searchCondition }"/>
				<c:param name="searchKeyword" value="${searchVO.searchKeyword }"/>
				<c:param name="pageIndex" value=""/>
			</c:url>
			<modoo:pagination paginationInfo="${paginationInfo}" type="text" jsFunction="" pageUrl="${pageUrl }" pageCssClass="page-css-class"/>
			<%-- 태그 변경 :src/main/java/modoo/module/common/ui/pagination/ModooPaginationRenderer.java --%>
		</nav>
		<br/>

		<div>
			<c:url var="writeUrl" value="/smaple/writeSample.do">
			</c:url>
			<a href="<c:out value="${writeUrl }"/>">쓰기</a>
		</div>
	</section>
</body>
</html>