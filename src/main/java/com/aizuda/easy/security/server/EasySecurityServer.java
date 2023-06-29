package com.aizuda.easy.security.server;

import com.aizuda.easy.security.properties.SecurityProperties;
import com.aizuda.easy.security.server.auth.AuthorizeServer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 继承此接口
 */
public interface EasySecurityServer extends AuthorizeServer {

    default void befor(HttpServletRequest request, HttpServletResponse response, SecurityProperties properties){

    }

    default void after(HttpServletRequest request, HttpServletResponse response, SecurityProperties properties){

    }

}
