package com.aizuda.easy.security.filter;

import com.aizuda.easy.security.properties.SecurityProperties;
import com.aizuda.easy.security.util.LocalUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


public class AfterFilter implements Filter {

    private static final Logger log = LoggerFactory.getLogger(AfterFilter.class);
    private SecurityProperties securityProperties;
    public AfterFilter(SecurityProperties securityProperties) {
        this.securityProperties = securityProperties;
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        filterChain.doFilter(request, response);
    }

}
