package com.ruidun.service.weixinservice.service;

import com.ruidun.service.weixinservice.mapper.ChargingMapper;
import com.ruidun.service.weixinservice.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PayService {
    @Autowired
    ChargingMapper chargingMapper;

    public SelectOrderModel selectOrderModel(String out_trade_no) {
        return chargingMapper.selectorder(out_trade_no);
    }

    public InsertOrderModel insertorder(String openId, String packag, long payment, String paytime, int status, String porepayId, String deviceId, int slotIndex, String out_trade_no) {
        return chargingMapper.insertorder(openId,packag,payment,paytime,status,porepayId,deviceId,slotIndex,out_trade_no);
    }

    public UpdateOrderModel updateOrderModel(int status, String out_trade_no) {
        return chargingMapper.updateorder(status,out_trade_no);
    }

    public ChargingInfoModel getpaylocation(String deviceId){

        return  chargingMapper.getpaylocation(deviceId);
    }

    public int getchargingorderstatus(String out_trade_no){

        return  chargingMapper.getchargingorderstatus(out_trade_no);
    }
}
