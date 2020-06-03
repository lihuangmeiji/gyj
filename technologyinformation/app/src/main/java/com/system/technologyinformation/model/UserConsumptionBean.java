package com.system.technologyinformation.model;

public class UserConsumptionBean {


    /**
     * id : 1078
     * shopName : Z工友家-张阿甜
     * shopId : 21
     * createTime : 2019-10-22 22:50:12
     * price : 0.08
     */

    private int id;
    private String shopName;
    private int shopId;
    private String createTime;
    private double price;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public int getShopId() {
        return shopId;
    }

    public void setShopId(int shopId) {
        this.shopId = shopId;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
