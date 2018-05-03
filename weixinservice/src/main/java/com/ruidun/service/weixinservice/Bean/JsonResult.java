package com.ruidun.service.weixinservice.Bean;

public class JsonResult {
    private int status;
    private String msg=null;



    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    private Object data = null;

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public JsonResult status(int status, String msg) {
        this.status = status;
        this.msg=msg;

        return this;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }


}
