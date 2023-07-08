package com.aizuda.easy.security.handler;

import com.aizuda.easy.security.properties.SecurityProperties;
import com.aizuda.easy.security.server.EasySecurityServer;
import com.fasterxml.jackson.databind.ObjectMapper;

public abstract class AbstractFunctionHandler implements FunctionHandler,FunctionSharedVariables{

    public EasySecurityServer easySecurityServer;

    public SecurityProperties properties;

    public ObjectMapper mapper = new ObjectMapper();

    @Override
    public void setEasySecurityServer(EasySecurityServer easySecurityServer) {
        this.easySecurityServer = easySecurityServer;
    }

    @Override
    public void setProperties(SecurityProperties properties) {
        this.properties = properties;
    }

}
