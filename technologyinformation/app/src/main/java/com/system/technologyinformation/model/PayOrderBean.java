package com.system.technologyinformation.model;

public class PayOrderBean {
    /**
     * id : 31
     * archive : 0
     * createAt : 2019-09-06 11:30:36
     * updateAt : null
     * userId : 20
     * shopId : 12
     * orderNo : 15724221280201909061130367009717
     * payStatus : 1
     * remissionPrice : 0.14
     * totalPrice : 0.5
     * point : 5
     * finalPrice : 0.31
     */

    private int id;
    private int archive;
    private String createAt;
    private Object updateAt;
    private int userId;
    private int shopId;
    private String orderNo;
    private int payStatus;
    private double remissionPrice;
    private double totalPrice;
    private int point;
    private double finalPrice;

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

    public int getShopId() {
        return shopId;
    }

    public void setShopId(int shopId) {
        this.shopId = shopId;
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

    public double getRemissionPrice() {
        return remissionPrice;
    }

    public void setRemissionPrice(double remissionPrice) {
        this.remissionPrice = remissionPrice;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public int getPoint() {
        return point;
    }

    public void setPoint(int point) {
        this.point = point;
    }

    public double getFinalPrice() {
        return finalPrice;
    }

    public void setFinalPrice(double finalPrice) {
        this.finalPrice = finalPrice;
    }
}
