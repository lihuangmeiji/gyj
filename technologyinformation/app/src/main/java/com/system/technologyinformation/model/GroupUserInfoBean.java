package com.system.technologyinformation.model;

public class GroupUserInfoBean {

    /**
     * me : true
     * nickName : 测试3
     * icon : http://api8081.ximuok.com/resource/e776d1d3b2864e578c4bc2a9455db3fe.jpg
     * imuserName : gyj15835568257
     */

    private boolean me;
    private String nickName;
    private String icon;
    private String imuserName;

    public boolean isMe() {
        return me;
    }

    public void setMe(boolean me) {
        this.me = me;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getImuserName() {
        return imuserName;
    }

    public void setImuserName(String imuserName) {
        this.imuserName = imuserName;
    }
}
