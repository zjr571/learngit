package com.ruidun.service.weixinservice.mapper;

import com.ruidun.service.weixinservice.model.*;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChargingMapper {
    @Select("SELECT * FROM charging_mark")
    List<MarkModel> getmarkById();
    @Select("SELECT SUM(charging_state.usedCount) FROM charging_state WHERE charging_state.locationId=#{locationId}")
    SumUsedCountModel getsumusedcount(int locatinId);
    @Select("SELECT SUM(charging_state.availableCount) FROM charging_state WHERE charging_state.locationId=#{locationId}")
    SumAvailableCountModel getsumavailablecount(int locatinId);
   @Select("SELECT charging_info.locationId,charging_info.location,charging_info.locationDetail,charging_info.lat,charging_info.lng,charging_state.usedCount,charging_state.availableCount FROM charging_info,charging_state WHERE charging_info.deviceId=charging_state.deviceId")
   List<NearChargingModel> getnearcharging();
    @Select("SELECT  charging_info.chargerIndex,charging_info.lat,charging_info.lng,charging_info.locationId,charging_info.deviceId,charging_info.location,charging_info.locationDetail,charging_state.usedCount,charging_state.availableCount FROM charging_info,charging_state WHERE charging_info.deviceId=charging_state.deviceId AND charging_info.locationId=#{locationId}")
    List<ChargingModel> getcharging(String locationId);
    @Select("SELECT charging_info.chargerIndex,charging_info.locationId,charging_info.deviceId,charging_info.location,charging_info.locationDetail,charging_state.usedCount,charging_state.availableCount FROM charging_info,charging_state WHERE charging_info.deviceId=charging_state.deviceId AND charging_info.locationId=#{locationId}")
    List<ChargingContentModel> getchargingcontent(String locationId);
    @Select("SELECT charging_info.locationId,charging_info.deviceId,charging_info.location,charging_info.locationDetail,charging_info.lat,charging_info.lng,charging_state.usedCount,charging_state.availableCount FROM charging_info,charging_state,user_charging WHERE charging_info.deviceId=charging_state.deviceId AND charging_state.deviceId=user_charging.deviceId AND user_charging.userid=#{userid}")
    List<CollectionChargingModel> getcollectioncharging(String locationId);
    @Select("SELECT chargingsock_state.slotIndex,chargingsock_state.slotStatus,chargingsock_state.time,charging_info.location,charging_info.locationDetail  FROM chargingsock_state,charging_info WHERE chargingsock_state.deviceid=charging_info.deviceId AND chargingsock_state.deviceid=#{deviceid}")
    List<SlotChargingModel> getslotcharging(String deviceid);
    @Select("SELECT chargingsock_state.slotIndex,chargingsock_state.slotStatus,chargingsock_state.time,charging_info.location,charging_info.locationDetail,charging_info.lat,charging_info.lng FROM chargingsock_state,charging_info WHERE chargingsock_state.deviceid=charging_info.deviceId AND chargingsock_state.deviceid=#{deviceid}")
    List<LocationModel> getLocation(String deviceid);
    @Select("SELECT chargingsock_state.slotIndex,chargingsock_state.deviceid,chargingsock_state.starttime,chargingsock_state.time,chargingsock_state.chargingTime,chargingsock_state.slotSN,charging_info.location,charging_info.locationDetail,charging_info.chargerIndex FROM chargingsock_state,charging_info WHERE chargingsock_state.deviceid=charging_info.deviceId AND chargingsock_state.deviceid=#{deviceid} AND chargingsock_state.slotIndex=#{slotId}")
    NowChargingModel getnowcharging(String deviceid,int slotId);
    @Select("SELECT charging_info.locationId,charging_info.location,charging_info.locationDetail,charging_info.lat,charging_info.lng,charging_info.city,charging_state.usedCount,charging_state.availableCount FROM charging_info,charging_state WHERE charging_info.deviceId=charging_state.deviceId AND charging_info.location LIKE '%${_parameter}%'")
    List<SearchWordChargingModel> getsearchcharging(String _parameter);
    @Select("SELECT chargingsock_state.slotIndex,chargingsock_state.deviceid,chargingsock_state.starttime,chargingsock_state.time,chargingsock_state.chargingTime,chargingsock_state.slotSN,charging_info.location,charging_info.locationDetail,charging_info.chargerIndex FROM chargingsock_state,charging_info,user_pay WHERE chargingsock_state.deviceid=charging_info.deviceId AND chargingsock_state.deviceid = user_pay.deviceId AND user_pay.slotId=chargingsock_state.slotId AND user_pay.userid=#{userid}")
    MyChargingModel getmycharging(String userid);
    @Select("SELECT chargingsock_state.deviceid,chargingsock_state.starttime,charging_info.location  FROM chargingsock_state,charging_info,user_pay WHERE chargingsock_state.deviceid=charging_info.deviceId AND chargingsock_state.deviceid = user_pay.deviceId AND user_pay.slotId=chargingsock_state.slotId AND user_pay.userid=#{userid}")
    List<RecordChargingModel> getrecordcharging(String userid);

    @Select("SELECT chargingsock_state.slotIndex,chargingsock_state.deviceid,chargingsock_state.slotSN,charging_info.location,charging_info.locationDetail,charging_info.chargerIndex FROM chargingsock_state,charging_info WHERE chargingsock_state.deviceid=charging_info.deviceId AND chargingsock_state.deviceid=#{deviceid} AND chargingsock_state.slotIndex=#{slotIndex}")
    PayChargingModel getpaycharging(String deviceid,int slotIndex);

    @Select("INSERT into charging_order (openId,package,payment,paytime,status,prepayId,deviceId,slotIndex)values(#{openId},#{package},#{payment},#{paytime},#{status},#{prepayId},#{deviceId},#{slotIndex})")
    InsertOrderModel insertorder(String openId,String packag,long payment,String paytime,int status,String prepayId,String deviceId,int slotIndex);
    @Select("UPDATE charging_order SET status = 0 WHERE prepayId = #{prepayId}")
    UpdateOrderModel updateorder (String prepayId);
    @Select("SELECT charging_order.status FROM charging_order WHERE charging_order.prepayId=#{prepayId}")
    SelectOrderModel selectorder(String prepayId);
}
