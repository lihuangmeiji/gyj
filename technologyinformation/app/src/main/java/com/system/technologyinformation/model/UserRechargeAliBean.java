package com.system.technologyinformation.model;

public class UserRechargeAliBean {


    /**
     * aliOrderNo : alipay_sdk=alipay-sdk-java-3.4.27.ALL&app_id=2019032663704286&biz_content=%7B%22body%22%3A%22%E5%B7%A8%E6%B5%AA%E5%95%86%E9%93%BA%22%2C%22out_trade_no%22%3A%2215724221280201909061130367009717%22%2C%22passback_params%22%3A%222%22%2C%22product_code%22%3A%22QUICK_MSECURITY_PAY%22%2C%22subject%22%3A%22%E5%B7%A8%E6%B5%AA%E5%95%86%E9%93%BA%22%2C%22timeout_express%22%3A%225m%22%2C%22total_amount%22%3A%220.31%22%7D&charset=UTF-8&format=json&method=alipay.trade.app.pay&notify_url=http%3A%2F%2Fapi8081.ximuok.com%2Fpayback%2Fali%2Fnotify&sign=WWkBMtpbEzyNH6WRD11boiixO7c7atFRCnPKTOI3%2FuWZbzaHXcH99r7mhjqayPqiAm8vI%2BaNvtyjZ%2BOyGkWWgJAM6SpOgbxdVlKD8QwSPKE4hyQF%2Fm3FsoboqEoDXdjIqlcdx92dhRydTQnUJl%2Fy3nLSicp4PwcQ8Cv5NynhnCV9u6KSsX9LyOmWn160zy6OatkwVp6mahEZjc%2BqeH7wzL1SSbqgmcsj8NuWS8JX4z9azyzHRyS5N8hcM9s4hIaIx5UUNn%2BHWtflq%2FfUghSGepC8ilfLqhldHpWUZ3kLt5h8%2BMge0WcUagkuVtdzwHZ8EcQswYjtIo9C%2B0bHcn%2BKqA%3D%3D&sign_type=RSA2&timestamp=2019-09-06+11%3A30%3A36&version=1.0
     * order : {"id":31,"archive":0,"createAt":"2019-09-06 11:30:36","updateAt":null,"userId":20,"shopId":12,"orderNo":"15724221280201909061130367009717","payStatus":1,"remissionPrice":0.14,"totalPrice":0.5,"point":5,"finalPrice":0.31}
     */

    private String aliOrderNo;
    private PayOrderBean order;

    public String getAliOrderNo() {
        return aliOrderNo;
    }

    public void setAliOrderNo(String aliOrderNo) {
        this.aliOrderNo = aliOrderNo;
    }

    public PayOrderBean getOrder() {
        return order;
    }

    public void setOrder(PayOrderBean order) {
        this.order = order;
    }


}
