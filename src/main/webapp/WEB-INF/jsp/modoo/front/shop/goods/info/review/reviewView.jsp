<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/modoo/common/commonTagLibs.jsp"%>
<!-- 포토리뷰 -->
<div class="popup-review" data-popup="reviewView">
<div class="review-img-area">
	<figure class="img-area" id="review-detail-img">
		<img src="../../../resources/front/site/SITE_00000/image/common/img_none.svg" alt="이미지 없음" />
	</figure>
    <div class="img-list">
        <div class="swiper-container">
            <ul class="swiper-wrapper" id="swiper-wrapper-review">
            </ul>
        </div>
        <button type="button" class="swiper-button-next"></button>
        <button type="button" class="swiper-button-prev"></button>
    </div>
</div>
<div class="pop-area">
    <div class="pop-header">
        <h1>리뷰 상세보기</h1>
        <button type="button" class="btn-pop-close" data-popup-close="reviewView">닫기</button>
    </div>
    <div class="pop-body">
        <div class="review-txt-area" id="review-detail-contents">
        </div>
    </div>
</div>
</div>
