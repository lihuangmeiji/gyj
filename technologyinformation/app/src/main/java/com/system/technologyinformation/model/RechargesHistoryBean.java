package com.system.technologyinformation.model;

public class RechargesHistoryBean {


    /**
     * id : 142
     * archive : 0
     * createAt : 2019-11-01 16:16:19
     * updateAt : 2019-11-01 16:16:28
     * orderNo : 15110344121201911011616192569768
     * userId : 864
     * payNo : 4200000410201911014996909273
     * payType : 1
     * rechargeNo : null
     * totalPrice : 10
     * inPrice : 10.08
     * finalPrice : 9.5
     * phone : 151****4121
     * status : 2
     * userPhone : null
     * errorMsg : null
     */

    private int id;
    private int archive;
    private String createAt;
    private String updateAt;
    private String orderNo;
    private int userId;
    private String payNo;
    private int payType;
    private Object rechargeNo;
    private int totalPrice;
    private double inPrice;
    private double finalPrice;
    private String phone;
    private int status;
    private Object userPhone;
    private Object errorMsg;

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

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getPayNo() {
        return payNo;
    }

    public void setPayNo(String payNo) {
        this.payNo = payNo;
    }

    public int getPayType() {
        return payType;
    }

    public void setPayType(int payType) {
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

    public Object getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(Object userPhone) {
        this.userPhone = userPhone;
    }

    public Object getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(Object errorMsg) {
        this.errorMsg = errorMsg;
    }
}