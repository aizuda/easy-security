package com.aizuda.easy.security.handler;

import com.aizuda.easy.security.properties.SecurityProperties;
import com.aizuda.easy.security.server.EasySecurityServer;

public interface FunctionSharedVariables {

    void setEasySecurityServer(EasySecurityServer easySecurityServer);

    void setProperties(SecurityProperties properties);

}
