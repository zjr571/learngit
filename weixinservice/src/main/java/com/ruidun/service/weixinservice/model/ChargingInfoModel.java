package com.ruidun.service.weixinservice.model;

public class ChargingInfoModel {
    private int id;
    private  String deviceId;
    private  String locationId;
    private String province;
    private String city;
    private  String county;
    private  String location;
    private  String locationDetail;
    private  String installationtime;
    private  String installationpeople;
    private  String installationpeoplephone;
    private  String agentid;
    private  String suppliername;
    private  double lat;
    private  double lng;


    public String getLocationId() {
        return locationId;
    }

    public void setLocationId(String locationId) {
        this.locationId = locationId;
    }

    public String getLocationDetail() {
        return locationDetail;
    }

    public void setLocationDetail(String locationDetail) {
        this.locationDetail = locationDetail;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }



    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCounty() {
        return county;
    }

    public void setCounty(String county) {
        this.county = county;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDeatiledlocation() {
        return locationDetail;
    }

    public void setDeatiledlocation(String locationDetail) {
        this.locationDetail = locationDetail;
    }

    public String getInstallationtime() {
        return installationtime;
    }

    public void setInstallationtime(String installationtime) {
        this.installationtime = installationtime;
    }

    public String getInstallationpeople() {
        return installationpeople;
    }

    public void setInstallationpeople(String installationpeople) {
        this.installationpeople = installationpeople;
    }

    public String getInstallationpeoplephone() {
        return installationpeoplephone;
    }

    public void setInstallationpeoplephone(String installationpeoplephone) {
        this.installationpeoplephone = installationpeoplephone;
    }

    public String getAgentid() {
        return agentid;
    }

    public void setAgentid(String agentid) {
        this.agentid = agentid;
    }

    public String getSuppliername() {
        return suppliername;
    }

    public void setSuppliername(String suppliername) {
        this.suppliername = suppliername;
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

}
