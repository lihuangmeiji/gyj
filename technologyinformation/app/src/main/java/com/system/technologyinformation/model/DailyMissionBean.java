package com.system.technologyinformation.model;

public class DailyMissionBean {


    /**
     * id : 1
     * name : 完善资料
     * icon : http://gongyoujia-image.oss-cn-hangzhou.aliyuncs.com/image/icon/cdc0bcfc81254077a266de8b47224831.png
     * point : 100
     * type : 1
     * code : user.info
     * target : gyj://task/userinfo
     * accomplish : true
     * shareTotal : null
     * maxShare;1
     */

    private int id;
    private String name;
    private String icon;
    private int point;
    private int type;
    private String code;
    private String target;
    private boolean accomplish;
    private int shareTotal;

    private int maxShare;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public int getPoint() {
        return point;
    }

    public void setPoint(int point) {
        this.point = point;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public boolean isAccomplish() {
        return accomplish;
    }

    public void setAccomplish(boolean accomplish) {
        this.accomplish = accomplish;
    }

    public int getShareTotal() {
        return shareTotal;
    }

    public void setShareTotal(int shareTotal) {
        this.shareTotal = shareTotal;
    }

    public int getMaxShare() {
        return maxShare;
    }

    public void setMaxShare(int maxShare) {
        this.maxShare = maxShare;
    }
}
