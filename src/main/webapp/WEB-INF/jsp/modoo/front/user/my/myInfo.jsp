<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/modoo/common/commonTagLibs.jsp"%>
<c:set var="subMenuId" value="${param.menuId}"/>
<c:set var="title" value="내 정보 보기"/>   
<title>${title}</title>

<div class="wrap">
	<c:import url="/user/my/subMenu.do" charEncoding="utf-8">
		<c:param name="menuId" value="myInfo_myDetail"/>
	</c:import>

	<div class="sub-contents">
			<c:import url="/user/my/myLocation.do" charEncoding="utf-8">
				<c:param name="menuId" value="myInfo"/>
				<c:param name="subMenuId" value="${title }"/>
			</c:import> 
				<section class="user-area">
					<div class="sub-tit-area">
						<h3 class="sub-tit">내 정보보기</h3>
						<div class="fnc-area">
							<a href="/user/sign/logout.do" class="fc-gr btn-logout">로그아웃 <i class="ico-arr-r gr back sm"></i></a>
						</div>
					</div>
					<div class="user-img-area">
						<div class="user-img"></div>
						<div class="user-txt-area">
							<h3><strong>${user.name}님</strong>(${user.id})</h3>
						</div>
					</div>
					<div class="write-border-type">
						<table>
							<caption>${subMenuId}</caption>
							<colgroup>
								<col style="width:150px">
								<col>
							</colgroup>
							<tbody>
								<tr>
									<th scope="row">로그인 ID</th>
									<td>${user.id}</td>
								</tr>
								<tr>
									<th scope="row">이메일주소</th>
									<td>${user.email }</td>
								</tr>
								<tr>
									<th scope="row">이름</th>
									<td>${user.name}</td>
								</tr>
								<!-- <tr>
									<th scope="row">FOXEDU STORE 가입 일시</th>
									<td>2020-06-11</td>
								</tr> -->
								<tr>
									<th scope="row">기본 결제 카드</th>
									<td>
										<c:if test="${!empty bassUseCard}">
											${bassUseCard.cardNm} (${bassUseCard.lastCardNo})
										</c:if>
										<c:if test="${empty bassUseCard}">
											기본 설정된 카드가 없습니다.
										</c:if>
										<div class="btn-area ar">
											<button type="button" onclick="location.href='/user/my/cardManage.do'" class="btn">변경하기</button>
										</div>

									</td>
								</tr>
								<tr>
									<th scope="row">기본 배송지</th>
									<td id="bassDlvyAdres">
									  	  ${bassDlvyAdres}
										<div class="btn-area ar">
											<button type="button" class="btn" id="changeAddress" onclick="initAddressPop();">변경하기</button>
										</div>	

									</td>
								</tr>
							</tbody>
						</table>
					</div>
				</section>
			</div>
		</div>


<javascript>
	<%-- <script src="${CTX_ROOT}/user/my/myInfoScript.do"></script> --%>
	<script src="${CTX_ROOT}/resources/front/my/myInfo/zip.js?v=1"></script>
	<script src="${CTX_ROOT}/resources/front/my/myInfo/address.js"></script>
</javascript>

<c:import url="/user/my/deliveryEdit.do" charEncoding="utf-8">
	<c:param name="menuId" value="myInfo"/>
</c:import>

