package com.ruidun.service.weixinservice.Bean;

public class SlotChargingJsonResult {
    private int status;
    private String msg=null;
    private Object data;

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

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

    public SlotChargingJsonResult status(int status, String msg) {
        this.status = status;
        this.msg=msg;
        return this;
    }
}
