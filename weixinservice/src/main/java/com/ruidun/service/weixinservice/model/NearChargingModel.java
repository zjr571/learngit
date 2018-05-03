package com.ruidun.service.weixinservice.model;

public class NearChargingModel {
    private  int locationId;
    private  String location;
    private  String locationDetail;
    private  int usedCount;
    private  int availableCount;
    private  double distance;
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

    public int getAvailableCount() {

        return availableCount;
    }

    public void setAvailableCount(int availableCount) {
        this.availableCount = availableCount;
    }


    public int getLocationId() {
        return locationId;
    }

    public void setLocationId(int locationId) {
        this.locationId = locationId;
    }

    public double getDistance() {

        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
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

    public int getUsedCount() {
        return usedCount;
    }

    public void setUsedCount(int usedCount) {
        this.usedCount = usedCount;
    }




}
