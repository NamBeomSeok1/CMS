<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/modoo/common/commonTagLibs.jsp"%>
    <div class="popup w1200" data-popup="subscribeHistory" style="display:hidden;">
        <div class="pop-header">
            <h1>회차별 구독현황</h1>
            <button type="button" class="btn-pop-close" data-popup-close="subscribeHistory">닫기</button>
        </div>
        <div class="pop-body">
            <p class="mb10" id="order-no-history"><strong id="order-no-history">주문번호 :</strong>${orderInfo.orderNo}</p>
            <div class="m-list-type">
                <table>
                    <caption>회차별 구독현황</caption>
                    <colgroup>
                        <col style="width:5%" />
                        <col style="width:10%" />
                        <col style="width:10%" />
                        <col />
                        <col />
                        <col style="width:15%" />
                        <col style="width:15%" />
                    </colgroup>
                    <thead>
                        <tr>
                            <th scope="col">회차</th>
                            <th scope="col">결제주기</th>
                            <th scope="col">결제날짜(요일)</th>
                            <th scope="col">상품명</th>
                            <th scope="col">수량 / 옵션</th>
                            <th scope="col">배송지</th>
                            <th scope="col">결제일</th>
                            <th scope="col">결제금액</th>
                        </tr>
                    </thead>
                    <tbody id="tbody-history">
                    </tbody>
                </table>
            </div>
            <ul class="paging" id="paging-history">
            </ul>
        </div>
    </div>