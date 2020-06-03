package com.system.technologyinformation.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ProvinceAreasBean {
    /**
     * id : 37
     * archive : 0
     * createAt : 2018-12-27 16:46:14
     * updateAt : null
     * areaCode : 130100
     * netName : 石家庄市
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
    private List<CityBean> provinceAreas;

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

    public List<CityBean> getProvinceAreas() {
        return provinceAreas;
    }

    public void setProvinceAreas(List<CityBean> provinceAreas) {
        this.provinceAreas = provinceAreas;
    }
}
