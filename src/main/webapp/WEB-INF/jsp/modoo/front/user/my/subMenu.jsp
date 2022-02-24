<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/modoo/common/commonTagLibs.jsp"%>
<c:set var="subMenuId" value="${param.menuId }"/>
<div class="lnb-area">
	<button type="button" class="btn-lnb-toggle">메뉴</button>
	<nav class="lnb">
		<cite>마이페이지</cite>
		<ul>
			<c:if test="${not empty USER_ID }">
				<li>
					<c:url var="subNowUrl" value="/user/my/mySubscribeNow.do"></c:url>
					<a href="${subNowUrl}" class="<c:if test="${fn:contains(subMenuId, 'sbs')}">is-active</c:if>">주문관리</a>
					<ul>
						<li><a href="${subNowUrl}" class="<c:if test="${fn:contains(subMenuId, 'Now')}">is-active</c:if>">주문확인</a></li>
						<!--<li><a href="#none" class="">구독 일시중지</a></li> -->
						<c:url var="subCancelUrl" value="/user/my/mySubscribeCancel.do"></c:url>
						<li><a href="${subCancelUrl}" class="<c:if test="${fn:contains(subMenuId, 'Cancel')}">is-active</c:if>">주문취소</a></li>
					</ul>
				</li>
	<!-- 		<a href="#none" class="">교환/환불</a> -->
				<c:url var="myRefundUrl" value="/user/my/myRefund.do"></c:url>
				<li>
					<a href="${myRefundUrl}" class="<c:if test="${fn:contains(subMenuId, 'refund')}">is-active</c:if>">교환/환불</a>
				</li>
				<li>
					<c:url var="goodsQnaUrl" value="/user/my/qainfo.do"> 
						<c:param name="qaSeCode" value="GOODS"></c:param>
					</c:url>
					<a href="${goodsQnaUrl}" class="<c:if test="${fn:contains(subMenuId, 'bbs')}">is-active</c:if>">게시판</a>
					<ul>
						<li><a href="${goodsQnaUrl}" class="<c:if test="${fn:contains(subMenuId, 'goodsQna')}">is-active</c:if>">Q&amp;A</a></li>
						<c:url var="myReviewUrl" value="/user/my/review.do"></c:url>
						<li><a href="${myReviewUrl}" class="<c:if test="${fn:contains(subMenuId, 'review')}">is-active</c:if>">내가 작성한 리뷰</a></li>
						<c:url var="myReviewTodoUrl" value="/user/my/reviewTodo.do"></c:url>
						<li><a href="${myReviewTodoUrl}" class="<c:if test="${fn:contains(subMenuId, 'todo')}">is-active</c:if>">작성 가능한 리뷰</a></li>
					</ul>
				</li>
			</c:if>
			<li>
				<c:url var="noticeUrl" value="/board/boardList.do">
					<c:param name="bbsId" value="BBSMSTR_000000000000"/>
				</c:url>
				<a href="${noticeUrl}" class="<c:if test="${fn:contains(subMenuId,'cs')}">is-active</c:if>">고객센터</a>
				<ul>
					<li><a href="${noticeUrl}" class="<c:if test="${fn:contains(subMenuId, 'BBSMSTR_000000000000')}">is-active</c:if>">공지사항</a></li>
					<c:url var="faqUrl" value="/user/my/faqList.do"></c:url>
					<li><a href="${faqUrl}" class="<c:if test="${fn:contains(subMenuId, 'FAQ')}">is-active</c:if>">FAQ</a></li>
					<c:if test="${not empty USER_ID }">
						<c:url var="siteQnaUrl" value="/user/my/qainfo.do"> 
							<c:param name="qaSeCode" value="SITE"></c:param>
						</c:url>
						<li><a href="${siteQnaUrl}" class="<c:if test="${fn:contains(subMenuId,'siteQna')}">is-active</c:if>">1:1 문의</a></li>
					</c:if>
				</ul>
			</li>
			<c:if test="${not empty USER_ID }">
				<li>
					<c:url var="muInfoUrl" value="/user/my/myInfo.do"> 
					</c:url>
					<a href="${muInfoUrl }" class="<c:if test="${fn:contains(subMenuId,'myInfo')}">is-active</c:if>">내 정보</a>
					<ul>
						<li><a href="${muInfoUrl}" class="<c:if test="${fn:contains(subMenuId,'myDetail')}">is-active</c:if>">내정보 보기</a></li>
						<c:url var="cardManageUrl" value="/user/my/cardManage.do"> 
						</c:url>
						<li><a href="${cardManageUrl}" class="<c:if test="${fn:contains(subMenuId,'cardManage')}">is-active</c:if>">카드 등록/관리</a></li>
					</ul>
				</li>
			</c:if>
		</ul>
		<button class="btn-lnb-close"><span class="txt-hide">닫기</span></button>
	</nav>
</div>