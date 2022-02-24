<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/modoo/common/commonTagLibs.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>장바구니</title>
</head>
<body>
	<div class="wrap">	
			<div class="sub-contents">
				<div class="sticky-cont cart">
					<form id="sendOrder" name="sendOrder" action="/shop/goods/goodsOrder.do" method="get">
					
					<div class="cont-area">
						<h2 class="sub-tit">장바구니</h2>
						<div class="sub-fnc-area">
							<div class="fl">
								<label><input type="checkbox" id="allSelect" /> 전체선택</label>
							</div>
							<div class="fr">
								<button type="button" onclick="deleteCart();" class="btn spot2">선택삭제</button>
							</div>
						</div>
						<section>
							<div class="table-type">
								<div class="thead">
									<div class="col-w50"></div>

									<cite>상품정보</cite>
									<cite class="col-w350">주문정보</cite>
									<cite class="col-w150">결제금액</cite>
								</div>
								<c:forEach var="item" items="${cartList}" varStatus="status">
								<div class="tbody">
									<c:set var="cartPc" value="0"/>
									<c:set var="dlvyPc" value="0"/>
									<c:set var="dscntPc" value="0"/>
									<c:set var="cartPc" value="${cartPc + item.cartTotPc }"/>
									<c:set var="dscntPc" value="${cartPc + item.dscntPc }"/>
									<c:set var="cartAmount" value="${cartPc+item.dscntPc}"/>
									<input type="hidden" name="isCart" value="Y"/>
									<c:choose>
										<c:when test="${cartAmount ge item.goods.freeDlvyPc && item.goods.freeDlvyPc ne '0'}">
											<input type="hidden" id="dlvyPc${status.index}" value="0"/>
											<input type="hidden" id="cartPc${status.index}" value="${item.cartTotPc+item.dscntPc}"/>
										</c:when>
										<c:otherwise>
											<input type="hidden" id="cartPc${status.index}" value="${item.cartTotPc+item.goods.dlvyPc+item.dscntPc}"/>
											<input type="hidden" id="dlvyPc${status.index}" value="${item.goods.dlvyPc}"/>
										</c:otherwise>
									</c:choose>
									<c:set var="exprnPc" value="0"/>
									<c:choose>
										<c:when test="${item.exprnUseAt eq 'Y'}">
											<c:set var="exprnPc" value="${item.goods.exprnPc*item.orderCo}"/>
										</c:when>
										<c:otherwise>
											<c:set var="exprnPc" value="0"/>
										</c:otherwise>
									</c:choose>
									<input type="hidden" id="goodsPc${status.index}" value="${item.goods.goodsPc*item.orderCo+exprnPc}"/>
									<input type="hidden" id="optPc${status.index}" value="${item.optPc}"/>
									<input type="hidden" id="dscntPc${status.index}" value="${-item.dscntPc}"/>
									<input type="hidden" id="orderCo${status.index}" value="${item.orderCo}"/>
									<input type="hidden" id="exprnPc${status.index}" value="${item.goods.exprnPc}"/>
									<input type="hidden" id="compnoDscntUseAt" value="${item.goods.compnoDscntUseAt}"/>
									<div class="col-w50"><label><input type="checkbox" name="cartno" class="selCart" data-selcartno="${status.index}" value="${item.cartNo}" data-cartid="${item.cartNo}" data-goodsid="${item.goodsId}"/></label></div>
									<div class="al m-col-block">
										<a href="${CTX_ROOT}/shop/goods/goodsView.do?goodsId=${item.goods.goodsId}">
											<div class="thumb-area lg">
												<figure><img src="${item.goods.goodsTitleImagePath}" alt="${item.goods.goodsNm }" /></figure>
												<div class="txt-area">
													<%-- <cite>${item.goods.goodsNm}</cite> --%>
													 <h2 class="tit">${item.goods.goodsNm}
													 <c:set var="exprnPc" value="0"/>
													 <c:if test="${item.exprnUseAt eq 'Y'}">
													 [체험구독]
													 <c:set var="exprnPc" value="${item.goods.exprnPc}"/>
													 </c:if>
													 </h2> 
													<div class="price">
														<strong><span><fmt:formatNumber type="number" pattern="#,###" value="${item.goods.goodsPc+exprnPc}"/></span>원</strong>
													</div>
													<c:if test="${item.exprnUseAt ne 'Y'}">
														<c:choose>
															<c:when test="${!empty item.goods.sbscrptMinUseWeek}">
																<p class="fs-sm fc-gr">최소이용주기 : ${item.goods.sbscrptMinUseWeek }주</p>
															</c:when>
															<c:when test="${!empty item.goods.sbscrptMinUseMt}">
																<p class="fs-sm fc-gr">최소이용주기 : ${item.goods.sbscrptMinUseMt}개월</p>
															</c:when>
															<c:otherwise>
															</c:otherwise>
														</c:choose>
													</c:if>
												</div>
											</div>
										</a>
									</div>
									<div class="col-w350 al m-col-block">
										<ul class="option-info">
											<c:choose>
												<c:when test="${item.sbscrptCycleSeCode eq 'WEEK'}">
													<li>
														<strong>구독주기 : </strong>
														<span>${item.sbscrptWeekCycle} 주</span>
													</li>
													<li>
														<strong>정기결제일 : </strong>
														<c:if test="${item.sbscrptDlvyWd eq '1'}">
															<span>일요일</span>
														</c:if>
														<c:if test="${item.sbscrptDlvyWd eq '2'}">
															<span>월요일</span>
														</c:if>
														<c:if test="${item.sbscrptDlvyWd eq '3'}">
															<span>화요일</span>
														</c:if>
														<c:if test="${item.sbscrptDlvyWd eq '4'}">
															<span>수요일</span>
														</c:if>
														<c:if test="${item.sbscrptDlvyWd eq '5'}">
															<span>목요일</span>
														</c:if>
														<c:if test="${item.sbscrptDlvyWd eq '6'}">
															<span>금요일</span>
														</c:if>
														<c:if test="${item.sbscrptDlvyWd eq '7'}">
															<span>토요일</span>
														</c:if>
													</li>
												</c:when>
												<c:when test="${item.sbscrptCycleSeCode eq 'MONTH'}">
													<li>
														<strong>구독주기 :</strong>
														<span>${item.sbscrptMtCycle}개월</span>
													</li>
													<li>
														<strong>정기결제일 :</strong>
														<span>${item.sbscrptDlvyDay}일</span>
													</li>
												</c:when>
											</c:choose>
										</ul>
										<ul class="option-info">
											<li>
												<cite>수량 :</cite>
												<span>${item.orderCo}개</span>
											</li>
										</ul>
										<ul class="option-info">
											<c:forEach var="opt" items="${item.cartItemList}" varStatus="status">
													<c:if test="${opt.gitemPc gt 0}">
														<c:set var="pc" value="(+${opt.gitemPc})"/>
													</c:if>
													<c:if test="${opt.gitemPc lt 0}">
														<c:set var="pc" value="(${opt.gitemPc})"/>
													</c:if>
												<c:if test="${opt.gitemSeCode eq 'D'}">
													<li>
														<cite>기본옵션 :</cite>
														<span>${opt.gitemNm} ${pc}</span>
													</li>
												</c:if>
												<c:if test="${opt.gitemSeCode eq 'F'}">
													<li>
														<cite>첫구독옵션 :</cite>
														<span>${opt.gitemNm} ${pc}</span>
													</li>
												</c:if>
												<c:if test="${opt.gitemSeCode eq 'A'}">
													<li>
														<cite>추가옵션 :</cite>
														<span>${opt.gitemNm} ${pc}</span>
													</li>
												</c:if>
											</c:forEach>
<%-- 											<c:if test="${item.exprnUseAt eq 'Y'}">
												<li>
												<c:if test="${item.goods.exprnPc ge 0}">
														<c:set var="exprnPc" value="+${item.goods.exprnPc}원"/>
												</c:if>
												<c:if test="${item.goods.exprnPc lt 0}">
														<c:set var="exprnPc" value="${item.goods.exprnPc}원"/>
												</c:if>
													<cite>1회 체험 가격 : </cite>
													<span>${exprnPc}</span></li>
											</c:if>
 --%>											<c:if test="${item.compnoDscntUseAt eq 'Y'}">
												<li>	
													<cite>복수구매할인: </cite>
													<span>${item.goods.compnoDscntPc*item.orderCo}원</span>
												</li>
											</c:if>
										</ul>
										<c:if test="${!empty item.goodsItemList }">
										<ul class="option-info">
											<li>
												<cite>업체요청사항</cite>
												<span>
													<c:forEach var="qOpt" items="${item.goodsItemList}" varStatus="status">
														${status.index+1}. ${qOpt.gitemNm}
													</c:forEach>
												</span>
											</li>
										</ul>
										</c:if>
										<c:if test="${item.goods.goodsId ne 'GOODS_00000000001460'}">
											<button type="button" class="btn-option-open" onclick="modCart(${item.cartNo});"  data-popup-open="optionEdit">주문정보변경 <i class="ico-arr-r gr sm back"></i></button>
										</c:if>
									</div>
									<div class="col-w150 m-col-block">
										<div class="price">
												<c:set var="cartPc" value="0"/>
												<c:set var="dlvyPc" value="0"/>
												<c:set var="dscntPc" value="0"/>
												<c:set var="cartPc" value="${cartPc + item.cartTotPc }"/>
												<c:set var="dscntPc" value="${cartPc + item.dscntPc }"/>
												<c:set var="cartAmount" value="${cartPc+item.dscntPc}"/> <%-- 포인트 할인 계산 해야함 --%>
												<strong><span><fmt:formatNumber type="number" pattern="#,###" value="${cartAmount}"/></span>원</strong>
												<c:choose>
													<c:when test="${cartAmount ge item.goods.freeDlvyPc && item.goods.freeDlvyPc ne '0'}">
														 <p class="fc-gr"><fmt:formatNumber type="number" pattern="#,###" value="${item.goods.freeDlvyPc}"/>원 이상 주문 <br/> 배송비무료</p> 
													</c:when>
													<c:otherwise>
														 <p class="fc-gr">배송비 (+<fmt:formatNumber type="number" pattern="#,###" value="${item.goods.dlvyPc}"/>)</p> 
													</c:otherwise>
												</c:choose>
										</div>
									</div>
								</div>
								</c:forEach>
								<c:if test="${!empty cartList }">
								<div class="tfooter">
									<ul class="sum-area">
										<li>기본가격 (<span class="bassPc">0</span>원)</li>
										<li><i>+</i> 옵션가격 (<span class="optPc">0</span>원)</li>
										<li><i>-</i> 할인예상가격 (<span class="dscntPc">0</span>원)</li>
										<li><i>+</i> 배송비(<span class="dlvyPc">0</span>원)</li>
										<li><i>=</i> <strong class="price"><em class="totPc">0</em></strong>원</li>
									</ul>
								</div>
								</c:if>
								<c:if test="${empty cartList }">
								<div class="tbody">
									<p class="none-txt">장바구니 목록이 없습니다.</p>
								</div>
								</c:if>
							</div>
						</section>
					</div>
					<div class="sticky-area total-buy-area">
						<div class="sticky-toggle-area">
							<div class="sticky-header">결제예정금액</div>
							<div class="sticky-body">
								<ul class="price-info-list">
									<li>
										<cite>상품금액</cite>
										<div><span class="bassPc">0</span>원</div>
									</li>
									<li>
										<cite>옵션가격</cite>
										<div><span class="optPc">0</span>원</div>
									</li>
									<li>
										<cite>할인예상금액</cite>
										<div><span class="dscntPc">0</span>원</div>
									</li> 
									<!-- <li>
										<cite>1회 체험 가격</cite>
										<div><span class="exprnPc">0</span>원</div>
									</li>  -->
									<li>
										<cite>배송비</cite>
										<div><span class="dlvyPc">0</span>원</div>
									</li>
								</ul>
								<div class="total-area">
									<cite>총 <em class="orderCo">0</em>건</cite>
									<div class="price">
										<strong><span class="totPc">0</span> 원</strong>
									</div>
								</div>
								<!-- <div class="agree-area">
									<label class="block"><input type="checkbox" /> 개인정보 제 3자 제공 동의</label>
									<label class="block"><input type="checkbox" /> 구매조건 확인 및 결제대행 서비스 약관 동의</label>
								</div> -->
							</div>
						</div>
						<div class="btn-table-area">
							<button type="submit" class="btn-lg spot orderBtn">주문하기</a>
						</div>
					</div>
				</form>
			</div>	
		</div>
	</div>
	
	
	 <!--주문정보변경-->
	<div class="popup" data-popup="optionEdit">
		<div class="pop-header">
			<h1>주문정보 변경</h1>
			<button type="button" class="btn-pop-close" data-popup-close="optionEdit">닫기</button>
		</div>
		<div class="pop-body">
			<ul class="option-list">

			</ul>
			<div class="btn-table-area">
				<!-- <button type="button" class="btn-lg" data-popup-close="optionEdit">취소</button> -->
				<button type="button" id="modBtn" class="btn-lg spot" data-popup-close="optionEdit">변경하기</button>
			</div>
		</div>
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
				 <script src="${CTX_ROOT }/resources/front/shop/goods/info/js/goodsCart.js"></script>
			</javascript>
</body>
</html>