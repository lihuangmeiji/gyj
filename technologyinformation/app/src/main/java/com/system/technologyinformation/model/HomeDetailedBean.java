package com.system.technologyinformation.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class HomeDetailedBean {

    /**
     * id : 2
     * archive : 0
     * createAt : 2019-01-28 14:29:18
     * updateAt : 2019-01-28 14:29:18
     * ctId : 2
     * title : 谷歌AI玩《星际争霸2》 打败人类顶尖电游玩家
     * imgs : [http://himg2.huanqiu.com/attachment2010/2019/0128/10/33/20190128103313429.jpg, http://himg2.huanqiu.com/attachment2010/2019/0128/10/33/20190128103324176.jpg, http://himg2.huanqiu.com/attachment2010/2019/0128/10/33/20190128103336802.jpg]
     * originName : 環球網
     * originIcon : http://himg2.huanqiu.com/statics/hq2017/article/images/logo/logo_channel_45.png
     * releaseTime : 1548656958000
     * content : 谷歌的人工智能软件AlphaZero在《星际争霸2》中击败了两名世界顶级玩家。    　　实时战略电子游戏《星际争霸2》涉及到基于玩家以往游戏中的动作做出复杂的决策，游戏中玩家与敌对势力在一场战斗中互相竞争。地图的大部分仍然模糊不清，所以战斗人员不得不依靠直觉来击败敌人。这使得编写该计算机程序比编写针对其他使用固定变量的策略游戏更加困难。    　　谷歌DeepMind部门训练了5个版本的人工智能，叫做AlphaStar，对人类选手比赛的画面进行学习。然后，AlphaStar在一个联盟内竞争。获胜的人工智能积累了相当于200年的游戏经验。    　　自从1997年IBM的国际象棋程序“深蓝”打败冠军卡斯帕罗夫以来，人工智能的游戏水平越来越高。AlphaZero的最新版本能够通过阅读规则手册来掌握国际象棋和幕府将军等游戏。该系统的早期版本，被称为AlphaGo，能够击败世界顶级围棋棋手。2015年开发的AlphaGo AI的一个版本证明，它在20世纪80年代的数十款雅达利电子游戏中的表现优于人类，包括视频弹球、拳击。(实习编译：林雨晨 审稿：李宗泽)
     * viId : null
     * type : 1
     * readNum : 34
     * forwardNum : 0
     * status : 0
     * typeName : 图文
     * likeNum : null
     * imgList : ["http://himg2.huanqiu.com/attachment2010/2019/0128/10/33/20190128103313429.jpg","http://himg2.huanqiu.com/attachment2010/2019/0128/10/33/20190128103324176.jpg","http://himg2.huanqiu.com/attachment2010/2019/0128/10/33/20190128103336802.jpg"]
     * feedbacks : [{"id":1,"archive":null,"createAt":null,"updateAt":null,"cId":null,"cftId":null,"userId":null,"num":null,"backName":"喜欢"},{"id":2,"archive":null,"createAt":null,"updateAt":null,"cId":2,"cftId":2,"userId":4,"num":1,"backName":"一般"},{"id":3,"archive":null,"createAt":null,"updateAt":null,"cId":null,"cftId":null,"userId":null,"num":null,"backName":"吐槽"},{"id":4,"archive":null,"createAt":null,"updateAt":null,"cId":null,"cftId":null,"userId":null,"num":null,"backName":"踩"}]
     * selfFeedBackId : null
     * consultationComments : null
     * vote : null
     * selfVote : null
     */

    @SerializedName("id")
    private int id;
    @SerializedName("archive")
    private int archive;
    @SerializedName("createAt")
    private String createAt;
    @SerializedName("updateAt")
    private String updateAt;
    @SerializedName("ctId")
    private int ctId;
    @SerializedName("title")
    private String title;
    @SerializedName("imgs")
    private String imgs;
    @SerializedName("originName")
    private String originName;
    @SerializedName("originIcon")
    private String originIcon;
    @SerializedName("releaseTime")
    private long releaseTime;
    @SerializedName("content")
    private String content;
    @SerializedName("viId")
    private Object viId;
    @SerializedName("type")
    private int type;
    @SerializedName("readNum")
    private int readNum;
    @SerializedName("forwardNum")
    private int forwardNum;
    @SerializedName("status")
    private int status;
    @SerializedName("typeName")
    private String typeName;
    @SerializedName("likeNum")
    private Object likeNum;
    @SerializedName("selfFeedBackId")
    private int selfFeedBackId;
    @SerializedName("consultationComments")
    private Object consultationComments;
    @SerializedName("vote")
    private VoteBean vote;
    @SerializedName("selfVote")
    private List<Integer> selfVote;
    @SerializedName("imgList")
    private List<String> imgList;
    @SerializedName("feedbacks")
    private List<FeedbacksBean> feedbacks;
    @SerializedName("contextType")
    private int contextType;

    public String getVideoImg() {
        return videoImg;
    }

    public void setVideoImg(String videoImg) {
        this.videoImg = videoImg;
    }

    private String videoImg;

    public String getShareLink() {
        return shareLink;
    }

    public void setShareLink(String shareLink) {
        this.shareLink = shareLink;
    }

    private String shareLink;

    public String getVideoUrl() {
        return videoUrl;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }

    private String videoUrl;

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

    public int getCtId() {
        return ctId;
    }

    public void setCtId(int ctId) {
        this.ctId = ctId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImgs() {
        return imgs;
    }

    public void setImgs(String imgs) {
        this.imgs = imgs;
    }

    public String getOriginName() {
        return originName;
    }

    public void setOriginName(String originName) {
        this.originName = originName;
    }

    public String getOriginIcon() {
        return originIcon;
    }

    public void setOriginIcon(String originIcon) {
        this.originIcon = originIcon;
    }

    public long getReleaseTime() {
        return releaseTime;
    }

    public void setReleaseTime(long releaseTime) {
        this.releaseTime = releaseTime;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Object getViId() {
        return viId;
    }

    public void setViId(Object viId) {
        this.viId = viId;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getReadNum() {
        return readNum;
    }

    public void setReadNum(int readNum) {
        this.readNum = readNum;
    }

    public int getForwardNum() {
        return forwardNum;
    }

    public void setForwardNum(int forwardNum) {
        this.forwardNum = forwardNum;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public Object getLikeNum() {
        return likeNum;
    }

    public void setLikeNum(Object likeNum) {
        this.likeNum = likeNum;
    }

    public int getSelfFeedBackId() {
        return selfFeedBackId;
    }

    public void setSelfFeedBackId(int selfFeedBackId) {
        this.selfFeedBackId = selfFeedBackId;
    }

    public Object getConsultationComments() {
        return consultationComments;
    }

    public void setConsultationComments(Object consultationComments) {
        this.consultationComments = consultationComments;
    }

    public VoteBean getVote() {
        return vote;
    }

    public void setVote(VoteBean vote) {
        this.vote = vote;
    }

    public List<Integer> getSelfVote() {
        return selfVote;
    }

    public void setSelfVote(List<Integer> selfVote) {
        this.selfVote = selfVote;
    }

    public List<String> getImgList() {
        return imgList;
    }

    public void setImgList(List<String> imgList) {
        this.imgList = imgList;
    }

    public List<FeedbacksBean> getFeedbacks() {
        return feedbacks;
    }

    public void setFeedbacks(List<FeedbacksBean> feedbacks) {
        this.feedbacks = feedbacks;
    }

    public int getContextType() {
        return contextType;
    }

    public void setContextType(int contextType) {
        this.contextType = contextType;
    }
}
