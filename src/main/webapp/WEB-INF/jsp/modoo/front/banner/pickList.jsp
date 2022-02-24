<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/modoo/common/commonTagLibs.jsp"%>
   
   <c:choose>
		<c:when test="${!fn:contains(USER_ID,'100')}">
			<c:set var="isB2B" value="false"/>	
		</c:when>
		<c:otherwise>
			<c:set var="isB2B" value="true"/>
		</c:otherwise>
	</c:choose>
	
    <c:choose>
	<c:when test="${param.searchSeCode eq 'BANN003'}">  <!-- 메인 배너 -->
	    <div class="swiper-container">
	        <ul class="swiper-wrapper">
				
				<c:forEach var="result" items="${resultList}" varStatus="status">
				
			    <c:choose>
					<c:when test="${empty result.bcrnClor}">
						<c:set var="bcrnClor" value="#006CBA"/>
					</c:when>
					<c:otherwise>
						<c:set var="bcrnClor" value="${result.bcrnClor}"/>
					</c:otherwise>
				</c:choose>
				<c:choose>
					<c:when test="${empty result.fontClor}">
						<c:set var="fontClor" value="#fff"/>
					</c:when>
					<c:otherwise>
						<c:set var="fontClor" value="${result.fontClor}"/>
					</c:otherwise>
				</c:choose>
				<c:choose>
					<c:when test="${empty result.bannerLblClor}">
						<c:set var="bannerLblClor" value="#000"/>
					</c:when>
					<c:otherwise>
						<c:set var="bannerLblClor" value="${result.bannerLblClor}"/>
					</c:otherwise>
				</c:choose>
				
				<c:choose>
					<c:when test="${result.bannerTyCode eq 'GOODS'}"> <!-- 모두's pick -->
			            <li class="swiper-slide">
							<a href="${result.bannerLink}" style="background-color:${bcrnClor}; color:${fontClor}" <c:if test="${result.bannerWindowAt eq 'Y'}">target="_blank"</c:if>>
			                    <div class="visual-img-area">
			                        <h2 style="background-color:${bannerLblClor}">${result.bannerLbl}</h2>
			                        <div class="img-cont">
			                            <img src="${result.bannerPath}" alt=" " class="m-none" />
			                            <img src="${result.bannerMPath}" alt=" " class="m-block" />
			                        </div>
			                    </div>
			                    <div class="visual-txt-area">
			                        <cite><c:out value="${result.brandNm}"/></cite>
			                        <h3><modoo:crlf content="${result.bannerNm}" /></h3>
			                        <p>
			                            <modoo:crlf content="${result.evtTxt}" />
			                        </p>
			                        <div class="price">
			                            <strong>
			                            	<span><fmt:formatNumber type="number" maxFractionDigits="3" value="${result.goodsPc}"/></span>원
			                            	<c:if test="${result.mrktUseAt eq 'Y'}">
												<del><span><fmt:formatNumber type="number" pattern="#,###" value="${result.mrktPc}"/></span>원</del>
											</c:if> 	
					                       <%--  <c:choose>
												<c:when test="${result.sbscrptCycleSeCode eq 'WEEK' }">주단위</c:when>
												<c:when test="${result.sbscrptCycleSeCode eq 'MONTH' }">월단위</c:when>
											</c:choose> --%>
										</strong>
			                        </div>
			                    </div>
			                </a>
			            </li>
					</c:when>
					<c:when test="${result.bannerTyCode eq 'EVENT'}"> <!-- EVENT -->
			            <li class="swiper-slide event-slide">
			                <a href="${result.bannerLink}" style="background-color:${bcrnClor}; color:${fontClor}" <c:if test="${result.bannerWindowAt eq 'Y'}">target="_blank"</c:if>>
			                    <div class="visual-img-area">
			                        <h2 style="background-color:${bannerLblClor}">${result.bannerLbl}</h2>
			                        <div class="img-cont">
			                            <img src="${result.bannerPath}" alt=" " class="m-none" />
			                            <img src="${result.bannerMPath}" alt=" " class="m-block" />
			                        </div>
			                    </div>
			                    <div class="visual-txt-area">
			                        <h3><modoo:crlf content="${result.bannerNm}" /></h3>
			                        <p>
			                            <modoo:crlf content="${result.evtTxt}" />
			                        </p>
			                    </div>
			                </a>
			            </li>
					</c:when>
					<c:otherwise>
					
					</c:otherwise>
				</c:choose>
				
				</c:forEach>
	
	        </ul>
	    </div>
	    <!-- If we need navigation buttons -->
		    <div class="swiper-prevnext">
		        <div class="swiper-button-prev">
		            <span>이전</span>
		        </div>
		        <div class="swiper-button-next">
		            <span>다음</span>
		        </div>
		    </div>
		    <div class="swiper-fnc-area">
		        <!-- Progressbar -->
		        <div class="swiper-progress-bar">
		            <span class="bg"></span>
		            <span class="bar"></span>
		        </div>
		        <div class="swiper-playstop">
		            <button type="button" class="btn-play">
		                <span>play</span>
		            </button>
		            <button type="button" class="btn-stop">
		                <span>stop</span>
		            </button>
		        </div>
		        <!-- If we need pagination -->
		        <div class="swiper-pagination"></div>
		    </div>

		
	</c:when>
	<c:when test="${param.searchSeCode eq 'BANN004'}"> <!-- 추천 PICK -->
		<c:forEach var="result" items="${resultList}" varStatus="status">
			<li class="swiper-slide">
                <a href="${CTX_ROOT }/shop/goods/goodsView.do?goodsId=${result.goodsId}">
                    <div class="product-img-area">
                        <img src="<c:out value="${result.goodsLrgeImagePath }"/>" alt=" " />
                       <%--  <c:choose>
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
									<c:when test="${result.prtnrId eq 'PRTNR_0001' }"><cite>체험구독</cite></c:when> EZWEL 
									<c:otherwise><cite>일반상품</cite></c:otherwise>
								</c:choose>
							</div>
						</c:when>
						</c:choose> --%>
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
                            <%-- <del><span><fmt:formatNumber type="number" maxFractionDigits="3" value="${result.mrktPc}"/></span>원</del> --%>
							<strong><span><fmt:formatNumber type="number" maxFractionDigits="3" value="${result.goodsPc}"/></span>원</strong>
                        </div>
                        <div class="label-area">
                            <span class="label">NEW</span>
                            <%-- <span class="label spot">HOT</span> --%>
                        </div>
                    </div>
                </a>
               <!--  <label class="btn-zzim"><input type="checkbox" title="찜" /></label> -->
            </li>
		</c:forEach>
	</c:when>
</c:choose>


