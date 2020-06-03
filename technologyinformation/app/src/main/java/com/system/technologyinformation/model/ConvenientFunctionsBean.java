package com.system.technologyinformation.model;

public class ConvenientFunctionsBean {


    /**
     * name : 抢购
     * icon : http://gongyoujia-image.oss-cn-hangzhou.aliyuncs.com/image/serviceIcon/fe30cbc6312e45cd9c2d9a33f018e82e.png
     * hot : null
     * status : false
     * type : 2
     * target : gyj://home/mall
     * needLogin : null
     */

    private String name;
    private String icon;
    private String hot;
    private boolean status;
    private int type;
    private String target;
    private boolean needLogin;

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

    public String getHot() {
        return hot;
    }

    public void setHot(String hot) {
        this.hot = hot;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public boolean getNeedLogin() {
        return needLogin;
    }

    public void setNeedLogin(boolean needLogin) {
        this.needLogin = needLogin;
    }
}
