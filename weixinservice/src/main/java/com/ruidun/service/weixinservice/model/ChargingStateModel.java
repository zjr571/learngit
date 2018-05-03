package com.ruidun.service.weixinservice.model;

public class ChargingStateModel {
    private int id;
    private String device_id;
    private String is_state;
    private  int usedCount;
    private  int availableCount;
    private  String power;

    public int getAvailableCount() {
        return availableCount;
    }

    public void setAvailableCount(int availableCount) {
        this.availableCount = availableCount;
    }

    private  String use_state;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDevice_id() {
        return device_id;
    }

    public void setDevice_id(String device_id) {
        this.device_id = device_id;
    }

    public String getIs_state() {
        return is_state;
    }

    public void setIs_state(String is_state) {
        this.is_state = is_state;
    }

    public int getUsedCount() {
        return usedCount;
    }

    public void setUsedCount(int usedCount) {
        this.usedCount = usedCount;
    }



    public String getPower() {
        return power;
    }

    public void setPower(String power) {
        this.power = power;
    }

    public String getUse_state() {
        return use_state;
    }

    public void setUse_state(String use_state) {
        this.use_state = use_state;
    }
}
