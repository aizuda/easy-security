package com.aizuda.easy.security.filter;

import com.aizuda.easy.security.properties.SecurityProperties;
import com.aizuda.easy.security.server.EasySecurityServer;
import com.aizuda.easy.security.server.wrapper.RequestDataWrapper;
import com.aizuda.easy.security.server.wrapper.ResponseDataWrapper;
import com.aizuda.easy.security.util.LocalUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


public class AfterFilter implements Filter {

    private static final Logger log = LoggerFactory.getLogger(AfterFilter.class);
    private SecurityProperties properties;
    private EasySecurityServer easySecurityServer;

    public AfterFilter(SecurityProperties properties, EasySecurityServer easySecurityServer) {
        this.properties = properties;
        this.easySecurityServer = easySecurityServer;
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        // 不是特殊路径，且开启了使用 RequestData功能
        LocalUtil.LocalEntity localEntity = LocalUtil.getLocalEntity();
        if(localEntity.getSpecial()){
            return;
        }
        request = new RequestDataWrapper(request, properties);
        response = new ResponseDataWrapper(response,properties);
        easySecurityServer.after(request, response, properties);
        filterChain.doFilter(request, response);
        ((ResponseDataWrapper)response).changeContent();
    }

}
