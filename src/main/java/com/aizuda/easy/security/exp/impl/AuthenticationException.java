package com.aizuda.easy.security.exp.impl;

import com.aizuda.easy.security.code.BasicCode;
import com.aizuda.easy.security.exp.IErrorCode;


public class AuthenticationException extends BasicException {

    public AuthenticationException(String msg){
        super(BasicCode.BASIC_CODE_401.getCode(),msg);
    }

    public AuthenticationException(IErrorCode iErrorCode){
        super(iErrorCode);
    }

}
