package com.ruidun.service.weixinservice.utils;

import com.ruidun.service.weixinservice.controller.ChargingController;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MenuUtil {
    private static final Logger logger = LoggerFactory.getLogger(ChargingController.class);
    private  final static String MENU_CREATE_URL="https://api.weixin.qq.com/cgi-bin/menu/create?access_token=ACCESS_TOKEN";
    public  static boolean cteateMenu(String json,String accessToken){
        boolean result = false;
        String requesUrl = MENU_CREATE_URL.replace("ACCESS_TOKEN",accessToken);
       String respJSON = CommonUtil.httpsRequest(requesUrl,"POST",json);

        JSONObject jsonObject = JSONObject.fromObject(respJSON);
        if (null != jsonObject){
            int errCode = jsonObject.getInt("errcode");
            String errmsg = jsonObject.getString("errmsg");
            if (0 == errCode){
                result = true;
                logger.info("service say:{}","创建菜单成功！！！");

            }else {
                result = false;
                logger.info("service say:{}",errmsg);
            }
        }
        return result;
    }
}
