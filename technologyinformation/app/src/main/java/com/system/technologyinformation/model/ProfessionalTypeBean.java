package com.system.technologyinformation.model;

import com.google.gson.annotations.SerializedName;

public class ProfessionalTypeBean {

    /**
     * id : 1
     * archive : 0
     * createAt : 2019-01-26 15:14:51
     * updateAt : 2019-01-26 15:14:51
     * name : 水电工
     * sort : 1
     */

    @SerializedName("id")
    private int id;
    @SerializedName("archive")
    private int archive;
    @SerializedName("createAt")
    private String createAt;
    @SerializedName("updateAt")
    private String updateAt;
    @SerializedName("name")
    private String name;
    @SerializedName("sort")
    private int sort;

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

    public int getSort() {
        return sort;
    }

    public void setSort(int sort) {
        this.sort = sort;
    }
}
