package com.system.technologyinformation.model;

import com.google.gson.annotations.SerializedName;

public class VersionShowBean {


    /**
     * updateAt : 2019-01-17 15:52:26
     * timeMillis : 1547715480350
     * pageSize : 20
     * osType : android
     * versionName : 1.0.01
     * msg : 天天影院，强势更新
     * url : http://app.ge75g.cn/app/app-release.apk
     * version : 1001
     * pageNum : 1
     * id : 3
     * createAt : 2018-12-14 13:23:50
     * osMinVersion : 15
     * forceUpdate : 1
     * archive : 0
     */

    @SerializedName("updateAt")
    private String updateAt;
    @SerializedName("timeMillis")
    private long timeMillis;
    @SerializedName("pageSize")
    private int pageSize;
    @SerializedName("osType")
    private String osType;
    @SerializedName("versionName")
    private String versionName;
    @SerializedName("msg")
    private String msg;
    @SerializedName("url")
    private String url;
    @SerializedName("version")
    private int version;
    @SerializedName("pageNum")
    private int pageNum;
    @SerializedName("id")
    private int id;
    @SerializedName("createAt")
    private String createAt;
    @SerializedName("osMinVersion")
    private int osMinVersion;
    @SerializedName("forceUpdate")
    private int forceUpdate;
    @SerializedName("archive")
    private int archive;

    public String getUpdateAt() {
        return updateAt;
    }

    public void setUpdateAt(String updateAt) {
        this.updateAt = updateAt;
    }

    public long getTimeMillis() {
        return timeMillis;
    }

    public void setTimeMillis(long timeMillis) {
        this.timeMillis = timeMillis;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public String getOsType() {
        return osType;
    }

    public void setOsType(String osType) {
        this.osType = osType;
    }

    public String getVersionName() {
        return versionName;
    }

    public void setVersionName(String versionName) {
        this.versionName = versionName;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public int getPageNum() {
        return pageNum;
    }

    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCreateAt() {
        return createAt;
    }

    public void setCreateAt(String createAt) {
        this.createAt = createAt;
    }

    public int getOsMinVersion() {
        return osMinVersion;
    }

    public void setOsMinVersion(int osMinVersion) {
        this.osMinVersion = osMinVersion;
    }

    public int getForceUpdate() {
        return forceUpdate;
    }

    public void setForceUpdate(int forceUpdate) {
        this.forceUpdate = forceUpdate;
    }

    public int getArchive() {
        return archive;
    }

    public void setArchive(int archive) {
        this.archive = archive;
    }
}
