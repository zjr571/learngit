package com.ruidun.service.weixinservice.service;

import com.ruidun.service.weixinservice.mapper.ChargingMapper;
import com.ruidun.service.weixinservice.model.ChargingModel;
import com.ruidun.service.weixinservice.model.NearChargingModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class ChargingService {
    @Autowired
    ChargingMapper chargingMapper;


    public List<ChargingModel> selectCharging(String mark) {

        return chargingMapper.getcharging(mark);
    }
}