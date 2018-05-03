package com.ruidun.service.weixinservice.utils;



import net.sf.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Map;

public class JsapiTicketUtil {
    /***
     * 模拟get请求
     * @param url
     * @param charset
     * @param timeout
     * @return
     */
    public static String sendGet(String url, String charset, int timeout)
    {
        String result = "";
        try
        {
            URL u = new URL(url);
            try
            {
                URLConnection conn = u.openConnection();
                conn.connect();
                conn.setConnectTimeout(timeout);
                BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream(), charset));
                String line="";
                while ((line = in.readLine()) != null)
                {

                    result = result + line;
                }
                in.close();
            } catch (IOException e) {
                return result;
            }
        }
        catch (MalformedURLException e)
        {
            return result;
        }

        return result;
    }
    /***
     * 获取acess_token
     * 来源www.vxzsk.com
     * @return
     */
    public static String getAccessToken(){
        String appid="wx01af434429e29725";//应用ID
        String appSecret="5e517cc30f12e4ffe8b4a2a183fe88e7";//(应用密钥)
        String url ="https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid="+appid+"&secret="+appSecret+"";
        String backData=sendGet(url, "utf-8", 10000);
        String accessToken = (String) JSONObject.fromObject(backData).get("access_token");

        return accessToken;
    }
    /***
     * 获取jsapiTicket
     * 来源 www.vxzsk.com
     * @return
     */
    public static String getJSApiTicket(){
        //获取token
        Map map=  AccessTokenUtil.getInstance().getAccessTokenAndJsapiTicket();
        String acess_token= map.get("access_token").toString();
        String urlStr = "https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token="+acess_token+"&type=jsapi";
        String backData=sendGet(urlStr, "utf-8", 10000);
        String ticket = (String) JSONObject.fromObject(backData).get("ticket");
        return  ticket;
    }

}
