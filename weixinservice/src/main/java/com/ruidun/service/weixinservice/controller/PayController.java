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

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
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

    @RequestMapping(value = "/createOrder", method = {RequestMethod.POST})
    public Map<String, Object> createOrder(HttpServletRequest request, HttpServletResponse response, @RequestBody PayModel payModel) {
        Map<String, Object> result = new HashMap<>();
        try {
            if (payModel.getOpenId() == null && payModel.getOpenId().equals("")) {
                response.sendRedirect("http://www.shouyifenxi.com/charger/verify?deviceId=" + payModel.getDeviceId() + "&slotIndex=" + payModel.getSlotIndex());
                return result;
            }
            Map<String, String> payConfigDict = PayUtils.getWeixinPara();
            String notifyUrl = "http://www.shouyifenxi.com/charger/paycallback";
            logger.info("getopenid Report status.============== {}", payModel.getOpenId());
            String out_trade_no = OrderIdUtil.getorderId();
            Map<String, Object> resultMap = PayUtils.WechatPay(out_trade_no, payModel.getPayment(), "1KUAIQIAN4HOURS", payModel.getOpenId(), notifyUrl, payConfigDict);
            resultMap.put("out_trade_no", out_trade_no);
            if (null == resultMap) {
                result.put("status", 1);
                result.put("msg", "无法获取微信支付XML！");
                return result;
            }
            result.put("state", "0");
            result.put("msg", "获取prepayid成功");
            result.put("data", resultMap);
            logger.info("result Report status.============== {}", result);
            insertOrderService.insertorder(payModel.getOpenId(), "111", payModel.getPayment(), resultMap.get("timeStamp").toString(), -1, resultMap.get("prepay_id").toString(), payModel.getDeviceId(), payModel.getSlotIndex(), resultMap.get("out_trade_no").toString());
            return result;
        } catch (Exception o) {
            logger.error("ERROR:{}", o.getStackTrace().toString());
            o.printStackTrace();
            return result;
        }
    }
    /**
     * 给腾讯支付回调使用
     *
     * @param request
     * @param response
     */
    @RequestMapping(value = "/paycallback", method = {RequestMethod.POST})
    public String payCallback(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> params = null;
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
                    + payConfigDict.get("app_key"));
            return XMLParser.setXML("FAILED", "");
        }
        try {
            logger.info("Pay detail:{}", params.toString());
            logger.info("================================支付成功{}", params.get("out_trade_no").toString());
//            // 设置订单状态为支付成功
            updateOrderService.updateOrderModel(params.get("out_trade_no").toString());
            SelectOrderModel selectOrderModel = selectOrderService.selectOrderModel(params.get("out_trade_no").toString());
            Map<String, Object> resultMap=new HashMap<>();
            resultMap.put("accessToken","518eb9368cc423fce6160463ed157a0e");
            resultMap.put("commandType",3);
            resultMap.put("status","0");
            resultMap.put("deviceId",selectOrderModel.getDeviceId());
            resultMap.put("msg","START_SLOT");
            resultMap.put("seq",1000);
            Map<String, Object> paraMap=new HashMap<>();
            paraMap.put("slotId",selectOrderModel.getSlotIndex());
            paraMap.put("serviceSeconds",10);
            paraMap.put("currentMa",100);
            resultMap.put("data", paraMap);
            JSONObject paraJson = JSONObject.fromObject(resultMap);
            logger.info("requeatparams=={}",paraJson.toString());
            logger.info("getDeviceId=={}",selectOrderModel.getDeviceId());
            for (int i=0;i<5;i++){
                String jsonvalues = HttpClient.doPost("http://47.104.144.249:19002",paraJson.toString());
                logger.debug("Device Gateway return = {}", jsonvalues);
                JSONObject json = JSONObject.fromObject(jsonvalues);
                JSONObject innerJson = json.getJSONObject("data");
                int status = innerJson.getInt("deviceReturnCode");
                if (status == 0){
                    break;
                }
            }
        } catch (Exception e) {
            logger.warn(String.format("Failed to set order status, e = %s", e.getMessage()));
            return XMLParser.setXML("FAILED", "");
        }
        return XMLParser.setXML("SUCCESS", "OK");
    }

    @RequestMapping(value = "/testStartSlot", method = { RequestMethod.GET})
    public void getOpenIdAndRedirect(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String deviceId = request.getParameter("deviceId");
        String slotId = request.getParameter("slotId");
        RequestBobyModel requestBobyModel = new RequestBobyModel();
        Map<String, Object> resultMap=new HashMap<>();
        resultMap.put("accessToken","518eb9368cc423fce6160463ed157a0e");
        resultMap.put("commandType",3);
        resultMap.put("status","0");
        resultMap.put("deviceId",deviceId);
        resultMap.put("msg","START_SLOT");
        resultMap.put("seq",1000);
        Map<String, Object> paraMap=new HashMap<>();
        paraMap.put("slotId",slotId);
        paraMap.put("serviceSeconds",10);
        paraMap.put("currentMa",100);
        resultMap.put("data", paraMap);
        JSONObject paraJson = JSONObject.fromObject(resultMap);
        logger.info("requeatparams=={}",paraJson.toString());
        for (int i=0;i<5;i++){
            String jsonvalues = HttpClient.doPost("http://47.104.144.249:19002",paraJson.toString());
            logger.debug("Device Gateway return = {}", jsonvalues);
            JSONObject json=JSONObject.fromObject(jsonvalues);
            JSONObject datajson=json.getJSONObject("data");
            int deviceReturnCode=datajson.getInt("deviceReturnCode");
            if (deviceReturnCode==0){
                break;
            }
        }
    }
    /**
     * 给前端查询订单状态使用
     *
     * @param request
     * @param response
     */
    @RequestMapping(value = "/getPayStatus", method = {RequestMethod.POST})
    public Map<String, Object> getOrderStatus(HttpServletRequest request, HttpServletResponse response, @RequestBody PrepayModel prepayModel) {
        Map<String, Object> result = new HashMap<>();
        SelectOrderModel selectOrderModel = selectOrderService.selectOrderModel(prepayModel.getOut_trade_no());
        if (selectOrderModel.getStatus() == 0) {
            result.put("msg", "支付成功.");
            result.put("status", "0");
            return result;
        } else if (selectOrderModel.getStatus() == 1) {
            result.put("msg", "其他原因未支付!!");
            result.put("status", "1");
            return result;
        } else {
            result.put("msg", "未支付!!");
            result.put("status", "-1");
            return result;
        }
        }
    }




