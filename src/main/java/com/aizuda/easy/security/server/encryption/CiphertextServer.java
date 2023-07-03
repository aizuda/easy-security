package com.aizuda.easy.security.server.encryption;

import cn.hutool.core.util.StrUtil;
import com.aizuda.easy.security.code.BasicCode;
import com.aizuda.easy.security.exp.impl.BasicException;
import com.aizuda.easy.security.util.AesEncryptUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

public interface CiphertextServer {

    String IV = "iv";

    default String encryption(HttpServletResponse response, String json, String key){
        String iv = getIvValue();
        response.setHeader(getIV(), iv);
        return AesEncryptUtil.encryption(json, key, iv);
    }

    default String decryption(HttpServletRequest request, String json,String key) throws BasicException {
        if(StrUtil.isEmpty(key)){
            throw new BasicException(BasicCode.BASIC_CODE_99990);
        }
        String iv = request.getHeader(IV);
        if(StrUtil.isEmpty(iv)){
            throw new BasicException(BasicCode.BASIC_CODE_99989);
        }
        return AesEncryptUtil.decryption(json, key,iv);
    }

    default String getIvValue(){
        return UUID.randomUUID().toString().replace("-","").substring(16);
    }

    default String getIV(){
        return IV;
    }

}
