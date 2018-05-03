package com.ruidun.service.weixinservice.utils;





import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;

import java.net.*;
import java.util.*;

/**
 * Created by chunxu_wu on 2015/9/11.
 */

@Service("payUtils")
public class PayUtils {
    //@Autowired
    //private ThirdPartyProperties thirdPartyProperties;

    private static final Logger LOGGER = LoggerFactory.getLogger(PayUtils.class);
    /**
     * 获取本机Ip
     * <p/>
     * 通过 获取系统所有的networkInterface网络接口 然后遍历 每个网络下的InterfaceAddress组。
     * 获得符合 <code>InetAddress instanceof Inet4Address</code> 条件的一个IpV4地址
     *
     * @return
     */
    @SuppressWarnings("rawtypes")
    public static String localIp() {
        String ip = null;
        Enumeration allNetInterfaces;
        try {
            allNetInterfaces = NetworkInterface.getNetworkInterfaces();
            while (allNetInterfaces.hasMoreElements()) {
                NetworkInterface netInterface = (NetworkInterface) allNetInterfaces.nextElement();
                List<InterfaceAddress> InterfaceAddress = netInterface.getInterfaceAddresses();
                for (InterfaceAddress add : InterfaceAddress) {
                    InetAddress Ip = add.getAddress();
                    if (Ip != null && Ip instanceof Inet4Address) {
                        ip = Ip.getHostAddress();
                    }
                }
            }
        } catch (SocketException e) {
            // TODO Auto-generated catch block

        }
        return ip;
    }

    public static Map<String, String> getWeixinPara(){
        Map<String, String> paraMap = new HashMap<>();
        paraMap.put("appid", "wx01af434429e29725");
        paraMap.put("mch_id", "1503101741");
        paraMap.put("app_key", "518eb9368cc423fce6160463ed157a0e");
        return paraMap;
    }

    public static  Map<String,Object> WechatPay(String tradeNo, long totalFee, String orderName,String openid,String notifyUrl, Map<String, String> configDict) {
        SortedMap<Object, Object> parameters = new TreeMap<Object, Object>();
        parameters.put("appid", configDict.get("appid"));
        parameters.put("body", orderName);
        parameters.put("mch_id", configDict.get("mch_id"));
        parameters.put("nonce_str", WXPayUtils.createNoncestr(32));
        parameters.put("notify_url", notifyUrl);
        parameters.put("out_trade_no", tradeNo);
        parameters.put("spbill_create_ip", localIp());
        parameters.put("trade_type", "NATIVE");
        parameters.put("trade_type", "JSAPI");
        parameters.put("openid",openid);
        parameters.put("total_fee", String.valueOf(totalFee));
        String sign = WXPayUtils.createSign("UTF-8", parameters, configDict.get("app_key"));
        LOGGER.info("sign==={}",sign);
        parameters.put("sign", sign);
        String requestXML = WXPayUtils.getRequestXml(parameters);
        LOGGER.info("requestXML==={}",requestXML);
        String responseXml = WXPayUtils.httpsRequest("https://api.mch.weixin.qq.com/pay/unifiedorder", "POST", requestXML);
        LOGGER.info("responseXml==={}",responseXml);
        Map<String,Object> params = null;
        try {
            params = XMLParser.getMapFromXML(responseXml);
        } catch (Exception e) {
            LOGGER.warn("WechatPay parse request xml Error! error = {}, request_body = {}", e, requestXML);
            return null;
        }
        LOGGER.warn(responseXml);
        if (!params.get("return_code").toString().equals("SUCCESS")) {
            LOGGER.warn("WechatPay get return_code error! return_msg = {}, recharge= {}",
                    params.get("return_msg").toString(), tradeNo);
            return null;
        }

        if (params.get("result_code").toString().equals("FAIL")) {
            LOGGER.warn("WechatPay get result_code error! err_code = {}, err_code_des= {}, recharge = {}",
                    params.get("err_code").toString(), params.get("err_code_des").toString(), tradeNo);
            return null;
        }
        long time = System.currentTimeMillis();
        String nowTimeStamp = String.valueOf(time / 1000);
        params.put("sign", sign);
        params.put("signType", "MD5");
        params.put("timeStamp", nowTimeStamp);
        return params;

//        // APP
//        SortedMap<Object, Object> appReqParameters = new TreeMap<Object, Object>();
//        String prepayId = params.get("prepay_id").toString();
//        appReqParameters.put("appid", parameters.get("appid").toString());
//        appReqParameters.put("noncestr", parameters.get("nonce_str").toString());
//        appReqParameters.put("package", "Sign=WXPay");
//        appReqParameters.put("partnerid", parameters.get("mch_id").toString());
//        Long currentSeconds = System.currentTimeMillis() / 1000;
//        appReqParameters.put("timestamp", String.valueOf(currentSeconds));
//        appReqParameters.put("prepayid", prepayId);
//        String newSign = WXPayUtils.createSign("UTF-8", appReqParameters, configDict.get("app_key"));
//        appReqParameters.put("sign", newSign);
//        String appRequestXML = WXPayUtils.getRequestXml(appReqParameters);
//        return appRequestXML;
    }
}
