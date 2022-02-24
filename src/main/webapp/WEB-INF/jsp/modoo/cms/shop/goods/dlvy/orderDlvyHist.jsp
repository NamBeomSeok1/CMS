<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/modoo/common/commonTagLibs.jsp"%>
<!-- Large Modal -->
<div class="modal fade bd-example-modal-lg" id="orderDlvyModal" tabindex="-1" role="dialog" aria-labelledby="modal-title-large" aria-hidden="true">
  <div class="modal-dialog modal-lg" role="document">
    <div class="modal-content">
      <div class="modal-header">
        <h5 class="modal-title" id="modal-title-large">회차별 구독현황</h5>
        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
          <span aria-hidden="true">&times;</span>
        </button>
      </div>
      <div class="modal-body" id="modal-body-large">
      </div>
<!--       <div class="modal-footer"> -->
<!--       </div> -->
    </div>
  </div>
</div>

<!-- Small modal -->
<div class="modal fade bd-example-modal-sm" id="editOrderDlvy" tabindex="-1" role="dialog" aria-labelledby="mySmallModalLabel" aria-hidden="true">
  <div class="modal-dialog modal-sm">
    <div class="modal-content">
    <div class="modal-header">
        <h5 class="modal-title" id="mySmallModalLabel">송장번호 입력</h5>
        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
          <span aria-hidden="true">&times;</span>
        </button>
      </div>
		<div class="modal-body">
			<div class="input-group input-group-sm">
				<div class="input-group-prepend">
						<span class="input-group-text">송장번호</span>
					</div>
					<input type="hidden" name="orderDlvyNo">
					<input name="invcNo" class="form-control" onkeyup="checkInvcNo(this);"/>
					<small class="form-text text-muted">※송장번호는 숫자만 입력할 수 있습니다. 하이픈(-)을 입력하지 말아주세요.</small>
			</div>
			<div class="form-check">
			    <input type="checkbox" class="form-check-input" id="withoutInvcNo">
			    <label style="color:red !important;" class="form-check-label" for="withoutInvcNo">수동으로 배송완료로 변경하기</label>
			  </div>
	      </div>
      <div class="modal-footer">
	      <div class="col-sm-6">
			<button type="button" id="editOrderDlvyBtn" class="btn btn-dark btn-block"><i class="fas"></i>입력</button>
		</div>
      </div>
    </div>
  </div>
</div>

<!-- 쿠폰 modal -->
<div class="modal fade bd-example-modal-lg" id="orderCouponModal" tabindex="-1" role="dialog" aria-labelledby="modal-title-large" aria-hidden="true">
  <div class="modal-dialog modal-lg" role="document">
    <div class="modal-content">
      <div class="modal-header">
        <h5 class="modal-title" id="modal-title-large">주문 쿠폰 현황</h5>
        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
          <span aria-hidden="true">&times;</span>
        </button>
      </div>
      <div class="modal-body" id="modal-body-large-coupon">
      </div>
<!--       <div class="modal-footer"> -->
<!--       </div> -->
    </div>
  </div>
</div>

