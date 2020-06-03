package com.system.technologyinformation.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class OnlineSupermarketBean implements Serializable{

    /**
     * id : 37
     * archive : null
     * createAt : 2019-05-14 11:40:26
     * updateAt : null
     * name : 测试商品
     * images : [https://www.baidu.com/img/bd_logo1.png,https://www.baidu.com/img/bd_logo1.png,https://www.baidu.com/img/bd_logo1.png]
     * desc : null
     * categoryId : 9
     * status : 0
     * dividerPercent : 0
     * storeId : 0
     * imageList : ["[https://www.baidu.com/img/bd_logo1.png","https://www.baidu.com/img/bd_logo1.png","https://www.baidu.com/img/bd_logo1.png]"]
     * image : [https://www.baidu.com/img/bd_logo1.png
     * sort : 0
     * skuList : null
     * amount : 50
     * sales : 0
     * maxPrice : 30
     * minPrice : 30
     * totalPrice : 0
     * categories : null
     * archive : null
     * updateAt : null
     * desc : null
     * food : null
     * sourcePrice : null
     * currentPrice : null
     * skuList : null
     * storeIds : null
     * amount : null
     * sales : null
     * maxPrice : null
     * minPrice : null
     * totalPrice : null
     * categories : null
     * inventory:0
     * todayBuy:true
     */

    private int id;
    private Object archive;
    private String createAt;
    private Object updateAt;
    private String name;
    private String images;
    private String desc;
    private String categoryId;
    private int status;
    private int dividerPercent;
    private int storeId;
    private String image;
    private int sort;
    private Object skuList;
    private int amount;
    private int sales;
    private double maxPrice;
    private double minPrice;
    private double totalPrice;
    private Object categories;
    private int shopnumber;
    private boolean sc_isChoosed;
    private List<String> imageList;
    private Object archiveX;
    private Object updateAtX;
    private Object descX;
    private Object food;
    private double sourcePrice;
    private double currentPrice;
    private Object skuListX;
    private Object storeIds;
    private Object amountX;
    private Object salesX;
    private Object categoriesX;
    private Integer inventory;

    private boolean todayBuy;

    public boolean isTodayBuy() {
        return todayBuy;
    }

    public void setTodayBuy(boolean todayBuy) {
        this.todayBuy = todayBuy;
    }

    public Integer getInventory() {
        return inventory;
    }

    public void setInventory(Integer inventory) {
        this.inventory = inventory;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Object getArchive() {
        return archive;
    }

    public void setArchive(Object archive) {
        this.archive = archive;
    }

    public String getCreateAt() {
        return createAt;
    }

    public void setCreateAt(String createAt) {
        this.createAt = createAt;
    }

    public Object getUpdateAt() {
        return updateAt;
    }

    public void setUpdateAt(Object updateAt) {
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

    public Object getSkuList() {
        return skuList;
    }

    public void setSkuList(Object skuList) {
        this.skuList = skuList;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public int getSales() {
        return sales;
    }

    public void setSales(int sales) {
        this.sales = sales;
    }

    public double getMaxPrice() {
        return maxPrice;
    }

    public void setMaxPrice(double maxPrice) {
        this.maxPrice = maxPrice;
    }

    public double getMinPrice() {
        return minPrice;
    }

    public void setMinPrice(double minPrice) {
        this.minPrice = minPrice;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public Object getCategories() {
        return categories;
    }

    public void setCategories(Object categories) {
        this.categories = categories;
    }

    public List<String> getImageList() {
        return imageList;
    }

    public void setImageList(List<String> imageList) {
        this.imageList = imageList;
    }

    public int getShopnumber() {
        return shopnumber;
    }

    public void setShopnumber(int shopnumber) {
        this.shopnumber = shopnumber;
    }

    public boolean isSc_isChoosed() {
        return sc_isChoosed;
    }

    public void setSc_isChoosed(boolean sc_isChoosed) {
        this.sc_isChoosed = sc_isChoosed;
    }

    public Object getArchiveX() {
        return archiveX;
    }

    public void setArchiveX(Object archiveX) {
        this.archiveX = archiveX;
    }

    public Object getUpdateAtX() {
        return updateAtX;
    }

    public void setUpdateAtX(Object updateAtX) {
        this.updateAtX = updateAtX;
    }

    public Object getDescX() {
        return descX;
    }

    public void setDescX(Object descX) {
        this.descX = descX;
    }

    public Object getFood() {
        return food;
    }

    public void setFood(Object food) {
        this.food = food;
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

    public Object getSkuListX() {
        return skuListX;
    }

    public void setSkuListX(Object skuListX) {
        this.skuListX = skuListX;
    }

    public Object getStoreIds() {
        return storeIds;
    }

    public void setStoreIds(Object storeIds) {
        this.storeIds = storeIds;
    }

    public Object getAmountX() {
        return amountX;
    }

    public void setAmountX(Object amountX) {
        this.amountX = amountX;
    }

    public Object getSalesX() {
        return salesX;
    }

    public void setSalesX(Object salesX) {
        this.salesX = salesX;
    }

    public Object getCategoriesX() {
        return categoriesX;
    }

    public void setCategoriesX(Object categoriesX) {
        this.categoriesX = categoriesX;
    }
}
