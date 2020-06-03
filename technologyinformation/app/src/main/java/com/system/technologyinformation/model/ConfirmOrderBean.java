package com.system.technologyinformation.model;

public class ConfirmOrderBean {
    /**
     * id : 275
     * archive : 0
     * createAt : 2019-08-28 21:10:14
     * updateAt : null
     * userId : 6
     * orderNo : 15835568257201908282110145124878
     * totalPrice : 0.02
     * discountPrice : null
     * couponPrice : null
     * finalPrice : null
     * point : 0
     * storeId : 0
     * status : null
     * storeName : null
     * payType : null
     * deliveryId : 317
     */

    private int id;
    private int archive;
    private String createAt;
    private Object updateAt;
    private int userId;
    private String orderNo;
    private double totalPrice;
    private Object discountPrice;
    private Object couponPrice;
    private Object finalPrice;
    private int point;
    private int storeId;
    private Object status;
    private Object storeName;
    private Object payType;
    private int deliveryId;

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

    public Object getUpdateAt() {
        return updateAt;
    }

    public void setUpdateAt(Object updateAt) {
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

    public Object getFinalPrice() {
        return finalPrice;
    }

    public void setFinalPrice(Object finalPrice) {
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

    public Object getStatus() {
        return status;
    }

    public void setStatus(Object status) {
        this.status = status;
    }

    public Object getStoreName() {
        return storeName;
    }

    public void setStoreName(Object storeName) {
        this.storeName = storeName;
    }

    public Object getPayType() {
        return payType;
    }

    public void setPayType(Object payType) {
        this.payType = payType;
    }

    public int getDeliveryId() {
        return deliveryId;
    }

    public void setDeliveryId(int deliveryId) {
        this.deliveryId = deliveryId;
    }
}
