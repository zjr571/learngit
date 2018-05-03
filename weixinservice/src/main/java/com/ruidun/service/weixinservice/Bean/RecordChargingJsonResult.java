package com.ruidun.service.weixinservice.Bean;

public class RecordChargingJsonResult {
    private int status;
    private String msg=null;


    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    private Object data = null;
}
