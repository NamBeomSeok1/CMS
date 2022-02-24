<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/modoo/common/commonTagLibs.jsp"%>
<div class="popup" data-popup="inquiryRegist">
        <div class="pop-header">
            <h1>입점문의</h1>
            <button type="button" class="btn-pop-close" data-popup-close="inquiryRegist">닫기</button>
        </div>
        <div class="pop-body">
        <c:if test="${!fn:contains(USER_ID,'100')}">
         	<p class="msg mb20"> FOXEDU STORE 입점 시, 이지웰 복지몰 입점 기회도 제공됩니다. </p>
         </c:if>
    	<form id="inqryForm" action="${CTX_ROOT}/inqryReg.json" method="post" enctype="multipart/form-data">
            <dl class="write-area">
                <dt>회사명</dt>
                <dd>
                    <input type="text" name="cmpnyNm"/>
                </dd>
                <dt>회사소개</dt>
                <dd>
                    <textarea rows="6" name="cmpnyIntrcn" maxlength="500"></textarea>
                </dd>
                <dt>상품소개</dt>
                <dd>
                    <textarea rows="6" name="goodsIntrcn" maxlength="500"></textarea>
                </dd>
                <dt>소개 첨부파일</dt>
                <dd>
                   <div class="file">
						<label for="inqryAtchFile" class="btn-file">파일 첨부하기</label>
						<input type="file" id="inqryAtchFile" onchange="addFile(this);"  name="atchFile"  class="file-hide fileAdd" multiple="multiple">
					</div>
					<div id="inqryAtchFile-list">
						<ul>
						</ul>
					</div>
                </dd>
                <dt>담당자</dt>
                <dd><input type="text" name="cmpnyCharger"/></dd>
                <dt>연락처</dt>
                <dd>
                    <div class="table-area">
                        <input type="number" name="telno1" class="q0" maxlength="3" />
                        <input type="number" name="telno2" class="q0" maxlength="4" />
                        <input type="number" name="telno3" class="q0" maxlength="4" />
                    </div>
                </dd>
                <dt>이메일</dt>
                <dd>
                    <div class="email">
                        <input type="text" name="email1"/>
                        <span>@</span>
                        <input type="text" name="email2"/>
                    </div>
                </dd>
            </dl>
            <p class="msg mb20">※ 담당자 확인 후 연락드리겠습니다.</p>	
            <div class="btn-table-area">
                <button type="button" class="btn-lg" onclick="popClose('inquiryRegist')">취소</button>
                <button type="button" class="btn-lg spot" id="inqryRegBtn">확인</button>
            </div>
   		</form>
        </div>
    </div>
</body>
</html>
