package com.ruidun.service.weixinservice.model;

public class ChargingContentModel {
    private  String deviceId;
    private  int chargerIndex;
    private  int usedCount;
    private  int availableCount;

    public int getChargerIndex() {
        return chargerIndex;
    }

    public void setChargerIndex(int chargerIndex) {
        this.chargerIndex = chargerIndex;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public int getUsedCount() {
        return usedCount;
    }

    public void setUsedCount(int usedCount) {
        this.usedCount = usedCount;
    }

    public int getAvailableCount() {
        return availableCount;
    }

    public void setAvailableCount(int availableCount) {
        this.availableCount = availableCount;
    }


}
