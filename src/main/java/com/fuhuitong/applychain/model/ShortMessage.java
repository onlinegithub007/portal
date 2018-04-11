package com.fuhuitong.applychain.model;

import java.util.Date;

public class ShortMessage {
    private Integer msgId;

    private String destAddr;

    private String message;

    private Date sendDate;
    
    private String sendDateText;

    public Integer getMsgId() {
        return msgId;
    }

    public void setMsgId(Integer msgId) {
        this.msgId = msgId;
    }

    public String getDestAddr() {
        return destAddr;
    }

    public void setDestAddr(String destAddr) {
        this.destAddr = destAddr == null ? null : destAddr.trim();
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message == null ? null : message.trim();
    }

    public Date getSendDate() {
        return sendDate;
    }

    public void setSendDate(Date sendDate) {
        this.sendDate = sendDate;
    }
    
    public void setSendDateText(String sendDateText) {
		this.sendDateText = sendDateText;
	}
    
    public String getSendDateText() {
		return sendDateText;
	}
}