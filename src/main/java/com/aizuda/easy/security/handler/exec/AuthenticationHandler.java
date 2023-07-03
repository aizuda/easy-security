package com.aizuda.easy.security.handler.exec;


import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.aizuda.easy.security.code.BasicCode;
import com.aizuda.easy.security.domain.LocalEntity;
import com.aizuda.easy.security.exp.impl.AuthenticationException;
import com.aizuda.easy.security.exp.impl.BasicException;
import com.aizuda.easy.security.handler.AbstractFunctionHandler;
import com.aizuda.easy.security.handler.ReqFunctionHandler;
import com.aizuda.easy.security.util.LocalUtil;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

@Component
public class AuthenticationHandler extends AbstractFunctionHandler implements ReqFunctionHandler {

    @Override
    public String exec(HttpServletRequest request, String json) throws BasicException {
        // 不为特殊路径和项目路径才获取用户信息
        LocalEntity localEntity = LocalUtil.getLocalEntity();
        if(localEntity.getSpecial() || localEntity.getProject()){
            return json;
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
        return json;
    }

    @Override
    public Integer getIndex() {
        return Integer.MIN_VALUE + 4;
    }

}
