<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/modoo/common/commonTagLibs.jsp"%>
<c:set var="ctgryNm" value=""/>
<c:set var="title" value="카테고리"/>
<!DOCTYPE html>
<html>
<head>
	<title>${title}</title>
</head>
<body>
<div class="wrap">
	<div class="lnb-area">
		<button type="button" class="btn-lnb-toggle">메뉴</button>
		<nav class="lnb">
			<cite>카테고리</cite>
			<ul>
				<c:forEach var="ctgry" items="${ctgryMenuList}">
					<%-- 임시로 폭스 부분만 나오게 설정 --%>
					<c:if test="${fn:contains(ctgry.goodsCtgryNm, '폭스')}">
						<c:set var="isActive" value=""/>
						<c:if test="${ctgry.actvtyAt eq 'Y' }">
							<c:if test="${searchVO.searchGoodsCtgryId eq ctgry.goodsCtgryId }">
								<c:set var="isActive" value="class=\"is-active\""/>
								<c:set var="ctgryNm" value="${ctgry.goodsCtgryNm}"/>
							</c:if>
							<c:if test="${ctgry.ctgryExpsrSeCode ne 'NONE' }">
								<li><a href="${CTX_ROOT }/shop/goods/goodsCtgryList.do?searchGoodsCtgryId=${ctgry.goodsCtgryId}" ${isActive }>
										<c:out value="${ctgry.goodsCtgryNm }"/>
									</a>
								</li>
							</c:if>
						</c:if>
					</c:if>
				</c:forEach>
			</ul>
			<button class="btn-lnb-close"><span class="txt-hide">닫기</span></button>
		</nav>
	</div>

	<div class="sub-contents">
		<c:import url="/embed/shop/goods/goodsCtgryLocation.do" charEncoding="utf-8">
			<c:param name="goodsCtgryId" value="${searchVO.searchGoodsCtgryId }"/>
		</c:import>
		
		<h2 class="txt-hide">카테고리</h2>
		<section>
			<c:import url="/embed/shop/goods/goodsMobileCtgryLocation.do" charEncoding="utf-8">	
				<c:param name="searchUpperGoodsCtgryId" value="${searchVO.searchGoodsCtgryId}"/>
				<c:param name="searchSubCtgryId" value="${searchVO.searchSubCtgryId}"/>	
				<c:param name="searchGoodsCtgryId" value="${searchVO.searchGoodsCtgryId}"/>
			</c:import>
			<div class="sub-tit-area">
				<h3 class="txt-hide">카테고리 리스트</h3>
				<p class="m-none"><strong><c:out value="${ctgryNm}"/></strong></p>
				<div class="fnc-area">
					<form:form modelAttribute="searchVO" id="searchForm" name="searchForm" method="get" action="${CTX_ROOT }/shop/goods/goodsCtgryList.do">
						<form:hidden path="pageIndex"/>
						<form:hidden path="searchGoodsCtgryId"/>
						<form:hidden path="searchSubCtgryId"/>
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

			<c:import url="/embed/shop/goods/goodsSubCtgryLocation.do" charEncoding="utf-8">	
				<c:param name="searchUpperGoodsCtgryId" value="${searchVO.searchGoodsCtgryId}"/>	
			</c:import>

				<ul class="product-list col3">
				<c:forEach var="result" items="${resultList}" varStatus="status">
					<li>
						<a href="${CTX_ROOT }/shop/goods/goodsView.do?goodsId=${result.goodsId}">
							<div class="product-img-area">
								<img src="<c:out value="${result.goodsLrgeImagePath }"/>" alt="<c:out value="${result.goodsNm }"/>" />
								<%-- <c:choose>
								<c:when test="${result.goodsKndCode eq 'SBS' }">
									<div class="label-info">
										<cite>구독상품 </cite>
										<span>배송주기</span>
										<strong>
											<c:choose>
												<c:when test="${result.sbscrptCycleSeCode eq 'WEEK' }">주단위</c:when>
												<c:when test="${result.sbscrptCycleSeCode eq 'MONTH' }">월단위</c:when>
											</c:choose>
										</strong>
									</div>
									<c:if test="${result.soldOutAt eq 'Y'}">
										<div class="soldout"><span>일시품절</span></div>
									</c:if>
								</c:when>
								<c:when test="${result.goodsKndCode eq 'GNR' }">
									<div class="label-info">
										<c:choose>
											<c:when test="${result.prtnrId eq 'PRTNR_0001' }"><cite>체험구독</cite></c:when> EZWEL 
											<c:otherwise><cite>일반상품</cite></c:otherwise>
										</c:choose>
									</div>
									<c:if test="${result.soldOutAt eq 'Y'}">
										<div class="soldout"><span>일시품절</span></div>
									</c:if>
								</c:when>
								</c:choose> --%>
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
										<c:when test="${not empty result.brandNm }"> <c:out value="${result.brandNm }"/> </c:when>
										<c:otherwise> <c:out value="${result.cmpnyNm }"/> </c:otherwise>
									</c:choose>
								</cite>
								<h3><c:out value="${result.goodsNm }"/></h3>
								<div class="price">
									<strong><span><fmt:formatNumber type="number" pattern="#,###" value="${result.goodsPc}"/></span>원</strong>
									<c:if test="${result.mrktUseAt eq 'Y'}">
										<del><span><fmt:formatNumber type="number" pattern="#,###" value="${result.mrktPc}"/></span></del>
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
			<c:url var="pageUrl" value="/shop/goods/goodsCtgryList.do">
				<c:param name="searchGoodsCtgryId" value="${searchVO.searchGoodsCtgryId }"/>
				<c:param name="searchOrderField" value="${searchVO.searchOrderField }"/>
				<c:param name="searchCondition" value="${searchVO.searchCondition }"/>
				<c:param name="searchKeyword" value="${searchVO.searchKeyword }"/>
				<c:param name="searchSubCtgryId" value="${searchVO.searchSubCtgryId }"/>
				<c:param name="pageIndex" value=""/>
			</c:url>
			<modoo:pagination paginationInfo="${paginationInfo}" type="text" jsFunction="" pageUrl="${pageUrl }" pageCssClass="paging"/>
		</section>
	</div>
</div>


	<javascript>
		<script src="${CTX_ROOT}/resources/front/shop/goods/info/js/goodsCtgryList.js"></script>
	</javascript>
</body>
</html>