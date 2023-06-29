package com.aizuda.easy.security.filter;

import cn.hutool.core.collection.CollectionUtil;
import com.aizuda.easy.security.code.BasicCode;
import com.aizuda.easy.security.properties.SecurityProperties;
import com.aizuda.easy.security.util.PathCheckUtil;
import com.aizuda.easy.security.util.ThreadLocalUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;


public class SpecialPathFilter implements Filter {

    private static final Logger log = LoggerFactory.getLogger(SpecialPathFilter.class);
    private SecurityProperties securityProperties;

    public void setAuthenticationProperties(SecurityProperties securityProperties) {
        this.securityProperties = securityProperties;
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        ThreadLocalUtil.ThreadLocalEntity threadLocalEntity = new ThreadLocalUtil.ThreadLocalEntity();
        List<String> urlFilter = securityProperties.getSpecialUrl();
        if(!CollectionUtil.isEmpty(urlFilter)){
            String url = request.getRequestURI();
            // 确定有该URL 就需要放到线程变量中
            PathCheckUtil.pathMatch(urlFilter,url, threadLocalEntity::setSpecial);
        }
        ThreadLocalUtil.threadLocal.set(threadLocalEntity);
        filterChain.doFilter(request, response);
    }


}
