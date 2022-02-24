<%@ include file="/WEB-INF/jsp/modoo/common/commonTagLibs.jsp"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<div class="sub-tit-area">
	<!-- <h3 class="sub-tit">내 정보보기</h3> -->
	<!--  <div class="fnc-area">
	     <a href="/user/sign/logout.do" class="fc-gr">로그아웃 <i class="ico-arr-r gr back sm"></i></a>
	 </div> -->
</div>
<section class="user-info-area">
	<div class="user-img"></div>
	<div class="info-txt-area">
		<h3>
			<strong>${USER_NAME}님</strong>(${USER_ID })
		</h3>
		<!-- <a href="#none" class="btn-setting-gr"><span class="txt-hide">setting</span></a> -->
		<div class="fnc-area">
			<a href="/user/my/myInfo.do">내 정보보기 <i class="ico-arr-r sm back gr"	aria-hidden="true"></i></a>
		</div>
	</div>
</section>
