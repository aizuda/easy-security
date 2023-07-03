package com.aizuda.easy.security.handler.exec;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.aizuda.easy.security.code.BasicCode;
import com.aizuda.easy.security.domain.LocalEntity;
import com.aizuda.easy.security.domain.Req;
import com.aizuda.easy.security.exp.impl.BasicException;
import com.aizuda.easy.security.handler.AbstractFunctionHandler;
import com.aizuda.easy.security.handler.ReqFunctionHandler;
import com.aizuda.easy.security.util.LocalUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Locale;

@Component
public class ReqDataHandler extends AbstractFunctionHandler implements ReqFunctionHandler {

    private final ObjectMapper mapper = new ObjectMapper();

    @Override
    public Integer getIndex() {
        return Integer.MIN_VALUE + 6;
    }

    @Override
    public String exec(HttpServletRequest request, String json) throws BasicException, IOException {
        String method = request.getMethod().toUpperCase(Locale.ROOT);
        LocalEntity localEntity = LocalUtil.getLocalEntity();
        if(StrUtil.isEmpty(json) || properties.getRequestDataEnable()) {
            return json;
        }
        if(!method.equals("POST")){
            throw new BasicException(BasicCode.BASIC_CODE_99997);
        }
        Req<Object,Object> req = mapper.readValue(json, Req.class);
        if (ObjectUtil.isEmpty(req)) {
            req = new Req<>();
        }
        req.setUser(localEntity.getUser());
        String token = request.getHeader(properties.getTokenKey());
        req.setToken(token);
        return mapper.writeValueAsString(req);
    }


}
