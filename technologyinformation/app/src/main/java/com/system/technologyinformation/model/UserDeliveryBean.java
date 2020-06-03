package com.system.technologyinformation.model;

import java.util.List;

public class UserDeliveryBean {

    /**
     * id : 45
     * archive : 0
     * createAt : 2019-05-21 15:15:38
     * updateAt : 2019-05-21 15:16:01
     * time : null
     * storeId : null
     * orderId : 144
     * status : 0
     * orderVoList : []
     */

    private int id;
    private int archive;
    private String createAt;
    private String updateAt;
    private List<Integer> time;
    private Object storeId;
    private int orderId;
    private int status;
    private String qrCodeImg;
    private List<UserOrderVoListBean> orderVoList;

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

    public List<Integer> getTime() {
        return time;
    }

    public void setTime(List<Integer> time) {
        this.time = time;
    }

    public Object getStoreId() {
        return storeId;
    }

    public void setStoreId(Object storeId) {
        this.storeId = storeId;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public List<UserOrderVoListBean> getOrderVoList() {
        return orderVoList;
    }

    public void setOrderVoList(List<UserOrderVoListBean> orderVoList) {
        this.orderVoList = orderVoList;
    }

    public String getQrCodeImg() {
        return qrCodeImg;
    }

    public void setQrCodeImg(String qrCodeImg) {
        this.qrCodeImg = qrCodeImg;
    }
}
