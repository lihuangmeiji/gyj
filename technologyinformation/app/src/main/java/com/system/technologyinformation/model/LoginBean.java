package com.system.technologyinformation.model;

import java.io.Serializable;
import java.util.List;

public class LoginBean implements Serializable {


    /**
     * id : 6
     * phone : 15835568257
     * nickName : 测试
     * icon : http://api8081.ximuok.com/resource/e776d1d3b2864e578c4bc2a9455db3fe.jpg
     * gender : 男
     * point : 9451
     * invCode : MNOT6L
     * bornDate : 1912-03-21
     * checkIn : false
     * toDayPoints : 50
     * token : 38bc9d43ef8d288d58efec9b4a920461
     * provinceAreaCode : null
     * areaCode : null
     * fromStr :
     * shopUser
     * constructionPlace : {"id":2,"archive":0,"createAt":"2019-05-07 17:01:43","updateAt":"2019-07-31 16:16:49","name":"金茂府","area":"2","province":"浙江省","city":"杭州市","zone":"2","address":"2","period":[2019,7,31],"longitude":"2","latitude":"21"}
     * careerKinds : [{"id":2,"archive":0,"createAt":"2019-01-26 15:14:51","updateAt":"2019-01-26 15:14:51","name":"桩机工","sort":2,"type":0},{"id":7,"archive":0,"createAt":"2019-01-26 15:14:51","updateAt":"2019-01-26 15:14:51","name":"装修工","sort":7,"type":0},{"id":11,"archive":0,"createAt":"2019-01-26 15:14:51","updateAt":"2019-01-26 15:14:51","name":"电工","sort":11,"type":0},{"id":21,"archive":0,"createAt":"2019-01-26 15:14:51","updateAt":"2019-01-26 15:14:51","name":"材料员","sort":21,"type":1}]
     */

    private int id;
    private String phone;
    private String nickName;
    private String icon;
    private String gender;
    private int point;
    private String invCode;
    private String bornDate;
    private boolean checkIn;
    private int toDayPoints;
    private String token;
    private Object provinceAreaCode;
    private Object areaCode;
    private String fromStr;
    private ConstructionPlaceBean constructionPlace;
    private List<CareerKindsBean> careerKinds;
    private ShopUsereBean shopUser;

    public ShopUsereBean getShopUser() {
        return shopUser;
    }

    public void setShopUser(ShopUsereBean shopUser) {
        this.shopUser = shopUser;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public int getPoint() {
        return point;
    }

    public void setPoint(int point) {
        this.point = point;
    }

    public String getInvCode() {
        return invCode;
    }

    public void setInvCode(String invCode) {
        this.invCode = invCode;
    }

    public String getBornDate() {
        return bornDate;
    }

    public void setBornDate(String bornDate) {
        this.bornDate = bornDate;
    }

    public boolean isCheckIn() {
        return checkIn;
    }

    public void setCheckIn(boolean checkIn) {
        this.checkIn = checkIn;
    }

    public int getToDayPoints() {
        return toDayPoints;
    }

    public void setToDayPoints(int toDayPoints) {
        this.toDayPoints = toDayPoints;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Object getProvinceAreaCode() {
        return provinceAreaCode;
    }

    public void setProvinceAreaCode(Object provinceAreaCode) {
        this.provinceAreaCode = provinceAreaCode;
    }

    public Object getAreaCode() {
        return areaCode;
    }

    public void setAreaCode(Object areaCode) {
        this.areaCode = areaCode;
    }

    public String getFromStr() {
        return fromStr;
    }

    public void setFromStr(String fromStr) {
        this.fromStr = fromStr;
    }

    public ConstructionPlaceBean getConstructionPlace() {
        return constructionPlace;
    }

    public void setConstructionPlace(ConstructionPlaceBean constructionPlace) {
        this.constructionPlace = constructionPlace;
    }

    public List<CareerKindsBean> getCareerKinds() {
        return careerKinds;
    }

    public void setCareerKinds(List<CareerKindsBean> careerKinds) {
        this.careerKinds = careerKinds;
    }

    public static class ConstructionPlaceBean {
        /**
         * id : 2
         * archive : 0
         * createAt : 2019-05-07 17:01:43
         * updateAt : 2019-07-31 16:16:49
         * name : 金茂府
         * area : 2
         * province : 浙江省
         * city : 杭州市
         * zone : 2
         * address : 2
         * period : [2019,7,31]
         * longitude : 2
         * latitude : 21
         */

        private int id;
        private int archive;
        private String createAt;
        private String updateAt;
        private String name;
        private String area;
        private String province;
        private String city;
        private String zone;
        private String address;
        private String longitude;
        private String latitude;
        private List<Integer> period;

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

        public String getArea() {
            return area;
        }

        public void setArea(String area) {
            this.area = area;
        }

        public String getProvince() {
            return province;
        }

        public void setProvince(String province) {
            this.province = province;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public String getZone() {
            return zone;
        }

        public void setZone(String zone) {
            this.zone = zone;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getLongitude() {
            return longitude;
        }

        public void setLongitude(String longitude) {
            this.longitude = longitude;
        }

        public String getLatitude() {
            return latitude;
        }

        public void setLatitude(String latitude) {
            this.latitude = latitude;
        }

        public List<Integer> getPeriod() {
            return period;
        }

        public void setPeriod(List<Integer> period) {
            this.period = period;
        }
    }

    public static class CareerKindsBean {
        /**
         * id : 2
         * archive : 0
         * createAt : 2019-01-26 15:14:51
         * updateAt : 2019-01-26 15:14:51
         * name : 桩机工
         * sort : 2
         * type : 0
         */

        private int id;
        private int archive;
        private String createAt;
        private String updateAt;
        private String name;
        private int sort;
        private int type;

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

        public int getSort() {
            return sort;
        }

        public void setSort(int sort) {
            this.sort = sort;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }
    }

    public static class ShopUsereBean {
       private int id;
       private String invCode;
       private String mobile;
       private int shopId;
       private String shopName;
       private int userId;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getInvCode() {
            return invCode;
        }

        public void setInvCode(String invCode) {
            this.invCode = invCode;
        }

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

        public int getShopId() {
            return shopId;
        }

        public void setShopId(int shopId) {
            this.shopId = shopId;
        }

        public String getShopName() {
            return shopName;
        }

        public void setShopName(String shopName) {
            this.shopName = shopName;
        }

        public int getUserId() {
            return userId;
        }

        public void setUserId(int userId) {
            this.userId = userId;
        }
    }
}
