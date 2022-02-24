<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/modoo/common/commonTagLibs.jsp"%>
<c:set var="title" value="2021 소확행 이벤트 1"/>
<!DOCTYPE html>
<html>
<head>
	<title>${title}</title>
	<!--이벤트 css-->
    <link href="${CTX_ROOT}/html/SITE_00000/event/210111open1/css/event.css" rel="stylesheet" />
    <metatag>
		<meta property="og:title" content="${title}"/>
		<meta property="og:image" content="${BASE_URL}/html/SITE_00000/event/210111open1/images/img_visual_Thumb.jpg"/>
	</metatag>
</head>
<body>
		<c:choose>
		 	<c:when test="${fn:contains(USER_ID,'100')}">
		      	<c:set var="isEventHome" value="true"/>
			</c:when>
			<c:otherwise>
		      	<c:set var="isEventHome" value="false"/>
			</c:otherwise>
		</c:choose>
		
	 <c:import url="/shop/event/goodsEventLocation.do" charEncoding="utf-8">
		<c:param name="isEventHome" value="${isEventHome}"/>
      	<c:param name="eventNm" value="${title}"/>
      	<c:param name="url" value="/shop/event/open1.do"/>
      	<c:param name="imgUrl" value="${BASE_URL}/html/SITE_00000/event/210111open1/images/img_visual_Thumb.jpg"/>
     </c:import>
	 <div class="event-area">
	    <div class="event-visual">
	        <div class="visual-img">
	            <img src="${CTX_ROOT}/html/SITE_00000/event/210111open1/images/img_visual.jpg" alt="2021 소확행 이벤트" />
	            <div class="img-cha">
                     <span><img src="${CTX_ROOT}/html/SITE_00000/event/210111open1/images/img_cha_txt1.svg" /></span>
                    <img src="${CTX_ROOT}/html/SITE_00000/event/210111open1/images/img_cow.png" alt="캐릭터" />
                </div>
	        </div>
	        <div class="pyro">
	            <div class="before"></div>
	            <div class="after"></div>
	        </div>
	    </div>
	    <div class="event-wrap">
	        <div class="event-txt">
	            <img src="${CTX_ROOT}/html/SITE_00000/event/210111open1/images/img_txt.jpg" alt="이벤트 기간 내 구매고객 선착순 2021분 모두에게 gs25 모바일상품권 3,000원 증정" />
	
	            <p class="txt-lg">
	                <strong>대상 품목 :</strong> 전품목<br />
	                
	                <c:choose>
	                	<c:when test="${param.se eq 'B2B'}">
	                		<strong>이벤트 기간 :</strong> 2021. 01. 18 - 2021. 02. 10<br />
	                	</c:when>
	                	<c:otherwise>
	                		<strong>이벤트 기간 :</strong> 2021. 01. 20 - 2021. 02. 10<br />
	                	</c:otherwise>
	                </c:choose>
	            </p>
              	<p class="txt-sm">
                	※ 기프티콘은 해당기간 내 구매고객에 대하여 상품 구매 후 차주 수 ~ 금요일 사이에 순차적 일괄발송됩니다.
                </p>
                <p class="txt-sm fc-gr">※ 5,000원 이상 구매 시 증정</p>
	        </div>
	        <div class="event-box">
	            <cite>이벤트 참여하기</cite>
	            <ol>
	                <li>
	                    <cite>Step1.</cite>
	                    <p>마음에 드는 상품을 주문합니다. (<em>체험구독, 일반상품, 정기구독상품, 이벤트상품</em> 등 구매)</p>
	                    <span>※ <strong>이벤트 기간내 ID당 1회 구매건</strong> 해당.</span>
	                </li>
	                <li>
	                    <cite>Step2.</cite>
	                    <p>주문페이지에서 주문정보를 입력하고 결제를 완료하면 끝!</p>
	                    <span>* 주문정보에서 입력한 전화번호로 기프티콘이 발송됩니다. (일괄발송)</span>
	                </li>
	            </ol>
	        </div>
	    </div>
	</div>
	
	<javascript>
		<script src="${CTX_ROOT}/html/SITE_00000/event/210111open1/js/event.js"></script>
		<script src="${CTX_ROOT}/resources/front/cmm/etc/js/kakaoApi.js"></script>
		<script src="${CTX_ROOT}/resources/front/shop/goods/event/js/210118open2.js"></script>
	</javascript>
</body>
</html>