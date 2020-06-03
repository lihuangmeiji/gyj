package com.system.technologyinformation.model;


import com.contrarywind.interfaces.IPickerViewData;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Sai on 15/11/22.
 */
public class ProvinceBean implements IPickerViewData {

    /**
     * id : 3
     * archive : 0
     * createAt : 2018-12-27 16:46:12
     * updateAt : null
     * areaCode : 130000
     * netName : 河北省
     * provinceAreas : [{"id":37,"archive":0,"createAt":"2018-12-27 16:46:14","updateAt":null,"areaCode":130100,"netName":"石家庄市","provinceAreas":null},{"id":38,"archive":0,"createAt":"2018-12-27 16:46:14","updateAt":null,"areaCode":130200,"netName":"唐山市","provinceAreas":null},{"id":39,"archive":0,"createAt":"2018-12-27 16:46:14","updateAt":null,"areaCode":130300,"netName":"秦皇岛市","provinceAreas":null},{"id":40,"archive":0,"createAt":"2018-12-27 16:46:14","updateAt":null,"areaCode":130400,"netName":"邯郸市","provinceAreas":null},{"id":41,"archive":0,"createAt":"2018-12-27 16:46:14","updateAt":null,"areaCode":130500,"netName":"邢台市","provinceAreas":null},{"id":42,"archive":0,"createAt":"2018-12-27 16:46:14","updateAt":null,"areaCode":130600,"netName":"保定市","provinceAreas":null},{"id":43,"archive":0,"createAt":"2018-12-27 16:46:15","updateAt":null,"areaCode":130700,"netName":"张家口市","provinceAreas":null},{"id":44,"archive":0,"createAt":"2018-12-27 16:46:15","updateAt":null,"areaCode":130800,"netName":"承德市","provinceAreas":null},{"id":45,"archive":0,"createAt":"2018-12-27 16:46:15","updateAt":null,"areaCode":130900,"netName":"沧州市","provinceAreas":null},{"id":46,"archive":0,"createAt":"2018-12-27 16:46:15","updateAt":null,"areaCode":131000,"netName":"廊坊市","provinceAreas":null},{"id":47,"archive":0,"createAt":"2018-12-27 16:46:15","updateAt":null,"areaCode":131100,"netName":"衡水市","provinceAreas":null}]
     */

    @SerializedName("id")
    private int id;
    @SerializedName("archive")
    private int archive;
    @SerializedName("createAt")
    private String createAt;
    @SerializedName("updateAt")
    private Object updateAt;
    @SerializedName("areaCode")
    private int areaCode;
    @SerializedName("netName")
    private String netName;
    @SerializedName("provinceAreas")
    private List<ProvinceAreasBean> provinceAreas;

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

    public Object getUpdateAt() {
        return updateAt;
    }

    public void setUpdateAt(Object updateAt) {
        this.updateAt = updateAt;
    }

    public int getAreaCode() {
        return areaCode;
    }

    public void setAreaCode(int areaCode) {
        this.areaCode = areaCode;
    }

    public String getNetName() {
        return netName;
    }

    public void setNetName(String netName) {
        this.netName = netName;
    }

    public List<ProvinceAreasBean> getProvinceAreas() {
        return provinceAreas;
    }

    public void setProvinceAreas(List<ProvinceAreasBean> provinceAreas) {
        this.provinceAreas = provinceAreas;
    }

    @Override
    public String getPickerViewText() {
        return this.netName;
    }

    public static class ProvinceAreasBean {
        /**
         * id : 37
         * archive : 0
         * createAt : 2018-12-27 16:46:14
         * updateAt : null
         * areaCode : 130100
         * netName : 石家庄市
         * provinceAreas : null
         */

        @SerializedName("id")
        private int id;
        @SerializedName("archive")
        private int archive;
        @SerializedName("createAt")
        private String createAt;
        @SerializedName("updateAt")
        private Object updateAt;
        @SerializedName("areaCode")
        private int areaCode;
        @SerializedName("netName")
        private String netName;
        @SerializedName("provinceAreas")
        private List<CityBean> provinceAreas;

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

        public Object getUpdateAt() {
            return updateAt;
        }

        public void setUpdateAt(Object updateAt) {
            this.updateAt = updateAt;
        }

        public int getAreaCode() {
            return areaCode;
        }

        public void setAreaCode(int areaCode) {
            this.areaCode = areaCode;
        }

        public String getNetName() {
            return netName;
        }

        public void setNetName(String netName) {
            this.netName = netName;
        }

        public List<CityBean> getProvinceAreas() {
            return provinceAreas;
        }

        public void setProvinceAreas(List<CityBean> provinceAreas) {
            this.provinceAreas = provinceAreas;
        }
    }
}
