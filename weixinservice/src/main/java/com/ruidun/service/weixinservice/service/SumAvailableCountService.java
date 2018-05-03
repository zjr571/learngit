package com.ruidun.service.weixinservice.service;

import com.ruidun.service.weixinservice.mapper.ChargingMapper;
import com.ruidun.service.weixinservice.model.SumAvailableCountModel;
import com.ruidun.service.weixinservice.model.SumUsedCountModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SumAvailableCountService {
    @Autowired
    ChargingMapper chargingMapper;


    public SumAvailableCountModel sumAvailableCountModel(int locationId) {
        return chargingMapper.getsumavailablecount(locationId);
    }
}
