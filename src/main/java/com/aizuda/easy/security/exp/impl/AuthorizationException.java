package com.aizuda.easy.security.exp.impl;

import com.aizuda.easy.security.code.BasicCode;
import com.aizuda.easy.security.exp.IErrorCode;


public class AuthorizationException extends BasicException {


    public AuthorizationException(String msg){
        super(BasicCode.BASIC_CODE_403.getCode(),msg);
    }

    public AuthorizationException(IErrorCode iErrorCode){
        super(iErrorCode);
    }
    
}
