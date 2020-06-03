package com.system.technologyinformation.model;

public class RtcOrderBean {

    /**
     * id : 15
     * archive : 0
     * createAt : 2019-09-06 23:52:03
     * updateAt : null
     * orderNo : 15835568257201909062352031171797
     * payNo : null
     * payType : null
     * rechargeNo : null
     * totalPrice : 1
     * inPrice : 1.06
     * finalPrice : 0.95
     * phone : 15835568257
     * status : 1
     */

    private int id;
    private int archive;
    private String createAt;
    private Object updateAt;
    private String orderNo;
    private Object payNo;
    private Object payType;
    private Object rechargeNo;
    private int totalPrice;
    private double inPrice;
    private double finalPrice;
    private String phone;
    private int status;

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

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public Object getPayNo() {
        return payNo;
    }

    public void setPayNo(Object payNo) {
        this.payNo = payNo;
    }

    public Object getPayType() {
        return payType;
    }

    public void setPayType(Object payType) {
        this.payType = payType;
    }

    public Object getRechargeNo() {
        return rechargeNo;
    }

    public void setRechargeNo(Object rechargeNo) {
        this.rechargeNo = rechargeNo;
    }

    public int getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(int totalPrice) {
        this.totalPrice = totalPrice;
    }

    public double getInPrice() {
        return inPrice;
    }

    public void setInPrice(double inPrice) {
        this.inPrice = inPrice;
    }

    public double getFinalPrice() {
        return finalPrice;
    }

    public void setFinalPrice(double finalPrice) {
        this.finalPrice = finalPrice;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
