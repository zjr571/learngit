package com.ruidun.service.weixinservice.service;

import com.ruidun.service.weixinservice.mapper.ChargingMapper;
import com.ruidun.service.weixinservice.model.SlotChargingModel;
import com.ruidun.service.weixinservice.model.SumPaymentModel;
import com.ruidun.service.weixinservice.model.SumUsedCountModel;
import org.apache.ibatis.annotations.Select;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
@Service
public class SumUsedCountService {
    @Autowired
    ChargingMapper chargingMapper;

    public SumUsedCountModel sumUsedCountService(int locationId) {

        return chargingMapper.getsumusedcount(locationId);
    }

    public SumPaymentModel sumPaymentModel(String openId){
        return chargingMapper.getsumPaymentcount(openId);
    }
}
