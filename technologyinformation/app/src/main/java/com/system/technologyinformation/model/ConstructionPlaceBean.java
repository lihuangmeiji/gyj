package com.system.technologyinformation.model;

import java.util.List;

public class ConstructionPlaceBean {

    /**
     * id : 5
     * archive : 0
     * createAt : 2019-05-07 17:01:51
     * updateAt : 2019-05-07 17:01:51
     * name : 2
     * area : 2
     * province : 2
     * city : 2
     * zone : 2
     * address : 2
     * period : [2019,5,24]
     * longitude : 2
     * latitude : 21
     */

    private int id;
    private int archive;
    private String createAt;
    private String updateAt;
    private String name;
    private String area;
    private String province;
    private String city;
    private String zone;
    private String address;
    private String longitude;
    private String latitude;
    private List<Integer> period;

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getZone() {
        return zone;
    }

    public void setZone(String zone) {
        this.zone = zone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
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

    public List<Integer> getPeriod() {
        return period;
    }

    public void setPeriod(List<Integer> period) {
        this.period = period;
    }
}
