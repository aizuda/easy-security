package com.aizuda.easy.security.filter;

import com.aizuda.easy.security.exp.impl.BasicException;
import com.aizuda.easy.security.properties.SecurityProperties;
import com.aizuda.easy.security.util.LocalUtil;
import org.apache.commons.codec.Charsets;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


public class BeforeFilter implements Filter {

    private static final Logger log = LoggerFactory.getLogger(BeforeFilter.class);
    private SecurityProperties securityProperties;

    public BeforeFilter(SecurityProperties securityProperties) {
        this.securityProperties = securityProperties;
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        request.setCharacterEncoding(Charsets.UTF_8.toString());
        response.setCharacterEncoding(Charsets.UTF_8.toString());
        try {
            LocalUtil.create();
            filterChain.doFilter(request, response);
        } catch (BasicException e) {
            log.error(e.getMsg());
            LocalUtil.forward(request,response, securityProperties.getErrorUrl(),e);
        }finally {
            LocalUtil.remove();
        }
    }

}
