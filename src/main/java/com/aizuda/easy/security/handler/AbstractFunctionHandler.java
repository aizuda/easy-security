package com.aizuda.easy.security.handler;

import com.aizuda.easy.security.server.EasySecurityServer;

public abstract class AbstractFunctionHandler implements FunctionHandler{

    public EasySecurityServer easySecurityServer;

    @Override
    public void setEasySecurityServer(EasySecurityServer easySecurityServer) {
        this.easySecurityServer = easySecurityServer;
    }
}
