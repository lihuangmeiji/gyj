package com.system.technologyinformation.model;

public class CreditsExchangePayBean {
    private String qrCode;
    private String aliCallback;
    private String orderNo;
    private UserRechargeWxBean payResponse;

    public String getQrCode() {
        return qrCode;
    }

    public void setQrCode(String qrCode) {
        this.qrCode = qrCode;
    }

    public String getAliCallback() {
        return aliCallback;
    }

    public void setAliCallback(String aliCallback) {
        this.aliCallback = aliCallback;
    }

    public UserRechargeWxBean getPayResponse() {
        return payResponse;
    }

    public void setPayResponse(UserRechargeWxBean payResponse) {
        this.payResponse = payResponse;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }
}
