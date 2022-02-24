<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/modoo/common/commonTagLibs.jsp"%>
<div class="sub-tit-area">
    <h3 class="sub-tit">Q&amp;A<em class="qaTotalCount"></em></h3>
    	<c:if test="${!empty USER_ID}">
         <div class="fnc-area">
             <button type="button" class="btn spot2 qaRegBtn" data-popup-open="qaWrite">작성하기</button>
        </div>
        </c:if>
   </div>
  <ul class="accordion qa-list">
  </ul>
  <ul class="paging" id="qa-paging">
  <%-- 	<c:url var="pageUrl" value="//goods/qainfo/qaInfoList.do">
		<c:param name="goodsId" value="${goods.goodsId}"/>
		<c:param name="pageIndex" value=""/>
	</c:url>
  <modoo:pagination paginationInfo="${paginationInfo}" type="text" jsFunction="" pageUrl="${pageUrl}" pageCssClass="page-css-class"/> --%>
  </ul>
  
