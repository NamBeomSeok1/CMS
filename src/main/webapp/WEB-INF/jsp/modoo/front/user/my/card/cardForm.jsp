<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ include file="/WEB-INF/jsp/modoo/common/commonTagLibs.jsp"%>

	<!-- 카드 등록폼 -->
	<div class="popup" data-popup="cardWrite">
		<div class="pop-header">
			<h1>카드정보</h1>
			<button type="button" class="btn-pop-close" data-popup-close="cardWrite">닫기</button>
		</div>
		<div class="pop-body">
		<form id="cardForm" name="cardForm" method="post" action="/card/cardReg.json">
			<dl class="write-area">
				<dt>카드번호</dt>
				<dd>
					<div class="card-num-area focus-auto border-input-area">
						<input type="number" name="cardNo" maxlength="4" autocomplete="one-time-code" placeholder="0000" title="카드번호 첫번째 입력" />
						<input type="number" name="cardNo" maxlength="4" autocomplete="one-time-code" placeholder="0000" title="카드번호 두번째 입력" />
						<input type="password" name="cardNo" class="pw" maxlength="4" placeholder="0000" autocomplete="new-password" title="카드번호 세번째 입력" />
						<input type="password" name="cardNo" class="pw" maxlength="4" placeholder="0000" autocomplete="new-password" title="카드번호 네번째 입력" />
					</div>
				</dd>
			 	 <dt class="txt-hide">유효기간/카드비밀번호</dt>
               	 <dd class="table-area">
	               	 <div>
	               	 	<cite class="write-tit">유효기간</cite>
						<div class="focus-auto border-input-area">
							<input type="password" name="cardUsgpd" class="pw ac" maxlength="2" autocomplete="new-password"  placeholder="MM" title="월입력(MM)" />
							/
							<input type="password" name="cardUsgpd" class="pw ac" maxlength="2"  autocomplete="new-password" placeholder="YY" title="연입력(YY)" />
						</div>
	               	 </div>
					<div>
						<cite class="write-tit">카드비밀번호</cite>
						<input type="password" name="cardPassword"  class="pw p10 al" maxlength="2" autocomplete="new-password" placeholder="비밀번호 앞 2자리 숫자" title="비밀번호 앞 2자리 숫자" />
					</div>
				</dd>
				 <dt class="txt-hide">간편비밀번호</dt>
	                <dd class="table-area">
	                    <div>
	                        <cite class="write-tit">간편비밀번호</cite>
	                        <input type="password" id="password1" class="p10" autocomplete="new-password"  placeholder="6자리" maxlength="6" />
	                    </div>
	                    <div>
	                        <cite class="write-tit">간편비밀번호 확인</cite>
	                        <input type="password" id="password2" class="p10" name="password" autocomplete="new-password" placeholder="6자리" maxlength="6" />
	                    </div>
	                </dd>
				<dt>카드구분</dt>
				<dd >
					<label><input type="radio" checked="checked" name="kind" data-kind="first" />개인카드</label>
					<label><input type="radio"  name="kind" data-kind="second"/>법인카드</label>
				</dd>
				<dt id="brthdy">생년월일</dt>
				<dd>
					<input type="number" name="brthdy" maxlength="6" autocomplete="off" class="p10 al" placeholder="6자리 입력" title="6자리 입력" />
				</dd>
				<dt style="display:none;" id="bizrno">사업자등록번호</dt>
				<dd style="display:none;">
				</dd>
				<dt>카드이름</dt>
				<dd>
					<input type="text" name="cardNm"  class="p10 al" placeholder="카드 이름" title="카드 이름" />
				</dd>
			</dl>
			<div class="agree-area">
				<label><input type="checkbox" name="bassUseAt" value="Y"/>기본 카드로 설정</label>
			</div>
			<div class="agree-all-area">
				<label><input type="checkbox" id="allStplat" value="Y" /><strong>전체 약관 동의</strong></label>
				<ul class="bg-box bullet-none">
					<li>
						<label><input type="checkbox" class="stplat" id="indvdlStplat"/>FOXEDU STORE 개인(신용)정보 수집 및 이용 동의</label>
						<a href="#none" onclick="popOpen2('termsPrivacy')" class="fc-gr fs-sm">보기 <i class="ico-arr-r sm back gr "></i></a>
					</li>
					<li>
						<label><input type="checkbox" class="stplat" id="cardStplat" />FOXEDU STORE · 카드사 간 개인(신용)정보 제공 동의</label>
						<a href="#none"  onclick="popOpen2('termsCard')" class="fc-gr fs-sm">보기 <i class="ico-arr-r sm back gr "></i></a>
					</li>
				</ul>
			</div>
			<div class="btn-table-area">
				<button type="submit" class="btn-lg spot width">완료</button>
			 </div>
		   </form>	
		</div>
	</div>
		
	 <!--카드 수정폼  -->
	 <div class="popup" data-popup="cardEdit">
		<div class="pop-header">
			<h1>카드관리</h1>
			<button type="button" class="btn-pop-close" data-popup-close="cardEdit">닫기</button>
		</div>
	   	<form id="cardUpdate" name="cardUpdate" method="post" action="/card/cardUpdate.json">
   			 <div class="pop-body">
				<input type="hidden" name="cardId" id="cardId" value=""/>
				<h2 class="pop-tit" id="lastCardNo"></h2>
				<dl class="write-area">
					<dt>카드 이름 수정</dt>
					 <dd>
						<input id="cardNm" name="cardNm" type="text" />
					</dd>
				</dl>
				<div class="agree-area">
					<label><input name="bassUseAt" id="bassUseAt" type="checkbox" value="Y"/>기본 카드로 설정</label>
				</div>
				<div class="agree-area">
					<label><input id="pwdChangeBtn" type="checkbox" />카드 비밀번호 변경</label>
				</div>
				 <dl class="write-area">
					<dt class="prevPassword" style="display:none;">이전 비밀번호</dt>
					 <dd class="prevPassword" style="display:none;">
						<input id="prevPassword"  placeholder="6자리" maxlength='6' type="password" />
						<button id="passwordChkBtn"  class="btn">확인</button>
					</dd>
					<dd class="table-area modPassword-area" style="display:none;">
	                    <div>
	                        <cite class="write-tit">간편 비밀번호</cite>
	                        <input type="password" id="password-mod1" class="p10" autocomplete="new-password" placeholder="6자리" maxlength="6">
	                    </div>
	                    <div>
	                        <cite class="write-tit">간편비밀번호 확인</cite>
	                        <input type="password" id="password-mod2" class="p10" name="password" autocomplete="new-password" placeholder="6자리"  maxlength="6">
	                    </div>
	                </dd>
				</dl>
				<div class="btn-table-area">
					<!-- <button type="button" class="btn-lg" data-popup-close="cardEdit">카드 삭제하기</button> -->
					<button type="submit" class="btn-lg spot" data-popup-close="cardEdit">저장</button>
				</div>
			</div>
	   	</form>
	</div>
	 <!-- 비밀번호 확인 폼 -->
	 <div class="popup" data-popup="passwordChk">
		<div class="pop-header">
			<h1>비밀번호 확인</h1>
			<button type="button" class="btn-pop-close" data-popup-close="passwordChk">닫기</button>
		</div>
   			 <div class="pop-body">
				<input type="hidden" name="cardId" id="cardId" value=""/>
				<dl class="write-area changeArea">
					 <dt>비밀번호</dt>
					 <dd>
					 	<p class="msg">삭제할 카드의 비밀번호 숫자 6자리를 입력해주세요.</p>
						<input id="passwordChk" type="password" class="p10" maxlength="6" />
					</dd>
				</dl>
				<div class="btn-table-area">
					<button type="button" class="btn-lg spot" id="delPasswordChkBtn">확인</button>
				</div>
			</div>
		</div> 
	
	<!--카드 목록 폼-->
	<div class="popup w1200" data-popup="cardSubscribeEdit">
        <div class="pop-header">
            <h1>정기결제 카드변경​</h1>
            <button type="button" class="btn-pop-close" data-popup-close="cardSubscribeEdit">닫기</button>
        </div>
        <div class="pop-body">
            <p class="mb20"><strong>결제카드</strong class="payedCard">: 신한카드(1123)</p>
            <div class="pop-tit-area">
                <h3 class="pop-tit">정기결제 카드 변경</h3>
                <div class="fnc-area">
                    <button type="button" onclick="popOpen2('cardWrite')" class="btn">카드 등록하기</button>
                </div>
            </div>
            <div class="list-type">
                <table>
                    <caption>정기결제 카드 변경</caption>
                    <thead>
                        <tr>
                            <th scope="col">카드(카드번호)</th>
                            <th scope="col">카드 이름</th>
                            <th scope="col">관리</th>
                        </tr>
                    </thead>
                    <tbody class="card-list">
                    </tbody>
                </table>
            </div>
        </div>
    </div>

	<template>
	<c:import url="/embed/termsPopup.do" charEncoding="utf-8">
	</c:import>
	</template>
	
