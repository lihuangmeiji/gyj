package com.system.technologyinformation.model;

import java.util.List;

public class HomeShoppingSpreeBean {

    /**
     * id : 37
     * archive : 0
     * createAt : 2019-05-14 11:40:26
     * updateAt : 2019-05-14 11:40:26
     * name : 测试商品1
     * images : https://www.baidu.com/img/bd_logo1.png,https://www.baidu.com/img/bd_logo1.png,https://www.baidu.com/img/bd_logo1.png
     * desc : 商品描述
     * categoryId : 9
     * status : 0
     * dividerPercent : 0
     * storeId : 0
     * imageList : ["https://www.baidu.com/img/bd_logo1.png","https://www.baidu.com/img/bd_logo1.png","https://www.baidu.com/img/bd_logo1.png"]
     * image : https://www.baidu.com/img/bd_logo1.png
     * sort : 0
     * model : 1
     * type : null
     * point : 0
     * soldTotal : 0
     * limit : null
     * details : <h2><img src="http://baiye-image.oss-cn-shanghai.aliyuncs.com/d10101a7-89e2-410e-9b8d-b6bc0b15a1ad.jpg"></h2><p>沙赞</p>
     * sourcePrice : 35.14
     * currentPrice : 0.01
     * skuList : null
     * storeIds : null
     */

    private int id;
    private int archive;
    private String createAt;
    private String updateAt;
    private String name;
    private String images;
    private String desc;
    private String categoryId;
    private int status;
    private int dividerPercent;
    private int storeId;
    private String image;
    private int sort;
    private int model;
    private int type;
    private int point;
    private int soldTotal;
    private int limit;
    private String details;
    private double sourcePrice;
    private double currentPrice;
    private Object skuList;
    private Object storeIds;
    private List<String> imageList;

    private int inventory;

    public int getInventory() {
        return inventory;
    }

    public void setInventory(int inventory) {
        this.inventory = inventory;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImages() {
        return images;
    }

    public void setImages(String images) {
        this.images = images;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getDividerPercent() {
        return dividerPercent;
    }

    public void setDividerPercent(int dividerPercent) {
        this.dividerPercent = dividerPercent;
    }

    public int getStoreId() {
        return storeId;
    }

    public void setStoreId(int storeId) {
        this.storeId = storeId;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getSort() {
        return sort;
    }

    public void setSort(int sort) {
        this.sort = sort;
    }

    public int getModel() {
        return model;
    }

    public void setModel(int model) {
        this.model = model;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getPoint() {
        return point;
    }

    public void setPoint(int point) {
        this.point = point;
    }

    public int getSoldTotal() {
        return soldTotal;
    }

    public void setSoldTotal(int soldTotal) {
        this.soldTotal = soldTotal;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public double getSourcePrice() {
        return sourcePrice;
    }

    public void setSourcePrice(double sourcePrice) {
        this.sourcePrice = sourcePrice;
    }

    public double getCurrentPrice() {
        return currentPrice;
    }

    public void setCurrentPrice(double currentPrice) {
        this.currentPrice = currentPrice;
    }

    public Object getSkuList() {
        return skuList;
    }

    public void setSkuList(Object skuList) {
        this.skuList = skuList;
    }

    public Object getStoreIds() {
        return storeIds;
    }

    public void setStoreIds(Object storeIds) {
        this.storeIds = storeIds;
    }

    public List<String> getImageList() {
        return imageList;
    }

    public void setImageList(List<String> imageList) {
        this.imageList = imageList;
    }
}
