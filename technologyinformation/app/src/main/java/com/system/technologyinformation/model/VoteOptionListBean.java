package com.system.technologyinformation.model;

import com.google.gson.annotations.SerializedName;

public class VoteOptionListBean {
    /**
     * id : 1
     * archive : 0
     * createAt : 2019-01-28 15:31:02
     * updateAt : 2019-01-28 15:31:02
     * viId : 1
     * name : Sleeping
     * imgs : sleeping.img
     * num : 2
     */

    @SerializedName("id")
    private int id;
    @SerializedName("archive")
    private int archive;
    @SerializedName("createAt")
    private String createAt;
    @SerializedName("updateAt")
    private String updateAt;
    @SerializedName("viId")
    private int viId;
    @SerializedName("name")
    private String name;
    @SerializedName("imgs")
    private String imgs;
    @SerializedName("num")
    private int num;

    private boolean isChoosed;

    public boolean isChoosed() {
        return isChoosed;
    }

    public void setChoosed(boolean choosed) {
        isChoosed = choosed;
    }

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

    public int getViId() {
        return viId;
    }

    public void setViId(int viId) {
        this.viId = viId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImgs() {
        return imgs;
    }

    public void setImgs(String imgs) {
        this.imgs = imgs;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }
}
