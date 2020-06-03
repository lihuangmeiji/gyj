package com.system.technologyinformation.model;

import com.google.gson.annotations.SerializedName;

public class FeedbacksBean {
    /**
     * id : 1
     * archive : null
     * createAt : null
     * updateAt : null
     * cId : null
     * cftId : null
     * userId : null
     * num : null
     * backName : 喜欢
     */

    @SerializedName("id")
    private int id;
    @SerializedName("archive")
    private Object archive;
    @SerializedName("createAt")
    private Object createAt;
    @SerializedName("updateAt")
    private Object updateAt;
    @SerializedName("cId")
    private Object cId;
    @SerializedName("cftId")
    private Object cftId;
    @SerializedName("userId")
    private Object userId;
    @SerializedName("num")
    private int num;
    @SerializedName("backName")
    private String backName;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Object getArchive() {
        return archive;
    }

    public void setArchive(Object archive) {
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

    public Object getCId() {
        return cId;
    }

    public void setCId(Object cId) {
        this.cId = cId;
    }

    public Object getCftId() {
        return cftId;
    }

    public void setCftId(Object cftId) {
        this.cftId = cftId;
    }

    public Object getUserId() {
        return userId;
    }

    public void setUserId(Object userId) {
        this.userId = userId;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public String getBackName() {
        return backName;
    }

    public void setBackName(String backName) {
        this.backName = backName;
    }
}
