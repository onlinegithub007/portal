package com.fuhuitong.applychain.model.response;

/**
 * Created by Administrator on 2018/4/17 0017.
 */
public class PosRepaymentResp {

    private HeadResp headResp;
    private String retCode;

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
}
