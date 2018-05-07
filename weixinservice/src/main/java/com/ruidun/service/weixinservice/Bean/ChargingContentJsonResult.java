package com.ruidun.service.weixinservice.Bean;

public class ChargingContentJsonResult {
    private int availableCountTotal;
    private int usedCountTotal;
    private String location=null;
    private String locationDetail=null;
    private  double lat;
    private  double lng;
    private Object content = null;

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    private double distance;

    public Object getContent() {
        return content;
    }

    public void setContent(Object content) {
        this.content = content;
    }

    public int getAvailableCountTotal() {
        return availableCountTotal;
    }

    public void setAvailableCountTotal(int availableCountTotal) {
        this.availableCountTotal = availableCountTotal;
    }

    public int getUsedCountTotal() {
        return usedCountTotal;
    }

    public void setUsedCountTotal(int usedCountTotal) {
        this.usedCountTotal = usedCountTotal;
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


    public ChargingContentJsonResult status( int availableCountTotal, int usedCountTotal, String location, String locationDetail, double distance, double lat, double lng) {
        this.availableCountTotal = availableCountTotal;
        this.usedCountTotal = usedCountTotal;
        this.location = location;
        this.locationDetail = locationDetail;
        this.lat = lat;
        this.lng = lng;
        return this;
    }
}
