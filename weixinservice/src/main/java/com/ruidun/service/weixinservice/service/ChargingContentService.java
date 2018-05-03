package com.ruidun.service.weixinservice.service;

import com.ruidun.service.weixinservice.mapper.ChargingMapper;
import com.ruidun.service.weixinservice.model.ChargingContentModel;
import com.ruidun.service.weixinservice.model.ChargingModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class ChargingContentService {
    @Autowired
    ChargingMapper chargingMapper;

    public List<ChargingContentModel> selectCharging(String mark) {

        return chargingMapper.getchargingcontent(mark);
    }
}
