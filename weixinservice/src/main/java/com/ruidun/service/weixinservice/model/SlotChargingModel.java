package com.ruidun.service.weixinservice.model;

public class SlotChargingModel {
    private int slotIndex;
    private int slotStatus;
    private String time;

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

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
