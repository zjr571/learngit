package com.ruidun.service.weixinservice.utils;

import com.ruidun.service.weixinservice.controller.ChargingController;
import com.ruidun.service.weixinservice.model.CountModel;
import com.ruidun.service.weixinservice.model.WeiXinConstants;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

public class ChargingControllerUtil {

    private static final Logger logger = LoggerFactory.getLogger(ChargingControllerUtil.class);

    public void chargingController (String deciceId,int slotIndex,int payment){
        Map<String, Object> resultMap=new HashMap<>();
        resultMap.put("accessToken","518eb9368cc423fce6160463ed157a0e");
        resultMap.put("commandType",3);
        resultMap.put("status","0");
        resultMap.put("deviceId",deciceId);
        resultMap.put("msg","START_SLOT");
        resultMap.put("seq",1000);
        Map<String, Object> paraMap=new HashMap<>();
        paraMap.put("slotId",slotIndex);
        paraMap.put("serviceSeconds",36000);
        paraMap.put("currentMa",1000);
        resultMap.put("data", paraMap);
        JSONObject paraJson = JSONObject.fromObject(resultMap);
        logger.info("requeatparams=={}",paraJson.toString());
        logger.info("getDeviceId=={}",deciceId);
        for (int i=0;i<5;i++){
            String jsonvalues = HttpClient.doPost("http://47.104.144.249:19002",paraJson.toString());
            logger.debug("Device Gateway return");
            logger.debug("Device Gateway return = {}", jsonvalues);
            JSONObject json = JSONObject.fromObject(jsonvalues);
            String status=json.getString("status");
            if (status.equals("0")){
                break;
            }
        }
    }

    public void sendMessage(String values, String openId,String deviceId,int slotIndex,String location,int payment,int totalPayment,String time){
        Map<String, Object> templateMap=new HashMap<>();
        templateMap.put("touser",openId);
        templateMap.put("template_id", WeiXinConstants.TEMPLATE_ID);
        templateMap.put("url",WeiXinConstants.REQUEST_URL+"dist/page/progress.html?deviceId="+deviceId+"&slotIndex="+slotIndex);
        Map<String, Object> dataMap=new HashMap<>();
        Map<String, Object> firstMap=new HashMap<>();
        Map<String, Object> keyword1Map=new HashMap<>();
        Map<String, Object> keyword2Map=new HashMap<>();
        Map<String, Object> keyword3Map=new HashMap<>();
        Map<String, Object> keyword4Map=new HashMap<>();
        Map<String, Object> keyword5Map=new HashMap<>();
        Map<String, Object> remarkMap=new HashMap<>();
        firstMap.put("value",values);
        firstMap.put("color","#FF0000");
        dataMap.put("first",firstMap);
        keyword1Map.put("value",deviceId);
        keyword1Map.put("color","#173177");
        dataMap.put("keyword1",keyword1Map);
        keyword2Map.put("value",location);
        keyword2Map.put("color","#173177");
        dataMap.put("keyword2",keyword2Map);
        keyword3Map.put("value",payment*4+"小时");
        keyword3Map.put("color","#173177");
        dataMap.put("keyword3",keyword3Map);
        keyword4Map.put("value",totalPayment*4+"小时");
        keyword4Map.put("color","#173177");
        dataMap.put("keyword4",keyword4Map);
        keyword5Map.put("value",time);
        keyword5Map.put("color","#173177");
        dataMap.put("keyword5",keyword5Map);
        remarkMap.put("value","感谢您使用【小太阳】充电桩！");
        remarkMap.put("color","#FF0000");
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
    }
}
