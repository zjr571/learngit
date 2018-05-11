package com.ruidun.service.weixinservice.service;

import com.ruidun.service.weixinservice.mapper.ChargingMapper;
import com.ruidun.service.weixinservice.model.SumUsedCountModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    ChargingMapper chargingMapper;

    public void insertUserCollection(String userid,String deviceId) {

         chargingMapper.insertUserCollection(userid,deviceId);
    }
}
