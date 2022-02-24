<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="egovframework.com.cmm.LoginVO" %>
<%@ page import="egovframework.com.cmm.util.EgovUserDetailsHelper" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="modoo" uri="http://ui.modooui.ai/ui"%>
<c:set var="CTX_ROOT" value="${pageContext.request.contextPath}"/>
<c:set var="USER_ID"><%=( egovframework.com.cmm.util.EgovUserDetailsHelper.getAuthenticatedUser() != null ? ((LoginVO)egovframework.com.cmm.util.EgovUserDetailsHelper.getAuthenticatedUser()).getId() : "") %></c:set>
<c:set var="USER_NAME"><%=( egovframework.com.cmm.util.EgovUserDetailsHelper.getAuthenticatedUser() != null ? ((LoginVO)egovframework.com.cmm.util.EgovUserDetailsHelper.getAuthenticatedUser()).getName() : "") %></c:set>
<c:set var="SITE_ID"><%=modoo.module.common.util.SiteDomainHelper.getSiteId() %></c:set>
<c:set var="IS_ADMIN"><%=(egovframework.com.cmm.util.EgovUserDetailsHelper.getAuthorities().contains("ROLE_ADMIN") == true ? "Y":"N") %></c:set>
<c:set var="USER_ROLE"><%=egovframework.com.cmm.util.EgovUserDetailsHelper.getAuthorities() %></c:set>
<c:set var="CMS_MODE"><%=egovframework.com.cmm.service.Globals.CMS_MODE %></c:set>
<c:set var="KAKAO_KEY"><%=egovframework.com.cmm.service.Globals.KAKAO_KEY %></c:set>
<c:set var="HTTP">
	<c:choose>
	<c:when test="${CMS_MODE eq 'REAL' }">http</c:when>
	<c:otherwise>http</c:otherwise>
	</c:choose>
</c:set>
<c:set var="BASE_URL" value="${HTTP}://${pageContext.request.serverName}${pageContext.request.contextPath}"/>