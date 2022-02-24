<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/modoo/common/commonTagLibs.jsp"%>
<c:set var="now" value="<%=new java.util.Date()%>" />
<c:set var="today"><fmt:formatDate value="${now}" pattern="d" /></c:set>
<!DOCTYPE html>
<html>
<head>
	<metatag>
		<meta property="og:title" content="${goods.goodsNm}"/>
		<meta property="og:description" content="${goods.goodsIntrcn}"/>
		<c:set var="imageUrl" value=""/>
		<c:forEach var="imgItem" items="${goods.goodsImageList }" varStatus="status">
		<c:if test="${status.first }">
			<c:set var="imageUrl" value="${BASE_URL}${imgItem.goodsMiddlImagePath }"/>
			<meta proerty="og:image" content="${BASE_URL}${imgItem.goodsMiddlImagePath}">
		</c:if>
		</c:forEach>
		<c:if test="${empty goods.goodsImageList }">
			<c:set var="imageUrl" value="${BASE_URL}/resources/front/site/${SITE_ID }/image/logo/modoo_logo.png"/>
			<meta property="og:image" content="${BASE_URL}/resources/front/site/${SITE_ID }/image/logo/modoo_logo.png"/>
		</c:if>
	</metatag>
	<title>FOXEDU STORE</title>
</head>
<body>
		<div class="wrap">
			<div class="sub-contents">
				<c:import url="/embed/shop/goods/goodsCtgryLocation.do" charEncoding="utf-8">
					<c:param name="goodsCtgryId" value="${goods.goodsCtgryId }"/>
				</c:import>
				<section class="product-detail-top">
					<div class="top-img-area">
						<div class="swiper-container">
							<c:if test="${goodsEventInfo.eventAt eq 'Y'}">
								<span class="label-event">이벤트</span>
							</c:if>
							<ul class="swiper-wrapper">
								<c:forEach var="imgItem" items="${goods.goodsImageList }">
									<li class="swiper-slide">
										<img src="<c:out value="${imgItem.goodsLrgeImagePath }"/>" alt="이미지1 썸네일" />
										<c:if test="${goods.soldOutAt eq 'Y'}">
											<div class="soldout"><span>일시품절</span></div>
										</c:if>
									</li>
								</c:forEach>
							</ul>
						</div>
						<button type="button" class="swiper-button-next"></button>
						<button type="button" class="swiper-button-prev"></button>
						<div class="swiper-pagination"></div>
					</div>
					<div class="product-buy-area">
						<form:form modelAttribute="goodsCart" id="goodsOrderForm" name="goodsOrderForm" method="post" action="${CTX_ROOT }/shop/goods/order.do">
							<fieldset>
								<input type="hidden" id="goodsId" name="goodsId" value="${goods.goodsId }"/>
								<input type="hidden" id="goodsKndCode" name="goodsKndCode" value="${goods.goodsKndCode}"/>
								<input type="hidden" id="sbscrptCycleSeCode" name="sbscrptCycleSeCode" value="${goods.sbscrptCycleSeCode }"/>
								<input type="hidden" id="orderMode" name="orderMode" value="CART"/> <%--CART :장바구니, SBS: 구독 --%>
								<input type="hidden" id="goodsPrice" value="${goods.goodsPc }"/>
								<input type="hidden" id="frstOptnEssntlAt" value="${goods.frstOptnEssntlAt }"/>
								<input type="hidden" id="exprnPc"  name="exprnPc" value="${goods.exprnPc}"/>
								<input type="hidden" id="exprnUseAt"  name="exprnUseAt" value=""/>
								<input type="hidden" id="compnoDscntPc"  name="compnoDscntPc" value="${goods.compnoDscntPc}"/>
								<input type="hidden" id="compnoDscntUseAt" value="${goods.compnoDscntUseAt}"/>
							</fieldset>
							<div class="info-area">
								<c:if test="${not empty goods.brandId }">
									<a href="${CTX_ROOT }/shop/goods/brandGoodsList.do?searchGoodsBrandId=${goods.brandId}" class="btn-link">
										<c:out value="${goods.brandNm }"/>몰 상품보러가기</a>
								</c:if>
								<h2><c:out value="${goods.goodsNm }"/></h2>
								<div class="price">
									<strong><span><c:if test="${goods.sbscrptCycleSeCode eq 'MONTH' }">월</c:if> <fmt:formatNumber type="number" pattern="#,###" value="${goods.goodsPc}"/></span> 원</strong>
									<c:if test="${goods.mrktUseAt eq 'Y'}">
										<del><span><fmt:formatNumber type="number" pattern="#,###" value="${goods.mrktPc}"/></span>원</del>
									</c:if>
								</div>
								<c:if test="${user.groupId ne 'GROUP_00000000000001'}">
									<div class="info-fnc-area">
										<div class="layerpopup-sm-area share-area">
											<button type="button" class="btn-layerpopup-sm"><i class="ico-share"></i><span>공유하기</span></button>
											<div class="layerpopup-sm">
												<!--<div class="pop-header">
													<h1>공유하기</h1>
												</div>-->
												<div class="pop-body">
													<div class="share">
														<button type="button" id="kakaoShare" data-title='${goods.goodsNm}' data-description='${goods.goodsIntrcn}' data-link='${BASE_URL}/shop/goods/goodsView.do?goodsId=${goods.goodsId}' data-imageurl='${imageUrl}'><i class="ico-sns-kakao-spot lg"></i><span class="txt-hide">카카오톡</span></button>
														<button type="button" id="facebookShare" data-title='${goods.goodsNm}' data-link='${BASE_URL}/shop/goods/goodsView.do?goodsId=${goods.goodsId}' data-imageurl='${imageUrl}'><i class="ico-sns-facebook-spot lg"></i><span class="txt-hide">페이스북</span></button>
														<!-- <button type="button"><i class="ico-sns-instagram-spot lg"></i><span class="txt-hide">인스타그램</span></button> -->
														<button type="button" id="twitterShare" data-title='${goods.goodsNm}' data-description='${goods.goodsIntrcn}' data-link='${BASE_URL}/shop/goods/goodsView.do?goodsId=${goods.goodsId}' data-imageurl='${imageUrl}'><i class="ico-sns-twitter-spot lg"></i><span class="txt-hide">트위터</span></button>
														<button type="button" id="naverShare" data-title='${goods.goodsNm}' data-description='${goods.goodsIntrcn}' data-link='${BASE_URL}/shop/goods/goodsView.do?goodsId=${goods.goodsId}' data-imageurl='${imageUrl}'><i class="ico-sns-naverblog-spot lg"></i><span class="txt-hide">네이버블로그</span></button>
														<button type="button" id="clipBoard" onclick="urlClipCopy('${BASE_URL}/shop/goods/goodsView.do?goodsId=${goods.goodsId}');"><i class="ico-sns-url-spot lg"></i><span class="txt-hide">URL</span></button>
													</div>
												</div>
												<button type="button" class="btn-close">닫기</button>
											</div>
										</div>
									</div>
								</c:if>
							</div>
							
							<c:choose>
								<c:when test="${goods.soldOutAt eq 'Y'}">
									<div class="fnc-area">
										<div class="info-box">
											<i class="ico-soldout"></i>
											<p>일시품절 입니다.</p>	
										</div>
									</div>
								</c:when>
								<c:when test="${adultCrtYn ne 'Y' && goods.adultCrtAt eq 'Y'}">
								<div class="fnc-area">
									 <div class="info-box">	
										<i class="ico-adult lg vt"></i>
										<p>
											본 상품은 청소년 유해매체물로서 19세 미만의 청소년이 이용할 수 없습니다.<br />
											이용을 원하시면 (청소년보호법에 따라) 나이 및 본인 여부를 확인해 주시기 바랍니다.
										</p>
									</div>
									<ul class="bullet sm">
										<li>본인인증시 입력하신 모든 정보는 항상 암호화되어 처리되며, 본인확인 외에 다른 목적으로 사용되지 않습니다.</li>
										<li>고객님께서 입력하신 정보는 NICE신용평가정보(주)에 제공됩니다.</li>
									</ul>
								</div>
								<div class="btn-area">
									<c:choose>
									<c:when test="${not empty USER_ID}">
										<button type="button" class="btn-lg spot adultCrtBtn">인증 후 구독하기 <i class="ico-arr-r sm back wh" aria-hidden="true"></i></button>
									</c:when>
									<c:otherwise>
										<button type="button"  onclick="popOpen('popupOrder')" class="btn-lg spot">인증 후 구독하기 <i class="ico-arr-r sm back wh" aria-hidden="true"></i></button>
									</c:otherwise>
								</c:choose>
								</div>
								</c:when>
								<c:otherwise>
									<div class="fnc-area">
										<c:if test="${empty USER_ID}">
											<c:set var="exprnChkCnt" value="0"/>
										</c:if>
										<div class="option-check-area">
										<c:if test="${goods.exprnUseAt eq 'Y'}">
											<div class="option-check is-active">
												<button type="button" class="btn-option-check" data-buytype="subscribe"><span class="txt-hide">정기구독 선택</span></button>
	                                			<span class="txt">정기구독</span>
	                                		</div>
											<div class="option-check">
												<button type="button" class="btn-option-check" name="exprnUseAt" data-buytype="experience"><span class="txt-hide">1회 체험 선택</span></button>
												<!-- <label><input type="checkbox" name="exprnUseAt" value="Y" class="optioncheck"/></label> -->
												<span class="txt">1회 체험 구매</span>
												<div class="tooltip-area">
													<button type="button" class="btn-tooltip">
														<i class="ico-info"></i>
													</button>
													<div class="popup-tooltip">
														<div class="pop-header">
															<h1>1회 체험 구매</h1>
															<button type="button" class="btn-close">닫기</button>
														</div>
														<div class="pop-body">
															<p>
																1회 체험 구매를 선택하면 한 번만 결제가 이루어지며, 
																구독상품을 체험해 보실 수 있습니다.
															</p>
														</div>
													</div>
												</div>
												<%-- <div class="price">
												<c:choose>
													<c:when test="${goods.exprnPc ge '0'}">
														+<fmt:formatNumber type="number" pattern="#,###" value="${goods.exprnPc}"/>원
													</c:when>
													<c:otherwise>
														<fmt:formatNumber type="number" pattern="#,###" value="${goods.exprnPc}"/>원
													</c:otherwise>
												</c:choose>	
												</div> --%>
											</div>
										</c:if>
										</div>
										<div class="option-area">
										<ul class="option-list">
										<c:if test="${goods.goodsKndCode eq 'SBS'}">
											<c:choose>
													<c:when test="${goods.sbscrptCycleSeCode eq 'WEEK'}"> <%-- 주 구독 --%>
														<li>
															<cite>구독주기</cite>
															<form:select path="sbscrptWeekCycle" class="sbscrptWeekCycle" title="주기 선택">
																<c:if test="${fn:length(goods.sbscrptWeekCycleList) gt 1 }">
																	<option value="">주기를 선택하세요.</option>
																</c:if>
																<c:forEach var="week" items="${goods.sbscrptWeekCycleList }">
																	<option value="${week }">${week }주</option>
																</c:forEach>
															</form:select>
														</li>
														<li>
															<cite>정기결제요일</cite> <%--오영석 차장 요청 : 구독요일을 정기결제일, 배송요일을 정기결제 요일 변경 10.28 --%>
															<form:select path="sbscrptDlvyWd" class="sbscrptDlvyWd" title="정기결제요일 선택">
																<option value="">정기결제 요일을 선택하세요.</option>
																<c:forEach var="code" items="${wdCodeList }">
																	<c:forEach var="wd" items="${goods.sbscrptDlvyWdList }">
																		<c:if test="${code.code eq wd}">
																			<option value="${code.code }">${code.codeNm }</option>
																		</c:if>
																	</c:forEach>
																</c:forEach>
															</form:select>
														</li>
													</c:when>
													<c:when test="${goods.sbscrptCycleSeCode eq 'MONTH' }"> <%--월 구독 --%>
														<li>
															<cite>구독주기</cite>
															<form:select path="sbscrptMtCycle" class="sbscrptMtCycle" title="구독주기 선택">
																<c:if test="${fn:length(goods.sbscrptMtCycleList) gt 1 }">
																	<option value="">주기를 선택하세요.</option>
																</c:if>
																<c:forEach var="month" items="${goods.sbscrptMtCycleList }">
																	<option value="${month }">${month }개월</option>
																</c:forEach>
															</form:select>
														</li>
														<li>
															<cite>정기결제일</cite>
															<div class="datepicker-area">
																<%-- <form:hidden path="sbscrptDlvyDay" value="${goods.sbscrptDlvyDay }"/>
																<input type="text" class="" disabled value="${goods.sbscrptDlvyDay} 일"/> --%>
																<c:choose>
																	<c:when test="${not empty goods.sbscrptDlvyDay }">
																		<c:choose>
																			<c:when test="${goods.sbscrptDlvyDay eq '0'}"> <%-- 결제일 기준 --%>
																				<form:hidden path="sbscrptDlvyDay" class="sbscrptDlvyDay" value="${today}"/>
																				<input type="text" disabled value="${today } 일"/>
																			</c:when>
																			<c:otherwise>
																				<form:hidden path="sbscrptDlvyDay" class="sbscrptDlvyDay" value="${goods.sbscrptDlvyDay}"/>
																				<input type="text" disabled value="${goods.sbscrptDlvyDay } 일"/>
																			</c:otherwise>
																		</c:choose>
																	</c:when>
																	<c:otherwise>
																		<form:input path="sbscrptDlvyDay" cssClass="datepicker-input sbscrptDlvyDay" placeholder="결제일을 선택하세요" title="결제일을 선택" readonly="true" />
																		<button class="btn-datepicker-toggle" type="button"><span class="text-hide"></span></button>
																	</c:otherwise>
																</c:choose> 
															</div>
														</li>
													</c:when>
												</c:choose>
											</c:if>
											
											<li>
												<cite>수량</cite>
												<div>
													<div class="count">
														<button type="button" class="btn-minus" disabled><span class="txt-hide">빼기</span></button>
														<form:input type="number" path="orderCo"  class="orderCo inputNumber" min="1" max="9999"  value="${orderInfo.orderCo}"  title="수량 입력" maxlength="4"/>
														<button type="button" class="btn-plus" <c:if test="${goods.goodsKndCode eq 'SBS'}">disabled</c:if>><span class="txt-hide">더하기</span></button>
													</div>
													<c:if test="${goods.compnoDscntUseAt eq 'Y'}">
														<p class="msg mt10">2개 이상 구매시 개당 ${-goods.compnoDscntPc}원이 할인됩니다.</p> 
													</c:if>
												</div>
											</li>
											
											<c:set var="orderItemNo" value="0"/>
											
											<c:if test="${goods.optnUseAt eq 'Y' }">
												<c:if test="${goods.dOptnUseAt eq 'Y' }">
													<li>
														<cite>기본옵션</cite>
														<select name="cartItemList[${orderItemNo }].gitemId" id="orderOption"  class="dOpt orderOption" title="옵션선택">
															<option value="">옵션을 선택하세요</option>
															<c:forEach var="opt" items="${goods.dGitemList }">
																<c:if test="${opt.gitemPc gt 0}">
																	<c:set var="pc" value="(+${opt.gitemPc}원)"/>
																</c:if>
																<c:if test="${opt.gitemPc lt 0}">
																		<c:set var="pc" value="(${opt.gitemPc}원)"/>
																</c:if>
																<c:if test="${opt.gitemPc eq 0}">
																	<c:set var="pc" value=""/>
																</c:if>
																<c:choose>
																	<c:when test="${opt.gitemSttusCode eq 'F'}">
																		<option value="${opt.gitemId }" disabled="disabled">${opt.gitemNm }${pc}(품절)</option>
																	</c:when>
																	<c:otherwise>
																		<option value="${opt.gitemId }" data-pc="${opt.gitemPc }">${opt.gitemNm }${pc}</option>
																	</c:otherwise>
																</c:choose>
															</c:forEach>
														</select>
													</li>
													<c:set var="orderItemNo" value="${orderItemNo + 1 }"/>
												</c:if>
												<c:if test="${goods.fOptnUseAt eq 'Y' and goods.goodsKndCode eq 'SBS' }">
													<li>
														<cite>첫구독옵션</cite>
														<select name="cartItemList[${orderItemNo }].gitemId" id="fOptOption" class="fOpt orderOption" title="옵션선택">
															<option value="">첫 구독 옵션을 선택하세요</option>
															<c:forEach var="opt" items="${goods.fGitemList }">
																<c:if test="${opt.gitemPc gt 0}">
																	<c:set var="pc" value="(+${opt.gitemPc}원)"/>
																</c:if>
																<c:if test="${opt.gitemPc lt 0}">
																		<c:set var="pc" value="(${opt.gitemPc}원)"/>
																</c:if>
																<c:if test="${opt.gitemPc eq 0}">
																	<c:set var="pc" value=""/>
																</c:if>
																<c:choose>
																	<c:when test="${opt.gitemSttusCode eq 'F'}">
																		<option value="${opt.gitemId }" disabled="disabled">${opt.gitemNm }${pc}(품절)</option>
																	</c:when>
																	<c:otherwise>
																		<option value="${opt.gitemId }" data-pc="${opt.gitemPc }">${opt.gitemNm }${pc}</option>
																	</c:otherwise>
																</c:choose>
															</c:forEach>
														</select>
														<p class="msg">
															첫 구독옵션은 첫 주문시에만 결제,배송되며 2회차부터는 결제,배송되지 않습니다.
														</p>
													</li>
													<c:set var="orderItemNo" value="${orderItemNo + 1 }"/>
												</c:if>
												<c:if test="${goods.aOptnUseAt eq 'Y' }">
													<li>
														<cite>추가옵션</cite>
														<select name="cartItemList[${orderItemNo }].gitemId" class="aOpt orderOption" title="옵션선택">
															<option value="">옵션을 선택하세요</option>
															<c:forEach var="opt" items="${goods.aGitemList }">
																<c:if test="${opt.gitemPc gt 0}">
																	<c:set var="pc" value="(+${opt.gitemPc}원)"/>
																</c:if>
																<c:if test="${opt.gitemPc lt 0}">
																		<c:set var="pc" value="(${opt.gitemPc}원)"/>
																</c:if>
																<c:if test="${opt.gitemPc eq 0}">
																	<c:set var="pc" value=""/>
																</c:if>
																<c:choose>
																	<c:when test="${opt.gitemSttusCode eq 'F'}">
																		<option value="${opt.gitemId }" disabled="disabled">${opt.gitemNm }${pc}(품절)</option>
																	</c:when>
																	<c:otherwise>
																		<option value="${opt.gitemId }" data-pc="${opt.gitemPc }">${opt.gitemNm }${pc}</option>
																	</c:otherwise>
																</c:choose>
															</c:forEach>
														</select>
													</li>
													<c:set var="orderItemNo" value="${orderItemNo + 1 }"/>
												</c:if>
											</c:if>
										</ul>
										</div>
										 <div class="bg-area">
											<ul class="total-info-list" style="display:none;">
												<li style="display:none;">
													<cite>1회체험 상품금액</cite>
													<div class="exprnPc-area">0원</div>	
												</li>
												<li>
													<cite>복수구매할인</cite>
													<div class="compnoDscntPc-area">0원</div>
												</li>
											</ul>
											<div class="total-area">
												<cite>총 상품 금액</cite>
												<div class="price">
													<strong><span class="totPrice">0</span> 원</strong>
												</div>
											</div>
										</div>
									</div>
								<div class="btn-area">
									<%-- <label class="btn-zzim"><input type="checkbox" title="찜" /></label> --%>
									<%-- <button type="button" id="clipBoard" onclick="urlClipCopy('${CTX_ROOT}/shop/goods/goodsView.do?goodsId=${goods.goodsId}');" class="btn">URL</button> --%>
									<c:choose>
										<c:when test="${fn:contains(USER_ROLE,'ROLE_SHOP') and not fn:contains(USER_ROLE, 'ROLE_EMPLOYEE')}">
											<button type="button" class="btn-lg spot2" disabled>장바구니 담기</button>
											<button type="button" class="btn-lg spot2 btnOrderText" disabled>
												<c:choose>
													<c:when test="${goods.goodsKndCode eq 'SBS'}">구독하기</c:when> 
													<c:otherwise>구매하기</c:otherwise>
												</c:choose>
											<i class="ico-arr-r sm back wh" aria-hidden="true"></i></button>
										</c:when>
										<c:when test="${empty USER_ID }">
											<button type="button" class="btn-lg" data-popup-open="popupOrder" onclick="popOpen('popupOrder');">장바구니 담기</button>
											<button type="button" class="btn-lg spot btnOrderText" data-popup-open="popupOrder" onclick="popOpen('popupOrder');">
												<c:choose>
													<c:when test="${goods.goodsKndCode eq 'SBS'}">
														구독하기
													</c:when>
													<c:otherwise>
														구매하기
													</c:otherwise>
												</c:choose>
											<i class="ico-arr-r sm back wh" aria-hidden="true"></i></button>
										</c:when>
										<c:otherwise>
											<button type="button" class="btn-lg btnOrder" data-order-mode="CART" onclick="popOpen('popupCart');">장바구니 담기</button>
											<button type="button" class="btn-lg spot btnOrder btnOrderText" data-order-mode="SBS">
												<c:choose>
													<c:when test="${goods.goodsKndCode eq 'SBS'}">
														구독하기
													</c:when>
													<c:otherwise>
														구매하기
													</c:otherwise>
												</c:choose>
											<i class="ico-arr-r sm back wh" aria-hidden="true"></i></button>
										</c:otherwise>
									</c:choose>
								</div>
								</c:otherwise>
							</c:choose>
							<c:if test="${goods.goodsKndCode ne 'CPN'}">
								<div class="etc-info-area">
									<dl>
										<c:choose>
											<%-- 폭스구독권(수강권) --%>
											<c:when test="${goods.goodsCtgryId eq 'GCTGRY_0000000000032'}">
												<dt>안내사항</dt>
				                                <dd>
								                                    본 상품은 문자/카카오톡(논의필요)를 통하여 전달되는 e-구독권입니다.<br />
								                                    구독 결제 시, 상품 구매 확인을 위해 안내 전화 후 구매가 확정됩니다.
				                                </dd>
											</c:when>
											<c:otherwise>
												<dt>배송정보</dt>
												<dd class="dlvyInfo-text">
													<c:choose>
														<c:when test="${not empty goods.dlvyPolicyCn}"><modoo:crlf content="${goods.dlvyPolicyCn}"/></c:when>
														<c:otherwise>${systemDlvyPolicyCn }</c:otherwise>
													</c:choose>
													
													<!-- 무료배송 / 설정한 요일에 맞추어 출고가 진행되며 , 배송일정은 판매자의 사정에 따라 <strong>변경 될 수 있음을 알려드립니다.</strong> -->
												</dd>
												<!-- <dt>결제정보</dt>
												<dd>
													첫 결제는 주문 당일에 결제되며, 2회차 결제부터는 배송일 3일전에 정기결제가 이루어 집니다.
												</dd>-->	
												<dt>배송비</dt>
												<dd>
													<c:choose>
														<c:when test="${goods.dlvySeCode eq 'DS01' }">배송비 선불 <fmt:formatNumber type="number" pattern="#,###" value="${goods.dlvyPc}"/>원</c:when>
														<c:when test="${goods.dlvySeCode eq 'DS02' }">배송비 착불 <fmt:formatNumber type="number" pattern="#,###" value="${goods.dlvyPc}"/>원</c:when>
														<c:when test="${goods.dlvySeCode eq 'DS03' }">배송비 무료 </c:when>
													</c:choose>
														<c:if test="${not empty goods.freeDlvyPc and  goods.freeDlvyPc gt '0'}">
															 <fmt:formatNumber type="number" pattern="#,###" value="${goods.freeDlvyPc}"/>원 이상 구매시 무료 /
														</c:if>
														<c:if test="${not empty goods.jejuDlvyPc and goods.jejuDlvyPc gt '0' }">
															제주 지역  <fmt:formatNumber type="number" pattern="#,###" value="${goods.jejuDlvyPc}"/>원 /
														</c:if>
														<c:if test="${not empty goods.islandDlvyPc and goods.islandDlvyPc gt '0' }">
															도서산간지역 <fmt:formatNumber type="number" pattern="#,###" value="${goods.islandDlvyPc}"/>원
														</c:if>
												</dd>
												<c:if test="${goods.goodsKndCode eq 'SBS' }">
													<dt>결제안내</dt>
													<dd class="payInfo-text">
														첫 결제는 주문 당일에 결제되며,<br/> 2회차 결제부터는 지정한 정기결제일에 결제가 이루어 집니다. <br/>
														<c:if test="${goods.sbscrptCycleSeCode eq 'MONTH'}">본 상품은 월 단위로 선택 결제가 가능하며,<br/> 마이페이지에서 결제주기,결제일 변경이 가능합니다.</c:if>
														<c:if test="${goods.sbscrptCycleSeCode eq 'WEEK'}">본 상품은 주 단위로 선택 결제가 가능하며,<br/> 마이페이지에서 결제주기,결제요일변경이 가능합니다.</c:if>
													</dd>
												</c:if>
				
												<c:if test="${!empty goods.sbscrptMinUseMt}">
													<dt>이용주기</dt>
													<dd>
														본 상품은 최소 ${goods.sbscrptMinUseMt}개월 구독기간을 유지해야 하는 상품으로 ${goods.sbscrptMinUseMt}개월 이용 후, 해지가 가능합니다. 구매 시 참고하여주세요.
													</dd>
												</c:if>
											</c:otherwise>
										</c:choose>
									</dl>
								</div>
								</c:if>
							</form:form>
						</div>
				</section>
					<div class="product-detail">
					<ul class="tabs-nav">
						<li class="anchor-detail01"><a href="#detail01">상세정보</a></li>
						<li class="anchor-detail02"><a href="#detail02">리뷰 <em class="review-cnt"></em></a></li>
						<li class="anchor-detail03"><a href="#detail03">Q&amp;A <em class="qaTotalCount"></em></a></li>
						<li class="anchor-detail04"><a href="#detail04">교환/반품 안내</a></li>
					</ul>
					<div class="product-detail-area">
					<!-- 상세정보 -->
						<section id="detail01" class="detail-info">
							<h2 class="txt-hide">상세정보</h2>
							<div class="info-txt-area">
								<div style="margin-bottom:20px;">
									&lt;상품 필수정보고시&gt;
								</div>
								<dl>
									<c:if test="${not empty goods.orgplce }">
										<dt>원산지</dt>
										<dd><c:out value="${goods.orgplce  }"/></dd>
									</c:if>
									<c:if test="${not empty goods.modelNm }">
										<dt>모델명</dt>
										<dd><c:out value="${goods.modelNm }"/></dd>
									</c:if>
									<c:if test="${not empty goods.makr }">
										<dt>제조사</dt>
										<dd><c:out value="${goods.makr }"/></dd>
									</c:if>
									<%-- <c:if test="${not empty goods.brandNm }">
										<dt>브랜드</dt>
										<dd><a href="${CTX_ROOT }/shop/goods/brandGoodsList.do?searchGoodsBrandId=${goods.brandId}" style="text-decoration:underline;">
											<strong><c:out value="${goods.brandNm }"/>몰 상품보러가기</strong></a></dd>
									</c:if> --%>
									<c:if test="${not empty goods.crtfcMatter }">
										<dt>인증사항</dt>
										<dd><c:out value="${goods.crtfcMatter }"/></dd>
									</c:if>
								</dl>
								<%-- <div class="txt-more">
									<button type="button">필수표기정보 더보기 <i class="ico-arr-b sm back" aria-hidden="true"></i></button>
									<div class="txt-area">
										<c:out value="${goods.goodsIntrcn }"/>
									</div>
								</div> --%>
							</div>
							<c:if test="${goodsEventInfo.eventAt eq 'Y'}">
								<c:choose>
									<c:when test="${user.groupId eq 'GROUP_00000000000001'}"><!-- B2B -->
										<div class="info-event-area view-content">
											<c:forEach var="evtImg" items="${goodsEventInfo.evtImageList }">
												<img src="<c:out value="${evtImg.goodsImagePath }"/>" alt="이벤트 이미지" />
											</c:forEach>
										</div>
									</c:when>
									<c:otherwise><!-- B2C -->
										<c:choose>
											<c:when test="${goods.goodsId eq 'GOODS_00000000002002'}">
												<div class="info-event-area view-content">
													<img src="<c:out value="${CTX_ROOT}/html/SITE_00000/event/210202health/images/banner_goods/GROUP_00000000000000/${goods.goodsId}/event.jpg"/>" alt="이벤트 이미지" />
												</div>
											</c:when>
											<c:otherwise>
												<c:if test="${goods.goodsId != 'GOODS_00000000001518'}">
													<div class="info-event-area view-content">
														<img src="<c:out value="${CTX_ROOT}/html/SITE_00000/event/210111open2/images/banner_goods/GROUP_00000000000000/${goods.goodsId}/event.jpg"/>" alt="이벤트 이미지" />
													</div>
												</c:if>
											</c:otherwise>
										</c:choose>
											
									</c:otherwise>
								</c:choose>
							</c:if>
							<c:if test="${not empty goods.mvpSourcCn }">
								<div class="info-video-area">
									<div class="video-box">
										<c:out value="${goods.mvpSourcCn }" escapeXml="false"/>
									</div>
								</div>
							</c:if>
							<div class="info-img-area">
								<div class="img-area">
									<div class="img-box view-content" id="detailImg">
										<c:out value="${goods.goodsCn }" escapeXml="false"/>
										<c:forEach var="imgItem" items="${goods.gdcImageList }" varStatus="status">
											<img src="<c:out value="${imgItem.goodsImagePath }"/>" alt="상세보기 이미지" />
											<c:if test="${(goods.goodsId eq'GOODS_00000000002002' || goods.goodsId  eq 'GOODS_00000000002012') && status.index eq 0}">
												<div class="video-box">
													<iframe width="560" height="315" src="https://www.youtube.com/embed/fKySZ_CRuKU?loop=1&playlist=fKySZ_CRuKU&rel=0" frameborder="0" allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture" allowfullscreen></iframe>
												</div>
											</c:if>
										</c:forEach>
									</div>
								</div>
								
								<div class="img-more">
									<a href="javascript:void(0);" class="btn-lg width spot2">상세정보 더보기 <i class="ico-arr-b sm back"></i></a>
								</div>
							</div>
						</section>
						<!--추천상품 -->	
						<%-- 
						<c:if test="${not empty goods.brandGoodsRecomendList}">
						 <section id="productRecoSwiper" class="product-recommend">
							<h3 class="sub-tit">추천상품</h3>
							<div class="product-recommend-swiper">
								<div class="swiper-container">
									<ul class="product-list-reco swiper-wrapper">
									<c:forEach var="item" items="${goods.brandGoodsRecomendList}">
										<li class="swiper-slide">
											<a href="/shop/goods/goodsView.do?goodsId=${item.goodsId}" target="_blank">
												<div class="product-img-area">
													<img src="${item.goodsTitleImagePath}" alt="${item.goodsNm}"/>
													<div class="label-info">
													<c:choose>
														<c:when test="${item.goodsKndCode eq 'SBS'}">
															<cite>구독상품</cite>
															<c:if test="${item.sbscrptCycleSeCode eq 'MONTH'}">
																<strong>주단위</strong>
															</c:if>
															<c:if test="${item.sbscrptCycleSeCode eq 'WEEK'}">
																<strong>월단위</strong>
															</c:if>
														</c:when>
														<c:otherwise>
														</c:otherwise>
													</c:choose> -- %>
													</div>
												</div>
												<div class="product-txt-area">
													<h3>
														${item.goodsNm}
													</h3>
													<div class="price">
														<strong><span><fmt:formatNumber type="number" pattern="#,###" value="${item.goodsPc}"/></span>원</strong>
													</div>
													<%-- <c:if test="${item.mrktUseAt eq 'Y'}">
														<del><span><fmt:formatNumber type="number" pattern="#,###" value="${goods.mrktPc}"/></span>원</del>
													</c:if> -- %>
												</div>
											</a>
										</li>
										</c:forEach>
									 </ul>
								</div>	
							</div>
							<div class="swiper-prevnext">
								<div class="swiper-button-prev"><span>이전</span></div>
								<div class="swiper-button-next"><span>다음</span></div>
							</div>
						</section>
						</c:if>
						--%>
						<!-- 리뷰 -->
						<section id="detail02" class="detail-review">
							<c:import url="/shop/goods/review/reviewList.do" charEncoding="utf-8"/>
						</section>

						<!-- QNA  -->
						<section id="detail03" class="detail-qa">
							<c:import url="/shop/goods/qainfo/qaInfo.do" charEncoding="utf-8">
							 </c:import> 
						</section>
						
						<!-- 교환/반품 안내 -->
						<section id="detail04" class="detail-etc">
							<%-- <c:import url="/shop/goods/goodsGuidance.do" charEncoding="utf-8">
								<c:param name="cmpnyId" value="${goods.cmpnyId }"/>
							</c:import> --%>
								<div class="sub-tit-area">
									<h2>교환/반품 안내</h2>
								</div>
								<ul class="border-list etc-list">
									<li>
										<cite>판매자 정보</cite>
										<div>
											업체명  : <c:out value="${cmpny.cmpnyNm }"/><br />
											통신판매업신고번호 : <c:out value="${cmpny.bizrno }"/><br />
											사업장 소재지 : <c:out value="${cmpny.bsnmAdres }"/><br />
											대표자 : <c:out value="${cmpny.rprsntvNm }"/><br />
											사업자등록번호 : <c:out value="${cmpny.bizrno }"/><br />
											소비자상담전화 : <c:out value="${goods.cnsltTelno }"/><br />
											이메일주소 : <c:out value="${cmpny.chargerEmail }"/><br />
											<c:if test="${not empty cmpny.csChnnl }">
											상담채널 : <a href="<c:out value="${cmpny.csChnnl}"/>" class="fc-spot" target="_blank">상담톡 바로가기 <i class="ico-arr-r gr sm"></i></a>
											</c:if>
											<p class="fs-sm fc-gr">* 상품정보에 오류가 있을 경우 고객센터(<c:out value="${goods.cnsltTelno }"/>)로 연락주시면 즉시 확인하도록 하겠습니다.</p>
										</div>
									</li>
									<c:choose>
										<%-- 폭스구독권(수강권) --%>
										<c:when test="${goods.goodsCtgryId eq 'GCTGRY_0000000000032'}">
											<li>
			                                    <cite><c:out value="${cmpny.cmpnyNm }"/> 멤버십<br /> 방법</cite>
			                                    <div>
			                                        <ol class="bullet">
			                                            <li>멤버십 구독 타입 이 필요시, 대표번호로 연락주시면 ‘회사’와의 상담 이후 진행 안내 도움 드리겠습니다.</li>
			                                            <li>대표번호 : <c:out value="${goods.cnsltTelno }"/></li>
			                                        </ol>
			                                    </div>
			                                </li>
			                                <li>
			                                    <cite><c:out value="${cmpny.cmpnyNm }"/> 멤버십<br />해지 및 환불</cite>
			                                    <div>
			                                        <div class="info-type">
					                                    <c:choose>
					                                    	<%-- [2월]방학 패키지 이용권(2/12~2/28) 상품 1회성 --%>
															<c:when test="${goods.goodsId eq 'GOODS_00000000001604'}">
					                                            <table>
					                                                <caption>해지 및 환불 정보</caption>
					                                                <colgroup>
					                                                    <col style="width:20%" />
					                                                    <col />
					                                                </colgroup>
					                                                <tbody>
					                                                    <tr>
					                                                        <td class="al">이용권</td>
					                                                        <td class="al">
					                                                            <ul class="bullet">
					                                                                <li>시작일 4일 이내, 미 수강시 전액 환불 </li>
					                                                                <li>시작일 기준 4일 이내 결제금액의 3/4 환불 </li>
					                                                                <li>시작일 기준 8일 이내 결제금액의 1/2 환불</li>
					                                                                <li>시작일 기준 12일 이내 결제금액의 1/4 환불</li>
					                                                            </ul>
					                                                        </td>
					                                                    </tr>
					                                                </tbody>
					                                            </table>
					                                            <p class="fs-sm fc-gr mt10">* 시작일은 구독권 결제 후, 센터에 방문하여  E-구독권을 사용한 일자를 기준으로 합니다.</p>
					                                            <p class="fs-sm fc-gr mt10">* 구독권 결제 후 4일 이내에 센터를 통해 사용신청 해야 하며, 4일 이상 사용신청이 없으면 환불됩니다.</p>
					                                            <p class="fs-sm fc-gr">* 결제할 때 제공된 사은품 및 할인 혜택의 경우 금액으로 환산하여 환불금액에서 공제</p>
					                                    	</c:when>
					                                    	<c:otherwise>
					                                    		<table>
					                                                <caption>해지 및 환불 정보</caption>
					                                                <colgroup>
					                                                    <col style="width:20%" />
					                                                    <col />
					                                                </colgroup>
					                                                <tbody>
					                                                    <tr>
					                                                        <td class="al">월 구독권</td>
					                                                        <td class="al">
					                                                            <ul class="bullet">
					                                                                <li>시작일 7일 이내, 미 수강시 전액 환불 </li>
					                                                                <li>시작일 기준 7일 이내 결제금액의 3/4 환불 </li>
					                                                                <li>시작일 기준 14일 이내 결제금액의 1/2 환불</li>
					                                                                <li>시작일 기준 21일 이내 결제금액의 1/4 환불</li>
					                                                            </ul>
					                                                        </td>
					                                                    </tr>
					                                                </tbody>
					                                            </table>
					                                            <p class="fs-sm fc-gr mt10">* 시작일은 구독권 결제 후, 센터에 방문하여  E-구독권을 사용한 일자를 기준으로 합니다.</p>
					                                            <p class="fs-sm fc-gr mt10">* 구독권 결제 후 2개월 이내에 센터를 통해 사용신청 해야 하며, 2개월 이상 사용신청이 없으면 환불됩니다.</p>
					                                            <p class="fs-sm fc-gr">* 결제할 때 제공된 사은품 및 할인 혜택의 경우 금액으로 환산하여 환불금액에서 공제</p>
					                                    	</c:otherwise>
					                                    </c:choose>
					                                </div>
					                            </div>
			                                </li>
										</c:when>
										<c:otherwise>
											<li>
												<cite>교환/반품 접수 안내</cite>
												<div>
													<ol class="bullet-demical">
														<li>마이페이지 &gt; 주문관리 &gt; 주문확인 으로 이동</li>
														<li>해당 주문건에서 [취소/교환/반품] 선택하여 접수 진행</li>
													</ol>
													<a href="${CTX_ROOT }/user/my/mySubscribeNow.do" class="fc-gr">접수하러 바로가기 <i class="ico-arr-r gr sm"></i></a>
												</div>
											</li>
											<li>
												<cite><span><c:out value="${cmpny.cmpnyNm }"/></span> 교환/반품 안내</cite>
												<div>
													<ol class="bullet-demical">
														<li>교환/반품에 관한 일반적인 사항은 판매자 제시사항보다 관계법령을 우선시합니다.</li>
														<li>교환/반품 관련 문의사항은 판매자 연락처 및 상품 상세 페이지의 Q&amp;A를 통하여 문의하여 주시기 바랍니다.													
															<ul class="bullet">
																<li>판매자 지정택배사 : 
																<c:choose>
																	<c:when test="${not empty brand.svcHdryNm}">
																		<c:out value="${brand.svcHdryNm}"/>
																	</c:when>
																	<c:otherwise>
																		<c:out value="${cmpny.svcHdryNm}"/>
																	</c:otherwise>
																</c:choose>
																</li>
																<li>반품 배송비 :
																	<c:choose>
																		<c:when test="${not empty brand.rtngudDlvyPc}">
																			<fmt:formatNumber type="number" pattern="#,###" value="${brand.rtngudDlvyPc}"/><c:if test="${not empty brand.rtngudDlvyPc}">원</c:if>
																		</c:when>
																		<c:otherwise>
																			<fmt:formatNumber type="number" pattern="#,###" value="${cmpny.rtngudDlvyPc}"/><c:if test="${not empty cmpny.rtngudDlvyPc}">원</c:if>
																		</c:otherwise>
																	</c:choose>
																	<c:if test="${empty cmpny.rtngudDlvyPc and empty brand.rtngudDlvyPc}">판매자의 정책에 따름</c:if>
																</li>
																<li>교환 배송비 :
																	<c:choose>
																		<c:when test="${not empty brand.exchngDlvyPc}">
																			<fmt:formatNumber type="number" pattern="#,###" value="${brand.exchngDlvyPc}"/><c:if test="${not empty brand.exchngDlvyPc}">원</c:if>
																		</c:when>
																		<c:otherwise>
																			<fmt:formatNumber type="number" pattern="#,###" value="${cmpny.exchngDlvyPc}"/><c:if test="${not empty cmpny.exchngDlvyPc}">원</c:if>
																		</c:otherwise>
																	</c:choose>
																	<c:if test="${empty cmpny.exchngDlvyPc and empty brand.exchngDlvyPc}">판매자의 정책에 따름</c:if>
																</li>
																<li>보내실 곳 :
																<c:choose>
																	<c:when test="${not empty brand.svcAdres}">
																		<c:out value="${brand.svcAdres}"/>
																	</c:when>
																	<c:otherwise>
																		<c:out value="${cmpny.svcAdres}"/>
																	</c:otherwise>
																</c:choose></li>
															</ul>
														</li>
													</ol>
												</div>
											</li>
											<li>
												<cite>교환/반품 사유에 따른 요청 기간</cite>
												<div>
													<p>반품 시 먼저 판매자와 연락하셔서 반품사유, 택배사, 배송비, 반품지 주소 등을 협의하신 후 반품상품을 발송해 주시기 바랍니다.</p>
													<ul class="bullet">
														<li>
															구매자 단순변심 : 상품 수령 후 7일 이내 (구매자 <strong>반품배송비</strong> 부담)
														</li>
														<li>
															표시/광고와 상이, 상품하자 : 상품 수령 후 3개월 이내 혹은 표시/광고와 다른 사실을 안 날로부터 30일 이내 (판매자 반품배송비 부담) 둘 중 하나 경과 시 반품/교환 불가
														</li>
													</ul>
												</div>
											</li>
											<li>
												<cite>교환/반품 불가능 사유</cite>
												<div>
													<p>아래와 같은 경우 반품/교환이 불가능합니다.</p>
		
													<ol class="bullet-demical">
														<li>
															반품요청기간이 지난 경우
														</li>
														<li>
															구매자의 책임 있는 사유로 상품 등이 멸실 또는 훼손된 경우(단, 상품의 내용을 확인하기 위하여 포장 등을 훼손한 경우는 제외)
														</li>
														
														<li>
															구매자의 책임있는 사유로 포장이 훼손되어 상품 가치가 현저히 상실된 경우 (예: 식품, 화장품, 향수류, 음반 등)
														</li>
														
														<li>
															구매자의 사용 또는 일부 소비에 의하여 상품의 가치가 현저히 감소한 경우 (라벨이 떨어진 의류 또는 태그가 떨어진 명품관 상품인 경우)
														</li>
														
														<li>
															시간의 경과에 의하여 재판매가 곤란할 정도로 상품 등의 가치가 현저히 감소한 경우
														</li>
														
														<li>
															고객의 요청사항에 맞춰 제작에 들어가는 맞춤제작상품의 경우(판매자에게 회복불가능한 손해가 예상되고, 그러한 예정으로 청약철회권 행사가 불가하다는 사실을 서면 동의 받는 경우)
														</li>
														<li>
															복제가 가능한 상품 등의 포장을 훼손한 경우(CD/DVD/GAME/도서의 경우 포장 개봉 시)
														</li>
														
													</ol>
												</div>
											</li>
										</c:otherwise>
									</c:choose>
									
								</ul>
						</section>
					</div>
					<div class="product-buy-area">
					<c:choose>	
						<c:when test="${goods.soldOutAt eq 'Y'}">
						<div class="toggle-area">
							<div class="info-area">
								<!--<a href="none" class="btn-link">델몬트</a>-->
								<h2><c:out value="${goods.goodsNm }"/></h2>
								<div class="price">
									<strong><span><c:if test="${goods.sbscrptCycleSeCode eq 'MONTH' }">월</c:if> <fmt:formatNumber type="number" pattern="#,###" value="${goods.goodsPc}"/></span> 원</strong>
								</div>
								<c:if test="${user.groupId ne 'GROUP_00000000000001'}">
								 <div class="info-fnc-area">
									<div class="layerpopup-sm-area share-area">
										<button type="button" class="btn-layerpopup-sm"><i class="ico-share"></i><span class="txt-hide">공유하기</span></button>
										<div class="layerpopup-sm">
											<!--<div class="pop-header">
												<h1>공유하기</h1>
											</div>-->
											<div class="pop-body">
												<div class="share">
													<button type="button" id="kakaoShare" data-title='${goods.goodsNm}' data-description='${goods.goodsIntrcn}' data-link='${BASE_URL}/shop/goods/goodsView.do?goodsId=${goods.goodsId}' data-imageurl='${imageUrl}'><i class="ico-sns-kakao-spot lg"></i><span class="txt-hide">카카오톡</span></button>
													<button type="button" id="facebookShare" data-title='${goods.goodsNm}' data-description='${goods.goodsIntrcn}' data-link='${BASE_URL}/shop/goods/goodsView.do?goodsId=${goods.goodsId}' data-imageurl='${imageUrl}'><i class="ico-sns-facebook-spot lg"></i><span class="txt-hide">페이스북</span></button>
													<!-- <button type="button"><i class="ico-sns-instagram-spot lg"></i><span class="txt-hide">인스타그램</span></button> -->
													<button type="button" id="twitterShare" data-title='${goods.goodsNm}' data-description='${goods.goodsIntrcn}' data-link='${BASE_URL}/shop/goods/goodsView.do?goodsId=${goods.goodsId}' data-imageurl='${imageUrl}'><i class="ico-sns-twitter-spot lg"></i><span class="txt-hide">트위터</span></button>
													<button type="button" id="naverShare" data-title='${goods.goodsNm}' data-description='${goods.goodsIntrcn}' data-link='${BASE_URL}/shop/goods/goodsView.do?goodsId=${goods.goodsId}' data-imageurl='${imageUrl}'><i class="ico-sns-naverblog-spot lg"></i><span class="txt-hide">네이버블로그</span></button>
													<button type="button" id="clipBoard" onclick="urlClipCopy('${BASE_URL}/shop/goods/goodsView.do?goodsId=${goods.goodsId}');"><i class="ico-sns-url-spot lg"></i><span class="txt-hide">URL</span></button>
												</div>
											</div>
											<button type="button" class="btn-close">닫기</button>
										</div>
									</div>
								</div>
								</c:if>
							</div>
						   	<div class="fnc-area">
								<div class="info-box">
									<i class="ico-soldout"></i>
									<p>일시품절 입니다.</p>	
								</div>
							</div>
						</div>
						</c:when>
						<c:when test="${adultCrtYn ne 'Y' && goods.adultCrtAt eq 'Y'}">
						<div class="toggle-area">
							<div class="info-area">
								<!--<a href="none" class="btn-link">델몬트</a>-->
								<h2><c:out value="${goods.goodsNm }"/></h2>
								<div class="price">
									<strong><span><c:if test="${goods.sbscrptCycleSeCode eq 'MONTH' }">월</c:if> <fmt:formatNumber type="number" pattern="#,###" value="${goods.goodsPc}"/></span> 원</strong>
								</div>
								 <div class="info-fnc-area">
									<div class="layerpopup-sm-area share-area">
										<button type="button" class="btn-layerpopup-sm"><i class="ico-share"></i><span class="txt-hide">공유하기</span></button>
										<div class="layerpopup-sm">
											<!--<div class="pop-header">
												<h1>공유하기</h1>
											</div>-->
											<div class="pop-body">
												<div class="share">
													<button type="button" id="kakaoShare" data-title='${goods.goodsNm}' data-description='${goods.goodsIntrcn}' data-link='${BASE_URL}/shop/goods/goodsView.do?goodsId=${goods.goodsId}' data-imageurl='${imageUrl}'><i class="ico-sns-kakao-spot lg"></i><span class="txt-hide">카카오톡</span></button>
													<button type="button" id="facebookShare" data-title='${goods.goodsNm}' data-description='${goods.goodsIntrcn}' data-link='${BASE_URL}/shop/goods/goodsView.do?goodsId=${goods.goodsId}' data-imageurl='${imageUrl}'><i class="ico-sns-facebook-spot lg"></i><span class="txt-hide">페이스북</span></button>
													<!-- <button type="button"><i class="ico-sns-instagram-spot lg"></i><span class="txt-hide">인스타그램</span></button> -->
													<button type="button" id="twitterShare" data-title='${goods.goodsNm}' data-description='${goods.goodsIntrcn}' data-link='${BASE_URL}/shop/goods/goodsView.do?goodsId=${goods.goodsId}' data-imageurl='${imageUrl}'><i class="ico-sns-twitter-spot lg"></i><span class="txt-hide">트위터</span></button>
													<button type="button" id="naverShare" data-title='${goods.goodsNm}' data-description='${goods.goodsIntrcn}' data-link='${BASE_URL}/shop/goods/goodsView.do?goodsId=${goods.goodsId}' data-imageurl='${imageUrl}'><i class="ico-sns-naverblog-spot lg"></i><span class="txt-hide">네이버블로그</span></button>
													<button type="button" id="clipBoard" onclick="urlClipCopy('${BASE_URL}/shop/goods/goodsView.do?goodsId=${goods.goodsId}');"><i class="ico-sns-url-spot lg"></i><span class="txt-hide">URL</span></button>
												</div>
											</div>
											<button type="button" class="btn-close">닫기</button>
										</div>
									</div>
								</div>
							</div>
							  <div class="fnc-area">
								<div class="info-box">
									<i class="ico-adult vt"></i>
									<p>
										본 상품은 청소년 유해매체물로서 19세 미만의 청소년이 이용할 수 없습니다.<br />
										이용을 원하시면 (청소년보호법에 따라) 나이 및 본인 여부를 확인해 주시기 바랍니다.
									</p>
								</div>
								<ul class="bullet sm">
									<li>본인인증시 입력하신 모든 정보는 항상 암호화되어 처리되며, 본인확인 외에 다른 목적으로 사용되지 않습니다.</li>
									<li>고객님께서 입력하신 정보는 NICE신용평가정보(주)에 제공됩니다.</li>
								</ul>
							</div>
							</div>
							<button type="button" class="btn-option-toggle">옵션토글버튼</button>
							<div class="btn-area">
								<c:choose>
									<c:when test="${not empty USER_ID}">
										<button type="button" class="btn-lg spot adultCrtBtn">인증 후 구독하기 <i class="ico-arr-r sm back wh" aria-hidden="true"></i></button>
									</c:when>
									<c:otherwise>
										<button type="button"  onclick="popOpen('popupOrder')" class="btn-lg spot">인증 후 구독하기 <i class="ico-arr-r sm back wh" aria-hidden="true"></i></button>
									</c:otherwise>
								</c:choose>	
							</div>
							</c:when>
							<c:otherwise>
							<div class="toggle-area">
								<div class="info-area">
									<!--<a href="none" class="btn-link">델몬트</a>-->
									<h2><c:out value="${goods.goodsNm }"/></h2>
									<div class="price">
										<strong><span><c:if test="${goods.sbscrptCycleSeCode eq 'MONTH' }">월</c:if> <fmt:formatNumber type="number" pattern="#,###" value="${goods.goodsPc}"/></span> 원</strong>
										<c:if test="${goods.mrktUseAt eq 'Y'}">
											<del><span><fmt:formatNumber type="number" pattern="#,###" value="${goods.mrktPc}"/></span>원</del>
										</c:if>
									</div>
									<c:if test="${user.groupId ne 'GROUP_00000000000001'}">
									 <div class="info-fnc-area">
										<div class="layerpopup-sm-area share-area">
											<button type="button" class="btn-layerpopup-sm"><i class="ico-share"></i><span class="txt-hide">공유하기</span></button>
											<div class="layerpopup-sm">
												<!--<div class="pop-header">
													<h1>공유하기</h1>
												</div>-->
												<div class="pop-body">
													<div class="share">
														<button type="button" id="kakaoShare" data-title='${goods.goodsNm}' data-description='${goods.goodsIntrcn}' data-link='${BASE_URL}/shop/goods/goodsView.do?goodsId=${goods.goodsId}' data-imageurl='${imageUrl}'><i class="ico-sns-kakao-spot lg"></i><span class="txt-hide">카카오톡</span></button>
														<button type="button" id="facebookShare" data-title='${goods.goodsNm}' data-description='${goods.goodsIntrcn}' data-link='${BASE_URL}/shop/goods/goodsView.do?goodsId=${goods.goodsId}' data-imageurl='${imageUrl}'><i class="ico-sns-facebook-spot lg"></i><span class="txt-hide">페이스북</span></button>
														<!-- <button type="button"><i class="ico-sns-instagram-spot lg"></i><span class="txt-hide">인스타그램</span></button> -->
														<button type="button" id="twitterShare" data-title='${goods.goodsNm}' data-description='${goods.goodsIntrcn}' data-link='${BASE_URL}/shop/goods/goodsView.do?goodsId=${goods.goodsId}' data-imageurl='${imageUrl}'><i class="ico-sns-twitter-spot lg"></i><span class="txt-hide">트위터</span></button>
														<button type="button" id="naverShare" data-title='${goods.goodsNm}' data-description='${goods.goodsIntrcn}' data-link='${BASE_URL}/shop/goods/goodsView.do?goodsId=${goods.goodsId}' data-imageurl='${imageUrl}'><i class="ico-sns-naverblog-spot lg"></i><span class="txt-hide">네이버블로그</span></button>
														<button type="button" id="clipBoard" onclick="urlClipCopy('${BASE_URL}/shop/goods/goodsView.do?goodsId=${goods.goodsId}');"><i class="ico-sns-url-spot lg"></i><span class="txt-hide">URL</span></button>
													</div>
												</div>
												<button type="button" class="btn-close">닫기</button>
											</div>
										</div>
									</div>
									</c:if>
								</div>
							<div class="fnc-area">
								<div class="option-check-area">
								<c:if test="${goods.exprnUseAt eq 'Y'}">
										<div class="option-check is-active">
											<button type="button" class="btn-option-check" data-buytype="subscribe"><span class="txt-hide">정기구독 선택</span></button>
                                			<span class="txt">정기구독</span>
                                		</div>
										<div class="option-check">
											<button type="button" class="btn-option-check" name="exprnUseAt" data-buytype="experience"><span class="txt-hide">1회 체험 선택</span></button>
											<!-- <label><input type="checkbox" name="exprnUseAt" value="Y" class="optioncheck"/></label> -->
											<span class="txt">1회 체험 구매</span>
											<div class="tooltip-area">
												<button type="button" class="btn-tooltip">
													<i class="ico-info"></i>
												</button>
												<div class="popup-tooltip">
													<div class="pop-header">
														<h1>1회 체험 구매</h1>
														<button type="button" class="btn-close">닫기</button>
													</div>
													<div class="pop-body">
														<p>
															1회 체험 구매를 선택하면 한 번만 결제가 이루어지며, 
															구독상품을 체험해 보실 수 있습니다.
														</p>
													</div>
												</div>
											</div>
											<%-- <div class="price">
											<c:choose>
												<c:when test="${goods.exprnPc ge '0'}">
													+<fmt:formatNumber type="number" pattern="#,###" value="${goods.exprnPc}"/>원
												</c:when>
												<c:otherwise>
													<fmt:formatNumber type="number" pattern="#,###" value="${goods.exprnPc}"/>원
												</c:otherwise>
											</c:choose>	
											</div> --%>
										</div>
									</c:if>
									</div>
								<div class="option-area">	
								<ul class="option-list">
									<c:if test="${goods.goodsKndCode eq 'SBS'}">
									<c:choose>
										<c:when test="${goods.sbscrptCycleSeCode eq 'WEEK' }"> <%-- 주 구독 --%>
											<li>
												<cite>구독주기</cite>
												<select class="sbscrptWeekCycle" title="주기 선택">
													<c:if test="${fn:length(goods.sbscrptWeekCycleList) gt 1 }">
														<option value="">주기를 선택하세요.</option>
													</c:if>
													<c:forEach var="week" items="${goods.sbscrptWeekCycleList }">
														<option value="${week }">${week }주</option>
													</c:forEach>
												</select>
											</li>
											<li>
												<cite>정기결제요일</cite>
												<select class="sbscrptDlvyWd" title="정기결제요일 선택">
													<option>정기결제요일 선택하세요.</option>
													<c:forEach var="code" items="${wdCodeList }">
														<c:forEach var="wd" items="${goods.sbscrptDlvyWdList }">
															<c:if test="${code.code eq wd}">
																<option value="${code.code }">${code.codeNm }</option>
															</c:if>
														</c:forEach>
													</c:forEach>
												</select>
											</li>
										</c:when>
										<c:when test="${goods.sbscrptCycleSeCode eq 'MONTH' }"> <%--월 구독 --%>
											<li>
												<cite>구독주기</cite>
												<select class="sbscrptMtCycle" title="주기를 선택">
													<c:if test="${fn:length(goods.sbscrptMtCycleList) gt 1 }">
														<option value="">주기를 선택하세요.</option>
													</c:if>
													<c:forEach var="month" items="${goods.sbscrptMtCycleList }">
														<option value="${month }">${month }개월</option>
													</c:forEach>
												</select>
											</li>
											<li class="">
												<cite>정기결제일</cite>
												<div class="datepicker-area">
												<c:choose>
													<c:when test="${not empty goods.sbscrptDlvyDay }">
														<c:choose>
															<c:when test="${goods.sbscrptDlvyDay eq '0'}"> <%-- 결제일 기준 --%>
																<input type="hidden" class="sbscrptDlvyDay" value="${today}"/>
																<input type="text" disabled value="${today } 일"/>
															</c:when>
															<c:otherwise>
																<input type="hidden" class="sbscrptDlvyDay" value="${goods.sbscrptDlvyDay }"/>
																<input type="text"  disabled value="${goods.sbscrptDlvyDay } 일"/>
															</c:otherwise>
														</c:choose>
													</c:when>
													<c:otherwise>
														<input type="text" class="datepicker-input sbscrptDlvyDay" placeholder="결제일을 선택하세요" title="결제일을 선택" readonly />
														<button class="btn-datepicker-toggle" type="button"><span class="text-hide"></span></button>
													</c:otherwise>
												</c:choose> 
												</div>
											</li>
										</c:when>
									</c:choose>
									</c:if>
									<li>
										<cite>수량</cite>
										<div>
											<div class="count">
												<button type="button" class="btn-minus" disabled><span class="txt-hide">빼기</span></button>
												<input type="number" id="orderCo"  class="orderCo inputNumber" min="1" max="9999"  value="1"  title="수량 입력" maxlength="4" />
												<button type="button" class="btn-plus" <c:if test="${goods.goodsKndCode eq 'SBS'}">disabled</c:if>><span class="txt-hide">더하기</span></button>
											</div>
											<c:if test="${goods.compnoDscntUseAt eq 'Y'}">
												<p class="msg mt10">2개 이상 구매시 개당 ${-goods.compnoDscntPc}원이 할인됩니다.</p> 
											</c:if>
										</div>
									</li>
									
									<c:set var="orderItemNo" value="0"/>
									<c:if test="${goods.optnUseAt eq 'Y' }">
										<c:if test="${goods.dOptnUseAt eq 'Y' }">
										<li>
											<cite>기본옵션</cite>
											<select class="dOpt " title="옵션선택">
												<option value="">옵션을 선택하세요</option>
												<c:forEach var="opt" items="${goods.dGitemList }">
														<c:if test="${opt.gitemPc gt 0}">
															<c:set var="pc" value="(+${opt.gitemPc}원)"/>
														</c:if>
														<c:if test="${opt.gitemPc lt 0}">
															<c:set var="pc" value="(${opt.gitemPc}원)"/>
														</c:if>
														<c:if test="${opt.gitemPc eq 0}">
															<c:set var="pc" value=""/>
														</c:if>
														<c:choose>
															<c:when test="${opt.gitemSttusCode eq 'F'}">
																<option value="${opt.gitemId }" disabled="disabled">${opt.gitemNm }${pc}(품절)</option>
															</c:when>
															<c:otherwise>
																<option value="${opt.gitemId }" data-pc="${opt.gitemPc }">${opt.gitemNm }${pc}</option>
															</c:otherwise>
														</c:choose>
														</c:forEach>
													</select>
												</li>
												<c:set var="orderItemNo" value="${orderItemNo + 1 }"/>
											</c:if>
											<c:if test="${goods.fOptnUseAt eq 'Y' and goods.goodsKndCode eq 'SBS' }">
												<li>
													<cite>첫구독옵션</cite>
													<select name="cartItemList[${orderItemNo }].gitemId" class="fOpt orderOption" title="옵션선택">
														<option value="">첫 구독 옵션을 선택하세요</option>
														<c:forEach var="opt" items="${goods.fGitemList }">
															<c:if test="${opt.gitemPc gt 0}">
																<c:set var="pc" value="(+${opt.gitemPc}원)"/>
															</c:if>
															<c:if test="${opt.gitemPc lt 0}">
																<c:set var="pc" value="(${opt.gitemPc}원)"/>
															</c:if>
															<c:if test="${opt.gitemPc eq 0}">
																<c:set var="pc" value=""/>
															</c:if>
															<c:choose>
																<c:when test="${opt.gitemSttusCode eq 'F'}">
																	<option value="${opt.gitemId }" disabled="disabled">${opt.gitemNm }${pc}(품절)</option>
																</c:when>
																<c:otherwise>
																	<option value="${opt.gitemId }" data-pc="${opt.gitemPc }">${opt.gitemNm }${pc}</option>
																</c:otherwise>
															</c:choose>
														</c:forEach>
													</select>
												</li>
												<c:set var="orderItemNo" value="${orderItemNo + 1 }"/>
											</c:if>
											<c:if test="${goods.aOptnUseAt eq 'Y' }">
												<li>
													<cite>추가옵션</cite>
													<select name="cartItemList[${orderItemNo }].gitemId" class="aOpt orderOption" title="옵션선택">
														<option value="">옵션을 선택하세요</option>
														<c:forEach var="opt" items="${goods.aGitemList }">
															<c:if test="${opt.gitemPc gt 0}">
																<c:set var="pc" value="(+${opt.gitemPc}원)"/>
															</c:if>
															<c:if test="${opt.gitemPc lt 0}">
																	<c:set var="pc" value="(${opt.gitemPc}원)"/>
															</c:if>
															<c:if test="${opt.gitemPc eq 0}">
																<c:set var="pc" value=""/>
															</c:if>
															<c:choose>
																<c:when test="${opt.gitemSttusCode eq 'F'}">
																	<option value="${opt.gitemId }" disabled="disabled">${opt.gitemNm }${pc}(품절)</option>
																</c:when>
																<c:otherwise>
																	<option value="${opt.gitemId }" data-pc="${opt.gitemPc }">${opt.gitemNm }${pc}</option>
																</c:otherwise>
															</c:choose>
														</c:forEach>
												</select>
										</li>
										</c:if>
									</c:if>
								</ul>
								</div>
								<div class="bg-area">
									<ul class="total-info-list" style="display:none;">
										<li>
											<cite>1회체험 상품금액</cite>
											<div class="exprnPc-area">0원</div>	
										</li>
										<li>
											<cite>복수구매할인</cite>
											<div class="compnoDscntPc-area">0원</div>
										</li>
									</ul>
									<div class="total-area">
										<cite>총 상품 금액</cite>
										<div class="price">
											<strong><span class="totPrice">0</span> 원</strong>
										</div>
									</div>
								</div>
							</div>
						</div>
						<button type="button" class="btn-option-toggle">옵션토글버튼</button>
						<div class="btn-area">
							<c:choose>
								<c:when test="${fn:contains(USER_ROLE,'ROLE_SHOP') and not fn:contains(USER_ROLE, 'ROLE_EMPLOYEE')}">
									<button type="button" class="btn-lg spot2" disabled>장바구니 담기</button>
									<button type="button" class="btn-lg spot2 btnOrderText" disabled>
										<c:choose>
											<c:when test="${goods.goodsKndCode eq 'SBS'}">구독하기</c:when> 
											<c:otherwise>구매하기</c:otherwise>
										</c:choose>
									<i class="ico-arr-r sm back wh" aria-hidden="true"></i></button>
								</c:when>
								<c:when test="${empty USER_ID }">
									<button type="button" class="btn-lg" data-popup-open="popupOrder" onclick="popOpen('popupOrder');">장바구니 담기</button>
									<button type="button" class="btn-lg spot btnOrderText" data-popup-open="popupOrder" onclick="popOpen('popupOrder');">
										<c:choose>
											<c:when test="${goods.goodsKndCode eq 'SBS'}">
												구독하기
											</c:when>
											<c:otherwise>
												구매하기
											</c:otherwise>
										</c:choose>
									<i class="ico-arr-r sm back wh" aria-hidden="true"></i></button>
								</c:when>
								<c:otherwise>
									<!--<label class="btn-zzim"><input type="checkbox" title="찜" /></label>-->
									<button type="button" class="btn-lg btnOrder" data-order-mode="CART"  onclick="popOpen('popupCart');">장바구니 담기</button>
									<button type="button" class="btn-lg spot btnOrder btnOrderText" data-order-mode="SBS">
										<c:choose>
											<c:when test="${goods.goodsKndCode eq 'SBS'}">
												구독하기
											</c:when>
											<c:otherwise>
												구매하기
											</c:otherwise>
										</c:choose>
									<i class="ico-arr-r sm back wh" aria-hidden="true"></i></button>
								</c:otherwise>
							</c:choose>
						</div>
					</div>
					</c:otherwise>
				</c:choose>		
				</div>
			</div>
		</div>
		
		<div class="popup-alert" data-popup="popupOrder">
			<form id="goodsViewLoginForm" name="goodsViewLoginForm" method="get" action="${CTX_ROOT }/user/sign/loginUser.do">
				<input type="hidden" name="refUrl" value="${requestScope['javax.servlet.forward.request_uri']}?${requestScope['javax.servlet.forward.query_string']}"/>
				<div class="pop-body">
					로그인 후 이용이 가능합니다.
				</div>
				<div class="pop-footer">
					<button type="button" data-popup-close="popupOrder" onclick="popClose('popupOrder');">취소</button>
					<button type="submit" class="spot2">로그인</button>
				</div>
			</form>
		</div>
	
		<div class="popup-alert" data-popup="popupCart">
			<div class="pop-body">
				장바구니에 상품을 담겠습니까?
			</div>
			<div class="pop-footer">
				<button type="button" data-popup-close="popupCart" onclick="popClose('popupCart');">아니오</button>
				<button type="button" id="btnAddCart" class="spot2">예</button>
			</div>
		</div>
		
		<div class="popup-alert" data-popup="popupCartCmplt">
			<div class="pop-body">
				<p class="pop-message">장바구니에 상품이 담겼습니다.</p>
			</div>
			<div class="pop-footer">
				<button type="button" data-popup-close="popupCart" onclick="popClose('popupCartCmplt');">닫기</button>
				<button type="button" class="spot2 button-link" data-src="${CTX_ROOT }/shop/goods/cart.do">장바구니로 이동</button>
			</div>
		</div>
		
		<!--성인인증정보-->
		<c:if test="${not empty iniCertInfo and goods.adultCrtAt eq 'Y'}">
			<form name="reqfrm" id="reqfrm" method="post" action="https://cas.inicis.com/casapp/ui/cardauthreq" style="display: none;">
				<input type="hidden" id="mid" name="mid" value="${iniCertInfo.mid }">
				<input type="hidden" id="Siteurl" name="Siteurl" value="${iniCertInfo.siteurl }">
				<input type="hidden" id="Tradeid" name="Tradeid" value="${iniCertInfo.tradeid }">
				<input type="hidden" id="DI_CODE" name="DI_CODE" value="${iniCertInfo.diCode}">
				<input type="hidden" id="MSTR" name="MSTR" value="${iniCertInfo.mstr}">
				<input type="hidden" id="Closeurl" name="Closeurl" value="${iniCertInfo.closeUrl}">
				<input type="hidden" id="Okurl" name="Okurl" value="${iniCertInfo.okUrl}">
			</form>
		</c:if>
	</div>
	
	<template>					
		<!--날짜선택 -->
		<div class="layer-datepicker">
			<button type="button"><span>1</span></button>
			<button type="button"><span>2</span></button>
			<button type="button"><span>3</span></button>
			<button type="button"><span>4</span></button>
			<button type="button"><span>5</span></button>
			<button type="button"><span>6</span></button>
			<button type="button"><span>7</span></button>
			<button type="button"><span>8</span></button>
			<button type="button"><span>9</span></button>
			<button type="button"><span>10</span></button>
			<button type="button"><span>11</span></button>
			<button type="button"><span>12</span></button>
			<button type="button"><span>13</span></button>
			<button type="button"><span>14</span></button>
			<button type="button"><span>15</span></button>
			<button type="button"><span>16</span></button>
			<button type="button"><span>17</span></button>
			<button type="button"><span>18</span></button>
			<button type="button"><span>19</span></button>
			<button type="button"><span>20</span></button>
			<button type="button"><span>21</span></button>
			<button type="button"><span>22</span></button>
			<button type="button"><span>23</span></button>
			<button type="button"><span>24</span></button>
			<button type="button"><span>25</span></button>
			<button type="button"><span>26</span></button>
			<button type="button"><span>27</span></button>
			<button type="button"><span>28</span></button>
			<button type="button"><span>29</span></button>
			<button type="button"><span>30</span></button>
			<button type="button"><span>31</span></button>
		</div>
	</template>
	
	<javascript>
		<script src="${CTX_ROOT }/resources/front/shop/goods/info/js/goodsView.js"></script>
		<script src="${CTX_ROOT }/resources/front/shop/goods/qna/js/qainfo.js"></script>
		<script src="${CTX_ROOT}/resources/front/shop/goods/comment/js/comment.js"></script>
		<script src="${CTX_ROOT}/resources/front/cmm/etc/js/kakaoApi.js"></script>
		<%-- <c:if test="${viewMode eq 'Y' }">
		<script>
			modooAlert('해당 상품은 등록대기 상품으로 구매 하실 수 없습니다.');
		</script>
		</c:if> --%>
	</javascript>
	
</body>
</html>
