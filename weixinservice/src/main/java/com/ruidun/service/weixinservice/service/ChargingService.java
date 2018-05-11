package com.ruidun.service.weixinservice.service;

import com.ruidun.service.weixinservice.mapper.ChargingMapper;
import com.ruidun.service.weixinservice.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class ChargingService {
    @Autowired
    ChargingMapper chargingMapper;
    //获取电桩信息
    public List<ChargingModel> selectCharging(String mark) {
        return chargingMapper.getcharging(mark);
    }
    //根据mark标识下的电桩信息
    public List<ChargingContentModel> selectContenCharging(String mark) {

        return chargingMapper.getchargingcontent(mark);
    }
    //获取地区的mark标识
    public List<MarkModel> selectMark() {
        return chargingMapper.getmarkById();
    }
    //根据mark标识获取常用电桩
    public List<CollectionChargingModel> selectcollectionCharging(String mark) {
        return chargingMapper.getcollectioncharging(mark);
    }
    //获取电桩的位置信息
    public List<LocationModel> selectlocation(String deviceId) {

        return chargingMapper.getLocation(deviceId);
    }
    //根据userid获取我正在使用的电桩信息
    public List<MyChargingModel> selectmycharging(String userid) {

        return chargingMapper.getmycharging(userid);
    }
    //获取附近电桩
    public List<NearChargingModel> selectNearChargingInfo() {

        return chargingMapper.getnearcharging();
    }
    //获取正在充电的插座的状态
    public NowChargingModel selectNowCharging(String deviceid,int slotId) {
        return chargingMapper.getnowcharging(deviceid,slotId);
    }
    //获取订单状态
    public List<RecordChargingModel> selectrecordcharging(String openId) {
        return chargingMapper.getrecordcharging(openId);
    }
    //搜索
    public List<SearchWordChargingModel> selectSearchWordCharging(String search) {
        return chargingMapper.getsearchcharging(search);
    }
    //获取插座信息
    public List<SlotChargingModel> selectslotCharging(String deviceid) {
        return chargingMapper.getslotcharging(deviceid);
    }
    //获取付款界面的信息
    public PayChargingModel selectpayCharging(String deviceid, int slotId) {
        return chargingMapper.getpaycharging(deviceid,slotId);
    }
}
