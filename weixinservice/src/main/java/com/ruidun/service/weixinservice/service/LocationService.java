package com.ruidun.service.weixinservice.service;

import com.ruidun.service.weixinservice.mapper.ChargingMapper;
import com.ruidun.service.weixinservice.model.LocationModel;
import com.ruidun.service.weixinservice.model.NearChargingModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LocationService {
    @Autowired
    ChargingMapper chargingMapper;


    public List<LocationModel> selectlocation(String deviceId) {
        return chargingMapper.getLocation(deviceId);
    }
}
