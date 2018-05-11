package com.ruidun.service.weixinservice.controller;







import com.mysql.cj.xdevapi.JsonArray;
import com.ruidun.service.weixinservice.Bean.JsonResult;
import com.ruidun.service.weixinservice.model.ChargingSockModel;
import com.ruidun.service.weixinservice.model.LocationModel;
import com.ruidun.service.weixinservice.service.ChargingService;
import com.ruidun.service.weixinservice.service.UpdateService;
import com.ruidun.service.weixinservice.service.UserService;

import com.ruidun.service.weixinservice.utils.AccessTokenUtil;
import com.ruidun.service.weixinservice.utils.HttpClient;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;


@RestController
public class ReceiveController {

    @Autowired
    private UpdateService updateService;
    @Autowired
    private ChargingService chargingService;

    private static final Logger logger = LoggerFactory.getLogger(ChargingController.class);
    @RequestMapping(value = "/status", method = {RequestMethod.POST})
    public void status (HttpServletRequest request) {
        String acceptjson = "";
        try {
            logger.info("chargingStatus");
            BufferedReader br;
            br = new BufferedReader(new InputStreamReader( (ServletInputStream) request.getInputStream(), "utf-8"));
            StringBuffer sb = new StringBuffer("");
            String temp;
            while ((temp = br.readLine()) != null) {
                sb.append(temp);
            }
            br.close();
            acceptjson = sb.toString();
            logger.info("chargingStatus:{}",acceptjson);
            //{"status":0,"commandType":5,"seq":11198,"deviceId":"2112018020700151","data":{"statusMap":{"11":0,"12":0,"13":0,"14":0,"15":0,"16":0,"1":0,"2":0,"3":0,"4":0,"5":0,"6":0,"7":0,"8":0,"9":0,"10":0},"statusBitFlag":0,"slotCount":16,"historyChargeValue":0},"msg":"状态上报"}
            //{"status":0,"commandType":6,"deviceId":"2112018020700151","data":[{"month":5,"hour":19,"year":2018,"slotId":2,"payment":0,"eventType":97,"chargeValue":0,"day":8,"minute":34,"second":16}],"msg":"事件上报","seq":11143}
            JSONObject json = JSONObject.fromObject(acceptjson);
            String deviceId=json.getString("deviceId");
            int commandType=json.getInt("commandType");
            if (commandType == 5){
                List<ChargingSockModel> chargingSockModelList = updateService.getchargingsockstate(deviceId);
                for (ChargingSockModel chargingSockModel : chargingSockModelList){
                    if (chargingSockModel.getSlotStatus()==1){
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                        Long beginUseTime = sdf.parse(chargingSockModel.getStarttime()).getTime();
                        logger.info("beginUseTime =={}",beginUseTime);
                        Date day=new Date();
                        String time = sdf.format(day);
                        Long nowUseTime = sdf.parse(time).getTime();
                        logger.info("nowUseTime =={}",nowUseTime);
                        DecimalFormat decimalFormat=new DecimalFormat("00");
                        long timeInterval = (nowUseTime - beginUseTime)/1000;
                        long hour=timeInterval%(24*3600)/3600;
                        long minute=timeInterval%3600/60;
                        long second=timeInterval%60;

                        long totalSenond = Integer.valueOf(chargingSockModel.getTotalTime())*60*60;
                        long i = totalSenond - timeInterval;
                        long hour1=i%(24*3600)/3600;
                        long minute1=i%3600/60;
                        long second1=i%60;

                        if (i>=0){
                            updateService.updateChargingsockTime(chargingSockModel.getStarttime(),decimalFormat.format(hour1)+":"+decimalFormat.format(minute1)+":"+decimalFormat.format(second1),decimalFormat.format(hour)+":"+decimalFormat.format(minute)+":"+decimalFormat.format(second),chargingSockModel.getDeviceId(),chargingSockModel.getSlotIndex());
                            logger.info("updateChargingsockTime update success");
                        }
                    }
                }
            }else if (commandType == 6){
                JSONArray datajson = json.getJSONArray("data");
                JSONObject daJson = datajson.getJSONObject(0);
                logger.info("deviceIdjson = {}",datajson.toString());
                int eventType = daJson.getInt("eventType");
                logger.info("eventType = {}",eventType+"");
                if (eventType == 97){

                }else {
                    int slotId = daJson.getInt("slotId");
                    logger.info("slotId == {}",slotId);
                    logger.info("deviceId == {}",deviceId);
                    ChargingSockModel chargingSockstateModel = updateService.getchargingsockstateone(deviceId,slotId);
                    List<LocationModel> locationModelList =chargingService.selectlocation(deviceId);
                    updateService.updateChargingsockstate(0,"","","",0,"","",deviceId,slotId);
                    List<ChargingSockModel> chargingSockModelList = updateService.getchargingsockstate(deviceId);
                    int useCount = 0;
                    int availableCount = 0;
                    StringBuffer use_state = new StringBuffer();
                    for (ChargingSockModel chargingSockModel : chargingSockModelList){
                        if (chargingSockModel.getSlotStatus()==0){
                            availableCount = availableCount + 1;
                        }else {
                            useCount = useCount + 1;
                        }
                        use_state.append(chargingSockModel.getSlotStatus());
                    }
                    updateService.updateChargingState(useCount,availableCount,use_state.toString(),deviceId);
                    logger.info("updateService update success");
                    if (eventType == 99){

                    }else if (eventType == 101){
                        Map<String, Object> templateMap=new HashMap<>();
                        templateMap.put("touser",chargingSockstateModel.getOpenId());
                        templateMap.put("template_id","uutDFqv6tZbb4mG2UQ9eyjkOYhlWRcw_N3awzzfUkTM");
                        Map<String, Object> dataMap=new HashMap<>();
                        Map<String, Object> firstMap=new HashMap<>();
                        Map<String, Object> keyword1Map=new HashMap<>();
                        Map<String, Object> keyword2Map=new HashMap<>();
                        Map<String, Object> keyword3Map=new HashMap<>();
                        Map<String, Object> keyword4Map=new HashMap<>();
                        Map<String, Object> keyword5Map=new HashMap<>();
                        Map<String, Object> remarkMap=new HashMap<>();
                        firstMap.put("value","断电提醒");
                        firstMap.put("color","#173177");
                        dataMap.put("first",firstMap);
                        keyword1Map.put("value",deviceId);
                        keyword1Map.put("color","#173177");
                        dataMap.put("keyword1",keyword1Map);
                        keyword2Map.put("value",locationModelList.get(0).getLocation());
                        keyword2Map.put("color","#173177");
                        dataMap.put("keyword2",keyword2Map);
                        keyword3Map.put("value",chargingSockstateModel.getTotalTime());
                        keyword3Map.put("color","#173177");
                        dataMap.put("keyword3",keyword3Map);
                        keyword4Map.put("value",chargingSockstateModel.getTotalTime());
                        keyword4Map.put("color","#173177");
                        Date day = new Date();
                        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                        dataMap.put("keyword4",keyword4Map);
                        keyword5Map.put("value",df.format(day));
                        keyword5Map.put("color","#173177");
                        dataMap.put("keyword5",keyword5Map);
                        remarkMap.put("value","感谢您使用【小太阳】充电桩！");
                        remarkMap.put("color","#173177");
                        dataMap.put("remark",remarkMap);
                        templateMap.put("data",dataMap);
                        JSONObject templateJson = JSONObject.fromObject(templateMap);
                        Map map=  AccessTokenUtil.getInstance().getAccessTokenAndJsapiTicket();
                        String templatejsonvalues = HttpClient.doPost("https://api.weixin.qq.com/cgi-bin/message/template/send?access_token="+map.get("access_token"),templateJson.toString());
                        JSONObject resulttemplatejson = JSONObject.fromObject(templatejsonvalues);
                        if (resulttemplatejson.getInt("errcode")==0){
                            logger.info("template=={}","创建模板成功!!!");
                        }else {
                            logger.error("error template=={}",resulttemplatejson.getString("errmsg"));
                        }

                        Map<String, Object> payRefundMap=new HashMap<>();
                        payRefundMap.put("merchantNumber",chargingSockstateModel.getOut_trade_no());
                        payRefundMap.put("totalFee",Integer.valueOf(chargingSockstateModel.getTotalTime())/4+"");
                        JSONObject dataJson = JSONObject.fromObject(payRefundMap);
                        HttpClient.doPost("http://www.shouyifenxi.com/charger/wxPayRefund",dataJson.toString());
                    }else if (eventType == 102){

                    }
                }
            }

        }catch (Exception o){
            logger.error("updateService error = {}",o.getMessage());
            o.printStackTrace();
        }
    }

}
