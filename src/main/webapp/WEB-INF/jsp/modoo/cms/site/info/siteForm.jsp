<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/modoo/common/commonTagLibs.jsp"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	
	<title>사이트 정보 수정</title>
</head>
<body>
	
	<div class="card shadow page-wrapper">
		<div class="card-header">
			<h6 class="m-0 font-weight-bold text-primary">정보수정</h6>
		</div>
		<div class="card-body">
			<p>
				(<i class="fas fa-star text-danger"></i>)는 필수 항목입니다.
			</p>
			<hr/>
			<form:form modelAttribute="site" id="registForm" method="post" action="/decms/ajax/site/info/modifySite.json">
				<fieldset>
					<form:hidden path="siteId"/>
					<form:hidden path="siteSn"/>
				</fieldset>
				<div class="form-group">
					<label for="siteNm" class="required">사이트명</label>
					<form:input path="siteNm" cssClass="form-control form-control-sm" placeholder="사이트명을 입력하세요"/>
				</div>
				<div class="form-row">
					<div class="form-group col-md-6">
						<label for="siteMngrNm">사이트 관리자명</label>
						<form:input path="siteMngrNm" cssClass="form-control form-control-sm" placeholder="사이트 관리자명"/>
					</div>
					<div class="form-group col-md-6">
						<label for="siteSn">사이트 이메일</label>
						<form:input path="siteEmail" cssClass="form-control form-control-sm" placeholder="Ex) mymail@foxedu.co.kr"/>
					</div>
				</div>
				<div class="form-group">
					<label for="siteNm">사이트주소</label>
					<form:input path="siteAdres" cssClass="form-control form-control-sm" placeholder="주소를 입력하세요."/>
				</div>
				<div class="form-row">
					<div class="form-group col-md-6">
						<label for="siteMngrNm">사이트 전화번호</label>
						<form:input path="siteTelno" cssClass="form-control form-control-sm" placeholder="전화번호를 입력하세요."/>
					</div>
					<div class="form-group col-md-6">
						<label for="siteSn">사이트 팩스번호</label>
						<form:input path="siteFaxno" cssClass="form-control form-control-sm" placeholder="팩스번호를 입력하세요."/>
					</div>
				</div>
				<div class="form-group">
					<label for="siteCopyright">Copyright</label>
					<form:textarea path="siteCopyright" cssClass="form-control form-control-sm"/>
				</div>
				<div class="form-group">
					<label for="siteLogoPath">로고경로</label>
					<form:input path="siteLogoPath" cssClass="form-control form-control-sm" placeholder="ex) /resources/logo.png"/>
				</div>
				<div class="form-group">
					<label for="termsCond">이용약관</label>
					<form:textarea path="termsCond" cssClass="form-control form-control-sm summernote"/>
				</div>
				<div class="form-group">
					<label for="privInfo">개인정보보호정책</label>
					<form:textarea path="privInfo" cssClass="form-control form-control-sm summernote"/>
				</div>
				<div class="form-group">
					<label for="eventMarkt">이벤트 마케팅 수신동의</label>
					<form:textarea path="eventMarkt" cssClass="form-control form-control-sm summernote"/>
				</div>
				<div class="form-group">
					<div class="form-check">
						<form:checkbox path="actvtyAt" cssClass="form-check-input" value="Y"/>
						<label class="form-check-label" for="actvtyAt">사이트 활성여부</label>
					</div>
				</div>
				<hr/>

				<div class="form-row">
					<div class="form-group col-md-6">
						<label for="siteMngrNm">사이트 도메인</label>
						<c:forEach var="domain" items="${site.siteDomainList }" varStatus="status">
							<div class="input-group input-group-sm mb-1">
								<div class="input-group-prepend">
									<span class="input-group-text">${status.index + 1 }</span>
									<span class="input-group-text">http://</span>
								</div>
								<form:input path="siteDomainList[${status.index }].domainNm" cssClass="form-control form-control-sm" placeholder="Ex) yourdomain.co.kr"/>
							</div>
						</c:forEach>
					</div>
					
				</div>

				<div class="text-right">
					<button type="button" class="btn btn-secondary btn-sm"><i class="fas fa-ban"></i> 취소</button>
					<button type="submit" class="btn btn-primary btn-sm"><i class="fas fa-save"></i> 저장</button>
				</div>
			</form:form>
		</div>
	</div>
	
	<javascript>
		<script src="${CTX_ROOT }/resources/decms/site/js/siteForm.js"></script>
	</javascript>
</body>
</html>