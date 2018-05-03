package com.ruidun.service.weixinservice.model;

public class SearchWordChargingBodyModel {
    private String accesstoken;
    private String searchWord;
    private String city;
    private double lat;
    private  double lng;

    public String getAccesstoken() {
        return accesstoken;
    }

    public void setAccesstoken(String accesstoken) {
        this.accesstoken = accesstoken;
    }

    public String getSearchWord() {
        return searchWord;
    }

    public void setSearchWord(String searchWord) {
        this.searchWord = searchWord;
    }



    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
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
