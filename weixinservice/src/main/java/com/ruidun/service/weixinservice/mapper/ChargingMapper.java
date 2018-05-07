package com.ruidun.service.weixinservice.mapper;

import com.ruidun.service.weixinservice.model.*;
import org.apache.ibatis.annotations.Param;
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

    @Select("SELECT SUM(charging_order.payment) AS payment FROM charging_order WHERE charging_order.openId=#{openId} AND charging_order.status=0")
    SumPaymentModel getsumPaymentcount(String openId);

    @Select("SELECT charging_info.locationId,charging_info.location,charging_info.locationDetail,charging_info.lat,charging_info.lng,charging_state.usedCount,charging_state.availableCount FROM charging_info,charging_state WHERE charging_info.deviceId=charging_state.deviceId")
    List<NearChargingModel> getnearcharging();

    @Select("SELECT charging_info.location FROM charging_info WHERE charging_info.deviceId=#{deviceId}")
    ChargingInfoModel getpaylocation(String deviceId);

    @Select("SELECT  charging_info.chargerIndex,charging_info.lat,charging_info.lng,charging_info.locationId,charging_info.deviceId,charging_info.location,charging_info.locationDetail,charging_state.usedCount,charging_state.availableCount FROM charging_info,charging_state WHERE charging_info.deviceId=charging_state.deviceId AND charging_info.locationId=#{locationId}")
    List<ChargingModel> getcharging(String locationId);

    @Select("SELECT charging_info.chargerIndex,charging_info.locationId,charging_info.deviceId,charging_info.location,charging_info.locationDetail,charging_state.usedCount,charging_state.availableCount FROM charging_info,charging_state WHERE charging_info.deviceId=charging_state.deviceId AND charging_info.locationId=#{locationId}")
    List<ChargingContentModel> getchargingcontent(String locationId);

    @Select("SELECT charging_info.locationId,charging_info.deviceId,charging_info.location,charging_info.locationDetail,charging_info.lat,charging_info.lng,charging_state.usedCount,charging_state.availableCount FROM charging_info,charging_state,user_charging WHERE charging_info.deviceId=charging_state.deviceId AND charging_state.deviceId=user_charging.deviceId AND user_charging.userid=#{userid}")
    List<CollectionChargingModel> getcollectioncharging(String locationId);

    @Select("SELECT chargingsock_state.slotIndex,chargingsock_state.slotStatus,chargingsock_state.time,charging_info.location,charging_info.locationDetail  FROM chargingsock_state,charging_info WHERE chargingsock_state.deviceId=charging_info.deviceId AND chargingsock_state.deviceId=#{deviceId}")
    List<SlotChargingModel> getslotcharging(String deviceId);

    @Select("SELECT chargingsock_state.slotIndex,chargingsock_state.slotStatus,chargingsock_state.time,charging_info.location,charging_info.locationDetail,charging_info.lat,charging_info.lng FROM chargingsock_state,charging_info WHERE chargingsock_state.deviceId=charging_info.deviceId AND chargingsock_state.deviceId=#{deviceId}")
    List<LocationModel> getLocation(String deviceId);

    @Select("SELECT chargingsock_state.slotIndex,chargingsock_state.deviceId,chargingsock_state.starttime,chargingsock_state.time,chargingsock_state.chargingTime,chargingsock_state.slotSN,charging_info.location,charging_info.locationDetail,charging_info.chargerIndex FROM chargingsock_state,charging_info WHERE chargingsock_state.deviceId=charging_info.deviceId AND chargingsock_state.deviceId=#{deviceId} AND chargingsock_state.slotIndex=#{slotId}")
    NowChargingModel getnowcharging(String deviceId,int slotId);

    @Select("SELECT charging_info.locationId,charging_info.location,charging_info.locationDetail,charging_info.lat,charging_info.lng,charging_info.city,charging_state.usedCount,charging_state.availableCount FROM charging_info,charging_state WHERE charging_info.deviceId=charging_state.deviceId AND charging_info.location LIKE '%${_parameter}%'")
    List<SearchWordChargingModel> getsearchcharging(String _parameter);

    @Select("SELECT chargingsock_state.slotIndex,chargingsock_state.deviceId,chargingsock_state.starttime,chargingsock_state.time,chargingsock_state.chargingTime,chargingsock_state.slotSN,charging_info.location,charging_info.locationDetail,charging_info.chargerIndex FROM chargingsock_state,charging_info,user_pay WHERE chargingsock_state.deviceId=charging_info.deviceId AND chargingsock_state.deviceId = user_pay.deviceId AND user_pay.slotIndex=chargingsock_state.slotIndex AND user_pay.userid=#{userid}")
    MyChargingModel getmycharging(String userid);

    @Select("SELECT chargingsock_state.deviceId,chargingsock_state.starttime,charging_info.location  FROM chargingsock_state,charging_info,user_pay WHERE chargingsock_state.deviceId=charging_info.deviceId AND chargingsock_state.deviceId = user_pay.deviceId AND user_pay.slotIndex=chargingsock_state.slotIndex AND user_pay.userid=#{userid}")
    List<RecordChargingModel> getrecordcharging(String userid);

    @Select("SELECT chargingsock_state.slotIndex,chargingsock_state.deviceId,chargingsock_state.slotSN,charging_info.location,charging_info.locationDetail,charging_info.chargerIndex FROM chargingsock_state,charging_info WHERE chargingsock_state.deviceId=charging_info.deviceId AND chargingsock_state.deviceId=#{deviceId} AND chargingsock_state.slotIndex=#{slotIndex}")
    PayChargingModel getpaycharging(String deviceId,int slotIndex);

    @Select("INSERT into charging_order (openId,package,payment,paytime,status,prepayId,deviceId,slotIndex,out_trade_no) values (#{openId},#{package},#{payment},#{paytime},#{status},#{prepayId},#{deviceId},#{slotIndex},#{out_trade_no})")
    InsertOrderModel insertorder(@Param("openId") String openId,@Param("package") String packag,@Param("payment") long payment,@Param("paytime") String paytime,@Param("status") int status,@Param("prepayId") String prepayId,@Param("deviceId") String deviceId,@Param("slotIndex") int slotIndex,@Param("out_trade_no") String out_trade_no);

    @Select("UPDATE charging_order SET status = #{status} WHERE out_trade_no = #{out_trade_no}")
    UpdateOrderModel updateorder (@Param("status") int status,@Param("out_trade_no") String out_trade_no);

    @Select("SELECT charging_order.openId,charging_order.status,charging_order.deviceId,charging_order.slotIndex,charging_order.payment,charging_order.paytime FROM charging_order WHERE charging_order.out_trade_no=#{out_trade_no}")
    SelectOrderModel selectorder(String out_trade_no);


}
