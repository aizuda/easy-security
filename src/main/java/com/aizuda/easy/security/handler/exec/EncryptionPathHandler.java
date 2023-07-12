package com.aizuda.easy.security.handler.exec;

import cn.hutool.core.collection.CollectionUtil;
import com.aizuda.easy.security.exp.impl.BasicException;
import com.aizuda.easy.security.handler.AbstractFunctionHandler;
import com.aizuda.easy.security.handler.RepFunctionHandler;
import com.aizuda.easy.security.util.PathCheckUtil;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;

@Component
public class EncryptionPathHandler  extends AbstractFunctionHandler implements RepFunctionHandler {

    @Override
    public Integer getIndex() {
        return Integer.MAX_VALUE - 10;
    }

    @Override
    public String exec(HttpServletResponse response, String json) throws BasicException, IOException {
        List<String> urlFilter = properties.getDecryptUrl();
        String url = response.getHeader("requestUri");
        if(CollectionUtil.isEmpty(urlFilter) || !PathCheckUtil.isMatch(urlFilter,url)){
            return json;
        }
        return easySecurityServer.encryption(response, json, properties.getSecretKey());
    }

}
