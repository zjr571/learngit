package com.ruidun.service.weixinservice.model;

import com.ruidun.service.weixinservice.Bean.ChargingContentJsonResult;

public class RequestBobyModel {
    private  String accessToken;
    private  int commandType;
    private String status;
    private String deviceId;
    private String msg;
    private  int seq;
    private Object data;

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }


    public  RequestBobyModel status(  String accessToken,  int commandType,String status, String deviceId, String msg,int seq) {
        this.accessToken = accessToken;
        this.commandType = commandType;
        this.status = status;
        this.deviceId = deviceId;
        this.msg = msg;
        this.seq = seq;
        return this;
    }
}
