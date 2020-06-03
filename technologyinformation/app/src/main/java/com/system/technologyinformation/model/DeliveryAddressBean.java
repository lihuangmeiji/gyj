package com.system.technologyinformation.model;

public class DeliveryAddressBean {

    /**
     * id : 3
     * archive : 0
     * createAt : 2019-09-10 14:47:45
     * updateAt : null
     * userId : 6
     * name : 阿斯蒂芬
     * phone : 15835568257
     * areaCode : 500000
     * address : 阿斯蒂芬
     * provinceCode : 500000
     * cityInfo : 重庆市
     * detailsAddress : 重庆市 阿斯蒂芬
     */

    private int id;
    private int archive;
    private String createAt;
    private Object updateAt;
    private int userId;
    private String name;
    private String phone;
    private int areaCode;
    private String address;
    private int provinceCode;
    private String cityInfo;
    private String detailsAddress;

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public int getAreaCode() {
        return areaCode;
    }

    public void setAreaCode(int areaCode) {
        this.areaCode = areaCode;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getProvinceCode() {
        return provinceCode;
    }

    public void setProvinceCode(int provinceCode) {
        this.provinceCode = provinceCode;
    }

    public String getCityInfo() {
        return cityInfo;
    }

    public void setCityInfo(String cityInfo) {
        this.cityInfo = cityInfo;
    }

    public String getDetailsAddress() {
        return detailsAddress;
    }

    public void setDetailsAddress(String detailsAddress) {
        this.detailsAddress = detailsAddress;
    }
}
