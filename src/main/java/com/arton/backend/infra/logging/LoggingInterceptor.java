package com.arton.backend.infra.logging;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;
import java.util.Objects;

@Component
public class LoggingInterceptor implements HandlerInterceptor {
    private final Logger log = LoggerFactory.getLogger("ELASTIC");

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (Objects.equals(request.getMethod(), "POST")){
            Map<String, Object> map = new ObjectMapper().readValue(request.getInputStream(), Map.class);
            log.info("RequestBody {}", map);
            log.info("RequestURI : {}", request.getRequestURI());
            return true;
        }else{
            log.info("RequestURI : {}", request.getRequestURI());
            return true;
        }
    }

}
