package com.system.technologyinformation.model;

public class UserNameInfoBean {

    /**
     * id : 53
     * archive : 0
     * createAt : 2019-09-06 14:50:34
     * updateAt : 2019-09-06 14:50:34
     * userId : 6
     * realName : null
     * idCode :
     * imgFront : http://gongyoujia-image.oss-cn-hangzhou.aliyuncs.com/image/idCard/3fbbd32694be4a56a723f298ce025f90.jpg
     * imgBack : http://gongyoujia-image.oss-cn-hangzhou.aliyuncs.com/image/idCard/f03cce7015fe47909e1923272fd9d38f.jpg
     * status : 1
     * rejectsInfo：null
     */

    private int id;
    private int archive;
    private String createAt;
    private String updateAt;
    private int userId;
    private String realName;
    private String idCode;
    private String imgFront;
    private String imgBack;
    private int status;
    private  String rejectsInfo;
    private String validityPeriod;

    public String getRejectsInfo() {
        return rejectsInfo;
    }

    public void setRejectsInfo(String rejectsInfo) {
        this.rejectsInfo = rejectsInfo;
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

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getIdCode() {
        return idCode;
    }

    public void setIdCode(String idCode) {
        this.idCode = idCode;
    }

    public String getImgFront() {
        return imgFront;
    }

    public void setImgFront(String imgFront) {
        this.imgFront = imgFront;
    }

    public String getImgBack() {
        return imgBack;
    }

    public void setImgBack(String imgBack) {
        this.imgBack = imgBack;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getValidityPeriod() {
        return validityPeriod;
    }

    public void setValidityPeriod(String validityPeriod) {
        this.validityPeriod = validityPeriod;
    }
}
