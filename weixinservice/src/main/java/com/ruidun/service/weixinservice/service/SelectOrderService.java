package com.ruidun.service.weixinservice.service;

import com.ruidun.service.weixinservice.mapper.ChargingMapper;
import com.ruidun.service.weixinservice.model.SelectOrderModel;
import com.ruidun.service.weixinservice.model.SlotChargingModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SelectOrderService {
    @Autowired
    ChargingMapper chargingMapper;


    public SelectOrderModel selectOrderModel(String out_trade_no) {
        return chargingMapper.selectorder(out_trade_no);
    }
}
