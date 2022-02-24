<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/modoo/common/commonTagLibs.jsp"%>

<form id="inicisFrm" name="" method="post" >
	<%-- 필수 --%>
	<input type="hidden" name="version" value="1.0"/>
	<input type="hidden" name="mid" value="${mid}"/>
	<input type="hidden" name="oid" value="${oid}"/>
	<input type="hidden" id="goodname" name="goodname" value="${goodname}"/>
	<input type="hidden" id="price" name="price" value="${price}"/>
	<input type="hidden" name="currency" value="WON"/>
	<input type="hidden" name="buyername" value="${ordName}"/>
	<input type="hidden" name="buyertel" value="${ordTelno}"/>
	<input type="hidden" name="buyeremail" value="${ordEmail}"/>
	<input type="hidden" name="timestamp" value="${timestamp}"/>
	<input type="hidden" name="signature" value="${signature}"/>
	<input type="hidden" name="mKey" value="${mKey}"/>
	<input type="hidden" name="returnUrl" value="http://store.foxedu.kr/shop/goods/goodsOrder.do?cartno=1008&pg=result"/>
		
	<%-- 기본 옵션 --%>
	<input type="hidden" name="gopaymethod" value=""/>
	
	<c:choose>
		<c:when test="${subscriptionAt eq 'Y'}">
			<c:set var="offerPeriod" value="M2"/>
			<c:set var="acceptmethod" value="BILLAUTH(card)"/>
			
			<input type="hidden" name="billPrint_msg" value="고객님의 매월 결제일은 ${orderGroup.orderList[0].sbscrptDlvyDay}일 입니다.">
		</c:when>
		<c:otherwise>
			<c:set var="offerPeriod" value=""/>
			<c:set var="acceptmethod" value="CARDPOINT:HPP(1):no_receipt:va_receipt:below1000"/>
			
			<%-- 결제 수단별 옵션 --%>
			<input type="hidden" name="quotabase" value="2:3:4:5:6:11:12:24:36"/>
			<input type="hidden" name="ini_onlycardcode" value=""/>
			<input type="hidden" name="ini_cardcode" value=""/>
			<input type="hidden" name="ansim_quota" value=""/>
		</c:otherwise>
	</c:choose>
	<input type="hidden" name="offerPeriod" value="${offerPeriod}"/>
	<input type="hidden" name="acceptmethod" value="${acceptmethod}"/>
	 
	<%-- 표시 옵션 --%>
	
	<input type="hidden" name="payViewType" value="" >
	<input type="hidden" name="closeUrl" value="${siteDomain}/close.jsp"/>
	<input type="hidden" name="popupUrl" value="${siteDomain}/popup.jsp"/>
</form>

<script>
$(document).ready(function(){
	INIStdPay.pay('inicisFrm');
});
</script>
