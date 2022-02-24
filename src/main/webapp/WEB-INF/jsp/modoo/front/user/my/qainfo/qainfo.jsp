<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 <%@ include file="/WEB-INF/jsp/modoo/common/commonTagLibs.jsp"%>
<!DOCTYPE html>
<html>
<head><title>${title}</title></head>
<body>

<div class="wrap">
	<c:choose>
		<c:when test="${qaSeCode eq 'GOODS'}">
			<c:set var="mode" value="goods"/>	
			<c:set var="title" value="Q&amp;A"/>	
			<c:import url="/user/my/subMenu.do" charEncoding="utf-8">
				<c:param name="menuId" value="bbs_goodsQna"/>
			</c:import>
		</c:when>
		<c:when test="${qaSeCode eq 'SITE'}">
			<c:set var="mode" value="site"/>	
			<c:set var="title" value="1:1 문의"/>
			<c:import url="/user/my/subMenu.do" charEncoding="utf-8">
				<c:param name="menuId" value="cs_siteQna"/>
			</c:import>	
		</c:when>
	</c:choose>
	
	<div class="sub-contents">
		<c:import url="/user/my/myLocation.do" charEncoding="utf-8">
			<c:param name="menuId" value="bbs"/>
			<c:param name="subMenuId" value="${title}"/>
		</c:import> 
		 <c:import url="/user/my/userInfo.do" charEncoding="utf-8">
		</c:import> 
                <c:if test="${mode eq 'site'}">
	                <section>
	                    <div class="sub-tit-area">
	                        <h3 class="sub-tit">${title}현황</h3>
		                        <div class="fnc-area">
		                            <button type="button" class="btn qaRegBtn">1:1 문의하기</button>
		                        </div>
		                    </div>
		                    <div class="list-type">
		                        <table>
		                            <caption>배송현황 리스트</caption>
		                            <thead>
		                                <tr>
		                                    <th scope="col">총 문의</th>
		                                    <th scope="col">답변 대기</th>
		                                    <th scope="col">답변 완료</th>
		                                </tr>
		                            </thead>
		                            <tbody>
		                                <tr>
		                                    <td><strong class="fs-lg ff-din">${waitCnt+completeCnt}</strong> 건</td>
		                                    <td><strong class="fs-lg ff-din">${waitCnt}</strong> 건</td>
		                                    <td><strong class="fs-lg ff-din">${completeCnt}</strong> 건</td>
		                                </tr>
		                            </tbody>
		                        </table>
		                    </div>
	                </section>
                </c:if>
                <section>
                    <div class="sub-tit-area">
                        <h3 class="sub-tit">${title} <c:if test="${mode eq 'goods'}"><span>최근 3개월 동안의 내역이 제공됩니다. </span></c:if></h3>
                        <div class="fnc-area">
                            <select class="border-none dayCode">
                                <option value="">전체</option>
                                <option value="WEEK">1주일</option>
                                <option value="MONTH">1개월</option>
                                <option value="3MONTH">3개월</option>
                            </select>
                        </div>
                    </div>
                    <ul class="accordion qa-list">
                    <c:if test="${not empty qaList}">
	                        <c:forEach var="list" items="${qaList}">
	                                <c:choose>
	                                	<c:when test="${list.qnaProcessSttusCode eq 'C'}">
		            		             <li>
	    			                    	<div class="accordion-tit-area">
				                                <button type="button" class="btn-accordion-toggle"><span class="txt-hide">토글버튼</span></button>
				                                <strong class="fc-spot">답변완료</strong>
				                                <c:if test="${mode eq 'goods'}">
				                                <dl>
			                                    	<dt>상품</dt>
				                                    <dd><a href="/shop/goods/goodsView.do?goodsId=${list.goodsId}">${list.goodsNm}</a></dd>
	                                			</dl>
	                                			</c:if>
				                                <div>
				                                 <c:forEach var="codeList" items="${qestnTyCodeList}">
				                                	<c:if test="${codeList.code eq list.qestnTyCode}">
				                                	[${codeList.codeNm}]
				                                	</c:if>
				                                </c:forEach> 
				                                ${list.qestnCn}</div>
				                                <span class="date"><fmt:formatDate pattern="yyyy-MM-dd" value="${list.frstRegistPnttm}"/></span>
				                                <div class="btn-area"> 
				                                    <button type="submit" class="btn-sm-gr deleteQaInfo" data-qaid="${list.qaId}">삭제</button>
				                                    <%-- <c:if test="${mode eq 'goods'}">
				                                    <button class="btn-sm" onclick="location.href='/shop/goods/goodsView.do?goodsId=${list.goodsId}'" >[${list.goodsNm}] 상품 보러 가기</button>
				                                    </c:if> --%>
				                                </div>
				                            </div>
				                            <div class="accordion-txt-area">
				                                <em>RE :</em>
				                                <div>
				                                    ${list.answerCn}
				                                </div>
				                            </div>
			                       		 </li>
	                               		</c:when>
	                                	<c:when test="${list.qnaProcessSttusCode eq 'R'}">
		            		             <li>
	    			                    	<div class="accordion-tit-area">
				                                <!-- <button type="button" class="btn-accordion-toggle"><span class="txt-hide">토글버튼</span></button> -->
				                                <strong>답변대기</strong>
				                                <div>
				                                <c:if test="${mode eq 'goods'}">
				                                <dl>
			                                    	<dt>상품</dt>
				                                    <dd><a href="/shop/goods/goodsView.do?goodsId=${list.goodsId}">${list.goodsNm}</a></dd>
	                                			</dl>
	                                			</c:if>
				                                <c:forEach var="codeList" items="${qestnTyCodeList}">
				                                	<c:if test="${codeList.code eq list.qestnTyCode}">
				                                		[${codeList.codeNm}]
				                                	</c:if>
				                                </c:forEach> 
				                                ${list.qestnCn}</div>
				                                <span class="date"><fmt:formatDate pattern="yyyy-MM-dd" value="${list.frstRegistPnttm}"/></span>
				                                <div class="btn-area">
				                                    <button type="button" class="btn-sm-gr updateQaInfo" data-qaid="${list.qaId}" data-goodsnm="${list.goodsNm}" data-usernm="${USER_NAME}">수정</button>
				                                    <button type="submit" class="btn-sm-gr deleteQaInfo" data-qaid="${list.qaId}">삭제</button>
				                                    <%-- <c:if test="${mode eq 'goods'}">
				                                    <button class="btn-sm" onclick="location.href='/shop/goods/goodsView.do?goodsId=${list.goodsId}'" >[${list.goodsNm}] 상품 보러 가기</button>
				                                    </c:if> --%>
				                                </div>
				                            </div>
			                       		 </li>
	                               		</c:when>
	                                </c:choose>
	                        </c:forEach>
                        </c:if>
                        <c:if test="${empty qaList}">
                        <li><p class="none-txt">등록된 문의가 없습니다.</p></li>
                        </c:if>
                    </ul>
                    <ul class="paging">
                   	<c:url var="pageUrl" value="/user/my/qainfo.do">
                   		<c:if test="${mode eq 'site'}">
                   			<c:param name="qaSeCode" value="SITE"/>
                   		</c:if>
                   		<c:if test="${mode eq 'goods'}">
                   			<c:param name="qaSeCode" value="GOODS"/>
                   		</c:if>
						<c:param name="pageIndex" value=""/>
					</c:url>
					<c:if test="${not empty qaList }">
  						<modoo:pagination paginationInfo="${paginationInfo}" type="text" jsFunction="" pageUrl="${pageUrl}" pageCssClass="page-css-class"/>
  					</c:if>
                    </ul>
                </section>
            </div>
            </div>
        </div><!-- //contents -->
        
        
   <div class="popup" data-popup="qaWrite">
	   <div class="pop-header">
			<h1>${title}</h1>
			<button type="button" class="btn-pop-close" data-popup-close="qaWrite">닫기</button>
		</div>
		<div class="pop-body">
		<form:form modelAttribute="qaInfo" id="qaInfoReg"  method="post" class="qaInfoForm" enaccept-charset="utf-8" enctype="multipart/form-data" action="${actionUrl}">
			<form:hidden path="qestnSj" value="${USER_ID}의 1:1문의"/>
			<form:hidden path="wrterNm" value="${USER_NAME}"/>
			<form:hidden path="wrterId" value="${USER_ID}"/>
			<form:hidden path="qaId" value=""/>
			<form:hidden path="pageIndex" value="${qaInfo.pageIndex}"/>
			<form:hidden path="qaSeCode" value="${qaInfo.qaSeCode}"/>
			<form:hidden path="searchKeyword" value="${qaInfo.searchKeyword}"/>
			<form:hidden path="searchCondition" value="${qaInfo.searchCondition}"/>
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
					<div  class="table-area">
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
			<c:if test="${mode eq 'site'}">
			<div class="file-area">
					<ul class="file-add" id="qa-file">
					</ul>
						<div class="file">
							<label for="atchFile" class="btn-file">사진 첨부하기</label>
							<input type="file" id="atchFile"  name="atchFile"  class="file-hide fileAdd" accept=".gif, .jpg, .png, .bmp"  multiple="multiple">
						</div>
					<p class="fc-gr fs-sm">상품과 무관한 사진을 첨부한 문의는 통보없이 삭제 및 적립 혜택이 회수될 수 있습니다.</p>
			</div>
			</c:if>
			<c:if test="${mode eq 'goods'}">
			<div class="agree-area">
				<label><form:checkbox path="secretAt" value="Y"/> 비밀글로 등록하기</label>
				<%-- <label><form:checkbox path="" value="Y"/> 문자로 받기</label> --%>
			</div>
			<ul class="bullet-sm">
				<li>질문에 대한 답변은 해당 페이지 및  마이구독 &gt; 게시판 &gt; Q&amp;A 에서 확인 하실 수 있습니다.</li>
				<li>상품과 무관하거나 비방,광고, 개인정보 노출 위험이 있는 글 등 Q&amp;A 운영 정책에 어긋나는 질문은 별도의 통보없이 노출 제한 될 수 있습니다.</li>
			</ul>
			</c:if>
			<div class="btn-table-area">
				<!-- <button type="button" class="btn-lg" data-popup-close="qaWrite">취소</button> -->
				<button type="button" class="btn-lg spot submitBtn">확인</button>
			</div>
			</form:form>
		</div>
	</div>
	
	<javascript>
		<script src="${CTX_ROOT}/resources/front/my/qainfo/qainfo.js"></script>
	</javascript>
 </body>	
 </html>