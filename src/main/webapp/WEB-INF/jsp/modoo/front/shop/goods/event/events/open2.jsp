<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/modoo/common/commonTagLibs.jsp"%>
<c:set var="title" value="2021 소확행 이벤트 2"/>
<c:set var="HD_URL" value="${CTX_ROOT}/shop/goods/brandGoodsList.do?searchGoodsBrandId=GBRAND_0000000000008"/>
<c:set var="PP_URL" value="${CTX_ROOT}/shop/goods/brandGoodsList.do?searchGoodsBrandId=GBRAND_0000000000002"/>
<c:set var="SN_URL" value="${CTX_ROOT}/shop/goods/brandGoodsList.do?searchGoodsBrandId=GBRAND_0000000000123"/>
<c:set var="SW_URL" value="${CTX_ROOT}/shop/goods/brandGoodsList.do?searchGoodsBrandId=GBRAND_0000000000122"/>
<c:set var="JS_URL" value="${CTX_ROOT}/shop/goods/brandGoodsList.do?searchGoodsBrandId=GBRAND_0000000000058"/>
<c:set var="MH_URL" value="${CTX_ROOT}/shop/goods/brandGoodsList.do?searchGoodsBrandId=GBRAND_0000000000017"/>
<c:set var="DI_URL" value="${CTX_ROOT}/shop/goods/brandGoodsList.do?searchGoodsBrandId=GBRAND_0000000000118"/>
<c:set var="SL_URL" value="${CTX_ROOT}/shop/goods/brandGoodsList.do?searchGoodsBrandId=GBRAND_0000000000015"/>
<c:set var="ID_URL" value="${CTX_ROOT}/shop/goods/brandGoodsList.do?searchGoodsBrandId=GBRAND_0000000000025"/>
<c:set var="OT_URL" value="${CTX_ROOT}/shop/goods/brandGoodsList.do?searchGoodsBrandId=GBRAND_0000000000124"/>
<c:set var="SE_URL" value="${CTX_ROOT}/shop/goods/brandGoodsList.do?searchGoodsBrandId=GBRAND_0000000000142"/>
<c:set var="ST_URL" value="${CTX_ROOT}/shop/goods/brandGoodsList.do?searchGoodsBrandId=GBRAND_0000000000130"/>
<c:set var="MD_URL" value="${CTX_ROOT}/shop/goods/brandGoodsList.do?searchGoodsBrandId=GBRAND_0000000000108"/>
<c:set var="RA_URL" value="${CTX_ROOT}/shop/goods/brandGoodsList.do?searchGoodsBrandId=GBRAND_0000000000116"/>
<c:set var="HN_URL" value="${CTX_ROOT}/shop/goods/brandGoodsList.do?searchGoodsBrandId=GBRAND_0000000000029"/>
<c:set var="MY_URL" value="${CTX_ROOT}/shop/goods/brandGoodsList.do?searchGoodsBrandId=GBRAND_0000000000143"/>
<c:set var="BR_URL" value="${CTX_ROOT}/shop/goods/brandGoodsList.do?searchGoodsBrandId=GBRAND_0000000000093"/>
<c:set var="PO_URL" value="${CTX_ROOT}/shop/goods/brandGoodsList.do?searchGoodsBrandId=GBRAND_0000000000094"/>
<c:set var="IC_URL" value="${CTX_ROOT}/shop/goods/brandGoodsList.do?searchGoodsBrandId=GBRAND_0000000000104"/>
<c:set var="DE_URL" value="${CTX_ROOT}/shop/goods/brandGoodsList.do?searchGoodsBrandId=GBRAND_0000000000099"/>
<c:set var="HM_URL" value="${CTX_ROOT}/shop/goods/brandGoodsList.do?searchGoodsBrandId=GBRAND_0000000000150"/>
<c:set var="HMH_URL" value="${CTX_ROOT}/shop/goods/goodsView.do?goodsId=GOODS_00000000001915"/>
<c:set var="JG_URL" value="${CTX_ROOT}/shop/goods/goodsView.do?goodsId=GOODS_00000000001914"/>
<c:set var="WG_URL" value="${CTX_ROOT}/shop/goods/goodsView.do?goodsId=GOODS_00000000001916"/>
<c:set var="BT_URL" value="${CTX_ROOT}/shop/goods/brandGoodsList.do?searchGoodsBrandId=GBRAND_0000000000027"/>
<c:set var="DD_URL" value="${CTX_ROOT}/shop/goods/brandGoodsList.do?searchGoodsBrandId=GBRAND_0000000000131"/>
<!DOCTYPE html>
<html>
<head>
	<title>${title}</title>
	<!--이벤트 css-->
    <link href="${CTX_ROOT}/html/SITE_00000/event/210111open2/css/event.css" rel="stylesheet" />
     <metatag>
		<meta property="og:title" content="${title}"/>
		<meta property="og:image" content="${CTX_ROOT}/html/SITE_00000/event/210111open1/images/img_visual.jpg"/>
	</metatag>
</head>
<body>
	<c:choose>
		<c:when test="${param.searchGoodsBrandId eq 'GBRAND_0000000000008'}">
			 <input type="hidden" id="eventBrand" value="hd"> 
		</c:when>
		<c:when test="${param.searchGoodsBrandId eq 'GBRAND_0000000000002'}">
			 <input type="hidden" id="eventBrand" value="pp"> 
		</c:when>
		<c:when test="${param.searchGoodsBrandId eq 'GBRAND_0000000000123'}">
			 <input type="hidden" id="eventBrand" value="sn"> 
		</c:when>
		<c:when test="${param.searchGoodsBrandId eq 'GBRAND_0000000000122'}">
			 <input type="hidden" id="eventBrand" value="sw"> 
		</c:when>
		<c:when test="${param.searchGoodsBrandId eq 'GBRAND_0000000000058'}">
			 <input type="hidden" id="eventBrand" value="js"> 
		</c:when>
		<c:when test="${param.searchGoodsBrandId eq 'GBRAND_0000000000017'}">
			 <input type="hidden" id="eventBrand" value="mh"> 
		</c:when>
		<c:when test="${param.searchGoodsBrandId eq 'GBRAND_0000000000118'}">
			 <input type="hidden" id="eventBrand" value="di"> 
		</c:when>
		<c:when test="${param.searchGoodsBrandId eq 'GBRAND_0000000000093'}">
			 <input type="hidden" id="eventBrand" value="br"> 
		</c:when>
		<c:when test="${param.searchGoodsBrandId eq 'GBRAND_0000000000015'}">
			 <input type="hidden" id="eventBrand" value="sl"> 
		</c:when>
		<c:when test="${param.searchGoodsBrandId eq 'GBRAND_0000000000025'}">
			 <input type="hidden" id="eventBrand" value="id"> 
		</c:when>
		<c:when test="${param.searchGoodsBrandId eq 'GBRAND_0000000000124'}">
			 <input type="hidden" id="eventBrand" value="ot"> 
		</c:when>
		<c:when test="${param.searchGoodsBrandId eq 'GBRAND_0000000000142'}">
			 <input type="hidden" id="eventBrand" value="se"> 
		</c:when>
		<c:when test="${param.searchGoodsBrandId eq 'GBRAND_0000000000108'}">
			 <input type="hidden" id="eventBrand" value="md"> 
		</c:when>
		<c:when test="${param.searchGoodsBrandId eq 'GBRAND_0000000000116'}">
			 <input type="hidden" id="eventBrand" value="ra"> 
		</c:when>
		<c:when test="${param.searchGoodsBrandId eq 'GBRAND_0000000000029'}">
			 <input type="hidden" id="eventBrand" value="hn"> 
		</c:when>
		<c:when test="${param.searchGoodsBrandId eq 'GBRAND_0000000000143'}">
			 <input type="hidden" id="eventBrand" value="my"> 
		</c:when>
		<c:when test="${param.searchGoodsBrandId eq 'GBRAND_0000000000142'}">
			 <input type="hidden" id="eventBrand" value="cn"> 
		 </c:when>
		<c:when test="${param.searchGoodsBrandId eq 'GBRAND_000000000093'}">
			 <input type="hidden" id="eventBrand" value="br"> 
		</c:when>
		<c:when test="${param.searchGoodsBrandId eq 'GBRAND_0000000000094'}">
			 <input type="hidden" id="eventBrand" value="po"> 
		</c:when>
		<c:when test="${param.searchGoodsBrandId eq 'GBRAND_0000000000104'}">
			 <input type="hidden" id="eventBrand" value="ic"> 
		</c:when>
		<c:when test="${param.searchGoodsBrandId eq 'GBRAND_0000000000099'}">
			 <input type="hidden" id="eventBrand" value="de"> 
		</c:when>
		<c:when test="${param.searchGoodsBrandId eq 'GBRAND_0000000000150'}">
			 <input type="hidden" id="eventBrand" value="hm"> 
		</c:when>
		<c:when test="${param.searchGoodsBrandId eq 'GBRAND_0000000000027'}">
			 <input type="hidden" id="eventBrand" value="bt"> 
		</c:when>
		<c:when test="${param.searchGoodsBrandId eq 'GBRAND_0000000000131'}">
			 <input type="hidden" id="eventBrand" value="dd"> 
		</c:when>
		<c:otherwise>
		</c:otherwise>
	</c:choose>
	 <c:import url="/shop/event/goodsEventLocation.do" charEncoding="utf-8">
      	<c:param name="isEventHome" value="true"/>
      	<c:param name="eventNm" value="${title}"/>
      	<c:param name="url" value="/shop/event/open2.do"/>
      	<c:param name="imgUrl" value="${BASE_URL}/html/SITE_00000/event/210111open2/images/m_img_visual.jpg"/>
     </c:import>
	 <div class="event-area">
	     <div class="event-visual">
	         <div class="visual-img">
	             <img src="${CTX_ROOT}/html/SITE_00000/event/210111open2/images/img_visual.jpg" alt="2021 소확행 이벤트2 파격적인 가격의 다양한 이벤트 상품을 만나봐요. ※이벤트 상품은 구매제한 없음" class="m-none" />
	             <img src="${CTX_ROOT}/html/SITE_00000/event/210111open2/images/m_img_visual.jpg" alt="2021 소확행 이벤트2 파격적인 가격의 다양한 이벤트 상품을 만나봐요. ※이벤트 상품은 구매제한 없음" class="m-block" />
	         </div>
	         <canvas class="snow" id="snow"></canvas>
	     </div>
	     <div class="event-tabs">
	         <ul class="event-tabs-nav">
                        <li class="finish">
                            <a href="#hd">
                                <cite>현대생활식서</cite>
                                <p>‘바로잡곡6종세트’증정</p>
                            </a>
                        </li>
                        <li class="event-mark is-active">
                            <a href="#hmh">
                                <cite>한미멀티비타민</cite>
                                <p>임팩트 멀티비타민 1+1 29,900원</p>
                            </a>
                        </li>
                        <li class="event-mark">
                            <a href="#hm">
                                <cite>한만두</cite>
                                <p>2021명 선착순 설 가격 할인이벤트</p>
                            </a>
                        </li>
                        <li class="event-mark">
                            <a href="#jg">
                                <cite>침향단</cite>
                                <p>종근당 활력 침향단 1+1</p>
                            </a>
                        </li>
                        <li class="event-mark">
                            <a href="#js">
                                <cite>장수김</cite>
                                <p>설 선물세트 / 미니김6종 증정</p>
                            </a>
                        </li>
                        <li class="event-mark">
                            <a href="#wg">
                                <cite>우가청담</cite>
                                <p>미슐랭 명품한우 설 선물세트</p>
                            </a>
                        </li>
                        <li class="event-mark">
                            <a href="#ot">
                                <cite>오트리</cite>
                                <p>설 선물세트 9종 / 견과류 가격 할인</p>
                            </a>
                        </li>
                        <li class="finish">
                            <a href="#pp">
                                <cite>엘레강스펫</cite>
                                <p>가격 할인, 1+1, 증정</p>
                            </a>
                        </li>
                        <li class="finish">
                            <a href="#hn">
                                <cite>어니스틴</cite>
                                <p>2주차프로모션 기획</p>
                            </a>
                        </li>
                        <li class="event-mark">
                            <a href="#id">
                                <cite>아이두비</cite>
                                <p>생생 수퍼푸드 현미칩 선물 세트</p>
                            </a>
                        </li>
                        <li class="finish">
                            <a href="#se">
                                <cite>씨앤트리</cite>
                                <p>정품 증정 스페셜 이벤트</p>
                            </a>
                        </li>
                        <li class="finish">
                            <a href="#sn">
                                <cite>스낵24</cite>
                                <p>간식서랍 가격 할인</p>
                            </a>
                        </li>
                        <li class="finish">
                            <a href="#sw">
                                <cite>성원상회</cite>
                                <p>제품 구매시 선착순 100명</p>
                            </a>
                        </li>
                        <li class="finish">
                            <a href="#sl">
                                <cite>샐러드판다</cite>
                                <p>첫 구매 고객 20% 할인</p>
                            </a>
                        </li>
                        <li class="event-mark">
                            <a href="#bt">
                                <cite>보틀웍스</cite>
                                <p>2021년 보틀웍스 설 선물세트</p>
                            </a>
                        </li>
                        <li>
                            <a href="#mh">
                                <cite>미하이삭스</cite>
                                <p>유튜브 구독 + 좋아요, 1+1 혜택</p>
                            </a>
                        </li>
                        <li class="finish">
                            <a href="#md">
                                <cite>메디에이지</cite>
                                <p>뉴트리에이지 990원</p>
                            </a>
                        </li>
                        <li class="finish">
                            <a href="#ra">
                                <cite>레아라 다이아포스</cite>
                                <p>첫구독시 할인</p>
                            </a>
                        </li>
                        <li class="event-mark">
                            <a href="#dd">
                                <cite>뜨라래</cite>
                                <p>동원 천지인 홍력천골드 10병</p>
                            </a>
                        </li>
                        <li>
                            <a href="#di">
                                <cite>다이아프리티</cite>
                                <p>립스틱, 밀키 크림 할인</p>
                            </a>
                        </li>
                        <li class="finish">
		<!--                      <a href="#st"> -->
							<div>
		                         <cite>스템코</cite>
		                         <p>마스크 1+1 특가</p>
		                    </div>
		<!--                      </a> -->
		                 </li>
                    </ul>
              <div class="event-tabs-cont  is-active" id="hmh">
                 <div class="event-txt-area">
                     <cite>임팩트 멀티비타민 1+1 <br> 29,900원</cite>
                     <p>한미멀티비타민</p>
                     <span>이벤트기간 : 2021/01/25 ~ 2021/02/14</span>
                     <a href="${HMH_URL}" class="btn-lg width spot4">바로가기 <i class="ico-arr-r sm spot"></i></a>
                 </div>
                 <div class="event-img-area view-content">
                     <img src="${CTX_ROOT}/html/SITE_00000/event/210111open2/images/hmh1.jpg" />
                 </div>
                 <div class="btn-area">
                     <a href="${HMH_URL}" class="btn-lg width spot">바로가기 <i class="ico-arr-r sm wh"></i></a>
                 </div>
             </div>
             <div class="event-tabs-cont" id="hm">
                 <div class="event-txt-area">
                     <cite>2021명 선착순<br />설 가격 할인이벤트</cite>
                     <p>한만두</p>
                     <span>이벤트기간 : 2021/01/25 ~ 2021/02/14</span>
                     <a href="${HM_URL}" class="btn-lg width spot4">바로가기 <i class="ico-arr-r sm spot"></i></a>
                 </div>
                 <div class="event-img-area view-content">
                     <img src="${CTX_ROOT}/html/SITE_00000/event/210111open2/images/hm1.jpg" />
                 </div>
                 <div class="btn-area">
                     <a href="${HM_URL}" class="btn-lg width spot">바로가기 <i class="ico-arr-r sm wh"></i></a>
                 </div>
             </div>
             <div class="event-tabs-cont" id="jg">
                 <div class="event-txt-area">
                     <cite>종근당 활력 침향단 1+1<br />2021세트 한정판</cite>
                     <p>침향단</p>
                     <span>이벤트기간 : 2021/01/25 ~ 2021/02/14</span>
                     <a href="${JG_URL}" class="btn-lg width spot4">바로가기 <i class="ico-arr-r sm spot"></i></a>
                 </div>
                 <div class="event-img-area view-content">
                     <img src="${CTX_ROOT}/html/SITE_00000/event/210111open2/images/jg1.jpg" />
                 </div>
                 <div class="btn-area">
                     <a href="${JG_URL}" class="btn-lg width spot">바로가기 <i class="ico-arr-r sm wh"></i></a>
                 </div>
             </div>
             <div class="event-tabs-cont" id="wg">
                 <div class="event-txt-area">
                     <cite>미슐랭 선정 명품 숙성한우 <br />오직 FOXEDU STORE에서만</cite>
                     <p>우가청담</p>
                     <span>이벤트기간 : 2021/01/25 ~ 2021/02/14</span>
                     <a href="${WG_URL}" class="btn-lg width spot4">바로가기 <i class="ico-arr-r sm spot"></i></a>
                 </div>
                 <div class="event-img-area view-content">
                     <img src="${CTX_ROOT}/html/SITE_00000/event/210111open2/images/wg1.jpg" />
                 </div>
                 <div class="btn-area">
                     <a href="${WG_URL}" class="btn-lg width spot">바로가기 <i class="ico-arr-r sm wh"></i></a>
                 </div>
             </div>
             <div class="event-tabs-cont" id="id">
                 <div class="event-txt-area">
                     <cite>
              [설맞이 이벤트]<br />
                         생생 수퍼푸드 현미칩 선물 세트
                     </cite>
                     <p>아이두비</p>
                     <span>이벤트기간 : 2021/01/25 ~ 2021/02/14</span>
                     <a href="${ID_URL}" class="btn-lg width spot4">바로가기 <i class="ico-arr-r sm spot"></i></a>
                 </div>
                 <div class="event-img-area view-content">
                     <img src="${CTX_ROOT}/html/SITE_00000/event/210111open2/images/id1.jpg" />
                 </div>
                 <div class="btn-area">
                     <a href="${ID_URL}" class="btn-lg width spot">바로가기 <i class="ico-arr-r sm wh"></i></a>
                 </div>
             </div>
             <div class="event-tabs-cont" id="bt">
                 <div class="event-txt-area">
                     <cite>2021년 보틀웍스<br/>설 선물세트</cite>
                     <p>보틀웍스 </p>
                     <span>이벤트기간 : 2021/01/25 ~ 2021/02/14</span>
                     <a href="${BT_URL}" class="btn-lg width spot4">바로가기 <i class="ico-arr-r sm spot"></i></a>
                 </div>
                 <div class="event-img-area view-content">
                     <img src="${CTX_ROOT}/html/SITE_00000/event/210111open2/images/bt1.jpg" />
                 </div>
                 <div class="btn-area">
                     <a href="${BT_URL}" class="btn-lg width spot">바로가기 <i class="ico-arr-r sm wh"></i></a>
                 </div>
             </div>
	         <div class="event-tabs-cont" id="ic">
	             <div class="event-txt-area">
	                 <cite>픽프로바이오틱스 1+1</cite>
	                 <p>힐스토리</p>
	                 <span>이벤트기간 : 2021/01/18 ~ 2021/02/10</span>
	                 <a href="${IC_URL}" class="btn-lg width spot4">바로가기 <i class="ico-arr-r sm spot"></i></a>
	             </div>
	             <div class="event-img-area view-content">
	                 <img src="${CTX_ROOT}/html/SITE_00000/event/210111open2/images/ic1.jpg" />
	             </div>
	             <div class="btn-area">
	                 <a href="${IC_URL}" class="btn-lg width spot">바로가기 <i class="ico-arr-r sm wh"></i></a>
	             </div>
	         </div>
	         <div class="event-tabs-cont" id="de">
	             <div class="event-txt-area">
	                 <cite>가격할인 이벤트</cite>
	                 <p>델몬트</p>
	                 <span>이벤트기간 : 2021/01/18 ~ 2021/02/10</span>
	                 <a href="${DE_URL}" class="btn-lg width spot4">바로가기 <i class="ico-arr-r sm spot"></i></a>
	             </div>
	             <div class="event-img-area view-content">
	                 <img src="${CTX_ROOT}/html/SITE_00000/event/210111open2/images/de1.jpg" />
	             </div>
	             <div class="btn-area">
	                 <a href="${DE_URL}" class="btn-lg width spot">바로가기 <i class="ico-arr-r sm wh"></i></a>
	             </div>
	         </div>
	         <div class="event-tabs-cont" id="hd">
	             <div class="event-txt-area">
	                 <cite>정기구독신청 고객<br />'바로잡곡6종세트'증정</cite>
	                 <p>현대생활식서</p>
	                 <span>이벤트기간 : 2021/01/18 ~ 2021/02/10</span>
	                 <a href="${HD_URL}" class="btn-lg width spot4">바로가기 <i class="ico-arr-r sm spot"></i></a>
	             </div>
	             <div class="event-img-area view-content">
	                 <img src="${CTX_ROOT}/html/SITE_00000/event/210111open2/images/hd1.jpg" />
	             </div>
	             <div class="btn-area">
	                 <a href="${HD_URL}" class="btn-lg width spot">바로가기 <i class="ico-arr-r sm wh"></i></a>
	             </div>
	         </div>
	         <div class="event-tabs-cont" id="po">
	             <div class="event-txt-area">
	                 <cite>댄케이크 증정이벤트</cite>
	                 <p>포미</p>
	                 <span>이벤트기간 : 2021/01/18 ~ 2021/02/10</span>
	                 <a href="${PO_URL}" class="btn-lg width spot4">바로가기 <i class="ico-arr-r sm spot"></i></a>
	             </div>
	             <div class="event-img-area view-content">
	                 <img src="${CTX_ROOT}/html/SITE_00000/event/210111open2/images/po1.jpg" />
	             </div>
	             <div class="btn-area">
	                 <a href="${PO_URL}" class="btn-lg width spot">바로가기 <i class="ico-arr-r sm wh"></i></a>
	             </div>
	         </div>
	         <div class="event-tabs-cont" id="pp">
	             <div class="event-tabs-sm">
	                 <ul class="event-tabs-nav-sm">
	                     <li>
	                         <a href="#pp1">
	                             엘레강스펫 샴푸 or 컨디셔너<br />
	                             스테이크큐브 간식 1개 증정
	                         </a>
	                     </li>
	                     <li>
	                         <a href="#pp2">
	                             엘레강스펫 워터리스워시<br />
	                             1,000원 할인 이벤트
	                         </a>
	                     </li>
	                     <li>
	                         <a href="#pp3">
	                             엘레강스펫 이어클리너<br />
	                             1+1이벤트
	                         </a>
	                     </li>
	                     <li>
	                         <a href="#pp4">
	                             엘레강스펫 주변 탈취제<br />
	                             1,000원 할인 이벤트
	                         </a>
	                     </li>
	                 </ul>
	                 <div class="event-tabs-cont-sm-area">
	                     <div class="event-tabs-cont-sm" id="pp1">
	                         <div class="event-txt-area">
	                             <cite>
	                                 엘레강스펫 샴푸 or 컨디셔너<br />
	                                 스테이크큐브 간식 1개 증정
	                             </cite>
	                             <p>엘레강스펫</p>
	
	                             <span>이벤트기간 : 2021/01/18 ~ 2021/02/10</span>
	                             <a href="${PP_URL}" class="btn-lg width spot4">바로가기 <i class="ico-arr-r sm spot"></i></a>
	                         </div>
	                         <div class="event-img-area view-content">
	                             <img src="${CTX_ROOT}/html/SITE_00000/event/210111open2/images/el1.jpg" />
	                         </div>
	                         <div class="btn-area">
	                             <a href="${PP_URL}" class="btn-lg width spot">바로가기 <i class="ico-arr-r sm wh"></i></a>
	                         </div>
	                     </div>
	                     <div class="event-tabs-cont-sm" id="pp2">
	                         <div class="event-txt-area">
	                             <cite>
	                                 엘레강스펫 워터리스워시<br />
	                                 1,000원 할인 이벤트
	                             </cite>
	                             <p>엘레강스펫</p>
	
	                             <span>이벤트기간 : 2021/01/18 ~ 2021/02/10</span>
	                             <a href="${PP_URL}" class="btn-lg width spot4">바로가기 <i class="ico-arr-r sm spot"></i></a>
	                         </div>
	                         <div class="event-img-area view-content">
	                             <img src="${CTX_ROOT}/html/SITE_00000/event/210111open2/images/el2.jpg" />
	                         </div>
	                         <div class="btn-area">
	                             <a href="${PP_URL}" class="btn-lg width spot">바로가기 <i class="ico-arr-r sm wh"></i></a>
	                         </div>
	                     </div>
	                     <div class="event-tabs-cont-sm" id="pp3">
	                         <div class="event-txt-area">
	                             <cite>
	                                 엘레강스펫 이어클리너<br />
	                                 1+1이벤트
	                             </cite>
	                             <p>엘레강스펫</p>
	
	                             <span>이벤트기간 : 2021/01/18 ~ 2021/02/10</span>
	                             <a href="${PP_URL}" class="btn-lg width spot4">바로가기 <i class="ico-arr-r sm spot"></i></a>
	                         </div>
	                         <div class="event-img-area view-content">
	                             <img src="${CTX_ROOT}/html/SITE_00000/event/210111open2/images/el3.jpg" />
	                         </div>
	                         <div class="btn-area">
	                             <a href="${PP_URL}" class="btn-lg width spot">바로가기 <i class="ico-arr-r sm wh"></i></a>
	                         </div>
	                     </div>
	                     <div class="event-tabs-cont-sm" id="pp4">
	                         <div class="event-txt-area">
	                             <cite>
	                                 엘레강스펫 주변 탈취제<br />
	                                 1,000원 할인 이벤트
	                             </cite>
	                             <p>엘레강스펫</p>
	
	                             <span>이벤트기간 : 2021/01/18 ~ 2021/02/10</span>
	                             <a href="${PP_URL}" class="btn-lg width spot4">바로가기 <i class="ico-arr-r sm spot"></i></a>
	                         </div>
	                         <div class="event-img-area view-content">
	                             <img src="${CTX_ROOT}/html/SITE_00000/event/210111open2/images/el4.jpg" />
	                         </div>
	                         <div class="btn-area">
	                             <a href="${PP_URL}" class="btn-lg width spot">바로가기 <i class="ico-arr-r sm wh"></i></a>
	                         </div>
	                     </div>
	                 </div>
	             </div>
	         </div>
	         <div class="event-tabs-cont" id="sn">
	             <div class="event-txt-area">
	                 <cite>
	                     간식서랍 가격 할인<br />
	                     선착순 30명
	                 </cite>
	                 <p>스낵24</p>
	                 <span>이벤트기간 : 2021/01/18 ~ 2021/02/10</span>
	                 <a href="${SN_URL}" class="btn-lg width spot4">바로가기 <i class="ico-arr-r sm spot"></i></a>
	             </div>
	             <div class="event-img-area view-content">
	                 <img src="${CTX_ROOT}/html/SITE_00000/event/210111open2/images/sn1.jpg" />
	             </div>
	             <div class="btn-area">
	                 <a href="${SN_URL}" class="btn-lg width spot">바로가기 <i class="ico-arr-r sm wh"></i></a>
	             </div>
	         </div>
	         <div class="event-tabs-cont" id="sw">
	             <div class="event-txt-area">
	                 <cite>
	                     성원상품 제품 구매시<br />
	                     선착순 100명 / 체험형 고객 참여가능
	                 </cite>
	                 <p>성원상회</p>
	                 <span>이벤트기간 : 2021/01/18 ~ 2021/02/10</span>
	                 <a href="${SW_URL}" class="btn-lg width spot4">바로가기 <i class="ico-arr-r sm spot"></i></a>
	             </div>
	             <div class="event-img-area view-content">
	                 <img src="${CTX_ROOT}/html/SITE_00000/event/210111open2/images/sw1.jpg" />
	             </div>
	             <div class="btn-area">
	                 <a href="${SW_URL}" class="btn-lg width spot">바로가기 <i class="ico-arr-r sm wh"></i></a>
	             </div>
	         </div>
			 <div class="event-tabs-cont" id="js">
                <div class="event-tabs-sm">
                    <ul class="event-tabs-nav-sm">
                        <li>
                            <a href="#js1">신안 명품 재래돌김 <br />설 선물세트</a>
                        </li>
                        <li>
                            <a href="#js2">
                                장수김 구독상품 주문시<br />
                                선착순 100명 미니김6종 증정
                            </a>
                        </li>
                    </ul>
                    <div class="event-tabs-cont-sm-area">
                        <div class="event-tabs-cont-sm" id="js1">
                            <div class="event-txt-area">
                                <cite>
                                    신안 명품 재래돌김<br />
                                    설 선물세트
                                </cite>
                                <p>장수김</p>
                                <span>이벤트기간 : 2021/01/25 ~ 2021/02/14</span>
                                <a href="${JS_URL}" class="btn-lg width spot4">바로가기 <i class="ico-arr-r sm spot"></i></a>
                            </div>
                            <div class="event-img-area view-content">
                                <img src="${CTX_ROOT}/html/SITE_00000/event/210111open2/images/js1.jpg" />
                            </div>
                            <div class="btn-area">
                                <a href="${JS_URL}" class="btn-lg width spot">바로가기 <i class="ico-arr-r sm wh"></i></a>
                            </div>
                        </div>
                        <div class="event-tabs-cont-sm" id="js2">
                            <div class="event-txt-area">
                                <cite>
                                    장수김 구독상품 주문시<br />
                                    선착순 100명 미니김6종 증정
                                </cite>
                                <p>장수김</p>
                                <span>이벤트기간 : 2021/01/18 ~ 2021/02/10</span>
                                <a href="${JS_URL}" class="btn-lg width spot4">바로가기 <i class="ico-arr-r sm spot"></i></a>
                            </div>
                            <div class="event-img-area view-content">
                                <img src="${CTX_ROOT}/html/SITE_00000/event/210111open2/images/js2.jpg" />
                            </div>
                            <div class="btn-area">
                                <a href="${JS_URL}" class="btn-lg width spot">바로가기 <i class="ico-arr-r wh"></i></a>
                            </div>
                        </div>
                    </div>
                </div>
            </div>

       		 
   		  	 <div class="event-tabs-cont" id="mh">
                    <div class="event-tabs-sm">
                        <ul class="event-tabs-nav-sm">
                            <li>
                                <a href="#mh1">1+1이벤트 혜택</a>
                            </li>
                            <li>
                                <a href="#mh2">미하이삭스 유튜브<br />구독 + 좋아요</a>
                            </li>
                        </ul>
                        <div class="event-tabs-cont-sm-area">
                            <div class="event-tabs-cont-sm" id="mh1">
                                <div class="event-txt-area">
                                    <cite>1+1이벤트 혜택</cite>
                                    <p>미하이삭스</p>
                                    <span>이벤트기간 : 2021/01/18 ~ 2021/02/19</span>
                                    <a href="${MH_URL}" class="btn-lg width spot4">바로가기 <i class="ico-arr-r sm spot"></i></a>
                                </div>
                                <div class="event-img-area view-content">
                                    <img src="${CTX_ROOT}/html/SITE_00000/event/210111open2/images/mh1.jpg" />
                                </div>
                                <div class="btn-area">
                                    <a href="${MH_URL}" class="btn-lg width spot">바로가기 <i class="ico-arr-r sm wh"></i></a>
                                </div>
                            </div>
                            <div class="event-tabs-cont-sm" id="mh2">
                                <div class="event-txt-area">
                                    <cite>
                                        미하이삭스 유튜브 구독+ 좋아요<br />
                                        [FOXEDU STORE]에서 상품 구매 후<br />
                                        배송메시지에 유튜브 아이디 남기면 참여완료
                                    </cite>
                                    <p>미하이삭스</p>
                                    <span>이벤트기간 : 2021/01/18 ~ 2021/02/19</span>
                                    <a href="${MH_URL}" class="btn-lg width spot4">바로가기 <i class="ico-arr-r sm spot"></i></a>
                                </div>
                                <div class="event-img-area view-content">
                                     <img src="${CTX_ROOT}/html/SITE_00000/event/210111open2/images/mh2.jpg" />
                                </div>
                                <div class="btn-area">
                                     <a href="${MH_URL}" class="btn-lg width spot">바로가기 <i class="ico-arr-r sm wh"></i></a>
                                </div>
                            </div>
                        </div>
                  </div>
             </div>	
             <div class="event-tabs-cont" id="br">
	             <div class="event-txt-area">
	                 <cite>러스크 40% 할인 이벤트</cite>
	                 <p>브란트</p>
	                 <span>이벤트기간 : 2021/01/18 ~ 2021/02/10</span>
	                 <a href="${BR_URL}" class="btn-lg width spot4">바로가기 <i class="ico-arr-r sm spot"></i></a>
	             </div>
	             <div class="event-img-area view-content">
	                 <img src="${CTX_ROOT}/html/SITE_00000/event/210111open2/images/br1.jpg" />
	             </div>
	             <div class="btn-area">
	                 <a href="${BR_URL}" class="btn-lg width spot">바로가기 <i class="ico-arr-r sm wh"></i></a>
	             </div>
	         </div>   
        	 <div class="event-tabs-cont" id="sl">
	             <div class="event-txt-area">
	                 <cite>첫구매 고객 오천원 할인</cite>
	                 <p>샐러드판다</p>
	                 <span>이벤트기간 : 2021/01/18 ~ 2021/02/10</span>
	                 <a href="${SL_URL}" class="btn-lg width spot4">바로가기 <i class="ico-arr-r sm spot"></i></a>
	             </div>
	             <div class="event-img-area view-content">
	                 <img src="${CTX_ROOT}/html/SITE_00000/event/210111open2/images/sl1.jpg" />
	             </div>
	             <div class="btn-area">
	                 <a href="${SL_URL}" class="btn-lg width spot">바로가기 <i class="ico-arr-r sm wh"></i></a>
	             </div>
	         </div>
	         <div class="event-tabs-cont" id="di">
	             <div class="event-txt-area">
	                 <cite>
	                     다이아프리티 톤업 밀키 크림 &<br />
	                     다이아프리티 키스추 립스틱 할인
	                 </cite>
	                 <p>다이아프리티</p>
	                 <span>이벤트기간 : 2021/01/18 ~ 재고소진시까지</span>
	                 <a href="${DI_URL}" class="btn-lg width spot4">바로가기 <i class="ico-arr-r sm spot"></i></a>
	             </div>
	             <div class="event-img-area view-content">
	                 <img src="${CTX_ROOT}/html/SITE_00000/event/210111open2/images/di1.jpg" />
	             </div>
	             <div class="btn-area">
	                 <a href="${DI_URL}" class="btn-lg width spot">바로가기 <i class="ico-arr-r sm wh"></i></a>
	             </div>
	         </div>
	         
	         <div class="event-tabs-cont" id="ot">
                 <div class="event-tabs-sm">
                     <ul class="event-tabs-nav-sm">
                         <li>
                             <a href="#ot1">
                                 설 선물세트 9종
					<br/> 가격할인 이벤트
                             </a>
                         </li>
                         <li>
                             <a href="#ot2">
                                 오트리 견과류<br />
                                 가격할인 이벤트
                             </a>
                         </li>
                     </ul>
                     <div class="event-tabs-cont-sm-area">
                         <div class="event-tabs-cont-sm" id="ot1">
                             <div class="event-txt-area">
                                 <cite>
                                     오트리 설 선물세트<br />가격할인 이벤트
                                 </cite>
                                 <p>오트리</p>
                                 <span>이벤트기간 : 2021/01/25 ~ 2021/02/14</span>
                                 <a href="${OT_URL}" class="btn-lg width spot4">바로가기 <i class="ico-arr-r sm spot"></i></a>
                             </div>
                             <div class="event-img-area view-content">
                                 <img src="${CTX_ROOT}/html/SITE_00000/event/210111open2/images/ot1.jpg" />
                             </div>
                             <div class="btn-area">
                                 <a href="${OT_URL}" class="btn-lg width spot">바로가기 <i class="ico-arr-r wh"></i></a>
                             </div>
                         </div>
                         <div class="event-tabs-cont-sm" id="ot2">
                             <div class="event-txt-area">
                                 <cite>
                                     오트리 견과류<br />
                                     가격할인 이벤트
                                 </cite>
                                 <p>오트리</p>
                                 <span>이벤트기간 : 2021/01/18 ~ 2021/02/10</span>
                                 <a href="${OT_URL}" class="btn-lg width spot4">바로가기 <i class="ico-arr-r sm spot"></i></a>
                             </div>
                             <div class="event-img-area view-content">
                                 <img src="${CTX_ROOT}/html/SITE_00000/event/210111open2/images/ot2.jpg" />
                             </div>
                             <div class="btn-area">
                                 <a href="${OT_URL}" class="btn-lg width spot">바로가기 <i class="ico-arr-r wh"></i></a>
                             </div>
                         </div>
                     </div>
                 </div>
             </div>

            <div class="event-tabs-cont" id="se">
                  <div class="event-tabs-sm">
                      <ul class="event-tabs-nav-sm">
                          <li>
                              <a href="#se1">겹보습 세트 구매 시<br /> 자작나무 힐러 크림 증정</a>
                          </li>
                          <li>
                              <a href="#se2">클렌징 폼 기획세트 구매 시<br /> 앰플 마스크 10매 증정</a>
                          </li>
                      </ul>
                      <div class="event-tabs-cont-sm-area">
                          <div class="event-tabs-cont-sm" id="se1">
                              <div class="event-txt-area">
                                  <cite>
                                     겹보습 세트 구매 시<br />
                                     자작나무 힐러 크림 증정
                                  </cite>
                                  <p>씨앤트리</p>
                                  <span>이벤트기간 : 2021/01/18 ~ 2021/02/10</span>
                                  <a href="${SE_URL}" class="btn-lg width spot4">바로가기 <i class="ico-arr-r sm spot"></i></a>
                              </div>
                              <div class="event-img-area view-content">
                                  <img src="${CTX_ROOT}/html/SITE_00000/event/210111open2/images/se1.jpg" />
                              </div>
                              <div class="btn-area">
                                  <a href="${SE_URL}" class="btn-lg width spot">바로가기 <i class="ico-arr-r wh"></i></a>
                              </div>
                          </div>
                          <div class="event-tabs-cont-sm" id="se2">
                              <div class="event-txt-area">
                                  <cite>
                                      히알루론 클렌징 폼 기획세트 구매 시<br />
                                      멀티 비타 앰플 마스크 시트 10매 증정
                                   </cite>
                                  <p>씨앤트리</p>
                                  <span>이벤트기간 : 2021/01/18 ~ 2021/02/10</span>
                                  <a href="${SE_URL}" class="btn-lg width spot4">바로가기 <i class="ico-arr-r sm spot"></i></a>
                              </div>
                              <div class="event-img-area view-content">
                                  <img src="${CTX_ROOT}/html/SITE_00000/event/210111open2/images/se2.jpg" />
                              </div>
                              <div class="btn-area">
                                  <a href="${SE_URL}" class="btn-lg width spot">바로가기 <i class="ico-arr-r sm wh"></i></a>
                              </div>
                          </div>
                      </div>
                  </div>
              </div>
	             <div class="event-tabs-cont" id="st">
                        <div class="event-txt-area">
                            <cite>
								스템코 마스크 1+1 특가 이벤트<br />
                                29,500원 / 무료배송
                            </cite>
                            <p>스템코</p>
                            <span>이벤트기간 : 2021/01/18 ~ 2021/02/10</span>
                            <a href="${ST_URL}" class="btn-lg width spot4">바로가기 <i class="ico-arr-r sm spot"></i></a>
                        </div>
                        <div class="event-img-area view-content">
                            <img src="${CTX_ROOT}/html/SITE_00000/event/210111open2/images/st1.jpg" />
                        </div>
                        <div class="btn-area">
                            <a href="${ST_URL}" class="btn-lg width spot">바로가기 <i class="ico-arr-r sm wh"></i></a>
                        </div>
                    </div>
                  <div class="event-tabs-cont" id="md">
	                  <div class="event-txt-area">
	                      <cite>뉴트리에이지<br />990원에 제공</cite>
	                      <p>메디에이지</p>
	                      <span>이벤트기간 : 2021/01/18 ~ 2021/02/10</span>
	                      <a href="${MD_URL}" class="btn-lg width spot4">바로가기 <i class="ico-arr-r sm spot"></i></a>
	                  </div>
	                  <div class="event-img-area view-content">
	                      <img src="${CTX_ROOT}/html/SITE_00000/event/210111open2/images/md1.jpg" />
	                  </div>
	                  <div class="btn-area">
	                      <a href="${MD_URL}" class="btn-lg width spot">바로가기 <i class="ico-arr-r sm wh"></i></a>
	                  </div>
                 </div>
                 <div class="event-tabs-cont" id="ra">
                     <div class="event-txt-area">
                         <cite>
                                프리미엄 하이드로겔 아이패치 골드 60매<br />+ 스노우 60매 + 퍼펙트 아이세럼 골드 30g<br />
                                첫 구독 할인
                         </cite>
                         <p>레아라 다이아포스</p>
                         <span>이벤트기간 : 2021/01/18 ~ 2021/02/10</span>
                         <a href="${RA_URL}" class="btn-lg width spot4">바로가기 <i class="ico-arr-r sm spot"></i></a>
                     </div>
                     <div class="event-img-area view-content">
                         <img src="${CTX_ROOT}/html/SITE_00000/event/210111open2/images/ra1.jpg" />
                     </div>
                     <div class="btn-area">
                         <a href="${RA_URL}" class="btn-lg width spot">바로가기 <i class="ico-arr-r sm wh"></i></a>
                     </div>
                 </div>
                 <div class="event-tabs-cont" id="hn">
                      <div class="event-txt-area">
                          <cite>
							2주차 프로모션 기획
                          </cite>
                          <p>어니스틴</p>
                          <span>이벤트기간 : 2021/01/18 ~ 2021/02/10</span>
                          <a href="${HN_URL}" class="btn-lg width spot4">바로가기 <i class="ico-arr-r sm spot"></i></a>
                      </div>
                      <div class="event-img-area view-content">
                          <img src="${CTX_ROOT}/html/SITE_00000/event/210111open2/images/hn1.jpg" />
                      </div>
                      <div class="btn-area">
                          <a href="${HN_URL}" class="btn-lg width spot">바로가기 <i class="ico-arr-r sm wh"></i></a>
                      </div>
                  </div>

                 <div class="event-tabs-cont" id="my">
                      <div class="event-txt-area">
                          <cite>
			              	선착순 100명<br />
							손소독제 증정 이벤트
                          </cite>
                          <p>마이얼스데이</p>
                          <span>이벤트기간 : 2021/01/18 ~ 2021/02/10</span>
                          <a href="${MY_URL}" class="btn-lg width spot4">바로가기 <i class="ico-arr-r sm spot"></i></a>
                      </div>
                      <div class="event-img-area view-content">
                          <img src="${CTX_ROOT}/html/SITE_00000/event/210111open2/images/my1.jpg" />
                      </div>
                      <div class="btn-area">
                          <a href="${MY_URL}" class="btn-lg width spot">바로가기 <i class="ico-arr-r sm wh"></i></a>
                      </div>
                  </div>
                  
                  <div class="event-tabs-cont" id="dd">
                      <div class="event-txt-area">
                          <cite>
			              	&lt;설명절 선물세트&gt;<br />
							동원 천지인 홍력천골드 10병
                          </cite>
                          <p>뜨라래</p>
                          <span>이벤트기간 : 2021/01/25 ~ 2021/02/14</span>
                          <a href="${DD_URL}" class="btn-lg width spot4">바로가기 <i class="ico-arr-r sm spot"></i></a>
                      </div>
                      <div class="event-img-area view-content">
                          <img src="${CTX_ROOT}/html/SITE_00000/event/210111open2/images/dd1.jpg" />
                      </div>
                      <div class="btn-area">
                          <a href="${DD_URL}" class="btn-lg width spot">바로가기 <i class="ico-arr-r sm wh"></i></a>
                      </div>
                  </div>
                  
	         <%-- <div class="img-cha">
	             <span><img src="${CTX_ROOT}/html/SITE_00000/event/210111open2/images/img_cha_txt1.svg" /></span>
	             <img src="${CTX_ROOT}/html/SITE_00000/event/210111open2/images/img_cow.png" alt="캐릭터" />
	         </div> --%>
	     </div>
	</div>
	<javascript>
		<script src="${CTX_ROOT}/html/SITE_00000/event/210111open2/js/event.js"></script>
		<script src="${CTX_ROOT}/resources/front/shop/goods/event/js/210118open2.js"></script>
		<script src="${CTX_ROOT}/resources/front/cmm/etc/js/kakaoApi.js"></script>
	</javascript>
</body>
</html>