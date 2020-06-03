package com.system.technologyinformation.model;

import com.google.gson.annotations.SerializedName;

public class UserRechargeWxBean {


    /**
     * prePayParams : null
     * payUri : null
     * appId : wxb2911332cf274f1f
     * timeStamp : 1553674603
     * nonceStr : bnMUMSqsQJZhflFZ
     * signType : MD5
     * paySign : CD2ACC150DC7D9EC23A8E446CA975D56
     * orderAmount : null
     * orderId : null
     * outTradeNo : null
     * mwebUrl : null
     * prypayId : wx27161643235708d50df788c04078099704
     * package : Sign=WXPay
     */

    @SerializedName("prePayParams")
    private Object prePayParams;
    @SerializedName("payUri")
    private Object payUri;
    @SerializedName("appId")
    private String appId;
    @SerializedName("timeStamp")
    private String timeStamp;
    @SerializedName("nonceStr")
    private String nonceStr;
    @SerializedName("signType")
    private String signType;
    @SerializedName("paySign")
    private String paySign;
    @SerializedName("orderAmount")
    private Object orderAmount;
    @SerializedName("orderId")
    private Object orderId;
    @SerializedName("outTradeNo")
    private Object outTradeNo;
    @SerializedName("mwebUrl")
    private Object mwebUrl;
    @SerializedName("prypayId")
    private String prypayId;
    @SerializedName("package")
    private String packageX;

    public Object getPrePayParams() {
        return prePayParams;
    }

    public void setPrePayParams(Object prePayParams) {
        this.prePayParams = prePayParams;
    }

    public Object getPayUri() {
        return payUri;
    }

    public void setPayUri(Object payUri) {
        this.payUri = payUri;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }

    public String getNonceStr() {
        return nonceStr;
    }

    public void setNonceStr(String nonceStr) {
        this.nonceStr = nonceStr;
    }

    public String getSignType() {
        return signType;
    }

    public void setSignType(String signType) {
        this.signType = signType;
    }

    public String getPaySign() {
        return paySign;
    }

    public void setPaySign(String paySign) {
        this.paySign = paySign;
    }

    public Object getOrderAmount() {
        return orderAmount;
    }

    public void setOrderAmount(Object orderAmount) {
        this.orderAmount = orderAmount;
    }

    public Object getOrderId() {
        return orderId;
    }

    public void setOrderId(Object orderId) {
        this.orderId = orderId;
    }

    public Object getOutTradeNo() {
        return outTradeNo;
    }

    public void setOutTradeNo(Object outTradeNo) {
        this.outTradeNo = outTradeNo;
    }

    public Object getMwebUrl() {
        return mwebUrl;
    }

    public void setMwebUrl(Object mwebUrl) {
        this.mwebUrl = mwebUrl;
    }

    public String getPrypayId() {
        return prypayId;
    }

    public void setPrypayId(String prypayId) {
        this.prypayId = prypayId;
    }

    public String getPackageX() {
        return packageX;
    }

    public void setPackageX(String packageX) {
        this.packageX = packageX;
    }
}
