package com.ruidun.service.weixinservice.controller;



import com.alibaba.fastjson.util.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;


@RestController
public class ReceiveController {
    private static final Logger logger = LoggerFactory.getLogger(ChargingController.class);
    @RequestMapping(value = "/status", method = {RequestMethod.POST})
    public void status (HttpServletRequest request) {
        String acceptjson = "";
        try {
            logger.info("chargindStatus");
            BufferedReader br;
            br = new BufferedReader(new InputStreamReader( (ServletInputStream) request.getInputStream(), "utf-8"));
            StringBuffer sb = new StringBuffer("");
            String temp;
            while ((temp = br.readLine()) != null) {
                sb.append(temp);
            }
            br.close();
            acceptjson = sb.toString();
            logger.info("chargindStatus:{}",acceptjson);
        }catch (Exception o){
        }
    }

}
