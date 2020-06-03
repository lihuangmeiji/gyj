package com.system.technologyinformation.model;

import com.google.gson.annotations.SerializedName;

public class PagingInformationBean<T> {

    /**
     * pageNum : 1
     * pageSize : 10
     * size : 3
     * startRow : 1
     * endRow : 3
     * total : 3
     * pages : 1
     */

    @SerializedName("pageNum")
    private int pageNum;
    @SerializedName("pageSize")
    private int pageSize;
    @SerializedName("size")
    private int size;
    @SerializedName("startRow")
    private int startRow;
    @SerializedName("endRow")
    private int endRow;
    @SerializedName("total")
    private int total;
    @SerializedName("pages")
    private int pages;

    private T list;

    public int getPageNum() {
        return pageNum;
    }

    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getStartRow() {
        return startRow;
    }

    public void setStartRow(int startRow) {
        this.startRow = startRow;
    }

    public int getEndRow() {
        return endRow;
    }

    public void setEndRow(int endRow) {
        this.endRow = endRow;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getPages() {
        return pages;
    }

    public void setPages(int pages) {
        this.pages = pages;
    }

    public T getList() {
        return list;
    }

    public void setList(T list) {
        this.list = list;
    }
}
