package com.system.technologyinformation.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class VoteBean {

    /**
     * id : 1
     * archive : 0
     * createAt : 2019-01-28 14:48:55
     * updateAt : 2019-01-28 14:48:55
     * num : 3
     * title : What was that you spend entire life looking for?
     * content : Confusing , cannot see anything in darkness? what`re you gonna do?
     * totalUser : 0
     * voteOptionList : [{"id":1,"archive":0,"createAt":"2019-01-28 15:31:02","updateAt":"2019-01-28 15:31:02","viId":1,"name":"Sleeping","imgs":"sleeping.img","num":2},{"id":2,"archive":0,"createAt":"2019-01-28 15:31:25","updateAt":"2019-01-28 15:31:25","viId":1,"name":"Thinking","imgs":"thinking.img","num":1},{"id":3,"archive":0,"createAt":"2019-01-28 15:31:43","updateAt":"2019-01-28 15:31:43","viId":1,"name":"Running","imgs":"running.img","num":1},{"id":4,"archive":0,"createAt":"2019-01-28 15:32:03","updateAt":"2019-01-28 15:32:03","viId":1,"name":"Do nothing","imgs":"nothing.img","num":1}]
     */

    @SerializedName("id")
    private int id;
    @SerializedName("archive")
    private int archive;
    @SerializedName("createAt")
    private String createAt;
    @SerializedName("updateAt")
    private String updateAt;
    @SerializedName("num")
    private int num;
    @SerializedName("title")
    private String title;
    @SerializedName("content")
    private String content;
    @SerializedName("totalUser")
    private int totalUser;
    @SerializedName("voteOptionList")
    private List<VoteOptionListBean> voteOptionList;

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

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
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

    public int getTotalUser() {
        return totalUser;
    }

    public void setTotalUser(int totalUser) {
        this.totalUser = totalUser;
    }

    public List<VoteOptionListBean> getVoteOptionList() {
        return voteOptionList;
    }

    public void setVoteOptionList(List<VoteOptionListBean> voteOptionList) {
        this.voteOptionList = voteOptionList;
    }
}
