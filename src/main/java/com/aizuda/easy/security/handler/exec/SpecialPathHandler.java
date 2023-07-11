package com.aizuda.easy.security.handler.exec;

import cn.hutool.core.collection.CollectionUtil;
import com.aizuda.easy.security.domain.LocalEntity;
import com.aizuda.easy.security.exp.impl.BasicException;
import com.aizuda.easy.security.handler.AbstractFunctionHandler;
import com.aizuda.easy.security.handler.ReqFunctionHandler;
import com.aizuda.easy.security.util.LocalUtil;
import com.aizuda.easy.security.util.PathCheckUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class SpecialPathHandler extends AbstractFunctionHandler implements ReqFunctionHandler {

    @Override
    public String exec(HttpServletRequest request, String json) throws BasicException {
        LocalEntity localEntity = LocalUtil.getLocalEntity();
        List<String> urlFilter = properties.getSpecialUrl();
        if(CollectionUtil.isEmpty(urlFilter)){
            return json;
        }
        String url = request.getRequestURI();
        // 确定有该URL 就需要放到线程变量中
        PathCheckUtil.pathMatch(urlFilter,url,(bol) -> localEntity.setSpecial(bol));
        return json;
    }

    @Override
    public Integer getIndex() {
        return Integer.MIN_VALUE + 1;
    }

}
