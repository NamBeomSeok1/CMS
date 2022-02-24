<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/modoo/common/commonTagLibs.jsp"%>
<!DOCTYPE html>
<html>
<head>
	<title>주문완료</title>
</head>
<body>
<div class="wrap">
			<div class="sub-contents">
				<section>
					<div class="sub-tit-area">
						<h3 class="sub-tit">주문이 완료되었습니다.</h3>
					</div>
					<div class="table-type">
						<div class="thead">
							<cite class="col-w200">주문번호</cite>
							<cite>상품정보</cite>
							<cite>주문정보</cite>
							<cite class="col-w300">결제금액</cite>
						</div>
                        <c:set var="totalOrderPc" value="0"/>
						<c:forEach var="result" items="${resultList }" varStatus="status">
                        <c:set var="totalOrderPc" value="${totalOrderPc+result.totAmount+result.dscntAmount}"/>
							<div class="tbody">
								<div class="col-w200">${result.orderNo}</div>
								<div class="al m-col-block">
									<a href="${CTX_ROOT}/shop/goods/goodsView.do?goodsId=${result.goods.goodsId}">
										<div class="thumb-area lg">
											<figure><img src="${CTX_ROOT}${result.goodsTitleImagePath}" alt="${result.goods.goodsNm}" /></figure>
											<div class="txt-area">
												<%-- <cite>${result.goods.makr}</cite> --%>
												<h2 class="tit">${result.goods.goodsNm}
												<c:set var="exprnAmount" value="0"/>
												<c:if test="${result.exprnUseAt eq 'Y'}">
													[체험구독]
													<c:set var="exprnAmount" value="${result.exprnAmount}"/>
												</c:if> 
												</h2>
												<div class="price">
													<strong><span><fmt:formatNumber type="number" pattern="#,###" value="${result.goods.goodsPc+exprnAmount}"/></span>원</strong>
												</div>
											</div>
										</div>
									</a>
								</div>
								<div class="al m-col-block">
								<c:if test="${result.sbscrptCycleSeCode eq 'WEEK'}">
									<ul class="option-info">
										<li>
											<strong>구독주기:</strong>
											<span>${result.sbscrptWeekCycle}주</span>
										</li>
										<li>
											<strong>구독요일 :</strong>
											<span>
											<c:choose>
												<c:when test="${result.sbscrptDlvyWd eq '1' }">일요일</c:when>
												<c:when test="${result.sbscrptDlvyWd eq '2' }">월요일</c:when>
												<c:when test="${result.sbscrptDlvyWd eq '3' }">화요일</c:when>
												<c:when test="${result.sbscrptDlvyWd eq '4' }">수요일</c:when>
												<c:when test="${result.sbscrptDlvyWd eq '5' }">목요일</c:when>
												<c:when test="${result.sbscrptDlvyWd eq '6' }">금요일</c:when>
												<c:when test="${result.sbscrptDlvyWd eq '7' }">토요일</c:when>
											</c:choose>
											</span>
										</li>
									</ul>
								</c:if>
								<c:if test="${result.sbscrptCycleSeCode eq 'MONTH'}">
									<ul class="option-info">
										<li>
											<strong>구독주기:</strong>
											<span>${result.sbscrptMtCycle} 개월</span>
										</li>
										<li>
											<strong>구독일 :</strong>
											<span>${result.sbscrptDlvyDay} 일</span>
										</li>
									</ul>
								</c:if>
									<ul class="option-info">
										<li>
											<cite>수량 :</cite>
											<span>${result.orderCo}개</span>
										</li>
									</ul>
									<c:if test="${!empty result.orderItemList}">
										<ul class="option-info">
										<c:forEach var="item" items="${result.orderItemList}">
													<c:if test="${item.gitemPc ge 0}">
															<c:set var="pc" value="+${item.gitemPc}"/>
													</c:if>
													<c:if test="${item.gitemPc lt 0}">
															<c:set var="pc" value="${item.gitemPc}"/>
													</c:if>
													<c:if test="${item.gistemSeCode eq 'D'}">
														<li>
															<cite>기본옵션 :</cite>
															<span>${item.gitemNm}(${pc}원)</span>
														</li>
													</c:if>
													<c:if test="${item.gistemSeCode eq 'F'}">
														<li>
															<cite>첫구독옵션 :</cite>
															<span>${item.gitemNm}(${pc}원)</span>
														</li>
													</c:if>
													<c:if test="${item.gistemSeCode eq 'A'}">
														<li>
															<cite>추가옵션 :</cite>
															<span>${item.gitemNm}(${pc}원)</span>
														</li>
													</c:if>
											</c:forEach>
										</ul>
										</c:if>
										<ul class="option-info">
											 <%-- <c:if test="${result.exprnUseAt eq 'Y'}">
												<li>
												<c:if test="${result.exprnAmount ge 0}">
														<c:set var="exprnPc" value="+${result.exprnAmount}원"/>
												</c:if>
												<c:if test="${result.exprnAmount lt 0}">
														<c:set var="exprnPc" value="${result.exprnAmount}원"/>
												</c:if>
													<cite>1회 체험 가격 : </cite>
													<span>${exprnPc}</span>
												</li>
											</c:if>  --%>
											<c:set var="compnoDscntPc" value="0"/>
											<c:if test="${result.compnoDscntUseAt eq 'Y'}">
											<c:set var="compnoDscntPc" value="${result.goods.compnoDscntPc*result.orderCo }"/>
												<li>	
													<cite>복수구매할인: </cite>
													<span>${result.goods.compnoDscntPc*result.orderCo}원</span>
												</li>
											</c:if>
											</ul>
										<c:if test="${!empty result.orderQItemList}">
											<ul class="option-info">
												<c:forEach var="qItem" items="${result.orderQItemList}" varStatus="status">
													<li>
														<cite>업체요청사항</cite>
														<span>${status.index+1}. ${qItem.gitemNm}: ${qItem.gitemAnswer}</span>
													</li>
												 </c:forEach>
											</ul>
										</c:if>
									</div>
									<div class="col-w300 al m-col-block">
										<ul class="price-info-list sm">
											<li>
												<cite>상품금액</cite>
												<div>${result.goodsAmount * result.orderCo}원</div>
											</li>
											<li>
												<cite>할인금액</cite>
												<div>${-result.dscntAmount}원</div>
											</li>
											<c:set var = "optPc" value="0"/>
											<c:forEach var="item" items="${result.orderItemList}">
												<c:if test="${item.gitemPc gt 0}">
													<c:set var="optPc" value="${optPc+item.gitemPc}"/>
												</c:if>
											</c:forEach>
											<li>
												<cite>옵션금액</cite>
												<div>${optPc}원</div>
											</li>
											<!-- <li>
												<cite>포인트사용</cite>
												<div>-1,000원</div>
											</li>
											<li>
												<cite>카드결제</cite>
												<div>-1,000원</div>
											</li> -->
											<c:if test="${result.exprnUseAt eq 'Y'}">
												<li>
													<cite>1회 체험 가격</cite>
													<div>${result.exprnAmount}원</div>
												</li>
											</c:if>
											<li>
												<cite>배송비</cite>
												<div>${result.dlvyAmount+result.islandDlvyAmount}원</div>
											</li>
										</ul>
									<div class="total-area sm">
										<cite>총 상품 금액</cite>
										<div class="price">
											<strong><span><fmt:formatNumber type="number" pattern="#,###" value="${result.totAmount+result.dscntAmount}"/></span> 원</strong>
										</div>
									</div>
									<c:if  test="${result.exprnUseAt ne 'Y' && result.orderKndCode eq 'SBS' }">
										<p class="fc-gr fs-sm ar">다음 회차 정기 결제예정액 : <fmt:formatNumber type="number" pattern="#,###" value="${result.nextTotPc+result.dlvyAmount+result.islandDlvyAmount+result.exprnAmount+compnoDscntPc}"/> 원</p>
									</c:if>
								</div>
							</div>
						</c:forEach>
					</div>
					<ul class="bullet fs-sm">
						<li>첫 구독옵션은 1회차에만 적용 및 결제되며, 2회차부터는 미결제 /미배송 됩니다.※ 결제주기 및 옵션/수량 변경은 마이구독 &gt; 구독변경에서 가능합니다.</li>
						<li>※ 결제주기 및 옵션/수량 변경은 마이구독 &gt; 구독변경에서 가능합니다.</li>
						<li>1회체험상품, 일반상품 및 쿠폰상품은 정기결제가 아닌 일회성 단건결제가 이루어집니다.</li>
					</ul>
					<div class="btn-area">
						<a href="${CTX_ROOT }/user/my/mySubscribeNow.do" class="btn-lg spot width">주문 확인하기</a>
						<a href="${CTX_ROOT }/index.do" class="btn-lg width">메인으로</a>
					</div>
				</section>
			</div>
</div>


			<javascript>
            <!-- 네이바 전환페이지 설정 -->
           <!--  <script type="text/javascript"> 
	            var _nasa={};
	            if(window.wcs) _nasa["cnv"] = wcs.cnv("1","${totalOrderPc}"); // 전환유형, 전환가치 설정해야함. 설치매뉴얼 참고
	            </script>  -->
            </javascript>
			
</body>
</html>