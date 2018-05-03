package com.ruidun.service.weixinservice.model;

public class MyChargingModel {
    private String deviceId;
    private String location;
    private  String locationDetail;
    private  int slotIndex;
    private  String startTime;
    private String time;
    private  String chargingTime;

    public String getSlotSN() {
        return slotSN;
    }

    public void setSlotSN(String slotSN) {
        this.slotSN = slotSN;
    }

    private String slotSN;
    private int progress;
    private int chargerIndex;
    public String getChargingTime() {
        return chargingTime;
    }

    public void setChargingTime(String chargingTime) {
        this.chargingTime = chargingTime;
    }



    public int getProgress() {
        return progress;
    }

    public int getSlotIndex() {
        return slotIndex;
    }

    public void setSlotIndex(int slotIndex) {
        this.slotIndex = slotIndex;
    }

    public int getChargerIndex() {
        return chargerIndex;
    }

    public void setChargerIndex(int chargerIndex) {
        this.chargerIndex = chargerIndex;
    }

    public void setProgress(int progress) {
        this.progress = progress;
    }





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

    public String getLocationDetail() {
        return locationDetail;
    }

    public void setLocationDetail(String locationDetail) {
        this.locationDetail = locationDetail;
    }



    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }


}
