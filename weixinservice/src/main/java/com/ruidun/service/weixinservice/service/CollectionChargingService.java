package com.ruidun.service.weixinservice.service;

import com.ruidun.service.weixinservice.mapper.ChargingMapper;
import com.ruidun.service.weixinservice.model.ChargingModel;
import com.ruidun.service.weixinservice.model.CollectionChargingModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class CollectionChargingService {
    @Autowired
    ChargingMapper chargingMapper;


    public List<CollectionChargingModel> selectcollectionCharging(String mark) {
        return chargingMapper.getcollectioncharging(mark);
    }
}
