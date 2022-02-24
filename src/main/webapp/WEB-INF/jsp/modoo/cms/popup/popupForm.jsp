<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/modoo/common/commonTagLibs.jsp"%>
<c:set var="pageTitle" value="팝업 등록"/>
<c:set var="mode" value="insert"/>
<c:set var="actionUrl" value="/decms/popup/writePopup.json"/>
<c:choose>
	<c:when test="${empty popup.popupId }">
		<c:set var="pageTitle" value="팝업 등록"/>
		<c:set var="mode" value="insert"/>
		<c:set var="actionUrl" value="/decms/popup/writePopup.json"/>
	</c:when>
	<c:otherwise>
		<c:set var="pageTitle" value="팝업 수정"/>
		<c:set var="mode" value="update"/>
		<c:set var="actionUrl" value="/decms/popup/modifyPopup.json"/>
	</c:otherwise>
</c:choose>

<form:form modelAttribute="popup" id="registForm" name="popupForm" cssClass="embedForm" method="post" enctype="multipart/form-data" action="${actionUrl }">
	<fieldset>
		<form:hidden path="siteId"/>
		<form:hidden path="popupId"/>
	</fieldset>
	<h5>${pageTitle }</h5>
	<p>
		<small>(<i class="fas fa-star text-danger"></i>)는 필수 항목입니다.</small>
	</p>
	<hr class="sm"/>
	<div class="form-group row">
		<label for="popupSj" class="col-sm-2 col-form-label col-form-label-sm required">제목</label>
		<div class="col-sm-10">
			<form:input path="popupSj" cssClass="form-control form-control-sm" maxlength="255" placeholder="제목을 입력하세요"/>
		</div>
	</div>
	<hr class="sm"/>
	
	<div class="form-group row">
		<label for="popupBgnDate" class="col-sm-2 col-form-label col-form-label-sm required">팝업시작일</label>
		<div class="col-sm-2">
			<div class="input-group input-group-sm" id="datepicker-popupBgnDate" data-target-input="nearest">
				<form:input path="popupBgnDate" cssClass="form-control datetimepicker-input" data-target="#datepicker-popupBgnDate"/>
				<div class="input-group-append" data-target="#datepicker-popupBgnDate" data-toggle="datetimepicker">
					<div class="input-group-text"><i class="fas fa-calendar"></i></div>
				</div>
			</div>
		</div>
		<div class="col-sm-2 form-inline">
			<form:select path="popupBgnHour" cssClass="custom-select custom-select-sm">
				<c:forEach var="item" begin="0" end="23">
					<fmt:formatNumber var="hour" minIntegerDigits="2" value="${item}" />
					<form:option value="${hour }">${item } 시</form:option>
				</c:forEach>
			</form:select>
			<form:select path="popupBgnMin" cssClass="custom-select custom-select-sm">
				<c:forEach var="item" begin="0" end="50" step="10">
					<fmt:formatNumber var="min" minIntegerDigits="2" value="${item}" />
					<form:option value="${min }">${item } 분</form:option>
				</c:forEach>
			</form:select>
		</div>
		<label for="popupEndDate" class="col-sm-2 col-form-label col-form-label-sm required">팝업종료일</label>
		<div class="col-sm-2">
			<div class="input-group input-group-sm" id="datepicker-popupEndDate" data-target-input="nearest">
				<form:input path="popupEndDate" cssClass="form-control datetimepicker-input" data-target="#datepicker-popupEndDate"/>
				<div class="input-group-append" data-target="#datepicker-popupEndDate" data-toggle="datetimepicker">
					<div class="input-group-text"><i class="fas fa-calendar"></i></div>
				</div>
			</div>
		</div>
		<div class="col-sm-2 form-inline">
			<form:select path="popupEndHour" cssClass="custom-select custom-select-sm">
				<c:forEach var="item" begin="0" end="23">
					<fmt:formatNumber var="hour" minIntegerDigits="2" value="${item}" />
					<form:option value="${hour }">${item } 시</form:option>
				</c:forEach>
			</form:select>
			<form:select path="popupEndMin" cssClass="custom-select custom-select-sm">
				<c:forEach var="item" begin="0" end="50" step="10">
					<fmt:formatNumber var="min" minIntegerDigits="2" value="${item}" />
					<form:option value="${min }">${item } 분</form:option>
				</c:forEach>
			</form:select>
		</div>
	</div>
	<hr class="sm"/>

	<div class="form-group row">
		<label for="popupSeCode" class="col-sm-2 col-form-label col-form-label-sm">팝업구분</label>
		<div class="col-sm-2">
			<form:select path="popupSeCode" cssClass="custom-select custom-select-sm">
				<form:option value="WINDOW">윈도우</form:option>
				<form:option value="LAYER">레이어</form:option>
			</form:select>
		</div>
	</div>
	<hr class="sm"/>

	<div class="form-group row">
		<label for="" class="col-sm-2 col-form-label col-form-label-sm">팝업위치</label>
		<div class="col-sm-10 row">
			<div class="col-sm-3">
				<div class="input-group input-group-sm">
					<div class="input-group-prepend">
						<label for="popupTop" class="input-group-text">Top</label>
					</div>
					<form:input path="popupTop" cssClass="form-control" placeholder="단위 : px" maxlength="4"/>
				</div>
			</div>
			<div class="col-sm-3">
				<div class="input-group input-group-sm">
					<div class="input-group-prepend">
						<label for="popupTop" class="input-group-text">Left</label>
					</div>
					<form:input path="popupLeft" cssClass="form-control" placeholder="단위 : px" maxlength="4"/>
				</div>
			</div>
		</div>
	</div>
	<hr class="sm"/>
	
	<div class="form-group row">
		<label for="" class="col-sm-2 col-form-label col-form-label-sm">팝업크기</label>
		<div class="col-sm-10 row">
			<div class="col-sm-3">
				<div class="input-group input-group-sm">
					<div class="input-group-prepend">
						<label for="popupWidth" class="input-group-text">Width</label>
					</div>
					<form:input path="popupWidth" cssClass="form-control" placeholder="단위 : px" maxlength="4"/>
				</div>
			</div>
			<div class="col-sm-3">
				<div class="input-group input-group-sm">
					<div class="input-group-prepend">
						<label for="popupWidth" class="input-group-text">Height</label>
					</div>
					<form:input path="popupHeight" cssClass="form-control" placeholder="단위 : px" maxlength="4"/>
				</div>
			</div>
		</div>
	</div>
	<hr class="sm"/>

	<div class="form-group row">
		<label for="popupLink" class="col-sm-2 col-form-label col-form-label-sm">팝업링크</label>
		<div class="col-sm-10">
			<form:input path="popupLink" cssClass="form-control form-control-sm" placeholder="외부연결 : http://popuplink.url, 내부연결 : /resources/mypage.html"/>
		</div>
	</div>
	<hr class="sm"/>
	
	<div class="form-group row">
		<label for="popupImgPath" class="col-sm-2 col-form-label col-form-label-sm">이미지경로</label>
		<div class="col-sm-10">
			<div class="input-group input-group-sm">
				<div class="input-group-prepend">
					<span class="input-group-text"><i class="fas fa-image"></i></span>
				</div>
				<form:input path="popupImgPath" cssClass="form-control form-control-sm" placeholder="/resources/decms/common/image/no-image.png"/>
				<div class="input-group-append">
					<button type="button" class="btn btn-dark btn-sm btnImageFinder" data-event="upload" data-target-url="#popupImgPath" data-target-img="#popup-image">
						<i class="fas fa-upload"></i>
					</button>
				</div>
			</div>
			<small class="text-success"><i class="fas fa-info"></i> 이미지경로 또는 이미지를 업로드 할 수 있습니다.</small>
			<div id="popup-image" class="border text-center p-2" style="width:400px; height:300px;">
				<c:choose>
				<c:when test="${not empty popup.popupImgPath }">
					<img src="${popup.popupImgPath }" style="max-width:100%; max-height:100%;"/>
				</c:when>
				<c:otherwise>
					<img src="${CTX_ROOT }/resources/decms/common/image/no-image.png" style="max-width:100%; max-height:100%;"/>
				</c:otherwise>
				</c:choose>
			</div>
		</div>
	</div>
	<hr class="sm"/>

	<div class="form-group row">
		<label for="actvtyAt" class="col-sm-2 col-form-label col-form-label-sm">활성여부</label>
		<div class="col-sm-2">
			<div class="custom-control custom-switch ">
				<form:checkbox path="actvtyAt" cssClass="custom-control-input" value="Y"/>
				<label class="custom-control-label" for="actvtyAt1">팝업활성</label>
			</div>
		</div>
	</div>
	<hr class="sm"/>

	<div class="form-group row">
		<label for="popupCn" class="col-sm-2 col-form-label col-form-label-sm">팝업내용</label>
		<div class="col-sm-10">
			<form:textarea path="popupCn" cssClass="form-control form-control-sm" rows="3"/>
		</div>
	</div>
	<hr class="sm"/>
	
	
	<div class="text-right mt-3">
		<button type="button" class="btn btn-secondary btn-sm" data-dismiss="modal"><i class="fas fa-ban"></i> 취소</button>
		<button type="submit" class="btn btn-primary btn-sm"><i class="fas fa-save"></i> 저장</button>
	</div>
</form:form>
