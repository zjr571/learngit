package com.ruidun.service.weixinservice.service;

import com.ruidun.service.weixinservice.mapper.ChargingMapper;
import com.ruidun.service.weixinservice.model.CollectionChargingModel;
import com.ruidun.service.weixinservice.model.InsertOrderModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InsertOrderService {
    @Autowired
    ChargingMapper chargingMapper;

    public InsertOrderModel insertorder(String openId,String packag,long payment,String paytime,int status,String porepayId,String deviceId,int slotIndex) {
        return chargingMapper.insertorder(openId,packag,payment,paytime,status,porepayId,deviceId,slotIndex);
    }
}
