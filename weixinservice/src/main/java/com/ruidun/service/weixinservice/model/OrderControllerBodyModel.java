package com.ruidun.service.weixinservice.model;

public class OrderControllerBodyModel {
    String accesstoken;
    String Out_trade_no;

    public String getAccesstoken() {
        return accesstoken;
    }

    public void setAccesstoken(String accesstoken) {
        this.accesstoken = accesstoken;
    }

    public String getOut_trade_no() {
        return Out_trade_no;
    }

    public void setOut_trade_no(String out_trade_no) {
        Out_trade_no = out_trade_no;
    }
}
