package com.ruidun.service.weixinservice.service;

import com.ruidun.service.weixinservice.mapper.ChargingMapper;
import com.ruidun.service.weixinservice.model.*;
import org.apache.ibatis.annotations.Select;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
@Service
public class SumService {
    @Autowired
    ChargingMapper chargingMapper;

    public SumUsedCountModel sumUsedCountService(int locationId) {

        return chargingMapper.getsumusedcount(locationId);
    }

    public SumAvailableCountModel sumAvailableCountModel(int locationId) {
        return chargingMapper.getsumavailablecount(locationId);
    }

    public SumPaymentModel sumPaymentModel(String openId){

        return chargingMapper.getsumPaymentcount(openId);
    }
    public CountModel countUserCharging(String userid,String deviceId){

        return chargingMapper.countUserCharging(userid,deviceId);
    }

}
