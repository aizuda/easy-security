package com.aizuda.easy.security.server.auth;

import com.aizuda.easy.security.exp.impl.BasicException;

import java.util.List;

public interface AuthorizeServer {

    Object getAuthUser(String token) throws BasicException;

    List<String> getAuthorizeUrl(String token) throws BasicException;

}
