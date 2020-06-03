package com.system.technologyinformation.model;

import com.google.gson.annotations.SerializedName;

public class StoreOrderBean {

    /**
     * id : 113
     * archive : 0
     * createAt : 2019-03-26 10:35:35
     * updateAt : 2019-03-26 10:35:35
     * userId : 6
     * orderNo : f521184b0fda43f987b7821bf671d23d
     * payStatus : 0
     * totalPrice : 30
     * discountPrice : null
     * couponPrice : null
     * finalPrice : 30
     * point : null
     * storeId : 1
     */

    @SerializedName("id")
    private int id;
    @SerializedName("archive")
    private int archive;
    @SerializedName("createAt")
    private String createAt;
    @SerializedName("updateAt")
    private String updateAt;
    @SerializedName("userId")
    private int userId;
    @SerializedName("orderNo")
    private String orderNo;
    @SerializedName("payStatus")
    private int payStatus;
    @SerializedName("totalPrice")
    private double totalPrice;
    @SerializedName("discountPrice")
    private Object discountPrice;
    @SerializedName("couponPrice")
    private Object couponPrice;
    @SerializedName("finalPrice")
    private double finalPrice;
    @SerializedName("point")
    private int point;
    @SerializedName("storeId")
    private int storeId;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getArchive() {
        return archive;
    }

    public void setArchive(int archive) {
        this.archive = archive;
    }

    public String getCreateAt() {
        return createAt;
    }

    public void setCreateAt(String createAt) {
        this.createAt = createAt;
    }

    public String getUpdateAt() {
        return updateAt;
    }

    public void setUpdateAt(String updateAt) {
        this.updateAt = updateAt;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public int getPayStatus() {
        return payStatus;
    }

    public void setPayStatus(int payStatus) {
        this.payStatus = payStatus;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public Object getDiscountPrice() {
        return discountPrice;
    }

    public void setDiscountPrice(Object discountPrice) {
        this.discountPrice = discountPrice;
    }

    public Object getCouponPrice() {
        return couponPrice;
    }

    public void setCouponPrice(Object couponPrice) {
        this.couponPrice = couponPrice;
    }

    public double getFinalPrice() {
        return finalPrice;
    }

    public void setFinalPrice(double finalPrice) {
        this.finalPrice = finalPrice;
    }

    public int getPoint() {
        return point;
    }

    public void setPoint(int point) {
        this.point = point;
    }

    public int getStoreId() {
        return storeId;
    }

    public void setStoreId(int storeId) {
        this.storeId = storeId;
    }
}
