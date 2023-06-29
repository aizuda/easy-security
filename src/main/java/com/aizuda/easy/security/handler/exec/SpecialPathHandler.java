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

public class SpecialPathHandler implements FunctionHandler {

    @Override
    public void exec(HttpServletRequest request, HttpServletResponse response, SecurityProperties properties) throws BasicException {
        LocalUtil.LocalEntity localEntity = LocalUtil.getLocalEntity();
        List<String> urlFilter = properties.getSpecialUrl();
        if(CollectionUtil.isEmpty(urlFilter)){
            return;
        }
        String url = request.getRequestURI();
        // 确定有该URL 就需要放到线程变量中
        PathCheckUtil.pathMatch(urlFilter,url, localEntity::setSpecial);
    }

    @Override
    public Integer getIndex() {
        return 1;
    }
}
