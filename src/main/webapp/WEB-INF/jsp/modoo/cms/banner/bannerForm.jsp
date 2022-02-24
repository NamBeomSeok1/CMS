<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/modoo/common/commonTagLibs.jsp"%>
<c:set var="pageTitle" value="배너 등록"/>
<c:set var="mode" value="insert"/>
<c:set var="actionUrl" value="/decms/banner/writeBanner.json"/>
<c:choose>
	<c:when test="${empty banner.bannerId }">
		<c:set var="pageTitle" value="배너 등록"/>
		<c:set var="mode" value="insert"/>
		<c:set var="actionUrl" value="/decms/banner/writeBanner.json"/>
	</c:when>
	<c:otherwise>
		<c:set var="pageTitle" value="배너 수정"/>
		<c:set var="mode" value="update"/>
		<c:set var="actionUrl" value="/decms/banner/modifyBanner.json"/>
	</c:otherwise>
</c:choose>

<form:form modelAttribute="banner" id="registForm" name="bannerForm" cssClass="embedForm" method="post" enctype="multipart/form-data" action="${actionUrl }">
	<fieldset>
		<form:hidden path="siteId"/>
		<form:hidden path="bannerId"/>
		<input type="hidden" id="type" name="type" value="banner"/>
	</fieldset>
	<h5>${pageTitle }</h5>
	<p>
		<small>(<i class="fas fa-star text-danger"></i>)는 필수 항목입니다.</small>
	</p>
	<hr class="sm"/>
	<div class="form-group row">
		<label for="bannerSeCode" class="col-sm-2 col-form-label col-form-label-sm">배너구분</label>
		<div class="col-sm-4">
			<form:select path="bannerSeCode" cssClass="custom-select custom-select-sm">
				<c:forEach var="code" items="${bannerSeCodeList }">
					<form:option value="${code.code }">${code.codeNm }</form:option>
				</c:forEach>
			</form:select>
		</div>
		<label for="bannerSeCode" class="col-sm-2 col-form-label col-form-label-sm type2" style="display:none;">제휴사</label>
		<div class="col-sm-4 type2 bannerSeCodeList" style="display:none;">
			<div class="form-check form-check-inline">
				<form:radiobutton path="prtnrId" class="form-check-input" value="PRTNR_0000"/>
				<label class="form-check-label small" for="prtnrId1">B2C</label>
			</div>
			<div class="form-check form-check-inline">
				<form:radiobutton path="prtnrId" class="form-check-input" value="PRTNR_0001"/>
				<label class="form-check-label small" for="prtnrId2">이지웰</label>
			</div>
			<%-- 상품일경우 사용자가 잘못 선택해도 컨트롤러에서 실제 값 입력 --%>
		</div>
	</div>
	<hr class="sm"/>
	
	<div class="form-group row">
		<label for="bannerNm" class="col-sm-2 col-form-label col-form-label-sm required">배너명</label>
		<div class="col-sm-7">
			<form:textarea path="bannerNm" cssClass="form-control form-control-sm" maxlength="255" placeholder="상품명 또는 이벤트명을 입력하세요"/>
		</div>
		<label for="sortNo" class="col-sm-2 col-form-label col-form-label-sm required">순번</label>
		<div class="col-sm-1">
			<form:input path="sortNo" cssClass="form-control form-control-sm text-right" maxlength="3" placeholder=""/>
		</div>
	</div>
	<hr class="sm"/>
	
	<div class="form-group row">
		<label for="bannerBgnDate" class="col-sm-2 col-form-label col-form-label-sm required">배너시작일</label>
		<div class="col-sm-2">
			<div class="input-group input-group-sm" id="datepicker-bannerBgnDate" data-target-input="nearest">
				<form:input path="bannerBgnDate" cssClass="form-control datetimepicker-input" data-target="#datepicker-bannerBgnDate"/>
				<div class="input-group-append" data-target="#datepicker-bannerBgnDate" data-toggle="datetimepicker">
					<div class="input-group-text"><i class="fas fa-calendar"></i></div>
				</div>
			</div>
		</div>
		<div class="col-sm-2 form-inline">
			<form:select path="bannerBgnHour" cssClass="custom-select custom-select-sm">
				<c:forEach var="item" begin="0" end="23">
					<fmt:formatNumber var="hour" minIntegerDigits="2" value="${item}" />
					<form:option value="${hour }">${item } 시</form:option>
				</c:forEach>
			</form:select>
			<form:select path="bannerBgnMin" cssClass="custom-select custom-select-sm">
				<c:forEach var="item" begin="0" end="50" step="10">
					<fmt:formatNumber var="min" minIntegerDigits="2" value="${item}" />
					<form:option value="${min }">${item } 분</form:option>
				</c:forEach>
			</form:select>
		</div>
		<label for="bannerEndDate" class="col-sm-2 col-form-label col-form-label-sm required">배너종료일</label>
		<div class="col-sm-2">
			<div class="input-group input-group-sm" id="datepicker-bannerEndDate" data-target-input="nearest">
				<form:input path="bannerEndDate" cssClass="form-control datetimepicker-input" data-target="#datepicker-bannerEndDate"/>
				<div class="input-group-append" data-target="#datepicker-bannerEndDate" data-toggle="datetimepicker">
					<div class="input-group-text"><i class="fas fa-calendar"></i></div>
				</div>
			</div>
		</div>
		<div class="col-sm-2 form-inline">
			<form:select path="bannerEndHour" cssClass="custom-select custom-select-sm">
				<c:forEach var="item" begin="0" end="23">
					<fmt:formatNumber var="hour" minIntegerDigits="2" value="${item}" />
					<form:option value="${hour }">${item } 시</form:option>
				</c:forEach>
			</form:select>
			<form:select path="bannerEndMin" cssClass="custom-select custom-select-sm">
				<c:forEach var="item" begin="0" end="50" step="10">
					<fmt:formatNumber var="min" minIntegerDigits="2" value="${item}" />
					<form:option value="${min }">${item } 분</form:option>
				</c:forEach>
			</form:select>
		</div>
	</div>
	<hr class="sm"/>

	<div class="form-group row type2 type3" style="display:none;">
		<label for="evtTxt" class="col-sm-2 col-form-label col-form-label-sm">요일</label>
		<div class="col-sm-10">
			<div class="form-check form-check-inline dfkAllCheckBox">
				<input class="form-check-input" type="checkbox" id="dfkAllCheck" value="dfkAll" <c:if test="${fn:indexOf(banner.dfk,'월,화,수,목,금,토,일') > -1}">checked="checked"</c:if>/>
				<label class="form-check-label small" for="dfkAllCheck">전체</label>
			</div>
			<div class="form-check form-check-inline">
				<input class="form-check-input check-dfk" type="checkbox" id="dfk1" name="dfk" value="월" <c:if test="${fn:indexOf(banner.dfk,'월') > -1}">checked="checked"</c:if> />
				<label class="form-check-label small" for="dfk1">월</label>
			</div>
			<div class="form-check form-check-inline">
				<input class="form-check-input check-dfk" type="checkbox" id="dfk2" name="dfk" value="화" <c:if test="${fn:indexOf(banner.dfk,'화') > -1}">checked="checked"</c:if> />
				<label class="form-check-label small" for="dfk2">화</label>
			</div>
			<div class="form-check form-check-inline">
				<input class="form-check-input check-dfk" type="checkbox" id="dfk3" name="dfk" value="수" <c:if test="${fn:indexOf(banner.dfk,'수') > -1}">checked="checked"</c:if> />
				<label class="form-check-label small" for="dfk3">수</label>
			</div>
			<div class="form-check form-check-inline">
				<input class="form-check-input check-dfk" type="checkbox" id="dfk4" name="dfk" value="목" <c:if test="${fn:indexOf(banner.dfk,'목') > -1}">checked="checked"</c:if> />
				<label class="form-check-label small" for="dfk4">목</label>
			</div>
			<div class="form-check form-check-inline">
				<input class="form-check-input check-dfk" type="checkbox" id="dfk5" name="dfk" value="금" <c:if test="${fn:indexOf(banner.dfk,'금') > -1}">checked="checked"</c:if> />
				<label class="form-check-label small" for="dfk5">금</label>
			</div>
			<div class="form-check form-check-inline">
				<input class="form-check-input check-dfk" type="checkbox" id="dfk6" name="dfk" value="토" <c:if test="${fn:indexOf(banner.dfk,'토') > -1}">checked="checked"</c:if> />
				<label class="form-check-label small" for="dfk6">토</label>
			</div>
			<div class="form-check form-check-inline">
				<input class="form-check-input check-dfk" type="checkbox" id="dfk7" name="dfk" value="일" <c:if test="${fn:indexOf(banner.dfk,'일') > -1}">checked="checked"</c:if> />
				<label class="form-check-label small" for="dfk7">일</label>
			</div>
			<!-- 
			<label for="dfk1">월 <input type="checkbox" id="dfk1" name="dfk" value="월" <c:if test="${fn:indexOf(banner.dfk,'월') > -1}">checked="checked"</c:if>/></label>
			&nbsp;&nbsp;
			<label for="dfk2">화 <input type="checkbox" id="dfk2" name="dfk" value="화" <c:if test="${fn:indexOf(banner.dfk,'화') > -1}">checked="checked"</c:if>/></label>
			&nbsp;&nbsp;
			<label for="dfk3">수 <input type="checkbox" id="dfk3" name="dfk" value="수" <c:if test="${fn:indexOf(banner.dfk,'수') > -1}">checked="checked"</c:if>/></label>
			&nbsp;&nbsp;
			<label for="dfk4">목 <input type="checkbox" id="dfk4" name="dfk" value="목" <c:if test="${fn:indexOf(banner.dfk,'목') > -1}">checked="checked"</c:if>/></label>
			&nbsp;&nbsp;
			<label for="dfk5">금 <input type="checkbox" id="dfk5" name="dfk" value="금" <c:if test="${fn:indexOf(banner.dfk,'금') > -1}">checked="checked"</c:if>/></label>
			&nbsp;&nbsp;
			<label for="dfk6">토 <input type="checkbox" id="dfk6" name="dfk" value="토" <c:if test="${fn:indexOf(banner.dfk,'토') > -1}">checked="checked"</c:if>/></label>
			&nbsp;&nbsp;
			<label for="dfk7">일 <input type="checkbox" id="dfk7" name="dfk" value="일" <c:if test="${fn:indexOf(banner.dfk,'일') > -1}">checked="checked"</c:if>/></label>
			-->
		</div>
	</div>
	<hr class="sm type2 type3" style="display:none;"/>

	<%-- <div class="form-group row type3no">
		<label for="bannerPath" class="col-sm-2 col-form-label col-form-label-sm required">배너</label>
		<div class="input-group input-group-sm col-sm-10">
			<div class="input-group-prepend">
				<span class="input-group-text"><i class="fas fa-image"></i> </span>
			</div>
			<form:input path="bannerPath" cssClass="form-control" placeholder="ex) /contents/banner1.png"/>
			<div class="input-group-append">
				<a href="#" class="btn btn-outline-secondary" data-event="upload" data-target-url="#bannerPath" data-target-img="#banner-img"><i class="fas fa-upload"></i></a>
			</div>
		</div>
		<div class="offset-2 col-10 mt-2">
			<div id="banner-img">
				<c:if test="${not empty banner.bannerPath }">
					<img src="<c:out value="${banner.bannerPath }"/>" style="max-width:100%; max-height:100%;"/>
				</c:if>
				
				<img src="/resources/front/banner/image/sample.png" style="max-height:100px;"/>
				
			</div>
		</div>
	</div>
	<hr class="sm type3no"/>
	
	<div class="form-group row type2" style="display:none;">
		<label for="bannerPath" class="col-sm-2 col-form-label col-form-label-sm required">모바일 배너</label>
		<div class="input-group input-group-sm col-sm-10">
			<div class="input-group-prepend">
				<span class="input-group-text"><i class="fas fa-image"></i> </span>
			</div>
			<form:input path="bannerMPath" cssClass="form-control" placeholder="ex) /contents/banner1.png"/>
			<div class="input-group-append">
				<a href="#" class="btn btn-outline-secondary" data-event="upload" data-target-url="#bannerMPath" data-target-img="#banner-m-img"><i class="fas fa-upload"></i></a>
			</div>
		</div>
		<div class="offset-2 col-10 mt-2">
			<div id="banner-m-img">
				<c:if test="${not empty banner.bannerMPath }">
					<img src="<c:out value="${banner.bannerMPath }"/>" style="max-width:100%; max-height:100%;"/>
				</c:if>
			</div>
		</div>
	</div>
	<hr class="sm type2" style="display:none;"/> --%>
	
	<div class="form-group row type2" style="display:none;">
		<label for="bannerTyCode" class="col-sm-2 col-form-label col-form-label-sm">배너타입</label>
		<div class="col-sm-10">
			<c:forEach var="code" items="${bannerTyCodeList }" varStatus="status">
			<div class="form-check form-check-inline">
				<form:radiobutton path="bannerTyCode" cssClass="radioBannerTyCode form-check-input" value="${code.code }"/>
				<label class="form-check-label small" for="bannerTyCode${status.index+1 }">${code.codeNm }</label>
			</div>
			</c:forEach>
		</div>
	</div>
	<hr class="sm type2" style="display:none;"/>
	
	<div class="form-group row type2" style="display:none;">
		<label for="bannerLbl" class="col-sm-2 col-form-label col-form-label-sm ">라벨</label>
		<div class="col-sm-4">
			<form:input path="bannerLbl" cssClass="form-control form-control-sm" maxlength="255" placeholder="ex) 라벨 텍스트 입력"/>
		</div>
		<label for="bannerLblClor" class="col-sm-2 col-form-label col-form-label-sm ">라벨컬러</label>
		<div class="col-sm-2">
			<div class="input-group input-group-sm">
				<div class="input-group-prepend">
					<input class="input-group-text hexValue" style="width: 70px;"value="${banner.bannerLblClor}">
				</div>
				<input type="color" id="bannerLblClor" name="bannerLblClor" class="inputColor form-control form-control-sm" value="${banner.bannerLblClor }"/>
			</div>
		</div>
	</div>
	<hr class="sm type2" style="display:none;"/>
	
	<div class="form-group row type2" style="display:none;">
		<label for="evtTxt" class="col-sm-2 col-form-label col-form-label-sm required">한줄소개</label> <%--이벤트문구 --%>
		<div class="col-sm-10">
			<form:textarea path="evtTxt" cssClass="form-control form-control-sm" maxlength="255" placeholder="한줄소개를 입력하세요" value="${banner.evtTxt}"/>
		</div>
	</div>
	<hr class="sm type2" style="display:none;"/>
	
	<div class="form-group row type2" style="display:none;">
		<label for="bcrnClor" class="col-sm-2 col-form-label col-form-label-sm">배경색</label>
		<div class="col-sm-2">
			<div class="input-group input-group-sm">
			<div class="input-group-prepend">
				<input class="input-group-text hexValue" style="width: 70px;" value="${banner.bcrnClor}">
			</div>
				<input type="color" id="bcrnClor" name="bcrnClor" class="inputColor form-control form-control-sm" value="${banner.bcrnClor }"/>
			</div>
			<%-- <form:input path="bcrnClor" cssClass="form-control form-control-sm" maxlength="7" placeholder="ex)#EDF1F8" style="background-color:${banner.bcrnClor}"/> --%>
		</div>
		<label for="fontClor" class="col-sm-2 offset-sm-2 col-form-label col-form-label-sm">글자색</label>
		<div class="col-sm-2">
			<div class="input-group input-group-sm">
				<div class="input-group-prepend">
					<input class="input-group-text hexValue" style="width: 70px;"value="${banner.fontClor}">
				</div>
				<input type="color" id="fontClor" name="fontClor" class="inputColor form-control form-control-sm" value="${banner.fontClor }"/>
			</div>
			<%-- <form:input path="bcrnClor" cssClass="form-control form-control-sm" maxlength="7" placeholder="ex)#EDF1F8" style="background-color:${banner.bcrnClor}"/> --%>
		</div>
	</div>
	<hr class="sm type2" style="display:none;"/>
	
	<%-- <div class="form-group row type2 type3" style="display:none;">
		<label for="evtTxt" class="col-sm-2 col-form-label col-form-label-sm required">요일</label>
		<label for="dfk1">월 <input type="checkbox" id="dfk1" name="dfk" value="월" <c:if test="${fn:indexOf(banner.dfk,'월') > -1}">checked="checked"</c:if>/></label>
		&nbsp;&nbsp;
		<label for="dfk2">화 <input type="checkbox" id="dfk2" name="dfk" value="화" <c:if test="${fn:indexOf(banner.dfk,'화') > -1}">checked="checked"</c:if>/></label>
		&nbsp;&nbsp;
		<label for="dfk3">수 <input type="checkbox" id="dfk3" name="dfk" value="수" <c:if test="${fn:indexOf(banner.dfk,'수') > -1}">checked="checked"</c:if>/></label>
		&nbsp;&nbsp;
		<label for="dfk4">목 <input type="checkbox" id="dfk4" name="dfk" value="목" <c:if test="${fn:indexOf(banner.dfk,'목') > -1}">checked="checked"</c:if>/></label>
		&nbsp;&nbsp;
		<label for="dfk5">금 <input type="checkbox" id="dfk5" name="dfk" value="금" <c:if test="${fn:indexOf(banner.dfk,'금') > -1}">checked="checked"</c:if>/></label>
		&nbsp;&nbsp;
		<label for="dfk6">토 <input type="checkbox" id="dfk6" name="dfk" value="토" <c:if test="${fn:indexOf(banner.dfk,'토') > -1}">checked="checked"</c:if>/></label>
		&nbsp;&nbsp;
		<label for="dfk7">일 <input type="checkbox" id="dfk7" name="dfk" value="일" <c:if test="${fn:indexOf(banner.dfk,'일') > -1}">checked="checked"</c:if>/></label>
	</div>
	<hr class="sm type2 type3" style="display:none;"/> --%>
	
	<div class="form-group row type2 type3 bannerGoods" style="display:none;">
		<label for="goodsId" class="col-sm-2 col-form-label col-form-label-sm required">상품</label>
		<div class="col-sm-4">
			<div class="input-group input-group-sm">
				<input type="text" id="goodsNm" class="form-control form-control-sm" value="${banner.goodsNm}" readonly="readonly">
				<form:hidden path="goodsId" cssClass="form-control form-control-sm" maxlength="20"/>
				<div class="input-group-append">
					<button type="button" class="btn btn-dark btn-sm btnAddRecomend" data-target="#recomendListModal"><i class="fas fa-plus"></i> 상품찾기</button>
				</div>
			</div>
		</div>
		<%-- <input type="text" id="goodsNm" class="col-sm-2 form-control form-control-sm" value="${banner.goodsNm}" readonly="readonly">
		<form:hidden path="goodsId" cssClass="col-sm-2 form-control form-control-sm" maxlength="20"/> --%>
		<!-- <div class="text-right mb-2">
			<button type="button" class="btn btn-dark btn-sm btnAddRecomend" data-target="#recomendListModal"><i class="fas fa-plus"></i> 상품찾기</button>
		</div> -->
	</div>
	<hr class="sm type2 type3 bannerGoods" style="display:none;"/>
	
	
	<div class="form-group row type3no">
		<label for="bannerLink" class="col-sm-2 col-form-label col-form-label-sm">배너링크</label>
		<div class="input-group input-group-sm col-sm-10">
			<div class="input-group-prepend">
				<span class="input-group-text"><i class="fas fa-link"></i> </span>
			</div>
			<form:input path="bannerLink" cssClass="form-control" placeholder="ex) http://target-domain-link"/>
			<div class="input-group-append">
				<div class="input-group-text">
					<form:checkbox path="bannerWindowAt" cssClass="" value="Y"/>
					<label for="bannerWindowAt1" class="m-0 ml-1">새창여부</label>
				</div>
			</div>
		</div>
	</div>
	<hr class="sm type3no"/>
	
	<div class="form-group row type3no">
		<label for="bannerPath" class="col-sm-2 col-form-label col-form-label-sm required">배너</label>
		<div class="input-group input-group-sm col-sm-10">
			<div class="input-group-prepend">
				<span class="input-group-text"><i class="fas fa-image"></i> </span>
			</div>
			<form:input path="bannerPath" cssClass="form-control" placeholder="ex) /contents/banner1.png"/>
			<div class="input-group-append">
				<a href="#" class="btn btn-outline-secondary" data-event="upload" data-target-url="#bannerPath" data-target-img="#banner-img"><i class="fas fa-upload"></i></a>
			</div>
		</div>
		<div class="offset-2 col-10 mt-2">
			<div id="banner-img">
				<c:if test="${not empty banner.bannerPath }">
					<img src="<c:out value="${banner.bannerPath }"/>" style="max-width:100%; max-height:100%;"/>
				</c:if>
				<%-- 
				<img src="/resources/front/banner/image/sample.png" style="max-height:100px;"/>
				--%>
			</div>
		</div>
	</div>
	<hr class="sm type3no"/>
	
	<div class="form-group row type2" style="display:none;">
		<label for="bannerPath" class="col-sm-2 col-form-label col-form-label-sm required">모바일 배너</label>
		<div class="input-group input-group-sm col-sm-10">
			<div class="input-group-prepend">
				<span class="input-group-text"><i class="fas fa-image"></i> </span>
			</div>
			<form:input path="bannerMPath" cssClass="form-control" placeholder="ex) /contents/banner1.png"/>
			<div class="input-group-append">
				<a href="#" class="btn btn-outline-secondary" data-event="upload" data-target-url="#bannerMPath" data-target-img="#banner-m-img"><i class="fas fa-upload"></i></a>
			</div>
		</div>
		<div class="offset-2 col-10 mt-2">
			<div id="banner-m-img">
				<c:if test="${not empty banner.bannerMPath }">
					<img src="<c:out value="${banner.bannerMPath }"/>" style="max-width:100%; max-height:100%;"/>
				</c:if>
			</div>
		</div>
	</div>
	<hr class="sm type2" style="display:none;"/>
	
	<div class="form-group row">
		<label for="actvtyAt" class="col-sm-2 col-form-label col-form-label-sm">활성여부</label>
		<div class="col-sm-2">
			<div class="custom-control custom-switch">
				<form:checkbox path="actvtyAt" cssClass="custom-control-input" value="Y"/>
				<label class="custom-control-label  col-form-label-sm" for="actvtyAt1">배너활성</label>
			</div>
		</div>
	</div>
	<hr class="sm"/>
	
	<div class="text-right mt-3">
		<button type="button" class="btn btn-secondary btn-sm" data-dismiss="modal"><i class="fas fa-ban"></i> 취소</button>
		<button type="submit" class="btn btn-primary btn-sm"><i class="fas fa-save"></i> 저장</button>
	</div>
</form:form>

	