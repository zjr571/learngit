package com.ruidun.service.weixinservice.controller;

import com.ruidun.service.weixinservice.model.*;
import com.ruidun.service.weixinservice.service.InsertOrderService;
import com.ruidun.service.weixinservice.service.SelectOrderService;
import com.ruidun.service.weixinservice.service.UpdateOrderService;
import  com.ruidun.service.weixinservice.utils.XMLParser;
import com.ruidun.service.weixinservice.service.PayChargingService;
import com.ruidun.service.weixinservice.utils.*;
import javafx.beans.binding.ObjectExpression;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping(value = "/charger")
public class PayController {
    private static final Logger logger = LoggerFactory.getLogger(ChargingController.class);
    @Autowired
    private InsertOrderService insertOrderService;
    @Autowired
    private UpdateOrderService updateOrderService;
    @Autowired
    private SelectOrderService selectOrderService;

    @RequestMapping(value = "/createOrder", method = { RequestMethod.POST})
    public Map<String, Object> createOrder(HttpServletRequest request, HttpServletResponse response, @RequestBody PayModel payModel) {
        Map<String, Object> result = new HashMap<>();
        Map<String, String> payConfigDict = PayUtils.getWeixinPara();
        String notifyUrl = "http://www.shouyifenxi.com/charger/paycallback";
        Map<String, Object> resultMap = PayUtils.WechatPay(OrderIdUtil.getorderId(), payModel.getPayment(), "1KUAIQIAN4HOURS",payModel.getOpenId() , notifyUrl, payConfigDict);
        if (null == resultMap) {
            result.put("status", 1);
            result.put("msg", "无法获取微信支付XML！");
            return result;
        }
        result.put("data", resultMap);
        Date day=new Date();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        //insertOrderService.insertorder(payModel.getOpenId(),"111",payModel.getPayment(),df.format(day).toString(),-1, result.get("prepay_id").toString(),payModel.getDeviceId(),payModel.getSlotIndex());
        return result;
    }

    /**
     * 给腾讯支付回调使用
     * @param request
     * @param response
     */
    @RequestMapping(value = "/paycallback", method = { RequestMethod.POST})
    public String payCallback(HttpServletRequest request, HttpServletResponse response) {
        Map<String,Object> params = null;
        try {
            BufferedReader rd = new BufferedReader(new InputStreamReader(request.getInputStream(), "UTF-8"));
            String line = null;
            final StringBuffer buffer = new StringBuffer(2048);
            while ((line = rd.readLine()) != null) {
                buffer.append(line);
            }
            String data = buffer.toString();
            logger.warn("data = " + data);
            params = XMLParser.getMapFromXML(data);
        } catch (Exception e) {
            logger.warn(String.format("RechargeWechatNotify Error! error = %s, request_body = %s", e));
        }

        if (!params.get("result_code").toString().equalsIgnoreCase("SUCCESS")) {
            logger.warn(String.format("RechargeWechatNotify error! 微信API返回错误信息：%s", params.get("result_msg").toString()));
            return XMLParser.setXML("FAILED", "");
        }


        Map<String, String> payConfigDict = PayUtils.getWeixinPara();
        if (!WXPayUtils.checkIsSignValidFromResponse(params, payConfigDict.get("app_key"))) {
            logger.warn("RechargeWechatNotify error! 微信API返回的数据签名验证不通过，有可能被第三方篡改!!!KEY="
                    +payConfigDict.get("app_key"));
            return XMLParser.setXML("FAILED", "");
        }

        try {
//            // 设置订单状态为支付成功
//            updateOrderService.updateOrderModel(params.get("prepay_id").toString());
//            SelectOrderModel selectOrderModel = selectOrderService.selectOrderModel(params.get("prepay_id").toString());
//            RequestBobyModel requestBobyModel = new RequestBobyModel();
//            requestBobyModel.status("518eb9368cc423fce6160463ed157a0e",3,"0",selectOrderModel.getDeviceId(),"START_SLOT",1000);
//            RequestSonBodyModel requestSonBodyModel = new RequestSonBodyModel();
//            requestSonBodyModel.setSlotIndex(selectOrderModel.getSlotIndex());
//            requestSonBodyModel.setCurrentMa(100);
//            requestSonBodyModel.setServiceSeconds( Integer.parseInt(String.valueOf(selectOrderModel.getPayment()*144)));
//            requestBobyModel.setData(requestSonBodyModel);
//            String param = ResponseEntity.ok(requestBobyModel).toString();
//            for (int i=0;i<5;i++){
//                String jsonvalues = HttpClient.doPost("http://47.104.144.249:19002",param);
//                JSONObject json=JSONObject.fromObject(jsonvalues);
//                JSONObject datajson=json.getJSONObject("data");
//                int deviceReturnCode=datajson.getInt("deviceReturnCode");
//                if (deviceReturnCode==0){
//                        break;
//                    }
//                }
        } catch (Exception e){
            logger.warn(String.format("Failed to set order status, e = %s", e.getMessage()));
            return XMLParser.setXML("FAILED", "");
        }
        return XMLParser.setXML("SUCCESS", "OK");
    }

    /**
     * 给前端查询订单状态使用
     * @param request
     * @param response
     */
    @RequestMapping(value = "/getPayStatus", method = { RequestMethod.POST})
    public Map<String, Object> getOrderStatus(HttpServletRequest request, HttpServletResponse response,@RequestBody String prepayId) {
        Map<String, Object> result = new HashMap<>();
//        SelectOrderModel selectOrderModel = selectOrderService.selectOrderModel(prepayId);
//        if (selectOrderModel.getStatus() == 0) {
//            result.put("status", "0");
//            result.put("msg", "支付成功");
//            return result;
//        } else {
//            result.put("status", "-1");
//            result.put("msg", "支付失败");
//            return result;
//        }
            return result;
   }
}
