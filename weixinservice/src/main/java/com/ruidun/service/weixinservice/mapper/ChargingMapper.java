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

    @Select("SELECT charging_city.city,charging_city.province FROM charging_city")
    List<CityModel> getCity();


    @Select("SELECT SUM(charging_order.payment) AS payment FROM charging_order WHERE charging_order.openId=#{openId} AND charging_order.status=0")
    SumPaymentModel getsumPaymentcount(String openId);

    @Select("SELECT A.locationId,A.location,A.locationDetail,A.lat,A.lng,B.usedCount,B.availableCount  FROM (SELECT  DISTINCT charging_info.locationId,charging_info.location,charging_info.locationDetail,charging_info.lat,charging_info.lng FROM charging_info) A  inner join (SELECT locationId, SUM(charging_state.usedCount) AS usedCount , SUM(charging_state.availableCount) AS availableCount  FROM charging_state GROUP BY locationId) B ON A.locationId=B.locationId LIMIT ${_parameter1},${_parameter2} ")
    List<NearChargingModel> getnearcharger(int _parameter1, int _parameter2);

    @Select("SELECT charging_info.location FROM charging_info WHERE charging_info.deviceId=#{deviceId}")
    ChargingInfoModel getpaylocation(String deviceId);

    @Select("SELECT A.location,A.locationDetail,A.lat,A.lng,B.usedCountTotal,B.availableCountTotal  FROM (SELECT  DISTINCT charging_info.locationId,charging_info.location,charging_info.locationDetail,charging_info.lat,charging_info.lng FROM charging_info) A inner join (SELECT locationId, SUM(charging_state.usedCount) AS usedCountTotal , SUM(charging_state.availableCount) AS availableCountTotal  FROM charging_state GROUP BY locationId) B ON A.locationId=B.locationId WHERE A.locationId=#{locationId}")
    ChargingModel getcharging(String locationId);

    @Select("SELECT charging_info.chargerIndex,charging_info.locationId,charging_info.deviceId,charging_info.location,charging_info.locationDetail,charging_state.usedCount,charging_state.availableCount FROM charging_info,charging_state WHERE charging_info.deviceId=charging_state.deviceId AND charging_info.locationId=#{locationId}")
    List<ChargingContentModel> getchargingcontent(String locationId);

    @Select("SELECT charging_info.locationId,charging_info.deviceId,charging_info.location,charging_info.locationDetail,charging_info.lat,charging_info.lng,charging_state.usedCount,charging_state.availableCount FROM charging_info,charging_state,user_charging WHERE charging_info.deviceId=charging_state.deviceId AND charging_state.deviceId=user_charging.deviceId AND user_charging.userid=#{userid}")
    List<CollectionChargingModel> getcollectioncharging(String locationId);

    @Select("SELECT chargingsock_state.slotIndex,chargingsock_state.slotStatus,chargingsock_state.time,charging_info.location,charging_info.locationDetail  FROM chargingsock_state,charging_info WHERE chargingsock_state.deviceId=charging_info.deviceId AND chargingsock_state.deviceId=#{deviceId}")
    List<SlotChargingModel> getslotcharging(String deviceId);

    @Select("SELECT chargingsock_state.slotIndex,chargingsock_state.slotStatus,chargingsock_state.time,charging_info.location,charging_info.locationDetail,charging_info.lat,charging_info.lng FROM chargingsock_state,charging_info WHERE chargingsock_state.deviceId=charging_info.deviceId AND chargingsock_state.deviceId=#{deviceId}")
    List<LocationModel> getLocation(String deviceId);

    @Select("SELECT chargingsock_state.slotIndex,chargingsock_state.deviceId,chargingsock_state.starttime,chargingsock_state.time,chargingsock_state.chargingTime,chargingsock_state.slotSN,chargingsock_state.totalTime,charging_info.location,charging_info.locationDetail,charging_info.chargerIndex FROM chargingsock_state,charging_info WHERE chargingsock_state.deviceId=charging_info.deviceId AND chargingsock_state.deviceId=#{deviceId} AND chargingsock_state.slotIndex=#{slotId}")
    NowChargingModel getnowcharging(String deviceId,int slotId);

    @Select("SELECT A.locationId,A.location,A.locationDetail,A.lat,A.lng,A.city,B.usedCount,B.availableCount FROM (SELECT  DISTINCT charging_info.locationId,charging_info.location,charging_info.locationDetail,charging_info.lat,charging_info.lng,charging_info.city FROM charging_info) A inner join (SELECT locationId, SUM(charging_state.usedCount) AS usedCount , SUM(charging_state.availableCount) AS availableCount  FROM charging_state GROUP BY locationId) B ON A.locationId=B.locationId WHERE A.location LIKE '%${_parameter}%'")
    List<SearchWordChargingModel> getsearchcharging(String _parameter);

    @Select("SELECT chargingsock_state.slotIndex,chargingsock_state.deviceId,chargingsock_state.starttime,chargingsock_state.time,chargingsock_state.chargingTime,chargingsock_state.slotSN,chargingsock_state.totalTime,charging_info.location,charging_info.locationDetail,charging_info.chargerIndex FROM chargingsock_state,charging_info WHERE chargingsock_state.deviceId=charging_info.deviceId AND chargingsock_state.openId=#{openId}")
    List <MyChargingModel> getmycharging(String userid);

    @Select("SELECT chargingsock_state.deviceId,charging_info.location,charging_info.chargerIndex,charging_order.status,charging_order.payment,charging_order.paytime,charging_order.slotIndex FROM chargingsock_state,charging_info,charging_order WHERE chargingsock_state.deviceId=charging_info.deviceId AND chargingsock_state.deviceId = charging_order.deviceId AND charging_order.slotIndex=chargingsock_state.slotIndex AND charging_order.openId=#{openId}")
    List<RecordChargingModel> getrecordcharging(String openId);

    @Select("SELECT chargingsock_state.slotIndex,chargingsock_state.deviceId,chargingsock_state.slotSN,charging_info.location,charging_info.locationDetail,charging_info.chargerIndex FROM chargingsock_state,charging_info WHERE chargingsock_state.deviceId=charging_info.deviceId AND chargingsock_state.deviceId=#{deviceId} AND chargingsock_state.slotIndex=#{slotIndex}")
    PayChargingModel getpaycharging(String deviceId,int slotIndex);

    @Select("INSERT into charging_order (openId,package,payment,paytime,status,prepayId,deviceId,slotIndex,out_trade_no) values (#{openId},#{package},#{payment},#{paytime},#{status},#{prepayId},#{deviceId},#{slotIndex},#{out_trade_no})")
    InsertOrderModel insertorder(@Param("openId") String openId,@Param("package") String packag,@Param("payment") long payment,@Param("paytime") String paytime,@Param("status") int status,@Param("prepayId") String prepayId,@Param("deviceId") String deviceId,@Param("slotIndex") int slotIndex,@Param("out_trade_no") String out_trade_no);

    @Select("UPDATE charging_order SET status = #{status} WHERE out_trade_no = #{out_trade_no}")
    UpdateOrderModel updateorder (@Param("status") int status,@Param("out_trade_no") String out_trade_no);

    @Select("SELECT charging_order.openId,charging_order.status,charging_order.deviceId,charging_order.slotIndex,charging_order.payment,charging_order.paytime FROM charging_order WHERE charging_order.out_trade_no=#{out_trade_no}")
    SelectOrderModel selectorder(String out_trade_no);

    @Select("INSERT into user_charging (userid,deviceId) values (#{userid},#{deviceId})")
    void insertUserCollection(@Param("userid") String userid,@Param("deviceId") String deviceId);

    @Select("SELECT count(1) AS count from user_charging WHERE user_charging.userid=#{userid} AND user_charging.deviceId=#{deviceId}")
    CountModel countUserCharging(@Param("userid") String userid,@Param("deviceId") String deviceId);

    @Select("UPDATE charging_state SET usedCount = #{usedCount},availableCount=#{availableCount},use_state=#{use_state} WHERE deviceId = #{deviceId}")
    void updateChargingState (@Param("usedCount") int usedCount,@Param("availableCount") int availableCount,@Param("use_state") String use_state,@Param("deviceId") String deviceId);

    @Select("UPDATE chargingsock_state SET slotStatus = #{slotStatus},starttime=#{starttime},time=#{time},chargingTime=#{chargingTime},totalTime=#{totalTime},openId=#{openId},out_trade_no=#{out_trade_no} WHERE deviceId = #{deviceId} AND slotIndex=#{slotIndex}")
    void updateChargingsockstate (@Param("slotStatus") int slotStatus,@Param("starttime") String starttime,@Param("time") String time,@Param("chargingTime") String chargingTime,@Param("totalTime") int totalTime,@Param("openId") String openId,@Param("out_trade_no") String out_trade_no,@Param("deviceId") String deviceId,@Param("slotIndex") int slotIndex);

    @Select("SELECT charging_order.status from charging_order where charging_order.out_trade_no=#{out_trade_no}")
    int getchargingorderstatus(String out_trade_no);

    @Select("SELECT chargingsock_state.deviceId,chargingsock_state.slotIndex,chargingsock_state.slotStatus,chargingsock_state.starttime,chargingsock_state.totalTime,chargingsock_state.openId,chargingsock_state.out_trade_no FROM chargingsock_state WHERE chargingsock_state.deviceId=#{deviceId}")
    List<ChargingSockModel> getchargingsockstate(String deviceId);

    @Select("SELECT chargingsock_state.deviceId,chargingsock_state.slotIndex,chargingsock_state.slotStatus,chargingsock_state.starttime,chargingsock_state.totalTime,chargingsock_state.openId,chargingsock_state.out_trade_no FROM chargingsock_state WHERE chargingsock_state.deviceId=#{deviceId} AND chargingsock_state.slotIndex=#{slotIndex}")
    ChargingSockModel getchargingsockstateone(String deviceId,int slotIndex);

    @Select("UPDATE chargingsock_state SET starttime=#{starttime},time=#{time},chargingTime=#{chargingTime} WHERE deviceId = #{deviceId} AND slotIndex=#{slotIndex}")
    void updateChargingsockTime (@Param("starttime") String starttime,@Param("time") String time,@Param("chargingTime") String chargingTime,@Param("deviceId") String deviceId,@Param("slotIndex") int slotIndex);

}
