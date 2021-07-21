//package com.oyzg.wiki.interceptor;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.stereotype.Component;
//import org.springframework.web.servlet.HandlerInterceptor;
//import org.springframework.web.servlet.ModelAndView;
//
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//
//@Component
//public class LogInterceptor implements HandlerInterceptor {
//    private static final Logger LOG = LoggerFactory.getLogger(LogInterceptor.class);
//
//    @Override
//    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
//        LOG.info("---------- LogInterceptor start----------");
//        LOG.info("请求地址：{} {}",request.getRequestURI(),request.getMethod());
//        LOG.info("远程地址：{}",request.getRemoteAddr());
//        long startTime = System.currentTimeMillis();
//        request.setAttribute("requestStartTime",startTime);
//        return true;
//    }
//
//    @Override
//    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
//        long startTime = (long) request.getAttribute("requestStartTime");
//        LOG.info("----------LogInterceptor---------- 请求耗时：{} ms",System.currentTimeMillis()-startTime);
//        HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
//    }
//}