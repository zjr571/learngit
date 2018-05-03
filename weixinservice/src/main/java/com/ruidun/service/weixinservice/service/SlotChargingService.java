package com.ruidun.service.weixinservice.service;

import com.ruidun.service.weixinservice.mapper.ChargingMapper;
import com.ruidun.service.weixinservice.model.NearChargingModel;
import com.ruidun.service.weixinservice.model.SlotChargingModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class SlotChargingService {
    @Autowired
    ChargingMapper chargingMapper;


    public List<SlotChargingModel> selectslotCharging(String deviceid) {
        return chargingMapper.getslotcharging(deviceid);
    }
}
