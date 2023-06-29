package com.aizuda.easy.security.server;

import com.aizuda.easy.security.server.auth.AuthorizeServer;

/**
 * 继承此接口
 */
public interface EasySecurityServer extends AuthorizeServer {

    default void befor(){

    }

    default void after(){

    }

}
