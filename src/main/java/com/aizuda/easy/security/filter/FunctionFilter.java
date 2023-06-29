package com.aizuda.easy.security.filter;

import com.aizuda.easy.security.properties.SecurityProperties;
import com.aizuda.easy.security.handler.DefaultHandlerFactory;
import com.aizuda.easy.security.handler.FunctionHandler;
import com.aizuda.easy.security.server.wrapper.RequestDataWrapper;
import com.aizuda.easy.security.server.wrapper.ResponseDataWrapper;
import com.aizuda.easy.security.util.LocalUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;


public class FunctionFilter implements Filter {

    private static final Logger log = LoggerFactory.getLogger(FunctionFilter.class);
    private SecurityProperties properties;
    private DefaultHandlerFactory defaultHandlerFactory;

    public FunctionFilter(SecurityProperties properties, DefaultHandlerFactory defaultHandlerFactory) {
        this.properties = properties;
        this.defaultHandlerFactory = defaultHandlerFactory;
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        Collection<FunctionHandler> functionHandlers = defaultHandlerFactory.getFunctionHandlers();
        for (FunctionHandler functionHandler : functionHandlers) {
            functionHandler.exec(request, response, properties);
        }
        // 不是特殊路径，且开启了使用 RequestData功能
        LocalUtil.LocalEntity localEntity = LocalUtil.getLocalEntity();
        if(localEntity.getSpecial()){
            return;
        }
        request = new RequestDataWrapper(request, properties);
        ResponseDataWrapper responseDataWrapper = new ResponseDataWrapper(response,properties);
        filterChain.doFilter(request, responseDataWrapper);
        responseDataWrapper.changeContent();
    }

}
