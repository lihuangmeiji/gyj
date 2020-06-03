package com.system.technologyinformation.model;

public class UserIntegralBean {


    /**
     * typeName : 积分抵扣
     * type : 21
     * id : 26705
     * createAt : 2019-10-22 22:50:31
     * delta : -1
     * deduction : -0.01
     */

    private String typeName;
    private int type;
    private int id;
    private String createAt;
    private int delta;
    private double deduction;

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCreateAt() {
        return createAt;
    }

    public void setCreateAt(String createAt) {
        this.createAt = createAt;
    }

    public int getDelta() {
        return delta;
    }

    public void setDelta(int delta) {
        this.delta = delta;
    }

    public double getDeduction() {
        return deduction;
    }

    public void setDeduction(double deduction) {
        this.deduction = deduction;
    }
}
