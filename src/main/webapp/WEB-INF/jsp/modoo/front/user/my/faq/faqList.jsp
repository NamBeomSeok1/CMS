<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/modoo/common/commonTagLibs.jsp"%>
<c:set var="title" value="FAQ"/>
<!DOCTYPE html>
<html>
<head>
	<title>${title}</title>
</head>
<body>
<div class="wrap">
	<c:import url="/user/my/subMenu.do" charEncoding="utf-8">
		<c:param name="menuId" value="cs_FAQ"/>
	</c:import>
	
	
	<div class="sub-contents">
		<c:import url="/user/my/myLocation.do" charEncoding="utf-8">
			<c:param name="menuId" value="cs"/>
			<c:param name="subMenuId" value="${title}"/>
		</c:import> 
		<c:import url="/user/my/userInfo.do" charEncoding="utf-8"/>
		<section>
			<div class="sub-tit-area">
				<h3 class="sub-tit">FAQ</h3>
			</div>
			<div class="">
				<ul class="tabs-nav sm ar">
					<li <c:if test="${searchVO.searchGroupCode eq 'F01' }">class="is-active"</c:if>><a href="${CTX_ROOT }/user/my/faqList.do?searchGroupCode=F01">주문 / 결제 / 취소</a></li>
					<li <c:if test="${searchVO.searchGroupCode eq 'F02' }">class="is-active"</c:if>><a href="${CTX_ROOT }/user/my/faqList.do?searchGroupCode=F02">배송문의</a></li>
					<li <c:if test="${searchVO.searchGroupCode eq 'F03' }">class="is-active"</c:if>><a href="${CTX_ROOT }/user/my/faqList.do?searchGroupCode=F03">교환 / 환불</a></li>
					<li <c:if test="${searchVO.searchGroupCode eq 'F04' }">class="is-active"</c:if>><a href="${CTX_ROOT }/user/my/faqList.do?searchGroupCode=F04">서비스 이용관련</a></li>
					<li <c:if test="${searchVO.searchGroupCode eq 'F05' }">class="is-active"</c:if>><a href="${CTX_ROOT }/user/my/faqList.do?searchGroupCode=F05">기타사항</a></li>
				</ul>
				<div>
					<ul class="accordion">
						<c:forEach var="result" items="${resultList }" varStatus="status">
						<li>
							<div class="accordion-tit-area">
								<button type="button" class="btn-accordion-toggle"><span class="txt-hide">토글버튼</span></button>
								<div>[<c:out value="${result.faqSeCodeNm }"/>] <c:out value="${result.qestnSj }"/></div>
								<%--<span class="date"><fmt:formatDate pattern="yyyy.MM.dd" value="${result.frstRegistPnttm}" /></span>--%>
							</div>
							<div class="accordion-txt-area">
								<div>
									<c:out value="${result.answerCn }" escapeXml="false"/>
								</div>
							</div>
						</li>
						</c:forEach>
						<c:if test="${empty resultList }">
							<li><p class="none-txt">작성된 FAQ가 없습니다.</p></li>
						</c:if>
					</ul>
					<c:url var="pageUrl" value="/user/my/faqList.do">
						<c:param name="menuNo" value=""/>
						<c:param name="menuId" value=""/>
						<c:param name="searchGroupCode" value="${searchVO.searchGroupCode }"/>
						<c:param name="searchCondition" value="${searchVO.searchCondition }"/>
						<c:param name="searchKeyword" value="${searchVO.searchKeyword }"/>
						<c:param name="pageIndex" value=""/>
					</c:url>
					<modoo:pagination paginationInfo="${paginationInfo}" type="text" jsFunction="" pageUrl="${pageUrl }" pageCssClass="paging"/>
				</div>
			</div>
		</section>
	</div>
</div>
</body>
</html>
<%-- 
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
</head>
<body>
<c:set var="title" value="FAQ"/>
<c:import url="/user/my/subMenu.do" charEncoding="utf-8">
	<c:param name="menuId" value="cs_FAQ"/>
</c:import>
<title>${title}</title>
<div class="sub-contents">
<c:import url="/user/my/myLocation.do" charEncoding="utf-8">
	<c:param name="menuId" value="cs"/>
	<c:param name="subMenuId" value="${title}"/>
</c:import> 
<c:import url="/user/my/userInfo.do" charEncoding="utf-8">
</c:import> 
<section>
    <div class="sub-tit-area">
        <h3 class="sub-tit">FAQ</h3>
    </div>
    <div class="tabs">
        <ul class="sm ar">
            <li><a href="#faq1">주문 / 결제 / 취소</a></li>
            <li><a href="#faq2">배송문의</a></li>
            <li><a href="#faq3">교환 / 환불</a></li>
            <li><a href="#faq4">서비스 이용관련</a></li>
            <li><a href="#faq5">기타사항</a></li>
        </ul>
        <div id="faq1">
            <ul class="accordion" id="accordion-faq1">
                
            </ul>
            <ul class="paging" id="paging-faq1">

            </ul>
        </div>
        <div id="faq2">
            <ul class="accordion" id="accordion-faq2">

            </ul>
            <ul class="paging" id="paging-faq2">
               
            </ul>
        </div>
        <div id="faq3">
            <ul class="accordion" id="accordion-faq3">
                
            </ul>
            <ul class="paging" id="paging-faq3">
                
            </ul>
        </div>
        <div id="faq4">
            <ul class="accordion" id="accordion-faq4">
                
            </ul>
            <ul class="paging" id="paging-faq4">
                
            </ul>
        </div>
        <div id="faq5">
            <ul class="accordion" id="accordion-faq5">
                
            </ul>
            <ul class="paging" id="paging-faq5">
                
            </ul>
        </div>
    </div>
</section>
</div>
</body>
	<javascript>
		<script src="${CTX_ROOT}/resources/front/my/faq/faq.js"></script>
	</javascript>
</html> --%>