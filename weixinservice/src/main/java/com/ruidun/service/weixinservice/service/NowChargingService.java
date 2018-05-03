package com.ruidun.service.weixinservice.service;

import com.ruidun.service.weixinservice.mapper.ChargingMapper;
import com.ruidun.service.weixinservice.model.NowChargingModel;
import com.ruidun.service.weixinservice.model.SlotChargingModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class NowChargingService {
    @Autowired
    ChargingMapper chargingMapper;


    public NowChargingModel selectNowCharging(String deviceid,int slotId) {
        return chargingMapper.getnowcharging(deviceid,slotId);
    }
}
