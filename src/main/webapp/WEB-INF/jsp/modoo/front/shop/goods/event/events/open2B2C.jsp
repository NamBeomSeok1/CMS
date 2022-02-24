<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/modoo/common/commonTagLibs.jsp"%>
<c:set var="title" value="2021 소확행 이벤트 2"/>
<c:set var="imgPath" value="${CTX_ROOT}/html/SITE_00000/event/210111open2"/>
<c:set var="JG_URL" value="${CTX_ROOT}/shop/goods/goodsView.do?goodsId=GOODS_00000000001812"/>
<c:set var="DI_URL" value="${CTX_ROOT}/shop/goods/brandGoodsList.do?searchGoodsBrandId=GBRAND_0000000000118"/>
<c:set var="RA_URL" value="${CTX_ROOT}/shop/goods/brandGoodsList.do?searchGoodsBrandId=GBRAND_0000000000116"/>
<c:set var="MH_URL" value="${CTX_ROOT}/shop/goods/brandGoodsList.do?searchGoodsBrandId=GBRAND_0000000000017"/>
<c:set var="MHJ_URL" value="${CTX_ROOT}/shop/goods/brandGoodsList.do?searchGoodsBrandId=GBRAND_0000000000025"/>
<c:set var="MY_URL" value="${CTX_ROOT}/shop/goods/brandGoodsList.do?searchGoodsBrandId=GBRAND_0000000000143"/>
<c:set var="SW_URL" value="${CTX_ROOT}/shop/goods/brandGoodsList.do?searchGoodsBrandId=GBRAND_0000000000122"/>
<c:set var="SN_URL" value="${CTX_ROOT}/shop/goods/brandGoodsList.do?searchGoodsBrandId=GBRAND_0000000000123"/>
<c:set var="SE_URL" value="${CTX_ROOT}/shop/goods/brandGoodsList.do?searchGoodsBrandId=GBRAND_0000000000142"/>
<c:set var="ST_URL" value="${CTX_ROOT}/shop/goods/brandGoodsList.do?searchGoodsBrandId=GBRAND_0000000000130"/>
<c:set var="HN_URL" value="${CTX_ROOT}/shop/goods/brandGoodsList.do?searchGoodsBrandId=GBRAND_0000000000029"/>
<c:set var="EL_URL" value="${CTX_ROOT}/shop/goods/brandGoodsList.do?searchGoodsBrandId=GBRAND_0000000000002"/>
<c:set var="JS_URL" value="${CTX_ROOT}/shop/goods/brandGoodsList.do?searchGoodsBrandId=GBRAND_0000000000058"/>
<c:set var="HD_URL" value="${CTX_ROOT}/shop/goods/brandGoodsList.do?searchGoodsBrandId=GBRAND_0000000000008"/>
<c:set var="HD_URL" value="${CTX_ROOT}/shop/goods/brandGoodsList.do?searchGoodsBrandId=GBRAND_0000000000008"/>
<c:set var="BR_URL" value="${CTX_ROOT}/shop/goods/brandGoodsList.do?searchGoodsBrandId=GBRAND_0000000000093"/>
<c:set var="PO_URL" value="${CTX_ROOT}/shop/goods/brandGoodsList.do?searchGoodsBrandId=GBRAND_0000000000094"/>
<!DOCTYPE html>
<html>
<head>
	<title>${title}</title>
	<!--이벤트 css-->
    <link href="${CTX_ROOT}/html/SITE_00000/event/210111open2/css/event.css" rel="stylesheet" />
     <metatag>
		<meta property="og:title" content="${title}"/>
		<meta property="og:image" content="${BASE_URL}/html/SITE_00000/event/210111open2/images/img_visual.jpg"/>
	</metatag>
</head>
<body>
	<c:choose>
		<c:when test="${param.searchGoodsBrandId eq 'GBRAND_0000000000118'}"> <!-- 다이아프리티 -->
			 <input type="hidden" id="eventBrand" value="di"> 
		</c:when>
		<c:when test="${param.searchGoodsBrandId eq 'GBRAND_0000000000116'}"> <!-- 레아라 다이아포스 -->
			 <input type="hidden" id="eventBrand" value="ra"> 
		</c:when>
		<c:when test="${param.searchGoodsBrandId eq 'GBRAND_0000000000017'}"> <!-- 미하이삭스 -->
			 <input type="hidden" id="eventBrand" value="mh"> 
		</c:when>
		<c:when test="${param.searchGoodsBrandId eq 'GBRAND_0000000000025'}"> <!-- 미학주의 -->
			 <input type="hidden" id="eventBrand" value="mhj"> 
		</c:when>
		<c:when test="${param.searchGoodsBrandId eq 'GBRAND_0000000000143'}"> <!-- 마이얼스데이 -->
			 <input type="hidden" id="eventBrand" value="my"> 
		</c:when>
		<c:when test="${param.searchGoodsBrandId eq 'GBRAND_0000000000122'}"> <!-- 성원상회 -->
			 <input type="hidden" id="eventBrand" value="sw"> 
		</c:when>
		<c:when test="${param.searchGoodsBrandId eq 'GBRAND_0000000000123'}"> <!-- 스낵24 -->
			 <input type="hidden" id="eventBrand" value="sn"> 
		</c:when>
		<c:when test="${param.searchGoodsBrandId eq 'GBRAND_0000000000142'}"> <!-- 씨앤트리 -->
			 <input type="hidden" id="eventBrand" value="se"> 
		</c:when>
		<c:when test="${param.searchGoodsBrandId eq 'GBRAND_0000000000029'}"> <!-- 어니스틴 -->
			 <input type="hidden" id="eventBrand" value="hn"> 
		</c:when>
		<c:when test="${param.searchGoodsBrandId eq 'GBRAND_0000000000002'}"> <!-- 엘레강스펫 -->
			 <input type="hidden" id="eventBrand" value="el"> 
		</c:when>
		<c:when test="${param.searchGoodsBrandId eq 'GBRAND_0000000000058'}"> <!-- 장수김 -->
			 <input type="hidden" id="eventBrand" value="js"> 
		</c:when>
		<c:when test="${param.searchGoodsBrandId eq 'GBRAND_0000000000094'}"> <!-- 포미 -->
			 <input type="hidden" id="eventBrand" value="po"> 
		</c:when>
		<c:when test="${param.searchGoodsBrandId eq 'GBRAND_0000000000008'}"> <!-- 현대생활식서 -->
			 <input type="hidden" id="eventBrand" value="hd"> 
		</c:when>
		<c:when test="${param.searchGoodsBrandId eq 'GBRAND_0000000000093'}"> <!-- 브란트 -->
			 <input type="hidden" id="eventBrand" value="br"> 
		</c:when>
		<c:otherwise>
			 <!-- <input type="hidden" id="eventBrand" value="jg"> 정관장 -->
		</c:otherwise>
	</c:choose>
	
	<c:import url="/shop/event/goodsEventLocation.do" charEncoding="utf-8">
      	<c:param name="isEventHome" value="false"/>
      	<c:param name="eventNm" value="${title}"/>
      	<c:param name="url" value="/shop/event/open2B2C.do"/>
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
                 <li class="is-active">
                     <a href="#di">
                         <cite>다이아프리티</cite>
                         <p>립스틱, 밀키 크림 할인</p>
                     </a>
                 </li>
                 <li>
                     <a href="#mh">
                         <cite>미하이삭스</cite>
                         <p>유튜브 구독 + 좋아요, 1+1 혜택</p>
                     </a>
                 </li>
                 <li class="finish">
                 	<div>
<!--                      <a href="#ra"> -->
                         <cite>레아라 다이아포스</cite>
                         <p>첫구독시 할인</p>
<!--                      </a> -->
					</div>
                 </li>
                 <li class="finish">
                 	<div>
<!--                      <a href="#mhj"> -->
                         <cite>미학주의</cite>
                         <p>설맞이 이벤트 6종</p>
<!--                      </a> -->
					</div>
                 </li>
                 <li class="finish">
                 	<div>
<!--                      <a href="#my"> -->
                         <cite>마이 얼스데이</cite>
                         <p>선착순 100명 손소독제 증정</p>
<!--                      </a> -->
					</div>
                 </li>
                 <li class="finish">
                 	<div>
<!--                      <a href="#br"> -->
                         <cite>브란트</cite>
                         <p>러스크 41% 할인 이벤트</p>
<!--                      </a> -->
					</div>
                 </li>
                 <li class="finish">
                 	<div>
<!--                      <a href="#sw"> -->
                         <cite>성원상회</cite>
                         <p>제품 구매시 선착순 100명</p>
<!--                      </a> -->
					</div>
                 </li>
                 <li class="finish">
                 	<div>
<!--                      <a href="#sn"> -->
                         <cite>스낵24</cite>
                         <p>간식서랍 가격 할인</p>
<!--                      </a> -->
					</div>
                 </li>
                  <li class="finish">
                  	<div>
<!--                      <a href="#se"> -->
                         <cite>씨앤트리</cite>
                         <p>정품 증정 스페셜 이벤트</p>
<!--                      </a> -->
					</div>
                 </li>
                 <li class="finish">
                 	<div>
<!--                      <a href="#hn"> -->
                         <cite>어니스틴</cite>
                         <p>2주차프로모션 기획</p>
<!--                      </a> -->
					</div>
                 </li>
                 <li class="finish">
                 	<div>
<!--                      <a href="#el"> -->
                         <cite>엘레강스펫</cite>
                         <p>가격 할인, 1+1, 증정</p>
<!--                      </a> -->
					</div>
                 </li>
                 <li class="finish">
                 	<div>
<!--                      <a href="#js"> -->
                         <cite>장수김</cite>
                         <p>설 선물세트, 미니김6종 증정</p>
<!--                      </a> -->
					</div>
                 </li>
                 <li class="finish">
                 	<div>
<!--                      <a href="#po"> -->
                         <cite>포미</cite>
                         <p>댄케이크 증정이벤트</p>
<!--                      </a> -->
					</div>
                 </li>
                 <li class="finish">
                 	<div>
<!--                      <a href="#hd"> -->
                         <cite>현대생활식서</cite>
                         <p>‘바로잡곡6종세트’증정</p>
<!--                      </a> -->
					</div>
                 </li>
                 <li class="finish">
<!--                      <a href="#st"> -->
					<div>
                         <cite>스템코</cite>
                         <p>마스크 1+1 특가</p>
                    </div>
<!--                      </a> -->
                 </li>
                 <li class="finish">
<!--                     <a href="#jg"> -->
					<div>
                   <cite>정관장</cite>
                   <p>보력 세트 1+1 특가 이벤트</p>
                   </div>
<!--                     </a> -->
                </li>

	         </ul>
	        <div class="event-tabs-cont" id="jg">
                        <div class="event-txt-area">
                            <cite>
                                정관장 보력 세트 1+1 특가 이벤트
                            </cite>
                            <p>정관장</p>
                            <span>이벤트기간 : 2021/01/20 ~ 재고소진 시</span>
                            <a href="${JG_URL}" class="btn-lg width spot4">바로가기 <i class="ico-arr-r sm spot"></i></a>
                        </div>
                        <div class="event-img-area view-content">
                            <img src="${imgPath}/images/jg1_b2c.jpg" />
                        </div>
                        <div class="btn-area">
                            <a href="${JG_URL}" class="btn-lg width spot">바로가기 <i class="ico-arr-r sm wh"></i></a>
                        </div>
                    </div>
                    <div class="event-tabs-cont is-active" id="di">
                        <div class="event-txt-area">
                            <cite>
                                다이아프리티 키스츄 립스틱 할인  &<br />
                                다이아프리티 톤업 밀키 크림
                            </cite>
                            <p>다이아프리티</p>
                            <span>이벤트기간 : 2021/01/20 ~ 재고소진 시</span>
                            <a href="${DI_URL}" class="btn-lg width spot4">바로가기 <i class="ico-arr-r sm spot"></i></a>
                        </div>
                        <div class="event-img-area view-content">
                            <img src="${imgPath}/images/di1_b2c.jpg" />
                        </div>
                        <div class="btn-area">
                            <a href="${DI_URL}" class="btn-lg width spot">바로가기 <i class="ico-arr-r sm wh"></i></a>
                        </div>
                    </div>
                    <div class="event-tabs-cont" id="ra">
                        <div class="event-txt-area">
                            <cite>
                                프리미엄 하이드로겔 아이패치 골드 60매<br />+ 스노우 60매 + 퍼펙트 아이세럼 골드 30g<br />
                                첫 구독 할인
                            </cite>
                            <p>레아라 다이아포스</p>
                            <span>이벤트기간 : 2021/01/20 ~ 2021/02/10</span>
                            <a href="${RA_URL}" class="btn-lg width spot4">바로가기 <i class="ico-arr-r sm spot"></i></a>
                        </div>
                        <div class="event-img-area view-content">
                            <img src="${imgPath}/images/ra1_b2c.jpg" />
                        </div>
                        <div class="btn-area">
                            <a href="${RA_URL}" class="btn-lg width spot">바로가기 <i class="ico-arr-r sm wh"></i></a>
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
                                        <span>이벤트기간 : 2021/01/20 ~ 2021/02/19</span>
                                        <a href="${MH_URL}" class="btn-lg width spot4">바로가기 <i class="ico-arr-r sm spot"></i></a>
                                    </div>
                                    <div class="event-img-area view-content">
                                        <img src="${imgPath}/images/mh1_b2c.jpg" />
                                    </div>
                                    <div class="btn-area">
                                        <a href="${MH_URL}" class="btn-lg width spot">바로가기 <i class="ico-arr-r wh"></i></a>
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
                                        <span>이벤트기간 : 2021/01/20 ~ 2021/02/19</span>
                                        <a href="${MH_URL}" class="btn-lg width spot4">바로가기 <i class="ico-arr-r sm spot"></i></a>
                                    </div>
                                    <div class="event-img-area view-content">
                                        <img src="${imgPath}/images/mh2_b2c.jpg" />
                                    </div>
                                    <div class="btn-area">
                                        <a href="${MH_URL}" class="btn-lg width spot">바로가기 <i class="ico-arr-r sm wh"></i></a>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="event-tabs-cont" id="mhj">
                        <div class="event-txt-area">
                            <cite>
                                아이두비 설맞이 이벤트 6종
                            </cite>
                            <p>미학주의</p>
                            <span>이벤트기간 : 2021/01/20 ~ 2021/02/10</span>
                            <a href="${MHJ_URL}" class="btn-lg width spot4">바로가기 <i class="ico-arr-r sm spot"></i></a>
                        </div>
                        <div class="event-img-area view-content">
                            <img src="${imgPath}/images/mhj1_b2c.jpg" />
                        </div>
                        <div class="btn-area">
                            <a href="${MHJ_URL}" class="btn-lg width spot">바로가기 <i class="ico-arr-r wh"></i></a>
                        </div>
                    </div>
                    <div class="event-tabs-cont" id="my">
                        <div class="event-txt-area">
                            <cite>
                                오픈기념이벤트<br />
                                선착순 100명 손소독제 증정
                            </cite>
                            <p>마이 얼스데이</p>
                            <span>이벤트기간 : 2021/01/20 ~ 2021/02/10</span>
                            <a href="${MY_URL}" class="btn-lg width spot4">바로가기 <i class="ico-arr-r sm spot"></i></a>
                        </div>
                        <div class="event-img-area view-content">
                            <img src="${imgPath}/images/my1_b2c.jpg" />
                        </div>
                        <div class="btn-area">
                            <a href="${MY_URL}" class="btn-lg width spot">바로가기 <i class="ico-arr-r wh"></i></a>
                        </div>
                    </div>
                     <div class="event-tabs-cont" id="br">
			             <div class="event-txt-area">
			                 <cite>러스크 41% 할인 이벤트</cite>
			                 <p>브란트</p>
			                 <span>이벤트기간 : 2021/01/20 ~ 2021/02/10</span>
			                 <a href="${BR_URL}" class="btn-lg width spot4">바로가기 <i class="ico-arr-r sm spot"></i></a>
			             </div>
			             <div class="event-img-area view-content">
			                 <img src="${CTX_ROOT}/html/SITE_00000/event/210111open2/images/br1_b2c.jpg" />
			             </div>
			             <div class="btn-area">
			                 <a href="${BR_URL}" class="btn-lg width spot">바로가기 <i class="ico-arr-r sm wh"></i></a>
			             </div>
			         </div>   
                    <div class="event-tabs-cont" id="sw">
                        <div class="event-txt-area">
                            <cite>
                                성원상품 제품 구매시<br />
                                선착순 100명 / 체험형 고객 참여가능
                            </cite>
                            <p>성원상회</p>
                            <span>이벤트기간 : 2021/01/20 ~ 2021/02/10</span>
                            <a href="${SW_URL}" class="btn-lg width spot4">바로가기 <i class="ico-arr-r sm spot"></i></a>
                        </div>
                        <div class="event-img-area view-content">
                            <img src="${imgPath}/images/sw1_b2c.jpg" />
                        </div>
                        <div class="btn-area">
                            <a href="${SW_URL}" class="btn-lg width spot">바로가기 <i class="ico-arr-r sm wh"></i></a>
                        </div>
                    </div>
                    <div class="event-tabs-cont" id="sn">
                        <div class="event-txt-area">
                            <cite>
                                간식서랍 가격 할인<br />
                                선착순 30명
                            </cite>
                            <p>스낵24</p>
                            <span>이벤트기간 : 2021/01/20 ~ 2021/02/10</span>
                            <a href="${SN_URL}" class="btn-lg width spot4">바로가기 <i class="ico-arr-r sm spot"></i></a>
                        </div>
                        <div class="event-img-area view-content">
                            <img src="${imgPath}/images/sn1_b2c.jpg" />
                        </div>
                        <div class="btn-area">
                            <a href="${SN_URL}" class="btn-lg width spot">바로가기 <i class="ico-arr-r sm wh"></i></a>
                        </div>
                    </div>
                    <div class="event-tabs-cont" id="st">
                        <div class="event-txt-area">
                            <cite>
                                스템코 마스크 1+1 특가 이벤트<br />
                                29,900원 / 무료배송
                            </cite>
                            <p>스템코</p>
                            <span>이벤트기간 : 2021/01/20 ~ 2021/02/10</span>
                            <a href="${ST_URL}" class="btn-lg width spot4">바로가기 <i class="ico-arr-r sm spot"></i></a>
                        </div>
                        <div class="event-img-area view-content">
                            <img src="${imgPath}/images/st1_b2c.jpg" />
                        </div>
                        <div class="btn-area">
                            <a href="${ST_URL}" class="btn-lg width spot">바로가기 <i class="ico-arr-r sm wh"></i></a>
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
                                        <span>이벤트기간 : 2021/01/20 ~ 2021/02/10</span>
                                        <a href="${SE_URL}" class="btn-lg width spot4">바로가기 <i class="ico-arr-r sm spot"></i></a>
                                    </div>
                                    <div class="event-img-area view-content">
                                        <img src="${imgPath}/images/se1_b2c.jpg" />
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
                                        <span>이벤트기간 : 2021/01/20 ~ 2021/02/10</span>
                                        <a href="${SE_URL}" class="btn-lg width spot4">바로가기 <i class="ico-arr-r sm spot"></i></a>
                                    </div>
                                    <div class="event-img-area view-content">
                                        <img src="${imgPath }/images/se2_b2c.jpg" />
                                    </div>
                                    <div class="btn-area">
                                        <a href="${SE_URL}" class="btn-lg width spot">바로가기 <i class="ico-arr-r sm wh"></i></a>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                    
                    <div class="event-tabs-cont" id="hn">
                        <div class="event-txt-area">
                            <cite>2주차프로모션 기획</cite>
                            <p>어니스틴</p>
                            <span>이벤트기간 : 2021/01/20 ~ 2021/02/10</span>
                            <a href="${HN_URL}" class="btn-lg width spot4">바로가기 <i class="ico-arr-r sm spot"></i></a>
                        </div>
                        <div class="event-img-area view-content">
                            <img src="${imgPath}/images/hn1_b2c.jpg" />
                        </div>
                        <div class="btn-area">
                            <a href="${HN_URL}" class="btn-lg width spot">바로가기 <i class="ico-arr-r sm wh"></i></a>
                        </div>
                    </div>
                    <div class="event-tabs-cont" id="el">
                        <div class="event-tabs-sm">
                            <ul class="event-tabs-nav-sm">
                                <li>
                                    <a href="#el1">
                                        엘레강스펫 샴푸 or 컨디셔너<br />
                                        스테이크큐브 간식 1개 증정
                                    </a>
                                </li>
                                <li>
                                    <a href="#el2">
                                        엘레강스펫 워터리스워시<br />
                                        1,000원 할인 이벤트
                                    </a>
                                </li>
                                <li>
                                    <a href="#el3">
                                        엘레강스펫 이어클리너<br />
                                        1+1이벤트
                                    </a>
                                </li>
                                <li>
                                    <a href="#el4">
                                        엘레강스펫 주변 탈취제<br />
                                        1,000원 할인 이벤트
                                    </a>
                                </li>
                            </ul>
                            <div class="event-tabs-cont-sm-area">
                                <div class="event-tabs-cont-sm is-active" id="el1">
                                    <div class="event-txt-area">
                                        <cite>
                                            엘레강스펫 샴푸 or 컨디셔너<br />
                                            스테이크큐브 간식 1개 증정
                                        </cite>
                                        <p>엘레강스펫</p>

                                        <span>이벤트기간 : 2021/01/20 ~ 2021/02/10</span>
                                        <a href="${EL_URL}" class="btn-lg width spot4">바로가기 <i class="ico-arr-r sm spot"></i></a>
                                    </div>
                                    <div class="event-img-area view-content">
                                        <img src="${imgPath}/images/el1_b2c.jpg" />
                                    </div>
                                    <div class="btn-area">
                                        <a href="${EL_URL}" class="btn-lg width spot">바로가기 <i class="ico-arr-r sm wh"></i></a>
                                    </div>
                                </div>
                                <div class="event-tabs-cont-sm" id="el2">
                                    <div class="event-txt-area">
                                        <cite>
                                            엘레강스펫 워터리스워시<br />
                                            1,000원 할인 이벤트
                                        </cite>
                                        <p>엘레강스펫</p>

                                        <span>이벤트기간 : 2021/01/20 ~ 2021/02/10</span>
                                        <a href="${EL_URL}" class="btn-lg width spot4">바로가기 <i class="ico-arr-r sm spot"></i></a>
                                    </div>
                                    <div class="event-img-area view-content">
                                        <img src="${imgPath}/images/el2_b2c.jpg" />
                                    </div>
                                    <div class="btn-area">
                                        <a href="${EL_URL}" class="btn-lg width spot">바로가기 <i class="ico-arr-r sm wh"></i></a>
                                    </div>
                                </div>
                                <div class="event-tabs-cont-sm" id="el3">
                                    <div class="event-txt-area">
                                        <cite>
                                            엘레강스펫 이어클리너<br />
                                            1+1이벤트
                                        </cite>
                                        <p>엘레강스펫</p>

                                        <span>이벤트기간 : 2021/01/20 ~ 2021/02/10</span>
                                        <a href="${EL_URL}" class="btn-lg width spot4">바로가기 <i class="ico-arr-r sm spot"></i></a>
                                    </div>
                                    <div class="event-img-area view-content">
                                        <img src="${imgPath}/images/el3_b2c.jpg" />
                                    </div>
                                    <div class="btn-area">
                                        <a href="${EL_URL}" class="btn-lg width spot">바로가기 <i class="ico-arr-r sm wh"></i></a>
                                    </div>
                                </div>
                                <div class="event-tabs-cont-sm" id="el4">
                                    <div class="event-txt-area">
                                        <cite>
                                            엘레강스펫 주변 탈취제<br />
                                            1,000원 할인 이벤트
                                        </cite>
                                        <p>엘레강스펫</p>

                                        <span>이벤트기간 : 2021/01/20 ~ 2021/02/10</span>
                                        <a href="${EL_URL}" class="btn-lg width spot4">바로가기 <i class="ico-arr-r sm spot"></i></a>
                                    </div>
                                    <div class="event-img-area view-content">
                                        <img src="${imgPath}/images/el4_b2c.jpg" />
                                    </div>
                                    <div class="btn-area">
                                        <a href="${EL_URL}" class="btn-lg width spot">바로가기 <i class="ico-arr-r sm wh"></i></a>
                                    </div>
                                </div>
                            </div>
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
                                        <span>이벤트기간 : 2021/01/20 ~ 2021/02/10</span>
                                        <a href="${JS_URL}" class="btn-lg width spot4">바로가기 <i class="ico-arr-r sm spot"></i></a>
                                    </div>
                                    <div class="event-img-area view-content">
                                        <img src="${imgPath}/images/js2_b2c.jpg" />
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
                                        <span>이벤트기간 : 2021/01/20 ~ 2021/02/10</span>
                                        <a href="${JS_URL}" class="btn-lg width spot4">바로가기 <i class="ico-arr-r sm spot"></i></a>
                                    </div>
                                    <div class="event-img-area view-content">
                                        <img src="${imgPath}/images/js1_b2c.jpg" />
                                    </div>
                                    <div class="btn-area">
                                        <a href="${JS_URL}" class="btn-lg width spot">바로가기 <i class="ico-arr-r wh"></i></a>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="event-tabs-cont" id="po">
                        <div class="event-txt-area">
                            <cite>댄케이크 증정이벤트</cite>
                            <p>포미</p>
                            <span>이벤트기간 : 2021/01/20 ~ 2021/02/10</span>
                            <a href="${PO_URL}" class="btn-lg width spot4">바로가기 <i class="ico-arr-r sm spot"></i></a>
                        </div>
                        <div class="event-img-area view-content">
                            <img src="${imgPath}/images/po1_b2c.jpg" />
                        </div>
                        <div class="btn-area">
                            <a href="${PO_URL}" class="btn-lg width spot">바로가기 <i class="ico-arr-r sm wh"></i></a>
                        </div>
                    </div>
                    <div class="event-tabs-cont" id="hd">
                        <div class="event-txt-area">
                            <cite>정기구독신청 고객<br />'바로잡곡6종세트'증정</cite>
                            <p>현대생활식서</p>
                            <span>이벤트기간 : 2021/01/20 ~ 2021/02/10</span>
                            <a href="${HD_URL}" class="btn-lg width spot4">바로가기 <i class="ico-arr-r sm spot"></i></a>
                        </div>
                        <div class="event-img-area view-content">
                            <img src="${imgPath}/images/hd1_b2c.jpg" />
                        </div>
                        <div class="btn-area">
                            <a href="${HD_URL}" class="btn-lg width spot">바로가기 <i class="ico-arr-r sm wh"></i></a>
                        </div>
                    </div>
	         <%-- <div class="img-cha">
	             <span><img src="${CTX_ROOT}/html/SITE_00000/event/210111open2/images/img_cha_txt1.svg" /></span>
	             <img src="${CTX_ROOT}/html/SITE_00000/event/210111open2/images/img_cow.jpg" alt="캐릭터" />
	         </div> --%>
	     </div>
	</div>
	<javascript>
		<script src="${CTX_ROOT}/html/SITE_00000/event/210111open2/js/event.js"></script>
		<script src="${CTX_ROOT}/resources/front/cmm/etc/js/kakaoApi.js"></script>
		<script src="${CTX_ROOT}/resources/front/shop/goods/event/js/210118open2.js"></script>
	</javascript>
</body>
</html>