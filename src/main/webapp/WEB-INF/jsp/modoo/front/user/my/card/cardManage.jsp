<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/modoo/common/commonTagLibs.jsp"%>
<c:set var="title" value="카드등록/관리"/>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>${title}</title>
</head>
<body>
<div class="wrap">
	<c:import url="/user/my/subMenu.do" charEncoding="utf-8">
		<c:param name="menuId" value="myInfo_cardManage"/>
	</c:import>	

	<div class="sub-contents">
		<c:import url="/user/my/myLocation.do" charEncoding="utf-8">
			<c:param name="menuId" value="myInfo"/>
			<c:param name="subMenuId" value="${title}"/>
		</c:import> 
	
		<h2 class="txt-hide">카드 등록/관리</h2>
		<section>
			<div class="sub-tit-area">
				<h3 class="sub-tit">카드 등록/관리</h3>
				<div class="fnc-area">
					<button type="button" id="cardWrite" class="btn">카드 등록하기</button>
				</div>
			</div>
			<div class="list-type">
				<table>
					<caption>카드 등록/관리</caption>
					<thead>
						<tr>
							<th scope="col">카드번호 뒷4자리(카드번호)</th>
							<th scope="col">카드 이름</th>
							<th scope="col">관리</th>
						</tr>
					</thead>
					<tbody>
					<c:if test="${not empty cardList}">
						<c:forEach var="list" items="${cardList}">
							<tr>
								<td class="ac">${list.lastCardNo}</td>
								<td class="ac"><em class="spot">${list.cardNm} <c:if test="${list.bassUseAt eq 'Y'}">[기본 카드]</c:if></em></td>
								<td>
									<button type="button" id="cardUpForm" class="btn" data-cardid="${list.cardId}" data-cardnm="${list.cardNm}" data-bassuseat="${list.bassUseAt}" data-cardno="${list.lastCardNo}">카드 관리</button>
									<button type="button" id="cardDel" class="btn spot2" data-cardid="${list.cardId}">삭제</button>
								</td>
							</tr>
						</c:forEach>
					</c:if>
					<c:if test="${empty cardList}">
					<tr>
						<td colspan="3">
							<p class="none-txt">등록된 카드가 없습니다.</p>
						</td>
					</tr>
					</c:if>
					<%-- <c:if test="${empty cardList}">
						<tr><td><p class="none-text">등록된 카드가 없습니다</p></td></tr>
					</c:if> --%>
					</tbody>
				</table>
			</div>
			<c:if test="${not empty cardList}">
			<ul class="paging">
				<c:url var="pageUrl" value="/user/my/cardManage.do">
					<c:param name="pageIndex" value=""/>
				</c:url>
				  <modoo:pagination paginationInfo="${paginationInfo}" type="text" jsFunction="" pageUrl="${pageUrl}" pageCssClass="page-css-class"/>
			</ul>
			</c:if>
		</section>
		<section>
			<div class="sub-tit-area">
				<h3 class="sub-tit sm">등록 가능한 카드사 보기</h3>
			</div>
			<ul class="card-list">
				<li><img src="${CTX_ROOT }/resources/front/site/${SITE_ID }/image/sub/card_logo_samsung.jpg" /></li>
				<c:if test="${!fn:contains(USER_ID,'100')}">
					<li><img src="${CTX_ROOT }/resources/front/site/${SITE_ID }/image/sub/card_logo_shinhan.jpg" /></li> 
				</c:if>
				<li><img src="${CTX_ROOT }/resources/front/site/${SITE_ID }/image/sub/card_logo_hyundai.jpg" /></li>
				<li><img src="${CTX_ROOT }/resources/front/site/${SITE_ID }/image/sub/card_logo_bc.jpg" /></li>
				<li><img src="${CTX_ROOT }/resources/front/site/${SITE_ID }/image/sub/card_logo_kb.jpg" /></li>
				<li><img src="${CTX_ROOT }/resources/front/site/${SITE_ID }/image/sub/card_logo_hana.jpg" /></li>
				<li><img src="${CTX_ROOT }/resources/front/site/${SITE_ID }/image/sub/card_logo_nh.jpg" /></li>
				<li><img src="${CTX_ROOT }/resources/front/site/${SITE_ID }/image/sub/card_logo_lotte.jpg" /></li>
				<%-- <li><img src="${CTX_ROOT }/resources/front/site/${SITE_ID }/image/sub/card_logo_citi.jpg" /></li> --%>
				<li><img src="${CTX_ROOT }/resources/front/site/${SITE_ID }/image/sub/card_logo_kakao.jpg" /></li>
			</ul>
		</section>
	
		 <c:import url="/user/my/cardForm.do"/>
	
	</div>
</div>


	<javascript>
		<script src="${CTX_ROOT}/resources/front/my/card/card.js"></script>
	</javascript>
</body>
</html>