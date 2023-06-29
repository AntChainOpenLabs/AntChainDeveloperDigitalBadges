package com.antgroup.antchain.xbuilders.web.interceptor;

import com.antgroup.antchain.xbuilders.service.SessionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Slf4j
@Component
public class LoginInterceptor implements HandlerInterceptor {

    @Resource
    private SessionService sessionService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        if (handler instanceof HandlerMethod) {
            // 只对Controller请求校验
            log.debug("request uri = {}, request cookies = {}", request.getRequestURI(), request.getHeader("cookie"));

            HandlerMethod handlerMethod = (HandlerMethod) handler;
            if (handlerMethod.getMethodAnnotation(Anonymous.class) != null) {
                return true;
            }
        }

        return true;
    }

    /**
     * 允许匿名访问
     */
    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.METHOD)
    public @interface Anonymous {
    }

}
