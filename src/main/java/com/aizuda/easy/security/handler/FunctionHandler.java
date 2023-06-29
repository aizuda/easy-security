package com.aizuda.easy.security.handler;

import com.aizuda.easy.security.exp.impl.BasicException;
import com.aizuda.easy.security.properties.SecurityProperties;
import com.aizuda.easy.security.server.EasySecurityServer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public interface FunctionHandler {

    void exec(HttpServletRequest request, HttpServletResponse response, SecurityProperties properties) throws BasicException, IOException;

    Integer getIndex();

    default void setEasySecurityServer(EasySecurityServer easySecurityServer){};
}
