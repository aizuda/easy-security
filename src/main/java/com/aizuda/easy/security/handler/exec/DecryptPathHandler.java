package com.aizuda.easy.security.handler.exec;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import com.aizuda.easy.security.code.BasicCode;
import com.aizuda.easy.security.domain.LocalEntity;
import com.aizuda.easy.security.exp.impl.BasicException;
import com.aizuda.easy.security.handler.AbstractFunctionHandler;
import com.aizuda.easy.security.handler.ReqFunctionHandler;
import com.aizuda.easy.security.util.LocalUtil;
import com.aizuda.easy.security.util.PathCheckUtil;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public class DecryptPathHandler extends AbstractFunctionHandler implements ReqFunctionHandler {
    @Override
    public String exec(HttpServletRequest request, String json) throws BasicException {
        List<String> urlFilter = properties.getDecryptUrl();
        LocalEntity localEntity = LocalUtil.getLocalEntity();
        // 为空则不拦截
        if(CollectionUtil.isEmpty(urlFilter)){
            return json;
        }
        if(StrUtil.isEmpty(properties.getSecretKey())){
            throw new BasicException(BasicCode.BASIC_CODE_99990);
        }
        String url = request.getRequestURI();
        PathCheckUtil.pathMatch(urlFilter,url, localEntity::setDecrypt);
        return json;
    }

    @Override
    public Integer getIndex() {
        return 3;
    }
}
