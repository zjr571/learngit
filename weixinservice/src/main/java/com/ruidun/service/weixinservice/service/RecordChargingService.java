package com.ruidun.service.weixinservice.service;

import com.ruidun.service.weixinservice.mapper.ChargingMapper;
import com.ruidun.service.weixinservice.model.RecordChargingModel;
import com.ruidun.service.weixinservice.model.SearchWordChargingModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class RecordChargingService {
    @Autowired
    ChargingMapper chargingMapper;


    public List<RecordChargingModel> selectrecordcharging(String useid) {
        return chargingMapper.getrecordcharging(useid);
    }
}
