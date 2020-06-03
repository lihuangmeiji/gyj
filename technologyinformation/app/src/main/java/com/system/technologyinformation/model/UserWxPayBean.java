package com.system.technologyinformation.model;

import com.google.gson.annotations.SerializedName;

public class UserWxPayBean {

   private UserRechargeWxBean wxPayRequest;

    public UserRechargeWxBean getWxPayRequest() {
        return wxPayRequest;
    }

    public void setWxPayRequest(UserRechargeWxBean wxPayRequest) {
        this.wxPayRequest = wxPayRequest;
    }

    private PayOrderBean order;

    public PayOrderBean getOrder() {
        return order;
    }

    public void setOrder(PayOrderBean order) {
        this.order = order;
    }
}
