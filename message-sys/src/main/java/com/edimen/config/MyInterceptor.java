package com.edimen.config;

import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Calendar;

/**
 * 自定义拦截器
 */
@Component
public class MyInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String requestURI = request.getRequestURI();
        System.out.println(requestURI);
        if (requestURI.indexOf("/toLoginPage") >=0) {
            return true;
        }
        Object loginUser = request.getSession().getAttribute("loginUser");
        if (loginUser !=null) {
            return true;
        }else {
            //不符合条件的给出提示信息，并转发到登录页面
            request.setAttribute("msg","您还没有登录，请登录!");
//            request.getRequestDispatcher("login.html").forward(request,response);
            response.sendRedirect("http://127.0.0.1:8080/toLoginPage");
            return false;
        }
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, @Nullable ModelAndView modelAndView) throws Exception {
        request.setAttribute("currentYear", Calendar.getInstance().get(Calendar.YEAR));
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler,@Nullable Exception ex) throws Exception {

    }
}
