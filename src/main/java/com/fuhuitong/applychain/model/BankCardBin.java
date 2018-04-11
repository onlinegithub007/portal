package com.fuhuitong.applychain.model;

public class BankCardBin {
    private Integer cardBinId;

    private String bankName;

    private String bankInsCode;

    private String cardName;

    private Integer cardNoLen;

    private String cardBin;

    private Integer cardType;

    public Integer getCardBinId() {
        return cardBinId;
    }

    public void setCardBinId(Integer cardBinId) {
        this.cardBinId = cardBinId;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName == null ? null : bankName.trim();
    }

    public String getBankInsCode() {
        return bankInsCode;
    }

    public void setBankInsCode(String bankInsCode) {
        this.bankInsCode = bankInsCode == null ? null : bankInsCode.trim();
    }

    public String getCardName() {
        return cardName;
    }

    public void setCardName(String cardName) {
        this.cardName = cardName == null ? null : cardName.trim();
    }

    public Integer getCardNoLen() {
        return cardNoLen;
    }

    public void setCardNoLen(Integer cardNoLen) {
        this.cardNoLen = cardNoLen;
    }

    public String getCardBin() {
        return cardBin;
    }

    public void setCardBin(String cardBin) {
        this.cardBin = cardBin == null ? null : cardBin.trim();
    }

    public Integer getCardType() {
        return cardType;
    }

    public void setCardType(Integer cardType) {
        this.cardType = cardType;
    }
}