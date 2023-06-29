package com.aizuda.easy.security.filter;

import com.aizuda.easy.security.handler.DefaultHandlerFactory;
import com.aizuda.easy.security.handler.FunctionHandler;
import com.aizuda.easy.security.properties.SecurityProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collection;


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
        filterChain.doFilter(request, response);
    }

}
