package com.aizuda.easy.security.handler.exec;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.json.JSONUtil;
import com.aizuda.easy.security.domain.Req;
import com.aizuda.easy.security.exp.impl.BasicException;
import com.aizuda.easy.security.handler.AbstractFunctionHandler;
import com.aizuda.easy.security.handler.ReqFunctionHandler;
import com.aizuda.easy.security.util.PathCheckUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

@Component
public class DecryptPathHandler extends AbstractFunctionHandler implements ReqFunctionHandler {

    @Override
    public String exec(HttpServletRequest request, String json) throws BasicException, IOException {
        List<String> urlFilter = properties.getDecryptUrl();
        String url = request.getRequestURI();
        if(CollectionUtil.isEmpty(urlFilter) || !PathCheckUtil.isMatch(urlFilter,url)){
            return json;
        }
        Req<Object,Object> req = mapper.readValue(json, Req.class);
        String decryption = easySecurityServer.decryption(request, mapper.writeValueAsString(req.getData()), properties.getSecretKey());
        req.setData(jsonToObject(decryption));
        return mapper.writeValueAsString(req);
    }

    @Override
    public Integer getIndex() {
        return Integer.MIN_VALUE + 6;
    }


    private Object jsonToObject(String json) throws JsonProcessingException {
        if(!JSONUtil.isTypeJSON(json)){
            return json;
        }
        if(JSONUtil.isTypeJSONObject(json)){
            return mapper.readValue(json, LinkedHashMap.class);
        }
        if(JSONUtil.isTypeJSONArray(json)){
            JavaType javaType = mapper.getTypeFactory().constructParametricType(ArrayList.class, Object.class);
            mapper.readValue(json,javaType);
        }
        return json;
    }

}
