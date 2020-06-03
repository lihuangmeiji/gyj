package com.system.technologyinformation.model;

import java.io.Serializable;

public class IdentificationInfosBean implements Serializable{


    /**
     * id : 1
     * archive : 0
     * createAt : 2019-01-26 15:14:51
     * updateAt : 2019-01-26 15:14:51
     * name : 木工
     * sort : 1
     * type : 0
     * use : false
     */

    private int id;
    private int archive;
    private String createAt;
    private String updateAt;
    private String name;
    private int sort;
    private int type;
    private boolean use;

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

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public boolean isUse() {
        return use;
    }

    public void setUse(boolean use) {
        this.use = use;
    }
}
