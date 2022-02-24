<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/modoo/common/commonTagLibs.jsp"%>
<c:set var="title" value="상품검색"/>
<!DOCTYPE html>
<html>
<head>
	<title>${title}</title>
</head>
<body>
<div class="wrap">
	<div class="sub-contents">
		<h2 class="txt-hide">검색</h2>
		<div class="sch">
			<div class="sch-area lg">
				<form:form modelAttribute="searchVO" id="searchGoodsForm" name="searchGoodsForm" method="get" action="${CTX_ROOT }/shop/goods/goodsSearch.do">
					<form:hidden path="pageIndex"/>
					<form:hidden path="searchOrderField"/>
					<form:input path="searchKeyword" cssClass="autocomplete"  tabindex="1" accesskey="s" />
					<input type="hidden" name="storeSearchWrdAt" value=""/>
					<button type="submit" class="btn-sch"><span class="txt-hide">검색</span></button>
				</form:form>
			</div>
			<div class="keyword-area">
				<cite>연관 검색어</cite>
				<ul>
					<c:forEach var="keyword" items="${keywordList }">
						<li><button type="button" class="btnKeywordNm">${keyword.keywordNm }</button></li>
					</c:forEach>
				</ul>
			</div>
		</div>
		<section>
			<div class="sub-tit-area">
				<h3 class="txt-hide">검색결과</h3>
				<p><span class="m-inlineblock-none"><strong><c:out value="${searchVO.searchKeyword }"/></strong>에 대한</span> <em>${paginationInfo.totalRecordCount }</em>개의 검색결과</p>
				<div class="fnc-area">
					<form:form modelAttribute="searchVO" id="searchOrderForm" name="searchOrderForm" method="get" action="${CTX_ROOT }/shop/goods/goodsSearch.do">
						<form:hidden path="pageIndex"/>
						<form:hidden path="searchKeyword"/>
						<form:select path="searchOrderField" cssClass="border-none">
							<form:option value="RDCNT">인기순</form:option>
							<form:option value="SEL">판매순</form:option>
							<form:option value="LAT">최신순</form:option>
							<form:option value="HPC">높은가격순</form:option>
							<form:option value="LPC">낮은가격순</form:option>
						</form:select>
					</form:form>
				</div>
			</div>
			<ul class="product-list col4 border-top">
				<c:forEach var="result" items="${resultList}" varStatus="status">
					<li>
						<a href="${CTX_ROOT }/shop/goods/goodsView.do?goodsId=${result.goodsId}">
							<div class="product-img-area">
								<img src="<c:out value="${result.goodsLrgeImagePath }"/>" alt="<c:out value="${result.goodsNm }"/>" />
								<%-- <c:if test="${result.goodsKndCode eq 'SBS' }">
									<div class="label-info">
										<cite>
											<c:choose>
												<c:when test="${result.goodsKndCode eq 'SBS' }">구독상품</c:when>
												<c:when test="${result.goodsKndCode eq 'GNR' }">일반상품</c:when>
											</c:choose>
										</cite>
										<span>배송주기</span>
										<strong>
											<c:choose>
												<c:when test="${result.sbscrptCycleSeCode eq 'WEEK' }">주단위</c:when>
												<c:when test="${result.sbscrptCycleSeCode eq 'MONTH' }">월단위</c:when>
											</c:choose>
										</strong>
									</div>
								</c:if> --%>
								<c:if test="${result.soldOutAt eq 'Y'}">
									<div class="soldout"><span>일시품절</span></div>
								</c:if>
								<c:if test="${result.eventAt eq 'Y'}">
									<div class="label-event">이벤트</div>
								</c:if>
							</div>
							<div class="product-txt-area">
								<cite>
									<c:choose>
										<c:when test="${not empty result.brandNm }">
											<c:out value="${result.brandNm }"/>
										</c:when>
										<c:otherwise>
											<c:out value="${result.cmpnyNm }"/>
										</c:otherwise>
									</c:choose>
								</cite>
								<h3>
									<c:out value="${result.goodsNm }"/>
								</h3>
								<div class="price">
									<strong><span><fmt:formatNumber type="number" pattern="#,###" value="${result.goodsPc}"/></span>원</strong>
									<c:if test="${result.mrktUseAt eq 'Y'}">
										<del><span><fmt:formatNumber type="number" pattern="#,###" value="${result.mrktPc}"/></span>원</del>
									</c:if>
								</div>
								<!-- <div class="label-area">
									<span class="label spot">HOT</span>
								</div> -->
								<c:if test="${result.dlvySeCode eq 'DS03' || result.dlvyPc eq 0 }">
								<div class="label-area">
										<span class="label spot3">무료배송</span>
									</div>	
								</c:if>
							</div>
						</a>
					<!--<label class="btn-zzim"><input type="checkbox" title="찜" /></label>-->
					</li>
				</c:forEach>
			</ul>
			<c:url var="pageUrl" value="/shop/goods/goodsSearch.do">
				<c:param name="searchOrderField" value="${searchVO.searchOrderField }"/>
				<c:param name="searchKeyword" value="${searchVO.searchKeyword }"/>
				<c:param name="pageIndex" value=""/>
			</c:url>
			<modoo:pagination paginationInfo="${paginationInfo}" type="text" jsFunction="" pageUrl="${pageUrl }" pageCssClass="paging"/>
		</section>
			
	</div>
</div>


	<javascript>
		<script src="${CTX_ROOT}/resources/front/shop/goods/info/js/goodsSearch.js?202010"></script>
	</javascript>
</body>
</html>