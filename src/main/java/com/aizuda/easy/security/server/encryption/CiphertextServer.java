package com.aizuda.easy.security.server.encryption;

import cn.hutool.core.util.StrUtil;
import com.aizuda.easy.security.code.BasicCode;
import com.aizuda.easy.security.exp.impl.BasicException;
import com.aizuda.easy.security.util.AesEncryptUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.util.UUID;

public interface CiphertextServer {

    String IV = "iv";

    default String encryption(HttpServletResponse response, String json, String key) throws BasicException {
        String iv = getIvValue();
        response.setHeader(IV, iv);
        json = AesEncryptUtil.encryption(json, key, iv);
        if(StrUtil.isEmpty(json)){
            throw new BasicException(BasicCode.BASIC_CODE_99988);
        }
        return json.trim();
    }

    default String decryption(HttpServletRequest request, String json, String key) throws BasicException {
        if(StrUtil.isEmpty(key)){
            throw new BasicException(BasicCode.BASIC_CODE_99990);
        }
        String iv = request.getHeader(IV);
        if(StrUtil.isEmpty(iv)){
            throw new BasicException(BasicCode.BASIC_CODE_99989);
        }
        json = AesEncryptUtil.decryption(json, key,iv);
        if(StrUtil.isEmpty(json)){
            throw new BasicException(BasicCode.BASIC_CODE_99987);
        }
        return json;
    }

    default String getIvValue(){
        return UUID.randomUUID().toString().replace("-","").substring(16);
    }

    default String getIV(){
        return IV;
    }

}
