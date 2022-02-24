<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/modoo/common/commonTagLibs.jsp"%>
<div class="contents" id="contents">
	<div class="wrap">
	    <div class="sub-contents">
	        <h2 class="sub-tit">사이트맵</h2>
	        <div class="sitemap">
	            <section>
	                <h3>회원</h3>
	                <ul>
	                	<c:if test="${empty USER_ID }">
	                    	<li><a href="/user/sign/loginUser.do">로그인</a></li>
	                    </c:if>
	                    <c:if test="${not empty USER_ID }">
	                    	<li><a href="/user/sign/logout.do">로그아웃</a></li>
	                    </c:if>
	                </ul>
	            </section>
	            <section>
	                <h3>카테고리</h3>
	                <ul>
	                <c:choose>
				   		<c:when test="${ctgryMenuList.size() > 0}">
					   		<c:forEach items="${ctgryMenuList}" var="ctgryMenu">
					   		<c:if test="${ctgryMenu.actvtyAt == 'Y'}">
						   		<li>
								   <li><a href="${CTX_ROOT}/shop/goods/goodsCtgryList.do?searchGoodsCtgryId=${ctgryMenu.goodsCtgryId}">${ctgryMenu.goodsCtgryNm}</a></li>
							   	</li>
						   	</c:if>
						   	</c:forEach>
				   		</c:when>
				   		<c:otherwise>
				   			카테고리가 없습니다.
				   		</c:otherwise>
					</c:choose>
					</ul>                
	            </section>
	            <section>
	                <h3>브랜드관</h3>
	                <ul>
	                    <li><a href="${CTX_ROOT}/shop/goods/brandList.do">브랜드관</a></li>
	                </ul>
	            </section>
	            <section>
	                <h3>마이구독</h3>
	                <ul>
	                	<c:if test="${not empty USER_ID }">
	                    <li>
	                    	<c:url var="sbsNowUrl" value="${CTX_ROOT}/user/my/mySubscribeNow.do">
								<c:param name="menuId" value="sbs_mySubscribeNow"/>
							</c:url>
	                        <a href="${sbsNowUrl}">주문관리</a>
	                        <ul>
	                            <li><a href="${sbsNowUrl}">주문확인</a></li>
		                        <c:url var="sbsCancelUrl" value="${CTX_ROOT}/user/my/mySubscribeCancel.do">
									<c:param name="menuId" value="sbs_mySubscribeCancel"/>
								</c:url>
	                            <li><a href="${sbsCancelUrl}">주문취소</a></li>
	                        </ul>
	                    </li>
	                    <li><a href="${CTX_ROOT}/user/my/myRefund.do?menuId=refund">교환/환불</a></li>
	                    <li>
	                        <a href="${CTX_ROOT}/user/my/qainfo.do?qaSeCode=GOODS&menuId=bbs_goodsQna">게시판</a>
	                        <ul>
	                            <li><a href="${CTX_ROOT}/user/my/qainfo.do?qaSeCode=GOODS&menuId=bbs_goodsQna">Q&A</a></li>
	                            <li><a href="${CTX_ROOT}/user/my/review.do?menuId=bbs_review">내가 작성한 리뷰</a></li>
	                            <li><a href="${CTX_ROOT}/user/my/reviewTodo.do?menuId=bbs_todo">작성 가능한 리뷰</a></li>
	                        </ul>
	                    </li>
	                    </c:if>
	                    <li>
	                        <c:url var="noticeUrl" value="${CTX_ROOT}/board/boardList.do">
								<c:param name="bbsId" value="BBSMSTR_000000000000"/>
								<c:param name="menuId" value="cs_BBSMSTR_000000000000"/>
							</c:url>
	                        <a href="${noticeUrl}">고객센터</a>
	                        <ul>
	                            <li><a href="${noticeUrl}">공지사항</a></li>
	                            <li><a href="${CTX_ROOT}/user/my/faqList.do?menuId=cs_FAQ">FAQ</a></li>
	                            <c:url var="siteQnaUrl" value="${CTX_ROOT}/user/my/qainfo.do?menuId=cs_siteQna"> 
									<c:param name="qaSeCode" value="SITE"></c:param>
								</c:url>
	                            <li><a href="${siteQnaUrl}">1:1 문의</a></li>
	                        </ul>
	                    </li>
	                    <c:if test="${not empty USER_ID }">
	                    <li>
	                    	<c:url var="muInfoUrl" value="${CTX_ROOT}/user/my/myInfo.do">
	                    		<c:param name="menuId" value="myInfo_myDetail"/>
	                    	</c:url>
	                        <a href="${muInfoUrl}">내 정보</a>
	                        <ul>
	                            <li><a href="${muInfoUrl}">내정보 보기</a></li>
	                            <c:url var="cardManageUrl" value="${CTX_ROOT}/user/my/cardManage.do">
	                            	<c:param name="menuId" value="myInfo_cardManage"/>
	                            </c:url>
	                            <li><a href="${cardManageUrl}">카드 등록/관리</a></li>
	                        </ul>
	                    </li>
	                    </c:if>
	                </ul>
	            </section>
	        </div>
	    </div>
	</div>
</div><!-- //contents -->