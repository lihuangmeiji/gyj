package com.system.technologyinformation.model.http.base;

/**
 * Created by zhaotun
 */

public class BaseStatus{

    public static final int RESPONSE_CODE_SUCCESS = 0;// 0成功
    public static final int RESPONSE_CODE_ERROR_INVALID_TOKEN = 10003;//非法的用户会话信息
    public static final int RESPONSE_CODE_LOGIN_OVERDUE = -1;//登录过期
    private int code;

    private String msg;

    private String detail;

    @Override
    public String toString() {
        return "BaseStatus{" +

                "code=" + code +
                ", message='" + msg + '\'' +
                ", detail='" + detail + '\'' +
                '}';
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }



    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public boolean isSuccess() {
        return code == RESPONSE_CODE_SUCCESS;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }
}
