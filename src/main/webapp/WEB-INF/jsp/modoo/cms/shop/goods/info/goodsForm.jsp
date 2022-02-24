<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/modoo/common/commonTagLibs.jsp"%>
<%@ taglib prefix="double-submit" uri="http://www.egovframe.go.kr/tags/double-submit/jsp" %>
<c:set var="pageTitle" value="등록"/>
<c:set var="mode" value="insert"/>
<c:set var="actionUrl" value="/decms/shop/cmpny/writeCmpny.json"/>
<c:choose>
	<c:when test="${empty goods.goodsId }">
		<c:set var="pageTitle" value="상품 등록"/>
		<c:set var="mode" value="insert"/>
		<c:set var="actionUrl" value="/decms/shop/goods/writeGoods.json"/>
	</c:when>
	<c:otherwise>
		<c:set var="pageTitle" value="상품 수정"/>
		<c:set var="mode" value="update"/>
		<c:set var="actionUrl" value="/decms/shop/goods/modifyGoods.json"/>
	</c:otherwise>
</c:choose>

<%-- CP 관리에서만 쓰임 --%>
<c:set var="IS_READ_ONLY" value="false"/>
<c:set var="READ_ONLY_ATTR" value=""/>
<c:if test="${readOnlyAt eq 'Y' }">
	<c:set var="IS_READ_ONLY" value="true"/>
	<c:set var="READ_ONLY_ATTR" value="readonly"/>
</c:if>

<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>${pageTitle }</title>
	<link rel="stylesheet" type="text/css" href="${CTX_ROOT }/resources/lib/tui/tui-grid/tui-grid.min.css"/>
	<link rel="stylesheet" type="text/css" href="${CTX_ROOT }/resources/lib/tui/tui-pagination/tui-pagination.min.css"/>
	<link rel="stylesheet" type="text/css" href="${CTX_ROOT }/resources/lib/tempusdominus-bootstrap-4/css/tempusdominus-bootstrap-4.min.css"/>
	<link rel="stylesheet" type="text/css" href="${CTX_ROOT }/resources/decms/shop/goods/info/css/goodsForm.css?20200914"/>
	<style>
		.vat-font {
			color: red;
			font-size: .875rem;
			position: absolute; 
			top: 50%; 
			-ms-transform: translateY(-50%); 
			transform: translateY(-50%);
		}
	</style>
</head>
<body>

	<div class="card shadow page-wrapper">
		<div class="card-header">
			<h6 class="m-0 font-weight-bold text-primary">${pageTitle }</h6>
		</div>
	
		<div class="card-body">
			<c:if test="${mode eq 'update' }">
				<div class="text-right">
					<c:url var="copyUrl" value="/decms/shop/goods/writeGoods.do">
						<c:param name="copyGoodsId" value="${goods.goodsId }"/>
						<c:param name="menuId" value="${param.menuId }"/>
					</c:url>
					<a href="<c:out value="${copyUrl }"/>" class="btnGoodsCopy btn btn-dark btn-sm"><i class="fas fa-copy"></i> 상품 복사하기</a>
				</div>
			</c:if>
			<c:if test="${not empty copyGoodsId  }">
				<div class="text-danger text-right">
					<strong>상품 복사 상태입니다.</strong>
				</div>
			</c:if>
			<h6><i class="fas fa-dice-one"></i> 상품정보 <small>(기본)</small></h6>
			<hr class="sm"/>
			<form:form modelAttribute="goods" id="registForm" name="goodsForm" cssClass="embedForm" method="post" action="${actionUrl }">
				<fieldset>
					<input type="hidden" name="menuId" value="${param.menuId }"/>
					<input type="hidden" name="pageIndex" value="${searchVO.pageIndex }"/>
					<form:hidden path="searchCondition" value="${searchVO.searchCondition }"/>
					<form:hidden path="searchKeyword" value="${searchVO.searchKeyword }"/>
					<input type="hidden" name="searchCateCode1" value="${searchVO.searchCateCode1 }"/>
					<input type="hidden" name="searchCateCode2" value="${searchVO.searchCateCode2 }"/>
					<input type="hidden" name="searchCateCode3" value="${searchVO.searchCateCode3 }"/>
					<input type="hidden" name="searchCmpnyId" value="${searchVO.searchCmpnyId }"/>
					<input type="hidden" name="searchCmpnyNm" value="${searchVO.searchCmpnyNm }"/>
					<input type="hidden" name="searchGoodsId" value="${searchVO.searchGoodsId }"/>
					<input type="hidden" name="searchRegistSttusCode" value="${searchVO.searchRegistSttusCode }"/>
					<form:hidden path="goodsId"/>
					<form:hidden path="cmpnyId"/>
					<form:hidden path="sbscrptCycleSeCode"/>
					<double-submit:preventer/>
				</fieldset>

				<div class="text-right">
					<!-- <button type="submit" class="btn btn-primary btn-sm"><i class="fas fa-save"></i> 테스트 저장</button> -->
				</div>

				<div class="form-group row">
					<c:if test="${fn:contains(USER_ROLE,'ROLE_EMPLOYEE') }">
						<label for="" class="col-sm-2 col-form-label col-form-label-sm required">업체</label>
						<div class="input-group input-group-sm col-sm-4 p-0">
							<form:input path="cmpnyNm" cssClass="form-control form-control-sm bg-light" readonly="true" placeholder="업체를 선택하세요!"/>
							<div class="input-group-append">
								<button type="button" class="btn btn-dark btn-sm btnSearchCmpny" data-target="#cmpnyListModal"><i class="fas fa-search"></i></button>
							</div>
						</div>
					</c:if>
					<label for="" class="col-sm-2 col-form-label col-form-label-sm required">제휴사</label>
					<div class="col-sm-4">
						<form:select path="pcmapngId" class="custom-select custom-select-sm">
							<c:forEach var="item" items="${goods.prtnrCmpnyList }">
								<c:if test="${not empty item.pcmapngId}">
									<form:option value="${item.pcmapngId }">${item.prtnrNm }</form:option>
								</c:if>
							</c:forEach>
							<c:if test="${empty goods.prtnrCmpnyList }">
								<form:option value="">=선택=</form:option>
							</c:if>
						</form:select>
					</div>
				</div>
				<hr class="sm"/>
				
				<%-- <div class="form-group row">
					<label for="" class="col-sm-2 col-form-label col-form-label-sm">상품 판매 종류</label> 상품결제구분코드
					<div class="col-sm-4">
						<form:select path="goodsSetleSeCode" class="custom-select custom-select-sm" disabled="true">
							<form:option value="SBS">구독판매</form:option>
							<form:option value="GNRL">일반판매</form:option>
						</form:select>
					</div>
				</div>
				<hr class="sm"/> --%>

					<div class="form-group row">
						<label for="" class="col-sm-2 col-form-label col-form-label-sm">상품 종류</label>
						<div class="col-sm-4">
							<c:choose>
								<c:when test="${readOnlyAt eq 'Y'}"> <%--CP 페이지에서 등록완료 상태이면 --%>
								<c:choose>
									<c:when test="${goods.goodsKndCode eq 'CPN'}">
										<c:set var="goodsKndName" value="쿠폰상품"/>
									</c:when>
									<c:when test="${goods.goodsKndCode eq 'GNR'}">
										<c:set var="goodsKndName" value="일반상품"/>
									</c:when>
									<c:otherwise>CONCAT('CMS',LPAD(CAST(SUBSTR(MAX(CODE_ID),4,3) AS INT) + 1 ,3 ,0))
										<c:set var="goodsKndName" value="구독상품"/>
									</c:otherwise>
								</c:choose>
									<input type="text" class="form-control form-control-sm" id="readGoodsKndCode"  readonly  value="${goodsKndName}"/>
								</c:when>
								<c:otherwise>
									<c:forEach var="item" items="${goodsKndCodeList }" varStatus="status">
										<div class="custom-control custom-control-sm custom-radio custom-control-inline">
											<form:radiobutton path="goodsKndCode" cssClass="custom-control-input" value="${item.code }"/>
											<label class="custom-control-label" for="goodsKndCode${status.index+1 }"><small>${item.codeNm }</small></label>
										</div>
									</c:forEach>
								</c:otherwise>
							</c:choose>
						</div>
						<label for="adultCrtAt" class="col-sm-2 col-form-label col-form-label-sm">성인인증여부</label>
						<div class="col-sm-4">
							<label for="adultCrtAt">
								<c:choose>
									<c:when test="${item.adultCrtAt eq 'Y'}">
										<form:checkbox path="adultCrtAt" cssClass="mb-0" checked="checked" value="Y"/>
									</c:when>
									<c:otherwise>
										<form:checkbox path="adultCrtAt" cssClass="mb-0" value="Y"/>
									</c:otherwise>
								</c:choose>
									<small>성인인증설정</small>&nbsp&nbsp&nbsp&nbsp
									<small>※  청소년판매불가 상품은 체크해주세요.</small>
							</label>
						</div>
					</div>

					<hr class="sm"/>

					<div class="form-group row">
					<label for="vchUseAt" class="col-sm-2 col-form-label col-form-label-sm">수강권상품여부</label>
						<div class="col-sm-4">
							<label for="vchUseAt">
								<c:choose>
									<c:when test="${not empty goods.vchCode }">
										<form:checkbox path="vchUseAt" cssClass="mb-0" checked="checked" value="Y"/>
										<small>수강권상품설정</small>
										<div id="vchArea">
										</br>
										<small>* 수강권타입</small>
											<form:select path="vchCode" cssClass="custom-select custom-select-sm" value="${vchCode}">
												<form:option value="${goods.vchCode}">${goods.vchCodeNm}</form:option>
												<c:forEach var="item" items="${vchKindCodeList }" varStatus="status">
													<c:if test="${item.code ne goods.vchCode }">
														<form:option value="${item.code }">${item.codeNm }</form:option>
													</c:if>
												</c:forEach>
											</form:select>
											<small>* 수강권기간타입</small>
											<form:select path="vchPdTy" cssClass="custom-select custom-select-sm" value="${vchPdTy}">
												<form:option value="${goods.vchPdTy}">${goods.vchPdTynm}</form:option>
												<c:forEach var="item" items="${vchPdTyCodeList }" varStatus="status">
													<c:if test="${item.code ne goods.vchPdTy }">
														<form:option value="${item.code }">${item.codeNm }</form:option>
													</c:if>
												</c:forEach>
											</form:select>
											<div class="input-group input-group-sm">
												<div class="input-group-append">
													* 수강권 유효기간&nbsp
												</div>
												<form:input path="vchValidPd" cssClass="form-control form-control-sm inputNumber"/>
												<div class="input-group-append">
													개월
												</div>
											</div>
										</div>
									</c:when>
									<c:otherwise>
										<form:checkbox path="vchUseAt" cssClass="mb-0" value="Y"/>
										<small>수강권 상품설정</small>
										<div id="vchArea" style="display: none" class="col-sm-8">
										</br>
										<small>* 수강권 타입</small>
										<form:select path="vchCode" cssClass="custom-select custom-select-sm">
											<form:option value="=선택="></form:option>
											<c:forEach var="item" items="${vchKindCodeList }" varStatus="status">
												<form:option value="${item.code}">${item.codeNm }</form:option>
											</c:forEach>
										</form:select>
											<small>* 수강권 기간타입</small>
											<form:select path="vchPdTy" cssClass="custom-select custom-select-sm">
												<form:option value="=선택="></form:option>
												<c:forEach var="item" items="${vchPdTyCodeList }" varStatus="status">
													<c:if test="${item.code ne goods.vchPdTy }">
														<form:option value="${item.code }">${item.codeNm }</form:option>
													</c:if>
												</c:forEach>
											</form:select>
											<div class="input-group input-group-sm">
												<div class="input-group-append">
													* 수강권 유효기간&nbsp
												</div>
												<form:input path="vchValidPd" cssClass="form-control form-control-sm inputNumber"/>
												<div class="input-group-append">
													개월
												</div>
											</div>
										</div>
									</c:otherwise>
								</c:choose>
							</label>
						</div>
					</div>
				<hr class="sm"/>

				<div class="form-group row">
					<label for="" class="col-sm-2 col-form-label col-form-label-sm required">카테고리</label>
					<div class="col-sm-10 form-inline ">
						<form:select path="cateCode1" cssClass="custom-select custom-select-sm selectCategory" data-dp="1">
							<form:option value="">선택</form:option>
							<c:forEach var="item" items="${cate1List }">
								<form:option value="${item.goodsCtgryId }">${item.goodsCtgryNm }</form:option>
							</c:forEach>
						</form:select>
						<form:select path="cateCode2" cssClass="custom-select custom-select-sm selectCategory" data-dp="2">
							<form:option value="">선택</form:option>
							<c:forEach var="item" items="${cate2List }">
								<form:option value="${item.goodsCtgryId }">${item.goodsCtgryNm }</form:option>
							</c:forEach>
						</form:select>
						<form:select path="cateCode3" cssClass="custom-select custom-select-sm selectCategory" data-dp="3">
							<form:option value="">선택</form:option>
							<c:forEach var="item" items="${cate3List }">
								<form:option value="${item.goodsCtgryId }">${item.goodsCtgryNm }</form:option>
							</c:forEach>
						</form:select>
					</div>
				</div>
				<hr class="sm"/>

				<div class="form-group row">
					<label for="goodsNm" class="col-sm-2 col-form-label col-form-label-sm required">상품명</label>
					<div class="col-sm-4">
						<form:input path="goodsNm" cssClass="form-control form-control-sm"/>
					</div>
					<label for="modelNm" class="col-sm-2 col-form-label col-form-label-sm required">모델명</label>
					<div class="col-sm-4">
						<form:input path="modelNm" cssClass="form-control form-control-sm"/>
					</div>
				</div>
				<hr class="sm"/>

				<div class="form-group row">
					<label for="makr" class="col-sm-2 col-form-label col-form-label-sm">제조사</label>
					<div class="col-sm-4">
						<form:input path="makr" cssClass="form-control form-control-sm" maxlength="30"/>
					</div>
					<label for="orgplce" class="col-sm-2 col-form-label col-form-label-sm">원산지</label>
					<div class="col-sm-4">
						<form:input path="orgplce" cssClass="form-control form-control-sm" maxlength="30"/>
					</div>
				</div>
				<hr class="sm"/>
			
				<div class="form-group row">
					<label for="brandId" class="col-sm-2 col-form-label col-form-label-sm">브랜드</label>
					<div class="col-sm-4">
						<form:select path="brandId" cssClass="custom-select custom-select-sm">
							<form:option value="">선택</form:option>
							<c:forEach var="brand" items="${brandList }">
								<form:option value="${brand.brandId }">${brand.brandNm }</form:option>
							</c:forEach>
						</form:select>
					</div>
					<%--
					<div class="input-group input-group-sm col-sm-6">
						<a href="${CTX_ROOT }/decms/embed/shop/goods/goodsBrandManage.do" class="btn btn-dark btn-sm btnBrandManage" data-target="#brandListModal"><i class="fas fa-external-link-alt"></i> 브랜드관리</a>
					</div>
					--%>
				</div>
				<hr class="sm"/>
				
				<div class="form-group row">
					<label for="crtfcMatter" class="col-sm-2 col-form-label col-form-label-sm">인증사항</label>
					<div class="col-sm-4">
						<form:input path="crtfcMatter" cssClass="form-control form-control-sm" maxlength="300"/>
					</div>
					<label for="cnsltTelno" class="col-sm-2 col-form-label col-form-label-sm required">소비자상담전화</label>
					<div class="col-sm-4">
						<form:input path="cnsltTelno" cssClass="form-control form-control-sm" maxlength="50"/>
					</div>
				</div>
				<hr class="sm"/>

				<div class="form-group row">
					<label for="goodsIntrcn" class="col-sm-2 col-form-label col-form-label-sm">상품간단소개</label>
					<div class="col-sm-10">
						<form:input path="goodsIntrcn" cssClass="form-control form-control-sm" maxlength="40" placeholder="최대 40자입니다."/>
					</div>
				</div>
				<hr class="sm"/>

				<div class="form-group row">
					<label for="evntWords" class="col-sm-2 col-form-label col-form-label-sm">이벤트문구</label>
					<div class="col-sm-10">
					<form:input path="evntWords" cssClass="form-control form-control-sm" maxlength="300"/>
					</div>
				</div>
				<hr class="sm"/>

				<div class="form-group row">
					<label for="" class="col-sm-2 col-form-label col-form-label-sm">검색키워드</label>
					<div class="col-sm-10">
						<div class="form-inline mb-2">
							<div class="input-group input-group-sm">
								<div class="input-group-prepend">
									<span class="input-group-text">키워드 등록</span>
								</div>
								<input type="text" id="keyword" size="10" class="form-control form-control-sm" maxlength="20"/>
								<div class="input-group-append">
									<button type="button" class="btn btn-dark btn-sm btnAddKeyword"><i class="fas fa-plus"></i></button>
								</div>
							</div>
						</div>
						<div id="keyword-list">
							<c:forEach var="item"  items="${goods.goodsKeywordList }" varStatus="status">
								<span class="badge badge-secondary keyword-item">
									<input type="hidden" name="goodsKeywordList[${status.index }].goodsKeywordNo" class="goodsKeywordNo" value="${item. goodsKeywordNo}"/>
									<input type="hidden" name="goodsKeywordList[${status.index }].keywordNm" class="keywordNm" value="<c:out value="${item.keywordNm }"/>"/>
									<c:out value="${item.keywordNm }"/>
									<a href="${CTX_ROOT }/decms/shop/goods/deleteGoodsKeyword.json?goodsKeywordNo=${item.goodsKeywordNo}" class="btnRemoveKeyword" title="삭제"><i class="fas fa-times"></i></a>
								</span>
							</c:forEach>
						</div>
					</div>
				</div>
				<hr class="sm"/>

				<div class="form-group row">
					<label for="mngrMemo" class="col-sm-2 col-form-label col-form-label-sm">관리자메모(쿠폰상품안내메모)</label>
					<div class="col-sm-4">
						<form:textarea path="mngrMemo" rows="3" cssClass="form-control form-control-sm"/>
					</div>
					<label for="soldOutAt" class="col-sm-2 col-form-label col-form-label-sm">품절유무</label>
					<div class="col-sm-4">
						<div class="custom-control custom-checkbox">
							<form:checkbox path="soldOutAt" cssClass="custom-control-input" value="Y"/>
							<label class="custom-control-label" for="soldOutAt1"><small>품절</small></label>
						</div>
					</div>
				</div>
				<hr class="sm"/>

				<c:choose>
				<c:when test="${readOnlyAt eq 'Y'}"> <%--CP 페이지에서 등록완료 상태이면 --%>
					<c:if test="${goods.goodsKndCode eq 'SBS' }">
						<div class="form-group row sbscrpt-cycle">
							<label for="" class="col-sm-2 col-form-label col-form-label-sm">결제주기</label>
							<div class="col-sm-10">
								<c:choose>
									<c:when test="${goods.sbscrptCycleSeCode eq 'WEEK' }"> <%-- CP 등록완료후 결제주기 (주) --%>
										<div class="input-group input-group-sm">
											<div class="input-group-prepend">
												<div class="input-group-text">주기</div>
											</div>
											<input type="text" class="form-control form-control-sm" readonly value="주단위 구독(주/요일)"/>
										</div>
										<div class="input-group input-group-sm">
											<div class="input-group-prepend">
												<div class="input-group-text">주 선택</div>
											</div>
											<c:set var="weekStr" value=""/>
											<c:forEach var="wc" items="${goods.sbscrptWeekCycleList }" varStatus="status">
												<c:set var="weekStr" value="${weekStr}${wc }주"/>
												<c:if test="${not status.last }"> <c:set var="weekStr" value="${weekStr}, "/> </c:if>
											</c:forEach>
											<input type="text" class="form-control form-control-sm" readonly value="${weekStr }"/>
										</div>
										<div class="input-group input-group-sm">
											<div class="input-group-prepend">
												<div class="input-group-text">요일 선택</div>
												<c:set var="dwStr" value=""/>
												<c:forEach var="dw" items="${goods.sbscrptDlvyWdList }" varStatus="status">
													<c:choose>
														<c:when test="${dw eq '1' }"> <c:set var="dwStr" value="${dwStr }일"/> </c:when>
														<c:when test="${dw eq '2' }"> <c:set var="dwStr" value="${dwStr }월"/> </c:when>
														<c:when test="${dw eq '3' }"> <c:set var="dwStr" value="${dwStr }화"/> </c:when>
														<c:when test="${dw eq '4' }"> <c:set var="dwStr" value="${dwStr }수"/> </c:when>
														<c:when test="${dw eq '5' }"> <c:set var="dwStr" value="${dwStr }목"/> </c:when>
														<c:when test="${dw eq '6' }"> <c:set var="dwStr" value="${dwStr }금"/> </c:when>
														<c:when test="${dw eq '7' }"> <c:set var="dwStr" value="${dwStr }토"/> </c:when>
													</c:choose>
													<c:if test="${not status.last }"> <c:set var="dwStr" value="${dwStr}, "/> </c:if>
												</c:forEach>
											</div>
											<input type="text" class="form-control form-control-sm" readonly value="${dwStr }"/>
										</div>
										<div class="input-group input-group-sm">
											<div class="input-group-prepend">
												<div class="input-group-text">최소 이용주기</div>
											</div>
											<input type="text" class="form-control form-control-sm" readonly value="${empty goods.sbscrptMinUseWeek?'미설정':goods.sbscrptMinUseWeek }"/>
										</div>
									</c:when>
									<c:when test="${goods.sbscrptCycleSeCode eq 'MONTH'}"> <%-- CP 등록완료후 결제주기 (월) --%>
										<div class="input-group input-group-sm">
											<div class="input-group-prepend">
												<div class="input-group-text">주기</div>
											</div>
											<input type="text" class="form-control form-control-sm" readonly value="월단위 구독(개월/날짜)"/>
										</div>
										<div class="input-group input-group-sm">
											<div class="input-group-prepend">
												<div class="input-group-text">개월수 선택</div>
											</div>
											<c:set var="mtStr" value=""/>
											<c:forEach var="mt" items="${goods.sbscrptMtCycleList }" varStatus="status">
												<c:set var="mtStr" value="${mtStr }${mt }개월"/>
												<c:if test="${not status.last }"> <c:set var="mtStr" value="${mtStr}, "/> </c:if>
											</c:forEach>
											<input type="text" class="form-control form-control-sm" readonly value="${mtStr }"/>
										</div>
										<div class="input-group input-group-sm">
											<div class="input-group-prepend">
												<div class="input-group-text">날짜선택</div>
											</div>
											<input type="text" class="form-control form-control-sm" readonly value="${goods.sbscrptDlvyDay } 일"/>
										</div>
										<div class="input-group input-group-sm">
											<div class="input-group-prepend">
												<div class="input-group-text">최소 이용주기</div>
											</div>
											<input type="text" class="form-control form-control-sm" readonly value="${goods.sbscrptMinUseMt } 개월"/>
										</div>
									</c:when>
								</c:choose>
							</div>
						</div>
					</c:if>
				</c:when>
				<c:otherwise>
					<div class="form-group row">
						<label for="" class="col-sm-2 col-form-label col-form-label-sm">결제주기</label>
						<div class="col-sm-5 sbscrptWeek">
							<c:set var="IS_DISABLED" value="false"/>
							<c:if test="${goods.sbscrptCycleSeCode ne 'WEEK' }">
								<c:set var="IS_DISABLED" value="true"/>
							</c:if>
							<label class="col-form-label col-form-label-sm d-block sbscrptCycleSe <c:if test="${goods.sbscrptCycleSeCode eq 'WEEK' }">active</c:if>" id="week" data-sbscrpt="WEEK">
								<i class="fas fa-check"></i>
								주단위 구독(주/요일)
							</label>
							<div class="row">
								<label class="col-3 col-form-label-sm">주 선택</label>
								<div class="col-9">
									<c:forEach var="item" begin="1" end="12">
										<c:set var="isChecked" value=""/>
										<div class="custom-control custom-checkbox custom-control-inline">
											<c:forEach var="wc" items="${goods.sbscrptWeekCycleList }">
												<c:if test="${wc eq item }"> <c:set var="isChecked" value="true"/> </c:if>
											</c:forEach>
											<input type="checkbox" id="sbscrptWeekCycle${item }" name="sbscrptWeekCycle" class="custom-control-input" value="${item }" 
												<c:if test="${isChecked }">checked</c:if> <c:if test="${IS_DISABLED }">disabled</c:if> />
											<label class="custom-control-label " for="sbscrptWeekCycle${item }"><small>${item }주</small></label>
										</div>
									</c:forEach>
								</div>
							</div>
							<hr class="sm"/>
							<div class="row">
								<label class="col-3 col-form-label-sm">요일 선택</label>
								<div class="col-9">
								<div class="custom-control custom-checkbox custom-control-inline">
										<c:set var="isChecked" value=""/>
										<c:forEach var="dw" items="${goods.sbscrptDlvyWdList }">
											<c:if test="${dw eq '1' }"> <c:set var="isChecked" value="true"/> </c:if>
										</c:forEach>
										<input type="checkbox" id="sbscrptDlvyWd1" name="sbscrptDlvyWd" class="custom-control-input" value="1" <c:if test="${isChecked }">checked</c:if> <c:if test="${IS_DISABLED }">disabled</c:if>/>
										<label class="custom-control-label " for="sbscrptDlvyWd1"><small>일</small></label>
									</div>
									<div class="custom-control custom-checkbox custom-control-inline">
										<c:set var="isChecked" value=""/>
										<c:forEach var="dw" items="${goods.sbscrptDlvyWdList }">
											<c:if test="${dw eq '2' }"> <c:set var="isChecked" value="true"/> </c:if>
										</c:forEach>
										<input type="checkbox" id="sbscrptDlvyWd2" name="sbscrptDlvyWd" class="custom-control-input" value="2" <c:if test="${isChecked }">checked</c:if> <c:if test="${IS_DISABLED }">disabled</c:if>/>
										<label class="custom-control-label " for="sbscrptDlvyWd2"><small>월</small></label>
									</div>
									<div class="custom-control custom-checkbox custom-control-inline">
										<c:set var="isChecked" value=""/>
										<c:forEach var="dw" items="${goods.sbscrptDlvyWdList }">
											<c:if test="${dw eq '3' }"> <c:set var="isChecked" value="true"/> </c:if>
										</c:forEach>
										<input type="checkbox" id="sbscrptDlvyWd3" name="sbscrptDlvyWd" class="custom-control-input" value="3" <c:if test="${isChecked }">checked</c:if> <c:if test="${IS_DISABLED }">disabled</c:if>/>
										<label class="custom-control-label " for="sbscrptDlvyWd3"><small>화</small></label>
									</div>
									<div class="custom-control custom-checkbox custom-control-inline">
										<c:set var="isChecked" value=""/>
										<c:forEach var="dw" items="${goods.sbscrptDlvyWdList }">
											<c:if test="${dw eq '4' }"> <c:set var="isChecked" value="true"/> </c:if>
										</c:forEach>
										<input type="checkbox" id="sbscrptDlvyWd4" name="sbscrptDlvyWd" class="custom-control-input" value="4" <c:if test="${isChecked }">checked</c:if> <c:if test="${IS_DISABLED }">disabled</c:if>/>
										<label class="custom-control-label " for="sbscrptDlvyWd4"><small>수</small></label>
									</div>
									<div class="custom-control custom-checkbox custom-control-inline">
										<c:set var="isChecked" value=""/>
										<c:forEach var="dw" items="${goods.sbscrptDlvyWdList }">
											<c:if test="${dw eq '5' }"> <c:set var="isChecked" value="true"/> </c:if>
										</c:forEach>
										<input type="checkbox" id="sbscrptDlvyWd5" name="sbscrptDlvyWd" class="custom-control-input" value="5" <c:if test="${isChecked }">checked</c:if> <c:if test="${IS_DISABLED }">disabled</c:if>/>
										<label class="custom-control-label " for="sbscrptDlvyWd5"><small>목</small></label>
									</div>
									<div class="custom-control custom-checkbox custom-control-inline">
										<c:set var="isChecked" value=""/>
										<c:forEach var="dw" items="${goods.sbscrptDlvyWdList }">
											<c:if test="${dw eq '6' }"> <c:set var="isChecked" value="true"/> </c:if>
										</c:forEach>
										<input type="checkbox" id="sbscrptDlvyWd6" name="sbscrptDlvyWd" class="custom-control-input" value="6" <c:if test="${isChecked }">checked</c:if> <c:if test="${IS_DISABLED }">disabled</c:if>/>
										<label class="custom-control-label " for="sbscrptDlvyWd6"><small>금</small></label>
									</div>
									<div class="custom-control custom-checkbox custom-control-inline">
										<c:set var="isChecked" value=""/>
										<c:forEach var="dw" items="${goods.sbscrptDlvyWdList }">
											<c:if test="${dw eq '7' }"> <c:set var="isChecked" value="true"/> </c:if>
										</c:forEach>
										<input type="checkbox" id="sbscrptDlvyWd7" name="sbscrptDlvyWd" class="custom-control-input" value="7" <c:if test="${isChecked }">checked</c:if> <c:if test="${IS_DISABLED }">disabled</c:if>/>
										<label class="custom-control-label " for="sbscrptDlvyWd7"><small>토</small></label>
									</div>
									
								</div>
							</div>
							<hr class="sm"/>
							<div class="row">
								<label class="col-3 col-form-label-sm m-0">최소 이용횟수</label>
								<div class="col-3">
									<form:select path="sbscrptMinUseWeek" cssClass="custom-select custom-select-sm" disabled="${IS_DISABLED}">
										<form:option value="">=선택없음=</form:option>
										<c:forEach var="item" begin="1" end="12">
											<form:option value="${item }">${item } 회</form:option>
										</c:forEach>
										<%--
										<form:option value="4">4 주</form:option>
										<form:option value="8">8 주</form:option>
										<c:forEach var="item" begin="12" end="48">
											<form:option value="${item }">${item } 주</form:option>
										</c:forEach>
										--%>
									</form:select>
								</div>
							</div>
						</div>
						<div class="col-sm-5 sbscrptMonth border-left">
							<c:set var="IS_DISABLED" value="false"/>
							<c:set var="DISABLED" value=""/>
							<c:if test="${goods.sbscrptCycleSeCode ne 'MONTH' }">
								<c:set var="IS_DISABLED" value="true"/>
								<c:set var="DISABLED">disabled="disabled"</c:set>
							</c:if>
							<label class="col-form-label col-form-label-sm d-block sbscrptCycleSe <c:if test="${goods.sbscrptCycleSeCode eq 'MONTH' }">active</c:if>" id="month" data-sbscrpt="MONTH">
								<i class="fas fa-check"></i>
								월단위 구독(개월/날짜)
							</label>
							<div class="row">
								<label class="col-3 col-form-label-sm m-0">개월수 선택</label>
								<div class="col-8">
									<c:forEach var="code" items="${dlvyCycleCtList }">
										<c:set var="isChecked" value=""/>
										<div class="custom-control custom-checkbox custom-control-inline">
											<c:forEach var="mt" items="${goods.sbscrptMtCycleList }">
												<c:if test="${mt eq code.code }"> <c:set var="isChecked" value="true"/> </c:if>
											</c:forEach>
											<input type="checkbox" id="sbscrptMtCycle${code.code }" name="sbscrptMtCycle" class="custom-control-input" value="${code.code }" 
												<c:if test="${isChecked }">checked</c:if> <c:if test="${IS_DISABLED }">disabled</c:if> />
											<label class="custom-control-label" for="sbscrptMtCycle${code.code }"><small>${code.codeNm }</small></label>
										</div>
									</c:forEach>
								</div>
							</div>
							<hr class="sm"/>
							
							<div class="row">
								<label for="sbscrptDlvyDay" class="col-3 col-form-label-sm m-0">날짜선택</label>
								<div class="col-4">
									<form:select path="sbscrptDlvyDay" cssClass="custom-select custom-select-sm" disabled="${IS_DISABLED}">
										<form:option value="">=선택없음=</form:option>
										<form:option value="0">결제일</form:option>
										<c:forEach var="item" begin="1" end="31">
											<form:option value="${item }">${item } 일</form:option>
										</c:forEach>
									</form:select>
								</div>
								<div class="col-5"><small class="text-primary">결제일: 구매일 기준 정기 결제</small></div>
							</div>
							<hr class="sm"/>
							
							<div class="row">
								<label for="sbscrptMinUseMt" class="col-3 col-form-label-sm m-0">최소 이용횟수</label>
								<div class="col-4">
									<form:select path="sbscrptMinUseMt" cssClass="custom-select custom-select-sm" disabled="${IS_DISABLED}">
										<form:option value="">=선택없음=</form:option>
										<c:forEach var="item" begin="1" end="12">
											<form:option value="${item }">${item } 회</form:option>
										</c:forEach>
									</form:select>
								</div>
							</div>
							<hr class="sm"/>
							
						</div>
						
					</div>
					<hr class="sm"/>
				</c:otherwise>
				</c:choose>
				<c:if test="${fn:contains(USER_ROLE,'ROLE_ADMIN')}">
					<div class="form-group row">
						<label for="bestOrdr1" class="col-sm-2 col-form-label col-form-label-sm">Best구독 여부</label>
						<div class="col-sm-4">
							<div class="custom-control custom-checkbox">
								<form:checkbox path="bestOrdr" cssClass="custom-control-input" value="1"/>
								<label class="custom-control-label" for="bestOrdr1"><small>Best구독 여부</small></label>
							</div>
						</div>
					</div>
					<hr class="sm"/>
				</c:if>
<%--------------------------------------------------------- 옵션설정 --%>				
				<hr/>
				<h6><i class="fas fa-dice-two"></i> 옵션 설정</h6>
				<hr class="sm"/>
				<c:choose>
				<c:when test="${readOnlyAt eq 'Y'}"> <%--CP 페이지에서 등록완료 상태이면 --%>
					<div class="form-group row">
						<label for="" class="col-sm-2 col-form-label col-form-label-sm">옵션 사용여부</label>
						<div class="col-2">
							<div class="text">
								<c:choose>
									<c:when test="${goods.optnUseAt eq 'Y' }">사용</c:when>
									<c:otherwise>사용안함</c:otherwise>
								</c:choose>
							</div>
						</div>
					</div>
					<hr class="sm"/>
					<div class="form-group row">
						<label for="" class="col-sm-2 col-form-label col-form-label-sm">기본옵션</label>
						<div class="col-2">
							<div class="text">
								<c:choose>
									<c:when test="${goods.dOptnUseAt eq 'Y' }">사용</c:when>
									<c:otherwise>사용안함</c:otherwise>
								</c:choose>
							</div>
						</div>
						<div class="col-8">
							<c:forEach var="item" items="${goods.dGitemList }" varStatus="status">
								<div class="d-flex optn-item mb-1">
									<div class="flex-fill pl-1 pr-1">
										<div class="input-group input-group-sm">
											<div class="input-group-prepend">
												<div class="input-group-text">옵션명(필수)</div>
											</div>
											<input type="text" class="form-control" readonly value="${item.gitemNm }"/>
										</div>
									</div>
									<div class="flex-fill pl-1 pr-1 border-left">
										<div class="input-group input-group-sm">
											<div class="input-group-prepend">
												<div class="input-group-text">가격입력</div>
											</div>
											<input type="text" class="form-control" readonly value="${item.gitemPc }"/>
										</div>
									</div>
									<div class="flex-fill pl-1 pr-1 border-left">
										<div class="input-group input-group-sm">
											<div class="input-group-prepend">
												<div class="input-group-text">품절여부</div>
											</div>
											<input type="text" class="form-control" readonly value="${item.gitemSttusCode eq 'F'?'품절':'-'}"/>
										</div>
									</div>
								</div>
							</c:forEach>
						</div>
					</div>
					<hr class="sm"/>
					<div class="form-group row">
						<label for="" class="col-sm-2 col-form-label col-form-label-sm">추가옵션</label>
						<div class="col-2">
							<div class="text">
								<c:choose>
									<c:when test="${goods.aOptnUseAt eq 'Y' }">사용</c:when>
									<c:otherwise>사용안함</c:otherwise>
								</c:choose>
							</div>
						</div>
						<div class="col-8">
							<c:forEach var="item" items="${goods.aGitemList }" varStatus="status">
								<div class="d-flex optn-item mb-1">
									<div class="flex-fill pl-1 pr-1">
										<div class="input-group input-group-sm">
											<div class="input-group-prepend">
												<div class="input-group-text">옵션명(필수)</div>
											</div>
											<input type="text" class="form-control" readonly value="${item.gitemNm }"/>
										</div>
									</div>
									<div class="flex-fill pl-1 pr-1 border-left">
										<div class="input-group input-group-sm">
											<div class="input-group-prepend">
												<div class="input-group-text">가격입력</div>
											</div>
											<input type="text" class="form-control" readonly value="${item.gitemPc }"/>
										</div>
									</div>
									<div class="flex-fill pl-1 pr-1 border-left">
										<div class="input-group input-group-sm">
											<div class="input-group-prepend">
												<div class="input-group-text">품절여부</div>
											</div>
											<input type="text" class="form-control" readonly value="${item.gitemSttusCode eq 'F'?'품절':'-'}"/>
										</div>
									</div>
								</div>
							</c:forEach>
						</div>
					</div>
					<hr class="sm"/>
					<c:if test="${goods.goodsKndCode eq 'SBS' }">
						<div class="form-group row">
							<label for="" class="col-sm-2 col-form-label col-form-label-sm">첫구독옵션</label>
							<div class="col-2">
								<div class="text">
									<c:choose>
										<c:when test="${goods.fOptnUseAt eq 'Y' }">사용</c:when>
										<c:otherwise>사용안함</c:otherwise>
									</c:choose>
								</div>
							</div>
							<div class="col-8">
								<c:forEach var="item" items="${goods.fGitemList }" varStatus="status">
									<div class="d-flex optn-item mb-1">
										<div class="flex-fill pl-1 pr-1">
											<div class="input-group input-group-sm">
												<div class="input-group-prepend">
													<div class="input-group-text">옵션명(필수)</div>
												</div>
												<input type="text" class="form-control" readonly value="${item.gitemNm }"/>
											</div>
										</div>
										<div class="flex-fill pl-1 pr-1 border-left">
											<div class="input-group input-group-sm">
												<div class="input-group-prepend">
													<div class="input-group-text">가격입력</div>
												</div>
												<input type="text" class="form-control" readonly value="${item.gitemPc }"/>
											</div>
										</div>
										<div class="flex-fill pl-1 pr-1 border-left">
											<div class="input-group input-group-sm">
												<div class="input-group-prepend">
													<div class="input-group-text">품절여부</div>
												</div>
												<input type="text" class="form-control" readonly value="${item.gitemSttusCode eq 'F'?'품절':'-'}"/>
											</div>
										</div>
									</div>
								</c:forEach>
							</div>
						</div>
					</c:if>
					<div class="form-group row">
						<label for="" class="col-sm-2 col-form-label col-form-label-sm">업체 요청 사항</label>
						<div class="col-2">
							<div class="text">
								<c:choose>
									<c:when test="${goods.qOptnUseAt eq 'Y' }">사용</c:when>
									<c:otherwise>사용안함</c:otherwise>
								</c:choose>
							</div>
						</div>
						<div class="col-8">
							<c:forEach var="item" items="${goods.qGitemList }" varStatus="status">
								<div class="d-flex optn-item mb-1">
									<div class="flex-fill pl-1 pr-1">
										<div class="input-group input-group-sm">
											<div class="input-group-prepend">
												<div class="input-group-text">요청사항(필수)</div>
											</div>
											<input type="text" class="form-control" readonly value="${item.gitemNm }"/>
										</div>
									</div>
								</div>
							</c:forEach>
						</div>
					</div>
				</c:when>
				<c:otherwise>
					<div class="form-group row">
						<label for="" class="col-sm-2 col-form-label col-form-label-sm">옵션 사용여부</label>
						<div class="col-sm-10">
							<div class="custom-control custom-control-sm custom-radio custom-control-inline">
								<form:radiobutton path="optnUseAt" cssClass="custom-control-input" value="Y"/>
								<label class="custom-control-label" for="optnUseAt1"><small>사용</small></label>
							</div>
							<div class="custom-control custom-control-sm custom-radio custom-control-inline">
								<form:radiobutton path="optnUseAt" cssClass="custom-control-input" value="N"/>
								<label class="custom-control-label" for="optnUseAt2"><small>사용안함</small></label>
							</div>
						</div>
					</div>
					<hr class="sm"/>
					<div id="optn-type-list" <c:if test="${goods.optnUseAt eq 'N' }">style="display:none;"</c:if>>
						<div class="form-group row goods-optn">
							<label for="" class="col-sm-2 col-form-label col-form-label-sm">기본옵션</label>
							<div class="col-sm-2">
								<div class="custom-control custom-control-sm custom-radio custom-control-inline">
									<form:radiobutton path="dOptnUseAt" cssClass="custom-control-input radio-OptnUseAt" value="Y"/>
									<label class="custom-control-label" for="dOptnUseAt1"><small>사용</small></label>
								</div>
								<div class="custom-control custom-control-sm custom-radio custom-control-inline">
									<form:radiobutton path="dOptnUseAt" cssClass="custom-control-input radio-OptnUseAt" value="N"/>
									<label class="custom-control-label" for="dOptnUseAt2"><small>사용안함</small></label>
								</div>
							</div>
							<div class="col-sm-1 text-right">
								<button type="button" class="btn btn-dark btn-sm btnAddItem" data-target="D" <c:if test="${goods.dOptnUseAt eq 'N'}">disabled="disabled"</c:if> ><i class="fas fa-plus"></i></button>
							</div>
							<div class="col-sm-7 border-left" data-gitem="D">
								<c:forEach var="item" items="${goods.dGitemList }" varStatus="status">
									<div class="d-flex optn-item mb-1">
										<input type="hidden" name="dGitemList[${status.index }].gitemId" class="gitemId" value="${item.gitemId }"/>
										<input type="hidden" name="dGitemList[${status.index }].gitemSn" class="gitemSn" value="${item.gitemSn }"/>
										
										<div class="flex-fill pl-1 pr-1">
											<div class="input-group input-group-sm">
												<div class="input-group-prepend">
													<div class="input-group-text">옵션명(필수)</div>
												</div>
												<input type="text" name="dGitemList[${status.index }].gitemNm" class="form-control gitemNm" placeholder="옵션명" value="${item.gitemNm }"/>
												<div class="input-group-append">
													<button type="button" class="btn btn-secondary btn-sm btnRemoveTxt" data-value=""><i class="fas fa-times"></i></button>
												</div>
											</div>
										</div>
										<div class="flex-fill pl-1 pr-1 border-left">
											<div class="input-group input-group-sm">
												<div class="input-group-prepend">
													<div class="input-group-text">가격입력</div>
												</div>
												<input type="text" name="dGitemList[${status.index }].gitemPc" class="form-control gitemPc inputNumber" value="${item.gitemPc }"/>
												<div class="input-group-append">
													<button type="button" class="btn btn-secondary btn-sm btnRemoveTxt" data-value="0"><i class="fas fa-times"></i></button>
												</div>
											</div>
										</div>
										<div class="flex-fill pl-1 pr-1 border-left">
											<div class="custom-control custom-checkbox custom-control-inline" style="width:50px;">
												<input type="checkbox" class="custom-control-input gitemSttusCode" id="dGitemList.gitemSttusCode${status.index }" name="dGitemList[${status.index }].gitemSttusCode" <c:if test="${item.gitemSttusCode eq 'F' }">checked="checked"</c:if> value="F" />
												<label class="custom-control-label" for="dGitemList.gitemSttusCode${status.index }"><small>품절</small></label>
											</div>
										</div>
										<div class="flex-fill pl-1 pr-1 border-left">
											<a href="${CTX_ROOT }/decms/shop/goods/deleteGoodsItem.json?gitemId=${item.gitemId}" class="btn btn-danger btn-sm btnRemoveGitem"><i class="fas fa-trash"></i></a>
										</div>
									</div>
								</c:forEach>
								<c:if test="${empty goods.dGitemList }">
									<div class="d-flex optn-item mb-1">
										<input type="hidden" name="dGitemList[0].gitemId" class="gitemId" value=""/>
										<input type="hidden" name="dGitemList[0].gitemSn" class="gitemSn" value="1"/>
										
										<div class="flex-fill pl-1 pr-1">
											<div class="input-group input-group-sm">
												<div class="input-group-prepend">
													<div class="input-group-text">옵션명(필수)</div>
												</div>
												<input type="text" name="dGitemList[0].gitemNm" class="form-control gitemNm" placeholder="옵션명"  <c:if test="${goods.dOptnUseAt eq 'N'}">disabled="disabled"</c:if>/>
												<div class="input-group-append">
													<button type="button" class="btn btn-secondary btn-sm btnRemoveTxt" data-value=""><i class="fas fa-times"></i></button>
												</div>
											</div>
										</div>
										<div class="flex-fill pl-1 pr-1 border-left">
											<div class="input-group input-group-sm">
												<div class="input-group-prepend">
													<div class="input-group-text">가격입력</div>
												</div>
												<input type="text" name="dGitemList[0].gitemPc" class="form-control gitemPc inputNumber" <c:if test="${goods.dOptnUseAt eq 'N'}">disabled="disabled"</c:if> value="0"/>
												<div class="input-group-append">
													<button type="button" class="btn btn-secondary btn-sm btnRemoveTxt" data-value="0"><i class="fas fa-times"></i></button>
												</div>
											</div>
										</div>
										<div class="flex-fill pl-1 pr-1 border-left">
											<div class="custom-control custom-checkbox custom-control-inline" style="width:50px;">
												<input type="checkbox" class="custom-control-input gitemSttusCode" id="dGitemList.gitemSttusCode1" name="dGitemList[0].gitemSttusCode" <c:if test="${goods.dOptnUseAt eq 'N'}">disabled="disabled"</c:if> value="F">
												<label class="custom-control-label" for="dGitemList.gitemSttusCode1"><small>품절</small></label>
											</div>
										</div>
										<div class="flex-fill pl-1 pr-1 border-left">
											<button type="button" class="btn btn-danger btn-sm btnRemoveGitem" <c:if test="${goods.dOptnUseAt eq 'N'}">disabled="disabled"</c:if> ><i class="fas fa-trash"></i></button>
										</div>
									</div>
								</c:if>
							</div>
						</div>
						<hr class="sm"/>
						<div class="form-group row goods-optn">
							<label for="" class="col-sm-2 col-form-label col-form-label-sm">추가옵션</label>
							<div class="col-sm-2">
								<div class="custom-control custom-control-sm custom-radio custom-control-inline">
									<form:radiobutton path="aOptnUseAt" cssClass="custom-control-input radio-OptnUseAt" value="Y"/>
									<label class="custom-control-label" for="aOptnUseAt1"><small>사용</small></label>
								</div>
								<div class="custom-control custom-control-sm custom-radio custom-control-inline">
									<form:radiobutton path="aOptnUseAt" cssClass="custom-control-input radio-OptnUseAt" value="N"/>
									<label class="custom-control-label" for="aOptnUseAt2"><small>사용안함</small></label>
								</div>
							</div>
							<div class="col-sm-1 text-right">
								<button type="button" class="btn btn-dark btn-sm btnAddItem" data-target="A" <c:if test="${goods.aOptnUseAt eq 'N'}">disabled="disabled"</c:if> ><i class="fas fa-plus"></i></button>
							</div>
							<div class="col-sm-7 border-left" data-gitem="A">
								<c:forEach var="item" items="${goods.aGitemList }" varStatus="status">
									<div class="d-flex optn-item mb-1">
										<input type="hidden" name="aGitemList[${status.index }].gitemId" class="gitemId" value="${item.gitemId }"/>
										<input type="hidden" name="aGitemList[${status.index }].gitemSn" class="gitemSn" value="${item.gitemSn }"/>
										
										<div class="flex-fill pl-1 pr-1">
											<div class="input-group input-group-sm">
												<div class="input-group-prepend">
													<div class="input-group-text">옵션명(필수)</div>
												</div>
												<input type="text" name="aGitemList[${status.index }].gitemNm" class="form-control gitemNm" placeholder="옵션명" value="${item.gitemNm }"/>
												<div class="input-group-append">
													<button type="button" class="btn btn-secondary btn-sm btnRemoveTxt" data-value=""><i class="fas fa-times"></i></button>
												</div>
											</div>
										</div>
										<div class="flex-fill pl-1 pr-1 border-left">
											<div class="input-group input-group-sm">
												<div class="input-group-prepend">
													<div class="input-group-text">가격입력</div>
												</div>
												<input type="text" name="aGitemList[${status.index }].gitemPc" class="form-control gitemPc inputNumber" value="${item.gitemPc }"/>
												<div class="input-group-append">
													<button type="button" class="btn btn-secondary btn-sm btnRemoveTxt" data-value="0"><i class="fas fa-times"></i></button>
												</div>
											</div>
										</div>
										<div class="flex-fill pl-1 pr-1 border-left">
											<div class="custom-control custom-checkbox custom-control-inline" style="width:50px;">
												<input type="checkbox" class="custom-control-input gitemSttusCode" id="aGitemList.gitemSttusCode${status.index }" name="aGitemList[${status.index }].gitemSttusCode" <c:if test="${item.gitemSttusCode eq 'F' }">checked="checked"</c:if> value="F"/>
												<label class="custom-control-label" for="aGitemList.gitemSttusCode${status.index }"><small>품절</small></label>
											</div>
										</div>
										<div class="flex-fill pl-1 pr-1 border-left">
											<a href="${CTX_ROOT }/decms/shop/goods/deleteGoodsItem.json?gitemId=${item.gitemId}" class="btn btn-danger btn-sm btnRemoveGitem"><i class="fas fa-trash"></i></a>
										</div>
									</div>
								</c:forEach>
								<c:if test="${empty goods.aGitemList }">
									<div class="d-flex optn-item mb-1">
										<input type="hidden" name="aGitemList[0].gitemId" class="gitemId" value=""/>
										<input type="hidden" name="aGitemList[0].gitemSn" class="gitemSn" value="1"/>
										
										<div class="flex-fill pl-1 pr-1">
											<div class="input-group input-group-sm">
												<div class="input-group-prepend">
													<div class="input-group-text">옵션명(필수)</div>
												</div>
												<input type="text" name="aGitemList[0].gitemNm" class="form-control gitemNm" placeholder="옵션명" <c:if test="${goods.aOptnUseAt eq 'N'}">disabled="disabled"</c:if>/>
												<div class="input-group-append">
													<button type="button" class="btn btn-secondary btn-sm btnRemoveTxt" data-value=""><i class="fas fa-times"></i></button>
												</div>
											</div>
										</div>
										<div class="flex-fill pl-1 pr-1 border-left">
											<div class="input-group input-group-sm">
												<div class="input-group-prepend">
													<div class="input-group-text">가격입력</div>
												</div>
												<input type="text" name="aGitemList[0].gitemPc" class="form-control gitemPc inputNumber" <c:if test="${goods.aOptnUseAt eq 'N'}">disabled="disabled"</c:if> value="0"/>
												<div class="input-group-append">
													<button type="button" class="btn btn-secondary btn-sm btnRemoveTxt" data-value="0"><i class="fas fa-times"></i></button>
												</div>
											</div>
										</div>
										<div class="flex-fill pl-1 pr-1 border-left">
											<div class="custom-control custom-checkbox custom-control-inline" style="width:50px;">
												<input type="checkbox" class="custom-control-input gitemSttusCode" id="aGitemList.gitemSttusCode1" name="aGitemList[0].gitemSttusCode" <c:if test="${goods.aOptnUseAt eq 'N'}">disabled="disabled"</c:if> value="F">
												<label class="custom-control-label" for="aGitemList.gitemSttusCode1"><small>품절</small></label>
											</div>
										</div>
										<div class="flex-fill pl-1 pr-1 border-left">
											<button type="button" class="btn btn-danger btn-sm btnRemoveGitem" <c:if test="${goods.aOptnUseAt eq 'N'}">disabled="disabled"</c:if> ><i class="fas fa-trash"></i></button>
										</div>
									</div>
								</c:if>
							</div>
						</div>
						<hr class="sm"/>
						<div class="form-group row goods-optn fOptn">
							<label for="" class="col-sm-2 col-form-label col-form-label-sm">첫구독옵션</label>
							<div class="col-sm-2">
								<div>
									<div class="custom-control custom-control-sm custom-radio custom-control-inline">
										<form:radiobutton path="fOptnUseAt" cssClass="custom-control-input radio-OptnUseAt" value="Y"/>
										<label class="custom-control-label" for="fOptnUseAt1"><small>사용</small></label>
									</div>
									<div class="custom-control custom-control-sm custom-radio custom-control-inline">
										<form:radiobutton path="fOptnUseAt" cssClass="custom-control-input radio-OptnUseAt" value="N"/>
										<label class="custom-control-label" for="fOptnUseAt2"><small>사용안함</small></label>
									</div>
								</div>
								<hr class="sm"/>
								<div>
									<div class="custom-control custom-switch">
										<form:checkbox path="frstOptnEssntlAt" cssClass="custom-control-input" value="Y"/>
										<label class="custom-control-label col-form-label-sm p-0" for="frstOptnEssntlAt1">첫 구독옵션 필수 여부</label>
									</div>
								</div>
							</div>
							<div class="col-sm-1 text-right">
								<button type="button" class="btn btn-dark btn-sm btnAddItem" data-target="F" <c:if test="${goods.fOptnUseAt eq 'N'}">disabled="disabled"</c:if> ><i class="fas fa-plus"></i></button>
							</div>
							<div class="col-sm-7 border-left" data-gitem="F">
								<c:forEach var="item" items="${goods.fGitemList }" varStatus="status">
									<div class="d-flex optn-item mb-1">
										<input type="hidden" name="fGitemList[${status.index }].gitemId" class="gitemId" value="${item.gitemId }"/>
										<input type="hidden" name="fGitemList[${status.index }].gitemSn" class="gitemSn" value="${item.gitemSn }"/>
										
										<div class="flex-fill pl-1 pr-1">
											<div class="input-group input-group-sm">
												<div class="input-group-prepend">
													<div class="input-group-text">옵션명(필수)</div>
												</div>
												<input type="text" name="fGitemList[${status.index }].gitemNm" class="form-control gitemNm" placeholder="옵션명" value="${item.gitemNm }"/>
												<div class="input-group-append">
													<button type="button" class="btn btn-secondary btn-sm btnRemoveTxt" data-value=""><i class="fas fa-times"></i></button>
												</div>
											</div>
										</div>
										<div class="flex-fill pl-1 pr-1 border-left">
											<div class="input-group input-group-sm">
												<div class="input-group-prepend">
													<div class="input-group-text">가격입력</div>
												</div>
												<input type="text" name="fGitemList[${status.index }].gitemPc" class="form-control gitemPc inputNumber" value="${item.gitemPc }"/>
												<div class="input-group-append">
													<button type="button" class="btn btn-secondary btn-sm btnRemoveTxt" data-value="0"><i class="fas fa-times"></i></button>
												</div>
											</div>
										</div>
										<div class="flex-fill pl-1 pr-1 border-left">
											<div class="custom-control custom-checkbox custom-control-inline" style="width:50px;">
												<input type="checkbox" class="custom-control-input gitemSttusCode" id="fGitemList.gitemSttusCode${status.index }" name="fGitemList[${status.index }].gitemSttusCode" <c:if test="${item.gitemSttusCode eq 'F' }">checked="checked"</c:if>  value="F"/>
												<label class="custom-control-label" for="fGitemList.gitemSttusCode${status.index }"><small>품절</small></label>
											</div>
										</div>
										<div class="flex-fill pl-1 pr-1 border-left">
											<a href="${CTX_ROOT }/decms/shop/goods/deleteGoodsItem.json?gitemId=${item.gitemId}" class="btn btn-danger btn-sm btnRemoveGitem"><i class="fas fa-trash"></i></a>
										</div>
									</div>
								</c:forEach>
								<c:if test="${empty goods.fGitemList }">
									<div class="d-flex optn-item mb-1">
										<input type="hidden" name="fGitemList[0].gitemId" class="gitemId" value=""/>
										<input type="hidden" name="fGitemList[0].gitemSn" class="gitemSn" value="1"/>
										
										<div class="flex-fill pl-1 pr-1">
											<div class="input-group input-group-sm">
												<div class="input-group-prepend">
													<div class="input-group-text">옵션명(필수)</div>
												</div>
												<input type="text" name="fGitemList[0].gitemNm" class="form-control gitemNm" placeholder="옵션명" <c:if test="${goods.fOptnUseAt eq 'N'}">disabled="disabled"</c:if>/>
												<div class="input-group-append">
													<button type="button" class="btn btn-secondary btn-sm btnRemoveTxt" data-value=""><i class="fas fa-times"></i></button>
												</div>
											</div>
										</div>
										<div class="flex-fill pl-1 pr-1 border-left">
											<div class="input-group input-group-sm">
												<div class="input-group-prepend">
													<div class="input-group-text">가격입력</div>
												</div>
												<input type="text" name="fGitemList[0].gitemPc" class="form-control gitemPc inputNumber" <c:if test="${goods.fOptnUseAt eq 'N'}">disabled="disabled"</c:if> value="0"/>
												<div class="input-group-append">
													<button type="button" class="btn btn-secondary btn-sm btnRemoveTxt" data-value="0"><i class="fas fa-times"></i></button>
												</div>
											</div>
										</div>
										<div class="flex-fill pl-1 pr-1 border-left">
											<div class="custom-control custom-checkbox custom-control-inline" style="width:50px;">
												<input type="checkbox" class="custom-control-input gitemSttusCode" id="fGitemList.gitemSttusCode1" name="fGitemList[0].gitemSttusCode" <c:if test="${goods.fOptnUseAt eq 'N'}">disabled="disabled"</c:if> value="F">
												<label class="custom-control-label" for="fGitemList.gitemSttusCode1"><small>품절</small></label>
											</div>
										</div>
										<div class="flex-fill pl-1 pr-1 border-left">
											<button type="button" class="btn btn-danger btn-sm btnRemoveGitem" <c:if test="${goods.fOptnUseAt eq 'N'}">disabled="disabled"</c:if> ><i class="fas fa-trash"></i></button>
										</div>
									</div>
								</c:if>
							</div>
						</div>
						<hr class="sm"/>
						<div class="form-group row goods-optn">
							<label for="" class="col-sm-2 col-form-label col-form-label-sm">업체 요청 사항</label>
							<div class="col-sm-2">
								<div class="custom-control custom-control-sm custom-radio custom-control-inline">
									<form:radiobutton path="qOptnUseAt" cssClass="custom-control-input radio-OptnUseAt" value="Y"/>
									<label class="custom-control-label" for="qOptnUseAt1"><small>사용</small></label>
								</div>
								<div class="custom-control custom-control-sm custom-radio custom-control-inline">
									<form:radiobutton path="qOptnUseAt" cssClass="custom-control-input radio-OptnUseAt" value="N"/>
									<label class="custom-control-label" for="qOptnUseAt2"><small>사용안함</small></label>
								</div>
							</div>
							<div class="col-sm-1 text-right">
								<button type="button" class="btn btn-dark btn-sm btnAddItem" data-target="Q" <c:if test="${goods.qOptnUseAt eq 'N'}">disabled="disabled"</c:if> ><i class="fas fa-plus"></i></button>
							</div>
							<div class="col-sm-7 border-left" data-gitem="Q">
								<c:forEach var="item" items="${goods.qGitemList }" varStatus="status">
									<div class="d-flex optn-item mb-1">
										<input type="hidden" name="qGitemList[${status.index }].gitemId" class="gitemId" value="${item.gitemId }"/>
										<input type="hidden" name="qGitemList[${status.index }].gitemSn" class="gitemSn" value="${item.gitemSn }"/>
										
										<div class="flex-fill pl-1 pr-1">
											<div class="input-group input-group-sm">
												<div class="input-group-prepend">
													<div class="input-group-text">요청사항(필수)</div>
												</div>
												<input type="text" name="qGitemList[${status.index }].gitemNm" class="form-control gitemNm" placeholder="요청사항" value="${item.gitemNm }"/>
												<div class="input-group-append">
													<button type="button" class="btn btn-secondary btn-sm btnRemoveTxt" data-value=""><i class="fas fa-times"></i></button>
												</div>
											</div>
										</div>
										<div class="flex-shrink-1 pl-1 pr-1 border-left">
											<a href="${CTX_ROOT }/decms/shop/goods/deleteGoodsItem.json?gitemId=${item.gitemId}" class="btn btn-danger btn-sm btnRemoveGitem"><i class="fas fa-trash"></i></a>
										</div>
									</div>
								</c:forEach>
								<c:if test="${empty goods.qGitemList }">
									<div class="d-flex optn-item mb-1">
										<input type="hidden" name="qGitemList[0].gitemId" class="gitemId" value=""/>
										<input type="hidden" name="qGitemList[0].gitemSn" class="gitemSn" value="1"/>
										
										<div class="flex-fill pl-1 pr-1">
											<div class="input-group input-group-sm">
												<div class="input-group-prepend">
													<div class="input-group-text">요청사항(필수)</div>
												</div>
												<input type="text" name="qGitemList[0].gitemNm" class="form-control gitemNm" placeholder="요청사항" <c:if test="${goods.qOptnUseAt eq 'N'}">disabled="disabled"</c:if>/>
												<div class="input-group-append">
													<button type="button" class="btn btn-secondary btn-sm btnRemoveTxt" data-value=""><i class="fas fa-times"></i></button>
												</div>
											</div>
										</div>
										<div class="flex-shrink-1 pl-1 pr-1 border-left">
											<button type="button" class="btn btn-danger btn-sm btnRemoveGitem" <c:if test="${goods.qOptnUseAt eq 'N'}">disabled="disabled"</c:if> ><i class="fas fa-trash"></i></button>
										</div>
									</div>
								</c:if>
							</div>
						</div>
						<hr class="sm"/>
					</div>
				</c:otherwise>
				</c:choose>
				<hr/>


<%------------------------------------------------------- 상품정보 --%>

				<h6><i class="fas fa-dice-three"></i> 상품정보 <small>(상세)</small></h6>
				<hr class="sm"/>
				
				<div id="goods-coupon" class="mb-2" <c:if test="${goods.goodsKndCode ne 'CPN' }">style="display:none"</c:if>> <%-- 쿠폰 목록 관리 --%>
					<div class="row mb-2">
						<div class="col-sm-6">
							<button type="button" id="btnCouponDelete" class="btn btn-danger btn-sm"><i class="fas fa-check"></i> 쿠폰선택삭제</button>
							<button type="button" id="btnCouponAppend" class="btn btn-dark btn-sm"><i class="fas fa-plus"></i> 쿠폰추가</button>
						</div>
						<div class="col-sm-6 text-right">
							<a href="${CTX_ROOT }/resources/decms/shop/goods/info/html/coupon_sample.xlsx" class="btn btn-outline-info btn-sm">
								<i class="fas fa-file-excel"></i> 쿠폰 양식 파일 다운로드</a>
							<button type="button" id="btnUploadCouponExcel" class="btn btn-success btn-sm"><i class="fas fa-file-upload"></i> 쿠폰 엑셀파일 업로드</button>
						</div>
					</div>
					<div id="data-coupon-grid"></div>
					<div id="data-coupon-grid-pagination"></div>

				</div>

				<div class="form-group row">
					<label for="mvpSourcCn" class="col-sm-6 col-form-label col-form-label-sm">영상 업로드(유튜브)</label>
					<div class="col-sm-6 text-right"><a href="https://support.google.com/youtube/answer/171780?hl=ko" class="text-dark" target="_blank"><i class="fas fa-external-link-square-alt"></i> 도움말</a></div>
					<div class="col-sm-6" style="position:relative; height:315px;">
						<div id="mvpSourcEditor"><c:out value="${goods.mvpSourcCn }"/></div>
						<form:textarea path="mvpSourcCn" rows="1" />
					</div>
					<div class="col-sm-6">
						<div id="viewMvpSource">
							<c:out value="${goods.mvpSourcCn }" escapeXml="false"/>
						</div>
					</div>
				</div>
				<hr class="sm"/>
				
				<div class="form-group row">
					<label for="goodsCn" class="col-sm-12 col-form-label col-form-label-sm">상품설명</label>
					<form:textarea path="goodsCn" cssClass="col-sm-12 summernote" />
				</div>
				<hr class="sm"/>
				
				<div class="form-group row">
					<label for="" class="col-sm-2 col-form-label col-form-label-sm">이벤트 이미지
					<p>※이벤트 관리 페이지에서 진행중인 이벤트 상품으로 등록이 되어 있을 경우에만 상품 상세 페이지에 노출됩니다.</p>
					</label>
					<div class="col-sm-10">
						<p class="text-danger"><small>[SIZE : 950 ~ ]</small></p>
						<div id="goods-event-image-list" class="row">
							<c:forEach var="item"  items="${goods.evtImageList}" varStatus="status">
								<div class="img-item col-sm-3">
									<div class="img-item-box">
										<input type="hidden" name="evtImageList[${status.index }].goodsImageNo" class="goodsImageNo" value="${item.goodsImageNo }"/>
										<input type="hidden" name="evtImageList[${status.index }].goodsImageSeCode" class="goodsImageSeCode" value="EVT"/>
										<input type="hidden" name="evtImageList[${status.index }].goodsImageSn" class="goodsImageSn" value="${item.goodsImageSn}"/>
										<input type="hidden" name="evtImageList[${status.index }].goodsImagePath" class="goodsImagePath" value="${item.goodsImagePath }"/>
										<input type="hidden" name="evtImageList[${status.index }].goodsImageThumbPath" class="goodsImageThumbPath" value="${item.goodsImageThumbPath }"/>
										<input type="hidden" name="evtImageList[${status.index }].goodsLrgeImagePath" class="goodsLrgeImagePath" value="${item.goodsLrgeImagePath }"/>
										<div class="evt-img-header d-flex">
											<div class="flex-fill pl-2 goods-num">${status.index+1}</div>
											<div class="flex-fill text-right">
												<a href="${item.goodsImagePath }" class="btn btn-dark btn-sm rounded-0 btnImgView" title="이미지 원본보기"><i class="fas fa-eye"></i></a>
												<a href="${CTX_ROOT }/decms/shop/goods/deleteGoodsImage.json?goodsImageNo=${item.goodsImageNo}" 
													class="btn btn-dark btn-sm rounded-0 btnDeleteImg" title="삭제" data-se-code="EVT"><i class="fas fa-times"></i></a>
											</div>
										</div>
										<img src="${item.goodsImageThumbPath }"/>
									</div>
								</div>
							</c:forEach>
						</div>
						<div class="text-right">
							<button type="button" class="btn btn-outline-dark btn-sm btnAddImg" title="이미지 추가" data-target="#goods-event-image-list"
								data-src="${CTX_ROOT }/decms/shop/goods/uploadEvtImageFile.json"><i class="fas fa-plus"></i></button>
						</div>
					</div>
				</div>

				<hr class="sm"/>

				<div class="form-group row">
					<label for="" class="col-sm-2 col-form-label col-form-label-sm required">상품 이미지</label>
					<div class="col-sm-10">
						<p class="text-danger"><small>[SIZE : 1000, 400(자동생성), 600(자동생성)]</small></p>
						<div id="goods-image-list" class="row">
							<c:forEach var="item" items="${goods.goodsImageList }" varStatus="status">
								<div class="img-item col-sm-3">
									<div class="img-item-box">
										<input type="hidden" name="goodsImageList[${status.index }].goodsImageNo" class="goodsImageNo" value="${item.goodsImageNo }"/>
										<input type="hidden" name="goodsImageList[${status.index }].goodsImageSeCode" class="goodsImageSeCode" value="GNR"/>
										<input type="hidden" name="goodsImageList[${status.index }].goodsImageSn" class="goodsImageSn" value="${item.goodsImageSn}"/>
										<input type="hidden" name="goodsImageList[${status.index }].goodsImagePath" class="goodsImagePath" value="${item.goodsImagePath }"/>
										<input type="hidden" name="goodsImageList[${status.index }].goodsImageThumbPath" class="goodsImageThumbPath" value="${item.goodsImageThumbPath }"/>
										<input type="hidden" name="goodsImageList[${status.index }].goodsLrgeImagePath" class="goodsLrgeImagePath" value="${item.goodsLrgeImagePath }"/>
										<input type="hidden" name="goodsImageList[${status.index }].goodsMiddlImagePath" class="goodsMiddlImagePath" value="${item.goodsMiddlImagePath }"/>
										<input type="hidden" name="goodsImageList[${status.index }].goodsSmallImagePath" class="goodsSmallImagePath" value="${item.goodsSmallImagePath }"/>
										<div class="img-header d-flex">
											<div class="flex-fill pl-2 goods-num">${status.index+1}</div>
											<div class="flex-fill text-right">
												<a href="${item.goodsImagePath }" class="btn btn-dark btn-sm rounded-0 btnImgView" title="이미지 원본보기"><i class="fas fa-eye"></i></a>
												<a href="${CTX_ROOT }/decms/shop/goods/deleteGoodsImage.json?goodsImageNo=${item.goodsImageNo}" 
													class="btn btn-dark btn-sm rounded-0 btnDeleteImg" title="삭제" data-se-code="GNR"><i class="fas fa-times"></i></a>
											</div>
										</div>
										<img src="${item.goodsImageThumbPath }"/>
									</div>
								</div>
							</c:forEach>
						</div>
						<div class="text-right">
							<button type="button" class="btn btn-outline-dark btn-sm btnAddImg" title="이미지 추가" data-target="#goods-image-list"
								data-src="${CTX_ROOT }/decms/shop/goods/uploadGnrImageFile.json"><i class="fas fa-plus"></i></button>
						</div>
					</div>
				</div>
				
				<hr class="sm"/>

				<div class="form-group row">
					<label for="" class="col-sm-2 col-form-label col-form-label-sm required">설명 이미지</label>
					<div class="col-sm-10">
						<p class="text-danger"><small>[SIZE : 950 ~ ]</small></p>
						<div id="goods-dc-image-list" class="row">
							<c:forEach var="item"  items="${goods.gdcImageList }" varStatus="status">
								<div class="img-item col-sm-3">
									<div class="gdc-img-item-box">
										<input type="hidden" name="gdcImageList[${status.index }].goodsImageNo" class="goodsImageNo" value="${item.goodsImageNo }"/>
										<input type="hidden" name="gdcImageList[${status.index }].goodsImageSeCode" class="goodsImageSeCode" value="GDC"/>
										<input type="hidden" name="gdcImageList[${status.index }].goodsImageSn" class="goodsImageSn" value="${item.goodsImageSn}"/>
										<input type="hidden" name="gdcImageList[${status.index }].goodsImagePath" class="goodsImagePath" value="${item.goodsImagePath }"/>
										<input type="hidden" name="gdcImageList[${status.index }].goodsImageThumbPath" class="goodsImageThumbPath" value="${item.goodsImageThumbPath }"/>
										<input type="hidden" name="gdcImageList[${status.index }].goodsLrgeImagePath" class="goodsLrgeImagePath" value="${item.goodsLrgeImagePath }"/>
										<div class="gdc-img-header d-flex">
											<div class="flex-fill pl-2 goods-num">${status.index+1}</div>
											<div class="flex-fill text-right">
												<a href="${item.goodsImagePath }" class="btn btn-dark btn-sm rounded-0 btnImgView" title="이미지 원본보기"><i class="fas fa-eye"></i></a>
												<a href="${CTX_ROOT }/decms/shop/goods/deleteGoodsImage.json?goodsImageNo=${item.goodsImageNo}" 
													class="btn btn-dark btn-sm rounded-0 btnDeleteImg" title="삭제" data-se-code="GDC"><i class="fas fa-times"></i></a>
											</div>
										</div>
										<img src="${item.goodsImageThumbPath }"/>
									</div>
								</div>
							</c:forEach>
						</div>
						<div class="text-right">
							<button type="button" class="btn btn-outline-dark btn-sm btnAddImg" title="이미지 추가" data-target="#goods-dc-image-list"
								data-src="${CTX_ROOT }/decms/shop/goods/uploadGdcImageFile.json"><i class="fas fa-plus"></i></button>
						</div>
					</div>
				</div>
				
				<hr class="sm"/>
				
				<div class="form-group row">
					<label for="" class="col-sm-2 col-form-label col-form-label-sm">관련 추천 상품</label>
					<div class="col-sm-10">
						<div class="text-right mb-2">
							<button type="button" class="btn btn-dark btn-sm btnAddRecomend" data-target="#recomendListModal"><i class="fas fa-plus"></i> 추천상품</button>
						</div>
						<div id="recomend-list" class="row">
							<c:forEach var="item"  items="${goods.goodsRecomendList }" varStatus="status">
								<div class="recomend-item col-sm-3">
									<input type="hidden" name="goodsRecomendList[${status.index }].goodsRecomendNo" class="goodsRecomendNo" value="${item.goodsRecomendNo }"/>
									<input type="hidden" name="goodsRecomendList[${status.index }].recomendGoodsSn" class="recomendGoodsSn" value="${item.recomendGoodsSn }"/>
									<input type="hidden" name="goodsRecomendList[${status.index }].recomendGoodsId" class="recomendGoodsId" value="${item.recomendGoodsId }"/>
									<figure class="figure">
										<div class="figure-header d-flex">
											<div class="figure-num flex-fill figure-num pl-2">${status.index+1 }</div>
											<div class="flex-fill text-right">
												<a href="${item.goodsTitleImagePath }" class="btn btn-dark btn-sm rounded-0 btnImgView" title="이미지 원본보기"><i class="fas fa-eye"></i></a>
												<a href="${CTX_ROOT }/decms/shop/goods/deleteRecomend.json?goodsRecomendNo=${item.goodsRecomendNo}" class="btn btn-dark btn-sm rounded-0 btnDeleteRecomend" title="삭제"><i class="fas fa-times"></i></a>
											</div>
										</div>
										<img src="${item.goodsTitleImageThumbPath }" class="figure-img img-fluid rounded" alt=""/>
										<figcaption class="figure-caption"><c:out value="${item.goodsNm }"/></figcaption>
										<div>
											<strong class="recomend-pc"><c:out value="${item.goodsPc }"/></strong> 원
										</div>
									</figure>
								</div>
							</c:forEach>
						</div>
					</div>
				</div>
				<hr class="sm"/>

				<hr/>

				<h6><i class="fas fa-dice-four"></i> 가격 및 배송비 정보</h6>
				<hr class="sm"/>
				
				
				<div class="form-group row">
					<label for="goodsPc" class="col-sm-2 col-form-label col-form-label-sm">판매가격</label>
					<div class="col-sm-3">
						<div class="input-group input-group-sm">
							<div class="input-group-prepend">
								<div class="input-group-text">판매가</div>
							</div>
							<form:input path="goodsPc" cssClass="form-control form-control-sm inputNumber" readonly="${IS_READ_ONLY }"/>
						</div>
					</div>
					<div class="col-sm-3">
						<div class="input-group input-group-sm">
							<div class="input-group-prepend">
								<div class="input-group-text">수수료율</div>
							</div>
							<form:input path="goodsFeeRate" cssClass="form-control form-control-sm" readonly="${IS_READ_ONLY }"/>
							<div class="input-group-append">
								<div class="input-group-text">%</div>
							</div>
						</div>
					</div>
					<div class="col-sm-3">
						<c:choose>
							<c:when test="${fn:contains(USER_ROLE,'ROLE_ADMIN')}">
								<div class="input-group input-group-sm">
									<div class="input-group-prepend">
										<div class="input-group-text">공급가</div>
									</div>
									<form:input path="goodsSplpc" cssClass="form-control form-control-sm" readonly="true"/>
								</div>						
							</c:when>
							<c:otherwise>
								<form:hidden path="goodsSplpc" cssClass="form-control form-control-sm" readonly="true"/>
								<!-- <p class="vat-font">※VAT 별도</p> -->
							</c:otherwise>
						</c:choose>
					</div>
				</div>
				<hr class="sm"/>
				
				<div class="form-group row">
					<label for="exprnPc" class="col-sm-2 col-form-label col-form-label-sm">1회 체험</label>
					<div class="col-sm-3">
						<div class="input-group input-group-sm">
							<div class="input-group-prepend">
								<div class="input-group-text">
								<c:if test="${goods.exprnUseAt eq 'Y'}"> 
									<form:checkbox path="exprnUseAt" cssClass="mr-2" value="Y" checked="checked" />
								</c:if>
								<c:if test="${goods.exprnUseAt eq 'N' or empty goods.exprnUseAt}"> 
									<form:checkbox path="exprnUseAt" cssClass="mr-2" value="Y" />
								</c:if>
									<label for="expsrUseAt" class="mb-0">설정</label>
								</div>
							</div>
							<c:if test="${goods.exprnUseAt eq 'Y'}"> 
								<form:input path="exprnPc" cssClass="form-control form-control-sm inputNumber" />
							</c:if>
							<c:if test="${goods.exprnUseAt eq 'N' or empty goods.exprnUseAt}"> 
								<form:input path="exprnPc" cssClass="form-control form-control-sm inputNumber" disabled="true" value="0" />
							</c:if>
						</div>
						<div class="col-sm-10">
							<p><small>※  (-) 이면 할인되며, (+)이면 상품가격이 추가됩니다.</br>구매수량당 적용됩니다.</small></br>
							</p>
						</div>
					</div>
				</div>
				<hr class="sm"/>
				<%-- <div class="form-group row">
					<label for="b2cPc" class="col-sm-2 col-form-label col-form-label-sm">B2C 가격</label>
					<form:input path="b2cPc" cssClass="col-sm-2 form-control form-control-sm inputNumber"/>
				</div>
				<hr class="sm"/> --%>
				
				<div class="form-group row">
					<label for="goodsRsrvmney" class="col-sm-2 col-form-label col-form-label-sm">상품 적립금</label>
					<div class="col-sm-2">
						<form:input path="goodsRsrvmney" cssClass="form-control form-control-sm inputNumber" readonly="${IS_READ_ONLY }"/>
					</div>
					<div class="col-sm-3">
						<div class="input-group input-group-sm">
							<div class="input-group-prepend">
								<div class="input-group-text">
									<form:checkbox path="goodsRsrvmneyRateAt" cssClass="mr-2" value="Y" disabled="${IS_READ_ONLY }"/>
									<label for="goodsRsrvmneyRateAt1" class="mb-0">% 설정</label>
								</div>
							</div>
							<form:input path="goodsRsrvmneyRate" cssClass="form-control form-control-sm" readonly="${IS_READ_ONLY }"/>
							<div class="input-group-append">
								<span class="input-group-text">%</span>
							</div>
						</div>
					</div>
				</div>
				<hr class="sm"/>
				
				<%-- <div class="form-group row">
					<label for="b2cRsrvmney" class="col-sm-2 col-form-label col-form-label-sm">B2C 적립금</label>
					<form:input path="b2cRsrvmney" cssClass="col-sm-2 form-control form-control-sm inputNumber"/>
					<div class="col-sm-3">
						<div class="input-group input-group-sm">
							<div class="input-group-prepend">
								<div class="input-group-text">
									<form:checkbox path="b2cRsrvmneyRateAt" cssClass="mr-2" value="Y"/>
									<label for="b2cRsrvmneyRateAt1" class="mb-0">% 설정</label>
								</div>
							</div>
							<form:input path="wmallRsrvmneyRate" cssClass="form-control form-control-sm"/>
							<div class="input-group-append">
								<span class="input-group-text">%</span>
							</div>
						</div>
					</div>
				</div>
				<hr class="sm"/> --%>
				
				<div class="form-group row">
					<label for="mrktPc" class="col-sm-2 col-form-label col-form-label-sm">시중가</label>
					<div class="col-sm-2">
						<form:input path="mrktPc" cssClass="form-control form-control-sm inputNumber"/>
					</div>
					&nbsp&nbsp&nbsp&nbsp
					<c:if test="${goods.mrktUseAt eq 'Y'}"> 
						<form:checkbox path="mrktUseAt" cssClass="mr-2" value="Y" checked="checked"/>
					</c:if>
					<c:if test="${goods.mrktUseAt eq 'N' or empty goods.mrktUseAt}"> 
						<form:checkbox path="mrktUseAt" cssClass="mr-2" value="Y"/>
					</c:if>
					<small>※ 시중가 노출 설정(고객에게 할인 전 금액으로표시)</small>
				</div>
				<hr class="sm"/>
				
				<%-- <div class="form-group row">
					<label for="goodsFee" class="col-sm-2 col-form-label col-form-label-sm">상품 수수료</label>
					<form:input path="goodsFee" cssClass="col-sm-2 form-control form-control-sm inputNumber"/>
				</div>
				<hr class="sm"/> --%>
				
				<div class="form-group row">
					<label for="taxtSeCode" class="col-sm-2 col-form-label col-form-label-sm">과세/면세</label>
					<div class="col-sm-10">
						<div class="form-check form-check-inline">
							<form:radiobutton path="taxtSeCode" cssClass="form-check-input" value="TA01" disabled="${IS_READ_ONLY }"/>
							<label class="form-check-label" for="taxtSeCode1"><small>과세</small></label>
						</div>
						<div class="form-check form-check-inline">
							<form:radiobutton path="taxtSeCode" cssClass="form-check-input" value="TA02" disabled="${IS_READ_ONLY }"/>
							<label class="form-check-label" for="taxtSeCode2"><small>면세</small></label>
						</div>
					</div>
				</div>
				<hr class="sm"/>
				<div class="form-group row">
					<label for="hdryId" class="col-sm-2 col-form-label col-form-label-sm required">택배사</label>
					<div class="col-sm-3">
						<form:select path="hdryId" cssClass="custom-select custom-select-sm">
							<form:option value="">선택</form:option>
							<c:forEach var="hdry" items="${hdryList}">
								<form:option value="${hdry.hdryId}">${hdry.hdryNm}</form:option>
							</c:forEach>
						</form:select>
					</div>
				</div>
				<hr class="sm"/>
				<div class="form-group row">
					<label for="" class="col-sm-2 col-form-label col-form-label-sm">배송정책</label>
					<div class="col-sm-6">
						<div class="row">
							<div class="col-sm-4">
								<div class="form-check form-check-inline">
									<form:radiobutton path="dlvyPolicySeCode" cssClass="dlvyPolicySeCode form-check-input" value="DP01" data-policy-cn="${cmpnyDlvyPolicyCn }" disabled="${IS_READ_ONLY }"/>
									<label class="form-check-label" for="dlvyPolicySeCode1"><small>판매자 정책</small></label>
								</div>
							</div>
							<div class="col-sm-8">
								<p><small>판매자의 기본정책을 따릅니다.</small><br/>
									<small></small>
								</p>
							</div>
						</div>
						<hr class="sm"/>
						
						<div class="row">
							<div class="col-sm-4">
								<div class="form-check form-check-inline">
									<form:radiobutton path="dlvyPolicySeCode" cssClass="dlvyPolicySeCode form-check-input" value="DP02" data-policy-cn="${dlvyPolicyCn }" disabled="${IS_READ_ONLY }"/>
									<label class="form-check-label" for="dlvyPolicySeCode2"><small>기본 정책</small></label>
								</div>
							</div>
							<div class="col-sm-8">
								<p><small>쇼핑몰의 기본정책을 따릅니다.</small><br/>
									<small></small>
								</p>
							</div>
						</div>
						<hr class="sm"/>

						<div class="row">
							<div class="col-sm-4">
								<div class="form-check form-check-inline">
									<form:radiobutton path="dlvyPolicySeCode" cssClass="dlvyPolicySeCode form-check-input" value="DP03" data-policy-cn="" disabled="${IS_READ_ONLY }"/>
									<label class="form-check-label" for="dlvyPolicySeCode3"><small>상품별 정책</small></label>
								</div>
							</div>
							<div class="col-sm-8">
								<p></p>
							</div>
						</div>
						<hr class="sm"/>

					</div>
					<div class="col-sm-4">
						<c:set var="dlvyPolicyCnAt" value="true"/>
						<c:if test="${goods.dlvyPolicySeCode eq 'DP03' }">
							<c:set var="dlvyPolicyCnAt" value="false"/>
						</c:if>
						<form:textarea path="dlvyPolicyCn" cssClass="form-control form-control-sm h-100" readonly="${dlvyPolicyCnAt }" placeholder="ex) 해당 상품은 10~14일 이내 배송됩니다."/>
					</div>
				</div>
				<hr class="sm"/>
				
				<div class="form-group row">
					<label for="dlvySeCode" class="col-sm-2 col-form-label col-form-label-sm required">도서산간 추가배송비</label>
					<div class="col-sm-4">
						<div class="input-group input-group-sm">
							<%-- <form:input path="islandDlvyPc" cssClass="form-control form-control-sm inputNumber" readonly="${IS_READ_ONLY }"/> --%>
							<form:input path="islandDlvyPc" cssClass="form-control form-control-sm inputNumber"/>
							<div class="input-group-append">
								<span class="input-group-text">원</span>
							</div>
						</div>
					</div>
					<div class="col-sm-4">
						<div class="input-group input-group-sm">
							<div class="input-group-prepend">
								<span class="input-group-text">제주도 배송비</span>
							</div>
							<%-- <form:input path="jejuDlvyPc" cssClass="form-control form-control-sm inputNumber" readonly="${IS_READ_ONLY }"/> --%>
							<form:input path="jejuDlvyPc" cssClass="form-control form-control-sm inputNumber" />
							<div class="input-group-append">
								<span class="input-group-text">원</span>
							</div>
						</div>
					</div>
				</div>
				
				<hr class="sm"/>
				
				
				<div class="form-group row">
					<label for="exprnPc" class="col-sm-2 col-form-label col-form-label-sm">복수구매할인</label>
					<div class="col-sm-3">
						<div class="input-group input-group-sm">
							<div class="input-group-prepend">
								<div class="input-group-text">
								<c:if test="${goods.compnoDscntUseAt eq 'Y'}"> 
									<form:checkbox path="compnoDscntUseAt" cssClass="mr-2" value="Y" checked="checked"/>
								</c:if>
								<c:if test="${goods.compnoDscntUseAt eq 'N' or empty goods.compnoDscntUseAt}"> 
									<form:checkbox path="compnoDscntUseAt" cssClass="mr-2" value="Y"/>
								</c:if>
									<label for="compnoDscntUseAt" class="mb-0">설정</label>
								</div>
							</div>
							<div class="input-group-append">
								<span class="input-group-text">2개 이상부터 개당</span>
							</div>
							<c:if test="${goods.compnoDscntUseAt eq 'Y'}"> 
								<form:input path="compnoDscntPc" cssClass="form-control form-control-sm inputNumber" value="${-goods.compnoDscntPc}"/>
							</c:if>
							<c:if test="${goods.compnoDscntUseAt eq 'N' or empty goods.compnoDscntUseAt}"> 
								<form:input path="compnoDscntPc" cssClass="form-control form-control-sm inputNumber" disabled="true" value="0"/>
							</c:if>
							<div class="input-group-append">
								<span class="input-group-text">원 할인</span>
							</div>
						</div>
					</div>
				</div>
				
				<hr class="sm"/>
				
				
				<div class="form-group row">
					<label for="dlvySeCode" class="col-sm-2 col-form-label col-form-label-sm">배송비</label>
					<div class="col-sm-4">
						<div>
							<div class="form-check form-check-inline">
								<form:radiobutton path="dlvySeCode" cssClass="form-check-input" value="DS01" disabled="${IS_READ_ONLY }"/>
								<label class="form-check-label" for="dlvySeCode1"><small>선불</small></label>
							</div>
							<div class="form-check form-check-inline">
								<form:radiobutton path="dlvySeCode" cssClass="form-check-input" value="DS02" disabled="${IS_READ_ONLY }"/>
								<label class="form-check-label" for="dlvySeCode2"><small>착불</small></label>
							</div>
							<div class="form-check form-check-inline">
								<form:radiobutton path="dlvySeCode" cssClass="form-check-input" value="DS03" disabled="${IS_READ_ONLY }"/>
								<label class="form-check-label" for="dlvySeCode3"><small>무료</small></label>
							</div>
						</div>
					</div>
					<div class="col-sm-3">
						<div class="input-group input-group-sm">
							<c:set var="dlvyPcReadonly" value="false"/>
							<c:choose>
								<c:when test="${goods.dlvySeCode eq 'DS03'}"> <c:set var="dlvyPcReadonly" value="true"/> </c:when>
								<c:otherwise> <c:set var="dlvyPcReadonly" value="${IS_READ_ONLY }"/> </c:otherwise>
							</c:choose>
							<form:input path="dlvyPc" cssClass="form-control form-control-sm inputNumber" readonly="${dlvyPcReadonly }"/>
							<div class="input-group-append">
								<span class="input-group-text">원</span>
							</div>
						</div>
					</div>
					<div class="col-sm-3">
						<div class="input-group input-group-sm">
							<form:input path="freeDlvyPc" cssClass="form-control form-control-sm inputNumber" readonly="${dlvyPcReadonly }"/>
							<div class="input-group-append">
								<span class="input-group-text">원 이상 주문 시 무료</span>
							</div>
						</div>
					</div>
				</div>
				<hr class="sm"/>

				<div class="form-group row">
					<label for="bundleDlvyAt" class="col-sm-2 col-form-label col-form-label-sm">묶음배송</label>
					<div class="col-sm-4">
						<form:select path="bundleDlvyAt" cssClass="custom-select custom-select-sm" disabled="${IS_READ_ONLY }">
							<form:option value="Y">가능</form:option>
							<form:option value="N">불가능</form:option>
						</form:select>
					</div>
					<div class="col-sm-4">
						<div class="input-group input-group-sm">
							<div class="input-group-prepend">
								<span class="input-group-text">매</span>
							</div>
							<form:select path="bundleDlvyCo" cssClass="custom-select custom-select-sm" disabled="${IS_READ_ONLY }">
								<c:forEach var="item" begin="1" end="10">
									<form:option value="${item }">${item } 개</form:option>
								</c:forEach>
							</form:select>
							<div class="input-group-append">
								<span class="input-group-text">마다</span>
							</div>
						</div>
					</div>
				</div>
				<hr class="sm"/>
				
				<form:hidden path="sbscrptSetleDay" /> <%--구독배송기준결제일 숨김처리 --%>
				<%-- 
				<div class="form-group row">
					<label for="sbscrptSetleDay" class="col-sm-2 col-form-label col-form-label-sm">구독배송일기준결제일</label>
					<div class="col-sm-4">
						<form:select path="sbscrptSetleDay" cssClass="custom-select custom-select-sm">
							<c:forEach var="item" begin="0" end="7">
								<form:option value="${item }">${item } 일</form:option>
							</c:forEach>
						</form:select>
					</div>
					<div class="col-sm-6">
						<small class="text-danger">ex) 3일 : 배송 3일 전 결제</small>
					</div>
				</div>
				<hr class="sm"/>
				 --%>
				<c:if test="${fn:contains(USER_ROLE,'ROLE_EMPLOYEE') }">
					<div class="form-group row">
						<label for="registSttusCode" class="col-sm-2 col-form-label col-form-label-sm">등록상태</label>
						<div class="col-sm-4">
							<form:select path="registSttusCode" cssClass="custom-select custom-select-sm">
								<c:forEach var="item" items="${registSttusCodeList }" varStatus="status">
									<form:option value="${item.code }">${item.codeNm }</form:option>
								</c:forEach>
							</form:select>
						</div>
					</div>
					<hr class="sm"/>
				</c:if>

				<div class="text-right mt-3">
					<c:url var="listUrl" value="/decms/shop/goods/goodsManage.do">
						<c:param name="menuId" value="${param.menuId }"/>
						<c:param name="searchCmpnyId" value="${searchVO.searchCmpnyId }"/>
						<c:param name="searchCmpnyNm" value="${searchVO.searchCmpnyNm }"/>
						<c:param name="searchCateCode1" value="${searchVO.searchCateCode1 }"/>
						<c:param name="searchCateCode2" value="${searchVO.searchCateCode2 }"/>
						<c:param name="searchCateCode3" value="${searchVO.searchCateCode3 }"/>
						<c:param name="searchRegistSttusCode" value="${searchVO.searchRegistSttusCode }"/>
						<c:param name="searchCondition" value="${searchVO.searchCondition }"/>
						<c:param name="searchKeyword" value="${searchVO.searchKeyword }"/>
						<c:param name="pageIndex" value="${searchVO.pageIndex }"/>
					</c:url>
					<a href="<c:out value="${listUrl }"/>" class="btn btn-secondary btn-sm"><i class="fas fa-ban"></i> 취소</a>
					<button type="submit" class="btn btn-primary btn-sm"><i class="fas fa-save"></i> 저장</button>
				</div>
			</form:form>
		</div>
	</div>
	
	<div class="modal fade" id="cmpnyListModal" tabindex="-1" role="dialog" aria-labelledby="cmpnyListModalLabel" aria-hidden="true">
		<div class="modal-dialog modal-lg modal-dialog-centered modal-dialog-scrollable modal-dark">
			<div class="modal-content">
				<div class="modal-header">
					<h6 class="modal-title" id="cmpnyListModalLabel">업체목록</h6>
					<button type="button" class="close" data-dismiss="modal" aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
				</div>
				<div class="modal-body">
					<form name="cmpnySearchForm" id="cmpnySearchForm" method="get" action="${CTX_ROOT}/decms/shop/cmpny/cmpnyList.json">
						<fieldset>
							<input type="hidden" id="cmpnyPageIndex" name="pageIndex" value=""/>
						</fieldset>
						<div class="form-group row">
							<div class="col-auto">
								<label for="searchCondition" class="sr-only">검색분류</label>
								<select name="searchCondition" class="custom-select custom-select-sm">
									<option value="NM">이름</option>
								</select>
							</div>
							<div class="col-auto">
								<div class="input-group input-group-sm">
									<input type="text" name="searchKeyword" class="form-control" value=""/>
									<span class="input-group-append">
										<button type="submit" class="btn btn-primary"><i class="fas fa-search"></i>검색</button>
									</span>
								</div>
							</div>
						</div>
					</form>
					<div class="cmpny-grid">
						<div id="data-cmpny-grid" class="mt-3"></div>
						<div id="data-cmpny-grid-pagination" class="tui-pagination"></div>
					</div>
				</div>
			</div>
		</div>
	</div>
	
	<div class="modal fade" id="brandListModal" tabindex="-1" role="dialog" aria-labelledby="brandListModalLabel" aria-hidden="true">
		<div class="modal-dialog modal-lg modal-dialog-centered modal-dialog-scrollable modal-dark">
			<div class="modal-content">
				<div class="modal-header">
					<h6 class="modal-title" id="brandListModalLabel">브랜드목록</h6>
					<button type="button" class="close" data-dismiss="modal" aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
				</div>
				<div class="modal-body">
					<div id="brand-regist-form" class="" style="display:none;">
						<div class="row">
							<div class="col-lg-9">
								<h6 class="brand-title">브랜드 등록</h6>
							</div>
							<div class="col-lg-3 text-right">
								<button type="button" class="btn btn-dark btn-sm btnBrandRegistClose" title="등록및수정 닫기"><i class="fas fa-times"></i></button>
							</div>
						</div>
						<hr class="sm"/>
						<form name="brandForm" id="brandForm" class="embedForm" method="post" enctype="multipart/form-data" action="${CTX_ROOT}/decms/shop/goods/saveGoodsBrand.json">
							<fieldset>
								<input type="hidden"  name="brandId" value=""/>
								<input type="hidden"  name="cmpnyId" value=""/>
								<input type="hidden"  name="brandImagePath" value=""/>
								<input type="hidden"  name="brandImageThumbPath" value=""/>
							</fieldset>
							<div class="form-group row">
								<label for="brandNm" class="col-sm-2 col-form-label col-form-label-sm required">브랜드명</label>
								<input type="text" id="brandNm" name="brandNm" class="col-sm-10 form-control form-control-sm"/>
							</div>
							<hr class="sm"/>
							<div class="form-group row">
								<label for="brandImage" class="col-sm-2 col-form-label col-form-label-sm">브랜드로고</label>
								<div class="col-sm-6 p-0">
									<input type="file" id="brandAtachFile" name="atchLogoFile" class="form-control form-control-sm"/>
									<%-- <div class="input-group input-group-sm">
										<div class="input-group-prepend">
											<span class="input-group-text">파일업로드</span>
										</div>
										<input type="file" id="brandAtachFile" name="atchLogoFile" class="form-control form-control-sm"/>
									</div> --%>
								</div>
								<div class="col-sm-4">
									<div id="brand-image-file">
										<img id="brandImage" src="#" alt=""/>
									</div>
								</div>
							</div>
							<hr class="sm"/>
							<div class="form-group row">
								<label for="brandImage" class="col-sm-2 col-form-label col-form-label-sm">브랜드이미지(PC)</label>
								<div class="col-sm-10">
									<div>
										<input type="file" id="brandDesktopFile"  name="atchDesktopFile" class="form-control form-control-sm" multiple/>
									</div>
									<div id="brandDesktopFileList">
									</div>
								</div>
							</div>
							<hr class="sm"/>
							<div class="form-group row">
								<label for="brandImage" class="col-sm-2 col-form-label col-form-label-sm">브랜드이미지(Mobile)</label>
								<div class="col-sm-10">
									<div>
										<input type="file" id="brandDesktopFile"  name="atchMobileFile" class="form-control form-control-sm" multiple/>
									</div>
									<div id="brandMobileFileList">
									</div>
								</div>
							</div>
							<hr class="sm"/>
							<div class="text-right">
								<button type="submit" class="btn btn-dark btn-sm"><i class="fas fa-save"></i> 저장</button>
							</div>
						</form>
						<hr/>
					</div>

					<form name="brandSearchForm" id="brandSearchForm" method="get" action="${CTX_ROOT}/decms/shop/goods/goodsBrandList.json">
						<fieldset>
							<input type="hidden" name="searchCmpnyId" value=""/>
						</fieldset>
						<div class="form-group row">
							<div class="col-auto">
								<label for="searchCondition" class="sr-only">검색분류</label>
								<select name="searchCondition" class="custom-select custom-select-sm">
									<option value="NM">이름</option>
								</select>
							</div>
							<div class="col-auto">
								<div class="input-group input-group-sm">
									<input type="text" name="searchKeyword" class="form-control" value=""/>
									<span class="input-group-append">
										<button type="submit" class="btn btn-primary"><i class="fas fa-search"></i>검색</button>
									</span>
								</div>
							</div>
							<div class="col-auto ml-auto text-right">
								<button type="button" class="btn btn-dark btn-sm btnAddBrand"><i class="fas fa-plus"></i> 등록</button>
							</div>
						</div>
					</form>
					<div class="brand-grid">
						<div id="data-brand-grid" class="mt-3"></div>
						<!-- <div id="data-brand-grid-pagination" class="tui-pagination"></div> -->
					</div>
				</div>
			</div>
		</div>
	</div>
	
	<div class="modal fade" id="recomendListModal" tabindex="-1" role="dialog" aria-labelledby="recomendListModalLabel" aria-hidden="true">
		<div class="modal-dialog modal-lg modal-dialog-centered modal-dialog-scrollable modal-dark">
			<div class="modal-content">
				<div class="modal-header">
					<h6 class="modal-title" id="cmpnyListModalLabel">상품목록</h6>
					<button type="button" class="close" data-dismiss="modal" aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
				</div>
				<div class="modal-body">
					<form name="recomendSearchForm" id="recomendSearchForm" method="get" action="${CTX_ROOT}/decms/shop/goods/goodsList.json">
						<fieldset>
							<input type="hidden" name="recomendPageIndex" value=""/>
							<input type="hidden" name="searchCmpnyId" value="${goods.cmpnyId }"/>
							<input type="hidden" name="searchNotGoodsId" value="${goods.goodsId }"/>
							<input type="hidden" name="searchPrtnrId" value="${goods.prtnrId}"/>
						</fieldset>
						<div class="form-group row">
							<div class="col-auto">
								<label for="searchCondition" class="sr-only">검색분류</label>
								<select name="searchCondition" class="custom-select custom-select-sm">
									<option value="NM">이름</option>
								</select>
							</div>
							<div class="col-auto">
								<div class="input-group input-group-sm">
									<input type="text" name="searchKeyword" class="form-control" value=""/>
									<span class="input-group-append">
										<button type="submit" class="btn btn-primary"><i class="fas fa-search"></i>검색</button>
									</span>
								</div>
							</div>
						</div>
					</form>
					<div class="recomend-grid">
						<div id="data-recomend-grid" class="mt-3"></div>
						<div id="data-recomend-grid-pagination" class="tui-pagination"></div>
					</div>
				</div>
			</div>
		</div>
	</div>
	
	<script id="goodsItemTemplate" type="text/template">
		<div class="d-flex optn-item mb-1">
			<input type="hidden" name="gitemList[{INDEX}].gitemId" class="gitemId" value=""/>
			<input type="hidden" name="gitemList[{INDEX}].gitemSn" class="gitemSn" value="{NO}"/>
			
			<div class="flex-fill pl-1 pr-1">
				<div class="input-group input-group-sm">
					<div class="input-group-prepend">
						<div class="input-group-text">옵션명(필수)</div>
					</div>
					<input type="text" name="gitemList[{INDEX}].gitemNm" class="form-control gitemNm" placeholder="옵션명"/>
					<div class="input-group-append">
						<button type="button" class="btn btn-secondary btn-sm btnRemoveTxt" data-value=""><i class="fas fa-times"></i></button>
					</div>
				</div>
			</div>
			<div class="flex-fill pl-1 pr-1 border-left">
				<div class="input-group input-group-sm">
					<div class="input-group-prepend">
						<div class="input-group-text">가격입력</div>
					</div>
					<input type="text" name="gitemList[{INDEX}].gitemPc" class="form-control gitemPc inputNumber" value="0"/>
					<div class="input-group-append">
						<button type="button" class="btn btn-secondary btn-sm btnRemoveTxt" data-value="0"><i class="fas fa-times"></i></button>
					</div>
				</div>
			</div>
			<div class="flex-fill pl-1 pr-1 border-left">
				<div class="custom-control custom-checkbox custom-control-inline" style="width:50px;">
					<input type="checkbox" class="custom-control-input gitemSttusCode" id="gitemList.gitemSttusCode{NO}" name="gitemList[{INDEX}].gitemSttusCode" value="F"/>
					<label class="custom-control-label" for="gitemList.gitemSttusCode{NO}"><small>품절</small></label>
				</div>
			</div>
			<div class="flex-fill pl-1 pr-1 border-left">
				<button type="button" class="btn btn-danger btn-sm btnRemoveGitem"><i class="fas fa-trash"></i></button>
			</div>
		</div>
	</script>

	<script id="goodsQestnItemTemplate" type="text/template">
		<div class="d-flex optn-item mb-1">
			<input type="hidden" name="gitemList[{INDEX}].gitemId" class="gitemId" value=""/>
			<input type="hidden" name="gitemList[{INDEX}].gitemSn" class="gitemSn" value="{NO}"/>
			
			<div class="flex-fill pl-1 pr-1">
				<div class="input-group input-group-sm">
					<div class="input-group-prepend">
						<div class="input-group-text">질문명(필수)</div>
					</div>
					<input type="text" name="gitemList[{INDEX}].gitemNm" class="form-control gitemNm" placeholder="질문명"/>
					<div class="input-group-append">
						<button type="button" class="btn btn-secondary btn-sm btnRemoveTxt" data-value=""><i class="fas fa-times"></i></button>
					</div>
				</div>
			</div>
			<div class="flex-shrink-1 pl-1 pr-1 border-left">
				<button type="button" class="btn btn-danger btn-sm btnRemoveGitem"><i class="fas fa-trash"></i></button>
			</div>
		</div>
	</script>
	
	<script id="goodsKeywordTemplate" type="text/template">
		<span class="badge badge-secondary keyword-item">
			<input type="hidden" name="goodsKeywordList[{INDEX}].goodsKeywordNo" class="goodsKeywordNo" value=""/>
			<input type="hidden" name="goodsKeywordList[{INDEX}].keywordNm" class="keywordNm" value="{KEYWORD}"/>
			{KEYWORD} 
			<a href="#" class="btnRemoveKeyword" title="삭제"><i class="fas fa-times"></i></a>
		</span>
	</script>
	
	<script id="evtImageTemplate" type="text/template">
		<div class="img-item col-sm-3">
			<div class="evt-img-item-box">
				<input type="hidden" name="evtImageList[{INDEX}].goodsImageNo" class="goodsImageNo" value=""/>
				<input type="hidden" name="evtImageList[{INDEX}].goodsImageSeCode" class="goodsImageSeCode" value="EVT"/>
				<input type="hidden" name="evtImageList[{INDEX}].goodsImageSn" class="goodsImageSn" value="{SN}"/>
				<input type="hidden" name="evtImageList[{INDEX}].goodsImagePath" class="goodsImagePath" value="{ORIGIN}"/>
				<input type="hidden" name="evtImageList[{INDEX}].goodsImageThumbPath" class="goodsImageThumbPath" value="{THUMB}"/>
				<input type="hidden" name="evtImageList[{INDEX}].goodsLrgeImagePath" class="goodsLrgeImagePath" value="{LRGE}"/>
				<div class="evt-img-header d-flex">
					<div class="flex-fill pl-2 goods-num">{SN}</div>
					<div class="flex-fill text-right">
						<a href="{ORIGIN}" class="btn btn-dark btn-sm rounded-0 btnImgView" title="이미지 원본보기"><i class="fas fa-eye"></i></a>
						<a href="#" class="btn btn-dark btn-sm rounded-0 btnDeleteImg" title="삭제" data-se-code="EVT"><i class="fas fa-times"></i></a>
					</div>
				</div>
				<img src="{ORIGIN}"/>
			</div>
		</div>
	</script>
	
	<script id="gdcImageTemplate" type="text/template">
		<div class="img-item col-sm-3">
			<div class="gdc-img-item-box">
				<input type="hidden" name="gdcImageList[{INDEX}].goodsImageNo" class="goodsImageNo" value=""/>
				<input type="hidden" name="gdcImageList[{INDEX}].goodsImageSeCode" class="goodsImageSeCode" value="GDC"/>
				<input type="hidden" name="gdcImageList[{INDEX}].goodsImageSn" class="goodsImageSn" value="{SN}"/>
				<input type="hidden" name="gdcImageList[{INDEX}].goodsImagePath" class="goodsImagePath" value="{ORIGIN}"/>
				<input type="hidden" name="gdcImageList[{INDEX}].goodsImageThumbPath" class="goodsImageThumbPath" value="{THUMB}"/>
				<input type="hidden" name="gdcImageList[{INDEX}].goodsLrgeImagePath" class="goodsLrgeImagePath" value="{LRGE}"/>
				<div class="gdc-img-header d-flex">
					<div class="flex-fill pl-2 goods-num">{SN}</div>
					<div class="flex-fill text-right">
						<a href="{ORIGIN}" class="btn btn-dark btn-sm rounded-0 btnImgView" title="이미지 원본보기"><i class="fas fa-eye"></i></a>
						<a href="#" class="btn btn-dark btn-sm rounded-0 btnDeleteImg" title="삭제" data-se-code="GDC"><i class="fas fa-times"></i></a>
					</div>
				</div>
				<img src="{ORIGIN}"/>
			</div>
		</div>
	</script>
	
	<script id="gnrImageTemplate" type="text/template">
		<div class="img-item col-sm-3">
			<div class="img-item-box">
				<input type="hidden" name="goodsImageList[{INDEX}].goodsImageNo" class="goodsImageNo" value=""/>
				<input type="hidden" name="goodsImageList[{INDEX}].goodsImageSeCode" class="goodsImageSeCode" value="GNR"/>
				<input type="hidden" name="goodsImageList[{INDEX}].goodsImageSn" class="goodsImageSn" value="{SN}"/>
				<input type="hidden" name="goodsImageList[{INDEX}].goodsImagePath" class="goodsImagePath" value="{ORIGIN}"/>
				<input type="hidden" name="goodsImageList[{INDEX}].goodsImageThumbPath" class="goodsImageThumbPath" value="{THUMB}"/>
				<input type="hidden" name="goodsImageList[{INDEX}].goodsLrgeImagePath" class="goodsLrgeImagePath" value="{LRGE}"/>
				<input type="hidden" name="goodsImageList[{INDEX}].goodsMiddlImagePath" class="goodsMiddlImagePath" value="{MIDDL}"/>
				<input type="hidden" name="goodsImageList[{INDEX}].goodsSmallImagePath" class="goodsSmallImagePath" value="{SMALL}"/>
				<div class="img-header d-flex">
					<div class="flex-fill pl-2 goods-num">{SN}</div>
					<div class="flex-fill text-right">
						<a href="{ORIGIN}" class="btn btn-dark btn-sm rounded-0 btnImgView" title="이미지 원본보기"><i class="fas fa-eye"></i></a>
						<a href="#" class="btn btn-dark btn-sm rounded-0 btnDeleteImg" title="삭제" data-se-code="DEK"><i class="fas fa-times"></i></a>
					</div>
				</div>
				<img src="{ORIGIN}"/>
			</div>
		</div>
	</script>
	
	
	<%--
	<script id="pcImageTemplate" type="text/template">
		<div class="img-item col-sm-3">
			<div class="img-item-box">
				<input type="hidden" name="pcImageList[{INDEX}].goodsImageNo" class="goodsImageNo" value=""/>
				<input type="hidden" name="pcImageList[{INDEX}].goodsImageSeCode" class="goodsImageSeCode" value="DEK"/>
				<input type="hidden" name="pcImageList[{INDEX}].goodsImageSn" class="goodsImageSn" value="{SN}"/>
				<input type="hidden" name="pcImageList[{INDEX}].goodsImagePath" class="goodsImagePath" value="{ORIGIN}"/>
				<input type="hidden" name="pcImageList[{INDEX}].goodsImageThumbPath" class="goodsImageThumbPath" value="{THUMB}"/>
				<div class="pc-img-header d-flex">
					<div class="flex-fill pl-2 goods-num">{SN}</div>
					<div class="flex-fill text-right">
						<a href="{ORIGIN}" class="btn btn-dark btn-sm rounded-0 btnImgView" title="이미지 원본보기"><i class="fas fa-eye"></i></a>
						<a href="#" class="btn btn-dark btn-sm rounded-0 btnDeleteImg" title="삭제" data-se-code="DEK"><i class="fas fa-times"></i></a>
					</div>
				</div>
				<img src="{ORIGIN}"/>
			</div>
		</div>
	</script>
	
	<script id="mobImageTemplate" type="text/template">
		<div class="img-item col-sm-3">
			<div class="img-item-box">
				<input type="hidden" name="mobImageList[{INDEX}].goodsImageNo" class="goodsImageNo" value=""/>
				<input type="hidden" name="mobImageList[{INDEX}].goodsImageSeCode" class="goodsImageSeCode" value="MOB"/>
				<input type="hidden" name="mobImageList[{INDEX}].goodsImageSn" class="goodsImageSn" value="{SN}"/>
				<input type="hidden" name="mobImageList[{INDEX}].goodsImagePath" class="goodsImagePath" value="{ORIGIN}"/>
				<input type="hidden" name="mobImageList[{INDEX}].goodsImageThumbPath" class="goodsImageThumbPath" value="{THUMB}"/>
				<div class="pc-img-header d-flex">
					<div class="flex-fill pl-2 goods-num">{SN}</div>
					<div class="flex-fill text-right">
						<a href="{ORIGIN}" class="btn btn-dark btn-sm rounded-0 btnImgView" title="이미지 원본보기"><i class="fas fa-eye"></i></a>
						<a href="#" class="btn btn-dark btn-sm rounded-0 btnDeleteImg" title="삭제" data-se-code="MOB"><i class="fas fa-times"></i></a>
					</div>
				</div>
				<img src="{ORIGIN}"/>
			</div>
		</div>
	</script>

	<script id="banImageTemplate" type="text/template">
		<div class="img-item col-sm-3">
			<div class="img-item-box">
				<input type="hidden" name="banImageList[{INDEX}].goodsImageNo" class="goodsImageNo" value=""/>
				<input type="hidden" name="banImageList[{INDEX}].goodsImageSeCode" class="goodsImageSeCode" value="BAN"/>
				<input type="hidden" name="banImageList[{INDEX}].goodsImageSn" class="goodsImageSn" value="{SN}"/>
				<input type="hidden" name="banImageList[{INDEX}].goodsImagePath" class="goodsImagePath" value="{ORIGIN}"/>
				<input type="hidden" name="banImageList[{INDEX}].goodsImageThumbPath" class="goodsImageThumbPath" value="{THUMB}"/>
				<div class="pc-img-header d-flex">
					<div class="flex-fill pl-2 goods-num">{SN}</div>
					<div class="flex-fill text-right">
						<a href="{ORIGIN}" class="btn btn-dark btn-sm rounded-0 btnImgView" title="이미지 원본보기"><i class="fas fa-eye"></i></a>
						<a href="#" class="btn btn-dark btn-sm rounded-0 btnDeleteImg" title="삭제" data-se-code="BAN"><i class="fas fa-times"></i></a>
					</div>
				</div>
				<img src="{ORIGIN}"/>
			</div>
		</div>
	</script>
	--%>

	<script id="recomendGoodsTemplate" type="text/template">
		<div class="recomend-item col-sm-3">
			<input type="hidden" name="goodsRecomendList[{INDEX}].goodsRecomendNo" class="goodsRecomendNo" value=""/>
			<input type="hidden" name="goodsRecomendList[{INDEX}].recomendGoodsSn" class="recomendGoodsSn" value="{SN}"/>
			<input type="hidden" name="goodsRecomendList[{INDEX}].recomendGoodsId" class="recomendGoodsId" value="{GOODS_ID}"/>
			<figure class="figure">
				<div class="figure-header d-flex">
					<div class="figure-num flex-fill figure-num pl-2">{SN}</div>
					<div class="flex-fill text-right">
						<a href="{ORIGIN}" class="btn btn-dark btn-sm rounded-0 btnImgView" title="이미지 원본보기"><i class="fas fa-eye"></i></a>
						<a href="#" class="btn btn-dark btn-sm rounded-0 btnDeleteRecomend" title="삭제"><i class="fas fa-times"></i></a>
					</div>
				</div>
				<img src="{THUMB}" class="figure-img img-fluid rounded" alt=""/>
				<figcaption class="figure-caption">{TIT}</figcaption>
				<div>
					<strong class="recomend-pc">{PC}</strong> 원
				</div>
			</figure>
		</div>
	</script>
	
	<javascript>
		<script src="${CTX_ROOT}/resources/lib/tui/tui-code-snippet/tui-code-snippet.min.js"></script>
		<script src="${CTX_ROOT}/resources/lib/tui/tui-pagination/tui-pagination.min.js"></script>
		<script src="${CTX_ROOT}/resources/lib/tui/tui-grid/tui-grid.min.js"></script>
		<script src="${CTX_ROOT}/resources/lib/tempusdominus-bootstrap-4/js/tempusdominus-bootstrap-4.min.js"></script>
		<script src="${CTX_ROOT}/resources/lib/ace/ace.js"></script>
		<script src="${CTX_ROOT}/resources/lib/jquery-ui/jquery-ui.min.js"></script>
		<script src="${CTX_ROOT}/resources/decms/shop/goods/info/js/goodsForm.js?20201228"></script>
		<script src="${CTX_ROOT}/resources/decms/shop/goods/info/js/goodsFormCmpnyList.js?20201105"></script>
		<script src="${CTX_ROOT}/resources/decms/shop/goods/info/js/goodsFormRecomendList.js?2020"></script>
		<script src="${CTX_ROOT}/resources/decms/shop/goods/brand/js/goodsBrandManage.js?20200910"></script>
		<script src="${CTX_ROOT}/resources/decms/shop/goods/info/js/goodsCoupon.js"></script>
	</javascript>
</body>
</html>