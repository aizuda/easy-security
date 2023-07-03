package com.aizuda.easy.security.handler;

import com.aizuda.easy.security.properties.SecurityProperties;
import com.aizuda.easy.security.server.EasySecurityServer;

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

}
