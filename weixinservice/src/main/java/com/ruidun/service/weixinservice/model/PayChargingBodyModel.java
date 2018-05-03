package com.ruidun.service.weixinservice.model;

public class PayChargingBodyModel {
        private String accesstoken;
        private String deviceId;
        private int slotIndex;

    public int getSlotIndex() {
        return slotIndex;
    }

    public void setSlotIndex(int slotIndex) {
        this.slotIndex = slotIndex;
    }

    public String getAccesstoken() {
        return accesstoken;

    }

    public void setAccesstoken(String accesstoken) {
        this.accesstoken = accesstoken;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }


}
