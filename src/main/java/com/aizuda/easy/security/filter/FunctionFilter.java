package com.aizuda.easy.security.filter;

import com.aizuda.easy.security.HandlerFactory;
import com.aizuda.easy.security.domain.LocalEntity;
import com.aizuda.easy.security.exp.impl.BasicException;
import com.aizuda.easy.security.filter.wrapper.RepWrapper;
import com.aizuda.easy.security.filter.wrapper.ReqWrapper;
import com.aizuda.easy.security.properties.SecurityProperties;
import com.aizuda.easy.security.util.LocalUtil;
import com.aizuda.easy.security.util.PathCheckUtil;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerExceptionResolver;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;


public class FunctionFilter implements Filter {

    private static final Logger log = LoggerFactory.getLogger(FunctionFilter.class);
    private SecurityProperties properties;
    private HandlerFactory factory;
    private HandlerExceptionResolver handlerExceptionResolver;
    public FunctionFilter(SecurityProperties properties,HandlerFactory factory,HandlerExceptionResolver handlerExceptionResolver) {
        this.properties = properties;
        this.factory = factory;
        this.handlerExceptionResolver = handlerExceptionResolver;
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        try {
            LocalUtil.create();
            specialPathHandler(request);
            request.setCharacterEncoding(StandardCharsets.UTF_8.name());
            response.setCharacterEncoding(StandardCharsets.UTF_8.name());
            if(!LocalUtil.getLocalEntity().getSpecial()){
                request = new ReqWrapper(request,factory);
                response = new RepWrapper(response,factory);
            }
            filterChain.doFilter(request, response);
            if(response instanceof RepWrapper) {
                response.setHeader("requestUri", request.getRequestURI());
                ((RepWrapper) response).changeContent();
            }
        } catch (BasicException e) {
            log.error(e.getMsg());
            handlerExceptionResolver.resolveException(request,response,null, e);
        }finally {
            LocalUtil.destroy();
        }
    }

    private void specialPathHandler(HttpServletRequest request){
        LocalEntity localEntity = LocalUtil.getLocalEntity();
        List<String> urlFilter = properties.getSpecialUrl();
        String url = request.getRequestURI();
        // 确定有该URL 就需要放到线程变量中
        PathCheckUtil.pathMatch(urlFilter,url, localEntity::setSpecial);
    }

}
