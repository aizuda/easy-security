package com.aizuda.easy.security.handler.exec;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import com.aizuda.easy.security.code.BasicCode;
import com.aizuda.easy.security.domain.LocalEntity;
import com.aizuda.easy.security.exp.impl.AuthenticationException;
import com.aizuda.easy.security.exp.impl.AuthorizationException;
import com.aizuda.easy.security.exp.impl.BasicException;
import com.aizuda.easy.security.handler.AbstractFunctionHandler;
import com.aizuda.easy.security.handler.ReqFunctionHandler;
import com.aizuda.easy.security.util.LocalUtil;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Component
public class AuthorizationHandler extends AbstractFunctionHandler implements ReqFunctionHandler {

    @Override
    public String exec(HttpServletRequest request,String json) throws BasicException {
        LocalEntity localEntity = LocalUtil.getLocalEntity();
        if(localEntity.getSpecial() || localEntity.getProject() || !properties.getAuthorizeEnable()){
            return json;
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
        return json;
    }

    @Override
    public Integer getIndex() {
        return Integer.MIN_VALUE + 4;
    }
}
