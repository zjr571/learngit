package com.ruidun.service.weixinservice.service;

import com.ruidun.service.weixinservice.mapper.ChargingMapper;
import com.ruidun.service.weixinservice.model.SlotChargingModel;
import com.ruidun.service.weixinservice.model.UpdateOrderModel;
import org.apache.ibatis.annotations.Select;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class UpdateOrderService {
    @Autowired
    ChargingMapper chargingMapper;

    public UpdateOrderModel updateOrderModel(int status,String out_trade_no) {
        return chargingMapper.updateorder(status,out_trade_no);
    }
}
