package com.system.technologyinformation.model;

public class UserOrderVoListBean {

    /**
     * id : 103
     * archive : 0
     * createAt : 2019-05-21 15:15:38
     * updateAt : 2019-05-21 15:15:38
     * num : 2
     * skuId : null
     * productId : 37
     * userId : 6
     * status : 1
     * deliveryId : 45
     * product : null
     * totalPrice : 0.03
     */

    private int id;
    private int archive;
    private String createAt;
    private String updateAt;
    private int num;
    private Object skuId;
    private int productId;
    private int userId;
    private int status;
    private int deliveryId;
    private OnlineSupermarketBean product;
    private double totalPrice;

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

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public Object getSkuId() {
        return skuId;
    }

    public void setSkuId(Object skuId) {
        this.skuId = skuId;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getDeliveryId() {
        return deliveryId;
    }

    public void setDeliveryId(int deliveryId) {
        this.deliveryId = deliveryId;
    }

    public OnlineSupermarketBean getProduct() {
        return product;
    }

    public void setProduct(OnlineSupermarketBean product) {
        this.product = product;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }
}
