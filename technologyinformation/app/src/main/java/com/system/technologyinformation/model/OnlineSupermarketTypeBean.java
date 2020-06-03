package com.system.technologyinformation.model;

import java.util.List;

public class OnlineSupermarketTypeBean {

    /**
     * id : 12
     * archive : 0
     * createAt : 2019-05-14 14:41:49
     * updateAt : 2019-05-14 14:41:49
     * name : test
     * parentId : 0
     * level : 0
     * sort : 0
     * icon : http://api8081.ximuok.com/resource/category/8.png
     * fake : 0
     * checked : false
     * categoryVos : null
     * products : []
     */

    private int id;
    private int archive;
    private String createAt;
    private String updateAt;
    private String name;
    private int parentId;
    private int level;
    private int sort;
    private String icon;
    private int fake;
    private boolean checked;
    private Object categoryVos;
    private List<OnlineSupermarketBean> products;

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

    public int getParentId() {
        return parentId;
    }

    public void setParentId(int parentId) {
        this.parentId = parentId;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getSort() {
        return sort;
    }

    public void setSort(int sort) {
        this.sort = sort;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public int getFake() {
        return fake;
    }

    public void setFake(int fake) {
        this.fake = fake;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    public Object getCategoryVos() {
        return categoryVos;
    }

    public void setCategoryVos(Object categoryVos) {
        this.categoryVos = categoryVos;
    }

    public List<OnlineSupermarketBean> getProducts() {
        return products;
    }

    public void setProducts(List<OnlineSupermarketBean> products) {
        this.products = products;
    }
}
