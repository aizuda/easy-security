package com.aizuda.easy.security.filter;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import com.aizuda.easy.security.code.BasicCode;
import com.aizuda.easy.security.properties.SecurityProperties;
import com.aizuda.easy.security.exp.impl.BasicException;
import com.aizuda.easy.security.util.PathCheckUtil;
import com.aizuda.easy.security.util.ThreadLocalUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;


public class DecryptPathFilter implements Filter {

    private static final Logger log = LoggerFactory.getLogger(DecryptPathFilter.class);
    private SecurityProperties securityProperties;

    public void setAuthenticationProperties(SecurityProperties securityProperties) {
        this.securityProperties = securityProperties;
    }


    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        List<String> urlFilter = securityProperties.getDecryptUrl();
        ThreadLocalUtil.ThreadLocalEntity threadLocalEntity = ThreadLocalUtil.threadLocal.get();
        // 为空则不拦截
        if(!CollectionUtil.isEmpty(urlFilter)){
            if(StrUtil.isEmpty(securityProperties.getSecretKey())){
                throw new BasicException(BasicCode.BASIC_CODE_99990);
            }
            String url = request.getRequestURI();
            PathCheckUtil.pathMatch(urlFilter,url,threadLocalEntity::setDecrypt);
        }
        filterChain.doFilter(request, response);
    }

}
