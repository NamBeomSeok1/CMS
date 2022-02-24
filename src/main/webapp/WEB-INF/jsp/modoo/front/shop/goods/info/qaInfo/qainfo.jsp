<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/modoo/common/commonTagLibs.jsp"%>

	<c:import url="/shop/goods/qainfo/qaInfoList.do">
		<c:param name="goodsId" value="${goods.goodsId}"/>
	</c:import>

	<div class="popup" data-popup="qaWrite">
	   <div class="pop-header">
			<h1>문의하기</h1>
			<button type="button" class="btn-pop-close" data-popup-close="qaWrite">닫기</button>
		</div>
		<div class="pop-body">
		<form:form modelAttribute="qaInfo" id="qaInfoReg"  method="post" class="qaInfoForm" enctype="multipart/form-data" enaccept-charset="utf-8" action="${actionUrl}">
			<form:hidden path="pageIndex" value=""/>
			<form:hidden path="goodsId" value="${goods.goodsId}"/>
			<form:hidden path="wrterNm" value="${USER_NAME}"/>
			<form:hidden path="wrterId" value="${USER_ID}"/>
			<form:hidden path="qestnSj" value="[상품문의] ${USER_ID}의 ${goods.goodsNm}대한 질문"/>
			<form:hidden path="qaId" value=""/>
			<!-- <h2 class="pop-tit">상품판매자 델몬트에게 문의하기</h2> -->
			<dl class="write-area">
				<dt>질문유형</dt>
				<dd>
					<form:select path="qestnTyCode" title="유형선택">
						   <form:option value="" disabled="true" >유형을 선택해주세요.</form:option>
						<c:forEach var="list" items="${qestnTyCodeList}">
						   <form:option value="${list.code}">${list.codeNm}</form:option>
						</c:forEach>
					</form:select>
				</dd>
				<dt>연락처</dt>
				<dd>
					<div class="table-area">
						<input type="number" id="qaTelno1" name="telno1" maxlength="3">
						<input type="number" id="qaTelno2" name="telno2" maxlength="4">
						<input type="number" id="qaTelno3" name="telno3" maxlength="4">
					</div>
						<p class="msg mt10">※ 답변완료시 입력하신 연락처로  알림이 발송됩니다.</p>
				</dd>
				<dt>질문내용</dt>
				<dd>
					<div class="txt-count-area">
						<form:textarea path="qestnCn" rows="6" placeholder="최소 10자 이상 입력해주세요." maxlength="500"></form:textarea>
						<div class="txt-count"><span>0</span> / 500</div>
					</div>
				</dd>
			</dl>
			<div class="agree-area">
				<label><form:checkbox path="secretAt" value="Y"/> 비밀글로 등록하기</label>
				<%-- <label><form:checkbox path="" value="Y"/> 문자로 받기</label> --%>
			</div>
			<ul class="bullet-sm">
				<li>질문에 대한 답변은 해당 페이지 및  마이구독 &gt; 게시판 &gt; Q&amp;A 에서 확인 하실 수 있습니다.</li>
				<li>상품과 무관하거나 비방,광고, 개인정보 노출 위험이 있는 글 등 Q&amp;A 운영 정책에 어긋나는 질문은 별도의 통보없이 노출 제한 될 수 있습니다.</li>
			</ul>
			<div class="btn-table-area">
				<!-- <button type="button" class="btn-lg" data-popup-close="qaWrite">취소</button> -->
				<button type="button" class="btn-lg spot submitBtn">확인</button>
			</div>
			</form:form>
		</div>
	</div>
