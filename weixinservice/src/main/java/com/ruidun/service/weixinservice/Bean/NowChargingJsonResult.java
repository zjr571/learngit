package com.ruidun.service.weixinservice.Bean;

public class NowChargingJsonResult {
    private int status;
    private String msg=null;
    private Object data = null;
    public int getStatus() {
        return status;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
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

    public NowChargingJsonResult status(int status, String msg) {
        this.status = status;
        this.msg=msg;
        return this;
    }
}
