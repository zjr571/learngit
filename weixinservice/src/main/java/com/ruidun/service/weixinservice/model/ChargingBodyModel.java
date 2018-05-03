package com.ruidun.service.weixinservice.model;

public class ChargingBodyModel {
    private String accesstoken;
    private String locationId;
    private double lat;
    private  double lng;

    public String getAccesstoken() {
        return accesstoken;
    }

    public void setAccesstoken(String accesstoken) {
        this.accesstoken = accesstoken;
    }

    public String getLocationId() {
        return locationId;
    }

    public void setLocationId(String locationId) {
        this.locationId = locationId;
    }
}
