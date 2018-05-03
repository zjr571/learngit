package com.ruidun.service.weixinservice.model;

public class RequestSonBodyModel {
    private int slotIndex;
    private int serviceSeconds;
    private int currentMa;


    public int getSlotIndex() {
        return slotIndex;
    }

    public void setSlotIndex(int slotIndex) {
        this.slotIndex = slotIndex;
    }

    public int getServiceSeconds() {
        return serviceSeconds;
    }

    public void setServiceSeconds(int serviceSeconds) {
        this.serviceSeconds = serviceSeconds;
    }

    public int getCurrentMa() {
        return currentMa;
    }

    public void setCurrentMa(int currentMa) {
        this.currentMa = currentMa;
    }
}
