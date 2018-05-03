package com.ruidun.service.weixinservice.service;

import com.ruidun.service.weixinservice.mapper.ChargingMapper;
import com.ruidun.service.weixinservice.model.NowChargingModel;
import com.ruidun.service.weixinservice.model.PayChargingModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PayChargingService {
    @Autowired
    ChargingMapper chargingMapper;


    public PayChargingModel selectpayCharging(String deviceid, int slotId) {
        return chargingMapper.getpaycharging(deviceid,slotId);
    }
}
