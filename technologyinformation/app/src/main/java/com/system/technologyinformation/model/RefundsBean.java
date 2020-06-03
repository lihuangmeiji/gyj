package com.system.technologyinformation.model;

public class RefundsBean {
    private int r_id;
    private String r_content;
    private boolean isSelcet;

    public int getR_id() {
        return r_id;
    }

    public void setR_id(int r_id) {
        this.r_id = r_id;
    }

    public String getR_content() {
        return r_content;
    }

    public void setR_content(String r_content) {
        this.r_content = r_content;
    }

    public boolean isSelcet() {
        return isSelcet;
    }

    public void setSelcet(boolean selcet) {
        isSelcet = selcet;
    }
}
