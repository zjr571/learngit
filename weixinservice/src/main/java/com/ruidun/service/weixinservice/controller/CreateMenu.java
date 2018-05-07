package com.ruidun.service.weixinservice.controller;

import com.ruidun.service.weixinservice.Menu.*;
import com.ruidun.service.weixinservice.model.PayModel;
import com.ruidun.service.weixinservice.utils.AccessTokenUtil;
import com.ruidun.service.weixinservice.utils.MenuUtil;
import com.ruidun.service.weixinservice.utils.OrderIdUtil;
import com.ruidun.service.weixinservice.utils.PayUtils;


import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping(value = "/charger")
public class CreateMenu {
    private static final Logger logger = LoggerFactory.getLogger(ChargingController.class);
    @RequestMapping(value = "/createMenu", method = {RequestMethod.GET})
    public void createOrder(HttpServletRequest request, HttpServletResponse response) {
        ClickButton btn11=new ClickButton();
        btn11.setName("扫码充电");
        btn11.setType("scancode_push");
        btn11.setKey("KEY_qcode");
        ViewButton btn22=new ViewButton();
        btn22.setName("附近充电站");
        btn22.setType("view");
        btn22.setUrl("http://www.shouyifenxi.com/dist/page/index.html");
        StringBuffer url=new StringBuffer("http://www.shouyifenxi.com/dist/page/user.html");
        ViewButton btn33=new ViewButton();
        btn33.setName("我的");
        btn33.setType("view");
        btn33.setUrl(url.toString());
        ComplexButton complexButton=new ComplexButton();
        complexButton.setName("发现");
        complexButton.setSub_button(new BaseButton[]{btn33});
        Menu menu=new Menu();
        menu.setButton(new BaseButton[]{btn22,btn11,btn33});
        String json= JSONObject.fromObject(menu).toString();
        System.out.println(json);
        Map map=  AccessTokenUtil.getInstance().getAccessTokenAndJsapiTicket();
        String token=map.get("access_token").toString();
        logger.info("accessyoken = {}",token);
        MenuUtil.cteateMenu(json,token);
    }
}
