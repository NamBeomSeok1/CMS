<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/modoo/common/commonTagLibs.jsp"%>
<c:set var="title" value="상세보기"/>
<c:set var= "totalAmount" value="0"/>
<title>${title}</title>

	<div class="wrap">
		<input type="hidden" name="siteId" value="${SITE_ID}">
			<c:import url="/user/my/subMenu.do" charEncoding="utf-8">
			</c:import>
			<div class="sub-contents">
					<c:import url="/user/my/myLocation.do" charEncoding="utf-8">
						<c:param name="menuId" value="sbs"/>
					</c:import>
				<h2 class="sub-tit">구독 상세보기</h2>
				<section>
					<div class="sub-tit-area">
						<h3 class="txt-hide">구독상세</h3>
					</div>
					<div class="table-type">
						<div class="thead">
							<cite class="col-w200">주문번호</cite>
							<cite>상품정보</cite>
							<cite>주문정보</cite>
							<cite class="col-w200">결제금액</cite>
						</div>
						<div class="tbody">
							<div class="col-w200">
								${orderInfo.orderNo}
							</div>
							<div class="al m-col-block">
								<a href="${CTX_ROOT}/shop/goods/goodsView.do?goodsId=${goodsInfo.goodsId}">
									<div class="thumb-area lg">
										<figure><img src="${goodsInfo.goodsTitleImagePath}" alt="${goodsInfo.goodsNm}" /></figure>
										<div class="txt-area">
											<%-- <cite>[${orderStatusNm}] ${goodsInfo.goodsNm}</cite> --%>
											 <h2 class="tit">[${orderStatusNm}] ${goodsInfo.goodsNm}</h2>
											 <c:if test="${orderInfo.orderKndCode eq 'SBS'}">
											 	<%-- <p class="fc-gr">${orderInfo.nowOdr-1}회차 / ${dlvySttusCodeNm}</p> --%>
												 	<c:if test="${orderInfo.orderSttusCode ne 'ST04' && orderInfo.orderSttusCode ne 'ST99'}">
												 		<p class="fc-gr">${orderInfo.nowOdr}회차 예정</p>
												 	</c:if>
												 	<c:if test="${orderInfo.orderSttusCode eq 'ST04' || orderInfo.orderSttusCode eq 'ST99'}">
												 		<p class="fc-gr">${orderInfo.nowOdr}회차 취소 </p>
												 	</c:if>
													<c:if test="${minUse gt '0'}">
														<p class="fs-sm fc-gr">최소이용주기 : ${minUse}회</p>
													</c:if>
											</c:if>
										</div>
									</div>
								</a>
							</div>
							<div class="al m-col-block">
								<c:if test="${orderInfo.orderKndCode eq 'SBS'}">
								<ul class="option-info">
									   <li>
										<strong>구독주기 :</strong>
										<span>${sbscrptCycle}</span>
									</li>
									<li>
										<strong>정기결제일 :</strong>
										<span>${sbscrptDlvyDate}</span>
									</li>
								</ul>
								</c:if>
								<ul class="option-info">
									<li>
										<cite>수량 :</cite>
										<span>${orderInfo.orderCo}개</span>
									</li>
								</ul>
								<ul class="option-info">
									<c:choose>
										<c:when test="${orderItemList.size() > 0}">
											<c:set var="totalAmount" value="0"/>
											<c:forEach var="orderItem" items="${orderItemList}" varStatus="status">
											<c:if test="${orderItem.gitemPc gt 0}">
													<c:set var="pc" value="(+${orderItem.gitemPc}원)"/>
												</c:if>
												<c:if test="${orderItem.gitemPc lt 0}">
													<c:set var="pc" value="(${orderItem.gitemPc}원)"/>
												</c:if>
												<c:if test="${orderItem.gitemPc eq 0}">
													<c:set var="pc" value=""/>
												</c:if>
												<c:if test="${orderItem.gistemSeCode eq 'A' or orderItem.gistemSeCode eq 'D' or orderItem.gistemSeCode eq 'F' }">
												<li>
													<cite>${orderItem.gistemSeCodeNm} :</cite>
													<span>${orderItem.gitemNm}${pc}</span>
												</li>
												</c:if>
												<c:if test="${orderItem.gitemPc ge 0}">
													<c:set var= "totalAmount" value="${totalAmount+orderItem.gitemPc}"/>
												</c:if>
											</c:forEach>
										</c:when>
										<c:otherwise>
											<%--
											<li>
												<p class="none-txt">옵션이 없습니다.</p>
											</li>
											--%>
										</c:otherwise>
									</c:choose>
									<%-- <c:if test="${orderInfo.exprnUseAt eq 'Y'}">
										<li>
										<c:if test="${orderInfo.exprnAmount ge 0}">
												<c:set var="exprnPc" value="+${orderInfo.exprnAmount}원"/>
										</c:if>
										<c:if test="${orderInfo.exprnAmount lt 0}">
												<c:set var="exprnPc" value="${orderInfo.exprnAmount}원"/>
										</c:if>
											<cite>1회 체험 가격 : </cite>
											<span>${exprnPc}</span>
										</li>
									</c:if> --%>
									<c:if test="${orderInfo.compnoDscntUseAt eq 'Y'}">
									<li>	
										<cite>복수구매할인: </cite>
										<span>${goodsInfo.compnoDscntPc*orderInfo.orderCo}원</span>
									</li>
									</c:if>
								</ul>
								<c:if test="${not empty goodsItemList}">
									<ul class="option-info">
										<li>
											<cite>업체요청사항</cite>
											<span>
											<c:choose>
												<c:when test="${goodsItemList.size() > 0}">
													<c:forEach var="goodsItem" items="${goodsItemList}" varStatus="status">
														${status.count}.${goodsItem.gitemSeCode}${goodsItem.gitemNm}: 없음
													</c:forEach>
												</c:when>
												<c:otherwise>
													<%-- : 없음 --%>
												</c:otherwise>
											</c:choose>
											</span>
										</li>
									</ul>
								</c:if>
							</div>
							<div class="col-w200 m-col-block">
								<div class="price">
								<c:set var="dlvyAmount" value="${orderInfo.dlvyAmount+orderInfo.islandDlvyAmount}"/>
									 <strong><span><fmt:formatNumber value="${orderInfo.goodsAmount*orderInfo.orderCo + totalAmount + dlvyAmount+orderInfo.exprnAmount+orderInfo.dscntAmount}" pattern="#,###" />원</span></strong>
								</div>
							</div>
						</div>
					</div>
				</section>
				<section>
					<div class="sub-tit-area">
						<h3 class="sub-tit">결제정보</h3>
					</div>
					<ul class="info-table-type">
						<li>
							<cite>상품금액</cite>
							<c:set var="exprnAmount" value="0"/>
							<c:if test="${orderInfo.exprnUseAt eq 'Y'}">
								<c:set var="exprnAmount" value="${goodsInfo.exprnPc*orderInfo.orderCo}"/>
							</c:if> 
							<div>${orderInfo.goodsAmount*orderInfo.orderCo+exprnAmount}원</div>
						</li>
						<li class="col-w400">
							<i class="ico-plus"></i>
							<cite>주문정보</cite>
							<div>
								<c:choose>
							   		<c:when test="${orderItemList.size() > 0}">
										<c:forEach var="orderItem" items="${orderItemList}" varStatus="status">
											<c:if test="${orderItem.gitemPc gt 0}">
												<c:set var="pc" value="(+${orderItem.gitemPc}원)"/>
											</c:if>
											<c:if test="${orderItem.gitemPc lt 0}">
												<c:set var="pc" value="(${orderItem.gitemPc}원)"/>
											</c:if>
											<c:if test="${orderItem.gitemPc eq 0}">
												<c:set var="pc" value=""/>
											</c:if>
											<c:if test="${orderItem.gistemSeCode eq 'A' or orderItem.gistemSeCode eq 'D' or orderItem.gistemSeCode eq 'F' }">
												${orderItem.gistemSeCodeNm} ${orderItem.gitemNm}${pc}<br/>
											</c:if>
								   			<%-- <c:if test="${orderItem.gistemSeCode eq 'Q'}">
												업체 요청 사항 ${status.index+1}. ${orderItem.gitemNm}:${orderItem.gitemAnswer}<br/>
											</c:if> --%>
									 	</c:forEach>
							   		</c:when>
							   		<c:otherwise>
									   	<span>옵션이 없습니다.<br/></span>
							   		</c:otherwise>
								</c:choose>
							 <%--  <c:if test="${orderInfo.exprnUseAt eq 'Y'}">
									<c:if test="${orderInfo.exprnAmount gt 0}">
											<c:set var="exprnPc" value="+${orderInfo.exprnAmount}원"/>
									</c:if>
									<c:if test="${orderInfo.exprnAmount lt 0}">
											<c:set var="exprnPc" value="${orderInfo.exprnAmount}원"/>
									</c:if>
									<c:if test="${orderInfo.exprnAmount eq 0}">
										<c:set var="exprnPc" value=""/>
									</c:if>
										<span>1회 체험 가격 : ${exprnPc}<br/>
								</c:if>--%> 
								<c:if test="${orderInfo.compnoDscntUseAt eq 'Y'}">
									복수구매할인:${goodsInfo.compnoDscntPc*orderInfo.orderCo}원
								</c:if>
							</div>
						</li>
						 <li>
							<i class="ico-plus"></i>
							<cite>배송비</cite>
							<c:set var="dlvyAmount" value="${orderInfo.dlvyAmount+orderInfo.islandDlvyAmount}"/>
							<div>${dlvyAmount}원</div>
						</li>
						<li class="tfooter">
							<i class="ico-equal"></i>
							<cite>결제금액</cite>
							<div class="price">
								<em><strong><span class="fs-lg"><fmt:formatNumber value="${orderInfo.goodsAmount*orderInfo.orderCo + totalAmount + dlvyAmount+orderInfo.exprnAmount+orderInfo.dscntAmount}" pattern="#,###" /></span> 원</strong></em>
							</div>
						</li>
					</ul>
				</section>
				<c:if test="${param.menuNm eq 'refund'}">
					<section>
						<div class="sub-tit-area">
							<h3 class="sub-tit">교환정보</h3>
						</div>
						<div class="list-type">
							<table>
								<caption>교환정보</caption>
								<thead>
									<tr>
										<th scope="col">교환처리</th>
										<th scope="col">교환사유</th>
									</tr>
								</thead>
								<tbody>
									<tr>
										<td class="ac">${orderStatusNm}</td>
										<td class="ac">${qaInfo.qestnCn}</td>
									</tr>
								</tbody>
							</table>
						</div>
					</section>
				</c:if>
				
				<c:if test="${param.menuNm ne 'refund' && not empty orderInfo.dlvyZip }">
				<section>
					<div class="sub-tit-area">
						<h3 class="sub-tit">배송정보</h3>
					</div>
					<div class="write-type">
						<table>
							<caption>배송지 정보</caption>
							<colgroup>
								<col style="width:150px" />
								<col />
							</colgroup>
							<tbody>
								<tr>
									<th scope="row">배송지명</th>
									<td>${orderInfo.dlvyAdresNm}</td>
								</tr>
								<tr>
									<th scope="row">수령인</th>
									<td>${orderInfo.dlvyUserNm}</td>
								</tr>
								<tr>
									<th scope="row">연락처</th>
									<td>${orderInfo.recptrTelno}</td>
								</tr>
								<tr>
									<th scope="row">배송주소</th>
									<td>(${orderInfo.dlvyZip}) ${orderInfo.dlvyAdres}, ${orderInfo.dlvyAdresDetail}</td>
								</tr>
								<tr>
									<th scope="row">배송메세지</th>
									<td>${orderInfo.dlvyMssage}</td>
								</tr>
							</tbody>
						</table>
					</div>
				</section>
				
				<c:if test="${orderInfo.orderKndCode eq 'SBS'}">
				<section>
					<div class="sub-tit-area">
						<h3 class="sub-tit">구독현황</h3>
						<div class="fnc-area">
							 <button type="button" class="btn spot2" onclick="showDlvyHistory('${orderInfo.orderNo}')">
							  		  회차별 상세보기
							 </button>
						</div>
					 </div>
					<div class="list-type">
						<table>
							<caption>구독현황 리스트</caption>
							<tbody>
							   <c:choose>
									<c:when test="${orderDlvyList.size() > 0}">
										<input type="hidden" id="orderNo" value="${orderInfo.orderNo}"/>
										<input type="hidden" id="orderSttusCode" value="${orderInfo.orderSttusCode}"/>
										
										<c:forEach var="dlvyItem" items="${orderDlvyList}" varStatus="status">
											<input type="hidden" class="listCnt"/>
											<input type="hidden" id="dlvySttusCode${status.index}" value="${dlvyItem.dlvySttusCode}" />
											<input type="hidden" id="dlvySetleSttusCode${status.index}" value="${dlvyItem.searchSetleSttusCode}" />
											<tr>
											<td><span class="block">${dlvyItem.orderOdr}회차</span></td>
											
											<td><!-- 상태 표시 area -->
												<c:choose>
													<c:when test="${orderInfo.orderSttusCode eq 'ST01'}"><!-- 해지 접수 -->
														<em>주문취소 접수</em>	
													</c:when>
													<c:when test="${orderInfo.orderSttusCode eq 'ST02'}"><!-- 구독중 -->
														<c:choose>
															<c:when test="${dlvyItem.searchSetleSttusCode eq 'T'}"><em>주문취소</em></c:when>
															<c:when test="${dlvyItem.searchSetleSttusCode eq 'F'}"><em>결제 실패</em></c:when>
															<c:when test="${dlvyItem.searchSetleSttusCode eq 'R'}"><em>결제 대기</em></c:when>
															<c:when test="${dlvyItem.searchSetleSttusCode eq 'P'}"><em>이번 회차 건너뛰기</em></c:when>
															<c:when test="${dlvyItem.searchSetleSttusCode eq 'S'}"><!-- 결제 완료 -->
																<c:choose>
																	<c:when test="${dlvyItem.dlvySttusCode eq 'DLVY00'}"><em>상품준비중</em></c:when>
																	<c:when test="${dlvyItem.dlvySttusCode eq 'DLVY01'}"><em>배송준비중</em></c:when>
																	<c:when test="${dlvyItem.dlvySttusCode eq 'DLVY02'}"><em>배송중</em></c:when>
																	<c:when test="${dlvyItem.dlvySttusCode eq 'DLVY03'}"><em>배송완료</em></c:when>
																	<c:otherwise></c:otherwise>
																</c:choose>
															</c:when>
															<c:when test="${dlvyItem.searchSetleSttusCode eq 'C'}">
															</c:when>
															<c:otherwise>
																		
															</c:otherwise>
														</c:choose>
													</c:when>
													<c:when test="${orderInfo.orderSttusCode eq 'ST03'}">
													</c:when>
													<c:when test="${orderInfo.orderSttusCode eq 'ST04'}"><!-- 구독해지 -->
														<em>주문취소</em>	
													</c:when>
													<c:when test="${orderInfo.orderSttusCode eq 'ST99'}"><!-- 구독취소해지 -->
														<em>주문취소</em>	
													</c:when>
													<c:otherwise></c:otherwise>
												</c:choose>
											</td>
											<td><!-- 버튼 표시 area -->
												<c:choose>
													<c:when test="${orderInfo.orderSttusCode eq 'ST01'}"><!-- 해지 접수 -->
														<!-- 해지버튼 숨김 -->
													</c:when>
													<c:when test="${orderInfo.orderSttusCode eq 'ST02'}"><!-- 구독중 -->
														<c:choose>
															<c:when test="${dlvyItem.searchSetleSttusCode eq 'T'}"></c:when>
															<c:when test="${dlvyItem.searchSetleSttusCode eq 'F'}"></c:when>
															<c:when test="${dlvyItem.searchSetleSttusCode eq 'R'}"><!-- 결제 대기 -->
																<a href="#none" class="btn spot2 sbsModBtn" data-kind="pause" data-dlvyno="${dlvyItem.orderDlvyNo}">건너뛰기</a>
																<!-- 해지버튼 표시 -->
															</c:when>
															<c:when test="${dlvyItem.searchSetleSttusCode eq 'P'}"><!-- 건너뛰기 -->
																<a href="#none" class="btn spot2 sbsModBtn" data-kind="pauseStop" data-dlvyno="${dlvyItem.orderDlvyNo}">건너뛰기해제</a>
																<!-- 해지버튼 표시 -->
															</c:when>
															<c:when test="${dlvyItem.searchSetleSttusCode eq 'S'}"><!-- 결제완료-->
																<c:choose>
																	<c:when test="${dlvyItem.dlvySttusCode eq 'DLVY00'}"> <!-- 상품 준비중 -->
																		<a href="#none" class="btn spot2 pauseBtn" onclick="cancelOrderDirect('${orderInfo.orderNo}', ${orderInfo.orderGroupNo}, ${dlvyItem.orderDlvyNo},'${orderInfo.orderKndCode}')">주문취소</a>
																	</c:when> 
																	<c:when test="${dlvyItem.dlvySttusCode eq 'DLVY01'}"> <!-- 배송준비중 -->
																		<a href="#none" class="btn spot2 pauseBtn" onclick="cancelOrder('${orderInfo.orderNo}','${orderInfo.orderKndCode}')">주문취소</a>
																	</c:when>
																	<c:when test="${dlvyItem.dlvySttusCode eq 'DLVY02'}"> <!-- 배송중 -->
																		<a href="#none" onclick="showDlvyStatus('${dlvyItem.hdryId}', ${dlvyItem.invcNo})" class="btn spot2">배송추적</a>
																	</c:when>
																	<c:when test="${dlvyItem.dlvySttusCode eq 'DLVY03'}"> <!-- 배송완료 -->
																		<a href="#none" onclick="initExchangeOrRecallPop('${orderInfo.orderNo}')" class="btn spot2">교환/반품</a>
																	</c:when>
																	<c:otherwise></c:otherwise>
																</c:choose>
															</c:when>
															<c:otherwise>
																
															</c:otherwise>
														</c:choose>
													</c:when>
													<c:when test="${orderInfo.orderSttusCode eq 'ST03'}">
													</c:when>
													<c:when test="${orderInfo.orderSttusCode eq 'ST04'}">
<!-- 														<em>구독 해지</em>	 -->
													</c:when>
													<c:when test="${orderInfo.orderSttusCode eq 'ST99'}">
<!-- 														<em>구독 해지</em>	 -->
													</c:when>
													<c:otherwise></c:otherwise>
												</c:choose>
											</td>
										</c:forEach>
										
									</c:when>
									<c:otherwise>
										<tr colspan="3">
											<p class="none-txt">구독현황이 없습니다.</p>
										</tr>
									</c:otherwise>
								</c:choose>
								</tbody>
							</table>
					</div>
				</section>
			   </c:if>
				<c:if test="${(orderInfo.orderKndCode eq 'GNR' && dlvyItem.size() gt 0) || (orderInfo.orderKndCode eq 'CPN' && dlvyItem.size() gt 0)}">
					<section>
						<div class="sub-tit-area">
							<h3 class="sub-tit">배송현황</h3>
						 </div>
						<div class="list-type">
							<table>
								<caption>배송 현황</caption>
								<tbody>
									<input type="hidden" id="orderNo" value="${orderInfo.orderNo}"/>
									<input type="hidden" id="orderSttusCode" value="${orderInfo.orderSttusCode}"/>
									<input type="hidden" class="listCnt"/>
									<input type="hidden" id="dlvySttusCode" value="${dlvyItem.dlvySttusCode}" />
									<input type="hidden" id="dlvySetleSttusCode" value="${dlvyItem.searchSetleSttusCode}" />
										<tr>
										<td><!-- 상태 표시 area -->
											<c:choose>
												<c:when test="${orderInfo.orderSttusCode eq 'ST02'}"><!-- 구독중 -->
													<c:choose>
														<c:when test="${dlvyItem.searchSetleSttusCode eq 'S'}"><!-- 결제 완료 -->
															<c:choose>
																<c:when test="${dlvyItem.dlvySttusCode eq 'DLVY00'}"><em>상품준비중</em></c:when>
																<c:when test="${dlvyItem.dlvySttusCode eq 'DLVY01'}"><em>배송준비중</em></c:when>
																<c:when test="${dlvyItem.dlvySttusCode eq 'DLVY02'}"><em>배송중</em></c:when>
																<c:when test="${dlvyItem.dlvySttusCode eq 'DLVY03'}"><em>배송완료</em></c:when>
																<c:otherwise></c:otherwise>
															</c:choose>
														</c:when>
														<c:when test="${dlvyItem.searchSetleSttusCode eq 'C'}">
														</c:when>
														<c:otherwise>
														
														</c:otherwise>
													</c:choose>
												</c:when>
												<c:when test="${orderInfo.orderSttusCode eq 'ST03'}">
												</c:when>
												<c:otherwise></c:otherwise>
											</c:choose>
										</td>
										<td><!-- 버튼 표시 area -->
											<c:choose>
												<c:when test="${orderInfo.orderSttusCode eq 'ST02'}"><!-- 구독중 -->
													<c:choose>
														<c:when test="${dlvyItem.searchSetleSttusCode eq 'S'}"><!-- 결제완료-->
															<c:choose>
																<c:when test="${dlvyItem.dlvySttusCode eq 'DLVY00' && orderInfo.orderKndCode ne 'CPN'}"> <!-- 상품 준비중 -->
<%-- 																	<c:if test="${sbsCnt gt minUse}"> --%>
																		<a href="#none" class="btn spot2 pauseBtn" onclick="cancelOrderDirect('${orderInfo.orderNo}', ${orderInfo.orderGroupNo}, ${dlvyItem.orderDlvyNo},'${orderInfo.orderKndCode}')">주문취소</a>
<%-- 																	</c:if> --%>
																</c:when> 
																<c:when test="${dlvyItem.dlvySttusCode eq 'DLVY01' && orderInfo.orderKndCode ne 'CPN'}"> <!-- 배송준비중 -->
<%-- 																	<c:if test="${sbsCnt gt minUse}"> --%>
																		<a href="#none" class="btn spot2 pauseBtn" onclick="cancelOrder('${orderInfo.orderNo}','${orderInfo.orderKndCode}')">주문취소</a>
<%-- 																	</c:if> --%>
																</c:when>
																<c:when test="${dlvyItem.dlvySttusCode eq 'DLVY02' && not empty dlvyItem.invcNo}"> <!-- 배송중 -->
																	<a href="#none" onclick="showDlvyStatus('${dlvyItem.hdryId}', ${dlvyItem.invcNo})" class="btn spot2">배송추적</a>
																</c:when>
																<%-- <c:when test="${dlvyItem.dlvySttusCode eq 'DLVY02' && empty dlvyItem.invcNo}"> <!-- 배송중 -->
																	<span>직접배송 상품입니다. (배송문의: <c:out value="${goodsInfo.cnsltTelno}"/>)</span>
																</c:when> --%>
																<c:when test="${dlvyItem.dlvySttusCode eq 'DLVY03'}"> <!-- 배송중 -->
																	<a href="#none" onclick="initExchangeOrRecallPop('${orderInfo.orderNo}')" class="btn spot2">교환/반품</a>
																</c:when>
																<c:otherwise></c:otherwise>
															</c:choose>
														</c:when>
														<c:otherwise>
															
														</c:otherwise>
													</c:choose>
												</c:when>
											</c:choose>
										</td>
								</tbody>
							</table>
						</div>
					</section>
			   </c:if>
			   </c:if>
			   <!--쿠폰상품-->
				<c:if test="${empty dlvyItem}">
					<c:set var="dlvyItem" value="${orderDlvyList[0]}"></c:set>
				</c:if>
				<c:if test="${orderInfo.orderKndCode eq 'CPN' && fn:length(orderInfo.orderCouponList) > 0 && param.menuId ne 'sbs_mySubscribeCancel' && orderInfo.orderSttusCode eq 'ST02' && dlvyItem.reqTyCode ne 'E01'&& dlvyItem.reqTyCode ne 'R01'}">
					<section>
						<div class="sub-tit-area">
							<h3 class="sub-tit">이용권 정보</h3>
						</div>
						<div class="border-top">
							<ul class="ticket-list">
								<c:forEach var="item" items="${orderInfo.orderCouponList}" varStatus="status">
									<li>
										<i class="bg-ticket"></i>
										<div class="ticket-txt-area">
											<cite>${item.goodsNm}</cite>
											<em>${item.couponNo}</em>
										</div>
									</li>
								</c:forEach>
							</ul>
						</div>
					</section>
				</c:if>
			   <!-- 구독해지 버튼 -->
			   <c:if test="${orderInfo.orderKndCode eq 'SBS'}">
				   <div class="btn-area stopSubscribe">
					   <c:choose>
						   <c:when test="${orderInfo.orderSttusCode eq 'ST01'}"><!-- 해지 접수 --></c:when>
						   <c:when test="${orderInfo.orderSttusCode eq 'ST02'}"><!-- 구독중 -->
							   <c:choose>
								   <c:when test="${orderDlvyList[1].searchSetleSttusCode eq 'T'}">
								   </c:when>
								   <c:when test="${orderDlvyList[1].searchSetleSttusCode eq 'F'}">
									   <button type="button" id="stopSubscribe" onclick="popOpen('popSubscribeStop1');" class="btn-lg width">구독 해지</button>
								   </c:when>
								   <c:when test="${orderDlvyList[1].searchSetleSttusCode eq 'R'}"><!-- 결제 대기 -->
									   <button type="button" id="stopSubscribe" onclick="popOpen('popSubscribeStop1');" class="btn-lg width">구독 해지</button>
								   </c:when>
								   <c:when test="${orderDlvyList[1].searchSetleSttusCode eq 'P'}">
									   <button type="button" id="stopSubscribe" onclick="popOpen('popSubscribeStop1');" class="btn-lg width">구독 해지</button>
								   </c:when>
								   <c:when test="${orderDlvyList[1].searchSetleSttusCode eq 'S'}"><!-- 결제완료-->
									   <c:choose>
										   <c:when test="${orderDlvyList[1].dlvySttusCode eq 'DLVY00'}"> <!-- 상품 준비중 -->
											   <button type="button" class="btn-lg width pauseBtn" onclick="cancelOrderDirect('${orderInfo.orderNo}', ${orderInfo.orderGroupNo}, ${dlvyItem.orderDlvyNo},'${orderInfo.orderKndCode}');">구독해지</button>
										   </c:when>
										   <c:when test="${orderDlvyList[1].dlvySttusCode eq 'DLVY01'}"> <!-- 배송준비중 -->
											   <button type="button" id="stopSubscribe" onclick="popOpen('popSubscribeCancel1')" class="btn-lg width">구독 해지</button>
										   </c:when>
										   <c:when test="${orderDlvyList[1].dlvySttusCode eq 'DLVY02'}"> <!-- 배송중 -->
											   <button type="button" id="stopSubscribe" class="btn-lg width" onclick="popOpen('popSubscribeStop1');">구독해지</button>
										   </c:when>
										   <c:when test="${orderDlvyList[1].dlvySttusCode eq 'DLVY03'}"> <!-- 배송완료 -->
											   <button type="button" id="stopSubscribe" class="btn-lg width" onclick="popOpen('popSubscribeStop1');">구독해지</button>
										   </c:when>
										   <c:otherwise></c:otherwise>
									   </c:choose>
								   </c:when>
								   <c:otherwise>

								   </c:otherwise>
							   </c:choose>
						   </c:when>
						   <c:otherwise></c:otherwise>
					   </c:choose>
				   </div>
			  <%-- <div class="btn-area ar stopSubscribe">
&lt;%&ndash;						<c:if test="${sbsCnt gt minUse}"> &ndash;%&gt;
					<c:choose>
						<c:when test="${orderInfo.orderSttusCode eq 'ST01'}"><!-- 해지 접수 -->
						</c:when>
						<c:when test="${orderInfo.orderSttusCode eq 'ST02'}"><!-- 구독중 -->
							<c:choose>
								<c:when test="${dlvyItem.searchSetleSttusCode eq 'T'}">
								</c:when>
								<c:when test="${dlvyItem.searchSetleSttusCode eq 'F'}">
									<button type="button" id="stopSubscribe" onclick="stopSubscribe('${orderNo}')"class="btn spot2">구독 해지</button>
								</c:when>
								<c:when test="${dlvyItem.searchSetleSttusCode eq 'R'}"><!-- 결제 대기 -->
									<button type="button" id="stopSubscribe" onclick="stopSubscribe('${orderNo}')"class="btn spot2">구독 해지</button>
								</c:when>
								<c:when test="${dlvyItem.searchSetleSttusCode eq 'P'}">
									<button type="button" id="stopSubscribe" onclick="stopSubscribe('${orderNo}')"class="btn spot2">구독 해지</button>
								</c:when>
								<c:when test="${dlvyItem.searchSetleSttusCode eq 'S'}"><!-- 결제완료-->

									<c:choose>
										<c:when test="${dlvyItem.dlvySttusCode eq 'DLVY00'}"> <!-- 상품 준비중 -->
										</c:when> 
										<c:when test="${dlvyItem.dlvySttusCode eq 'DLVY01'}"> <!-- 배송준비중 -->
										</c:when>
										<c:when test="${dlvyItem.dlvySttusCode eq 'DLVY02'}"> <!-- 배송중 -->
										</c:when>
										<c:when test="${dlvyItem.dlvySttusCode eq 'DLVY03'}"> <!-- 배송완료 -->
											<button type="button" id="stopSubscribe" onclick="stopSubscribe('${orderNo}')"class="btn spot2">구독 해지</button>
										</c:when>
										<c:otherwise></c:otherwise>
									</c:choose>
								</c:when>
								<c:otherwise>
									
								</c:otherwise>
							</c:choose>
						</c:when>
						<c:otherwise></c:otherwise>
					</c:choose>
&lt;%&ndash; 					</c:if> &ndash;%&gt;
			   </div>--%>
		   </c:if>
			<!-- 구독해지 버튼 (한번만사보기상품) -->
		<%--	<c:if test="${orderInfo.orderKndCode eq 'GNR'}">
				<div class="btn-area stopSubscribe">
						&lt;%&ndash;						<c:if test="${sbsCnt gt minUse}"> &ndash;%&gt;
					<c:choose>
						<c:when test="${orderInfo.orderSttusCode eq 'ST01'}"><!-- 해지 접수 -->

						</c:when>
						<c:when test="${orderInfo.orderSttusCode eq 'ST02'}"><!-- 구독중 -->
							<c:choose>
								<c:when test="${dlvyItem.searchSetleSttusCode eq 'T'}">
								</c:when>
								<c:when test="${dlvyItem.searchSetleSttusCode eq 'F'}">
									<button type="button" id="stopSubscribe" onclick="stopSubscribe('${orderInfo.orderNo}')"class="btn spot2">주문 취소</button>
								</c:when>
								<c:when test="${dlvyItem.searchSetleSttusCode eq 'R'}"><!-- 결제 대기 -->
									<button type="button" id="stopSubscribe" onclick="stopSubscribe('${orderInfo.orderNo}')"class="btn spot2">주문 취소</button>
								</c:when>
								<c:when test="${dlvyItem.searchSetleSttusCode eq 'P'}">
									<button type="button" id="stopSubscribe" onclick="stopSubscribe('${orderInfo.orderNo}')"class="btn spot2">주문 취소</button>
								</c:when>
								<c:when test="${dlvyItem.searchSetleSttusCode eq 'S'}"><!-- 결제완료-->
									<c:choose>
										<c:when test="${dlvyItem.dlvySttusCode eq 'DLVY00'}"> <!-- 상품 준비중 -->
											<button type="button" class="btn-lg width pauseBtn" onclick="cancelOrderDirect('${orderInfo.orderNo}', ${orderInfo.orderGroupNo}, ${dlvyItem.orderDlvyNo},'${orderInfo.orderKndCode}');">주문 취소</button>
											&lt;%&ndash;											<a href="#none" class="btn spot2 pauseBtn" onclick="cancelOrderDirect('${orderInfo.orderNo}', ${orderInfo.orderGroupNo}, ${dlvyItem.orderDlvyNo},'${orderInfo.orderKndCode}')">주문취소</a>&ndash;%&gt;
										</c:when>
										<c:when test="${dlvyItem.dlvySttusCode eq 'DLVY01'}"> <!-- 배송준비중 -->
											<button type="button" class="btn-lg width pauseBtn" onclick="popOpen('popSubscribeCancel1');">주문 취소</button>
											&lt;%&ndash;											<a href="#none" class="btn spot2 pauseBtn" onclick="cancelOrder('${orderInfo.orderNo}','${orderInfo.orderKndCode}')">주문취소</a>&ndash;%&gt;
										</c:when>
										<c:when test="${dlvyItem.dlvySttusCode eq 'DLVY02'}"> <!-- 배송중 -->
											<button type="button" class="btn-lg width pauseBtn" onclick="cancelOrder('${orderInfo.orderNo}','${orderInfo.orderKndCode}');">주문 취소</button>
										</c:when>
										<c:when test="${dlvyItem.dlvySttusCode eq 'DLVY03'}"> <!-- 배송완료 -->
											&lt;%&ndash;											<button type="button" id="stopSubscribe" class="btn-lg width" onclick="stopSubscribe('${orderInfo.orderNo}')">주문 취소</button>&ndash;%&gt;
											&lt;%&ndash;											<button type="button" id="stopSubscribe" onclick="stopSubscribe('${orderNo}')"class="btn spot2">구독 해지</button>&ndash;%&gt;
										</c:when>
										<c:otherwise></c:otherwise>
									</c:choose>
								</c:when>
								<c:otherwise>

								</c:otherwise>
							</c:choose>
						</c:when>
						<c:otherwise></c:otherwise>
					</c:choose>
						&lt;%&ndash; 					</c:if> &ndash;%&gt;
				</div>
			</c:if>--%>
			<c:if test="${orderInfo.orderKndCode eq 'CPN' && fn:length(orderInfo.orderCouponList) > 0 && param.menuId ne 'sbs_mySubscribeCancel' && orderInfo.orderSttusCode eq 'ST02'}">
				<div class="btn-area">
				  		<a onclick="cancelOrder('${orderInfo.orderNo}','CPN')" class="btn-lg width">주문취소</a>
				</div>
			</c:if>
		</div>
	<!-- 구독상품이 아닌 상품 주문취소 -->
	<div class="btn-area">
		<c:if test="${orderInfo.orderKndCode eq 'CPN' && fn:length(orderInfo.orderCouponList) > 0 && orderInfo.orderSttusCode eq 'ST02'}">
			<a onclick="cancelOrder('${orderInfo.orderNo}','CPN')" class="btn-lg width">주문취소</a>
		</c:if>
		</div>
	</div>
</div><!-- //site-body -->
	<c:import url="${CTX_ROOT}/user/my/refundWrite.do" charEncoding="utf-8"/>
	<c:import url="${CTX_ROOT}/user/my/subscribeHistory.do" charEncoding="utf-8"/>

	<!-- 배송준비중 -> 구독해지 확인 팝업 -->
	<div class="popup-alert" data-popup="popSubscribeCancel1" style="display: none;">
		<div class="pop-body">
			<p class="tit"><span class="fc-spot">배송 준비중인 상품</span>은 판매자 확인 후 구독해지가 완료됩니다.</p>
			<p class="mt10">구독을 해지하시겠습니까?</p>
		</div>
		<div class="pop-footer">
			<button type="button" onclick="popClose('popSubscribeCancel1');">취소</button>
			<button type="button" class="spot2" onclick="cancelOrderSbs('${orderInfo.orderNo}','${orderInfo.orderKndCode}');">확인</button>
		</div>
	</div>

	<!-- 배송중 -> 구독해지 확인 팝업 -->
	<div class="popup-alert" data-popup="popSubscribeStop1" style="display: none;">
		<div class="pop-body">
			<p class="tit"><span class="fc-spot">다음 회차부터</span> 구독해지가 완료됩니다.</p>
			<p class="mt10">구독을 해지하시겠습니까?</p>
		</div>
		<div class="pop-footer">
			<button type="button" onclick="popClose('popSubscribeStop1');">취소</button>
			<button type="button" class="spot2" onclick="stopSubscribe('${orderInfo.orderDlvyNo}', '${orderInfo.orderNo}', '${orderInfo.orderGroupNo}');">확인</button>
		</div>
	</div>

	<!-- 배송준비중 -> 구독해지 팝업 -->
	<div class="popup-alert" data-popup="popSubscribeCancel" style="display: none; margin-left: -166px; margin-top: -164px; opacity: 1; transform: matrix(1, 0, 0, 1, 0, 0);">
		<div class="pop-body">
			<p class="tit">배송준비중인 상품은<br>판매자 확인 후 구독해지 가능합니다.</p>
			<p class="al mt10">자세한 문의는 소비자 상담전화로 문의바랍니다.</p>
			<p class="al mt10">
				판매자: ${goodsInfo.cmpnyNm}<br>
				소비자상담전화: <a href="tel:${goodsInfo.cnsltTelno}"><em>${goodsInfo.cnsltTelno}</em></a>
			</p>
			<p class="al fc-gr mt20 fs-sm">* 상품의 특성이나 배송상태에 따라서 이번 회차 환불이 불가능할 수 있습니다.</p>
		</div>
		<div class="pop-footer">
			<button type="button" class="spot2" onclick="closeSbsCancelPopup();">확인</button>
		</div>
	</div>

	<!-- 배송중 -> 구독해지 팝업 -->
	<div class="popup-alert" data-popup="popSubscribeStop" style="display: none; margin-left: -166px; margin-top: -164px; opacity: 1; transform: matrix(1, 0, 0, 1, 0, 0);">
		<div class="pop-body">
			<p class="tit">다음 회차부터 해지가 완료됩니다.</p>
			<p class="al mt10">자세한 문의는 소비자 상담전화로 문의바랍니다.</p>
			<p class="al mt10">
				판매자: ${goodsInfo.cmpnyNm}<br>
				소비자상담전화: <a href="tel:${goodsInfo.cnsltTelno}"><em>${goodsInfo.cnsltTelno}</em></a>
			</p>
			<p class="al fc-gr mt20 fs-sm">* 상품의 특성이나 배송상태에 따라서 이번 회차 환불이 불가능할 수 있습니다.</p>
		</div>
		<div class="pop-footer">
			<button type="button" class="spot2" onclick="closeSbsStopPopup();">확인</button>
		</div>
	</div>
	<javascript>
		<script src="${CTX_ROOT}/resources/front/my/mySubscribe/mySubscribeView.js?v=2021091001"></script>
	</javascript>
</body>
</html>