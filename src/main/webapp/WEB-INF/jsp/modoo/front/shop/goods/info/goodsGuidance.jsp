<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/modoo/common/commonTagLibs.jsp"%>
	<div class="sub-tit-area">
		<h2>교환/반품/안내</h2>
	</div>
	<ul class="border-list etc-list">
		<li>
			<cite>판매자 정보</cite>
			<div>
				업체명  : <c:out value="${cmpny.cmpnyNm }"/><br />
				통신판매업신고번호 : <c:out value="${cmpny.bizrno }"/><br />
				사업장 소재지 : <c:out value="${cmpny.bsnmAdres }"/><br />
				<p class="fs-sm fc-gr">* 상품정보에 오류가 있을 경우 고객센터(<c:out value="${cmpny.chargerTelno }"/>)로 연락주시면 즉시 조치하도록 하겠습니다.</p>
			</div>
		</li>
		<li>
			<cite>교환/반품 접수 안내</cite>
			<div>
				<ol class="bullet-demical">
					<li>마이페이지 주문관리 으로 이동 &gt; 주문확인으로 이동</li>
					<li>주문건에서 교환/반품 버튼 선택하여 접수 진행</li>
				</ol>
				<a href="#none" class="fc-gr">접수하러 바로가기 <i class="ico-arr-r gr sm"></i></a>
			</div>
		</li>
		<li>
			<cite>교환/반품 규정안내</cite>
			<div>
				<cite>교환/반품 기간</cite>
				<ul class="bullet">
					<li>
						교환/반품은 구매확정 전까지 가능합니다.<br />
						(상품을 받으신 날로부터 7일 경과 시 자동 구매확정이 됩니다.)
					</li>
					<li>
						구매확정이 된 이후에는 고객센터나 판매자를 통해서 교환/반품 가능여부를 확인할 수 있습니
						다.
					</li>
					<li>
						주문제작, 설치 상품 등 일부 상품은 교환/반품 기준이 상이할 수 있습니다.
					</li>
					<li>
						스마트픽 픽업 배송상품은 스마트픽 픽업 지점에 상품도착일로부터 5일까지 픽업 가능합니다.
						픽업가능일 경과시 자동으로 반품되며 반품배송비가 차감 후 환불되거나, 필요시 고객센터를
						통해서 반품비에 대한 결제 안내를 받으시게 됩니다.
					</li>
				</ul>
				<cite>교환/반품 불가 사유</cite>
				<ul class="bullet">
					<li>고객님의 책임 있는 사유로 상품 등의 가치가 심하게 파손되거나 훼손된 경우</li>
					<li>소비자의 사용 또는 소비에 의해 상품 등의 가치가 현저히 감소한 경우</li>
					<li>시간의 경과에 의해 재판매가 곤란할 정도로 상품 등의 가치가 현저히 감소한 경우</li>
					<li>복제가 가능한 상품 등의 포장을 훼손할 경우</li>
					<li>
						판매/생산방식의 특성상, 교환/반품 시 판매자에게 회복할 수 없는 손해가 발생하는 경우
						(주문접수 후 개별생산, 맞춤 제작 등)
					</li>
					<li>
						기타, '전자상거래 등에서의 소비자보호에 관한 법률'이 정하는 청약철회 제한사유에 해당되는
						경우
					</li>
				</ul>
			</div>
		</li>
	</ul>