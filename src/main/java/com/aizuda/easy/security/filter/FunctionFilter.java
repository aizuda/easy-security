package com.aizuda.easy.security.filter;

import com.aizuda.easy.security.exp.impl.BasicException;
import com.aizuda.easy.security.filter.solt.FunctionFilterSolt;
import com.aizuda.easy.security.properties.SecurityProperties;
import com.aizuda.easy.security.server.wrapper.RequestDataWrapper;
import com.aizuda.easy.security.server.wrapper.ResponseDataWrapper;
import com.aizuda.easy.security.util.LocalUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


public class FunctionFilter extends FunctionFilterSolt implements Filter, FilterSolt {

    private static final Logger log = LoggerFactory.getLogger(FunctionFilter.class);
    private static final String PARAMES = "?code=%s&msg=%s";
    private SecurityProperties properties;

    public FunctionFilter(SecurityProperties properties) {
        this.properties = properties;
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        RequestDataWrapper qw;
        ResponseDataWrapper pw;
        try {
            qw = new RequestDataWrapper(request, properties);
            pw = new ResponseDataWrapper(response,properties);
            filterChain.doFilter(qw, pw);
        } catch (BasicException e) {
            log.error(e.getMsg());
            forward(request,response, properties.getErrorUrl(),e);
        }finally {
            LocalUtil.remove();
        }
    }

    public void forward(HttpServletRequest request, HttpServletResponse response, String errUrl, BasicException basicException) throws ServletException, IOException {
        String url = errUrl + String.format(PARAMES, basicException.getCode(),basicException.getMsg());
        request.getRequestDispatcher(url).forward(request,response);
    }

}
