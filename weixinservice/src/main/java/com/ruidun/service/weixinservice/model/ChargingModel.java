package com.ruidun.service.weixinservice.model;

public class ChargingModel {

    private  String location;
    private  String locationDetail;
    private  double distance;
    private  int availableCountTotal;
    private  int usedCountTotal;
    private  double lat;
    private  double lng;



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

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
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


}
