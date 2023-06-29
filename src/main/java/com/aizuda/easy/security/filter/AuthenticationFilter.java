package com.aizuda.easy.security.filter;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.aizuda.easy.security.properties.SecurityProperties;
import com.aizuda.easy.security.code.BasicCode;
import com.aizuda.easy.security.exp.impl.AuthenticationException;
import com.aizuda.easy.security.exp.impl.BasicException;
import com.aizuda.easy.security.server.EasySecurityServer;
import com.aizuda.easy.security.util.ThreadLocalUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


public class AuthenticationFilter implements Filter {

    private static final Logger log = LoggerFactory.getLogger(AuthenticationFilter.class);

    private SecurityProperties securityProperties;
    private EasySecurityServer easySecurityServer;

    public void setAuthenticationProperties(SecurityProperties securityProperties) {
        this.securityProperties = securityProperties;
    }

    public void setAuthenticationServerSource(EasySecurityServer easySecurityServer) {
        this.easySecurityServer = easySecurityServer;
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        // 不为特殊路径和项目路径才获取用户信息
        ThreadLocalUtil.ThreadLocalEntity threadLocalEntity = ThreadLocalUtil.threadLocal.get();
        if(!threadLocalEntity.getSpecial() && !threadLocalEntity.getProject()){
            Object obj = getUser(request);
            threadLocalEntity.setUser(obj);
        }
        filterChain.doFilter(request, response);
    }


    public Object getUser(HttpServletRequest httpServletRequest) throws BasicException {
        String token = httpServletRequest.getHeader(securityProperties.getTokenKey());
        if (StrUtil.isEmpty(token)) {
            throw new AuthenticationException(BasicCode.BASIC_CODE_401);
        }
        Object obj = easySecurityServer.getAuthUser(token);
        if(ObjectUtil.isEmpty(obj)){
            throw new AuthenticationException(BasicCode.BASIC_CODE_401);
        }
        return obj;
    }

}
