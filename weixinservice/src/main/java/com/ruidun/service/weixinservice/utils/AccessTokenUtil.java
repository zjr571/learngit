package com.ruidun.service.weixinservice.utils;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class AccessTokenUtil {
    //缓存accessToken 的Map,map中包含 一个accessToken 和 缓存的时间戳
    private Map<String, String> map = new HashMap<String,String>();

    private AccessTokenUtil() {

    }

    private static AccessTokenUtil accessTokenUtil = null;

    public static AccessTokenUtil getAccessTokenUtil() {
        return accessTokenUtil;
    }

    public static void setAccessTokenUtil(AccessTokenUtil accessTokenUtil) {
        AccessTokenUtil.accessTokenUtil = accessTokenUtil;
    }

    // 静态工厂方法
    public static AccessTokenUtil getInstance() {

        if (accessTokenUtil == null) {
            accessTokenUtil = new AccessTokenUtil();
        }
        return accessTokenUtil;
    }

    public Map<String, String> getMap() {
        return map;
    }

    public void setMap(Map<String, String> map) {
        this.map = map;
    }



    /**
     * 获取 accessToken Jsapi_ticket 已加入缓存机制
     * @return
     */
    public Map<String,Object> getAccessTokenAndJsapiTicket() {
        Map<String,Object> result = new HashMap<String,Object>();
        AccessTokenUtil accessTokenUtil = AccessTokenUtil.getInstance();
        Map<String, String> map = accessTokenUtil.getMap();
        String time = map.get("time");//从缓存中拿数据
        String accessToken = map.get("access_token");//从缓存中拿数据
        String jsapiticket = map.get("jsapiticket");//从缓存中拿数据
        Long nowDate = new Date().getTime();
        //这里设置过期时间 3000*1000就好了
        if (accessToken != null && time != null && nowDate - Long.parseLong(time) < 3000 * 1000) {
            System.out.println("-----从缓存读取access_token："+accessToken);
            //从缓存中拿数据为返回结果赋值
            result.put("access_token", accessToken);
            result.put("jsapiticket", jsapiticket);
        } else {
            //将信息放置缓存中
            map.put("time", nowDate + "");
            map.put("access_token", JsapiTicketUtil.getAccessToken());
            map.put("jsapiticket", JsapiTicketUtil.getJSApiTicket());
            //为返回结果赋值
            result.put("access_token", JsapiTicketUtil.getAccessToken());
            result.put("jsapiticket", JsapiTicketUtil.getJSApiTicket());
        }
        return result;
    }
}
