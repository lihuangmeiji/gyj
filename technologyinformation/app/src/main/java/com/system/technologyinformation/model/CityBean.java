package com.system.technologyinformation.model;

import com.google.gson.annotations.SerializedName;

public class CityBean {

    /**
     * id : 46
     * archive : 0
     * createAt : 2018-12-27 16:46:15
     * updateAt : null
     * areaCode : 131000
     * netName : 廊坊市
     * provinceAreas : null
     */

    @SerializedName("id")
    private int id;
    @SerializedName("archive")
    private int archive;
    @SerializedName("createAt")
    private String createAt;
    @SerializedName("updateAt")
    private Object updateAt;
    @SerializedName("areaCode")
    private int areaCode;
    @SerializedName("netName")
    private String netName;
    @SerializedName("provinceAreas")
    private Object provinceAreas;

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

    public int getAreaCode() {
        return areaCode;
    }

    public void setAreaCode(int areaCode) {
        this.areaCode = areaCode;
    }

    public String getNetName() {
        return netName;
    }

    public void setNetName(String netName) {
        this.netName = netName;
    }

    public Object getProvinceAreas() {
        return provinceAreas;
    }

    public void setProvinceAreas(Object provinceAreas) {
        this.provinceAreas = provinceAreas;
    }
}
