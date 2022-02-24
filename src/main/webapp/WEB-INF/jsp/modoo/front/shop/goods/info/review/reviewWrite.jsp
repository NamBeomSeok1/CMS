<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/modoo/common/commonTagLibs.jsp"%>
		<!-- 리뷰 팝업  -->
		<div class="popup" data-popup="reviewWrite">
			<div class="pop-header" id="review-pop-title">
				<h1>리뷰작성</h1>
				<button type="button" class="btn-pop-close" data-popup-close="reviewWrite" class="commentClose">닫기</button>
			</div>
			<div class="pop-body">
				<div class="thumb-area" id="thumb-area-goods-info">
					<figure><img src="../../../resources/front/site/SITE_00000/image/temp/thumb11.jpg" alt="델몬트 허니글로우 패키지 01" /></figure>
					<div class="txt-area">
						<h2 class="tit">델몬트 허니글로우 패키지 01</h2>
						<div class="price"><strong><span>8900</span>원</strong></div>
					</div>
				</div>
				<div class="grade-check-area">
					<p>상품은 만족하셨나요?</p>
					<div class="grade-check">
						<button type="button" data-val="1"><span class="txt-hide">1점</span></button>
						<button type="button" data-val="2"><span class="txt-hide">2점</span></button>
						<button type="button" data-val="3"><span class="txt-hide">3점</span></button>
						<button type="button" data-val="4"><span class="txt-hide">4점</span></button>
						<button type="button" data-val="5"><span class="txt-hide">5점</span></button>
					</div>
				</div>
				<dl class="write-area">
					<dt>어떤 점이 좋았나요?</dt>
					<dd>
						<div class="txt-count-area">
							<textarea rows="6" placeholder="최소 10자 이상 입력해주세요." maxlength="500" id="commentCn"></textarea>
							<div class="txt-count"><span>0</span> / 500</div>
						</div>
					</dd>
				</dl>
				<div class="file-area">
					<ul class="file-add" id="review-file">
					</ul>
					<div class="file">
						<label for="fileAdd" class="btn-file">사진<!--  / 동영상 --> 첨부하기</label>
						<input type="file" name="atchFile" id="fileAdd" class="file-hide" accept=".gif, .jpg, .png, .bmp" multiple="multiple">
					</div>
					<p class="fc-gr fs-sm">상품과 무관한 사진<!--/동영상-->을 첨부한 리뷰는 통보없이 삭제 및 적립 혜택이 회수될 수 있습니다.</p>
				</div>
				<c:if test="${IS_ADMIN eq 'Y'}">
					<div class="agree-area">
						<label>작성자 아이디</label>
	<!-- 					<label><input type="checkbox" /> 리뷰 등록시 옵션정보를 노출하지 않습니다.</label> -->
						<input type="text" name="wrterId"/>
					</div>
				</c:if>
				<div class="btn-table-area">
					<!-- <button type="button" class="btn-lg" data-popup-close="reviewWrite" class="commentClose">취소</button> -->
					<button type="button" class="btn-lg spot" data-popup-close="reviewWrite" id="commentReg">등록하기</button>
				</div>
			</div>
		</div>