package com.aizuda.easy.security.handler.exec;

import cn.hutool.core.collection.CollectionUtil;
import com.aizuda.easy.security.exp.impl.BasicException;
import com.aizuda.easy.security.handler.AbstractFunctionHandler;
import com.aizuda.easy.security.handler.ReqFunctionHandler;
import com.aizuda.easy.security.util.PathCheckUtil;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Component
public class DecryptPathHandler extends AbstractFunctionHandler implements ReqFunctionHandler {

    @Override
    public String exec(HttpServletRequest request, String json) throws BasicException {
        List<String> urlFilter = properties.getDecryptUrl();
        String url = request.getRequestURI();
        if(CollectionUtil.isEmpty(urlFilter) || !PathCheckUtil.isMatch(urlFilter,url)){
            return json;
        }
        return easySecurityServer.decryption(request,json,properties.getSecretKey());
    }

    @Override
    public Integer getIndex() {
        return Integer.MIN_VALUE + 3;
    }

}
