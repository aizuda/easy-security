package com.aizuda.easy.security.handler;

import com.aizuda.easy.security.exp.impl.BasicException;
import com.aizuda.easy.security.properties.SecurityProperties;
import com.aizuda.easy.security.server.EasySecurityServer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public abstract class AbstractFunctionHandler implements FunctionHandler,FunctionSharedVariables{

    public EasySecurityServer easySecurityServer;

    public SecurityProperties properties;

    @Override
    public void setEasySecurityServer(EasySecurityServer easySecurityServer) {
        this.easySecurityServer = easySecurityServer;
    }

    @Override
    public void setProperties(SecurityProperties properties) {
        this.properties = properties;
    }

    @Override
    public void doExec(HttpServletRequest request, HttpServletResponse response, FunctionHandler functionHandler) throws BasicException, IOException {
        functionHandler.exec(request,response);
    }
}
