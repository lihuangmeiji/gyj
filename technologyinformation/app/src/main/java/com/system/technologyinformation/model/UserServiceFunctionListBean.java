package com.system.technologyinformation.model;

import java.util.List;

public class UserServiceFunctionListBean {
    private List<UserServiceFunctionBean> userFunctionBeanList;

    public List<UserServiceFunctionBean> getUserFunctionBeanList() {
        return userFunctionBeanList;
    }

    public void setUserFunctionBeanList(List<UserServiceFunctionBean> userFunctionBeanList) {
        this.userFunctionBeanList = userFunctionBeanList;
    }

    public static class UserServiceFunctionBean{
        private int uf_id;
        private int uf_ico;
        private String uf_name;

        public int getUf_id() {
            return uf_id;
        }

        public void setUf_id(int uf_id) {
            this.uf_id = uf_id;
        }

        public int getUf_ico() {
            return uf_ico;
        }

        public void setUf_ico(int uf_ico) {
            this.uf_ico = uf_ico;
        }

        public String getUf_name() {
            return uf_name;
        }

        public void setUf_name(String uf_name) {
            this.uf_name = uf_name;
        }
    }
}
