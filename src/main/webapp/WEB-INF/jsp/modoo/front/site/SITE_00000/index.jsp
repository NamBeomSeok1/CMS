<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/modoo/common/commonTagLibs.jsp"%>
<!DOCTYPE html>
<html>
<head>
	<title>FOXEDU STORE</title>
</head>
<body>

		<%-- Today's Pick --%>
		<%-- 
		<section class="mn-visual mn-visaul-swiper">
			<c:import url="/banner/pickList.do" charEncoding="utf-8">
				<c:param name="searchSeCode" value="BANN003"/>
			</c:import>
		</section>
		--%>
		
		<section class="mn-visual mn-visaul-swiper">
            <div class="swiper-container">
                <ul class="swiper-wrapper">
                    <li class="swiper-slide event-slide" style="background-color:#97d2fe">
                        <a href="/shop/goods/goodsView.do?goodsId=GOODS_00000000001591">
                            <div class="wrap">
                                <img src="/resources/front/site/SITE_00000/image/main/visual01.jpg" alt=" " class="m-none" />
                                <img src="/resources/front/site/SITE_00000/image/main/visual01_m.jpg" alt=" " class="m-block" />
                            </div>
                        </a>
                    </li>
                    <li class="swiper-slide event-slide" style="background-color:#719af4">
                        <a href="/shop/goods/goodsView.do?goodsId=GOODS_00000000001610">
                            <div class="wrap">
                                <img src="/resources/front/site/SITE_00000/image/main/visual02.jpg" alt=" " class="m-none" />
                                <img src="/resources/front/site/SITE_00000/image/main/visual02_m.jpg" alt=" " class="m-block" />
                            </div>
                        </a>
                    </li>
                </ul>
            </div>
            <div class="visual-btn-area">
                <div class="swiper-prevnext">
                    <div class="swiper-button-prev">
                        <span>이전</span>
                    </div>
                    <div class="swiper-button-next">
                        <span>다음</span>
                    </div>
                </div>
                <div class="swiper-fnc-area">
                    <div class="swiper-progress-bar">
                        <span class="bg"></span>
                        <span class="bar"></span>
                    </div>

                    <div class="swiper-pagination"></div>
                    <div class="swiper-playstop">
                        <button type="button" class="btn-play">
                            <span>play</span>
                        </button>
                        <button type="button" class="btn-stop">
                            <span>stop</span>
                        </button>
                    </div>
                </div>
            </div>
        </section>
		
		
		<%-- Best 구독 --%>
		<%-- 
		<div class="wrap">
			<section id="bestProduct" class="mn-product">
				<%-- <c:import url="/embed/shop/goods/bestGoodsList.do" charEncoding="utf-8"/>--  %>
			</section>		  
		</div>
		 --%>
		 <div class="wrap">
		 	<%-- 폭스구독권 --%>
			<section id="sbsGoods" class="mn-product">
				<%-- <c:import url="/embed/shop/goods/mainGoodsList.do" charEncoding="utf-8"/>--%>
			</section>
			
			<%-- 폭스굿즈 --%>
			<section id="gdsGoods" class="mn-product">
				<%-- <c:import url="/embed/shop/goods/mainGoodsList.do" charEncoding="utf-8"/>--%>
			</section>
			
			<%-- 폭스교육상품 --%>
			<section id="gnrGoods" class="mn-product">
				<%-- <c:import url="/embed/shop/goods/mainGoodsList.do" charEncoding="utf-8"/>--%>
			</section>
		</div>
		
		<javascript>
			<script src="${CTX_ROOT}/resources/front/site/${SITE_ID }/js/main.js"></script>
			<c:if test="${empty USER_ID}">
				<script src="${CTX_ROOT }/user/sign/loginUserScript.do"></script>
			</c:if>
		</javascript>
		
		<%-- 메인페이지 접근 시 자동 로그인 처리 --%>
		<c:if test="${empty USER_ID}">
			<form id="loginForm" name="frm" action="/user/sign/snsActionLogin.do" method="post">
				   <input type="hidden" id="userKey" name="userKey"/>
				   <input type="hidden" id="clientCd" name="clientCd"/>
				   <input type="hidden" id="email" name="email"/>
				   <input type="hidden" id="name" name="name"/>
				   <input type="hidden" id="sexdstn" name="sexdstn"/>
				   <input type="hidden" id="agrde" name="agrde"/>
		   	</form>
		</c:if>
</body>
</html>