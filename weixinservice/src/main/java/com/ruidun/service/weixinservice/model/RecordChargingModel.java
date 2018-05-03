package com.ruidun.service.weixinservice.model;

public class RecordChargingModel {
    private String deviceId;
    private String location;
    private String startTime;
    private int payment;
    private String state;

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public int getPayment() {
        return payment;
    }

    public void setPayment(int payment) {
        this.payment = payment;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }


}
