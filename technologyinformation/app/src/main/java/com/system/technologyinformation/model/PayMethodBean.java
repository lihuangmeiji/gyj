package com.system.technologyinformation.model;

public class PayMethodBean {

    /**
     * aliPay : true
     * wxPay : false
     */

    private boolean aliPay;
    private boolean wxPay;

    public boolean isAliPay() {
        return aliPay;
    }

    public void setAliPay(boolean aliPay) {
        this.aliPay = aliPay;
    }

    public boolean isWxPay() {
        return wxPay;
    }

    public void setWxPay(boolean wxPay) {
        this.wxPay = wxPay;
    }
}
