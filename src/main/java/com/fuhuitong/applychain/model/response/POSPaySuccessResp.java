package com.fuhuitong.applychain.model.response;

public class POSPaySuccessResp {
    private HeadResp headResp;

    private String retCode;

    private String orderId;
    private String orderCode;
    private String memberCode;

    private String memberName;

    private int memberLevel;

    private int memberScore;

    private int menberBalance;

    private String printInfo;

    private String payBillNumber;

    public String getOrderCode() {
        return orderCode;
    }

    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode;
    }

    public String getPayBillNumber() {
        return payBillNumber;
    }

    public void setPayBillNumber(String payBillNumber) {
        this.payBillNumber = payBillNumber;
    }

    public HeadResp getHeadResp() {
        return headResp;
    }

    public void setHeadResp(HeadResp headResp) {
        this.headResp = headResp;
    }

    public String getRetCode() {
        return retCode;
    }

    public void setRetCode(String retCode) {
        this.retCode = retCode;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getMemberCode() {
        return memberCode;
    }

    public void setMemberCode(String memberCode) {
        this.memberCode = memberCode;
    }

    public String getMemberName() {
        return memberName;
    }

    public void setMemberName(String memberName) {
        this.memberName = memberName;
    }

    public int getMemberLevel() {
        return memberLevel;
    }

    public void setMemberLevel(int memberLevel) {
        this.memberLevel = memberLevel;
    }

    public int getMemberScore() {
        return memberScore;
    }

    public void setMemberScore(int memberScore) {
        this.memberScore = memberScore;
    }

    public int getMenberBalance() {
        return menberBalance;
    }

    public void setMenberBalance(int menberBalance) {
        this.menberBalance = menberBalance;
    }

    public String getPrintInfo() {
        return printInfo;
    }

    public void setPrintInfo(String printInfo) {
        this.printInfo = printInfo;
    }


}
