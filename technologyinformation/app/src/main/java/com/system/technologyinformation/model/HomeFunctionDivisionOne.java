package com.system.technologyinformation.model;

import java.util.List;

public class HomeFunctionDivisionOne {
  private List<ConvenientFunctionsBean> convenientFunctionsBeanList;

    public List<ConvenientFunctionsBean> getConvenientFunctionsBeanList() {
        return convenientFunctionsBeanList;
    }

    public void setConvenientFunctionsBeanList(List<ConvenientFunctionsBean> convenientFunctionsBeanList) {
        this.convenientFunctionsBeanList = convenientFunctionsBeanList;
    }

    private  HomeConfigurationInformationBean homeConfigurationInformationBean;

    public HomeConfigurationInformationBean getHomeConfigurationInformationBean() {
        return homeConfigurationInformationBean;
    }

    public void setHomeConfigurationInformationBean(HomeConfigurationInformationBean homeConfigurationInformationBean) {
        this.homeConfigurationInformationBean = homeConfigurationInformationBean;
    }
}
