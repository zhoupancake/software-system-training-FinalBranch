package com.systemtraining.interceptor;

import com.systemtraining.common.UserMessage;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.Optional;

// 拦截器获取cookie

public class UserInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler){
        Cookie[] cookies = request.getCookies();
        Arrays.stream(Optional.ofNullable(cookies).orElse(new Cookie[0])).filter(e -> e.getName().equals("username"))
                .findFirst().ifPresent(e-> UserMessage.setUsername(e.getValue()));
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex){
        UserMessage.remove();
    }
}
