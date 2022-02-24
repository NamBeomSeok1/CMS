<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/modoo/common/commonTagLibs.jsp"%>
    <div class="site-body" role="main">
        <div class="contents" id="contents">
        	<div class="wrap">
	            <div class="sub-contents">
	                <div class="location">
	                    <a href="none"><strong>HOME</strong></a>
	                    <a href="none">마이페이지</a>
	                    <a href="none">주문관리</a>
	                    <a href="none">주문확인</a>
	                </div>
	                <h2 class="sub-tit">구독 변경</h2>
	                <section>
	                    <div class="sub-tit-area">
	                        <h3 class="txt-hide">구독상세</h3>
	                    </div>
	                    <div class="table-type">
	                        <div class="thead">
	                            <cite class="col-w200">주문번호</cite>
	                            <cite>상품정보</cite>
	                            <cite>주문정보</cite>
	                            <cite class="col-w200">결제금액</cite>
	                        </div>
	                        <div class="tbody">
	                        	<!-- 
	                            <div class="col-w200">
	                                2020072201234
	                            </div>
	                            <div class="al m-col-block">
	                                <a href="#none">
	                                    <div class="thumb-area lg">
	                                        <figure><img src="../../../resources/front/site/SITE_00000/image/temp/thumb11.jpg" alt="델몬트 허니글로우 패키지 01" /></figure>
	                                        <div class="txt-area">
	                                            <cite>델몬트</cite>
	                                            <h2 class="tit">[구독중] 델몬트 허니글로우 패키지 01</h2>
	                                            <p class="fc-gr">1회차 / 배송전</p>
	                                        </div>
	                                    </div>
	                                </a>
	                            </div>
	                            <div class="al m-col-block">
	                                <ul class="option-info">
	                                    <li>
	                                        <strong>구독주기 :</strong>
	                                        <span>1주</span>
	                                    </li>
	                                    <li>
	                                        <strong>구독요일 :</strong>
	                                        <span>화요일</span>
	                                    </li>
	                                </ul>
	                                <ul class="option-info">
	                                    <li>
	                                        <cite>수량 :</cite>
	                                        <span>1개</span>
	                                    </li>
	                                </ul>
	                                <ul class="option-info">
	                                    <li>
	                                        <cite>기본옵션 :</cite>
	                                        <span>냄비(+600원)</span>
	                                    </li>
	                                    <li>
	                                        <cite>첫구독옵션 :</cite>
	                                        <span>냄비(+600원)</span>
	                                    </li>
	                                    <li>
	                                        <cite>추가옵션 :</cite>
	                                        <span>냄비(+6000원)</span>
	                                    </li>
	                                </ul>
	                                <ul class="option-info">
	                                    <li>
	                                        <cite>업체요청사항</cite>
	                                        <span>1.섭취연령: 5세, 13세 2.알레르기 반응: 없음 3.기타요청사항: 없음</span>
	                                    </li>
	                                </ul>
	                                <button type="button" class="btn-option-open" data-popup-open="optionEdit">주문정보변경 <i class="ico-arr-r gr sm back"></i></button>
	                            </div>
	                            <div class="col-w200 m-col-block">
	                                <div class="price">
	                                    <strong><span>29,000</span>원</strong>
	                                </div>
	                            </div>
	                             -->
	                        </div>
	                    </div>
	                </section>
	                <section>
	                    <div class="sub-tit-area">
	                        <h3 class="sub-tit">배송정보</h3>
	                    </div>
	                    <div class="write-type">
	                        <p class="txt ar"><i class="required"></i> 필수입력</p>
	                        <table>
	                            <caption>신규 입력</caption>
	                            <colgroup>
	                                <col style="width:150px" />
	                                <col />
	                            </colgroup>
	                            <tbody>
	                                <tr>
	                                    <th scope="row">배송지명</th>
	                                    <td><input type="text" class="p10" placeholder="예)집, 회사" /></td>
	                                </tr>
	                                <tr>
	                                    <th scope="row" class="required">수령인</th>
	                                    <td><input type="text" class="p10" placeholder="이름을 입력하세요." /></td>
	                                </tr>
	                                <tr>
	                                    <th scope="row" class="required">연락처</th>
	                                    <td><input type="number" class="p0" maxlength="3" /><input type="number" class="p0" maxlength="4" /><input type="number" class="p0" maxlength="4" /></td>
	                                </tr>
	                                <tr>
	                                    <th scope="row" class="required">배송주소</th>
	                                    <td>
	                                        <input type="text" class="p5" disabled /><button type="button" class="btn spot2">주소 찾기</button>
	                                        <input type="text" class="p10" disabled />
	                                        <input type="text" class="p10" />
	                                        <div class="block">
	                                            <label><input type="checkbox" /> 기본 배송지로 설정</label>
	                                        </div>
	                                    </td>
	                                </tr>
	                                <tr>
	                                    <th scope="row" class="required">배송메세지</th>
	                                    <td>
	                                        <select title="배송메세지 선택" class="p10">
	                                            <option>배송 전에 미리 연락 바랍니다.</option>
	                                        </select>
	                                        <!--<textarea rows="3"></textarea>-->
	                                    </td>
	                                </tr>
	                            </tbody>
	                        </table>
	                    </div>
	                </section>
	            </div>
	        </div>
        </div><!-- //contents -->
        <a href="#" class="btn-top"><span class="txt-hide">처음으로</span></a>
    </div><!-- //site-body -->

</body>
</html>