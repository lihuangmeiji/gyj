package com.system.technologyinformation.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class HomeDataBean {


    /**
     * id : 1
     * archive : 0
     * createAt : 2019-01-28 11:35:03
     * updateAt : 2019-03-05 09:56:38
     * ctId : 2
     * title : 字节跳动副总裁斥微信垄断:因竞争做封杀 情理之中
     * imgs : null
     * originName : 網易163
     * originIcon : 163.img
     * releaseTime : null
     * content : null
     * viId : 1
     * type : 0
     * readNum : 482
     * forwardNum : 6
     * status : 0
     * videoUrl : null
     * points : 0
     * typeName : 文字
     * likeNum : 3
     * imgList : null
     * contextType : 1
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
    private Object imgs;
    @SerializedName("originName")
    private String originName;
    @SerializedName("originIcon")
    private String originIcon;
    @SerializedName("releaseTime")
    private Object releaseTime;
    @SerializedName("content")
    private Object content;
    @SerializedName("viId")
    private int viId;
    @SerializedName("type")
    private int type;
    @SerializedName("readNum")
    private int readNum;
    @SerializedName("forwardNum")
    private int forwardNum;
    @SerializedName("status")
    private int status;
    @SerializedName("videoUrl")
    private String videoUrl;
    @SerializedName("points")
    private int points;
    @SerializedName("typeName")
    private String typeName;
    @SerializedName("likeNum")
    private int likeNum;
    @SerializedName("imgList")
    private List<String> imgList;
    @SerializedName("contextType")
    private int contextType;

    private String videoImg;

    public String getVideoImg() {
        return videoImg;
    }

    public void setVideoImg(String videoImg) {
        this.videoImg = videoImg;
    }

    private String ctName;

    private String rgb;

    public String getRgb() {
        return rgb;
    }

    public void setRgb(String rgb) {
        this.rgb = rgb;
    }

    public String getCtName() {
        return ctName;
    }

    public void setCtName(String ctName) {
        this.ctName = ctName;
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

    public Object getImgs() {
        return imgs;
    }

    public void setImgs(Object imgs) {
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

    public Object getReleaseTime() {
        return releaseTime;
    }

    public void setReleaseTime(Object releaseTime) {
        this.releaseTime = releaseTime;
    }

    public Object getContent() {
        return content;
    }

    public void setContent(Object content) {
        this.content = content;
    }

    public int getViId() {
        return viId;
    }

    public void setViId(int viId) {
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

    public String getVideoUrl() {
        return videoUrl;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public int getLikeNum() {
        return likeNum;
    }

    public void setLikeNum(int likeNum) {
        this.likeNum = likeNum;
    }

    public List<String> getImgList() {
        return imgList;
    }

    public void setImgList(List<String> imgList) {
        this.imgList = imgList;
    }

    public int getContextType() {
        return contextType;
    }

    public void setContextType(int contextType) {
        this.contextType = contextType;
    }
}
