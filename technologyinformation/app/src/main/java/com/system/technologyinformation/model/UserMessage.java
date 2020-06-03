package com.system.technologyinformation.model;

import com.google.gson.annotations.SerializedName;

public class UserMessage {

    /**
     * id : 20
     * archive : 0
     * createAt : 2019-03-17 16:53:38
     * updateAt : 2019-03-17 16:53:38
     * title : 您提交的认证信息状态有更新!
     * content : 您提交的资质认证已经审核成功!
     * userId : 6
     * status : 0
     * iId : 21
     */

    @SerializedName("id")
    private int id;
    @SerializedName("archive")
    private int archive;
    @SerializedName("createAt")
    private String createAt;
    @SerializedName("updateAt")
    private String updateAt;
    @SerializedName("title")
    private String title;
    @SerializedName("content")
    private String content;
    @SerializedName("userId")
    private int userId;
    @SerializedName("status")
    private int status;
    @SerializedName("iId")
    private int iId;

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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getIId() {
        return iId;
    }

    public void setIId(int iId) {
        this.iId = iId;
    }
}
