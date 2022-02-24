<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/modoo/common/commonTagLibs.jsp"%>
<div class="sub-tit-area">
	<h3 class="sub-tit">리뷰<em class="review-cnt"></em></h3>
	<div class="fnc-area" id="fnc-area-review">
		<select class="border-none" name="commentSortOrdr">
			<option value="0">평점순</option>
			<option value="1">최신순</option>
		</select>
		<c:if test="${isLogin == true and hasOrdered == true and hasRegistered == false}">
			<button type="button" class="btn spot2" id="review-regist" data-popup-open="reviewWrite">작성하기</button>
		</c:if>
	</div>
</div>
<div class="review-grade">
	<span class="grade">
		<span class="grade-per" id="score-avg" style="width:00%;"></span>
	</span>
	<p>사용자 평점 <strong>0.0</strong></p>
</div>
<ul class="review-list">
</ul>
<ul class="paging" id="review-paging">
</ul>
<c:import url="/shop/goods/review/reviewView.do" charEncoding="utf-9"/>
<c:import url="/shop/goods/review/reviewWrite.do" charEncoding="utf-9"/>