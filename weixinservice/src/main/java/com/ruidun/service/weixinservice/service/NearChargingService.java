package com.ruidun.service.weixinservice.service;

import com.ruidun.service.weixinservice.mapper.ChargingMapper;
import com.ruidun.service.weixinservice.model.ChargingInfoModel;
import com.ruidun.service.weixinservice.model.NearChargingModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NearChargingService {
    @Autowired
    ChargingMapper chargingMapper;


    public List<NearChargingModel> selectNearChargingInfo() {
        return chargingMapper.getnearcharging();
    }
}
