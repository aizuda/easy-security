package com.aizuda.easy.security.server;

import com.aizuda.easy.security.exp.impl.BasicException;

import java.util.List;

public class EasySecurityServerImpl implements EasySecurityServer{

    @Override
    public Object getAuthUser(String token) throws BasicException {
        return null;
    }

    @Override
    public List<String> getAuthorizeUrl(String token) throws BasicException {
        return null;
    }

}
