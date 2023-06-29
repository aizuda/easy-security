package com.aizuda.easy.security.handler.exec;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import com.aizuda.easy.security.code.BasicCode;
import com.aizuda.easy.security.exp.impl.AuthenticationException;
import com.aizuda.easy.security.exp.impl.AuthorizationException;
import com.aizuda.easy.security.exp.impl.BasicException;
import com.aizuda.easy.security.handler.AbstractFunctionHandler;
import com.aizuda.easy.security.handler.FunctionHandler;
import com.aizuda.easy.security.properties.SecurityProperties;
import com.aizuda.easy.security.server.EasySecurityServer;
import com.aizuda.easy.security.util.LocalUtil;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class AuthorizationHandler extends AbstractFunctionHandler implements FunctionHandler {

    @Override
    public void exec(HttpServletRequest request, HttpServletResponse response, SecurityProperties properties) throws BasicException {
        LocalUtil.LocalEntity localEntity = LocalUtil.getLocalEntity();
        if(localEntity.getSpecial() || localEntity.getProject()){
            return;
        }
        String token = request.getHeader(properties.getTokenKey());
        if (StrUtil.isEmpty(token)) {
            throw new AuthenticationException(BasicCode.BASIC_CODE_401);
        }
        List<String> list = easySecurityServer.getAuthorizeUrl(token);
        if(CollectionUtil.isEmpty(list)){
            throw new AuthorizationException(BasicCode.BASIC_CODE_403);
        }
        String url = request.getRequestURI();
        if(!list.contains(url)){
            throw new AuthorizationException(BasicCode.BASIC_CODE_403);
        }
    }

    @Override
    public Integer getIndex() {
        return 5;
    }
}
