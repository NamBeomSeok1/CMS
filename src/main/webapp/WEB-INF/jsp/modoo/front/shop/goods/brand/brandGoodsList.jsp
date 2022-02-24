<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/modoo/common/commonTagLibs.jsp"%>
<c:set var="title" value="브랜드관"/>
<!DOCTYPE html>
<html>
<head>
	<title>${title}</title>
</head>
<body>
<div class="wrap">
	<section class="brand-visual">
			<h2 class="txt-hide">브랜드관</h2>
			<div class="visual-img-area">
				<c:choose>
					<c:when test="${not empty brand.repBrandImageList[0].brandImagePath}">
						<img src="<c:out value="${brand.repBrandImageList[0].brandImagePath }"/>" alt="<c:out value="${brand.brandNm }"/>" class="m-none" />
						<c:if test="${not empty brand.repMobBrandImageList[0]}">
							<img src="<c:out value="${brand.repMobBrandImageList[0].brandImagePath }"/>" alt="<c:out value="${brand.brandNm }"/>" class="m-block"/> 
						</c:if>
						<c:if test="${empty brand.repMobBrandImageList[0]}">
							<img src="${CTX_ROOT }/resources/decms/common/image/brand_noimg_mob.jpeg" alt="<c:out value="${brand.brandNm }"/>" class="m-block" />
						</c:if> 
					</c:when>
					<c:otherwise>
						<img src="${CTX_ROOT }/resources/decms/common/image/brand_noimg_web.jpeg" alt="<c:out value="${brand.brandNm }"/>" class="m-none"/> 
						<img src="${CTX_ROOT }/resources/decms/common/image/brand_noimg_mob.jpeg" alt="<c:out value="${brand.brandNm }"/>" class="m-block" />  
					</c:otherwise>
				</c:choose>
		</div>
		<div class="visual-txt-area">
			<%-- <span class="logo-brand">
				<img src="<c:out value="${brand.brandImageThumbPath }"/>" alt="<c:out value="${brand.brandNm }"/>" />
			</span> --%>
			<div>
				<h3><span><c:out value="${brand.brandNm }"/></span> </h3>
				<p>
					<c:out value="${brand.brandIntSj }"/>
				</p>
			</div>
			<a href="${CTX_ROOT }/shop/goods/goodsBrandInfo.do?searchGoodsBrandId=${searchVO.searchGoodsBrandId}" class="btn-intro">	
				브랜드 소개 <i class="ico-arr-r sm back" aria-hidden="true"></i>
			</a>
		</div>
	</section>
	<div class="sub-contents">
		<c:if test="${brand.eventAt eq 'Y'}">
			<c:choose>
				<c:when test="${user.groupId eq 'GROUP_00000000000001'}"><!-- B2B -->
					<div class="banner-band">
			            <a href="${brand.eventUrl}?searchGoodsBrandId=${searchVO.searchGoodsBrandId}" target="_blank">
							<c:if test="${not empty brand.evtBrandImageList[0]}">
								<img src="<c:out value="${brand.evtBrandImageList[0].brandImagePath }"/>" alt="<c:out value="${brand.brandNm }"/>" class="m-none"/> 
							</c:if>
							<c:if test="${not empty brand.evtMobBrandImageList[0]}">
								<img src="<c:out value="${brand.evtMobBrandImageList[0].brandImagePath }"/>" alt="<c:out value="${brand.brandNm }"/>" class="m-block"/> 
							</c:if>			
						</a>
			        </div>
				</c:when>
				<c:otherwise><!-- B2C -->
					<div class="banner-band">
			            <a href="${brand.eventUrl}?searchGoodsBrandId=${searchVO.searchGoodsBrandId}" target="_blank">
							<img src="<c:out value="${CTX_ROOT}/html/SITE_00000/event/210111open2/images/banner_brand/GROUP_00000000000000/${searchVO.searchGoodsBrandId}/brand_banner.jpg"/>" alt="<c:out value="${brand.brandNm }"/>" class="m-none"/>
							<img src="<c:out value="${CTX_ROOT}/html/SITE_00000/event/210111open2/images/banner_brand/GROUP_00000000000000/${searchVO.searchGoodsBrandId}/brand_banner_m.jpg"/>" alt="<c:out value="${brand.brandNm }"/>" class="m-block"/>			
						</a>
			        </div>
				</c:otherwise>
			</c:choose>
		</c:if>
<!-- 			<div class="banner-band"> -->
<!-- 	            <a href="#none"> -->
<!-- 	                <img src="../../../resources/front/site/SITE_00000/image/temp/banner_band.jpg" class="m-none"> -->
<!-- 	                <img src="../../../resources/front/site/SITE_00000/image/temp/m_banner_band.jpg" class="m-block"> -->
<!-- 	            </a> -->
<!-- 	        </div> -->

	
		<section>
			<div class="sub-tit-area">
				<h3 class="sub-tit"><c:out value="${brand.brandNm }"/></h3>
				<div class="fnc-area">
					<form:form modelAttribute="searchVO" id="searchForm" name="searchForm" method="get" action="${CTX_ROOT }/shop/goods/brandGoodsList.do">
						<form:hidden path="pageIndex"/>
						<form:hidden path="searchGoodsBrandId"/>
						<form:select path="searchOrderField" cssClass="border-none">
							<form:option value="RDCNT">인기순</form:option>
							<form:option value="SEL">판매순</form:option>
							<form:option value="LAT">최신순</form:option>
							<form:option value="HPC">높은가격순</form:option>
							<form:option value="LPC">낮은가격순</form:option>
							<form:option value="GNM">상품명</form:option>
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
								<%-- <div class="label-info">
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
								</div> --%>
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
				<c:if test="${empty resultList }">
					<li style="width:100%;"><div style="font-size:14px; text-align:center;">등록된 상품이 없습니다.</div> </li>
				</c:if>
			</ul>
			<c:url var="pageUrl" value="/shop/goods/brandGoodsList.do">
				<c:param name="searchGoodsBrandId" value="${searchVO.searchGoodsBrandId }"/>
				<c:param name="searchOrderField" value="${searchVO.searchOrderField }"/>
				<c:param name="searchCondition" value="${searchVO.searchCondition }"/>
				<c:param name="searchKeyword" value="${searchVO.searchKeyword }"/>
				<c:param name="pageIndex" value=""/>
			</c:url>
			<modoo:pagination paginationInfo="${paginationInfo}" type="text" jsFunction="" pageUrl="${pageUrl }" pageCssClass="paging"/>
		</section>
	</div>
</div>

	<javascript>
		<script src="${CTX_ROOT}/resources/front/shop/goods/brand/js/brandGoodsList.js"></script>
	</javascript>
</body>
</html>