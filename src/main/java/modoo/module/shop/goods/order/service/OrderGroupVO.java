package modoo.module.shop.goods.order.service;

import java.util.List;

import modoo.module.card.service.CreditCardVO;
import modoo.module.shop.user.service.DlvyAdresVO;

public class OrderGroupVO {


    /**
     * 주문그룹고유번호
     */
    private java.math.BigDecimal orderGroupNo;

    /**
     * 배송주소
     */
    private DlvyAdresVO dlvyInfo;

    /**
     * 신규배송주소여부
     */
    private boolean isNewDlvyInfo;

    /**
     * 주문목록
     */
    private List<OrderVO> orderList;

    /**
     * 개인정보동의 여부
     */
    private String indvdlinfoAgreAt;

    /**
     * 구매조건동의 여부
     */
    private String purchsCndAgreAt;
    /**
     * 도서산간지역 금액
     */
    private java.math.BigDecimal islandDlvyPc;
    /**
     * 도서산간지역 체크
     */
    private String islandChk;
    /**
     * 신용카드 정보
     */
    private CreditCardVO creditCard;
    /**
     * 지불 방법 * card,point,both
     */
    private String payMethod;
    /**
     * 주문자 전화번호
     */
    private String ordTelno1;
    private String ordTelno2;
    private String ordTelno3;
    /**
     * 주문자 이메일
     */
    private String ordEmail1;
    private String ordEmail2;

    /**
     * 주문자 이름
     */
    private String ordName;

    /**
     * 구독주문 여부
     */
    private String subscriptionAt;

    /**
     * 이니시스API,MODULE 유형
     */
    private String payment;
    /**
     * 이니시스 주문 유형
     */
    private String goPayMethod;

    public String getIslandChk() {
        return islandChk;
    }

    public void setIslandChk(String islandChk) {
        this.islandChk = islandChk;
    }

    public java.math.BigDecimal getOrderGroupNo() {
        return orderGroupNo;
    }

    public void setOrderGroupNo(java.math.BigDecimal orderGroupNo) {
        this.orderGroupNo = orderGroupNo;
    }

    public DlvyAdresVO getDlvyInfo() {
        return dlvyInfo;
    }

    public void setDlvyInfo(DlvyAdresVO dlvyInfo) {
        this.dlvyInfo = dlvyInfo;
    }

    public List<OrderVO> getOrderList() {
        return orderList;
    }

    public void setOrderList(List<OrderVO> orderList) {
        this.orderList = orderList;
    }

    public String getIndvdlinfoAgreAt() {
        return indvdlinfoAgreAt;
    }

    public void setIndvdlinfoAgreAt(String indvdlinfoAgreAt) {
        this.indvdlinfoAgreAt = indvdlinfoAgreAt;
    }

    public String getPurchsCndAgreAt() {
        return purchsCndAgreAt;
    }

    public void setPurchsCndAgreAt(String purchsCndAgreAt) {
        this.purchsCndAgreAt = purchsCndAgreAt;
    }

    public boolean isNewDlvyInfo() {
        return isNewDlvyInfo;
    }

    public void setNewDlvyInfo(boolean isNewDlvyInfo) {
        this.isNewDlvyInfo = isNewDlvyInfo;
    }

    public CreditCardVO getCreditCard() {
        return creditCard;
    }

    public void setCreditCard(CreditCardVO creditCard) {
        this.creditCard = creditCard;
    }

    public String getPayMethod() {
        return payMethod;
    }

    public void setPayMethod(String payMethod) {
        this.payMethod = payMethod;
    }

    public String getOrdTelno1() {
        return ordTelno1;
    }

    public void setOrdTelno1(String ordTelno1) {
        this.ordTelno1 = ordTelno1;
    }

    public String getOrdTelno2() {
        return ordTelno2;
    }

    public void setOrdTelno2(String ordTelno2) {
        this.ordTelno2 = ordTelno2;
    }

    public String getOrdTelno3() {
        return ordTelno3;
    }

    public void setOrdTelno3(String ordTelno3) {
        this.ordTelno3 = ordTelno3;
    }

    public String getOrdEmail1() {
        return ordEmail1;
    }

    public void setOrdEmail1(String ordEmail1) {
        this.ordEmail1 = ordEmail1;
    }

    public String getOrdEmail2() {
        return ordEmail2;
    }

    public void setOrdEmail2(String ordEmail2) {
        this.ordEmail2 = ordEmail2;
    }

    public String getOrdName() {
        return ordName;
    }

    public void setOrdName(String ordName) {
        this.ordName = ordName;
    }

    public java.math.BigDecimal getIslandDlvyPc() {
        return islandDlvyPc;
    }

    public void setIslandDlvyPc(java.math.BigDecimal islandDlvyPc) {
        this.islandDlvyPc = islandDlvyPc;
    }

    public String getSubscriptionAt() {
        return subscriptionAt;
    }

    public void setSubscriptionAt(String subscriptionAt) {
        this.subscriptionAt = subscriptionAt;
    }

    public String getPayment() {
        return payment;
    }

    public void setPayment(String payment) {
        this.payment = payment;
    }

    public String getGoPayMethod() {
        return goPayMethod;
    }

    public void setGoPayMethod(String goPayMethod) {
        this.goPayMethod = goPayMethod;
    }
}
