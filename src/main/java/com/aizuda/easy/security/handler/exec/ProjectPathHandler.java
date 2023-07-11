package com.aizuda.easy.security.handler.exec;

import cn.hutool.core.collection.CollectionUtil;
import com.aizuda.easy.security.domain.LocalEntity;
import com.aizuda.easy.security.exp.impl.BasicException;
import com.aizuda.easy.security.handler.AbstractFunctionHandler;
import com.aizuda.easy.security.handler.ReqFunctionHandler;
import com.aizuda.easy.security.util.LocalUtil;
import com.aizuda.easy.security.util.PathCheckUtil;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Component
public class ProjectPathHandler extends AbstractFunctionHandler implements ReqFunctionHandler {

    @Override
    public String exec(HttpServletRequest request, String json) throws BasicException {
        LocalEntity localEntity = LocalUtil.getLocalEntity();;
        List<String> urlFilter = properties.getProjectUrl();
        if(!CollectionUtil.isEmpty(urlFilter)){
            String url = request.getRequestURI();
            PathCheckUtil.pathMatch(urlFilter,url,localEntity::setProject);
        }
        return json;
    }

    @Override
    public Integer getIndex() {
        return Integer.MIN_VALUE + 20;
    }
}
