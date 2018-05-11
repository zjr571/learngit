package com.ruidun.service.weixinservice.service;

import com.ruidun.service.weixinservice.mapper.ChargingMapper;
import com.ruidun.service.weixinservice.model.ChargingSockModel;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UpdateService {
    @Autowired
    ChargingMapper chargingMapper;

    public void updateChargingState(int usedCount,int availableCount, String use_state, String deviceId) {

        chargingMapper.updateChargingState(usedCount,availableCount,use_state,deviceId);
    }

    public void updateChargingsockstate(int slotStatus,String starttime,String time,String chargingTime,int totalTime,String openId,String out_trade_no,String deviceId,int slotIndex) {

        chargingMapper.updateChargingsockstate(slotStatus,starttime,time,chargingTime,totalTime,openId,out_trade_no,deviceId,slotIndex);
    }

    public List<ChargingSockModel> getchargingsockstate(String deviceId) {

       return chargingMapper.getchargingsockstate(deviceId);
    }
    public void updateChargingsockTime(String starttime,String time,String chargingTime,String deviceId,int slotIndex) {

         chargingMapper.updateChargingsockTime(starttime,time,chargingTime,deviceId,slotIndex);
    }
    public ChargingSockModel getchargingsockstateone(String deviceId,int slotIndex) {

       return chargingMapper.getchargingsockstateone(deviceId,slotIndex);
    }

}
