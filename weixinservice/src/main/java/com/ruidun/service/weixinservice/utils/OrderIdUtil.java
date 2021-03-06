package com.ruidun.service.weixinservice.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

public class OrderIdUtil {
    public static String getOrderId(){
        Date day = new Date();
        SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
        String base = "0123456789";
        Random random = new Random();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < 5; i++) {
            int number = random.nextInt(base.length());
            sb.append(base.charAt(number));
        }
        String orderIdString = df.format(day).toString()+sb;

        return orderIdString;
    }
}
