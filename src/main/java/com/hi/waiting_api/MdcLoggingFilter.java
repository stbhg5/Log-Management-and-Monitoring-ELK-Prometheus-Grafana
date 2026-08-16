package com.hi.waiting_api;

import jakarta.servlet.*;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.UUID;

@Component
public class MdcLoggingFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        Filter.super.init(filterConfig);
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        // 1. 요청이 들어오면 고유한 8글자 Trace ID 생성
        String traceId = UUID.randomUUID().toString().substring(0, 8);

        // 2. MDC라는 곳에 해당 Trace ID를 보관
        MDC.put("traceId", traceId);

        try {
            // 3.Controller로 요청을 넘김
            // 현재 필터가 최종 필터면, 바로 Controller 호출
            // 현재 필터가 최종이 아니고 다음 필터가 있으면, 다음 필터 호출
            chain.doFilter(request, response);
        } finally {
            // 요청 처리가 끝나면 반드시 비워줘야 함
            // 톰캣은 재사용되기 때문에, 안 비우면 다음 사람 로그에 이전 사람 Trace ID 가 섞일 수 있음
            MDC.clear();
        }
    }

    @Override
    public void destroy() {
        Filter.super.destroy();
    }

}