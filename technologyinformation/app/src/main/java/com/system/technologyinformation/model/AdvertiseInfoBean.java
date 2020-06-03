package com.system.technologyinformation.model;

public class AdvertiseInfoBean {


    /**
     * id : 1
     * archive : 0
     * createAt : 2019-09-27 18:37:48
     * updateAt : 2019-09-27 18:37:50
     * name : 福利等你拿
     * desc : 福利等你拿
     * target : http://gyj-dev-api.idougong.com/luckyTurntable/index.html
     * title : null
     * needLogin : true
     * img : http://gongyoujia-image.oss-cn-hangzhou.aliyuncs.com/image//popupShow18bebd59d40d4f50bbe9b5d96c42c2d6.png
     * dispatchBegin : 2019-09-23
     * dispatchEnd : 2019-09-27
     * showBegin : 2019-11-20
     * showEnd : 2019-11-30
     * disable : false
     */

    private int id;
    private int archive;
    private String createAt;
    private String updateAt;
    private String name;
    private String desc;
    private String target;
    private Object title;
    private boolean needLogin;
    private String img;
    private String dispatchBegin;
    private String dispatchEnd;
    private String showBegin;
    private String showEnd;
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

    public Object getTitle() {
        return title;
    }

    public void setTitle(Object title) {
        this.title = title;
    }

    public boolean isNeedLogin() {
        return needLogin;
    }

    public void setNeedLogin(boolean needLogin) {
        this.needLogin = needLogin;
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

    public boolean isDisable() {
        return disable;
    }

    public void setDisable(boolean disable) {
        this.disable = disable;
    }
}

