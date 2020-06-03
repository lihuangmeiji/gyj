package com.system.technologyinformation.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class CareerInfoBean implements Serializable {

    /**
     * id : 4
     * archive : 0
     * createAt : null
     * updateAt : null
     * userId : 6
     * addressProvince : 山西省
     * addressCity : 太原市
     * addressArea : 小店区
     * addressDetail : 南中环
     * longitude : 120.89
     * latitude : 37.68
     * salary : null
     */

    @SerializedName("id")
    private int id;
    @SerializedName("archive")
    private int archive;
    @SerializedName("createAt")
    private Object createAt;
    @SerializedName("updateAt")
    private Object updateAt;
    @SerializedName("userId")
    private int userId;
    @SerializedName("addressProvince")
    private String addressProvince;
    @SerializedName("addressCity")
    private String addressCity;
    @SerializedName("addressArea")
    private String addressArea;
    @SerializedName("addressDetail")
    private String addressDetail;
    @SerializedName("longitude")
    private String longitude;
    @SerializedName("latitude")
    private String latitude;
    @SerializedName("salary")
    private String salary;

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

    public Object getCreateAt() {
        return createAt;
    }

    public void setCreateAt(Object createAt) {
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

    public String getAddressProvince() {
        return addressProvince;
    }

    public void setAddressProvince(String addressProvince) {
        this.addressProvince = addressProvince;
    }

    public String getAddressCity() {
        return addressCity;
    }

    public void setAddressCity(String addressCity) {
        this.addressCity = addressCity;
    }

    public String getAddressArea() {
        return addressArea;
    }

    public void setAddressArea(String addressArea) {
        this.addressArea = addressArea;
    }

    public String getAddressDetail() {
        return addressDetail;
    }

    public void setAddressDetail(String addressDetail) {
        this.addressDetail = addressDetail;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getSalary() {
        return salary;
    }

    public void setSalary(String salary) {
        this.salary = salary;
    }
}
