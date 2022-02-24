<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/modoo/common/commonTagLibs.jsp"%>

<c:choose>
	<c:when test="${empty param.listMode}">
		<div class="mn-tit-area">
			<c:choose>
				<%-- 폭스구독권 --%>
				<c:when test="${searchVO.searchGoodsCtgryId eq 'GCTGRY_0000000000032'}">
					<h2 class="mn-tit">폭스구독권</h2>
            		<p>FOX LEARNING CENTER 구독권</p>
				</c:when>
				<c:when test="${searchVO.searchGoodsCtgryId eq 'GCTGRY_0000000000033'}">
					<h2 class="mn-tit">폭스굿즈</h2>
            		<p>폭스에듀만의 특별한 상품</p>
				</c:when>
				<c:otherwise>
					<h2 class="mn-tit">폭스교육상품</h2>
                    <p>미래교육을 위한 폭스교육상품</p>
				</c:otherwise>
			</c:choose>
		</div>
		
		<ul class="product-list col3">
			<c:forEach var="result" items="${resultList }" varStatus="status">
				<li>
	                <a href="${CTX_ROOT }/shop/goods/goodsView.do?goodsId=${result.goodsId}">
	                    <div class="product-img-area">
	                        <img src="<c:out value="${result.goodsLrgeImagePath }"/>" alt="${result.goodsNm }" />
	                        <c:choose>
								<c:when test="${result.goodsKndCode eq 'SBS' }">
									<div class="label-info">
										<cite>구독상품 </cite>
										<span>구독주기</span>
										<strong>
											<c:choose>
												<c:when test="${result.sbscrptCycleSeCode eq 'WEEK' }">주단위</c:when>
												<c:when test="${result.sbscrptCycleSeCode eq 'MONTH' }">1개월</c:when>
											</c:choose>
										</strong>
									</div>
								</c:when>
								<c:when test="${result.goodsKndCode eq 'GNR' }">
									<div class="label-info">
										<c:choose>
											<c:when test="${result.prtnrId eq 'PRTNR_0001' }"><cite>체험구독</cite></c:when> 
											<c:otherwise><cite>일반상품</cite></c:otherwise>
										</c:choose>
									</div>
								</c:when>
							</c:choose>
	                    </div>
	                    <div class="product-txt-area">
	                        <h3><c:out value="${result.goodsNm }"/></h3>
	                        <div class="price">
	                            <strong><span><c:if test="${result.sbscrptCycleSeCode eq 'MONTH' }">월</c:if> <fmt:formatNumber type="number" pattern="#,###" value="${result.goodsPc}"/></span>원</strong>
								<c:if test="${result.mrktUseAt eq 'Y'}">
									<del><span><fmt:formatNumber type="number" pattern="#,###" value="${result.mrktPc}"/></span>원</del>
								</c:if>
	                        </div>
	                    </div>
	                </a>
	            </li>
			</c:forEach>
        </ul>
	</c:when>
	<c:when test="${param.listMode eq 'list' }">
		<c:if test="${not empty resultList }">
			<c:forEach var="result" items="${resultList }" varStatus="status">
				<li class="goods-item">
					<a href="${CTX_ROOT }/shop/goods/goodsView.do?goodsId=${result.goodsId}">
						<div class="product-img-area">
							<img src="<c:out value="${result.goodsLrgeImagePath }"/>" alt="${result.goodsNm }" />
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
							</c:when>
							<c:when test="${result.goodsKndCode eq 'GNR' }">
								<div class="label-info">
									<c:choose>
										<c:when test="${result.prtnrId eq 'PRTNR_0000' }"><cite>일반상품</cite></c:when> B2C 
										<c:when test="${result.prtnrId eq 'PRTNR_0001' }"><cite>체험구독</cite></c:when> EZWEL 
									</c:choose>
								</div>
							</c:when>
							</c:choose> --%>
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
					<!-- <label class="btn-zzim"><input type="checkbox" title="찜" /></label> -->
				</li>
			</c:forEach>
		</c:if>
	</c:when>
</c:choose>