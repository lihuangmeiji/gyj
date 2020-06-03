package com.system.technologyinformation.model;

public class RechargersTelephoneChargesBean {

    /**
     * currentPrice : 9.5
     * sourcePrice : 10
     */

    private double currentPrice;
    private Integer sourcePrice;

    private boolean isSelect;

    public boolean isSelect() {
        return isSelect;
    }

    public void setSelect(boolean select) {
        isSelect = select;
    }

    public double getCurrentPrice() {
        return currentPrice;
    }

    public void setCurrentPrice(double currentPrice) {
        this.currentPrice = currentPrice;
    }

    public Integer getSourcePrice() {
        return sourcePrice;
    }

    public void setSourcePrice(Integer sourcePrice) {
        this.sourcePrice = sourcePrice;
    }
}
