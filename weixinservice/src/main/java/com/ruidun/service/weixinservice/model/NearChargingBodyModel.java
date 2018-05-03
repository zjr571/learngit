package com.ruidun.service.weixinservice.model;

public class NearChargingBodyModel {
    private String accesstoken;
    private double lat;
    private  double lng;
    private  int pageindex;
    private int pagesize;
    public String getAccesstoken() {
        return accesstoken;
    }

    public void setAccesstoken(String accesstoken) {
        this.accesstoken = accesstoken;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }

    public int getPageindex() {
        return pageindex;
    }

    public void setPageindex(int pageindex) {
        this.pageindex = pageindex;
    }

    public int getPagesize() {
        return pagesize;
    }

    public void setPagesize(int pagesize) {
        this.pagesize = pagesize;
    }


}
