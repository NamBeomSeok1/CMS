<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/modoo/common/commonTagLibs.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<c:set var="title" value="구독변경"/>
<c:set var= "totalAmount" value="0"/>
<title>${title}</title>
</head>
<body>
<c:import url="/user/my/subMenu.do" charEncoding="utf-8">
	<c:param name="menuId" value="sbs_mySubscribeNow"/>
</c:import>
<div class="wrap">
	<div class="sub-contents">
	<%-- <c:import url="/user/my/myLocation.do" charEncoding="utf-8">
		<c:param name="menuId" value="sbs"/>
		<c:param name="subMenuId" value="${title}"/>
	</c:import>  --%>
	  <h2 class="sub-tit">구독 옵션 변경</h2>
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
                                <input type="hidden" id="orderNo" value="${orderInfo.orderNo}">
                                <input type="hidden" id="payCardInfo" value="${payCardInfo}">
                                <input type="hidden" id="payCardId" value="${payCardId}">
                                <input type="hidden" id="jejuDlvyPc" value="${goodsInfo.jejuDlvyPc}">
                                <input type="hidden" id="islandDlvyPc" value="${goodsInfo.islandDlvyPc}">
                                <input type="hidden" id="freeDlvyPc" value="${goodsInfo.freeDlvyPc}">
                                <input type="hidden" id="compnoDscntUseAt" value="${orderInfo.compnoDscntUseAt}">
                            </div>
                            <div class="al m-col-block">
                                <a href="${CTX_ROOT}/shop/goods/goodsView.do?goodsId=${goodsInfo.goodsId}">
                                    <div class="thumb-area lg">
                                        <figure><img src="${goodsInfo.goodsTitleImagePath}" alt="${goodsInfo.goodsNm}" /></figure>
                                        <div class="txt-area">
                                            <%-- <cite>[${orderStatusNm}] ${goodsInfo.goodsNm}</cite> --%>
                                             <h2 class="tit">[${orderStatusNm}] ${goodsInfo.goodsNm}</h2>
                                             <c:if test="${goodsInfo.goodsKndCode eq 'SBS'}">
                                             	<p class="fc-gr">다음회차: ${orderInfo.nowOdr}회차 </p>
	                                            <c:if test="${minUse gt '0'}">
	                                            	<c:if test="${goodsInfo.sbscrptCycleSeCode eq 'WEEK' }">
	                                            		<p class="fs-sm fc-gr">최소이용주기 : ${minUse} 주</p>
	                                            	</c:if>
	                                            	<c:if test="${goodsInfo.sbscrptCycleSeCode eq 'MONTH' }">
	                                            		<p class="fs-sm fc-gr">최소이용주기 : ${minUse} 개월</p>
	                                            	</c:if>
	                                            </c:if>
                                            </c:if>
                                        </div>
                                    </div>
                                </a>
                            </div>
                            <div class="al m-col-block">
                            	<c:if test="${goodsInfo.goodsKndCode eq 'SBS'}">
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
	                                			<c:if test="${orderItem.gistemSeCode eq 'A' or orderItem.gistemSeCode eq 'D'}">
		                                		<li>
			                                        <cite>${orderItem.gistemSeCodeNm} :</cite>
			                                        <span>${orderItem.gitemNm}${pc}</span>
			                                    </li>
			                                    </c:if>
	                                			<c:if test="${orderItem.gistemSeCode eq 'Q'}">
		                                		<li>
			                                        <cite>업체 요청 사항 ${status.index+1}:</cite>
			                                        <span>${orderItem.gitemNm}:${orderItem.gitemAnswer}</span>
			                                    </li>
			                                    </c:if>
			                                    <c:if test="${orderItem.gitemPc ge 0 }">
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
                                <button type="button" class="btn-option-open" onclick="modOrder('${orderInfo.orderNo}');"  data-popup-open="optionEdit">주문정보변경 <i class="ico-arr-r gr sm back"></i></button>
                            </div>
                            <div class="col-w200 m-col-block">
                                <div class="price">
                                   <c:set var="dlvyAmount" value="${orderInfo.dlvyAmount+orderInfo.islandDlvyAmount+orderInfo.dscntAmount}"/>
                                     <strong><span><fmt:formatNumber value="${orderInfo.goodsAmount*orderInfo.orderCo + totalAmount + dlvyAmount}" pattern="#,###" />원</span></strong>
                                     <button type="button" onclick="changeCardOpen();" class="fc-gr sm">결제카드 변경 <i class="ico-arr-r sm back gr" aria-hidden="true"></i></button>
                                </div>
                            </div>
                        </div>
                    </div>
                </section>
				<section>
					<div class="sub-tit-area">
						<h3 class="sub-tit">배송정보변경</h3>
					</div>
					<div class="write-type">
						<p class="txt ar"><i class="required"></i> 필수입력</p>
						<table>
							<caption>신규 입력</caption>
							<colgroup>
								<col style="width:150px" />
								<col />
							</colgroup>
							<tbody>
								<tr>
									<th scope="row">배송지명</th>
									<td><input type="text" id="dlvyAdresNm" class="p10" placeholder="예)집, 회사" value="${orderInfo.dlvyAdresNm}"/></td>
								</tr>
								<tr>
									<th scope="row" class="required">수령인</th>
									<td><input type="text"  id="dlvyUserNm"class="p10" placeholder="이름을 입력하세요." value="${orderInfo.dlvyUserNm}"/></td>
								</tr>
								<tr>
									<th scope="row" class="required">연락처</th>
									<c:set var="telno" value="${fn:split(orderInfo.recptrTelno,'-')}"/>
									<td>
									<c:forEach var="item" items="${telno}" varStatus="status">
										<input type="number" id="ordTelno${status.index+1}" class="p0" <c:if test="${status.index eq 0}">maxlength="3"</c:if> maxlength="4" value="${item}" />
									</c:forEach>
									</td>
								</tr>
								<tr>
									<th scope="row" class="required">배송주소</th>
									<td>
										<input type="text" id="dlvyZip" class="p5" disabled  value="${orderInfo.dlvyZip}"/><button type="button" id="zipOpenBtn" class="btn spot2">주소 찾기</button>
										<input type="text" id="dlvyAdres" class="p10" disabled value="${orderInfo.dlvyAdres}"/>
										<input type="text" id="dlvyAdresDetail" class="p10" value="${orderInfo.dlvyAdresDetail}"/>
										<input type="hidden" id="islandDlvyChk" value="N"/>
										<!-- <div class="block">
											<label><input type="checkbox" id="" name="bassUseAt"/> 기본 배송지로 설정</label>
										</div> -->
										<p id="islandMsg" class="msg"></p>
									</td>
								</tr>
								<tr>
									<th scope="row">배송메세지</th>
									<td>
										<select title="배송메세지 선택" class="dlvyMssage" class="p10">
										<c:choose>
											<c:when test="${fn:contains(orderInfo.dlvyMssage ,'배송')}">										
												<option selected="selected">배송 전에 미리 연락바랍니다.</option>
											</c:when>
											<c:when test="${!fn:contains(orderInfo.dlvyMssage ,'배송')}">
												<option>배송 전에 미리 연락바랍니다.​</option>
											</c:when>
										</c:choose>
										<c:choose>
											<c:when test="${orderInfo.dlvyMssage eq '문 앞에 놔주세요.'}">										
												<option selected="selected">문 앞에 놔주세요.</option>
											</c:when>
											<c:when test="${orderInfo.dlvyMssage ne '문 앞에 놔주세요.'}">
												<option>문 앞에 놔주세요.</option>
											</c:when>
										</c:choose>
										<c:choose>
											<c:when test="${orderInfo.dlvyMssage eq '부재시 전화나 문자 남겨주세요.'}">										
												<option selected="selected">부재시 전화나 문자 남겨주세요.</option>
											</c:when>
											<c:when test="${orderInfo.dlvyMssage ne '부재시 전화나 문자 남겨주세요.'}">
												<option>부재시 전화나 문자 남겨주세요.</option>
											</c:when>
										</c:choose>
										<c:choose>
											<c:when test="${orderInfo.dlvyMssage ne '부재시 전화나 문자 남겨주세요.' and orderInfo.dlvyMssage ne '문 앞에 놔주세요.' and !fn:contains(orderInfo.dlvyMssage ,'배송')}">										
												<option value="0" selected="selected">요청사항을 직접 입력합니다.</option>
											</c:when>
											<c:otherwise>
												<option value="0">요청사항을 직접 입력합니다.</option>
											</c:otherwise>
										</c:choose>
										</select>
										<textarea id="dlvyMssage" rows="3" style="display:none;" data-text="${orderInfo.dlvyMssage}"></textarea>
									</td>
								</tr>	
							</tbody>
						</table>
					</div>
					<div class="btn-area">
						<a href="#none" onclick="modDlvy('${orderInfo.orderNo}');" class="btn-lg width">변경하기</a>
	                </div>
				</section>
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

	<!--날짜선택-->
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
	
	<!-- 배송지api  -->
 <div class="popup" data-popup="adress">
		<div class="pop-header">
			<h1>주소 찾기</h1>
			<button type="button" class="btn-pop-close" data-popup-close="adress">닫기</button>
		</div>
		<div class="pop-body">
			<form name="form" id="form" method="post">
			<input type="hidden" name="currentPage" value="1"/> 
			<input type="hidden" name="countPerPage" value="5"/>
			<input type="hidden" name="resultType" value="json"/>
			<input type="hidden" name="confmKey" value="U01TX0FVVEgyMDIxMDExNDEwMTM1MDExMDY4ODQ="/>
			<div class="sch-area lg">
				<input type="text" name="keyword" value="" onkeydown="enterSearch(this);" placeholder="주소를 입력해주세요." />
				<button type="submit" class="btn-sch"></button>
			</div>
			<p style="display:none;" id="addTotalCnt" class="mb10">총 <em></em>건</p>
			<p style="display:none;" id="message" class="mb10">주소를 상세히 입력해 주시기 바랍니다.</p>
			<ul class="border-list">
			</ul>
			<ul class="paging">
			</ul>
		</div>
	</div>
	
	<!--카드 목록 팝업-->
	 <div class="popup w1200" data-popup="cardSubscribeEdit">
        <div class="pop-header">
            <h1>정기결제 카드변경​</h1>
            <button type="button" class="btn-pop-close" data-popup-close="cardSubscribeEdit">닫기</button>
        </div>
        <div class="pop-body">
            <p class="mb20"><strong>결제카드:</strong><span class="payedCard"></span></p>
            <div class="pop-tit-area">
                <h3 class="pop-tit">정기결제 카드 변경</h3>
                <div class="fnc-area">
                    <button type="button" id="cardWrite" class="btn">카드 등록하기</button>
                </div>
            </div>
            <div class="list-type">
                <table>
                    <caption>정기결제 카드 변경</caption>
                    <thead>
                        <tr>
                            <th scope="col">카드(카드번호)</th>
                            <th scope="col">카드 이름</th>
                            <th scope="col">관리</th>
                        </tr>
                    </thead>
                    <tbody class="cardlist">
                    </tbody>
                </table>
            </div>
        </div>
    </div> 
				
	<!-- 카드 등록폼 -->
	<div class="popup" data-popup="cardWrite">
		<div class="pop-header">
			<h1>카드정보</h1>
			<button type="button" class="btn-pop-close" data-popup-close="cardWrite">닫기</button>
		</div>
		<div class="pop-body">
		<form id="cardForm" name="cardForm" method="post" action="/card/cardReg.json">
			<dl class="write-area">
				<dt>카드번호</dt>
				<dd>
					<div class="card-num-area focus-auto border-input-area">
						<input type="number" class="cardNo" name="cardNo1" maxlength="4" autocomplete="one-time-code" placeholder="0000" title="카드번호 첫번째 입력" />
						<input type="number" class="cardNo" name="cardNo2" maxlength="4" autocomplete="one-time-code" placeholder="0000" title="카드번호 두번째 입력" />
						<input type="password" class="cardNo" name="cardNo3" class="pw" maxlength="4" placeholder="0000" autocomplete="new-password" title="카드번호 세번째 입력" />
						<input type="password" class="cardNo" name="cardNo4" class="pw" maxlength="4" placeholder="0000" autocomplete="new-password" title="카드번호 네번째 입력" />
					</div>
				</dd>
				 <dd class="table-area">
	               	 <div>
	               	 	<cite class="write-tit">유효기간</cite>
						<div class="focus-auto border-input-area">
							<input type="password" name="cardUsgpd1"  autocomplete="new-password" class="pw ac cardUsgpd" maxlength="2"  placeholder="MM" title="월입력(MM)" />
							/
							<input type="password" name="cardUsgpd2"  autocomplete="new-password" class="pw ac cardUsgpd" maxlength="2"  placeholder="YY" title="연입력(YY)" />
						</div>
	               	 </div>
					<div>
						<cite class="write-tit">카드비밀번호</cite>
						<input type="password" name="cardPassword" autocomplete="new-password" class="pw p10 al" maxlength="2"  placeholder="비밀번호 앞 2자리 숫자" title="비밀번호 앞 2자리 숫자" />
					</div>
				</dd>
				<dt class="txt-hide">간편비밀번호</dt>
                <dd class="table-area">
                    <div>
                        <cite class="write-tit">간편비밀번호</cite>
                        <input type="password" id="password1" class="p10" autocomplete="new-password" placeholder="6자리" maxlength="6" />
                    </div>
                    <div>
                        <cite class="write-tit">간편비밀번호 확인</cite>
                        <input type="password" id="password2" name="password" class="p10" autocomplete="new-password" placeholder="6자리" maxlength="6" />
                    </div>
                </dd>
				<dt>카드구분</dt>
				<dd>
					<label><input type="radio" checked="checked" name="kind" data-kind="first" />개인카드</label>
					<label><input type="radio"  name="kind" data-kind="second"/>법인카드</label>
				</dd>
				<dt id="brthdy">생년월일</dt>
				<dd>
					<input type="number" name="brthdy" maxlength="6" class="p10 al" placeholder="6자리 입력" title="6자리 입력" />
				</dd>
				<dt style="display:none;" id="bizrno">사업자등록번호</dt>
				<dd style="display:none;">
				</dd>
				<dt>카드이름</dt>
				<dd>
					<input type="text" name="cardNm"  class="p10 al" placeholder="카드 이름" title="카드 이름" />
				</dd>
			</dl>
			<div class="agree-area">
				<label><input type="checkbox" name="bassUseAt"/>기본 카드로 설정</label>
			</div>
			
			<!-- <div class="agree-area">
			<p class="msg"><a href="#none" onclick="popOpen2('cardList')" class="fc-gr fs-sm">등록 가능한 카드사 보기<i class="ico-arr-r sm back gr "></i></a></p>
			</div> -->
			<div class="agree-all-area">
				<label><input type="checkbox" id="allStplat" value="Y" /><strong>전체 약관 동의</strong></label>
				<ul class="bg-box bullet-none">
					<li>
						<label><input type="checkbox" class="stplat" id="indvdlStplat"/>FOXEDU STORE 개인(신용)정보 수집 및 이용 동의</label>
						<a href="#none" onclick="popOpen2('termsPrivacy')" class="fc-gr fs-sm">보기 <i class="ico-arr-r sm back gr "></i></a>
					</li>
					<li>
						<label><input type="checkbox" class="stplat" id="cardStplat" />FOXEDU STORE · 카드사 간 개인(신용)정보 제공 동의</label>
						<a href="#none"  onclick="popOpen2('termsCard')" class="fc-gr fs-sm">보기 <i class="ico-arr-r sm back gr "></i></a>
					</li>
				</ul>
			</div>
			<div class="btn-table-area">
				<button type="submit" onclick="cardSubmit();" id="cardReg" class="btn-lg spot width">완료</button>
			 </div>
		   </form>	
		</div>
	</div>				
	<javascript>
		<script src="${CTX_ROOT}/resources/front/my/mySubscribe/mySubscribeModify.js"></script>
		<script src="${CTX_ROOT}/resources/front/my/myInfo/zip.js"></script>
	</javascript>
</body>
</html>