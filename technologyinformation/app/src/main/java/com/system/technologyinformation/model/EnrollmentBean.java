package com.system.technologyinformation.model;

public class EnrollmentBean {


    /**
     * id : 1
     * archive : 0
     * createAt : 2019-06-06 11:25:16
     * updateAt : 2019-06-26 14:55:12
     * name : 工友家才艺大赛
     * address : 工友家驿站
     * content : 歌曲 舞蹈 小品 相声 诗歌朗诵等
     * process : 1.报名方式：在本页面填写报名人信息以及报名节目内容\n2.报名结束后在工友家驿站开始节目初选活动，初选过程在工友家APP全程直播\n3.初选过程同步开启投票环节，全体工友均可为你喜欢的选手、节目加油“打call”，助力节目获选\n4.晚会节目优先从人气节目中选取\n5.同时，工友家驿站象棋、台球开启“棋王”、“球王”争霸赛，在驿站得到胜出的选手可为喜爱的选手、节目增加高额人气值。在晚会活动结束后，将开启全区“棋王”、“球王”争霸赛，赢取高额奖金和“棋王”、“球王”特殊纪念奖品\n
     * rule : 1.海选全程APP直播，根据大家投票评选最具人气的节目参加晚会节目\n2.晚会当日全程APP直播，节目开启时由观众投票，根据人气评选获奖名单\n
     * endTime : 2019-06-08 12:30:00
     * startTime : null
     */

    private int id;
    private int archive;
    private String createAt;
    private String updateAt;
    private String name;
    private String address;
    private String content;
    private String process;
    private String rule;
    private String endTime;
    private Object startTime;

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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getProcess() {
        return process;
    }

    public void setProcess(String process) {
        this.process = process;
    }

    public String getRule() {
        return rule;
    }

    public void setRule(String rule) {
        this.rule = rule;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public Object getStartTime() {
        return startTime;
    }

    public void setStartTime(Object startTime) {
        this.startTime = startTime;
    }
}
