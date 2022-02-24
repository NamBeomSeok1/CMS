<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/modoo/common/commonTagLibs.jsp"%>
<c:set var="title" value="${boardMaster.bbsNm }"/>
<!DOCTYPE html>
<html>
<head>
<title>${title}</title>
</head>
<body>
<div class="wrap">
	<c:import url="/user/my/subMenu.do" charEncoding="utf-8">
		<c:param name="menuId" value="cs_${boardMaster.bbsId }"/>
	</c:import>
	
	
	<div class="sub-contents">
		<c:import url="/user/my/myLocation.do" charEncoding="utf-8">
			<c:param name="menuId" value="cs"/>
			<c:param name="subMenuId" value="${title}"/>
		</c:import> 
		<h2 class="txt-hide">${title }</h2>
		<c:import url="/user/my/userInfo.do" charEncoding="utf-8"/>
		<section>
			<div class="sub-tit-area">
				<h3 class="sub-tit">공지사항</h3>
			</div>
			<ul class="accordion notice-list">
				<c:forEach var="result" items="${resultList }" varStatus="status">
				<li <c:if test="${param.nttId eq result.nttId}">class="is-active"</c:if>>
					<div class="accordion-tit-area">
						<button type="button" class="btn-accordion-toggle"><span class="txt-hide">토글버튼</span></button>
						<div>[FOXEDU STORE] <c:out value="${result.nttSj }"/></div>
						<span class="date"><fmt:formatDate pattern="yyyy.MM.dd" value="${result.frstRegistPnttm}" /></span>
					</div>
					<div class="accordion-txt-area">
						<div class="view-content">
							<c:out value="${result.nttCn }" escapeXml="false"/>
						</div>
						
						<c:if test="${not empty result.atchFileId }">
							<c:import url="/shop/fms/selectFileList.do" charEncoding="utf-8">
								<c:param name="paramAtchFileId" value="${result.atchFileId }"/>
							</c:import>
						</c:if>
						<%-- 
						<div class="file-list">
							<a href="#none">
								<i class="ico-file" aria-hidden="true"></i>
								[첨부파일] 첨부파일
							</a>
						</div>
						--%>
					</div>
				</li>
				</c:forEach>
			</ul>
			<c:url var="pageUrl" value="/board/boardList.do">
				<c:param name="menuNo" value="${param.menuNo }"/>
				<c:param name="menuId" value="${boardMaster.bbsId }"/>
				<c:param name="bbsId" value="${boardMaster.bbsId }"/>
				<c:param name="searchCondition" value="${searchVO.searchCondition }"/>
				<c:param name="searchKeyword" value="${searchVO.searchKeyword }"/>
				<c:param name="pageIndex" value=""/>
			</c:url>
			<modoo:pagination paginationInfo="${paginationInfo}" type="text" jsFunction="" pageUrl="${pageUrl }" pageCssClass="paging"/>
		</section>
	</div>
</div>
</body>
</html>