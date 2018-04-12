package com.fuhuitong.applychain.model;

public class Charge {


    private String chargeId;//varchar(64) NOT NULL费率主键
    private String merId;//varchar(64) NOT NULL商户编号
    private double cash;//double NULL现金
    private double alipay;//double NULL支付宝费率
    private double wechat;//double NULL微信费率
    private double bestpay;//double NULL翼支付费率
    private double debit;//double NULL借记卡费率
    private double debitMax;//double NULL借记卡峰值
    private double credit;//double NULL贷记卡费率
    private double creditMax;//double NULL借记卡峰值

    public String getChargeId() {
        return chargeId;
    }

    public void setChargeId(String chargeId) {
        this.chargeId = chargeId;
    }

    public String getMerId() {
        return merId;
    }

    public void setMerId(String merId) {
        this.merId = merId;
    }

    public double getCash() {
        return cash;
    }

    public void setCash(double cash) {
        this.cash = cash;
    }

    public double getAlipay() {
        return alipay;
    }

    public void setAlipay(double alipay) {
        this.alipay = alipay;
    }

    public double getWechat() {
        return wechat;
    }

    public void setWechat(double wechat) {
        this.wechat = wechat;
    }

    public double getBestpay() {
        return bestpay;
    }

    public void setBestpay(double bestpay) {
        this.bestpay = bestpay;
    }

    public double getDebit() {
        return debit;
    }

    public void setDebit(double debit) {
        this.debit = debit;
    }

    public double getDebitMax() {
        return debitMax;
    }

    public void setDebitMax(double debitMax) {
        this.debitMax = debitMax;
    }

    public double getCredit() {
        return credit;
    }

    public void setCredit(double credit) {
        this.credit = credit;
    }

    public double getCreditMax() {
        return creditMax;
    }

    public void setCreditMax(double creditMax) {
        this.creditMax = creditMax;
    }
}

