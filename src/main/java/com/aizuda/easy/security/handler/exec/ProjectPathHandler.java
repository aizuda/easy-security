package com.aizuda.easy.security.handler.exec;

import cn.hutool.core.collection.CollectionUtil;
import com.aizuda.easy.security.exp.impl.BasicException;
import com.aizuda.easy.security.handler.FunctionHandler;
import com.aizuda.easy.security.properties.SecurityProperties;
import com.aizuda.easy.security.util.LocalUtil;
import com.aizuda.easy.security.util.PathCheckUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class ProjectPathHandler implements FunctionHandler {
    @Override
    public void exec(HttpServletRequest request, HttpServletResponse response, SecurityProperties properties) throws BasicException {
        LocalUtil.LocalEntity localEntity = LocalUtil.getLocalEntity();;
        List<String> urlFilter = properties.getProjectUrl();
        if(!CollectionUtil.isEmpty(urlFilter)){
            String url = request.getRequestURI();
            PathCheckUtil.pathMatch(urlFilter,url, localEntity::setProject);
        }
    }

    @Override
    public Integer getIndex() {
        return 2;
    }
}
