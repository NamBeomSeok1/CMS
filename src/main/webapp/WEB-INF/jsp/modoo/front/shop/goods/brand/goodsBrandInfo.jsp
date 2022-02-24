<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/modoo/common/commonTagLibs.jsp"%>
<c:set var="title" value="브랜드소개"/>
<!DOCTYPE html>
<html>
<head>
	<title>${title}</title>
</head>
<body>
<div class="wrap">
	<div class="sub-contents">
		<div class="location">
			<a href="${CTX_ROOT}/index.do"><strong>HOME</strong></a>
			<a href="${CTX_ROOT}/index.do">브랜드관</a>
			<a href="${CTX_ROOT}/shop/goods/brandGoodsList.do?searchGoodsBrandId=${brand.brandId}"><c:out value="${brand.brandNm }"/></a>
		</div>
		<h2 class="txt-hide">브랜드 소개</h2>
		<section class="brand-intro">
				<c:choose>
					<c:when test="${empty brand.brandImageThumbPath}">
						<span class="logo-brand-txt">
                       		 ${brand.brandNm}
                    	</span>
					</c:when>
					<c:otherwise>
						<span class="logo-brand">
							<img src="<c:out value="${brand.brandImageThumbPath }"/>" alt="브랜드로고" />
						</span>
					</c:otherwise>
				</c:choose>
			<figure class="img-full">
				<c:choose>
					<c:when test="${empty brand.brandImageThumbPath}">
						<img src="${CTX_ROOT }/resources/decms/common/image/brand_noimg_web.jpeg" alt="<c:out value="${brand.brandNm }"/>" class="m-none" />
						<img src="${CTX_ROOT }/resources/decms/common/image/brand_noimg_mob.jpeg" alt="<c:out value="${brand.brandNm }"/>" class="m-block" />
					</c:when>
					<c:otherwise>
						<img src="<c:out value="${brand.intBrandImageList[0].brandImagePath }"/>" alt="브랜드 소개 이미지" />
					</c:otherwise>
				</c:choose>
			</figure>
			<h3>
				About <c:out value="${brand.brandNm }"/><br />
			</h3>
			<p>
			<c:choose>
				<c:when test="${empty brand.brandIntSj}">
					안녕하세요. <c:out value="${brand.brandNm }"/> 입니다.
				</c:when>
				<c:otherwise>
					<modoo:crlf content="${brand.brandIntCn }"/>
				</c:otherwise>
			</c:choose>
			</p>
			<a href="${CTX_ROOT }/shop/goods/brandGoodsList.do?searchGoodsBrandId=${searchVO.searchGoodsBrandId}" class="btn-lg width spot2">
				상품 보러가기 <i class="ico-arr-r bk sm back" aria-hidden="true"></i></a>
		</section>	
	</div>
</div>
</body>
</html>