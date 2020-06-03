package com.system.technologyinformation.model;

public class HomeConfigurationInformationBean {
    /**
     * id : 1
     * archive : 0
     * createAt : 2019-09-27 17:06:47
     * updateAt : 2019-09-27 17:06:52
     * name : 70周年
     * desc : 建国70周年
     * homeImg : http://gongyoujia-image.oss-cn-hangzhou.aliyuncs.com/image//background60f20305729b419cbfe2d5bc2fca5fd6.png
     * homeIcon : http://gongyoujia-image.oss-cn-hangzhou.aliyuncs.com/image//background95bf5b321a4043b49277b56d43e51504.png
     * scanImg : http://gongyoujia-image.oss-cn-hangzhou.aliyuncs.com/image//background37f6b3c931024ac480eeb11510970250.png
     * checkInImg : http://gongyoujia-image.oss-cn-hangzhou.aliyuncs.com/image//backgroundb00e2c6e12ec41c68edf5d66d6f38659.png
     * taskImg : null
     * textColor : #FCEB99
     * meImg : null
     * dispatchBegin : 2019-09-26
     * dispatchEnd : 2019-09-28
     * showBegin : 2019-09-27
     * showEnd : 2019-10-07
     * priority : 1
     * disable : false
     */
    private int id;
    private int archive;
    private String createAt;
    private String updateAt;
    private String name;
    private String desc;
    private String homeImg;
    private String homeIcon;
    private String scanImg;
    private String checkInImg;
    private Object taskImg;
    private String textColor;
    private Object meImg;
    private String dispatchBegin;
    private String dispatchEnd;
    private String showBegin;
    private String showEnd;
    private int priority;
    private boolean disable;

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

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getHomeImg() {
        return homeImg;
    }

    public void setHomeImg(String homeImg) {
        this.homeImg = homeImg;
    }

    public String getHomeIcon() {
        return homeIcon;
    }

    public void setHomeIcon(String homeIcon) {
        this.homeIcon = homeIcon;
    }

    public String getScanImg() {
        return scanImg;
    }

    public void setScanImg(String scanImg) {
        this.scanImg = scanImg;
    }

    public String getCheckInImg() {
        return checkInImg;
    }

    public void setCheckInImg(String checkInImg) {
        this.checkInImg = checkInImg;
    }

    public Object getTaskImg() {
        return taskImg;
    }

    public void setTaskImg(Object taskImg) {
        this.taskImg = taskImg;
    }

    public String getTextColor() {
        return textColor;
    }

    public void setTextColor(String textColor) {
        this.textColor = textColor;
    }

    public Object getMeImg() {
        return meImg;
    }

    public void setMeImg(Object meImg) {
        this.meImg = meImg;
    }

    public String getDispatchBegin() {
        return dispatchBegin;
    }

    public void setDispatchBegin(String dispatchBegin) {
        this.dispatchBegin = dispatchBegin;
    }

    public String getDispatchEnd() {
        return dispatchEnd;
    }

    public void setDispatchEnd(String dispatchEnd) {
        this.dispatchEnd = dispatchEnd;
    }

    public String getShowBegin() {
        return showBegin;
    }

    public void setShowBegin(String showBegin) {
        this.showBegin = showBegin;
    }

    public String getShowEnd() {
        return showEnd;
    }

    public void setShowEnd(String showEnd) {
        this.showEnd = showEnd;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public boolean isDisable() {
        return disable;
    }

    public void setDisable(boolean disable) {
        this.disable = disable;
    }
}
