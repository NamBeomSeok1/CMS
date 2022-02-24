<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/modoo/common/commonTagLibs.jsp"%>
<c:set var="pageTitle" value="업체 등록"/>
<c:set var="mode" value="insert"/>
<c:set var="actionUrl" value="/decms/shop/cmpny/writeCmpny.json"/>
<c:choose>
	<c:when test="${empty cmpny.cmpnyId }">
		<c:set var="pageTitle" value="업체 등록"/>
		<c:set var="mode" value="insert"/>
		<c:set var="actionUrl" value="/decms/shop/cmpny/writeCmpny.json"/>
	</c:when>
	<c:otherwise>
		<c:set var="pageTitle" value="업체 수정"/>
		<c:set var="mode" value="update"/>
		<c:set var="actionUrl" value="/decms/shop/cmpny/modifyCmpny.json"/>
	</c:otherwise>
</c:choose>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>${pageTitle }</title>
	<link rel="stylesheet" type="text/css" href="${CTX_ROOT }/resources/lib/tui/tui-grid/tui-grid.min.css"/>
	<link rel="stylesheet" type="text/css" href="${CTX_ROOT }/resources/lib/tui/tui-pagination/tui-pagination.min.css"/>
	<link rel="stylesheet" type="text/css" href="${CTX_ROOT }/resources/lib/tempusdominus-bootstrap-4/css/tempusdominus-bootstrap-4.min.css"/>
</head>
<body>

	<div class="card shadow page-wrapper">
		<div class="card-header">
			<h6 class="m-0 font-weight-bold text-primary">${pageTitle }</h6>
		</div>
	
		<div class="card-body">
			<c:if test="${mode eq 'update' and fn:contains(USER_ROLE,'ROLE_EMPLOYEE')}">
				<div class="row">
					<div class="offset-md-9 col-md-3">
						<div class="mb-3 text-right">
							<c:set var="sttusText" value=""/>
							<c:choose>
								<c:when test="${cmpny.opnngSttusCode eq 'D'}"> 
									<c:set var="sttusText" value="입점취소"/>
								</c:when> 
								<c:when test="${cmpny.opnngSttusCode eq 'A'}"> 
									<c:set var="sttusText" value="처리중"/>
								</c:when> 
								<c:when test="${cmpny.opnngSttusCode eq 'C'}"> 
									<c:set var="sttusText" value="입점승인"/>
								</c:when> 
								<c:when test="${cmpny.opnngSttusCode eq 'R'}"> 
									<c:set var="sttusText" value="입점대기"/>
								</c:when> 
							</c:choose>
							<div class="input-group input-group-sm">
								<div class="input-group-prepend"><span class="input-group-text">현재상태</span></div>
								<input type="text" class="form-control bg-white text-primary" readonly="readonly" value="${sttusText }"/>
								<div class="input-group-append">
									<button class="btn btn-dark btn-sm dropdown-toggle" type="button" id="dropdownActionMenu" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false"><i class="fas fa-"></i> 상태변경</button>
									<div class="dropdown-menu dropdown-menu-right" aria-labelledby="dropdownActionMenu">
										<button class="dropdown-item ${cmpny.opnngSttusCode eq 'D'?'active':'' } btnChangeSttus" type="button" data-code="D"><i class="fas fa-toggle-${cmpny.opnngSttusCode eq 'D'?'on':'off' }"></i> 입점취소</button>
										<button class="dropdown-item ${cmpny.opnngSttusCode eq 'A'?'active':'' } btnChangeSttus" type="button" data-code="A"><i class="fas fa-toggle-${cmpny.opnngSttusCode eq 'A'?'on':'off' }"></i> 처리중</button>
										<button class="dropdown-item ${cmpny.opnngSttusCode eq 'C'?'active':'' } btnChangeSttus" type="button" data-code="C"><i class="fas fa-toggle-${cmpny.opnngSttusCode eq 'C'?'on':'off' }"></i> 입점승인</button>
										<button class="dropdown-item ${cmpny.opnngSttusCode eq 'R'?'active':'' } btnChangeSttus" type="button" data-code="R"><i class="fas fa-toggle-${cmpny.opnngSttusCode eq 'R'?'on':'off' }"></i> 입점대기</button>
									</div>
								</div>
								
							</div>
						</div>
					</div>
				</div>
			</c:if>
			<form:form modelAttribute="cmpny" id="registForm" name="cmpnyForm" cssClass="embedForm" method="post" enctype="multipart/form-data" action="${actionUrl }">
				<fieldset>
					<input type="hidden" name="menuId" value="${param.menuId }"/>
					<form:hidden path="pageIndex" value="${searchVO.pageIndex }"/>
					<form:hidden path="searchCondition" value="${searchVO.searchCondition }"/>
					<form:hidden path="searchKeyword" value="${searchVO.searchKeyword }"/>
					<form:hidden path="searchBgnde" value="${searchVO.searchBgnde }"/>
					<form:hidden path="searchEndde" value="${searchVO.searchEndde }"/>
					<form:hidden path="cmpnyId"/>
					<form:hidden path="cmpnyUserEsntlId"/> <%--사용자고유ID (ESNTL_ID) --%>
					<input type="hidden" name="oldEsntlId" value="${cmpny.cmpnyUserEsntlId }"/>
				</fieldset>
				<small>
					(<i class="fas fa-star text-danger"></i>)는 필수 항목입니다.
				</small>
				<hr class="sm"/>

				<c:if test="${fn:contains(USER_ROLE,'ROLE_EMPLOYEE') }">
					<div class="form-group row">
						<label for="prtnrId" class="col-sm-2 col-form-label col-form-label-sm required">제휴사</label>
						<div class="col-sm-10">
							<c:forEach var="item" items="${cmpny.prtnrCmpnyList }" varStatus="status">
								<div class="custom-control custom-control-sm custom-checkbox custom-control-inline">
									<input type="hidden" name="prtnrCmpnyList[${status.index }].pcmapngId" value="${item.pcmapngId }"/>
									<input type="hidden" name="prtnrCmpnyList[${status.index }].prtnrId" value="${item.prtnrId }"/>
									<input type="hidden" name="prtnrCmpnyList[${status.index }].cmpnyId" value="${item.cmpnyId }"/>
									<input type="checkbox" id="prtnrCmpnyList${status.index + 1 }" name="prtnrCmpnyList[${status.index }].useAt" class="custom-control-input pcmangUseAt" 
										<c:if test="${item.useAt eq 'Y' }">checked="checked"</c:if> value="Y"/>
									<label for="prtnrCmpnyList${status.index + 1 }" class="custom-control-label">${item.prtnrNm }</label>
								</div>
							</c:forEach>
						</div>
					</div>
					<hr class="sm"/>
				</c:if>

				<div class="form-group row">
					<label for="cmpnyNm" class="col-sm-2 col-form-label col-form-label-sm required">업체명</label>
					<form:input path="cmpnyNm" cssClass="col-sm-4 form-control form-control-sm" maxlength="30" placeholder="업체명을 입력하세요"/>
				</div>
				<hr class="sm"/>

				<div class="form-group row">
					<label for="cmpnyMberId" class="col-sm-2 col-form-label col-form-label-sm required">사용자ID</label>
					<div class="input-group col-sm-4 p-0">
						<c:choose>
							<c:when test="${mode eq 'insert' }">
								<form:input path="cmpnyMberId" cssClass="form-control form-control-sm"/>
							</c:when>
							<c:otherwise>
								<form:input path="cmpnyMberId" cssClass="form-control form-control-sm" readonly="true"/>
							</c:otherwise>
						</c:choose>
						<c:if test="${fn:contains(USER_ROLE,'ROLE_EMPLOYEE')}">
							<div class="input-group-append">
								<a href="#" class="btn btn-dark btn-sm btnSearchMber" data-target="#mberListModal"><i class="fas fa-search"></i> 찾기</a>
							</div>
						</c:if>
					</div>
					<c:if test="${fn:contains(USER_ROLE,'ROLE_SHOP') and not fn:contains(USER_ROLE,'ROLE_EMPLOYEE')}">
						<div class="col-sm-6">
							<button id="btnChangePwd" type="button" class="btn btn-danger btn-sm" data-src="${CTX_ROOT }/decms/shop/cmpny/cmpnyUserPwdChange.do"><i class="fas fa-key"></i> 비밀번호 변경</button>
						</div>
					</c:if>
				</div>
				<hr class="sm"/>

				<div class="form-group row">
					<label for="bizrno" class="col-sm-2 col-form-label col-form-label-sm required">사업자등록번호</label>
					<form:input path="bizrno" cssClass="col-sm-4 form-control form-control-sm" maxlength="12" placeholder=""/>
					<label for="rprsntvNm" class="col-sm-2 col-form-label col-form-label-sm ">대표자명</label>
					<form:input path="rprsntvNm" cssClass="col-sm-4 form-control form-control-sm" maxlength="30" placeholder=""/>
				</div>
				<hr class="sm"/>

				<div class="form-group row">
					<label for="bsnmAdres" class="col-sm-2 col-form-label col-form-label-sm ">사업자주소</label>
					<form:input path="bsnmAdres" cssClass="col-sm-4 form-control form-control-sm" maxlength="150" placeholder=""/>
					<label for="cmpnyLogoPath" class="col-sm-2 col-form-label col-form-label-sm ">업체로고</label>
					<div class="input-group col-sm-4 p-0">
						<div class="input-group-prepend">
							<span class="input-group-text"><i class="fas fa-image"></i> </span>
						</div>
						<form:input path="cmpnyLogoPath" cssClass="form-control form-control-sm" maxlength="" placeholder="ex) /contents/sample.png"/>
						<div class="input-group-append">
							<button type="button" class="btn btn-dark btn-sm" data-event="upload" data-target-url="#cmpnyLogoPath" data-target-img="#cmpnyLogoPath"><i class="fas fa-search"></i> 파일찾기</button>
						</div>
					</div>
				</div>
				<hr class="sm"/>
				
				<div class="form-group row">
					<label for="hmpg" class="col-sm-2 col-form-label col-form-label-sm ">홈페이지</label>
					<form:input path="hmpg" cssClass="col-sm-4 form-control form-control-sm" maxlength="200" placeholder=""/>
					<label for="csChnnl" class="col-sm-2 col-form-label col-form-label-sm ">CS채널</label>
					<form:input path="csChnnl" cssClass="col-sm-4 form-control form-control-sm" maxlength="200" placeholder=""/>
				</div>
				<hr class="sm"/>

				<div class="form-group row">
					<label for="opnngDe" class="col-sm-2 col-form-label col-form-label-sm ">입점일</label>
					<div class="input-group col-sm-4 p-0" id="datepicker-opnngDe" data-target-input="nearest">
						<form:input path="opnngDe" cssClass="form-control form-control-sm datetimepicker-input" data-target="#datepicker-opnngDe"/>
						<div class="input-group-append" data-target="#datepicker-opnngDe" data-toggle="datetimepicker">
							<div class="input-group-text"><i class="fas fa-calendar"></i></div>
						</div>
					</div>
					<label for="cmpnyTelno" class="col-sm-2 col-form-label col-form-label-sm required">연락처</label>
					<form:input path="cmpnyTelno" cssClass="col-sm-4 form-control form-control-sm" maxlength="15" placeholder=""/>
				</div>
				<hr class="sm"/>

				<div class="form-group row">
					<label for="chargerNm" class="col-sm-2 col-form-label col-form-label-sm required ">담당자</label>
					<form:input path="chargerNm" cssClass="col-sm-4 form-control form-control-sm" maxlength="30" placeholder=""/>
					<label for="chargerTelno" class="col-sm-2 col-form-label col-form-label-sm required">담당자 연락처</label>
					<form:input path="chargerTelno" cssClass="col-sm-4 form-control form-control-sm" maxlength="15" placeholder=""/>
				</div>
				<hr class="sm"/>
				
				<div class="form-group row">
					<label for="chargerEmail" class="col-sm-2 col-form-label col-form-label-sm required">담당자이메일</label>
					<div class="input-group col-sm-4 p-0">
						<div class="input-group-prepend">
							<span class="input-group-text"><i class="fas fa-envelope"></i></span>
						</div>
						<form:input path="chargerEmail" cssClass="form-control form-control-sm" maxlength="60" placeholder=""/>
					</div>
					<label for="stdeSeCode" class="col-sm-2 col-form-label col-form-label-sm ">정산일</label>
					<%-- <div class="col-sm-4">
						<form:hidden path="stdeSeCode" value="STDE04"/>
						<small>말일</small>
					</div> --%>
					<c:choose>
						<c:when test="${fn:contains(USER_ROLE,'ROLE_SHOP') and not fn:contains(USER_ROLE,'ROLE_EMPLOYEE')}">
							<div class="col-sm-4">
								<c:forEach var="item" items="${stdeSeCodeList }">
									<c:if test="${item.code eq cmpny.stdeSeCode  }">
										<form:hidden path="stdeSeCode" value="${cmpny.stdeSeCode }"/>
										<small>${item.codeNm }</small>
									</c:if>
								</c:forEach>
							</div>
						</c:when>
						<c:otherwise>
							<form:select path="stdeSeCode" cssClass="col-sm-4 custom-select custom-select-sm">
								<c:forEach var="item" items="${stdeSeCodeList }">
									<form:option value="${item.code }">${item.codeNm }</form:option>
								</c:forEach>
							</form:select>
						</c:otherwise>
					</c:choose>
				</div>
				<hr class="sm"/>
				
				<div class="form-group row">
					<label for="splpcSeCode" class="col-sm-2 col-form-label col-form-label-sm ">공급가 정책</label>
					<div class="col-sm-10">
						<form:hidden path="splpcSeCode" value="SP02"/> <%-- 2020.10.07 오영석 차장 요청으로 코드선택 숨김처리 --%>
						<small>입점사 상품별 수수료 정책</small> <%--공통 코드로 존재함 아래 코드는 지우지 말것 --%>
						<%-- <c:forEach var="item" items="${splpcSeCodeList }" varStatus="status">
							<div class="custom-control custom-control-sm custom-radio custom-control-inline">
								<form:radiobutton path="splpcSeCode" cssClass="custom-control-input" value="${item.code }"/>
								<label class="custom-control-label" for="splpcSeCode${status.index+1 }"><small>${item.codeNm }</small></label>
							</div>
						</c:forEach> --%>
					</div>
				</div>
				<hr class="sm"/>

				<div class="form-group row">
					<label for="bankId" class="col-sm-2 col-form-label col-form-label-sm ">은행계좌</label>
					<form:select path="bankId" cssClass="col-sm-4 custom-select custom-select-sm">
						<form:option value="">= 은행선택 =</form:option>
						<c:forEach var="bank" items="${bankList }">
							<form:option value="${bank.bankId }">${bank.bankNm }</form:option>
						</c:forEach>
					</form:select>
					<div class="input-group input-group-sm col-sm-4 p-0 ml-1">
						<div class="input-group-prepend">
							<span class="input-group-text">계좌번호</span>
						</div>
						<form:input path="acnutno" cssClass="form-control"/>
					</div>
				</div>
				<hr class="sm"/>

				<div class="form-group row">
					<label for="hdryId" class="col-sm-2 col-form-label col-form-label-sm required">배송회사</label>
					<div class="col-sm-10">
						<%-- <c:forEach var="item" items="${hdryCmpnyList }" varStatus="status">
							<div class="custom-control custom-control-sm custom-radio custom-control-inline">
								<c:set var="checked" value=""/>
								<c:forEach var="hdry" items="${cmpny.hdryCmpnyList }">
									<c:if test="${hdry.hdryId eq item.hdryId }"> 
										<c:set var="checked">checked="checked"</c:set>
									</c:if>
								</c:forEach>
								<input type="radio" id="hdryId${status.index+1 }" name="hdryCmpnyList[0].hdryId" class="radioHdryId custom-control-input" ${checked } value="${item.hdryId }"/>
								<label class="custom-control-label" for="hdryId${status.index+1 }"><small>${item.hdryNm }</small></label>
							</div>
						</c:forEach> --%>
						<c:forEach var="item" items="${hdryCmpnyList }" varStatus="status">
							<div class="custom-control custom-control-sm custom-checkbox custom-control-inline">
								<c:set var="checked" value=""/>
								<c:forEach var="hdry" items="${cmpny.hdryCmpnyList }">
									<c:if test="${hdry.hdryId eq item.hdryId }"> 
										<c:set var="checked">checked="checked"</c:set>
									</c:if>
								</c:forEach>
								<input type="checkbox" id="hdryId${status.index+1 }" name="hdryCmpnyList[${status.index }].hdryId" class="custom-control-input" ${checked } value="${item.hdryId }"/>
								<label class="custom-control-label" for="hdryId${status.index+1 }"><small>${item.hdryNm }</small></label>
							</div>
						</c:forEach>
					</div>
				</div>
				<hr class="sm"/>

				<div class="form-group row">
					<label for="svcAdres" class="col-sm-2 col-form-label col-form-label-sm required">교환/반품주소</label>
					<form:input path="svcAdres" cssClass="col-sm-10 form-control form-control-sm" maxlength="200" placeholder=""/>
				</div>
				<hr class="sm"/>
				<div class="form-group row">
					<label for="svcHdryNm" class="col-sm-2 col-form-label col-form-label-sm required">교환/반품 택배사명</label>
					<form:input path="svcHdryNm" cssClass="col-sm-4 form-control form-control-sm" maxlength="20" placeholder=""/>
				</div>
				<hr class="sm"/>
				<div class="form-group row">
					<label for="rtngudDlvyPc" class="col-sm-2 col-form-label col-form-label-sm required">반품 배송비</label>
					<form:input path="rtngudDlvyPc" cssClass="col-sm-4 form-control form-control-sm inputNumber" maxlength="20" placeholder=""/>
					<label for="exchngDlvyPc" class="col-sm-2 col-form-label col-form-label-sm required">교환 배송비</label>
					<form:input path="exchngDlvyPc" cssClass="col-sm-4 form-control form-control-sm inputNumber" maxlength="20" placeholder=""/>
				</div>
				<hr class="sm"/>
				
				<div class="form-group row">
					<label for="cmpnyDlvyPolicyCn" class="col-sm-2 col-form-label col-form-label-sm">배송정책</label>
					<form:textarea path="cmpnyDlvyPolicyCn" cssClass="col-sm-10 form-control form-control-sm" rows="3" maxlength="2000"/>
				</div>
				<hr class="sm"/>

				<c:choose>
				<c:when test="${mode eq 'update' and fn:contains(USER_ROLE,'ROLE_EMPLOYEE')}"> <%--직원권한 --%>
					<div class="form-group row">
						<label for="opnngSttusCode" class="col-sm-2 col-form-label col-form-label-sm">등록상태</label>
						<form:select path="opnngSttusCode" cssClass="col-sm-2 custom-select custom-select-sm">
							<c:forEach var="item" items="${opnngSttusCodeList }">
								<form:option value="${item.code }">${item.codeNm }</form:option>
							</c:forEach>
						</form:select>
					</div>
					<hr class="sm"/>
				</c:when>
				<c:otherwise>
					<input type="hidden" name="opnngSttusCode" value="${cmpny.opnngSttusCode }"/> <%--서버에서 변조했는지 체크함 --%>
				</c:otherwise>
				</c:choose>

				<div class="form-group row">
					<label for="mngrMemo" class="col-sm-2 col-form-label col-form-label-sm ">관리자메모</label>
					<form:textarea path="mngrMemo" cssClass="col-sm-10 form-control form-control-sm" rows="3" maxlength="2000"/>
				</div>
				<hr class="sm"/>

				<div class="text-center mt-3">
					<c:if test="${mode eq 'update' and fn:contains(USER_ROLE,'ROLE_EMPLOYEE')}"> <%--직원권한 --%>
						<c:url var="deleteUrl" value="/decms/shop/cmpny/deleteCmpny.json">
							<c:param name="menuId" value="${param.menuId }"/>
							<c:param name="cmpnyId" value="${cmpny.cmpnyId }"/>
						</c:url>
						<a href="<c:out value="${deleteUrl }"/>" class="btn btn-danger btn-sm btnDelete"><i class="fas fa-trash"></i> 삭제</a>
					
						
					</c:if>
					<c:url var="cancelUrl" value="/decms/shop/cmpny/cmpnyManage.do">
						<c:param name="menuId" value="${param.menuId }"/>
					</c:url>
					<a href="<c:out value="${cancelUrl }"/>" class="btn btn-secondary btn-sm"><i class="fas fa-ban"></i> 취소</a>
					<button type="submit" class="btn btn-primary btn-sm"><i class="fas fa-save"></i> 저장</button>
				</div>
			</form:form>
			
			<c:if test="${mode eq 'update' }">
				<div class="mt-3 mb-3">
					<h6>상품 정보</h6>
					<div class="text-right"><small>등록상품 : ${(empty goodsSttusCnt.tcnt)?'0':goodsSttusCnt.tcnt }개</small> 
											<small class="ml-4">등록대기: ${(empty goodsSttusCnt.rcnt)?'0':goodsSttusCnt.rcnt }개</small></div>
					<form id="searchGoodsForm" name="searchGoodsForm" method="get" action="/decms/shop/goods/goodsList.json">
						<input type="hidden" id="goodsPageIndex" name="pageIndex" value="1"/>
						<input type="hidden" name="searchCmpnyId" value="${cmpny.cmpnyId }"/>

					</form>
					<div>
						<div id="data-goods-grid"></div>
						<div id="data-goods-grid-pagination" class="tui-pagination"></div>
					</div>
				</div>
			</c:if>

		</div>
	</div>
	
	<%-- <c:if test="${fn:contains(USER_ROLE,'ROLE_EMPLOYEE')}"> --%>
	<div class="modal fade" id="mberListModal" tabindex="-1" role="dialog" aria-labelledby="mberListModalLabel" aria-hidden="true">
		<div class="modal-dialog modal-xl modal-dialog-centered modal-dialog-scrollable modal-dark">
			<div class="modal-content">
				<div class="modal-header">
					<h6 class="modal-title" id="mberListModalLabel">업체사용자목록</h6>
					<button type="button" class="close" data-dismiss="modal" aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
				</div>
				<div class="modal-body">
					<div id="member-form" style="display:none;">
					</div>
					<div id="member-list">
						<form name="mberSearchForm" id="mberSearchForm" method="get" action="${CTX_ROOT}/decms/shop/cmpny/cmpnyMberList.json">
							<fieldset>
								<input type="hidden" id="pageIndex" name="pageIndex" value="1"/>
							</fieldset>
							<div class="form-group row">
								<div class="col-sm-6">
									<div class="input-group input-group-sm">
										<div class="input-group-prepend">
											<select name="searchCondition" class="custom-select custom-select-sm">
												<option value="NM">이름</option>
												<option value="ID">ID</option>
											</select>
										</div>
										<input type="text" name="searchKeyword" class="form-control" value=""/>
										<div class="input-group-append">
											<button type="submit" class="btn btn-primary"><i class="fas fa-search"></i>검색</button>
										</div>
									</div>
								</div>
								<div class="col-sm-6 text-right">
									<a href="${CTX_ROOT }/decms/embed/mber/writeMber.do?searchUserType=SHOP" class="btn btn-dark btn-sm btnAddUser"><i class="fas fa-plus"></i>사용자 추가</a>
								</div>
							</div>
						</form>
						<div class="mber-grid">
							<div id="data-member-grid" class="mt-3"></div>
							<div id="data-member-grid-pagination" class="tui-pagination"></div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	<%-- </c:if> --%>
	
	<%-- <div class="modal fade" id="mmberChangePwdModal" tabindex="-1" role="dialog" aria-labelledby="mmberChangePwdModalLabel" aria-hidden="true">
		<div class="modal-dialog modal-md modal-dialog-centered modal-dialog-scrollable modal-dark">
			<div class="modal-content">
				<div class="modal-header">
					<h6 class="modal-title" id="mmberChangePwdModalLabel">비밀번호변경</h6>
					<button type="button" class="close" data-dismiss="modal" aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
				</div>
				<div class="modal-body">
					<form id="mberPwdFrom" name="mberPwdFrom" method="post" action="${CTX_ROOT }/decms/shop/cmpny/cmpnyUserPwdChange.do">
						<div class="row">
							<div class="col-12">
								<div class="input-group input-group-sm mb-2">
									<div class="input-group-prepend">
										<span class="input-group-text" style="min-width:100px;">새 비밀번호</span>
									</div>
									<input type="password" name="password" class="form-control" autocomplete="one-time-code" value="" placeholder="8자이상 20자미만"/>
								</div>
							</div>
							<div class="col-12">
								<div class="input-group input-group-sm">
									<div class="input-group-prepend">
										<span class="input-group-text" style="min-width:100px;">새 비밀번호확인</span>
									</div>
									<input type="password" name="repassword" class="form-control" autocomplete="one-time-code" value="" placeholder="8자이상 20자미만"/>
								</div>
							</div>
						</div>
						<div class="text-center mt-3">
							<button type="button" class="btn btn-secondary btn-sm" data-dismiss="modal"><i class="fas fa-ban"></i> 취소</button>
							<button type="submit" class="btn btn-dark btn-sm"><i class="fas fa-save"></i> 변경</button>
						</div>
					</form>
				</div>
			</div>
		</div>
	</div> --%>

	<javascript>
		<script src="${CTX_ROOT}/resources/lib/tui/tui-code-snippet/tui-code-snippet.min.js"></script>
		<script src="${CTX_ROOT}/resources/lib/tui/tui-pagination/tui-pagination.min.js"></script>
		<script src="${CTX_ROOT}/resources/lib/tui/tui-grid/tui-grid.min.js"></script>
		<script src="${CTX_ROOT}/resources/lib/tempusdominus-bootstrap-4/js/tempusdominus-bootstrap-4.min.js"></script>
		<script src="${CTX_ROOT}/resources/decms/shop/cmpny/js/cmpnyForm.js?20201105"></script>
		<script src="${CTX_ROOT}/resources/decms/shop/cmpny/js/cmpnyFormUserList.js?20201105"></script>
		<c:if test="${mode eq 'update' }">
		<script src="${CTX_ROOT}/resources/decms/shop/cmpny/js/cmpnyGoods.js?20201113"></script>
		</c:if>
	</javascript>
</body>
</html>
