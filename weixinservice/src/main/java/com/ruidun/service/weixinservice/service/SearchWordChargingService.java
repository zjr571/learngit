package com.ruidun.service.weixinservice.service;

import com.ruidun.service.weixinservice.mapper.ChargingMapper;
import com.ruidun.service.weixinservice.model.NowChargingModel;
import com.ruidun.service.weixinservice.model.SearchWordChargingModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class SearchWordChargingService {
    @Autowired
    ChargingMapper chargingMapper;


    public List<SearchWordChargingModel> selectSearchWordCharging(String search) {
        return chargingMapper.getsearchcharging(search);
    }
}
