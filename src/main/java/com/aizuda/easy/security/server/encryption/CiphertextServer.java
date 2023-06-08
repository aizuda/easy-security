package com.aizuda.easy.security.server.encryption;

import com.aizuda.easy.security.server.encryption.impl.AesEncryptServer;

import java.util.UUID;

public interface CiphertextServer {

    String IV = "iv";

    default String encryption(String data, String key, String iv){
        return new AesEncryptServer().encryption(data, key, iv);
    }

    default String decryption(String data, String key, String iv){
        return new AesEncryptServer().decryption(data, key, iv);
    }

    default String getIV(){
        return UUID.randomUUID().toString().replace("-","").substring(16);
    }

}
