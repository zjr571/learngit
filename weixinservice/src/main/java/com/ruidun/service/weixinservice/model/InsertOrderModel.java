package com.ruidun.service.weixinservice.model;

public class InsertOrderModel {
    private String openId;
    private String packag;

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public String getPackag() {
        return packag;
    }

    public void setPackag(String packag) {
        this.packag = packag;
    }

    public long getPayment() {
        return payment;
    }

    public void setPayment(long payment) {
        this.payment = payment;
    }

    public String getPaytime() {
        return paytime;
    }

    public void setPaytime(String paytime) {
        this.paytime = paytime;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }



    private long payment;
    private String paytime;
    private int status;

    public String getPrepayId() {
        return prepayId;
    }

    public void setPrepayId(String prepayId) {
        this.prepayId = prepayId;
    }

    private String prepayId;
}
