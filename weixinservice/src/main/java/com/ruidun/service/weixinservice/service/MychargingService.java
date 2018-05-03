package com.ruidun.service.weixinservice.service;

import com.ruidun.service.weixinservice.mapper.ChargingMapper;
import com.ruidun.service.weixinservice.model.LocationModel;
import com.ruidun.service.weixinservice.model.MyChargingModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class MychargingService {
    @Autowired
    ChargingMapper chargingMapper;


    public MyChargingModel selectmycharging(String userid) {
        return chargingMapper.getmycharging(userid);
    }
}
