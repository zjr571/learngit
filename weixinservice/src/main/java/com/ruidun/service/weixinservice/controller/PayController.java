package com.ruidun.service.weixinservice.controller;

import com.alibaba.fastjson.JSON;
import com.mysql.cj.core.util.LogUtils;
import com.ruidun.service.weixinservice.model.*;
import com.ruidun.service.weixinservice.service.*;
import  com.ruidun.service.weixinservice.utils.XMLParser;
import com.ruidun.service.weixinservice.utils.*;
import net.sf.json.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@RestController
@RequestMapping(value = "/charger")
public class PayController {
    private static final Logger logger = LoggerFactory.getLogger(ChargingController.class);
    @Autowired
    private PayService payService;
    @Autowired
    private SumService sumService;
    @Autowired
    private UserService userService;
    @Autowired
    private UpdateService updateService;



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
            String out_trade_no = OrderIdUtil.getOrderId();
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
            payService.insertorder(payModel.getOpenId(), "ruidun", payModel.getPayment(), resultMap.get("timeStamp").toString(), -1, resultMap.get("prepay_id").toString(), payModel.getDeviceId(), payModel.getSlotIndex(), resultMap.get("out_trade_no").toString());
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
            payService.updateOrderModel(1,params.get("out_trade_no").toString());
            return XMLParser.setXML("FAILED", "");
        }
        Map<String, String> payConfigDict = PayUtils.getWeixinPara();
        if (!WXPayUtils.checkIsSignValidFromResponse(params, payConfigDict.get("app_key"))) {
            logger.warn("RechargeWechatNotify error! 微信API返回的数据签名验证不通过，有可能被第三方篡改!!!KEY="
                    + payConfigDict.get("app_key"));
            payService.updateOrderModel(1,params.get("out_trade_no").toString());
            return XMLParser.setXML("FAILED", "");
        }

        try {
                    Date day = new Date();
                    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    logger.info("Pay detail:{}", params.toString());
                    logger.info("================================支付成功{}", params.get("out_trade_no").toString());
                    // 设置订单状态为支付成功
                    payService.updateOrderModel(0,params.get("out_trade_no").toString());
                    SelectOrderModel selectOrderModel = payService.selectOrderModel(params.get("out_trade_no").toString());
                    ChargingInfoModel chargingInfoModel=payService.getpaylocation(selectOrderModel.getDeviceId());
                    SumPaymentModel sumPaymentModel=sumService.sumPaymentModel(selectOrderModel.getOpenId());

                    String str1=selectOrderModel.getPayment()*4+"";
                    DecimalFormat decimalFormat=new DecimalFormat("00");
                    String time=decimalFormat.format(Integer.parseInt(str1)) + ":" + "00" + ":" + "00";
                    updateService.updateChargingsockstate(1,df.format(day),time,"00:00:00",selectOrderModel.getPayment()*4,selectOrderModel.getOpenId(),params.get("out_trade_no").toString(),selectOrderModel.getDeviceId(),selectOrderModel.getSlotIndex());
                    List<ChargingSockModel> chargingSockModelList = updateService.getchargingsockstate(selectOrderModel.getDeviceId());
                    int useCount = 0;
                    int availableCount = 0;
                    StringBuffer use_state = new StringBuffer();
                    for (ChargingSockModel chargingSockModel : chargingSockModelList){
                        if (chargingSockModel.getSlotStatus()==0){
                            availableCount = availableCount + 1;

                        }else {
                            useCount = useCount + 1;
                        }
                        use_state.append(chargingSockModel.getSlotStatus()+"");
                    }
                    updateService.updateChargingState(useCount,availableCount,use_state.toString(),selectOrderModel.getDeviceId());
                     CountModel countModel=sumService.countUserCharging(selectOrderModel.getOpenId(),selectOrderModel.getDeviceId());
                     if (countModel.getCount()==0){
                        userService.insertUserCollection(selectOrderModel.getOpenId(),selectOrderModel.getDeviceId());
                     }

                     ChargingControllerUtil chargingControllerUtil=new ChargingControllerUtil();
                     chargingControllerUtil.chargingController(selectOrderModel.getDeviceId(),selectOrderModel.getSlotIndex(),selectOrderModel.getPayment());
                     chargingControllerUtil.sendMessage(selectOrderModel.getOpenId(),selectOrderModel.getDeviceId(),chargingInfoModel.getLocation(),selectOrderModel.getPayment(),sumPaymentModel.getPayment(),df.format(day));


        } catch (Exception e) {
            logger.warn(String.format("Failed to set order status, e = %s", e.getMessage()));
           e.printStackTrace();
            return XMLParser.setXML("SUCCESS", "OK");
        }
        return XMLParser.setXML("SUCCESS", "OK");
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
        SelectOrderModel selectOrderModel = payService.selectOrderModel(prepayModel.getOut_trade_no());
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

        //控制电桩，发送充电提醒
    @RequestMapping(value = "/setOrderController", method = {RequestMethod.POST})
    public void setOrderController(HttpServletRequest request, HttpServletResponse response, @RequestBody PrepayModel prepayModel) {
        Map<String, Object> result = new HashMap<>();
        Date day = new Date();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SelectOrderModel selectOrderModel = payService.selectOrderModel(prepayModel.getOut_trade_no());
        ChargingInfoModel chargingInfoModel=payService.getpaylocation(selectOrderModel.getDeviceId());
        SumPaymentModel sumPaymentModel=sumService.sumPaymentModel(selectOrderModel.getOpenId());
        ChargingControllerUtil chargingControllerUtil=new ChargingControllerUtil();
        chargingControllerUtil.chargingController(selectOrderModel.getDeviceId(),selectOrderModel.getSlotIndex(),selectOrderModel.getPayment());
        chargingControllerUtil.sendMessage(selectOrderModel.getOpenId(),selectOrderModel.getDeviceId(),chargingInfoModel.getLocation(),selectOrderModel.getPayment(),sumPaymentModel.getPayment(),df.format(day));
    }
    @RequestMapping(value = "/wxPayRefund", method = { RequestMethod.POST })
    public String qxwxsign(HttpServletResponse response, HttpServletRequest request, @RequestBody RefundModel refundmodel) throws IOException {
        String param = WXPayUtils.wxPayRefund(refundmodel.getMerchantNumber(),String.valueOf(refundmodel.getTotalFee()*100));
        logger.info("wxPayRefund param == {}",param);
        String result = "";
        String url = "https://api.mch.weixin.qq.com/secapi/pay/refund";
        try {
            result = WXPayUtils.wxPayBack(url, param);
            logger.info("wxPayRefund result == {}",result);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("wxPayRefund error == {}",e.getMessage());
        }
        String tt = "2007072001201611180592733503";
        Pattern p = Pattern.compile("\\.*(\\w{" + tt.length() + "})\\.*");
        int st = result.indexOf("<refund_id>");
        String res = "";
        if (st >= 0) {
            int en = result.indexOf("</refund_id>");
            res = result.substring(st, en);
            Matcher m = p.matcher(res);
            if (m.find()) {
                res = m.group(1);
            }
            if (res.length() > 0) {
                result = "code:1,msg:退款成功";
                logger.info("wxPayRefund  == code:1,msg:退款成功");
            } else {
                logger.info("wxPayRefund  == code:-1,msg:退款失败");
                result = "code:-1,msg:退款失败";
            }
            response.getWriter().print(JSON.toJSON(result));
        }
        return null;
    }
    }




