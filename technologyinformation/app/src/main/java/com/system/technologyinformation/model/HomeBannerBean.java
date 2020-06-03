package com.system.technologyinformation.model;

public class HomeBannerBean {


    /**
     * id : 9
     * archive : 0
     * createAt : 2019-06-19 17:45:00
     * updateAt : 2019-06-19 17:45:26
     * sort : 3
     * img : http://gongyoujia-image.oss-cn-hangzhou.aliyuncs.com/c1f5a9b3-882b-46da-89ac-4817a5dda0fb.jpg
     * title : null
     * type : 3
     * target : gyj://home/exchange?id=63
     * private boolean needLogin;
     */

    private int id;
    private int archive;
    private String createAt;
    private String updateAt;
    private int sort;
    private String img;
    private String title;
    private int type;
    private String target;
    private boolean needLogin;
    private String name;

    public boolean isNeedLogin() {
        return needLogin;
    }

    public void setNeedLogin(boolean needLogin) {
        this.needLogin = needLogin;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public int getSort() {
        return sort;
    }

    public void setSort(int sort) {
        this.sort = sort;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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
}
