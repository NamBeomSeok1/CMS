<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/modoo/common/commonTagLibs.jsp"%>
		
		<div class="wrap">
			<h1 class="logo">
				<a href="${CTX_ROOT }/index.do">FOXEDU STORE</a>
			</h1>
			
			<nav class="site-gnb">
                <ul>
                    <li><a href="/shop/goods/goodsCtgryList.do?searchGoodsCtgryId=GCTGRY_0000000000031&searchSubCtgryId=GCTGRY_0000000000032">폭스구독권</a></li>
                    <li><a href="/shop/goods/goodsCtgryList.do?searchGoodsCtgryId=GCTGRY_0000000000031&searchSubCtgryId=GCTGRY_0000000000033">폭스굿즈</a></li>
                    <li><a href="/shop/goods/goodsCtgryList.do?searchGoodsCtgryId=GCTGRY_0000000000031&searchSubCtgryId=GCTGRY_0000000000034">폭스교육상품</a></li>
                    <li><a href="/shop/event/goodsEventList.do">이벤트</a></li>
                </ul>
            </nav>
			<%-- 
			<c:choose>
				<c:when test="${fn:contains(USER_ROLE,'ROLE_SHOP') and not fn:contains(USER_ROLE, 'ROLE_EMPLOYEE')}">
					<button type="button" class="btn-schall-toggle" style="right:60px;"><i class="ico-sch" aria-hidden="true"></i><span class="txt-hide">검색</span></button>
				</c:when>
				<c:otherwise>
					<button type="button" class="btn-schall-toggle"><i class="ico-sch" aria-hidden="true"></i><span class="txt-hide">검색</span></button>
				</c:otherwise>
			</c:choose>
			 
			<div class="schall">
				<div class="sch-area lg">
					<form id="goodsSearchForm" name="goodsSearchForm" method="get" action="${CTX_ROOT }/shop/goods/goodsSearch.do">
						<input type="hidden" name="storeSearchWrdAt" value=""/>
						<input type="text" name="searchKeyword" class="autocomplete" />
						<button type="submit" class="btn-sch"><span class="txt-hide">검색</span></button>
					</form>
				</div>
				<div class="schall-result-area">
					<div class="keyword-area">
						<div>
							<cite>최근검색어</cite>
							<ul id="latestSearchKeywords">
								<%-- javascript localStorage -- %>
							</ul>
						</div>
						<div>
							<cite>인기 검색어</cite>
							<ul id="histsSearchKeywrods">
								<%-- localStorage -- %>
							</ul>
						</div>
					</div>
				</div>
				<div class="fnc-area">
					<div class="fl">
						<button type="submit" class="btn-sm-gr btn-schall-close">닫기</button>
					</div>
					<div class="fr">
						<button type="submit" class="btn-sm-gr btnStopSaveSearchKeywords">검색어 자동저장 끄기</button>
						<button type="submit" class="btn-sm-gr btnDeleSearchKeywords">검색기록 삭제</button>
					</div>
				</div>
			</div>
			--%>
			<c:choose>
				<c:when test="${fn:contains(USER_ROLE,'ROLE_SHOP') and not fn:contains(USER_ROLE, 'ROLE_EMPLOYEE')}"></c:when>
				<c:when test="${empty USER_ID }">
					<div class="util">
						<a href="${CTX_ROOT }/user/sign/loginUser.do"><i class="ico-user" aria-hidden="true"></i><span class="txt-hide">마이페이지</span></a>
						<%-- <a href="${CTX_ROOT }/user/sign/loginUser.do"><i class="ico-zzim" aria-hidden="true"></i><span class="txt-hide">찜</span></a> --%>
						<a href="${CTX_ROOT }/user/sign/loginUser.do">
							<i class="ico-cart" aria-hidden="true"></i>
							<span class="txt-hide">장바구니</span>
						</a>
					</div>
				</c:when>
				<c:otherwise>
					<div class="util">
						<a href="/user/sign/logout.do">
		                    <i class="ico-logout" aria-hidden="true"></i>
		                    <span>로그아웃</span>
		                </a>
						<a href="${CTX_ROOT}/user/my/mySubscribeNow.do?menuId=sbs_mySubscribeNow"><i class="ico-user" aria-hidden="true"></i><span class="txt-hide">마이페이지</span></a>
						<!-- <a href="#none"><i class="ico-zzim" aria-hidden="true"></i><span class="txt-hide">찜</span></a> -->
						<a href="${CTX_ROOT }/shop/goods/cart.do" id="cart">
							<i class="ico-cart" aria-hidden="true"></i>
							<span class="txt-hide">장바구니</span>
							<c:choose>
								<c:when test="${not empty cartCnt}">
									<c:set var="isNotice" value="label-notice"/>
								</c:when>
								<c:otherwise>
									<c:set var="isNotice" value=""/>
								</c:otherwise>
							</c:choose>
						<span id="cartCnt" class="${isNotice}">${cartCnt}</span>
						</a>
					</div>
			   </c:otherwise>
		   </c:choose>

			<!--모바일 우측 메뉴-->
			<a href='${CTX_ROOT}/embed/mMenu.do' class="m-btn-menu-toggle">
				<i class="ico-menu" aria-hidden="true"></i>
				<span class="txt-hide">메뉴</span>
			</a>
			
			  <%-- <div class="m-menu">
				<div class="m-util">
					<a href="${CTX_ROOT }/shop/goods/cart.do" id="cart">
						<i class="ico-cart" aria-hidden="true"></i>
						<span class="txt-hide">장바구니</span>
						<span id="cartCnt" class="label-notice">${cartCnt}</span>
					</a>
					<c:if test="${empty USER_ID}">
					 <a href="/user/sign/loginUser.do">
						<i class="ico-login" aria-hidden="true"></i>
						<span>로그인</span>
					</a>
					</c:if>
					<c:if test="${!empty USER_ID}">
					<a href="/user/sign/logout.do" class="btn-logout">
						<i class="ico-logout" aria-hidden="true"></i>
						<span>로그아웃</span>
					</a>
					</c:if>
				</div>
				
				<nav>
				   <div>
					   <cite>카테고리</cite>
					   <ul>
					   <c:choose>
					   		<c:when test="${ctgryMenuList.size() > 0}">
						   		<c:forEach items="${ctgryMenuList}" var="ctgryMenu">
						   		<li>
								   <button type="button" class="btn-menu-accordion">${ctgryMenu.goodsCtgryNm}</button>
								   <ul>
									   <li><a href="${CTX_ROOT}/shop/goods/goodsCtgryList.do?searchGoodsCtgryId=${ctgryMenu.goodsCtgryId}">전체</a></li>
									   <c:forEach items="${ctgryMenu._children}" var="subCtgry">
									   		<li><a href="${CTX_ROOT}/shop/goods/goodsCtgryList.do?searchGoodsCtgryId=${subCtgry.upperGoodsCtgryId}&searchSubCtgryId=${subCtgry.goodsCtgryId}">${subCtgry.goodsCtgryNm}</a></li>
									   </c:forEach>
								   </ul>
							   	</li>
							   	</c:forEach>
					   		</c:when>
					   		<c:otherwise>
					   			카테고리가 없습니다.
					   		</c:otherwise>
					   </c:choose>
					   </ul>
				   </div>
				   		<a href="${CTX_ROOT}/shop/goods/brandList.do"><button type="button" class="btn-menu">브랜드</button></a>
				   <div>
					   <cite>마이 구독</cite>
					   <ul>
						   <c:if test="${not empty USER_ID }">
							   <li>
								   <button type="button" class="btn-menu-accordion">구독관리</button>
								   <ul>
									   <li><a href="${CTX_ROOT}/user/my/mySubscribeNow.do?menuId=sbs_mySubscribeNow" class="is-active">구독중</a></li>
									   <li><a href="${CTX_ROOT}/user/my/mySubscribeCancel.do?menuId=sbs_mySubscribeCancel">구독 해지</a></li>
								   </ul>
							   </li>
							   <li>
								   <a href="${CTX_ROOT}/user/my/myRefund.do?menuId=refund">교환/환불</a>
							   </li>
							   <li>
								   <button type="button" class="btn-menu-accordion">게시판</button>
								   <ul>
									   <li><a href="${CTX_ROOT}/user/my/qainfo.do?qaSeCode=GOODS&menuId=bbs_goodsQna">Q&amp;A</a></li>
									   <li><a href="${CTX_ROOT}/user/my/review.do?menuId=bbs_review">내가 작성한 리뷰</a></li>
									   <li><a href="${CTX_ROOT}/user/my/reviewTodo.do?menuId=bbs_todo">작성 가능한 리뷰</a></li>
								   </ul>
							   </li>
							</c:if>
							<li>
								<button type="button" class="btn-menu-accordion">고객센터</button>
								<ul>
							   		<li><a href="${CTX_ROOT}/board/boardList.do?bbsId=BBSMSTR_000000000000&menuId=cs_BBSMSTR_000000000000">공지사항</a></li>
									<li><a href="${CTX_ROOT}/user/my/faqList.do?menuId=cs_FAQ">FAQ</a></li>
									<c:if test="${not empty USER_ID }">
										<li><a href="${CTX_ROOT}/user/my/qainfo.do?qaSeCode=SITE&menuId=cs_siteQna">1:1 문의</a></li>
								   </c:if>
							   </ul>
							</li>
							<c:if test="${not empty USER_ID }">
							   <li>
								   <button type="button" class="btn-menu-accordion">내 정보</button>
								   <ul>
									   <li><a href="${CTX_ROOT}/user/my/myInfo.do?menuId=myInfo_myDetail">내정보 보기</a></li>
									   <li><a href="${CTX_ROOT}/user/my/cardManage.do?menuId=myInfo_cardManage">카드 등록/관리</a></li>
								   </ul>
							   </li>
							</c:if>
					   </ul>
				   </div>
				</nav>
				<button type="button" class="btn-menu-close"><span class="txt-hide">닫기</span></button>
			</div> --%>

			<!--모바일 lnb-->
			<c:set var="menuUrl" value="${requestScope['javax.servlet.forward.request_uri']}"/>
			<c:if test="${fn:indexOf('/shop/goods/goodsCtgryList.do, /user/my/mySubscribeNow.do, /user/my/mySubscribeCancel.do , /user/my/myRefund.do, /user/my/qainfo.do,/user/my/review.do,/user/my/reviewTodo.do, , /board/boardList.do, /user/my/faqList.do, /user/my/myInfo.do,/user/my/cardManage.do' , menuUrl) ge 0}">
			
				<div class="m-lnb-area">
					<button type="button" class="btn-lnb-toggle">메뉴</button>
					<nav class="lnb">
					<c:choose>
						<c:when test="${fn:indexOf('/shop/goods/goodsCtgryList.do' , menuUrl) ge 0}">
							<%-- 
							<cite>카테고리</cite>
							<ul>
								<c:forEach items="${ctgryMenuList}" var="ctgryMenu">
									<c:set var="activeClass" value=""/>
									<c:if test="${param.searchGoodsCtgryId eq ctgryMenu.goodsCtgryId}"> <c:set var="activeClass" value="is-active"/> </c:if>
									<li><a href="${CTX_ROOT}/shop/goods/goodsCtgryList.do?searchGoodsCtgryId=${ctgryMenu.goodsCtgryId}" class="${activeClass }"> ${ctgryMenu.goodsCtgryNm}</a> </li>
								</c:forEach>
							</ul>
							 --%>
					</c:when>
					<c:otherwise>
						<c:set var="subMenuId" value="${param.menuId}"/>
						<cite>마이페이지</cite>
							<ul>
							<c:if test="${not empty USER_ID }">
								<li>
									<c:url var="subNowUrl" value="/user/my/mySubscribeNow.do?menuId=sbs_mySubscribeNow"></c:url>
									<a href="${subNowUrl}" class="<c:if test="${fn:contains(subMenuId, 'sbs')}">is-active</c:if>">주문관리</a>
									<ul>
										<li><a href="${subNowUrl}" class="<c:if test="${fn:contains(subMenuId, 'Now')}">is-active</c:if>">주문확인</a></li>
										<!--<li><a href="#none" class="">구독 일시중지</a></li> -->
										<c:url var="subCancelUrl" value="/user/my/mySubscribeCancel.do?menuId=sbs_mySubscribeCancel"></c:url>
										<li><a href="${subCancelUrl}" class="<c:if test="${fn:contains(subMenuId, 'Cancel')}">is-active</c:if>">주문취소</a></li>
									</ul>
								</li>
								<c:url var="myRefundUrl" value="/user/my/myRefund.do?menuId=refund"></c:url>
								<li>
									<a href="${myRefundUrl}" class="<c:if test="${fn:contains(subMenuId, 'refund')}">is-active</c:if>">교환/환불</a>
								</li>
								<li>
									<c:url var="goodsQnaUrl" value="/user/my/qainfo.do?menuId=bbs_goodsQna"> 
										<c:param name="qaSeCode" value="GOODS"></c:param>
									</c:url>
									<a href="${goodsQnaUrl}" class="<c:if test="${fn:contains(subMenuId, 'bbs')}">is-active</c:if>">게시판</a>
									<ul>
										<li><a href="${goodsQnaUrl}" class="<c:if test="${fn:contains(subMenuId, 'goodsQna')}">is-active</c:if>">Q&amp;A</a></li>
										<c:url var="myReviewUrl" value="/user/my/review.do?menuId=bbs_review"></c:url>
										<li><a href="${myReviewUrl}" class="<c:if test="${fn:contains(subMenuId, 'review')}">is-active</c:if>">내가 작성한 리뷰</a></li>
										<c:url var="myReviewTodoUrl" value="/user/my/reviewTodo.do?menuId=bbs_todo"></c:url>
										<li><a href="${myReviewTodoUrl}" class="<c:if test="${fn:contains(subMenuId, 'todo')}">is-active</c:if>">작성 가능한 리뷰</a></li>
									</ul>
								</li>
							</c:if>
							<li>
								<c:url var="noticeUrl" value="/board/boardList.do">
									<c:param name="bbsId" value="BBSMSTR_000000000000"/>
									<c:param name="menuId" value="cs_BBSMSTR_000000000000"/>
								</c:url>
								<a href="${noticeUrl}" class="<c:if test="${fn:contains(subMenuId,'cs')}">is-active</c:if>">고객센터</a>
								<ul>
									<li><a href="${noticeUrl}" class="<c:if test="${fn:contains(subMenuId, 'BBSMSTR_000000000000')}">is-active</c:if>">공지사항</a></li>
									<c:url var="faqUrl" value="/user/my/faqList.do?menuId=cs_FAQ"></c:url>
									<li><a href="${faqUrl}" class="<c:if test="${fn:contains(subMenuId, 'FAQ')}">is-active</c:if>">FAQ</a></li>
									<c:if test="${not empty USER_ID }">
										<c:url var="siteQnaUrl" value="/user/my/qainfo.do?menuId=cs_siteQna"> 
											<c:param name="qaSeCode" value="SITE"></c:param>
										</c:url>
										<li><a href="${siteQnaUrl}" class="<c:if test="${fn:contains(subMenuId,'siteQna')}">is-active</c:if>">1:1 문의</a></li>
									</c:if>
								</ul>
							</li>
							<c:if test="${not empty USER_ID }">
								<li>
									<c:url var="muInfoUrl" value="/user/my/myInfo.do?menuId=myInfo_myDetail"> 
									</c:url>
									<a href="${muInfoUrl }" class="<c:if test="${fn:contains(subMenuId,'myInfo')}">is-active</c:if>">내 정보</a>
									<ul>
										<li><a href="${muInfoUrl}" class="<c:if test="${fn:contains(subMenuId,'myDetail')}">is-active</c:if>">내정보 보기</a></li>
										<c:url var="cardManageUrl" value="/user/my/cardManage.do?menuId=myInfo_cardManage"> 
										</c:url>
										<li><a href="${cardManageUrl}" class="<c:if test="${fn:contains(subMenuId,'cardManage')}">is-active</c:if>">카드 등록/관리</a></li>
									</ul>
								</li>
							</c:if>
						</ul>
							
					</c:otherwise>
				</c:choose>			
			<button class="btn-lnb-close"><span class="txt-hide">닫기</span></button>
		</nav>
	</div>
	</c:if>
<%-- 			<c:set var="menuUrl" value="${requestScope['javax.servlet.forward.request_uri']}"/> --%>
<%-- 			<c:if test="${fn:indexOf('/shop/goods/goodsCtgryList.do, /user/my/mySubscribeNow.do, /user/my/mySubscribeCancel.do , /user/my/myRefund.do, /user/my/qainfo.do,/user/my/review.do,/user/my/reviewTodo.do, , /board/boardList.do, /user/my/faqList.do, /user/my/myInfo.do,/user/my/cardManage.do' , menuUrl) ge 0}">  --%>
<!-- 				모바일 lnb -->
<!-- 				<div class="m-lnb-area"> -->
<!-- 					<button type="button" class="btn-lnb-toggle">메뉴</button> -->
<!-- 					<nav class="lnb"> -->
<!-- 						<cite>카테고리</cite> -->
<!-- 						<ul> -->
<%-- 							<c:forEach items="${ctgryMenuList}" var="ctgryMenu"> --%>
<%-- 								<c:set var="activeClass" value=""/> --%>
<%-- 								<c:if test="${param.searchGoodsCtgryId eq ctgryMenu.goodsCtgryId}"> <c:set var="activeClass" value="is-active"/> </c:if> --%>
<%-- 								<li><a href="${CTX_ROOT}/shop/goods/goodsCtgryList.do?searchGoodsCtgryId=${ctgryMenu.goodsCtgryId}" class="${activeClass }"> ${ctgryMenu.goodsCtgryNm}</a> </li> --%>
<%-- 							</c:forEach> --%>
<!-- 						</ul> -->
<!-- 						<button class="btn-lnb-close"><span class="txt-hide">닫기</span></button> -->
<!-- 					</nav> -->
<!-- 				</div> -->
<%--             </c:if> --%>
		   <!--  <div class="m-menu">
				<button class="btn-close"><span class="txt-hide">닫기</span></button>
			</div> -->
		</div><!-- //wrap -->
	<div class="pop-brand">
		<ul class="brand-list">
		</ul>
		<button type="button" class="btn-brand-close"><span class="txt-hide">닫기</span></button>
	</div>		
