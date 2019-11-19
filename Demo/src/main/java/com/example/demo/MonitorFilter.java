package com.example.demo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.LongAdder;

@Component
public class MonitorFilter implements Filter {

    private final Logger logger = LoggerFactory.getLogger(MonitorFilter .class);
    // LongAdder only long type long +1 or -1 add
    // thread-safe + synchronized  = LongAdder
    // sayac işlemlerinde thread-safe + performans için LongAdder Kullan!
    public static ConcurrentMap<String, LongAdder> requestCounts = new ConcurrentHashMap<>();


    //
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
        HttpServletResponse response = (HttpServletResponse) res;
        HttpServletRequest request = (HttpServletRequest) req;
        String url = request.getServletPath();
        chain.doFilter(req, res);
        requestCounts.computeIfAbsent(url, k -> new LongAdder()).increment();
    }

    public void init(FilterConfig filterConfig) {}

    public void destroy() {}

}