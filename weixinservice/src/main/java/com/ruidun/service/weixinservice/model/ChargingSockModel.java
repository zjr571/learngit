package com.ruidun.service.weixinservice.model;

public class ChargingSockModel {
    private String deviceId;
    private int slotIndex;
    private int slotStatus;
    private String starttime;
    private String totalTime;
    private String openId;
    private  String out_trade_no;

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public String getOut_trade_no() {
        return out_trade_no;
    }

    public void setOut_trade_no(String out_trade_no) {
        this.out_trade_no = out_trade_no;
    }

    public String getTotalTime() {
        return totalTime;
    }

    public void setTotalTime(String totalTime) {
        this.totalTime = totalTime;
    }

    public String getStarttime() {
        return starttime;
    }

    public void setStarttime(String starttime) {
        this.starttime = starttime;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public int getSlotIndex() {
        return slotIndex;
    }

    public void setSlotIndex(int slotIndex) {
        this.slotIndex = slotIndex;
    }

    public int getSlotStatus() {
        return slotStatus;
    }

    public void setSlotStatus(int slotStatus) {
        this.slotStatus = slotStatus;
    }
}
