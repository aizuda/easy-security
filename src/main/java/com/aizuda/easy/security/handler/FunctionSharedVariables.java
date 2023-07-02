package com.aizuda.easy.security.handler;

import com.aizuda.easy.security.properties.SecurityProperties;
import com.aizuda.easy.security.server.EasySecurityServer;

public interface FunctionSharedVariables {

    default void setEasySecurityServer(EasySecurityServer easySecurityServer){};

    default void setProperties(SecurityProperties properties){};

}
