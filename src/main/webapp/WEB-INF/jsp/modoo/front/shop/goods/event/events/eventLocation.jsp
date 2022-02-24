<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/modoo/common/commonTagLibs.jsp"%>
			
	 <div class="location">
        <a href="/index.do"><strong>HOME</strong></a>
        <a href="${CTX_ROOT}/shop/event/goodsEventList.do">이벤트</a>
        <c:if test="${not empty param.eventNm}">
        	<span>${param.eventNm}</span>
        </c:if>
        </div>
        <c:if test="${param.isEventHome ne 'true' && not empty param.eventNm}">
	        <div class="event-fnc">
	          <span class="date"></span>
	          <div class="layerpopup-sm-area share-area">
	              <button type="button" class="btn-layerpopup-sm"><i class="ico-share"></i><span>공유하기</span></button>
	              <div class="layerpopup-sm">
	                  <div class="pop-body">
	                      <div class="share">
                        	<button type="button" id="kakaoShare" data-title='${param.eventNm}' data-description='FOXEDU STORE 이벤트' data-link='${BASE_URL}${param.url}' data-imageurl='${param.imgUrl}'><i class="ico-sns-kakao-spot lg"></i><span class="txt-hide">카카오톡</span></button>
							<button type="button" id="facebookShare" data-title='${param.eventNm}'  data-link='${BASE_URL}${param.url}' data-imageurl='${param.imgUrl}'><i class="ico-sns-facebook-spot lg"></i><span class="txt-hide">페이스북</span></button>
							<button type="button" id="twitterShare" data-title='${param.eventNm}'  data-link='${BASE_URL}${param.url}' data-imageurl='${param.imgUrl}'><i class="ico-sns-twitter-spot lg"></i><span class="txt-hide">트위터</span></button>
							<button type="button" id="naverShare" data-title='${param.eventNm}'  data-link='${BASE_URL}${param.url}' data-imageurl='${param.imgUrl}'><i class="ico-sns-naverblog-spot lg"></i><span class="txt-hide">네이버블로그</span></button>
							<button type="button" id="clipBoard" onclick="urlClipCopy('${BASE_URL}${param.url}');"><i class="ico-sns-url-spot lg"></i><span class="txt-hide">URL</span></button>
	                      </div>
	                  </div>
	                  <button type="button" class="btn-close">닫기</button>
	              </div>
	          </div>
	      </div>
      </c:if>

