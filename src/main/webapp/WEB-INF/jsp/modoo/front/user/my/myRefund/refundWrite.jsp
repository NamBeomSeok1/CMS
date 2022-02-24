<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/modoo/common/commonTagLibs.jsp"%>
<div class="popup" data-popup="reFundWrite" style="display:hidden;">
<div class="pop-header">
    <h1>교환/반품 접수</h1>
    <button type="button" class="btn-pop-close" data-popup-close="reFundWrite">닫기</button>
</div>
<div class="pop-body">
    <dl class="write-area">
        <dt>문의유형</dt>
        <dd>
            <select title="유형선택" name="qaSeCode">
                <option value="">문의 유형을 선택해주세요.</option>
                <option value="EX">교환</option>
                <option value="RF">반품</option>
            </select>
        </dd>
        <dt>문의내용</dt>
        <dd>
            <div class="txt-count-area">
                <textarea id="qestnCn" rows="6" placeholder="최소 10자 이상 입력해주세요." maxlength="5000"></textarea>
                <div class="txt-count"><span>0</span> / 5,000</div>
            </div>
        </dd>
    </dl>
    <div class="file-area">
        <ul class="file-add" id="file-refund">
        </ul>
        <div class="file">
            <label for="fileAdd" class="btn-file">사진<!-- / 동영상-->  첨부하기 <span>(선택사항)</span></label>
            <input type="file" id="fileAdd" class="file-hide" accept=".gif, .jpg, .png, .bmp" multiple="multiple">
        </div>
        <ul class="bullet sm">
            <li>사진 첨부는 5MB 미만의 jpg, png 파일만 등록 가능하며, 최대 3개까지 등록 가능합니다.</li>
            <li>개인정보 관련 피해 방지를 위해 이름, 연락처, 주소 등 개인정보가 노출되지 않도록 주의해주시기 바랍니다.</li>
        </ul>
    </div>
<!--     <dl class="write-area"> -->
<!--         <dt>답변알림</dt> -->
<!--         <dd class="agree-area"> -->
<!--             <label><input type="checkbox" />문자</label> -->
<!--             <label><input type="checkbox" />이메일</label> -->
<!--         </dd> -->
<!--     </dl> -->
    <div class="btn-table-area">
        <button type="button" class="btn-lg" data-popup-close="reFundWrite">취소</button>
        <button type="submit" class="btn-lg spot" id="refund-regist" data-popup-close="reFundWrite">등록하기</button>
    </div>
</div>
</div>