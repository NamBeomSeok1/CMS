<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/modoo/common/commonTagLibs.jsp"%>
<div class="popup w800" data-popup="deliveryEdit" id="deliveryEdit">
    <div class="pop-header">
        <h1>배송지 변경</h1>
        <button type="button" class="btn-pop-close" data-popup-close="deliveryEdit">닫기</button>
    </div>
    <div class="pop-body">
        <div class="tabs">
            <ul>
                <li><a href="#deliveryEdit1" onclick="getMyAddressList(currentPageAddressPop);">기본 배송지</a></li>
                <li><a href="#deliveryEdit2">신규입력</a></li>
            </ul>
            <div id="deliveryEdit1">
                <ul class="check-list" id="address-list">

                </ul>
                <ul class="paging">
                </ul>
            </div>
            <div id="deliveryEdit2">
                <div class="write-type border-none">
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
                                <td><input type="text" class="p10" placeholder="예)집, 회사" id="dlvyAdresNm" /></td>
                            </tr>
                            <tr>
                                <th scope="row" class="required">수령인</th>
                                <td><input type="text" class="p10" placeholder="이름을 입력하세요." id="dlvyUserNm" /></td>
                            </tr>
                            <tr>
                                <th scope="row" class="required">연락처</th>
                                <td>
                                	<input type="number" class="p0" maxlength="3" id="telno1"/>
                                	<input type="number" class="p0" maxlength="4" id="telno2"/>
                                	<input type="number" class="p0" maxlength="4" id="telno3"/>
                                </td>
                            </tr>
                            <tr>
                                <th scope="row" class="required">배송주소</th>
                                <td>
                                    <input type="text" class="p5" disabled id="dlvyZip"/>
                                    <button type="button" class="btn spot2" id="findAddress">주소 찾기</button>
                                    <input type="text" class="p10" disabled id="dlvyAdres" />
                                    <input type="text" class="p10" placeholder="상세주소를 입력해주세요" id="dlvyAdresDetail" />
                                    <div class="block">
                                        <label><input type="checkbox" id="bassDlvyAt" value="N"/> 기본 배송지로 설정</label>
                                    </div>
                                </td>
                            </tr>
                        </tbody>
                    </table>
                </div>
                <ul class="bullet"> 
                    <li>입력하신 배송지는 배송지 목록에 저장됩니다.</li>
                </ul>
            </div>
        </div>
        <div class="btn-area">
            <button type="button" class="btn-lg width closeBtn" data-popup-close="deliveryEdit">닫기</button>
            <button type="button" class="btn-lg spot width" data-popup-close="deliveryEdit" id="registAddress">저장</button>
        </div>
    </div>
</div>
<!-- 주소 팝업 -->
<div class="popup" data-popup="adress" id="adress">
	<div class="pop-header">
	<h1>주소 찾기</h1>
	<button type="button" class="btn-pop-close" data-popup-close="adress">닫기</button>
	</div>
	<div class="pop-body">
	<form name="form" id="form" method="post">
	<input type="hidden" name="currentPage" value="1"/> 
	<input type="hidden" name="countPerPage" value="5"/>
	<input type="hidden" name="resultType" value="json"/>  
	<input type="hidden" name="confmKey" value="U01TX0FVVEgyMDIxMDExNDEwMTM1MDExMDY4ODQ="/>
	<div class="sch-area lg">
	<input type="text" name="keyword" value="" onkeydown="enterSearch();" placeholder="주소를 입력해주세요." />
	<button type="submit" class="btn-sch"></button>
	</div>
	</form>
	<p style="display:none;" id="addTotalCnt" class="mb10">총 <em></em>건</p>
	<p style="display:none;" id="message" class="mb10">주소를 상세히 입력해 주시기 바랍니다.</p>
	<ul class="border-list">
	</ul>
	<ul class="paging">
	</ul>
	</div>
</div>
