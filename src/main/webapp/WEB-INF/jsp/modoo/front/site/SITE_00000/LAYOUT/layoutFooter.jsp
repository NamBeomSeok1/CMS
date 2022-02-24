<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/modoo/common/commonTagLibs.jsp"%>
        <div class="wrap">
        	<div class="footer-info">
                <address class="company-info">
                    <cite>㈜폭스에듀</cite>
                    <p>경기도 성남시 분당구 판교공원로1길 5, 3F 폭스에듀</p>
                    <dl>
                        <dt>통신판매업신고</dt>
                        <dd>2022-성남분당C-0073호</dd>
                        <dt>사업자등록번호</dt>
                        <dd>697-88-02547</dd>
                        <dt>대표자</dt>
                        <dd>이종탁</dd>
                        <dt>TEL</dt>
                        <dd><a href="tel:1588-1234">1433-2700</a></dd>
                    </dl>
                </address>
                <address>
                    <cite>고객센터</cite>
                    <p>경기도 성남시 분당구 판교공원로1길 5, 3F 폭스에듀</p>
                    <dl>
                        <dt>TEL</dt>
                        <dd><a href="tel:1433-2700">1433-2700</a></dd>
                        <dt>E-mail</dt>
                        <dd>sales@foxedu.kr</dd>
                    </dl>
                </address>
                <nav class="footer-menu">
                     <c:url var="termsUrl" value="${CTX_ROOT}/terms.do">
                    	<c:param name="menuNm" value="use"/>
                    	<c:param name="title" value="이용약관"/>
                    </c:url>
                    <a href="${termsUrl}">이용약관</a>
                    
                    <c:url var="termsUrl" value="${CTX_ROOT}/terms.do">
                    	<c:param name="menuNm" value="info"/>
                    	<c:param name="title" value="개인정보처리방침"/>
                    </c:url>
                    <a href="${termsUrl}"><strong>개인정보처리방침</strong></a>
                    
                    <!--<a href="#none">공지사항</a>
                    <a href="#none">FAQ</a>
                    <a href="#none">입점문의</a>-->
                    
                    <c:if test="${fn:contains(roleList, 'ROLE_ADMIN') }">
                    	<a href="/decms/index.do">관리자</a>
                    </c:if>
                    
                    <!--임시 로그아웃 -->
                    <c:if test="${not empty USER_ID and (fn:contains(roleList, 'ROLE_ADMIN') or fn:contains(roleList, 'ROLE_SHOP'))}">
                    	<a href="/user/sign/logout.do">로그아웃</a>
                    </c:if>
                    <c:if test="${empty USER_ID}">
                    	<a href="/user/sign/loginUser.do">로그인</a>
                    </c:if>
                </nav>
            </div>
            <figure class="footer-mark">
            	<img src="/resources/front/site/SITE_00000/image/logo/logo_foxedu.svg" alt="foxedu" />
                <%-- <a href="https://www.ftc.go.kr/www/bizCommView.do?key=232&apv_perm_no=2022378021930200397&pageUnit=10&searchCnd=wrkr_no&searchKrwd=6978802547&pageIndex=1" target="_blank" title="새창열기">사업자 정보확인 <i class="ico-arr-r sm gr"></i></a> --%>
            </figure>
            <p class="footer-txt">
                COPYRIGHT ⓒ 2021 FOX EDU CO., LTD ALL RIGHT RESERVED.
            </p>
            <%-- 
            <div class="footer-info">
                <address class="company-info">
                    <cite>한국구독경제서비스(주)</cite>
                    <p>${site.siteAdres }</p>
                    <dl>
                        <dt>통신판매업신고</dt>
                        <dd>2020-서울강남-01981</dd>
                        <dt>대표이사</dt>
                        <dd>최영권</dd>
                        <dt>사업자등록번호</dt>
                        <dd>410 - 87 - 10313</dd>
                        <%-- <dt>TEL</dt>
                        <dd><a href="tel:${site.siteTelno}">${site.siteTelno }</a></dd>
                        <dt>FAX</dt>
                        <dd>${site.siteFaxno }</dd> -- %>
                        <dt>TEL</dt>
                        <dd><a href="tel:02-6011-8434">02-6011-8434</a></dd>
                        <dt>FAX</dt>
                        <dd>02-586-8580</dd>
                    </dl>
                </address>
                <address>
                    <cite>고객센터</cite>
                    <p>${site.siteAdres }</p>
                    <dl>
                        <%-- <dt>TEL</dt>
                         <dd><a href="tel:${site.siteTelno }">${site.siteTelno }</a></dd> 
                        <dd><a href="tel:1566-9768">1566-9768</a></dd> 
                        <dt>FAX</dt>
                        <dd>${site.siteFaxno}</dd>
                        <dt>E-mail</dt>
                        <dd>${site.siteEmail}</dd> - -%>
                        <dt>TEL</dt>
                        <dd><div><a href="tel:1566-9768">1566-9768</a></div>
                        </dd>
                        <dt>FAX</dt>
                        <dd>02-586-8580</dd>
                        <dt>E-mail</dt>
                        <dd>${site.siteEmail}</dd>
                        <dt>영업시간</dt>
                        <dd>평일 9:00 ~ 18:00 (주말, 공휴일 제외)</dd>
                        <dt>상담톡</dt>
                        <dd><a href="http://pf.kakao.com/_NjZKK/chat" >문의하기</a></dd>
                    </dl>
                </address>
                <nav class="footer-menu">
                	<c:url var="sitemapUrl" value="${CTX_ROOT}/sitemap.do">
                    	<c:param name="menuNm" value="sitemap"/>
                    	<c:param name="title" value="사이트맵"/>
                    </c:url>
                    <a href="${sitemapUrl}">사이트맵</a>
                 	 <c:url var="termsUrl" value="${CTX_ROOT}/terms.do">
                    	<c:param name="menuNm" value="use"/>
                    	<c:param name="title" value="이용약관"/>
                    </c:url>
                    <a href="${termsUrl}">이용약관</a>
                    <c:url var="termsUrl" value="${CTX_ROOT}/terms.do">
                    	<c:param name="menuNm" value="info"/>
                    	<c:param name="title" value="개인정보처리방침"/>
                    </c:url>
                    <a href="${termsUrl}"><strong>개인정보처리방침</strong></a>
                    <a href="${CTX_ROOT }/board/boardList.do?bbsId=BBSMSTR_000000000000">공지사항</a>
                    <a href="${CTX_ROOT }/user/my/faqList.do">FAQ</a>
<%--                     <c:if test="${empty USER_ID}"> -- %>
<!-- 	                    <a href="/user/sign/loginUser.do">입점등록문의</a> -->
<%--                     </c:if> --%>
<%--                     <c:if test="${not empty USER_ID}"> -- %>
	                    <a href="#none" id="inqryBtn">입점등록문의</a>
<%--                     </c:if> -- %>
                    <c:if test="${fn:contains(roleList, 'ROLE_ADMIN') }">
                    <a href="/decms/index.do">관리자</a>
                    </c:if>
                    <!--임시 로그아웃 -->
                    <c:if test="${not empty USER_ID and (fn:contains(roleList, 'ROLE_ADMIN') or fn:contains(roleList, 'ROLE_SHOP'))}">
                    	<a href="/user/sign/logout.do" class="fc-wh">로그아웃</a>
                    </c:if>
                    <c:if test="${empty USER_ID}">
                    	<a href="/user/sign/loginUser.do" class="fc-wh">로그인</a>
                    </c:if>
                </nav>
            </div>
            <figure class="footer-mark">
                <img src="${CTX_ROOT }/resources/front/site/SITE_00000/image/logo/logo_kses_gray.png" alt="KSES" />
                <a href="http://www.ftc.go.kr/www/bizCommView.do?key=232&apv_perm_no=2020322023630201981&pageUnit=10&searchCnd=bup_nm&searchKrwd=%ED%95%9C%EA%B5%AD%EA%B5%AC%EB%8F%85%EA%B2%BD%EC%A0%9C%EC%84%9C%EB%B9%84%EC%8A%A4&pageIndex=1" target="_blank">사업자 정보확인 <i class="ico-arr-r sm gr"></i></a>
            </figure>
            <p class="footer-txt">
            	<modoo:crlf content="${site.siteCopyright }"/>
            </p>
            --%>
        </div>
<javascript>       
    <!-- Global site tag (gtag.js) - Google Ads: 448483818 -->
	<script async src="https://www.googletagmanager.com/gtag/js?id=AW-448483818"></script>
</javascript>