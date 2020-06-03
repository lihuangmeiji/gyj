package com.system.technologyinformation.model;

public class FlickerScreenBean {

    /**
     * id : 1
     * archive : 0
     * createAt : 2019-09-27 17:56:12
     * updateAt : 2019-09-27 17:56:16
     * name : 70周年
     * desc : 建国70周年
     * target : gyj://main/home/food
     * img : http://gongyoujia-image.oss-cn-hangzhou.aliyuncs.com/image//splash0591bf047ec74e3e8e64a349c248a41b.png
     * dispatchBegin : 2019-09-26
     * dispatchEnd : 2019-10-07
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
    private String target;
    private String img;
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

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
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
