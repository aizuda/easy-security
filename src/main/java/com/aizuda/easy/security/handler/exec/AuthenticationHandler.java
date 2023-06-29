package com.aizuda.easy.security.handler.exec;


import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.aizuda.easy.security.code.BasicCode;
import com.aizuda.easy.security.exp.impl.AuthenticationException;
import com.aizuda.easy.security.exp.impl.BasicException;
import com.aizuda.easy.security.handler.AbstractFunctionHandler;
import com.aizuda.easy.security.handler.FunctionHandler;
import com.aizuda.easy.security.properties.SecurityProperties;
import com.aizuda.easy.security.server.EasySecurityServer;
import com.aizuda.easy.security.util.LocalUtil;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AuthenticationHandler extends AbstractFunctionHandler {

    @Override
    public void exec(HttpServletRequest request, HttpServletResponse response, SecurityProperties properties) throws BasicException {
        // 不为特殊路径和项目路径才获取用户信息
        LocalUtil.LocalEntity localEntity = LocalUtil.getLocalEntity();
        if(localEntity.getSpecial() || localEntity.getProject()){
            return;
        }
        String token = request.getHeader(properties.getTokenKey());
        if (StrUtil.isEmpty(token)) {
            throw new AuthenticationException(BasicCode.BASIC_CODE_401);
        }
        Object obj = easySecurityServer.getAuthUser(token);
        if(ObjectUtil.isEmpty(obj)){
            throw new AuthenticationException(BasicCode.BASIC_CODE_401);
        }
        localEntity.setUser(obj);
    }

    @Override
    public Integer getIndex() {
        return 4;
    }

}
