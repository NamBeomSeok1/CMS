<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/modoo/common/commonTagLibs.jsp"%>
<c:set var="title" value="건강 지킴이 3총사 무료체험 이벤트"/>
<!DOCTYPE html>
<html>
<head>
	<title>${title}</title>
	<!--이벤트 css-->
    <link href="${CTX_ROOT}/html/SITE_00000/event/210202health/css/event.css" rel="stylesheet" />
    <metatag>
		<meta property="og:title" content="${title}"/>
		<meta property="og:image" content="${BASE_URL}/html/SITE_00000/event/210202health/images/share_image_m.jpg"/>
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
      	<c:param name="url" value="/shop/event/eventHealth.do"/>
      	<c:param name="imgUrl" value="${BASE_URL}/html/SITE_00000/event/210202health/images/share_image_m.jpg"/>
     </c:import>
    <div class="event-area">
       <div class="event-visual">
           <img src="${CTX_ROOT}/html/SITE_00000/event/210202health/images/img_visual.png" class="m-none" />
           <img src="${CTX_ROOT}/html/SITE_00000/event/210202health/images/img_visual_m.png" class="m-block" />
           <figure>
               <img src="${CTX_ROOT}/html/SITE_00000/event/210202health/images/img_cha.svg" />
           </figure>
       </div>
       <div class="event-cont">
           <img src="${CTX_ROOT}/html/SITE_00000/event/210202health/images/img_txt.png" class="m-none" />
           <img src="${CTX_ROOT}/html/SITE_00000/event/210202health/images/img_txt_m.png" class="m-block" />
           <div class="event-btn-cont">
           
           <c:choose>
				<c:when test="${USER.groupId eq 'GROUP_00000000000001'}">
					<a href="/shop/goods/goodsView.do?goodsId=GOODS_00000000002012">
                   		<img src="${CTX_ROOT}/html/SITE_00000/event/210202health/images/btn.png" />
               		</a>
				</c:when>
				<c:otherwise>
					<a href="/shop/goods/goodsView.do?goodsId=GOODS_00000000002002">
                   		<img src="${CTX_ROOT}/html/SITE_00000/event/210202health/images/btn.png" />
               		</a>
				</c:otherwise>
			</c:choose>
               
           </div>
       </div>
   </div>

	<javascript>
		<script src="${CTX_ROOT}/resources/front/cmm/etc/js/kakaoApi.js"></script>
		<script src="${CTX_ROOT}/resources/front/shop/goods/event/js/210118open2.js"></script>
	</javascript>
</body>
</html>