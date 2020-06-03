package com.system.technologyinformation.model;

import java.io.Serializable;

public class StoreInfoBean implements Serializable {


    /**
     * id : 1
     * name : 物美超市
     * icon : http://gyj-d.idougong.com/logo/57.png
     * userPoint : 3991
     * pointConvert : 0.01
     * pointPercentage : 0.1
     * aliPay : true
     * wxPay : false
     * url
     * code
     * refreshMine
     * usePoint
     */

    private int id;
    private String name;
    private String icon;
    private int userPoint;
    private double pointConvert;
    private double pointPercentage;
    private boolean aliPay;
    private boolean wxPay;
    private String url;
    private int code;
    private String refreshMine;

    public boolean isUsePoint() {
        return usePoint;
    }

    public void setUsePoint(boolean usePoint) {
        this.usePoint = usePoint;
    }

    private boolean usePoint;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

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

    public int getUserPoint() {
        return userPoint;
    }

    public void setUserPoint(int userPoint) {
        this.userPoint = userPoint;
    }

    public double getPointConvert() {
        return pointConvert;
    }

    public void setPointConvert(double pointConvert) {
        this.pointConvert = pointConvert;
    }

    public double getPointPercentage() {
        return pointPercentage;
    }

    public void setPointPercentage(double pointPercentage) {
        this.pointPercentage = pointPercentage;
    }

    public boolean isAliPay() {
        return aliPay;
    }

    public void setAliPay(boolean aliPay) {
        this.aliPay = aliPay;
    }

    public boolean isWxPay() {
        return wxPay;
    }

    public void setWxPay(boolean wxPay) {
        this.wxPay = wxPay;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getRefreshMine() {
        return refreshMine;
    }

    public void setRefreshMine(String refreshMine) {
        this.refreshMine = refreshMine;
    }
}
