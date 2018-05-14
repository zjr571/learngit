package com.ruidun.service.weixinservice.utils;

import com.ruidun.service.weixinservice.controller.ChargingController;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

@Component
public class OpenIdInterceptor  implements HandlerInterceptor {
    private static final Logger logger = LoggerFactory.getLogger(OpenIdInterceptor.class);

    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        logger.info("进入controller层之前调用拦截器的方法");
       Cookie[] cookies = request.getCookies();
       String openId = null;
        if (null == cookies){
            //获取openId,赋值给cookie
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {
        logger.info("controller层方法执行完毕之后视图渲染之前调用的拦截器的方法");
    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {
        logger.info("进行视图渲染之后调用的拦截器的方法，用于资源清理");
    }
}


