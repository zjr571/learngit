package com.ruidun.service.weixinservice.model;

public class PayChargingModel {
    private String location;
    private String locationDetail;
    private String deviceId;
    private String slotSN;
    private int chargerIndex;
    private int slotIndex;


    public int getChargerIndex() {
        return chargerIndex;
    }

    public void setChargerIndex(int chargerIndex) {
        this.chargerIndex = chargerIndex;
    }

    public int getSlotIndex() {
        return slotIndex;
    }

    public void setSlotIndex(int slotIndex) {
        this.slotIndex = slotIndex;
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

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getSlotSN() {
        return slotSN;
    }

    public void setSlotSN(String slotSN) {
        this.slotSN = slotSN;
    }
}
